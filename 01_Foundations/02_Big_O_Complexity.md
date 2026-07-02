# Section 1.2 — Big O Notation & Complexity Analysis

---

## 1. What Problem Does This Solve?

How do you compare two solutions to the same problem?  
You need a **universal language** to measure algorithm performance **independent of machine speed**.

That language is **Big O Notation**.

---

## 2. Beginner-Friendly Intuition

Imagine you need to find your name in a list of 1,000,000 people.

- **Strategy A:** Check every name one by one → worst case: 1,000,000 checks.
- **Strategy B:** List is sorted; open to middle, eliminate half → ~20 checks.

Both are correct. But Strategy B is **vastly faster**. Big O lets us express this difference mathematically.

---

## 3. Real-World Analogy

You're searching for a word in a dictionary:
- **Linear Search:** Start from page 1, go word by word → O(n)
- **Binary Search:** Open to middle, go left or right → O(log n)
- **Direct Lookup (index):** Go directly to letter → O(1)

---

## 4. Core Concept: What is Big O?

Big O describes the **worst-case growth rate** of an algorithm's time or space as input size n grows.

**It ignores constants and lower-order terms because at large n, only the dominant term matters.**

```
f(n) = 3n² + 5n + 100  →  O(n²)
f(n) = 100n             →  O(n)
f(n) = 5                →  O(1)
```

---

## 5. Common Complexity Classes (Best → Worst)

```
O(1)         Constant      — Best possible
O(log n)     Logarithmic   — Very fast
O(n)         Linear        — Scales with input
O(n log n)   Linearithmic  — Merge sort, good sort
O(n²)        Quadratic     — Nested loops, slow
O(n³)        Cubic         — Triple nested, very slow
O(2ⁿ)        Exponential   — Doubles per input, terrible
O(n!)        Factorial     — All permutations, worst
```

### Visual Growth at n = 1,000:

| Complexity | Operations |
|-----------|-----------|
| O(1) | 1 |
| O(log n) | ~10 |
| O(n) | 1,000 |
| O(n log n) | ~10,000 |
| O(n²) | 1,000,000 |
| O(2ⁿ) | 10^301 (impossible) |

---

## 6. How to Analyze Loops

### Single Loop → O(n)
```java
for (int i = 0; i < n; i++) {
    // O(1) work
}
// Total: O(n)
```

### Nested Loop → O(n²)
```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < n; j++) {
        // O(1) work
    }
}
// Total: O(n × n) = O(n²)
```

### Loop Halving → O(log n)
```java
for (int i = 1; i < n; i *= 2) {
    // i doubles each time: 1, 2, 4, 8, 16...
    // How many doublings until i reaches n?
    // 2^k = n → k = log₂(n)
}
// Total: O(log n)
```

### Two Separate Loops → O(n + m)
```java
for (int i = 0; i < n; i++) { }  // O(n)
for (int j = 0; j < m; j++) { }  // O(m)
// Total: O(n + m)
// If m << n, simplifies to O(n)
```

### Loop with Constant Inner Work → O(n)
```java
for (int i = 0; i < n; i++) {
    for (int j = 0; j < 1000; j++) { }  // inner is constant!
}
// Total: O(1000n) = O(n)
```

---

## 7. How to Analyze Recursion

### Fibonacci (Naive) → O(2ⁿ)
```java
int fib(int n) {
    if (n <= 1) return n;
    return fib(n - 1) + fib(n - 2);
    // Each call spawns 2 more → 2⁰ + 2¹ + 2² + ... = O(2ⁿ)
}
```

Recursion tree for fib(5):
```
                fib(5)
              /        \
          fib(4)       fib(3)
          /    \       /    \
       fib(3) fib(2) fib(2) fib(1)
       ...
```
Each level doubles the calls → exponential.

### Binary Search Recursion → O(log n)
```java
int binarySearch(int[] arr, int lo, int hi, int target) {
    if (lo > hi) return -1;
    int mid = (lo + hi) / 2;
    if (arr[mid] == target) return mid;
    if (arr[mid] < target) return binarySearch(arr, mid + 1, hi, target);
    return binarySearch(arr, lo, mid - 1, target);
    // Each call halves the problem → O(log n)
}
```

