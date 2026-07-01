# Hot 150 Solutions - Trees, Backtracking, Graphs, DP Expansion

> This file expands the hottest MAANG areas that were still queued after the starter walkthroughs: Trees/BST, Recursion/Backtracking, Graphs/DSU, and Dynamic Programming.

---

## Tree and BST Cards

### 226. Invert Binary Tree

```java
TreeNode invertTree(TreeNode root) {
    if (root == null) return null;

    TreeNode left = invertTree(root.left);
    TreeNode right = invertTree(root.right);
    root.left = right;
    root.right = left;
    return root;
}
```

- Pattern: Tree recursion
- Time: `O(n)`
- Space: `O(h)`
- Trap: Swapping before or after recursion both work, but return the root.
- Interview line: "Every node's left and right children are swapped recursively, so the whole tree is mirrored."

---

### 104. Maximum Depth of Binary Tree

```java
int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

- Pattern: DFS height
- Time: `O(n)`
- Space: `O(h)`
- Trap: Depth of null is `0`, not `-1`, for LeetCode-style node count depth.
- Interview line: "The depth of a node is one plus the deeper child depth."

---

### 543. Diameter of Binary Tree

```java
int diameter;

int diameterOfBinaryTree(TreeNode root) {
    diameter = 0;
    height(root);
    return diameter;
}

int height(TreeNode node) {
    if (node == null) return 0;

    int left = height(node.left);
    int right = height(node.right);
    diameter = Math.max(diameter, left + right);

    return 1 + Math.max(left, right);
}
```

- Pattern: Postorder height with global answer
- Time: `O(n)`
- Space: `O(h)`
- Trap: Return height upward, but update diameter with `left + right`.
- Interview line: "A diameter passing through a node uses the best left height plus best right height."

---

### 110. Balanced Binary Tree

```java
boolean isBalanced(TreeNode root) {
    return checkHeight(root) != -1;
}

int checkHeight(TreeNode node) {
    if (node == null) return 0;

    int left = checkHeight(node.left);
    if (left == -1) return -1;

    int right = checkHeight(node.right);
    if (right == -1) return -1;

    if (Math.abs(left - right) > 1) return -1;
    return 1 + Math.max(left, right);
}
```

- Pattern: Postorder with sentinel
- Time: `O(n)`
- Space: `O(h)`
- Trap: Recomputing height at every node causes `O(n^2)`.
- Interview line: "I return height if balanced, and `-1` as an early failure signal."

---

### 100. Same Tree

```java
boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null || q == null) return p == q;
    if (p.val != q.val) return false;

    return isSameTree(p.left, q.left)
            && isSameTree(p.right, q.right);
}
```

- Pattern: Paired recursion
- Time: `O(n)`
- Space: `O(h)`
- Trap: Both structure and values must match.
- Interview line: "Two trees are the same if roots match and both child pairs are the same."

---

### 572. Subtree of Another Tree

```java
boolean isSubtree(TreeNode root, TreeNode subRoot) {
    if (root == null) return false;
    if (same(root, subRoot)) return true;

    return isSubtree(root.left, subRoot)
            || isSubtree(root.right, subRoot);
}

