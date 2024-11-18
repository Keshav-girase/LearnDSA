package Graph;
import java.util.*;

import javax.sound.sampled.DataLine.Info;

public class Graph01 {
    static class Edge implements Comparable<Edge> {
        int src;
        int dest;
        int w;

        Edge(int src, int dest, int w) {
            this.src = src;
            this.dest = dest;
            this.w = w;
        }
        // Compare edges based on their weight (for sorting)
        public int compareTo(Edge other) {
            return this.w - other.w;
        }
        
    }

    static void printGraph(ArrayList<Edge>[] graph) {
        for(int i = 0; i < graph.length; i++) {
            System.out.print("Vertex " + i + " has edges to: ");
            for(Edge edge : graph[i]) {
                System.out.print(" (dest: " + edge.dest + ", weight: " + edge.w + ")");
            }
            System.out.println();
        }
    }

    public static void bfs(ArrayList<Edge>[] graph) {
        Queue<Integer> q = new LinkedList<>();         // Queue to hold vertices for BFS traversal
        boolean vis[] = new boolean[graph.length];     // Visited array to track visited nodes
    
        q.add(0);                                      // Start BFS from node 0 (or any starting node)
    
        while (!q.isEmpty()) {
            int curr = q.remove();                     // Dequeue a vertex
    
            if (!vis[curr]) {                          // Process if not visited
                System.out.print(curr + " ");          // Print the current node
                vis[curr] = true;                      // Mark current node as visited
    
                for (int i = 0; i < graph[curr].size(); i++) {
                    Edge e = graph[curr].get(i);       // Access each edge connected to current node
                    q.add(e.dest);                     // Enqueue adjacent vertices
                }
            }
        }
    }

    public static void dfs(ArrayList<Edge>[] graph, int curr, boolean[] visited) {
        System.out.print(curr + " ");       // Process the current node (print it)
        visited[curr] = true;               // Mark the current node as visited

        // Recursively visit all unvisited neighbors
        for (Edge edge : graph[curr]) {     // For each edge from the current node
            int neighbor = edge.dest;       // Get the destination node
            if (!visited[neighbor]) {       // If not visited, recurse
                dfs(graph, neighbor, visited);
            }
        }
    }


    public static boolean hasPath(ArrayList<Edge>[] graph, int src, int dest, boolean[] visited) {
        // Base Case: If source is the same as destination, a path exists
        if (src == dest) {
            return true;
        }
    
        // Mark the source node as visited to avoid revisiting it
        visited[src] = true;
    
        // Explore all adjacent nodes (neighbors)
        for (int i = 0; i < graph[src].size(); i++) {
            Edge e = graph[src].get(i);    // Get each edge connected to the current node
    
            // Recursive Case: If the destination node of the edge has not been visited,
            // and there exists a path from this node to the destination, return true
            if (!visited[e.dest] && hasPath(graph, e.dest, dest, visited)) {
                return true;
            }      
        }
        
        // If no path found, return false
        return false;
    }

    // Function to detect a cycle in an undirected graph.
    public static boolean detectCycle(ArrayList<Edge>[] graph) {
        boolean visited[] = new boolean[graph.length]; // Track visited nodes
        
        // Iterate over all nodes to handle disconnected components
        for (int i = 0; i < graph.length; i++) {
            if (!visited[i]) { // If node 'i' is unvisited
                // Start DFS from this node; check if cycle is detected
                if (detectCycleUtil(graph, visited, i, -1)) {
                    return true; // Cycle found
                }
            }
        }
        
        return false; // No cycle found in any component
    }
    
    // Utility function to perform DFS and detect cycle in an undirected graph
    public static boolean detectCycleUtil(ArrayList<Edge>[] graph, boolean visited[], int curr, int par) {
        visited[curr] = true; // Mark the current node as visited
        
        // Iterate through all edges of the current node
        for (int i = 0; i < graph[curr].size(); i++) {
            Edge e = graph[curr].get(i); // Get the edge from the adjacency list
            
            // If the destination node is not visited, perform DFS on it
            if (!visited[e.dest]) {
                // Recursively visit the destination node
                if (detectCycleUtil(graph, visited, e.dest, curr)) {
                    return true; // Cycle found in the recursion
                }
            } else if (e.dest != par) { // (visited[e.dest] && e.dest != par) --> why?
                // If destination node is visited and is not the parent node
                // of the current node, then a cycle is detected
                return true;
            }
        }   
        return false; // No cycle found from the current node
    }

