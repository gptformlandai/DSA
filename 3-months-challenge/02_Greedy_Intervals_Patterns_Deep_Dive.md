# Greedy & Intervals Patterns Deep Dive
## Junior Dev's Complete Guide to L5 MAANG Greedy Mastery

--

# THE GREEDY MINDSET: Before You Code Anything

## The One Sentence That Unlocks Greedy

> **Greedy = Make the locally optimal choice at each step, hoping it leads to global optimum.**

## When Does Greedy Work?

```
1. GREEDY CHOICE PROPERTY: Local optimal leads to global optimal
2. OPTIMAL SUBSTRUCTURE: Optimal solution contains optimal sub-solutions

If BOTH hold → Greedy works!
If NOT → Need DP or other approach
```

## The Greedy vs DP Decision

```
GREEDY: Make ONE choice, never look back
DP: Consider ALL choices, pick best

Greedy is FASTER but only works for specific problems!
```

--

# PART 1: INTERVAL PATTERNS

## The Interval Sorting Insight

> **90% of interval problems start with SORTING!**

```java
// Sort by START time (for merging, scheduling)
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

// Sort by END time (for max non-overlapping)
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
```

--

## PATTERN 1: Merge Intervals (LC 56)

### Pattern Recognition Signal

> **When you see:** "Merge overlapping intervals"
> **Instant thought:** "Sort by start, merge if overlap!"

### The Mental Model: Timeline Consolidation

You have meetings on a calendar. Overlapping meetings become one block.

### Visual Dry Run

```
intervals = [[1,3], [2,6], [8,10], [15,18]]

Sort by start (already sorted):
[[1,3], [2,6], [8,10], [15,18]]

Process:
result = [[1,3]]

[2,6]: 2 <= 3 (overlaps with [1,3])
  Merge: [1, max(3,6)] = [1,6]
  result = [[1,6]]

[8,10]: 8 > 6 (no overlap)
  Add new: result = [[1,6], [8,10]]

[15,18]: 15 > 10 (no overlap)
  Add new: result = [[1,6], [8,10], [15,18]]

Answer: [[1,6], [8,10], [15,18]]
```

### The Code

```java
public int[][] merge(int[][] intervals) {
    if (intervals.length <= 1) return intervals;
    
    // Sort by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    List<int[]> result = new ArrayList<>();
    int[] current = intervals[0];
    result.add(current);
    
    for (int[] interval : intervals) {
        if (interval[0] <= current[1]) {
            // Overlaps - merge by extending end
            current[1] = Math.max(current[1], interval[1]);
        } else {
            // No overlap - add new interval
            current = interval;
            result.add(current);
        }
    }
    
    return result.toArray(new int[result.size()][]);
}
```

### Mind-Map Anchor

```
MERGE INTERVALS
      |
      v
+-----------+
| Sort by START       |
| If start <= prevEnd |
|   -> Merge (max end)|
| Else add new        |
+-----------+
```

**Memory phrase:** "Sort by start, merge if overlaps, extend end"

--

## PATTERN 2: Insert Interval (LC 57)

### Pattern Recognition Signal

> **When you see:** "Insert new interval into sorted list"
> **Instant thought:** "Three phases: before, merge, after!"

### Visual Dry Run

```
intervals = [[1,2], [3,5], [6,7], [8,10], [12,16]]
newInterval = [4,8]

Phase 1: Add all intervals BEFORE newInterval
  [1,2] ends at 2 < 4 (newInterval start) -> add [1,2]
  [3,5] ends at 5 >= 4 -> stop

Phase 2: Merge overlapping intervals
  [3,5]: 3 <= 8 (overlaps) -> merge: [min(4,3), max(8,5)] = [3,8]
  [6,7]: 6 <= 8 (overlaps) -> merge: [3, max(8,7)] = [3,8]
  [8,10]: 8 <= 8 (overlaps) -> merge: [3, max(8,10)] = [3,10]
  [12,16]: 12 > 10 -> stop merging

Phase 3: Add merged + remaining
  Add [3,10], then [12,16]

Answer: [[1,2], [3,10], [12,16]]
```

### The Code

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0, n = intervals.length;
    
    // Phase 1: Add all intervals before newInterval
    while (i < n && intervals[i][1] < newInterval[0]) {
        result.add(intervals[i++]);
    }
    
    // Phase 2: Merge overlapping intervals
    while (i < n && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    result.add(newInterval);
    
    // Phase 3: Add remaining intervals
    while (i < n) {
        result.add(intervals[i++]);
    }
    
    return result.toArray(new int[result.size()][]);
}
```

### Mind-Map Anchor

```
INSERT INTERVAL
      |
      v
+-----------+
| Phase 1: Before     |
| Phase 2: Merge      |
| Phase 3: After      |
| No sorting needed!  |
+-----------+
```

**Memory phrase:** "Before, merge, after - three phases"

--

## PATTERN 3: Non-overlapping Intervals (LC 435)

### Pattern Recognition Signal

> **When you see:** "Minimum removals for non-overlapping" or "Maximum non-overlapping"
> **Instant thought:** "Sort by END time! Greedy activity selection"

### The Key Insight

Sort by END time. Always pick the interval that ends earliest - leaves most room for others!

### Visual Dry Run

```
intervals = [[1,2], [2,3], [3,4], [1,3]]

