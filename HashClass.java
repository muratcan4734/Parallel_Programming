public class HashClass extends Thread {
	
	ConcurrentHashList<Integer> concurrentHashList ;
	public HashClass(ConcurrentHashList<Integer> concurrentHashList1) {
		this.concurrentHashList = concurrentHashList1;
	}
	public void run() {
		int local = (int)(Math.random()*10000);
		this.concurrentHashList.add(local);
		this.concurrentHashList.remove(local);
	}
}
