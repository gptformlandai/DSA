# Hot 150 Solutions — Completion Batch (Trees, Backtracking, Graphs, DP)

> This file closes the final 41 core Hot 150 problems that were tracked but lacked a solution card, so **every** problem in `01_Hot_150_Index.md` now has a written solution. Same compact format as files 03–06: code → Pattern / Time / Space / Trap / Interview line.

---

## Trees & BST

## 226. Invert Binary Tree

```java
TreeNode invertTree(TreeNode root) {
    if (root == null) return null;
    TreeNode left = invertTree(root.left);
    root.left = invertTree(root.right);
    root.right = left;
    return root;
}
```

- Pattern: Tree recursion (swap children)
- Time: `O(n)`
- Space: `O(h)` recursion stack
- Trap: Save one child before overwriting it.
- Interview line: "Invert both subtrees, then swap the pointers."

---

## 104. Maximum Depth of Binary Tree

```java
int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

- Pattern: DFS height
- Time: `O(n)`
- Space: `O(h)`
- Trap: Null node contributes depth 0, not 1.
- Interview line: "Depth of a node is one plus the deeper child."

---

## 543. Diameter of Binary Tree

```java
int best = 0;
int diameterOfBinaryTree(TreeNode root) {
    depth(root);
    return best;
}
int depth(TreeNode node) {
    if (node == null) return 0;
    int l = depth(node.left), r = depth(node.right);
    best = Math.max(best, l + r);      // longest path THROUGH this node
    return 1 + Math.max(l, r);         // height returned UP
}
```

- Pattern: Postorder return height, update global answer
- Time: `O(n)`
- Space: `O(h)`
- Trap: Diameter counts edges (`l + r`), but you return `1 + max` for height.
- Interview line: "Separate what I update at the node from what I return to the parent."

---

## 110. Balanced Binary Tree

```java
boolean balanced = true;
boolean isBalanced(TreeNode root) {
    height(root);
    return balanced;
}
int height(TreeNode node) {
    if (node == null) return 0;
    int l = height(node.left), r = height(node.right);
    if (Math.abs(l - r) > 1) balanced = false;
    return 1 + Math.max(l, r);
}
```

- Pattern: Postorder with a sentinel flag
- Time: `O(n)`
- Space: `O(h)`
- Trap: Naive `isBalanced` recomputing height is `O(n^2)`; compute height once bottom-up.
- Interview line: "One postorder pass computes height and checks balance simultaneously."

---

## 100. Same Tree

```java
boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null || q == null) return p == q;
    return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
}
```

- Pattern: Paired recursion
- Time: `O(n)`
- Space: `O(h)`
- Trap: Handle one-null-one-not before dereferencing `.val`.
- Interview line: "Both null means equal; one null means different; else compare value and children."

---

## 572. Subtree of Another Tree

```java
boolean isSubtree(TreeNode root, TreeNode sub) {
    if (root == null) return false;
    if (isSameTree(root, sub)) return true;
    return isSubtree(root.left, sub) || isSubtree(root.right, sub);
}
boolean isSameTree(TreeNode a, TreeNode b) {
    if (a == null || b == null) return a == b;
    return a.val == b.val && isSameTree(a.left, b.left) && isSameTree(a.right, b.right);
}
```

- Pattern: Recursion + same-tree check
- Time: `O(m * n)` worst case
- Space: `O(h)`
- Trap: Must match a *full* subtree, not just a path.
- Interview line: "At each node, test whether the subtree rooted here equals the target."

---

## 235. Lowest Common Ancestor of a BST

```java
TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    while (root != null) {
        if (p.val < root.val && q.val < root.val) root = root.left;
        else if (p.val > root.val && q.val > root.val) root = root.right;
        else return root;                  // split point = LCA
    }
    return null;
}
```

- Pattern: BST ordering
- Time: `O(h)`
- Space: `O(1)`
- Trap: The split point (where p and q diverge) is the LCA — no full search needed.
- Interview line: "Walk down while both are on the same side; the first split is the LCA."

---

## 102. Binary Tree Level Order Traversal

```java
List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    while (!q.isEmpty()) {
        int size = q.size();               // freeze this level's width
        List<Integer> level = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode n = q.poll();
            level.add(n.val);
            if (n.left != null) q.offer(n.left);
            if (n.right != null) q.offer(n.right);
        }
        res.add(level);
    }
    return res;
}
```

- Pattern: BFS by level
- Time: `O(n)`
- Space: `O(n)`
- Trap: Capture `q.size()` before the inner loop, or levels merge.
- Interview line: "Process one full level at a time by snapshotting the queue size."

---

## 199. Binary Tree Right Side View

```java
List<Integer> rightSideView(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q = new LinkedList<>();
    q.offer(root);
    while (!q.isEmpty()) {
        int size = q.size();
        for (int i = 0; i < size; i++) {
            TreeNode n = q.poll();
            if (i == size - 1) res.add(n.val);   // last node of the level
            if (n.left != null) q.offer(n.left);
            if (n.right != null) q.offer(n.right);
        }
    }
    return res;
}
```

- Pattern: BFS, take last node per level
- Time: `O(n)`
- Space: `O(n)`
- Trap: It's the last node *seen* per level, not always a right child.
- Interview line: "The right view is the last node in each BFS level."

---

## 105. Construct Binary Tree from Preorder and Inorder

```java
Map<Integer,Integer> idx = new HashMap<>();
int pre = 0;
TreeNode buildTree(int[] preorder, int[] inorder) {
    for (int i = 0; i < inorder.length; i++) idx.put(inorder[i], i);
    return build(preorder, 0, inorder.length - 1);
}
TreeNode build(int[] preorder, int lo, int hi) {
    if (lo > hi) return null;
    int rootVal = preorder[pre++];
    TreeNode root = new TreeNode(rootVal);
    int mid = idx.get(rootVal);
    root.left  = build(preorder, lo, mid - 1);
    root.right = build(preorder, mid + 1, hi);
    return root;
}
```

- Pattern: Preorder gives roots; inorder splits subtrees
- Time: `O(n)`
- Space: `O(n)`
- Trap: Build the left subtree before the right so the shared `pre` index advances correctly.
- Interview line: "Preorder's next value is the root; its inorder index splits left and right."

---

## 297. Serialize and Deserialize Binary Tree

```java
String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    dfsSer(root, sb);
    return sb.toString();
}
void dfsSer(TreeNode n, StringBuilder sb) {
    if (n == null) { sb.append("#,"); return; }
    sb.append(n.val).append(",");
    dfsSer(n.left, sb);
    dfsSer(n.right, sb);
}
int p = 0;
TreeNode deserialize(String data) {
    String[] t = data.split(",");
    return dfsDe(t);
}
TreeNode dfsDe(String[] t) {
    String v = t[p++];
    if (v.equals("#")) return null;
    TreeNode n = new TreeNode(Integer.parseInt(v));
    n.left = dfsDe(t);
    n.right = dfsDe(t);
    return n;
}
```

- Pattern: Preorder DFS with null markers
- Time: `O(n)`
- Space: `O(n)`
- Trap: Encode nulls (`#`) so structure is recoverable.
- Interview line: "Preorder with explicit null markers uniquely encodes the tree."