boolean same(TreeNode a, TreeNode b) {
    if (a == null || b == null) return a == b;
    return a.val == b.val
            && same(a.left, b.left)
            && same(a.right, b.right);
}
```

- Pattern: Try every root + same tree
- Time: `O(n * m)` worst case
- Space: `O(h)`
- Trap: Matching a value is not enough; the whole subtree structure must match.
- Interview line: "At every node in the main tree, I test whether the subtree rooted there equals `subRoot`."

---

### 235. Lowest Common Ancestor of a BST

```java
TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    while (root != null) {
        if (p.val < root.val && q.val < root.val) root = root.left;
        else if (p.val > root.val && q.val > root.val) root = root.right;
        else return root;
    }

    return null;
}
```

- Pattern: BST property
- Time: `O(h)`
- Space: `O(1)`
- Trap: This works for BST only, not general binary trees.
- Interview line: "If both values are smaller, go left; if both larger, go right; otherwise current root splits them."

---

### 102. Binary Tree Level Order Traversal

```java
List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> ans = new ArrayList<>();
    if (root == null) return ans;

    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        List<Integer> level = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            level.add(node.val);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }

        ans.add(level);
    }

    return ans;
}
```

- Pattern: BFS by level size
- Time: `O(n)`
- Space: `O(w)`, where `w` is max width
- Trap: Capture `queue.size()` before processing the level.
- Interview line: "BFS naturally visits nodes level by level."

---

### 199. Binary Tree Right Side View

```java
List<Integer> rightSideView(TreeNode root) {
    List<Integer> ans = new ArrayList<>();
    if (root == null) return ans;

    Queue<TreeNode> queue = new ArrayDeque<>();
    queue.offer(root);

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            if (i == size - 1) ans.add(node.val);
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    return ans;
}
```

- Pattern: BFS last node per level
- Time: `O(n)`
- Space: `O(w)`
- Trap: Add only the last node of each level.
- Interview line: "The right side view is the last visible node in each BFS level."

---

### 105. Construct Binary Tree From Preorder and Inorder

```java
int preIndex;
Map<Integer, Integer> inIndex;

TreeNode buildTree(int[] preorder, int[] inorder) {
    preIndex = 0;
    inIndex = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) inIndex.put(inorder[i], i);
    return build(preorder, 0, inorder.length - 1);
}

TreeNode build(int[] preorder, int left, int right) {
    if (left > right) return null;

    int rootVal = preorder[preIndex++];
    TreeNode root = new TreeNode(rootVal);
    int mid = inIndex.get(rootVal);

    root.left = build(preorder, left, mid - 1);
    root.right = build(preorder, mid + 1, right);
    return root;
}
```

- Pattern: Preorder root + inorder split
- Time: `O(n)`
- Space: `O(n)`
- Trap: Build left subtree before right because preorder is root-left-right.
- Interview line: "Preorder gives the next root; inorder tells how to split its left and right subtrees."

---

### 297. Serialize and Deserialize Binary Tree

```java
String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    encode(root, sb);
    return sb.toString();
}

void encode(TreeNode node, StringBuilder sb) {
    if (node == null) {
        sb.append("#,");
        return;
    }

    sb.append(node.val).append(",");
    encode(node.left, sb);
    encode(node.right, sb);
}

TreeNode deserialize(String data) {
    Queue<String> queue = new ArrayDeque<>(Arrays.asList(data.split(",")));
    return decode(queue);
}

TreeNode decode(Queue<String> queue) {
    String value = queue.poll();
    if (value.equals("#")) return null;

    TreeNode node = new TreeNode(Integer.parseInt(value));
    node.left = decode(queue);
    node.right = decode(queue);
    return node;
}
```

- Pattern: Preorder with null markers
- Time: `O(n)`
- Space: `O(n)`
- Trap: Without null markers, tree shape cannot be uniquely reconstructed.
- Interview line: "I record preorder values including nulls, then consume them in the same order to rebuild."

---

### 99. Recover Binary Search Tree

```java
TreeNode first;
TreeNode second;
TreeNode prev;

void recoverTree(TreeNode root) {
    first = null;
    second = null;
    prev = null;
    inorder(root);

    int temp = first.val;
    first.val = second.val;
    second.val = temp;
}

void inorder(TreeNode node) {
    if (node == null) return;

    inorder(node.left);

    if (prev != null && prev.val > node.val) {
        if (first == null) first = prev;
        second = node;
    }
    prev = node;

    inorder(node.right);
}
```

- Pattern: BST inorder anomaly
- Time: `O(n)`
- Space: `O(h)`
- Trap: Adjacent and non-adjacent swaps both work if `second` is updated on every inversion.
- Interview line: "A valid BST inorder is sorted; swapped nodes create one or two inversions."

---

## Recursion and Backtracking Cards

### 90. Subsets II

```java
List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> ans = new ArrayList<>();
    dfsSubsetsDup(0, nums, new ArrayList<>(), ans);
    return ans;
}

