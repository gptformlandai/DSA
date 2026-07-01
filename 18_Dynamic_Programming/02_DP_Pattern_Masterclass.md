# Section 18.2 - DP Pattern Masterclass

> Goal: learn DP from the baby step of recursion, understand why raw recursion is too slow, then master the major DP patterns from `DPPatterns.pdf`.

---

## 0. The Big Idea

Dynamic Programming is not a new magic algorithm.

It is this:

```text
Recursion + remembering repeated answers = DP
```

Think like this:

- Recursion asks: "Can I solve this problem using smaller versions of the same problem?"
- DP asks: "Am I solving the same smaller problem again and again?"
- Memoization says: "Save the answer the first time."
- Tabulation says: "Fill the saved answers in a smart order without recursion."

If you can write the recursive relation, you are already most of the way to DP.

---

## 1. Start With Recursion

### Baby Example: Climbing Stairs

You can climb either 1 step or 2 steps.

To reach step `n`, your last move came from:

- step `n - 1`, then take 1 step
- step `n - 2`, then take 2 steps

So:

```text
ways(n) = ways(n - 1) + ways(n - 2)
```

Recursive code:

```java
int ways(int n) {
    if (n == 0) return 1;
    if (n < 0) return 0;
    return ways(n - 1) + ways(n - 2);
}
```

This is easy to understand, but it repeats work.

```text
ways(5)
  ways(4)
    ways(3)
    ways(2)
  ways(3)   <- repeated
```

For a small `n`, it is fine. For a large `n`, it explodes.

---

## 2. Why Plain Recursion Is Not Enough

Plain recursion has two problems in DP-style questions:

| Problem | What Happens |
|---|---|
| Repeated subproblems | Same state is solved many times |
| Deep call stack | Large input can cause stack overflow |

The important word is **state**.

A state is the exact input to your recursive function.

For `ways(n)`, the state is just `n`.

If `ways(3)` appears 20 times, all 20 calls return the same answer. So we should compute it once.

That is DP.

---

## 3. Recursion To Memoization

Memoization means top-down DP.

We keep the recursion, but add a cache.

```java
int ways(int n, int[] memo) {
    if (n == 0) return 1;
    if (n < 0) return 0;
    if (memo[n] != -1) return memo[n];

    memo[n] = ways(n - 1, memo) + ways(n - 2, memo);
    return memo[n];
}

int climbStairs(int n) {
    int[] memo = new int[n + 1];
    Arrays.fill(memo, -1);
    return ways(n, memo);
}
```

Now:

- `ways(3)` is computed once.
- Later calls reuse `memo[3]`.
- Time becomes `O(n)`.
- Space becomes `O(n)`.

---

## 4. Memoization To Tabulation

Tabulation means bottom-up DP.

Instead of asking recursion to go down, we build from the smallest answers.

```java
int climbStairs(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 1;

    for (int i = 1; i <= n; i++) {
        dp[i] += dp[i - 1];
        if (i >= 2) dp[i] += dp[i - 2];
    }

    return dp[n];
}
```

Same formula:

```text
ways(i) = ways(i - 1) + ways(i - 2)
```

Different execution style:

| Style | Starts From | Good For |
|---|---|---|
| Recursion | Final answer | Understanding choices |
| Memoization | Final answer + cache | Fast implementation |
| Tabulation | Base cases | Avoiding recursion stack |
| Space optimization | Only needed previous states | Interview polish |

Space optimized:

```java
int climbStairs(int n) {
    int prev2 = 1; // ways(0)
    int prev1 = 1; // ways(1)

    for (int i = 2; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }

    return prev1;
}
```

---

## 5. The DP Checklist

For every DP problem, answer these six questions before coding:

| Question | Meaning |
|---|---|
| 1. What is the state? | What does `dp[...]` mean exactly? |
| 2. What is the choice? | What decisions are available now? |
| 3. What is the transition? | How do smaller answers create this answer? |
| 4. What is the base case? | What smallest input is already known? |
| 5. What is the order? | Which states must be ready first? |
| 6. What is the answer? | Which `dp` cell do we return? |

The most common interview mistake is writing code before defining the state.

Say the state out loud:

```text
dp[i] means ...
dp[i][j] means ...
dp[i][j][k] means ...
```

If you cannot say it clearly, the code will usually become messy.

---

## 6. Pattern 1 - Minimum Or Maximum Path To Reach A Target