---

## 99. Recover Binary Search Tree

```java
TreeNode first, second, prev;
void recoverTree(TreeNode root) {
    inorder(root);
    int t = first.val; first.val = second.val; second.val = t;   // swap back
}
void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    if (prev != null && prev.val > node.val) {   // anomaly in sorted order
        if (first == null) first = prev;
        second = node;
    }
    prev = node;
    inorder(node.right);
}
```

- Pattern: Inorder detects the two out-of-order nodes
- Time: `O(n)`
- Space: `O(h)`
- Trap: Two swapped nodes create either one or two inversions; `first` = first inversion's prev, `second` = last inversion's current.
- Interview line: "Inorder of a BST is sorted; the swapped pair shows up as inversions."

---

## Recursion & Backtracking

## 90. Subsets II

```java
List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, 0, new ArrayList<>(), res);
    return res;
}
void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> res) {
    res.add(new ArrayList<>(path));
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue;   // skip duplicate at this level
        path.add(nums[i]);
        backtrack(nums, i + 1, path, res);
        path.remove(path.size() - 1);
    }
}
```

- Pattern: Subset DFS with duplicate skip
- Time: `O(n * 2^n)`
- Space: `O(n)` recursion
- Trap: Sort first; skip `nums[i]==nums[i-1]` only when `i > start`.
- Interview line: "Sorting groups duplicates so I can skip repeats at the same tree level."

