import java.util.Arrays;

public class Recursion2 {

    public static boolean checkg(int[] nums) {
        int n = nums.length;
        int arr[] = new int[n];
        int pivotIdx = 0;
        for(int i=0; i<n; i++){
            arr[i] = nums[i];
            if(nums[i] < nums[pivotIdx]){
                pivotIdx = i;
            }
        }
        System.out.println(pivotIdx);
        Arrays.sort(arr);
       
        for(int i=0; i<n; i++){
            if(arr[i] != nums[(i+pivotIdx) % n]){
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        int nums[] = {3,4,5,1,2};
        int n = nums.length;
        for(int i=0; i<n; i++){
            System.out.println(nums[(i+1)%n]);
        }
    }
}

