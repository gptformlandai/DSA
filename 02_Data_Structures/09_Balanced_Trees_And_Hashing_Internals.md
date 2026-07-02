# Section 02b — Balanced Trees & Hash Table Internals

> Companion to `07_Trees_Binary_BST.md`, `06_HashMap_HashSet.md`, and `08_Heap_PriorityQueue.md`. This file closes the data-structure-internals gaps interviewers probe: **BST deletion**, **self-balancing trees (AVL & Red-Black)**, **hash collision strategies (chaining vs open addressing)**, **load factor & resizing**, and the **O(n) heapify proof**.

---

## 1. Why This Matters

`06`–`08` taught how to *use* maps, sets, trees, and heaps. Senior interviews ask how they *work inside*:

- "How does `TreeMap` stay balanced?" → **Red-Black tree**.
- "What happens on a hash collision?" → **chaining vs open addressing**.
- "Why is the default load factor 0.75?" → **space/collision trade-off**.
- "Why is building a heap O(n), not O(n log n)?" → **the heapify proof**.

Knowing these separates "can code a solution" from "understands the machine."

---

## 2. BST Deletion — The Missing Operation

`07` covered insert and search. Deletion has three cases:

1. **Leaf** → just remove it.
2. **One child** → replace the node with its child.
3. **Two children** → replace the node's value with its **in-order successor** (smallest in the right subtree), then delete that successor (which has at most one child).

```java
TreeNode deleteNode(TreeNode root, int key) {
    if (root == null) return null;
    if (key < root.val)      root.left  = deleteNode(root.left, key);
    else if (key > root.val) root.right = deleteNode(root.right, key);
    else {
        // Found the node to delete.
        if (root.left == null)  return root.right;   // cases 1 & 2
        if (root.right == null) return root.left;
        TreeNode succ = root.right;                  // case 3: in-order successor
        while (succ.left != null) succ = succ.left;
        root.val = succ.val;                         // copy successor value up
        root.right = deleteNode(root.right, succ.val); // delete successor
    }
    return root;
}
```
Time O(h): O(log n) balanced, O(n) degenerate. **Canonical problem:** LeetCode 450 *Delete Node in a BST*.

### Successor & predecessor
- **Successor**: if a right child exists → leftmost node of the right subtree; else the lowest ancestor for which the node is in its left subtree.
- **Predecessor**: mirror image (rightmost of left subtree, else lowest ancestor where node is in the right subtree).

---

## 3. Why Balancing Exists

Insert 1, 2, 3, 4, 5 into a plain BST → a right-leaning "linked list" of height n → search degrades to O(n). **Self-balancing trees** enforce O(log n) height via **rotations**.

### Rotations — the atomic rebalancing move
```
     y                                x
    / \     right rotate (y)         / \
   x   C   ───────────────────►     A   y
  / \       ◄───────────────────       / \
 A   B        left rotate (x)         B   C
```
```java
TreeNode rotateRight(TreeNode y) {
    TreeNode x = y.left;
    y.left = x.right;
    x.right = y;
    update(y); update(x);     // recompute heights/sizes bottom-up
    return x;                 // x is the new subtree root
}
TreeNode rotateLeft(TreeNode x) {
    TreeNode y = x.right;
    x.right = y.left;
    y.left = x;
    update(x); update(y);
    return y;
}
```
Rotations preserve the BST in-order ordering while changing height — the foundation of AVL, Red-Black, splay, and treap.

---

## 4. AVL Trees — Strictly Height-Balanced

**Invariant:** for every node, `|height(left) - height(right)| ≤ 1` (the *balance factor* ∈ {-1, 0, 1}). After each insert/delete, walk back up and rebalance with one of four rotation cases:

| Imbalance | Shape | Fix |
|-----------|-------|-----|
| Left-Left | inserted into left child's left | rotate right |
| Right-Right | inserted into right child's right | rotate left |
| Left-Right | left child's right | rotate left on child, then right |
| Right-Left | right child's left | rotate right on child, then left |

