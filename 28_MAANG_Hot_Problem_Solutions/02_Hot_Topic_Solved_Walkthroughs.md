# Hot Topic Solved Walkthroughs

> These are the first detailed solutions for the highest-signal MAANG topics: Recursion/Backtracking, Trees/BST, Graphs/DSU, and DP. Use this file as the style guide for adding the rest of the Hot 150.

---

## Common Java Models

```java
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}

class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<>();
    }

    public Node(int val) {
        this.val = val;
        neighbors = new ArrayList<>();
    }
}
```

---

## 78. Subsets

- Pattern: Backtracking, include/exclude
- Difficulty: Medium
- Company signal: Foundation for recursion trees

### Intuition

For every number, there are two choices: take it or do not take it. A subset is just one path through that decision tree.

### Key Idea

At each index, add the current path to the answer, then try extending it with every later number.

### Java Solution

```java
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> ans = new ArrayList<>();
    backtrack(0, nums, new ArrayList<>(), ans);
    return ans;
}

void backtrack(int start, int[] nums, List<Integer> path, List<List<Integer>> ans) {
    ans.add(new ArrayList<>(path));

    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);
        backtrack(i + 1, nums, path, ans);
        path.remove(path.size() - 1);
    }
}
```

### Complexity

- Time: `O(n * 2^n)` because there are `2^n` subsets and copying costs up to `n`
- Space: `O(n)` recursion depth, excluding output

### Common Mistakes

- Mistake: Add `path` directly to `ans`.
- Fix: Add `new ArrayList<>(path)` so later backtracking does not mutate saved answers.

### Interview Explanation

I treat each partial subset as a path in a decision tree. From a given start index, I can choose any later element, recurse, then undo the choice. I add the path at every node because every partial path is a valid subset. The increasing `start` index prevents reusing earlier elements and avoids duplicate orderings.

---

## 39. Combination Sum

- Pattern: Backtracking with reuse
- Difficulty: Medium
- Company signal: Classic pruning and candidate reuse

### Intuition

You are filling a basket until the remaining target becomes zero. Because a candidate can be reused, after choosing `candidates[i]`, recursion stays at `i`.

### Key Idea

Sort candidates, stop when the candidate exceeds the remaining target, and pass `i` instead of `i + 1` to allow reuse.

### Java Solution

```java
List<List<Integer>> combinationSum(int[] candidates, int target) {
    Arrays.sort(candidates);
    List<List<Integer>> ans = new ArrayList<>();
    dfs(0, target, candidates, new ArrayList<>(), ans);
    return ans;
}

void dfs(int start, int remain, int[] candidates,
         List<Integer> path, List<List<Integer>> ans) {
    if (remain == 0) {
        ans.add(new ArrayList<>(path));
        return;
    }

    for (int i = start; i < candidates.length; i++) {
        if (candidates[i] > remain) break;

        path.add(candidates[i]);
        dfs(i, remain - candidates[i], candidates, path, ans);
        path.remove(path.size() - 1);
    }
}
```

### Complexity

- Time: Exponential, roughly `O(branches^depth)`
- Space: `O(target / minCandidate)` recursion depth

### Common Mistakes

- Mistake: Recurse with `i + 1`, which forbids reuse.
- Fix: Recurse with `i` for Combination Sum I.

### Interview Explanation

I sort first so I can stop early when a number is larger than the remaining target. The recursion state is `(start, remain, path)`. Each recursive call chooses a candidate at or after `start`; using `i` again allows the same candidate to repeat. When `remain` reaches zero, the path is a valid combination.

---

## 79. Word Search

- Pattern: Grid backtracking
- Difficulty: Medium
- Company signal: Very common matrix recursion problem

### Intuition

Start from any cell that matches the first character. Then walk one character at a time through neighboring cells without using a cell twice in the same path.

### Key Idea

Mark the current cell as visited, explore four directions, then restore it before returning.

### Java Solution

