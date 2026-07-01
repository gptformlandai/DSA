# Section 2.7 — Trees, Binary Trees & Binary Search Trees

---

## 1. What Problem Does This Solve?

Trees model hierarchical relationships (file systems, organization charts, HTML DOM). For algorithms:
- **Binary Tree:** Recursive structure where every node has at most 2 children. Foundation for tree traversal patterns.
- **BST (Binary Search Tree):** A binary tree where left subtree values < node < right subtree values. Enables O(log n) search, insert, delete for balanced trees.

---

## 2. Beginner-Friendly Intuition

**Binary Tree:** Every family tree is a tree. Each person has at most two children. You can visit every person by starting from the root and recursively visiting left then right (or right then left, or root first/last).

**BST:** Imagine a tree where at each fork, smaller values go left and larger go right. To find 42, you compare at each node and go left or right — cutting the remaining tree roughly in half each step, just like binary search on a sorted array.

---

## 3. Real-World Analogy

**BST — Library decimal system:** Books are arranged so that each shelf has smaller numbers on the left and larger on the right. Finding a specific Dewey decimal number takes O(depth) time — O(log n) for balanced shelves.

**Binary Tree traversal — Expression parsing:** `(2 + 3) * 4` is a tree: * at root, + as left child, 4 as right child. In-order traversal of an expression tree produces infix notation.

---

## 4. Core Concept

### Node Structure
```java
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}
```

### Traversal Types
| Traversal | Order | Use Case |
|-----------|-------|---------|
| Inorder (L, Root, R) | Sorted order for BST | BST validation |
| Preorder (Root, L, R) | Root first | Serialize tree, copy tree |
| Postorder (L, R, Root) | Root last | Delete tree, evaluate expression |
| Level order (BFS) | Level by level | Find min depth, level sums |

### BST Properties
```
For every node n:
  - All values in left subtree < n.val
  - All values in right subtree > n.val
  - Both left and right subtrees are also BSTs

Inorder traversal of BST → sorted ascending sequence
```

---

## 5. Pattern Recognition Signals

```
"Traverse all nodes" → DFS (inorder/preorder/postorder) or BFS
"Level-by-level processing" → BFS with Queue
"Height/depth of tree" → DFS recursion
"Check BST validity" → Inorder traversal or min/max bounds
"Lowest Common Ancestor" → Recursive LCA pattern
"Path sum" → DFS with running sum
"Symmetric / mirror tree" → Recursive comparison of left↔right
"Serialization" → Preorder DFS with null markers
"Kth smallest in BST" → Inorder traversal (sorted)
"Insert/Delete in BST" → Recursive BST operations
```

---

## 6. Step-by-Step Algorithm

### Validate BST (min/max bounds)
```
isValid(node, min, max):
    if node == null: return true
    if node.val <= min OR node.val >= max: return false
    return isValid(node.left, min, node.val) AND
           isValid(node.right, node.val, max)

Call: isValid(root, Long.MIN_VALUE, Long.MAX_VALUE)
```

### Lowest Common Ancestor
```
lca(node, p, q):
    if node == null OR node == p OR node == q: return node
    left = lca(node.left, p, q)
    right = lca(node.right, p, q)
    if left != null AND right != null: return node  // p and q in different subtrees
    return left != null ? left : right              // both in same subtree
```

---

## 7. Dry Run with Example

### Inorder Traversal: BST with root=4, left=2(1,3), right=6(5,7)
```
Tree structure:
       4
      / \
     2   6
    / \ / \
   1  3 5  7

Inorder (L, Root, R):
  go left to 2 → go left to 1 → leaf, print 1
  back to 2, print 2
  go right to 3 → leaf, print 3
  back to 4, print 4
  go right to 6 → go left to 5 → leaf, print 5
  back to 6, print 6
  go right to 7 → leaf, print 7

Result: 1,2,3,4,5,6,7 (sorted ✓ — confirms valid BST)
```