void dfsSubsetsDup(int start, int[] nums, List<Integer> path, List<List<Integer>> ans) {
    ans.add(new ArrayList<>(path));

    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue;
        path.add(nums[i]);
        dfsSubsetsDup(i + 1, nums, path, ans);
        path.remove(path.size() - 1);
    }
}
```

- Pattern: Sort + skip duplicate choices at same depth
- Time: `O(n * 2^n)`
- Space: `O(n)`
- Trap: Use `i > start`, not `i > 0`, so duplicates are skipped only at the current depth.
- Interview line: "Sorting groups duplicates, then each recursion level chooses only the first copy of the same value."

---

### 46. Permutations

```java
List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    boolean[] used = new boolean[nums.length];
    dfsPerm(nums, used, new ArrayList<>(), ans);
    return ans;
}

void dfsPerm(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> ans) {
    if (path.size() == nums.length) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        path.add(nums[i]);
        dfsPerm(nums, used, path, ans);
        path.remove(path.size() - 1);
        used[i] = false;
    }
}
```

- Pattern: Used-array DFS
- Time: `O(n * n!)`
- Space: `O(n)`
- Trap: Subsets move forward with `start`; permutations can choose any unused element.
- Interview line: "Each depth fills one position in the permutation."

---

### 47. Permutations II

```java
List<List<Integer>> permuteUnique(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> ans = new ArrayList<>();
    dfsUniquePerm(nums, new boolean[nums.length], new ArrayList<>(), ans);
    return ans;
}

void dfsUniquePerm(int[] nums, boolean[] used, List<Integer> path,
                   List<List<Integer>> ans) {
    if (path.size() == nums.length) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;

        used[i] = true;
        path.add(nums[i]);
        dfsUniquePerm(nums, used, path, ans);
        path.remove(path.size() - 1);
        used[i] = false;
    }
}
```

- Pattern: Duplicate-aware permutation DFS
- Time: `O(n * n!)`
- Space: `O(n)`
- Trap: The duplicate skip condition is `!used[i - 1]`, not `used[i - 1]`.
- Interview line: "For equal values, I force them to be chosen in original sorted order."

---

### 40. Combination Sum II

```java
List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    List<List<Integer>> ans = new ArrayList<>();
    dfsComb2(0, target, candidates, new ArrayList<>(), ans);
    return ans;
}

void dfsComb2(int start, int remain, int[] nums, List<Integer> path,
              List<List<Integer>> ans) {
    if (remain == 0) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue;
        if (nums[i] > remain) break;

        path.add(nums[i]);
        dfsComb2(i + 1, remain - nums[i], nums, path, ans);
        path.remove(path.size() - 1);
    }
}
```

- Pattern: Pick once + duplicate skip
- Time: Exponential
- Space: `O(n)`
- Trap: Use `i + 1`, not `i`, because each candidate can be used once.
- Interview line: "Sorting enables duplicate skipping and early break."

---

### 131. Palindrome Partitioning

```java
List<List<String>> partition(String s) {
    List<List<String>> ans = new ArrayList<>();
    dfsPartition(0, s, new ArrayList<>(), ans);
    return ans;
}

void dfsPartition(int start, String s, List<String> path, List<List<String>> ans) {
    if (start == s.length()) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int end = start; end < s.length(); end++) {
        if (!isPal(s, start, end)) continue;
        path.add(s.substring(start, end + 1));
        dfsPartition(end + 1, s, path, ans);
        path.remove(path.size() - 1);
    }
}

