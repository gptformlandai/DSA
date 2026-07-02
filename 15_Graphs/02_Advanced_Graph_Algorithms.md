# Section 15b — Advanced Graph Algorithms

> Companion to `01_Graph_Algorithms.md`. This file closes the PRO-level gaps: shortest paths with negative weights, all-pairs shortest path, 0-1 BFS, A*, both MST algorithms, strongly connected components, and bridges/articulation points.

---

## 1. What Problem Does This Solve?

`01_Graph_Algorithms.md` covered BFS, DFS, Dijkstra, Kahn's topological sort, and cycle detection. But real interviews and systems push further:

- **Negative edge weights** → Dijkstra breaks. Need **Bellman-Ford**.
- **Shortest path between every pair** → Need **Floyd-Warshall**.
- **Weights are only 0 or 1** → **0-1 BFS** beats Dijkstra.
- **Goal-directed search with a heuristic** → **A***.
- **Cheapest way to connect all nodes** → **MST** (Kruskal + Prim).
- **Which nodes/edges are critical to connectivity?** → **Bridges & articulation points**.
- **Which groups of nodes can all reach each other?** → **Strongly Connected Components**.

---

## 2. Algorithm Selection Cheat Sheet

| Need | Algorithm | Time | Handles negatives? |
|------|-----------|------|--------------------|
| Shortest path, non-negative weights | Dijkstra (heap) | O((V+E) log V) | No |
| Shortest path, negative weights allowed | Bellman-Ford | O(V·E) | Yes (+ detects neg cycle) |
| All-pairs shortest path | Floyd-Warshall | O(V³) | Yes (+ detects neg cycle) |
| All-pairs, sparse graph, negatives | Johnson's | O(V·E + V² log V) | Yes |
| Weights ∈ {0, 1} | 0-1 BFS (deque) | O(V+E) | No |
| Shortest path with good heuristic | A* | O(E) best case | No |
| Minimum spanning tree, sparse | Kruskal (DSU) | O(E log E) | n/a |
| Minimum spanning tree, dense | Prim (heap) | O((V+E) log V) | n/a |
| Strongly connected components | Tarjan / Kosaraju | O(V+E) | n/a |
| Bridges / articulation points | Tarjan (low-link) | O(V+E) | n/a |

---

## 3. Bellman-Ford — Shortest Path with Negative Edges

### Intuition
Dijkstra greedily finalizes the closest node, assuming you can never later find a cheaper path — negative edges violate that. Bellman-Ford instead **relaxes every edge V-1 times**. After `i` rounds, every shortest path using at most `i` edges is correct. Since a simple shortest path has at most `V-1` edges, `V-1` rounds suffice. A `V`-th round that still improves something proves a **negative cycle**.

### Code
```java
// Returns null if a negative cycle is reachable from src; else shortest dist[].
public long[] bellmanFord(int n, int[][] edges, int src) {
    long INF = Long.MAX_VALUE / 4;
    long[] dist = new long[n];
    Arrays.fill(dist, INF);
    dist[src] = 0;

    // Relax all edges V-1 times.
    for (int i = 1; i < n; i++) {
        boolean changed = false;
        for (int[] e : edges) {              // e = {u, v, w}
            if (dist[e[0]] == INF) continue;
            if (dist[e[0]] + e[2] < dist[e[1]]) {
                dist[e[1]] = dist[e[0]] + e[2];
                changed = true;
            }
        }
        if (!changed) break;                 // early exit: converged
    }

    // V-th relaxation: any improvement => negative cycle.
    for (int[] e : edges) {
        if (dist[e[0]] != INF && dist[e[0]] + e[2] < dist[e[1]]) {
            return null;                     // negative cycle detected
        }
    }
    return dist;
}
```

### Key numbers & pitfalls
- Time **O(V·E)**, space O(V).
- Use a wide sentinel (`Long.MAX_VALUE / 4`) so `INF + w` never overflows.
- To find **which** nodes are affected by a negative cycle, run BFS/DFS from all nodes updated in round V.
- **SPFA** (queue-based Bellman-Ford) is faster on average but O(V·E) worst case — avoid claiming it's always fast in interviews.

