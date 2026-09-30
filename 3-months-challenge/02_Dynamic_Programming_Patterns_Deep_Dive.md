# Dynamic Programming Patterns Deep Dive
## Junior Dev's Complete Guide to L5 MAANG DP Mastery

--

# THE DP MINDSET: Before You Code Anything

## The One Sentence That Unlocks DP

> **DP is just "smart recursion" - solve small problems, remember answers, build up to big problems.**

## The 5 Questions Framework (Ask EVERY time!)

```
1. WHAT is the state? (What variables define a subproblem?)
2. WHAT is the recurrence? (How do states relate?)
3. WHAT is the base case? (Where does recursion stop?)
4. WHAT is the answer? (Which state gives final answer?)
5. CAN we optimize space? (Do we need all states?)
```

## The Two Approaches

```
TOP-DOWN (Memoization):
- Start from big problem
- Recursively solve smaller
- Cache results in memo[]

BOTTOM-UP (Tabulation):
- Start from base cases
- Build up to answer
- Fill dp[] table iteratively
```

--

# PART 1: 1D DYNAMIC PROGRAMMING (Week 17)

## The 1D DP Template

```java
// State: dp[i] = answer for subproblem ending at/using index i
// Recurrence: dp[i] = f(dp[i-1], dp[i-2], ...)
// Base: dp[0] = ..., dp[1] = ...
// Answer: dp[n-1] or max(dp[])

int[] dp = new int[n];
dp[0] = base_case;

for (int i = 1; i < n; i++) {
    dp[i] = /* recurrence using dp[i-1], dp[i-2], etc */;
}

return dp[n-1];
```

--

## PATTERN 1: Climbing Stairs (LC 70)

### Pattern Recognition Signal

> **When you see:** "How many ways to reach end" with limited step sizes
> **Instant thought:** "Fibonacci variant! dp[i] = dp[i-1] + dp[i-2]"

### The Mental Model: The Staircase

At each step, you can come from 1 step below OR 2 steps below. Total ways = sum of both.

### Visual Dry Run

```
n = 5 stairs

dp[i] = ways to reach stair i

dp[0] = 1 (ground, 1 way to be here)
dp[1] = 1 (only 1 step from ground)
dp[2] = dp[1] + dp[0] = 1 + 1 = 2
dp[3] = dp[2] + dp[1] = 2 + 1 = 3
dp[4] = dp[3] + dp[2] = 3 + 2 = 5
dp[5] = dp[4] + dp[3] = 5 + 3 = 8

Answer: 8 ways
```

### The Code

```java
public int climbStairs(int n) {
    if (n <= 2) return n;
    
    int prev2 = 1, prev1 = 2;
    
    for (int i = 3; i <= n; i++) {
        int curr = prev1 + prev2;  // Ways = from 1 step + from 2 steps
        prev2 = prev1;
        prev1 = curr;
    }
    
    return prev1;
}
```

### Mind-Map Anchor

```
CLIMBING STAIRS
      |
      v
+---------+
| dp[i] = ways to i|
| = dp[i-1]+dp[i-2]|
| Fibonacci pattern|
| O(1) space ok    |
+---------+
```

**Memory phrase:** "Fibonacci - sum of previous two ways"

--

## PATTERN 2: House Robber (LC 198)

### Pattern Recognition Signal

> **When you see:** "Maximum sum with no adjacent elements"
> **Instant thought:** "Take or skip! dp[i] = max(dp[i-1], dp[i-2] + nums[i])"

### The Mental Model: The Thief's Dilemma

At each house: Rob it (add value + skip previous) OR Skip it (keep previous best).

### Visual Dry Run

```
nums = [2, 7, 9, 3, 1]

dp[i] = max money robbing houses 0..i

dp[0] = 2 (rob house 0)
dp[1] = max(2, 7) = 7 (rob house 1, skip 0)
dp[2] = max(dp[1], dp[0]+9) = max(7, 2+9) = 11 (rob 0,2)
dp[3] = max(dp[2], dp[1]+3) = max(11, 7+3) = 11 (still rob 0,2)
dp[4] = max(dp[3], dp[2]+1) = max(11, 11+1) = 12 (rob 0,2,4)

Answer: 12
```

### The Code

```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    
    int prev2 = 0, prev1 = 0;
    
    for (int num : nums) {
        int curr = Math.max(prev1, prev2 + num);  // Skip or take
        prev2 = prev1;
        prev1 = curr;
    }
    
    return prev1;
}
```

### Mind-Map Anchor

```
HOUSE ROBBER
     |
     v
+----------+
| Take or Skip      |
| dp[i] = max of:   |
|   skip: dp[i-1]   |
|   take: dp[i-2]+v |
+----------+
```

**Memory phrase:** "Take current + skip one, or skip current"

--

## PATTERN 3: House Robber II (LC 213) - Circular

### Pattern Recognition Signal

> **When you see:** "Circular array" + House Robber
> **Instant thought:** "Run twice: exclude first OR exclude last!"

### The Key Insight

First and last houses are adjacent (circular). So either:
- Rob houses 0 to n-2 (exclude last)
- Rob houses 1 to n-1 (exclude first)

Take maximum of both!

### The Code

```java
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    
    return Math.max(
        robLinear(nums, 0, nums.length - 2),  // Exclude last
        robLinear(nums, 1, nums.length - 1)   // Exclude first
    );
}

private int robLinear(int[] nums, int start, int end) {
    int prev2 = 0, prev1 = 0;
    for (int i = start; i <= end; i++) {
        int curr = Math.max(prev1, prev2 + nums[i]);
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
```

### Mind-Map Anchor

```
HOUSE ROBBER II (CIRCULAR)
           |
           v
+-----------+
| Circular = 2 cases   |
| Case 1: skip last    |
| Case 2: skip first   |
| Answer = max of both |
+-----------+
```

**Memory phrase:** "Circular = two linear runs, exclude one end each"

--

## PATTERN 4: Jump Game (LC 55)

### Pattern Recognition Signal

> **When you see:** "Can you reach the end?" with variable jumps
> **Instant thought:** "Track farthest reachable position!"

### The Mental Model: The Greedy Reach

Track the farthest index you can reach. If current index > farthest, you're stuck!

### Visual Dry Run

```
nums = [2, 3, 1, 1, 4]

i=0: farthest = max(0, 0+2) = 2
i=1: farthest = max(2, 1+3) = 4 (can reach end!)
i=2: farthest = max(4, 2+1) = 4
i=3: farthest = max(4, 3+1) = 4
i=4: reached end!

Answer: true
```

### The Code

```java
public boolean canJump(int[] nums) {
    int farthest = 0;
    
    for (int i = 0; i < nums.length; i++) {
        if (i > farthest) return false;  // Can't reach this index!
        farthest = Math.max(farthest, i + nums[i]);
        if (farthest >= nums.length - 1) return true;
    }
    
    return true;
}
```

### Mind-Map Anchor

```
JUMP GAME
    |
    v
+---------+
| Track farthest   |
| If i > farthest  |
|   -> stuck!      |
| Greedy approach  |
+---------+
```

**Memory phrase:** "Track farthest reach, stuck if i > farthest"

--

## PATTERN 5: Jump Game II (LC 45)

### Pattern Recognition Signal

> **When you see:** "Minimum jumps to reach end"
> **Instant thought:** "BFS-like greedy! Count levels"

### The Mental Model: Level-by-Level Jumps

Each "level" is one jump. Track current level's end and farthest reach.

### Visual Dry Run

```
nums = [2, 3, 1, 1, 4]

Jump 0: at index 0, can reach indices 1,2
  currentEnd = 2, farthest = 2
  
Jump 1: at indices 1,2
  From 1: can reach up to 4
  From 2: can reach up to 3
  farthest = 4, jumps = 1
  
At i=2, i == currentEnd, so jumps++, currentEnd = farthest = 4

Jump 2: reached end!

Answer: 2 jumps
```

### The Code

```java
public int jump(int[] nums) {
    int jumps = 0;
    int currentEnd = 0;
    int farthest = 0;
    
    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);
        
        if (i == currentEnd) {  // Must jump now!
            jumps++;
            currentEnd = farthest;
        }
    }
    
    return jumps;
}
```

### Mind-Map Anchor

```
JUMP GAME II
     |
     v
+----------+
| BFS-like levels    |
| currentEnd = level |
| farthest = next lvl|
| Jump when i==end   |
+----------+
```

**Memory phrase:** "Level ends, must jump, update to farthest"

--

## PATTERN 6: Decode Ways (LC 91)

### Pattern Recognition Signal

> **When you see:** "Count ways to decode/parse string"
> **Instant thought:** "1 digit + 2 digit choices! dp[i] = dp[i-1] + dp[i-2]"

### The Mental Model

At each position, try:
1. Single digit decode (1-9)
2. Two digit decode (10-26)

### Visual Dry Run

```
s = "226"

dp[i] = ways to decode s[0..i-1]

dp[0] = 1 (empty string, 1 way)
dp[1] = 1 (s[0]='2' valid, 1 way: "B")

dp[2]: s[1]='2'
  Single '2' valid: dp[2] += dp[1] = 1
  Two digits '22' valid (<=26): dp[2] += dp[0] = 2
  dp[2] = 2 ("BB", "V")

dp[3]: s[2]='6'
  Single '6' valid: dp[3] += dp[2] = 2
  Two digits '26' valid (<=26): dp[3] += dp[1] = 3
  dp[3] = 3 ("BBF", "VF", "BZ")

Answer: 3
```

### The Code

```java
public int numDecodings(String s) {
    if (s.charAt(0) == '0') return 0;
    
    int n = s.length();
    int prev2 = 1, prev1 = 1;  // dp[0]=1, dp[1]=1
    
    for (int i = 2; i <= n; i++) {
        int curr = 0;
        
        // Single digit
        int oneDigit = s.charAt(i-1) - '0';
        if (oneDigit >= 1) curr += prev1;
        
        // Two digits
        int twoDigits = Integer.parseInt(s.substring(i-2, i));
        if (twoDigits >= 10 && twoDigits <= 26) curr += prev2;
        
        prev2 = prev1;
        prev1 = curr;
    }
    
    return prev1;
}
```

