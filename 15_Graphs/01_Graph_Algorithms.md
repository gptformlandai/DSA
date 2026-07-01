# Section 15 — Graph Algorithms

---

## 1. What Problem Does This Solve?

Graphs model relationships between entities — social networks, road maps, web pages, dependency chains. Unlike trees, graphs can have cycles, disconnected components, and edges with weights. Graph algorithms solve:

- **Connectivity:** Are two nodes connected?
- **Shortest path:** What is the minimum cost to travel from A to B?
- **Cycle detection:** Does a circular dependency exist?
- **Topological ordering:** What is a valid order to execute tasks with dependencies?
- **Minimum spanning tree:** What is the cheapest way to connect all nodes?

---

## 2. Beginner-Friendly Intuition

**DFS:** Explore as deep as possible before backtracking — like following a maze by always taking the rightmost turn until you hit a wall, then backing up.

**BFS:** Explore all neighbors at the current distance before going further — like ripples expanding outward when you drop a stone in water.

**Topological Sort:** Imagine tasks where some must complete before others. Topological sort gives a valid linear ordering where all prerequisites come first.

---

## 3. Real-World Analogy

**BFS → Google Maps "fastest route":** BFS explores all roads reachable in 1 minute, then 2 minutes, then 3 — guaranteeing the shortest number of hops.

**Topological Sort → Course prerequisites:** You can't take Data Structures before Intro to Programming. Topological sort gives a valid course order.

**Cycle detection → Deadlock in operating systems:** Process A waits for B, B waits for C, C waits for A — a cycle in the dependency graph = deadlock.

---

## 4. Core Concept

### Graph Representations

| Representation | Build Time | Space | Edge Query | Best For |
|----------------|-----------|-------|-----------|---------|
| Adjacency Matrix | O(V²) | O(V²) | O(1) | Dense graphs |
| Adjacency List | O(V+E) | O(V+E) | O(degree) | Sparse graphs (most problems) |
| Edge List | O(E) | O(E) | O(E) | Kruskal's MST |

### Key Graph Types

| Type | Property | Algorithm Implications |
|------|----------|----------------------|
| **Directed** | Edges have direction | DFS/BFS on directed edges only |
| **Undirected** | Edges are bidirectional | Add edge in both directions |
| **Weighted** | Edges have costs | Dijkstra for shortest path |
| **Unweighted** | All edges cost 1 | BFS for shortest path |
| **DAG** | Directed, no cycles | Topological sort |

---

## 5. Pattern Recognition Signals

```
"Connected components" → DFS/BFS/DSU
"Shortest path in unweighted graph" → BFS
"Shortest path in weighted graph" → Dijkstra
"Course schedule" / "dependency order" → Topological Sort
"Detect cycle" → DFS (directed: back edge) / BFS Kahn's
"Bipartite graph" → BFS/DFS 2-coloring
"Number of islands" → DFS/BFS on grid
"Clone graph" → DFS/BFS + HashMap
"All paths from source to target" → DFS backtracking
```

---

## 6. Step-by-Step Algorithm

### DFS Template (Iterative or Recursive)
```
visited = set()
dfs(node):
    if node in visited: return
    visited.add(node)
    process(node)
    for neighbor in adj[node]:
        dfs(neighbor)
```

### Topological Sort — Kahn's Algorithm (BFS)
```
Step 1: Compute in-degree for each node
Step 2: Add all nodes with in-degree 0 to queue
Step 3: While queue not empty:
    node = queue.poll()
    result.add(node)
    For each neighbor of node:
        in-degree[neighbor]--
        If in-degree[neighbor] == 0: queue.add(neighbor)
Step 4: If result.size() < V: CYCLE EXISTS
```

### Dijkstra's Algorithm (Shortest Path, Non-negative Weights)
```
dist[source] = 0; all others = INFINITY
minHeap = {(0, source)}
While heap not empty:
    (d, u) = heap.poll()
    If d > dist[u]: skip (stale entry)
    For each neighbor v with weight w:
        If dist[u] + w < dist[v]:
            dist[v] = dist[u] + w
            heap.offer((dist[v], v))
```

---

## 7. Dry Run with Example

### Example 1: DFS on Undirected Graph

