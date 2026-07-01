# Section 17 — Greedy Algorithms

---

## 1. What Problem Does This Solve?

Greedy algorithms find the globally optimal solution by **making the locally optimal choice at each step without reconsidering past decisions**. They work when the problem has the **greedy-choice property** — a local best choice is also a global best choice — and **optimal substructure** — the optimal solution contains optimal solutions to subproblems.

When greedy works: Activity selection, interval scheduling, minimum spanning trees, Huffman coding, jump games.
When greedy fails: 0/1 Knapsack, matrix chain multiplication (need DP instead).

---

## 2. Beginner-Friendly Intuition

Imagine you're collecting the most valuable items in a museum but your bag can only hold 10 kg. A greedy approach says: "Always pick the item with the highest value-to-weight ratio." This local best choice leads to the global optimum for the fractional knapsack. But for 0/1 Knapsack (you can't split items), this fails — you need DP.

**The test:** If you can always "commit" to a local choice and it never hurts you later, greedy works.

---

## 3. Real-World Analogy

**Coin change (denominations: 25¢, 10¢, 5¢, 1¢):** To make $0.41 change, always pick the largest coin that fits. Pick 25¢, then 10¢, then 5¢, then 1¢ = 4 coins. This greedy works for standard US coin denominations (but not all coin systems!).

**Meeting room scheduling:** To fit the maximum number of meetings, always pick the meeting that ends earliest — it leaves the most room for future meetings.

---

## 4. Core Concept

### Greedy Proof Technique

To prove a greedy algorithm is correct, use one of:
1. **Exchange argument:** Assume there's a better solution. Show you can swap the greedy choice in without making things worse.
2. **Greedy stays ahead:** Show the greedy solution is always at least as good as any other solution at every step.

### Greedy vs DP Decision

| Property | Greedy | DP |
|---------|--------|-----|
| Decision | Irrevocable (commit and move on) | Flexible (consider all choices) |
| Subproblem overlap | Not required | Required |
| Speed | Usually O(n log n) | Usually O(n²) or O(n×k) |
| Works when | Greedy-choice property holds | Optimal substructure holds |

---

## 5. Pattern Recognition Signals

Use Greedy when:
```
"Activity selection" / "Non-overlapping intervals"
"Minimum number of intervals to cover range"
"Meeting rooms" / "scheduling"
"Jump Game" (can you reach end?)
"Largest number from digits"
"Task scheduler"
"Huffman coding / minimum cost"
"Candy distribution with constraints"
"Gas station circular tour"
"Assign cookies to children"
```

**Key greedy signal:** Sorting the input first, then making a pass with a simple decision at each step.

---

## 6. Step-by-Step Algorithm

### Interval Scheduling (Maximum Non-Overlapping)
```
Step 1: Sort intervals by END time (earliest end first)
Step 2: lastEnd = -∞
Step 3: For each interval [start, end]:
    If start >= lastEnd:
        Select this interval
        lastEnd = end
```

### Jump Game (Can You Reach End?)
```
Step 1: maxReach = 0
Step 2: For each index i from 0 to n-1:
    If i > maxReach: CANNOT REACH (return false)
    maxReach = max(maxReach, i + nums[i])
Step 3: return true
```

### Minimum Number of Arrows to Burst Balloons
```
Step 1: Sort by end point
Step 2: arrowPos = intervals[0][1] (first balloon's end), count = 1
Step 3: For each interval [start, end]:
    If start > arrowPos:  ← this balloon is not hit by current arrow
        count++
        arrowPos = end
Return count
```

---

## 7. Dry Run with Example

### Example 1: Non-Overlapping Intervals (Remove minimum intervals)

**Input:** `[[1,2],[2,3],[3,4],[1,3]]`

Sort by end time: `[[1,2],[2,3],[1,3],[3,4]]` → `[[1,2],[1,3],[2,3],[3,4]]`

Wait — sort by end:
- [1,2] end=2
- [2,3] end=3
- [3,4] end=4
- [1,3] end=3