### Max Depth
```
maxDepth(4) = 1 + max(maxDepth(2), maxDepth(6))
maxDepth(2) = 1 + max(maxDepth(1), maxDepth(3))
maxDepth(1) = 1 + max(null, null) = 1
maxDepth(3) = 1
maxDepth(2) = 1 + max(1,1) = 2
maxDepth(6) = 2 (same structure)
maxDepth(4) = 1 + max(2, 2) = 3 ✓
```

---

## 8. Code Implementation

```java
class TreeNode { int val; TreeNode left, right; TreeNode(int v){val=v;} }

public class TreeAlgorithms {

    // ── Traversals ─────────────────────────────────────────────────────────
    void inorder(TreeNode root, List<Integer> result) {
        if (root == null) return;
        inorder(root.left, result);
        result.add(root.val);           // process root BETWEEN children
        inorder(root.right, result);
    }

    void preorder(TreeNode root, List<Integer> result) {
        if (root == null) return;
        result.add(root.val);           // process root BEFORE children
        preorder(root.left, result);
        preorder(root.right, result);
    }

    // ── Max Depth ──────────────────────────────────────────────────────────
    int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    // ── Validate BST ──────────────────────────────────────────────────────
    boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) &&
               validate(node.right, node.val, max);
    }

    // ── Lowest Common Ancestor ─────────────────────────────────────────────
    TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root; // split point
        return left != null ? left : right;
    }

    // ── LCA in BST (O(log n) for balanced) ────────────────────────────────
    TreeNode lcaBST(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val < root.val && q.val < root.val)
            return lcaBST(root.left, p, q);  // both in left
        if (p.val > root.val && q.val > root.val)
            return lcaBST(root.right, p, q); // both in right
        return root; // split here → root is LCA
    }

    // ── Path Sum ──────────────────────────────────────────────────────────
    boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) return false;
        if (root.left == null && root.right == null)
            return root.val == targetSum; // leaf check
        return hasPathSum(root.left, targetSum - root.val) ||
               hasPathSum(root.right, targetSum - root.val);
    }

    // ── Level Order Traversal ─────────────────────────────────────────────
    List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
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
            result.add(level);
        }
        return result;
    }

    // ── BST Insert ────────────────────────────────────────────────────────
    TreeNode insertBST(TreeNode root, int val) {
        if (root == null) return new TreeNode(val);
        if (val < root.val) root.left = insertBST(root.left, val);
        else root.right = insertBST(root.right, val);
        return root;
    }

    // ── Kth Smallest in BST ───────────────────────────────────────────────
    int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) { stack.push(curr); curr = curr.left; }
            curr = stack.pop();
            if (--k == 0) return curr.val;
            curr = curr.right;
        }
        return -1;
    }
}
```

---

## 9. Time Complexity

| Operation | Balanced BST | Unbalanced BST | Binary Tree |
|-----------|-------------|---------------|------------|
| Search | O(log n) | O(n) | O(n) |
| Insert | O(log n) | O(n) | O(n) |
| Delete | O(log n) | O(n) | O(n) |
| Traversal | O(n) | O(n) | O(n) |
| Max depth | O(n) | O(n) | O(n) |
| Level order | O(n) | O(n) | O(n) |

---

## 10. Space Complexity

| Operation | Space |
|-----------|-------|
| DFS traversal (recursion) | O(h) — h = tree height |
| DFS on balanced tree | O(log n) |
| DFS on skewed tree | O(n) |
| BFS level order | O(w) — w = max width |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Null root | Return early with base case |
| Single node tree | Leaf: no left/right children |
| Skewed tree (like linked list) | O(n) height — recursion can stack overflow |
| Integer.MIN_VALUE as node value | Use Long.MIN_VALUE/MAX_VALUE for BST validation bounds |
| Duplicate values in BST | Depends on definition: usually not allowed |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Validating BST only by comparing parent and child
boolean isValid(TreeNode node) {
    if (node == null) return true;
    if (node.left != null && node.left.val >= node.val) return false; // NOT ENOUGH
    // Counter-example: [5,4,6,null,null,3,7] appears valid locally but isn't
    // CORRECT: pass min/max bounds through recursion
}

