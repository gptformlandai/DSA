# Section 19 — Interval Patterns

---

## 1. What Problem Does This Solve?

Interval problems involve a collection of ranges `[start, end]` and ask questions like: merge overlapping ones, find gaps, check if a new interval overlaps, count simultaneous events, or select the maximum non-overlapping set. They appear heavily in scheduling, calendar, and range-covering problems.

---

## 2. Beginner-Friendly Intuition

Think of intervals as **time slots on a calendar**. Two meetings overlap if one starts before the other ends. Merging means combining overlapping meetings into a single block. Inserting a new meeting means finding where it fits and merging any conflicts.

The fundamental insight: **after sorting by start time**, two adjacent intervals either overlap (merge them) or don't (they're independent).

---

## 3. Real-World Analogy

**Meeting room booking:** You have a list of meeting times. Do any overlap? How many rooms do you need? What's the merged schedule? All classic interval problems.

**TV schedule:** Find all time slots not covered by any show (gaps). Or: is there any time when more than k shows are airing simultaneously?

---

## 4. Core Concept

### The Three Interval Relationships

Given intervals A = [a1, a2] and B = [b1, b2] (sorted so a1 ≤ b1):

```
Case 1: a2 < b1   → NO overlap (A ends before B starts)
Case 2: a2 >= b1  → OVERLAP (A's end is at or after B's start)
Case 3: A contains B or B extends beyond A → merge to [min(a1,b1), max(a2,b2)]
```

### Core Interval Operations

| Operation | Sort By | Technique |
|-----------|---------|----------|
| Merge overlapping | Start time | Scan, extend currentEnd |
| Insert new interval | — | Find overlap zone, merge |
| Non-overlapping count (min remove) | End time | Greedy: keep earliest-ending |
| Meeting rooms needed | Start time | Min-heap of end times |
| Meeting rooms II | Start time | Count max simultaneous meetings |

---

## 5. Pattern Recognition Signals

Use Interval algorithms when:
```
"Merge overlapping intervals"
"Insert interval"
"Meeting rooms" / "minimum rooms needed"
"Non-overlapping intervals" / "minimum removals"
"Minimum interval to include each query"
"Employee free time"
"Interval list intersections"
"My calendar" / "Event scheduling"
```

---

## 6. Step-by-Step Algorithm

### Merge Intervals
```
Step 1: Sort intervals by start time
Step 2: result = [intervals[0]]
Step 3: For each interval [s, e] from index 1:
    If s <= result.last().end:
        result.last().end = max(result.last().end, e)  ← extend
    Else:
        result.add([s, e])  ← no overlap, new separate interval
Return result
```

### Meeting Rooms II (Minimum Rooms)
```
Step 1: Sort by start time
Step 2: Min-heap tracks end times of ongoing meetings
Step 3: For each meeting [s, e]:
    If heap not empty AND heap.top() <= s:
        heap.poll()  ← reuse the room that just freed up
    heap.offer(e)    ← use a room (new or reused)
Return heap.size()  ← rooms simultaneously in use at peak
```

### Insert Interval
```
Step 1: result = []
Step 2: Add all intervals that END before newInterval.start
Step 3: Merge all intervals that OVERLAP with newInterval:
    newInterval = [min(starts), max(ends)]
Step 4: Add remaining intervals that START after newInterval.end
Return result
```

---

## 7. Dry Run with Example

### Example 1: Merge Intervals

**Input:** `[[1,3],[2,6],[8,10],[15,18]]`

```
Sort by start: [[1,3],[2,6],[8,10],[15,18]] (already sorted)
result = [[1,3]]

[2,6]: 2 <= 3 → overlap! extend: result=[[1,6]]
[8,10]: 8 > 6 → no overlap: result=[[1,6],[8,10]]
[15,18]: 15 > 10 → no overlap: result=[[1,6],[8,10],[15,18]]

Output: [[1,6],[8,10],[15,18]] ✓
```

### Example 2: Insert Interval