Sorted: `[[1,2], [2,3], [1,3], [3,4]]` → by end: `[[1,2], [2,3] or [1,3], [3,4]]`

Let's sort properly: `[[1,2], [1,3], [2,3], [3,4]]` sorted by end → `[[1,2], [1,3] or [2,3], [3,4]]`

Correct sorted: `[[1,2](end=2), [2,3](end=3), [1,3](end=3), [3,4](end=4)]`

```
lastEnd = -∞, count_kept = 0, to_remove = 0

[1,2]: start=1 >= lastEnd=-∞ → KEEP. lastEnd=2, kept=1
[2,3]: start=2 >= lastEnd=2 → KEEP. lastEnd=3, kept=2
[1,3]: start=1 < lastEnd=3 → REMOVE. to_remove=1
[3,4]: start=3 >= lastEnd=3 → KEEP. lastEnd=4, kept=3

Total removed = 4 - 3 = 1 ✓
```

### Example 2: Jump Game

**Input:** `nums = [2, 3, 1, 1, 4]`

```
maxReach=0

i=0(2): 0 <= maxReach=0 ✓. maxReach=max(0, 0+2)=2
i=1(3): 1 <= maxReach=2 ✓. maxReach=max(2, 1+3)=4
i=2(1): 2 <= maxReach=4 ✓. maxReach=max(4, 2+1)=4
i=3(1): 3 <= maxReach=4 ✓. maxReach=max(4, 3+1)=4
i=4(4): 4 <= maxReach=4 ✓. maxReach=max(4, 4+4)=8

Return true ✓
```

**Input:** `nums = [3, 2, 1, 0, 4]`

```
i=0(3): maxReach=3
i=1(2): 1<=3 ✓. maxReach=max(3,3)=3
i=2(1): 2<=3 ✓. maxReach=max(3,3)=3
i=3(0): 3<=3 ✓. maxReach=max(3,3)=3
i=4(4): 4 > maxReach=3 → CANNOT REACH. return false ✓
```

---

## 8. Code Implementation

### Non-Overlapping Intervals (Minimum Removals)

```java
int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // sort by end
    int lastEnd = Integer.MIN_VALUE, removals = 0;
    for (int[] interval : intervals) {
        if (interval[0] >= lastEnd) {
            lastEnd = interval[1]; // keep this interval
        } else {
            removals++; // remove overlapping interval
        }
    }
    return removals;
}
```

### Jump Game I (Can Reach End?)

```java
boolean canJump(int[] nums) {
    int maxReach = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > maxReach) return false; // stuck
        maxReach = Math.max(maxReach, i + nums[i]);
    }
    return true;
}
```

### Jump Game II (Minimum Jumps to Reach End)

```java
int jump(int[] nums) {
    int jumps = 0, currentEnd = 0, farthest = 0;
    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);
        if (i == currentEnd) { // must take a jump now
            jumps++;
            currentEnd = farthest;
        }
    }
    return jumps;
}
```

### Minimum Number of Arrows to Burst Balloons

```java
int findMinArrowShots(int[][] points) {
    Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
    int arrows = 1, arrowPos = points[0][1];
    for (int[] p : points) {
        if (p[0] > arrowPos) { // balloon starts after current arrow
            arrows++;
            arrowPos = p[1];
        }
    }
    return arrows;
}
```

### Task Scheduler (Minimum Intervals)

```java
int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char t : tasks) freq[t - 'A']++;
    Arrays.sort(freq);
    int maxFreq = freq[25];
    int idleSlots = (maxFreq - 1) * n;
    for (int i = 24; i >= 0; i--) {
        idleSlots -= Math.min(freq[i], maxFreq - 1);
    }
    return tasks.length + Math.max(0, idleSlots);
}
```

### Candy Distribution (Two-Pass Greedy)

