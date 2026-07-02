# Section 18c — Advanced Dynamic Programming

> Companion to `01_DP_Patterns.md` and `02_DP_Pattern_Masterclass.md`. This file closes the PRO-level DP gaps: bitmask DP, digit DP, DP on trees (with rerooting), interval DP beyond the basics, and solution reconstruction / state-space design.

---

## 1. What Problem Does This Solve?

The first two DP files taught 1D/2D DP, knapsack, LIS, edit distance, and the five pattern families. Interviews at Google, Meta, and quant firms go further:

- **"Assign N tasks to N workers" / "visit all cities"** → the state is a **set** → **bitmask DP**.
- **"Count numbers in [L, R] with property P on their digits"** → **digit DP**.
- **"Optimize something over a tree where each node's answer depends on children"** → **tree DP**, and if the answer is needed *for every root*, **rerooting**.
- **"Merge/partition an array optimally"** → **interval DP**.
- **"Return the actual optimal solution, not just its cost"** → **DP reconstruction**.

---

## 2. Bitmask DP — State Is a Subset

### Intuition
When N ≤ ~20, a subset of items fits in an `int` (bit `i` set = item `i` chosen). The DP table is indexed by this bitmask. Transitions add/remove one bit. Total states 2^N, each transition O(N) → O(2^N · N).

### Pattern A — Assignment / Partition (Bitmask over one dimension)
**Problem:** N people, N tasks, `cost[i][j]` = cost of person `i` doing task `j`. Minimize total cost.
`dp[mask]` = min cost to assign the first `popcount(mask)` people to exactly the tasks in `mask`.

```java
public int minAssignmentCost(int[][] cost) {
    int n = cost.length;
    int FULL = (1 << n) - 1;
    int[] dp = new int[1 << n];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    for (int mask = 0; mask < (1 << n); mask++) {
        if (dp[mask] == Integer.MAX_VALUE) continue;
        int person = Integer.bitCount(mask);       // next person to assign
        if (person == n) continue;
        for (int task = 0; task < n; task++) {
            if ((mask & (1 << task)) != 0) continue; // task already taken
            int next = mask | (1 << task);
            dp[next] = Math.min(dp[next], dp[mask] + cost[person][task]);
        }
    }
    return dp[FULL];
}
```

### Pattern B — Travelling Salesman (Bitmask + last-node dimension)
**Problem:** Shortest route visiting all cities once, returning to start.
`dp[mask][i]` = min cost of a path that visits exactly the cities in `mask` and ends at city `i`.

```java
public int tsp(int[][] dist) {
    int n = dist.length, FULL = (1 << n) - 1;
    int[][] dp = new int[1 << n][n];
    for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE / 2);
    dp[1][0] = 0;                                   // start at city 0
    for (int mask = 1; mask <= FULL; mask++) {
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0 || dp[mask][i] == Integer.MAX_VALUE / 2) continue;
            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) != 0) continue;
                int next = mask | (1 << j);
                dp[next][j] = Math.min(dp[next][j], dp[mask][i] + dist[i][j]);
            }
        }
    }
    int best = Integer.MAX_VALUE;
    for (int i = 1; i < n; i++)
        best = Math.min(best, dp[FULL][i] + dist[i][0]); // close the loop
    return best;
}
```
Time **O(2^N · N²)**, space O(2^N · N). Practical up to N ≈ 18–20.

**Canonical problems:** LeetCode 943 *Find the Shortest Superstring*, 847 *Shortest Path Visiting All Nodes*, 1349 *Maximum Students Taking Exam* (broken-profile variant), 698 *Partition to K Equal Sum Subsets*.

### Subset enumeration trick (iterate all submasks of a mask) — O(3^N) total
```java
for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
    // sub ranges over every non-empty subset of mask
}
```
Used in set-partition DP (e.g., LeetCode 1125 *Smallest Sufficient Team*, 1723 *Find Minimum Time to Finish All Jobs*).

---

## 3. Digit DP — Counting Numbers by Their Digits

