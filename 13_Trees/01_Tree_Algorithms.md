# Section 13 — Tree Algorithms (Deep Dive)

---

## 1. What Problem Does This Solve?

Trees model hierarchical data — file systems, organization charts, decision trees, parse trees. Almost all tree algorithms reduce to one of two operations:
1. **Traverse** every node (DFS/BFS)
2. **Compute a value** at each node based on its children (recursive decomposition)

The elegant truth: most tree problems are solved by asking "what do I need from the left subtree and right subtree to compute the answer at the current node?"

---

## 2. Beginner-Friendly Intuition

A binary tree is a family tree — each person (node) has at most two children. To find the height of your family tree, you ask each of your children for their subtree heights, take the max, and add 1. Each child does the same recursively. The base case: a person with no children has height 0.

This "ask children, combine, return" pattern solves 90% of tree problems.

---

## 3. Real-World Analogy

**Decision tree for medical diagnosis:** Root = first symptom check. Left child = "symptom present," right child = "absent." Each leaf = final diagnosis. Traversing the tree = following a diagnostic path.

**Company org chart:** CEO at root, VPs as children, managers below, etc. "Total salary of team under manager X" = sum of salary at X + recursively sum all subtrees below X.

---

## 4. Core Concept

### Three DFS Traversal Orders

| Order | When Root is Processed | Output for [1, L, R] |
|-------|----------------------|---------------------|
| **Preorder** | Before children (Root → L → R) | Root first |
| **Inorder** | Between children (L → Root → R) | Sorted order for BST |
| **Postorder** | After children (L → R → Root) | Children processed before parent |

**Key insight:** Inorder of a BST gives nodes in sorted (ascending) order.

### The Universal Recursion Pattern for Trees
```
solve(node):
    if node == null: return base_value
    leftResult = solve(node.left)
    rightResult = solve(node.right)
    return combine(leftResult, rightResult, node.val)
```

---

## 5. Pattern Recognition Signals

Use Tree algorithms when:
```
"Height / depth of tree"
"Diameter / longest path"
"Check if balanced"
"Lowest Common Ancestor (LCA)"
"Path sum" / "all paths"
"Level order traversal"
"Serialize / deserialize"
"BST validation" / "BST search/insert/delete"
"Invert / mirror tree"
"Same tree / symmetric tree"
"Count nodes" / "Count leaves"
"Vertical/horizontal order traversal"
```

---

## 6. Step-by-Step Algorithm

### DFS Traversals (Iterative + Recursive)
```
Preorder (Root, Left, Right):
    visit(root)
    preorder(root.left)
    preorder(root.right)

Inorder (Left, Root, Right):
    inorder(root.left)
    visit(root)
    inorder(root.right)

Postorder (Left, Right, Root):
    postorder(root.left)
    postorder(root.right)
    visit(root)
```

### BFS Level Order
```
queue.add(root)
while queue not empty:
    size = queue.size()  ← nodes at current level
    for i from 0 to size-1:
        node = queue.poll()
        process(node)
        if node.left != null: queue.add(node.left)
        if node.right != null: queue.add(node.right)
```

### Lowest Common Ancestor (LCA)
```
lca(node, p, q):
    if node == null OR node == p OR node == q: return node
    left = lca(node.left, p, q)
    right = lca(node.right, p, q)
    if left != null AND right != null: return node  ← p, q in different subtrees
    return left if left != null else right            ← both in same subtree
```

---

## 7. Dry Run with Example

### Example 1: Tree Height

**Tree:**
```
        1
       / \
      2   3
     / \
    4   5
```

```
height(1):
  height(2):
    height(4): height(null)=0, height(null)=0 → return 1
    height(5): height(null)=0, height(null)=0 → return 1
    return max(1,1) + 1 = 2
  height(3): return 1
  return max(2, 1) + 1 = 3

Height = 3 ✓
```

### Example 2: Inorder Traversal (BST gives sorted output)

**BST:**
```
    4
   / \
  2   6
 / \ / \
1  3 5  7
```