**Input:** `intervals=[[1,3],[6,9]]`, `newInterval=[2,5]`

```
Step 1 — Add intervals ending before newInterval.start=2:
  [1,3]: 3 >= 2 → overlaps with newInterval → STOP step 1

Step 2 — Merge overlapping:
  [1,3]: 3 >= 2 → overlaps. newInterval = [min(2,1), max(5,3)] = [1,5]
  [6,9]: 6 > 5 → no overlap → STOP step 2

After merge: newInterval = [1,5]

Step 3 — Add remaining: [6,9]

Output: [[1,5],[6,9]] ✓
```

### Example 3: Meeting Rooms II

**Input:** `[[0,30],[5,10],[15,20]]`

```
Sort by start: [[0,30],[5,10],[15,20]]
heap = [] (end times of active meetings)

[0,30]: heap empty → use room. heap=[30]
[5,10]: heap.top()=30 > 5 → conflict → need new room. heap=[10,30]
[15,20]: heap.top()=10 <= 15 → room freed! poll 10. heap=[30]. offer 20. heap=[20,30]

Peak heap size = 2

Output: 2 rooms needed ✓
```

---

## 8. Code Implementation

### Merge Intervals

```java
int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    List<int[]> result = new ArrayList<>();
    result.add(intervals[0]);
    for (int i = 1; i < intervals.length; i++) {
        int[] last = result.get(result.size() - 1);
        if (intervals[i][0] <= last[1]) {
            last[1] = Math.max(last[1], intervals[i][1]); // extend end
        } else {
            result.add(intervals[i]);
        }
    }
    return result.toArray(new int[0][]);
}
```

### Insert Interval

```java
int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0, n = intervals.length;
    // Add all intervals before newInterval
    while (i < n && intervals[i][1] < newInterval[0]) result.add(intervals[i++]);
    // Merge overlapping
    while (i < n && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    result.add(newInterval);
    // Add remaining
    while (i < n) result.add(intervals[i++]);
    return result.toArray(new int[0][]);
}
```

### Meeting Rooms II (Minimum Rooms)

```java
int minMeetingRooms(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    PriorityQueue<Integer> heap = new PriorityQueue<>(); // stores end times
    for (int[] interval : intervals) {
        if (!heap.isEmpty() && heap.peek() <= interval[0])
            heap.poll(); // reuse room
        heap.offer(interval[1]);
    }
    return heap.size();
}
```

### Non-Overlapping Intervals (Min Removals)

```java
int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // sort by END
    int lastEnd = Integer.MIN_VALUE, removals = 0;
    for (int[] interval : intervals) {
        if (interval[0] >= lastEnd) lastEnd = interval[1]; // keep
        else removals++;                                     // remove
    }
    return removals;
}
```

### Interval List Intersections

```java
int[][] intervalIntersection(int[][] A, int[][] B) {
    List<int[]> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < A.length && j < B.length) {
        int lo = Math.max(A[i][0], B[j][0]);
        int hi = Math.min(A[i][1], B[j][1]);
        if (lo <= hi) result.add(new int[]{lo, hi}); // valid intersection
        // Move pointer with smaller end (it can no longer overlap with others)
        if (A[i][1] < B[j][1]) i++;
        else j++;
    }
    return result.toArray(new int[0][]);
}
```

---

## 9. Time Complexity

| Problem | Complexity | Bottleneck |
|---------|-----------|-----------|
| Merge Intervals | O(n log n) | Sorting |
| Insert Interval | O(n) | Single pass (pre-sorted) |
| Meeting Rooms II | O(n log n) | Sort + heap operations |
| Non-Overlapping | O(n log n) | Sorting |
| Interval Intersections | O(m + n) | Two-pointer scan |

---

## 10. Space Complexity