### Mind-Map Anchor

```
DECODE WAYS
     |
     v
+----------+
| 1 digit: 1-9 valid |
| 2 digits: 10-26    |
| dp[i] = sum of both|
| Watch for '0'!     |
+----------+
```

**Memory phrase:** "Single digit + double digit, watch for zeros"

--

## PATTERN 7: Maximum Subarray (LC 53) - Kadane's

### Pattern Recognition Signal

> **When you see:** "Maximum sum contiguous subarray"
> **Instant thought:** "Kadane's! dp[i] = max(nums[i], dp[i-1] + nums[i])"

### The Mental Model

At each position: Start fresh OR extend previous subarray. Track global max.

### Visual Dry Run

```
nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]

dp[i] = max subarray ending at i

dp[0] = -2, maxSum = -2
dp[1] = max(1, -2+1) = 1, maxSum = 1
dp[2] = max(-3, 1-3) = -2, maxSum = 1
dp[3] = max(4, -2+4) = 4, maxSum = 4
dp[4] = max(-1, 4-1) = 3, maxSum = 4
dp[5] = max(2, 3+2) = 5, maxSum = 5
dp[6] = max(1, 5+1) = 6, maxSum = 6
dp[7] = max(-5, 6-5) = 1, maxSum = 6
dp[8] = max(4, 1+4) = 5, maxSum = 6

Answer: 6 (subarray [4,-1,2,1])
```

### The Code

```java
public int maxSubArray(int[] nums) {
    int currentMax = nums[0];
    int globalMax = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        currentMax = Math.max(nums[i], currentMax + nums[i]);
        globalMax = Math.max(globalMax, currentMax);
    }
    
    return globalMax;
}
```

### Mind-Map Anchor

```
KADANE'S ALGORITHM
       |
       v
+----------+
| Start fresh or     |
| extend previous    |
| curr = max(a[i],   |
|   curr + a[i])     |
| Track global max   |
+----------+
```

**Memory phrase:** "Extend or restart, track global max"

--

## PATTERN 8: Maximum Product Subarray (LC 152)

### Pattern Recognition Signal

> **When you see:** "Maximum product subarray"
> **Instant thought:** "Track both max AND min! Negative * negative = positive"

### The Key Insight

A negative number can flip min to max! Track both.

### Visual Dry Run

```
nums = [2, 3, -2, 4]

i=0: maxProd=2, minProd=2, result=2
i=1: maxProd=max(3, 2*3, 2*3)=6, minProd=3, result=6
i=2: maxProd=max(-2, 6*-2, 3*-2)=-2, minProd=min(-2,-12,-6)=-12, result=6
i=3: maxProd=max(4, -2*4, -12*4)=4, minProd=-48, result=6

Answer: 6
```

### The Code

```java
public int maxProduct(int[] nums) {
    int maxProd = nums[0], minProd = nums[0], result = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        int num = nums[i];
        
        // Swap if negative (min becomes max candidate)
        if (num < 0) {
            int temp = maxProd;
            maxProd = minProd;
            minProd = temp;
        }
        
        maxProd = Math.max(num, maxProd * num);
        minProd = Math.min(num, minProd * num);
        result = Math.max(result, maxProd);
    }
    
    return result;
}
```

### Mind-Map Anchor

```
MAX PRODUCT SUBARRAY
        |
        v
+-----------+
| Track MAX and MIN   |
| Negative flips them |
| Swap on negative    |
| Result = global max |
+-----------+
```

**Memory phrase:** "Track both max and min, swap on negative"

--

## PATTERN 9: Perfect Squares (LC 279)

### Pattern Recognition Signal

> **When you see:** "Minimum count to sum to n" with choices
> **Instant thought:** "Unbounded knapsack! dp[i] = min(dp[i], dp[i-sq] + 1)"

### Visual Dry Run

```
n = 12

Squares: 1, 4, 9

dp[0] = 0
dp[1] = dp[0] + 1 = 1 (1)
dp[2] = dp[1] + 1 = 2 (1+1)
dp[3] = dp[2] + 1 = 3 (1+1+1)
dp[4] = min(dp[3]+1, dp[0]+1) = 1 (4)
dp[5] = min(dp[4]+1, dp[1]+1) = 2 (4+1)
...
dp[12] = min(dp[11]+1, dp[8]+1, dp[3]+1) = min(4, 3, 4) = 3 (4+4+4)

Answer: 3
```

### The Code

```java
public int numSquares(int n) {
    int[] dp = new int[n + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j * j <= i; j++) {
            dp[i] = Math.min(dp[i], dp[i - j*j] + 1);
        }
    }
    
    return dp[n];
}
```

### Mind-Map Anchor

```
PERFECT SQUARES
      |
      v
+----------+
| Unbounded knapsack |
| Try all squares<=i |
| dp[i] = min count  |
| dp[i-sq*sq] + 1    |
+----------+
```

**Memory phrase:** "Try all squares, take minimum + 1"


--

# PART 2: KNAPSACK DYNAMIC PROGRAMMING (Week 18)

## The Knapsack Family

```
0/1 KNAPSACK: Each item used at most once
UNBOUNDED KNAPSACK: Each item used unlimited times
BOUNDED KNAPSACK: Each item has a limit
```

## The 0/1 Knapsack Template

```java
// dp[i][w] = max value using items 0..i-1 with capacity w
// Recurrence: dp[i][w] = max(dp[i-1][w], dp[i-1][w-wt[i]] + val[i])

for (int i = 1; i <= n; i++) {
    for (int w = 0; w <= capacity; w++) {
        dp[i][w] = dp[i-1][w];  // Don't take item i
        if (w >= weight[i-1]) {
            dp[i][w] = Math.max(dp[i][w], dp[i-1][w-weight[i-1]] + value[i-1]);
        }
    }
}
```

--

## PATTERN 10: 0/1 Knapsack (Classic)

### Pattern Recognition Signal

> **When you see:** "Maximum value with weight limit, each item once"
> **Instant thought:** "Classic 0/1 knapsack! Take or skip each item"

### Visual Dry Run

```
weights = [1, 2, 3], values = [6, 10, 12], capacity = 5

dp[i][w] = max value with items 0..i-1, capacity w

        w: 0  1  2  3  4  5
item 0:    0  6  6  6  6  6   (weight=1, value=6)
item 1:    0  6 10 16 16 16   (weight=2, value=10)
item 2:    0  6 10 16 18 22   (weight=3, value=12)

dp[3][5] = max(dp[2][5], dp[2][5-3]+12) = max(16, 10+12) = 22

Answer: 22 (items 1 and 2)
```

### The Code

```java
public int knapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[] dp = new int[capacity + 1];  // Space optimized
    
    for (int i = 0; i < n; i++) {
        // Traverse RIGHT to LEFT for 0/1 (each item once)
        for (int w = capacity; w >= weights[i]; w-) {
            dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
        }
    }
    
    return dp[capacity];
}
```

### Mind-Map Anchor

```
0/1 KNAPSACK
     |
     v
+-----------+
| Take or skip item   |
| dp[w] = max of:     |
|   skip: dp[w]       |
|   take: dp[w-wt]+val|
| RIGHT to LEFT!      |
+-----------+
```

**Memory phrase:** "Take or skip, right to left for 0/1"

--

## PATTERN 11: Partition Equal Subset Sum (LC 416)

### Pattern Recognition Signal

> **When you see:** "Can array be partitioned into two equal sum subsets?"
> **Instant thought:** "0/1 Knapsack! Can we make sum/2?"

### The Key Insight

If total sum is odd, impossible. Otherwise, find if subset with sum/2 exists.

### Visual Dry Run

```
nums = [1, 5, 11, 5], sum = 22, target = 11

dp[i] = can we make sum i?

dp[0] = true (empty subset)

Process 1: dp[1] = true
Process 5: dp[6] = true, dp[5] = true
Process 11: dp[11] = true! FOUND!

Answer: true
```

### The Code

```java
public boolean canPartition(int[] nums) {
    int sum = 0;
    for (int num : nums) sum += num;
    
    if (sum % 2 != 0) return false;
    
    int target = sum / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    
    for (int num : nums) {
        for (int j = target; j >= num; j-) {  // Right to left!
            dp[j] = dp[j] || dp[j - num];
        }
    }
    
    return dp[target];
}
```

### Mind-Map Anchor

```
PARTITION EQUAL SUBSET
         |
         v
+-----------+
| Sum must be even     |
| Target = sum / 2     |
| 0/1 knapsack boolean |
| Can we make target?  |
+-----------+
```

**Memory phrase:** "Half the sum, 0/1 knapsack for existence"

--

## PATTERN 12: Target Sum (LC 494)

### Pattern Recognition Signal

> **When you see:** "Assign + or - to each number to reach target"
> **Instant thought:** "Subset sum! P - N = target, P + N = sum"

### The Math Trick

```
Let P = sum of positive, N = sum of negative
P - N = target
P + N = sum
=> 2P = target + sum
=> P = (target + sum) / 2

Find count of subsets with sum P!
```

### The Code

```java
public int findTargetSumWays(int[] nums, int target) {
    int sum = 0;
    for (int num : nums) sum += num;
    
    if ((sum + target) % 2 != 0 || sum < Math.abs(target)) return 0;
    
    int subsetSum = (sum + target) / 2;
    int[] dp = new int[subsetSum + 1];
    dp[0] = 1;
    
    for (int num : nums) {
        for (int j = subsetSum; j >= num; j-) {
            dp[j] += dp[j - num];
        }
    }
    
    return dp[subsetSum];
}
```

### Mind-Map Anchor

```
TARGET SUM
    |
    v
+-----------+
| P - N = target      |
| P = (sum+target)/2  |
| Count subsets = P   |
| 0/1 knapsack count  |
+-----------+
```

