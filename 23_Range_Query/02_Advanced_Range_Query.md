# Section 23b — Advanced Range Query Structures

> Companion to `01_Range_Query_Structures.md`. That file covered the basic segment tree (point update), Fenwick/BIT (point update + range sum), and sparse table (static RMQ). This file closes the PRO-level gaps: **lazy propagation** (range update + range query), **range-update Fenwick tricks**, **sqrt decomposition**, and **Mo's algorithm** for offline queries.

---

## 1. What Problem Does This Solve?

The basics let you query a range and update a single element. But interviews and contests demand more:

- **"Add v to every element in [l, r], then query a range sum"** → **segment tree with lazy propagation**.
- **"Range update + range sum with less code than a segment tree"** → **two-BIT trick**.
- **"Answer range queries with an expensive merge, offline"** → **Mo's algorithm**.
- **"Simple range structure I can code under pressure"** → **sqrt decomposition**.

---

## 2. Structure Selection Cheat Sheet

| Operation mix | Structure | Update | Query |
|---------------|-----------|--------|-------|
| Point update, range sum | Fenwick (BIT) | O(log n) | O(log n) |
| Range update, point query | Difference BIT | O(log n) | O(log n) |
| Range update, range sum | Two-BIT trick / lazy segtree | O(log n) | O(log n) |
| Range update, range min/max/assign | Lazy segment tree | O(log n) | O(log n) |
| Static range min/max (no updates) | Sparse table | build O(n log n) | O(1) |
| Offline queries, expensive merge | Mo's algorithm | — | O((n+q)√n) |
| Simple range, easy to code | Sqrt decomposition | O(1)/O(√n) | O(√n) |

---

## 3. Segment Tree with Lazy Propagation

### Intuition
A range update touching O(n) leaves would be slow. **Lazy propagation** stops at the O(log n) nodes that fully cover the update range, stamps a "pending" value there, and only pushes it down to children when a later query actually needs those children. Work is deferred until required.

### Range-add, range-sum
```java
class LazySegTree {
    long[] tree, lazy;
    int n;

    LazySegTree(int[] a) {
        n = a.length;
        tree = new long[4 * n];
        lazy = new long[4 * n];
        build(a, 1, 0, n - 1);
    }
    void build(int[] a, int node, int lo, int hi) {
        if (lo == hi) { tree[node] = a[lo]; return; }
        int mid = (lo + hi) >>> 1;
        build(a, 2 * node, lo, mid);
        build(a, 2 * node + 1, mid + 1, hi);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }
    // Push pending add from a node to its two children.
    private void push(int node, int lo, int hi) {
        if (lazy[node] != 0) {
            int mid = (lo + hi) >>> 1;
            apply(2 * node, lo, mid, lazy[node]);
            apply(2 * node + 1, mid + 1, hi, lazy[node]);
            lazy[node] = 0;
        }
    }
    private void apply(int node, int lo, int hi, long add) {
        tree[node] += add * (hi - lo + 1);   // sum increases by add * count
        lazy[node] += add;                   // remember for children
    }
    void update(int l, int r, long add) { update(1, 0, n - 1, l, r, add); }
    private void update(int node, int lo, int hi, int l, int r, long add) {
        if (r < lo || hi < l) return;                    // no overlap
        if (l <= lo && hi <= r) { apply(node, lo, hi, add); return; } // full cover
        push(node, lo, hi);                              // partial: push down first
        int mid = (lo + hi) >>> 1;
        update(2 * node, lo, mid, l, r, add);
        update(2 * node + 1, mid + 1, hi, l, r, add);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }
    long query(int l, int r) { return query(1, 0, n - 1, l, r); }
    private long query(int node, int lo, int hi, int l, int r) {
        if (r < lo || hi < l) return 0;
        if (l <= lo && hi <= r) return tree[node];
        push(node, lo, hi);
        int mid = (lo + hi) >>> 1;
        return query(2 * node, lo, mid, l, r) + query(2 * node + 1, mid + 1, hi, l, r);
    }
}
```

### Range-assign, range-min variant
Change `apply` to overwrite (`tree[node] = val; lazy[node] = val;`) and use a "no pending" sentinel (e.g., `Long.MIN_VALUE`) instead of `0`. The push logic is otherwise identical. This is how you handle "set all elements in [l, r] to v, query the min."

- Time **O(log n)** per update/query, space O(n).
- **The golden rule:** always `push` before recursing into children on a *partial* overlap; never push on a *full* cover.

**Canonical problems:** LeetCode 715 *Range Module*, 699 *Falling Squares*, 218 *The Skyline Problem*, 2213 *Longest Substring of One Repeating Character*.

---

## 4. Range Update + Range Sum with Two BITs

### Intuition
A single Fenwick tree gives point-update/range-sum. To support **range-update + range-sum** without a full segment tree, maintain two BITs so that the prefix sum becomes a closed-form expression. Less code, same O(log n).

```java
class RangeBIT {
    long[] b1, b2;
    int n;
    RangeBIT(int n) { this.n = n; b1 = new long[n + 1]; b2 = new long[n + 1]; }

    private void add(long[] b, int i, long v) { for (; i <= n; i += i & -i) b[i] += v; }
    private long sum(long[] b, int i) { long s = 0; for (; i > 0; i -= i & -i) s += b[i]; return s; }

    // Add v to all indices in [l, r]  (1-indexed).
    void rangeAdd(int l, int r, long v) {
        add(b1, l, v);          add(b1, r + 1, -v);
        add(b2, l, v * (l - 1)); add(b2, r + 1, -v * r);
    }
    private long prefix(int i) { return sum(b1, i) * i - sum(b2, i); }
    long rangeSum(int l, int r) { return prefix(r) - prefix(l - 1); }
}
```
This is the lightest structure for range-add / range-sum — memorize the four `add` calls.