| Problem | Space |
|---------|-------|
| Merge Intervals | O(n) for result list |
| Meeting Rooms II | O(n) for heap |
| Non-Overlapping | O(1) |
| Insert Interval | O(n) for result list |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Single interval | Return as-is (merge/insert both work) |
| All intervals same | Merge into one |
| New interval completely before all | Prepend |
| New interval completely after all | Append |
| New interval covers all existing | Result = just newInterval |
| Touching intervals [1,3] and [3,5] | Overlap check: `intervals[i][0] <= lastEnd` → merge to [1,5] |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Forgetting to sort before merging
// Without sort, non-adjacent overlapping intervals are missed

// MISTAKE 2: Wrong sort key for different problems
// Merge: sort by START time
// Non-overlapping removal: sort by END time (greedy - keep earliest-ending)

// MISTAKE 3: Wrong overlap condition
if (intervals[i][0] < last[1])   // WRONG: misses [1,3],[3,5] (touching counts as overlap)
if (intervals[i][0] <= last[1])  // CORRECT: touching intervals should merge

// MISTAKE 4: In Insert Interval, wrong condition for "before new interval"
while (i < n && intervals[i][0] < newInterval[0]) // WRONG: uses start comparison
while (i < n && intervals[i][1] < newInterval[0]) // CORRECT: interval ends before new starts