### Baby Meaning

You are trying to reach a target.

Every move has a cost or score.

You want the cheapest, largest, smallest, or best result.

### Recognition Keywords

Use this pattern when the problem says:

- minimum cost
- maximum score
- cheapest way
- fewest coins
- minimum path sum
- minimum tickets
- reach target with best cost

### Core Question

```text
To reach state i, what previous states could I come from?
```

Then choose the best one.

### Formula Shape

```text
dp[i] = min(dp[i - move] + costOfMove)
```

or:

```text
dp[i] = max(dp[i - move] + scoreOfMove)
```

### Recursion First

Example: minimum coins to make `amount`.

```java
int solve(int amount, int[] coins) {
    if (amount == 0) return 0;
    if (amount < 0) return 1_000_000;

    int best = 1_000_000;
    for (int coin : coins) {
        best = Math.min(best, 1 + solve(amount - coin, coins));
    }

    return best;
}
```

This is correct thinking, but slow because the same `amount` repeats.

### Memoized Version

```java
int solve(int amount, int[] coins, int[] memo) {
    if (amount == 0) return 0;
    if (amount < 0) return 1_000_000;
    if (memo[amount] != -1) return memo[amount];

    int best = 1_000_000;
    for (int coin : coins) {
        best = Math.min(best, 1 + solve(amount - coin, coins, memo));
    }

    memo[amount] = best;
    return best;
}

int coinChange(int[] coins, int amount) {
    int[] memo = new int[amount + 1];
    Arrays.fill(memo, -1);

    int answer = solve(amount, coins, memo);
    return answer >= 1_000_000 ? -1 : answer;
}
```

### Bottom-Up Version

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

### Beginner Trap

For minimum problems, do not initialize everything to `0`.

`0` looks like a real answer. Use a large impossible value.

### Practice From This Pattern

| Problem | Why It Fits |
|---|---|
| 746. Min Cost Climbing Stairs | Min cost to reach top |
| 64. Minimum Path Sum | Min path in grid |
| 322. Coin Change | Min coins to reach amount |
| 931. Minimum Falling Path Sum | Min path with row transitions |
| 983. Minimum Cost For Tickets | Min cost to cover days |
| 279. Perfect Squares | Min squares to reach target |
| 120. Triangle | Min path through triangle |
| 174. Dungeon Game | Min initial health with reverse thinking |
| 871. Minimum Number of Refueling Stops | Min stops to reach target |

---

## 7. Pattern 2 - Distinct Ways

### Baby Meaning

You are not looking for the best path.

You are counting how many valid paths exist.

If there are multiple previous states that can reach the current state, add their counts.

### Recognition Keywords

Use this pattern when the problem says:

- number of ways
- count paths
- count combinations
- count possible sequences
- how many ways to reach
- probability after moves

### Core Question

```text
How many previous states can send me into this state?
```

Then add them.

### Formula Shape

```text
dp[i] = sum(dp[i - move])
```

For a grid:

```text
dp[r][c] = dp[r - 1][c] + dp[r][c - 1]
```

### Recursion First

Example: unique paths in a grid.

```java
int paths(int r, int c) {
    if (r == 0 && c == 0) return 1;
    if (r < 0 || c < 0) return 0;

    return paths(r - 1, c) + paths(r, c - 1);
}
```

This repeats the same cells many times.

### Memoized Version

```java
int paths(int r, int c, int[][] memo) {
    if (r == 0 && c == 0) return 1;
    if (r < 0 || c < 0) return 0;
    if (memo[r][c] != -1) return memo[r][c];

    memo[r][c] = paths(r - 1, c, memo) + paths(r, c - 1, memo);
    return memo[r][c];
}
```

### Bottom-Up Version

```java
int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];

    for (int r = 0; r < m; r++) {
        for (int c = 0; c < n; c++) {
            if (r == 0 && c == 0) {
                dp[r][c] = 1;
            } else {
                if (r > 0) dp[r][c] += dp[r - 1][c];
                if (c > 0) dp[r][c] += dp[r][c - 1];
            }
        }
    }

    return dp[m - 1][n - 1];
}
```

### Important Difference: Count Vs Best

| Problem Type | Transition Uses |
|---|---|
| Count ways | `+` |
| Minimum | `min(...)` |
| Maximum | `max(...)` |
| Possible or impossible | `||` |

### Beginner Trap

