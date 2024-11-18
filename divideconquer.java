public class divideconquer {
    public static void printArr(int arr[]){
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
    
    // merge sort 
    public static void mergeSort(int arr[], int si, int ei){
        if(si >= ei) {
            return;
        }

        //kaam
        int mid = si + (ei -si)/2;
        mergeSort(arr, si, mid);
        mergeSort(arr, mid+1, ei);
        merge(arr, si, mid, ei); // call to helping funx
    }

    //helping function for mergeSort
    public static void merge(int arr[], int si, int mid, int ei){

        int temp[] = new int[ei-si+1]; // dividing the array into two halfs
        int i = si; // interator for left part
        int j = mid+1; // interator for right part
        int k = 0; // interator for temp array

        while(i <= mid && j <= ei){
            if(arr[i] < arr[j]){
                temp[k] = arr[i];
                i++;
            } else {
                temp[k] = arr[j];
                j++;
            }
            k++;
        }

        //for left part remaining 
        while(i <= mid) {
            temp[k++] = arr[i++];
        } 

        //for right part remaining 
        while(j <= ei) {
            temp[k++] = arr[j++];
        }

        //copy temp to originsl array
        for (k=0, i=si; k<temp.length; k++, i++ ) {
            arr[i] = temp[k];
        }
    }

    // quick sort 
    public static void quickSort(int arr[], int si, int ei){
        if(si >= ei) {
            return;
        }

        int pIdx = partition(arr, si, ei);
        quickSort(arr, si, pIdx-1);
        quickSort(arr, pIdx+1, ei);

    }

    // helping funx for quick sort funx
    public static int partition(int arr[], int si, int ei){
        int pivot = arr[ei];
        int i = si - 1;

        for (int j = si; j < ei; j++) {
            if( arr[j] <= pivot){
                i++;
                int temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
        // to place pivot at right position
        i++;
        int temp = pivot;
        arr[ei] = arr[i]; // pivot is veriable and we want to make change in array so i.e arr[ei] instead 
        arr[i] = temp;
        return i;
 
    }



    //search in rotated sorted array
    /* input - sorted, rotated array with distinct number number 
     * (in ascending order) it is rotated at a pivot point. find the index 
     * of given element -> 2 5 6 7 0 1 2 , target -> 0
     */

    public static int search(int arr[], int tar, int si, int ei){

        if (si > ei) {
            return -1;
        }
        int mid = si * (ei - si)/2;

        if(arr[mid] == tar){
            return mid;
        }

        if(arr[si] <= arr[mid]){
            if(arr[si] <= tar && arr[mid] <= tar){
                return search(arr, tar, si, mid-1);
            } else {
                return search(arr, tar, mid+1, ei);
            }
        }else {
            if(arr[si] <= tar && tar <= arr[ei]){
                return search(arr, tar, mid+1, ei);
            }else{
                return search(arr, tar, si, mid-1);
            }
        }

    }

    public static void main(String[] args) {
        int arr[] = {6, 3, 9, 8, 2, 5};
        

    }
       
}
