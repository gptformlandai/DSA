# Section 16 — Disjoint Set Union (DSU / Union-Find)

---

## 1. What Problem Does This Solve?

DSU answers two queries efficiently on a dynamic set of elements that are grouped into disjoint (non-overlapping) sets:
1. **Find(x):** Which group does element x belong to?
2. **Union(x, y):** Merge the groups containing x and y.

Problems solved:
- Are two nodes in the same connected component?
- Detect cycles in undirected graphs (adding an edge within the same component creates a cycle)
- Kruskal's Minimum Spanning Tree
- Number of provinces / friend circles
- Redundant connection

---

## 2. Beginner-Friendly Intuition

Imagine students at a school forming friend groups. Each group has a "representative" (like a group leader). To check if two students are in the same group, you follow each person's pointer to their group leader. If they reach the same leader, they're in the same group. To merge two groups, you just make one leader point to the other.

The optimization: instead of following a long chain of pointers, you directly connect everyone to the root (path compression). This makes future queries instant.

---

## 3. Real-World Analogy

**Bank account mergers:** Each person has a bank account. When two people merge finances, one account points to the other. "Are these people's finances connected?" = follow pointers to find the root account. Merging = link one root to another.

**Internet connectivity:** Each city has a server. When two cities connect, their networks merge. "Is city A reachable from city B?" = DSU find query.

---

## 4. Core Concept

### Two Optimizations

| Optimization | Description | Effect on Time Complexity |
|-------------|-------------|--------------------------|
| **Union by Rank** | Always attach smaller tree under larger tree | Prevents O(n) chains |
| **Path Compression** | Make every node point directly to root during Find | Flattens tree for future queries |

With both optimizations, **each operation is effectively O(α(n))** — the inverse Ackermann function, which is ≤ 4 for any practical n. Essentially O(1).

### Without Optimizations: O(n) per operation (linked list degeneration)
### With Both Optimizations: O(α(n)) ≈ O(1) amortized

---

## 5. Pattern Recognition Signals

Use DSU when:
```
"Are these two nodes connected?"
"Number of connected components"
"Detect cycle in undirected graph"
"Minimum spanning tree (Kruskal)"
"Merge groups / union sets"
"Friend circles / provinces"
"Redundant connection"
"Accounts merge" (grouping by shared property)
"Earliest time all nodes connected"
```

---

## 6. Step-by-Step Algorithm

### DSU Initialization
```
parent[i] = i  (each element is its own parent initially)
rank[i] = 0    (all trees start with height 0)
```

### Find with Path Compression
```
find(x):
    if parent[x] != x:
        parent[x] = find(parent[x])  ← path compression: make x point directly to root
    return parent[x]
```

### Union by Rank
```
union(x, y):
    rootX = find(x)
    rootY = find(y)
    if rootX == rootY: return false  ← already same component (cycle if adding edge)
    if rank[rootX] < rank[rootY]:
        parent[rootX] = rootY
    elif rank[rootX] > rank[rootY]:
        parent[rootY] = rootX
    else:
        parent[rootY] = rootX
        rank[rootX]++
    return true
```

---

## 7. Dry Run with Example

### Building DSU for edges: (0,1), (1,2), (3,4), (2,3)

**Initial state:**
```
parent = [0, 1, 2, 3, 4]  (each is its own root)
rank   = [0, 0, 0, 0, 0]
```

**Union(0, 1):**
```
find(0)=0, find(1)=1. rank equal → parent[1]=0, rank[0]=1
parent = [0, 0, 2, 3, 4]
rank   = [1, 0, 0, 0, 0]
```

**Union(1, 2):**
```
find(1): parent[1]=0, return 0
find(2): parent[2]=2, return 2
rank[0]=1 > rank[2]=0 → parent[2]=0
parent = [0, 0, 0, 3, 4]
```

**Union(3, 4):**
```
find(3)=3, find(4)=4. rank equal → parent[4]=3, rank[3]=1
parent = [0, 0, 0, 3, 3]
rank   = [1, 0, 0, 1, 0]
```

**Union(2, 3):**
```
find(2): parent[2]=0, return 0
find(3): parent[3]=3, return 3
rank[0]=1, rank[3]=1. Equal → parent[3]=0, rank[0]=2
parent = [0, 0, 0, 0, 3]
rank   = [2, 0, 0, 1, 0]

After path compression, if we call find(4):
  find(4): parent[4]=3, parent[3]=0 → parent[4]=0, return 0
  parent = [0, 0, 0, 0, 0]  ← all point to root 0
```