**Canonical problem:** LeetCode 787 *Cheapest Flights Within K Stops* — Bellman-Ford limited to `K+1` rounds, relaxing from a **snapshot** of `dist` each round (clone the array) so one round can't use two new edges at once.

```java
public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
    long INF = Long.MAX_VALUE / 4;
    long[] dist = new long[n];
    Arrays.fill(dist, INF);
    dist[src] = 0;
    for (int i = 0; i <= K; i++) {
        long[] next = dist.clone();          // snapshot: cap edges per round
        for (int[] f : flights) {
            if (dist[f[0]] == INF) continue;
            next[f[1]] = Math.min(next[f[1]], dist[f[0]] + f[2]);
        }
        dist = next;
    }
    return dist[dst] == INF ? -1 : (int) dist[dst];
}
```

---

## 4. Floyd-Warshall — All-Pairs Shortest Path

### Intuition
Dynamic programming over "which intermediate vertices are allowed." `dist[i][j]` using only vertices `{0..k}` as stops = better of (not using `k`) vs (going `i → k → j`). Relax `k` from 0 to V-1.

### Code
```java
public void floydWarshall(long[][] dist) {   // dist[i][j] preloaded; INF for no edge, 0 on diagonal
    int n = dist.length;
    for (int k = 0; k < n; k++)
        for (int i = 0; i < n; i++) {
            if (dist[i][k] == INF) continue;   // prune
            for (int j = 0; j < n; j++)
                if (dist[i][k] + dist[k][j] < dist[i][j])
                    dist[i][j] = dist[i][k] + dist[k][j];
        }
    // Negative cycle iff any dist[i][i] < 0.
}
```

### When to use
- Small dense graphs (V ≤ ~400–500). O(V³) time, O(V²) space.
- Transitive closure (reachability): replace `min/plus` with `OR/AND`.
- **Canonical problems:** LeetCode 1334 *Find the City With the Smallest Number of Neighbors*, 399 *Evaluate Division* (multiply along paths).

> **Loop order matters:** `k` MUST be the outermost loop. Swapping it produces wrong answers.

---

## 5. 0-1 BFS — When Weights Are 0 or 1

### Intuition
Dijkstra's heap is overkill when weights are only 0 or 1. Use a **double-ended queue**: a 0-weight edge keeps you in the same "layer" → push to **front**; a 1-weight edge moves you to the next layer → push to **back**. The deque stays sorted by distance automatically, giving O(V+E).

### Code
```java
public int zeroOneBFS(List<int[]>[] adj, int src, int n) {  // edge = {to, weight in {0,1}}
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    Deque<Integer> dq = new ArrayDeque<>();
    dq.offerFirst(src);
    while (!dq.isEmpty()) {
        int u = dq.pollFirst();
        for (int[] e : adj[u]) {
            int v = e[0], w = e[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                if (w == 0) dq.offerFirst(v);
                else        dq.offerLast(v);
            }
        }
    }
    return dist[n - 1];
}
```

**Canonical problem:** LeetCode 1368 *Minimum Cost to Make at Least One Valid Path in a Grid* — moving in the arrow's direction costs 0, any other direction costs 1.

---

## 6. A* Search — Heuristic-Guided Shortest Path

### Intuition
Dijkstra expands nodes in order of `g(n)` = cost-so-far, exploring blindly in all directions. A* adds a **heuristic** `h(n)` estimating remaining cost to the goal and expands by `f(n) = g(n) + h(n)`, steering the search toward the target. If `h` never overestimates (**admissible**) and is **consistent**, A* returns the optimal path while touching far fewer nodes.