boolean isPal(String s, int left, int right) {
    while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) return false;
    }
    return true;
}
```

- Pattern: Partition DFS
- Time: `O(n * 2^n)` with palindrome checks
- Space: `O(n)`
- Trap: Each recursive choice is a cut after a palindromic prefix.
- Interview line: "I try every palindromic prefix, then solve the suffix."

---

### 17. Letter Combinations of a Phone Number

```java
List<String> letterCombinations(String digits) {
    if (digits.isEmpty()) return new ArrayList<>();

    String[] map = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    List<String> ans = new ArrayList<>();
    dfsLetters(0, digits, map, new StringBuilder(), ans);
    return ans;
}

void dfsLetters(int index, String digits, String[] map,
                StringBuilder path, List<String> ans) {
    if (index == digits.length()) {
        ans.add(path.toString());
        return;
    }

    String letters = map[digits.charAt(index) - '0'];
    for (char ch : letters.toCharArray()) {
        path.append(ch);
        dfsLetters(index + 1, digits, map, path, ans);
        path.deleteCharAt(path.length() - 1);
    }
}
```

- Pattern: Choice tree
- Time: `O(4^n)`
- Space: `O(n)`
- Trap: Empty input should return an empty list, not `[""]`.
- Interview line: "Each digit contributes one choice level in the recursion tree."

---

## Graph and DSU Cards

### 695. Max Area of Island

```java
int maxAreaOfIsland(int[][] grid) {
    int best = 0;
    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == 1) best = Math.max(best, area(grid, r, c));
        }
    }
    return best;
}

int area(int[][] grid, int r, int c) {
    if (r < 0 || c < 0 || r == grid.length || c == grid[0].length) return 0;
    if (grid[r][c] == 0) return 0;

    grid[r][c] = 0;
    return 1 + area(grid, r + 1, c) + area(grid, r - 1, c)
            + area(grid, r, c + 1) + area(grid, r, c - 1);
}
```

- Pattern: Grid DFS component size
- Time: `O(rows * cols)`
- Space: `O(rows * cols)` worst-case recursion
- Trap: Mark visited before recursing to avoid cycles.
- Interview line: "Each island is a connected component; DFS returns its size."

---

### 417. Pacific Atlantic Water Flow

```java
List<List<Integer>> pacificAtlantic(int[][] heights) {
    int rows = heights.length;
    int cols = heights[0].length;
    boolean[][] pac = new boolean[rows][cols];
    boolean[][] atl = new boolean[rows][cols];

    for (int r = 0; r < rows; r++) {
        flow(heights, r, 0, pac, -1);
        flow(heights, r, cols - 1, atl, -1);
    }
    for (int c = 0; c < cols; c++) {
        flow(heights, 0, c, pac, -1);
        flow(heights, rows - 1, c, atl, -1);
    }

    List<List<Integer>> ans = new ArrayList<>();
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (pac[r][c] && atl[r][c]) ans.add(Arrays.asList(r, c));
        }
    }
    return ans;
}