```java
TreeNode insert(TreeNode node, int key) {
    if (node == null) return new TreeNode(key);
    if (key < node.val) node.left = insert(node.left, key);
    else if (key > node.val) node.right = insert(node.right, key);
    else return node;                         // no duplicates
    update(node);                             // set node.height
    int bf = balanceFactor(node);             // h(left) - h(right)
    if (bf > 1 && key < node.left.val)  return rotateRight(node);          // LL
    if (bf < -1 && key > node.right.val) return rotateLeft(node);          // RR
    if (bf > 1 && key > node.left.val) { node.left = rotateLeft(node.left); return rotateRight(node); }   // LR
    if (bf < -1 && key < node.right.val){ node.right = rotateRight(node.right); return rotateLeft(node); } // RL
    return node;
}
```
- Height ≤ ~1.44·log₂(n) → **very tight balance, fast lookups**.
- Trade-off: more rotations on insert/delete than Red-Black → better for **read-heavy** workloads.

---

## 5. Red-Black Trees — What `TreeMap` Uses

A Red-Black tree is a looser-balanced BST governed by five rules:

1. Every node is red or black.
2. The root is black.
3. All leaves (null nodes) are black.
4. A red node's children are both black (no two reds in a row).
5. Every root-to-leaf path has the same number of black nodes (**black-height**).

These guarantee the longest path is at most **twice** the shortest → height ≤ 2·log₂(n+1) → O(log n) operations. Rebalancing uses **recoloring + at most 2 rotations** per insert (fewer structural changes than AVL).

| | AVL | Red-Black |
|---|-----|-----------|
| Balance | strict (≤1 factor) | loose (≤2x path) |
| Lookups | faster (shorter) | slightly slower |
| Insert/delete rotations | more | fewer |
| Used by | read-heavy DBs, indexes | `java.util.TreeMap`, `TreeSet`, C++ `std::map`, Linux CFS scheduler |

> Interview-safe answer: "Java's `TreeMap` is a Red-Black tree — it favors fewer rotations on writes, trading a bit of lookup speed for cheaper updates versus a strict AVL tree."

**B-Trees** (a related generalization) keep many keys per node to minimize disk seeks — the backbone of database and filesystem indexes.

---

## 6. Hash Table Internals

### Collision resolution — two families

**A) Separate chaining** (Java's `HashMap`): each bucket holds a linked list; on collision, append. Java 8+ converts a bucket to a **balanced tree** once it holds ≥ 8 entries (and the table ≥ 64), bounding worst-case bucket lookup at O(log k) instead of O(k).

**B) Open addressing**: store entries directly in the array; on collision, **probe** for the next free slot.

| Probing scheme | Next index | Issue |
|----------------|-----------|-------|
| Linear probing | `(h + i) % m` | primary clustering (long runs) |
| Quadratic probing | `(h + i²) % m` | secondary clustering; needs load < 0.5 |
| Double hashing | `(h1 + i·h2) % m` | best distribution; `h2` must be coprime to `m` |

```java
// Linear-probing hash set (open addressing) — illustrative.
class OpenAddrSet {
    Integer[] slots;
    int size = 0, capacity;
    OpenAddrSet(int cap) { capacity = cap; slots = new Integer[cap]; }

    private int hash(int key) { return (key % capacity + capacity) % capacity; }

    boolean add(int key) {
        if (size >= capacity * 0.5) resize();       // keep load factor low
        int i = hash(key);
        while (slots[i] != null) {
            if (slots[i] == key) return false;       // already present
            i = (i + 1) % capacity;                  // linear probe
        }
        slots[i] = key;
        size++;
        return true;
    }
    private void resize() {
        Integer[] old = slots;
        capacity *= 2;
        slots = new Integer[capacity];
        size = 0;
        for (Integer v : old) if (v != null) add(v); // rehash everything
    }
}
```

### Load factor & resizing
- **Load factor α = entries / buckets.** Chaining default is **0.75** — a balance: higher wastes fewer buckets but lengthens chains; lower speeds lookups but wastes memory.
- Open addressing degrades sharply past α ≈ 0.7; keep it well below 1. Expected probes ≈ `1 / (1 - α)`.
- **Resize** (usually doubling) rehashes all entries → amortized O(1) insert, but individual inserts can spike to O(n).