---

## 46. Permutations

```java
List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, new boolean[nums.length], new ArrayList<>(), res);
    return res;
}
void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> res) {
    if (path.size() == nums.length) { res.add(new ArrayList<>(path)); return; }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true; path.add(nums[i]);
        backtrack(nums, used, path, res);
        used[i] = false; path.remove(path.size() - 1);
    }
}
```

- Pattern: Used-array DFS
- Time: `O(n * n!)`
- Space: `O(n)`
- Trap: Reset `used[i]` and pop the path on backtrack.
- Interview line: "At each position, try every unused number, then undo."

---

## 47. Permutations II

```java
List<List<Integer>> permuteUnique(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> res = new ArrayList<>();
    backtrack(nums, new boolean[nums.length], new ArrayList<>(), res);
    return res;
}
void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> res) {
    if (path.size() == nums.length) { res.add(new ArrayList<>(path)); return; }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;  // dedup siblings
        used[i] = true; path.add(nums[i]);
        backtrack(nums, used, path, res);
        used[i] = false; path.remove(path.size() - 1);
    }
}
```

- Pattern: Permutation DFS with sibling dedup
- Time: `O(n * n!)`
- Space: `O(n)`
- Trap: Skip a duplicate only when its identical predecessor is unused (`!used[i-1]`).
- Interview line: "Sort, then force duplicates to be used in a fixed left-to-right order."

---

## 40. Combination Sum II

```java
List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    List<List<Integer>> res = new ArrayList<>();
    backtrack(candidates, target, 0, new ArrayList<>(), res);
    return res;
}
void backtrack(int[] c, int target, int start, List<Integer> path, List<List<Integer>> res) {
    if (target == 0) { res.add(new ArrayList<>(path)); return; }
    for (int i = start; i < c.length && c[i] <= target; i++) {
        if (i > start && c[i] == c[i - 1]) continue;   // each number used once per branch
        path.add(c[i]);
        backtrack(c, target - c[i], i + 1, path, res); // i+1: no reuse
        path.remove(path.size() - 1);
    }
}
```

- Pattern: Combination DFS, use each element once
- Time: `O(2^n)`
- Space: `O(n)`
- Trap: Advance to `i + 1` (no reuse) and skip duplicates at the same level.
- Interview line: "Sort, prune when `c[i] > target`, and skip repeated candidates per level."

---

## 131. Palindrome Partitioning

```java
List<List<String>> partition(String s) {
    List<List<String>> res = new ArrayList<>();
    backtrack(s, 0, new ArrayList<>(), res);
    return res;
}
void backtrack(String s, int start, List<String> path, List<List<String>> res) {
    if (start == s.length()) { res.add(new ArrayList<>(path)); return; }
    for (int end = start; end < s.length(); end++) {
        if (isPal(s, start, end)) {
            path.add(s.substring(start, end + 1));
            backtrack(s, end + 1, path, res);
            path.remove(path.size() - 1);
        }
    }
}
boolean isPal(String s, int l, int r) {
    while (l < r) if (s.charAt(l++) != s.charAt(r--)) return false;
    return true;
}
```