**Recurrence:** T(n) = T(n/2) + O(1) → O(log n)

### Master Theorem (for understanding)
For T(n) = aT(n/b) + O(n^d):
- If d > log_b(a): O(n^d)
- If d = log_b(a): O(n^d log n)  
- If d < log_b(a): O(n^(log_b a))

Merge Sort: T(n) = 2T(n/2) + O(n) → a=2, b=2, d=1 → d = log₂2 = 1 → O(n log n)

---

## 8. Best, Average, Worst Case

**Example: Linear Search for target in array**

| Case | Scenario | Complexity |
|------|---------|-----------|
| Best | Target is at index 0 | O(1) |
| Average | Target is at middle | O(n/2) = O(n) |
| Worst | Target is at end / not found | O(n) |

**Big O typically refers to worst case** unless stated otherwise.

**Example: QuickSort**
| Case | Scenario | Complexity |
|------|---------|-----------|
| Best | Pivot always splits evenly | O(n log n) |
| Average | Random pivots | O(n log n) |
| Worst | Pivot is always min/max | O(n²) |

---

## 9. Space Complexity

Space complexity measures extra memory used (not counting input).

```java
// O(1) space — only a few variables
int sum = 0;
for (int x : arr) sum += x;

// O(n) space — new array proportional to input
int[] copy = new int[n];

// O(n) space — recursion stack
void dfs(int depth) {
    if (depth == 0) return;
    dfs(depth - 1);   // n recursive calls on stack
}

// O(log n) space — binary search recursion stack
```

### In-Place vs Extra Space

| Operation | Space |
|-----------|-------|
| Sorting in-place (QuickSort) | O(log n) recursion stack |
| Merge Sort | O(n) auxiliary array |
| HashMap storage | O(n) |
| BFS queue | O(n) |

---

## 10. Amortized Complexity

Some operations are occasionally expensive but cheap on average.

**Example: Dynamic Array (ArrayList)**
- Most `add()` calls → O(1)
- Occasional resize → O(n) (copy all elements)
- But resizes double capacity each time
- Amortized cost per add → O(1)

---

## 11. Common Complexity Traps in Interviews

### Trap 1: Not seeing hidden O(n) inside loop
```java
for (int i = 0; i < n; i++) {
    String s = "";
    for (int j = 0; j < i; j++) s += "a";  // String concat is O(j)!
}
// This is actually O(n²), not O(n)
```

### Trap 2: Forgetting recursion stack space
```java
// DFS on a tree of height h → O(h) stack space
// Worst case (skewed tree): O(n) space
```

### Trap 3: Confusing input size with value
```java
// Checking if n is prime by trial division
for (int i = 2; i <= n; i++) { ... }
// Complexity is O(n) in the VALUE of n
// But n here is a number, not an array size
// Often we write O(√n) for prime checking
```

---

## 12. Interview-Level Explanation

**Interviewer: "What is the complexity of your solution?"**

Good answer:
> "My outer loop runs n times. The inner loop runs at most k times where k is bounded by [reason]. So total is O(n × k). Given k ≤ 26 (alphabet), this simplifies to O(n). Space is O(k) = O(1) for the frequency array."

**Always explain the reasoning, not just state the number.**

---

## 13. Dry Run: Counting Operations

Problem: Find maximum in array [3, 7, 1, 9, 4]

```
i=0: max = 3         (1 comparison)
i=1: 7 > 3, max = 7  (1 comparison)
i=2: 1 < 7, skip     (1 comparison)
i=3: 9 > 7, max = 9  (1 comparison)
i=4: 4 < 9, skip     (1 comparison)
Total: 5 comparisons for n=5 → O(n)
```

---

## 13b. Amortized Analysis — Averaging Over a Sequence

Some operations are usually cheap but occasionally expensive. **Amortized analysis** proves the *average* cost per operation over a worst-case sequence is small — even though a single operation can spike. Three formal methods:

### 1. Aggregate method
Bound the total cost of `n` operations, then divide by `n`.
- **Dynamic array (ArrayList) push:** `n` appends trigger doubling at sizes 1, 2, 4, ..., n. Total copy work = `1 + 2 + 4 + ... + n < 2n`. Plus `n` cheap writes → total `< 3n` → **amortized O(1)** per push, even though the resize itself is O(n).