**Graph:** `0-1, 0-2, 1-3, 2-3, 3-4`

```
DFS from 0, visited={}:
  Visit 0 → visited={0}
  Neighbors of 0: [1, 2]
    DFS(1): Visit 1 → visited={0,1}
      Neighbors of 1: [0(skip), 3]
        DFS(3): Visit 3 → visited={0,1,3}
          Neighbors of 3: [1(skip), 2, 4]
            DFS(2): Visit 2 → visited={0,1,3,2}
              Neighbors: [0(skip), 3(skip)] → return
            DFS(4): Visit 4 → visited={0,1,3,2,4}
              No unvisited neighbors → return
    DFS(2): already visited, skip

DFS order: [0, 1, 3, 2, 4]
```

### Example 2: Topological Sort

**Graph (courses):** 0→1, 0→2, 1→3, 2→3

```
in-degree: [0:0, 1:1, 2:1, 3:2]
queue: [0] (in-degree 0)

Process 0: result=[0]
  Neighbors 1,2: in-degree[1]=0 → add to queue; in-degree[2]=0 → add
  queue: [1, 2]

Process 1: result=[0,1]
  Neighbor 3: in-degree[3]=1 → not 0, not added
  queue: [2]

Process 2: result=[0,1,2]
  Neighbor 3: in-degree[3]=0 → add
  queue: [3]

Process 3: result=[0,1,2,3]
  No neighbors. queue empty.

result.size()=4 == V=4 → NO CYCLE. Order: [0,1,2,3] ✓
```

### Example 3: Dijkstra

**Graph:** `0→1 (w=4), 0→2 (w=1), 2→1 (w=2), 1→3 (w=1), 2→3 (w=5)`

```
dist=[0, ∞, ∞, ∞], heap=[(0,0)]

Pop (0,0): process 0
  0→1 (4): dist[1]=4, heap={(4,1)}
  0→2 (1): dist[2]=1, heap={(4,1),(1,2)}

Pop (1,2): process 2
  2→1 (2): dist[0]+1+2=3 < dist[1]=4 → dist[1]=3, heap={(4,1),(3,1),(5,3)}

Pop (3,1): process 1 (d=3 == dist[1]=3, valid)
  1→3 (1): dist[3]=4, heap={(4,1),(5,3),(4,3)}

Pop (4,1): d=4 > dist[1]=3 → SKIP (stale)

Pop (4,3): process 3 (d=4 == dist[3])
  No outgoing edges.

Pop (5,3): d=5 > dist[3]=4 → SKIP

Final: dist=[0, 3, 1, 4] ✓
Shortest: 0→2→1→3 = 1+2+1=4
```

---

## 8. Code Implementation

### DFS — Connected Components

```java
int countComponents(int n, int[][] edges) {
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] e : edges) {
        adj.get(e[0]).add(e[1]);
        adj.get(e[1]).add(e[0]);
    }
    boolean[] visited = new boolean[n];
    int components = 0;
    for (int i = 0; i < n; i++) {
        if (!visited[i]) { dfs(i, adj, visited); components++; }
    }
    return components;
}

void dfs(int node, List<List<Integer>> adj, boolean[] visited) {
    visited[node] = true;
    for (int neighbor : adj.get(node))
        if (!visited[neighbor]) dfs(neighbor, adj, visited);
}
```

### Topological Sort — Kahn's Algorithm

```java
int[] topologicalSort(int n, int[][] prerequisites) {
    List<List<Integer>> adj = new ArrayList<>();
    int[] inDegree = new int[n];
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] pre : prerequisites) {
        adj.get(pre[1]).add(pre[0]);
        inDegree[pre[0]]++;
    }
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) if (inDegree[i] == 0) queue.offer(i);
    int[] order = new int[n];
    int idx = 0;
    while (!queue.isEmpty()) {
        int node = queue.poll();
        order[idx++] = node;
        for (int neighbor : adj.get(node))
            if (--inDegree[neighbor] == 0) queue.offer(neighbor);
    }
    return idx == n ? order : new int[]{}; // empty = cycle detected
}
```

### Detect Cycle in Directed Graph (DFS + rec-stack)