```
inorder(4):
  inorder(2):
    inorder(1): inorder(null), visit 1, inorder(null) → output: 1
    visit 2 → output: 1, 2
    inorder(3): visit 3 → output: 1, 2, 3
  visit 4 → output: 1, 2, 3, 4
  inorder(6):
    inorder(5): visit 5 → output: ..., 4, 5
    visit 6 → output: ..., 5, 6
    inorder(7): visit 7 → output: ..., 6, 7

Final: [1, 2, 3, 4, 5, 6, 7] ✓ (BST inorder = sorted)
```

### Example 3: LCA of nodes 3 and 5

**Tree:** (same as Example 2 above, but general binary tree)

```
lca(4, 3, 5):
  left = lca(2, 3, 5):
    left = lca(1, 3, 5): returns null (3 not here, 5 not here)
    right = lca(3, 3, 5): node==3 → return 3
    left=null, right=3 → return 3
  right = lca(6, 3, 5):
    left = lca(5, 3, 5): node==5 → return 5
    right = lca(7, 3, 5): returns null
    left=5, right=null → return 5
  left=3, right=5 → BOTH non-null → return node 4

LCA(3, 5) = 4 ✓
```

---

## 8. Code Implementation

### DFS Traversals

```java
// Inorder (recursive)
void inorder(TreeNode node, List<Integer> result) {
    if (node == null) return;
    inorder(node.left, result);
    result.add(node.val);
    inorder(node.right, result);
}

// Inorder (iterative with explicit stack)
List<Integer> inorderIterative(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode curr = root;
    while (curr != null || !stack.isEmpty()) {
        while (curr != null) { stack.push(curr); curr = curr.left; } // go left
        curr = stack.pop();
        result.add(curr.val); // visit
        curr = curr.right;    // go right
    }
    return result;
}

// Preorder (iterative)
List<Integer> preorderIterative(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        result.add(node.val);         // visit before children
        if (node.right != null) stack.push(node.right); // right first (LIFO)
        if (node.left != null) stack.push(node.left);
    }
    return result;
}
```

### BFS Level Order

```java
List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    Queue<TreeNode> queue = new LinkedList<>();
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
        result.add(level);
    }
    return result;
}
```

### Maximum Depth

```java
int maxDepth(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
}
```

### Diameter of Binary Tree

```java
int diameter = 0;

int diameterOfBinaryTree(TreeNode root) {
    height(root);
    return diameter;
}

int height(TreeNode node) {
    if (node == null) return 0;
    int left = height(node.left);
    int right = height(node.right);
    diameter = Math.max(diameter, left + right); // path through this node
    return 1 + Math.max(left, right);
}
```

### Lowest Common Ancestor

```java
TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root; // p in one subtree, q in other
    return left != null ? left : right;             // both in same subtree
}
```

### BST Validation

```java
boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

boolean validate(TreeNode node, long min, long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return validate(node.left, min, node.val) && // left subtree: max = current
           validate(node.right, node.val, max);  // right subtree: min = current
}
```

### Serialize and Deserialize Binary Tree

```java
String serialize(TreeNode root) {
    if (root == null) return "null";
    return root.val + "," + serialize(root.left) + "," + serialize(root.right);
}

TreeNode deserialize(String data) {
    Queue<String> tokens = new LinkedList<>(Arrays.asList(data.split(",")));
    return buildTree(tokens);
}

TreeNode buildTree(Queue<String> tokens) {
    String val = tokens.poll();
    if (val.equals("null")) return null;
    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left = buildTree(tokens);
    node.right = buildTree(tokens);
    return node;
}
```

### Path Sum II (All Paths)

```java
List<List<Integer>> pathSum(TreeNode root, int targetSum) {
    List<List<Integer>> result = new ArrayList<>();
    dfs(root, targetSum, new ArrayList<>(), result);
    return result;
}

void dfs(TreeNode node, int remaining, List<Integer> path, List<List<Integer>> result) {
    if (node == null) return;
    path.add(node.val);
    if (node.left == null && node.right == null && remaining == node.val)
        result.add(new ArrayList<>(path)); // leaf with correct sum
    dfs(node.left, remaining - node.val, path, result);
    dfs(node.right, remaining - node.val, path, result);
    path.remove(path.size() - 1); // backtrack
}
```