- Pattern: Partition DFS + palindrome check
- Time: `O(n * 2^n)`
- Space: `O(n)`
- Trap: Only recurse when the current prefix is a palindrome.
- Interview line: "Cut after each palindromic prefix and recurse on the rest."

---

## 17. Letter Combinations of a Phone Number

```java
String[] MAP = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
List<String> letterCombinations(String digits) {
    List<String> res = new ArrayList<>();
    if (digits.isEmpty()) return res;
    backtrack(digits, 0, new StringBuilder(), res);
    return res;
}
void backtrack(String digits, int i, StringBuilder sb, List<String> res) {
    if (i == digits.length()) { res.add(sb.toString()); return; }
    for (char c : MAP[digits.charAt(i) - '0'].toCharArray()) {
        sb.append(c);
        backtrack(digits, i + 1, sb, res);
        sb.deleteCharAt(sb.length() - 1);
    }
}
```

- Pattern: Choice-tree DFS
- Time: `O(4^n * n)`
- Space: `O(n)`
- Trap: Return early for empty input to avoid an empty-string result.
- Interview line: "Each digit multiplies the branches; DFS builds every string."

---

## Graphs & DSU

## 695. Max Area of Island

```java
int maxAreaOfIsland(int[][] grid) {
    int max = 0;
    for (int i = 0; i < grid.length; i++)
        for (int j = 0; j < grid[0].length; j++)
            if (grid[i][j] == 1) max = Math.max(max, dfs(grid, i, j));
    return max;
}
int dfs(int[][] g, int i, int j) {
    if (i < 0 || j < 0 || i >= g.length || j >= g[0].length || g[i][j] == 0) return 0;
    g[i][j] = 0;                          // sink to avoid revisits
    return 1 + dfs(g, i+1, j) + dfs(g, i-1, j) + dfs(g, i, j+1) + dfs(g, i, j-1);
}
```

- Pattern: Grid DFS area
- Time: `O(m*n)`
- Space: `O(m*n)` recursion worst case
- Trap: Mark visited by sinking to 0, or you double-count.
- Interview line: "DFS each unvisited land cell, summing its connected area."

---

## 417. Pacific Atlantic Water Flow

```java
int m, n;
List<List<Integer>> pacificAtlantic(int[][] h) {
    m = h.length; n = h[0].length;
    boolean[][] pac = new boolean[m][n], atl = new boolean[m][n];
    for (int i = 0; i < m; i++) { dfs(h, i, 0, pac); dfs(h, i, n-1, atl); }
    for (int j = 0; j < n; j++) { dfs(h, 0, j, pac); dfs(h, m-1, j, atl); }
    List<List<Integer>> res = new ArrayList<>();
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            if (pac[i][j] && atl[i][j]) res.add(List.of(i, j));
    return res;
}
void dfs(int[][] h, int i, int j, boolean[][] seen) {
    seen[i][j] = true;
    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    for (int[] d : dirs) {
        int ni = i + d[0], nj = j + d[1];
        if (ni>=0 && nj>=0 && ni<m && nj<n && !seen[ni][nj] && h[ni][nj] >= h[i][j])
            dfs(h, ni, nj, seen);          // flow UPHILL from the ocean inward
    }
}
```

- Pattern: Reverse DFS from both oceans
- Time: `O(m*n)`
- Space: `O(m*n)`
- Trap: Search from the borders inward (uphill), then intersect the two reachable sets.
- Interview line: "Instead of tracing each cell to the ocean, flood inward from both oceans and intersect."

---

## 994. Rotting Oranges