### 2. Accounting (banker's) method
Charge each cheap operation a little extra and store the surplus as "credit" to pay for future expensive ones.
- Charge **3 units** per push: 1 for the write, 2 saved as credit. When a resize copies `k` elements, the credit accumulated since the last resize exactly covers the `k` copies. Credit never goes negative → amortized cost ≤ 3 = **O(1)**.

### 3. Potential method
Define a potential function Φ (stored "energy" of the structure). Amortized cost = actual cost + ΔΦ. Choose Φ so expensive operations release potential.
- For the dynamic array, Φ = `2·size - capacity`. A normal push raises Φ by 2 (pays its way); a resize drops Φ sharply, absorbing the O(n) copy → amortized **O(1)**.

### Where amortized reasoning shows up
| Structure / operation | Single worst case | Amortized |
|-----------------------|-------------------|-----------|
| ArrayList append (with doubling) | O(n) on resize | O(1) |
| HashMap insert (with rehash) | O(n) on resize | O(1) |
| Two-stack queue (`push`/`pop`) | O(n) on transfer | O(1) |
| Monotonic stack/deque scan | O(n) for one step | O(1) per element |
| Union-Find with path compression | ~O(log n) | ~O(α(n)) ≈ O(1) |
| Fibonacci heap decrease-key | O(n) | O(1) |

> **Amortized ≠ average-case.** Amortized is a *worst-case guarantee over a sequence* (no probability involved); average-case depends on input distribution.

---

## 13c. Recurrence Relations & the Master Theorem

Divide-and-conquer recurrences take the form `T(n) = a·T(n/b) + f(n)` (`a` subproblems, each size `n/b`, plus `f(n)` combine work). Compare `f(n)` against `n^(log_b a)`:

| Case | Condition | Result |
|------|-----------|--------|
| 1 | `f(n) = O(n^(log_b a − ε))` (leaves dominate) | `T(n) = Θ(n^(log_b a))` |
| 2 | `f(n) = Θ(n^(log_b a))` (balanced) | `T(n) = Θ(n^(log_b a) · log n)` |
| 3 | `f(n) = Ω(n^(log_b a + ε))` (root dominates) | `T(n) = Θ(f(n))` |

**Worked examples:**
- **Merge sort:** `T(n)=2T(n/2)+O(n)` → `n^(log_2 2)=n^1=n`, matches `f(n)` → Case 2 → **Θ(n log n)**.
- **Binary search:** `T(n)=T(n/2)+O(1)` → `n^(log_2 1)=n^0=1` → Case 2 → **Θ(log n)**.
- **Karatsuba multiply:** `T(n)=3T(n/2)+O(n)` → `n^(log_2 3)≈n^1.585` dominates → Case 1 → **Θ(n^1.585)**.
- **Naive matrix mult (recursive):** `T(n)=8T(n/2)+O(n^2)` → `n^(log_2 8)=n^3` → Case 1 → **Θ(n^3)**.

For subtract-and-conquer (`T(n)=T(n-1)+f(n)`), just sum: linear recursion with O(1) work → O(n); with O(n) work → O(n^2). The **recursion tree** method (sum work per level × number of levels) handles cases the Master Theorem can't.

---

## 14. Practice Problems

**Easy:**
1. Count total operations in a given nested loop structure.
2. Determine Big O for: 3n² + 2n + 5.
3. Which grows faster: O(n log n) or O(n²)? At what n do they cross?

**Medium:**
1. Analyze the complexity of recursive Fibonacci with memoization.
2. What is the space complexity of BFS on a graph with V vertices and E edges?
3. Give a function with O(n log n) time and O(1) space.

**Hard:**
1. Analyze the time complexity of building a heap from n elements.
2. Explain amortized O(1) for a stack with `getMin()` in O(1).

---

## 15. Mini Quiz

1. What is O(n) + O(n²) simplified?
2. A loop runs n/2 times. What is its complexity?
3. What is the space complexity of a recursive function that makes n calls?
4. What complexity class does merge sort belong to?
5. If n = 10⁶, roughly how many operations does O(n log n) take?

---

**Next →** `03_Problem_Solving_Framework.md`