```java
boolean exist(char[][] board, String word) {
    int rows = board.length;
    int cols = board[0].length;

    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (dfs(board, word, r, c, 0)) return true;
        }
    }

    return false;
}

boolean dfs(char[][] board, String word, int r, int c, int index) {
    if (index == word.length()) return true;
    if (r < 0 || c < 0 || r == board.length || c == board[0].length) return false;
    if (board[r][c] != word.charAt(index)) return false;

    char saved = board[r][c];
    board[r][c] = '#';

    boolean found = dfs(board, word, r + 1, c, index + 1)
            || dfs(board, word, r - 1, c, index + 1)
            || dfs(board, word, r, c + 1, index + 1)
            || dfs(board, word, r, c - 1, index + 1);

    board[r][c] = saved;
    return found;
}
```

### Complexity

- Time: `O(rows * cols * 4^L)`, where `L` is word length
- Space: `O(L)` recursion depth

### Common Mistakes

- Mistake: Forgetting to restore the cell after recursion.
- Fix: Always undo the visited mark before returning.

### Interview Explanation

I try every cell as a starting point. The DFS state is the cell and the current word index. If the cell matches, I temporarily mark it visited, search four directions for the next character, then restore the cell. This prevents reusing the same cell in one path while allowing it in other paths.

---

## 51. N-Queens

- Pattern: Backtracking with constraint sets
- Difficulty: Hard
- Company signal: Tests clean state design

### Intuition

Place one queen per row. For each row, try columns that are not attacked by previous queens.

### Key Idea

Track used columns and diagonals:

- main diagonal: `row - col`
- anti-diagonal: `row + col`

### Java Solution

```java
List<List<String>> solveNQueens(int n) {
    List<List<String>> ans = new ArrayList<>();
    char[][] board = new char[n][n];
    for (char[] row : board) Arrays.fill(row, '.');

    backtrack(0, n, board, new HashSet<>(), new HashSet<>(), new HashSet<>(), ans);
    return ans;
}

void backtrack(int row, int n, char[][] board,
               Set<Integer> cols, Set<Integer> diag, Set<Integer> anti,
               List<List<String>> ans) {
    if (row == n) {
        List<String> placement = new ArrayList<>();
        for (char[] r : board) placement.add(new String(r));
        ans.add(placement);
        return;
    }

    for (int col = 0; col < n; col++) {
        int d = row - col;
        int a = row + col;
        if (cols.contains(col) || diag.contains(d) || anti.contains(a)) continue;

        board[row][col] = 'Q';
        cols.add(col);
        diag.add(d);
        anti.add(a);

        backtrack(row + 1, n, board, cols, diag, anti, ans);

        board[row][col] = '.';
        cols.remove(col);
        diag.remove(d);
        anti.remove(a);
    }
}
```

### Complexity

- Time: `O(n!)` search space after pruning
- Space: `O(n)` recursion and sets, excluding output

### Common Mistakes

- Mistake: Scanning the board every time to check safety.
- Fix: Use column and diagonal sets for `O(1)` safety checks.

### Interview Explanation

Because only one queen can be placed in each row, I recurse row by row. I keep sets for attacked columns and diagonals so each placement can be validated in constant time. After placing a queen, I recurse to the next row and then undo all state. When `row == n`, the board is complete.

---

## 98. Validate Binary Search Tree

- Pattern: BST bounds recursion
- Difficulty: Medium
- Company signal: Extremely common BST invariant test

### Intuition

A BST is not valid just because each child compares correctly with its parent. Every node must fit inside a range created by all ancestors.

### Key Idea

Pass a valid `(low, high)` range down the tree.

### Java Solution

```java
boolean isValidBST(TreeNode root) {
    return valid(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

boolean valid(TreeNode node, long low, long high) {
    if (node == null) return true;
    if (node.val <= low || node.val >= high) return false;

    return valid(node.left, low, node.val)
            && valid(node.right, node.val, high);
}
```