Sort by END time:
[[1,2], [2,3], [1,3], [3,4]]

Greedy selection:
Pick [1,2] (ends earliest)
  end = 2

[2,3]: start 2 >= end 2 -> Pick! end = 3
[1,3]: start 1 < end 3 -> Skip (overlaps)
[3,4]: start 3 >= end 3 -> Pick! end = 4

Selected: 3 intervals
Removed: 4 - 3 = 1

Answer: 1
```

### The Code

```java
public int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Sort by END time (greedy choice!)
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    
    int count = 1;  // First interval always selected
    int end = intervals[0][1];
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= end) {
            // No overlap - select this interval
            count++;
            end = intervals[i][1];
        }
        // Else: overlaps, skip (remove) this interval
    }
    
    return intervals.length - count;  // Removed = total - selected
}
```

### Mind-Map Anchor

```
NON-OVERLAPPING INTERVALS
          |
          v
+------------+
| Sort by END time!      |
| Pick earliest ending   |
| Skip if overlaps       |
| Removed = total - kept |
+------------+
```

**Memory phrase:** "Sort by end, pick non-overlapping, count removals"

--

## PATTERN 4: Meeting Rooms (LC 252)

### Pattern Recognition Signal

> **When you see:** "Can attend all meetings?"
> **Instant thought:** "Sort by start, check for any overlap!"

### Visual Dry Run

```
intervals = [[0,30], [5,10], [15,20]]

Sort by start: [[0,30], [5,10], [15,20]]

Check overlaps:
[0,30] and [5,10]: 5 < 30 -> OVERLAP!

Answer: false (can't attend all)
```

### The Code

```java
public boolean canAttendMeetings(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] < intervals[i-1][1]) {
            return false;  // Overlap found!
        }
    }
    
    return true;
}
```

### Mind-Map Anchor

```
MEETING ROOMS I
      |
      v
+---------+
| Sort by start    |
| Check: start <   |
| prev end = overlap|
| Any overlap = NO |
+---------+
```

**Memory phrase:** "Sort, check adjacent overlap"

--

## PATTERN 5: Meeting Rooms II (LC 253)

### Pattern Recognition Signal

> **When you see:** "Minimum meeting rooms needed" or "Maximum concurrent events"
> **Instant thought:** "Min-heap of end times OR sweep line!"

### The Mental Model: Conference Room Booking

When a meeting starts, check if any room is free (earliest ending meeting). If not, need new room.

### Visual Dry Run

```
intervals = [[0,30], [5,10], [15,20]]

Sort by start: [[0,30], [5,10], [15,20]]

Min-heap (end times):
[0,30]: heap = [30], rooms = 1
[5,10]: 5 < 30 (no room free) -> heap = [10, 30], rooms = 2
[15,20]: 15 >= 10 (room free!) -> remove 10, add 20 -> heap = [20, 30], rooms = 2

Answer: 2 rooms
```

### The Code

```java
public int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    // Min-heap of end times
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    heap.offer(intervals[0][1]);
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= heap.peek()) {
            heap.poll();  // Reuse room
        }
        heap.offer(intervals[i][1]);
    }
    
    return heap.size();
}
```

### Alternative: Sweep Line

```java
public int minMeetingRooms(int[][] intervals) {
    int[] starts = new int[intervals.length];
    int[] ends = new int[intervals.length];
    
    for (int i = 0; i < intervals.length; i++) {
        starts[i] = intervals[i][0];
        ends[i] = intervals[i][1];
    }
    
    Arrays.sort(starts);
    Arrays.sort(ends);
    
    int rooms = 0, endPtr = 0;
    
    for (int start : starts) {
        if (start < ends[endPtr]) {
            rooms++;  // Need new room
        } else {
            endPtr++;  // Reuse room
        }
    }
    
    return rooms;
}
```

### Mind-Map Anchor

```
MEETING ROOMS II
       |
       v
+-----------+
| Method 1: Min-heap   |
|   of end times       |
| Method 2: Sweep line |
|   sort starts & ends |
| Count max concurrent |
+-----------+
```

**Memory phrase:** "Min-heap of ends, or sweep line with two pointers"

--

## PATTERN 6: Minimum Arrows to Burst Balloons (LC 452)

### Pattern Recognition Signal

> **When you see:** "Minimum points to cover all intervals"
> **Instant thought:** "Same as max non-overlapping! Sort by end"

### Visual Dry Run

```
points = [[10,16], [2,8], [1,6], [7,12]]

Sort by END: [[1,6], [2,8], [7,12], [10,16]]

Greedy:
Arrow at 6: bursts [1,6], [2,8] (both contain 6)
Arrow at 12: bursts [7,12], [10,16] (both contain 12)

Answer: 2 arrows
```

### The Code

```java
public int findMinArrowPoints(int[][] points) {
    if (points.length == 0) return 0;
    
    // Sort by END
    Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
    
    int arrows = 1;
    int arrowPos = points[0][1];
    
    for (int i = 1; i < points.length; i++) {
        if (points[i][0] > arrowPos) {
            // Balloon not burst by current arrow
            arrows++;
            arrowPos = points[i][1];
        }
    }
    
    return arrows;
}
```

### Mind-Map Anchor

```
MIN ARROWS BALLOONS
        |
        v
