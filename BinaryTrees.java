import java.util.*;

/* ------------ BINARY TREE IMPLEMENTATION ----------- */

public class BinaryTrees {


    //  node class for tree
    static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }
    
    // Implentation logic -- USING PREODDER SEQ
    static class Binarytree {
        static int idx = -1;
        public Node buildTree (int nodes[]) {
            idx++;
            if(nodes[idx] == -1) {
                return null;
            }

            Node newNode = new Node(nodes[idx]);
            newNode.left = buildTree(nodes);
            newNode.right = buildTree(nodes);
            return newNode;
        }

        /*  ----------   Traveral   ------------- */

        //preoder travarsal 
        public void preOrder(Node root) {
            if(root == null) {
                return;
            }
            System.out.print(root.data+" ");
            preOrder(root.left);
            preOrder(root.right);
        }

        // post order traversal -- left subtree --> right subtree --> root 
        public static void postOrder(Node root) {
            if(root == null) {
                return;
            }
        
            postOrder(root.left);
            postOrder(root.right);
            System.out.print(root.data+" ");
        }
        
        //inorder traversal
        public static void inOrder(Node root) {
            if(root == null) {
                return;
            }
        
            inOrder(root.left);
            System.out.print(root.data+" ");
            inOrder(root.right);
        }


        //level order traversal 
        public static void levelOrder(Node root) {
            if(root == null) {
                return;
            }

            Queue<Node> q = new LinkedList<>();

            q.add(root);
            q.add(null);
            
            while(!q.isEmpty()) {
                Node currNode = q.remove();
                if(currNode == null) {
                    System.out.println();
                    if(q.isEmpty()) {
                        break;
                    } else {
                        q.add(null);
                    }
                } else {
                    System.out.print(currNode.data+" ");
                    if(currNode.left != null) {
                        q.add(currNode.left);
                    }
                    if(currNode.right != null) {
                        q.add(currNode.right);
                    }
                }
            }
        }
    }

    /* ------------- QUESTIONS BASED ON BINARY TREES ------------- */

    /* --------------------- PROBLEM NO : 01 --------------------- */

    // height of tree
    public static int height(Node root) {
        if(root == null) {
            return 0;
        }

        int leftHeight = height(root.left);
        int rightHeight = height(root.right);
        return Math.max(leftHeight, rightHeight)+1; // +1 for self height 
    }

    /* --------------------- PROBLEM NO : 02 --------------------- */

    // count of nodes in tree
    public static int count(Node root) {
        if(root == null) {
            return 0;
        }

        int leftcount = count(root.left);
        int rightcount = count(root.right);
        return leftcount + rightcount + 1; // +1 for self count

    }

    /* --------------------- PROBLEM NO : 03 --------------------- */

    // sum of each node data in binary tree
    public static int sum(Node root) {
        if(root == null) {
            return 0;
        }

        int leftSum = sum(root.left);
        int rightSum = sum(root.right);

        return leftSum + rightSum + root.data;
    }
    
    /* --------------------- PROBLEM NO : 04 --------------------- */

    // funx to find the diameter of given tree  ... where the diameter 
    // of tree is longest path or distance between the leaf two nodes 
    //  ---> O(n^2)
    public static int diameter(Node root) {
        if(root == null) {
            return 0;
        }
        
        int leftDiam = diameter(root.left);
        int leftHt = height(root.left);
        int rightDiam = diameter(root.right);
        int rightHt = height(root.right);

        int selfDiam = leftHt + rightHt +1;

        return Math.max(selfDiam, Math.max(rightDiam, leftDiam));
    }
                           /*+++++++++++++ */
    
    // helper class 
    static class info {
        int diam;
        int ht;

        public info(int diam, int ht) {
            this.diam = diam;
            this.ht = ht;
        }
    }
    // funx for findind diameter of tree with  --> O(n)
    public static info diameter2(Node root) {
        if(root == null) {
            return new info(0, 0);
        }

        info leftInfo = diameter2(root.left);
        info rightInfo = diameter2(root.right);
        
        int diam = Math.max( Math.max(leftInfo.diam, rightInfo.diam), leftInfo.ht + rightInfo.ht + 1);

        int ht = Math.max(leftInfo.ht, rightInfo.ht) + 1;

        return new info(diam, ht);
    }

    /* --------------------- PROBLEM NO : 05 --------------------- */


    public static boolean isIndical(Node node, Node subRoot) {
        if(node == null && subRoot == null) {
            return true;
        } else if(node == null || subRoot == null || node.data != subRoot.data) {
            return false;
        }
        if( !isIndical(node.left, subRoot.left) ) {
            return false;
        }
        if( !isIndical(node.right, subRoot.right) ) {
            return false;
        }
        return true;
    }

    public static boolean isSubtree(Node root, Node subRoot) {
        if(root == null) {
            return false;
        }
        if(root.data == subRoot.data) {
            if(isIndical(root, subRoot)){
                return true;
            }
        }
       
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }
 
    /* --------------------- PROBLEM NO : 06 --------------------- */

    static class info2 {
        Node node;
        int hd;

        public info2(Node node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }

    public static void topView(Node root) {
        
        Queue<info2> q = new LinkedList<>();
        HashMap<Integer, Node> map = new HashMap<>();

        int min = 0, max = 0;
        q.add(new info2(root, 0));
        q.add(null);

        while (!q.isEmpty()) {
            info2 curr = q.remove();
            if (curr == null) {
                if (q.isEmpty()) {
                    break;
                } else {
                    q.add(null);
                }
            } else {

                if (!map.containsKey(curr.hd)) {
                    map.put(curr.hd, curr.node);
                }
    
                if (curr.node.left != null) {
                    q.add(new info2(curr.node.left, curr.hd-1));
                    min = Math.min(min, curr.hd-1);
                }
                if (curr.node.right != null) {
                    q.add(new info2(curr.node.right, curr.hd+1));
                    max = Math.max(max, curr.hd+1);
                }
            }

           
        }

        for (int i = min; i <= max; i++) {
            System.out.print(map.get(i).data + " ");
        }
    }


    /* --------------------- PROBLEM NO : 07 --------------------- */

    public static void kLevel(Node root, int level, int k){
        if (root == null) {
            return;
        }
        if(level == k) {
            System.out.print(root.data + " ");
            return;
        }

        kLevel(root.left, level+1, k);
        kLevel(root.right, level+1, k);
    }

    /* --------------------- PROBLEM NO : 08 ---------------------   */
    /*      PROBLEM NAME : -->  lowest common ancestor
     * 
     *      [approach : 01 - with extra space and N(3n)] 
     */

    // Helper funx --->
    public static boolean getPath (Node root, int n, ArrayList<Node> path) {
        if (root == null) {
            return false;
        }

        path.add(root);

        if (root.data == n) {
            return true;
        }

        boolean foundLeft = getPath(root.left, n, path);
        boolean foundRight = getPath(root.right, n, path);

        if(foundLeft || foundRight) {
            return true;
        }
        path.remove(path.size()-1);
        return false;
    }
    // cont--->
    public static Node lca(Node root, int n1, int n2) {
        ArrayList<Node> path1 = new ArrayList<>();
        ArrayList<Node> path2 = new ArrayList<>();
        
        getPath(root, n1, path1);
        getPath(root, n2, path2);

        int i = 0;
        for ( ; i < path1.size() && i < path2.size(); i++) {
            if (path1.get(i) != path2.get(i)) {
                break;
            }
        }

        Node lca = path1.get(i-1); // arraylist type is also --> Node .. 
        return lca;

    }  
       
    /*  [approach : 02 - with constent space and N(n)] */

    public static Node lca2(Node root, int n1, int n2) {
        if(root == null){
            return root;
        }
        if(root.data == n1 || root.data == n2) {
            return root;
        }

        Node leftlca = lca2(root.left, n1, n2);
        Node rightlca = lca2(root.right, n1, n2);
        
        // if lca belongs to the left subtree of root
        if(rightlca == null){
            return leftlca;
        }
        // if lca belongs to the right subtree of root
        if(leftlca == null){
            return rightlca;
        }
        // if lca  not belongs to both left and right subtree of root means that belongs to root node
        return root;
    }

    /* --------------------- PROBLEM NO : 09 --------------------- */
   
    /*                      
     * Kth ancestor of node
     */
    public static int kAncestor(Node root, int n, int k) {
        if (root == null) {
            return -1;
        }
        if (root.data == n) {
            return 0;
        }

        int leftDist = kAncestor(root.left, n, k);
        int rightDist = kAncestor(root.right, n, k);

        if (leftDist == -1 && rightDist == -1) {
            return -1;
        }

        int max = Math.max(leftDist, rightDist);
        if (max + 1 == k) {
            System.out.println(root.data);
        }
        return max+1;
    }
   
    /* --------------------- PROBLEM NO : 10 --------------------- */
    
    // transforn to sum tree
    public static int transforn(Node root) {
        if (root == null) {
            return 0;
        }
        int leftChild = transforn(root.left);
        int rightChild = transforn(root.right);

        int data = root.data;
        
        int newLeft = root.left == null ? 0 : root.left.data;
        int newRight = root.right == null ? 0 : root.right.data;

        root.data = newLeft + leftChild + newRight + rightChild;
        return data;
    }

    /* ---------------------  F I N I S H  --------------------- */
    
    public static void main(String[] args) {
        int nodes[] = {1, 2, 4, -1, -1, 5, -1, -1, 3, -1, 6, -1, -1};
        Binarytree tree = new Binarytree();
        Node root = tree.buildTree(nodes);

        /* 
         *       2
         *      / \
         *     4   5
         */
        Node subRoot = new Node(2);
        subRoot.left = new Node(4);
        subRoot.right = new Node(5);
        
        transforn(root);
        tree.preOrder(root);
    }
         
}