### Complexity

- Time: `O(n)`
- Space: `O(h)`, where `h` is tree height

### Common Mistakes

- Mistake: Checking only `node.left.val < node.val < node.right.val`.
- Fix: Use ancestor bounds.

### Interview Explanation

Each node in a BST has a valid range. The root can be any value, but the left child must be below the root and the right child must be above it. As I recurse, I tighten the range using ancestor values. I use `long` bounds to avoid integer overflow edge cases.

---

## 230. Kth Smallest in BST

- Pattern: Inorder traversal
- Difficulty: Medium
- Company signal: Tests whether you know BST inorder is sorted

### Intuition

Inorder traversal of a BST visits values in ascending order. The kth visited node is the answer.

### Key Idea

Do iterative inorder and decrement `k` each time a node is popped.

### Java Solution

```java
int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;

    while (curr != null || !stack.isEmpty()) {
        while (curr != null) {
            stack.push(curr);
            curr = curr.left;
        }

        curr = stack.pop();
        k--;
        if (k == 0) return curr.val;

        curr = curr.right;
    }

    return -1;
}
```

### Complexity

- Time: `O(h + k)` average work until kth node
- Space: `O(h)`

### Common Mistakes

- Mistake: Traversing the whole tree when kth is near the beginning.
- Fix: Stop immediately when `k == 0`.

### Interview Explanation

I use the BST property: inorder traversal gives sorted order. I simulate recursion with a stack, pushing all left nodes first. Every pop gives the next smallest value. Once I pop the kth node, I return immediately.

---

## 236. Lowest Common Ancestor of a Binary Tree

- Pattern: Postorder recursion
- Difficulty: Medium
- Company signal: Core tree recursion problem

### Intuition

Ask each subtree: "Did you find `p` or `q`?" If both sides report a target, the current node is the split point and therefore the LCA.

### Key Idea

Return the found node upward. If both left and right are non-null, current root is the answer.

### Java Solution

```java
TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;

    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);

    if (left != null && right != null) return root;
    return left != null ? left : right;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(h)`

### Common Mistakes

- Mistake: Trying to use BST logic on a normal binary tree.
- Fix: For general binary trees, search both sides.

### Interview Explanation

The recursive function returns a target node if it finds one in the subtree. If the left and right subtrees both return non-null, then the current root is where the two targets split, so it is the LCA. If only one side returns a node, I bubble that node upward. This works without parent pointers.

---

## 124. Binary Tree Maximum Path Sum

- Pattern: Tree DP
- Difficulty: Hard
- Company signal: Very high

### Intuition

A path can pass through a node and use both children, but when returning to the parent, it can only continue through one side.

### Key Idea

Maintain a global best for "path that can split here." Return only "best one-way path upward."

### Java Solution

```java
int best;

int maxPathSum(TreeNode root) {
    best = Integer.MIN_VALUE;
    gain(root);
    return best;
}

int gain(TreeNode node) {
    if (node == null) return 0;

    int left = Math.max(0, gain(node.left));
    int right = Math.max(0, gain(node.right));

    best = Math.max(best, node.val + left + right);
    return node.val + Math.max(left, right);
}
```

### Complexity

- Time: `O(n)`
- Space: `O(h)`

### Common Mistakes

- Mistake: Returning `node + left + right` to the parent.
- Fix: Return only one side upward; use both sides only for global best.

### Interview Explanation

For each node, I compute the best gain from the left and right subtrees, ignoring negative gains. A complete path may pass through the node using both sides, so I update the global answer with `node + left + right`. But a parent path can only choose one branch, so I return `node + max(left, right)`.

---

## 200. Number of Islands

- Pattern: Grid DFS/BFS
- Difficulty: Medium
- Company signal: Most common graph warmup

### Intuition

Each island is one connected component of land cells. When you find land, flood-fill the whole island and count one.

### Key Idea

Scan every cell. On `'1'`, increment count and turn all connected `'1'` cells into `'0'`.

