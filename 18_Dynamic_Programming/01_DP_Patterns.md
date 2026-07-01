# Section 18 — Dynamic Programming

---

> Start here for the compact reference. For a slower beginner-to-pro path that begins with recursion and then maps into the `DPPatterns.pdf` families, read `02_DP_Pattern_Masterclass.md`.

---

## 1. What Problem Does This Solve?

Dynamic Programming solves problems with two properties:
1. **Optimal Substructure:** The optimal solution to the problem contains optimal solutions to subproblems.
2. **Overlapping Subproblems:** The same subproblems are solved multiple times in a naive recursive approach.

DP avoids redundant computation by **storing (memoizing) subproblem results** and building up from base cases. It converts exponential recursive solutions to polynomial ones.

---

## 2. Beginner-Friendly Intuition

Think of climbing stairs where you can take 1 or 2 steps. To find how many ways to reach step n, you need: ways(n-1) + ways(n-2). Without DP, you recompute ways(n-3) for both. With DP, you compute it once and store it.

**The key question:** "Have I already solved this subproblem? If yes, reuse the answer. If no, solve it and save it."

---

## 3. Real-World Analogy

**GPS navigation (routing):** To find the shortest path from A to D passing through B and C, you compute the shortest path A→B once, then use it when computing A→B→C and when computing A→B→D. Without memoization, you recompute A→B multiple times.

**Financial portfolio:** To find the best investment combination using k investments with total budget B, you check: "What's the best I can do with k-1 investments and various budget amounts?" Those sub-answers are stored and reused.

---

## 4. Core Concept

### Two DP Approaches

| Approach | Direction | When to Use |
|---------|-----------|-------------|
| **Top-Down (Memoization)** | Start with full problem, recurse down | Natural recursion with pruning |
| **Bottom-Up (Tabulation)** | Start with base cases, build up | When recursion stack is too deep |

### The DP Framework (5 Steps)

```
1. Define state: dp[i] = what does it mean?
2. Base case: dp[0], dp[1] = known values
3. Transition: dp[i] = f(dp[i-1], dp[i-2], ...) — how does state i depend on earlier states?
4. Final answer: Which dp[?] is the answer?
5. Optimize: Can you reduce space by only keeping last k states?
```

---

## 5. Pattern Recognition Signals

Use DP when:
```
"Minimum/maximum number of ways"
"How many ways to..."
"Can you achieve..." (true/false over all possibilities)
"Optimal partitioning"
"String edit distance"
"Longest common subsequence"
"Knapsack" / "subset sum"
"Coin change" / "minimum coins"
"Buying and selling stock with constraints"
"Palindromic substrings"
"Word break"
```

**Red flag for needing DP:** Backtracking TLE on similar problem = overlapping subproblems = DP needed.

---

## 6. Step-by-Step Algorithm

### 1D DP Template
```
dp = new int[n+1]
dp[0] = base_case
dp[1] = base_case

For i from 2 to n:
    dp[i] = transition using dp[i-1], dp[i-2], etc.

Return dp[n]
```

### 2D DP Template (Subsequence/Grid)
```
dp = new int[m+1][n+1]
// Fill base cases (row 0 and col 0)

For i from 1 to m:
    For j from 1 to n:
        if match condition:
            dp[i][j] = dp[i-1][j-1] + 1  (or similar)
        else:
            dp[i][j] = max/min of neighbors

Return dp[m][n]
```

---

## 7. Dry Run with Example

### Example 1: Climbing Stairs

**Problem:** How many ways to reach step n (1 or 2 steps at a time)?

**Input:** `n = 5`

```
State: dp[i] = number of ways to reach step i
Base: dp[0]=1, dp[1]=1
Transition: dp[i] = dp[i-1] + dp[i-2]

dp[0]=1
dp[1]=1
dp[2]=dp[1]+dp[0]=1+1=2
dp[3]=dp[2]+dp[1]=2+1=3
dp[4]=dp[3]+dp[2]=3+2=5
dp[5]=dp[4]+dp[3]=5+3=8

Answer: 8 ways ✓
```