**Memory phrase:** "Transform to subset sum, count ways"

--

## PATTERN 13: Coin Change (LC 322) - Unbounded

### Pattern Recognition Signal

> **When you see:** "Minimum coins to make amount" (unlimited coins)
> **Instant thought:** "Unbounded knapsack! dp[i] = min(dp[i], dp[i-coin] + 1)"

### Visual Dry Run

```
coins = [1, 2, 5], amount = 11

dp[i] = min coins for amount i

dp[0] = 0
dp[1] = dp[0] + 1 = 1 (one 1-coin)
dp[2] = min(dp[1]+1, dp[0]+1) = 1 (one 2-coin)
dp[3] = min(dp[2]+1, dp[1]+1) = 2
dp[4] = min(dp[3]+1, dp[2]+1) = 2
dp[5] = min(dp[4]+1, dp[3]+1, dp[0]+1) = 1 (one 5-coin)
...
dp[11] = 3 (5+5+1)

Answer: 3
```

### The Code

```java
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, amount + 1);  // Impossible value
    dp[0] = 0;
    
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (coin <= i) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    
    return dp[amount] > amount ? -1 : dp[amount];
}
```

### Mind-Map Anchor

```
COIN CHANGE (MIN)
       |
       v
+----------+
| Unbounded knapsack |
| dp[i] = min coins  |
| Try all coins <= i |
| LEFT to RIGHT!     |
+----------+
```

**Memory phrase:** "Unbounded = left to right, minimize coins"

--

## PATTERN 14: Coin Change II (LC 518) - Count Ways

### Pattern Recognition Signal

> **When you see:** "Count ways to make amount" (unlimited coins)
> **Instant thought:** "Unbounded knapsack count! dp[i] += dp[i-coin]"

### The Key Difference from Coin Change I