+-----------+
| Sort by END          |
| Arrow at first end   |
| New arrow if start > |
| current arrow pos    |
+-----------+
```

**Memory phrase:** "Sort by end, shoot at end, new arrow if not covered"

--

## PATTERN 7: Interval List Intersections (LC 986)

### Pattern Recognition Signal

> **When you see:** "Find intersections of two interval lists"
> **Instant thought:** "Two pointers! Intersection = [max start, min end]"

### Visual Dry Run

```
A = [[0,2], [5,10], [13,23], [24,25]]
B = [[1,5], [8,12], [15,24], [25,26]]

Two pointers i=0, j=0:

A[0]=[0,2], B[0]=[1,5]:
  Intersection: [max(0,1), min(2,5)] = [1,2]
  2 < 5, so i++

A[1]=[5,10], B[0]=[1,5]:
  Intersection: [max(5,1), min(10,5)] = [5,5]
  5 <= 10, so j++

A[1]=[5,10], B[1]=[8,12]:
  Intersection: [max(5,8), min(10,12)] = [8,10]
  10 < 12, so i++

... continue

Answer: [[1,2], [5,5], [8,10], [15,23], [24,24], [25,25]]
```

### The Code

```java
public int[][] intervalIntersection(int[][] A, int[][] B) {
    List<int[]> result = new ArrayList<>();
    int i = 0, j = 0;
    
    while (i < A.length && j < B.length) {
        int start = Math.max(A[i][0], B[j][0]);
        int end = Math.min(A[i][1], B[j][1]);
        
        if (start <= end) {
            result.add(new int[]{start, end});
        }
        
        // Move pointer with smaller end
        if (A[i][1] < B[j][1]) i++;
        else j++;
    }
    
    return result.toArray(new int[result.size()][]);
}
```

### Mind-Map Anchor

```
INTERVAL INTERSECTIONS
         |
         v
+------------+
| Two pointers           |
| Intersection =         |
| [max start, min end]   |
| Move smaller end ptr   |
+------------+
```

**Memory phrase:** "Max start, min end, move smaller end pointer"


--

# PART 2: GREEDY PATTERNS

## PATTERN 8: Gas Station (LC 134)

### Pattern Recognition Signal

> **When you see:** "Circular route, can complete circuit?"
> **Instant thought:** "If total gas >= total cost, solution exists! Find starting point"

### The Key Insight

1. If sum(gas) >= sum(cost), a solution ALWAYS exists
2. If we can't reach station i from start, start from i+1

### Visual Dry Run

```
gas  = [1, 2, 3, 4, 5]
cost = [3, 4, 5, 1, 2]

Total gas = 15, Total cost = 15 -> Solution exists!

Try starting from 0:
  Station 0: tank = 0 + 1 - 3 = -2 < 0 -> Can't start here

Try starting from 1:
  Station 1: tank = 0 + 2 - 4 = -2 < 0 -> Can't start here

Try starting from 2:
  Station 2: tank = 0 + 3 - 5 = -2 < 0 -> Can't start here

Try starting from 3:
  Station 3: tank = 0 + 4 - 1 = 3
  Station 4: tank = 3 + 5 - 2 = 6
  Station 0: tank = 6 + 1 - 3 = 4
  Station 1: tank = 4 + 2 - 4 = 2
  Station 2: tank = 2 + 3 - 5 = 0 >= 0 -> SUCCESS!

Answer: 3
```

### The Code

```java
public int canCompleteCircuit(int[] gas, int[] cost) {
    int totalTank = 0;
    int currentTank = 0;
    int startStation = 0;
    
    for (int i = 0; i < gas.length; i++) {
        int diff = gas[i] - cost[i];
        totalTank += diff;
        currentTank += diff;
        
        if (currentTank < 0) {
            // Can't reach i+1 from startStation
            // Try starting from i+1
            startStation = i + 1;
            currentTank = 0;
        }
    }
    
    return totalTank >= 0 ? startStation : -1;
}
```

### Mind-Map Anchor

```
GAS STATION
    |
    v
+-----------+
| If total >= 0,       |
| solution exists      |
| If tank < 0 at i,    |
| start from i+1       |
| One pass solution!   |
+-----------+
```

**Memory phrase:** "Total check, reset start when tank goes negative"

--

## PATTERN 9: Candy (LC 135)

### Pattern Recognition Signal

> **When you see:** "Distribute items based on neighbors"
> **Instant thought:** "Two passes! Left-to-right, then right-to-left"

### The Mental Model: Fair Distribution

Each child must have more candy than lower-rated neighbor. Two passes ensure both directions are satisfied.

### Visual Dry Run

```
ratings = [1, 0, 2]

Pass 1 (left to right):
  candy = [1, 1, 1]
  i=1: ratings[1]=0 < ratings[0]=1 -> no change
  i=2: ratings[2]=2 > ratings[1]=0 -> candy[2] = candy[1]+1 = 2
  candy = [1, 1, 2]