### Example 2: Longest Common Subsequence (LCS)

**Input:** `s1 = "abcde"`, `s2 = "ace"`

```
    ""  a  c  e
""   0  0  0  0
a    0  1  1  1
b    0  1  1  1
c    0  1  2  2
d    0  1  2  2
e    0  1  2  3

Transition: if s1[i-1]==s2[j-1]: dp[i][j]=dp[i-1][j-1]+1
            else: dp[i][j]=max(dp[i-1][j], dp[i][j-1])

Answer: dp[5][3] = 3 (LCS = "ace") ✓
```

### Example 3: 0/1 Knapsack

**Input:** weights=[1,3,4,5], values=[1,4,5,7], capacity=7

```
    cap: 0  1  2  3  4  5  6  7
item0    0  0  0  0  0  0  0  0
item1(w=1,v=1): 0  1  1  1  1  1  1  1
item2(w=3,v=4): 0  1  1  4  5  5  5  5
item3(w=4,v=5): 0  1  1  4  5  6  6  9
item4(w=5,v=7): 0  1  1  4  5  7  8  9

Answer: dp[4][7] = 9 (items with w=3,v=4 and w=4,v=5 → total w=7, v=9) ✓
```

---

## 8. Code Implementation

### Fibonacci / Climbing Stairs

```java
int climbStairs(int n) {
    if (n <= 1) return 1;
    int prev2 = 1, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1; // O(1) space optimization
}
```

### Coin Change (Minimum Coins)

```java
int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1); // "infinity"
    dp[0] = 0;
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (coin <= i)
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
        }
    }
    return dp[amount] > amount ? -1 : dp[amount];
}
```

### Longest Common Subsequence

```java
int longestCommonSubsequence(String s1, String s2) {
    int m = s1.length(), n = s2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (s1.charAt(i-1) == s2.charAt(j-1))
                dp[i][j] = dp[i-1][j-1] + 1;
            else
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
        }
    }
    return dp[m][n];
}
```

### 0/1 Knapsack

```java
int knapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[] dp = new int[capacity + 1]; // space-optimized 1D
    for (int i = 0; i < n; i++) {
        for (int w = capacity; w >= weights[i]; w--) { // reverse to avoid reuse
            dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
        }
    }
    return dp[capacity];
}
```

### Longest Increasing Subsequence (O(n log n))

```java
int lengthOfLIS(int[] nums) {
    List<Integer> tails = new ArrayList<>();
    for (int num : nums) {
        int pos = Collections.binarySearch(tails, num);
        if (pos < 0) pos = -(pos + 1); // insertion point
        if (pos == tails.size()) tails.add(num);
        else tails.set(pos, num); // replace with smaller tail
    }
    return tails.size();
}
```

### Word Break

```java
boolean wordBreak(String s, List<String> wordDict) {
    Set<String> words = new HashSet<>(wordDict);
    int n = s.length();
    boolean[] dp = new boolean[n + 1];
    dp[0] = true; // empty string is always breakable
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j < i; j++) {
            if (dp[j] && words.contains(s.substring(j, i))) {
                dp[i] = true;
                break;
            }
        }
    }
    return dp[n];
}
```

### Edit Distance

```java
int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    for (int i = 0; i <= m; i++) dp[i][0] = i; // delete all from word1
    for (int j = 0; j <= n; j++) dp[0][j] = j; // insert all from word2
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i-1) == word2.charAt(j-1))
                dp[i][j] = dp[i-1][j-1]; // no operation needed
            else
                dp[i][j] = 1 + Math.min(dp[i-1][j-1],   // replace
                                Math.min(dp[i-1][j],      // delete
                                         dp[i][j-1]));    // insert
        }
    }
    return dp[m][n];
}
```

### Best Time to Buy and Sell Stock with Cooldown