// MISTAKE 2: Using Integer.MIN_VALUE/MAX_VALUE as bounds for BST validation
validate(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
// WRONG: node value could BE Integer.MIN_VALUE, causing false negative
validate(root, Long.MIN_VALUE, Long.MAX_VALUE); // CORRECT: use Long bounds

// MISTAKE 3: Off-by-one in path sum at leaf
boolean hasPathSum(TreeNode root, int target) {
    if (root == null) return target == 0; // WRONG: counts null paths
    // CORRECT: check at leaf node
    if (root.left == null && root.right == null) return root.val == target;
    return hasPathSum(root.left, target-root.val) || hasPathSum(root.right, target-root.val);
}

// MISTAKE 4: Level order — not capturing size before loop
while (!queue.isEmpty()) {
    TreeNode node = queue.poll(); // WRONG: processes all levels mixed
    int size = queue.size(); // WRONG: size changes during loop
}
// CORRECT: capture size = queue.size() at START of each while iteration
```

---

## 13. Interview-Level Explanation

**Q: "What is the time complexity of BST operations, and when can it degrade?"**

> "For a balanced BST, all operations (search, insert, delete) are O(log n) because the tree height is O(log n). However, if we insert elements in sorted order (1, 2, 3, ..., n), the BST degenerates into a linked list with height O(n), making all operations O(n). Self-balancing trees like AVL trees and Red-Black trees automatically maintain O(log n) height. Java's `TreeMap` uses a Red-Black Tree internally."

**Q: "Explain the difference between the three DFS traversals."**

> "All three traverse the tree in DFS order, but differ in when the current node is processed relative to its children. Preorder: process root first (useful for copying/serializing a tree — the structure is preserved). Inorder: process root between left and right subtrees — for a BST, this yields sorted ascending order. Postorder: process root last after both subtrees (useful for deleting a tree or evaluating expression trees where children must be evaluated before the operator)."

---

## 14. Real-World Use Cases

| Application | Tree Structure |
|------------|---------------|
| **File system** | Directory tree |
| **HTML/XML DOM** | Document tree |
| **Database B-trees** | Self-balancing search tree |
| **Java TreeMap/TreeSet** | Red-Black Tree |
| **Compiler AST** | Abstract Syntax Tree |
| **Decision trees (ML)** | Binary classification tree |
| **Routing tables** | Trie (prefix tree) |

---

## 15. Variations

| Variation | Structure/Algorithm |
|-----------|-------------------|
| AVL Tree | Self-balancing BST with rotations |
| Red-Black Tree | Java TreeMap's underlying structure |
| B-Tree | Multi-way balanced tree (databases) |
| Segment Tree | Range query tree |
| Trie | Prefix tree for strings |
| Binary Heap | Complete binary tree (priority queue) |

---

## 16. Practice Problems

### Easy — Foundation
1. **Maximum Depth of Binary Tree** (LeetCode #104)
   - *Task:* Find maximum depth.
   - *Hint:* Recursive: 1 + max(depth(left), depth(right)).

2. **Invert Binary Tree** (LeetCode #226)
   - *Task:* Mirror the tree.
   - *Hint:* Swap left and right recursively.

3. **Symmetric Tree** (LeetCode #101)
   - *Task:* Check if tree is mirror symmetric.
   - *Hint:* Compare left.left with right.right and left.right with right.left.

### Medium — Core
1. **Validate Binary Search Tree** (LeetCode #98)
   - *Task:* Validate BST with min/max bounds.
   - *Hint:* Pass (min, max) bounds through recursion; use Long.

2. **Binary Tree Level Order Traversal** (LeetCode #102)
   - *Task:* Return values level by level.
   - *Hint:* BFS with queue; capture size at each level.

3. **Lowest Common Ancestor of BST** (LeetCode #235)
   - *Task:* Find LCA using BST property.
   - *Hint:* If both p,q < root: go left. If both > root: go right. Else: root is LCA.

4. **Binary Tree Right Side View** (LeetCode #199)
   - *Task:* Return rightmost node value at each level.
   - *Hint:* BFS; take last element of each level.

5. **Kth Smallest Element in BST** (LeetCode #230)
   - *Task:* Kth smallest via inorder traversal.
   - *Hint:* Iterative inorder with stack; decrement k, return at k=0.

### Hard — Advanced
1. **Binary Tree Maximum Path Sum** (LeetCode #124)
   - *Task:* Maximum sum path (need not pass through root).
   - *Hint:* Post-order; track gain = max(0, left) + max(0, right) + node.val.

2. **Serialize and Deserialize Binary Tree** (LeetCode #297)
   - *Task:* Convert tree to string and back.
   - *Hint:* Preorder DFS with null markers.

3. **Count of Smaller Numbers After Self** (LeetCode #315)
   - *Task:* For each element, count smaller to its right.
   - *Hint:* BST insertion tracking rank, or merge sort.

---

## 17. How to Know You Have Mastered Trees

You have mastered this topic when you can:
- [ ] Write inorder, preorder, postorder (recursive and iterative)
- [ ] Implement BFS level order with correct level size capture
- [ ] Validate BST with min/max bounds (not just parent-child check)
- [ ] Implement LCA for both generic binary tree and BST
- [ ] Calculate tree height/depth recursively
- [ ] Explain time complexity for balanced vs unbalanced BST
- [ ] Use Long bounds in BST validation to handle Integer.MIN/MAX values
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. BST with values: [5, 3, 7, 1, 4, 6, 8]. Inorder traversal result?

2. Why use `Long.MIN_VALUE` instead of `Integer.MIN_VALUE` for BST validation bounds?

3. `hasPathSum(root, 0)` where root = null. The function returns `false`. Why?

4. LCA of nodes 1 and 4 in the BST from question 1? Trace the algorithm.

5. What is the max depth of a perfectly balanced BST with 7 nodes?

6. Preorder traversal of BST [5, 3, 7, 1, 4, 6, 8]?

7. Why does inorder traversal of a BST produce sorted output?

8. In level order traversal, why must you capture `int size = queue.size()` before the inner loop?

> **Answers:**
> 1. [1, 3, 4, 5, 6, 7, 8] — inorder of BST is always sorted ascending.
> 2. If a node's value equals Integer.MIN_VALUE, using Integer.MIN_VALUE as a lower bound would reject it (since the condition `val <= min` would be `MIN_VALUE <= MIN_VALUE` = true = invalid). Long.MIN_VALUE is always smaller than any valid int value, so it never falsely rejects valid nodes.
> 3. The null case returns `false` (not `target == 0`) because we check the null of a child, not a leaf. A path must end at a leaf. We return false for null to ensure only leaf-to-root paths count.
> 4. LCA(root=5, p=1, q=4): Both < 5? No (4<5 but 1<5... both < 5 → go left). Wait: 1<5 and 4<5 → both < 5 → go to left(3). At 3: 1<3 → go left? No: 1<3 but 4>3 → split → return 3. LCA = 3.
> 5. 3. A balanced BST with 7 nodes has 3 levels: root (1 node), level 2 (2 nodes), level 3 (4 nodes). Height = 3 = floor(log2(7)) + 1.
> 6. [5, 3, 1, 4, 7, 6, 8] — preorder: root first, then left subtree, then right subtree.
> 7. BST property: for every node, all left subtree values < node < all right subtree values. Inorder visits left subtree first (all smaller values), then root, then right subtree (all larger values). Applied recursively, this produces ascending sorted order.
> 8. The queue size changes as we add children during the loop. Capturing `size` before the loop freezes the count of nodes at the current level. Without it, we'd process nodes from multiple levels in one iteration.

---

**Next →** `08_Heap_PriorityQueue.md`

Hierarchical data — file systems, org charts, HTML DOM, decision trees.  
Also enables O(log n) search/insert/delete when balanced (BST).

---

## 2. Beginner-Friendly Intuition

A tree is like a **family tree**:
- One root ancestor at the top.
- Each person (node) has children.
- No cycles (unlike graphs).

```
         1         ← Root
        / \
       2   3       ← Internal nodes
      / \   \
     4   5   6     ← Leaf nodes
```

---

## 3. Core Terminology

| Term | Meaning |
|------|---------|
| Root | Top node (no parent) |
| Leaf | Node with no children |
| Height | Longest path from root to leaf |
| Depth | Distance from root to a node |
| Subtree | Tree rooted at any node |
| Parent | Node directly above |
| Child | Node directly below |
| Sibling | Nodes sharing same parent |
| Level | Depth + 1 |

---

## 4. Java Node

```java
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}
```

---

## 5. Tree Traversals

### Inorder (Left → Root → Right) — gives sorted order for BST
```java
void inorder(TreeNode root) {
    if (root == null) return;
    inorder(root.left);
    System.out.print(root.val + " ");
    inorder(root.right);
}
```

### Preorder (Root → Left → Right) — used to copy/serialize tree
```java
void preorder(TreeNode root) {
    if (root == null) return;
    System.out.print(root.val + " ");
    preorder(root.left);
    preorder(root.right);
}
```

### Postorder (Left → Right → Root) — used to delete tree, evaluate expressions
```java
void postorder(TreeNode root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.val + " ");
}
```

### Level Order (BFS) — level by level
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

**Dry Run for tree [1,2,3,4,5,6]:**
```
Level 0: [1]
Level 1: [2, 3]
Level 2: [4, 5, 6]
```

---

## 6. Height of Tree
```java
int height(TreeNode root) {
    if (root == null) return 0;
    return 1 + Math.max(height(root.left), height(root.right));
}
```
Time: O(n), Space: O(h) where h = height

---

## 7. Binary Search Tree (BST)

**Property:** For every node:
- All left subtree values < node value
- All right subtree values > node value

```
        5
       / \
      3   8
     / \ / \
    2  4 7  9
