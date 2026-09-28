# Section 13 — Tree Patterns Through ONE Recursion Template (FAANG Deep Dive)

---

# 📑 INDEX — Quick Navigation (50 Patterns)

## 🎯 Core Concepts
| Section | Line |
|---------|------|
| [The "One Sentence That Unlocks All Tree Problems"](#-the-one-sentence-that-unlocks-all-tree-problems) | 85 |
| [The TWO Pipes (DOWN & UP)](#-the-two-pipes--the-entire-subject-in-two-lines) | 92 |
| [The ONE Template (6 Steps)](#the-one-template-that-rules-all-tree-problems) | 115 |

---

## 🌳 Foundation Patterns (0-17)

| # | Pattern | LeetCode | Line |
|---|---------|----------|------|
| 0 | [Maximum Depth](#pattern-0-the-foundation--maximum-depth) | 104 | 104 |
| 1 | [Diameter of Binary Tree](#pattern-1-diameter-of-binary-tree) | 543 | 177 |
| 2 | [Count Good Nodes](#pattern-2-count-good-nodes-the-down-pipe--parameter-pattern) | 1448 | 282 |
| 3 | [Invert / Same Tree / Is Mirror](#pattern-3-the-structural-trio--invert-same-tree-is-mirror) | 226, 100, 101 | 385 |
| 4 | [Lowest Common Ancestor (LCA)](#pattern-4-lowest-common-ancestor-lca) | 236 | 475 |
| 5 | [Inorder Successor in BST](#pattern-5-inorder-successor-in-a-bst-the-next-bigger-node) | 285 | 565 |
| 6 | [Largest BST Subtree](#pattern-6-largest-bst-subtree-the-multi-value-return-pattern) | 333 | 634 |
| 7 | [Distance Between Two Nodes](#pattern-7-distance-between-two-nodes-lca--depth-composed) | 1740 | 708 |
| 8 | [Maximum Path Sum](#pattern-8-maximum-path-sum-diameters-richer-twin) | 124 | 791 |
| 9 | [Balanced Binary Tree](#pattern-9-balanced-binary-tree-the-height--a-flag-bundle-mini-version) | 110 | 836 |
| 10 | [Path Sum II (Backtracking)](#pattern-10-path-sum-ii-the-backtracking-pattern--the-one-true-mutation) | 113 | 875 |
| 11 | [Validate BST](#pattern-11-validate-bst-the-range-pushed-down-pattern--good-nodes-twin) | 98 | 924 |
| 12 | [BFS / Level-Order Family](#pattern-12-bfs--level-order-family-the-other-traversal-mode) | 102 | 969 |
| 13 | [Kth Smallest in BST](#pattern-13-kth-smallest-in-a-bst-inorder--counter-early-stop) | 230 | 1049 |
| 14 | [Serialize & Deserialize](#pattern-14-serialize--deserialize-preorder-encode--queue-decode) | 297 | 1096 |
| 15 | [Build from Preorder + Inorder](#pattern-15-build-tree-from-preorder--inorder-root-splits-the-arrays) | 105 | 1161 |
| 16 | [Path Sum III & Subtree of Another](#pattern-16-path-sum-iii--subtree-of-another-tree-nested-dfs--prefix-on-a-path) | 437, 572 | 1211 |
| 17 | [Tree Views (Vertical/Right-Side)](#pattern-17-tree-views-coordinate-pushed-down--right-side-vertical-topbottom) | 199, 987 | 1277 |

---

## 🆕 Extended Patterns (18-50) — L5 Complete Coverage

### BST Core Operations
| # | Pattern | LeetCode | Line |
|---|---------|----------|------|
| 18 | [BST Search](#pattern-18-bst-search-the-foundation-of-bst-operations) | 700 | 1531 |
| 19 | [BST Insert](#pattern-19-bst-insert-find-the-right-spot) | 701 | 1572 |
| 20 | [BST Delete](#pattern-20-bst-delete-the-three-cases) | 450 | 1614 |

### Tree DP Patterns
| # | Pattern | LeetCode | Line |
|---|---------|----------|------|
| 21 | [House Robber III](#pattern-21-house-robber-iii-the-bundle-return-pattern) | 337 | 1691 |
| 22 | [Binary Tree Cameras](#pattern-22-binary-tree-cameras-3-state-tree-dp) | 968 | 1759 |
| 23 | [Distribute Coins](#pattern-23-distribute-coins-in-binary-tree-flow-counting) | 979 | 1839 |

### BFS Variants
| # | Pattern | LeetCode | Line |
|---|---------|----------|------|
| 24 | [Maximum Width](#pattern-24-maximum-width-of-binary-tree-bfs--index-tracking) | 662 | 1905 |
| 25 | [Next Right Pointers](#pattern-25-populating-next-right-pointers-level-linking) | 116/117 | 1989 |
| 26 | [Boundary Traversal](#pattern-26-boundary-of-binary-tree-three-part-traversal) | 545 | 2064 |
| 27 | [Left Side View](#pattern-27-left-side-view-mirror-of-right-side-view) | — | 2135 |
| 30 | [Average of Levels](#pattern-30-average-of-levels-bfs-aggregation) | 637 | 2266 |

### Traversal & Path Patterns
| # | Pattern | LeetCode | Line |
|---|---------|----------|------|
| 28 | [Diagonal Traversal](#pattern-28-diagonal-traversal-coordinate-variant) | — | 2195 |
| 29 | [Merge Two Trees](#pattern-29-merge-two-binary-trees-parallel-traversal) | 617 | 2232 |
| 31 | [Longest Univalue Path](#pattern-31-longest-univalue-path-diameter-variant) | 687 | 2309 |
| 32 | [LCA of Deepest Leaves](#pattern-32-lca-of-deepest-leaves-depth--lca-combined) | 1123 | 2354 |
| 33 | [Max Product Split](#pattern-33-maximum-product-of-splitted-binary-tree-total-sum-trick) | 1339 | 2399 |
| 34 | [Pseudo-Palindromic Paths](#pattern-34-pseudo-palindromic-paths-backtracking--bit-trick) | 1457 | 2442 |
| 35 | [Delete Nodes & Return Forest](#pattern-35-delete-nodes-and-return-forest-post-order-with-set) | 1110 | 2487 |
| 36 | [Sum Root to Leaf Numbers](#pattern-36-sum-root-to-leaf-numbers-path-value-accumulation) | 129 | 2537 |

### BST Construction & Manipulation
| # | Pattern | LeetCode | Line |
|---|---------|----------|------|
| 37 | [Sorted List to BST](#pattern-37-convert-sorted-list-to-bst-two-pointer--recursion) | 109 | 2574 |
| 38 | [Two Sum IV - BST](#pattern-38-two-sum-iv---input-is-bst-inorder--two-pointers) | 653 | 2616 |
| 39 | [Balance a BST](#pattern-39-balance-a-bst-inorder--array--rebuild) | 1382 | 2657 |
| 40 | [Unique BSTs (Count)](#pattern-40-unique-binary-search-trees-catalan-number-dp) | 96 | 2702 |
| 41 | [Unique BSTs (Generate)](#pattern-41-unique-binary-search-trees-ii-generate-all-bsts) | 95 | 2753 |

### Advanced Patterns
| # | Pattern | LeetCode | Line |
|---|---------|----------|------|
| 42 | [Sum of Distances (Re-rooting)](#pattern-42-sum-of-distances-in-tree-re-rooting-dp--advanced) | 834 | 2804 |
| 43 | [Recover BST](#pattern-43-recover-binary-search-tree-inorder-anomaly-detection) | 99 | 2881 |
| 44 | [Trim BST](#pattern-44-trim-a-bst-range-pruning) | 669 | 2940 |
| 45 | [All Nodes Distance K](#pattern-45-all-nodes-distance-k-bfs-from-target) | 863 | 2981 |
| 46 | [Flatten to Linked List](#pattern-46-flatten-binary-tree-to-linked-list-preorder-rewiring) | 114 | 3053 |
| 47 | [BST from Preorder](#pattern-47-construct-bst-from-preorder-range-validation) | 1008 | 3109 |
| 48 | [Range Sum of BST](#pattern-48-range-sum-of-bst-bst-pruned-search) | 938 | 3149 |
| 49 | [Min Difference in BST](#pattern-49-minimum-difference-in-bst-inorder--track-previous) | 530 | 3186 |
| 50 | [Closest BST Value](#pattern-50-closest-bst-value-bst-binary-search) | 270 | 3226 |

---

## 📚 Reference Sections
| Section | Line |
|---------|------|
| [Master Decision Tree](#-the-master-decision-tree--pick-your-weapon-in-10-seconds) | 1327 |
| [MAANG Coverage Map](#-updated-maang-coverage-map) | 3261 |
| [Pattern Recognition Cheat Sheet](#-master-pattern-recognition-cheat-sheet) | 3323 |
| [Final Mastery Checklist](#-final-mastery-checklist) | 3357 |

---

## 🔍 Quick Find by Problem Type

| If you need to... | Go to Pattern # |
|-------------------|-----------------|
| Find depth/height | 0, 9 |
| Find diameter/longest path | 1, 8, 31 |
| Count nodes with ancestor condition | 2 |
| Modify tree structure | 3, 46 |
| Compare two trees | 3, 29 |
| Find LCA | 4, 32 |
| BST search/insert/delete | 18, 19, 20 |
| Validate/recover BST | 11, 43, 44 |
| BST kth/successor/range | 5, 13, 48, 49, 50 |
| Path sum problems | 10, 16, 34, 36 |
| Level-order/BFS | 12, 24, 25, 27, 30 |
| Serialize/build tree | 14, 15, 37, 39, 47 |
| Tree DP | 21, 22, 23, 33 |
| Tree views | 17, 26, 27, 28 |
| Distance problems | 7, 42, 45 |

---

> **How to use this file (read this first — and yes, this line is for the podcast too):**
> This document is written to be **read out loud**. You will drop it into NotebookLM, generate a podcast, and listen on loop. So everything here is spoken in plain sentences, in a repeating rhythm, so your brain forms a **mind map by repetition**.
>
> Every single pattern below is explained through the **exact same 6-step skeleton**. That repetition is intentional. After a handful of patterns, your brain will already be *predicting* the next step before the narrator says it. That is what "becoming a pro" actually feels like — the structure becomes muscle memory. There are **50 patterns** here, but they're really just a few reusable *moves* wearing different costumes.

---

## 🧠 The "One Sentence That Unlocks All Tree Problems"

> **Every tree problem is just asking: "What do I need from ABOVE me?" and "What do I need from BELOW me?"**

That's it. Once you figure out which direction the information flows, the code writes itself.

---

## 🎯 The TWO Pipes — The Entire Subject in Two Lines

Think of a tree like a company org chart. Information can flow in only TWO directions:

### 📥 The DOWN Pipe (Parent → Child)
**How it works:** Function PARAMETERS
**When it happens:** BEFORE you visit children (pre-order)
**Use when:** You need info from ancestors — "what's the max value seen so far on my path from the root?"

### 📤 The UP Pipe (Child → Parent)  
**How it works:** RETURN values
**When it happens:** AFTER children finish (post-order)
**Use when:** You need info from descendants — "how tall is my subtree?"

**The Golden Rule:**
```
Need ancestor info?  → Push it DOWN as a parameter  → Work in PRE-order
Need descendant info? → Pull it UP as a return value → Work in POST-order
```

---

## The ONE Template That Rules All Tree Problems

Before any pattern, burn this into memory. Every tree problem is just answering these 6 questions:

```
solve(node, [stuff from parent]):

    1. PARAMETERS      → What do I need from ABOVE (my ancestors)?
    2. BASE CASE       → When do I stop? (usually: node is null)
    3. PRE-ORDER work  → What do I do BEFORE visiting my kids? (uses ancestor info)
    4. GO LEFT / RIGHT → Ask my kids to solve their part (trust them!)
    5. POST-ORDER work → What do I do AFTER my kids answer? (combine their answers)
    6. RETURN          → What do I hand UP to my parent?
```

### The Simple Mental Cheat Sheet

- **Parameters (going DOWN):** Info from ancestors → stuff I need to know before my kids run
- **Recursive Calls (EXPLORE):** Let left and right kids do their thing
- **After-Call Logic (COMBINE):** Put together what my kids told me
- **Return Values (going UP):** The answer I give to my parent

### ⚠️ The #1 Mistake Everyone Makes

> **Parameters only move info DOWNWARD. They do NOT bring values back up.**

To send info UP, you MUST use **return values**. If you mix these up, every hard tree problem will feel impossible.

The only exception: if you pass a **shared object** (like a list or a map) as a parameter, you can modify it and the changes persist. But that's a special trick, not the normal flow.

### How I will teach every pattern (the repeating rhythm)

For each pattern you will hear the same 8 beats:

1. **The story** — a plain-English analogy so it *clicks* immediately.
2. **What the interviewer is really testing.**
3. **Walk the 6-step template**, one step at a time.
4. **The code** (Java).
5. **A tiny dry run** so the abstract becomes concrete.
6. **The "aha" line** — the one sentence that unlocks it.
7. **The classic trap.**
8. **Mind-map anchor** — 3 keywords to recall the whole thing.

Let's build you into a pro. One template, many faces.

---

## 🔑 The 6 Questions You Ask EVERY Time

Your goal is **not** to memorize each problem. Your goal is to **figure out** the solution by asking the same 6 questions every time:

1. **PARAMETERS:** *"What do I need to know from my ancestors to do my job?"*
2. **BASE CASE:** *"What's the smallest/simplest case where I know the answer immediately?"*
3. **PRE-ORDER:** *"Is there work I must do BEFORE my kids run — because it needs ancestor info?"*
4. **GO LEFT / RIGHT:** *"What do I ask each kid? Do I trust them to give me the right answer?"*
5. **POST-ORDER:** *"Now that both kids answered, how do I combine their answers with myself?"*
6. **RETURN:** *"What single thing does my parent need from me?"*

**The 6 questions NEVER change. Only the answers do.**

If you learn to ask these questions, you can walk up to ANY tree problem you've never seen, ask them in order, and the code writes itself.

---

# PATTERN 0: The Foundation — Maximum Depth

*(Every other pattern is just a twist on this one. Master this and you're 60% done.)*

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** You're the CEO of a company. Someone asks "How many levels deep is our org chart?" You don't count it yourself. You turn to your two direct reports and ask: "How deep is YOUR team?" They each give you a number. You take the bigger one, add 1 for yourself, and report that up. Done.

**The Key Insight:** You don't measure the whole tree. You just ask your kids "how deep are you?" and add 1 for yourself.

## What the interviewer is really testing
Do you understand that tree answers are built by **trusting your children** and doing simple math on their answers? This is the "leap of faith" of recursion.

## Walk the 6-step template

1. **PARAMETERS — "Do I need anything from my ancestors?"**
   **Answer:** No. Just the `node`.
   **Why:** Depth is about what's BELOW me, not above. I don't need to know anything about my parents.

2. **BASE CASE — "What's the simplest case where I know the answer immediately?"**
   **Answer:** If `node == null`, return `0`.
   **Why:** An empty tree has zero levels. This is where the recursion stops and starts coming back up.

3. **PRE-ORDER — "Any work before my kids run?"**
   **Answer:** None.
   **Why:** I don't need to prepare anything for my children. This step is empty — and that's a valid answer! When pre-order is empty, it's a signal that the problem is purely about what's BELOW you.

4. **GO LEFT / RIGHT — "What do I ask each kid?"**
   **Answer:** `left = maxDepth(node.left)`, `right = maxDepth(node.right)`
   **Why:** I trust each child to tell me the true depth of their side. This is the "leap of faith" — don't try to trace through the whole tree yourself!

5. **POST-ORDER — "Both kids answered. How do I combine them?"**
   **Answer:** Take `Math.max(left, right)` — the deeper of my two sides.
   **Why:** Depth is the LONGEST chain going down, and a chain only goes through ONE side. So I pick the bigger one.

6. **RETURN — "What does my parent need from me?"**
   **Answer:** `1 + Math.max(left, right)` — my deeper side, plus 1 for myself.
   **Why:** My parent counts ME as one level. The `+1` IS me.

## The code
```java
int maxDepth(TreeNode node) {
    if (node == null) return 0;              // STOP: empty = 0 levels
    int left  = maxDepth(node.left);         // Ask left kid
    int right = maxDepth(node.right);        // Ask right kid
    return 1 + Math.max(left, right);        // Take the deeper side + me
}
```

## Tiny dry run
```
      A
     / \
    B   C
   /
  D
```
- `maxDepth(D)`: left=0, right=0 → returns 1 (just D)
- `maxDepth(B)`: left=1 (from D), right=0 → returns 2
- `maxDepth(C)`: left=0, right=0 → returns 1
- `maxDepth(A)`: left=2, right=1 → returns `1 + max(2,1)` = **3** ✓

## The "aha" line
> "I don't measure the tree. I ask my kids for their depths and just add 1 for myself."

## The classic trap
Returning `1` for the base case instead of `0`. Remember: `null` is NOTHING — zero levels, not one.

## Mind-map anchor
**`null→0` · `max(left,right)` · `+1 for me`**

---

# PATTERN 1: Diameter of Binary Tree

*(The first "two-brained" pattern: the number you RETURN is different from the number you ANSWER. This single idea appears in half of all hard tree problems, so slow down here.)*

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Imagine you're finding the longest hiking trail in a mountain range. The trail can START anywhere and END anywhere — it doesn't have to go through the main peak. At each peak, you ask: "If the longest trail BENDS right here at me — going down my left slope, through me, and down my right slope — how long is it?"

**The Key Insight:** At each node, you calculate TWO different things:
1. **The ANSWER** = the longest path that BENDS at me (uses BOTH my arms)
2. **The RETURN** = the longest path that PASSES THROUGH me going UP (uses only ONE arm)

**Why are they different?** A path can't FORK. If my parent wants to extend a path through me, that path can only use ONE of my arms — it can't go down both!

```
        Parent
           |
          ME  ← Parent's path can only use ONE of my arms
         /  \
      Left  Right  ← But the path that BENDS at me uses BOTH
```

## What the interviewer is really testing
Can you separate **"the value I return to my parent"** from **"the global answer I'm tracking"**? These are two DIFFERENT numbers computed at the same node.

## Walk the 6-step template

1. **PARAMETERS — "Do I need ancestor info?"**
   **Answer:** No — just `node`. The running max diameter lives OUTSIDE the recursion (a class field or one-element array).
   **Why:** The longest path is about what's BELOW, so it's discovered going UP. The global lives outside because MANY nodes might each produce a candidate answer.

2. **BASE CASE — "Simplest case?"**
   **Answer:** `node == null` → return height `0`.
   **Why:** An empty branch contributes zero edges to any path.

3. **PRE-ORDER — "Work before kids?"**
   **Answer:** None.
   **Why:** Pure bottom-up — no info flows down.

4. **GO LEFT / RIGHT — "What do I get from each kid?"**
   **Answer:** `leftH = height(left)`, `rightH = height(right)` — each kid's height.
   **Why:** The longest path bending at me is built from how deep I can reach on each side.

5. **POST-ORDER — "How do I turn the two heights into a candidate ANSWER?"**
   **Answer:** The path that BENDS at me spans `leftH + rightH` edges. Update `diameter = max(diameter, leftH + rightH)`.
   **Why:** A path through me goes down-left, up through me, down-right — that's left height + right height edges.

6. **RETURN — "What does my parent need — the same number?"**
   **Answer:** **NO!** Return `1 + max(leftH, rightH)`, NOT `leftH + rightH`.
   **Why (THE WHOLE PATTERN):** My parent wants to extend a path THROUGH me and keep going up. But a path can't FORK — it can only use ONE of my arms. So I return just my taller arm plus myself. The `leftH+rightH` was a TERMINAL answer (the path ends by bending here); it can't be extended, so it never goes up.

## ⚡ The Critical Difference

```
ANSWER = leftH + rightH     ← Path BENDS here (uses both arms) — save to global
RETURN = 1 + max(leftH, rightH)  ← Path PASSES THROUGH here (one arm only) — send to parent
```

**This "answer ≠ return" split is the single most important idea in hard tree problems.**

## The code
```java
int diameter = 0;  // Global to track the best answer

int diameterOfBinaryTree(TreeNode root) {
    height(root);
    return diameter;
}

int height(TreeNode node) {
    if (node == null) return 0;                       // STOP: empty = 0
    int leftH  = height(node.left);                   // Ask left kid
    int rightH = height(node.right);                  // Ask right kid
    diameter = Math.max(diameter, leftH + rightH);    // ANSWER: path bending here
    return 1 + Math.max(leftH, rightH);               // RETURN: one arm only!
}
```

## Tiny dry run
```
        1
       / \
      2   3
     / \
    4   5
```
- `height(4)=1`, `height(5)=1`
- At node 2: leftH=1, rightH=1 → bend = `1+1 = 2` → diameter becomes **2**. Returns `1+max(1,1)=2`.
- At node 3: returns 1.
- At node 1: leftH=2, rightH=1 → bend = `2+1 = 3` → diameter becomes **3**. ✓
- Longest walk: `4 → 2 → 1 → 3` = 3 edges.

## The "aha" line
> "At each node I ANSWER with both sides added (the bend), but I RETURN only one side plus me — because my parent's path can't fork through me."

## The classic trap
Returning `leftH + rightH` to your parent instead of `1 + max(leftH, rightH)`. If you return the bend, you're claiming a parent can walk down BOTH your arms at once — impossible!

**Remember:** The bend is the **ANSWER**, the max side is the **RETURN**. Keep them in two separate mental buckets.

## Mind-map anchor
**`answer = left+right` · `return = 1+max` · "path can't fork"**

---

# PATTERN 2: Count Good Nodes (the DOWN pipe / parameter pattern)

*(This is the OPPOSITE of Diameter. Diameter used the UP pipe (return values). Good Nodes uses the DOWN pipe (parameters). This is where you truly feel the difference between the two directions.)*

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** You're walking down a mountain trail from the peak. A viewpoint is "good" if you can see the original peak from there — meaning no taller mountain blocked your view on the way down. As you walk, you keep track of "the tallest thing I've seen so far." At each viewpoint, you check: "Am I at least as tall as the tallest thing so far?" If yes, you're good!

**The Key Insight:** To know if I'm "good," I need to know what happened ABOVE me (the max value on my path from the root). That info lives with my ANCESTORS — so my parent must HAND IT DOWN to me as a parameter.

**The Pattern Recognition Signal:**
```
When the problem mentions:
- "from the root"
- "on the path so far"  
- "ancestors"
- "seen before reaching this node"

→ You need the DOWN pipe → Use a PARAMETER → Work in PRE-order
```

## What the interviewer is really testing
Can you use **parameters to push info DOWN** (pre-order)? The context — "max on the path so far" — is given by a parent to its children BEFORE they run.

## Walk the 6-step template

1. **PARAMETERS — "Do I need anything from my ancestors?"**
   **Answer:** YES! Carry `maxSoFar` = the largest value on the path from the root down to my parent.
   **Why (THE WHOLE PATTERN):** "Good" is defined by who came BEFORE me on the root path. That info lives ABOVE me, so the only way to have it is for my parent to HAND IT DOWN as a parameter.

2. **BASE CASE — "Simplest case?"**
   **Answer:** `node == null` → return `0`.
   **Why:** An empty spot has no node to be good, so it adds zero to the count.

3. **PRE-ORDER — "Work before kids, using the ancestor info?"**
   **Answer:** YES, two things:
   - (a) Judge myself: `good = (node.val >= maxSoFar) ? 1 : 0`
   - (b) Build the UPDATED context to pass down: `newMax = max(maxSoFar, node.val)`
   **Why:** My kids' "max so far" must INCLUDE me, so I compute `newMax` before calling them. Judging happens here too because it only depends on ancestors (already known).

4. **GO LEFT / RIGHT — "What do I hand each kid?"**
   **Answer:** The UPDATED context: `dfs(node.left, newMax)`, `dfs(node.right, newMax)`.
   **Why:** Both kids continue the same root-path with me now included.

5. **POST-ORDER — "How do I combine the kids' counts?"**
   **Answer:** Add them: `left + right`.
   **Why:** Total good nodes below me = good nodes in left + good nodes in right.

6. **RETURN — "What does my parent need?"**
   **Answer:** `good + left + right` — my own goodness plus both subtree totals.
   **Why:** My parent is adding up a grand total; I hand up the count for my entire subtree (including me).

## The code
```java
int goodNodes(TreeNode root) {
    return dfs(root, Integer.MIN_VALUE);   // Start: nothing seen yet, so -infinity
}

int dfs(TreeNode node, int maxSoFar) {         // maxSoFar comes DOWN from parent
    if (node == null) return 0;                // STOP: empty = 0 good nodes
    
    int good = (node.val >= maxSoFar) ? 1 : 0; // PRE-ORDER: Am I good?
    int newMax = Math.max(maxSoFar, node.val); // Build updated context for kids
    
    int left  = dfs(node.left,  newMax);       // Pass updated max DOWN to left
    int right = dfs(node.right, newMax);       // Pass updated max DOWN to right
    
    return good + left + right;                // Total: me + both subtrees
}
```

## Tiny dry run
```
        3          (max seen: -inf → 3 >= -inf, so 3 is GOOD)
       / \
      1   4        (4 >= 3 → GOOD; 1 < 3 → NOT good)
       \   \
        3   5      (3 >= max(3,1)=3 → GOOD; 5 >= 4 → GOOD)
```
Good nodes: `3, 4, 5, and the deep 3` → **4 good nodes**.

## The "aha" line
> "I judge myself using info my parent handed me, THEN I hand an updated version of that info to my kids. Down pipe, pre-order."

## The classic trap
Trying to compute "good" in POST-order (after children). You can't — goodness depends on ANCESTORS, which are only known on the way DOWN. If you need ancestor info, that's your signal: **use a parameter, do the work in pre-order.**

## ⚡ DOWN vs. UP — The Twin Comparison

| Pattern | Needs info from... | Pipe | Work happens in... |
|---------|-------------------|------|-------------------|
| **Good Nodes** | ANCESTORS | DOWN (parameter) | PRE-order |
| **Diameter / Depth** | DESCENDANTS | UP (return) | POST-order |

**Quick rule:**
- Problem says *"from the root"*, *"on the path so far"*, *"ancestor"* → DOWN/parameter/pre-order
- Problem says *"height"*, *"deepest"*, *"subtree"*, *"below"* → UP/return/post-order

## Mind-map anchor
**`carry maxSoFar down` · `judge in pre-order` · "ancestor ⇒ parameter"**

---

# PATTERN 3: The Structural Trio — Invert, Same Tree, Is Mirror

*(Three problems, ONE idea: recurse on TWO things in parallel, or transform the current node then recurse. Grouping them shows you the family resemblance so they stop cluttering your brain.)*

## 3A. Invert Binary Tree (a "do-then-recurse" transform)

### The Story
Hold the tree up to a mirror. Every left child becomes a right child and vice versa, all the way down. To do this, at each node you just **swap your two children**, then tell both children to do the same to themselves.

### Walk the 6-step template
> Ask → Answer → Why.

1. **PARAMETERS — Ask:** *"Ancestor info needed?"* **Answer:** No, just `node`. **Why:** Mirroring is a local operation — swap my kids — independent of anything above.
2. **BASE CASE — Ask:** *"Smallest tree?"* **Answer:** `node == null` → return `null`. **Why:** An empty tree mirrored is still empty. Nothing to swap.
3. **PRE-ORDER — Ask:** *"Do work before children?"* **Answer:** Swap `node.left` and `node.right` right now. **Why:** Swapping is symmetric — pre or post both work — but doing it here reads naturally as "flip me, then tell my (new) kids to flip themselves."
4. **GO LEFT / RIGHT — Ask:** *"What do I ask each child?"* **Answer:** `invert(node.left)`, `invert(node.right)` — each mirrors itself. **Why:** Mirroring must reach every level; trust each child to mirror its own subtree.
5. **POST-ORDER — Ask:** *"Combine child results?"* **Answer:** Nothing to combine. **Why:** There's no value to aggregate; the mutation already happened in place.
6. **RETURN — Ask:** *"What does my parent need?"* **Answer:** `node` itself (now mirrored). **Why:** So the caller receives the transformed subtree root.

```java
TreeNode invert(TreeNode node) {
    if (node == null) return null;             // 2. base case
    TreeNode tmp = node.left;                  // 3. swap children
    node.left = node.right;
    node.right = tmp;
    invert(node.left);                         // 4. recurse both sides
    invert(node.right);
    return node;                               // 6. return the (now mirrored) node
}
```

### The "aha" line
> "Invert = swap my two kids, then ask both kids to invert themselves."

---

## 3B. Same Tree & 3C. Is Mirror (parallel two-node recursion)

Here the recursion walks **two nodes at once**. This is the key new move: the function takes **two** node arguments and steps them in lockstep.

### The Story
- **Same Tree:** Put two trees side by side. Are they identical? Check the two roots match, then check "left-vs-left" and "right-vs-right."
- **Is Mirror (Symmetric Tree):** Is one tree the mirror of the other? Check the two roots match, then check "left-vs-**right**" and "right-vs-**left**" — the cross pairing. That single flip (left↔right) is the *only* difference between Same and Mirror.

### Walk the 6-step template (two-node version)
> Ask → Answer → Why. **The new move: the function takes TWO nodes and steps them in lockstep.**

1. **PARAMETERS — Ask:** *"What do I need to carry?"* **Answer:** **Two** nodes, `a` and `b` (one from each tree, or the two branches being compared). **Why:** The question is about a *relationship between two positions*, so I must hold both at once. This is the tell for "comparison" problems.
2. **BASE CASE — Ask:** *"Which tiny inputs give an instant true/false?"* **Answer:** both `null` → `true`; exactly one `null` → `false`; then values differ → `false`. **Why:** Two empties match. One empty vs a node is a structural mismatch. Order matters: the null checks must come *first* so the value check never touches a null.
3. **PRE-ORDER — Ask:** *"Work before recursing?"* **Answer:** Compare `a.val == b.val`. **Why:** If the current pair already disagrees, I can fail fast without exploring below.
4. **GO LEFT / RIGHT — Ask (THE pattern-defining question):** *"How do I pair up the children?"*
   **Answer:** Same Tree → `(a.left, b.left)` and `(a.right, b.right)`. Mirror → `(a.left, b.right)` and `(a.right, b.left)` — **the cross.**
   **Why:** For *identical*, matching positions align directly (left with left). For *mirror image*, my left should equal the other's right. That single crossing of arguments is the entire difference between the two problems.
5. **POST-ORDER — Ask:** *"Combine the two child booleans?"* **Answer:** AND them together. **Why:** The whole comparison holds only if the current pair matches **and** both recursive pairings match. One false anywhere collapses to false.
6. **RETURN — Ask:** *"What goes up?"* **Answer:** the AND result. **Why:** Each level reports "everything below me is consistent" upward until the root gives the final verdict.

```java
// Same Tree
boolean isSame(TreeNode a, TreeNode b) {
    if (a == null && b == null) return true;       // 2. both empty → equal
    if (a == null || b == null) return false;      // 2. one empty → not equal
    if (a.val != b.val) return false;              // 3. values must match
    return isSame(a.left,  b.left)                 // 4. left-vs-left
        && isSame(a.right, b.right);               // 4. right-vs-right
}

// Symmetric Tree = mirror of itself
boolean isSymmetric(TreeNode root) {
    return root == null || isMirror(root.left, root.right);
}
boolean isMirror(TreeNode a, TreeNode b) {
    if (a == null && b == null) return true;
    if (a == null || b == null) return false;
    if (a.val != b.val) return false;
    return isMirror(a.left,  b.right)              // 4. THE CROSS: left-vs-right
        && isMirror(a.right, b.left);              // 4. right-vs-left
}
```

### The "aha" line
> "Same Tree and Mirror are the SAME code — the only difference is Mirror crosses the recursive calls (left with right)."

### The classic trap
Checking `a.val == b.val` **before** the null checks. If one node is null, `a.val` throws a NullPointerException. Always do the null base cases first, values after.

## Mind-map anchor for the whole trio
**Invert = "swap kids" · Same = "L-L, R-R" · Mirror = "L-R, R-L (the cross)"**

---

# PATTERN 4: Lowest Common Ancestor (LCA)

*(The famous "return a node, not a number" pattern. The return value is a TreeNode, and its meaning changes depending on where you are.)*

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Two cousins, `p` and `q`, are lost somewhere in a family tree. You want to find their closest shared ancestor. Here's the trick: every person asks their two kids, "Did you find either p or q down there?"

- If your LEFT kid says "I found one" AND your RIGHT kid says "I found one" → p and q are on OPPOSITE sides of you → **YOU are the meeting point!**
- If only ONE side found something → the answer is somewhere on that side, so just pass it up.

**The Key Insight:** The return value has TWO meanings:
1. Low in the tree: "I found a target!"
2. Higher up: "Here's the LCA!"

Same return slot, layered meaning.

## What the interviewer is really testing
Comfort with a recursion whose **return value is a node**, and whose meaning changes depending on context.

## Walk the 6-step template

1. **PARAMETERS — "What do I carry?"**
   **Answer:** `node`, plus the two targets `p` and `q`.
   **Why:** Every node must know WHAT it's hunting for to report a find.

2. **BASE CASE — "When do I stop?"**
   **Answer:** 
   - `node == null` → return `null` (nothing here)
   - `node == p || node == q` → return `node` ("I found a target!")
   **Why:** Finding a target is itself a stopping point — no need to search below.

3. **PRE-ORDER — "Work before kids?"**
   **Answer:** The found-check above IS the pre-order work.
   **Why:** I decide "am I a target?" before bothering to search below.

4. **GO LEFT / RIGHT — "What comes back from each side?"**
   **Answer:** `left = lca(node.left)`, `right = lca(node.right)` — each is either a found node or `null`.
   **Why:** I need to know WHICH sides contain a target to decide if I'm the meeting point.

5. **POST-ORDER — "Given the two reports, am I the answer?"**
   **Answer:** If `left != null && right != null` → the two targets were found on OPPOSITE sides of me → **I am the LCA**, return `node`.
   **Why:** The lowest node that has one target in its left subtree and the other in its right subtree IS by definition their lowest common ancestor.

6. **RETURN — "If I'm not the meeting point, what do I send up?"**
   **Answer:** Whichever side is non-null (bubble the single find upward); if both null, return null.
   **Why:** A single non-null means "a target (or the already-found LCA) lives below me on this side" — I forward that upward so an ancestor can pair it with the other target.

## The code
```java
TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {
    // STOP: empty, or I found a target
    if (node == null || node == p || node == q) return node;
    
    TreeNode left  = lca(node.left,  p, q);   // Search left
    TreeNode right = lca(node.right, p, q);   // Search right
    
    // Both sides found something → I'm the meeting point!
    if (left != null && right != null) return node;
    
    // Only one side found something → pass it up
    return (left != null) ? left : right;
}
```

## Tiny dry run — LCA of 5 and 1
```
        3
       / \
      5   1
     / \
    6   2
```
- `lca(6)=null`, `lca(2)=null` → at node 5, left=null, right=null, but 5==p → **returns 5** (base case fires).
- Right side: `lca(1)` → 1==q → returns 1.
- At root 3: left=5 (non-null), right=1 (non-null) → **both found → return 3**. ✓

## The "aha" line
> "If p and q come back from opposite sides of me, I'm the meeting point. Otherwise I just forward whichever kid found something."

## The classic trap
Writing `if (left != null) return left;` BEFORE checking if right is also non-null. If BOTH are non-null, you must return the CURRENT node, not just the left. Always test `left != null && right != null` FIRST.

> **BST shortcut:** In a Binary Search Tree, LCA is simpler — walk down: if both `p,q < node`, go left; if both `> node`, go right; the moment they split (or one equals node), that node is the LCA. O(h), no post-order needed.

## Mind-map anchor
**`node==target ⇒ return node` · "both sides non-null ⇒ I'm LCA" · else bubble up**

---

# PATTERN 5: Inorder Successor in a BST ("the next bigger node")

*(This is the "walk in sorted order, find who comes right after me" pattern. It leans on the golden BST fact: inorder traversal of a BST is sorted ascending.)*

## The Story
Line everyone up in **sorted order** (that's exactly what a BST inorder traversal gives you). The **inorder successor** of a node `p` is simply the **very next person in that sorted line** — the smallest value that is still **strictly greater** than `p`.

There are two flavors. Know both — interviewers pick one:

### Flavor A — you only have the value/target and the root (BST search style)
Walk down from the root like a search, keeping a "best candidate so far":
- If `node.val > p.val`: this node **could** be the successor (it's bigger). Record it as a candidate, then go **left** to try to find an even smaller-but-still-bigger one.
- If `node.val <= p.val`: too small or equal, the successor must be bigger, go **right** and don't record.

```java
TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
    TreeNode candidate = null;
    TreeNode node = root;
    while (node != null) {
        if (node.val > p.val) {          // node is bigger → possible successor
            candidate = node;            // remember it
            node = node.left;            // try to find a tighter (smaller) one
        } else {
            node = node.right;           // node too small/equal → go bigger
        }
    }
    return candidate;                    // tightest node that was > p, or null
}
```
This is **O(h)** — no full traversal needed. This is the pro answer for a BST.

### Flavor B — the node has a parent pointer, OR you reason by BST structure
- **If `p` has a right subtree:** the successor is the **leftmost (smallest) node of that right subtree**. (Go right once, then all the way left.)
- **If `p` has no right subtree:** the successor is the **lowest ancestor for which `p` is in its left subtree** — i.e., walk up via parent pointers until you move up-and-to-the-right.

```java
TreeNode successorWithParent(Node p) {
    if (p.right != null) {               // case 1: leftmost of right subtree
        Node cur = p.right;
        while (cur.left != null) cur = cur.left;
        return cur;
    }
    Node cur = p;                        // case 2: climb until we go up-right
    while (cur.parent != null && cur == cur.parent.right) cur = cur.parent;
    return cur.parent;                   // may be null if p is the maximum
}
```

## Walk the 6-step template (mapping Flavor A onto the skeleton)
> Ask → Answer → Why. **This one bends the template: it's a directed WALK (like binary search), not a two-child recursion. Seeing how the same 6 questions still apply is the lesson.**

1. **PARAMETERS — Ask:** *"What state must survive the walk down?"* **Answer:** `node`, target `p`, and a running `candidate` (best successor found so far). **Why:** The answer is built up *as I descend*, so I carry the best-so-far like a torch. The BST's sorted structure is my ancestor context — I don't need an explicit "max so far," the tree shape encodes it.
2. **BASE CASE — Ask:** *"When do I stop?"* **Answer:** `node == null` → stop; the answer is whatever `candidate` holds. **Why:** Falling off the tree means I've narrowed the search as far as possible; the last recorded candidate is the tightest successor.
3. **PRE-ORDER — Ask:** *"What decision do I make at this node before moving on?"* **Answer:** Compare `node.val` to `p.val` and **choose a direction**. **Why:** This comparison is the pre-order judgment — it uses only the current node and target (no child info), so it belongs before any descent.
4. **GO LEFT / RIGHT — Ask:** *"Do I explore both sides?"* **Answer:** No — exactly **one** side. If `node.val > p.val`: record `candidate = node`, go **left** (hunt for a smaller-yet-still-bigger value). Else: go **right**. **Why:** This is the crucial deviation from normal tree recursion. BST order lets me *prune* an entire half each step — that's why it's O(h), not O(n). A bigger node is a valid successor candidate, but maybe a tighter one hides to its left.
5. **POST-ORDER — Ask:** *"Any combine step?"* **Answer:** None. **Why:** The answer is captured on the way *down*, not assembled from children — there are no two children to merge.
6. **RETURN — Ask:** *"Final answer?"* **Answer:** `candidate`. **Why:** It holds the smallest value that was still strictly greater than `p` — the definition of the inorder successor.

## The "aha" line
> "Successor = the smallest value still bigger than me. In a BST I binary-search downward, remembering the last node bigger than p — that last remembered one is the answer."

## The classic trap
Forgetting the **no-right-subtree** case in Flavor B (people always remember "go right then left" and forget the "climb the ancestors" case). And in Flavor A, using `>=` instead of `>` — the successor must be **strictly** greater.

## Mind-map anchor
**"next in sorted line" · `right? leftmost-of-right` · `no right? climb up-right` · A: `remember last >p`**

---

# PATTERN 6: Largest BST Subtree (the MULTI-VALUE RETURN pattern)

*(Until now children returned ONE number. Here a child must return a BUNDLE of facts. This is the capstone of bottom-up thinking — once you get this, "return an object" becomes a tool you reach for confidently.)*

## The Story
Somewhere inside a big messy binary tree, there may be a chunk that happens to be a **valid BST**. You want the **largest** such chunk (most nodes). To know if *I* am the root of a valid BST, one number from my children isn't enough. I need to know four things from each side:
1. Is that side **itself** a valid BST?
2. What's the **min** value down there?
3. What's the **max** value down there?
4. How many **nodes** are down there (its size)?

Then I am a valid BST only if: left is a BST, right is a BST, **and** `left.max < my.val < right.min`. That's the BST ordering rule enforced at the seam.

## What the interviewer is really testing
Can you make the recursion **return a small object/tuple** instead of a single value, because the parent needs *several* facts at once? This is the moment you graduate from "return an int" to "return a struct."

## Walk the 6-step template
> Ask → Answer → Why. **The star: step 6 returns a BUNDLE, not a number. And step 2's null identity is a genuinely tricky design choice.**

1. **PARAMETERS — Ask:** *"Ancestor info needed?"* **Answer:** No — just `node`. **Why:** Whether a subtree is a BST depends entirely on what's *inside* it (descendants), so it's pure bottom-up. The global `best` size lives outside.
2. **BASE CASE — Ask:** *"What does an empty subtree report — and what values make the parent's check just work?"* **Answer:** `isBST=true, size=0, min=+∞, max=−∞`. **Why (the subtle part):** An empty side is trivially a valid BST of size 0. The infinities are a *neutral identity*: a parent checks `left.max < node.val < right.min`; with `left.max = −∞` and `right.min = +∞`, both comparisons pass automatically, so a leaf isn't wrongly rejected. This is the classic "pick identity values so the general formula needs no special-casing" trick.
3. **PRE-ORDER — Ask:** *"Work before children?"* **Answer:** None. **Why:** I can't judge myself until I hear back from both subtrees — this is inherently post-order.
4. **GO LEFT / RIGHT — Ask:** *"What do I get from each child?"* **Answer:** A full **bundle** `{isBST, size, min, max}` from each side. **Why:** One number can't tell me if I'm a BST. I need *four* facts per side, so children must return a small object.
5. **POST-ORDER — Ask (THE key):** *"How do I decide if I'm a valid BST and update the answer?"* **Answer:** I'm a BST iff `left.isBST && right.isBST && left.max < node.val && node.val < right.min`. If so: my `size = left.size + right.size + 1`, update `best = max(best, size)`, my `min = min(node.val, left.min)`, my `max = max(node.val, right.max)`. **Why:** The BST rule is enforced *at the seam* between my two subtrees — everything on my left must be smaller than me, everything on my right larger. I only know that once both bundles are in.
6. **RETURN — Ask:** *"What does my parent need — and what if I'm broken?"* **Answer:** If valid, return my assembled bundle. If not, return an `isBST=false` bundle. **Why:** A parent needs my four facts to run its own seam-check. And a broken subtree must *poison* all ancestors — once BST-ness breaks below you, no ancestor can be a BST either. The `isBST=false` flag propagates that truth upward.

## The code
```java
class Info {
    boolean isBST;
    int size, min, max;
    Info(boolean b, int s, int mn, int mx) { isBST=b; size=s; min=mn; max=mx; }
}

int best = 0;

int largestBSTSubtree(TreeNode root) {
    dfs(root);
    return best;
}

Info dfs(TreeNode node) {
    if (node == null)                                       // 2. neutral identity
        return new Info(true, 0, Integer.MAX_VALUE, Integer.MIN_VALUE);

    Info left  = dfs(node.left);                            // 4. bundle from left
    Info right = dfs(node.right);                           // 4. bundle from right

    // 5. post-order: am I a valid BST at the seam?
    if (left.isBST && right.isBST
            && node.val > left.max && node.val < right.min) {
        int size = left.size + right.size + 1;
        best = Math.max(best, size);
        int mn = Math.min(node.val, left.min);
        int mx = Math.max(node.val, right.max);
        return new Info(true, size, mn, mx);                // 6. valid bundle up
    }
    return new Info(false, 0, 0, 0);                        // 6. broken → poison up
}
```

## The "aha" line
> "One number wasn't enough, so my children hand me a *bundle* — isBST, size, min, max — and I check the BST rule right at the seam between my two kids."

## The classic trap
Wrong identity for the null bundle. Using `min=+∞, max=−∞` for empty is what makes `node.val > left.max` and `node.val < right.min` **automatically true** for a leaf. Flip them and every leaf wrongly fails. Also: a broken subtree must return `isBST=false` so it correctly disqualifies every ancestor.

> **Same skeleton, cousin problems:** `isBalanced` (return `{height, isBalanced}`), `isValidBST` (return `{min,max,isBST}`), "House Robber III" (return `{robThis, skipThis}`). The instant you need **two-or-more facts** from a child, reach for the **Info-object return**.

## Mind-map anchor
**"return a bundle {isBST,size,min,max}" · check `L.max < val < R.min` · null = `+∞/−∞` identity**

---

# PATTERN 7: Distance Between Two Nodes (LCA + depth, composed)

*(A "compose two patterns you already know" problem. This teaches you that hard problems are often just LEGO built from easy ones. You already have all the bricks.)*

## The Story
You want the number of edges on the path from `p` to `q`. Picture the tree as a subway map. To travel from station p to station q, you go **up** to the first shared junction — that junction is the **Lowest Common Ancestor** — and then **down** to q. So the total distance is:

```
distance(p, q) = depth(p) + depth(q) − 2 × depth(LCA(p, q))
```

Why subtract twice the LCA depth? Because the path from the root down to the LCA is shared by both p and q — you counted it once in `depth(p)` and once in `depth(q)`, so you remove **both** copies.

## What the interviewer is really testing
Can you **decompose** a new problem into patterns you already own? Distance = **LCA** (Pattern 4) + **depth measurement** (Pattern 0). No new machinery — just composition.

## The step-by-step recipe
1. Find `L = LCA(root, p, q)` — reuse Pattern 4 exactly.
2. Measure `d1 =` distance (levels) from `L` down to `p`.
3. Measure `d2 =` distance from `L` down to `q`.
4. Answer = `d1 + d2`.

(Equivalently, measure depths from the root and use the formula above. Both are correct; measuring from the LCA avoids the "×2" bookkeeping.)

## Walk the 6-step template (applied to the helper `depthFrom`)
> Ask → Answer → Why. **The composition itself isn't recursive magic — it's LCA + a "find depth of a target" search. Here's that search through the 6 questions.**

1. **PARAMETERS — Ask:** *"What do I carry down?"* **Answer:** `node`, the `target` value, and the `depth` accumulated so far. **Why:** Depth is *distance from the start node*, so I count it downward as a parameter — a classic DOWN-pipe counter, just like Good Nodes carried `maxSoFar`.
2. **BASE CASE — Ask:** *"When do I stop?"* **Answer:** `node == null` → return `-1` (not found here). `node.val == target` → return `depth`. **Why:** `-1` is a sentinel meaning "this branch doesn't contain the target"; a real depth means "found it, here's how deep."
3. **PRE-ORDER — Ask:** *"Work before children?"* **Answer:** The found-check. **Why:** If this node is the target, no need to go deeper.
4. **GO LEFT / RIGHT — Ask:** *"How do I search both sides?"* **Answer:** Try left with `depth+1`; if it returned something other than `-1`, use it; else try right. **Why:** The target is in exactly one subtree — short-circuit the moment the left side finds it.
5. **POST-ORDER — Ask:** *"Combine?"* **Answer:** Pick the non-`-1` result. **Why:** Only one side can hold the target; forward whichever found it.
6. **RETURN — Ask:** *"What goes up?"* **Answer:** The found depth, or `-1`. **Why:** So the two top-level calls (from the LCA) yield `d1` and `d2`, and we add them.

## The code
```java
int findDistance(TreeNode root, int p, int q) {
    TreeNode lca = lca(root, p, q);           // Pattern 4 reused
    int d1 = depthFrom(lca, p, 0);            // levels from LCA down to p
    int d2 = depthFrom(lca, q, 0);            // levels from LCA down to q
    return d1 + d2;
}

// returns how many edges from 'node' down to target value, or -1 if not found
int depthFrom(TreeNode node, int target, int depth) {
    if (node == null) return -1;
    if (node.val == target) return depth;
    int left = depthFrom(node.left, target, depth + 1);
    if (left != -1) return left;              // found on the left → use it
    return depthFrom(node.right, target, depth + 1);   // else try right
}

TreeNode lca(TreeNode node, int p, int q) {
    if (node == null || node.val == p || node.val == q) return node;
    TreeNode l = lca(node.left, p, q);
    TreeNode r = lca(node.right, p, q);
    if (l != null && r != null) return node;
    return l != null ? l : r;
}
```

## Tiny dry run
```
        1
       / \
      2   3
     / \
    4   5
```
Distance(4, 5): `LCA(4,5) = 2`. From 2: depth to 4 = 1, depth to 5 = 1. Answer = **2** (path `4 → 2 → 5`). ✓
Distance(4, 3): `LCA(4,3) = 1`. From 1: depth to 4 = 2, depth to 3 = 1. Answer = **3** (path `4 → 2 → 1 → 3`). ✓

## The "aha" line
> "Go up to the LCA, then down to the target. Distance is just depth-from-LCA to p plus depth-from-LCA to q."

## The classic trap
Measuring depths from the **root** but forgetting the `− 2 × depth(LCA)` correction, so you double-count the shared trunk. Measuring **from the LCA** sidesteps this entirely — that's why it's the cleaner framing.

## Mind-map anchor
**"up to LCA, then down" · `dist = dLCA→p + dLCA→q` · reuse LCA + depth**

---

# PATTERN 8: Maximum Path Sum (Diameter's richer twin)

*(Structurally IDENTICAL to Diameter — same "answer vs return" split — but with two new wrinkles: values can be negative, and we sum instead of count edges. If you nailed Diameter, this is a 5-minute win.)*

## The Story
Find the path with the biggest total value. A path can start and end **anywhere** and bends at its highest node. At each node, the best path *bending here* is `node.val + best-gain-from-left + best-gain-from-right`. But here's the negativity wrinkle: if a child's best contribution is **negative**, you'd rather take **nothing** from that side — so you clamp each child's gain at 0 with `Math.max(0, childGain)`.

## Walk the 6-step template
> Ask → Answer → Why. **This is Diameter's twin — same steps 5/6 split — plus one new reflex: clamp negatives.**

1. **PARAMETERS — Ask:** *"Ancestor info?"* **Answer:** No — just `node`; global `maxSum` outside. **Why:** The best path is a bottom-up property; the winner might bend anywhere, so we track it globally.
2. **BASE CASE — Ask:** *"Smallest input?"* **Answer:** `node == null` → return `0` gain. **Why:** An absent child contributes zero to any sum passing through me.
3. **PRE-ORDER — Ask:** *"Work before kids?"* **Answer:** None. **Why:** Pure bottom-up.
4. **GO LEFT / RIGHT — Ask (the new reflex):** *"What do I take from each child?"* **Answer:** `leftGain = max(0, gain(left))`, `rightGain = max(0, gain(right))` — clamp each at 0. **Why:** A child path with a *negative* total only hurts me. Taking `max(0, …)` means "if your best contribution is negative, I'll take nothing from you instead." This clamp is the one idea Diameter didn't need (edge counts are never negative, but values can be).
5. **POST-ORDER — Ask:** *"Candidate ANSWER through me?"* **Answer:** `maxSum = max(maxSum, node.val + leftGain + rightGain)` — the path that *bends* at me. **Why:** Same bend logic as Diameter, but summing values instead of counting edges.
6. **RETURN — Ask:** *"What can my parent extend?"* **Answer:** `node.val + max(leftGain, rightGain)` — me plus my *better* single arm. **Why:** Identical "no fork" rule as Diameter: a path continuing up through me may use only one of my arms. The both-arms sum is a terminal answer; the one-arm value is the extendable return.

```java
int maxSum = Integer.MIN_VALUE;

int maxPathSum(TreeNode root) {
    gain(root);
    return maxSum;
}

int gain(TreeNode node) {
    if (node == null) return 0;                         // 2. base
    int leftGain  = Math.max(0, gain(node.left));       // 4. clamp negatives
    int rightGain = Math.max(0, gain(node.right));      // 4.
    maxSum = Math.max(maxSum, node.val + leftGain + rightGain); // 5. bend = ANSWER
    return node.val + Math.max(leftGain, rightGain);    // 6. one side = RETURN
}
```

## The "aha" line
> "It's Diameter with money: ANSWER through both sides, RETURN one side — but clamp negative children to zero because a bad branch is optional."

## The classic trap
Initializing `maxSum = 0`. If every node is negative (e.g. all `-3`), the answer is the least-negative single node, not 0. Start at `Integer.MIN_VALUE`.

## Mind-map anchor
**"Diameter with values" · `clamp max(0,child)` · answer=both, return=one**

---

# PATTERN 9: Balanced Binary Tree (the "height + a flag" bundle, mini version)

## The Story
A tree is height-balanced if, at **every** node, the left and right subtree heights differ by **at most 1**. The naive way recomputes height repeatedly (O(n²)). The pro way: compute height **and** check balance in a single post-order pass, using a sentinel `−1` to mean "already unbalanced below."

## Walk the 6-step template
> Ask → Answer → Why. **The trick: overload the return value to carry TWO meanings — a height, or a "-1 = broken" flag.**

1. **PARAMETERS — Ask:** *"Ancestor info?"* **Answer:** No — just `node`. **Why:** Balance depends on subtree heights below, so bottom-up.
2. **BASE CASE — Ask:** *"Smallest input?"* **Answer:** `null` → height `0`. **Why:** Empty is perfectly balanced, height 0.
3. **PRE-ORDER — Ask:** *"Work before kids?"* **Answer:** None. **Why:** Need child heights first.
4. **GO LEFT / RIGHT — Ask:** *"What do I get, and can I bail early?"* **Answer:** Get each child's height; if either is `-1`, immediately return `-1` (short-circuit). **Why:** Once any subtree is unbalanced, the whole tree is — no point computing further. Propagating `-1` upward carries that verdict cheaply.
5. **POST-ORDER — Ask:** *"How do I judge balance here?"* **Answer:** If `abs(leftH - rightH) > 1`, return `-1` (I'm unbalanced). **Why:** The balance definition is checked at *every* node using its two child heights — a post-order combine.
6. **RETURN — Ask:** *"What goes up?"* **Answer:** If balanced, `1 + max(leftH, rightH)` (normal height); else `-1`. **Why:** This single return slot does double duty — a real height means "balanced so far, here's my height for your check," and `-1` is a poison flag meaning "already broken below." Overloading the return this way turns an O(n²) recompute into one O(n) pass. (Same "overloaded return" idea as LCA's node, and cousin to Largest BST's bundle.)

```java
boolean isBalanced(TreeNode root) {
    return check(root) != -1;
}

int check(TreeNode node) {
    if (node == null) return 0;                    // 2. base
    int left = check(node.left);                   // 4.
    if (left == -1) return -1;                     // short-circuit up
    int right = check(node.right);                 // 4.
    if (right == -1) return -1;
    if (Math.abs(left - right) > 1) return -1;     // 5. unbalanced here → poison
    return 1 + Math.max(left, right);              // 6. normal height up
}
```

## The "aha" line
> "I overload the return: a real height means 'balanced so far,' and `−1` is a secret flag for 'already broken.' One pass, O(n)."

## Mind-map anchor
**"height OR −1 flag" · `abs(L−R)>1 ⇒ −1` · single post-order pass**

---

# PATTERN 10: Path Sum II (the BACKTRACKING pattern — the one true mutation)

*(Every pattern so far returned values up. This one is different: it MUTATES a shared list on the way down and UNDOES the mutation on the way up. This is real backtracking, and it's the last big mental category.)*

## The Story
Find **all** root-to-leaf paths that sum to a target. You walk down carrying a growing list `path`. At a leaf, if the running sum matches, you **snapshot** the path into the results. Crucially, when you finish exploring a node and step back up, you **remove yourself** from the path so your sibling branches start clean. That "add, explore, remove" dance is backtracking.

## Walk the 6-step template (with the mutation twist)
> Ask → Answer → Why. **The one pattern that legitimately moves state DOWN and cleans it UP via a shared object. This is backtracking, and it's a different beast from post-order aggregation.**

1. **PARAMETERS — Ask:** *"What travels down, and how does state come back clean?"* **Answer:** `node`, the `remaining` target, a **shared mutable `path` list**, and the `result` collector. **Why:** The `path` is the one sanctioned exception to "parameters only go down." It's a shared object: I *push* onto it going down and *pop* coming up, so the same list is reused for every branch. This is the only clean way to carry an evolving path.
2. **BASE CASE — Ask:** *"When do I stop?"* **Answer:** `node == null` → return. **Why:** Nothing to add or check at an empty spot.
3. **PRE-ORDER — Ask:** *"Work before kids?"* **Answer:** `path.add(node.val)` — record myself. **Why:** I must be *on* the path before my children extend it, so adding happens on the way in (pre-order).
4. **GO LEFT / RIGHT — Ask:** *"What do I hand each child?"* **Answer:** `remaining - node.val`, same shared `path`. **Why:** Each child continues the same partial path with a reduced target. I trust them to explore and collect any matches below.
5. **POST-ORDER — Ask:** *"After exploring, what two things must I do?"* **Answer:** (a) At a leaf where `remaining == node.val`, snapshot `new ArrayList<>(path)` into `result`. (b) After **both** children return, `path.remove(last)` — undo my own addition. **Why (the crux):** The snapshot must be a **copy**, because the shared list keeps mutating. The removal is **backtracking** — I erase myself so my *sibling* branches don't inherit my node. This is NOT post-order aggregation (I'm not combining child return values); it's *state restoration* on a shared structure. Same position after the children, completely different purpose.
6. **RETURN — Ask:** *"What goes up?"* **Answer:** Nothing (void) — answers are collected in the shared `result`. **Why:** The output isn't a single value to bubble up; it's a growing list of full paths, gathered via the shared collector.

```java
List<List<Integer>> pathSum(TreeNode root, int target) {
    List<List<Integer>> result = new ArrayList<>();
    dfs(root, target, new ArrayList<>(), result);
    return result;
}

void dfs(TreeNode node, int remaining, List<Integer> path, List<List<Integer>> result) {
    if (node == null) return;                                // 2. base
    path.add(node.val);                                      // 3. pre-order: add me
    if (node.left == null && node.right == null && remaining == node.val)
        result.add(new ArrayList<>(path));                   // 5. leaf match → SNAPSHOT
    dfs(node.left,  remaining - node.val, path, result);     // 4. go left
    dfs(node.right, remaining - node.val, path, result);     // 4. go right
    path.remove(path.size() - 1);                            // 5. BACKTRACK: undo me
}
```

## The "aha" line
> "Add myself on the way in, snapshot at a matching leaf, remove myself on the way out. The single shared list is my scratchpad; backtracking keeps it honest for siblings."

## The two classic traps
1. **Forgetting the snapshot** — you must add `new ArrayList<>(path)`, a **copy**. If you add `path` directly, later mutations corrupt your stored answer (they all point to the same list).
2. **Removing in the wrong place** — the `path.remove(...)` goes **after both** recursive calls, exactly once per node. Put it after each call and you remove yourself twice.

> **This is the difference the correction in your template flagged:** *post-order aggregation* (combining child return values, like Diameter) is **not** the same as *backtracking* (undoing a mutation to a shared structure, like `path.remove()`). Same "after the children" position, totally different purpose. Say it out loud until it's obvious.

## Mind-map anchor
**"add → explore → remove" · snapshot a COPY at leaf · undo once, after both kids**

---

# PATTERN 11: Validate BST (the RANGE-pushed-DOWN pattern — Good Nodes' twin)

*(This is the second great DOWN-pipe pattern. Good Nodes pushed a single "max so far" down. Validate BST pushes a whole (low, high) window down. If you understand Good Nodes, this is the same reflex with two bounds instead of one.)*

## The Story
A binary tree is a valid BST if **every** node obeys: everything in my left subtree is smaller than me, everything in my right is larger — and this must hold *against all ancestors*, not just my parent. The clean way: as you walk down, carry a **legal window `(low, high)`** that this node's value must fall inside. When you go left, the window's ceiling tightens to the current value (left kids must be smaller than me). When you go right, the window's floor rises to the current value.

## What the interviewer is really testing
Do you know that a **local** parent-child check is NOT enough? A node can be bigger than its parent yet still violate a grandparent's constraint. The fix is to push the *accumulated* legal range down as parameters.

## Walk the 6-step template
> Ask → Answer → Why.

1. **PARAMETERS — Ask:** *"What ancestor info must I carry?"* **Answer:** `low` and `high` — the open interval my value must lie strictly inside. **Why:** Validity is defined by *all* ancestors, and that accumulated constraint lives above me → push it down (just like Good Nodes' `maxSoFar`, but now it's a two-sided window).
2. **BASE CASE — Ask:** *"Smallest input?"* **Answer:** `node == null` → `true`. **Why:** An empty subtree can't violate anything.
3. **PRE-ORDER — Ask:** *"Work before kids, using the window?"* **Answer:** Check `low < node.val < high`; if it fails, return `false` immediately. **Why:** My legality depends only on ancestors (the window), so it's judged on the way *down* — pre-order.
4. **GO LEFT / RIGHT — Ask:** *"How does the window tighten for each child?"* **Answer:** Left child gets `(low, node.val)`; right child gets `(node.val, high)`. **Why:** Everything left of me must be below me → my value becomes the new ceiling. Everything right must be above me → my value becomes the new floor.
5. **POST-ORDER — Ask:** *"Combine?"* **Answer:** `left && right`. **Why:** The whole tree is a BST only if both subtrees are.
6. **RETURN — Ask:** *"Up?"* **Answer:** the AND. **Why:** Each node reports "everything below me is a valid BST within its window."

## The code
```java
boolean isValidBST(TreeNode root) {
    return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);   // widest window
}

boolean validate(TreeNode node, long low, long high) {
    if (node == null) return true;                           // 2. base
    if (node.val <= low || node.val >= high) return false;   // 3. window check
    return validate(node.left,  low, node.val)               // 4. tighten ceiling
        && validate(node.right, node.val, high);             // 4. raise floor
}
```

## The "aha" line
> "Don't compare parent-to-child. Carry the *legal window* down: going left drops the ceiling to me, going right lifts the floor to me."

## The classic trap
Comparing only `node.left.val < node.val < node.right.val` locally — this passes trees that are globally invalid (a deep-left grandchild can exceed a grandparent). Also: use `long` bounds (or handle equals carefully) so a node equal to `Integer.MIN/MAX_VALUE` doesn't break the check.

## Mind-map anchor
**"push (low,high) down" · left ⇒ high=val, right ⇒ low=val · "ancestor ⇒ parameter" (again)**

---

# PATTERN 12: BFS / Level-Order Family (the OTHER traversal mode)

*(Everything so far was DFS recursion. This is the big sibling: BFS with a queue, processing the tree LEVEL BY LEVEL. A huge fraction of MAANG tree questions are secretly "just do a level-order and tweak what you record." Learn the skeleton once; four problems fall out.)*

## The Story
Instead of diving deep, you sweep the tree **row by row**, like reading a book line by line. You keep a queue. The magic trick: **before** processing a level, you freeze its size (`int size = queue.size()`). That `size` is exactly how many nodes are on the current row. You process exactly that many, enqueuing their children (the next row) as you go. When the loop ends, the queue holds precisely the next level. Repeat.

## What the interviewer is really testing
Do you know when the answer depends on **levels / breadth / nearest** — signaling BFS instead of DFS? And can you use the `size`-freeze trick to keep levels separated?

## The ONE BFS skeleton (memorize this — all four problems reuse it)
```java
Queue<TreeNode> q = new ArrayDeque<>();
if (root != null) q.offer(root);
while (!q.isEmpty()) {
    int size = q.size();                 // FREEZE this level's node count
    for (int i = 0; i < size; i++) {     // process exactly one level
        TreeNode node = q.poll();
        // ---- do per-node work here (this is the only part that changes) ----
        if (node.left  != null) q.offer(node.left);
        if (node.right != null) q.offer(node.right);
    }
    // ---- do per-level work here (e.g., close off a level's list) ----
}
```

### 12A. Level Order Traversal — record every level as its own list
```java
List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    Queue<TreeNode> q = new ArrayDeque<>();
    if (root != null) q.offer(root);
    while (!q.isEmpty()) {
        int size = q.size();
        List<Integer> level = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode node = q.poll();
            level.add(node.val);                 // per-node: collect value
            if (node.left  != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }
        res.add(level);                          // per-level: close the row
    }
    return res;
}
```

### 12B. Right-Side View — the last node of each level
```java
// per-node change: if (i == size - 1) res.add(node.val);  // last in the row
```
**Why:** Standing on the right, you see the rightmost node of every row — that's the final node processed in each level loop.

### 12C. Minimum Depth — first leaf found, level by level
```java
// per-node change: if (node.left == null && node.right == null) return depth;
// increment depth once per level
```
**Why:** BFS reaches shallower nodes first, so the first leaf you hit is guaranteed the minimum depth. (DFS would explore a deep branch needlessly — BFS is the natural fit for "nearest/shortest.")

### 12D. Zigzag Level Order — alternate direction each level
```java
// per-level change: if the level index is odd, Collections.reverse(level);
```

## The "aha" line
> "One queue, freeze the level size, process exactly that many. Only the per-node and per-level lines change between problems."

## The classic trap
Forgetting to freeze `size` before the inner loop. If you loop `while (!q.isEmpty())` and poll without the fixed count, children mix into the current level and you lose all level boundaries.

## When BFS beats DFS (say this out loud)
> **"nearest," "shortest," "minimum depth," "level," "row," "view," "widest"** → BFS.
> **"height," "path," "subtree property," "all paths," "diameter"** → DFS.

## Mind-map anchor
**"queue + freeze size" · per-node vs per-level lines · "nearest/level ⇒ BFS"**

---

# PATTERN 13: Kth Smallest in a BST (INORDER + counter, early-stop)

*(The flagship of "BST inorder = sorted." Any question about the k-th smallest/largest, or validating sorted order, is this pattern. The new idea: stop the traversal early once you've counted enough.)*

## The Story
Inorder traversal of a BST spits out values in **ascending sorted order** (left, node, right). So the k-th smallest is simply the k-th value produced by an inorder walk. You don't need to finish — carry a countdown `k`, decrement it as you *visit* each node, and the moment it hits zero, that node is your answer. Stop immediately.

## What the interviewer is really testing
Do you *reflexively* connect "BST + order statistic" to "inorder traversal," and can you thread a mutable counter through recursion (or use an explicit stack for clean early-exit)?

## Walk the 6-step template
> Ask → Answer → Why.

1. **PARAMETERS — Ask:** *"What state threads through?"* **Answer:** `node` plus a shared mutable counter (an `int[]` or field holding `k` and the answer). **Why:** The count must persist across the whole traversal and survive returning up — that's a shared mutable object, like Path Sum II's list.
2. **BASE CASE — Ask:** *"When stop?"* **Answer:** `node == null`, or once `k` has hit 0 (answer found). **Why:** Null is the leaf boundary; the `k==0` check lets us short-circuit and skip the rest of the tree.
3. **PRE-ORDER — Ask:** *"Work before children?"* **Answer:** None — but note we go **left first**. **Why:** In inorder, the left subtree (all smaller values) must be fully counted before the current node.
4. **GO LEFT — then visit — then GO RIGHT — Ask:** *"What's the visiting order?"* **Answer:** Recurse left, then *visit* (decrement `k`; if it's now 0, record `node.val`), then recurse right. **Why:** This L-node-R order *is* ascending order in a BST. The "visit" work sits *between* the two recursive calls — the defining shape of inorder.
5. **POST-ORDER — Ask:** *"Combine?"* **Answer:** Nothing to combine numerically. **Why:** The answer is captured mid-traversal via the counter, not aggregated from children.
6. **RETURN — Ask:** *"Up?"* **Answer:** Void (answer lives in the shared holder). **Why:** Like Path Sum II, the output is collected externally, not bubbled as a return value.

## The code
```java
int kthSmallest(TreeNode root, int k) {
    int[] state = {k, -1};      // state[0] = countdown, state[1] = answer
    inorder(root, state);
    return state[1];
}

void inorder(TreeNode node, int[] state) {
    if (node == null || state[0] == 0) return;   // 2. base + early stop
    inorder(node.left, state);                   // 4. left (smaller values first)
    if (--state[0] == 0) { state[1] = node.val; return; } // 4. visit
    inorder(node.right, state);                  // 4. right (larger values)
}
```

## The "aha" line
> "BST + k-th anything = inorder traversal with a countdown. Stop the instant the counter hits zero."

## The classic trap
Collecting the *entire* inorder list then indexing `list.get(k-1)` — correct but O(n) space and no early exit. The counter version stops after k visits. For k-th *largest*, just do reverse inorder (right, node, left).

## Mind-map anchor
**"inorder = sorted" · countdown k, stop at 0 · reverse for k-th largest**

---

# PATTERN 14: Serialize & Deserialize (PREORDER encode + queue decode)

*(The "turn a tree into a string and back" archetype. Two mirrored recursions. The key idea: null markers preserve the exact shape, and a token queue rebuilds it in the same order it was written.)*

## The Story
To save a tree as text, walk it in **preorder** (root, then left, then right) and write each value — but critically, write a marker like `#` for every null too. Those null markers are what let you reconstruct the *exact* shape later. To rebuild, read the tokens in the **same preorder**: the first token is the root, then recursively build its left subtree, then its right, consuming tokens from a queue as you go.

## What the interviewer is really testing
Can you design an encoding that captures structure (not just values), and can you write two recursions that are exact mirrors — one producing tokens, one consuming them in the same order?

## Walk the 6-step template (for `serialize`)
> Ask → Answer → Why.

1. **PARAMETERS — Ask:** *"Carry?"* **Answer:** `node` and a `StringBuilder`. **Why:** We append to one shared buffer as we walk.
2. **BASE CASE — Ask:** *"Smallest input?"* **Answer:** `node == null` → append `"#,"`. **Why:** The null marker records "nothing here," preserving shape.
3. **PRE-ORDER — Ask:** *"Work before children?"* **Answer:** Append `node.val + ","` FIRST. **Why:** Root-first is the definition of preorder; decode must see the root before its subtrees.
4. **GO LEFT / RIGHT — Ask:** *"Order?"* **Answer:** Serialize left subtree, then right. **Why:** A fixed, agreed order so decode can mirror it.
5/6. **POST-ORDER / RETURN:** Nothing to combine; the string is the shared output.

## Walk the 6-step template (for `deserialize`)
1. **PARAMETERS:** a **queue of tokens** (shared cursor into the string).
2. **BASE CASE:** next token is `"#"` → return `null` (rebuild the null we recorded).
3. **PRE-ORDER:** read one token = this node's value, create the node — *before* building children (mirrors how we wrote root first).
4. **GO LEFT / RIGHT:** recursively build `node.left` then `node.right`, consuming tokens in order.
5/6. **RETURN:** the reconstructed `node` up to its parent.

## The code
```java
String serialize(TreeNode root) {
    StringBuilder sb = new StringBuilder();
    build(root, sb);
    return sb.toString();
}
void build(TreeNode node, StringBuilder sb) {
    if (node == null) { sb.append("#,"); return; }   // 2. null marker
    sb.append(node.val).append(",");                 // 3. root first (preorder)
    build(node.left, sb);                            // 4. left
    build(node.right, sb);                           // 4. right
}

TreeNode deserialize(String data) {
    Queue<String> tokens = new LinkedList<>(Arrays.asList(data.split(",")));
    return rebuild(tokens);
}
TreeNode rebuild(Queue<String> tokens) {
    String val = tokens.poll();                      // read in same preorder
    if (val.equals("#")) return null;                // 2. rebuild null
    TreeNode node = new TreeNode(Integer.parseInt(val)); // 3. root first
    node.left  = rebuild(tokens);                    // 4. left
    node.right = rebuild(tokens);                    // 4. right
    return node;                                     // 6. up to parent
}
```

## The "aha" line
> "Write root-first with null markers; read root-first consuming a token queue. Encode and decode are the same preorder walk, mirrored."

## The classic trap
Omitting null markers — then you can't tell a leaf from an internal node, and the shape is ambiguous (e.g., `[1,2]` could mean 2 is left or right child). Every null must be recorded.

## Mind-map anchor
**"preorder + null markers" · token queue rebuild · encode/decode mirror**

---

# PATTERN 15: Build Tree from Preorder + Inorder (ROOT splits the arrays)

*(The "reconstruct a tree from two traversals" archetype. The insight: preorder hands you roots in order; inorder tells you how many nodes fall left vs right of each root.)*

## The Story
The **first** element of preorder is always the current subtree's **root**. Find that root's position inside the **inorder** array: everything to its *left* in inorder is the left subtree, everything to its *right* is the right subtree. That split tells you the sizes, so you can carve preorder into left and right chunks too, and recurse. A hashmap of `value → inorder index` makes each lookup O(1), giving overall O(n).

## What the interviewer is really testing
Can you exploit *what each traversal tells you* (preorder = root ordering, inorder = left/right partition) and manage index boundaries without off-by-one errors?

## Walk the 6-step template
> Ask → Answer → Why.

1. **PARAMETERS — Ask:** *"Carry?"* **Answer:** The current preorder index (shared/advancing) and the inorder range `[lo, hi]` this subtree spans. **Why:** Preorder is consumed front-to-back (a moving cursor); the inorder range defines *which* nodes belong to this subtree.
2. **BASE CASE — Ask:** *"Smallest input?"* **Answer:** `lo > hi` → `null`. **Why:** An empty inorder range means no nodes — an empty subtree.
3. **PRE-ORDER — Ask:** *"Work before children?"* **Answer:** Take the next preorder value as the root; look up its index `mid` in inorder. **Why:** Root-first: preorder gives the root before its subtrees, exactly the order we build.
4. **GO LEFT / RIGHT — Ask:** *"How split?"* **Answer:** Left subtree = inorder `[lo, mid-1]`; right subtree = inorder `[mid+1, hi]`. Build left *before* right so the preorder cursor advances correctly. **Why:** In inorder, left-of-root is the left subtree; and preorder lists the entire left subtree before the right, so consuming left first keeps the cursor aligned.
5/6. **POST-ORDER / RETURN:** attach the built children and return the root.

## The code
```java
Map<Integer,Integer> idx = new HashMap<>();
int pre = 0;

TreeNode buildTree(int[] preorder, int[] inorder) {
    for (int i = 0; i < inorder.length; i++) idx.put(inorder[i], i);
    return build(preorder, 0, inorder.length - 1);
}
TreeNode build(int[] preorder, int lo, int hi) {
    if (lo > hi) return null;                     // 2. empty range
    int rootVal = preorder[pre++];                // 3. next preorder = root
    TreeNode root = new TreeNode(rootVal);
    int mid = idx.get(rootVal);                   // 3. find split in inorder
    root.left  = build(preorder, lo, mid - 1);    // 4. left first (cursor order!)
    root.right = build(preorder, mid + 1, hi);    // 4. then right
    return root;                                  // 6. up
}
```

## The "aha" line
> "Preorder gives me the next root; its spot in inorder splits the rest into left and right. Recurse, always building left before right."

## The classic trap
Building right before left — the shared preorder cursor then advances in the wrong order and the tree scrambles. Also forgetting the hashmap and scanning inorder each time → O(n²). (For **postorder + inorder**, consume postorder from the **end** and build **right before left**.)

## Mind-map anchor
**"preorder = root, inorder = split" · left before right · hashmap for O(n)**

---

# PATTERN 16: Path Sum III & Subtree of Another Tree (NESTED DFS / prefix on a path)

*(Two "a DFS that launches another computation at every node" archetypes. This teaches the move of running a per-node sub-search, then optimizing it away with a running prefix map.)*

## 16A. Subtree of Another Tree — DFS that launches an `isSame` at each node

### The Story
Is tree `t` an exact subtree of tree `s`? Walk every node of `s`; at each one, ask "is the subtree rooted here **identical** to `t`?" using the **Same Tree** check (Pattern 3). If any node says yes, done.

```java
boolean isSubtree(TreeNode s, TreeNode t) {
    if (s == null) return false;                 // ran out of s
    if (isSame(s, t)) return true;               // launch Same-Tree here
    return isSubtree(s.left, t) || isSubtree(s.right, t); // else try children
}
// isSame(...) is Pattern 3, reused verbatim
```
**The move:** an outer DFS (visit every node) that, at each node, runs an **inner DFS** (the equality check). O(n·m) naive; mention hashing/serialization for O(n+m) if pushed.

### The "aha" line
> "Outer walk picks a candidate root; inner Same-Tree check verifies it. A DFS inside a DFS."

## 16B. Path Sum III — count downward paths summing to target (PREFIX SUM on a path)

### The Story
Count paths going **downward** (parent→child, not necessarily root-to-leaf) that sum to `target`. Naive: from every node, DFS down summing — O(n²). The pro trick borrows the **prefix-sum + hashmap** idea from arrays: carry the running sum from the root, and a map of `prefixSum → count`. A path ending at the current node with sum `target` exists for every earlier prefix equal to `runningSum − target`.

### Walk the 6-step template
> Ask → Answer → Why.

1. **PARAMETERS — Ask:** *"Carry down?"* **Answer:** `runningSum` (root→here) and a shared `Map<Long,Integer>` of prefix-sum frequencies. **Why:** Both are ancestor context — the sum accumulated above me, and the prefixes seen on my current root-path. Pure DOWN pipe.
2. **BASE CASE — Ask:** *"Stop?"* **Answer:** `node == null` → contribute 0. **Why:** Nothing to add.
3. **PRE-ORDER — Ask:** *"Work before kids?"* **Answer:** `runningSum += node.val`; add `count += map.getOrDefault(runningSum − target, 0)`; then `map[runningSum]++`. **Why:** A path ending here with the target sum corresponds to some ancestor prefix equal to `runningSum − target`. Record my own prefix so my descendants can use it.
4. **GO LEFT / RIGHT — Ask:** *"Hand down?"* **Answer:** Recurse both with the updated `runningSum` and shared map.
5. **POST-ORDER — Ask:** *"Cleanup?"* **Answer:** `map[runningSum]--` — **backtrack** the prefix. **Why:** My prefix is only valid for *my* subtree; siblings must not see it. (Same backtracking discipline as Path Sum II — undo the shared-map mutation on the way up.)
6. **RETURN:** the count from my subtree.

```java
int pathSum(TreeNode root, int target) {
    Map<Long,Integer> prefix = new HashMap<>();
    prefix.put(0L, 1);                            // empty prefix (path from root)
    return dfs(root, 0L, target, prefix);
}
int dfs(TreeNode node, long sum, int target, Map<Long,Integer> prefix) {
    if (node == null) return 0;                   // 2. base
    sum += node.val;                              // 3. extend running sum
    int count = prefix.getOrDefault(sum - target, 0);   // 3. paths ending here
    prefix.merge(sum, 1, Integer::sum);           // 3. record my prefix
    count += dfs(node.left,  sum, target, prefix);      // 4.
    count += dfs(node.right, sum, target, prefix);      // 4.
    prefix.merge(sum, -1, Integer::sum);          // 5. BACKTRACK the prefix
    return count;                                 // 6.
}
```

### The "aha" line
> "Prefix-sum-in-a-tree: a path summing to target ending at me = an ancestor prefix equal to runningSum − target. Add the empty-prefix seed, and backtrack the map on the way up."

### The classic trap
Forgetting `prefix.put(0L, 1)` (misses paths that start at the root), or forgetting to decrement the map on the way up (leaks prefixes into sibling subtrees).

## Mind-map anchor
**Subtree = "DFS inside DFS (Same-Tree)" · PathSumIII = "prefix map + seed 0 + backtrack"**

---

# PATTERN 17: Tree Views (COORDINATE pushed DOWN — right-side, vertical, top/bottom)

*(The "assign each node a coordinate, then group/pick" archetype. You push a position — depth, column — DOWN as a parameter, then collect. This unifies right-side view, vertical order, top view, and bottom view.)*

## The Story
Give every node a coordinate as you descend. For **vertical order**, that's a **column**: root is column 0, going left is `col-1`, going right is `col+1`. Bucket every node by its column, and read the buckets left to right. For **top/bottom view**, keep the *first* (or *last*) node seen per column. For **right-side view**, the coordinate is *depth*, and you keep the last node at each depth (or the first, if you visit right-first).

## What the interviewer is really testing
Can you turn a spatial question into "assign coordinates via a DOWN parameter, then group by coordinate"? This is Good Nodes' DOWN-pipe reflex again, but the state you carry is a *position*, not a value.

## Walk the 6-step template (Vertical Order via DFS)
> Ask → Answer → Why.

1. **PARAMETERS — Ask:** *"What coordinate do I carry down?"* **Answer:** `row` and `col`. **Why:** A node's bucket is defined by its position, which is determined by the path from the root → push it down.
2. **BASE CASE:** `node == null` → return.
3. **PRE-ORDER — Ask:** *"Record before recursing?"* **Answer:** Add `(row, node.val)` into the map bucket for `col`. **Why:** The node's placement depends only on ancestors (its coordinate), so record it top-down.
4. **GO LEFT / RIGHT — Ask:** *"How does the coordinate change?"* **Answer:** left → `(row+1, col-1)`, right → `(row+1, col+1)`. **Why:** Down a level increments row; left/right shifts the column.
5/6. **POST-ORDER / RETURN:** none; after the walk, sort columns left-to-right (and within a column by row, then value) and emit.

## The code (vertical order)
```java
void dfs(TreeNode node, int row, int col, TreeMap<Integer, List<int[]>> map) {
    if (node == null) return;                                 // 2.
    map.computeIfAbsent(col, k -> new ArrayList<>())
       .add(new int[]{row, node.val});                        // 3. record by column
    dfs(node.left,  row + 1, col - 1, map);                   // 4. left ⇒ col-1
    dfs(node.right, row + 1, col + 1, map);                   // 4. right ⇒ col+1
}
```
Right-side view is even simpler — it's the BFS skeleton (Pattern 12) keeping the last node per level, or a DFS visiting **right first** and recording the first node seen at each new depth:
```java
void view(TreeNode node, int depth, List<Integer> res) {
    if (node == null) return;
    if (depth == res.size()) res.add(node.val);   // first node reached at this depth
    view(node.right, depth + 1, res);             // RIGHT first
    view(node.left,  depth + 1, res);
}
```

## The "aha" line
> "Push a coordinate (column or depth) down as a parameter, bucket nodes by it, then read the buckets in order."

## The classic trap
Vertical order: not breaking ties correctly. When two nodes share a column, order by row, then by value — a plain DFS without recording `row` can misorder them. (LeetCode's strict "Vertical Order Traversal" requires the row+value tie-break.)

## Mind-map anchor
**"carry (row,col) down" · left ⇒ col−1, right ⇒ col+1 · group by coordinate · right-view = right-first DFS**

---

## 🎯 The Master Decision Tree — Pick Your Weapon in 10 Seconds

Ask yourself these questions **in order**:

### 1. "Do I need info from my ANCESTORS (root-to-me path)?"
→ YES: Push state **DOWN** as a **parameter**, work in **PRE-order**
*Examples: Good Nodes, Validate BST*

### 2. "Do I need info from my DESCENDANTS (what's below me)?"
→ YES: Pull answers **UP** via **return value**, work in **POST-order**
*Examples: Depth, Diameter, Max Path Sum, Balanced, Largest BST*

### 3. "Is the value I RETURN different from the ANSWER I track?"
→ YES: Keep a **global** for the answer, **return** only the one-side extendable value
*Examples: Diameter, Max Path Sum*

### 4. "Do I need SEVERAL facts from each kid, not just one number?"
→ YES: **Return a bundle/object** with multiple fields
*Examples: Largest BST, Balanced-with-flag*

### 5. "Am I comparing/walking TWO nodes at once?"
→ YES: Function takes **two node arguments**
*Examples: Same Tree, Is Mirror*

### 6. "Do I need EVERY path, and must the path reset between branches?"
→ YES: **Backtracking** — add to shared list, **remove after both kids**
*Examples: Path Sum II*

### 7. "Is it a BST and about order/next/search?"
→ YES: Use **inorder = sorted** and **O(h) directed walk**
*Examples: Inorder Successor, BST LCA, Kth Smallest*

### 8. "Does the answer depend on LEVELS / nearest / a side-view?"
→ YES: **BFS** with a queue, **freeze the level size**
*Examples: Level Order, Right-Side View, Min Depth, Zigzag*

### 9. "Do I carry a POSITION (depth/column) to group or pick nodes?"
→ YES: Push a **coordinate DOWN** as a parameter, bucket by it
*Examples: Vertical Order, Top/Bottom View, Right-Side View*

### 10. "Am I encoding/rebuilding a tree, or building from traversals?"
→ YES: **Preorder + markers** (serialize) or **root-splits-array** (build from pre+in)
*Examples: Serialize/Deserialize, Build from Traversals*

### 11. "Do I run a sub-search at every node, or need a running prefix on the path?"
→ YES: **Nested DFS** (DFS inside DFS) or **prefix-sum map + backtrack**
*Examples: Subtree of Another, Path Sum III*

### 12. "Is this a new problem?"
→ Try to **COMPOSE** patterns you already know
*Example: Distance = LCA + Depth*

---

## 🔑 The Two Pipes — The Whole Subject in Two Lines

```
DOWN pipe = parameters = pre-order = ancestor context
UP pipe   = return values = post-order = descendant answers
```

And the special cases:
- **Backtracking** = a shared object that uses the DOWN pipe on entry and UNDOES itself on exit
- **BFS** = the SIDEWAYS pipe: a queue that moves across a level instead of up/down

---

## 📊 The One-Table Pattern Map

| # | Pattern | Direction | Return vs Answer | Key Move |
|---|---------|-----------|------------------|----------|
| 0 | Max Depth | UP | same | `1 + max(L,R)` |
| 1 | Diameter | UP | **different** | answer=`L+R`, return=`1+max` |
| 2 | Good Nodes | **DOWN** | — | carry `maxSoFar` down |
| 3 | Invert | UP | — | swap kids |
| 3 | Same Tree | UP (2 args) | — | L-L, R-R |
| 3 | Is Mirror | UP (2 args) | — | **cross**: L-R, R-L |
| 4 | LCA | UP | — | both non-null ⇒ me |
| 5 | Inorder Successor | DOWN/walk | — | last node `> p` |
| 6 | Largest BST | UP | — | return **Info bundle** |
| 7 | Distance | compose | — | LCA + depth |
| 8 | Max Path Sum | UP | **different** | clamp `max(0,child)` |
| 9 | Balanced | UP | same (w/ flag) | `−1` = broken |
| 10 | Path Sum II | **backtrack** | — | add→explore→**remove** |
| 11 | Validate BST | **DOWN** | — | push `(low,high)` window |
| 12 | Level Order | **BFS** | — | queue + **freeze size** |
| 13 | Kth Smallest | inorder | — | countdown, stop at 0 |
| 14 | Serialize | preorder | — | null markers + token queue |
| 15 | Build from Pre+In | preorder | — | preorder=root, inorder=split |
| 16 | Subtree of Another | **nested DFS** | — | DFS launches `isSame` |
| 16 | Path Sum III | **DOWN + backtrack** | — | prefix map + seed 0 |
| 17 | Tree Views | **DOWN (coord)** | — | carry `(row,col)`, group |

---

## 🎤 The 60-Second Interview Script

Memorize this and say it when you see a tree problem:

> "This is a tree problem. First: is it about **levels or nearest**? If so, BFS with a queue, freezing the level size.
>
> Otherwise it's DFS, and I ask: does the answer depend on **ancestors** or **descendants**?
>
> **Ancestors** → I push state down as a parameter and work in pre-order.
>
> **Descendants** → I compute after my kids return, in post-order.
>
> If the value my parent needs **differs** from the global answer — like diameter — I track the answer in a field and return only the one-sided value, because a path can't fork.
>
> If I need **several facts** from each kid, I return a small Info object.
>
> If I need **every path**, I backtrack: add myself, explore, remove myself.
>
> And if it's a **BST and about order**, I lean on inorder-equals-sorted."

---

## 🔥 Common Mistakes — Loop This Until It's Automatic

| Mistake | The Fix |
|---------|---------|
| Base case returns `1` for null | Return `0` — null is NOTHING |
| Diameter: returning `L+R` to parent | Return `1+max(L,R)` — path can't fork |
| Max Path Sum: init to `0` | Init to `MIN_VALUE` — all nodes might be negative |
| Max Path Sum: not clamping negatives | Use `max(0, child)` — bad branch is optional |
| LCA: returning left before checking both | Check `left != null && right != null` FIRST |
| Same/Mirror: checking `.val` before null | Always do null checks FIRST |
| Path Sum II: adding `path` directly | Add `new ArrayList<>(path)` — a COPY |
| Path Sum II: removing twice | Remove ONCE, AFTER both kids |
| Validate BST: local parent-child check | Push `(low, high)` window DOWN |
| BFS: not freezing level size | `int size = q.size()` BEFORE the loop |
| Kth Smallest: building entire list | Use countdown, stop at 0 |
| Serialize: no null markers | Always write `#` for null |
| Path Sum III: no seed | Add `prefix.put(0L, 1)` |
| Path Sum III: not backtracking | Decrement map on the way UP |
| Build from Pre+In: wrong order | Build **left before right** (cursor order) |
| Tree Views: wrong tie-break | Break column ties by **row, then value** |

---

## 🏆 Your Mastery Checklist

You've mastered tree patterns when you can:

- [ ] State the 6-step template with your eyes closed
- [ ] Instantly classify a problem as DOWN (parameter/pre-order), UP (return/post-order), or BFS (levels)
- [ ] Explain why Diameter's answer and return values differ
- [ ] Write Same Tree, then turn it into Is Mirror by crossing the calls
- [ ] Write LCA and explain the "both sides non-null" moment
- [ ] Return an Info bundle for Largest BST and pick the right null identity
- [ ] Validate a BST by pushing a `(low, high)` window down (not a local check)
- [ ] Write the one BFS skeleton and adapt it to right-side view, min depth, and zigzag
- [ ] Find the k-th smallest with an inorder countdown that stops early
- [ ] Serialize with null markers and deserialize from a token queue
- [ ] Rebuild a tree from preorder + inorder using the root-splits-array idea
- [ ] Count paths with Path Sum III's prefix map (and remember to backtrack it)
- [ ] Assign coordinates for vertical/right-side views
- [ ] Compose Distance from LCA + depth without looking
- [ ] Explain how backtracking (Path Sum II) differs from post-order combining
- [ ] Recite the 60-second interview script cold

---

## 📚 MAANG Coverage Map — Which Pattern Owns Each Problem

| Classic MAANG question | Pattern # | LeetCode |
|---|---|---|
| Maximum Depth | 0 | 104 |
| Diameter of Binary Tree | 1 | 543 |
| Count Good Nodes | 2 | 1448 |
| Invert Binary Tree | 3 | 226 |
| Same Tree | 3 | 100 |
| Symmetric / Mirror Tree | 3 | 101 |
| Lowest Common Ancestor | 4 | 236 / 235 (BST) |
| Inorder Successor in BST | 5 | 285 |
| Largest BST Subtree | 6 | 333 |
| Distance Between Two Nodes | 7 | 1740 |
| Binary Tree Maximum Path Sum | 8 | 124 |
| Balanced Binary Tree | 9 | 110 |
| Path Sum II | 10 | 113 |
| Path Sum (exists?) | 10 (simpler) | 112 |
| Validate BST | 11 | 98 |
| Level Order Traversal | 12 | 102 |
| Binary Tree Right Side View | 12 / 17 | 199 |
| Minimum Depth | 12 | 111 |
| Zigzag Level Order | 12 | 103 |
| Kth Smallest in BST | 13 | 230 |
| Serialize & Deserialize | 14 | 297 |
| Construct from Preorder+Inorder | 15 | 105 |
| Construct from Postorder+Inorder | 15 | 106 |
| Subtree of Another Tree | 16 | 572 |
| Path Sum III | 16 | 437 |
| Vertical Order Traversal | 17 | 987 |
| Flatten Tree to Linked List | 3-style (preorder rewire) | 114 |
| Recover BST (two swapped) | 13-style (inorder + track) | 99 |

> If a new problem isn't on this list, it's almost always a **remix** of one of these patterns. Run the Master Decision Tree and you'll land on the right one.

---

# 🆕 EXTENDED PATTERNS (18-30) — Complete L5 Coverage

---

# PATTERN 18: BST Search (The Foundation of BST Operations)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** You're looking for a book in a library where books are sorted by number. You check the middle shelf. If your book's number is smaller, you go LEFT. If bigger, you go RIGHT. You never need to check the other side!

**The Key Insight:** BST property means you can ELIMINATE half the tree at each step. This is why BST operations are O(h) not O(n).

```
BST Rule: Left < Parent < Right (always!)
```

## Walk the 6-step template

1. **PARAMETERS:** Just `node` and `target` value.
2. **BASE CASE:** `node == null` → not found, return null. `node.val == target` → found it!
3. **PRE-ORDER:** Compare `target` with `node.val` to decide direction.
4. **GO LEFT / RIGHT:** Only ONE side! `target < node.val` → go left. `target > node.val` → go right.
5. **POST-ORDER:** None — answer found on the way DOWN.
6. **RETURN:** The found node, or null.

## The code
```java
TreeNode searchBST(TreeNode node, int target) {
    if (node == null) return null;           // Not found
    if (node.val == target) return node;     // Found it!
    if (target < node.val) 
        return searchBST(node.left, target); // Go left
    else 
        return searchBST(node.right, target); // Go right
}
```

## The "aha" line
> "BST search is binary search on a tree. Compare, pick ONE side, repeat."

## Mind-map anchor
**`target < val → left` · `target > val → right` · O(h) not O(n)**

---

# PATTERN 19: BST Insert (Find the Right Spot)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** You're adding a new book to the sorted library. You walk down comparing numbers until you find an EMPTY spot. That's where the new book goes!

**The Key Insight:** Insert is just Search that creates a new node when it hits null.

## Walk the 6-step template

1. **PARAMETERS:** `node` and `val` to insert.
2. **BASE CASE:** `node == null` → create and return `new TreeNode(val)`.
3. **PRE-ORDER:** Compare `val` with `node.val` to decide direction.
4. **GO LEFT / RIGHT:** Only ONE side, and REASSIGN the child pointer!
5. **POST-ORDER:** None.
6. **RETURN:** The (possibly new) subtree root.

## The code
```java
TreeNode insertIntoBST(TreeNode node, int val) {
    if (node == null) return new TreeNode(val);  // Found empty spot!
    
    if (val < node.val)
        node.left = insertIntoBST(node.left, val);   // Insert left, REASSIGN
    else
        node.right = insertIntoBST(node.right, val); // Insert right, REASSIGN
    
    return node;  // Return unchanged root
}
```

## The "aha" line
> "Insert = Search until null, then create. The REASSIGNMENT `node.left = ...` is what attaches the new node."

## The classic trap
Forgetting to REASSIGN: `insertIntoBST(node.left, val)` without `node.left = ...` creates the node but doesn't attach it!

## Mind-map anchor
**`null → new node` · `node.left = recurse(...)` · reassignment attaches**

---

# PATTERN 20: BST Delete (The Three Cases)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Removing a book from the sorted library. Easy if it's at the end of a shelf (leaf). Tricky if it's in the middle — you need to find a replacement to keep things sorted!

**The Key Insight:** Three cases based on how many children the node has:
1. **No children (leaf):** Just remove it
2. **One child:** Replace node with its child
3. **Two children:** Find the INORDER SUCCESSOR (smallest in right subtree), copy its value, delete the successor

## Walk the 6-step template

1. **PARAMETERS:** `node` and `key` to delete.
2. **BASE CASE:** `node == null` → key not found, return null.
3. **PRE-ORDER:** Compare `key` with `node.val`. If equal, handle the 3 cases.
4. **GO LEFT / RIGHT:** Search for the key in the appropriate subtree.
5. **POST-ORDER:** None.
6. **RETURN:** The new subtree root (may change if we deleted the current node).

## The code
```java
TreeNode deleteNode(TreeNode node, int key) {
    if (node == null) return null;  // Key not found
    
    // SEARCH phase
    if (key < node.val) {
        node.left = deleteNode(node.left, key);
    } else if (key > node.val) {
        node.right = deleteNode(node.right, key);
    } else {
        // FOUND the node to delete!
        
        // Case 1 & 2: No left child OR no right child
        if (node.left == null) return node.right;
        if (node.right == null) return node.left;
        
        // Case 3: Two children
        // Find inorder successor (smallest in right subtree)
        TreeNode successor = findMin(node.right);
        node.val = successor.val;  // Copy successor's value
        node.right = deleteNode(node.right, successor.val);  // Delete successor
    }
    return node;
}

TreeNode findMin(TreeNode node) {
    while (node.left != null) node = node.left;
    return node;
}
```

## Tiny dry run — Delete 3
```
        5
       / \
      3   7
     / \
    2   4
```
- Delete 3: Has two children (2 and 4)
- Inorder successor of 3 = 4 (smallest in right subtree)
- Copy 4's value to node 3's position
- Delete the original 4 (which is a leaf)
- Result: `5 → [4 → [2, null], 7]`

## The "aha" line
> "No children? Remove. One child? Replace with child. Two children? Swap with inorder successor, then delete successor."

## The classic trap
Forgetting that after swapping with successor, you must DELETE the successor from the right subtree!

## Mind-map anchor
**3 cases: leaf/one-child/two-children · successor = leftmost of right · swap then delete**

---

# PATTERN 21: House Robber III (The Bundle Return Pattern)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** You're a thief robbing houses arranged in a tree. If you rob a house, you CAN'T rob its direct children (alarm system). At each house, you have two choices: rob it or skip it. You want maximum money!

**The Key Insight:** At each node, you need to return TWO values:
1. **Max money if I ROB this house**
2. **Max money if I SKIP this house**

This is the "return a bundle" pattern — one number isn't enough!

## Walk the 6-step template

1. **PARAMETERS:** Just `node`. No ancestor info needed.
2. **BASE CASE:** `node == null` → return `[0, 0]` (rob=0, skip=0).
3. **PRE-ORDER:** None — pure bottom-up.
4. **GO LEFT / RIGHT:** Get `[rob, skip]` bundle from each child.
5. **POST-ORDER:** Calculate my two options:
   - `robMe = node.val + left.skip + right.skip` (if I rob, kids must skip)
   - `skipMe = max(left.rob, left.skip) + max(right.rob, right.skip)` (if I skip, kids can do either)
6. **RETURN:** `[robMe, skipMe]` bundle.

## The code
```java
int rob(TreeNode root) {
    int[] result = dfs(root);
    return Math.max(result[0], result[1]);
}

int[] dfs(TreeNode node) {
    if (node == null) return new int[]{0, 0};  // [rob, skip]
    
    int[] left = dfs(node.left);
    int[] right = dfs(node.right);
    
    // If I rob this house, my children MUST skip
    int robMe = node.val + left[1] + right[1];
    
    // If I skip this house, my children can do either (pick their best)
    int skipMe = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    
    return new int[]{robMe, skipMe};
}
```

## Tiny dry run
```
        3
       / \
      2   3
       \   \
        3   1
```
- Leaves: `[3,0]`, `[1,0]`
- Node 2: rob=2+0=2, skip=max(3,0)=3 → `[2,3]`
- Node 3(right): rob=3+0=3, skip=max(1,0)=1 → `[3,1]`
- Root 3: rob=3+3+1=7, skip=max(2,3)+max(3,1)=3+3=6 → `[7,6]`
- Answer: max(7,6) = **7** ✓

## The "aha" line
> "Return TWO numbers: max if I rob, max if I skip. If I rob, kids must skip. If I skip, kids choose their best."

## Mind-map anchor
**return [rob, skip] · robMe = val + kids.skip · skipMe = max of each kid**

---

# PATTERN 22: Binary Tree Cameras (3-State Tree DP)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** You're placing security cameras in a building (tree). A camera covers itself, its parent, and its children. You want MINIMUM cameras to cover all rooms.

**The Key Insight:** Each node can be in one of THREE states:
- **State 0:** I NEED a camera (I'm not covered)
- **State 1:** I HAVE a camera
- **State 2:** I'm COVERED (by my child's camera)

**The Greedy Insight:** Place cameras as LOW as possible (at parents of leaves), so each camera covers more nodes!

## Walk the 6-step template

1. **PARAMETERS:** `node` and a global `cameras` counter.
2. **BASE CASE:** `node == null` → return `2` (null is "covered" — doesn't need anything).
3. **PRE-ORDER:** None.
4. **GO LEFT / RIGHT:** Get state from each child.
5. **POST-ORDER:** Decide my state based on children:
   - If ANY child needs camera (state 0) → I MUST have camera, return 1
   - If ANY child has camera (state 1) → I'm covered, return 2
   - Otherwise → I need camera from parent, return 0
6. **RETURN:** My state.

## The code
```java
int cameras = 0;

int minCameraCover(TreeNode root) {
    if (dfs(root) == 0) cameras++;  // Root needs camera? Add one!
    return cameras;
}

int dfs(TreeNode node) {
    if (node == null) return 2;  // Null is "covered"
    
    int left = dfs(node.left);
    int right = dfs(node.right);
    
    // If ANY child needs a camera, I MUST place one here
    if (left == 0 || right == 0) {
        cameras++;
        return 1;  // I have a camera
    }
    
    // If ANY child has a camera, I'm covered
    if (left == 1 || right == 1) {
        return 2;  // I'm covered
    }
    
    // Both children are covered (state 2), so I need coverage from parent
    return 0;  // I need a camera
}
```

## Tiny dry run
```
        0
       /
      0
     / \
    0   0
```
- Leaves return 0 (need camera)
- Their parent: child needs camera → place camera, return 1
- Root: child has camera → return 2 (covered)
- Cameras = **1** ✓

## The "aha" line
> "Three states: needs camera (0), has camera (1), covered (2). If child needs → I place. If child has → I'm covered. Otherwise → I need from parent."

## The classic trap
Forgetting to check if ROOT needs a camera at the end! If root returns 0, add one more camera.

## Mind-map anchor
**3 states: 0=needs, 1=has, 2=covered · child needs → I place · greedy: cameras low**

---

# PATTERN 23: Distribute Coins in Binary Tree (Flow Counting)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Each node has some coins. You want exactly 1 coin per node. Coins can only move between parent-child. Count the MINIMUM moves.

**The Key Insight:** Each node returns its "excess" coins (positive = has extra, negative = needs coins). The number of moves through an edge = |excess| from that subtree.

```
excess = (coins at node) + (excess from left) + (excess from right) - 1
         ↑ what I have      ↑ what flows up from kids                ↑ I keep 1
```

## Walk the 6-step template

1. **PARAMETERS:** `node` and global `moves` counter.
2. **BASE CASE:** `node == null` → return `0` excess.
3. **PRE-ORDER:** None.
4. **GO LEFT / RIGHT:** Get excess from each child.
5. **POST-ORDER:** 
   - `moves += |leftExcess| + |rightExcess|` (coins flowing through me)
   - Calculate my excess to pass up
6. **RETURN:** My excess = `node.val + leftExcess + rightExcess - 1`.

## The code
```java
int moves = 0;

int distributeCoins(TreeNode root) {
    dfs(root);
    return moves;
}

int dfs(TreeNode node) {
    if (node == null) return 0;
    
    int leftExcess = dfs(node.left);
    int rightExcess = dfs(node.right);
    
    // Coins flowing through edges to/from me
    moves += Math.abs(leftExcess) + Math.abs(rightExcess);
    
    // My excess: what I have + what flows up - 1 (I keep one)
    return node.val + leftExcess + rightExcess - 1;
}
```

## Tiny dry run
```
        3
       / \
      0   0
```
- Left child: excess = 0 - 1 = -1 (needs 1 coin)
- Right child: excess = 0 - 1 = -1 (needs 1 coin)
- Root: moves += |-1| + |-1| = 2. Excess = 3 + (-1) + (-1) - 1 = 0
- Answer: **2** moves ✓

## The "aha" line
> "Each node returns excess coins. Moves through an edge = |excess| from that subtree. Excess = have + kids_excess - 1."

## Mind-map anchor
**excess = val + kids - 1 · moves += |left| + |right| · flow counting**

---

# PATTERN 24: Maximum Width of Binary Tree (BFS + Index Tracking)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** You want to find the WIDEST level of a tree, counting nulls between nodes. Imagine the tree as a complete binary tree with positions numbered.

**The Key Insight:** Assign each node an INDEX like a heap:
- Root = index 0
- Left child = 2 * parent_index
- Right child = 2 * parent_index + 1

Width of a level = rightmost_index - leftmost_index + 1

## Walk the 6-step template (BFS version)

1. **PARAMETERS:** Queue holds `(node, index)` pairs.
2. **BASE CASE:** Empty tree → width 0.
3. **PER-LEVEL:** Track first and last index of each level.
4. **ENQUEUE CHILDREN:** With their calculated indices.
5. **AFTER LEVEL:** Update max width.
6. **RETURN:** Maximum width seen.

## The code
```java
int widthOfBinaryTree(TreeNode root) {
    if (root == null) return 0;
    
    int maxWidth = 0;
    Queue<long[]> queue = new LinkedList<>();  // [node_id, index]
    queue.offer(new long[]{0, 0});  // We'll use a map for nodes
    
    // Better approach: queue of (node, index) pairs
    Queue<Pair<TreeNode, Long>> q = new LinkedList<>();
    q.offer(new Pair<>(root, 0L));
    
    while (!q.isEmpty()) {
        int size = q.size();
        long levelStart = q.peek().getValue();  // First index of this level
        long first = 0, last = 0;
        
        for (int i = 0; i < size; i++) {
            Pair<TreeNode, Long> curr = q.poll();
            TreeNode node = curr.getKey();
            long idx = curr.getValue() - levelStart;  // Normalize to prevent overflow
            
            if (i == 0) first = idx;
            if (i == size - 1) last = idx;
            
            if (node.left != null) 
                q.offer(new Pair<>(node.left, idx * 2));
            if (node.right != null) 
                q.offer(new Pair<>(node.right, idx * 2 + 1));
        }
        
        maxWidth = Math.max(maxWidth, (int)(last - first + 1));
    }
    
    return maxWidth;
}
```

## Tiny dry run
```
        1
       / \
      3   2
     /     \
    5       9
```
- Level 0: indices [0] → width = 1
- Level 1: indices [0, 1] → width = 2
- Level 2: indices [0, 3] (5 is at 2*0=0, 9 is at 2*1+1=3) → width = 3+1 = **4** ✓

## The "aha" line
> "Number nodes like a heap: left = 2i, right = 2i+1. Width = rightmost - leftmost + 1. Normalize indices per level to prevent overflow."

## The classic trap
Integer overflow! Indices can get huge (2^depth). Normalize by subtracting the first index of each level.

## Mind-map anchor
**heap indexing: left=2i, right=2i+1 · width = last - first + 1 · normalize per level**

---

# PATTERN 25: Populating Next Right Pointers (Level Linking)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Connect each node to its RIGHT neighbor on the same level. Like linking people standing in a row.

**The Key Insight:** Two approaches:
1. **BFS:** Process level by level, link as you go
2. **O(1) space:** Use the ALREADY-ESTABLISHED next pointers to traverse the current level while linking the next level

## The code (BFS approach)
```java
Node connect(Node root) {
    if (root == null) return null;
    
    Queue<Node> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            Node node = queue.poll();
            
            // Link to next node in queue (if not last in level)
            if (i < size - 1) {
                node.next = queue.peek();
            }
            
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }
    
    return root;
}
```

## The code (O(1) space — Perfect Binary Tree)
```java
Node connect(Node root) {
    if (root == null) return null;
    
    Node levelStart = root;
    
    while (levelStart.left != null) {  // While there's a next level
        Node curr = levelStart;
        
        while (curr != null) {
            // Connect left child to right child
            curr.left.next = curr.right;
            
            // Connect right child to next node's left child
            if (curr.next != null) {
                curr.right.next = curr.next.left;
            }
            
            curr = curr.next;  // Move right using established links!
        }
        
        levelStart = levelStart.left;  // Move to next level
    }
    
    return root;
}
```

## The "aha" line
> "BFS: link nodes as you process each level. O(1) space: use already-established next pointers to traverse while linking the level below."

## Mind-map anchor
**BFS: link within level · O(1): use next to traverse, link children · left.next = right**

---

# PATTERN 26: Boundary of Binary Tree (Three-Part Traversal)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Walk around the EDGE of the tree: left boundary (top-down), all leaves (left-to-right), right boundary (bottom-up). Like tracing the outline!

**The Key Insight:** Three separate traversals:
1. **Left boundary:** Go left-first, but NOT leaves
2. **Leaves:** All leaves, left to right
3. **Right boundary:** Go right-first, but NOT leaves, then REVERSE

## The code
```java
List<Integer> boundaryOfBinaryTree(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    
    if (!isLeaf(root)) result.add(root.val);  // Add root (if not leaf)
    
    // 1. Left boundary (top-down, exclude leaves)
    addLeftBoundary(root.left, result);
    
    // 2. All leaves (left to right)
    addLeaves(root, result);
    
    // 3. Right boundary (bottom-up, exclude leaves)
    addRightBoundary(root.right, result);
    
    return result;
}

void addLeftBoundary(TreeNode node, List<Integer> result) {
    while (node != null) {
        if (!isLeaf(node)) result.add(node.val);
        node = (node.left != null) ? node.left : node.right;
    }
}

void addLeaves(TreeNode node, List<Integer> result) {
    if (node == null) return;
    if (isLeaf(node)) {
        result.add(node.val);
        return;
    }
    addLeaves(node.left, result);
    addLeaves(node.right, result);
}

void addRightBoundary(TreeNode node, List<Integer> result) {
    List<Integer> temp = new ArrayList<>();
    while (node != null) {
        if (!isLeaf(node)) temp.add(node.val);
        node = (node.right != null) ? node.right : node.left;
    }
    Collections.reverse(temp);  // Bottom-up!
    result.addAll(temp);
}

boolean isLeaf(TreeNode node) {
    return node.left == null && node.right == null;
}
```

## The "aha" line
> "Three parts: left boundary (top-down), leaves (left-to-right), right boundary (bottom-up reversed). Don't double-count leaves!"

## Mind-map anchor
**3 parts: left-down + leaves + right-up · exclude leaves from boundaries · reverse right**

---

# PATTERN 27: Left Side View (Mirror of Right Side View)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Stand on the LEFT side of the tree. What do you see? The FIRST node at each level!

**The Key Insight:** Same as Right Side View, but visit LEFT child first, or take the FIRST node of each BFS level.

## The code (DFS — left first)
```java
List<Integer> leftSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    dfs(root, 0, result);
    return result;
}

void dfs(TreeNode node, int depth, List<Integer> result) {
    if (node == null) return;
    
    // First node at this depth = leftmost
    if (depth == result.size()) {
        result.add(node.val);
    }
    
    dfs(node.left, depth + 1, result);   // LEFT first!
    dfs(node.right, depth + 1, result);
}
```

## The code (BFS — first of each level)
```java
List<Integer> leftSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            if (i == 0) result.add(node.val);  // FIRST of level
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }
    
    return result;
}
```

## The "aha" line
> "Left view = first node at each depth. DFS left-first, or BFS take index 0."

## Mind-map anchor
**left-first DFS · or BFS i==0 · mirror of right view**

---

# PATTERN 28: Diagonal Traversal (Coordinate Variant)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Draw diagonal lines from top-right to bottom-left. Group nodes by which diagonal they're on.

**The Key Insight:** Assign a "diagonal number" to each node:
- Root = diagonal 0
- Going LEFT = diagonal + 1 (moves to next diagonal)
- Going RIGHT = same diagonal (stays on same diagonal)

## The code
```java
List<List<Integer>> diagonalTraversal(TreeNode root) {
    Map<Integer, List<Integer>> diagonals = new TreeMap<>();
    dfs(root, 0, diagonals);
    return new ArrayList<>(diagonals.values());
}

void dfs(TreeNode node, int diagonal, Map<Integer, List<Integer>> map) {
    if (node == null) return;
    
    map.computeIfAbsent(diagonal, k -> new ArrayList<>()).add(node.val);
    
    dfs(node.left, diagonal + 1, map);  // Left = next diagonal
    dfs(node.right, diagonal, map);      // Right = same diagonal
}
```

## The "aha" line
> "Left child moves to next diagonal, right child stays on same diagonal. Group by diagonal number."

## Mind-map anchor
**left = diagonal+1 · right = same diagonal · group by diagonal**

---

# PATTERN 29: Merge Two Binary Trees (Parallel Traversal)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Overlay two trees on top of each other. Where both have nodes, ADD the values. Where only one has a node, use that node.

**The Key Insight:** Walk both trees in parallel. Handle the cases where one or both are null.

## The code
```java
TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    // If one is null, return the other
    if (t1 == null) return t2;
    if (t2 == null) return t1;
    
    // Both exist: add values
    t1.val += t2.val;
    
    // Recursively merge children
    t1.left = mergeTrees(t1.left, t2.left);
    t1.right = mergeTrees(t1.right, t2.right);
    
    return t1;
}
```

## The "aha" line
> "Walk both trees together. Null + node = node. Node + node = sum. Recurse on both children pairs."

## Mind-map anchor
**parallel walk · null returns other · both exist = sum**

---

# PATTERN 30: Average of Levels (BFS Aggregation)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Find the average value of nodes at each level. Classic BFS with per-level computation.

**The Key Insight:** BFS with level-size freeze. Sum all values in a level, divide by count.

## The code
```java
List<Double> averageOfLevels(TreeNode root) {
    List<Double> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        double sum = 0;
        
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            sum += node.val;
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        result.add(sum / size);
    }
    
    return result;
}
```

## The "aha" line
> "BFS, freeze level size, sum all values, divide by count. Same skeleton as Level Order."

## Mind-map anchor
**BFS + freeze size · sum / count per level · same skeleton**

---

# PATTERN 31: Longest Univalue Path (Diameter Variant)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Find the longest path where ALL nodes have the SAME value. Like Diameter, but only count edges where values match!

**The Key Insight:** Same "answer vs return" split as Diameter:
- **ANSWER:** Path bending at me (left arm + right arm) — only if values match
- **RETURN:** Longest single arm extending up — only if my value matches parent's

## The code
```java
int maxLength = 0;

int longestUnivaluePath(TreeNode root) {
    dfs(root);
    return maxLength;
}

int dfs(TreeNode node) {
    if (node == null) return 0;
    
    int left = dfs(node.left);
    int right = dfs(node.right);
    
    // Only extend if child's value matches mine
    int leftArm = (node.left != null && node.left.val == node.val) ? left + 1 : 0;
    int rightArm = (node.right != null && node.right.val == node.val) ? right + 1 : 0;
    
    // ANSWER: path bending at me
    maxLength = Math.max(maxLength, leftArm + rightArm);
    
    // RETURN: longest single arm (for parent to extend)
    return Math.max(leftArm, rightArm);
}
```

## The "aha" line
> "Diameter, but only count edges where values match. Reset arm to 0 if values differ."

## Mind-map anchor
**diameter variant · arm = 0 if values differ · answer = both arms, return = one arm**

---

# PATTERN 32: LCA of Deepest Leaves (Depth + LCA Combined)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Find the lowest common ancestor of ALL the deepest leaves. If there's only one deepest leaf, return it. If multiple, return their LCA.

**The Key Insight:** Return a bundle: `(node, depth)`. The LCA of deepest leaves is where left and right depths are EQUAL and MAXIMUM.

## The code
```java
TreeNode lcaDeepestLeaves(TreeNode root) {
    return dfs(root).node;
}

Result dfs(TreeNode node) {
    if (node == null) return new Result(null, 0);
    
    Result left = dfs(node.left);
    Result right = dfs(node.right);
    
    if (left.depth > right.depth) {
        return new Result(left.node, left.depth + 1);
    } else if (right.depth > left.depth) {
        return new Result(right.node, right.depth + 1);
    } else {
        // Equal depths: I am the LCA of deepest leaves in my subtree
        return new Result(node, left.depth + 1);
    }
}

class Result {
    TreeNode node;
    int depth;
    Result(TreeNode n, int d) { node = n; depth = d; }
}
```

## The "aha" line
> "Return (node, depth). If left deeper, bubble left's answer. If right deeper, bubble right's. If equal, I'm the LCA!"

## Mind-map anchor
**return (node, depth) · deeper side wins · equal depths = I'm LCA**

---

# PATTERN 33: Maximum Product of Splitted Binary Tree (Total Sum Trick)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Remove ONE edge to split tree into two parts. Maximize the PRODUCT of their sums.

**The Key Insight:** 
1. First pass: Calculate TOTAL sum of tree
2. Second pass: At each node, subtree sum = S. Other part = Total - S. Product = S * (Total - S)

## The code
```java
long maxProduct = 0;
long totalSum = 0;

int maxProduct(TreeNode root) {
    totalSum = getSum(root);  // First pass
    getSum(root);              // Second pass (updates maxProduct)
    return (int)(maxProduct % (1e9 + 7));
}

long getSum(TreeNode node) {
    if (node == null) return 0;
    
    long subtreeSum = node.val + getSum(node.left) + getSum(node.right);
    
    // If totalSum is set, we're in second pass
    if (totalSum > 0) {
        maxProduct = Math.max(maxProduct, subtreeSum * (totalSum - subtreeSum));
    }
    
    return subtreeSum;
}
```

## The "aha" line
> "First find total sum. Then at each node: product = subtreeSum × (total - subtreeSum). Track max."

## Mind-map anchor
**two passes · product = S × (total - S) · mod 1e9+7**

---

# PATTERN 34: Pseudo-Palindromic Paths (Backtracking + Bit Trick)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Count root-to-leaf paths that can be REARRANGED into a palindrome. A path is pseudo-palindromic if at most ONE digit has odd frequency.

**The Key Insight:** Use a BITMASK to track odd/even counts. XOR toggles bits. A number is pseudo-palindromic if it has at most one bit set (power of 2 or 0).

## The code
```java
int count = 0;

int pseudoPalindromicPaths(TreeNode root) {
    dfs(root, 0);
    return count;
}

void dfs(TreeNode node, int path) {
    if (node == null) return;
    
    // Toggle the bit for this digit
    path ^= (1 << node.val);
    
    // If leaf, check if pseudo-palindromic
    if (node.left == null && node.right == null) {
        // At most one bit set = (path & (path-1)) == 0
        if ((path & (path - 1)) == 0) {
            count++;
        }
        return;
    }
    
    dfs(node.left, path);
    dfs(node.right, path);
}
```

## The "aha" line
> "XOR toggles odd/even count. At leaf, check if at most one bit set: (path & (path-1)) == 0."

## Mind-map anchor
**XOR toggles bits · at most 1 bit = palindrome · (n & n-1) == 0**

---

# PATTERN 35: Delete Nodes and Return Forest (Post-Order with Set)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Given a list of nodes to delete, remove them and return the resulting FOREST (list of remaining trees).

**The Key Insight:** Post-order traversal. When deleting a node:
1. Its children become NEW roots (add to result)
2. Return null to parent (so parent's link is severed)

## The code
```java
List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
    List<TreeNode> forest = new ArrayList<>();
    Set<Integer> toDelete = new HashSet<>();
    for (int val : to_delete) toDelete.add(val);
    
    root = dfs(root, toDelete, forest);
    if (root != null) forest.add(root);  // Root survives? Add it!
    
    return forest;
}

TreeNode dfs(TreeNode node, Set<Integer> toDelete, List<TreeNode> forest) {
    if (node == null) return null;
    
    // Post-order: process children first
    node.left = dfs(node.left, toDelete, forest);
    node.right = dfs(node.right, toDelete, forest);
    
    // Should I be deleted?
    if (toDelete.contains(node.val)) {
        // My children become new roots
        if (node.left != null) forest.add(node.left);
        if (node.right != null) forest.add(node.right);
        return null;  // I'm deleted
    }
    
    return node;  // I survive
}
```

## The "aha" line
> "Post-order: process children first. If deleted, children become new roots, return null. If not deleted, return self."

## Mind-map anchor
**post-order · deleted → children are new roots · return null to sever link**

---

# PATTERN 36: Sum Root to Leaf Numbers (Path Value Accumulation)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Each root-to-leaf path forms a number (like 1→2→3 = 123). Sum ALL such numbers.

**The Key Insight:** Carry the "number so far" DOWN as a parameter. At each node: `newNum = oldNum * 10 + node.val`. At leaf, add to total.

## The code
```java
int sumNumbers(TreeNode root) {
    return dfs(root, 0);
}

int dfs(TreeNode node, int currentNum) {
    if (node == null) return 0;
    
    currentNum = currentNum * 10 + node.val;
    
    // Leaf? Return the complete number
    if (node.left == null && node.right == null) {
        return currentNum;
    }
    
    // Sum from both subtrees
    return dfs(node.left, currentNum) + dfs(node.right, currentNum);
}
```

## The "aha" line
> "Carry number DOWN: newNum = oldNum × 10 + val. At leaf, return the number. Sum all leaf numbers."

## Mind-map anchor
**carry number down · num = num*10 + val · sum at leaves**

---

# PATTERN 37: Convert Sorted List to BST (Two-Pointer + Recursion)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Convert a sorted linked list to a height-balanced BST. Can't random access like array!

**The Key Insight:** Use slow/fast pointers to find MIDDLE. Middle becomes root. Recurse on left half and right half.

## The code
```java
TreeNode sortedListToBST(ListNode head) {
    if (head == null) return null;
    if (head.next == null) return new TreeNode(head.val);
    
    // Find middle (and the node before it)
    ListNode slow = head, fast = head, prev = null;
    while (fast != null && fast.next != null) {
        prev = slow;
        slow = slow.next;
        fast = fast.next.next;
    }
    
    // Cut the list
    if (prev != null) prev.next = null;
    
    // Middle becomes root
    TreeNode root = new TreeNode(slow.val);
    root.left = sortedListToBST(head == slow ? null : head);  // Left half
    root.right = sortedListToBST(slow.next);                   // Right half
    
    return root;
}
```

## The "aha" line
> "Find middle with slow/fast. Middle = root. Cut list, recurse on both halves."

## Mind-map anchor
**slow/fast finds middle · middle = root · cut and recurse**

---

# PATTERN 38: Two Sum IV - Input is BST (Inorder + Two Pointers)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Find if two nodes in BST sum to target. Like Two Sum, but on a tree!

**The Key Insight:** Inorder traversal gives SORTED array. Then use two-pointer technique!

## The code
```java
boolean findTarget(TreeNode root, int k) {
    List<Integer> sorted = new ArrayList<>();
    inorder(root, sorted);
    
    // Two-pointer on sorted list
    int left = 0, right = sorted.size() - 1;
    while (left < right) {
        int sum = sorted.get(left) + sorted.get(right);
        if (sum == k) return true;
        if (sum < k) left++;
        else right--;
    }
    return false;
}

void inorder(TreeNode node, List<Integer> list) {
    if (node == null) return;
    inorder(node.left, list);
    list.add(node.val);
    inorder(node.right, list);
}
```

## The "aha" line
> "Inorder = sorted. Then two-pointer: too small → move left, too big → move right."

## Mind-map anchor
**inorder → sorted · two-pointer technique · O(n) space**

---

# PATTERN 39: Balance a BST (Inorder → Array → Rebuild)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Given an unbalanced BST, make it balanced. 

**The Key Insight:** 
1. Inorder traversal → sorted array
2. Build balanced BST from sorted array (middle = root, recurse)

## The code
```java
TreeNode balanceBST(TreeNode root) {
    List<Integer> sorted = new ArrayList<>();
    inorder(root, sorted);
    return buildBST(sorted, 0, sorted.size() - 1);
}

void inorder(TreeNode node, List<Integer> list) {
    if (node == null) return;
    inorder(node.left, list);
    list.add(node.val);
    inorder(node.right, list);
}

TreeNode buildBST(List<Integer> nums, int left, int right) {
    if (left > right) return null;
    
    int mid = left + (right - left) / 2;
    TreeNode root = new TreeNode(nums.get(mid));
    root.left = buildBST(nums, left, mid - 1);
    root.right = buildBST(nums, mid + 1, right);
    
    return root;
}
```

## The "aha" line
> "Inorder gives sorted. Build balanced BST: middle = root, recurse on halves."

## Mind-map anchor
**inorder → sorted array → middle = root → recurse**

---

# PATTERN 40: Unique Binary Search Trees (Catalan Number DP)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** How many STRUCTURALLY UNIQUE BSTs can store values 1 to n?

**The Key Insight:** This is the **Catalan Number**! For each root i:
- Left subtree has i-1 nodes
- Right subtree has n-i nodes
- Total combinations = left_count × right_count

```
dp[n] = Σ dp[i-1] × dp[n-i] for i = 1 to n
dp[0] = dp[1] = 1
```

## The code
```java
int numTrees(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 1;  // Empty tree
    dp[1] = 1;  // Single node
    
    for (int nodes = 2; nodes <= n; nodes++) {
        for (int root = 1; root <= nodes; root++) {
            int leftCount = root - 1;      // Nodes smaller than root
            int rightCount = nodes - root;  // Nodes larger than root
            dp[nodes] += dp[leftCount] * dp[rightCount];
        }
    }
    
    return dp[n];
}
```

## Tiny dry run — n = 3
```
dp[0] = 1, dp[1] = 1
dp[2] = dp[0]*dp[1] + dp[1]*dp[0] = 1 + 1 = 2
dp[3] = dp[0]*dp[2] + dp[1]*dp[1] + dp[2]*dp[0] = 2 + 1 + 2 = 5
```
Answer: **5** unique BSTs ✓

## The "aha" line
> "Catalan number! For each root, multiply left subtree count × right subtree count. Sum over all roots."

## Mind-map anchor
**Catalan number · dp[n] = Σ dp[i-1] × dp[n-i] · dp[0] = dp[1] = 1**

---

# PATTERN 41: Unique Binary Search Trees II (Generate All BSTs)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Actually GENERATE all unique BSTs, not just count them.

**The Key Insight:** For each possible root, recursively generate all left subtrees and all right subtrees, then combine them.

## The code
```java
List<TreeNode> generateTrees(int n) {
    if (n == 0) return new ArrayList<>();
    return generate(1, n);
}

List<TreeNode> generate(int start, int end) {
    List<TreeNode> trees = new ArrayList<>();
    
    if (start > end) {
        trees.add(null);  // Empty subtree
        return trees;
    }
    
    // Try each value as root
    for (int root = start; root <= end; root++) {
        List<TreeNode> leftTrees = generate(start, root - 1);
        List<TreeNode> rightTrees = generate(root + 1, end);
        
        // Combine all left subtrees with all right subtrees
        for (TreeNode left : leftTrees) {
            for (TreeNode right : rightTrees) {
                TreeNode node = new TreeNode(root);
                node.left = left;
                node.right = right;
                trees.add(node);
            }
        }
    }
    
    return trees;
}
```

## The "aha" line
> "For each root, generate all left trees × all right trees. Combine with nested loops."

## Mind-map anchor
**generate left × right · nested loops to combine · return list of trees**

---

# PATTERN 42: Sum of Distances in Tree (Re-rooting DP — Advanced)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** For each node, find the sum of distances to ALL other nodes. Naive is O(n²). Can we do O(n)?

**The Key Insight:** **Re-rooting DP** — two passes:
1. **First DFS:** Calculate answer for root (node 0)
2. **Second DFS:** Derive each child's answer from parent's answer

When moving root from parent to child:
- Nodes in child's subtree get 1 CLOSER
- All other nodes get 1 FARTHER

```
answer[child] = answer[parent] - count[child] + (n - count[child])
              = answer[parent] + n - 2 * count[child]
```

## The code
```java
int[] answer, count;
List<List<Integer>> graph;

int[] sumOfDistancesInTree(int n, int[][] edges) {
    answer = new int[n];
    count = new int[n];
    graph = new ArrayList<>();
    
    for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
        count[i] = 1;  // Count self
    }
    
    for (int[] edge : edges) {
        graph.get(edge[0]).add(edge[1]);
        graph.get(edge[1]).add(edge[0]);
    }
    
    // First DFS: calculate count[] and answer[0]
    dfs1(0, -1);
    
    // Second DFS: derive all answers from answer[0]
    dfs2(0, -1, n);
    
    return answer;
}

void dfs1(int node, int parent) {
    for (int child : graph.get(node)) {
        if (child != parent) {
            dfs1(child, node);
            count[node] += count[child];
            answer[node] += answer[child] + count[child];
        }
    }
}

void dfs2(int node, int parent, int n) {
    for (int child : graph.get(node)) {
        if (child != parent) {
            // Re-root formula
            answer[child] = answer[node] + n - 2 * count[child];
            dfs2(child, node, n);
        }
    }
}
```

## The "aha" line
> "Re-rooting: first DFS gets root's answer. Second DFS: child's answer = parent's answer + n - 2×count[child]."

## Mind-map anchor
**re-rooting DP · two passes · answer[child] = answer[parent] + n - 2×count[child]**

---

# PATTERN 43: Recover Binary Search Tree (Inorder Anomaly Detection)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Two nodes in a BST were SWAPPED by mistake. Find and fix them WITHOUT changing the structure.

**The Key Insight:** Inorder of valid BST is SORTED. Two swapped nodes create anomalies:
- **Adjacent swap:** ONE anomaly (prev > curr)
- **Non-adjacent swap:** TWO anomalies

Track `first` (first anomaly's prev) and `second` (last anomaly's curr). Swap their values!

## The code
```java
TreeNode first = null, second = null, prev = null;

void recoverTree(TreeNode root) {
    inorder(root);
    // Swap the values
    int temp = first.val;
    first.val = second.val;
    second.val = temp;
}

void inorder(TreeNode node) {
    if (node == null) return;
    
    inorder(node.left);
    
    // Check for anomaly
    if (prev != null && prev.val > node.val) {
        if (first == null) {
            first = prev;  // First anomaly: prev is wrong
        }
        second = node;     // Always update second (handles both cases)
    }
    prev = node;
    
    inorder(node.right);
}
```

## Tiny dry run
```
Swapped BST: [3, 2, 1] (should be [1, 2, 3])
Inorder: 3 → 2 → 1
- At 2: prev=3 > curr=2 → first=3, second=2
- At 1: prev=2 > curr=1 → second=1
- Swap 3 and 1 → Fixed!
```

## The "aha" line
> "Inorder should be sorted. Find anomalies where prev > curr. First anomaly's prev and last anomaly's curr are the swapped nodes."

## Mind-map anchor
**inorder anomaly · first = prev of first anomaly · second = curr of last anomaly · swap values**

---

# PATTERN 44: Trim a BST (Range Pruning)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Keep only nodes with values in range [low, high]. Remove everything else.

**The Key Insight:** Use BST property:
- If `node.val < low`: entire left subtree is too small, return trimmed right
- If `node.val > high`: entire right subtree is too big, return trimmed left
- Otherwise: trim both children and keep node

## The code
```java
TreeNode trimBST(TreeNode node, int low, int high) {
    if (node == null) return null;
    
    // Too small: skip me and my left subtree
    if (node.val < low) {
        return trimBST(node.right, low, high);
    }
    
    // Too big: skip me and my right subtree
    if (node.val > high) {
        return trimBST(node.left, low, high);
    }
    
    // In range: trim children and keep me
    node.left = trimBST(node.left, low, high);
    node.right = trimBST(node.right, low, high);
    return node;
}
```

## The "aha" line
> "Too small? Return trimmed right. Too big? Return trimmed left. In range? Trim both children."

## Mind-map anchor
**val < low → skip left subtree · val > high → skip right subtree · in range → trim both**

---

# PATTERN 45: All Nodes Distance K (BFS from Target)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Find all nodes at distance K from a target node. Can go UP to parent too!

**The Key Insight:** 
1. Build a PARENT MAP (so we can go up)
2. BFS from target, treating tree as undirected graph

## The code
```java
List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    // Build parent map
    Map<TreeNode, TreeNode> parent = new HashMap<>();
    buildParentMap(root, null, parent);
    
    // BFS from target
    Queue<TreeNode> queue = new LinkedList<>();
    Set<TreeNode> visited = new HashSet<>();
    queue.offer(target);
    visited.add(target);
    
    int distance = 0;
    while (!queue.isEmpty()) {
        if (distance == k) {
            List<Integer> result = new ArrayList<>();
            for (TreeNode node : queue) result.add(node.val);
            return result;
        }
        
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode node = queue.poll();
            
            // Go to children and parent
            if (node.left != null && !visited.contains(node.left)) {
                visited.add(node.left);
                queue.offer(node.left);
            }
            if (node.right != null && !visited.contains(node.right)) {
                visited.add(node.right);
                queue.offer(node.right);
            }
            TreeNode p = parent.get(node);
            if (p != null && !visited.contains(p)) {
                visited.add(p);
                queue.offer(p);
            }
        }
        distance++;
    }
    
    return new ArrayList<>();
}

void buildParentMap(TreeNode node, TreeNode par, Map<TreeNode, TreeNode> map) {
    if (node == null) return;
    map.put(node, par);
    buildParentMap(node.left, node, map);
    buildParentMap(node.right, node, map);
}
```

## The "aha" line
> "Build parent map first. Then BFS from target, going to children AND parent. Stop at distance K."

## Mind-map anchor
**parent map · BFS from target · go up/down/sideways · visited set**

---

# PATTERN 46: Flatten Binary Tree to Linked List (Preorder Rewiring)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Flatten tree into a "linked list" using right pointers, in PREORDER.

**The Key Insight:** Process in REVERSE preorder (right → left → root). Keep track of `prev` node. Each node's right = prev.

## The code
```java
TreeNode prev = null;

void flatten(TreeNode root) {
    if (root == null) return;
    
    // Reverse preorder: right, left, root
    flatten(root.right);
    flatten(root.left);
    
    // Rewire
    root.right = prev;
    root.left = null;
    prev = root;
}
```

## Alternative (Iterative with stack)
```java
void flatten(TreeNode root) {
    if (root == null) return;
    
    Stack<TreeNode> stack = new Stack<>();
    stack.push(root);
    
    while (!stack.isEmpty()) {
        TreeNode node = stack.pop();
        
        if (node.right != null) stack.push(node.right);
        if (node.left != null) stack.push(node.left);
        
        if (!stack.isEmpty()) {
            node.right = stack.peek();
        }
        node.left = null;
    }
}
```

## The "aha" line
> "Reverse preorder (right → left → root). Each node's right = prev. Or use stack: push right then left, peek for next."

## Mind-map anchor
**reverse preorder · right = prev · or stack: push right, left, peek for next**

---

# PATTERN 47: Construct BST from Preorder (Range Validation)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Given preorder traversal of BST, reconstruct the tree.

**The Key Insight:** First element is root. Use RANGE bounds to determine which elements go left vs right.

## The code
```java
int index = 0;

TreeNode bstFromPreorder(int[] preorder) {
    return build(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
}

TreeNode build(int[] preorder, int min, int max) {
    if (index >= preorder.length) return null;
    
    int val = preorder[index];
    if (val < min || val > max) return null;  // Out of valid range
    
    TreeNode node = new TreeNode(val);
    index++;
    
    node.left = build(preorder, min, val);    // Left: must be < val
    node.right = build(preorder, val, max);   // Right: must be > val
    
    return node;
}
```

## The "aha" line
> "Use range bounds. If value is out of range, return null. Left subtree range: [min, val). Right subtree range: (val, max]."

## Mind-map anchor
**preorder: root first · range bounds · left < val < right**

---

# PATTERN 48: Range Sum of BST (BST Pruned Search)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Sum all node values in range [low, high]. Use BST property to SKIP entire subtrees!

**The Key Insight:** 
- If `node.val < low`: skip left subtree (all values too small)
- If `node.val > high`: skip right subtree (all values too big)
- Otherwise: add value and check both sides

## The code
```java
int rangeSumBST(TreeNode node, int low, int high) {
    if (node == null) return 0;
    
    if (node.val < low) {
        return rangeSumBST(node.right, low, high);  // Skip left
    }
    if (node.val > high) {
        return rangeSumBST(node.left, low, high);   // Skip right
    }
    
    // In range: add me + both sides
    return node.val + rangeSumBST(node.left, low, high) 
                    + rangeSumBST(node.right, low, high);
}
```

## The "aha" line
> "BST lets you prune: too small → skip left, too big → skip right. In range → add and check both."

## Mind-map anchor
**BST pruning · val < low → go right · val > high → go left · in range → add + both**

---

# PATTERN 49: Minimum Difference in BST (Inorder + Track Previous)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Find minimum absolute difference between ANY two nodes. In BST, minimum difference is always between ADJACENT nodes in inorder!

**The Key Insight:** Inorder = sorted. Track `prev` node. Min diff = min of all `curr - prev`.

## The code
```java
int minDiff = Integer.MAX_VALUE;
TreeNode prev = null;

int getMinimumDifference(TreeNode root) {
    inorder(root);
    return minDiff;
}

void inorder(TreeNode node) {
    if (node == null) return;
    
    inorder(node.left);
    
    if (prev != null) {
        minDiff = Math.min(minDiff, node.val - prev.val);
    }
    prev = node;
    
    inorder(node.right);
}
```

## The "aha" line
> "Inorder = sorted. Min diff is always between adjacent elements. Track prev, compute diff at each step."

## Mind-map anchor
**inorder = sorted · min diff = adjacent · track prev**

---

# PATTERN 50: Closest BST Value (BST Binary Search)

## 💡 The "Aha!" Moment

**The Dopamine Trigger:** Find the value closest to target. Use BST property to narrow down!

**The Key Insight:** At each node, update closest if current is better. Then go LEFT if target < val, RIGHT if target > val.

## The code
```java
int closestValue(TreeNode root, double target) {
    int closest = root.val;
    
    while (root != null) {
        // Update closest if current is better
        if (Math.abs(root.val - target) < Math.abs(closest - target)) {
            closest = root.val;
        }
        
        // BST navigation
        root = (target < root.val) ? root.left : root.right;
    }
    
    return closest;
}
```

## The "aha" line
> "BST search, but track closest along the way. Update if current is closer, then navigate based on target."

## Mind-map anchor
**BST search + track closest · update if closer · O(h)**

---

# 📊 UPDATED MAANG Coverage Map

| Problem | Pattern | LeetCode |
|---------|---------|----------|
| Maximum Depth | 0 (pure UP) | 104 |
| Diameter | 1 (answer vs return) | 543 |
| Good Nodes | 2 (DOWN pipe) | 1448 |
| Invert Tree | 3 (preorder mutate) | 226 |
| Same Tree | 4 (parallel walk) | 100 |
| Is Mirror | 4-variant | 101 |
| LCA | 5 (bubble-up) | 236 |
| Inorder Successor BST | 6 (BST search) | 285 |
| Largest BST Subtree | 7 (bundle return) | 333 |
| Distance Between Nodes | 8 (LCA + depth) | 1740 |
| Max Path Sum | 9 (answer vs return) | 124 |
| Balanced Tree | 10 (height + check) | 110 |
| Path Sum II | 11 (backtracking) | 113 |
| Validate BST | 12 (range DOWN) | 98 |
| Level Order | 13 (BFS) | 102 |
| Kth Smallest BST | 14 (inorder + count) | 230 |
| Serialize/Deserialize | 15 (preorder + queue) | 297 |
| Build from Pre+In | 16 (divide & conquer) | 105 |
| Subtree of Another | 17 (nested recursion) | 572 |
| **BST Search** | **18** | **700** |
| **BST Insert** | **19** | **701** |
| **BST Delete** | **20** | **450** |
| **House Robber III** | **21 (bundle return)** | **337** |
| **Binary Tree Cameras** | **22 (3-state DP)** | **968** |
| **Distribute Coins** | **23 (flow counting)** | **979** |
| **Max Width** | **24 (BFS + index)** | **662** |
| **Next Right Pointers** | **25 (level linking)** | **116/117** |
| **Boundary Traversal** | **26 (3-part)** | **545** |
| **Left Side View** | **27 (mirror of right)** | **— (variant)** |
| **Diagonal Traversal** | **28 (coordinate)** | **— (variant)** |
| **Merge Two Trees** | **29 (parallel)** | **617** |
| **Average of Levels** | **30 (BFS aggregate)** | **637** |
| **Longest Univalue Path** | **31 (diameter variant)** | **687** |
| **LCA Deepest Leaves** | **32 (depth + LCA)** | **1123** |
| **Max Product Split** | **33 (total sum trick)** | **1339** |
| **Pseudo-Palindromic** | **34 (bit trick)** | **1457** |
| **Delete & Return Forest** | **35 (post-order set)** | **1110** |
| **Sum Root to Leaf Numbers** | **36 (path accumulation)** | **129** |
| **Sorted List to BST** | **37 (slow/fast)** | **109** |
| **Two Sum IV BST** | **38 (inorder + 2ptr)** | **653** |
| **Balance a BST** | **39 (inorder rebuild)** | **1382** |
| **Unique BSTs Count** | **40 (Catalan DP)** | **96** |
| **Unique BSTs Generate** | **41 (generate all)** | **95** |
| **Sum of Distances** | **42 (re-rooting DP)** | **834** |
| **Recover BST** | **43 (inorder anomaly)** | **99** |
| **Trim BST** | **44 (range pruning)** | **669** |
| **All Nodes Distance K** | **45 (parent map + BFS)** | **863** |
| **Flatten to Linked List** | **46 (reverse preorder)** | **114** |
| **BST from Preorder** | **47 (range validation)** | **1008** |
| **Range Sum of BST** | **48 (BST pruning)** | **938** |
| **Min Diff in BST** | **49 (inorder + prev)** | **530** |
| **Closest BST Value** | **50 (BST search + track)** | **270** |
| Path Sum III | 11-variant (prefix sum) | 437 |
| Vertical Order | 13-variant (BFS + col) | 314 |
| Right Side View | 27-mirror | 199 |

---

# 🧠 MASTER PATTERN RECOGNITION CHEAT SHEET

## By Question Type

| When you see... | Think... | Pattern # |
|-----------------|----------|-----------|
| "Maximum/minimum depth" | Pure UP pipe | 0 |
| "Diameter/longest path" | Answer vs Return split | 1, 31 |
| "Count nodes with condition from root" | DOWN pipe (carry max/min) | 2 |
| "Modify tree structure" | Preorder mutate | 3 |
| "Compare two trees" | Parallel walk | 4, 29 |
| "Find ancestor" | Bubble-up LCA | 5, 32 |
| "BST search/insert/delete" | BST property | 18, 19, 20 |
| "BST range query" | BST pruning | 48 |
| "BST min/max difference" | Inorder + track prev | 49 |
| "Closest value in BST" | BST search + track | 50 |
| "Return multiple values" | Bundle return | 7, 21, 22 |
| "Path sum to leaf" | Backtracking or accumulation | 11, 36 |
| "Validate BST" | Range DOWN | 12, 44 |
| "Level by level" | BFS + freeze size | 13, 24, 25, 30 |
| "Kth element in BST" | Inorder + count | 14 |
| "Serialize tree" | Preorder + markers | 15 |
| "Build tree from traversals" | Divide & conquer | 16, 47 |
| "Tree DP with states" | State machine return | 21, 22 |
| "Flow/distribution" | Excess counting | 23 |
| "Width with nulls" | Heap indexing | 24 |
| "Views (left/right/boundary)" | DFS order or BFS position | 26, 27 |
| "Two swapped nodes" | Inorder anomaly | 43 |
| "Distance from target" | Parent map + BFS | 45 |
| "Flatten to list" | Reverse preorder | 46 |
| "Count unique structures" | Catalan number | 40, 41 |

---

# 🏆 FINAL MASTERY CHECKLIST

## Core Patterns (Must Know Cold)
- [ ] Maximum Depth (pure UP)
- [ ] Diameter (answer vs return)
- [ ] LCA (bubble-up)
- [ ] Validate BST (range DOWN)
- [ ] Level Order (BFS freeze)
- [ ] Serialize/Deserialize
- [ ] Build from Preorder + Inorder

## BST Operations (Must Know Cold)
- [ ] Search, Insert, Delete
- [ ] Kth Smallest (inorder)
- [ ] Validate BST
- [ ] Recover BST (two swapped)
- [ ] Trim BST

## Advanced Patterns (Know Well)
- [ ] House Robber III (bundle return)
- [ ] Binary Tree Cameras (3-state)
- [ ] Max Path Sum (answer vs return)
- [ ] All Nodes Distance K (parent map)
- [ ] Sum of Distances (re-rooting)

## BFS Variants (Know Well)
- [ ] Level Order + variants
- [ ] Max Width (heap indexing)
- [ ] Next Right Pointers
- [ ] Right/Left Side View

## Construction Patterns (Know Well)
- [ ] Build from traversals
- [ ] Sorted Array/List to BST
- [ ] Unique BSTs (Catalan)

---

**Prev →** `01_Tree_Algorithms.md`  ·  **Next →** `../14_Heap/01_Heap_Patterns.md`