- Coin Change I: Minimize count
- Coin Change II: Count combinations (order doesn't matter!)

### Visual Dry Run

```
coins = [1, 2, 5], amount = 5

dp[i] = number of ways to make amount i

Process coin 1: dp = [1,1,1,1,1,1]
Process coin 2: dp = [1,1,2,2,3,3]
Process coin 5: dp = [1,1,2,2,3,4]

Answer: 4 ways (5, 2+2+1, 2+1+1+1, 1+1+1+1+1)
```

### The Code

```java
public int change(int amount, int[] coins) {
    int[] dp = new int[amount + 1];
    dp[0] = 1;  // One way to make 0
    
    // Process coin by coin to avoid counting permutations
    for (int coin : coins) {
        for (int i = coin; i <= amount; i++) {  // Left to right!
            dp[i] += dp[i - coin];
        }
    }
    
    return dp[amount];
}
```

### Mind-Map Anchor

```
COIN CHANGE II (COUNT)
         |
         v
+-----------+
| Count combinations   |
| NOT permutations     |
| Outer: coins         |
| Inner: amounts (L->R)|
+-----------+
```

**Memory phrase:** "Coin-by-coin outer loop avoids permutation counting"

--

## PATTERN 15: Ones and Zeroes (LC 474)

### Pattern Recognition Signal

> **When you see:** "Maximum items with two constraints"
> **Instant thought:** "2D 0/1 knapsack! dp[m][n] with two capacities"

### The Code

```java
public int findMaxForm(String[] strs, int m, int n) {
    int[][] dp = new int[m + 1][n + 1];
    
    for (String s : strs) {
        int zeros = 0, ones = 0;
        for (char c : s.toCharArray()) {
            if (c == '0') zeros++;
            else ones++;
        }
        
        // 0/1 knapsack: right to left in both dimensions
        for (int i = m; i >= zeros; i-) {
            for (int j = n; j >= ones; j-) {
                dp[i][j] = Math.max(dp[i][j], dp[i-zeros][j-ones] + 1);
            }
        }
    }
    
    return dp[m][n];
}
```

### Mind-Map Anchor

```
ONES AND ZEROES
      |
      v
+-----------+
| 2D 0/1 knapsack     |
| Two capacities: m,n |
| Count 0s and 1s     |
| Right to left both  |
+-----------+
```

**Memory phrase:** "Two constraints = 2D knapsack"


--

# PART 3: 2D DYNAMIC PROGRAMMING (Week 19)

## The 2D DP Template

```java
// dp[i][j] = answer for subproblem with parameters i and j
// Common patterns:
//   - Grid: dp[row][col]
//   - Two strings: dp[i][j] for s1[0..i-1] and s2[0..j-1]
//   - Intervals: dp[i][j] for range [i..j]
```

--

## PATTERN 16: Unique Paths (LC 62)

### Pattern Recognition Signal

> **When you see:** "Count paths in grid, only right/down"
> **Instant thought:** "dp[i][j] = dp[i-1][j] + dp[i][j-1]"

### Visual Dry Run

```
m = 3, n = 3

dp[i][j] = paths to reach (i,j)

    0   1   2
0   1   1   1
1   1   2   3
2   1   3   6

dp[2][2] = dp[1][2] + dp[2][1] = 3 + 3 = 6

Answer: 6 paths
```

### The Code

```java
public int uniquePaths(int m, int n) {
    int[] dp = new int[n];
    Arrays.fill(dp, 1);  // First row all 1s
    
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[j] += dp[j-1];  // From above + from left
        }
    }
    
    return dp[n-1];
}
```

### Mind-Map Anchor

```
UNIQUE PATHS
     |
     v
+---------+
| dp[i][j] = paths |
| = above + left   |
| First row/col = 1|
| O(n) space ok    |
+---------+
```

**Memory phrase:** "Paths = from above + from left"

--

## PATTERN 17: Minimum Path Sum (LC 64)

### Pattern Recognition Signal

> **When you see:** "Minimum cost path in grid"
> **Instant thought:** "dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])"

### Visual Dry Run

```
grid = [[1,3,1],
        [1,5,1],
        [4,2,1]]

dp[i][j] = min cost to reach (i,j)

    0   1   2
0   1   4   5
1   2   7   6
2   6   8   7

Answer: 7 (path: 1->3->1->1->1)
```

### The Code

```java
public int minPathSum(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (i == 0 && j == 0) continue;
            else if (i == 0) grid[i][j] += grid[i][j-1];
            else if (j == 0) grid[i][j] += grid[i-1][j];
            else grid[i][j] += Math.min(grid[i-1][j], grid[i][j-1]);
        }
    }
    
    return grid[m-1][n-1];
}
```

### Mind-Map Anchor

```
MIN PATH SUM
     |
     v
+----------+
| dp[i][j] = cost +  |
| min(above, left)   |
| In-place possible  |
+----------+
```

**Memory phrase:** "Current + min of above and left"

--

## PATTERN 18: Longest Common Subsequence (LC 1143)

### Pattern Recognition Signal

> **When you see:** "Longest common subsequence of two strings"
> **Instant thought:** "Classic 2D DP! Match = diagonal+1, else max(left, up)"

### Visual Dry Run

```
text1 = "abcde", text2 = "ace"

dp[i][j] = LCS of text1[0..i-1] and text2[0..j-1]

      ""  a  c  e
  ""   0  0  0  0
  a    0  1  1  1
  b    0  1  1  1
  c    0  1  2  2
  d    0  1  2  2
  e    0  1  2  3

dp[5][3] = 3 (LCS = "ace")
```

### The Code

```java
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i-1) == text2.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1] + 1;  // Match! Diagonal + 1
            } else {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);  // Max of skip either
            }
        }
    }
    
    return dp[m][n];
}
```

### Mind-Map Anchor

```
LCS
 |
 v
+-----------+
| Match: diagonal + 1 |
| No match: max(↑, ←) |
| dp[m][n] = answer   |
+-----------+
```

**Memory phrase:** "Match = diagonal+1, else max of up and left"

--

## PATTERN 19: Edit Distance (LC 72)

### Pattern Recognition Signal

> **When you see:** "Minimum operations to transform string A to B"
> **Instant thought:** "Classic DP! Insert, delete, replace operations"

### The Three Operations

```
Insert:  dp[i][j-1] + 1  (insert char to match)
Delete:  dp[i-1][j] + 1  (delete char from word1)
Replace: dp[i-1][j-1] + 1 (replace char)
Match:   dp[i-1][j-1]    (no operation needed)
```

### Visual Dry Run

```
word1 = "horse", word2 = "ros"

      ""  r  o  s
  ""   0  1  2  3
  h    1  1  2  3
  o    2  2  1  2
  r    3  2  2  2
  s    4  3  3  2
  e    5  4  4  3

dp[5][3] = 3 (horse -> rorse -> rose -> ros)
```

### The Code

```java
public int minDistance(String word1, String word2) {
    int m = word1.length(), n = word2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    // Base cases
    for (int i = 0; i <= m; i++) dp[i][0] = i;  // Delete all
    for (int j = 0; j <= n; j++) dp[0][j] = j;  // Insert all
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1.charAt(i-1) == word2.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1];  // Match, no op
            } else {
                dp[i][j] = 1 + Math.min(dp[i-1][j-1],    // Replace
                               Math.min(dp[i-1][j],      // Delete
                                       dp[i][j-1]));     // Insert
            }
        }
    }
    
    return dp[m][n];
}
```

### Mind-Map Anchor

```
EDIT DISTANCE
      |
      v
+-----------+
| Match: diagonal (0)  |
| Replace: diag + 1    |
| Delete: up + 1       |
| Insert: left + 1     |
+-----------+
```

**Memory phrase:** "Match=free, else min of replace/delete/insert + 1"

--

## PATTERN 20: Longest Palindromic Subsequence (LC 516)

### Pattern Recognition Signal

> **When you see:** "Longest palindromic subsequence"
> **Instant thought:** "LCS of string with its reverse! Or interval DP"

### The Code (LCS approach)

```java
public int longestPalindromeSubseq(String s) {
    String rev = new StringBuilder(s).reverse().toString();
    return longestCommonSubsequence(s, rev);
}
```

### The Code (Interval DP approach)

```java
public int longestPalindromeSubseq(String s) {
    int n = s.length();
    int[][] dp = new int[n][n];
    
    // Base: single chars are palindromes of length 1
    for (int i = 0; i < n; i++) dp[i][i] = 1;
    
    // Fill diagonally (increasing length)
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            if (s.charAt(i) == s.charAt(j)) {
                dp[i][j] = dp[i+1][j-1] + 2;
            } else {
                dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
            }
        }
    }
    
    return dp[0][n-1];
}
```

### Mind-Map Anchor

```
LONGEST PALINDROME SUBSEQ
           |
           v
+------------+
| Method 1: LCS(s, rev)  |
| Method 2: Interval DP  |
| Match ends: inner + 2  |
| No match: max(shrink)  |
+------------+
```

**Memory phrase:** "LCS with reverse, or interval DP"

--

## PATTERN 21: Maximal Square (LC 221)

### Pattern Recognition Signal

> **When you see:** "Largest square of 1s in matrix"
> **Instant thought:** "dp[i][j] = min(left, up, diagonal) + 1"

### Visual Dry Run

```
matrix:
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0

dp[i][j] = side length of largest square ending at (i,j)

1 0 1 0 0
1 0 1 1 1
1 1 1 2 2
1 0 0 1 0

Max = 2, Area = 4
```

### The Code

```java
public int maximalSquare(char[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    int[][] dp = new int[m + 1][n + 1];
    int maxSide = 0;
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (matrix[i-1][j-1] == '1') {
                dp[i][j] = Math.min(dp[i-1][j-1], 
                           Math.min(dp[i-1][j], dp[i][j-1])) + 1;
                maxSide = Math.max(maxSide, dp[i][j]);
            }
        }
    }
    
    return maxSide * maxSide;
}
```

### Mind-Map Anchor

```
MAXIMAL SQUARE
      |
      v
+-----------+
| dp[i][j] = side len |
| = min(↖,↑,←) + 1    |
| Only if cell = '1'  |
| Area = side * side  |
+-----------+
```

**Memory phrase:** "Square limited by smallest neighbor + 1"


--

# PART 4: LIS PATTERN (Week 20)

## PATTERN 22: Longest Increasing Subsequence (LC 300)

### Pattern Recognition Signal

> **When you see:** "Longest increasing subsequence"
> **Instant thought:** "O(n log n) binary search with tails array!"

### Visual Dry Run

```
nums = [10, 9, 2, 5, 3, 7, 101, 18]

tails = []
10: tails = [10]
9:  tails = [9]
2:  tails = [2]
5:  tails = [2, 5]
3:  tails = [2, 3]
7:  tails = [2, 3, 7]
101: tails = [2, 3, 7, 101]
18: tails = [2, 3, 7, 18]

Answer: 4
```

### The Code

```java
public int lengthOfLIS(int[] nums) {
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

### Mind-Map Anchor

```
LIS
 |
 v
+------------+
| tails[i] = smallest    |
| tail of LIS length i+1 |
| Binary search position |
| Extend or replace      |
+------------+
```

**Memory phrase:** "Tails array, binary search, extend or replace"

--

## PATTERN 23: Russian Doll Envelopes (LC 354)

### Pattern Recognition Signal

> **When you see:** "Fit items inside each other" with 2D
> **Instant thought:** "Sort by width ASC, same width height DESC, LIS on heights!"

### The Code

```java
public int maxEnvelopes(int[][] envelopes) {
    Arrays.sort(envelopes, (a, b) -> 
        a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
    
    List<Integer> tails = new ArrayList<>();
    for (int[] env : envelopes) {
        int h = env[1];
        int pos = Collections.binarySearch(tails, h);
        if (pos < 0) pos = -(pos + 1);
        if (pos == tails.size()) tails.add(h);
        else tails.set(pos, h);
    }
    return tails.size();
}
```

**Memory phrase:** "Sort width ASC, same width height DESC, LIS on heights"


--

# PART 5: INTERVAL DP (Week 21)

## The Interval DP Template

```java
// dp[i][j] = answer for interval [i..j]
// Fill by increasing length
// Recurrence often involves trying all split points k

for (int len = 2; len <= n; len++) {
    for (int i = 0; i <= n - len; i++) {
        int j = i + len - 1;
        for (int k = i; k < j; k++) {
            dp[i][j] = optimize(dp[i][k], dp[k+1][j], cost);
        }
    }
}
```

--

## PATTERN 24: Burst Balloons (LC 312)

### Pattern Recognition Signal

> **When you see:** "Burst items, score depends on neighbors"
> **Instant thought:** "Interval DP! Think of LAST balloon to burst in range"

### The Key Insight

Instead of thinking which balloon to burst FIRST, think which to burst LAST in range [i,j]. When it's the last, its neighbors are the boundaries!

### Visual Dry Run

```
nums = [3, 1, 5, 8] -> with boundaries: [1, 3, 1, 5, 8, 1]

dp[i][j] = max coins bursting balloons in (i,j) exclusive

For range (0,5) with boundaries 1 and 1:
  Try k=1 last: 1*3*1 + dp[0][1] + dp[1][5]
  Try k=2 last: 1*1*1 + dp[0][2] + dp[2][5]
  Try k=3 last: 1*5*1 + dp[0][3] + dp[3][5]
  Try k=4 last: 1*8*1 + dp[0][4] + dp[4][5]

Answer: 167
```

### The Code

```java
public int maxCoins(int[] nums) {
    int n = nums.length;
    int[] arr = new int[n + 2];
    arr[0] = arr[n + 1] = 1;
    for (int i = 0; i < n; i++) arr[i + 1] = nums[i];
    
    int[][] dp = new int[n + 2][n + 2];
    
    for (int len = 1; len <= n; len++) {
        for (int i = 1; i <= n - len + 1; i++) {
            int j = i + len - 1;
            for (int k = i; k <= j; k++) {
                // k is the LAST balloon to burst in [i,j]
                int coins = arr[i-1] * arr[k] * arr[j+1];
                coins += dp[i][k-1] + dp[k+1][j];
                dp[i][j] = Math.max(dp[i][j], coins);
            }
        }
    }
    
    return dp[1][n];
}
```

### Mind-Map Anchor

```
BURST BALLOONS
      |
      v
+-----------+
| Think LAST to burst  |
| Add boundary 1s      |
| dp[i][j] = max coins |
| Try all k as last    |
+-----------+
```

**Memory phrase:** "Last balloon to burst, boundaries become neighbors"

--

## PATTERN 25: Palindrome Partitioning II (LC 132)

### Pattern Recognition Signal

> **When you see:** "Minimum cuts to make all palindromes"
> **Instant thought:** "Precompute palindromes + DP for min cuts"

### The Code

```java
public int minCut(String s) {
    int n = s.length();
    boolean[][] isPalin = new boolean[n][n];
    
    // Precompute palindromes
    for (int i = n - 1; i >= 0; i-) {
        for (int j = i; j < n; j++) {
            if (s.charAt(i) == s.charAt(j) && (j - i < 2 || isPalin[i+1][j-1])) {
                isPalin[i][j] = true;
            }
        }
    }
    
    // dp[i] = min cuts for s[0..i]
    int[] dp = new int[n];
    
    for (int i = 0; i < n; i++) {
        if (isPalin[0][i]) {
            dp[i] = 0;  // Whole prefix is palindrome
        } else {
            dp[i] = i;  // Worst case: cut after each char
            for (int j = 1; j <= i; j++) {
                if (isPalin[j][i]) {
                    dp[i] = Math.min(dp[i], dp[j-1] + 1);
                }
            }
        }
    }
    
    return dp[n-1];
}
```

### Mind-Map Anchor

```
PALINDROME PARTITION II
          |
          v
+------------+
| Precompute isPalin[][] |
| dp[i] = min cuts 0..i  |
| If [j..i] palindrome   |
| dp[i] = dp[j-1] + 1    |
+------------+
```

**Memory phrase:** "Precompute palindromes, then min cuts DP"

--

## PATTERN 26: Stone Game (LC 877)

### Pattern Recognition Signal

> **When you see:** "Two players, take from ends, maximize score"
> **Instant thought:** "Interval DP! dp[i][j] = max advantage"

### The Code

```java
public boolean stoneGame(int[] piles) {
    int n = piles.length;
    int[][] dp = new int[n][n];
    
    // dp[i][j] = max(your score - opponent score) for piles[i..j]
    for (int i = 0; i < n; i++) dp[i][i] = piles[i];
    
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            dp[i][j] = Math.max(
                piles[i] - dp[i+1][j],  // Take left
                piles[j] - dp[i][j-1]   // Take right
            );
        }
    }
    
    return dp[0][n-1] > 0;
}
```

### Mind-Map Anchor

```
STONE GAME
    |
    v
+-----------+
| dp[i][j] = advantage|
| Take left or right  |
| Subtract opponent's |
| best response       |
+-----------+
```

**Memory phrase:** "Your pick minus opponent's best = advantage"


--

# PART 6: BITMASK DP + STATE MACHINE DP (Week 22)

## Bitmask DP Concept

Use bits to represent which items are used/visited. If n items, state is 0 to 2^n - 1.

```java
// Check if item i is in mask
boolean used = (mask & (1 << i)) != 0;

// Add item i to mask
int newMask = mask | (1 << i);

// Remove item i from mask
int newMask = mask & ~(1 << i);

// Count set bits
int count = Integer.bitCount(mask);
```

--

## PATTERN 27: Partition to K Equal Sum Subsets (LC 698) - Bitmask

### Pattern Recognition Signal

> **When you see:** "Partition into K groups" with small n
> **Instant thought:** "Bitmask DP! Track which elements used"

### The Code

```java
public boolean canPartitionKSubsets(int[] nums, int k) {
    int sum = 0;
    for (int num : nums) sum += num;
    if (sum % k != 0) return false;
    
    int target = sum / k;
    int n = nums.length;
    int[] dp = new int[1 << n];
    Arrays.fill(dp, -1);
    dp[0] = 0;
    
    for (int mask = 0; mask < (1 << n); mask++) {
        if (dp[mask] == -1) continue;
        
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) continue;  // Already used
            if (dp[mask] + nums[i] > target) continue;  // Exceeds target
            
            int newMask = mask | (1 << i);
            dp[newMask] = (dp[mask] + nums[i]) % target;
        }
    }
    
    return dp[(1 << n) - 1] == 0;
}
```

### Mind-Map Anchor

```
PARTITION K SUBSETS (BITMASK)
             |
             v
