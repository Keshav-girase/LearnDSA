public class Recursion1 {

    // recursive fuction to print number from n to 1
    public static void printDec(int n){
        if(n == 1){
            System.out.print(n);
            return;
        }
        System.out.print(n+" ");
        printDec(n - 1);
    }


    // recursive fuction to print number from 1 to n
    public static void printInc(int n){
        if(n == 1){
            System.out.print(n+" ");
            return;
        }
        printInc(n-1);
        System.out.print(n+" ");
    } // funx call --->  printInc(10);


    // print factorial of a number N
    public static int fact(int n){
        if(n==0){
            return 1;
        }
        int fn1 = fact(n-1);
        int result = n * fn1;
        return result;

    }// funx call ---> System.out.println(fact(5));


    //print sum of first N natural number
    public static int sum(int N){
        if(N==1){
            return 1;
        }
        int fn1 = sum(N-1);
        return N + fn1;
    }// funx call --> System.out.println(sum(10)); ==> 55


    //recursive funx to calculate N fibonacci number where 1st tearm = 0 and 2nd tearm = 1;
    public static int fibonacciNum(int n){
        if(n==0 || n==1){
            return n;
        }
        return fibonacciNum(n-1) + fibonacciNum(n-2);
    }//funx call -->  System.out.println(fibonacciNum(28)); ==> 317811


    //recursive funx to cheack if array is sorted or not
    // funx name >> isSorted
    public static boolean checkforSorted(int arr[], int i){
         if(arr.length-1 == i){
            return true;
        }
        if(arr[i] > arr[i+1]){
            return false;
        }
        return checkforSorted(arr, i+1);
    }/*funx call --> int arr[]={ 3, 4, 5, 94, 10, 13, 15, 16, 19};
                     System.out.print(checkforSorted(arr, 0));
                     false --->> because of 94 i.e is  3rd index
    */


    // WAF to find the firsrt occurence of key in arr and return index i.e search and element and thair index 
    public static int firstOccurence(int arr[], int i, int key){
        if(arr.length == i){
            return -1;
        }
        if(arr[i]==key){
            return i;
        }
        return firstOccurence(arr, i+1, key);
    } /* funx call ---> int arr[]={ 3, 4, 5, 94, 10, 13, 15, 16, 19};
                        System.out.println(firstOccurence(arr, 0, 10)); 
    */


    //WAF a to find the last of key in give array 
    public static int lastOccurence(int arr[], int i, int key){
        if (i == arr.length) {
            return -1;
        }

        int isFound = lastOccurence(arr, i+1, key);
        if(isFound == -1 && arr[i] == key){
            return i;
        }
        return isFound;
    } /* funx call ---> int arr[]={ 3, 4, 5, 94, 10, 13, 15, 4, 19};
                        System.out.println(lastOccurence(arr, 0, 4)); 
                 ===>   7
    */
 
    // WAF a  to calculate the x power n i.e x^n
    public static int power(int x, int n){
        if(n == 0){
            return 1;
        }
        int xnm1 = power(x, n-1);
        return xnm1*x;
    } //     System.out.println(power(2, 10));
     // ==>  1024
    // wrong ... but improved if possible
    public static int power2(int x, int n){
        if(n <= 2){
            if (n < 2) {
                return (x*x)*x;
            }
            return x*x;
        }
        int halfPow = power2(x, n/2);

        int halfPow2 = halfPow*halfPow;
        return halfPow2;
    }


    // IMPORTANT NOTE ---->
    // 3/2 => 1 stop division after .
    // 3%2 => 1

    
    // optimized function to calculate x power n i.e x^n
    public static int optimizedPower2(int x, int n){
        if(n == 0){
            return 1;
        } 
        int halfPow = optimizedPower2(x, n/2);
        int halfPowSq = halfPow * halfPow;
        
        if(n % 2 != 0){
            halfPowSq = x * halfPowSq;
        }
        return halfPowSq;
    }// funx call --> 


     
    /* //tiling problem  ---
    given a "2*n" board and tiles of size "2*1",
    count the number of way to tile the given board using the "2*1" tiles 
    (a tile can either be board horizontally or vertically) 
    */
    public static int tillingProblem(int n){
        if(n == 0 || n == 1){
            return 1;
        }
        //vertical choices
        int fnm1 = tillingProblem(n-1);
        //horizonatal choices 
        int fnm2 = tillingProblem(n-2);
        // total ways 
        int totways = fnm1 + fnm2;
        return totways; 
    }//


    // remove duplicates in string ( char are only from capital "a to z")
    public static void removeDuplicates(String str, int idx, StringBuilder newStr, boolean map[] ){
        if(idx == str.length()){
            System.out.println(newStr);
            return;
        }

        char currChar = str.charAt(idx);
        if(map[currChar - 'a'] == true){
            //duplicates case
            removeDuplicates(str, idx+1, newStr, map);
        } else {
            map[currChar -'a'] = true;
            removeDuplicates(str, idx+1, newStr.append(currChar), map);
        }
    }


    // friends pairing problem 
    /*
     * 
     * 
     * 
     */
    public static int friendsPairing(int n){
        if(n == 1 | n == 2){
            return n;
        }
        //choise for single
        int fnm1 = friendsPairing(n-1);

        //choise for pairs 
        int fnm2 = friendsPairing(n-2);
        int pairWays = (n-1) * fnm2;

        //toatal ways
        int totalWays = fnm1 + pairWays;
        return totalWays;
    }



    //binary strings problem
    /*
     *print all binary srtings of size N without concsecutive ones
     */
    public static void printBinStrings(int n, int lastPlace, String str){
        if(n == 0){
            System.out.println(str);
            return;
        }

        printBinStrings(n-1, 0, str+0);

        if( lastPlace == 0){
            printBinStrings(n-1, 1, str+1);
        }
    }



    public static void main(String[] args) {
       printBinStrings(3, 0, "");
    }
} 