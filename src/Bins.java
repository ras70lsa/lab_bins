import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Runs a number of algorithms that try to fit files onto disks.
 */
public class Bins {
	
	
    public static final String DATA_FILE = "example.txt";

    /**
     * Reads list of integer data from the given input.
     *
     * @param input tied to an input source that contains space separated numbers
     * @return list of the numbers in the order they were read
     */
    public List<Integer> readData (Scanner input) {
        List<Integer> results = new ArrayList<Integer>();
        while (input.hasNext()) {
            results.add(input.nextInt());
        }
        return results;
    }

    /*
     * Method for displaying information to user given properly stored disk
     */
    private void giveOutput(PriorityQueue<Disk> disks) {
    	
    	System.out.println("number of pq used: " + disks.size());
        while (!disks.isEmpty()) {
            System.out.println(disks.poll());
        }
        System.out.println();

    }
    
    /*
     * Stores the files to the disk using the inOrder method
     * Calculating the size in this step was left to eliminate using an algorithm to do it separately
     * (possibly the size should have been done separately)
     */
    
    private PriorityQueue<Disk> inOrder(String MethodType, List<Integer> data) {
    	
    	System.out.println(MethodType);
        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
    	pq.add(new Disk(0));

        int diskId = 1;
        int total = 0;
        for (Integer size : data) {
            Disk d = pq.peek();
            if (d.freeSpace() > size) {
                pq.poll();
                d.add(size);
                pq.add(d);
            } else {
                Disk d2 = new Disk(diskId);
                diskId++;
                d2.add(size);
                pq.add(d2);
            }
            total += size;
        }

        System.out.println("total size = " + total / 1000000.0 + "GB");
        System.out.println();
        return pq;
    }
    
    /*
     * Stores the files to the disk using the decreasingOrder method
     */
    
    private PriorityQueue<Disk> decreasingOrder(String str, List<Integer> data) {
    	
    	 PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        pq.add(new Disk(0));

        int diskId = 1;
        for (Integer size : data) {
            Disk d = pq.peek();
            if (d.freeSpace() >= size) {
                pq.poll();
                d.add(size);
                pq.add(d);
            } else {
                Disk d2 = new Disk(diskId);
                diskId++;
                d2.add(size);
                pq.add(d2);
            }
        }

        System.out.println();
        return pq;
    }
    /**
     * The main program.
     */
    public static void main (String args[]) {
        Bins b = new Bins();
        Scanner input = new Scanner(Bins.class.getClassLoader().getResourceAsStream(DATA_FILE));
        List<Integer> data = b.readData(input);

        //In Order method
        
        PriorityQueue<Disk> pq = new PriorityQueue<Disk>();
        b.giveOutput(b.inOrder("worst-fit method", data));
        

        //Sort list
        Collections.sort(data, Collections.reverseOrder());
        
        //Decreasing Order method
        b.giveOutput(b.decreasingOrder("worst-fit decreasing method", data));
      
    }
}