+------------+
| mask = which nums used |
| dp[mask] = current sum |
|   mod target           |
| All used & sum=0 = yes |
+------------+
```

**Memory phrase:** "Bitmask tracks used, dp tracks sum mod target"

--

## PATTERN 28: Traveling Salesman Problem (TSP)

### Pattern Recognition Signal

> **When you see:** "Visit all cities exactly once, return to start, minimize cost"
> **Instant thought:** "Bitmask DP! dp[mask][i] = min cost to visit mask cities ending at i"

### Visual Dry Run

```
Cities: 0, 1, 2, 3
dist[i][j] = distance from i to j

dp[mask][i] = min cost to visit cities in mask, ending at i

dp[0001][0] = 0 (start at city 0)
dp[0011][1] = dist[0][1]
dp[0101][2] = dist[0][2]
...
dp[1111][i] = min cost visiting all, ending at i

Answer = min(dp[1111][i] + dist[i][0]) for all i
```

### The Code

```java
public int tsp(int[][] dist) {
    int n = dist.length;
    int[][] dp = new int[1 << n][n];
    for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
    
    dp[1][0] = 0;  // Start at city 0
    
    for (int mask = 1; mask < (1 << n); mask++) {
        for (int last = 0; last < n; last++) {
            if ((mask & (1 << last)) == 0) continue;
            if (dp[mask][last] == Integer.MAX_VALUE) continue;
            
            for (int next = 0; next < n; next++) {
                if ((mask & (1 << next)) != 0) continue;
                
                int newMask = mask | (1 << next);
                dp[newMask][next] = Math.min(
                    dp[newMask][next],
                    dp[mask][last] + dist[last][next]
                );
            }
        }
    }
    
    int fullMask = (1 << n) - 1;
    int ans = Integer.MAX_VALUE;
    for (int i = 0; i < n; i++) {
        if (dp[fullMask][i] != Integer.MAX_VALUE) {
            ans = Math.min(ans, dp[fullMask][i] + dist[i][0]);
        }
    }
    return ans;
}
```

### Mind-Map Anchor

```
TSP (BITMASK DP)
      |
      v
+------------+
| dp[mask][i] = min cost |
| mask = visited cities  |
| i = current city       |
| Add return to start    |
+------------+
```

**Memory phrase:** "Mask = visited, track last city, add return cost"

--

# STATE MACHINE DP: Stock Problems

## The State Machine Concept

Model problem as states with transitions. For stocks:
- State: (day, holding stock?, cooldown?, transactions left)
- Transitions: buy, sell, rest

--

## PATTERN 29: Best Time to Buy and Sell Stock (LC 121)

### Pattern Recognition Signal

> **When you see:** "One transaction, max profit"
> **Instant thought:** "Track min price so far, max profit = price - minPrice"

### The Code

```java
public int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;
    
    for (int price : prices) {
        minPrice = Math.min(minPrice, price);
        maxProfit = Math.max(maxProfit, price - minPrice);
    }
    
    return maxProfit;
}
```

**Memory phrase:** "Track min, profit = current - min"

--

## PATTERN 30: Best Time to Buy and Sell Stock II (LC 122)

### Pattern Recognition Signal

> **When you see:** "Unlimited transactions"
> **Instant thought:** "Sum all positive differences!"

### The Code

```java
public int maxProfit(int[] prices) {
    int profit = 0;
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i-1]) {
            profit += prices[i] - prices[i-1];
        }
    }
    return profit;
}
```

**Memory phrase:** "Add all upward slopes"

--

## PATTERN 31: Best Time to Buy and Sell Stock III (LC 123)

### Pattern Recognition Signal

> **When you see:** "At most 2 transactions"
> **Instant thought:** "State machine with 4 states!"

### The States

```
buy1:  max profit after 1st buy
sell1: max profit after 1st sell
buy2:  max profit after 2nd buy
sell2: max profit after 2nd sell
```

### The Code

```java
public int maxProfit(int[] prices) {
    int buy1 = Integer.MIN_VALUE, sell1 = 0;
    int buy2 = Integer.MIN_VALUE, sell2 = 0;
    
    for (int price : prices) {
        buy1 = Math.max(buy1, -price);           // Buy 1st
        sell1 = Math.max(sell1, buy1 + price);   // Sell 1st
        buy2 = Math.max(buy2, sell1 - price);    // Buy 2nd
        sell2 = Math.max(sell2, buy2 + price);   // Sell 2nd
    }
    
    return sell2;
}
```

### Mind-Map Anchor

```
STOCK III (2 TRANSACTIONS)
           |
           v
+------------+
| 4 states: b1,s1,b2,s2  |
| buy1 = max(-price)     |
| sell1 = buy1 + price   |
| buy2 = sell1 - price   |
| sell2 = buy2 + price   |
+------------+
```

**Memory phrase:** "Chain: buy1 -> sell1 -> buy2 -> sell2"

--

## PATTERN 32: Best Time to Buy and Sell Stock IV (LC 188)

### Pattern Recognition Signal

> **When you see:** "At most k transactions"
> **Instant thought:** "Generalize Stock III with k states!"

### The Code

```java
public int maxProfit(int k, int[] prices) {
    if (prices.length == 0) return 0;
    
    // If k >= n/2, unlimited transactions
    if (k >= prices.length / 2) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) profit += prices[i] - prices[i-1];
        }
        return profit;
    }
    
    int[] buy = new int[k + 1];
    int[] sell = new int[k + 1];
    Arrays.fill(buy, Integer.MIN_VALUE);
    
    for (int price : prices) {
        for (int i = 1; i <= k; i++) {
            buy[i] = Math.max(buy[i], sell[i-1] - price);
            sell[i] = Math.max(sell[i], buy[i] + price);
        }
    }
    
    return sell[k];
}
```

### Mind-Map Anchor

```
STOCK IV (K TRANSACTIONS)
           |
           v
+------------+
| buy[i] = after i-th buy|
| sell[i] = after i-th   |
| sell                   |
| Chain transitions      |
+------------+
```

**Memory phrase:** "K buy-sell pairs, chain them"

--

## PATTERN 33: Best Time to Buy and Sell Stock with Cooldown (LC 309)

### Pattern Recognition Signal

> **When you see:** "Must wait 1 day after selling"
> **Instant thought:** "3 states: hold, sold, rest!"

### The States

```
hold: holding stock
sold: just sold (cooldown next)
rest: not holding, not in cooldown
```

### The Code

```java
public int maxProfit(int[] prices) {
    int hold = Integer.MIN_VALUE;  // Holding stock
    int sold = 0;                   // Just sold
    int rest = 0;                   // Resting
    
    for (int price : prices) {
        int prevSold = sold;
        sold = hold + price;        // Sell: hold -> sold
        hold = Math.max(hold, rest - price);  // Buy: rest -> hold
        rest = Math.max(rest, prevSold);      // Rest: sold -> rest
    }
    
    return Math.max(sold, rest);
}
```

### Mind-Map Anchor

```
STOCK WITH COOLDOWN
        |
        v
+-----------+
| hold: have stock     |
| sold: just sold      |
| rest: can buy        |
| sold -> rest (cool)  |
+-----------+
```

**Memory phrase:** "Three states: hold, sold, rest. Sold must rest."

--

# QUICK REFERENCE: DP Pattern Categories

| Category | Patterns | Key Insight |
|-----|-----|-------|
| 1D DP | Climbing, Robber, Jump | dp[i] depends on dp[i-1], dp[i-2] |
| Knapsack | Partition, Coins, Target | Take or skip, 0/1 vs unbounded |
| 2D DP | Grid, LCS, Edit | dp[i][j] for two parameters |
| LIS | LIS, Envelopes | Tails array + binary search |
| Interval | Balloons, Palindrome | Think of last operation in range |
| Bitmask | TSP, Partition K | Mask = which items used |
| State Machine | Stocks | States + transitions |

--

*End of Dynamic Programming Patterns Deep Dive*


--

# PART 7: ADDITIONAL HIGH-PRIORITY PATTERNS

## PATTERN 34: Word Break (LC 139)

### Pattern Recognition Signal

> **When you see:** "Can string be segmented into dictionary words?"
> **Instant thought:** "dp[i] = can we form s[0..i-1] from dictionary?"

### Visual Dry Run

```
s = "leetcode", wordDict = ["leet", "code"]

dp[i] = can we segment s[0..i-1]?

dp[0] = true (empty string)
dp[1] = false ("l" not in dict)
dp[2] = false
dp[3] = false
dp[4] = true (dp[0] && "leet" in dict)
dp[5] = false
dp[6] = false
dp[7] = false
dp[8] = true (dp[4] && "code" in dict)

Answer: true
```

### The Code

```java
public boolean wordBreak(String s, List<String> wordDict) {
    Set<String> dict = new HashSet<>(wordDict);
    int n = s.length();
    boolean[] dp = new boolean[n + 1];
    dp[0] = true;
    
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j < i; j++) {
            if (dp[j] && dict.contains(s.substring(j, i))) {
                dp[i] = true;
                break;
            }
        }
    }
    
    return dp[n];
}
```

### Mind-Map Anchor

```
WORD BREAK
    |
    v