---

## 5. Sqrt Decomposition — Simple and Robust

### Intuition
Split the array into √n blocks. Keep a per-block aggregate (sum/min). A range query stitches together whole blocks (O(√n) of them) plus partial edges. Easy to code, forgiving to modify, good when a segment tree feels heavy.

```java
class SqrtDecomp {
    int[] a;
    long[] blockSum;
    int blockSize, n;
    SqrtDecomp(int[] arr) {
        a = arr.clone();
        n = a.length;
        blockSize = (int) Math.sqrt(n) + 1;
        blockSum = new long[n / blockSize + 1];
        for (int i = 0; i < n; i++) blockSum[i / blockSize] += a[i];
    }
    void update(int i, int val) {           // point assign
        blockSum[i / blockSize] += val - a[i];
        a[i] = val;
    }
    long query(int l, int r) {              // inclusive range sum
        long res = 0;
        while (l <= r && l % blockSize != 0) res += a[l++];   // left partial
        while (l + blockSize - 1 <= r) { res += blockSum[l / blockSize]; l += blockSize; }
        while (l <= r) res += a[l++];                          // right partial
        return res;
    }
}
```
Query **O(√n)**, update O(1). Extends naturally to lazy block updates (add a `blockLazy[]`).

---

## 6. Mo's Algorithm — Offline Range Queries

### Intuition
When queries can be answered **offline** and the answer for `[l, r]` can be cheaply updated to `[l±1, r±1]` by adding/removing one element, sort the queries cleverly and slide two pointers. Sorting by `(block of l, then r)` bounds total pointer movement to **O((n+q)√n)**.

### Code — example: number of distinct values in each range
```java
public int[] mosAlgorithm(int[] a, int[][] queries) {
    int n = a.length, q = queries.length;
    int block = (int) Math.sqrt(n) + 1;
    Integer[] idx = new Integer[q];
    for (int i = 0; i < q; i++) idx[i] = i;
    Arrays.sort(idx, (x, y) -> {
        int bx = queries[x][0] / block, by = queries[y][0] / block;
        if (bx != by) return bx - by;
        // odd/even sort of r reduces pointer movement further
        return (bx & 1) == 0 ? queries[x][1] - queries[y][1]
                             : queries[y][1] - queries[x][1];
    });

    int[] cnt = new int[1_000_001];   // value frequency; size to value range
    int[] ans = new int[q];
    int curL = 0, curR = -1, distinct = 0;
    for (int qi : idx) {
        int L = queries[qi][0], R = queries[qi][1];
        while (curR < R) { if (++cnt[a[++curR]] == 1) distinct++; }
        while (curL > L) { if (++cnt[a[--curL]] == 1) distinct++; }
        while (curR > R) { if (--cnt[a[curR--]] == 0) distinct--; }
        while (curL < L) { if (--cnt[a[curL++]] == 0) distinct--; }
        ans[qi] = distinct;
    }
    return ans;
}
```

- Only works **offline** (all queries known up front, no interleaved updates).
- The add/remove of one element must be O(1) (or O(log) for a Mo's-with-updates variant).
- **Applications:** distinct counts, range mode, range frequency, XOR/sum with a twist — problems where no clean segment-tree merge exists.

---

## 7. Which Structure? Decision Flow

```
Need range queries?
├── Static array, min/max only ............ Sparse table (O(1) query)
├── Point update, range sum ................ Fenwick BIT
├── Range update, point query .............. Difference BIT
├── Range update, range sum ................ Two-BIT trick (light) or lazy segtree
├── Range update, range min/max/assign ..... Lazy segment tree
├── Offline, expensive/unmergeable merge ... Mo's algorithm
└── Want simplest code that works .......... Sqrt decomposition
```

---

## 8. Failure Modes & Interview Traps

| Trap | Fix |
|------|-----|
| Forgetting to `push` lazy before recursing on partial overlap | Query/update returns stale values. |
| Pushing lazy on a full-cover node | Wasted work; only push on partial overlap. |
| Lazy "assign" using 0 as the sentinel | 0 may be a valid assignment; use a distinct sentinel. |
| Overflow in range-sum with big updates | Use `long`, and `add * (hi - lo + 1)` for the count. |
| Using Mo's with online updates | Mo's is offline-only; use a BIT/segment tree instead. |
| Mo's without odd/even r-sorting | Still correct but ~2x slower. |
| Off-by-one in BIT (0- vs 1-indexed) | BIT is 1-indexed; shift inputs by 1. |

---

## 9. 60-Second Explanation Template

> "I need [point/range] updates and [point/range] queries with a [sum/min/max/assign] merge. That maps to [Fenwick / lazy segtree / two-BIT / sparse table]. If queries are offline with an expensive merge, I'd use Mo's at O((n+q)√n). The key invariant is [lazy pushed before descending / prefix closed-form / block aggregates]. Complexity is O(log n) per op."

---

## Practice Problems

**Medium:**
1. Range Sum Query - Mutable (Fenwick / segment tree).
2. Count of Smaller Numbers After Self (BIT on ranks).
3. Corporate Flight Bookings (difference array / range BIT).

**Hard:**
1. Range Module (lazy assign segment tree).
2. Falling Squares (segment tree with coordinate compression).
3. The Skyline Problem (segment tree / sweep + heap).
4. Longest Substring of One Repeating Character (lazy segment tree with merges).
5. Distinct values in ranges, offline (Mo's algorithm).

---

**Next →** `../24_Math/01_Math_Algorithms.md`