```java
int maxProfit(int[] prices) {
    int held = Integer.MIN_VALUE, sold = 0, rest = 0;
    for (int price : prices) {
        int prevSold = sold;
        sold = held + price;             // sell today
        held = Math.max(held, rest - price); // buy today or keep holding
        rest = Math.max(rest, prevSold); // rest or continue resting
    }
    return Math.max(sold, rest);
}
```

---

## 9. Time Complexity

| Problem | Complexity | Notes |
|---------|-----------|-------|
| Fibonacci / Climbing Stairs | O(n) | Single loop |
| Coin Change | O(n × amount) | Nested loops |
| LCS | O(m × n) | 2D table |
| 0/1 Knapsack | O(n × W) | n items, W capacity |
| LIS (simple DP) | O(n²) | Compare all pairs |
| LIS (patience sort) | O(n log n) | Binary search |
| Edit Distance | O(m × n) | 2D table |
| Word Break | O(n² × L) | L = max word length |

---

## 10. Space Complexity

| Problem | Full DP | Optimized |
|---------|---------|----------|
| Fibonacci | O(n) array | O(1) two variables |
| Coin Change | O(amount) | Already 1D |
| LCS | O(m×n) | O(min(m,n)) two rows |
| 0/1 Knapsack | O(n×W) | O(W) reverse iteration |
| Edit Distance | O(m×n) | O(min(m,n)) |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| amount = 0 in Coin Change | dp[0]=0, return 0 |
| Empty strings in LCS | dp[i][0]=0, dp[0][j]=0 |
| Empty word list in Word Break | return false for non-empty s |
| Single element in LIS | return 1 |
| Coins larger than amount | Skip those coins in the loop |
| Negative prices in stock | Constraints usually prevent this |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Wrong initialization for "minimum" DP
int[] dp = new int[n + 1]; // WRONG: dp[0..n] = 0, but 0 is a valid minimum too
Arrays.fill(dp, Integer.MAX_VALUE); // WRONG: overflow when adding 1 to MAX_VALUE
Arrays.fill(dp, amount + 1);        // CORRECT: sentinel value > any valid answer

// MISTAKE 2: Traversal direction in 0/1 Knapsack (1D space optimization)
for (int w = 0; w <= capacity; w++) // WRONG: allows item reuse (unbounded knapsack)
for (int w = capacity; w >= weight; w--) // CORRECT: reverse prevents reuse

// MISTAKE 3: Off-by-one in LCS indexing
if (s1.charAt(i) == s2.charAt(j)) // WRONG: should be i-1, j-1 for 1-indexed dp
if (s1.charAt(i-1) == s2.charAt(j-1)) // CORRECT

// MISTAKE 4: Not handling the base case for top-down memoization
int memo(int n) {
    // MISSING: if (n <= 1) return n;
    if (dp[n] != -1) return dp[n];
    return dp[n] = memo(n-1) + memo(n-2);
}