void flow(int[][] h, int r, int c, boolean[][] seen, int prev) {
    if (r < 0 || c < 0 || r == h.length || c == h[0].length) return;
    if (seen[r][c] || h[r][c] < prev) return;

    seen[r][c] = true;
    flow(h, r + 1, c, seen, h[r][c]);
    flow(h, r - 1, c, seen, h[r][c]);
    flow(h, r, c + 1, seen, h[r][c]);
    flow(h, r, c - 1, seen, h[r][c]);
}
```

- Pattern: Reverse DFS from oceans
- Time: `O(rows * cols)`
- Space: `O(rows * cols)`
- Trap: Start from oceans and move uphill, not from every cell downhill.
- Interview line: "Reverse reachability avoids doing DFS from every cell."

---

### 994. Rotting Oranges

```java
int orangesRotting(int[][] grid) {
    Queue<int[]> queue = new ArrayDeque<>();
    int fresh = 0;

    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == 2) queue.offer(new int[] {r, c});
            if (grid[r][c] == 1) fresh++;
        }
    }

    int minutes = 0;
    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};

    while (!queue.isEmpty() && fresh > 0) {
        int size = queue.size();
        minutes++;

        for (int i = 0; i < size; i++) {
            int[] cur = queue.poll();
            for (int[] d : dirs) {
                int nr = cur[0] + d[0];
                int nc = cur[1] + d[1];
                if (nr < 0 || nc < 0 || nr == grid.length || nc == grid[0].length) continue;
                if (grid[nr][nc] != 1) continue;

                grid[nr][nc] = 2;
                fresh--;
                queue.offer(new int[] {nr, nc});
            }
        }
    }

    return fresh == 0 ? minutes : -1;
}
```

- Pattern: Multi-source BFS
- Time: `O(rows * cols)`
- Space: `O(rows * cols)`
- Trap: All initially rotten oranges start at minute zero.
- Interview line: "BFS levels represent minutes."

---

### 210. Course Schedule II

```java
int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());

    int[] indegree = new int[numCourses];
    for (int[] p : prerequisites) {
        graph.get(p[1]).add(p[0]);
        indegree[p[0]]++;
    }

    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < numCourses; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }

    int[] order = new int[numCourses];
    int index = 0;

    while (!queue.isEmpty()) {
        int course = queue.poll();
        order[index++] = course;

        for (int next : graph.get(course)) {
            if (--indegree[next] == 0) queue.offer(next);
        }
    }

    return index == numCourses ? order : new int[0];
}
```

- Pattern: Topological sort
- Time: `O(V + E)`
- Space: `O(V + E)`
- Trap: Edge direction is prerequisite -> course.
- Interview line: "If topo sort cannot consume all nodes, a cycle blocks completion."

---

### 684. Redundant Connection

```java
int[] findRedundantConnection(int[][] edges) {
    int n = edges.length;
    int[] parent = new int[n + 1];
    for (int i = 1; i <= n; i++) parent[i] = i;

    for (int[] edge : edges) {
        int a = find(parent, edge[0]);
        int b = find(parent, edge[1]);
        if (a == b) return edge;
        parent[a] = b;
    }

    return new int[0];
}

