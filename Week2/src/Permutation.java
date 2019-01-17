import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

        while (true) {
            try {
                randomizedQueue.enqueue(StdIn.readString());
            } catch (java.util.NoSuchElementException e) {
                break;
            }
        }
        while (k != 0) {
            System.out.println(randomizedQueue.dequeue());
            --k;
        }
    }
}