Pass 2 (right to left):
  i=1: ratings[1]=0 < ratings[2]=2 -> no change (candy[1] already < candy[2])
  i=0: ratings[0]=1 > ratings[1]=0 -> candy[0] = max(1, candy[1]+1) = 2
  candy = [2, 1, 2]

Answer: 2 + 1 + 2 = 5
```

### The Code

```java
public int candy(int[] ratings) {
    int n = ratings.length;
    int[] candy = new int[n];
    Arrays.fill(candy, 1);  // Everyone gets at least 1
    
    // Left to right: handle increasing sequences
    for (int i = 1; i < n; i++) {
        if (ratings[i] > ratings[i-1]) {
            candy[i] = candy[i-1] + 1;
        }
    }
    
    // Right to left: handle decreasing sequences
    for (int i = n - 2; i >= 0; i-) {
        if (ratings[i] > ratings[i+1]) {
            candy[i] = Math.max(candy[i], candy[i+1] + 1);
        }
    }
    
    int total = 0;
    for (int c : candy) total += c;
    return total;
}
```

### Mind-Map Anchor

```
CANDY
  |
  v
+-----------+
| Two passes!          |
| L->R: if > left,     |
|   candy = left + 1   |
| R->L: if > right,    |
|   candy = max(curr,  |
|   right + 1)         |
+-----------+
```

**Memory phrase:** "Two passes: left-to-right, right-to-left, take max"

--

## PATTERN 10: Partition Labels (LC 763)

### Pattern Recognition Signal

> **When you see:** "Partition string so each letter appears in one part"
> **Instant thought:** "Track last occurrence, extend partition to include all!"

### Visual Dry Run

```
s = "ababcbacadefegdehijhklij"

Last occurrence of each char:
a->8, b->5, c->7, d->14, e->15, f->11, g->13, h->19, i->22, j->23, k->20, l->21

Partition:
i=0 'a': end = max(0, 8) = 8
i=1 'b': end = max(8, 5) = 8
i=2 'a': end = max(8, 8) = 8
...
i=8 'a': i == end! Partition [0,8], length = 9

i=9 'd': end = 14
...
i=15 'e': i == end! Partition [9,15], length = 7

i=16 'h': end = 19
...
i=23 'j': i == end! Partition [16,23], length = 8

Answer: [9, 7, 8]
```

### The Code

```java
public List<Integer> partitionLabels(String s) {
    // Find last occurrence of each character
    int[] last = new int[26];
    for (int i = 0; i < s.length(); i++) {
        last[s.charAt(i) - 'a'] = i;
    }
    
    List<Integer> result = new ArrayList<>();
    int start = 0, end = 0;
    
    for (int i = 0; i < s.length(); i++) {
        end = Math.max(end, last[s.charAt(i) - 'a']);
        
        if (i == end) {
            result.add(end - start + 1);
            start = i + 1;
        }
    }
    
    return result;
}
```

### Mind-Map Anchor

```
PARTITION LABELS
       |
       v