int find(int[] parent, int x) {
    if (parent[x] != x) parent[x] = find(parent, parent[x]);
    return parent[x];
}
```

- Pattern: DSU cycle detection
- Time: `O(E alpha(V))`
- Space: `O(V)`
- Trap: In an undirected graph, an edge is redundant if both endpoints are already connected.
- Interview line: "Union succeeds for tree edges and fails for the edge that creates a cycle."

---

### 743. Network Delay Time

```java
int networkDelayTime(int[][] times, int n, int k) {
    List<List<int[]>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) graph.add(new ArrayList<>());

    for (int[] t : times) graph.get(t[0]).add(new int[] {t[1], t[2]});

    int[] dist = new int[n + 1];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[k] = 0;

    PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
    pq.offer(new int[] {k, 0});

    while (!pq.isEmpty()) {
        int[] cur = pq.poll();
        int node = cur[0];
        int d = cur[1];
        if (d > dist[node]) continue;

        for (int[] edge : graph.get(node)) {
            int next = edge[0];
            int nd = d + edge[1];
            if (nd < dist[next]) {
                dist[next] = nd;
                pq.offer(new int[] {next, nd});
            }
        }
    }

    int ans = 0;
    for (int i = 1; i <= n; i++) ans = Math.max(ans, dist[i]);
    return ans == Integer.MAX_VALUE ? -1 : ans;
}
```

- Pattern: Dijkstra
- Time: `O((V + E) log V)`
- Space: `O(V + E)`
- Trap: Skip stale priority-queue entries.
- Interview line: "The answer is the largest shortest-path distance from the source."

---

### 1584. Min Cost to Connect All Points

```java
int minCostConnectPoints(int[][] points) {
    int n = points.length;
    boolean[] used = new boolean[n];
    int[] minDist = new int[n];
    Arrays.fill(minDist, Integer.MAX_VALUE);
    minDist[0] = 0;

    int cost = 0;
    for (int edges = 0; edges < n; edges++) {
        int u = -1;
        for (int i = 0; i < n; i++) {
            if (!used[i] && (u == -1 || minDist[i] < minDist[u])) u = i;
        }

        used[u] = true;
        cost += minDist[u];

        for (int v = 0; v < n; v++) {
            if (!used[v]) {
                int d = Math.abs(points[u][0] - points[v][0])
                        + Math.abs(points[u][1] - points[v][1]);
                minDist[v] = Math.min(minDist[v], d);
            }
        }
    }

    return cost;
}
```

- Pattern: Prim MST
- Time: `O(n^2)`
- Space: `O(n)`
- Trap: Building all edges costs `O(n^2)` memory; Prim can avoid that.
- Interview line: "This is a complete graph MST with Manhattan edge weights."

---

## Dynamic Programming Cards

### 70. Climbing Stairs

```java
int climbStairs(int n) {
    int a = 1;
    int b = 1;

    for (int i = 2; i <= n; i++) {
        int c = a + b;
        a = b;
        b = c;
    }

    return b;
}
```

- Pattern: 1D count ways
- Time: `O(n)`
- Space: `O(1)`
- Trap: `ways(0) = 1` makes the recurrence clean.
- Interview line: "The last move came from `n - 1` or `n - 2`."

---

### 198. House Robber

```java
int rob(int[] nums) {
    int prev2 = 0;
    int prev1 = 0;

    for (int num : nums) {
        int curr = Math.max(prev1, prev2 + num);
        prev2 = prev1;
        prev1 = curr;
    }

    return prev1;
}
```

- Pattern: Take/skip DP
- Time: `O(n)`
- Space: `O(1)`
- Trap: If you take current house, you must use `i - 2`.
- Interview line: "At each house, choose between skipping it or robbing it plus the best before previous."

---

### 213. House Robber II

```java
int robCircle(int[] nums) {
    if (nums.length == 1) return nums[0];
    return Math.max(robRange(nums, 0, nums.length - 2),
            robRange(nums, 1, nums.length - 1));
}