```java
boolean hasCycle(int n, List<List<Integer>> adj) {
    int[] state = new int[n]; // 0=unvisited, 1=in-progress, 2=done
    for (int i = 0; i < n; i++)
        if (state[i] == 0 && dfsCycle(i, adj, state)) return true;
    return false;
}

boolean dfsCycle(int node, List<List<Integer>> adj, int[] state) {
    state[node] = 1; // mark in-progress
    for (int neighbor : adj.get(node)) {
        if (state[neighbor] == 1) return true;  // back edge → cycle
        if (state[neighbor] == 0 && dfsCycle(neighbor, adj, state)) return true;
    }
    state[node] = 2; // fully explored
    return false;
}
```

### Dijkstra's Shortest Path

```java
int[] dijkstra(int n, List<List<int[]>> adj, int src) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    PriorityQueue<int[]> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
    heap.offer(new int[]{0, src});
    while (!heap.isEmpty()) {
        int[] curr = heap.poll();
        int d = curr[0], u = curr[1];
        if (d > dist[u]) continue; // stale entry
        for (int[] edge : adj.get(u)) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                heap.offer(new int[]{dist[v], v});
            }
        }
    }
    return dist;
}
```

### Bipartite Check (2-Coloring)

```java
boolean isBipartite(int[][] graph) {
    int n = graph.length;
    int[] color = new int[n]; // 0=uncolored, 1=red, -1=blue
    for (int i = 0; i < n; i++) {
        if (color[i] != 0) continue;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(i);
        color[i] = 1;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph[node]) {
                if (color[neighbor] == 0) {
                    color[neighbor] = -color[node]; // opposite color
                    queue.offer(neighbor);
                } else if (color[neighbor] == color[node]) {
                    return false; // same color as neighbor → not bipartite
                }
            }
        }
    }
    return true;
}
```

---

## 9. Time Complexity