// MISTAKE 5: Using greedy for 0/1 Knapsack
// Greedy picks by value/weight ratio — doesn't guarantee optimal for integer knapsack
// ALWAYS use DP for 0/1 Knapsack
```

---

## 13. Interview-Level Explanation

**Q: "What is the difference between memoization (top-down) and tabulation (bottom-up) DP?"**

> "Both avoid recomputing subproblems. Top-down (memoization) starts with the original problem, recurses into subproblems, and caches results. It's intuitive and only computes what's needed. Bottom-up (tabulation) fills a table starting from base cases, building up to the original problem. It avoids recursion overhead and is usually faster in practice. For very large inputs, top-down may hit stack overflow — bottom-up is safer."

**Q: "When does DP fail, and what do you use instead?"**

> "DP fails when: 1) The state space is too large to memoize efficiently. 2) The problem doesn't have optimal substructure (e.g., 'find all paths in a graph with negative cycles'). In those cases, use greedy (if greedy-choice property holds), BFS (for shortest path on unweighted graph), or heuristics."

---

## 14. Real-World Use Cases

| Application | DP Usage |
|------------|---------|
| **Spell checkers** | Edit distance (Levenshtein distance) |
| **Bioinformatics** | DNA sequence alignment (LCS/edit distance) |
| **Compiler optimization** | Register allocation, code generation |
| **Finance** | Optimal portfolio selection |
| **NLP** | Viterbi algorithm for HMM (sequence labeling) |
| **Game AI** | Minimax + memoization for game trees |
| **Text justification** | Optimal paragraph wrapping |

---

## 15. DP Pattern Categories

| Category | Core Transition | Example Problems |
|----------|----------------|-----------------|
| **Linear DP** | dp[i] = f(dp[i-1], dp[i-2]) | Climbing Stairs, House Robber |
| **Subsequence DP** | dp[i][j] = f(dp[i-1][j], dp[i][j-1]) | LCS, Edit Distance |
| **Knapsack DP** | dp[w] = max(dp[w], dp[w-weight]+value) | 0/1 Knapsack, Coin Change |
| **Interval DP** | dp[i][j] = f(dp[i][k] + dp[k+1][j]) | Matrix Chain, Burst Balloons |
| **State Machine DP** | States: held/sold/rest | Stock problems |
| **Bitmask DP** | dp[mask] | TSP, Assignment |

---

## 16. Practice Problems

### Easy — Foundation
1. **Climbing Stairs** (LeetCode #70)
   - *Task:* How many ways to reach step n?
   - *Hint:* dp[i] = dp[i-1] + dp[i-2]. Fibonacci pattern.

2. **House Robber** (LeetCode #198)
   - *Task:* Max money without robbing adjacent houses.
   - *Hint:* dp[i] = max(dp[i-1], dp[i-2] + nums[i]).

3. **Maximum Subarray** (LeetCode #53)
   - *Task:* Max sum contiguous subarray.
   - *Hint:* Kadane's: dp[i] = max(nums[i], dp[i-1] + nums[i]).

### Medium — Classic DP
1. **Coin Change** (LeetCode #322)
   - *Task:* Minimum coins to make amount.
   - *Hint:* dp[i] = min(dp[i], dp[i-coin]+1) for each coin.

2. **Longest Common Subsequence** (LeetCode #1143)
   - *Task:* Length of LCS of two strings.
   - *Hint:* 2D table. Match = diagonal + 1; mismatch = max of up/left.

3. **Longest Increasing Subsequence** (LeetCode #300)
   - *Task:* Length of LIS in array.
   - *Hint:* O(n²) DP or O(n log n) patience sorting.

4. **Word Break** (LeetCode #139)
   - *Task:* Can string be segmented using dictionary words?
   - *Hint:* dp[i] = any dp[j] && word(j..i) in dict.

5. **Unique Paths** (LeetCode #62)
   - *Task:* Number of paths from top-left to bottom-right in grid.
   - *Hint:* dp[i][j] = dp[i-1][j] + dp[i][j-1].

### Hard — Advanced DP
1. **Edit Distance** (LeetCode #72)
   - *Task:* Minimum operations to convert word1 to word2.
   - *Hint:* 3-way choice: insert, delete, replace.

2. **Burst Balloons** (LeetCode #312)
   - *Task:* Maximum coins from bursting balloons optimally.
   - *Hint:* Interval DP. dp[i][j] = max coins bursting all in range [i,j]. Last balloon = k.

3. **Regular Expression Matching** (LeetCode #10)
   - *Task:* Match string s against pattern p with `.` and `*`.
   - *Hint:* 2D DP. `*` can match 0 or more of preceding character.

---

## 17. How to Know You Have Mastered Dynamic Programming

You have mastered this topic when you can:
- [ ] Define the DP state clearly before writing any code
- [ ] Identify the transition formula and explain WHY it's correct
- [ ] Implement Coin Change and LCS from memory
- [ ] Space-optimize 2D DP to 1D (LCS, Knapsack)
- [ ] Implement the 0/1 Knapsack reversal trick (traverse capacity backward)
- [ ] Distinguish memoization vs tabulation and know when each is preferable
- [ ] Identify DP vs Greedy vs Backtracking from problem description
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Fibonacci computed naively is O(2ⁿ). After memoization, it's O(n). What changed?

2. In 0/1 Knapsack 1D optimization, why do you traverse capacity from high to low instead of low to high?

3. LCS of "ABCBDAB" and "BDCAB" — what is the length?

4. Coin Change with coins=[2] and amount=3 should return -1. Trace through the DP to show this.

5. Edit distance between "horse" and "ros" — what operations are needed?

6. The Interval DP for Burst Balloons: why is "last balloon to burst" the right way to think about it?

7. House Robber II (circular array) requires two passes. Why?

8. What is the difference between "number of ways" problems and "minimum/maximum" problems in terms of DP transition?

> **Answers:**
> 1. Each subproblem is computed only once (cached). Without memoization, fib(n-2) is recomputed exponentially many times.
> 2. When traversing low to high, you might use an item that was already added for a lower weight in the same iteration — effectively allowing unbounded reuse. High-to-low ensures each item is only considered once per row.
> 3. LCS length = 4 (e.g., "BCAB" or "BDAB").
> 4. dp[0]=0. dp[1]=∞(no coin≤1), dp[2]=0+1=1, dp[3]=dp[3-2]+1=dp[1]+1=∞. dp[3]>3 → return -1.
> 5. "horse"→"rorse" (replace h→r) →"rose" (delete r) →"ros" (delete e) = 3 operations. Edit distance = 3.
> 6. If we think "first to burst," the boundaries change as balloons burst. Thinking "last to burst in range [i,j]" keeps boundaries fixed (i-1 and j+1 are always the neighbors), making the subproblem well-defined.
> 7. Robbing house 0 and house n-1 in a circular array creates adjacency — they can't both be robbed. Run House Robber on [0..n-2] and on [1..n-1], take the max.
> 8. "Number of ways": transition uses addition (dp[i] += dp[j]). "Min/Max": transition uses min() or max(). Both build from overlapping subproblems — the aggregate operation differs.

---

**Next →** `../19_Intervals/01_Intervals.md`

### 2. Beginner-Friendly Intuition

Fibonacci without DP:
```
fib(5) = fib(4) + fib(3)
fib(4) = fib(3) + fib(2)   ← fib(3) computed twice!
fib(3) = fib(2) + fib(1)
...
```

Fibonacci with DP:
```
Compute once, store. fib(3)=2, fib(4)=3, fib(5)=5 — no repeats.
```

### 3. Two Approaches

**Top-Down (Memoization):** Recursive + cache results.
```java
int[] memo = new int[n + 1];
Arrays.fill(memo, -1);