```java
int orangesRotting(int[][] grid) {
    int m = grid.length, n = grid[0].length, fresh = 0, minutes = 0;
    Queue<int[]> q = new LinkedList<>();
    for (int i = 0; i < m; i++)
        for (int j = 0; j < n; j++)
            if (grid[i][j] == 2) q.offer(new int[]{i, j});
            else if (grid[i][j] == 1) fresh++;
    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
    while (!q.isEmpty() && fresh > 0) {
        minutes++;
        for (int k = q.size(); k > 0; k--) {
            int[] c = q.poll();
            for (int[] d : dirs) {
                int ni = c[0]+d[0], nj = c[1]+d[1];
                if (ni>=0 && nj>=0 && ni<m && nj<n && grid[ni][nj] == 1) {
                    grid[ni][nj] = 2; fresh--;
                    q.offer(new int[]{ni, nj});
                }
            }
        }
    }
    return fresh == 0 ? minutes : -1;
}
```

- Pattern: Multi-source BFS
- Time: `O(m*n)`
- Space: `O(m*n)`
- Trap: Seed the queue with ALL rotten oranges; return -1 if fresh remain.
- Interview line: "All rotten cells spread simultaneously — BFS level count is the answer."

---

## 210. Course Schedule II

```java
int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> adj = new ArrayList<>();
    int[] indeg = new int[numCourses];
    for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
    for (int[] p : prerequisites) { adj.get(p[1]).add(p[0]); indeg[p[0]]++; }
    Queue<Integer> q = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) if (indeg[i] == 0) q.offer(i);
    int[] order = new int[numCourses];
    int idx = 0;
    while (!q.isEmpty()) {
        int u = q.poll();
        order[idx++] = u;
        for (int v : adj.get(u)) if (--indeg[v] == 0) q.offer(v);
    }
    return idx == numCourses ? order : new int[0];   // cycle => empty
}
```