---

## 9. Time Complexity

| Operation | Complexity | Reason |
|-----------|-----------|--------|
| Any traversal | O(n) | Visit every node exactly once |
| Height / depth | O(n) | Visit every node |
| Diameter | O(n) | Single postorder traversal |
| LCA | O(n) | Single DFS |
| BST search | O(h) | h = height = O(log n) balanced, O(n) worst |
| BST insert | O(h) | Find correct position |
| Level order | O(n) | BFS visits every node |
| Serialize | O(n) | Visit every node |

---

## 10. Space Complexity

| Operation | Space | Reason |
|-----------|-------|--------|
| Recursive DFS | O(h) | Call stack depth = height |
| Iterative DFS | O(h) | Explicit stack holds at most h nodes |
| BFS Level Order | O(w) | Queue holds widest level; worst O(n/2) = O(n) |
| Balanced tree | O(log n) | h = log n |
| Skewed tree | O(n) | h = n |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Empty tree (root = null) | Return 0/null/false at the start |
| Single node | left=null, right=null → base cases handle it |
| Skewed tree (like linked list) | Algorithms still O(n), but recursion depth = n |
| BST with duplicate values | Typical BST doesn't allow; clarify in interview |
| p or q not in tree (LCA) | Algorithm returns null if not found |
| Negative values in path sum | Can't prune early; must explore all paths |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Inorder iterative — wrong pointer management
while (!stack.isEmpty()) { // WRONG: curr can be null while stack is non-empty
// CORRECT:
while (curr != null || !stack.isEmpty()) { ... }

// MISTAKE 2: BST validation using only local parent check
// Each node must satisfy constraints from ALL ancestors, not just direct parent
// WRONG: just check node.left.val < node.val && node.right.val > node.val
// CORRECT: pass min/max bounds down the recursion

// MISTAKE 3: Diameter — computing it as max depth, not max path through node
// The diameter CAN bypass the root entirely
// Track: diameter = max(diameter, leftHeight + rightHeight) at EVERY node

// MISTAKE 4: Forgetting to check if both left and right are non-null in LCA
if (left != null) return left; // WRONG if both are non-null (should return root)
if (left != null && right != null) return root; // CORRECT

// MISTAKE 5: Not accounting for integer overflow in BST validation
// Node value could be Integer.MIN_VALUE or MAX_VALUE
// CORRECT: use Long.MIN_VALUE and Long.MAX_VALUE as bounds
```

---

## 13. Interview-Level Explanation

**Q: "How is tree diameter computed, and why does a simple height-based approach fail?"**

> "The diameter is the longest path between any two nodes. This path might not pass through the root. The correct approach is a single postorder traversal where at each node, I compute the left and right subtree heights and update a global maximum with `left + right` (the path through that node). The function returns the height — used by the parent — but the actual answer is tracked via the running maximum."

**Q: "Why does inorder traversal of a BST give sorted output?"**

> "A BST maintains the invariant: all left subtree values < root < all right subtree values. Inorder visits the left subtree first (all values smaller than root), then the root itself, then the right subtree (all values larger). Since this invariant applies at every node recursively, the traversal yields all values in ascending order."

---

## 14. Real-World Use Cases

| Application | Tree Algorithm |
|------------|---------------|
| **File systems** | Directory hierarchy traversal |
| **HTML/XML parsing** | DOM tree traversal |
| **Database indexes** | B-trees for range queries |
| **Compiler design** | AST (Abstract Syntax Tree) traversal |
| **Decision making** | Decision tree traversal in ML |
| **DNS resolution** | Tree of domain zones |
| **JSON/YAML parsing** | Nested structure as tree |

---

## 15. Variations of This Pattern

| Variation | Key Technique | Example |
|-----------|--------------|---------|
| Inorder / Preorder / Postorder | DFS order | Tree traversals |
| Level order | BFS | Level Order Traversal |
| Height/depth | Postorder recursion | Maximum Depth |
| Diameter | Postorder + global max | Diameter of Binary Tree |
| LCA (general) | Two-phase DFS | LCA of Binary Tree |
| LCA (BST) | Exploit BST property | LCA of BST |
| BST validation | Pass min/max bounds | Validate BST |
| Path sum | DFS + backtrack | Path Sum II |
| Serialize/Deserialize | Preorder + queue | Serialize Tree |
| Morris Traversal | O(1) space inorder | Inorder without stack |

---

## 16. Practice Problems

### Easy — Core Tree Operations
1. **Maximum Depth of Binary Tree** (LeetCode #104)
   - *Task:* Find the maximum depth.
   - *Hint:* `1 + max(depth(left), depth(right))`. Base: null → 0.

2. **Invert Binary Tree** (LeetCode #226)
   - *Task:* Mirror the tree (swap left/right at every node).
   - *Hint:* Swap children, then recursively invert both.

3. **Symmetric Tree** (LeetCode #101)
   - *Task:* Is the tree a mirror of itself?
   - *Hint:* Check `isMirror(root.left, root.right)` recursively.

### Medium — Classic Tree Problems
1. **Binary Tree Level Order Traversal** (LeetCode #102)
   - *Task:* Return values grouped by level.
   - *Hint:* BFS with inner for-loop processing all nodes at current level.

2. **Path Sum II** (LeetCode #113)
   - *Task:* All root-to-leaf paths summing to target.
   - *Hint:* DFS + backtracking. Add to result only at leaf with correct sum.

3. **Diameter of Binary Tree** (LeetCode #543)
   - *Task:* Longest path between any two nodes.
   - *Hint:* Postorder: update `diameter = max(diameter, left + right)` at each node.

4. **Lowest Common Ancestor** (LeetCode #236)
   - *Task:* Find LCA of two nodes in a binary tree.
   - *Hint:* If both non-null from left/right subtrees, current node is LCA.

5. **Validate Binary Search Tree** (LeetCode #98)
   - *Task:* Check if tree is a valid BST.
   - *Hint:* Pass `(min, max)` bounds and validate `min < node.val < max`.

### Hard — Advanced Tree Patterns
1. **Serialize and Deserialize Binary Tree** (LeetCode #297)
   - *Task:* Convert tree to string and back.
   - *Hint:* Preorder DFS; use "null" for null nodes; parse with queue of tokens.

2. **Binary Tree Maximum Path Sum** (LeetCode #124)
   - *Task:* Path with maximum sum (path can start/end at any node).
   - *Hint:* At each node: `maxPath = max(maxPath, left + right + node.val)`. Return `node.val + max(left, right, 0)`.

3. **Recover Binary Search Tree** (LeetCode #99)
   - *Task:* Two nodes in BST are swapped; find and swap them back.
   - *Hint:* Inorder traversal. Find the two nodes that are out of order.

---

## 17. How to Know You Have Mastered Tree Algorithms

You have mastered this topic when you can:
- [ ] Write all three recursive DFS traversals from memory
- [ ] Write iterative inorder traversal using an explicit stack
- [ ] Explain why inorder of a BST is sorted
- [ ] Compute tree height, diameter, and balance check in single passes
- [ ] Implement LCA correctly for both general trees and BSTs
- [ ] Validate a BST using range propagation (not local comparison)
- [ ] Serialize and deserialize a tree using preorder traversal
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Preorder, Inorder, and Postorder traversal of a single node returns what in each case?

2. A balanced binary tree with n nodes has height O(log n). What is the height of a completely skewed tree with n nodes?

3. For LCA: if `p = root.left` and `q = root.right`, the LCA is the root. Trace through the algorithm to confirm.

4. Why does BST validation need to pass bounds (`min, max`) rather than just comparing parent and child values?

5. What does the diameter being `left + right` at each node represent geometrically?

6. In Path Sum II, you call `path.remove(path.size() - 1)` after both recursive calls. Why after both and not after each?

7. Serialization uses "null" as a sentinel for null nodes. Why is this necessary?

8. BFS level order traversal uses an inner `for (int i = 0; i < size; i++)` loop. What happens if you remove this and just `queue.poll()` directly?

> **Answers:**
> 1. All three return the same single value — the traversal order only matters for multiple nodes.
> 2. O(n). A completely skewed tree is essentially a linked list.
> 3. `lca(root, L, R)`: left = lca(root.left, L, R) = L (found). right = lca(root.right, L, R) = R (found). Both non-null → return root. ✓
> 4. Example: A node could have a larger value than its parent's left child limit but still violate an ancestor's constraint. E.g., root=5, left=3, left.right=7 — locally 7 > 3 (valid), but globally 7 > 5 (invalid for left subtree).
> 5. It's the number of edges in the path going down left subtree, through this node, and up the right subtree. The longest path passing through this node.
> 6. Both recursive calls share the same `path` list. You add the node, recurse left, recurse right, then remove the node (undo for the parent's perspective). If you removed after each call, you'd remove the current node twice.
> 7. Without null markers, you can't distinguish between a node with no children and a node with children not yet listed. The null sentinels mark the exact tree shape.
> 8. You'd lose level information — you couldn't group nodes by level since the queue mixes all levels together.

---

**Next →** `../15_Graphs/01_Graph_Algorithms.md`
1. **What do I return from each node?** (bottom-up thinking)
2. **What do I pass to children?** (top-down thinking)
3. **Is the answer at a node, or propagated up?**

---

## Pattern 1: Diameter of Binary Tree

**Diameter** = longest path between any two nodes (may not go through root).

```java
int maxDiam = 0;

int diameterOfBinaryTree(TreeNode root) {
    height(root);
    return maxDiam;
}

int height(TreeNode node) {
    if (node == null) return 0;
    int leftH = height(node.left);
    int rightH = height(node.right);
    maxDiam = Math.max(maxDiam, leftH + rightH);  // diameter through this node
    return 1 + Math.max(leftH, rightH);           // height to return upward
}
```

**Key insight:** At each node, diameter through it = leftHeight + rightHeight.

---

## Pattern 2: Maximum Path Sum

```java
int maxSum = Integer.MIN_VALUE;

int maxPathSum(TreeNode root) {
    gainFromNode(root);
    return maxSum;
}

int gainFromNode(TreeNode node) {
    if (node == null) return 0;
    int leftGain = Math.max(0, gainFromNode(node.left));   // ignore negative paths
    int rightGain = Math.max(0, gainFromNode(node.right));
    maxSum = Math.max(maxSum, node.val + leftGain + rightGain);  // path through node
    return node.val + Math.max(leftGain, rightGain);  // only ONE side upward
}
```

---

## Pattern 3: Lowest Common Ancestor (LCA)

```java
TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lca(root.left, p, q);
    TreeNode right = lca(root.right, p, q);
    // If both non-null, this is the split point = LCA
    if (left != null && right != null) return root;
    return left != null ? left : right;
}
```

---

## Pattern 4: Serialize & Deserialize Binary Tree

```java
// Preorder serialization
String serialize(TreeNode root) {
    if (root == null) return "null,";
    return root.val + "," + serialize(root.left) + serialize(root.right);
}

