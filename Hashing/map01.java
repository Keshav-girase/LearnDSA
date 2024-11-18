import java.util.HashMap;

public class map01 {
    public static void main(String[] args) {
        HashMap<String,Integer> hm = new HashMap<>();

        //insertion -> O(1)
        hm.put("india", 100);
        hm.put("china", 150);
        hm.put("us", 20);

        System.out.println(hm);

        //Get value using key -> O(1)
        int population = hm.get("india");
        System.out.println(population);

        //Contains key - O(1)
        
    }
}
