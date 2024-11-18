import java.util.ArrayList;
import java.util.PriorityQueue;

public class Heaps {
    
    static class Heap {
        ArrayList<Integer> arr = new ArrayList<>();

        public void add(int data) { // o(logn)
            //add at last index
            arr.add(data);

            int x = arr.size()-1; //x is child index 
            int par = (x - 1) / 2;

            while (arr.get(x) < arr.get(par)) {
                //swap
                int temp = arr.get(x);
                arr.set(x, arr.get(par));
                arr.set(par, temp);
                x = par;
                par = (x - 1) / 2;
            }
        }

        public int peek() {
            return arr.get(0); 
        }

        public void heapify(int i) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int minIdx = i;

            if (left < arr.size() && arr.get(minIdx) > arr.get(left)) {
                minIdx = left;
            }

            if (right < arr.size() && arr.get(minIdx) > arr.get(right)) {
                minIdx = right;
            }

            if (minIdx != i) {
                int temp = arr.get(i);
                arr.set(i, arr.get(minIdx));
                arr.set(minIdx, temp);

                heapify(minIdx);
            }
        }

        public int remove() {
            int data = arr.get(0);

            // swap
            int temp = arr.get(0);
            arr.set(0, arr.get(arr.size()-1));
            arr.set(arr.size()-1, temp);

            // remove last element 
            arr.remove(arr.size()-1);


            heapify(0);

            return data;
        }

       

        public boolean isEmpty() {
            return arr.size() == 0;
        }

    
    }

    /* --------------------- PROBLEM NO : 01 --------------------- */

    public static void heapify ( int arr[], int i, int size) {
        int left = 2*i+1;
        int right = 2*i+2;
        int maxIdx = i;

        if (left < size && arr[left] > arr[maxIdx]) {
            maxIdx = left;
        }

        if (right < size && arr[right] > arr[maxIdx]) {
            maxIdx = right;
        }

        if (maxIdx != i) {
            int temp = arr[i];
            arr[i] = arr[maxIdx];
            arr[maxIdx] = temp;

            heapify(arr, maxIdx, size);
        }
    }

    public static void heapSort(int arr[]) {
        int n = arr.length;
        for (int i = n-1; i >= 0; i--) {
            heapify (arr, i, n);
        }

        for(int i = n-1; i > 0; i--) {
            
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, 0, i); // i this --> i decreased by 1 from loop itself
        }
    }

    /* --------------------- PROBLEM NO : 02 --------------------- */

    /* PROBLEM STATEMENT : nearbt car
     * we are given N point in a 2D plane which are the loacation of N car. 
     * if we are at the origin, print the nearest Kth car
     */
    static class Point implements Comparable<Point> {
        int x;
        int y;
        int distsq;
        int idx;

        public Point(int x, int y,int distsq, int idx) {
            this.x = x;
            this.y = y;
            this.distsq =distsq;
            this.idx = idx;
        }

        @Override
        public int compareTo (Point p2) {
            return this.distsq - p2.distsq;
        }
    }
    /*
        int pts [][] = {{3, 3}, {5, -1}, {-2, 4}};
        int k = 2;

        PriorityQueue<Point>pq = new PriorityQueue<>();
        for (int i = 0; i < pts.length; i++) {
            int distsq =pts[i][0] * pts[i][0] + pts[i][1] * pts[i][1];
            pq.add(new Point(pts[i][0], pts[i][1], distsq, i));
        }

        for(int i = 0; i < k; i++) {
            System.out.println("C" + pq.remove().idx);
        }

     */


    /* --------------------- PROBLEM NO : 03 --------------------- */

    /* PROBLEM STATEMENT : connect N ropes
     * given are N ropes of different lenght , the task is to connect
     * these ropes into one rope with minimum cost, such that the the
     * cost to connect two ropes is equal to the sum of the their lenghts
     */

    public static int connectNRopes(int arr[]) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            pq.add(arr[i]);
        }

        int cost = 0;
        while(pq.size() > 1) {
            int min = pq.remove();
            int min2 = pq.remove();
            cost += min + min2;
            pq.add(min + min2);
        }

        return cost;
    }  

     /* --------------------- PROBLEM NO : 04 --------------------- */


    public static void main(String[] args) {
        int ropes[] = {2, 3, 3 ,4, 6};
        System.out.println("Cost of connecting N ropes = " + connectNRopes(ropes));
    }
}
