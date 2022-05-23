import java.util.*;
import java.util.concurrent.locks.*;

public class ConcurrentHashList<E> implements Iterable<E> {
    private LinkedList<E> data[];
    private Lock lock = new ReentrantLock();

    @SuppressWarnings("unchecked")
    public ConcurrentHashList(int n) {
        if (n > 1000)
            data = (LinkedList<E>[]) (new LinkedList[n / 10]);
        else
            data = (LinkedList<E>[]) (new LinkedList[100]);
        for (int j = 0; j < data.length; j++)
            data[j] = new LinkedList<E>();
    }

    @SuppressWarnings("unchecked")
    public ConcurrentHashList(Collection<? extends E> cl) {
        if (cl.size() > 1000)
            data = (LinkedList<E>[]) (new LinkedList[cl.size() / 10]);
        else
            data = (LinkedList<E>[]) (new LinkedList[100]);
        for (int j = 0; j < data.length; j++)
            data[j] = new LinkedList<E>();
        for (E x : cl) this.add(x);
    }

    private int hashC(E x) {
        int k = x.hashCode();
        int h = Math.abs(k % data.length);
        return (h);
    }

    public void add(E x) {
        if (x != null) {
            lock.lock();
            try {
                int index = hashC(x);
                if (!data[index].contains(x))
                    data[index].add(x);
            } finally {
                lock.unlock();
            }
        }
    }

    public boolean contains(E x) {
        if (x == null) return false;
        lock.lock();
        try {
            int index = hashC(x);
            return (data[index].contains(x));
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(E x) {
        if (x == null) return false;
        lock.lock();
        try {
            int index = hashC(x);
            return data[index].remove(x);
        } finally {
            lock.unlock();
        }
    }

    public String toString() {
        lock.lock();
        try {
            StringBuffer s = new StringBuffer(this.size());
            s.append('<');
            int ind = 0;
            while (ind < data.length) {
                Iterator<E> it = data[ind].iterator();
                while (it.hasNext())
                    s.append(it.next() + ", ");
                ind++;
            }
            s.deleteCharAt(s.length() - 1);
            s.setCharAt(s.length() - 1, '>');
            return s.toString();
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            int j = 0;
            for (LinkedList<E> lst : data) j += lst.size();
            return j;
        } finally {
            lock.unlock();
        }
    }

    public Iterator<E> iterator() {
        lock.lock();
        try {
            ArrayList<E> items = new ArrayList<E>();
            int ind = 0;
            while (ind < data.length) {
                Iterator<E> it = data[ind].iterator();
                while (it.hasNext())
                    items.add(it.next());
                ind++;
            }
            return items.iterator();
        } finally {
            lock.unlock();
        }
    }
}