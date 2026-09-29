# Sorting Patterns Deep Dive (MAANG L5 Coverage)

---

# INDEX

| Category | Patterns |
|----------|----------|
| [Core Algorithms](#the-7-sorting-algorithms) | Quick, Merge, Heap, Counting, etc. |
| [Fundamental](#fundamental-sorting-patterns) | Patterns 0-4 |
| [Quick Select](#quick-select-patterns) | Patterns 5-8 |
| [Intervals](#interval-patterns) | Patterns 9-14 |
| [Custom Sort](#custom-sorting-patterns) | Patterns 15-18 |

---

# The One Sentence That Unlocks All Sorting Problems

> **"Sorting transforms CHAOS into ORDER — enabling binary search, two pointers, and greedy approaches."**

---

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

---

# The 7 Sorting Algorithms You Must Know

## Algorithm Comparison Table

| Algorithm | Time (Avg) | Time (Worst) | Space | Stable | In-Place |
|-----------|------------|--------------|-------|--------|----------|
| Quick Sort | O(n log n) | O(n²) | O(log n) | No | Yes |
| Merge Sort | O(n log n) | O(n log n) | O(n) | Yes | No |
| Heap Sort | O(n log n) | O(n log n) | O(1) | No | Yes |
| Counting Sort | O(n + k) | O(n + k) | O(k) | Yes | No |
| Bucket Sort | O(n + k) | O(n²) | O(n) | Yes | No |
| Radix Sort | O(d·n) | O(d·n) | O(n + k) | Yes | No |
| Tim Sort | O(n log n) | O(n log n) | O(n) | Yes | No |

---

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

---

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

---

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
            swap(nums, mid, high--);
        }
    }
}
```

**Use when:** 3 distinct values, partition around pivot

---

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

---

# PATTERN 0: Sort Colors (LC 75) ⭐⭐

## Pattern Recognition Signal

**When you see:** "sort array with 3 values", "Dutch National Flag"

**Instant thought:** "Three pointers! low, mid, high."

---

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

---

## Visual Dry Run

**Input:** `[2, 0, 2, 1, 1, 0]`

```
═══════════════════════════════════════════════════════════════

Initial: [2, 0, 2, 1, 1, 0]
          L
          M
                         H

═══════════════════════════════════════════════════════════════

nums[mid]=2: Swap with high, high--
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

---

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
            swap(nums, mid, high--);
            // Don't advance mid! New element needs checking
        }
    }
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Advancing mid after swap with high | New element unchecked | Only high-- |
| Using < instead of <= | Miss last element | Use mid <= high |

---

# PATTERN 5: Kth Largest Element (LC 215) ⭐⭐

## Pattern Recognition Signal

**When you see:** "Kth largest", "Kth smallest"

**Instant thought:** "Quick Select! O(n) average."

---

## The Mental Model: "Partial Sorting"

```
We don't need full sort!
Just partition until pivot lands at position n-k.

Array: [3, 2, 1, 5, 6, 4], k=2
Target index: 6-2 = 4 (0-indexed)

After partition, if pivot at index 4 → that's our answer!
```

---

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

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Using k directly as index | Kth largest ≠ index k | Use n-k for 0-indexed |
| Not handling duplicates | May infinite loop | Randomize pivot |

---

# PATTERN 9: Merge Intervals (LC 56) ⭐⭐

## Pattern Recognition Signal

**When you see:** "intervals", "merge overlapping"

**Instant thought:** "Sort by start! Then merge adjacent."

---

## The Mental Model: "Calendar Merging"

```
Meetings on calendar - merge overlapping ones:

Before: [1,3], [2,6], [8,10], [15,18]
        |---|
          |-----|
                  |--|
                        |---|

After:  [1,6], [8,10], [15,18]
        |-------|
                  |--|
                        |---|
```

---

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

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting to sort | Can't detect overlaps | Always sort first |
| Using < instead of <= | Miss adjacent [1,2],[2,3] | Use curr[0] <= last[1] |
| Not using max for end | Miss contained intervals | Use max(last[1], curr[1]) |

---

# PATTERN 10: Meeting Rooms II (LC 253) ⭐⭐

## Pattern Recognition Signal

**When you see:** "minimum rooms", "maximum concurrent"

**Instant thought:** "Sort + Min Heap! Track end times."

---

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

---

# MAANG Coverage Map

| Pattern | Problem | Difficulty | Frequency |
|---------|---------|------------|-----------|
| 0 | Sort Colors (LC 75) | Medium | ⭐⭐⭐⭐⭐ |
| 5 | Kth Largest (LC 215) | Medium | ⭐⭐⭐⭐⭐ |
| 6 | Top K Frequent (LC 347) | Medium | ⭐⭐⭐⭐⭐ |
| 9 | Merge Intervals (LC 56) | Medium | ⭐⭐⭐⭐⭐ |
| 10 | Meeting Rooms II (LC 253) | Medium | ⭐⭐⭐⭐⭐ |
| 11 | Non-overlapping (LC 435) | Medium | ⭐⭐⭐⭐ |
| 15 | Largest Number (LC 179) | Medium | ⭐⭐⭐⭐ |

---

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

---

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

---

*End of Sorting Patterns Deep Dive*