### Code
```java
public int aStar(int start, int goal, List<int[]>[] adj, int[] h) { // h[node] = heuristic to goal
    int n = adj.length;
    int[] g = new int[n];
    Arrays.fill(g, Integer.MAX_VALUE);
    g[start] = 0;
    // PQ ordered by f = g + h.
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // {node, f}
    pq.offer(new int[]{start, h[start]});
    while (!pq.isEmpty()) {
        int[] top = pq.poll();
        int u = top[0];
        if (u == goal) return g[u];
        if (top[1] - h[u] > g[u]) continue;          // stale entry
        for (int[] e : adj[u]) {
            int v = e[0], w = e[1];
            if (g[u] + w < g[v]) {
                g[v] = g[u] + w;
                pq.offer(new int[]{v, g[v] + h[v]});
            }
        }
    }
    return -1;
}
```

- `h ≡ 0` → A* degenerates to Dijkstra.
- Common admissible heuristics: **Manhattan distance** (4-directional grid), **Euclidean** (any-angle), **Chebyshev** (8-directional).
- Used in game pathfinding, GPS routing, puzzle solvers (8-puzzle, sliding puzzles).

---

## 7. Minimum Spanning Tree — Kruskal & Prim

An MST connects all V nodes with V-1 edges at minimum total weight. Two greedy algorithms, both provably correct via the **cut property** (the lightest edge crossing any cut is in some MST) and **cycle property** (the heaviest edge in any cycle is in no MST).

### Kruskal (edge-centric, uses DSU) — best for sparse graphs
```java
public int kruskal(int n, int[][] edges) {  // edges = {u, v, w}
    Arrays.sort(edges, (a, b) -> a[2] - b[2]);
    DSU dsu = new DSU(n);
    int total = 0, used = 0;
    for (int[] e : edges) {
        if (dsu.union(e[0], e[1])) {         // union returns false if already connected (cycle)
            total += e[2];
            if (++used == n - 1) break;      // MST complete
        }
    }
    return used == n - 1 ? total : -1;       // -1 => graph disconnected
}
```

### Prim (vertex-centric, uses heap) — best for dense graphs
```java
public int prim(List<int[]>[] adj, int n) {  // edge = {to, weight}
    boolean[] inMST = new boolean[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // {node, keyWeight}
    pq.offer(new int[]{0, 0});
    int total = 0, count = 0;
    while (!pq.isEmpty() && count < n) {
        int[] top = pq.poll();
        int u = top[0];
        if (inMST[u]) continue;              // lazy deletion
        inMST[u] = true;
        total += top[1];
        count++;
        for (int[] e : adj[u])
            if (!inMST[e[0]]) pq.offer(new int[]{e[0], e[1]});
    }
    return count == n ? total : -1;
}
```

| | Kruskal | Prim |
|---|---------|------|
| Data structure | DSU + sorted edges | Heap + visited |
| Best for | Sparse (E ≈ V) | Dense (E ≈ V²) |
| Grows | Forest merged by cheapest edges | One tree outward from a seed |

**Canonical problems:** LeetCode 1584 *Min Cost to Connect All Points*, 1135 *Connecting Cities With Minimum Cost*.

---

## 8. Strongly Connected Components (SCC)

A **strongly connected component** in a directed graph is a maximal set of nodes where every node can reach every other. Condensing each SCC into a single super-node yields a DAG — the basis for 2-SAT, dependency analysis, and deadlock detection.