TreeNode deserialize(String data) {
    Queue<String> tokens = new LinkedList<>(Arrays.asList(data.split(",")));
    return buildTree(tokens);
}

TreeNode buildTree(Queue<String> tokens) {
    String val = tokens.poll();
    if (val.equals("null")) return null;
    TreeNode node = new TreeNode(Integer.parseInt(val));
    node.left = buildTree(tokens);
    node.right = buildTree(tokens);
    return node;
}
```

---

## Pattern 5: Construct Tree from Traversals

```java
// Construct from Preorder + Inorder
TreeNode buildFromPreIn(int[] preorder, int[] inorder) {
    Map<Integer, Integer> inMap = new HashMap<>();
    for (int i = 0; i < inorder.length; i++) inMap.put(inorder[i], i);
    return build(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1, inMap);
}

TreeNode build(int[] pre, int preL, int preR, int[] in, int inL, int inR, Map<Integer,Integer> inMap) {
    if (preL > preR) return null;
    TreeNode root = new TreeNode(pre[preL]);
    int mid = inMap.get(pre[preL]);
    int leftSize = mid - inL;
    root.left = build(pre, preL+1, preL+leftSize, in, inL, mid-1, inMap);
    root.right = build(pre, preL+leftSize+1, preR, in, mid+1, inR, inMap);
    return root;
}
```

---

## Pattern 6: Vertical Order Traversal

```java
List<List<Integer>> verticalOrder(TreeNode root) {
    Map<Integer, List<int[]>> colMap = new TreeMap<>(); // sorted by column
    Queue<int[]> queue = new LinkedList<>(); // [node, row, col]
    // We need to carry node reference — use Object[] or a helper class

    // Standard approach: DFS with (node, row, col)
    dfs(root, 0, 0, colMap);

    List<List<Integer>> result = new ArrayList<>();
    for (List<int[]> col : colMap.values()) {
        col.sort((a, b) -> a[0] != b[0] ? a[0]-b[0] : a[1]-b[1]); // sort by row, then val
        List<Integer> vals = new ArrayList<>();
        for (int[] entry : col) vals.add(entry[1]);
        result.add(vals);
    }
    return result;
}

