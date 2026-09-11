# Section 13 — Tree Patterns Through ONE Recursion Template (FAANG Deep Dive)

> **How to use this file (read this first — and yes, this line is for the podcast too):**
> This document is written to be **read out loud**. You will drop it into NotebookLM, generate a podcast, and listen on loop. So everything here is spoken in plain sentences, in a repeating rhythm, so your brain forms a **mind map by repetition**.
>
> Every single pattern below is explained through the **exact same 6-step skeleton**. That repetition is intentional. After a handful of patterns, your brain will already be *predicting* the next step before the narrator says it. That is what "becoming a pro" actually feels like — the structure becomes muscle memory. There are **18 patterns** here, but they're really just a few reusable *moves* wearing different costumes.

---

## The ONE Template That Rules All Tree Problems

Before any pattern, burn this into memory. Every tree recursion is these 6 questions, always in this order:

```
solve(node, [state passed down]):

    1. PARAMETERS      → what state do I carry DOWN from my parent?
    2. BASE CASE       → when do I stop? (usually node == null)
    3. PRE-ORDER work  → what do I compute BEFORE calling children? (top-down)
    4. GO LEFT / RIGHT → delegate the two subproblems, trust the recursion
    5. POST-ORDER work → combine what children returned (bottom-up)
    6. RETURN          → what do I hand UP to my parent?
```

### The Unified Mental Cheat Sheet (say this in your sleep)

- **Parameters (DOWN):** Push state down → Pre-order / top-down context.
- **Recursive Calls (EXPLORE):** Delegate to the left and right subtrees.
- **Post-Call Logic (COMBINE):** Aggregate child results → post-order computation.
- **Return Values (UP):** Pull answers up → bottom-up propagation to ancestors.

### The two directions of information — the single biggest idea

There are only **two pipes** for moving information in a tree:

1. **The DOWN pipe = function parameters.** A parent hands context to a child *before* the child runs. Example: "the max value seen on the path so far," or "the valid min/max range." This is **top-down / pre-order**.
2. **The UP pipe = return values.** A child hands an answer back to the parent *after* the child finishes. Example: "my subtree's height," or "did I find the target below me." This is **bottom-up / post-order**.

> **The one correction to tattoo on your brain:** Parameters only move state **downward**. They do **not** carry values back up during backtracking — *unless* you deliberately pass a shared mutable object (an array, a list, a hashmap, or a class field). Upward data transfer is strictly the job of **return values**. Mix these two up and every hard tree problem will feel impossible.

### How I will teach every pattern (the repeating rhythm)

For each pattern you will hear the same 8 beats:

1. **The story** — a plain-English analogy so a junior dev *feels* it.
2. **What the interviewer is really testing.**
3. **Walk the 6-step template**, one step at a time.
4. **The code** (Java).
5. **A tiny dry run** so the abstract becomes concrete.
6. **The "aha" line** — the one sentence that unlocks it.
7. **The classic trap.**
8. **Mind-map anchor** — 3 keywords to recall the whole thing.

Let's build you into a pro. One template, many faces.

### IMPORTANT — how to read every "Walk the 6-step template" section

Your goal is **not** to memorize each problem. Your goal is to learn to **derive** the solution by asking the same 6 questions every time. So in each pattern I don't just *state* the 6 steps — I turn each step into a **question you ask yourself**, then give the answer, then say **why**. Read them like this:

> **Ask → Answer → Why.**

If you internalize the *questions*, you can walk up to a brand-new tree problem you've never seen, ask the same 6 questions in order, and the code writes itself. That is what "pattern recognition" actually is: not recognizing the *problem*, but recognizing **which answer each of the 6 questions takes**. The 6 questions never change. Only the answers do.

