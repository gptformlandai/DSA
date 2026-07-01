# Section 4 — Sorting Algorithms

---

## 1. What Problem Does This Solve?

Sorting arranges elements in a specific order (ascending/descending). It unlocks nearly every other algorithm: binary search requires sorted input, two-pointer problems assume sorted arrays, interval merging requires sorted start times, and Dijkstra's uses a priority queue (which internally sorts).

Understanding sorting algorithms teaches you:
- Comparison-based vs non-comparison-based sorting
- Divide and conquer (Merge Sort, Quick Sort)
- When to use which algorithm in practice (Java's built-in sort)

---

## 2. Beginner-Friendly Intuition

**Bubble Sort:** Repeatedly compare adjacent pairs. Larger elements "bubble up" to the end. Simplest to understand, worst in practice.

**Merge Sort:** Split the array in half recursively until single elements, then merge pairs in sorted order. Guaranteed O(n log n) always.

**Quick Sort:** Pick a "pivot", partition: elements smaller go left, larger go right. Then recursively sort each half. Fast in practice (O(n log n) average) but worst-case O(n^2).

**Counting Sort / Radix Sort:** Don't compare — use the values themselves as indices. O(n) but only works for integers in a bounded range.

---

## 3. Real-World Analogy

**Merge Sort — Tournament bracket:** Split players into pairs, find winner of each pair, then compare winners. The overall winner emerges after O(n log n) comparisons.

**Quick Sort — Classroom seating:** Pick any student as pivot. Everyone shorter sits left, taller sits right. Repeat for each group. Usually finishes fast; rarely devolves to O(n^2) if you pick a bad pivot every time.

**Counting Sort — Voting:** Count how many votes each candidate gets, then output in order. No comparisons needed.

---

## 4. Core Concept

### Complexity Comparison

| Algorithm | Best | Average | Worst | Space | Stable? |
|-----------|------|---------|-------|-------|---------|
| Bubble Sort | O(n) | O(n^2) | O(n^2) | O(1) | Yes |
| Selection Sort | O(n^2) | O(n^2) | O(n^2) | O(1) | No |
| Insertion Sort | O(n) | O(n^2) | O(n^2) | O(1) | Yes |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes |
| Quick Sort | O(n log n) | O(n log n) | O(n^2) | O(log n) | No |
| Counting Sort | O(n+k) | O(n+k) | O(n+k) | O(k) | Yes |
| Radix Sort | O(d×n) | O(d×n) | O(d×n) | O(n+k) | Yes |

### Java's Arrays.sort Internals
- **Primitives:** Dual-pivot Quick Sort (O(n log n) average, O(n^2) worst — rare)
- **Objects:** TimSort (hybrid Merge+Insertion Sort, O(n log n), stable)
- Use `Arrays.sort()` — it's optimized and production-grade.

---

## 5. Pattern Recognition Signals

```
"Sort and process" → Apply sort first, then algorithm
"Two pointers on array" → Must sort first
"Merge intervals" → Sort by start time
"K smallest/largest" → Heap (O(n log k)) or partial sort
"Nearly sorted array" → Insertion Sort (O(n×k) for k-sorted)
"Integer keys in small range" → Counting Sort
"Sort strings by length/custom key" → Arrays.sort with Comparator
"External sort (data doesn't fit RAM)" → Merge Sort
```

---

## 6. Step-by-Step Algorithm

### Merge Sort
```
mergeSort(arr, left, right):
    if left >= right: return
    mid = (left + right) / 2
    mergeSort(arr, left, mid)
    mergeSort(arr, mid+1, right)
    merge(arr, left, mid, right)

merge(arr, left, mid, right):
    copy arr[left..mid] to L[], arr[mid+1..right] to R[]
    i=0, j=0, k=left
    while i < len(L) and j < len(R):
        if L[i] <= R[j]: arr[k++] = L[i++]
        else: arr[k++] = R[j++]
    copy remaining elements
```

### Quick Sort (with median-of-3 pivot)
```
quickSort(arr, low, high):
    if low < high:
        pi = partition(arr, low, high)
        quickSort(arr, low, pi-1)
        quickSort(arr, pi+1, high)

partition(arr, low, high):
    pivot = arr[high]
    i = low - 1
    for j = low to high-1:
        if arr[j] <= pivot:
            i++
            swap(arr[i], arr[j])
    swap(arr[i+1], arr[high])
    return i+1
```

---

## 7. Dry Run with Example

### Merge Sort on [5, 2, 4, 1, 3]
```
Split:
  [5,2,4,1,3]
  → [5,2,4] and [1,3]
  → [5,2] [4] and [1] [3]
  → [5] [2] [4] [1] [3]

Merge:
  [5]+[2] → [2,5]
  [2,5]+[4] → [2,4,5]
  [1]+[3] → [1,3]
  [2,4,5]+[1,3]:
    Compare 2 vs 1 → take 1
    Compare 2 vs 3 → take 2
    Compare 4 vs 3 → take 3
    Take 4, take 5
  → [1,2,3,4,5] ✓
```

---

## 8. Code Implementation

```java
import java.util.Arrays;

public class SortingAlgorithms {

    // ── Bubble Sort ────────────────────────────────────────────────────────
    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j]; arr[j] = arr[j+1]; arr[j+1] = tmp;
                    swapped = true;
                }
            }
            if (!swapped) break; // already sorted — best case O(n)
        }
    }

    // ── Insertion Sort ────────────────────────────────────────────────────
    static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i], j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j]; j--;
            }
            arr[j + 1] = key; // insert key in correct position
        }
    }

    // ── Merge Sort ────────────────────────────────────────────────────────
    static void mergeSort(int[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    static void merge(int[] arr, int left, int mid, int right) {
        int[] L = Arrays.copyOfRange(arr, left, mid + 1);
        int[] R = Arrays.copyOfRange(arr, mid + 1, right + 1);
        int i = 0, j = 0, k = left;
        while (i < L.length && j < R.length)
            arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < L.length) arr[k++] = L[i++];
        while (j < R.length) arr[k++] = R[j++];
    }

    // ── Quick Sort ────────────────────────────────────────────────────────
    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // last element as pivot
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
            }
        }
        int tmp = arr[i+1]; arr[i+1] = arr[high]; arr[high] = tmp;
        return i + 1;
    }

    // ── Counting Sort (for non-negative integers in range [0, max]) ───────
    static int[] countingSort(int[] arr) {
        int max = Arrays.stream(arr).max().getAsInt();
        int[] count = new int[max + 1];
        for (int x : arr) count[x]++;
        // Build sorted output
        int[] sorted = new int[arr.length];
        int idx = 0;
        for (int i = 0; i <= max; i++)
            while (count[i]-- > 0) sorted[idx++] = i;
        return sorted;
    }

    // ── Java's Built-in Sort with Custom Comparator ───────────────────────
    // Sort array of strings by length, then alphabetically
    static void sortByLength(String[] strs) {
        Arrays.sort(strs, (a, b) -> a.length() != b.length()
                ? a.length() - b.length()
                : a.compareTo(b));
    }

    // Sort int[] descending (must use Integer[])
    static void sortDescending(Integer[] arr) {
        Arrays.sort(arr, (a, b) -> b - a);
    }
}
```

---

## 9. Time Complexity

| Algorithm | Best | Average | Worst |
|-----------|------|---------|-------|
| Bubble Sort | O(n) | O(n^2) | O(n^2) |
| Insertion Sort | O(n) | O(n^2) | O(n^2) |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) |
| Quick Sort | O(n log n) | O(n log n) | O(n^2) |
| Counting Sort | O(n+k) | O(n+k) | O(n+k) |

