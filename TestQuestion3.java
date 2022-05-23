public class TestQuestion3 {
	  public static void main(String[] args) {
	    	ConcurrentHashList<Integer> concurrentHashList;
		  concurrentHashList = new ConcurrentHashList<>(10000);

		  HashClass[] hashClassTable = new HashClass[10000];

	      long startTime = System.currentTimeMillis();
	      for (int i = 0; i < 10000; i++) {
	    	  hashClassTable[i] = new HashClass(concurrentHashList);
	    	  hashClassTable[i].start();
	      }

	      try {
	          for (int i = 0; i < 10000; i++) {
	        	  hashClassTable[i].join();
	          }
	      } catch (InterruptedException e) {
	          e.printStackTrace();
	      }

	      long endTime = System.currentTimeMillis();
	      long runTime = endTime - startTime;
	      System.out.println(runTime + " milliseconds (" + (runTime / 1000.0) + ")");
	  }

}