The 6 questions, forever:
1. **PARAMETERS:** *"What must I know from ABOVE (ancestors) to do my job? Carry it as a parameter."*
2. **BASE CASE:** *"What is the smallest input where the answer is obvious with no recursion?"*
3. **PRE-ORDER:** *"Is there work I must do BEFORE my children run — because it depends on ancestor context?"*
4. **GO LEFT / RIGHT:** *"What subproblem do I hand each child? Do I trust them to return the right thing?"*
5. **POST-ORDER:** *"Now that both children answered, how do I COMBINE their answers with myself?"*
6. **RETURN:** *"What single thing does my PARENT need from me? (This may differ from the global answer.)"*

---

# PATTERN 0: The Foundation — Maximum Depth

*(Every other pattern is a mutation of this one. Master this and you're 60% done.)*

## The Story
Imagine you're the boss of a company and someone asks, "How many layers deep is our org chart?" You don't count it yourself. You turn to your two direct reports and ask each of them the same question: "How deep is *your* team?" You wait for both numbers, take the bigger one, add 1 for yourself, and report it up. Every manager does the exact same thing. A person with no reports answers "0." That is the whole algorithm.

## What the interviewer is really testing
Do you understand that a tree answer is built by **trusting your children** and doing a tiny bit of arithmetic on their answers? This is the "leap of faith" of recursion.

## Walk the 6-step template
> Read each step as **Ask → Answer → Why.** This is the template you'll reuse for *every* problem, so feel the reasoning, not the result.

1. **PARAMETERS — Ask:** *"Do I need anything from my ancestors to compute my depth?"*
   **Answer:** No. Just the `node`. Carry nothing down.
   **Why:** Depth is about what's *below* me, not above. Zero ancestor context needed → no extra parameter. (Contrast this with Good Nodes later, where the answer flips to "yes.")

2. **BASE CASE — Ask:** *"What's the smallest tree where I know the depth instantly?"*
   **Answer:** The empty tree. `node == null` → return `0`.
   **Why:** Null is *nothing* — zero levels. This is the floor where the call stack stops growing and starts unwinding. Every recursion needs a floor or it runs forever.

3. **PRE-ORDER — Ask:** *"Is there work I must do BEFORE my kids run?"*
   **Answer:** None.
   **Why:** I don't hand my children any context, so there's nothing to prepare. (This step is *empty* here — and that's a valid, common answer. Noticing "pre-order is empty" is itself a signal: the problem is purely bottom-up.)

4. **GO LEFT / RIGHT — Ask:** *"What do I ask each child, and do I trust the answer?"*
   **Answer:** `left = maxDepth(node.left)`, `right = maxDepth(node.right)`. Yes — trust each returns the true depth of that side.
   **Why:** This is the *leap of faith*. If you don't trust the recursion for the smaller subtree, you'll try to hand-trace the whole tree and drown. Assume it works for smaller inputs (induction).

5. **POST-ORDER — Ask:** *"Both kids answered. How do I combine them with myself?"*
   **Answer:** Take `Math.max(left, right)` — the deeper of my two branches.
   **Why:** Depth is a *longest* chain downward, and a chain goes through only one side. So I pick the bigger side.

6. **RETURN — Ask:** *"What does my parent need from me?"*
   **Answer:** `1 + Math.max(left, right)` — my deeper side, plus me.
   **Why:** My parent counts me as one level on its way down. The `+1` *is* me. Here the return value **equals** the answer — but hold that thought; in Diameter they'll split apart.

## The code
```java
int maxDepth(TreeNode node) {
    if (node == null) return 0;              // 2. base case
    int left  = maxDepth(node.left);         // 4. go left
    int right = maxDepth(node.right);        // 4. go right
    return 1 + Math.max(left, right);        // 5+6. combine + return up
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
- `maxDepth(D)`: left=0, right=0 → returns 1
- `maxDepth(B)`: left=1 (from D), right=0 → returns 2
- `maxDepth(C)`: 0,0 → returns 1
- `maxDepth(A)`: left=2, right=1 → returns `1 + max(2,1)` = **3** ✓

## The "aha" line
> "I don't measure the tree. I ask my children for their depths and just add myself."

## The classic trap
Returning depth in **pre-order** (trying to pass a "current depth" down and track a max). That works too, but it's the *height-as-parameter* style. For pure depth, bottom-up return is cleaner. Also: base case is `0`, not `1` — null is *nothing*, not a node.

## Mind-map anchor
**`null→0` · `max(left,right)` · `+1 for me`**

---

# PATTERN 1: Diameter of Binary Tree

*(The first "two-brained" pattern: the number you RETURN is different from the number you ANSWER. This single idea appears in half of all hard tree problems, so slow down here.)*

## The Story
The diameter is the **longest walk between any two nodes** in the tree — measured in edges. Here's the twist that trips everyone: that longest walk **does not have to pass through the root**. It could be buried deep in one branch. So you can't just measure from the top. Instead, you stand at *every* node and ask: "If the longest path *bends* right here at me — going down my left side, through me, and down my right side — how long is it?" You quietly remember the biggest such bend you've ever seen. That remembered maximum is the answer.

## What the interviewer is really testing
Can you separate **"the value I return to my parent"** from **"the global answer I'm tracking"**? These are two *different* numbers computed at the same node. This is the #1 leveling-up moment in trees.

## Walk the 6-step template
> Ask → Answer → Why. **The star of this pattern is steps 5 and 6 — where "answer" and "return" split into two different numbers.**

1. **PARAMETERS — Ask:** *"Do I need ancestor info?"*
   **Answer:** No — just `node`. The running max diameter lives **outside** the recursion (a field or one-element array).
   **Why:** The longest path is a property of what's *below*, so it's discovered bottom-up. The global lives outside because *many different nodes* might each produce a candidate answer, and we want the max across all of them.

2. **BASE CASE — Ask:** *"Smallest tree with an obvious height?"*
   **Answer:** `node == null` → height `0`.
   **Why:** An empty branch contributes zero edges to any path. This makes leaf math clean: a leaf sees `0` from both sides.

3. **PRE-ORDER — Ask:** *"Any work before the children?"*
   **Answer:** None.
   **Why:** Pure bottom-up again — no context flows down.

4. **GO LEFT / RIGHT — Ask:** *"What do I get from each child?"*
   **Answer:** `leftH = height(left)`, `rightH = height(right)` — each child's height.
   **Why:** The longest path bending at me is built from how deep I can reach on each side. So I need both heights.

5. **POST-ORDER — Ask:** *"How do I turn the two heights into a candidate ANSWER?"*
   **Answer:** The path that *bends at me* spans `leftH + rightH` edges. Update `diameter = max(diameter, leftH + rightH)`.
   **Why:** A path through me goes *down-left, up through me, down-right* — that's left height plus right height edges. I record the best I've ever seen because the true diameter might bend at *some other* node, not me.

6. **RETURN — Ask:** *"What does my parent need — the same number I just computed?"*
   **Answer:** **No.** Return `1 + max(leftH, rightH)`, NOT `leftH + rightH`.
   **Why (the whole pattern):** My parent wants to extend a path *through* me and keep going up. But a path can't **fork** — it can pass through me using only **one** of my arms. So upward I offer just my taller arm plus myself. The `leftH+rightH` was a *terminal* answer (the path ends by bending here); it can't be extended, so it never goes up. **This "answer ≠ return" split is the single most important idea in hard tree problems.**

## The code
```java
int diameter = 0;

int diameterOfBinaryTree(TreeNode root) {
    height(root);
    return diameter;
}

int height(TreeNode node) {
    if (node == null) return 0;                       // 2. base case
    int leftH  = height(node.left);                   // 4. go left
    int rightH = height(node.right);                  // 4. go right
    diameter = Math.max(diameter, leftH + rightH);    // 5. ANSWER: path bending here
    return 1 + Math.max(leftH, rightH);               // 6. RETURN: one side only
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
- At node 2: `leftH=1, rightH=1` → bend = `1+1 = 2` → diameter becomes **2**. Returns `1+max(1,1)=2`.
- At node 3: returns 1.
- At node 1: `leftH=2, rightH=1` → bend = `2+1 = 3` → diameter becomes **3**. ✓
- Longest walk: `4 → 2 → 1 → 3` = 3 edges.

## The "aha" line
> "At each node I *answer* with both sides added (the bend), but I *return* only one side plus me — because my parent's path can't fork through me."

## The classic trap
Returning `leftH + rightH` upward instead of `1 + max(leftH, rightH)`. If you return the bend, you're claiming a parent can walk down both your arms at once — impossible. The bend is the **answer**, `max` side is the **return**. Keep them in two separate mental buckets.

## Mind-map anchor
**`answer = left+right` · `return = 1+max` · "path can't fork"**

---

# PATTERN 2: Count Good Nodes (the TOP-DOWN / parameter pattern)

*(This is the mirror image of Diameter. Diameter used the UP pipe. Good Nodes uses the DOWN pipe — parameters. This is where you truly feel the difference between the two directions.)*

## The Story
Walk from the root down any path. A node is **"good"** if, on the way from the root to that node, **nobody you passed was bigger than you**. In other words, you are good if you are the tallest person seen so far on your root-to-you path. So as you descend, you carry a running value: **"the maximum I've seen so far."** At each node you check "am I ≥ that max?" — if yes, you're good, count yourself. Then you pass an *updated* max down to your children.

## What the interviewer is really testing
Can you use **parameters to push state DOWN** (top-down / pre-order)? The context — "max on the path so far" — is enforced by a parent onto its children *before* they run. This is the opposite direction from Diameter.

## Walk the 6-step template
> Ask → Answer → Why. **The star here is steps 1 and 3 — the DOWN pipe. Notice how everything mirrors Diameter but flipped in direction.**

1. **PARAMETERS — Ask:** *"Do I need anything from my ANCESTORS to judge myself?"*
   **Answer:** YES — carry `maxSoFar` = the largest value on the path from the root down to my parent.
   **Why (the whole pattern):** "Good" is defined relative to *who came before me* on the root path. That information lives **above** me, so the only way to have it is for a parent to **hand it down** as a parameter. This is the moment the answer to Question 1 flips from "no" (Depth/Diameter) to "yes." **When the definition mentions "ancestors / path from root / so far," Question 1 is always YES → parameter.**

2. **BASE CASE — Ask:** *"Smallest input with an obvious count?"*
   **Answer:** `node == null` → return `0`.
   **Why:** An empty spot has no node to be good, so it contributes zero to the count.

3. **PRE-ORDER — Ask:** *"Is there work I must do BEFORE the children — using the ancestor context I was handed?"*
   **Answer:** YES, two things. (a) Judge myself: `good = (node.val >= maxSoFar) ? 1 : 0`. (b) Build the *updated* context to pass down: `newMax = max(maxSoFar, node.val)`.
   **Why:** My children's "max so far" must **include me**, so I compute `newMax` before calling them. Judging happens here too because it only depends on ancestors (already known) — not on children. **Rule of thumb: work that depends on ancestors happens in PRE-order; work that depends on descendants happens in POST-order.**

4. **GO LEFT / RIGHT — Ask:** *"What do I hand each child?"*
   **Answer:** The **updated** context: `dfs(node.left, newMax)`, `dfs(node.right, newMax)`.
   **Why:** Both children continue the same root-path with me now included. They'll return how many good nodes live in each subtree.

5. **POST-ORDER — Ask:** *"How do I combine the children's counts?"*
   **Answer:** Add them: `left + right`.
   **Why:** Total good nodes below me = good nodes in left subtree + good nodes in right subtree. Simple sum, because good-ness is per-node and independent across subtrees.

6. **RETURN — Ask:** *"What does my parent need?"*
   **Answer:** `good + left + right` — my own goodness plus both subtree totals.
   **Why:** My parent is accumulating a grand total; I hand up the count for my entire subtree (including me). Here return == the accumulated answer (there's no separate global needed).

## The code
```java
int goodNodes(TreeNode root) {
    return dfs(root, Integer.MIN_VALUE);   // start: nothing seen, so -infinity
}

int dfs(TreeNode node, int maxSoFar) {         // 1. parameter carries state DOWN
    if (node == null) return 0;                // 2. base case
    int good = (node.val >= maxSoFar) ? 1 : 0; // 3. pre-order: judge me first
    int newMax = Math.max(maxSoFar, node.val); // 3. build context to push down
    int left  = dfs(node.left,  newMax);       // 4. go left with updated max
    int right = dfs(node.right, newMax);       // 4. go right with updated max
    return good + left + right;                // 5+6. combine + return up
}
```

## Tiny dry run
```
        3          (max seen: -inf → 3 is good)
       / \
      1   4        (4 >= 3 good; 1 < 3 not good)
       \   \
        3   5      (3 >= max(3,1)=3 good; 5 >= 4 good)
```
Good nodes: `3, 4, 5, and the deep 3` → **4 good nodes**.

## The "aha" line
> "I judge myself using state my parent handed me, THEN I hand an updated version of that state to my kids. Down pipe, pre-order."

## The classic trap
Trying to compute "good" in **post-order** (after children). You can't — goodness depends on **ancestors**, which are only known on the way **down**. If you find yourself needing ancestor info, that's your signal: **use a parameter, do the work in pre-order.**

## Down vs. Up — the twin comparison (say this out loud)
- **Good Nodes** needs **ancestor** info → push **DOWN** via parameter → **pre-order**.
- **Diameter / Depth** needs **descendant** info → pull **UP** via return → **post-order**.
> When a problem says *"from the root"*, *"on the path so far"*, *"ancestor"* → think DOWN/parameter/pre-order.
> When a problem says *"height"*, *"deepest"*, *"longest below"*, *"subtree"* → think UP/return/post-order.

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

*(The famous "return a node, not a number" pattern. The return value is a TreeNode, and its meaning shifts depending on where you are. Read slowly.)*

## The Story
Two cousins, `p` and `q`, are lost somewhere in a family tree. You want the **lowest** (closest) shared ancestor. Here's the elegant trick: every node asks its two subtrees, "Did you find *either* p or q down there?"
- If your **left** subtree reports "I found one" **and** your **right** subtree reports "I found one," then p and q are on **opposite sides of you** — so **you** are the meeting point. You are the LCA.
- If only one side reports a find, the LCA is somewhere on that side, so you just **pass that finding upward**.

## What the interviewer is really testing
Comfort with a recursion whose **return value is a node**, and whose meaning is context-dependent: sometimes it means "I found a target," sometimes it means "I am the answer." Same return slot, layered meaning.

## Walk the 6-step template
> Ask → Answer → Why. **The twist: the return value is a NODE whose meaning changes depending on where you are.**

1. **PARAMETERS — Ask:** *"What do I carry?"* **Answer:** `node`, plus the two targets `p` and `q`. **Why:** Every frame must know *what it's hunting for* to report a find, so the targets ride along as parameters (they never change — pure read-only context).
2. **BASE CASE — Ask:** *"Which tiny inputs give an instant answer?"* **Answer:** `node == null` → return `null` (nothing here). `node == p || node == q` → return `node` ("I found a target — stop and report me up"). **Why:** Finding a target is itself a terminal signal; there's no need to search below a found node, because the *lowest* ancestor logic is handled by the parents receiving this signal.
3. **PRE-ORDER — Ask:** *"Work before children?"* **Answer:** The found-check above *is* the pre-order judgment. **Why:** I decide "am I a target?" before bothering to search below.
4. **GO LEFT / RIGHT — Ask:** *"What comes back from each side?"* **Answer:** `left = lca(node.left)`, `right = lca(node.right)` — each is either a found node/LCA or `null`. **Why:** I need to know *which sides* contain a target to decide if I'm the meeting point.
5. **POST-ORDER — Ask (THE key):** *"Given the two child reports, am I the answer?"* **Answer:** If `left != null && right != null` → the two targets were found on **opposite sides of me** → **I am the LCA**, return `node`. **Why:** The lowest node that has one target in its left subtree and the other in its right subtree *is by definition* their lowest common ancestor.
6. **RETURN — Ask:** *"If I'm not the meeting point, what do I send up?"* **Answer:** Whichever side is non-null (bubble the single find upward); if both null, return null. **Why:** A single non-null means "a target (or the already-found LCA) lives below me on this side" — I forward that upward unchanged so an ancestor can pair it with the other target. Notice the return slot carries **two meanings**: "a found target" low down, and "the confirmed LCA" once both sides met. Same slot, layered meaning.

## The code
```java
TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null || node == p || node == q) return node;  // 2+3. found or empty
    TreeNode left  = lca(node.left,  p, q);                   // 4. search left
    TreeNode right = lca(node.right, p, q);                   // 4. search right
    if (left != null && right != null) return node;           // 5. split → I'm LCA
    return (left != null) ? left : right;                     // 6. bubble the find up
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
- `lca(6)=null`, `lca(2)=null` → at node 5, left=null,right=null, but 5==p → **returns 5** (base case fires before recursion).
- Right side: `lca(1)` → 1==q → returns 1.
- At root 3: left=5 (non-null), right=1 (non-null) → **both found → return 3**. ✓

## The "aha" line
> "If p and q come back from opposite sides of me, I'm the meeting point. Otherwise I just forward whichever child found something."

## The classic trap
Writing `if (left != null) return left;` before checking that right is null too. If **both** are non-null you must return the **current node**, not just the left. Always test `left != null && right != null` **first**.

> **BST shortcut:** In a *Binary Search Tree*, LCA is even simpler — walk down: if both `p,q < node`, go left; if both `> node`, go right; the moment they split (or one equals node), that node is the LCA. O(h), no post-order needed. Mention this if the interviewer says "BST."

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

*(This is the section to loop most. Everything above collapses into a handful of decisions.)*

## The Master Decision Tree — pick your weapon in 10 seconds

Ask yourself these questions **in order**:

1. **"Does the answer need info from my ANCESTORS (root-to-me path)?"**
   → YES: push state **DOWN** as a **parameter**, do the work in **pre-order**. *(Good Nodes)*

2. **"Does the answer need info from my DESCENDANTS (what's below me)?"**
   → YES: pull answers **UP** via **return value**, do the work in **post-order**. *(Depth, Diameter, Max Path Sum, Balanced, LargestBST)*

3. **"Is the value I RETURN different from the ANSWER I track?"**
   → YES: keep a **global** for the answer, **return** only the extendable one-side value. *(Diameter, Max Path Sum)*

4. **"Do I need SEVERAL facts from each child, not just one number?"**
   → YES: **return an Info object/bundle**. *(Largest BST, Balanced-with-flag, Validate BST)*

5. **"Am I comparing/walking TWO nodes at once?"**
   → YES: recursion takes **two node args**. *(Same Tree, Is Mirror)*

6. **"Do I need EVERY path, and must the path reset between branches?"**
   → YES: **backtracking** — mutate a shared list, **undo after both children**. *(Path Sum II)*

7. **"Is it a BST and about order/next/search?"**
   → YES: exploit **inorder = sorted** and **O(h) directed walk**. *(Inorder Successor, BST LCA, Kth Smallest)*

8. **"Does the answer depend on LEVELS / nearest / a side-view?"**
   → YES: **BFS** with a queue, **freeze the level size**. *(Level Order, Right-Side View, Min Depth, Zigzag)*

9. **"Do I carry a POSITION (depth/column) to group or pick nodes?"**
   → YES: push a **coordinate DOWN** as a parameter, bucket by it. *(Vertical Order, Top/Bottom View, Right-Side View)*

10. **"Am I encoding/rebuilding a tree, or building one from traversals?"**
    → YES: **preorder + markers** (serialize) or **root-splits-array** (build from pre+in). *(Serialize/Deserialize, Build from Traversals)*

11. **"Do I run a sub-search at every node, or need a running prefix on the path?"**
    → YES: **nested DFS** (DFS inside DFS) or **prefix-sum map + backtrack**. *(Subtree of Another, Path Sum III)*

12. **"Is this a new problem?"** → Try to **compose** patterns you own. *(Distance = LCA + Depth)*

## The Two Pipes — the whole subject in two lines

- **DOWN pipe = parameters = pre-order = ancestor context.**
- **UP pipe = return values = post-order = descendant answers.**
- (And **backtracking** = a shared mutable object that uses the down pipe on entry and *undoes itself* on exit.)
- (And **BFS** = the sideways pipe: a queue that moves across a level instead of up/down.)

## The One-Table Pattern Map

| # | Pattern | Direction / Mode | Return vs Answer | Return type | Signature move |
|---|---|---|---|---|---|
| 0 | Max Depth | UP / DFS | same | int | `1 + max(L,R)` |
| 1 | Diameter | UP / DFS | **different** | int (height) | answer=`L+R`, return=`1+max` |
| 2 | Good Nodes | **DOWN** / DFS | — | int (count) | carry `maxSoFar` down |
| 3 | Invert | UP / DFS | — | node | swap kids |
| 3 | Same Tree | UP / DFS (2 args) | — | boolean | L-L, R-R |
| 3 | Is Mirror | UP / DFS (2 args) | — | boolean | **cross**: L-R, R-L |
| 4 | LCA | UP / DFS | — | node | both non-null ⇒ me |
| 5 | Inorder Successor | DOWN / walk | — | node | last node `> p` |
| 6 | Largest BST | UP / DFS | — | **Info bundle** | `L.max<val<R.min` |
| 7 | Distance | compose | — | int | LCA + depth |
| 8 | Max Path Sum | UP / DFS | **different** | int (gain) | clamp `max(0,child)` |
| 9 | Balanced | UP / DFS | same (w/ flag) | int or `−1` | `−1` = broken sentinel |
| 10 | Path Sum II | **backtrack** / DFS | — | void | add→explore→**remove** |
| 11 | Validate BST | **DOWN** / DFS | — | boolean | push `(low,high)` window down |
| 12 | Level Order family | **BFS** | — | list(s) | queue + **freeze size** |
| 13 | Kth Smallest | inorder / DFS | — | void | sorted walk + countdown |
| 14 | Serialize/Deserialize | preorder / DFS | — | string / node | null markers + token queue |
| 15 | Build from Pre+In | preorder / DFS | — | node | preorder=root, inorder=split |
| 16 | Subtree of Another | **nested DFS** | — | boolean | DFS launches `isSame` |
| 16 | Path Sum III | **DOWN + backtrack** | — | int | prefix map + seed 0 |
| 17 | Tree Views | **DOWN (coord)** / DFS or BFS | — | list(s) | carry `(row,col)`, group |

## The 60-Second Interview Script (memorize word-for-word)

> "This is a tree problem. First: is it about **levels or nearest**? If so, BFS with a queue, freezing the level size. Otherwise it's DFS recursion, and I ask: does the answer depend on **ancestors** or **descendants**? Ancestors → I push state down as a parameter and act in pre-order (like validate-BST's window or good-nodes' max). Descendants → I compute after my children return, in post-order. If the value my parent needs **differs** from the global answer — like diameter or max path sum — I track the answer in a field and return only the one-sided extendable value, because a path can't fork. If I need **several facts** from each child, I return a small Info object. If I need **every path**, I backtrack: add myself, explore, remove myself. And if it's a **BST and about order**, I lean on inorder-equals-sorted."

## Common-Mistake Rapid-Fire (loop this until reflexive)

- Base case for depth/size is **0**, not 1 (null is nothing).
- Diameter/MaxPathSum: **answer ≠ return**. Answer uses both sides; return uses one.
- Max Path Sum: init `MIN_VALUE`, and **clamp negatives** with `max(0, child)`.
- LCA: check `left != null && right != null` **first**, before returning a single side.
- Two-node problems (Same/Mirror): **null checks before `.val`**, always.
- Mirror is Same with the **cross** (L-R, R-L). That's the only difference.
- Largest BST: null identity is `min=+∞, max=−∞`; broken subtree returns `isBST=false`.
- Path Sum II: store a **copy** (`new ArrayList<>(path)`) and `remove` **once, after both** kids.
- Inorder Successor: **strictly** greater (`>`, not `>=`); don't forget the no-right-subtree climb.
- Distance: measure from the **LCA** to skip the `−2×depth(LCA)` correction.
- Validate BST: never use a **local** parent-child check — push the `(low, high)` window down; use `long` bounds.
- BFS: **freeze `size` before the inner loop**, or levels merge together.
- Kth Smallest: don't build the whole list — use a **countdown** and stop at 0.
- Serialize: **always write null markers**, or the shape is ambiguous.
- Build from Pre+In: build **left before right** (cursor order) and use a **hashmap** for O(n).
- Path Sum III: seed `prefix.put(0L, 1)` and **decrement the map on the way up** (backtrack).
- Tree Views (vertical): break column ties by **row, then value**.

## How to Loop This as a Podcast (your learning strategy, spoken)

1. **Pass 1 — the shape:** listen only for the repeating 6 steps: *parameters, base case, pre-order, go left/right, post-order, return.* Don't chase details; feel the rhythm.
2. **Pass 2 — the two pipes:** every pattern, ask silently *"is this DOWN (parameter) or UP (return)?"* before the narrator says it. You're training prediction.
3. **Pass 3 — answer vs return:** on Diameter, Max Path Sum, catch the split between the tracked answer and the returned value.
4. **Pass 4 — the anchors:** by now you should be able to say each pattern's 3-keyword mind-map anchor from memory before it plays.
5. **Pass 5 — the script:** recite the 60-second interview script along with the narrator. When you can, you're a pro.

## Your Mastery Checklist

- [ ] I can state the 6-step template with my eyes closed.
- [ ] I can instantly classify a problem as DOWN (parameter/pre-order), UP (return/post-order), or BFS (levels).
- [ ] I can explain why Diameter's answer and return values differ.
- [ ] I can write Same Tree, then turn it into Is Mirror by crossing the calls.
- [ ] I can write LCA and explain the "both sides non-null" moment.
- [ ] I can return an Info bundle for Largest BST and pick the right null identity.
- [ ] I can validate a BST by pushing a `(low, high)` window down (not a local check).
- [ ] I can write the one BFS skeleton and adapt it to right-side view, min depth, and zigzag.
- [ ] I can find the k-th smallest with an inorder countdown that stops early.
- [ ] I can serialize with null markers and deserialize from a token queue.
- [ ] I can rebuild a tree from preorder + inorder using the root-splits-array idea.
- [ ] I can count paths with Path Sum III's prefix map (and remember to backtrack it).
- [ ] I can assign coordinates for vertical/right-side views.
- [ ] I can compose Distance from LCA + depth without looking.
- [ ] I can explain how backtracking (Path Sum II) differs from post-order aggregation.
- [ ] I can recite the 60-second interview script cold.

## MAANG Coverage Map — every "favorite," which pattern owns it

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

> If a new problem isn't on this list, it's almost always a **remix** of one of these 18 patterns. Run the Master Decision Tree and you'll land on the right one.

---

**Prev →** `01_Tree_Algorithms.md`  ·  **Next →** `../14_Heap/01_Heap_Patterns.md`