void dfs(TreeNode node, int row, int col, Map<Integer, List<int[]>> map) {
    if (node == null) return;
    map.computeIfAbsent(col, k -> new ArrayList<>()).add(new int[]{row, node.val});
    dfs(node.left, row+1, col-1, map);
    dfs(node.right, row+1, col+1, map);
}
```

---

## Pattern 7: Top View & Bottom View

```java
// Top View: for each column, the first (topmost) node seen in BFS
Map<Integer, Integer> topView(TreeNode root) {
    Map<Integer, Integer> result = new TreeMap<>();
    Queue<Object[]> queue = new LinkedList<>(); // [node, col]
    queue.offer(new Object[]{root, 0});
    while (!queue.isEmpty()) {
        Object[] curr = queue.poll();
        TreeNode node = (TreeNode) curr[0];
        int col = (int) curr[1];
        result.putIfAbsent(col, node.val);  // only first occurrence
        if (node.left != null) queue.offer(new Object[]{node.left, col-1});
        if (node.right != null) queue.offer(new Object[]{node.right, col+1});
    }
    return result;
}
```

---

## Pattern 8: Path Sum Problems

```java
// All root-to-leaf paths with target sum
List<List<Integer>> pathSum(TreeNode root, int target) {
    List<List<Integer>> result = new ArrayList<>();
    dfs(root, target, new ArrayList<>(), result);
    return result;
}