### Java Solution

```java
int numIslands(char[][] grid) {
    int count = 0;

    for (int r = 0; r < grid.length; r++) {
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[r][c] == '1') {
                count++;
                sink(grid, r, c);
            }
        }
    }

    return count;
}

void sink(char[][] grid, int r, int c) {
    if (r < 0 || c < 0 || r == grid.length || c == grid[0].length) return;
    if (grid[r][c] != '1') return;

    grid[r][c] = '0';
    sink(grid, r + 1, c);
    sink(grid, r - 1, c);
    sink(grid, r, c + 1);
    sink(grid, r, c - 1);
}
```

### Complexity

- Time: `O(rows * cols)`
- Space: `O(rows * cols)` worst-case recursion depth

### Common Mistakes

- Mistake: Counting every land cell.
- Fix: Count only the first land cell of each component, then sink the component.

### Interview Explanation

I model the grid as a graph where land cells are nodes connected in four directions. When I see a land cell, that starts one island. I DFS from it and mark every reachable land cell as water, so it will not be counted again. Each cell is visited at most once.

---

## 133. Clone Graph

- Pattern: Graph DFS with visited map
- Difficulty: Medium
- Company signal: Tests graph identity and cycles

### Intuition

To clone a graph with cycles, you must remember which original nodes already have clones.

### Key Idea

Map original node -> cloned node. Reuse clones when a node is seen again.

### Java Solution

```java
Node cloneGraph(Node node) {
    if (node == null) return null;
    return clone(node, new HashMap<>());
}

Node clone(Node node, Map<Node, Node> seen) {
    if (seen.containsKey(node)) return seen.get(node);

    Node copy = new Node(node.val);
    seen.put(node, copy);

    for (Node nei : node.neighbors) {
        copy.neighbors.add(clone(nei, seen));
    }

    return copy;
}
```

### Complexity

- Time: `O(V + E)`
- Space: `O(V)` for map and recursion

### Common Mistakes

- Mistake: Cloning neighbors before putting current node in the map.
- Fix: Store the clone immediately to break cycles.

### Interview Explanation

I do DFS from the given node. The map prevents infinite recursion on cycles and also ensures every original node has exactly one clone. After creating a copy, I recursively clone each neighbor and attach it to the copy's neighbor list. This visits every node and edge once.

---

## 207. Course Schedule

- Pattern: Directed cycle detection / topological sort
- Difficulty: Medium
- Company signal: Very common graph interview problem

### Intuition

You can finish all courses only if prerequisites do not contain a directed cycle.

### Key Idea

Use indegree topological sort. Repeatedly take courses with no remaining prerequisites.

### Java Solution

```java
boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());

    int[] indegree = new int[numCourses];
    for (int[] edge : prerequisites) {
        int course = edge[0];
        int prereq = edge[1];
        graph.get(prereq).add(course);
        indegree[course]++;
    }

    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < numCourses; i++) {
        if (indegree[i] == 0) queue.offer(i);
    }

    int taken = 0;
    while (!queue.isEmpty()) {
        int course = queue.poll();
        taken++;

        for (int next : graph.get(course)) {
            indegree[next]--;
            if (indegree[next] == 0) queue.offer(next);
        }
    }

    return taken == numCourses;
}
```

### Complexity

- Time: `O(V + E)`
- Space: `O(V + E)`

### Common Mistakes

- Mistake: Building the edge direction backward.
- Fix: If `[course, prereq]`, edge is `prereq -> course`.

### Interview Explanation

The prerequisite graph must be a DAG. I build edges from prerequisite to course and count indegrees. Courses with indegree zero can be taken immediately. Every time I take one, I reduce indegrees of dependent courses. If I can take all courses, there is no cycle.

---

## 721. Accounts Merge

- Pattern: DSU grouping
- Difficulty: Medium
- Company signal: Strong union-find modeling problem

### Intuition