int robRange(int[] nums, int left, int right) {
    int prev2 = 0;
    int prev1 = 0;

    for (int i = left; i <= right; i++) {
        int curr = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = curr;
    }

    return prev1;
}
```

- Pattern: Circular take/skip
- Time: `O(n)`
- Space: `O(1)`
- Trap: First and last houses cannot both be used.
- Interview line: "Break the circle into two linear robber cases."

---

### 518. Coin Change II

```java
int change(int amount, int[] coins) {
    int[] dp = new int[amount + 1];
    dp[0] = 1;

    for (int coin : coins) {
        for (int a = coin; a <= amount; a++) {
            dp[a] += dp[a - coin];
        }
    }

    return dp[amount];
}
```

- Pattern: Count combinations
- Time: `O(amount * coins)`
- Space: `O(amount)`
- Trap: Coins outer loop counts combinations; amount outer loop counts ordered sequences.
- Interview line: "For each coin, I add ways that use this coin after smaller amounts are known."

---

### 300. Longest Increasing Subsequence

```java
int lengthOfLIS(int[] nums) {
    List<Integer> tails = new ArrayList<>();

    for (int num : nums) {
        int pos = Collections.binarySearch(tails, num);
        if (pos < 0) pos = -(pos + 1);

        if (pos == tails.size()) tails.add(num);
        else tails.set(pos, num);
    }

    return tails.size();
}
```

- Pattern: Patience sorting
- Time: `O(n log n)`
- Space: `O(n)`
- Trap: `tails` is not the actual subsequence; it stores best possible tails by length.
- Interview line: "Smaller tails are better because they leave more room for future numbers."

---

### 416. Partition Equal Subset Sum

```java
boolean canPartition(int[] nums) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % 2 == 1) return false;

    int target = sum / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;

    for (int num : nums) {
        for (int t = target; t >= num; t--) {
            dp[t] = dp[t] || dp[t - num];
        }
    }

    return dp[target];
}
```

- Pattern: 0/1 knapsack feasibility
- Time: `O(n * target)`
- Space: `O(target)`
- Trap: Traverse target backward so each number is used once.
- Interview line: "The problem becomes whether any subset sums to half of total sum."

---

### 494. Target Sum

```java
int findTargetSumWays(int[] nums, int target) {
    int sum = 0;
    for (int num : nums) sum += num;

    if (Math.abs(target) > sum || (sum + target) % 2 == 1) return 0;
    int subset = (sum + target) / 2;

    int[] dp = new int[subset + 1];
    dp[0] = 1;

    for (int num : nums) {
        for (int s = subset; s >= num; s--) {
            dp[s] += dp[s - num];
        }
    }

    return dp[subset];
}
```

- Pattern: Count subset transforms
- Time: `O(n * subset)`
- Space: `O(subset)`
- Trap: Algebra transforms `positive - negative = target` into subset sum.
- Interview line: "Choosing plus signs forms a subset with sum `(total + target) / 2`."

---

### 72. Edit Distance

```java
int minDistance(String a, String b) {
    int m = a.length();
    int n = b.length();
    int[][] dp = new int[m + 1][n + 1];

    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;

    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                        Math.min(dp[i - 1][j], dp[i][j - 1]));
            }
        }
    }

    return dp[m][n];
}
```

- Pattern: Two-string edit DP
- Time: `O(m * n)`
- Space: `O(m * n)`
- Trap: Replace uses diagonal; delete uses top; insert uses left.
- Interview line: "For mismatch, I try the three legal operations and take the cheapest."

---

### 115. Distinct Subsequences

```java
int numDistinct(String s, String t) {
    int m = s.length();
    int n = t.length();
    long[][] dp = new long[m + 1][n + 1];

    for (int i = 0; i <= m; i++) dp[i][0] = 1;

    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            dp[i][j] = dp[i - 1][j];
            if (s.charAt(i - 1) == t.charAt(j - 1)) {
                dp[i][j] += dp[i - 1][j - 1];
            }
        }
    }

    return (int) dp[m][n];
}
```

- Pattern: Two-string count DP
- Time: `O(m * n)`
- Space: `O(m * n)`
- Trap: Empty target has one way: choose nothing.
- Interview line: "For each character in `s`, I either skip it or use it if it matches the next needed target character."

---

### 62. Unique Paths

```java
int uniquePaths(int m, int n) {
    int[] dp = new int[n];
    Arrays.fill(dp, 1);

    for (int r = 1; r < m; r++) {
        for (int c = 1; c < n; c++) {
            dp[c] += dp[c - 1];
        }
    }

    return dp[n - 1];
}
```

- Pattern: Grid count DP
- Time: `O(m * n)`
- Space: `O(n)`
- Trap: First row and first column each have exactly one path.
- Interview line: "Each cell can be reached from top or left."

---

### 64. Minimum Path Sum

```java
int minPathSum(int[][] grid) {
    int rows = grid.length;
    int cols = grid[0].length;
    int[] dp = new int[cols];

    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (r == 0 && c == 0) {
                dp[c] = grid[r][c];
            } else if (r == 0) {
                dp[c] = dp[c - 1] + grid[r][c];
            } else if (c == 0) {
                dp[c] = dp[c] + grid[r][c];
            } else {
                dp[c] = Math.min(dp[c], dp[c - 1]) + grid[r][c];
            }
        }
    }

    return dp[cols - 1];
}
```

- Pattern: Grid min DP
- Time: `O(rows * cols)`
- Space: `O(cols)`
- Trap: Handle first row and first column separately.
- Interview line: "The cheapest path to a cell comes from the cheaper of top or left."

---

### 221. Maximal Square

```java
int maximalSquare(char[][] matrix) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    int[][] dp = new int[rows + 1][cols + 1];
    int best = 0;

    for (int r = 1; r <= rows; r++) {
        for (int c = 1; c <= cols; c++) {
            if (matrix[r - 1][c - 1] == '1') {
                dp[r][c] = 1 + Math.min(dp[r - 1][c - 1],
                        Math.min(dp[r - 1][c], dp[r][c - 1]));
                best = Math.max(best, dp[r][c]);
            }
        }
    }

    return best * best;
}
```

- Pattern: 2D local recurrence
- Time: `O(rows * cols)`
- Space: `O(rows * cols)`
- Trap: Return area, not side length.
- Interview line: "A square can grow only if top, left, and diagonal squares all support it."

---

### 647. Palindromic Substrings

```java
int countSubstrings(String s) {
    int count = 0;

    for (int center = 0; center < s.length(); center++) {
        count += expand(s, center, center);
        count += expand(s, center, center + 1);
    }

    return count;
}