---

## 10. Space Complexity

| Algorithm | Extra Space |
|-----------|------------|
| Bubble / Insertion / Selection | O(1) |
| Merge Sort | O(n) auxiliary |
| Quick Sort | O(log n) call stack |
| Counting Sort | O(k) where k = value range |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Empty array | n=0: no sorting needed |
| Single element | n=1: already sorted |
| All same elements | All sorts handle correctly |
| Already sorted | Insertion/Bubble: O(n); Quicksort: O(n^2) worst case |
| Reverse sorted | Quicksort worst case with naive pivot |
| Negative numbers | Counting Sort needs offset; use `min` as base |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Using Bubble/Insertion Sort for large input
// O(n^2) for n=10^5 = 10^10 operations → TLE
// CORRECT: Use Arrays.sort() or merge sort for n > 10^3

// MISTAKE 2: Quick Sort on already-sorted array with last-element pivot
// Leads to O(n^2) worst case. Fix: random pivot or median-of-3.
int pivotIdx = low + new Random().nextInt(high - low + 1);
// swap arr[pivotIdx] with arr[high] before partitioning

// MISTAKE 3: Not stable when stability required
// Quick Sort is NOT stable. Use Merge Sort when relative order of
// equal elements must be preserved (e.g., sort by last name, then first name).