+-----------+
| Track last[] of each |
| char                 |
| Extend end to include|
| all occurrences      |
| Cut when i == end    |
+-----------+
```

**Memory phrase:** "Last occurrence map, extend end, cut when i reaches end"

--

## PATTERN 11: Jump Game (LC 55) - Greedy

### Pattern Recognition Signal

> **When you see:** "Can reach end with jumps?"
> **Instant thought:** "Track farthest reachable!"

### The Code

```java
public boolean canJump(int[] nums) {
    int farthest = 0;
    
    for (int i = 0; i < nums.length; i++) {
        if (i > farthest) return false;  // Can't reach here
        farthest = Math.max(farthest, i + nums[i]);
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
|   -> unreachable |
| Update farthest  |
+---------+
```

**Memory phrase:** "Track farthest, fail if i > farthest"

--

## PATTERN 12: Jump Game II (LC 45) - Greedy

### Pattern Recognition Signal

> **When you see:** "Minimum jumps to reach end"
> **Instant thought:** "BFS-like! Track current level end"

### Visual Dry Run

```
nums = [2, 3, 1, 1, 4]

jumps = 0, currentEnd = 0, farthest = 0

i=0: farthest = max(0, 0+2) = 2
     i == currentEnd (0), jumps = 1, currentEnd = 2

i=1: farthest = max(2, 1+3) = 4

i=2: farthest = max(4, 2+1) = 4
     i == currentEnd (2), jumps = 2, currentEnd = 4
     currentEnd >= last index, done!

Answer: 2
```

### The Code

```java
public int jump(int[] nums) {
    int jumps = 0, currentEnd = 0, farthest = 0;
    
    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);
        
        if (i == currentEnd) {
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
| Track currentEnd   |
| Track farthest     |
| Jump when i == end |
| Update end to far  |
+----------+
```

**Memory phrase:** "Level-by-level BFS, jump at level end"

--

## PATTERN 13: Task Scheduler (LC 621)

### Pattern Recognition Signal

> **When you see:** "Schedule tasks with cooldown"
> **Instant thought:** "Most frequent task determines minimum time!"

### The Key Insight

Most frequent task needs (count-1) gaps of size n. Fill gaps with other tasks.

### Visual Dry Run

```
tasks = ["A","A","A","B","B","B"], n = 2

Count: A=3, B=3
Max count = 3, tasks with max = 2 (A and B)

Formula: (maxCount - 1) * (n + 1) + tasksWithMax
       = (3 - 1) * (2 + 1) + 2
       = 2 * 3 + 2 = 8

Schedule: A B _ A B _ A B
         or A B idle A B idle A B

Answer: max(8, tasks.length) = max(8, 6) = 8
```

### The Code

```java
public int leastInterval(char[] tasks, int n) {
    int[] count = new int[26];
    for (char task : tasks) count[task - 'A']++;
    
    int maxCount = 0;
    for (int c : count) maxCount = Math.max(maxCount, c);
    
    int tasksWithMax = 0;
    for (int c : count) if (c == maxCount) tasksWithMax++;
    
    int result = (maxCount - 1) * (n + 1) + tasksWithMax;
    
    return Math.max(result, tasks.length);
}
```

### Mind-Map Anchor

```
TASK SCHEDULER
      |
      v
+------------+
| Find max frequency     |
| Gaps = (max-1) * (n+1) |
| + tasks with max freq  |
| Answer = max(formula,  |
|   total tasks)         |
+------------+
```

**Memory phrase:** "Max freq determines gaps, fill with others"

--

## PATTERN 14: Queue Reconstruction by Height (LC 406)

### Pattern Recognition Signal

> **When you see:** "Reconstruct queue based on height and count"
> **Instant thought:** "Sort by height DESC, then insert by k!"

### Visual Dry Run

```
people = [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

Sort by height DESC, then k ASC:
[[7,0], [7,1], [6,1], [5,0], [5,2], [4,4]]

Insert at index k:
[7,0]: result = [[7,0]]
[7,1]: result = [[7,0], [7,1]]
[6,1]: result = [[7,0], [6,1], [7,1]]
[5,0]: result = [[5,0], [7,0], [6,1], [7,1]]
[5,2]: result = [[5,0], [7,0], [5,2], [6,1], [7,1]]
[4,4]: result = [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]

Answer: [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
```

### The Code

```java
public int[][] reconstructQueue(int[][] people) {
    // Sort: height DESC, then k ASC
    Arrays.sort(people, (a, b) -> 
        a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);
    
    List<int[]> result = new ArrayList<>();
    
    for (int[] person : people) {
        result.add(person[1], person);  // Insert at index k
    }
    
    return result.toArray(new int[result.size()][]);
}
```

### Mind-Map Anchor

```
QUEUE RECONSTRUCTION
         |
         v
+------------+
| Sort: height DESC      |
| Same height: k ASC     |
| Insert at index k      |
| Taller people first!   |
+------------+
```

**Memory phrase:** "Tallest first, insert at k position"

--

## PATTERN 15: Minimum Platforms (GFG)

### Pattern Recognition Signal

> **When you see:** "Minimum platforms for trains"
> **Instant thought:** "Same as Meeting Rooms II! Sweep line"

### Visual Dry Run

```
arrivals   = [9:00, 9:40, 9:50, 11:00, 15:00, 18:00]
departures = [9:10, 12:00, 11:20, 11:30, 19:00, 20:00]

Sort both:
arrivals   = [9:00, 9:40, 9:50, 11:00, 15:00, 18:00]
departures = [9:10, 11:20, 11:30, 12:00, 19:00, 20:00]

Sweep line:
9:00 arrival: platforms = 1
9:10 departure: platforms = 0
9:40 arrival: platforms = 1
9:50 arrival: platforms = 2
11:00 arrival: platforms = 3  <- MAX
11:20 departure: platforms = 2
...

Answer: 3 platforms
```

### Mind-Map Anchor

```
MINIMUM PLATFORMS
       |
       v
+-----------+
| Sort arrivals        |
| Sort departures      |
| Two pointers sweep   |
| Track max concurrent |
+-----------+
```

### The Code

```java
public int findPlatform(int[] arr, int[] dep) {
    Arrays.sort(arr);
    Arrays.sort(dep);
    
    int platforms = 0, maxPlatforms = 0;
    int i = 0, j = 0;
    
    while (i < arr.length) {
        if (arr[i] <= dep[j]) {
            platforms++;
            i++;
        } else {
            platforms-;
            j++;
        }
        maxPlatforms = Math.max(maxPlatforms, platforms);
    }
    
    return maxPlatforms;
}
```

**Memory phrase:** "Sweep line: arrival adds, departure removes"

--

## PATTERN 16: Activity Selection (GFG)

### Pattern Recognition Signal

> **When you see:** "Maximum activities without overlap"
> **Instant thought:** "Sort by end time, greedy select!"

### Visual Dry Run

```
activities: [(1,4), (3,5), (0,6), (5,7), (3,9), (5,9), (6,10), (8,11), (8,12), (2,14), (12,16)]

Sort by end time:
[(1,4), (3,5), (0,6), (5,7), (3,9), (5,9), (6,10), (8,11), (8,12), (2,14), (12,16)]

Greedy selection:
Select (1,4), lastEnd = 4
(3,5): 3 < 4, skip
(0,6): 0 < 4, skip
(5,7): 5 >= 4, SELECT, lastEnd = 7
(3,9): 3 < 7, skip
(5,9): 5 < 7, skip
(6,10): 6 < 7, skip
(8,11): 8 >= 7, SELECT, lastEnd = 11
(8,12): 8 < 11, skip
(2,14): 2 < 11, skip
(12,16): 12 >= 11, SELECT, lastEnd = 16

Selected: 4 activities
```

### Mind-Map Anchor

```
ACTIVITY SELECTION
       |
       v
+-----------+
| Sort by END time     |
| Pick if start >= end |
| Update lastEnd       |
| Classic greedy!      |
+-----------+
```

### The Code

```java
public int maxActivities(int[] start, int[] end) {
    // Create and sort by end time
    int n = start.length;
    int[][] activities = new int[n][2];
    for (int i = 0; i < n; i++) {
        activities[i] = new int[]{start[i], end[i]};
    }
    Arrays.sort(activities, (a, b) -> a[1] - b[1]);
    
    int count = 1;
    int lastEnd = activities[0][1];
    
    for (int i = 1; i < n; i++) {
        if (activities[i][0] >= lastEnd) {
            count++;
            lastEnd = activities[i][1];
        }
    }
    
    return count;
}
```

**Memory phrase:** "Sort by end, pick if start >= last end"

--

## PATTERN 17: Job Sequencing (GFG)

### Pattern Recognition Signal

> **When you see:** "Jobs with deadlines and profits, maximize profit"
> **Instant thought:** "Sort by profit DESC, assign to latest available slot!"

### Visual Dry Run

```
Jobs: [(1,20), (2,15), (3,10), (3,5), (3,1)]
      (deadline, profit)

Sort by profit DESC: [(1,20), (2,15), (3,10), (3,5), (3,1)]

Slots: [_, _, _] (3 slots for max deadline 3)

Job (1,20): deadline 1, assign to slot 1 -> [20, _, _]
Job (2,15): deadline 2, assign to slot 2 -> [20, 15, _]
Job (3,10): deadline 3, assign to slot 3 -> [20, 15, 10]
Job (3,5): deadline 3, slots 3,2,1 all full -> skip
Job (3,1): skip

Profit: 20 + 15 + 10 = 45
```

### The Code

```java
public int[] jobSequencing(int[][] jobs) {
    // Sort by profit DESC
    Arrays.sort(jobs, (a, b) -> b[1] - a[1]);
    
    int maxDeadline = 0;
    for (int[] job : jobs) maxDeadline = Math.max(maxDeadline, job[0]);
    
    int[] slots = new int[maxDeadline + 1];
    Arrays.fill(slots, -1);
    
    int count = 0, profit = 0;
    
    for (int[] job : jobs) {
        // Find latest available slot <= deadline
        for (int j = job[0]; j > 0; j-) {
            if (slots[j] == -1) {
                slots[j] = job[1];
                count++;
                profit += job[1];
                break;
            }
        }
    }
    
    return new int[]{count, profit};
}
```

### Mind-Map Anchor

```
JOB SEQUENCING
      |
      v
+------------+
| Sort by profit DESC    |
| For each job, find     |
| latest slot <= deadline|
| Assign if slot free    |
+------------+
```

**Memory phrase:** "Highest profit first, latest available slot"


--

# PART 3: ADDITIONAL HIGH-PRIORITY GREEDY PATTERNS

## PATTERN 18: Boats to Save People (LC 881)

### Pattern Recognition Signal

> **When you see:** "Pair items with weight limit"
> **Instant thought:** "Sort, two pointers! Heaviest with lightest"

### Visual Dry Run

```
people = [3, 2, 2, 1], limit = 3

Sort: [1, 2, 2, 3]

Two pointers:
left=0 (1), right=3 (3): 1+3=4 > 3, only 3 fits -> boats=1, right-
left=0 (1), right=2 (2): 1+2=3 <= 3, both fit -> boats=2, left++, right-
left=1 (2), right=1: left >= right, done

Answer: 2 boats
```

### The Code

```java
public int numRescueBoats(int[] people, int limit) {
    Arrays.sort(people);
    int boats = 0;
    int left = 0, right = people.length - 1;
    
    while (left <= right) {
        if (people[left] + people[right] <= limit) {
            left++;  // Light person fits with heavy
        }
        right-;  // Heavy person always goes
        boats++;
    }
    
    return boats;
}
```

### Mind-Map Anchor

```
BOATS TO SAVE PEOPLE
        |
        v
+-----------+
| Sort weights         |
| Two pointers         |
| Try pair light+heavy |
| Heavy always goes    |
+-----------+
```

**Memory phrase:** "Sort, pair lightest with heaviest if fits"

--

## PATTERN 19: Assign Cookies (LC 455)

### Pattern Recognition Signal

> **When you see:** "Assign items to satisfy requirements"
> **Instant thought:** "Sort both, greedy match smallest!"

### Visual Dry Run

```
g (greed) = [1, 2, 3]  (children's greed factors)
s (size)  = [1, 1]     (cookie sizes)

Sort both: g = [1, 2, 3], s = [1, 1]

child=0, cookie=0:
  s[0]=1 >= g[0]=1? YES -> child satisfied, child++, cookie++

child=1, cookie=1:
  s[1]=1 >= g[1]=2? NO -> cookie++

cookie=2 >= s.length -> done

Answer: 1 child satisfied
```

### Mind-Map Anchor

```
ASSIGN COOKIES
      |
      v
+-----------+
| Sort greed factors   |
| Sort cookie sizes    |
| Match smallest first |
| Move cookie ptr always|
+-----------+
```

### The Code

```java
public int findContentChildren(int[] g, int[] s) {
    Arrays.sort(g);  // Greed factors
    Arrays.sort(s);  // Cookie sizes
    
    int child = 0, cookie = 0;
    
    while (child < g.length && cookie < s.length) {
        if (s[cookie] >= g[child]) {
            child++;  // Child satisfied
        }
        cookie++;  // Try next cookie
    }
    
    return child;
}
```

**Memory phrase:** "Sort both, smallest cookie to smallest greed"

--

## PATTERN 20: Lemonade Change (LC 860)

### Pattern Recognition Signal

> **When you see:** "Make change with limited bills"
> **Instant thought:** "Greedy! Use larger bills first for change"

### Visual Dry Run

```
bills = [5, 5, 5, 10, 20]

Customer pays $5: fives=1
Customer pays $5: fives=2
Customer pays $5: fives=3
Customer pays $10: change $5, fives=2, tens=1
Customer pays $20: change $15
  Try $10+$5: tens=0, fives=1 -> SUCCESS

Answer: true
```

### The Code

```java
public boolean lemonadeChange(int[] bills) {
    int fives = 0, tens = 0;
    
    for (int bill : bills) {
        if (bill == 5) {
            fives++;
        } else if (bill == 10) {
            if (fives == 0) return false;
            fives-;
            tens++;
        } else {  // bill == 20
            if (tens > 0 && fives > 0) {
                tens-;
                fives-;
            } else if (fives >= 3) {
                fives -= 3;
            } else {
                return false;
            }
        }
    }
    
    return true;
}
```

### Mind-Map Anchor

```
LEMONADE CHANGE
      |
      v
+-----------+
| Track fives and tens |
| For $20: prefer      |
| $10+$5 over 3x$5     |
| Greedy: use larger   |
+-----------+
```

**Memory phrase:** "Track bills, prefer larger denominations for change"

--

## PATTERN 21: Maximum Units on a Truck (LC 1710)

### Pattern Recognition Signal

> **When you see:** "Maximum value with capacity limit"
> **Instant thought:** "Fractional knapsack! Sort by value, take greedily"

### Visual Dry Run

```
boxTypes = [[1,3], [2,2], [3,1]], truckSize = 4
           [boxes, units per box]

Sort by units DESC: [[1,3], [2,2], [3,1]]

Take greedily:
[1,3]: take 1 box, units = 1*3 = 3, remaining = 3
[2,2]: take 2 boxes, units = 3 + 2*2 = 7, remaining = 1
[3,1]: take 1 box (only 1 space), units = 7 + 1*1 = 8, remaining = 0

Answer: 8 units
```

### Mind-Map Anchor

```
MAX UNITS TRUCK
      |
      v
+-----------+
| Sort by units DESC   |
| Take as many as fit  |
| Greedy: best value   |
| first                |
+-----------+
```

### The Code

```java
public int maximumUnits(int[][] boxTypes, int truckSize) {
    // Sort by units per box DESC
    Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);
    
    int units = 0;
    
    for (int[] box : boxTypes) {
        int take = Math.min(box[0], truckSize);
        units += take * box[1];
        truckSize -= take;
        if (truckSize == 0) break;
    }
    
    return units;
}
```

**Memory phrase:** "Sort by value, take as many as possible"

--

## PATTERN 22: Minimum Cost to Connect Sticks (LC 1167)

### Pattern Recognition Signal

> **When you see:** "Combine items with cost = sum"
> **Instant thought:** "Min-heap! Always combine two smallest"

### Visual Dry Run

```
sticks = [2, 4, 3]

Min-heap: [2, 3, 4]

Combine 2+3=5, cost=5, heap=[4, 5]
Combine 4+5=9, cost=5+9=14, heap=[9]

Answer: 14
```

### The Code

```java
public int connectSticks(int[] sticks) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    for (int s : sticks) heap.offer(s);
    
    int cost = 0;
    
    while (heap.size() > 1) {
        int first = heap.poll();
        int second = heap.poll();
        int combined = first + second;
        cost += combined;
        heap.offer(combined);
    }
    
    return cost;
}
```

### Mind-Map Anchor

```
CONNECT STICKS
      |
      v
+-----------+
| Min-heap             |
| Always combine two   |
| smallest             |
| Add combined back    |
+-----------+
```

**Memory phrase:** "Min-heap, combine smallest two, repeat"

--

## PATTERN 23: Reorganize String (LC 767)

### Pattern Recognition Signal

> **When you see:** "Rearrange so no adjacent same"
> **Instant thought:** "Max-heap! Place most frequent, then next"

### Visual Dry Run

```
s = "aab"

Count: a=2, b=1
Max-heap: [(2,'a'), (1,'b')]

Round 1:
  Pop (2,'a'), pop (1,'b')
  Append "ab"
  Push back (1,'a') if count > 0
  Heap: [(1,'a')]

Round 2:
  Only (1,'a') left, count=1 <= 1, append "a"

Result: "aba"
```

### Mind-Map Anchor

```
REORGANIZE STRING
        |
        v
+------------+
| Max-heap by frequency  |
| Take top 2, place both |
| Put back if count > 0  |
| Fail if last count > 1 |
+------------+
```

### The Code

```java
public String reorganizeString(String s) {
    int[] count = new int[26];
    for (char c : s.toCharArray()) count[c - 'a']++;
    
    // Max-heap: (count, char)
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
    for (int i = 0; i < 26; i++) {
        if (count[i] > 0) heap.offer(new int[]{count[i], i});
    }
    
    StringBuilder result = new StringBuilder();
    
    while (heap.size() >= 2) {
        int[] first = heap.poll();
        int[] second = heap.poll();
        
        result.append((char)(first[1] + 'a'));
        result.append((char)(second[1] + 'a'));
        
        if (-first[0] > 0) heap.offer(first);
        if (-second[0] > 0) heap.offer(second);
    }
    
    if (!heap.isEmpty()) {
        int[] last = heap.poll();
        if (last[0] > 1) return "";  // Can't reorganize
        result.append((char)(last[1] + 'a'));
    }
    
    return result.toString();
}
```

### Mind-Map Anchor

```
REORGANIZE STRING
        |
        v
+------------+
| Max-heap by frequency  |
| Take top 2, place both |
| Put back if count > 0  |
| Fail if last count > 1 |
+------------+
```

**Memory phrase:** "Max-heap, alternate top two frequencies"

--

## PATTERN 24: Two City Scheduling (LC 1029)

### Pattern Recognition Signal

> **When you see:** "Assign n items to A, n to B, minimize cost"
> **Instant thought:** "Sort by cost difference! Greedy assignment"

### The Key Insight

Sort by (costA - costB). First n go to A (where A is relatively cheaper), rest to B.

### Visual Dry Run

```
costs = [[10,20], [30,200], [400,50], [30,20]]

Differences (costA - costB):
[10,20]: 10-20 = -10
[30,200]: 30-200 = -170
[400,50]: 400-50 = 350
[30,20]: 30-20 = 10

Sort by diff: [[30,200], [10,20], [30,20], [400,50]]
              -170      -10      10       350

First n=2 go to A: 30 + 10 = 40
Last n=2 go to B: 20 + 50 = 70

Answer: 40 + 70 = 110
```

### The Code

```java
public int twoCitySchedCost(int[][] costs) {
    // Sort by (costA - costB)
    Arrays.sort(costs, (a, b) -> (a[0] - a[1]) - (b[0] - b[1]));
    
    int total = 0;
    int n = costs.length / 2;
    
    for (int i = 0; i < n; i++) {
        total += costs[i][0];      // First n to city A
        total += costs[i + n][1];  // Last n to city B
    }
    
    return total;
}
```

### Mind-Map Anchor

```
TWO CITY SCHEDULING
        |
        v
+------------+
| Sort by (costA-costB)  |
| First n -> city A      |
| Last n -> city B       |
| Greedy by difference   |
+------------+
```

**Memory phrase:** "Sort by cost difference, split in half"

--

# QUICK REFERENCE: All 24 Greedy & Interval Patterns

| # | Pattern | Key Technique |
|--|-----|--------|
| 1 | Merge Intervals | Sort by start, merge overlaps |
| 2 | Insert Interval | Three phases: before, merge, after |
| 3 | Non-overlapping | Sort by END, greedy select |
| 4 | Meeting Rooms I | Sort, check adjacent overlap |
| 5 | Meeting Rooms II | Min-heap of ends / sweep line |
| 6 | Min Arrows | Sort by end, shoot at end |
| 7 | Interval Intersections | Two pointers, max start min end |
| 8 | Gas Station | Total check, reset on negative |
| 9 | Candy | Two passes: L->R, R->L |
| 10 | Partition Labels | Last occurrence, extend end |
| 11 | Jump Game | Track farthest reachable |
| 12 | Jump Game II | Level-by-level BFS |
| 13 | Task Scheduler | Max freq determines gaps |
| 14 | Queue Reconstruction | Sort height DESC, insert at k |
| 15 | Min Platforms | Sweep line |
| 16 | Activity Selection | Sort by end, greedy |
| 17 | Job Sequencing | Sort profit DESC, latest slot |
| 18 | Boats | Sort, two pointers |
| 19 | Assign Cookies | Sort both, match smallest |
| 20 | Lemonade Change | Track bills, prefer larger |
| 21 | Max Units Truck | Sort by value, take greedily |
| 22 | Connect Sticks | Min-heap, combine smallest |
| 23 | Reorganize String | Max-heap, alternate top 2 |
| 24 | Two City Scheduling | Sort by cost difference |

--

## Greedy vs DP Decision Tree

```
Can I make a LOCAL choice that's always optimal?
  |
  YES -> GREEDY
  |       - Intervals: sort by start/end
  |       - Selection: pick best available
  |       - Scheduling: earliest deadline
  |
  NO -> Need to consider ALL choices -> DP
        - Knapsack: take or skip
        - Paths: all routes
        - Subsequences: all combinations
```

--

*End of Greedy & Intervals Patterns Deep Dive - 24 Patterns for L5 MAANG*

