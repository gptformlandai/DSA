# Sorting Patterns Deep Dive (MAANG L5 Coverage)

--

# INDEX

| Category | Patterns |
|-----|-----|
| [Core Algorithms](#the-7-sorting-algorithms-you-must-know) | Quick, Merge, Heap, Counting, etc. |
| [Fundamental](#pattern-0-sort-colors-lc-75) | Patterns 0-4 |
| [Quick Select](#pattern-5-kth-largest-element-lc-215) | Patterns 5-8 |
| [Intervals](#pattern-9-merge-intervals-lc-56) | Patterns 9-14 |
| [Custom Sort](#pattern-4-largest-number-lc-179) | Patterns 15-18 |

--

# The One Sentence That Unlocks All Sorting Problems

> **"Sorting transforms CHAOS into ORDER — enabling binary search, two pointers, and greedy approaches."**

--

# 🌟 ZERO TO HERO: Understanding Sorting

## Why Sorting Matters

```
UNSORTED: Can't do much efficiently
[5, 2, 8, 1, 9] → Linear search O(n)

SORTED: Unlocks powerful techniques
[1, 2, 5, 8, 9] → Binary search O(log n)
                → Two pointers O(n)
                → Merge intervals O(n)
```

--

# The 7 Sorting Algorithms You Must Know

## Algorithm Comparison Table

| Algorithm | Time (Avg) | Time (Worst) | Space | Stable | In-Place |
|------|------|-------|----|----|-----|
| Quick Sort | O(n log n) | O(n²) | O(log n) | No | Yes |
| Merge Sort | O(n log n) | O(n log n) | O(n) | Yes | No |
| Heap Sort | O(n log n) | O(n log n) | O(1) | No | Yes |
| Counting Sort | O(n + k) | O(n + k) | O(k) | Yes | No |
| Bucket Sort | O(n + k) | O(n²) | O(n) | Yes | No |
| Radix Sort | O(d·n) | O(d·n) | O(n + k) | Yes | No |
| Tim Sort | O(n log n) | O(n log n) | O(n) | Yes | No |

--

## 1. Quick Sort

```java
void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }
}

int partition(int[] arr, int low, int high) {
    int pivot = arr[high];  // Choose last as pivot
    int i = low - 1;        // Index of smaller element
    
    for (int j = low; j < high; j++) {
        if (arr[j] <= pivot) {
            i++;
            swap(arr, i, j);
        }
    }
    swap(arr, i + 1, high);
    return i + 1;
}
```

**Use when:** General purpose, in-place needed

--

## 2. Merge Sort

```java
void mergeSort(int[] arr, int left, int right) {
    if (left < right) {
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }
}

void merge(int[] arr, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;
    
    while (i <= mid && j <= right) {
        if (arr[i] <= arr[j]) temp[k++] = arr[i++];
        else temp[k++] = arr[j++];
    }
    while (i <= mid) temp[k++] = arr[i++];
    while (j <= right) temp[k++] = arr[j++];
    
    System.arraycopy(temp, 0, arr, left, temp.length);
}
```

**Use when:** Stable sort needed, linked lists, counting inversions

--

## 3. Dutch National Flag (3-Way Partition)

```java
void sortColors(int[] nums) {
    int low = 0, mid = 0, high = nums.length - 1;
    
    while (mid <= high) {
        if (nums[mid] == 0) {
            swap(nums, low++, mid++);
        } else if (nums[mid] == 1) {
            mid++;
        } else {
            swap(nums, mid, high-);
        }
    }
}
```

**Use when:** 3 distinct values, partition around pivot

--

# When to Use Which Sorting

```
┌─────────────────────────────────────────────────────────────┐
│                  SORTING DECISION TREE                      │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  Need stable sort? ──YES──► Merge Sort / Tim Sort           │
│         │                                                   │
│        NO                                                   │
│         │                                                   │
│  Small range integers? ──YES──► Counting Sort               │
│         │                                                   │
│        NO                                                   │
│         │                                                   │
│  Need O(1) space? ──YES──► Heap Sort                        │
│         │                                                   │
│        NO                                                   │
│         │                                                   │
│  General purpose ──────────► Quick Sort                     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

--

# PATTERN 0: Sort Colors (LC 75) ⭐⭐

## Pattern Recognition Signal

**When you see:** "sort array with 3 values", "Dutch National Flag"

**Instant thought:** "Three pointers! low, mid, high."

--

## The Mental Model: "The Flag Sorter"

```
Imagine sorting balls into 3 buckets: Red(0), White(1), Blue(2)

Three regions:
[0...low-1]  → All 0s (Red)
[low...mid-1] → All 1s (White)  
[high+1...n-1] → All 2s (Blue)
[mid...high] → Unsorted

Process mid pointer:
- See 0? Swap with low, advance both
- See 1? Just advance mid
- See 2? Swap with high, only decrease high
```

--

## Visual Dry Run

**Input:** `[2, 0, 2, 1, 1, 0]`

```
═══════════════════════════════════════════════════════════════

Initial: [2, 0, 2, 1, 1, 0]
          L
          M
                         H

═══════════════════════════════════════════════════════════════

nums[mid]=2: Swap with high, high-
         [0, 0, 2, 1, 1, 2]
          L
          M
                      H

═══════════════════════════════════════════════════════════════

nums[mid]=0: Swap with low, low++, mid++
         [0, 0, 2, 1, 1, 2]
             L
             M
                      H

═══════════════════════════════════════════════════════════════

... continue until mid > high

Final: [0, 0, 1, 1, 2, 2] ✓
```

--

## The Code

```java
void sortColors(int[] nums) {
    int low = 0, mid = 0, high = nums.length - 1;
    
    while (mid <= high) {
        if (nums[mid] == 0) {
            // Swap with low region, advance both
            swap(nums, low++, mid++);
        } else if (nums[mid] == 1) {
            // Already in place, just advance
            mid++;
        } else {
            // Swap with high region, only decrease high
            swap(nums, mid, high-);
            // Don't advance mid! New element needs checking
        }
    }
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Advancing mid after swap with high | New element unchecked | Only high- |
| Using < instead of <= | Miss last element | Use mid <= high |

--

# PATTERN 5: Kth Largest Element (LC 215) ⭐⭐

## Pattern Recognition Signal

**When you see:** "Kth largest", "Kth smallest"

**Instant thought:** "Quick Select! O(n) average."

--

## The Mental Model: "Partial Sorting"

```
We don't need full sort!
Just partition until pivot lands at position n-k.

Array: [3, 2, 1, 5, 6, 4], k=2
Target index: 6-2 = 4 (0-indexed)

After partition, if pivot at index 4 → that's our answer!
```

--

## Visual Dry Run

**Input:** `[3, 2, 1, 5, 6, 4]`, k=2 (find 2nd largest)

```
═══════════════════════════════════════════════════════════════

Target index: n - k = 6 - 2 = 4

Initial: [3, 2, 1, 5, 6, 4]
                          ^pivot

═══════════════════════════════════════════════════════════════

Step 1: Partition with pivot = 4
  i = -1, scan j from 0 to 4

  j=0: nums[0]=3 <= 4? YES → i=0, swap(0,0) → [3, 2, 1, 5, 6, 4]
  j=1: nums[1]=2 <= 4? YES → i=1, swap(1,1) → [3, 2, 1, 5, 6, 4]
  j=2: nums[2]=1 <= 4? YES → i=2, swap(2,2) → [3, 2, 1, 5, 6, 4]
  j=3: nums[3]=5 <= 4? NO
  j=4: nums[4]=6 <= 4? NO

  Final swap: swap(i+1, pivot) → swap(3, 5)
  Result: [3, 2, 1, 4, 6, 5]
                   ^
              pivotIndex = 3

═══════════════════════════════════════════════════════════════

Step 2: pivotIndex(3) < target(4) → search RIGHT half
  Partition [6, 5] with pivot = 5

  [3, 2, 1, 4, 6, 5]
                 ^pivot
  
  j=4: nums[4]=6 <= 5? NO
  
  Final swap: swap(4, 5)
  Result: [3, 2, 1, 4, 5, 6]
                      ^
                 pivotIndex = 4

═══════════════════════════════════════════════════════════════

Step 3: pivotIndex(4) == target(4) → FOUND!

Final: nums[4] = 5 (2nd largest element) ✓

Sorted verification: [1, 2, 3, 4, 5, 6] → 2nd largest = 5 ✓
```

--

## The Code

```java
int findKthLargest(int[] nums, int k) {
    int targetIndex = nums.length - k;
    return quickSelect(nums, 0, nums.length - 1, targetIndex);
}

int quickSelect(int[] nums, int left, int right, int target) {
    int pivotIndex = partition(nums, left, right);
    
    if (pivotIndex == target) {
        return nums[pivotIndex];
    } else if (pivotIndex < target) {
        return quickSelect(nums, pivotIndex + 1, right, target);
    } else {
        return quickSelect(nums, left, pivotIndex - 1, target);
    }
}

int partition(int[] nums, int left, int right) {
    int pivot = nums[right];
    int i = left - 1;
    
    for (int j = left; j < right; j++) {
        if (nums[j] <= pivot) {
            swap(nums, ++i, j);
        }
    }
    swap(nums, i + 1, right);
    return i + 1;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using k directly as index | Kth largest ≠ index k | Use n-k for 0-indexed |
| Not handling duplicates | May infinite loop | Randomize pivot |

--

# PATTERN 9: Merge Intervals (LC 56) ⭐⭐

## Pattern Recognition Signal

**When you see:** "intervals", "merge overlapping"

**Instant thought:** "Sort by start! Then merge adjacent."

--

## The Mental Model: "Calendar Merging"

```
Meetings on calendar - merge overlapping ones:

Before: [1,3], [2,6], [8,10], [15,18]
        |--|
          |---|
                  |-|
                        |--|

After:  [1,6], [8,10], [15,18]
        |----|
                  |-|
                        |--|
```

--

## Visual Dry Run

**Input:** `[[1,3], [2,6], [8,10], [15,18]]`

```
═══════════════════════════════════════════════════════════════

Step 1: Sort by start time (already sorted)
  [[1,3], [2,6], [8,10], [15,18]]

═══════════════════════════════════════════════════════════════

Step 2: Initialize result with first interval
  result = [[1,3]]
  
  Timeline:
  1--3
  |████|

═══════════════════════════════════════════════════════════════

Step 3: Process [2,6]
  last = [1,3], curr = [2,6]
  curr[0]=2 <= last[1]=3? YES → OVERLAP!
  
  Merge: last[1] = max(3, 6) = 6
  result = [[1,6]]
  
  Timeline:
  1-----6
  |█████████|

═══════════════════════════════════════════════════════════════

Step 4: Process [8,10]
  last = [1,6], curr = [8,10]
  curr[0]=8 <= last[1]=6? NO → No overlap
  
  Add new interval
  result = [[1,6], [8,10]]
  
  Timeline:
  1-----6     8--10
  |█████████|     |███|

═══════════════════════════════════════════════════════════════

Step 5: Process [15,18]
  last = [8,10], curr = [15,18]
  curr[0]=15 <= last[1]=10? NO → No overlap
  
  Add new interval
  result = [[1,6], [8,10], [15,18]]
  
  Timeline:
  1-----6     8--10        15--18
  |█████████|     |███|         |████|

═══════════════════════════════════════════════════════════════

Final: [[1,6], [8,10], [15,18]] ✓
```

--

## The Code

```java
int[][] merge(int[][] intervals) {
    if (intervals.length <= 1) return intervals;
    
    // Sort by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    List<int[]> result = new ArrayList<>();
    result.add(intervals[0]);
    
    for (int i = 1; i < intervals.length; i++) {
        int[] last = result.get(result.size() - 1);
        int[] curr = intervals[i];
        
        if (curr[0] <= last[1]) {
            // Overlap! Extend end
            last[1] = Math.max(last[1], curr[1]);
        } else {
            // No overlap, add new
            result.add(curr);
        }
    }
    
    return result.toArray(new int[result.size()][]);
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting to sort | Can't detect overlaps | Always sort first |
| Using < instead of <= | Miss adjacent [1,2],[2,3] | Use curr[0] <= last[1] |
| Not using max for end | Miss contained intervals | Use max(last[1], curr[1]) |

--

# PATTERN 10: Meeting Rooms II (LC 253) ⭐⭐

## Pattern Recognition Signal

**When you see:** "minimum rooms", "maximum concurrent"

**Instant thought:** "Sort + Min Heap! Track end times."

--

## Visual Dry Run

**Input:** `[[0,30], [5,10], [15,20]]`

```
═══════════════════════════════════════════════════════════════

Step 1: Sort by start time (already sorted)
  [[0,30], [5,10], [15,20]]

═══════════════════════════════════════════════════════════════

Step 2: Initialize heap with first meeting's end time
  Process [0,30]
  heap = [30]  (min heap of end times)
  
  Rooms: 1
  
  Timeline:
  Room 1: |████████████████████████████████████████| [0,30]
          0                                        30

═══════════════════════════════════════════════════════════════

Step 3: Process [5,10]
  Meeting starts at 5
  Earliest room frees at: heap.peek() = 30
  
  5 >= 30? NO → Need new room!
  heap.offer(10)
  heap = [10, 30]
  
  Rooms: 2
  
  Timeline:
  Room 1: |████████████████████████████████████████| [0,30]
  Room 2:      |████|                                [5,10]
          0    5   10                              30

═══════════════════════════════════════════════════════════════

Step 4: Process [15,20]
  Meeting starts at 15
  Earliest room frees at: heap.peek() = 10
  
  15 >= 10? YES → Reuse room!
  heap.poll() → removes 10
  heap.offer(20)
  heap = [20, 30]
  
  Rooms: 2 (reused!)
  
  Timeline:
  Room 1: |████████████████████████████████████████| [0,30]
  Room 2:      |████|    |████|                      [5,10], [15,20]
          0    5   10   15   20                    30

═══════════════════════════════════════════════════════════════

Final: heap.size() = 2 rooms needed ✓
```

--

## The Code

```java
int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Sort by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    // Min heap of end times
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    heap.offer(intervals[0][1]);
    
    for (int i = 1; i < intervals.length; i++) {
        // If earliest ending room is free, reuse it
        if (intervals[i][0] >= heap.peek()) {
            heap.poll();
        }
        // Add current meeting's end time
        heap.offer(intervals[i][1]);
    }
    
    return heap.size();
}
```

--

# MAANG Coverage Map

| Pattern | Problem | Difficulty | Frequency |
|-----|-----|------|------|
| 0 | Sort Colors (LC 75) | Medium | ⭐⭐⭐⭐⭐ |
| 5 | Kth Largest (LC 215) | Medium | ⭐⭐⭐⭐⭐ |
| 6 | Top K Frequent (LC 347) | Medium | ⭐⭐⭐⭐⭐ |
| 9 | Merge Intervals (LC 56) | Medium | ⭐⭐⭐⭐⭐ |
| 10 | Meeting Rooms II (LC 253) | Medium | ⭐⭐⭐⭐⭐ |
| 11 | Non-overlapping (LC 435) | Medium | ⭐⭐⭐⭐ |
| 15 | Largest Number (LC 179) | Medium | ⭐⭐⭐⭐ |

--

# Mastery Checklist

## Tier 1: Must Know
- [ ] Sort Colors (LC 75)
- [ ] Kth Largest Element (LC 215)
- [ ] Merge Intervals (LC 56)
- [ ] Top K Frequent Elements (LC 347)

## Tier 2: Interview Favorites
- [ ] Meeting Rooms II (LC 253)
- [ ] Non-overlapping Intervals (LC 435)
- [ ] Largest Number (LC 179)

## Tier 3: Differentiators
- [ ] Count of Smaller Numbers After Self (LC 315)
- [ ] Reverse Pairs (LC 493)

--

# Quick Reference Card

```
┌─────────────────────────────────────────────────────────────┐
│                   SORTING CHEAT SHEET                       │
├─────────────────────────────────────────────────────────────┤
│ QUICK SELECT (Kth element):                                 │
│   Partition until pivot at target index                     │
│   O(n) average, O(n²) worst                                 │
├─────────────────────────────────────────────────────────────┤
│ DUTCH NATIONAL FLAG (3 values):                             │
│   low, mid, high pointers                                   │
│   0 → swap low, advance both                                │
│   1 → advance mid only                                      │
│   2 → swap high, decrease high only                         │
├─────────────────────────────────────────────────────────────┤
│ MERGE INTERVALS:                                            │
│   Sort by start                                             │
│   Overlap if curr.start <= last.end                         │
│   Merge: extend end to max                                  │
├─────────────────────────────────────────────────────────────┤
│ MEETING ROOMS:                                              │
│   Sort by start + Min Heap of end times                     │
│   Heap size = rooms needed                                  │
└─────────────────────────────────────────────────────────────┘
```

--

# ADDITIONAL DETAILED PATTERNS

--

# PATTERN 2: Sort List (LC 148) ⭐

## Pattern Recognition Signal

**When you see:** "sort linked list", "O(n log n)", "constant space"

**Instant thought:** "Merge Sort! Find middle, split, merge."

--

## The Mental Model: "Divide and Conquer on a Chain"

```
Linked lists don't have random access, so Quick Sort is hard.
But Merge Sort works perfectly!

1. Find the middle (fast-slow pointers)
2. Split into two halves
3. Recursively sort each half
4. Merge the sorted halves

Why Merge Sort?
- Only needs sequential access (perfect for linked lists)
- Stable sort
- O(n log n) guaranteed
```

--

## Visual Dry Run

**Input:** `4 → 2 → 1 → 3`

```
═══════════════════════════════════════════════════════════════

Step 1: Find middle using slow/fast pointers
  
  4 → 2 → 1 → 3 → null
  S   F
  
  4 → 2 → 1 → 3 → null
      S       F
  
  slow = 2 (middle node)

═══════════════════════════════════════════════════════════════

Step 2: Split into two halves
  
  Left:  4 → 2 → null
  Right: 1 → 3 → null
  
  (Cut by setting mid.next = null)

═══════════════════════════════════════════════════════════════

Step 3: Recursively sort LEFT half (4 → 2)
  
  Find mid: slow = 4
  Split: Left = 4, Right = 2
  
  Merge 4 and 2:
    Compare 4 vs 2 → pick 2
    Compare 4 vs null → pick 4
    Result: 2 → 4 → null

═══════════════════════════════════════════════════════════════

Step 4: Recursively sort RIGHT half (1 → 3)
  
  Find mid: slow = 1
  Split: Left = 1, Right = 3
  
  Merge 1 and 3:
    Compare 1 vs 3 → pick 1
    Compare null vs 3 → pick 3
    Result: 1 → 3 → null

═══════════════════════════════════════════════════════════════

Step 5: Merge sorted halves
  
  Left:  2 → 4 → null
  Right: 1 → 3 → null
  
  dummy → ?
  
  Compare 2 vs 1 → pick 1
  dummy → 1
  
  Compare 2 vs 3 → pick 2
  dummy → 1 → 2
  
  Compare 4 vs 3 → pick 3
  dummy → 1 → 2 → 3
  
  Append remaining: 4
  dummy → 1 → 2 → 3 → 4

═══════════════════════════════════════════════════════════════

Final: 1 → 2 → 3 → 4 ✓
```

--

## The Code

```java
ListNode sortList(ListNode head) {
    // Base case: empty or single node
    if (head == null || head.next == null) return head;
    
    // Find middle and split
    ListNode mid = getMid(head);
    ListNode left = head;
    ListNode right = mid.next;
    mid.next = null;  // Cut the list
    
    // Recursively sort both halves
    left = sortList(left);
    right = sortList(right);
    
    // Merge sorted halves
    return merge(left, right);
}

ListNode getMid(ListNode head) {
    ListNode slow = head, fast = head.next;  // Note: fast starts at head.next
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;
}

ListNode merge(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            curr.next = l1;
            l1 = l1.next;
        } else {
            curr.next = l2;
            l2 = l2.next;
        }
        curr = curr.next;
    }
    
    curr.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| fast starting at head | Wrong middle for even length | Start fast at head.next |
| Not cutting the list | Infinite recursion | Set mid.next = null |
| Forgetting base case | Stack overflow | Check head == null or head.next == null |

--

## Mind-Map Anchor

**Memory phrase:** "Find mid, cut, sort both, merge back."

--

# PATTERN 4: Largest Number (LC 179) ⭐

## Pattern Recognition Signal

**When you see:** "largest number from array", "concatenate"

**Instant thought:** "Custom comparator! Compare a+b vs b+a."

--

## The Mental Model: "The Greedy Concatenation"

```
Given [3, 30, 34, 5, 9]
Which order gives largest number?

Naive: Sort descending by value? 9, 5, 34, 30, 3 → "9534303"
But wait: Is "330" or "303" larger? 330 > 303!

Key insight: Compare concatenations!
- "3" + "30" = "330"
- "30" + "3" = "303"
- 330 > 303, so "3" should come before "30"

Custom comparator: (a, b) → compare (b+a) vs (a+b)
```

--

## Visual Dry Run

**Input:** `[3, 30, 34, 5, 9]`

```
═══════════════════════════════════════════════════════════════

Step 1: Convert to strings
  ["3", "30", "34", "5", "9"]

═══════════════════════════════════════════════════════════════

Step 2: Custom sort using (b+a).compareTo(a+b)

  Compare "3" vs "30":
    "30" + "3" = "303"
    "3" + "30" = "330"
    "330" > "303" → "3" comes before "30" ✓

  Compare "3" vs "34":
    "34" + "3" = "343"
    "3" + "34" = "334"
    "343" > "334" → "34" comes before "3"

  Compare "9" vs "5":
    "5" + "9" = "59"
    "9" + "5" = "95"
    "95" > "59" → "9" comes before "5"

═══════════════════════════════════════════════════════════════

Step 3: Sorting process (showing key comparisons)

  Initial: ["3", "30", "34", "5", "9"]
  
  After sorting by (b+a) > (a+b):
  
  "9" vs all:  "9X" > "X9" for all X → "9" first
  "5" vs rest: "5X" > "X5" for 3,30,34 → "5" second
  "34" vs 3,30: "343" > "334", "3430" > "3034" → "34" third
  "3" vs "30": "330" > "303" → "3" fourth
  "30" last
  
  Sorted: ["9", "5", "34", "3", "30"]

═══════════════════════════════════════════════════════════════

Step 4: Concatenate result
  "9" + "5" + "34" + "3" + "30" = "9534330"

═══════════════════════════════════════════════════════════════

Final: "9534330" ✓

Verification:
  Other orderings would give smaller numbers:
  "9534303" (naive descending) < "9534330" ✓
```

--

## The Code

```java
String largestNumber(int[] nums) {
    // Convert to strings
    String[] strs = new String[nums.length];
    for (int i = 0; i < nums.length; i++) {
        strs[i] = String.valueOf(nums[i]);
    }
    
    // Custom sort: compare b+a vs a+b
    Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
    
    // Edge case: all zeros
    if (strs[0].equals("0")) return "0";
    
    // Build result
    StringBuilder sb = new StringBuilder();
    for (String s : strs) sb.append(s);
    
    return sb.toString();
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Sorting by numeric value | "9" vs "34": 34 > 9 but "9" should come first | Use string concatenation comparison |
| Not handling all zeros | Returns "000..." | Check if first element is "0" |
| Wrong comparator order | Smallest instead of largest | Use (b+a).compareTo(a+b) |

--

# PATTERN 6: Top K Frequent Elements (LC 347) ⭐⭐

## Pattern Recognition Signal

**When you see:** "top K", "most frequent"

**Instant thought:** "Bucket Sort O(n) or Min Heap O(n log k)!"

--

## The Mental Model: "Frequency Buckets"

```
Array: [1,1,1,2,2,3], k=2

Step 1: Count frequencies
  {1: 3, 2: 2, 3: 1}

Step 2: Create buckets by frequency
  bucket[1] = [3]      (frequency 1)
  bucket[2] = [2]      (frequency 2)
  bucket[3] = [1]      (frequency 3)

Step 3: Collect from highest bucket down
  bucket[3] → [1]
  bucket[2] → [2]
  Result: [1, 2]

Why O(n)? Max frequency is n, so max n buckets.
```

--

## Visual Dry Run

**Input:** `[1, 1, 1, 2, 2, 3]`, k=2

```
═══════════════════════════════════════════════════════════════

Step 1: Count frequencies
  
  Process each element:
  [1, 1, 1, 2, 2, 3]
   ^
  freq = {1: 1}
  
  [1, 1, 1, 2, 2, 3]
      ^
  freq = {1: 2}
  
  [1, 1, 1, 2, 2, 3]
         ^
  freq = {1: 3}
  
  [1, 1, 1, 2, 2, 3]
            ^
  freq = {1: 3, 2: 1}
  
  [1, 1, 1, 2, 2, 3]
               ^
  freq = {1: 3, 2: 2}
  
  [1, 1, 1, 2, 2, 3]
                  ^
  freq = {1: 3, 2: 2, 3: 1}

═══════════════════════════════════════════════════════════════

Step 2: Create buckets (index = frequency)
  
  buckets[0] = []
  buckets[1] = [3]     ← element 3 has frequency 1
  buckets[2] = [2]     ← element 2 has frequency 2
  buckets[3] = [1]     ← element 1 has frequency 3
  buckets[4] = []
  buckets[5] = []
  buckets[6] = []
  
  Visual:
  ┌─────────────────────────────────────────┐
  │ Frequency │  0  │  1  │  2  │  3  │ ... │
  ├───────────┼─────┼─────┼─────┼─────┼─────┤
  │ Elements  │ [ ] │ [3] │ [2] │ [1] │ [ ] │
  └─────────────────────────────────────────┘

═══════════════════════════════════════════════════════════════

Step 3: Collect from highest frequency (k=2 elements)
  
  Start from buckets[6] (max possible frequency = n)
  
  buckets[6] = [] → skip
  buckets[5] = [] → skip
  buckets[4] = [] → skip
  buckets[3] = [1] → add 1, result = [1], count = 1
  buckets[2] = [2] → add 2, result = [1, 2], count = 2
  
  count == k → STOP!

═══════════════════════════════════════════════════════════════

Final: [1, 2] (top 2 frequent elements) ✓

Verification:
  Element 1 appears 3 times (most frequent)
  Element 2 appears 2 times (second most frequent)
  Element 3 appears 1 time
```

--

## Bucket Sort Solution (O(n))

```java
int[] topKFrequent(int[] nums, int k) {
    // Count frequencies
    Map<Integer, Integer> freq = new HashMap<>();
    for (int num : nums) {
        freq.put(num, freq.getOrDefault(num, 0) + 1);
    }
    
    // Create buckets (index = frequency)
    List<Integer>[] buckets = new List[nums.length + 1];
    for (int i = 0; i < buckets.length; i++) {
        buckets[i] = new ArrayList<>();
    }
    
    for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
        buckets[entry.getValue()].add(entry.getKey());
    }
    
    // Collect from highest frequency
    int[] result = new int[k];
    int index = 0;
    
    for (int i = buckets.length - 1; i >= 0 && index < k; i-) {
        for (int num : buckets[i]) {
            result[index++] = num;
            if (index == k) break;
        }
    }
    
    return result;
}
```

--

## Min Heap Solution (O(n log k))

```java
int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int num : nums) {
        freq.put(num, freq.getOrDefault(num, 0) + 1);
    }
    
    // Min heap by frequency
    PriorityQueue<Integer> heap = new PriorityQueue<>(
        (a, b) -> freq.get(a) - freq.get(b)
    );
    
    for (int num : freq.keySet()) {
        heap.offer(num);
        if (heap.size() > k) heap.poll();  // Remove least frequent
    }
    
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
        result[i] = heap.poll();
    }
    
    return result;
}
```

--

# PATTERN 11: Non-overlapping Intervals (LC 435) ⭐

## Pattern Recognition Signal

**When you see:** "remove minimum intervals", "non-overlapping"

**Instant thought:** "Sort by END time! Greedy: keep earliest ending."

--

## The Mental Model: "The Activity Selection"

```
Classic greedy problem!

Sort by END time (not start time).
Why? Ending earlier leaves more room for future intervals.

[1,4], [2,3], [3,5]
Sorted by end: [2,3], [1,4], [3,5]

Pick [2,3] (ends earliest)
Skip [1,4] (overlaps with [2,3])
Pick [3,5] (starts at 3, [2,3] ends at 3, no overlap)

Removed: 1 interval
```

--

## Visual Dry Run

**Input:** `[[1,2], [2,3], [3,4], [1,3]]`

```
═══════════════════════════════════════════════════════════════

Step 1: Sort by END time
  
  Original: [[1,2], [2,3], [3,4], [1,3]]
  
  Sorted:   [[1,2], [2,3], [1,3], [3,4]]
                          ↑
            Note: [1,3] moved (end=3 > end=3, but came after [2,3])
  
  Actually: [[1,2], [2,3], [1,3], [3,4]]
  
  Timeline before:
  [1,2]:  |██|
  [2,3]:     |██|
  [1,3]:  |█████|
  [3,4]:        |██|
          1  2  3  4

═══════════════════════════════════════════════════════════════

Step 2: Initialize
  removed = 0
  prevEnd = 2 (first interval's end)
  
  KEEP: [1,2]
  
  Timeline:
  [1,2]:  |██|  ← KEPT
          1  2  3  4

═══════════════════════════════════════════════════════════════

Step 3: Process [2,3]
  curr[0]=2 < prevEnd=2? NO (2 is not < 2)
  
  No overlap! Keep this interval.
  prevEnd = 3
  
  KEEP: [2,3]
  
  Timeline:
  [1,2]:  |██|     ← KEPT
  [2,3]:     |██|  ← KEPT
          1  2  3  4

═══════════════════════════════════════════════════════════════

Step 4: Process [1,3]
  curr[0]=1 < prevEnd=3? YES
  
  OVERLAP! Remove this interval.
  removed = 1
  (prevEnd stays 3 - we keep the earlier ending one)
  
  REMOVE: [1,3]
  
  Timeline:
  [1,2]:  |██|        ← KEPT
  [2,3]:     |██|     ← KEPT
  [1,3]:  |█████|     ← REMOVED (overlaps)
          1  2  3  4

═══════════════════════════════════════════════════════════════

Step 5: Process [3,4]
  curr[0]=3 < prevEnd=3? NO (3 is not < 3)
  
  No overlap! Keep this interval.
  prevEnd = 4
  
  KEEP: [3,4]
  
  Timeline:
  [1,2]:  |██|        ← KEPT
  [2,3]:     |██|     ← KEPT
  [3,4]:        |██|  ← KEPT
          1  2  3  4

═══════════════════════════════════════════════════════════════

Final: removed = 1 ✓

Kept intervals: [1,2], [2,3], [3,4] (non-overlapping)
```

--

## The Code

```java
int eraseOverlapIntervals(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Sort by END time
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    
    int removed = 0;
    int prevEnd = intervals[0][1];
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] < prevEnd) {
            // Overlap! Remove current (keep the one ending earlier)
            removed++;
        } else {
            // No overlap, update prevEnd
            prevEnd = intervals[i][1];
        }
    }
    
    return removed;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Sorting by start time | Greedy doesn't work | Sort by END time |
| Using <= for overlap | Adjacent intervals don't overlap | Use < for overlap check |
| Updating prevEnd on overlap | Should keep earlier end | Only update when no overlap |

--

# PATTERN 15: Queue Reconstruction by Height (LC 406) ⭐

## Pattern Recognition Signal

**When you see:** "reconstruct queue", "height and position"

**Instant thought:** "Sort by height DESC, then insert by k value!"

--

## The Mental Model: "Tallest First"

```
People: [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]

Each person [h, k]: height h, k people taller in front.

Strategy: Insert tallest people first!
Why? When we insert shorter people later, they don't affect
     the "k" count of taller people already placed.

Sort by height DESC (if same height, by k ASC):
[7,0], [7,1], [6,1], [5,0], [5,2], [4,4]

Insert at index k:
[7,0] → [[7,0]]
[7,1] → [[7,0], [7,1]]
[6,1] → [[7,0], [6,1], [7,1]]
[5,0] → [[5,0], [7,0], [6,1], [7,1]]
[5,2] → [[5,0], [7,0], [5,2], [6,1], [7,1]]
[4,4] → [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
```

--

## Visual Dry Run

**Input:** `[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]`

```
═══════════════════════════════════════════════════════════════

Step 1: Sort by height DESC, then k ASC
  
  Original: [[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]
  
  Sorted:   [[7,0], [7,1], [6,1], [5,0], [5,2], [4,4]]
             h=7     h=7    h=6    h=5    h=5    h=4

═══════════════════════════════════════════════════════════════

Step 2: Insert [7,0] at index 0
  
  result = [[7,0]]
  
  Position:  0
            [7,0]
             ↑
             0 people taller in front ✓

═══════════════════════════════════════════════════════════════

Step 3: Insert [7,1] at index 1
  
  result = [[7,0], [7,1]]
  
  Position:  0      1
            [7,0]  [7,1]
                    ↑
                    1 person same/taller in front ✓

═══════════════════════════════════════════════════════════════

Step 4: Insert [6,1] at index 1
  
  result = [[7,0], [6,1], [7,1]]
  
  Position:  0      1      2
            [7,0]  [6,1]  [7,1]
                    ↑
                    1 person taller (7) in front ✓

═══════════════════════════════════════════════════════════════

Step 5: Insert [5,0] at index 0
  
  result = [[5,0], [7,0], [6,1], [7,1]]
  
  Position:  0      1      2      3
            [5,0]  [7,0]  [6,1]  [7,1]
             ↑
             0 people taller in front ✓

═══════════════════════════════════════════════════════════════

Step 6: Insert [5,2] at index 2
  
  result = [[5,0], [7,0], [5,2], [6,1], [7,1]]
  
  Position:  0      1      2      3      4
            [5,0]  [7,0]  [5,2]  [6,1]  [7,1]
                           ↑
                           2 people taller (7,7) in front ✓

═══════════════════════════════════════════════════════════════

Step 7: Insert [4,4] at index 4
  
  result = [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
  
  Position:  0      1      2      3      4      5
            [5,0]  [7,0]  [5,2]  [6,1]  [4,4]  [7,1]
                                         ↑
                                         4 people taller in front ✓

═══════════════════════════════════════════════════════════════

Final: [[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]] ✓
```

--

## The Code

```java
int[][] reconstructQueue(int[][] people) {
    // Sort: height DESC, then k ASC
    Arrays.sort(people, (a, b) -> {
        if (a[0] != b[0]) return b[0] - a[0];  // Height DESC
        return a[1] - b[1];                     // k ASC
    });
    
    List<int[]> result = new ArrayList<>();
    
    for (int[] person : people) {
        result.add(person[1], person);  // Insert at index k
    }
    
    return result.toArray(new int[people.length][]);
}
```

--

# PATTERN 18: Count of Smaller Numbers After Self (LC 315) ⭐⭐

## Pattern Recognition Signal

**When you see:** "count smaller elements to the right", "for each element"

**Instant thought:** "Merge Sort with counting! Count during merge."

--

## The Mental Model: "Counting During Merge"

```
When merging two sorted halves:
- Left half: [5, 2]  (sorted: [2, 5])
- Right half: [6, 1] (sorted: [1, 6])

When we pick from LEFT half, count how many from RIGHT
have already been placed (they're smaller and to the right).

This is the KEY insight: merge sort naturally compares
elements from left with elements from right!
```

--

## Visual Dry Run

**Input:** `[5, 2, 6, 1]`

```
═══════════════════════════════════════════════════════════════

Goal: For each element, count smaller elements to its right
  
  Index:    0  1  2  3
  Array:   [5, 2, 6, 1]
  
  Expected counts:
    5 → elements to right smaller than 5: [2, 1] → count = 2
    2 → elements to right smaller than 2: [1] → count = 1
    6 → elements to right smaller than 6: [1] → count = 1
    1 → elements to right smaller than 1: [] → count = 0
  
  Answer: [2, 1, 1, 0]

═══════════════════════════════════════════════════════════════

Step 1: Split array (tracking original indices)
  
  indices: [0, 1, 2, 3]
  values:  [5, 2, 6, 1]
  
  Left:  indices [0, 1], values [5, 2]
  Right: indices [2, 3], values [6, 1]

═══════════════════════════════════════════════════════════════

Step 2: Recursively sort LEFT [5, 2]
  
  Split: [5] and [2]
  Merge: Compare 5 vs 2
    2 < 5 → pick 2, rightCount = 1
    pick 5, counts[0] += 1 (one element from right was smaller)
  
  Sorted left: [2, 5] with indices [1, 0]
  counts = [1, 0, 0, 0]  (5 has 1 smaller to right within this subarray)

═══════════════════════════════════════════════════════════════

Step 3: Recursively sort RIGHT [6, 1]
  
  Split: [6] and [1]
  Merge: Compare 6 vs 1
    1 < 6 → pick 1, rightCount = 1
    pick 6, counts[2] += 1
  
  Sorted right: [1, 6] with indices [3, 2]
  counts = [1, 0, 1, 0]

═══════════════════════════════════════════════════════════════

Step 4: Merge sorted halves
  
  Left:  [2, 5] indices [1, 0]
  Right: [1, 6] indices [3, 2]
  rightCount = 0
  
  Compare 2 vs 1:
    1 < 2 → pick 1 (from right)
    rightCount = 1
  
  Compare 2 vs 6:
    2 < 6 → pick 2 (from left)
    counts[1] += rightCount → counts[1] = 0 + 1 = 1
  
  Compare 5 vs 6:
    5 < 6 → pick 5 (from left)
    counts[0] += rightCount → counts[0] = 1 + 1 = 2
  
  Pick remaining 6 (from right)
  
  Final sorted: [1, 2, 5, 6]
  counts = [2, 1, 1, 0]

═══════════════════════════════════════════════════════════════

Final: [2, 1, 1, 0] ✓

Verification:
  5 has 2 smaller elements to right (2, 1) ✓
  2 has 1 smaller element to right (1) ✓
  6 has 1 smaller element to right (1) ✓
  1 has 0 smaller elements to right ✓
```

--

## The Code (Simplified)

```java
int[] counts;
int[] indices;

List<Integer> countSmaller(int[] nums) {
    int n = nums.length;
    counts = new int[n];
    indices = new int[n];
    
    for (int i = 0; i < n; i++) indices[i] = i;
    
    mergeSort(nums, 0, n - 1);
    
    List<Integer> result = new ArrayList<>();
    for (int count : counts) result.add(count);
    return result;
}

void mergeSort(int[] nums, int left, int right) {
    if (left >= right) return;
    
    int mid = left + (right - left) / 2;
    mergeSort(nums, left, mid);
    mergeSort(nums, mid + 1, right);
    merge(nums, left, mid, right);
}

void merge(int[] nums, int left, int mid, int right) {
    int[] tempIndices = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;
    int rightCount = 0;  // Elements from right half that are smaller
    
    while (i <= mid && j <= right) {
        if (nums[indices[j]] < nums[indices[i]]) {
            tempIndices[k++] = indices[j++];
            rightCount++;  // This right element is smaller
        } else {
            counts[indices[i]] += rightCount;  // Add count for left element
            tempIndices[k++] = indices[i++];
        }
    }
    
    while (i <= mid) {
        counts[indices[i]] += rightCount;
        tempIndices[k++] = indices[i++];
    }
    
    while (j <= right) {
        tempIndices[k++] = indices[j++];
    }
    
    System.arraycopy(tempIndices, 0, indices, left, right - left + 1);
}
```

--

## Mind-Map Anchor

```
"COUNT SMALLER TO THE RIGHT"
           │
           ▼
┌─────────────────────────────────────────┐
│ Merge Sort with Counting               │
│ During merge:                          │
│ - Track rightCount (smaller from right)│
│ - When picking from left, add rightCount│
│ - Use indices array to track original  │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Merge sort counts inversions. Track indices, count during merge."

--

*End of Sorting Patterns Deep Dive*