**Final:** All 5 nodes in one component with root 0.

---

## 8. Code Implementation

### Full DSU Class (Rank + Path Compression)

```java
class DSU {
    private int[] parent, rank;
    private int components;

    DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        components = n;
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // path compression
        return parent[x];
    }

    boolean union(int x, int y) {
        int rx = find(x), ry = find(y);
        if (rx == ry) return false; // already connected
        if (rank[rx] < rank[ry]) parent[rx] = ry;
        else if (rank[rx] > rank[ry]) parent[ry] = rx;
        else { parent[ry] = rx; rank[rx]++; }
        components--;
        return true;
    }

    boolean connected(int x, int y) { return find(x) == find(y); }
    int getComponents() { return components; }
}
```

### Redundant Connection (Detect Cycle)

```java
int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    DSU dsu = new DSU(n + 1); // 1-indexed
    for (int[] edge : edges) {
        if (!dsu.union(edge[0], edge[1]))
            return edge; // this edge creates a cycle → it's redundant
    }
    return new int[]{};
}
```

### Number of Provinces (Connected Components)

```java
int findCircleNum(int[][] isConnected) {
    int n = isConnected.length;
    DSU dsu = new DSU(n);
    for (int i = 0; i < n; i++)
        for (int j = i + 1; j < n; j++)
            if (isConnected[i][j] == 1) dsu.union(i, j);
    return dsu.getComponents();
}
```

### Kruskal's Minimum Spanning Tree

```java
int kruskalMST(int n, int[][] edges) {
    // edges[i] = [weight, u, v]
    Arrays.sort(edges, Comparator.comparingInt(e -> e[0]));
    DSU dsu = new DSU(n);
    int totalCost = 0, edgesUsed = 0;
    for (int[] edge : edges) {
        if (dsu.union(edge[1], edge[2])) {
            totalCost += edge[0];
            edgesUsed++;
            if (edgesUsed == n - 1) break; // MST complete
        }
    }
    return edgesUsed == n - 1 ? totalCost : -1; // -1 if graph disconnected
}
```

### Accounts Merge (Grouping by Shared Email)

```java
List<List<String>> accountsMerge(List<List<String>> accounts) {
    // Map each email to an integer id
    Map<String, Integer> emailId = new HashMap<>();
    Map<String, String> emailName = new HashMap<>();
    int id = 0;
    for (List<String> account : accounts) {
        String name = account.get(0);
        for (int i = 1; i < account.size(); i++) {
            emailId.putIfAbsent(account.get(i), id++);
            emailName.put(account.get(i), name);
        }
    }
    DSU dsu = new DSU(id);
    for (List<String> account : accounts) {
        int rootId = emailId.get(account.get(1));
        for (int i = 2; i < account.size(); i++)
            dsu.union(rootId, emailId.get(account.get(i)));
    }
    // Group emails by component root
    Map<Integer, List<String>> groups = new HashMap<>();
    for (String email : emailId.keySet()) {
        int root = dsu.find(emailId.get(email));
        groups.computeIfAbsent(root, k -> new ArrayList<>()).add(email);
    }
    List<List<String>> result = new ArrayList<>();
    for (List<String> emails : groups.values()) {
        Collections.sort(emails);
        emails.add(0, emailName.get(emails.get(0)));
        result.add(emails);
    }
    return result;
}
```

---

## 9. Time Complexity

| Operation | Naive DSU | With Both Optimizations |
|-----------|----------|------------------------|
| find(x) | O(n) worst | O(α(n)) ≈ O(1) |
| union(x, y) | O(n) worst | O(α(n)) ≈ O(1) |
| n operations | O(n²) | O(n × α(n)) ≈ O(n) |

**α(n)** = inverse Ackermann function. For n ≤ 10^600, α(n) ≤ 4. Practically constant.

---

## 10. Space Complexity

**O(n)** — two arrays: `parent[n]` and `rank[n]`.

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Nodes already in same component | `union()` returns false — useful for cycle detection |
| Single node | parent[0]=0, find(0)=0 — works |
| Self-loop edge (u, u) | find(u)==find(u) → returns false (cycle) |
| Disconnected graph | `components` stays > 1 after all unions |
| 1-indexed nodes | Initialize DSU with n+1, use indices 1..n |
| Finding before union | find() is safe on initialization (each is own parent) |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Forgetting path compression — makes DSU O(n) per operation
int find(int x) {
    if (parent[x] != x) return find(parent[x]); // WRONG: no assignment = no compression
    return parent[x];
}
// CORRECT:
if (parent[x] != x) parent[x] = find(parent[x]); // path compression: assign!