Emails connect accounts. If two accounts share an email, they belong to the same person.

### Key Idea

Union emails that appear in the same account, then group emails by their DSU root.

### Java Solution

```java
List<List<String>> accountsMerge(List<List<String>> accounts) {
    Map<String, String> parent = new HashMap<>();
    Map<String, String> owner = new HashMap<>();

    for (List<String> account : accounts) {
        String name = account.get(0);
        String firstEmail = account.get(1);

        for (int i = 1; i < account.size(); i++) {
            String email = account.get(i);
            parent.putIfAbsent(email, email);
            owner.put(email, name);
            union(firstEmail, email, parent);
        }
    }

    Map<String, TreeSet<String>> groups = new HashMap<>();
    for (String email : parent.keySet()) {
        String root = find(email, parent);
        groups.computeIfAbsent(root, k -> new TreeSet<>()).add(email);
    }

    List<List<String>> ans = new ArrayList<>();
    for (String root : groups.keySet()) {
        List<String> merged = new ArrayList<>();
        merged.add(owner.get(root));
        merged.addAll(groups.get(root));
        ans.add(merged);
    }

    return ans;
}

String find(String x, Map<String, String> parent) {
    if (!parent.get(x).equals(x)) {
        parent.put(x, find(parent.get(x), parent));
    }
    return parent.get(x);
}

void union(String a, String b, Map<String, String> parent) {
    String rootA = find(a, parent);
    String rootB = find(b, parent);
    if (!rootA.equals(rootB)) parent.put(rootB, rootA);
}
```

### Complexity

- Time: `O(E log E)` because each group uses sorted emails
- Space: `O(E)`, where `E` is number of emails

### Common Mistakes

- Mistake: Unioning account names instead of emails.
- Fix: Emails are the unique graph nodes; names are labels.

### Interview Explanation

I model every email as a DSU node. Emails in the same account are unioned because they belong to the same person. After all unions, emails with the same root are one merged account. I use a `TreeSet` to output emails sorted lexicographically.

---

## 322. Coin Change

- Pattern: Minimum path to target DP
- Difficulty: Medium
- Company signal: Core DP pattern

### Intuition

To make amount `a`, try taking one last coin. If the last coin is `coin`, the previous state was `a - coin`.

### Key Idea

`dp[a] = minimum coins needed to make amount a`.

### Java Solution

```java
int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);
    dp[0] = 0;

    for (int a = 1; a <= amount; a++) {
        for (int coin : coins) {
            if (a >= coin) {
                dp[a] = Math.min(dp[a], dp[a - coin] + 1);
            }
        }
    }

    return dp[amount] > amount ? -1 : dp[amount];
}
```

### Complexity

- Time: `O(amount * numberOfCoins)`
- Space: `O(amount)`

### Common Mistakes

- Mistake: Initializing `dp` with zero.
- Fix: Use an impossible sentinel like `amount + 1`.

### Interview Explanation

The state `dp[a]` means the fewest coins to make amount `a`. For every amount, I try each coin as the final coin and transition from `dp[a - coin]`. The base case is `dp[0] = 0` because zero coins make amount zero. If the final state is still impossible, I return `-1`.

---

## 1143. Longest Common Subsequence

- Pattern: Two-string DP
- Difficulty: Medium
- Company signal: Foundation for string DP

### Intuition

Compare prefixes. If the last characters match, they can extend the best subsequence from the smaller prefixes.

### Key Idea

`dp[i][j] = LCS length using first i chars of a and first j chars of b`.

### Java Solution

```java
int longestCommonSubsequence(String a, String b) {
    int m = a.length();
    int n = b.length();
    int[][] dp = new int[m + 1][n + 1];

    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    return dp[m][n];
}
```

### Complexity

- Time: `O(m * n)`
- Space: `O(m * n)`

### Common Mistakes

- Mistake: Using substring logic and requiring contiguity.
- Fix: LCS is subsequence, so skipping characters is allowed.