+-----------+
| dp[i] = segmentable |
| Try all splits j    |
| dp[j] && s[j..i] in |
| dictionary          |
+-----------+
```

**Memory phrase:** "Try all splits, check prefix valid AND suffix in dict"

--

## PATTERN 35: Regular Expression Matching (LC 10)

### Pattern Recognition Signal

> **When you see:** "Match string with . and * wildcards"
> **Instant thought:** "2D DP! Handle . (any char) and * (zero or more)"

### The Key Cases

```
1. p[j] == s[i] or p[j] == '.'  -> dp[i][j] = dp[i-1][j-1]
2. p[j] == '*':
   - Zero occurrences: dp[i][j] = dp[i][j-2]
   - One+ occurrences: dp[i][j] = dp[i-1][j] (if p[j-1] matches s[i])
```

### Visual Dry Run

```
s = "aab", p = "c*a*b"

      ""  c  *  a  *  b
  ""   T  F  T  F  T  F
  a    F  F  F  T  T  F
  a    F  F  F  F  T  F
  b    F  F  F  F  F  T

Answer: true
```

### The Code

```java
public boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;
    
    // Handle patterns like a*, a*b*, etc. matching empty string
    for (int j = 2; j <= n; j += 2) {
        if (p.charAt(j - 1) == '*') {
            dp[0][j] = dp[0][j - 2];
        }
    }
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            char sc = s.charAt(i - 1);
            char pc = p.charAt(j - 1);
            
            if (pc == '.' || pc == sc) {
                dp[i][j] = dp[i - 1][j - 1];
            } else if (pc == '*') {
                char prev = p.charAt(j - 2);
                // Zero occurrences
                dp[i][j] = dp[i][j - 2];
                // One or more occurrences
                if (prev == '.' || prev == sc) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j];
                }
            }
        }
    }
    
    return dp[m][n];
}
```

### Mind-Map Anchor

```
REGEX MATCHING
      |
      v
+------------+
| . = any single char    |
| * = zero or more of    |
|     previous char      |
| dp[i][j] = match?      |
| * : skip or consume    |
+------------+
```

**Memory phrase:** "Star means zero (skip 2) or more (consume and stay)"

--

## PATTERN 36: Wildcard Matching (LC 44)

### Pattern Recognition Signal

> **When you see:** "Match with ? and * wildcards"
> **Instant thought:** "Similar to regex but * matches ANY sequence!"

### The Difference from Regex

```
Regex *:  matches zero or more of PREVIOUS character
Wildcard *: matches ANY sequence (including empty)
```

### Visual Dry Run

```
s = "adceb", p = "*a*b"

      ""  *  a  *  b
  ""   T  T  F  F  F
  a    F  T  T  T  F
  d    F  T  F  T  F
  c    F  T  F  T  F
  e    F  T  F  T  F
  b    F  T  F  T  T

* matches empty or any sequence
dp[5][4] = true

Answer: true
```

### Mind-Map Anchor

```
WILDCARD MATCHING
       |
       v
+-----------+
| ? = any single char  |
| * = any sequence     |
| * : empty OR consume |
| dp[i][j-1] || dp[i-1][j]|
+-----------+
```

### The Code

```java
public boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;
    
    // * can match empty string
    for (int j = 1; j <= n; j++) {
        if (p.charAt(j - 1) == '*') {
            dp[0][j] = dp[0][j - 1];
        }
    }
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            char sc = s.charAt(i - 1);
            char pc = p.charAt(j - 1);
            
            if (pc == '?' || pc == sc) {
                dp[i][j] = dp[i - 1][j - 1];
            } else if (pc == '*') {
                // * matches empty (dp[i][j-1]) OR any char (dp[i-1][j])
                dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
            }
        }
    }
    
    return dp[m][n];
}
```

### Mind-Map Anchor

```
WILDCARD MATCHING
       |
       v
+-----------+
| ? = any single char  |
| * = any sequence     |
| * : empty OR consume |
| dp[i][j-1] || dp[i-1][j]|
+-----------+
```

**Memory phrase:** "Star = empty (left) or consume one (up)"

--

## PATTERN 37: Distinct Subsequences (LC 115)

### Pattern Recognition Signal

> **When you see:** "Count subsequences of s that equal t"
> **Instant thought:** "2D DP! Match = diagonal + skip, no match = skip only"

### Visual Dry Run

```
s = "rabbbit", t = "rabbit"

      ""  r  a  b  b  i  t
  ""   1  0  0  0  0  0  0
  r    1  1  0  0  0  0  0
  a    1  1  1  0  0  0  0
  b    1  1  1  1  0  0  0
  b    1  1  1  2  1  0  0
  b    1  1  1  3  3  0  0
  i    1  1  1  3  3  3  0
  t    1  1  1  3  3  3  3

Answer: 3
```

### The Code

```java
public int numDistinct(String s, String t) {
    int m = s.length(), n = t.length();
    long[][] dp = new long[m + 1][n + 1];
    
    // Empty t can be formed from any prefix of s
    for (int i = 0; i <= m; i++) dp[i][0] = 1;
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            dp[i][j] = dp[i - 1][j];  // Skip s[i-1]
            if (s.charAt(i - 1) == t.charAt(j - 1)) {
                dp[i][j] += dp[i - 1][j - 1];  // Use s[i-1]
            }
        }
    }
    
    return (int) dp[m][n];
}
```

### Mind-Map Anchor

```
DISTINCT SUBSEQUENCES
         |
         v
+------------+
| dp[i][j] = count       |
| Always: skip s[i]      |
| Match: + use s[i]      |
| dp[i-1][j] + dp[i-1][j-1]|
+------------+
```

**Memory phrase:** "Always can skip, match adds diagonal"

--

## PATTERN 38: Interleaving String (LC 97)

### Pattern Recognition Signal

> **When you see:** "Can s3 be formed by interleaving s1 and s2?"
> **Instant thought:** "2D DP! dp[i][j] = can form s3[0..i+j-1] from s1[0..i-1] and s2[0..j-1]"

### Visual Dry Run

```
s1 = "aab", s2 = "axy", s3 = "aaxaby"

dp[i][j] = can form s3[0..i+j-1] from s1[0..i-1] and s2[0..j-1]

      ""  a  x  y
  ""   T  T  F  F
  a    T  T  T  F
  a    T  T  T  T
  b    F  F  F  T

dp[3][3] = true (s3 = "aaxaby" can be formed)

Trace: a(s1) + a(s2) + x(s2) + a(s1) + b(s1) + y(s2)
```

### Mind-Map Anchor

```
INTERLEAVING STRING
        |
        v
+------------+
| dp[i][j] = can form    |
| s3[0..i+j-1]           |
| From s1[0..i-1] and    |
| s2[0..j-1]             |
| Take from s1 OR s2     |
+------------+
```

### The Code

```java
public boolean isInterleave(String s1, String s2, String s3) {
    int m = s1.length(), n = s2.length();
    if (m + n != s3.length()) return false;
    
    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;
    
    // First row: only s2
    for (int j = 1; j <= n; j++) {
        dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
    }
    
    // First col: only s1
    for (int i = 1; i <= m; i++) {
        dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
    }
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            int k = i + j - 1;  // Index in s3
            dp[i][j] = (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(k)) ||
                       (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(k));
        }
    }
    
    return dp[m][n];
}
```

### Mind-Map Anchor

```
INTERLEAVING STRING
        |
        v
+------------+
| dp[i][j] = can form    |
| s3[0..i+j-1]           |
| From s1[0..i-1] and    |
| s2[0..j-1]             |
| Take from s1 OR s2     |
+------------+
```

**Memory phrase:** "Each char from s1 or s2, must match s3"

--

## PATTERN 39: Triangle (LC 120)

### Pattern Recognition Signal

> **When you see:** "Minimum path sum in triangle"
> **Instant thought:** "Bottom-up DP! dp[j] = min(dp[j], dp[j+1]) + triangle[i][j]"

### Visual Dry Run

```
Triangle:
   2
  3 4
 6 5 7
4 1 8 3

Bottom-up:
Row 3: [4, 1, 8, 3]
Row 2: [6+1, 5+1, 7+3] = [7, 6, 10]
Row 1: [3+6, 4+6] = [9, 10]
Row 0: [2+9] = [11]

Answer: 11
```

### The Code

```java
public int minimumTotal(List<List<Integer>> triangle) {
    int n = triangle.size();
    int[] dp = new int[n + 1];
    
    // Bottom-up
    for (int i = n - 1; i >= 0; i-) {
        for (int j = 0; j <= i; j++) {
            dp[j] = triangle.get(i).get(j) + Math.min(dp[j], dp[j + 1]);
        }
    }
    
    return dp[0];
}
```

### Mind-Map Anchor

```
TRIANGLE
   |
   v
+----------+
| Bottom-up DP       |
| dp[j] = curr +     |
| min(dp[j], dp[j+1])|
| O(n) space         |
+----------+
```

**Memory phrase:** "Bottom-up, pick min of two children"

--

## PATTERN 40: Dungeon Game (LC 174)

### Pattern Recognition Signal

> **When you see:** "Minimum initial value to survive path"
> **Instant thought:** "Reverse DP! Start from end, work backwards"

### The Key Insight

We need minimum HP to START, not minimum HP at end. Work backwards!

### Visual Dry Run

```
dungeon = [[-2,-3,3],
           [-5,-10,1],
           [10,30,-5]]

Work backwards from princess at (2,2):

dp[i][j] = min HP needed at (i,j) to reach princess

dp[2][2] = max(1, 1-(-5)) = 6  (need 6 HP to survive -5 and have 1 left)
dp[2][1] = max(1, dp[2][2]-30) = 1  (30 heals, need just 1)
dp[2][0] = max(1, dp[2][1]-10) = 1  (10 heals, need just 1)
dp[1][2] = max(1, dp[2][2]-1) = 5
dp[1][1] = max(1, min(dp[1][2],dp[2][1])-(-10)) = max(1, 5+10) = 15
...
dp[0][0] = max(1, min(dp[0][1],dp[1][0])-(-2)) = 7

Answer: 7 (need 7 HP to start)
```

### Mind-Map Anchor

```
DUNGEON GAME
     |
     v