    public static boolean isBipartite(ArrayList<Edge>[] graph) {
        int[] colors = new int[graph.length]; // Array to store color (0 or 1) of each node
        
        // Initialize all nodes as uncolored (-1)
        for (int i = 0; i < colors.length; i++) {
            colors[i] = -1;
        }
    
        Queue<Integer> queue = new LinkedList<>(); // Queue for BFS traversal
    
        // Iterate through each node to handle disconnected components
        for (int startNode = 0; startNode < graph.length; startNode++) {
            if (colors[startNode] == -1) { // If the node is uncolored
                queue.add(startNode); // Start BFS from this node
                colors[startNode] = 0; // Color the starting node with color 0
    
                // BFS traversal
                while (!queue.isEmpty()) {
                    int currentNode = queue.remove(); // Get the next node in the queue
                    
                    // Iterate through all neighbors of the current node
                    for (int edgeIndex = 0; edgeIndex < graph[currentNode].size(); edgeIndex++) {
                        Edge edge = graph[currentNode].get(edgeIndex); // Get the edge
                        
                        // If the neighbor node is uncolored
                        if (colors[edge.dest] == -1) {
                            // Assign opposite color to neighbor
                            colors[edge.dest] = 1 - colors[currentNode];
                            queue.add(edge.dest); // Add neighbor to the queue for further exploration
                        } else if (colors[edge.dest] == colors[currentNode]) {
                            // If the neighbor has the same color as the current node, graph is not bipartite
                            return false;
                        }
                    }
                }
            }
        }
    
        return true; // All components are bipartite
    }

    public static void topoSort(ArrayList<Edge>[] graph) {
        boolean[] vis = new boolean[graph.length];
        Stack<Integer> s = new Stack<>(); // Define stack with Integer type for better type safety

        // Perform DFS for each unvisited node to ensure all nodes are processed
        for (int i = 0; i < graph.length; i++) {
            if (!vis[i]) {
                topoSortUtil(graph, i, s, vis);
            }
        }

        // Print the topological sort order by popping from the stack
        while (!s.isEmpty()) {
            System.out.print(s.pop() + " ");
        }
    }

    private static void topoSortUtil(ArrayList<Edge>[] graph, int curr, Stack<Integer> s, boolean[] vis) {
        vis[curr] = true; // Mark the current node as visited

        // Visit all adjacent nodes of the current node
        for (int i = 0; i < graph[curr].size(); i++) {
            Edge e = graph[curr].get(i);
            if (!vis[e.dest]) {
                topoSortUtil(graph, e.dest, s, vis);
            }
        }
        
        // Push the current node onto the stack once all adjacent nodes are processed
        s.push(curr);
    }