void dfs(TreeNode node, int remaining, List<Integer> path, List<List<Integer>> result) {
    if (node == null) return;
    path.add(node.val);
    if (node.left == null && node.right == null && remaining == node.val) {
        result.add(new ArrayList<>(path));
    }
    dfs(node.left, remaining - node.val, path, result);
    dfs(node.right, remaining - node.val, path, result);
    path.remove(path.size() - 1);  // backtrack
}
```

---

## Pattern 9: BST Operations

```java
// Kth Smallest in BST (inorder = sorted)
int kthSmallest(TreeNode root, int k) {
    int[] counter = {k, -1};
    inorder(root, counter);
    return counter[1];
}

void inorder(TreeNode node, int[] counter) {
    if (node == null || counter[0] == 0) return;
    inorder(node.left, counter);
    if (--counter[0] == 0) counter[1] = node.val;
    inorder(node.right, counter);
}

// Delete Node in BST
TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) return null;
    if (key < root.val) root.left = deleteNode(root.left, key);
    else if (key > root.val) root.right = deleteNode(root.right, key);
    else {
        if (root.left == null) return root.right;
        if (root.right == null) return root.left;
        // Find inorder successor (min of right subtree)
        TreeNode minNode = findMin(root.right);
        root.val = minNode.val;
        root.right = deleteNode(root.right, minNode.val);
    }
    return root;
}

