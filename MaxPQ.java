import java.util.Comparator;

public class MaxPQ {

    private team[] pq;
    private int size;
    private Comparator<team> comparator;

    public MaxPQ(int capacity, Comparator<team> comparator) {
        pq = new team[capacity + 1]; // pq[0] is not used
        size = 0;
        this.comparator = comparator;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(team item) {
        pq[++size] = item;
        swim(size);
    }

    public team delMax() {
        team max = pq[1];
        exch(1, size--);
        sink(1);
        pq[size + 1] = null; // to avoid loitering
        return max;
    }

    private void swim(int k) {
        while (k > 1 && less(parent(k), k)) {
            exch(parent(k), k);
            k = parent(k);
        }
    }

    private void sink(int k) {
        while (left(k) <= size) {
            int j = left(k);
            if (j < size && less(j, j + 1)) {
                j++;
            }
            if (!less(k, j)) {
                break;
            }
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return comparator.compare(pq[i], pq[j]) < 0;
    }

    private void exch(int i, int j) {
        team temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private int parent(int k) {
        return k / 2;
    }

    private int left(int k) {
        return 2 * k;
    }
    public static void main(String[] args) {
        /*
         * MaxPQ max = new MaxPQ(10, team.compareByTime);
        for(int i = 0; i < 10; i++){
            max.insert(new team(i,0,"kami",0));
        }
        for(int i = 0; i < 10; i++){
            System.out.println(max.delMax().elapsedtime);
        }
         */
    }
}