### Chaining vs open addressing

| | Chaining | Open addressing |
|---|----------|-----------------|
| Memory | pointer overhead per node | compact array, cache-friendly |
| Load factor | can exceed 1 | must stay < 1 |
| Deletion | trivial (unlink) | needs tombstones |
| Worst case | O(n) (or O(log n) treeified) | O(n) with clustering |
| Used by | Java `HashMap` | Python `dict`, many C++ flat maps |

### Good hash functions & DoS safety
- Aim for **uniform distribution**; Java mixes bits: `h ^= (h >>> 16)` to spread high bits into low buckets.
- Adversaries can force collisions (**hash-flooding DoS**) — production maps randomize seeds or treeify to stay O(log n).
- **Consistent hashing** (a ring of hash values) is the distributed-systems cousin, minimizing re-mapping when nodes join/leave.

---

## 7. The O(n) Heapify Proof

`08` builds heaps but doesn't justify the cost. Building a heap by inserting n elements is O(n log n). Building **bottom-up** (sift-down from the last internal node to the root) is **O(n)**.

### Why
A node at height `h` costs at most `h` sift-down swaps. There are at most `⌈n / 2^(h+1)⌉` nodes at height `h`. Total work:

$$\sum_{h=0}^{\log n} \frac{n}{2^{h+1}} \cdot h = \frac{n}{2}\sum_{h=0}^{\log n} \frac{h}{2^{h}} \le \frac{n}{2}\cdot 2 = O(n)$$

because $\sum_{h=0}^{\infty} h/2^h = 2$ (a convergent series). Leaves (half the nodes) cost 0; only shallow nodes near the bottom are numerous, and they do little work.

```java
void buildHeap(int[] a) {                 // O(n)
    for (int i = a.length / 2 - 1; i >= 0; i--) siftDown(a, i, a.length);
}
void siftDown(int[] a, int i, int n) {
    while (true) {
        int l = 2 * i + 1, r = 2 * i + 2, largest = i;
        if (l < n && a[l] > a[largest]) largest = l;
        if (r < n && a[r] > a[largest]) largest = r;
        if (largest == i) break;
        int t = a[i]; a[i] = a[largest]; a[largest] = t;
        i = largest;
    }
}
```
> **Contrast:** *building* a heap is O(n); *heapsort* (build once, then extract-max n times) is O(n log n) because each of the n extractions costs O(log n).

---

## 8. Failure Modes & Interview Traps

| Trap | Fix |
|------|-----|
| BST delete replacing with the wrong node | Use in-order successor (or predecessor) consistently. |
| Claiming heap build is O(n log n) | Bottom-up heapify is O(n); explain the height series. |
| Saying `TreeMap` is an AVL tree | It's Red-Black (fewer write rotations). |
| Open addressing deletion without tombstones | Breaks probe chains; mark deleted slots. |
| Quadratic probing at high load | Fails to find slots; keep α < 0.5. |
| Ignoring resize cost in worst case | Amortized O(1), but a single insert can be O(n). |
| Bad hash → everything in one bucket | Java treeifies to O(log n); custom maps should mix bits. |

---

## 9. 60-Second Explanation Template

> "For ordered operations with guaranteed O(log n), I'd use a self-balancing BST — Red-Black like `TreeMap` for cheap writes, AVL for read-heavy. For O(1) average lookups I'd use a hash table; Java uses chaining with treeification past 8 entries and a 0.75 load factor. Deletion in a BST replaces with the in-order successor. And building a heap is O(n) bottom-up because most nodes sit near the leaves."

---

## Practice Problems

**Medium:**
1. Delete Node in a BST (deletion, successor).
2. Design HashMap / Design HashSet (open addressing or chaining from scratch).
3. Insert into a Binary Search Tree.
4. Balance a Binary Search Tree (rebuild balanced from sorted order).

**Hard:**
1. Implement an AVL tree with insert + delete + rotations.
2. Count of Range Sum (balanced BST / BIT).
3. Design a hash map with O(1) resize amortization and tombstone deletes.

---

**Next →** `../03_Searching/01_Searching_Algorithms.md`