TreeNode findMin(TreeNode node) {
    while (node.left != null) node = node.left;
    return node;
}
```

---

## MAANG Pro Upgrade: Tree Recursion Decision Framework

Most tree problems become easy once you identify **when** the current node should be processed.

| Need | Traversal | Reason |
|---|---|---|
| Copy, serialize, root-to-leaf path prefix | Preorder | Root state is needed before children. |
| BST sorted order / kth smallest | Inorder | BST inorder is sorted. |
| Height, diameter, balance, max path | Postorder | Parent needs answers from children. |
| Level view, min depth, shortest level answer | BFS | First time you reach a level is minimal. |
| Path with undo | DFS + backtracking | Path state must be restored after each branch. |

### What Should the Recursive Function Return?

Ask: **"What does my parent need from me?"**

| Problem | Return Value | Global / External State |
|---|---|---|
| Max depth | Height of subtree | None |
| Balanced tree | Height or `-1` if unbalanced | None |
| Diameter | Height of subtree | `maxDiameter` |
| Max path sum | Best one-sided gain | `maxPathSum` |
| LCA | Found node or null | None |
| Count good nodes | Count in subtree | Max-so-far passed down |
| Path Sum III | Count from prefix map | Prefix map with undo |
| Binary Tree Cameras | State: has camera / covered / needs camera | Camera count |

### Postorder Template for Tree DP

```java
int solve(TreeNode node) {
    if (node == null) return base;

    int left = solve(node.left);
    int right = solve(node.right);

    // Use left/right to update answer at current node.
    answer = Math.max(answer, combineThroughRoot(left, right, node.val));

    // Return only what the parent can extend upward.
    return extendToParent(left, right, node.val);
}
```

**Pro insight:** The value you update globally is often different from the value you return upward.

Example:
- Diameter updates with `leftHeight + rightHeight`.
- But it returns `1 + max(leftHeight, rightHeight)`.

### BFS Level Template

```java
Queue<TreeNode> queue = new ArrayDeque<>();
queue.offer(root);

while (!queue.isEmpty()) {
    int size = queue.size(); // freeze current level
    for (int i = 0; i < size; i++) {
        TreeNode node = queue.poll();
        // process node for this level
        if (node.left != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
    }
}
```

Use BFS when the answer depends on **levels**, **nearest leaf**, or **left/right/vertical view**.

### Tree Interview Traps

| Trap | Symptom | Safer Habit |
|---|---|---|
| Returning global answer instead of extendable value | Max path / diameter wrong | Separate "update answer" from "return to parent". |
| Treating null as leaf | Path sum false positives | Leaf means `left == null && right == null`. |
| Forgetting negative values | Max path sum wrong | Clamp child gain with `Math.max(0, child)`. |
| BFS without level size | Levels merge together | Capture `size` before loop. |
| Recursive depth on skewed tree | Stack overflow risk | Know iterative fallback. |
| Assuming tree is balanced | O(log n) claims wrong | Use O(h), worst-case O(n). |

### 60-Second Explanation Template

> "This is a tree recursion problem. I will solve each subtree and return exactly the information the parent needs. If the answer can pass through both children, I update a global answer at the current node, but return only a one-sided value upward because a parent path can continue through only one child."

---

## Practice Problems

**Easy:**
1. Maximum Depth of Binary Tree.
2. Invert Binary Tree.
3. Same Tree.

**Medium:**
1. Flatten Binary Tree to Linked List.
2. Boundary of Binary Tree.
3. Kth Smallest in BST.
4. Binary Tree Right Side View.
5. Count Good Nodes in Binary Tree.

**Hard:**
1. Serialize and Deserialize Binary Tree.
2. Binary Tree Maximum Path Sum.
3. Recover BST (two nodes swapped).

---

**Next →** `../14_Heap/01_Heap_Patterns.md`