+-----------+
| Reverse DP!          |
| Start from princess  |
| dp[i][j] = min HP    |
| needed to reach end  |
| At least 1 HP always |
+-----------+
```

### The Code

```java
public int calculateMinimumHP(int[][] dungeon) {
    int m = dungeon.length, n = dungeon[0].length;
    int[][] dp = new int[m + 1][n + 1];
    
    // Initialize with large values
    for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
    dp[m][n - 1] = dp[m - 1][n] = 1;  // Need at least 1 HP to survive
    
    for (int i = m - 1; i >= 0; i-) {
        for (int j = n - 1; j >= 0; j-) {
            int minHpNeeded = Math.min(dp[i + 1][j], dp[i][j + 1]) - dungeon[i][j];
            dp[i][j] = Math.max(1, minHpNeeded);  // At least 1 HP
        }
    }
    
    return dp[0][0];
}
```

### Mind-Map Anchor

```
DUNGEON GAME
     |
     v
+-----------+
| Reverse DP!          |
| Start from princess  |
| dp[i][j] = min HP    |
| needed to reach end  |
| At least 1 HP always |
+-----------+
```

**Memory phrase:** "Work backwards, need at least 1 HP"


--

## PATTERN 41: Unique Paths II (LC 63) - With Obstacles

### Pattern Recognition Signal

> **When you see:** "Unique paths with obstacles"
> **Instant thought:** "Same as Unique Paths, but obstacle = 0 paths"

### Visual Dry Run

```
grid = [[0,0,0],
        [0,1,0],
        [0,0,0]]

dp[i][j] = paths to (i,j)

    0   1   2
0   1   1   1
1   1   0   1   (obstacle at (1,1) = 0 paths through it)
2   1   1   2

Answer: 2 paths
```

### The Code

```java
public int uniquePathsWithObstacles(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    if (grid[0][0] == 1) return 0;
    
    int[] dp = new int[n];
    dp[0] = 1;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                dp[j] = 0;  // Obstacle!
            } else if (j > 0) {
                dp[j] += dp[j - 1];
            }
        }
    }
    
    return dp[n - 1];
}
```

**Memory phrase:** "Same as unique paths, obstacle = 0"

### Mind-Map Anchor

```
UNIQUE PATHS II
      |
      v
+----------+
| Same as Unique I   |
| Obstacle cell = 0  |
| dp[j] = 0 if block |
| Else dp[j] += left |
+----------+
```

--

## PATTERN 42: Number of LIS (LC 673)

### Pattern Recognition Signal

> **When you see:** "Count number of longest increasing subsequences"
> **Instant thought:** "Track both length AND count!"

### Visual Dry Run

```
nums = [1, 3, 5, 4, 7]

len[i] = LIS length ending at i
cnt[i] = count of LIS ending at i

i=0: len[0]=1, cnt[0]=1  (just [1])
i=1: len[1]=2, cnt[1]=1  ([1,3])
i=2: len[2]=3, cnt[2]=1  ([1,3,5])
i=3: len[3]=3, cnt[3]=1  ([1,3,4])
i=4: len[4]=4, cnt[4]=2  ([1,3,5,7] and [1,3,4,7])

Max length = 4
Count at max = cnt[4] = 2

Answer: 2
```

### Mind-Map Anchor

```
NUMBER OF LIS
      |
      v
+-----------+
| Track len[] AND cnt[]|
| Longer: reset count  |
| Same len: add count  |
| Sum counts at maxLen |
+-----------+
```

### The Code

```java
public int findNumberOfLIS(int[] nums) {
    int n = nums.length;
    int[] len = new int[n];
    int[] cnt = new int[n];
    Arrays.fill(len, 1);
    Arrays.fill(cnt, 1);
    int maxLen = 1;
    
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                if (len[j] + 1 > len[i]) {
                    len[i] = len[j] + 1;
                    cnt[i] = cnt[j];
                } else if (len[j] + 1 == len[i]) {
                    cnt[i] += cnt[j];
                }
            }
        }
        maxLen = Math.max(maxLen, len[i]);
    }
    
    int result = 0;
    for (int i = 0; i < n; i++) {
        if (len[i] == maxLen) result += cnt[i];
    }
    return result;
}
```

**Memory phrase:** "Two arrays: length and count"

--

## PATTERN 43: Longest Valid Parentheses (LC 32)

### Pattern Recognition Signal

> **When you see:** "Longest valid parentheses substring"
> **Instant thought:** "DP or Stack! dp[i] = length ending at i"

### Visual Dry Run

```
s = "(()()"

dp[i] = longest valid ending at i

i=0 '(': dp[0] = 0 (can't end with '(')
i=1 '(': dp[1] = 0
i=2 ')': s[1]='(' -> dp[2] = dp[0] + 2 = 2  "()"
i=3 '(': dp[3] = 0
i=4 ')': s[3]='(' -> dp[4] = dp[2] + 2 = 4  "()()"

Answer: 4
```

### Mind-Map Anchor

```
LONGEST VALID PARENS
        |
        v
+-----------+
| dp[i] = valid length |
| ending at i          |
| Case 1: ...()        |
| Case 2: ...))        |
+-----------+
```

### The Code (DP approach)

```java
public int longestValidParentheses(String s) {
    int n = s.length();
    int[] dp = new int[n];
    int maxLen = 0;
    
    for (int i = 1; i < n; i++) {
        if (s.charAt(i) == ')') {
            if (s.charAt(i - 1) == '(') {
                // ...()
                dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
            } else if (i - dp[i - 1] - 1 >= 0 && 
                       s.charAt(i - dp[i - 1] - 1) == '(') {
                // ...))
                dp[i] = dp[i - 1] + 2 + 
                        (i - dp[i - 1] - 2 >= 0 ? dp[i - dp[i - 1] - 2] : 0);
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
    }
    
    return maxLen;
}
```

### Mind-Map Anchor

```
LONGEST VALID PARENS
        |
        v
+-----------+
| dp[i] = valid length |
| ending at i          |
| Case 1: ...()        |
| Case 2: ...))        |
+-----------+
```

**Memory phrase:** "Two cases: () pair or )) with matching ("

--

## PATTERN 44: Delete and Earn (LC 740)

### Pattern Recognition Signal

> **When you see:** "Delete number, can't use adjacent values"
> **Instant thought:** "House Robber on value counts!"

### Visual Dry Run

```
nums = [3, 4, 2]

Step 1: Build count array (sum of each value)
  count[2] = 2
  count[3] = 3
  count[4] = 4

Step 2: House Robber on count array
  Can't take adjacent values (2,3) or (3,4)
  
  i=0: prev1 = 0
  i=1: prev1 = 0
  i=2: prev1 = max(0, 0+2) = 2
  i=3: prev1 = max(2, 0+3) = 3
  i=4: prev1 = max(3, 2+4) = 6

Answer: 6 (take 2 and 4)
```

### Mind-Map Anchor

```
DELETE AND EARN
      |
      v
+-----------+
| count[v] = sum of v  |
| House Robber on it   |
| Can't take adjacent  |
| values (not indices) |
+-----------+
```

### The Code

```java
public int deleteAndEarn(int[] nums) {
    int max = 0;
    for (int num : nums) max = Math.max(max, num);
    
    int[] count = new int[max + 1];
    for (int num : nums) count[num] += num;
    
    // House Robber on count array
    int prev2 = 0, prev1 = 0;
    for (int i = 0; i <= max; i++) {
        int curr = Math.max(prev1, prev2 + count[i]);
        prev2 = prev1;
        prev1 = curr;
    }
    
    return prev1;
}
```

**Memory phrase:** "Transform to House Robber on value sums"

--

## PATTERN 45: Min Cost Climbing Stairs (LC 746)

### Pattern Recognition Signal

> **When you see:** "Minimum cost to climb stairs"
> **Instant thought:** "dp[i] = min(dp[i-1], dp[i-2]) + cost[i]"

### Visual Dry Run

```
cost = [10, 15, 20]

Can start at index 0 or 1.
Can climb 1 or 2 steps.
Need to reach past the last step.

dp[0] = 10 (cost to be at step 0)
dp[1] = 15 (cost to be at step 1)
dp[2] = min(dp[0], dp[1]) + 20 = min(10, 15) + 20 = 30

To reach top (past step 2):
  From step 1: 15
  From step 2: 30
  
Answer: min(15, 30) = 15
```

### Mind-Map Anchor

```
MIN COST CLIMBING
       |
       v
+-----------+
| dp[i] = min cost to |
| reach step i        |
| = min(i-1, i-2) +   |
| cost[i]             |
| Answer: min of last2|
+-----------+
```

### The Code

```java
public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int prev2 = cost[0], prev1 = cost[1];
    
    for (int i = 2; i < n; i++) {
        int curr = cost[i] + Math.min(prev1, prev2);
        prev2 = prev1;
        prev1 = curr;
    }
    
    return Math.min(prev1, prev2);  // Can end at n-1 or n-2
}
```

**Memory phrase:** "Min of previous two + current cost"

--

## PATTERN 46: Stock with Transaction Fee (LC 714)

### Pattern Recognition Signal

> **When you see:** "Unlimited transactions with fee"
> **Instant thought:** "State machine: hold and cash states, subtract fee on sell"

### Visual Dry Run

```
prices = [1, 3, 2, 8, 4, 9], fee = 2

cash = not holding stock
hold = holding stock

Day 0 (price=1):
  cash = 0
  hold = -1 (bought at 1)

Day 1 (price=3):
  cash = max(0, -1+3-2) = max(0, 0) = 0
  hold = max(-1, 0-3) = -1

Day 2 (price=2):
  cash = max(0, -1+2-2) = 0
  hold = max(-1, 0-2) = -1

Day 3 (price=8):
  cash = max(0, -1+8-2) = 5
  hold = max(-1, 5-8) = -1

Day 4 (price=4):
  cash = max(5, -1+4-2) = 5
  hold = max(-1, 5-4) = 1

Day 5 (price=9):
  cash = max(5, 1+9-2) = 8
  hold = max(1, 5-9) = 1