Loop order changes the meaning.

For coin-style counting:

- count combinations: loop coins first, amount second
- count ordered sequences: loop amount first, coins second

### Practice From This Pattern

| Problem | Why It Fits |
|---|---|
| 70. Climbing Stairs | Count ways to reach step |
| 62. Unique Paths | Count paths in grid |
| 63. Unique Paths II | Count paths with blocked cells |
| 494. Target Sum | Count signs that reach target |
| 377. Combination Sum IV | Count ordered combinations |
| 416. Partition Equal Subset Sum | Possible subset target |
| 576. Out of Boundary Paths | Count paths leaving grid |
| 935. Knight Dialer | Count knight movement sequences |
| 1220. Count Vowels Permutation | Count strings with transition rules |
| 1155. Dice Rolls With Target Sum | Count dice outcomes |

---

## 8. Pattern 3 - Merging Intervals / Interval DP

### Baby Meaning

You are given a range.

You need the best way to split, merge, burst, cut, or build inside that range.

The state usually means:

```text
dp[left][right] = best answer for the interval from left to right
```

### Recognition Keywords

Use this pattern when the problem says:

- merge stones
- burst balloons
- triangulate polygon
- minimum score for interval
- build tree from values
- optimal way to combine ranges
- guess number with worst-case cost

### Core Question

```text
What is the last operation inside interval [left, right]?
```

This is the trick that makes interval DP easier.

If you pick the first operation, the interval boundaries can change in confusing ways.

If you pick the last operation, the left and right subintervals are already solved.

### Formula Shape

```text
dp[left][right] = best over split k:
    dp[left][k] + dp[k + 1][right] + cost(left, k, right)
```

For Burst Balloons, the better mental model is:

```text
k is the last balloon burst inside [left, right]
```

### Recursion First

```java
int solve(int left, int right, int[] nums) {
    if (left > right) return 0;

    int best = 0;
    for (int k = left; k <= right; k++) {
        int coins = nums[left - 1] * nums[k] * nums[right + 1];
        coins += solve(left, k - 1, nums);
        coins += solve(k + 1, right, nums);
        best = Math.max(best, coins);
    }

    return best;
}
```

### Memoized Version

```java
int solve(int left, int right, int[] nums, int[][] memo) {
    if (left > right) return 0;
    if (memo[left][right] != -1) return memo[left][right];

    int best = 0;
    for (int k = left; k <= right; k++) {
        int coins = nums[left - 1] * nums[k] * nums[right + 1];
        coins += solve(left, k - 1, nums, memo);
        coins += solve(k + 1, right, nums, memo);
        best = Math.max(best, coins);
    }

    memo[left][right] = best;
    return best;
}
```

### Bottom-Up Version

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

            for (int k = left; k <= right; k++) {
                int coins = nums[left - 1] * nums[k] * nums[right + 1];
                coins += dp[left][k - 1] + dp[k + 1][right];
                dp[left][right] = Math.max(dp[left][right], coins);
            }
        }
    }

    return dp[1][n];
}
```

### Beginner Trap

For interval DP, fill smaller intervals before larger intervals.

That is why the outer loop is usually `len`.

### Practice From This Pattern

| Problem | Why It Fits |
|---|---|
| 312. Burst Balloons | Last balloon in interval |
| 1039. Minimum Score Triangulation of Polygon | Pick split triangle |
| 1130. Minimum Cost Tree From Leaf Values | Merge interval/tree cost |
| 96. Unique Binary Search Trees | Pick root, combine left/right |
| 375. Guess Number Higher or Lower II | Pick guess, pay worst side |
| 546. Remove Boxes | Advanced interval state |
| 1000. Minimum Cost to Merge Stones | Merge intervals with constraints |

---

## 9. Pattern 4 - DP On Strings

### Baby Meaning

String DP usually compares prefixes.

For two strings:

```text
dp[i][j] = answer using first i chars of string1 and first j chars of string2
```

For one string:

```text
dp[left][right] = answer inside substring left..right
```

### Recognition Keywords

Use this pattern when the problem says:

- longest common subsequence
- edit distance
- shortest common supersequence
- distinct subsequences
- palindrome substring
- palindrome subsequence
- delete/insert/replace characters

### Core Question For Two Strings

```text
What happens if the last characters match?
What happens if they do not match?
```

### LCS Formula

```text
if s1[i - 1] == s2[j - 1]:
    dp[i][j] = dp[i - 1][j - 1] + 1