int expand(String s, int left, int right) {
    int count = 0;
    while (left >= 0 && right < s.length()
            && s.charAt(left) == s.charAt(right)) {
        count++;
        left--;
        right++;
    }
    return count;
}
```

- Pattern: Expand around centers
- Time: `O(n^2)`
- Space: `O(1)`
- Trap: Count both odd and even centers.
- Interview line: "Every palindrome has a center, so I expand from all possible centers."

---

### 309. Best Time to Buy and Sell Stock With Cooldown

```java
int maxProfitCooldown(int[] prices) {
    int hold = -prices[0];
    int sold = 0;
    int rest = 0;

    for (int i = 1; i < prices.length; i++) {
        int prevSold = sold;
        sold = hold + prices[i];
        hold = Math.max(hold, rest - prices[i]);
        rest = Math.max(rest, prevSold);
    }

    return Math.max(sold, rest);
}
```

- Pattern: State-machine DP
- Time: `O(n)`
- Space: `O(1)`
- Trap: Buy uses `rest`, not yesterday's `sold`, because of cooldown.
- Interview line: "The states are holding, just sold, and resting."

---

### 714. Best Time to Buy and Sell Stock With Transaction Fee

```java
int maxProfitFee(int[] prices, int fee) {
    int hold = -prices[0];
    int cash = 0;

    for (int i = 1; i < prices.length; i++) {
        int oldCash = cash;
        cash = Math.max(cash, hold + prices[i] - fee);
        hold = Math.max(hold, oldCash - prices[i]);
    }

    return cash;
}
```

- Pattern: State-machine DP
- Time: `O(n)`
- Space: `O(1)`
- Trap: Apply the fee once per completed transaction, usually on sell.
- Interview line: "Cash means no stock held; hold means one stock held."

---

### 91. Decode Ways

```java
int numDecodings(String s) {
    if (s.charAt(0) == '0') return 0;

    int prev2 = 1;
    int prev1 = 1;

    for (int i = 1; i < s.length(); i++) {
        int curr = 0;

        if (s.charAt(i) != '0') curr += prev1;

        int two = Integer.parseInt(s.substring(i - 1, i + 1));
        if (two >= 10 && two <= 26) curr += prev2;

        prev2 = prev1;
        prev1 = curr;
    }

    return prev1;
}
```

- Pattern: 1D count DP
- Time: `O(n)`
- Space: `O(1)`
- Trap: `0` cannot stand alone; it must be part of `10` or `20`.
- Interview line: "At each index, I can decode one digit if valid and two digits if the pair is between 10 and 26."

---

**Back:** `02_Hot_Topic_Solved_Walkthroughs.md`