| Algorithm | Complexity | Notes |
|-----------|-----------|-------|
| DFS / BFS | O(V + E) | V = vertices, E = edges |
| Topological Sort (Kahn's) | O(V + E) | Process each node/edge once |
| Dijkstra (min-heap) | O((V + E) log V) | Each node processed once |
| Bellman-Ford | O(V × E) | Handles negative weights |
| Floyd-Warshall | O(V³) | All-pairs shortest path |
| Grid DFS/BFS | O(m × n) | m rows, n columns |

---

## 10. Space Complexity

| Algorithm | Space |
|-----------|-------|
| Adjacency list | O(V + E) |
| DFS (recursive) | O(V) call stack |
| BFS | O(V) queue + visited |
| Dijkstra | O(V + E) for heap |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Disconnected graph | Start DFS/BFS from every unvisited node |
| Self-loop | Edge (u, u) — can skip or detect as cycle |
| No path between nodes | Dijkstra: dist remains ∞ |
| Negative weight edges | Dijkstra fails → use Bellman-Ford |
| Negative weight cycles | Bellman-Ford detects them |
| Graph with 0 nodes/edges | Return 0 components, empty order |
| Cycle in undirected graph | Don't revisit parent node in DFS |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Not building adjacency list for both directions (undirected graph)
adj.get(u).add(v); // WRONG for undirected — only adds one direction
adj.get(u).add(v);
adj.get(v).add(u); // CORRECT — add both

// MISTAKE 2: DFS on undirected graph visits parent as "cycle"
// Fix: pass parent node to DFS to avoid going back immediately
if (neighbor != parent) dfs(neighbor, node, adj, visited);

// MISTAKE 3: Not checking stale entries in Dijkstra
int[] curr = heap.poll();
// MISSING: if (curr[0] > dist[curr[1]]) continue; // skip stale

// MISTAKE 4: Topological sort not detecting cycles
// If result.size() < n after Kahn's, there's a cycle — return empty/error

// MISTAKE 5: Using DFS for shortest path in unweighted graph
// DFS finds A path, not THE shortest. BFS guarantees shortest in unweighted graphs.
```

---

## 13. Interview-Level Explanation

**Q: "Why can't you use Dijkstra with negative edge weights?"**

> "Dijkstra greedily finalizes the shortest distance when a node is popped from the min-heap — it assumes no future edge can improve an already-finalized distance. With negative weights, a future path through a negative edge could produce a shorter route to an already-finalized node, violating this assumption. Bellman-Ford handles negatives by relaxing all edges V-1 times without the greedy finalization."

**Q: "What is topological sort and when is it impossible?"**

> "Topological sort produces a linear ordering of vertices in a DAG such that every directed edge (u→v) has u appearing before v. It's impossible when the graph has a cycle — because a cycle creates a circular dependency where no vertex can come 'first.' Kahn's algorithm detects this: if the result doesn't include all vertices after the BFS, the missing ones form cycles."

---

## 14. Real-World Use Cases

| Application | Algorithm |
|------------|----------|
| **Google Maps** | Dijkstra / A* for shortest route |
| **npm/Maven dependencies** | Topological sort for install order |
| **Social networks** | BFS for degrees of separation |
| **Web crawlers** | BFS/DFS over hyperlink graph |
| **Deadlock detection** | Cycle detection in resource graph |
| **Flight routing** | Shortest path with weights = distance/cost |
| **Recommendation systems** | Graph traversal for similar items |

---

## 15. Variations of This Pattern

| Variation | Algorithm | Example |
|-----------|----------|---------|
| Shortest path unweighted | BFS | Word Ladder |
| Shortest path weighted | Dijkstra | Network Delay Time |
| All-pairs shortest path | Floyd-Warshall | Find City with Fewest Neighbors |
| Minimum spanning tree | Kruskal / Prim | Minimum Cost to Connect Nodes |
| Topological order | Kahn's / DFS | Course Schedule II |
| Cycle detection | DFS state machine | Course Schedule I |
| Bipartite | BFS 2-coloring | Is Graph Bipartite? |
| Strongly connected | Kosaraju / Tarjan | Critical Connections |
| Multi-source BFS | BFS from all sources | Rotting Oranges, 01-BFS |

---

## 16. Practice Problems

### Easy — Foundation
1. **Find if Path Exists in Graph** (LeetCode #1971)
   - *Task:* Check if path from source to destination exists.
   - *Hint:* BFS or DFS from source; return true if destination visited.

2. **Number of Islands** (LeetCode #200)
   - *Task:* Count connected regions of '1's in a grid.
   - *Hint:* DFS from each unvisited '1', mark entire island as visited.

3. **Flood Fill** (LeetCode #733)
   - *Task:* Fill connected region with new color.
   - *Hint:* DFS/BFS from starting cell to all same-color neighbors.

### Medium — Core Graph Algorithms
1. **Course Schedule** (LeetCode #207)
   - *Task:* Can you finish all courses (detect cycle)?
   - *Hint:* Topological sort — if result size < n, cycle exists.

2. **Course Schedule II** (LeetCode #210)
   - *Task:* Return valid course order.
   - *Hint:* Kahn's algorithm — return order, or [] if cycle.

3. **Network Delay Time** (LeetCode #743)
   - *Task:* Time for signal to reach all nodes.
   - *Hint:* Dijkstra from source. Answer = max of all distances.

4. **Clone Graph** (LeetCode #133)
   - *Task:* Deep copy of a graph.
   - *Hint:* DFS/BFS + HashMap<original, clone>. Visit each node once.

5. **Pacific Atlantic Water Flow** (LeetCode #417)
   - *Task:* Cells where water can flow to both oceans.
   - *Hint:* Reverse BFS from each ocean border; find intersection.

### Hard — Advanced Algorithms
1. **Minimum Cost to Connect All Points** (LeetCode #1584)
   - *Task:* MST on fully connected graph (Manhattan distances).
   - *Hint:* Prim's with a visited set and min-heap.

2. **Critical Connections in a Network** (LeetCode #1192)
   - *Task:* Find all bridges in the graph.
   - *Hint:* Tarjan's bridge-finding algorithm (DFS with low array).

3. **Cheapest Flights Within K Stops** (LeetCode #787)
   - *Task:* Shortest path with at most k intermediate stops.
   - *Hint:* Modified Dijkstra or BFS with state (node, stops_used).

---

## 17. How to Know You Have Mastered Graph Algorithms

You have mastered this topic when you can:
- [ ] Build an adjacency list from an edge list in Java
- [ ] Implement DFS and BFS for graph traversal (not just trees)
- [ ] Implement topological sort using Kahn's algorithm
- [ ] Detect cycles in both directed and undirected graphs
- [ ] Implement Dijkstra with a min-heap (including stale-entry check)
- [ ] Identify when BFS vs DFS vs Dijkstra vs Topological Sort is needed
- [ ] Handle disconnected graphs (outer loop over all nodes)
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. What is the time complexity of DFS on a graph stored as an adjacency list vs adjacency matrix?

2. DFS finds a path in an unweighted graph. Is it the shortest path? Why or why not?

3. Topological sort is only possible on DAGs. If a graph has 5 nodes and you run Kahn's and get only 4 nodes in the result, what does that mean?

4. In Dijkstra, you pop a node from the heap and check `if (d > dist[node]) continue`. When does this happen?

5. Can Dijkstra be used for negative-weight graphs? What algorithm should you use instead?

6. In cycle detection for a directed graph, you use 3 states: unvisited (0), in-progress (1), done (2). Why does "in-progress" indicate a back edge (cycle)?

7. What makes a graph bipartite? Can a graph with an odd cycle be bipartite?

8. Multi-source BFS adds multiple starting nodes at distance 0. How does this differ from running BFS separately from each source and taking the minimum?

> **Answers:**
> 1. Adjacency list: O(V + E). Adjacency matrix: O(V²) — must scan entire row for neighbors.
> 2. No. DFS finds *a* path via the first explored branch. BFS guarantees shortest path (fewest hops) in unweighted graphs.
> 3. The 5th node is part of a cycle — it was never added to the queue because its in-degree never reached 0.
> 4. When a node is added to the heap multiple times with different distances, and a shorter path was already found and processed before this entry was popped. The earlier processing already finalized a better distance.
> 5. No — Dijkstra can give wrong answers with negative edges. Use Bellman-Ford (handles negatives) or Johnson's algorithm.
> 6. If neighbor's state is "in-progress," it's currently on the recursion stack — meaning we have a path from neighbor to current node, plus the current edge back to neighbor = cycle.
> 7. A bipartite graph can be 2-colored with no adjacent nodes sharing the same color. A graph with an odd-length cycle cannot be bipartite — odd cycles require 3 colors (try: A→B→C→A: A=red, B=blue, C must be red but C→A requires C≠red).
> 8. Multi-source BFS correctly computes minimum distance from the nearest source simultaneously. Running separately and taking minimum is equivalent but O(S×(V+E)) instead of O(V+E).

---

**Next →** `../16_DSU/01_DSU.md`

```
    1 ── 2
    |    |
    3 ── 4
```

### Graph Terminology

| Term | Meaning |
|------|---------|
| Vertex (V) | A node in the graph |
| Edge (E) | Connection between two nodes |
| Directed | Edges have direction (A→B ≠ B→A) |
| Undirected | Edges are bidirectional |
| Weighted | Edges have costs/distances |
| Cyclic | Contains at least one cycle |
| DAG | Directed Acyclic Graph |
| Connected | Path exists between all vertices |
| Degree | Number of edges at a vertex |

### Graph Representations

**Adjacency List (most common):**
```java
List<List<Integer>> adj = new ArrayList<>();
for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
// Add edge u-v (undirected)
adj.get(u).add(v);
adj.get(v).add(u);
```

**Adjacency Matrix:**
```java
int[][] matrix = new int[n][n];
matrix[u][v] = 1;  // directed
matrix[v][u] = 1;  // undirected
```

**Edge List:**
```java
int[][] edges = {{0,1}, {0,2}, {1,3}};  // each edge as [u,v]
```

| Representation | Space | Edge lookup | Neighbors |
|---------------|-------|------------|-----------|
| Adjacency List | O(V+E) | O(E/V) | O(degree) |
| Adjacency Matrix | O(V²) | O(1) | O(V) |

---

## Part 2: Graph Traversal

### BFS — Breadth-First Search

Explores level by level. Uses a queue.  
**Best for:** Shortest path in unweighted graphs.

```java
void bfs(List<List<Integer>> adj, int start, int n) {
    boolean[] visited = new boolean[n];
    Queue<Integer> queue = new LinkedList<>();
    visited[start] = true;
    queue.offer(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        System.out.print(node + " ");
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
            }
        }
    }
}
```

### DFS — Depth-First Search

Goes as deep as possible before backtracking.  
**Best for:** Cycle detection, topological sort, connectivity.

```java
void dfs(List<List<Integer>> adj, int node, boolean[] visited) {
    visited[node] = true;
    System.out.print(node + " ");
    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor])
            dfs(adj, neighbor, visited);
    }
}
```

---

## Part 3: Cycle Detection

### Undirected Graph — DFS

```java
boolean hasCycleUndirected(List<List<Integer>> adj, int n) {
    boolean[] visited = new boolean[n];
    for (int i = 0; i < n; i++)
        if (!visited[i] && dfsDetect(adj, i, visited, -1))
            return true;
    return false;
}