    // Helper method to calculate the in-degree of each vertex in the graph
    public static void calculateInDegrees(ArrayList<Edge>[] graph, int[] inDegree) {
        // Iterate through each vertex's adjacency list
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].size(); j++) {
                Edge e = graph[i].get(j);
                // Increment in-degree for destination vertex
                inDegree[e.dest]++; 
            }
        }
    }


    // Que no - 210 leetcode course schedule -  solve this one
    // Method to perform topological sort using Kahn's Algorithm (BFS approach)
    public static void topoSort2(ArrayList<Edge>[] graph) {
        int[] inDegree = new int[graph.length];  // Array to store in-degrees of all vertices
        Queue<Integer> queue = new LinkedList<>();  // Queue to manage vertices with in-degree of 0

        // Step 1: Calculate the initial in-degrees for all vertices
        calculateInDegrees(graph, inDegree);

        // Step 2: Add all vertices with in-degree 0 to the queue
        for (int i = 0; i < graph.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        // Step 3: Process each vertex in the queue
        while (!queue.isEmpty()) {
            int curr = queue.remove();  // Dequeue vertex with in-degree 0
            System.out.print(curr + " ");  // Print the vertex as part of topological order

            // For each outgoing edge from the current vertex
            for (int i = 0; i < graph[curr].size(); i++) {
                Edge e = graph[curr].get(i);
                inDegree[e.dest]--;  // Decrement in-degree of the neighboring vertex

                // If in-degree becomes 0, add it to the queue
                if (inDegree[e.dest] == 0) {
                    queue.add(e.dest);
                }
            }
        }
    }

    public static void allPath(ArrayList<Edge>[] graph, int sre, int dest, String path) {
        if(sre == dest) {
            System.out.println(path+dest);
            return;
        }

        for (int i = 0; i < graph[sre].size(); i++) {
            Edge e = graph[sre].get(i);
            allPath(graph, e.dest, dest, path+sre);
        }
    }

    public static class Pair implements Comparable<Pair> {
        int node; // Node in the graph
        int path; // Distance from the source node
    
        public Pair(int node, int path) {
            this.node = node;
            this.path = path;
        }
    
        // Comparison based on path length (used by priority queue)
        @Override
        public int compareTo(Pair p2) {
            return this.path - p2.path;
        }
    }
    
    public static void dijkstra(ArrayList<Edge>[] graph, int src) {
        int dist[] = new int[graph.length];
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize distances to "infinity"
        dist[src] = 0; // Distance to source is 0
    
        boolean vis[] = new boolean[graph.length]; // Track visited nodes
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(src, 0)); // Start from the source node
    
        while (!pq.isEmpty()) {
            Pair curr = pq.remove(); // Get node with smallest path distance
    
            if (!vis[curr.node]) {
                vis[curr.node] = true; // Mark node as visited
    
                // Iterate over neighbors of the current node
                for (Edge e : graph[curr.node]) {
                    int u = e.src;
                    int v = e.dest;
                    int wt = e.w;
    
                    // Relaxation: Check if a shorter path to v is found
                    if (!vis[v] && dist[u] + wt < dist[v]) { // why ---> !vis[v] && -- check lecture 
                        dist[v] = dist[u] + wt; // Update the distance to v
                        pq.add(new Pair(v, dist[v])); // Add the updated node to the queue
                    }
                }
            }
        }

        // Print shortest distances from source to each node
        for (int i = 0; i < dist.length; i++) {
            System.out.println("Distance from " + src + " to " + i + " is " + dist[i]);
        }
    }


    public static void bellmanFord(ArrayList<Edge>[] graph, int src, int V) {
        int dist[] = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize distances to "infinity"
        dist[src] = 0; // Distance to source is 0

        for (int i = 0; i < V-1; i++) {
            
            for (int j = 0; j < graph.length; j++) {
                for (Edge e : graph[j]) {
                    int u = e.src;
                    int v = e.dest;
                    int wt = e.w;
    
                    // Relaxation: Check if a shorter path to v is found
                    if (dist[u] != Integer.MAX_VALUE && dist[u] + wt < dist[v]) { // 
                        dist[v] = dist[u] + wt; // Update the distance to v
                    }
                }
            }
        }

        // Print shortest distances from source to each node
        for (int i = 0; i < dist.length; i++) {
            System.out.println("Distance from " + src + " to " + i + " is " + dist[i]);
        }
    }

    public static class Pair2 implements Comparable<Pair2>{
        int v;
        int cost;
        
        public Pair2 (int v, int cost) {
            this.v = v;
            this.cost = cost;
        }

        @Override 
        public int compareTo(Pair2 p2) {
            return this.cost - p2.cost;
        }
    }
    
    public static void prims(ArrayList<Edge>[] graph) {
        boolean vis[] = new boolean[graph.length]; // Keeps track of visited nodes
        PriorityQueue<Pair2> pq = new PriorityQueue<>(); // Min-heap to pick minimum cost edge
        pq.add(new Pair2(0, 0)); // Start from node 0 with initial cost 0
        int finalCost = 0; // Total weight of the MST
    
        while (!pq.isEmpty()) { // Process nodes while there are edges to explore
            Pair2 curr = pq.remove(); // Get the edge with the smallest cost
            if (!vis[curr.v]) { // If the node is not visited
                vis[curr.v] = true; // Mark it as visited
                finalCost += curr.cost; // Add its cost to the total cost of MST
    
                // Explore all edges of the current node
                for (int i = 0; i < graph[curr.v].size(); i++) {
                    Edge e = graph[curr.v].get(i);
                    pq.add(new Pair2(e.dest, e.w)); // Add the neighboring nodes to the priority queue
                }
            }
        }
    
        System.out.println("Minimum final cost of MST " + finalCost); // Output the total MST cost
    }


    static void createGraph(int flights[][], ArrayList<Edge>[] graph) {       
        for(int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int i = 0; i < flights.length; i++) {
            int src = flights[i][0];
            int dest = flights[i][1];
            int wt = flights[i][2];

            graph[src].add(new Edge(src, dest, wt));
        }
    }

    static class Info {
        int src;
        int cost;
        int stops;

        public Info(int src, int cost, int stops) {
            this.src = src;
            this.cost = cost;
            this.stops = stops;
        }
    }

    // this code has some issue figure it out 
    public static int cheapestFlight(int n, int flights[][], int src, int des, int k) {
        ArrayList<Edge> graph[] = new ArrayList[n];
        createGraph(flights, graph);
        int dist[] = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE); // Initialize distances to "infinity"
        dist[src] = 0;

        Queue<Info> q = new LinkedList<>();
        q.add(new Info(src, 0, 0));

        while (!q.isEmpty()) {
            Info curr = q.remove();
            if(curr.stops > k) {
                break;
            }

            for (int i = 0; i < graph[curr.src].size(); i++) {
                Edge e = graph[curr.src].get(i);
                int s = e.src;
                int d = e.dest;
                int w = e.w;

                // why this not --> dist[s] != Integer.MAX_VALUE && dist[s] + w < dist[d] && curr.stops <= k
                if ( curr.cost + w < dist[d] && curr.stops <= k) {
                    dist[d] = curr.cost + w;
                    q.add(new Info(d, dist[d], curr.stops+1));
                }
            }
        }
        if (dist[des] == Integer.MAX_VALUE) {
            return -1;
        }

        return dist[des];
    }
    

    public static class DisjointSet {
        // Array to store the parent of each node
        int[] parent;
        // Array to store the rank (or height) of each tree
        int[] rank;

        // Constructor to initialize the disjoint set
        // 'size' is the number of elements in the set
        public DisjointSet(int size) {
            parent = new int[size];  // Initialize the parent array
            rank = new int[size];    // Initialize the rank array

            // Initially, each node is its own parent (each node is a separate set)
            for (int i = 0; i < parent.length; i++) {
                parent[i] = i;  // Each element points to itself as its own parent
                rank[i] = 0;    // Initially, all elements have rank 0 (tree height)
            }
        }

        // Find operation: Finds the representative (root) of the set containing 'x'
        public int find(int x) {
            if (parent[x] != x) { // If x is not the root, recursively find the root
                parent[x] = find(parent[x]); // Path compression: make the parent of x point directly to the root
            }
            return parent[x]; // Return the root (representative) of the set containing x
        }

        // Union operation: Merges the sets containing 'x' and 'y'
        public void union(int x, int y) {
            // Find the root of the sets containing x and y
            int parentX = find(x);
            int parentY = find(y);

            // If the roots are different, merge the sets
            if (parentX != parentY) {
                // Union by rank: attach the smaller tree under the larger tree to keep the tree balanced
                if (rank[parentX] > rank[parentY]) {
                    parent[parentY] = parentX; // Make the root of parentY point to parentX
                } else if (rank[parentX] < rank[parentY]) {
                    parent[parentX] = parentY; // Make the root of parentX point to parentY
                } else {
                    parent[parentY] = parentX; // If both have the same rank, arbitrarily make parentY point to parentX
                    rank[parentX]++;           // Increment the rank of the new root
                }
            }
        }
    }

     // Kruskal's Algorithm to find the Minimum Spanning Tree (MST)
     public static void kruskal(int V, List<Edge> edges) {
        // Sort all edges by weight
        Collections.sort(edges);
        
        // Initialize Disjoint Set (Union-Find)
        DisjointSet ds = new DisjointSet(V);
        
        // To store the resulting MST
        List<Edge> mst = new ArrayList<>();
        int totalWt = 0;
        // Process each edge in sorted order
        for (Edge edge : edges) {
            int srcParent = ds.find(edge.src);
            int destParent = ds.find(edge.dest);
            
            // If including this edge does not form a cycle, include it in the MST
            if (srcParent != destParent) {
                mst.add(edge);
                ds.union(srcParent, destParent);  // Merge the sets
                totalWt += edge.w;
            }
        }
        
        // Print the resulting MST
        System.out.println("Edges in the Minimum Spanning Tree (MST):");
        int totalWeight = 0;
        for (Edge edge : mst) {
            System.out.println(edge.src + " - " + edge.dest + ": " + edge.w);
            totalWeight += edge.w;
        }
        System.out.println("1st method - Total weight of MST: " + totalWeight);
        System.out.println("2nd method - Total weight of MST: " + totalWt);
    }

    public static void main(String[] args) {
        // int v = 7; // Total vertices (0 to 6)
        // @SuppressWarnings("unchecked")
        // ArrayList<Edge>[] graph = new ArrayList[v]; // Array of ArrayList of type Edge

        // createGraph(graph); // Initialize and populate the graph
        // printGraph(graph); // Print the graph structure
        // bfs(graph);
        // dfs(graph, 0, new boolean[graph.length]);
        // System.out.println(hasPath(graph, 0, 9, new boolean[graph.length]));
        // System.out.println(detectCycle(graph));
        // System.out.println(isBipartite(graph));
        // int V = 6; // Number of vertices in the graph
        // ArrayList<Edge>[] graph = new ArrayList[V];
        
        // for (int i = 0; i < V; i++) {
        //     graph[i] = new ArrayList<>();
        // }

        // Example edges of a DAG
        // graph[0].add(new Edge(0, 2, 0));
        // graph[0].add(new Edge(0, 3, 0));
        // graph[1].add(new Edge(1, 3, 0));
        // graph[1].add(new Edge(1, 4, 0));
        // graph[2].add(new Edge(2, 4, 0));
        // graph[3].add(new Edge(3, 5, 0));
        // graph[4].add(new Edge(4, 5, 0));
        // topoSort(graph);

        // bellmanFord(graph, 0, graph.length);

        // prims(graph);

        // int n = 7;
        // int[][] flights = {
        //     {0, 1, 1}, {0, 2, 1}, {1, 3, 1}, {2, 4, 1},
        //     {3, 5, 1}, {4, 5, 1}, {5, 6, 1}
        // };
        // int src = 0, des = 6, k = 2;

        // System.out.println("Cheapest flight cost: " + cheapestFlight(n, flights, src, des, k));

// ------------------------------------------------------------------------------------------------------------------------------
        // DisjointSet ds = new DisjointSet(5);

        // // Perform unions
        // ds.union(0, 1);
        // ds.union(2, 3);
        // ds.union(1, 2); // Connects 0, 1, 2, and 3 into one set

        // // Find roots
        // System.out.println(ds.find(0)); // Expected: 0 (or another consistent root)
        // System.out.println(ds.find(1)); // Expected: Same as find(0)
        // System.out.println(ds.find(2)); // Expected: Same as find(0)
        // System.out.println(ds.find(3)); // Expected: Same as find(0)
        // System.out.println(ds.find(4)); // Expected: 4 (not connected to others)

// --------------------------------------------------------------------------------------------------------------------------------

        // Create a list of edges for the graph
        List<Edge> edges = new ArrayList<>();
        
        // Add edges to the graph (src, dest, weight)
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(0, 3, 5));
        edges.add(new Edge(1, 3, 15));
        edges.add(new Edge(2, 3, 4));
        
        // Number of vertices in the graph
        int V = 4;
        
        // Call Kruskal's Algorithm to find MST
        kruskal(V, edges);

//  -----------------------------------------------------------------------------------------------------------------------------------
    }
}