```java
int candy(int[] ratings) {
    int n = ratings.length;
    int[] candies = new int[n];
    Arrays.fill(candies, 1); // everyone gets at least 1
    // Left pass: child with higher rating than left neighbor gets more
    for (int i = 1; i < n; i++)
        if (ratings[i] > ratings[i-1]) candies[i] = candies[i-1] + 1;
    // Right pass: child with higher rating than right neighbor gets more
    for (int i = n - 2; i >= 0; i--)
        if (ratings[i] > ratings[i+1]) candies[i] = Math.max(candies[i], candies[i+1] + 1);
    int total = 0;
    for (int c : candies) total += c;
    return total;
}
```

### Gas Station (Circular Tour)

```java
int canCompleteCircuit(int[] gas, int[] cost) {
    int totalGas = 0, currentGas = 0, startStation = 0;
    for (int i = 0; i < gas.length; i++) {
        totalGas += gas[i] - cost[i];
        currentGas += gas[i] - cost[i];
        if (currentGas < 0) { // can't reach from startStation
            startStation = i + 1;
            currentGas = 0;
        }
    }
    return totalGas >= 0 ? startStation : -1; // if total < 0, impossible
}
```

---

## 9. Time Complexity

| Problem | Complexity | Bottleneck |
|---------|-----------|-----------|
| Non-overlapping intervals | O(n log n) | Sorting |
| Jump Game I/II | O(n) | Single pass |
| Minimum arrows | O(n log n) | Sorting |
| Task Scheduler | O(n) | Frequency count |
| Candy | O(n) | Two passes |
| Gas Station | O(n) | Single pass |

---

## 10. Space Complexity

| Problem | Space |
|---------|-------|
| Interval problems | O(1) (just track lastEnd) |
| Candy | O(n) (candies array) |
| Task Scheduler | O(1) (fixed 26-size frequency array) |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Single interval | Always select it (0 removals) |
| All intervals overlapping | Must remove n-1 intervals (keep 1) |
| nums=[0] in Jump Game | Already at end → true |
| nums=[0, 1] in Jump Game | Can't move from 0 → false |
| All same ratings in Candy | All get 1 candy |
| Total gas < total cost | Impossible circuit → -1 |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Sorting by start time instead of end time for interval scheduling
Arrays.sort(intervals, Comparator.comparingInt(a -> a[0])); // WRONG for non-overlapping
Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // CORRECT (sort by end)

// MISTAKE 2: Jump Game — not updating maxReach before checking i > maxReach
if (i > maxReach) return false;
maxReach = Math.max(maxReach, i + nums[i]); // CORRECT ORDER

// MISTAKE 3: Gas Station — resetting startStation to i instead of i+1
startStation = i;   // WRONG: includes the failing station
startStation = i + 1; // CORRECT: next station after the one where tank went negative

// MISTAKE 4: Candy — only doing one pass misses cases where ascending then descending
// One left-to-right pass catches left constraints
// One right-to-left pass catches right constraints
// Both passes with max() at each position = correct