### Kosaraju (two passes, easy to remember)
```java
// 1) DFS on G, push nodes onto a stack in finish-time order.
// 2) Transpose all edges. 3) DFS on G^T in reverse finish order; each tree = one SCC.
public List<List<Integer>> kosaraju(int n, List<Integer>[] g) {
    List<Integer>[] gr = new List[n];                    // reversed graph
    for (int i = 0; i < n; i++) gr[i] = new ArrayList<>();
    for (int u = 0; u < n; u++)
        for (int v : g[u]) gr[v].add(u);

    boolean[] seen = new boolean[n];
    Deque<Integer> order = new ArrayDeque<>();
    for (int i = 0; i < n; i++)
        if (!seen[i]) fillOrder(i, g, seen, order);      // pass 1

    Arrays.fill(seen, false);
    List<List<Integer>> sccs = new ArrayList<>();
    while (!order.isEmpty()) {
        int u = order.pop();
        if (!seen[u]) {
            List<Integer> comp = new ArrayList<>();
            collect(u, gr, seen, comp);                  // pass 2
            sccs.add(comp);
        }
    }
    return sccs;
}
private void fillOrder(int u, List<Integer>[] g, boolean[] seen, Deque<Integer> order) {
    seen[u] = true;
    for (int v : g[u]) if (!seen[v]) fillOrder(v, g, seen, order);
    order.push(u);                                       // push on finish
}
private void collect(int u, List<Integer>[] gr, boolean[] seen, List<Integer> comp) {
    seen[u] = true;
    comp.add(u);
    for (int v : gr[u]) if (!seen[v]) collect(v, gr, seen, comp);
}
```

### Tarjan (single pass, low-link) — one DFS, no transpose
```java
class TarjanSCC {
    int idx = 0, n;
    int[] disc, low;
    boolean[] onStack;
    Deque<Integer> stack = new ArrayDeque<>();
    List<Integer>[] g;
    List<List<Integer>> sccs = new ArrayList<>();

    TarjanSCC(int n, List<Integer>[] g) {
        this.n = n; this.g = g;
        disc = new int[n]; low = new int[n]; onStack = new boolean[n];
        Arrays.fill(disc, -1);
    }
    void run() { for (int i = 0; i < n; i++) if (disc[i] == -1) dfs(i); }

    void dfs(int u) {
        disc[u] = low[u] = idx++;
        stack.push(u); onStack[u] = true;
        for (int v : g[u]) {
            if (disc[v] == -1) { dfs(v); low[u] = Math.min(low[u], low[v]); }
            else if (onStack[v]) low[u] = Math.min(low[u], disc[v]);
        }
        if (low[u] == disc[u]) {                 // u is an SCC root
            List<Integer> comp = new ArrayList<>();
            int w;
            do { w = stack.pop(); onStack[w] = false; comp.add(w); } while (w != u);
            sccs.add(comp);
        }
    }
}
```

- Both are **O(V+E)**. Tarjan is one pass (preferred); Kosaraju is more intuitive to explain.
- **Applications:** 2-SAT, finding cycles in build systems, collapsing mutually-recursive modules.

---

## 9. Bridges & Articulation Points (Tarjan low-link)

In an **undirected** graph:
- A **bridge** is an edge whose removal increases the number of components.
- An **articulation point** (cut vertex) is a node whose removal does the same.

Both use `disc[u]` (discovery time) and `low[u]` (earliest reachable ancestor via the DFS subtree). Edge `(u, v)` is a bridge iff `low[v] > disc[u]` — the subtree at `v` has no back-edge escaping above `u`.

```java
class BridgeFinder {
    int timer = 0;
    int[] disc, low;
    List<Integer>[] g;
    List<int[]> bridges = new ArrayList<>();
    Set<Integer> articulation = new HashSet<>();

    void dfs(int u, int parent) {
        disc[u] = low[u] = timer++;
        int children = 0;
        for (int v : g[u]) {
            if (v == parent) continue;
            if (disc[v] == -1) {
                children++;
                dfs(v, u);
                low[u] = Math.min(low[u], low[v]);
                if (low[v] > disc[u]) bridges.add(new int[]{u, v});      // bridge
                if (parent != -1 && low[v] >= disc[u]) articulation.add(u); // cut vertex
            } else {
                low[u] = Math.min(low[u], disc[v]);   // back edge
            }
        }
        if (parent == -1 && children > 1) articulation.add(u);           // root special case
    }
}
```