// MISTAKE 2: Checking if edge creates cycle before union
if (find(u) == find(v)) return; // only checks
dsu.union(u, v);
// This is CORRECT for cycle detection — but note: union already checks this internally

// MISTAKE 3: Forgetting to handle 1-indexed nodes
DSU dsu = new DSU(n);    // WRONG for 1-indexed (node n is out of bounds)
DSU dsu = new DSU(n+1);  // CORRECT for 1-indexed

// MISTAKE 4: Not sorting edges by weight in Kruskal's
// Without sorting, you don't pick minimum-weight edges first
// Always Arrays.sort(edges, Comparator.comparingInt(e -> e[0])); before Kruskal's

// MISTAKE 5: Not decrementing component count in union()
// If you track components, decrement when two different components merge
if (rx != ry) { ...; components--; }
```

---

## 13. Interview-Level Explanation

**Q: "What is path compression and why does it help?"**

> "When we call find(x), we traverse up the parent chain to find the root. Path compression makes every node on this path directly point to the root. So the next time we call find on any of those nodes, it's O(1). This doesn't change the logical structure (same roots, same components), but flattens the tree physically, making future queries faster."

**Q: "When would you use DSU instead of BFS/DFS for connected components?"**

> "DSU is better when you're processing edges one by one and need to answer connectivity queries dynamically (like after each new edge). BFS/DFS is better when you have the full graph upfront and want to traverse it. DSU also shines for Kruskal's MST because you can sort edges and greedily add the minimum-weight edge that doesn't create a cycle."

---

## 14. Real-World Use Cases

| Application | DSU Usage |
|------------|----------|
| **Network connectivity** | Determine if two computers are in same subnet |
| **Social networks** | Find all connected friend groups |
| **Image processing** | Connected component labeling |
| **Kruskal's MST** | Building cheapest spanning network |
| **Percolation** | Physics simulation of connected pores |
| **Git merge** | Detecting if branches share common history |
| **Game grids** | Check if two regions are connected |

---

## 15. Variations of This Pattern

| Variation | Key Change | Example |
|-----------|-----------|---------|
| Basic DSU | No optimizations | Basic connectivity |
| DSU + Path Compression | Fast finds | Redundant Connection |
| DSU + Union by Rank | Balanced trees | All standard problems |
| DSU with component size | Track sizes | Number of Provinces |
| Weighted DSU | Store relative weights | Evaluate Division |
| DSU on grid | Map 2D cell to 1D index | Number of Islands (DSU variant) |
| Offline DSU | Process queries offline | Dynamic Connectivity |

---

## 16. Practice Problems

### Easy — Foundation
1. **Find if Path Exists in Graph** (LeetCode #1971)
   - *Task:* Check connectivity between source and destination.
   - *Hint:* Union all edges, then check `find(source) == find(destination)`.

2. **Number of Provinces** (LeetCode #547)
   - *Task:* Count connected groups of cities.
   - *Hint:* Union all connected pairs. Answer = components after all unions.

3. **Merge Accounts** — simplified warmup
   - *Task:* Check if any two accounts share an email.
   - *Hint:* Map emails to IDs, union accounts sharing emails.

### Medium — Classic DSU
1. **Redundant Connection** (LeetCode #684)
   - *Task:* Find the extra edge that creates a cycle.
   - *Hint:* Process edges in order. First edge where both nodes have same root = answer.

2. **Number of Operations to Make Network Connected** (LeetCode #1319)
   - *Task:* Min cable moves to connect all computers.
   - *Hint:* Count components after union. Answer = components - 1 (if enough cables exist).

3. **Accounts Merge** (LeetCode #721)
   - *Task:* Merge accounts sharing common email.
   - *Hint:* Map each email to an ID; union IDs within same account; group by root.

4. **Minimum Spanning Tree / Cost** (LeetCode #1584, #1135)
   - *Task:* Connect all points/nodes with minimum cost.
   - *Hint:* Kruskal's — sort edges by weight, union greedily.

5. **Evaluate Division** (LeetCode #399)
   - *Task:* Answer queries like a/c given a/b and b/c.
   - *Hint:* Weighted DSU where weight = ratio to root.

### Hard — Advanced DSU
1. **Satisfiability of Equality Equations** (LeetCode #990)
   - *Task:* Check if a set of equality/inequality equations is consistent.
   - *Hint:* Union all equalities first, then check inequalities for contradiction.

2. **Swim in Rising Water** (LeetCode #778)
   - *Task:* Minimum time to swim from (0,0) to (n-1,n-1).
   - *Hint:* Sort cells by elevation, union neighbors, return time when (0,0) and (n-1,n-1) connect.

3. **Bricks Falling When Hit** (LeetCode #803)
   - *Task:* After removing bricks, how many fall?
   - *Hint:* Reverse process — add bricks back, use DSU to count new cells attached to top.

---

## 17. How to Know You Have Mastered DSU

You have mastered this topic when you can:
- [ ] Write the full DSU class (with path compression + union by rank) from memory
- [ ] Explain why path compression doesn't change logical correctness
- [ ] Implement Redundant Connection (first edge forming a cycle)
- [ ] Implement Kruskal's MST using DSU
- [ ] Count connected components using the `components` counter
- [ ] Handle 1-indexed vs 0-indexed node IDs correctly
- [ ] Identify when DSU is better than BFS/DFS for a problem
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. After `union(1,2)`, `union(2,3)`, `union(4,5)`, how many components remain in a 5-node graph (0-indexed)?

2. In path compression, you write `parent[x] = find(parent[x])`. What does this assignment accomplish?

3. Union by rank always attaches the tree with smaller rank under the tree with larger rank. What happens when ranks are equal?

4. In Kruskal's, you sort edges and process them greedily. What condition stops you from adding an edge?

5. If `find(u) == find(v)` before calling `union(u, v)`, what does that mean in the context of Redundant Connection?

6. Can DSU handle a directed graph for cycle detection? What about an undirected graph?

7. In path compression, is the tree's logical structure (which nodes are in which component) changed? Is the physical structure changed?

8. How does the `components` counter help you determine if it's impossible to connect all computers with k cables?

> **Answers:**
> 1. 2 components: {0}, {1,2,3}, {4,5} → Wait: 0 was never unioned → components: {0}, {1,2,3}, {4,5} = 3 components.
> 2. It makes x directly point to the root (skipping intermediate nodes). Future find(x) calls are O(1) instead of traversing the chain.
> 3. When ranks are equal, you can attach either way. By convention, attach y's root under x's root, then increment rank[x] (tree height increased by 1).
> 4. If `find(edge[0]) == find(edge[1])` — adding this edge would create a cycle (both endpoints already in the same component).
> 5. It means u and v are already in the same connected component. Adding the edge (u,v) creates a redundant (cycle-creating) connection.
> 6. Basic DSU works for undirected graphs (cycle detection: same component before union = cycle). For directed graphs, DSU doesn't distinguish direction — you'd need DFS with state tracking for directed cycle detection.
> 7. Logical structure: NOT changed (same roots, same components). Physical structure: YES changed — intermediate nodes now point directly to root.
> 8. After all unions, `components - 1` = minimum cables needed to connect all. If you have fewer cables (extra connections) than `components - 1`, it's impossible.

---

**Next →** `../17_Greedy/01_Greedy_Algorithms.md`

---

## 2. Beginner-Friendly Intuition

Students in a school form friend groups. If Alice and Bob are friends, they're in the same group. If you then become friends with Bob, you join Alice's group too.

DSU tracks which group each person belongs to and merges groups efficiently.

---

## 3. Core Operations

| Operation | Description | Naive | Optimized |
|-----------|-------------|-------|-----------|
| find(x) | Which group does x belong to? | O(n) | O(α(n)) ≈ O(1) |
| union(x,y) | Merge groups of x and y | O(n) | O(α(n)) ≈ O(1) |

α(n) = inverse Ackermann function — effectively constant for all practical n.

---

## 4. Implementation with Path Compression + Union by Rank

```java
class DSU {
    int[] parent, rank;

    DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;  // each node is its own parent
    }

    // Find with path compression
    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);  // path compression: point directly to root
        return parent[x];
    }

    // Union by rank
    boolean union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == rootY) return false;  // already same group
        if (rank[rootX] < rank[rootY]) { int tmp = rootX; rootX = rootY; rootY = tmp; }
        parent[rootY] = rootX;
        if (rank[rootX] == rank[rootY]) rank[rootX]++;
        return true;
    }

    boolean connected(int x, int y) { return find(x) == find(y); }
}
```

---

## 5. Dry Run: Path Compression

Initial: parent=[0,1,2,3,4] (each node is own parent)

```
union(0,1): rootX=0, rootY=1 → parent[1]=0; parent=[0,0,2,3,4]
union(1,2): find(1)→0, rootX=0, rootY=2 → parent[2]=0; parent=[0,0,0,3,4]
union(3,4): parent=[0,0,0,3,3]
union(0,3): parent[3]=0; parent=[0,0,0,0,3]

find(4): parent[4]=3 → parent[3]=0 → return 0 (path compression!)
         parent[4] now = 0 directly: parent=[0,0,0,0,0] ← compressed!
```

---

## 6. DSU with Size (instead of Rank)

```java
class DSUSize {
    int[] parent, size;

    DSUSize(int n) {
        parent = new int[n];
        size = new int[n];
        Arrays.fill(size, 1);
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    boolean union(int x, int y) {
        int rx = find(x), ry = find(y);
        if (rx == ry) return false;
        if (size[rx] < size[ry]) { int tmp = rx; rx = ry; ry = tmp; }
        parent[ry] = rx;
        size[rx] += size[ry];
        return true;
    }

    int getSize(int x) { return size[find(x)]; }
}
```

---

## 7. Key Patterns

### Pattern 1: Number of Connected Components
```java
int countComponents(int n, int[][] edges) {
    DSU dsu = new DSU(n);
    int components = n;
    for (int[] e : edges)
        if (dsu.union(e[0], e[1])) components--;
    return components;
}
```

### Pattern 2: Cycle Detection in Undirected Graph
```java
boolean hasCycle(int n, int[][] edges) {
    DSU dsu = new DSU(n);
    for (int[] e : edges) {
        if (!dsu.union(e[0], e[1])) return true;  // already connected = cycle
    }
    return false;
}
```

### Pattern 3: Redundant Connection
```java
int[] findRedundantConnection(int[][] edges) {
    DSU dsu = new DSU(edges.length + 1);
    for (int[] e : edges) {
        if (!dsu.union(e[0], e[1])) return e;  // this edge creates a cycle
    }
    return new int[]{};
}
```

### Pattern 4: Accounts Merge (String-based DSU)
```java
List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, String> parent = new HashMap<>();
    Map<String, String> emailToName = new HashMap<>();