// MISTAKE 5: Greedy on 0/1 Knapsack (classic failure case)
// Items: {value=6,weight=4}, {value=5,weight=3}, {value=3,weight=2}, capacity=5
// Greedy by value/weight: pick {5,3} + {3,2} = value 8
// Optimal: pick {6,4} + {3,2} doesn't fit; {5,3} + {3,2} = 8 (same here but illustrates fragility)
// For 0/1 Knapsack: ALWAYS USE DP
```

---

## 13. Interview-Level Explanation

**Q: "Why does sorting by end time (not start time) maximize non-overlapping intervals?"**

> "We want to keep as many intervals as possible. By choosing the interval that ends earliest, we leave the maximum amount of time available for future intervals. An interval ending later would cut into the available future time, potentially blocking more intervals. This is the exchange argument: if we swap our greedy choice (earliest end) with any other choice, we can only do worse or equal, never better."

**Q: "How does the two-pass approach for Candy work?"**

> "The left-to-right pass ensures every child with a higher rating than their left neighbor gets more candy than that neighbor. But this might violate the right-neighbor constraint. The right-to-left pass fixes right-neighbor violations: if a child has a higher rating than their right neighbor, they should get more candy than that right neighbor. We take the max of both passes at each position — satisfying both constraints simultaneously."

---

## 14. Real-World Use Cases

| Application | Greedy Usage |
|------------|-------------|
| **CPU scheduling** | Shortest Job First (SJF) minimizes average wait time |
| **Data compression** | Huffman coding builds optimal prefix-free codes |
| **Network design** | Kruskal's / Prim's for minimum-cost network |
| **Airline scheduling** | Assign gates to maximize flights |
| **File caching** | Evict least-recently-used (LRU) |
| **Project management** | Earliest deadline first scheduling |

---

## 15. Variations of This Pattern

| Variation | Greedy Strategy | Example |
|-----------|----------------|---------|
| Interval scheduling | Sort by end, keep earliest-ending | Non-Overlapping Intervals |
| Interval covering | Sort by start, extend coverage | Min Interval to Cover Range |
| Jump Game | Track max reach | Jump Game I/II |
| Coins (standard denominations) | Always pick largest fitting | Coin Change (greedy) |
| Huffman | Merge two lowest-freq nodes | Huffman Coding |
| Scheduling with cooldowns | Arrange by frequency | Task Scheduler |
| Circular tour | Skip and restart | Gas Station |
| Two-directional constraints | Two passes | Candy |

---

## 16. Practice Problems

### Easy — Foundation
1. **Assign Cookies** (LeetCode #455)
   - *Task:* Assign cookies to satisfy max children (greedy size matching).
   - *Hint:* Sort both. Match smallest sufficient cookie to greediest satisfiable child.

2. **Jump Game** (LeetCode #55)
   - *Task:* Can you reach the last index?
   - *Hint:* Track maxReach. If i > maxReach at any point, return false.

3. **Lemonade Change** (LeetCode #860)
   - *Task:* Give correct change using bills received.
   - *Hint:* Greedy: when giving $10 change, use a $10 bill before two $5s.

### Medium — Classic Greedy
1. **Non-Overlapping Intervals** (LeetCode #435)
   - *Task:* Minimum intervals to remove to eliminate all overlaps.
   - *Hint:* Sort by end. Count overlapping ones greedily.

2. **Meeting Rooms II** (LeetCode #253)
   - *Task:* Minimum number of meeting rooms needed.
   - *Hint:* Sort by start time. Use min-heap of end times.

3. **Jump Game II** (LeetCode #45)
   - *Task:* Minimum jumps to reach end.
   - *Hint:* Track current window end and farthest reachable.

4. **Gas Station** (LeetCode #134)
   - *Task:* Find the starting gas station for a complete circuit.
   - *Hint:* If total gas >= total cost, a solution exists. Find it with running sum.

5. **Candy** (LeetCode #135)
   - *Task:* Minimum candies with rating-based constraints.
   - *Hint:* Left pass + right pass, take max at each index.

### Hard — Advanced Greedy
1. **Task Scheduler** (LeetCode #621)
   - *Task:* Minimum intervals to finish all tasks with cooldown n.
   - *Hint:* Fill idle slots with most frequent remaining tasks.

2. **Minimum Number of Arrows to Burst Balloons** (LeetCode #452)
   - *Task:* Minimum arrows to burst all balloons.
   - *Hint:* Sort by end. Arrow at current end position hits all overlapping balloons.

3. **IPO / Maximize Capital** (LeetCode #502)
   - *Task:* Maximize capital by picking at most k projects.
   - *Hint:* Two heaps: min-heap by capital (available when affordable), max-heap by profit (pick best available).

---

## 17. How to Know You Have Mastered Greedy Algorithms

You have mastered this topic when you can:
- [ ] Explain the greedy-choice property and when it applies
- [ ] Prove a greedy algorithm's correctness via exchange argument
- [ ] Sort by the right key for interval problems (end time, not start time)
- [ ] Implement Jump Game in a single pass
- [ ] Implement Candy with two-pass greedy
- [ ] Distinguish when greedy fails vs. succeeds (0/1 Knapsack vs. activity selection)
- [ ] Recognize the "two-heap" pattern for greedy with priority constraints
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. For the interval scheduling problem (maximize non-overlapping intervals), why is sorting by start time incorrect?

2. Jump Game: `nums = [1, 0, 0, 0]`. What is `maxReach` at each step? Can you reach the end?

3. Coin denominations `{1, 3, 4}`, target `6`. Greedy picks 4+1+1 = 3 coins. But optimal is 3+3 = 2 coins. What does this tell you about greedy for coin change?

4. In Gas Station, if `sum(gas) >= sum(cost)`, a solution always exists. Why?

5. The Candy problem requires two passes. Can you solve it in a single pass? Under what conditions?

6. Task Scheduler: tasks = `[A,A,A,B,B,B]`, n=2. What's the minimum intervals needed?

7. When is greedy better than DP? When is DP better than greedy?

8. What is the exchange argument proof technique in 1-2 sentences?

> **Answers:**
> 1. Sorting by start time doesn't minimize the "time occupied." A short interval starting early might end late, blocking many others. Sorting by end time ensures each chosen interval releases the schedule as early as possible.
> 2. i=0(1): maxReach=1. i=1(0): 1<=1✓, maxReach=max(1,1)=1. i=2(0): 2>1 → return false. Cannot reach.
> 3. Greedy for coin change doesn't always work for arbitrary denominations. It only works for specific denomination sets (like US coins). For general coin change, use DP.
> 4. The total fuel exceeds total cost globally, so there must be a starting point where the cumulative surplus is always non-negative. The algorithm finds it by identifying where the running total drops below zero and resetting.
> 5. Theoretically yes using only one pass with more complex logic (tracking slopes), but the two-pass approach is cleaner and universally accepted.
> 6. 8 intervals: A→B→_→A→B→_→A→B. Pattern: (maxFreq-1) × (n+1) + count_with_maxFreq = 2×3 + 2 = 8.
> 7. Greedy: when local optimal = global optimal, O(n log n) is achievable. DP: when you need to consider all previous decisions to make the current one (overlapping subproblems).
> 8. Assume an optimal solution different from the greedy solution. Show that swapping the greedy choice into the optimal solution produces a solution that's equally good or better — proving the greedy choice is safe.

---

**Next →** `../18_Dynamic_Programming/01_DP_Patterns.md`

## 2. Beginner-Friendly Intuition

You're picking coins to make $0.41 using quarters (25¢), dimes (10¢), nickels (5¢), pennies (1¢).  
Greedy: Always pick the largest coin that fits.  
25¢ + 10¢ + 5¢ + 1¢ = 41¢ (4 coins — optimal!)

**But:** Greedy doesn't always work! For coins [1, 3, 4], target=6:  
Greedy picks 4+1+1 = 3 coins. Optimal is 3+3 = 2 coins.

---

## 3. How to Prove Greedy Works

Two main techniques:

1. **Exchange Argument:** Show that swapping any greedy choice with a non-greedy choice only makes things worse.
2. **Greedy Stays Ahead:** Show that at every step, the greedy solution is at least as good as any other.

---

## 4. Pattern 1: Interval Scheduling / Activity Selection

**Problem:** Select maximum non-overlapping intervals.

**Greedy Rule:** Always pick the interval that ends earliest.

```java
int maxActivities(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);  // sort by end time
    int count = 1, end = intervals[0][1];
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= end) {  // starts after last end
            count++;
            end = intervals[i][1];
        }
    }
    return count;
}
```

**Why sort by end time?** Ending early leaves more room for future intervals.

---

## 5. Pattern 2: Merge Intervals

```java
int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a,b) -> a[0] - b[0]);  // sort by start
    List<int[]> merged = new ArrayList<>();
    for (int[] interval : intervals) {
        if (merged.isEmpty() || merged.get(merged.size()-1)[1] < interval[0])
            merged.add(interval);
        else
            merged.get(merged.size()-1)[1] = Math.max(merged.get(merged.size()-1)[1], interval[1]);
    }
    return merged.toArray(new int[0][]);
}
```

---

## 6. Pattern 3: Jump Game

**Problem:** Can you reach the last index?

```java
boolean canJump(int[] nums) {
    int maxReach = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > maxReach) return false;  // can't reach here
        maxReach = Math.max(maxReach, i + nums[i]);
    }
    return true;
}
```

**Jump Game II — Minimum jumps:**
```java
int jump(int[] nums) {
    int jumps = 0, currEnd = 0, farthest = 0;
    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);
        if (i == currEnd) {   // we must jump here
            jumps++;
            currEnd = farthest;
        }
    }
    return jumps;
}
```

---

## 7. Pattern 4: Gas Station

```java
int canCompleteCircuit(int[] gas, int[] cost) {
    int totalGas = 0, tank = 0, start = 0;
    for (int i = 0; i < gas.length; i++) {
        totalGas += gas[i] - cost[i];
        tank += gas[i] - cost[i];
        if (tank < 0) { start = i + 1; tank = 0; }  // reset start
    }
    return totalGas >= 0 ? start : -1;
}
```

**Insight:** If total gas ≥ total cost, a solution exists. The start is after the last negative tank.

---

## 8. Pattern 5: Task Scheduler

```java
int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char t : tasks) freq[t - 'A']++;
    Arrays.sort(freq);
    int maxFreq = freq[25];
    int idleTime = (maxFreq - 1) * n;
    for (int i = 24; i >= 0 && freq[i] > 0; i--)
        idleTime -= Math.min(maxFreq - 1, freq[i]);
    idleTime = Math.max(0, idleTime);
    return tasks.length + idleTime;
}
```

---

## 9. Pattern 6: Candy Problem

```java
int candy(int[] ratings) {
    int n = ratings.length;
    int[] candies = new int[n];
    Arrays.fill(candies, 1);
    // Left to right: give more if higher than left neighbor
    for (int i = 1; i < n; i++)
        if (ratings[i] > ratings[i-1]) candies[i] = candies[i-1] + 1;
    // Right to left: give more if higher than right neighbor
    for (int i = n-2; i >= 0; i--)
        if (ratings[i] > ratings[i+1]) candies[i] = Math.max(candies[i], candies[i+1]+1);
    int total = 0;
    for (int c : candies) total += c;
    return total;
}
```

---

## 10. Pattern 7: Fractional Knapsack

```java
double fractionalKnapsack(int W, int[][] items) {
    // items[i] = {value, weight}
    Arrays.sort(items, (a,b) -> Double.compare((double)b[0]/b[1], (double)a[0]/a[1]));
    double profit = 0;
    for (int[] item : items) {
        if (W >= item[1]) { profit += item[0]; W -= item[1]; }
        else { profit += (double) item[0] * W / item[1]; break; }
    }
    return profit;
}
```

---

## 11. When Greedy Works vs When It Doesn't

| Works | Doesn't Work |
|-------|-------------|
| Activity selection | 0/1 Knapsack |
| Fractional knapsack | Coin change (non-canonical) |
| Huffman coding | Longest increasing subsequence |
| Dijkstra | All-paths counting |
| Prim's/Kruskal's MST | Problems with global dependencies |

**Greedy hint words:** "maximum", "minimum", "optimal", "at least", "as few as"

---

## Practice Problems

**Easy:**
1. Assign Cookies.
2. Lemonade Change.
3. Score After Flipping Matrix.

**Medium:**
1. Jump Game I & II.
2. Non-overlapping Intervals.
3. Minimum Number of Arrows to Burst Balloons.
4. Task Scheduler.
5. Partition Labels.

**Hard:**
1. Candy.
2. IPO (maximize capital).
3. Meeting Rooms III.

---

**Next →** `../18_Dynamic_Programming/01_DP_Patterns.md`