- **Bridge condition:** `low[v] > disc[u]` (strict).
- **Articulation condition:** non-root with `low[v] >= disc[u]`, or root with ≥2 DFS children.
- **Canonical problem:** LeetCode 1192 *Critical Connections in a Network* (find all bridges).
- **Applications:** network reliability, identifying single points of failure.

---

## 10. Topological Sort — DFS Variant (complement to Kahn's)

`01_Graph_Algorithms.md` covered Kahn's (BFS, indegree). The DFS variant pushes each node onto a stack **after** all its descendants finish; the reversed finish order is a valid topological order. It naturally reports cycles via the gray/black coloring.

```java
public int[] topoDFS(int n, List<Integer>[] adj) {
    int[] state = new int[n];                // 0=white, 1=gray(in stack), 2=black(done)
    Deque<Integer> order = new ArrayDeque<>();
    boolean[] hasCycle = {false};
    for (int i = 0; i < n; i++)
        if (state[i] == 0) dfs(i, adj, state, order, hasCycle);
    if (hasCycle[0]) return new int[0];      // cycle => no ordering
    int[] res = new int[n];
    for (int i = 0; i < n; i++) res[i] = order.pop();
    return res;
}
private void dfs(int u, List<Integer>[] adj, int[] state, Deque<Integer> order, boolean[] cyc) {
    state[u] = 1;
    for (int v : adj[u]) {
        if (state[v] == 1) { cyc[0] = true; return; }   // back edge => cycle
        if (state[v] == 0) dfs(v, adj, state, order, cyc);
    }
    state[u] = 2;
    order.push(u);                           // finished => push
}
```

| | Kahn's (BFS) | DFS |
|---|--------------|-----|
| Cycle detection | processed count < n | gray node revisited |
| Lexicographically smallest order | use a min-heap for zero-indegree | harder |
| Intuition | peel off no-dependency nodes | reverse finish times |

---

## 11. Failure Modes & Interview Traps

| Trap | Fix |
|------|-----|
| Using Dijkstra with negative edges | Switch to Bellman-Ford; Dijkstra's "finalized" assumption fails. |
| Floyd-Warshall with `k` not outermost | Wrong answers; `k` must be the outer loop. |
| Integer overflow in path sums | Use `long` and a sentinel like `Long.MAX_VALUE/4`. |
| 0-1 BFS pushing to wrong end | Weight 0 → front, weight 1 → back. |
| Prim/Dijkstra without stale-entry skip | Correct but slower; always `if (inMST[u]) continue;`. |
| Bridge vs articulation condition mix-up | Bridge is strict `>`; articulation is `>=`. |
| Forgetting the root special case for articulation points | Root is a cut vertex only if it has ≥2 DFS children. |
| MST on a disconnected graph | Detect: fewer than V-1 edges used → return -1. |

---

## 12. 60-Second Explanation Template

> "The weights are [non-negative / can be negative / just 0-1], and I need [single-source / all-pairs / MST / connectivity structure]. So I'll use [Dijkstra / Bellman-Ford / Floyd-Warshall / 0-1 BFS / Kruskal / Prim / Tarjan]. The core invariant is [finalized-min / relax V-1 times / DP over intermediates / low-link]. Complexity is [state it]. I'll guard against [negatives / overflow / disconnected components]."

---

## Practice Problems

**Medium:**
1. Network Delay Time (Dijkstra / Bellman-Ford).
2. Cheapest Flights Within K Stops (bounded Bellman-Ford).
3. Min Cost to Connect All Points (MST).
4. Find the City With the Smallest Number of Neighbors (Floyd-Warshall).
5. Minimum Cost to Make at Least One Valid Path in a Grid (0-1 BFS).

**Hard:**
1. Critical Connections in a Network (bridges).
2. Evaluate Division (Floyd-Warshall / DFS with ratios).
3. Number of Ways to Arrive at Destination (Dijkstra + counting).
4. Reconstruct Itinerary (Hierholzer's Eulerian path).
5. Strongly connected components / 2-SAT style problems.

---

**Next →** `../16_DSU/01_DSU.md`