- Pattern: Topological sort (Kahn's)
- Time: `O(V+E)`
- Space: `O(V+E)`
- Trap: If fewer than `numCourses` are emitted, a cycle exists — return empty.
- Interview line: "Peel off zero-indegree nodes; incomplete output means a cycle."

---

## 684. Redundant Connection

```java
int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n + 1];
    for (int i = 1; i <= n; i++) parent[i] = i;
    for (int[] e : edges) {
        if (find(parent, e[0]) == find(parent, e[1])) return e;  // both already connected
        parent[find(parent, e[0])] = find(parent, e[1]);
    }
    return new int[0];
}
int find(int[] p, int x) {
    while (p[x] != x) { p[x] = p[p[x]]; x = p[x]; }   // path compression
    return x;
}
```

- Pattern: Union-Find cycle edge
- Time: `O(n α(n))`
- Space: `O(n)`
- Trap: The answer is the first edge whose endpoints are already connected.
- Interview line: "Union edges; the edge that closes a cycle is redundant."

---

## 743. Network Delay Time

```java
int networkDelayTime(int[][] times, int n, int k) {
    List<int[]>[] adj = new List[n + 1];
    for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
    for (int[] t : times) adj[t[0]].add(new int[]{t[1], t[2]});
    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
    pq.offer(new int[]{k, 0});
    while (!pq.isEmpty()) {
        int[] cur = pq.poll();
        int u = cur[0], d = cur[1];
        if (d > dist[u]) continue;                    // stale entry
        for (int[] e : adj[u])
            if (d + e[1] < dist[e[0]]) { dist[e[0]] = d + e[1]; pq.offer(new int[]{e[0], dist[e[0]]}); }
    }
    int max = 0;
    for (int i = 1; i <= n; i++) { if (dist[i] == Integer.MAX_VALUE) return -1; max = Math.max(max, dist[i]); }
    return max;
}
```

- Pattern: Dijkstra (single source)
- Time: `O(E log V)`
- Space: `O(V+E)`
- Trap: Answer is the MAX finalized distance; -1 if any node is unreachable.
- Interview line: "Dijkstra from the source; the signal arrives when the last node is reached."

---

## 1584. Min Cost to Connect All Points

```java
int minCostConnectPoints(int[][] points) {
    int n = points.length, total = 0, count = 0;
    boolean[] inMST = new boolean[n];
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // {node, cost}
    pq.offer(new int[]{0, 0});
    while (!pq.isEmpty() && count < n) {
        int[] top = pq.poll();
        int u = top[0];
        if (inMST[u]) continue;
        inMST[u] = true; total += top[1]; count++;
        for (int v = 0; v < n; v++)
            if (!inMST[v]) {
                int w = Math.abs(points[u][0]-points[v][0]) + Math.abs(points[u][1]-points[v][1]);
                pq.offer(new int[]{v, w});
            }
    }
    return total;
}
```

- Pattern: Prim's MST on a complete graph
- Time: `O(n^2 log n)`
- Space: `O(n^2)` edges in the heap
- Trap: Edges are implicit (Manhattan distance between every pair); skip nodes already in the MST.
- Interview line: "Grow a minimum spanning tree using Manhattan-distance edges."

---

## Dynamic Programming

## 70. Climbing Stairs

```java
int climbStairs(int n) {
    int a = 1, b = 1;                 // ways to reach step 0 and 1
    for (int i = 2; i <= n; i++) { int c = a + b; a = b; b = c; }
    return b;
}
```

- Pattern: 1D count (Fibonacci)
- Time: `O(n)`
- Space: `O(1)`
- Trap: Rolling two variables avoids an array.
- Interview line: "Ways to reach step n = ways to n-1 plus ways to n-2."

---

## 198. House Robber

```java
int rob(int[] nums) {
    int prev = 0, cur = 0;            // best up to i-2 and i-1
    for (int n : nums) { int t = Math.max(cur, prev + n); prev = cur; cur = t; }
    return cur;
}
```

- Pattern: Take/skip DP
- Time: `O(n)`
- Space: `O(1)`
- Trap: `take = prev + n` (skip the adjacent house).
- Interview line: "At each house, best of skipping it or robbing it plus the best two back."

---

## 213. House Robber II

```java
int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    return Math.max(robLine(nums, 0, nums.length - 2),   // exclude last
                    robLine(nums, 1, nums.length - 1));   // exclude first
}
int robLine(int[] nums, int lo, int hi) {
    int prev = 0, cur = 0;
    for (int i = lo; i <= hi; i++) { int t = Math.max(cur, prev + nums[i]); prev = cur; cur = t; }
    return cur;
}
```

- Pattern: Circular take/skip (two linear passes)
- Time: `O(n)`
- Space: `O(1)`
- Trap: Houses are a circle, so the first and last can't both be robbed — try each exclusion.
- Interview line: "Break the circle by solving twice: without the first, and without the last."

---

## 518. Coin Change II

```java
int change(int amount, int[] coins) {
    int[] dp = new int[amount + 1];
    dp[0] = 1;
    for (int coin : coins)                 // coin loop OUTSIDE => combinations, not permutations
        for (int a = coin; a <= amount; a++)
            dp[a] += dp[a - coin];
    return dp[amount];
}
```

- Pattern: Unbounded knapsack (count combinations)
- Time: `O(amount * coins)`
- Space: `O(amount)`
- Trap: Coin loop must be outer to avoid counting `[1,2]` and `[2,1]` separately.
- Interview line: "Iterate coins outside so each combination is counted once."

---

## 300. Longest Increasing Subsequence

```java
int lengthOfLIS(int[] nums) {
    List<Integer> tails = new ArrayList<>();
    for (int n : nums) {
        int i = Collections.binarySearch(tails, n);
        if (i < 0) i = -(i + 1);
        if (i == tails.size()) tails.add(n);
        else tails.set(i, n);              // replace to keep tails minimal
    }
    return tails.size();
}
```

- Pattern: Patience sorting + binary search
- Time: `O(n log n)`
- Space: `O(n)`
- Trap: `tails` isn't the actual LIS, but its length is correct.
- Interview line: "Keep the smallest possible tail for each subsequence length via binary search."

---

## 416. Partition Equal Subset Sum

```java
boolean canPartition(int[] nums) {
    int sum = 0;
    for (int n : nums) sum += n;
    if ((sum & 1) == 1) return false;
    int target = sum / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    for (int n : nums)
        for (int a = target; a >= n; a--)   // descending => 0/1 knapsack (no reuse)
            dp[a] |= dp[a - n];
    return dp[target];
}
```

- Pattern: 0/1 knapsack (subset sum)
- Time: `O(n * sum)`
- Space: `O(sum)`
- Trap: Iterate capacity descending so each number is used once.
- Interview line: "Can I hit half the total sum with a subset? That's subset-sum DP."

---

## 494. Target Sum

```java
int findTargetSumWays(int[] nums, int target) {
    int sum = 0;
    for (int n : nums) sum += n;
    if (Math.abs(target) > sum || ((sum + target) & 1) == 1) return 0;
    int s = (sum + target) / 2;             // subset that gets '+' signs
    int[] dp = new int[s + 1];
    dp[0] = 1;
    for (int n : nums)
        for (int a = s; a >= n; a--)
            dp[a] += dp[a - n];
    return dp[s];
}
```

- Pattern: Transform to subset-count knapsack
- Time: `O(n * sum)`
- Space: `O(sum)`
- Trap: `P - N = target`, `P + N = sum` ⇒ `P = (sum+target)/2`; must be a non-negative even split.
- Interview line: "Assigning signs reduces to counting subsets summing to `(sum+target)/2`."

---

## 72. Edit Distance

```java
int minDistance(String a, String b) {
    int m = a.length(), n = b.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) dp[i][0] = i;   // delete all
    for (int j = 0; j <= n; j++) dp[0][j] = j;   // insert all
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            dp[i][j] = a.charAt(i-1) == b.charAt(j-1)
                ? dp[i-1][j-1]
                : 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
    return dp[m][n];
}
```

- Pattern: Two-string edit DP
- Time: `O(m*n)`
- Space: `O(m*n)`
- Trap: The three operations are replace (`dp[i-1][j-1]`), delete (`dp[i-1][j]`), insert (`dp[i][j-1]`).
- Interview line: "Match diagonally for free, else take one plus the cheapest of the three edits."

---

## 115. Distinct Subsequences

```java
int numDistinct(String s, String t) {
    int m = s.length(), n = t.length();
    long[][] dp = new long[m + 1][n + 1];
    for (int i = 0; i <= m; i++) dp[i][0] = 1;   // empty t matched one way
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++) {
            dp[i][j] = dp[i-1][j];               // skip s[i-1]
            if (s.charAt(i-1) == t.charAt(j-1)) dp[i][j] += dp[i-1][j-1]; // use it
        }
    return (int) dp[m][n];
}
```

- Pattern: Two-string count DP
- Time: `O(m*n)`
- Space: `O(m*n)`
- Trap: Always inherit the skip case; add the match case only on equal characters.
- Interview line: "Count ways to form `t` from `s` by optionally using each matching character."

---

## 62. Unique Paths

```java
int uniquePaths(int m, int n) {
    int[] dp = new int[n];
    Arrays.fill(dp, 1);                    // top row and left col are all 1
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            dp[j] += dp[j - 1];            // from above + from left
    return dp[n - 1];
}
```

- Pattern: Grid count DP (rolling row)
- Time: `O(m*n)`
- Space: `O(n)`
- Trap: `dp[j]` already holds the "from above" value before adding `dp[j-1]`.
- Interview line: "Paths to a cell = paths from above plus paths from the left."

---

## 64. Minimum Path Sum

```java
int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[] dp = new int[n];
    dp[0] = grid[0][0];
    for (int j = 1; j < n; j++) dp[j] = dp[j-1] + grid[0][j];
    for (int i = 1; i < m; i++) {
        dp[0] += grid[i][0];
        for (int j = 1; j < n; j++)
            dp[j] = grid[i][j] + Math.min(dp[j], dp[j-1]);
    }
    return dp[n-1];
}
```

- Pattern: Grid min DP (rolling row)
- Time: `O(m*n)`
- Space: `O(n)`
- Trap: Seed the first row/column before the main loop.
- Interview line: "Each cell's min cost is its value plus the cheaper of top and left."

---

## 221. Maximal Square

```java
int maximalSquare(char[][] matrix) {
    int m = matrix.length, n = matrix[0].length, best = 0;
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (matrix[i-1][j-1] == '1') {
                dp[i][j] = 1 + Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]));
                best = Math.max(best, dp[i][j]);
            }
    return best * best;
}
```

- Pattern: 2D local recurrence
- Time: `O(m*n)`
- Space: `O(m*n)`
- Trap: A square's side is bounded by the MIN of its three neighbors, plus one.
- Interview line: "Each cell stores the largest square ending there, limited by its weakest neighbor."

---

## 647. Palindromic Substrings

```java
int countSubstrings(String s) {
    int count = 0;
    for (int center = 0; center < s.length(); center++) {
        count += expand(s, center, center);       // odd length
        count += expand(s, center, center + 1);    // even length
    }
    return count;
}
int expand(String s, int l, int r) {
    int c = 0;
    while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) { c++; l--; r++; }
    return c;
}
```

- Pattern: Expand around center
- Time: `O(n^2)`
- Space: `O(1)`
- Trap: Count both odd and even centers.
- Interview line: "Every palindrome has a center; expand outward from all 2n-1 centers."

---

## 309. Best Time to Buy and Sell Stock with Cooldown

```java
int maxProfit(int[] prices) {
    int hold = Integer.MIN_VALUE, sold = 0, rest = 0;
    for (int p : prices) {
        int prevSold = sold;
        sold = hold + p;                   // sell today
        hold = Math.max(hold, rest - p);   // buy today (rest was yesterday's cooldown)
        rest = Math.max(rest, prevSold);   // cooldown after selling
    }
    return Math.max(sold, rest);
}
```

- Pattern: State-machine DP (hold / sold / rest)
- Time: `O(n)`
- Space: `O(1)`
- Trap: You can only buy from `rest` (enforces the one-day cooldown after a sale).
- Interview line: "Three states — holding, just sold, resting — transition each day."

---

## 714. Best Time to Buy and Sell Stock with Transaction Fee

```java
int maxProfit(int[] prices, int fee) {
    int cash = 0, hold = -prices[0];
    for (int p : prices) {
        cash = Math.max(cash, hold + p - fee);   // sell (pay the fee)
        hold = Math.max(hold, cash - p);         // buy
    }
    return cash;
}
```

- Pattern: State-machine DP (cash / hold)
- Time: `O(n)`
- Space: `O(1)`
- Trap: Subtract the fee once per completed transaction (on sell).
- Interview line: "Track best cash and best holding value; charge the fee when selling."

---

## 91. Decode Ways

```java
int numDecodings(String s) {
    if (s.charAt(0) == '0') return 0;
    int prev2 = 1, prev1 = 1;              // ways to decode length 0 and 1
    for (int i = 1; i < s.length(); i++) {
        int cur = 0;
        if (s.charAt(i) != '0') cur += prev1;                 // single digit
        int two = Integer.parseInt(s.substring(i - 1, i + 1));
        if (two >= 10 && two <= 26) cur += prev2;             // valid pair
        prev2 = prev1; prev1 = cur;
    }
    return prev1;
}
```

- Pattern: 1D count DP with validity checks
- Time: `O(n)`
- Space: `O(1)`
- Trap: `'0'` can't stand alone; a valid pair is 10–26.
- Interview line: "At each index, add ways from a valid single digit and a valid two-digit pair."

---

## Coverage

With this batch, **all 150 core problems** in `01_Hot_150_Index.md` have written solution cards across files 02–07. Continue to the Hot 200 extension queue only after you can solve the core 150 from memory.

---

**Next:** `../27_Practice_Plan/04_Behavioral_Communication_And_Mock_Rubric.md`