// MISTAKE 5: Not updating newInterval while merging in Insert
while (i < n && intervals[i][0] <= newInterval[1]) {
    newInterval[1] = Math.max(newInterval[1], intervals[i][1]); // MUST update both ends
    newInterval[0] = Math.min(newInterval[0], intervals[i][0]); // needed too
    i++;
}
```

---

## 13. Interview-Level Explanation

**Q: "How do you determine the minimum number of meeting rooms needed?"**

> "I sort meetings by start time and use a min-heap of end times. For each new meeting, I check if the earliest-ending meeting is done (its end ≤ current meeting's start). If so, I pop it from the heap and reuse that room. Either way, I push the current meeting's end time. The heap size at any point represents rooms currently in use. The maximum heap size throughout the process is the minimum rooms needed."

**Q: "Why sort by end time for non-overlapping intervals but start time for merge?"**

> "For merge, I need to know which interval comes 'first' to decide if the current one extends the previous. Sorting by start gives me that order. For non-overlapping (keeping maximum intervals), I sort by end time because choosing the interval that ends earliest leaves the most future time available — a greedy argument."

---

## 14. Real-World Use Cases

| Application | Interval Usage |
|------------|---------------|
| **Calendar apps** | Merge overlapping events |
| **Conference scheduling** | Minimum rooms needed |
| **Network packet merging** | Combine contiguous TCP segments |
| **Healthcare** | Medication dosing windows |
| **Airline systems** | Flight schedule management |
| **Video streaming** | Buffer intervals, segment loading |

---

## 15. Variations of This Pattern

| Variation | Key Difference | Example |
|-----------|---------------|---------|
| Merge overlapping | Combine all overlapping | Merge Intervals |
| Insert new interval | May create cascading merges | Insert Interval |
| Minimum rooms | Heap of end times | Meeting Rooms II |
| Non-overlapping (max keep) | Sort by end, greedy | Non-Overlapping Intervals |
| Intersection | Two-pointer on two lists | Interval List Intersections |
| Employee free time | Merge all → find gaps | Employee Free Time |
| Minimum covers | Cover range with min intervals | Video Stitching |

---

## 16. Practice Problems

### Easy — Foundation
1. **Meeting Rooms** (LeetCode #252)
   - *Task:* Can a person attend all meetings? (No overlap check)
   - *Hint:* Sort by start. Any interval[i].start < interval[i-1].end → false.

2. **Summary Ranges** (LeetCode #228)
   - *Task:* Convert sorted array to range list ["1->3","6","8->9"].
   - *Hint:* Two pointers to find contiguous ranges.

3. **Count Days Worked** (range coverage check)
   - *Task:* Count total days covered by intervals.
   - *Hint:* Merge all intervals, sum up (end - start + 1).

### Medium — Core Operations
1. **Merge Intervals** (LeetCode #56)
   - *Task:* Merge all overlapping intervals.
   - *Hint:* Sort by start. Extend last interval's end if overlap.

2. **Insert Interval** (LeetCode #57)
   - *Task:* Insert a new interval into a sorted list, merging overlaps.
   - *Hint:* Three phases: skip non-overlapping left, merge, add non-overlapping right.

3. **Non-Overlapping Intervals** (LeetCode #435)
   - *Task:* Minimum removals to eliminate overlaps.
   - *Hint:* Sort by end. Greedy keep earliest-ending.

4. **Meeting Rooms II** (LeetCode #253)
   - *Task:* Minimum rooms for all meetings.
   - *Hint:* Sort by start + min-heap of end times.

5. **Interval List Intersections** (LeetCode #986)
   - *Task:* Find all intersections of two sorted interval lists.
   - *Hint:* Two pointers. Move pointer with smaller end after each step.

### Hard — Advanced
1. **Employee Free Time** (LeetCode #759)
   - *Task:* Find all time slots free for all employees.
   - *Hint:* Flatten all intervals, sort, merge → find gaps between merged intervals.

2. **Minimum Number of Arrows to Burst Balloons** (LeetCode #452)
   - *Task:* Minimum arrows to burst all balloons.
   - *Hint:* Sort by end. Arrow at current end hits all overlapping.

3. **Video Stitching** (LeetCode #1024)
   - *Task:* Minimum clips to cover [0, T].
   - *Hint:* Sort by start. At each coverage boundary, greedily extend furthest.

---

## 17. How to Know You Have Mastered Interval Patterns

You have mastered this topic when you can:
- [ ] Immediately know whether to sort by start or end time for a given problem
- [ ] Implement Merge Intervals from memory
- [ ] Implement Insert Interval (three-phase approach) correctly
- [ ] Implement Meeting Rooms II with a min-heap
- [ ] Distinguish "touching intervals" from "non-overlapping" (≤ vs <)
- [ ] Implement Interval Intersections with two pointers
- [ ] Derive the non-overlapping greedy argument from scratch
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Are intervals [1,3] and [3,5] overlapping? Should they be merged?

2. For Merge Intervals, why sort by START time rather than end time?

3. Meeting Rooms II — if all meetings end before the next one starts, how many rooms do you need?

4. The min-heap in Meeting Rooms II stores end times. When do you pop from it?

5. For Insert Interval with newInterval=[4,8] and intervals=[[1,3],[6,9]], what are the three phases?

6. In Non-Overlapping Intervals, you sort by END time. If two intervals have the same end time, does order matter?

7. In Interval Intersections with two pointers, why do you always advance the pointer with the smaller end?

8. What is the maximum number of overlapping intervals at any single point in time? How would you compute it?

> **Answers:**
> 1. They're "touching" at point 3. Most problems treat them as overlapping and merge to [1,5]. Use `<=` in overlap check.
> 2. Sorting by start ensures we process intervals in chronological order — we can then extend the "current interval" by comparing its end to each next interval's start.
> 3. 1 room — all meetings are sequential, never simultaneous.
> 4. When the smallest end time in the heap (earliest-ending meeting) is ≤ the current meeting's start time — meaning that room is free.
> 5. Phase 1 (no overlap, add as-is): [1,3] ends at 3 < 4=newStart → add [1,3]. Phase 2 (merge): [6,9] starts at 6 ≤ 8=newEnd → merge to [min(4,6), max(8,9)]=[4,9]. Phase 3 (no overlap remaining): nothing left. Result: [[1,3],[4,9]].
> 6. No. Both have the same end, so either can be chosen; choosing one removes the same amount of future time.
> 7. The interval with the smaller end can no longer produce any future intersections with the other list's current element (since future elements have start ≥ current start). So advance it to possibly find a new intersection.
> 8. Maximum simultaneous overlaps. Compute via events: for each interval add +1 at start and -1 at (end+1). Sort events, find max running sum.

---

**Next →** `../20_Bit_Manipulation/01_Bit_Manipulation.md`