### Interview Explanation

I define `dp[i][j]` as the LCS length for the first `i` and `j` characters. If the current characters match, I extend the diagonal answer. If they do not match, I skip one character from either string and take the better result. The extra row and column represent empty prefixes.

---

## 139. Word Break

- Pattern: Prefix DP
- Difficulty: Medium
- Company signal: Common DP/string problem

### Intuition

The prefix `s[0..i)` is breakable if there is a split `j` where the prefix before `j` is breakable and `s[j..i)` is a dictionary word.

### Key Idea

`dp[i] = whether first i characters can be segmented`.

### Java Solution

```java
boolean wordBreak(String s, List<String> wordDict) {
    Set<String> words = new HashSet<>(wordDict);
    boolean[] dp = new boolean[s.length() + 1];
    dp[0] = true;

    for (int i = 1; i <= s.length(); i++) {
        for (int j = 0; j < i; j++) {
            if (dp[j] && words.contains(s.substring(j, i))) {
                dp[i] = true;
                break;
            }
        }
    }

    return dp[s.length()];
}
```

### Complexity

- Time: `O(n^3)` in Java if substring copying is counted, often discussed as `O(n^2 * L)`
- Space: `O(n + dictionarySize)`

### Common Mistakes

- Mistake: Greedily taking the first matching word.
- Fix: Use DP because an early word can block a valid later segmentation.

### Interview Explanation

I use `dp[i]` to mean the prefix ending before index `i` can be segmented. For each endpoint, I try every previous split. If the left prefix is valid and the right substring is in the dictionary, then this prefix is valid. The base case is the empty string, `dp[0] = true`.

---

## 312. Burst Balloons

- Pattern: Interval DP
- Difficulty: Hard
- Company signal: Elite DP pattern

### Intuition

Thinking about the first balloon to burst is messy because neighbors keep changing. Think about the last balloon to burst inside an interval instead.

### Key Idea

`dp[left][right] = max coins from bursting all balloons between left and right`.

### Java Solution

```java
int maxCoins(int[] original) {
    int n = original.length;
    int[] nums = new int[n + 2];
    nums[0] = 1;
    nums[n + 1] = 1;

    for (int i = 0; i < n; i++) {
        nums[i + 1] = original[i];
    }

    int[][] dp = new int[n + 2][n + 2];

    for (int len = 1; len <= n; len++) {
        for (int left = 1; left + len - 1 <= n; left++) {
            int right = left + len - 1;

            for (int last = left; last <= right; last++) {
                int coins = nums[left - 1] * nums[last] * nums[right + 1];
                coins += dp[left][last - 1] + dp[last + 1][right];
                dp[left][right] = Math.max(dp[left][right], coins);
            }
        }
    }

    return dp[1][n];
}
```

### Complexity

- Time: `O(n^3)`
- Space: `O(n^2)`

### Common Mistakes

- Mistake: Trying to simulate popping from left to right.
- Fix: Pick the last balloon in each interval so boundaries stay fixed.

### Interview Explanation

I add virtual balloons of value `1` at both ends. For every interval, I try each balloon as the last one burst in that interval. If `last` is the final balloon, the left and right subintervals are independent and already solved. I fill intervals by increasing length so smaller intervals are available first.

---

## How To Add The Next Solutions

For every queued problem in `01_Hot_150_Index.md`, copy this compact checklist:

```md
## <#>. <Problem>

- Pattern:
- Difficulty:
- Company signal:

### Intuition
### Key Idea
### Java Solution
### Complexity
### Common Mistakes
### Interview Explanation
```

Add solutions in this priority order:

1. All Detailed-status hot-topic problems are done first.
2. Fill remaining DP and Graph queued problems.
3. Fill Trees/BST and Recursion/Backtracking.
4. Fill Arrays, Sliding Window, Stack, Linked List, Heap.
5. Fill Hot 200 extension problems.

---

**Back:** `01_Hot_150_Index.md`