boolean dfsDetect(List<List<Integer>> adj, int node, boolean[] visited, int parent) {
    visited[node] = true;
    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            if (dfsDetect(adj, neighbor, visited, node)) return true;
        } else if (neighbor != parent) return true;  // back edge = cycle
    }
    return false;
}
```

### Directed Graph — DFS with Recursion Stack

```java
boolean hasCycleDirected(List<List<Integer>> adj, int n) {
    boolean[] visited = new boolean[n];
    boolean[] inStack = new boolean[n];  // currently in recursion stack
    for (int i = 0; i < n; i++)
        if (!visited[i] && dfsDirected(adj, i, visited, inStack))
            return true;
    return false;
}

boolean dfsDirected(List<List<Integer>> adj, int node, boolean[] visited, boolean[] inStack) {
    visited[node] = true;
    inStack[node] = true;
    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor] && dfsDirected(adj, neighbor, visited, inStack))
            return true;
        else if (inStack[neighbor]) return true;  // back edge in directed graph
    }
    inStack[node] = false;
    return false;
}
```

---

## Part 4: Topological Sort

**Only for DAGs. Order nodes such that u comes before v for every edge u→v.**

### Kahn's Algorithm (BFS-based — detects cycle naturally)

```java
List<Integer> topoSort(int n, List<List<Integer>> adj) {
    int[] indegree = new int[n];
    for (int u = 0; u < n; u++)
        for (int v : adj.get(u)) indegree[v]++;

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) if (indegree[i] == 0) queue.offer(i);

    List<Integer> order = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        order.add(node);
        for (int neighbor : adj.get(node))
            if (--indegree[neighbor] == 0) queue.offer(neighbor);
    }
    return order.size() == n ? order : new ArrayList<>();  // empty = cycle
}
```

**Use case:** Course Schedule — detect if all courses can be completed.

---

## Part 5: Shortest Path

### BFS — Unweighted Graph (O(V+E))

```java
int[] shortestPath(List<List<Integer>> adj, int src, int n) {
    int[] dist = new int[n];
    Arrays.fill(dist, -1);
    dist[src] = 0;
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(src);
    while (!queue.isEmpty()) {
        int node = queue.poll();
        for (int neighbor : adj.get(node)) {
            if (dist[neighbor] == -1) {
                dist[neighbor] = dist[node] + 1;
                queue.offer(neighbor);
            }
        }
    }
    return dist;
}
```

### Dijkstra's Algorithm (Weighted, non-negative — O((V+E) log V))

```java
int[] dijkstra(List<int[]>[] adj, int src, int n) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]); // {dist, node}
    pq.offer(new int[]{0, src});

    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int d = curr[0], u = curr[1];
        if (d > dist[u]) continue;  // stale entry
        for (int[] edge : adj[u]) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                pq.offer(new int[]{dist[v], v});
            }
        }
    }
    return dist;
}
```

**Dry Run:** 
```
Graph: 0-1(4), 0-2(1), 2-1(2), 1-3(1)
src=0: dist=[0,∞,∞,∞]
Process 0: update 1→4, 2→1; pq={[1,2],[4,1]}
Process 2(d=1): update 1→3; pq={[3,1],[4,1]}
Process 1(d=3): update 3→4; pq={[4,1],[4,3]}
Process 1(d=4): stale, skip
Process 3(d=4): done
dist=[0,3,1,4]
```

### Bellman-Ford (Handles negative weights — O(V×E))

```java
int[] bellmanFord(int n, int[][] edges, int src) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;

    for (int i = 0; i < n - 1; i++) {  // n-1 relaxations
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], w = edge[2];
            if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v])
                dist[v] = dist[u] + w;
        }
    }
    // Check negative cycles
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v])
            return null;  // negative cycle exists
    }
    return dist;
}
```

### Floyd-Warshall (All-pairs shortest path — O(V³))

```java
void floydWarshall(int[][] dist, int n) {
    for (int k = 0; k < n; k++)
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE)
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
}
```

---

## Part 6: Minimum Spanning Tree (MST)

MST connects all vertices with minimum total edge weight (no cycles).

### Kruskal's Algorithm (Sort edges + DSU)

```java
int kruskal(int n, int[][] edges) {
    Arrays.sort(edges, (a,b) -> a[2] - b[2]);  // sort by weight
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;

    int mstWeight = 0;
    for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (find(parent, u) != find(parent, v)) {  // different components
            union(parent, u, v);
            mstWeight += w;
        }
    }
    return mstWeight;
}
```

### Prim's Algorithm (Greedy + Min-Heap)

```java
int prims(List<int[]>[] adj, int n) {
    boolean[] inMST = new boolean[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0]-b[0]); // {weight, node}
    pq.offer(new int[]{0, 0});
    int mstWeight = 0;

    while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int w = curr[0], u = curr[1];
        if (inMST[u]) continue;
        inMST[u] = true;
        mstWeight += w;
        for (int[] edge : adj[u]) {
            if (!inMST[edge[0]]) pq.offer(new int[]{edge[1], edge[0]});
        }
    }
    return mstWeight;
}
```

---

## Part 7: Important Graph Patterns

### Bipartite Check (2-coloring / BFS)

```java
boolean isBipartite(int[][] graph) {
    int n = graph.length;
    int[] color = new int[n];  // 0: uncolored, 1: red, -1: blue
    for (int start = 0; start < n; start++) {
        if (color[start] != 0) continue;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start); color[start] = 1;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : graph[node]) {
                if (color[neighbor] == 0) {
                    color[neighbor] = -color[node];
                    queue.offer(neighbor);
                } else if (color[neighbor] == color[node]) return false;
            }
        }
    }
    return true;
}
```

### Number of Islands (DFS/BFS flood fill)

```java
int numIslands(char[][] grid) {
    int count = 0;
    for (int r = 0; r < grid.length; r++)
        for (int c = 0; c < grid[0].length; c++)
            if (grid[r][c] == '1') { dfs(grid, r, c); count++; }
    return count;
}