// MISTAKE 4: Counting sort with negative numbers
int min = Arrays.stream(arr).min().getAsInt();
int max = Arrays.stream(arr).max().getAsInt();
int[] count = new int[max - min + 1]; // shift by min
for (int x : arr) count[x - min]++;

// MISTAKE 5: Comparator returning Integer.MIN_VALUE for (a-b) overflow
Arrays.sort(arr, (a, b) -> a - b); // WRONG: a=Integer.MIN_VALUE, b=1 → overflow
Arrays.sort(arr, Integer::compare); // CORRECT: safe comparison
```

---

## 13. Interview-Level Explanation

**Q: "When would you use Merge Sort over Quick Sort?"**

> "When stability is required — Merge Sort preserves the relative order of equal elements, Quick Sort doesn't. Also for linked lists, since Merge Sort works well without random access. And when worst-case guarantee matters: Merge Sort is always O(n log n), while Quick Sort degrades to O(n^2) on pathological inputs unless randomized. For external sorting (data larger than RAM), Merge Sort is the standard choice because it accesses memory sequentially."

**Q: "What sorting algorithm does Java use internally?"**

> "Java uses two different strategies. For primitive arrays (int[], double[]), Arrays.sort() uses Dual-Pivot Quick Sort, which is very cache-efficient and fast in practice. For object arrays (Integer[], String[]), it uses TimSort — a hybrid of Merge Sort and Insertion Sort that exploits existing order in real-world data. TimSort is stable and guarantees O(n log n) worst case."

---

## 14. Real-World Use Cases

| Application | Algorithm |
|------------|-----------|
| **Database ORDER BY** | External Merge Sort |
| **Java Collections.sort** | TimSort |
| **Spreadsheet sort** | TimSort (stable) |
| **Database join (sort-merge join)** | Merge Sort |
| **Counting occurrences** | Counting Sort |
| **DNS lookup caching** | Sorted structure (binary search) |

---

## 15. Variations

| Variation | Algorithm |
|-----------|----------|
| Sort linked list | Merge Sort (natural for linked lists) |
| Sort k-sorted array | Heap-based sort (O(n log k)) |
| Sort by multiple keys | Java Comparator chaining |
| Partial sort (top-K) | Quick Select or Heap |
| External sort | Multi-way Merge Sort |
| Sort in-place with O(1) space | Quick Sort or Shell Sort |

---

## 16. Practice Problems

### Easy — Foundation
1. **Sort an Array** (LeetCode #912)
   - *Task:* Sort an integer array.
   - *Hint:* Implement merge sort or quick sort from scratch.

2. **Sort Colors** (LeetCode #75)
   - *Task:* Sort [0,1,2] array in-place without library sort.
   - *Hint:* Dutch National Flag algorithm (three pointers).

3. **Merge Sorted Array** (LeetCode #88)
   - *Task:* Merge two sorted arrays in-place.
   - *Hint:* Fill from the back using two pointers.

### Medium — Core
1. **Kth Largest Element** (LeetCode #215)
   - *Task:* Find kth largest without full sort.
   - *Hint:* Quick Select O(n) average, or Min-Heap O(n log k).

2. **Sort List** (LeetCode #148)
   - *Task:* Sort a linked list in O(n log n) time.
   - *Hint:* Merge Sort on linked list — find mid via slow/fast pointers.

3. **Largest Number** (LeetCode #179)
   - *Task:* Arrange numbers to form the largest number.
   - *Hint:* Custom comparator: compare (a+""+b) vs (b+""+a) as strings.

4. **Wiggle Sort II** (LeetCode #324)
   - *Task:* Rearrange so nums[0] < nums[1] > nums[2] < nums[3]...
   - *Hint:* Sort, interleave from median.

5. **Count of Smaller Numbers After Self** (LeetCode #315)
   - *Task:* For each element, count elements to its right that are smaller.
   - *Hint:* Modified merge sort — count inversions during merge.

### Hard — Advanced
1. **Reverse Pairs** (LeetCode #493)
   - *Task:* Count pairs where arr[i] > 2*arr[j] for i < j.
   - *Hint:* Modified merge sort — count during merge step.

2. **Maximum Gap** (LeetCode #164)
   - *Task:* Maximum gap between consecutive elements after sorting.
   - *Hint:* Bucket sort / Radix Sort for O(n) solution.

3. **Count of Range Sum** (LeetCode #327)
   - *Task:* Count prefix sums in a given range.
   - *Hint:* Modified merge sort on prefix sums.

---

## 17. How to Know You Have Mastered Sorting Algorithms

You have mastered this topic when you can:
- [ ] Implement Merge Sort correctly from scratch (including merge step)
- [ ] Implement Quick Sort with correct partition and handle worst-case
- [ ] Know the stability and space properties of each algorithm
- [ ] Use Java's `Arrays.sort` with custom Comparator correctly and safely (no overflow)
- [ ] Know when counting sort is applicable (bounded integers)
- [ ] Implement Dutch National Flag (3-way partition) for sort colors
- [ ] Explain Java's TimSort and why it's used for objects
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Why is Merge Sort preferred for sorting a linked list over Quick Sort?

2. What makes TimSort different from pure Merge Sort?

3. Comparator `(a, b) -> a - b` is unsafe for what reason?

4. Counting Sort can sort n numbers in O(n) time. What is its limitation?

5. Quick Sort worst case is O(n^2). When does this happen, and how do you fix it?

6. You need to sort an array of (name, age) pairs by age, but if ages are equal, preserve the original order. Which sorting algorithm must you use?

7. In the merge step of Merge Sort, when both pointers are at equal elements, which side do you take to maintain stability?

8. Bubble Sort has a best-case of O(n). When does this happen, and what code change enables it?

> **Answers:**
> 1. Quick Sort requires random access (jump to index) for efficient partitioning, which linked lists don't support in O(1). Merge Sort only needs sequential access and finding the midpoint (via slow/fast pointers) — perfect for linked lists.
> 2. TimSort exploits existing "runs" (sorted subsequences) in real-world data. It finds natural runs and merges them efficiently. For nearly-sorted data, it approaches O(n); worst case is still O(n log n). Pure Merge Sort ignores existing order.
> 3. Integer overflow: if a = Integer.MIN_VALUE and b = 1, then a-b = -2^31 - 1, which wraps around to a positive number, giving wrong comparison. Use `Integer.compare(a, b)` instead.
> 4. It only works for non-negative integers (or integers with bounded range). The range k must be small enough that an array of size k fits in memory. Not suitable for floating-point, strings, or arbitrary objects.
> 5. When pivot is always the smallest or largest element (e.g., already-sorted array with last-element pivot). Fix: randomize pivot selection or use median-of-three.
> 6. A stable sort — Merge Sort (or TimSort). Stable sorts preserve the relative order of equal-key elements, so original order is maintained for equal ages.
> 7. Take from the LEFT array (L[i]). This ensures equal elements from the left half appear before equal elements from the right half, preserving the original relative order — making Merge Sort stable.
> 8. When the array is already sorted. The early-exit `if (!swapped) break;` detects that no swaps occurred in a complete pass, meaning the array is sorted, and exits in O(n) — one pass with no swaps.

---

**Next →** `../02_Data_Structures/01_Arrays.md`
- Interviewers love asking about sorting internals and tradeoffs.

---

## Part 1: Simple Sorts (Understand, Don't Use in Production)

---

### Bubble Sort

**Intuition:** Repeatedly swap adjacent elements that are out of order. Largest "bubbles up" to end each pass.

**Analogy:** Sorting playing cards by comparing neighbors repeatedly.

```java
void bubbleSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        boolean swapped = false;
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j]; arr[j] = arr[j+1]; arr[j+1] = temp;
                swapped = true;
            }
        }
        if (!swapped) break;  // already sorted — early exit
    }
}
```

**Dry Run:** [5, 3, 1, 4]
```
Pass 1: [3,1,4,5] (5 bubbles to end)
Pass 2: [1,3,4,5] (3 moves right)
Pass 3: [1,3,4,5] (no swap → done early)
```

| Case | Time | Space |
|------|------|-------|
| Best | O(n) | O(1) |
| Average | O(n²) | O(1) |
| Worst | O(n²) | O(1) |
| Stable | ✅ Yes | |

---

### Selection Sort

**Intuition:** Find the minimum each time and place it in position.

```java
void selectionSort(int[] arr) {
    int n = arr.length;
    for (int i = 0; i < n - 1; i++) {
        int minIdx = i;
        for (int j = i + 1; j < n; j++)
            if (arr[j] < arr[minIdx]) minIdx = j;
        int temp = arr[i]; arr[i] = arr[minIdx]; arr[minIdx] = temp;
    }
}
```

| Case | Time | Space |
|------|------|-------|
| All | O(n²) | O(1) |
| Stable | ❌ No | |

---

### Insertion Sort

**Intuition:** Like sorting cards in hand — pick one, insert at right position.

**Best on:** Nearly sorted arrays, small inputs (< 20 elements).

```java
void insertionSort(int[] arr) {
    int n = arr.length;
    for (int i = 1; i < n; i++) {
        int key = arr[i], j = i - 1;
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}
```

**Dry Run:** [4, 2, 5, 1]
```
i=1: key=2, shift 4 right → [_, 4, 5, 1], insert: [2,4,5,1]
i=2: key=5, 5>4 so no shift → [2,4,5,1]
i=3: key=1, shift 5,4,2 right → [_, 2, 4, 5], insert: [1,2,4,5]
```

| Case | Time | Space |
|------|------|-------|
| Best | O(n) | O(1) |
| Average | O(n²) | O(1) |
| Worst | O(n²) | O(1) |
| Stable | ✅ Yes | |
| Adaptive | ✅ Yes (fast on nearly sorted) | |

---

## Part 2: Efficient Sorts

---

### Merge Sort

**Intuition:** Split array in half, sort each half recursively, merge sorted halves.

**Divide & Conquer:** The workhorse of stable sorting.

```java
void mergeSort(int[] arr, int lo, int hi) {
    if (lo >= hi) return;
    int mid = lo + (hi - lo) / 2;
    mergeSort(arr, lo, mid);
    mergeSort(arr, mid + 1, hi);
    merge(arr, lo, mid, hi);
}

void merge(int[] arr, int lo, int mid, int hi) {
    int[] temp = new int[hi - lo + 1];
    int i = lo, j = mid + 1, k = 0;
    while (i <= mid && j <= hi) {
        if (arr[i] <= arr[j]) temp[k++] = arr[i++];
        else temp[k++] = arr[j++];
    }
    while (i <= mid) temp[k++] = arr[i++];
    while (j <= hi) temp[k++] = arr[j++];
    System.arraycopy(temp, 0, arr, lo, temp.length);
}
```

**Dry Run:** [38, 27, 43, 3]
```
Split: [38,27] | [43,3]
Sort:  [27,38] | [3,43]
Merge: [3, 27, 38, 43]
```

| Case | Time | Space |
|------|------|-------|
| All | O(n log n) | O(n) |
| Stable | ✅ Yes | |

**Real-world use:** Sorting linked lists (where random access is expensive). Java's `Arrays.sort` for objects uses TimSort (Merge + Insertion hybrid).

---

### Quick Sort

**Intuition:** Pick a pivot. Put all smaller elements left, larger right. Recurse on both sides.

```java
void quickSort(int[] arr, int lo, int hi) {
    if (lo < hi) {
        int pivot = partition(arr, lo, hi);
        quickSort(arr, lo, pivot - 1);
        quickSort(arr, pivot + 1, hi);
    }
}

int partition(int[] arr, int lo, int hi) {
    int pivot = arr[hi];  // last element as pivot
    int i = lo - 1;
    for (int j = lo; j < hi; j++) {
        if (arr[j] <= pivot) {
            i++;
            int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
        }
    }
    int temp = arr[i+1]; arr[i+1] = arr[hi]; arr[hi] = temp;
    return i + 1;
}
```

**Dry Run:** [3, 6, 8, 10, 1, 2, 1], pivot=arr[6]=1
```
Elements ≤ 1: [1, 1]
Pivot position: index 2
After partition: [1, 1, ?, 6, 8, 10, 3, 2] (approx)
```

| Case | Time | Space |
|------|------|-------|
| Best/Avg | O(n log n) | O(log n) |
| Worst | O(n²) — sorted array with bad pivot | O(n) |
| Stable | ❌ No | |

**Optimization:** Random pivot or median-of-three to avoid worst case.

**Real-world use:** Java's `Arrays.sort` for primitives uses dual-pivot QuickSort.

---

### Heap Sort

**Intuition:** Build a max-heap, then repeatedly extract maximum.

```java
void heapSort(int[] arr) {
    int n = arr.length;
    // Build max heap
    for (int i = n / 2 - 1; i >= 0; i--)
        heapify(arr, n, i);
    // Extract elements
    for (int i = n - 1; i > 0; i--) {
        int temp = arr[0]; arr[0] = arr[i]; arr[i] = temp;
        heapify(arr, i, 0);
    }
}

void heapify(int[] arr, int n, int i) {
    int largest = i, left = 2*i+1, right = 2*i+2;
    if (left < n && arr[left] > arr[largest]) largest = left;
    if (right < n && arr[right] > arr[largest]) largest = right;
    if (largest != i) {
        int temp = arr[i]; arr[i] = arr[largest]; arr[largest] = temp;
        heapify(arr, n, largest);
    }
}
```

| Case | Time | Space |
|------|------|-------|
| All | O(n log n) | O(1) |
| Stable | ❌ No | |

---

### Counting Sort

**Intuition:** Count frequency of each element, reconstruct sorted array.

**When:** Values in small range (e.g., 0–100).

```java
void countingSort(int[] arr, int maxVal) {
    int[] count = new int[maxVal + 1];
    for (int x : arr) count[x]++;
    int idx = 0;
    for (int i = 0; i <= maxVal; i++)
        while (count[i]-- > 0) arr[idx++] = i;
}
```

| Case | Time | Space |
|------|------|-------|
| All | O(n + k) | O(k) |
| Stable | ✅ Yes | |

---

### Radix Sort

**Intuition:** Sort digit by digit from least significant to most significant using counting sort.

```
Input:  [170, 45, 75, 90, 802, 24, 2, 66]
Pass 1 (units): [170, 90, 802, 2, 24, 45, 75, 66]
Pass 2 (tens):  [802, 2, 24, 45, 66, 170, 75, 90]
Pass 3 (hundreds): [2, 24, 45, 66, 75, 90, 170, 802]
```

Time: O(d × (n + k)) where d = digits, k = base (10)

---

## Part 3: Sorting Comparison Table

| Algorithm | Best | Average | Worst | Space | Stable | Use Case |
|-----------|------|---------|-------|-------|--------|---------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | ✅ | Learning only |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | ❌ | Learning only |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | ✅ | Small / nearly sorted |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | ✅ | Stable sort, linked lists |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | ❌ | General purpose |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | ❌ | Memory critical |
| Counting Sort | O(n+k) | O(n+k) | O(n+k) | O(k) | ✅ | Small range integers |
| Radix Sort | O(d(n+k)) | O(d(n+k)) | O(d(n+k)) | O(n+k) | ✅ | Fixed-length integers |

---

## Part 4: Topological Sort

**What:** Order nodes of a DAG such that for every directed edge u→v, u appears before v.

**Use cases:** Build systems, course prerequisites, task scheduling.

### Kahn's Algorithm (BFS-based)
```java
List<Integer> topologicalSort(int n, int[][] edges) {
    List<List<Integer>> adj = new ArrayList<>();
    int[] indegree = new int[n];
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] e : edges) { adj.get(e[0]).add(e[1]); indegree[e[1]]++; }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < n; i++) if (indegree[i] == 0) queue.offer(i);

    List<Integer> order = new ArrayList<>();
    while (!queue.isEmpty()) {
        int node = queue.poll();
        order.add(node);
        for (int neighbor : adj.get(node)) {
            if (--indegree[neighbor] == 0) queue.offer(neighbor);
        }
    }
    return order.size() == n ? order : new ArrayList<>();  // empty = cycle exists
}
```

---

## Practice Problems

**Easy:**
1. Sort an array using insertion sort.
2. Sort colors (Dutch National Flag problem).
3. Merge two sorted arrays.

**Medium:**
1. Sort list (merge sort on linked list).
2. Find Kth largest using QuickSelect.
3. Count inversions in array (modified merge sort).
4. Sort characters by frequency.
5. Course Schedule (topological sort).

**Hard:**
1. Maximum gap (radix sort insight).
2. Alien Dictionary (topological sort on chars).
3. Count smaller numbers after self (merge sort + BIT).

---

**Next →** `../05_Two_Pointers/01_Two_Pointers.md`