### Intuition
To count numbers in `[0, N]` satisfying a digit property, build the number digit-by-digit from the most significant position. Carry a **`tight`** flag (are we still bounded by `N`'s prefix?) and any property state (running sum, remainder, last digit, etc.). Count in `[L, R]` = `f(R) - f(L-1)`.

### Template
```java
// Count integers in [0, N] whose digit sum is divisible by K (example property).
String num;
int K;
Integer[][][] memo;   // [pos][state][tight]

int countUpTo(int N, int k) {
    num = Integer.toString(N);
    K = k;
    memo = new Integer[num.length()][K][2];
    return dp(0, 0, 1);   // start: position 0, state 0, tight = true
}

int dp(int pos, int rem, int tight) {
    if (pos == num.length()) return rem == 0 ? 1 : 0;    // base: whole number built
    if (memo[pos][rem][tight] != null) return memo[pos][rem][tight];
    int limit = (tight == 1) ? num.charAt(pos) - '0' : 9; // cap on this digit
    int total = 0;
    for (int d = 0; d <= limit; d++) {
        int newTight = (tight == 1 && d == limit) ? 1 : 0;
        total += dp(pos + 1, (rem + d) % K, newTight);
    }
    return memo[pos][rem][tight] = total;
}
```

- The `tight` dimension is what makes digit DP work — when `tight == 0` the answer is independent of `N`, so it caches across many prefixes.
- Add a `started` (leading-zero) flag when the property cares about the *first significant digit* or number length.
- Time **O(digits · states · 10)**.

**Canonical problems:** LeetCode 233 *Number of Digit One*, 902 *Numbers At Most N Given Digit Set*, 1012 *Numbers With Repeated Digits*, 600 *Non-negative Integers without Consecutive Ones*.

---

## 4. DP on Trees

### Intuition
A tree has no cycles, so each node's answer can be computed purely from its children's answers via one post-order DFS. Root the tree, recurse, combine.

### Pattern A — Subtree aggregation (e.g., House Robber III, LeetCode 337)
Return two values per node: best if we **take** it vs **skip** it.
```java
int[] rob(TreeNode node) {                 // returns {skip, take}
    if (node == null) return new int[]{0, 0};
    int[] l = rob(node.left);
    int[] r = rob(node.right);
    int take = node.val + l[0] + r[0];      // can't take children
    int skip = Math.max(l[0], l[1]) + Math.max(r[0], r[1]);
    return new int[]{skip, take};
}
```

### Pattern B — Tree diameter as tree DP (LeetCode 543)
```java
int diameter = 0;
int depth(TreeNode node) {
    if (node == null) return 0;
    int l = depth(node.left), r = depth(node.right);
    diameter = Math.max(diameter, l + r);   // longest path through this node
    return 1 + Math.max(l, r);
}
```

### Pattern C — Rerooting (answer for EVERY node as root)
When you need `f(v)` = some aggregate over the whole tree measured from each node `v` (e.g., sum of distances to all other nodes), a naive re-DFS per node is O(N²). **Rerooting** does it in O(N) with two passes:

1. **Down pass:** root at 0, compute subtree size `cnt[v]` and `res[0]` = sum of distances from root.
2. **Up pass:** move the root along each edge `u → v`. Nodes in `v`'s subtree get 1 closer; the rest get 1 farther:
   `res[v] = res[u] - cnt[v] + (N - cnt[v])`.

```java
// LeetCode 834: Sum of Distances in Tree.
int n;
List<Integer>[] adj;
int[] cnt, res;

public int[] sumOfDistancesInTree(int N, int[][] edges) {
    n = N;
    adj = new List[n];
    for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
    for (int[] e : edges) { adj[e[0]].add(e[1]); adj[e[1]].add(e[0]); }
    cnt = new int[n];
    res = new int[n];
    dfsDown(0, -1);
    dfsUp(0, -1);
    return res;
}
void dfsDown(int u, int p) {
    cnt[u] = 1;
    for (int v : adj[u]) if (v != p) {
        dfsDown(v, u);
        cnt[u] += cnt[v];
        res[u] += res[v] + cnt[v];      // each subtree node is 1 edge deeper
    }
}
void dfsUp(int u, int p) {
    for (int v : adj[u]) if (v != p) {
        res[v] = res[u] - cnt[v] + (n - cnt[v]);  // reroot formula
        dfsUp(v, u);
    }
}
```
Time **O(N)**. This is the signature PRO tree-DP technique.

---

## 5. Interval DP — Beyond Burst Balloons

### Intuition
`dp[i][j]` = best answer for subarray `[i..j]`. Compute by length, splitting on the **last operation** (a split point or a "final survivor" `k`). Order: increasing interval length.

### Matrix Chain Multiplication
Minimize scalar multiplications to multiply matrices `A1..An` with dimensions `p[i-1] x p[i]`.
```java
public int matrixChain(int[] p) {          // p.length = n+1
    int n = p.length - 1;
    int[][] dp = new int[n + 1][n + 1];
    for (int len = 2; len <= n; len++) {
        for (int i = 1; i + len - 1 <= n; i++) {
            int j = i + len - 1;
            dp[i][j] = Integer.MAX_VALUE;
            for (int k = i; k < j; k++) {   // split A[i..k] | A[k+1..j]
                int cost = dp[i][k] + dp[k + 1][j] + p[i - 1] * p[k] * p[j];
                dp[i][j] = Math.min(dp[i][j], cost);
            }
        }
    }
    return dp[1][n];
}
```

### Merge Stones / partition family
Same skeleton with a different cost function. **Canonical problems:** LeetCode 1000 *Minimum Cost to Merge Stones*, 312 *Burst Balloons*, 1547 *Minimum Cost to Cut a Stick*, 375 *Guess Number Higher or Lower II*, 546 *Remove Boxes* (3D interval DP).

Time is typically **O(N³)**.

---

## 6. DP Reconstruction — Recover the Actual Answer

Most DP returns a *value*. To return the *choice sequence*, either store a `parent`/`choice` array during the forward pass, or backtrack through the finished table.

### Example: Longest Increasing Subsequence — return the actual subsequence
```java
public List<Integer> lisSequence(int[] a) {
    int n = a.length;
    int[] dp = new int[n], parent = new int[n];
    Arrays.fill(dp, 1);
    Arrays.fill(parent, -1);
    int best = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (a[j] < a[i] && dp[j] + 1 > dp[i]) {
                dp[i] = dp[j] + 1;
                parent[i] = j;                  // remember predecessor
            }
        }
        if (dp[i] > dp[best]) best = i;
    }
    LinkedList<Integer> seq = new LinkedList<>();
    for (int i = best; i != -1; i = parent[i]) seq.addFirst(a[i]); // walk back
    return seq;
}
```

**General rule:** whenever a transition picks a `min`/`max`, also record *which* predecessor won. Reconstruction then walks the pointers backward.

---

## 7. State-Space Design — The Real Skill

The hardest part of DP is not the code — it's discovering the **minimal state** that makes future choices independent of the past ("optimal substructure" + "no hidden dependency").

Checklist to design state:
1. **What decision am I making at each step?** (take/skip an item, place a digit, pick a split.)
2. **What must I remember so the remaining problem is self-contained?** (index, remaining capacity, last choice, running remainder, subset used.)
3. **Can two different histories with the same remembered value be treated identically?** If yes → that's a valid state; if not → add a dimension.
4. **Can I drop a dimension?** (rolling array when `dp[i]` only depends on `dp[i-1]`.)

| Symptom | Likely missing state |
|---------|----------------------|
| Answer depends on what you picked last | add "last value/color/index" |
| Answer depends on a running total mod m | add "remainder" |
| Choices are over a small set of items | add "bitmask" |
| Bounded by a number's digits | add "tight" + "position" |
| Cost differs by parity/step count | add "count mod k" |

---

## 8. Complexity & Space Optimization

| Technique | When | Effect |
|-----------|------|--------|
| Rolling array (`dp[2][...]`) | `dp[i]` depends only on `dp[i-1]` | space O(N)→O(1) per row |
| Reverse-iteration 0/1 knapsack | in-place 1D knapsack | avoids reusing an item |
| Bitset DP | boolean reachability (subset sum) | ~64x speedup with `long[]` bitset |
| Monotonic-queue / CHT / D&C optimization | 1D DP with special cost structure | O(N²)→O(N log N) or O(N) |

---

## 9. Failure Modes & Interview Traps

| Trap | Fix |
|------|-----|
| Bitmask DP with N > 22 | 2^N explodes; look for a different state. |
| Digit DP forgetting the `tight` flag | Overcounts numbers above N. |
| Digit DP ignoring leading zeros when length matters | Add a `started` flag. |
| Rerooting formula off by subtree side | `res[v] = res[u] - cnt[v] + (n - cnt[v])`. |
| Interval DP wrong loop order | Must iterate by increasing length. |
| 0/1 knapsack 1D iterating capacity ascending | Reuses items; iterate capacity **descending**. |
| Reconstruction without parent pointers | Store the winning choice during the forward pass. |

---

## 10. 60-Second Explanation Template

> "The state that makes the future independent of the past is [describe it]; I encode it as [index / capacity / bitmask / (position, tight) / (node)]. The transition is [take/skip / place digit / split at k / combine children]. There are [X] states and each costs [Y], so it's O(X·Y). I'll reconstruct the answer by [parent pointers / backtracking the table]."

---

## Practice Problems

**Medium:**
1. Partition to K Equal Sum Subsets (bitmask).
2. House Robber III (tree DP).
3. Number of Digit One (digit DP).
4. Minimum Cost to Cut a Stick (interval DP).

**Hard:**
1. Shortest Path Visiting All Nodes (bitmask + BFS).
2. Find the Shortest Superstring (bitmask TSP-style).
3. Sum of Distances in Tree (rerooting).
4. Minimum Cost to Merge Stones (interval DP).
5. Numbers With Repeated Digits (digit DP).
6. Maximum Students Taking Exam (broken-profile bitmask DP).

---

**Next →** `../19_Intervals/01_Intervals.md`
