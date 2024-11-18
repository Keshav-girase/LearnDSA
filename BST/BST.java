import java.util.ArrayList;

public class BST {

    // structure class
    static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
        }
    }

    /* --------------------- PROBLEM NO : 01 (PART - 1) --------------------- */

    // build a BST
    public static Node insert(Node root, int val) {
        if (root == null) {
            root = new Node(val);
            return root;
        }

        if (root.data > val) {
            root.left = insert(root.left, val);
        } else {
            root.right = insert(root.right, val);
        }

        return root;
    }

    /* --------------------- PROBLEM NO : 02 --------------------- */

    // print BST using inorder ( sorted )
    public static void inOrder ( Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.data + " ");
        inOrder(root.right);
    }

    /* --------------------- PROBLEM NO : 03 --------------------- */

    //search in BST
    public static boolean search (Node root, int key) {
        if (root == null) {
            return false;
        }

        if (root.data == key) {
            return true;
        }

        if (root.data > key) {
            return search(root.left, key);
        }
        else {
            return search(root.right, key);
        }
    }

    /* --------------------- PROBLEM NO : 04 --------------------- */

    // To delete node 
    public static Node delete(Node root, int val) {
        if(root.data < val) {
            root.right = delete(root.right, val);
        } else if (root.data > val) {
            root.left =delete(root.left, val);
        } else {
            //case 1 -> leaf node
            if(root.left == null && root.right == null) {
                return null;
            }

            //case 2 --> single chlid
            if(root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // case 3 --> has both chlid
            Node inOrderSucc = findInorderSuccessor(root.right);
            root.data = inOrderSucc.data;
            root.right = delete(root.right, inOrderSucc.data);
        }

        return root; 
    }
    // Helper funx To find the inorder successor 
    public static Node findInorderSuccessor(Node root) {
        while(root.left != null) {
            root = root.left;
        }
        return root;
    }

    /* --------------------- PROBLEM NO : 05 --------------------- */

    public static void printInRange(Node root, int k1, int k2) {
        if (root == null) {
            return;
        } 
        if (root.data >= k1 && root.data <= k2) {   // k1 --> root.data <-- k2 
            printInRange(root.left, k1, k2);
            System.out.print(root.data+" ");
            printInRange(root.right, k1, k2);
        }

        else if (root.data > k2) {      // k1 --> k2 --> root.data
            printInRange(root.left, k1, k2);
        } 

        else {                         // root.data --> k1 --> k2
            printInRange(root.right, k1, k2); 
        }

    }
    
    /* --------------------- PROBLEM NO : 05 --------------------- */
    
    //print node all posible paths in BST
    public static void printRoot2Leaf(Node root, ArrayList<Integer> path) {
        if (root == null) {
            return;
        }
        path.add(root.data);
        if (root.left == null && root.right == null) {
            printPath(path);
        }
        printRoot2Leaf(root.left, path);
        printRoot2Leaf(root.right, path);
        path.remove(path.size()-1);
    }
    public static void printPath(ArrayList<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i) +" -> ");
        }
        System.out.println("Null");
    }

    /* --------------------- PROBLEM NO : 06 --------------------- */

    //
    public static boolean isValidBST(Node root, Node min, Node max) {
        if (root == null) {
            return true;
        }

        if (max != null && root.data >= max.data) {
            return false;
        }
        else if (min != null && root.data <= min.data) {
            return false;
        }

        return isValidBST(root.left, min, root) && isValidBST(root.right, root, max);
    }

    /* --------------------- PROBLEM NO : 07 --------------------- */

    public static Node mirrorBST(Node root) {
        if(root == null) {
            return null;
        }
        Node left = mirrorBST(root.left);
        Node right = mirrorBST(root.right);

        root.left = right;
        root.right = left;

        return root;
    }

    /* --------------------- PROBLEM NO : 08 --------------------- */

    public static void preOrder ( Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.data + " ");
        preOrder(root.left);
        preOrder(root.right);
    
    }

    /* --------------------- PROBLEM NO : 09 --------------------- */

    public static void postOrder ( Node root) {
        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.data + " ");
    }
        //funx call for part - 1
        // Node root = null;
        // int arr [] = {3, 5, 6, 8, 10, 11, 12};
        // for (int i = 0; i < values.length; i++) {
        //     root = insert(root, values[i]);
        // }
        // preOrder(root);
        // System.out.println();
        // Node newRoot = mirrorBST(root);
        // preOrder(newRoot); 

    /* --------------------- PROBLEM NO : 10  (PART - 2) --------------------- */
    
    // Create the balance BST from the list element or array of element 
    public static Node createBalancedBST (int arr[], int si, int ei) {
        if (si > ei) {
            return null;
        }

        int mid = (si + ei)/2;
        Node root = new Node(arr[mid]);
        root.left = createBalancedBST(arr, si, mid-1);
        root.right = createBalancedBST(arr, mid+1, ei);

        return root;

    }

    /*  int arr [] = {3, 5, 6, 8, 10, 11, 12};
        Node root = balancedBST(arr, 0, arr.length-1);
        preOrder(root);
    */
    
    /*
    *  expected tree
    *          8
    *       /     \
    *      5       11
    *    /  \     /  \
    *   3    6  10    12
    */

    /* --------------------- PROBLEM NO : 11 --------------------- */

    public static void getInorder(Node root, ArrayList<Integer> inorder) {
        if (root == null) {
            return;
        }

        getInorder(root.left, inorder);
        inorder.add(root.data);
        getInorder(root.right, inorder);
    }

    public static Node createBST(ArrayList<Integer> inoder ,int si, int ei) {
        if (si > ei) {
            return null;
        }

        int mid = (si + ei) / 2;
        Node root = new Node(inoder.get(mid));
        root.left = createBST(inoder, si, mid-1);
        root.right = createBST(inoder, mid + 1, ei);

        return root;
    }

    public static Node convertToBalBST(Node root) {
        ArrayList<Integer> inorder = new ArrayList<>();
        getInorder(root, inorder);

        root = createBST(inorder, 0, inorder.size()-1);

        return root;
    }
    /*  Node root = new Node(8);

        root.left =new Node(6);
        root.left.left =new Node(5);
        root.left.left.left =new Node(3);

        root.right =new Node(10);
        root.right.right =new Node(11);
        root.right.right.right =new Node(12);

        root = convertToBalBST(root);
        preOrder(root);
    

       original tree

            8
          /   \
         6     12
        /       \
       5         11
      /            \
     3              10
    
    *  expected tree
    *          8
    *       /     \
    *      5       11
    *    /  \     /  \
    *   3    6  10    12
    */

    /* --------------------- PROBLEM NO : 12 --------------------- */

    //size of largest BST in binary tree

    static class Info {
        boolean isBST;
        int size;
        int min;
        int max;

        public Info (boolean isBST, int size, int min, int max) {
            this.isBST = isBST;
            this.size = size;
            this.min = min;
            this.max = max;
        }
    }

    public static int maxBST = 0;
    public static Info largestBST(Node root) {
        if (root == null) {
            return new Info(false, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        Info leftInfo = largestBST(root.left);
        Info rightInfo = largestBST(root.right);
        int size = leftInfo.size + rightInfo.size + 1;
        int min = Math.min(root.data, Math.min(leftInfo.min, rightInfo.min));
        int max = Math.max(root.data, Math.max(leftInfo.max, rightInfo.max));

        if (root.data <= leftInfo.max || root.data >= rightInfo.min) {
            return new Info(false, size, min, max);
        }

        if (leftInfo.isBST && rightInfo.isBST) {
            maxBST = Math.max(maxBST, size);
            return new Info(true, size, min, max);
        }

        return new Info(false, size, min, max);

    }


    /* --------------------- PROBLEM NO : 13 --------------------- */


    public static Node mergeBSTs(Node root1, Node root2) {

        // step - 1
        ArrayList<Integer> arr1 = new ArrayList<>();
        getInorder(root1, arr1);

        // step - 2
        ArrayList<Integer> arr2 = new ArrayList<>();
        getInorder(root2, arr2);

        // step - 3 
        ArrayList<Integer> finalArr = new ArrayList<>();
        int i = 0;
        int j = 0;

        while ( i < arr1.size() && j < arr2.size()) {
            if (arr1.get(i) <= arr2.get(j)) {
                finalArr.add(arr1.get(i));
                i++;
            } else {
                finalArr.add(arr2.get(j));
                j++;
            }

        }

        while (i < arr1.size()) {
            finalArr.add(arr1.get(i));
            i++;
        }

        while (j < arr2.size()) {
            finalArr.add(arr2.get(j));
            j++;
        }

        return createBST(finalArr, 0, finalArr.size()-1);
    } // funx call and inputs
    /* 
    Node root1 = new Node(2);
    root1.left =new Node(1);
    root1.right =new Node(4);
    
    *      2 
    *     /  \    
    *    1    4  
    

    Node root2 = new Node(9);
    root2.left = new Node(3);
    root2.right = new Node(12);
    
    *      9
    *     /  \    
    *    3    12  
    

    Node root = mergeBSTs(root1, root2);
    preOrder(root);
    
    *      3
    *    /   \
    *   1     9
    *    \   / \
    *     2 4   12
        expected final ans tree : BST --> 3 1 2 9 4 12
    */

    /* --------------------- PROBLEM NO : 11 --------------------- */

    public static void main(String[] args) {
        /* 
        Node root1 = new Node(2);
        root1.left =new Node(1);
        root1.right =new Node(4);
       
        *      2 
        *     /  \    
        *    1    4  
        *

        Node root2 = new Node(9);
        root2.left = new Node(3);
        root2.right = new Node(12);
        
        *      9
        *     /  \    
        *    3    12  
        *

        Node root = mergeBSTs(root1, root2);
        preOrder(root);
         *
         *      3
         *    /   \
         *   1     9
         *    \   / \
         *     2 4   12
         *  expected final ans tree : BST --> 3 1 2 9 4 12
         */

    }
}