int fib(int n) {
    if (n <= 1) return n;
    if (memo[n] != -1) return memo[n];
    return memo[n] = fib(n-1) + fib(n-2);
}
```

**Bottom-Up (Tabulation):** Iterative, fill table from base case up.
```java
int[] dp = new int[n + 1];
dp[0] = 0; dp[1] = 1;
for (int i = 2; i <= n; i++)
    dp[i] = dp[i-1] + dp[i-2];
return dp[n];
```

**Space Optimization:** Often you only need last 1-2 rows.
```java
int a = 0, b = 1;
for (int i = 2; i <= n; i++) { int c = a + b; a = b; b = c; }
return b;
```

### 4. Three Keys to Every DP Problem

1. **State:** What does dp[i] / dp[i][j] represent?
2. **Transition:** How does dp[i] depend on previous states?
3. **Base Case:** What are the smallest inputs?

---

## Part 2: 1D DP Patterns

### Climbing Stairs
```java
// State: dp[i] = number of ways to reach step i
// Transition: dp[i] = dp[i-1] + dp[i-2]
int climbStairs(int n) {
    if (n <= 2) return n;
    int a = 1, b = 2;
    for (int i = 3; i <= n; i++) { int c = a+b; a=b; b=c; }
    return b;
}
```

### House Robber
```java
// State: dp[i] = max money robbing houses 0..i
// Transition: dp[i] = max(dp[i-1], dp[i-2] + nums[i])
int rob(int[] nums) {
    int prev2 = 0, prev1 = 0;
    for (int num : nums) {
        int curr = Math.max(prev1, prev2 + num);
        prev2 = prev1; prev1 = curr;
    }
    return prev1;
}
```

### Longest Increasing Subsequence (LIS)

```java
// O(n²) DP
int lis(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, 1);
    int maxLen = 1;
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++)
            if (nums[j] < nums[i]) dp[i] = Math.max(dp[i], dp[j] + 1);
        maxLen = Math.max(maxLen, dp[i]);
    }
    return maxLen;
}