    // Initialize each email as own parent
    for (List<String> account : accounts) {
        String name = account.get(0);
        for (int i = 1; i < account.size(); i++) {
            parent.put(account.get(i), account.get(i));
            emailToName.put(account.get(i), name);
        }
    }
    // Union emails in same account
    for (List<String> account : accounts) {
        String first = find(parent, account.get(1));
        for (int i = 2; i < account.size(); i++)
            parent.put(find(parent, account.get(i)), first);
    }
    // Group by root
    Map<String, TreeSet<String>> groups = new HashMap<>();
    for (String email : parent.keySet()) {
        String root = find(parent, email);
        groups.computeIfAbsent(root, k -> new TreeSet<>()).add(email);
    }
    List<List<String>> result = new ArrayList<>();
    for (Map.Entry<String, TreeSet<String>> e : groups.entrySet()) {
        List<String> merged = new ArrayList<>();
        merged.add(emailToName.get(e.getKey()));
        merged.addAll(e.getValue());
        result.add(merged);
    }
    return result;
}

String find(Map<String, String> parent, String x) {
    if (!parent.get(x).equals(x)) parent.put(x, find(parent, parent.get(x)));
    return parent.get(x);
}
```

---

## 8. Why Path Compression is Powerful

Without compression: find() traces the whole chain → O(n) worst case.  
With compression: After first find(), every node on path points to root directly.  
Result: Subsequent finds are O(1).

---

## 9. Practice Problems

**Easy:**
1. Find if Path Exists in Graph.
2. Number of Provinces.
3. Redundant Connection.

**Medium:**
1. Graph Valid Tree.
2. Accounts Merge.
3. Number of Operations to Make Network Connected.
4. Satisfiability of Equality Equations.
5. Most Stones Removed.

**Hard:**
1. Swim in Rising Water.
2. Minimize Malware Spread.
3. Number of Islands II (online queries).

---

**Next →** `../17_Greedy/01_Greedy_Algorithms.md`
