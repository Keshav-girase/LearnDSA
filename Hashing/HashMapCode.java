import java.util.ArrayList;
import java.util.LinkedList;

public class HashMapCode {

    static class HashMap<K, V> { // generic 
        // Node class for linkedlist
        private class Node {
            K key;
            V value;

            public Node(K key, V value) {
                this.key = key;
                this.value = value;
            }
        }

        private int n; // NUMBER OF NODES 
        private int N; // BUCKET SIZE
        private LinkedList<Node> buckets[]; // array with linkedlist data type 

        @SuppressWarnings("unchecked")
        public HashMap() { // CONSTRUCTOR OF HASHMAP CLASS
            this.N = 4;
            this.buckets = new LinkedList[4]; // INTAILIZE BUCKET WITH LINKEDLIST DATA TYPE AND SIZE 4
            for (int i = 0; i < buckets.length; i++) {
                this.buckets[i] = new LinkedList<>(); // ASSIGN EMPTY LINKEDLIST TO EACH BUCKET INDEX
            }
        }

        public int hashFunction(K key) {
            int hc = key.hashCode();
            return Math.abs(hc) % N;
        }

        private int searchInLL(K key, int bi) {
            LinkedList<Node> ll = buckets[bi]; //  bi --> bucket index
            int di = 0; // data index

            for (Node node : ll) {
                if (node.key.equals(key)) {
                    return di;
                }
                di++;
            }

            return -1; 
        }
 
        private void rehash() {
            LinkedList<Node> oldBuck[] = buckets;
            buckets = new LinkedList[2*N];
            N = 2 * N;
            for (int i = 0; i < buckets.length; i++) {
                this.buckets[i] = new LinkedList<>();
            }
            
            // for (int i = 0; i < oldBuck.length; i++) {
            //     LinkedList<Node> ll = oldBuck[i];
            //     for (int j = 0; j < ll.size(); j++) {
            //         Node node = ll.remove(j);
            //         put(node.key, node.value);
            //     }
            // }
            for (LinkedList<Node> ll : oldBuck) {
                for (Node node : ll) {
                    put(node.key, node.value);
                }
            }
        }

        public void put(K key, V value) {
            int bi = hashFunction(key);
            int di = searchInLL(key, bi);

            if (di != -1) {
                Node node = buckets[bi].get(di); // FIND NODE FROM LINKEDLIST PRESENT AT Data INDEX I.E di
                node.value = value;
            } else {
                buckets[bi].add(new Node(key, value));
                n++;
            }
            double lambda = (double) n/N;
            if (lambda > 2.0) {
                rehash();
            }
        }

        public boolean containsKey(K key) {
            int bi = hashFunction(key);
            int di = searchInLL(key, bi);

            if (di != -1) {
                return true;
            } else {
                return false;
            }
        }

        public V get(K key) {
            int bi = hashFunction(key);
            int di = searchInLL(key, bi);

            if (di != -1) {
                Node node = buckets[bi].get(di); // FIND NODE FROM LINKEDLIST PRESENT AT Data INDEX I.E di
                return node.value;
            } else {
                return null;
            }
        }

        public V remove(K key) {
            int bi = hashFunction(key);
            int di = searchInLL(key, bi);

            if (di != -1) {
                Node node = buckets[bi].remove(di); // FIND NODE FROM LINKEDLIST PRESENT AT Data INDEX I.E di
                n--;
                return node.value;
            } else {
                return null;
            }
        }

        public ArrayList<K> keySet() {
            ArrayList<K> keys = new ArrayList<>();

            for (LinkedList<Node> ll : buckets) {
                for (Node node : ll) {
                    keys.add(node.key);
                }
            }
            return keys;
        }

        public boolean isEmpty() {
            return n == 0;
        }
    }

    public static void main(String[] args) {
        HashMap<String, Integer> hm = new HashMap<>(); 

        // Test put and get methods
        hm.put("keshav", 201);
        hm.put("john", 102);
        hm.put("jane", 203);

        System.out.println("Value for 'keshav': " + hm.get("keshav")); // Output: 201
        System.out.println("Value for 'john': " + hm.get("john")); // Output: 102
        System.out.println("Value for 'jane': " + hm.get("jane")); // Output: 203

        // Test containsKey method
        System.out.println("Contains key 'keshav': " + hm.containsKey("keshav")); // Output: true
        System.out.println("Contains key 'doe': " + hm.containsKey("doe")); // Output: false

        // Test remove method
        System.out.println("Removed value for 'john': " + hm.remove("john")); // Output: 102
        System.out.println("Contains key 'john' after removal: " + hm.containsKey("john")); // Output: false

        // Test keySet method
        System.out.println("Keys: " + hm.keySet()); // Output: [keshav, jane]

        // Test isEmpty method
        System.out.println("Is empty: " + hm.isEmpty()); // Output: false

        // Remove remaining keys
        hm.remove("keshav");
        hm.remove("jane");

        // Test isEmpty method after removing all keys
        System.out.println("Is empty after removing all keys: " + hm.isEmpty()); // Output: true
    }
}