// O(n log n) with patience sorting
int lisOptimal(int[] nums) {
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

---

## Part 3: Knapsack Patterns

### 0/1 Knapsack

**Problem:** n items, each with weight[i] and value[i]. Capacity W. Maximize value without exceeding capacity. Each item used at most once.

```java
int knapsack01(int[] weights, int[] values, int W) {
    int n = weights.length;
    int[][] dp = new int[n+1][W+1];
    // dp[i][w] = max value using first i items with capacity w

    for (int i = 1; i <= n; i++) {
        for (int w = 0; w <= W; w++) {
            // Don't take item i
            dp[i][w] = dp[i-1][w];
            // Take item i (if it fits)
            if (weights[i-1] <= w)
                dp[i][w] = Math.max(dp[i][w], dp[i-1][w - weights[i-1]] + values[i-1]);
        }
    }
    return dp[n][W];
}

// Space-optimized (1D dp, iterate w backwards!)
int knapsack01Opt(int[] weights, int[] values, int W) {
    int[] dp = new int[W+1];
    for (int i = 0; i < weights.length; i++)
        for (int w = W; w >= weights[i]; w--)  // backwards to avoid reuse
            dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
    return dp[W];
}
```

### Unbounded Knapsack (each item can be used multiple times)

```java
int unboundedKnapsack(int[] weights, int[] values, int W) {
    int[] dp = new int[W+1];
    for (int w = 1; w <= W; w++)
        for (int i = 0; i < weights.length; i++)
            if (weights[i] <= w)
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
    return dp[W];
    // Note: iterate w forwards (allows reuse of same item)
}
```

### Subset Sum
```java
boolean subsetSum(int[] nums, int target) {
    boolean[] dp = new boolean[target+1];
    dp[0] = true;
    for (int num : nums)
        for (int s = target; s >= num; s--)  // backwards = 0/1 (no reuse)
            dp[s] = dp[s] || dp[s - num];
    return dp[target];
}
```

### Coin Change (Minimum coins — unbounded)
```java
int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount+1];
    Arrays.fill(dp, amount+1);  // "infinity"
    dp[0] = 0;
    for (int amt = 1; amt <= amount; amt++)
        for (int coin : coins)
            if (coin <= amt) dp[amt] = Math.min(dp[amt], dp[amt - coin] + 1);
    return dp[amount] > amount ? -1 : dp[amount];
}
```

---

## Part 4: String DP Patterns

### Longest Common Subsequence (LCS)

```java
int lcs(String s1, String s2) {
    int m = s1.length(), n = s2.length();
    int[][] dp = new int[m+1][n+1];
    // dp[i][j] = LCS of s1[0..i-1] and s2[0..j-1]
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (s1.charAt(i-1) == s2.charAt(j-1))
                dp[i][j] = dp[i-1][j-1] + 1;
            else
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
    return dp[m][n];
}
```

### Edit Distance (Levenshtein)

```java
int editDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m+1][n+1];
    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;
    for (int i = 1; i <= m; i++)
        for (int j = 1; j <= n; j++)
            if (word1.charAt(i-1) == word2.charAt(j-1))
                dp[i][j] = dp[i-1][j-1];
            else
                dp[i][j] = 1 + Math.min(dp[i-1][j-1],   // replace
                                Math.min(dp[i-1][j],     // delete
                                         dp[i][j-1]));   // insert
    return dp[m][n];
}
```

---

## Part 5: Grid DP

### Unique Paths
```java
int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];
    for (int[] row : dp) Arrays.fill(row, 1);  // first row and column = 1
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            dp[i][j] = dp[i-1][j] + dp[i][j-1];
    return dp[m-1][n-1];
}
```

### Minimum Path Sum
```java
int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dp = new int[m][n];
    dp[0][0] = grid[0][0];
    for (int i = 1; i < m; i++) dp[i][0] = dp[i-1][0] + grid[i][0];
    for (int j = 1; j < n; j++) dp[0][j] = dp[0][j-1] + grid[0][j];
    for (int i = 1; i < m; i++)
        for (int j = 1; j < n; j++)
            dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
    return dp[m-1][n-1];
}
```

---

## Part 6: Interval DP

### Matrix Chain Multiplication
```java
int matrixChainMultiplication(int[] dims) {
    int n = dims.length - 1;  // n matrices
    int[][] dp = new int[n][n];
    // dp[i][j] = min operations to multiply matrices i..j
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            dp[i][j] = Integer.MAX_VALUE;
            for (int k = i; k < j; k++) {
                int cost = dp[i][k] + dp[k+1][j] + dims[i]*dims[k+1]*dims[j+1];
                dp[i][j] = Math.min(dp[i][j], cost);
            }
        }
    }
    return dp[0][n-1];
}
```

---

## Practice Problems

**Easy:**
1. Climbing Stairs.
2. House Robber.
3. Best Time to Buy and Sell Stock.

**Medium:**
1. Coin Change.
2. Longest Increasing Subsequence.
3. Unique Paths.
4. Jump Game.
5. Partition Equal Subset Sum.

**Hard:**
1. Edit Distance.
2. Longest Common Subsequence.
3. Regular Expression Matching.

---

## DP Problem-Solving Template

```
1. Identify: Does it have overlapping subproblems + optimal substructure?
2. Define: What does dp[i] (or dp[i][j]) represent?
3. Transition: How do I compute dp[i] from previous states?
4. Base Case: What are dp[0] and dp[1]?
5. Code: Top-down first (easier to reason), then tabulate.
6. Optimize: Can I reduce space?
```

---

## MAANG Pro Upgrade: Recurrence-First DP System

DP becomes manageable when you stop asking "which code template?" and start asking:

> "What decision am I making, and what information must survive into the next decision?"

### The 7-Step DP Interview Flow

1. **Brute force decision tree:** What choices exist at each step?
2. **State:** Which variables uniquely describe a subproblem?
3. **Meaning:** Say `dp[...]` in one sentence.
4. **Transition:** Try all choices and combine previous states.
5. **Base cases:** What is true before any choices?
6. **Answer location:** Which cell/state contains the answer?
7. **Order:** In what order are dependencies already computed?

If step 3 is vague, the DP will be wrong.

### State Design Cheat Sheet

| Problem Signal | Likely State | Example |
|---|---|---|
| Prefix of one array/string | `dp[i]` | Climbing Stairs, Word Break |
| Prefixes of two strings | `dp[i][j]` | LCS, Edit Distance |
| Grid path | `dp[r][c]` | Unique Paths, Min Path Sum |
| Choose/not choose items | `dp[i][capacity]` | 0/1 Knapsack |
| Unlimited item reuse | `dp[amount]` or `dp[i][amount]` | Coin Change |
| Subarray/subsequence ending at i | `dp[i]` ending at `i` | LIS, Max Product Subarray |
| Interval from i to j | `dp[i][j]` | Burst Balloons, MCM |
| Subset of used items | `dp[mask]` | TSP, Assignment |
| Tree node decisions | return tuple per node | House Robber III |
| Stock trading constraints | `dp[day][holding][transactions]` | Stock III/IV |

### Recurrence Templates

#### 1D Count Ways

```text
dp[i] = sum(dp[i - move] for each valid move)
```

Use for: Climbing Stairs, Decode Ways, Coin Change II.

#### Min / Max Optimization

```text
dp[state] = best(dp[next/previous state] + cost)
```

Use for: Coin Change, Min Cost Tickets, Dungeon Game.

#### 0/1 Choice

```text
dp[i][cap] = max(
    dp[i-1][cap],                         // skip item
    value[i] + dp[i-1][cap - weight[i]]   // take item
)
```

Use for: Partition Equal Subset Sum, Target Sum, Ones and Zeroes.

#### Unbounded Choice

```text
dp[amount] = best(dp[amount - coin] + 1)
```

Loop direction matters:
- 0/1 knapsack: capacity goes backward.
- unbounded knapsack: capacity goes forward.

#### Two-String DP

```text
if chars match:
    dp[i][j] = diagonal transition
else:
    dp[i][j] = best(top, left, diagonal + edit cost)
```

Use for: LCS, Edit Distance, Regex, Wildcard Matching.

#### Interval DP

```text
for len from small to large:
    for left:
        right = left + len - 1
        for split in left..right:
            dp[left][right] = best(dp[left][split] + dp[split+1][right] + cost)
```

Use for: Matrix Chain Multiplication, Burst Balloons, Palindrome Partitioning.

#### Bitmask DP

```text
dp[mask] = best answer after choosing the set bits in mask
for each item not in mask:
    nextMask = mask | (1 << item)
    dp[nextMask] = best(dp[nextMask], dp[mask] + cost)
```

Use for: TSP, Assignment Problem, Shortest Superstring.

### Tabulation Order Rules

| Dependency | Fill Order |
|---|---|
| `dp[i]` uses smaller `i` | Left to right |
| `dp[r][c]` uses top/left | Top-left to bottom-right |
| `dp[i][j]` uses shorter intervals | Increasing interval length |
| 0/1 knapsack 1D | Capacity descending |
| Unbounded knapsack 1D | Capacity ascending |
| Tree DP | Postorder DFS |
| Bitmask DP | Increasing mask or BFS over masks |

### Memoization to Tabulation Conversion

1. Write recursive memo first.
2. List every dependency in the recursive formula.
3. Fill the table in the opposite direction of recursion.
4. Replace recursive calls with table lookups.
5. Keep only previous row/state if dependencies allow it.

### DP Proof Template

> "My state `dp[...]` means [exact meaning]. The transition is complete because it considers every possible last decision. It is non-overlapping because each last decision maps to a distinct previous state. The base case represents the empty/minimal input. Therefore, by induction over the fill order, each state is correct."

### Common Pro Mistakes

| Mistake | Fix |
|---|---|
| Defining `dp[i]` as "answer so far" | State exactly what prefix/condition it represents. |
| Wrong loop direction in 1D knapsack | Backward for 0/1, forward for unbounded. |
| Missing impossible sentinel | Use large INF for min problems, not 0. |
| Confusing subsequence and substring | Subsequence can skip; substring must be contiguous. |
| Returning wrong cell | Identify answer state before coding. |
| Optimizing space too early | Get 2D/tabulation correct first. |
| Ignoring reconstruction | Track parent/choice when actual solution is required. |

### 60-Second DP Explanation Template

> "I first define `dp[...]` as [meaning]. The last decision is [choice]. If I take that choice, I transition from [previous state]; if I skip it, I use [other state]. The base case is [base]. Since every state depends only on smaller/already-computed states, I can fill it in [order] with time [complexity] and space [complexity]."

---

**Next →** `../19_Intervals/01_Intervals.md`