else:
    dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])
```

### Bottom-Up LCS

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

### Edit Distance Formula

If the last characters match, no operation is needed.

If they do not match, try:

- insert
- delete
- replace

```java
int minDistance(String a, String b) {
    int m = a.length();
    int n = b.length();
    int[][] dp = new int[m + 1][n + 1];

    for (int i = 0; i <= m; i++) dp[i][0] = i;
    for (int j = 0; j <= n; j++) dp[0][j] = j;

    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                int replace = dp[i - 1][j - 1];
                int delete = dp[i - 1][j];
                int insert = dp[i][j - 1];
                dp[i][j] = 1 + Math.min(replace, Math.min(delete, insert));
            }
        }
    }

    return dp[m][n];
}
```

### One-String Palindrome DP

```text
dp[left][right] = whether s[left..right] is a palindrome
```

```java
int countSubstrings(String s) {
    int n = s.length();
    boolean[][] dp = new boolean[n][n];
    int count = 0;

    for (int len = 1; len <= n; len++) {
        for (int left = 0; left + len - 1 < n; left++) {
            int right = left + len - 1;

            if (s.charAt(left) == s.charAt(right)
                    && (len <= 2 || dp[left + 1][right - 1])) {
                dp[left][right] = true;
                count++;
            }
        }
    }

    return count;
}
```

### Beginner Trap

Do not confuse substring and subsequence.

| Term | Meaning |
|---|---|
| Substring | Contiguous characters |
| Subsequence | Can skip characters |

`"ace"` is a subsequence of `"abcde"`, but not a substring.

### Practice From This Pattern

| Problem | Why It Fits |
|---|---|
| 1143. Longest Common Subsequence | Compare two prefixes |
| 72. Edit Distance | Insert/delete/replace |
| 115. Distinct Subsequences | Count ways one string forms another |
| 1092. Shortest Common Supersequence | Build from LCS thinking |
| 516. Longest Palindromic Subsequence | One string, interval DP |
| 647. Palindromic Substrings | One string, palindrome table |
| 5. Longest Palindromic Substring | One string, contiguous palindrome |
| 712. Minimum ASCII Delete Sum | String edit cost |

---

## 10. Pattern 5 - Decision Making DP

### Baby Meaning

At each item, day, or index, you must decide what to do.

Common choices:

- take or skip
- buy or sell
- rob or not rob
- include or exclude
- hold or not hold

### Recognition Keywords

Use this pattern when the problem says:

- choose or ignore
- maximum profit
- cannot take adjacent items
- buy/sell with cooldown or fee
- at most k transactions
- include/exclude current value
- subset choice

### Core Question

```text
What choices are allowed at this state, and what state do I move to after each choice?
```

### House Robber Thinking

At house `i`, there are two choices:

- rob it: then you cannot rob `i - 1`
- skip it: keep the best answer from `i - 1`

Formula:

```text
dp[i] = max(dp[i - 1], nums[i] + dp[i - 2])
```

Code:

```java
int rob(int[] nums) {
    int prev2 = 0;
    int prev1 = 0;

    for (int money : nums) {
        int take = prev2 + money;
        int skip = prev1;
        int curr = Math.max(take, skip);

        prev2 = prev1;
        prev1 = curr;
    }

    return prev1;
}
```

### Stock State Machine Thinking

For stock problems, define states by what you currently hold.

```text
hold = best profit while holding a stock
cash = best profit while holding no stock
```

With transaction fee:

```java
int maxProfit(int[] prices, int fee) {
    int hold = -prices[0];
    int cash = 0;

    for (int i = 1; i < prices.length; i++) {
        int oldCash = cash;

        cash = Math.max(cash, hold + prices[i] - fee);
        hold = Math.max(hold, oldCash - prices[i]);
    }

    return cash;
}
```

### 0/1 Choice Thinking

For each item:

- skip it
- take it if capacity allows

```java
boolean canPartition(int[] nums) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % 2 == 1) return false;

    int target = sum / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;

    for (int num : nums) {
        for (int t = target; t >= num; t--) {
            dp[t] = dp[t] || dp[t - num];
        }
    }

    return dp[target];
}
```

### Beginner Trap

For 0/1 decisions, loop backward when using 1D DP.

Backward means each item is used once.

Forward means the same item may be reused, which becomes unbounded knapsack.

### Practice From This Pattern

| Problem | Why It Fits |
|---|---|
| 198. House Robber | Take/skip with adjacency rule |
| 213. House Robber II | Take/skip on a circle |
| 337. House Robber III | Take/skip on a tree |
| 121. Best Time to Buy and Sell Stock | Buy/sell once |
| 714. Stock With Transaction Fee | Hold/cash state |
| 309. Stock With Cooldown | Hold/sold/rest state |
| 123. Stock III | At most two transactions |
| 188. Stock IV | At most k transactions |
| 416. Partition Equal Subset Sum | Include/exclude to hit target |
| 474. Ones and Zeroes | Include/exclude with two capacities |

---

## 11. Pattern Picker

When you read a DP problem, map its wording to a pattern.

| If The Problem Says | Think |
|---|---|
| minimum cost, maximum score, fewest moves | Min/Max path to target |
| number of ways, count paths, count sequences | Distinct ways |
| merge, burst, cut, triangulate, guess interval | Interval DP |
| two strings, edit, common subsequence | String DP |
| palindrome in one string | String interval DP |
| choose/skip item | Decision making / knapsack |
| buy/sell stock | Decision making state machine |
| target sum, subset sum, partition | Decision making target DP |

---

## 12. The Beginner-To-Pro Build Order

Do not jump directly to optimized code.

Use this ladder:

1. Write the recursive function signature.
2. Define what the function returns.
3. Write base cases.
4. Try all choices.
5. Add memoization.
6. Convert to tabulation.
7. Optimize space only after the table is correct.
8. Explain time and space.

### Example Explanation Template

Use this in interviews:

```text
I define dp[state] as ...
The last decision is ...
If I choose option A, I move to ...
If I choose option B, I move to ...
The base case is ...
The answer is ...
The time complexity is ...
The space complexity is ...
```

---

## 13. Mini Drills

For each problem, force yourself to write only the state and transition first.

| Problem | State To Define | Transition Type |
|---|---|---|
| Climbing Stairs | `dp[i]` | count ways |
| Coin Change | `dp[amount]` | min over coins |
| Unique Paths | `dp[r][c]` | count from top/left |
| LCS | `dp[i][j]` | match/mismatch |
| Edit Distance | `dp[i][j]` | insert/delete/replace |
| Burst Balloons | `dp[left][right]` | choose last balloon |
| House Robber | `dp[i]` | take/skip |
| Stock With Fee | `hold`, `cash` | buy/sell/rest |
| Partition Equal Subset Sum | `dp[target]` | include/exclude |

---

## 14. Common DP Mistakes

| Mistake | Why It Hurts | Fix |
|---|---|---|
| Starting with code | State becomes unclear | Write `dp` meaning first |
| Using `0` for impossible min states | `0` may be treated as valid | Use `INF` |
| Forgetting base cases | Table never grows correctly | Fill smallest known states first |
| Wrong loop direction | Reuses items incorrectly | Backward for 0/1, forward for unbounded |
| Returning wrong cell | Good table, wrong answer | Identify answer before coding |
| Optimizing too early | Harder to debug | Start with full table |
| Confusing count and min/max | Wrong transition operation | Count uses `+`, best uses `min/max` |

---

## 15. Mastery Checklist

You are strong at these DP patterns when you can:

- [ ] Convert recursion to memoization without looking it up
- [ ] Convert memoization to tabulation
- [ ] Say the state in one clean sentence
- [ ] Identify count vs min/max vs possible
- [ ] Recognize interval DP from "last operation" thinking
- [ ] Recognize string DP from prefix comparison
- [ ] Recognize decision DP from take/skip or state-machine choices
- [ ] Explain why loop direction matters in 0/1 knapsack
- [ ] Solve Coin Change, Unique Paths, LCS, Edit Distance, Burst Balloons, House Robber, and Stock With Fee from memory

---

## 16. Fast Revision Notes

- DP starts as recursion.
- Memoization removes repeated recursion.
- Tabulation removes recursion stack.
- State is the most important part.
- Count ways usually adds.
- Minimum/maximum usually uses `min` or `max`.
- String DP usually compares prefixes.
- Interval DP usually chooses the last operation.
- Decision DP usually chooses take/skip, buy/sell, or hold/cash.

---

**Back to reference:** `01_DP_Patterns.md`

**Next:** `../19_Intervals/01_Intervals.md`