Answer: 8
```

### Mind-Map Anchor

```
STOCK WITH FEE
      |
      v
+----------+
| cash = not holding |
| hold = holding     |
| Sell: +price - fee |
| Buy: -price        |
+----------+
```

### The Code

```java
public int maxProfit(int[] prices, int fee) {
    int cash = 0;  // Not holding
    int hold = -prices[0];  // Holding
    
    for (int i = 1; i < prices.length; i++) {
        cash = Math.max(cash, hold + prices[i] - fee);  // Sell
        hold = Math.max(hold, cash - prices[i]);  // Buy
    }
    
    return cash;
}
```

**Memory phrase:** "Subtract fee when selling"

--

## PATTERN 47: Paint House (LC 256)

### Pattern Recognition Signal

> **When you see:** "Paint houses with colors, no adjacent same"
> **Instant thought:** "State machine! dp[i][color] = min cost"

### Visual Dry Run

```
costs = [[17,2,17],
         [16,16,5],
         [14,3,19]]

House 0: R=17, B=2, G=17
House 1: R=16, B=16, G=5
House 2: R=14, B=3, G=19

dp[i][c] = min cost to paint houses 0..i with house i color c

House 0: dp = [17, 2, 17]

House 1:
  R: min(B0, G0) + 16 = min(2,17) + 16 = 18
  B: min(R0, G0) + 16 = min(17,17) + 16 = 33
  G: min(R0, B0) + 5 = min(17,2) + 5 = 7
  dp = [18, 33, 7]

House 2:
  R: min(B1, G1) + 14 = min(33,7) + 14 = 21
  B: min(R1, G1) + 3 = min(18,7) + 3 = 10
  G: min(R1, B1) + 19 = min(18,33) + 19 = 37
  dp = [21, 10, 37]

Answer: min(21, 10, 37) = 10
```

### Mind-Map Anchor

```
PAINT HOUSE
    |
    v
+-----------+
| dp[c] = min cost     |
| ending with color c  |
| Each color takes min |
| of OTHER two colors  |
+-----------+
```

### The Code

```java
public int minCost(int[][] costs) {
    if (costs.length == 0) return 0;
    
    int[] dp = costs[0].clone();
    
    for (int i = 1; i < costs.length; i++) {
        int[] newDp = new int[3];
        newDp[0] = costs[i][0] + Math.min(dp[1], dp[2]);
        newDp[1] = costs[i][1] + Math.min(dp[0], dp[2]);
        newDp[2] = costs[i][2] + Math.min(dp[0], dp[1]);
        dp = newDp;
    }
    
    return Math.min(dp[0], Math.min(dp[1], dp[2]));
}
```

**Memory phrase:** "Each color takes min of other two colors"

--

## PATTERN 48: Matrix Chain Multiplication

### Pattern Recognition Signal

> **When you see:** "Optimal way to multiply matrices"
> **Instant thought:** "Interval DP! Try all split points"

### Visual Dry Run

```
dims = [10, 30, 5, 60] -> matrices A(10x30), B(30x5), C(5x60)

dp[i][j] = min cost to multiply matrices i to j

Length 2:
  dp[0][1] = 10*30*5 = 1500  (A*B)
  dp[1][2] = 30*5*60 = 9000  (B*C)

Length 3:
  dp[0][2] = min(
    dp[0][0] + dp[1][2] + 10*30*60 = 0 + 9000 + 18000 = 27000,  (A)*(BC)
    dp[0][1] + dp[2][2] + 10*5*60 = 1500 + 0 + 3000 = 4500      (AB)*(C)
  ) = 4500

Answer: 4500
```

### Mind-Map Anchor

```
MATRIX CHAIN MULT
       |
       v
+-----------+
| dp[i][j] = min cost  |
| to multiply i..j     |
| Try all split k      |
| cost = left + right  |
| + dims[i]*dims[k+1]  |
|   *dims[j+1]         |
+-----------+
```

### The Code

```java
public int matrixChainOrder(int[] dims) {
    int n = dims.length - 1;  // Number of matrices
    int[][] dp = new int[n][n];
    
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            dp[i][j] = Integer.MAX_VALUE;
            
            for (int k = i; k < j; k++) {
                int cost = dp[i][k] + dp[k + 1][j] + 
                           dims[i] * dims[k + 1] * dims[j + 1];
                dp[i][j] = Math.min(dp[i][j], cost);
            }
        }
    }
    
    return dp[0][n - 1];
}
```

**Memory phrase:** "Try all split points, cost = left + right + multiply"

--

## PATTERN 49: Stone Game II (LC 1140)

### Pattern Recognition Signal

> **When you see:** "Take 1 to 2M piles, M updates"
> **Instant thought:** "Minimax with memoization! dp[i][M]"

### Visual Dry Run

```
piles = [2, 7, 9, 4, 4]

suffix = [26, 24, 17, 8, 4]  (sum from index i to end)

Alice starts at i=0, M=1
  Can take X = 1 or 2 piles
  
  X=1: Take pile[0]=2, Bob plays from i=1, M=max(1,1)=1
    Bob's best from (1,1) = ?
    
  X=2: Take pile[0]+pile[1]=9, Bob plays from i=2, M=max(1,2)=2
    Bob's best from (2,2) = ?

Recursively compute...
Alice's score = suffix[i] - Bob's best score

Answer: 10 (Alice takes optimally)
```

### Mind-Map Anchor

```
STONE GAME II
      |
      v
+-----------+
| dp[i][M] = max stones|
| from position i with |
| current M value      |
| Try X = 1 to 2M      |
| My score = total -   |
| opponent's best      |
+-----------+
```

### The Code

```java
public int stoneGameII(int[] piles) {
    int n = piles.length;
    int[] suffix = new int[n + 1];
    for (int i = n - 1; i >= 0; i-) {
        suffix[i] = suffix[i + 1] + piles[i];
    }
    
    int[][] memo = new int[n][n + 1];
    return dfs(piles, suffix, 0, 1, memo);
}

private int dfs(int[] piles, int[] suffix, int i, int M, int[][] memo) {
    if (i >= piles.length) return 0;
    if (i + 2 * M >= piles.length) return suffix[i];  // Take all
    if (memo[i][M] > 0) return memo[i][M];
    
    int maxStones = 0;
    for (int x = 1; x <= 2 * M; x++) {
        int opponentBest = dfs(piles, suffix, i + x, Math.max(M, x), memo);
        maxStones = Math.max(maxStones, suffix[i] - opponentBest);
    }
    
    memo[i][M] = maxStones;
    return maxStones;
}
```

**Memory phrase:** "Suffix sum - opponent's best = my best"

--

## PATTERN 50: Cherry Pickup (LC 741)

### Pattern Recognition Signal

> **When you see:** "Two paths, collect items, can't double count"
> **Instant thought:** "Simulate two people walking together! dp[r1][c1][r2]"

### Visual Dry Run

```
grid = [[0,1,-1],
        [1,0,-1],
        [1,1,1]]

Two people start at (0,0), end at (2,2)
They move simultaneously (same number of steps)

Step 0: Both at (0,0), collect grid[0][0] = 0
Step 1: Person1 at (0,1) or (1,0), Person2 at (0,1) or (1,0)
  Best: P1=(1,0), P2=(0,1) -> collect 1+1 = 2
Step 2: Continue...

Key: If both at same cell, only count once!

Answer: 5
```

### Mind-Map Anchor

```
CHERRY PICKUP
      |
      v
+------------+
| Two people walk together|
| dp[r1][c1][r2]         |
| c2 = r1+c1-r2 (same steps)|
| Same cell: count once  |
| -1 = blocked           |
+------------+
```

### The Key Insight

Instead of going there and back, simulate TWO people going from start to end simultaneously.

### The Code

```java
public int cherryPickup(int[][] grid) {
    int n = grid.length;
    int[][][] dp = new int[n][n][n];
    for (int[][] layer : dp) for (int[] row : layer) Arrays.fill(row, Integer.MIN_VALUE);
    dp[0][0][0] = grid[0][0];
    
    for (int r1 = 0; r1 < n; r1++) {
        for (int c1 = 0; c1 < n; c1++) {
            for (int r2 = 0; r2 < n; r2++) {
                int c2 = r1 + c1 - r2;
                if (c2 < 0 || c2 >= n || grid[r1][c1] == -1 || grid[r2][c2] == -1) continue;
                
                int val = dp[r1][c1][r2];
                if (r1 > 0) val = Math.max(val, dp[r1-1][c1][r2]);
                if (c1 > 0) val = Math.max(val, dp[r1][c1-1][r2]);
                if (r2 > 0) val = Math.max(val, dp[r1][c1][r2-1]);
                if (r1 > 0 && r2 > 0) val = Math.max(val, dp[r1-1][c1][r2-1]);
                // ... more combinations
                
                if (val == Integer.MIN_VALUE) continue;
                
                dp[r1][c1][r2] = val + grid[r1][c1] + (r1 != r2 ? grid[r2][c2] : 0);
            }
        }
    }
    
    return Math.max(0, dp[n-1][n-1][n-1]);
}
```

**Memory phrase:** "Two people walking together, don't double count same cell"

--

# UPDATED Quick Reference: All 50 DP Patterns

| Category | Patterns | Count |
|-----|-----|----|
| 1D DP | Climbing, Robber, Jump, Decode, Kadane, Word Break | 12 |
| Knapsack | 0/1, Partition, Target, Coins, Ones/Zeros | 6 |
| 2D DP | Paths, LCS, Edit, Regex, Wildcard, Interleave | 12 |
| LIS | LIS, Envelopes, Number of LIS, Arithmetic | 4 |
| Interval | Balloons, Palindrome, Stone, MCM | 5 |
| Bitmask | TSP, Partition K | 2 |
| State Machine | Stocks I-IV, Cooldown, Fee, Paint | 9 |

--

*End of Dynamic Programming Patterns Deep Dive - 50 Patterns for L5 MAANG*