void dfs(char[][] grid, int r, int c) {
    if (r<0||r>=grid.length||c<0||c>=grid[0].length||grid[r][c]!='1') return;
    grid[r][c] = '0';  // mark visited
    dfs(grid, r+1, c); dfs(grid, r-1, c);
    dfs(grid, r, c+1); dfs(grid, r, c-1);
}
```

---

## Part 8: Real-World Use Cases

| Algorithm | Real-World Use |
|-----------|---------------|
| BFS | Social network friend distance, web crawling levels |
| DFS | Maze solving, dependency analysis, network exploration |
| Dijkstra | Google Maps routing, network packet routing |
| Bellman-Ford | Currency arbitrage detection, routing protocols |
| Floyd-Warshall | Road network analysis (all-pairs) |
| Kruskal/Prim | Network design, electrical grid, pipeline routes |
| Topological Sort | Build systems (make), course scheduling, import ordering |
| Bipartite | Job assignment, matching problems, scheduling conflicts |

---

## Practice Problems

**Easy:**
1. Number of Islands.
2. Flood Fill.
3. Clone Graph.

**Medium:**
1. Course Schedule I & II.
2. Pacific Atlantic Water Flow.
3. Number of Connected Components.
4. Graph Valid Tree.
5. Network Delay Time (Dijkstra).

**Hard:**
1. Word Ladder.
2. Alien Dictionary (topological sort).
3. Cheapest Flights Within K Stops.

---

**Next →** `../16_DSU/01_DSU.md`