```

**Search in BST:** O(log n) average, O(n) worst (skewed)
```java
TreeNode search(TreeNode root, int target) {
    if (root == null || root.val == target) return root;
    if (target < root.val) return search(root.left, target);
    return search(root.right, target);
}
```

**Insert in BST:**
```java
TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    if (val < root.val) root.left = insert(root.left, val);
    else if (val > root.val) root.right = insert(root.right, val);
    return root;
}
```

**Validate BST:** (must pass min/max bounds down)
```java
boolean isValidBST(TreeNode root, long min, long max) {
    if (root == null) return true;
    if (root.val <= min || root.val >= max) return false;
    return isValidBST(root.left, min, root.val)
        && isValidBST(root.right, root.val, max);
}
// Call: isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE)
```

---

## 8. Lowest Common Ancestor (LCA)
```java
TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) return root;
    TreeNode left = lca(root.left, p, q);
    TreeNode right = lca(root.right, p, q);
    if (left != null && right != null) return root;  // split here
    return left != null ? left : right;
}
```

---

## 9. Tree Complexity Summary

| Operation | Balanced BST | Skewed BST |
|-----------|-------------|-----------|
| Search | O(log n) | O(n) |
| Insert | O(log n) | O(n) |
| Delete | O(log n) | O(n) |
| Traversal | O(n) | O(n) |

---

## 10. Practice Problems

**Easy:**
1. Maximum depth of binary tree.
2. Invert binary tree.
3. Symmetric tree.

**Medium:**
1. Level order traversal (zigzag, bottom-up).
2. Validate BST.
3. Lowest Common Ancestor.
4. Construct tree from preorder & inorder.
5. Path sum (root to leaf equals target).

**Hard:**
1. Serialize and deserialize binary tree.
2. Binary Tree Maximum Path Sum.
3. Recover BST (two nodes swapped).

---

**Next →** `08_Heap_PriorityQueue.md`
