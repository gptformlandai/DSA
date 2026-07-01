# Section 2.8 — Heap & Priority Queue

---

## 1. What Problem Does This Solve?

A heap solves the problem of efficiently tracking the minimum or maximum element as elements are dynamically added and removed. Without a heap:
- Finding min/max: O(n) scan
- Maintaining sorted order after each insert: O(n log n)

With a heap: O(log n) insert/delete, O(1) peek at min/max.

Applications: Dijkstra's algorithm, Kth largest/smallest, merge K sorted lists, task scheduling, median maintenance.

---

## 2. Beginner-Friendly Intuition

A heap is a special binary tree that stays "almost sorted." In a **min-heap**, the smallest element is always at the root. In a **max-heap**, the largest is at the root.

Imagine a tournament: the overall champion (winner of all matches) is always at the top. When the champion leaves, the runner-up quickly bubbles up to take their place — in O(log n) time.

---

## 3. Real-World Analogy

**Emergency Room triage:** Patients don't wait in FIFO order. The most critical patient is treated next, regardless of arrival time. A priority queue (min-heap by severity score) always surfaces the most urgent case in O(1), and inserting a new patient takes O(log n).

**Stock order book:** Highest bid price is always on top (max-heap). When a match occurs, the top is removed, and the next best price surfaces in O(log n).

---

## 4. Core Concept

### Heap Property
```
Min-heap: parent.val ≤ children.val (root = minimum)
Max-heap: parent.val ≥ children.val (root = maximum)

Stored as array (0-indexed):
  parent(i)      = (i-1)/2
  left_child(i)  = 2*i + 1
  right_child(i) = 2*i + 2

Complete binary tree → no gaps → array storage is compact
```

### Heap Operations
| Operation | Time | Description |
|-----------|------|-------------|
| peek (min/max) | O(1) | View root |
| offer (insert) | O(log n) | Insert + bubble up |
| poll (remove min/max) | O(log n) | Remove root + bubble down |
| heapify (build from array) | O(n) | Build heap in linear time |
| size / isEmpty | O(1) | |

### Java PriorityQueue
```java
// Min-heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// Custom comparator (min by absolute value)
PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> Math.abs(a) - Math.abs(b));

// Key operations
pq.offer(5);   // insert
pq.poll();     // remove and return minimum
pq.peek();     // view minimum without removing
```

---

## 5. Pattern Recognition Signals

```
"Kth largest element" → Max-heap of size n, poll k times
                        OR min-heap of size k (efficient)
"Kth smallest element" → Min-heap, poll k times
"Merge K sorted arrays/lists" → Min-heap of heads
"Find median of data stream" → Two heaps: max-heap (lower half) + min-heap (upper half)
"Top K frequent elements" → HashMap freq + min-heap of size K
"Dijkstra shortest path" → Min-heap on (distance, node)
"Task scheduling" → Max-heap of task frequencies
"Sliding window maximum" → Monotonic deque (or heap with lazy deletion)
"Sort nearly-sorted array (k-sorted)" → Min-heap of size k+1
```

---

## 6. Step-by-Step Algorithm

### Find Kth Largest (Min-Heap of size K)
```
Create min-heap of size k
For each num in array:
    offer num to heap
    if heap.size() > k: poll (removes smallest)
After loop: heap.peek() = kth largest
```

### Median of Data Stream (Two Heaps)
```
maxHeap: left half (max-heap, holds ≤ median)
minHeap: right half (min-heap, holds > median)

Invariant: maxHeap.size() == minHeap.size() OR maxHeap.size() == minHeap.size() + 1

Add num:
    if num ≤ maxHeap.peek(): add to maxHeap
    else: add to minHeap
    Rebalance if sizes differ by > 1

findMedian:
    if sizes equal: (maxHeap.peek() + minHeap.peek()) / 2.0
    else: maxHeap.peek() (odd total count)
```

---

## 7. Dry Run with Example

### Kth Largest (k=3) on [3, 2, 1, 5, 6, 4]
```
Min-heap of size k=3:
  add 3: heap=[3]
  add 2: heap=[2,3]
  add 1: heap=[1,2,3], size=3 (no poll)
  add 5: heap=[1,2,3,5], size=4>3 → poll min(1) → heap=[2,3,5]
  add 6: heap=[2,3,5,6] → poll 2 → heap=[3,5,6]
  add 4: heap=[3,4,5,6] → poll 3 → heap=[4,5,6]

heap.peek() = 4 = 3rd largest ✓ (sorted: [6,5,4,3,2,1], 3rd=4)
```

### Merge 3 Sorted Lists: [1,4,7], [2,5,8], [3,6,9]
```
Min-heap initially: {(1, list0), (2, list1), (3, list2)}

poll (1,list0): output 1, add next from list0: (4,list0)
  heap: {(2,list1), (3,list2), (4,list0)}
poll (2,list1): output 2, add (5,list1)
  heap: {(3,list2), (4,list0), (5,list1)}
poll (3,list2): output 3, add (6,list2)
...
Output: 1,2,3,4,5,6,7,8,9 ✓
```

---

## 8. Code Implementation

```java
import java.util.*;

public class HeapAlgorithms {

    // ── Kth Largest Element ────────────────────────────────────────────────
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // default min
        for (int num : nums) {
            minHeap.offer(num);
            if (minHeap.size() > k) minHeap.poll(); // remove smallest
        }
        return minHeap.peek(); // kth largest = smallest in heap of size k
    }

    // ── Merge K Sorted Lists ───────────────────────────────────────────────
    public ListNode mergeKLists(ListNode[] lists) {
        // Min-heap ordered by node value
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode node : lists)
            if (node != null) pq.offer(node); // add head of each list
        ListNode dummy = new ListNode(0), curr = dummy;
        while (!pq.isEmpty()) {
            ListNode node = pq.poll();      // smallest current head
            curr.next = node; curr = curr.next;
            if (node.next != null) pq.offer(node.next); // add next node from same list
        }
        return dummy.next;
    }

    // ── Top K Frequent Elements ────────────────────────────────────────────
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) freq.merge(n, 1, Integer::sum);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a, b) -> freq.get(a) - freq.get(b)); // min-heap by frequency
        for (int num : freq.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k) minHeap.poll();
        }
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) result[i] = minHeap.poll();
        return result;
    }

    // ── Median Finder (Two Heaps) ──────────────────────────────────────────
    class MedianFinder {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // left half
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // right half

        public void addNum(int num) {
            maxHeap.offer(num);
            minHeap.offer(maxHeap.poll()); // balance: ensure right >= left
            if (minHeap.size() > maxHeap.size())
                maxHeap.offer(minHeap.poll()); // maxHeap always >= minHeap size
        }

        public double findMedian() {
            if (maxHeap.size() > minHeap.size())
                return maxHeap.peek();
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    // ── Dijkstra's Shortest Path ───────────────────────────────────────────
    public int[] dijkstra(int n, int[][] edges, int src) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
            graph[e[1]].add(new int[]{e[0], e[2]});
        }
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, src}); // {distance, node}
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], u = curr[1];
            if (d > dist[u]) continue; // stale entry
            for (int[] next : graph[u]) {
                int v = next[0], w = next[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }
        return dist;
    }
}
```

---

## 9. Time Complexity

| Operation | Min/Max Heap |
|-----------|-------------|
| peek() | O(1) |
| offer() | O(log n) |
| poll() | O(log n) |
| build (heapify n elements) | O(n) |
| Kth Largest (n elements) | O(n log k) |
| Merge K sorted lists (n total) | O(n log k) |
| Dijkstra (V vertices, E edges) | O((V+E) log V) |

---

## 10. Space Complexity

| Algorithm | Space |
|-----------|-------|
| Heap of size k | O(k) |
| Merge k lists heap | O(k) |
| Median finder | O(n) |
| Dijkstra | O(V+E) |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Empty heap poll() | Throws `NoSuchElementException` — use `isEmpty()` check |
| k > array size | Handle gracefully; min-heap never exceeds n |
| Negative numbers in Dijkstra | Use Bellman-Ford instead (Dijkstra requires non-negative) |
| Equal priorities | Java PriorityQueue doesn't guarantee FIFO for equal priorities |
| Heap comparator overflow | Use `Integer.compare(a,b)` instead of `a-b` |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Comparator with subtraction (overflow)
PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> a - b); // WRONG if a=MIN_VALUE
PriorityQueue<Integer> pq = new PriorityQueue<>(Integer::compare); // CORRECT

// MISTAKE 2: Treating PriorityQueue as a sorted structure for iteration
for (int x : pq) // WRONG: iteration order is NOT sorted
int[] sorted = pq.toArray(); Arrays.sort(sorted); // CORRECT if you need sorted

// MISTAKE 3: Not handling stale entries in Dijkstra
int[] curr = pq.poll();
// WRONG: process even if a shorter path was already found
if (curr[0] > dist[curr[1]]) continue; // CORRECT: skip stale entries

// MISTAKE 4: Using max-heap for Kth Largest (wrong approach)
// Max-heap approach: add all, poll k times → O(n + k log n)
// Min-heap of size k → O(n log k) — more efficient for large n

// MISTAKE 5: Median finder — wrong rebalancing order
maxHeap.offer(num); // first push to maxHeap
minHeap.offer(maxHeap.poll()); // then push max of maxHeap to minHeap
// This ensures right half is always >= left half
```

---

## 13. Interview-Level Explanation

**Q: "Why does building a heap from an array take O(n) instead of O(n log n)?"**

> "Inserting n elements one by one each takes O(log n) → O(n log n) total. But bottom-up heapify is smarter: we only need to heapify internal nodes, and nodes near the leaves have very short 'bubble down' paths. Mathematically, the work sums to O(n) because most nodes are near the leaves. Specifically: n/2 nodes at height 0 (leaves, O(1) each), n/4 nodes at height 1 (O(1) each), ..., 1 node at height log n (O(log n)). The total = O(n × (sum of h/2^h)) = O(n × 2) = O(n)."

**Q: "How does the two-heap approach maintain the median?"**

> "We keep the lower half of numbers in a max-heap and the upper half in a min-heap. The max-heap's top is the largest of the small numbers; the min-heap's top is the smallest of the large numbers. We maintain the invariant that the heaps differ in size by at most 1. The median is either the top of the larger heap (odd count) or the average of both tops (even count). This gives O(log n) per insertion and O(1) median retrieval."

---

## 14. Real-World Use Cases

| Application | Heap Usage |
|------------|-----------|
| **Dijkstra's algorithm** | Min-heap on (distance, node) |
| **OS task scheduler** | Priority queue by task priority |
| **Event simulation** | Min-heap by event time |
| **Top-K recommendations** | Min-heap of size K |
| **Data stream median** | Two heaps |
| **A* pathfinding** | Min-heap on (f-score, node) |

---

## 15. Variations

| Variation | Technique |
|-----------|----------|
| Max-heap | `Collections.reverseOrder()` or `(a,b) -> b-a` |
| K-way merge | Min-heap of (value, list_index, element_index) |
| Running median | Two heaps |
| Lazy deletion | Mark deleted; skip stale elements on poll |
| D-ary heap | Each node has d children — reduced height |
| Fibonacci heap | Amortized O(1) decrease-key (theoretical) |

---

## 16. Practice Problems

### Easy — Foundation
1. **Kth Largest Element in a Stream** (LeetCode #703)
   - *Task:* Maintain Kth largest as elements arrive.
   - *Hint:* Min-heap of size k; peek is the answer.

2. **Last Stone Weight** (LeetCode #1046)
   - *Task:* Smash two heaviest stones repeatedly.
   - *Hint:* Max-heap; poll two, push difference if non-zero.

3. **K Closest Points to Origin** (LeetCode #973)
   - *Task:* Find K points closest to origin.
   - *Hint:* Max-heap of size K by distance; poll when size > K.

### Medium — Core
1. **Kth Largest Element in an Array** (LeetCode #215)
   - *Task:* Kth largest without full sort.
   - *Hint:* Min-heap of size k: O(n log k).

2. **Top K Frequent Elements** (LeetCode #347)
   - *Task:* K most frequent numbers.
   - *Hint:* HashMap freq + min-heap of size k by frequency.

3. **Find Median from Data Stream** (LeetCode #295)
   - *Task:* Add numbers and find running median.
   - *Hint:* Max-heap (lower half) + min-heap (upper half).

4. **Reorganize String** (LeetCode #767)
   - *Task:* Rearrange so no two adjacent chars are same.
   - *Hint:* Max-heap by frequency; always place most frequent.

5. **Task Scheduler** (LeetCode #621)
   - *Task:* Minimum time to execute tasks with cooldown n.
   - *Hint:* Max-heap by task count; greedy placement.

### Hard — Advanced
1. **Merge K Sorted Lists** (LeetCode #23)
   - *Task:* Merge k sorted linked lists.
   - *Hint:* Min-heap of list heads; O(n log k).

2. **Sliding Window Median** (LeetCode #480)
   - *Task:* Median of each sliding window of size k.
   - *Hint:* Two heaps + lazy deletion.

3. **IPO (Maximize Capital)** (LeetCode #502)
   - *Task:* Choose projects to maximize capital.
   - *Hint:* Sort by capital; use max-heap for profits.

---

## 17. How to Know You Have Mastered Heap & Priority Queue

You have mastered this topic when you can:
- [ ] Use Java's `PriorityQueue` with custom comparator (safely, without overflow)
- [ ] Build a min-heap of size k for Kth Largest in O(n log k)
- [ ] Implement Merge K Sorted Lists with min-heap
- [ ] Implement MedianFinder with two heaps
- [ ] Explain why heapify is O(n) not O(n log n)
- [ ] Know when to use a heap vs a TreeMap vs a sorted array
- [ ] Handle stale entries in lazy deletion heap
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Min-heap or max-heap for Kth Largest? Why?

2. Java `PriorityQueue<Integer> pq = new PriorityQueue<>()`. Is it a min-heap or max-heap by default?

3. `pq.peek()` vs `pq.poll()` — what is the difference?

4. For Median Finder: after adding [1, 2, 3, 4, 5], what are the two heaps' tops?

5. Why is `(a, b) -> a - b` unsafe as a heap comparator for integers?

6. Heap stores [10, 5, 8, 3, 4, 7, 6] as an array. What is `parent(4)` (0-indexed)?

7. Dijkstra uses a min-heap on (distance, node). If a shorter path to node V is found after V was already polled, what happens?

8. Building a heap from n unsorted elements: O(n log n) via repeated insert vs O(n) via heapify. When does the O(n) approach matter in practice?

> **Answers:**
> 1. Min-heap of size k. The k largest elements are in the heap; the smallest of those (the kth largest overall) is at the top. Max-heap would require polling k times from all n elements — O(k log n) — while min-heap of size k is O(n log k).
> 2. Min-heap by default. The natural order for `Integer` is ascending. `PriorityQueue` by default uses natural ordering — so the smallest element is at the top.
> 3. `peek()` returns the minimum (or maximum for max-heap) without removing it — O(1). `poll()` removes and returns the minimum — O(log n) because the heap must be restructured.
> 4. After [1,2,3,4,5]: maxHeap (lower half) = [1,2,3] → top=3. minHeap (upper half) = [4,5] → top=4. Sizes: 3 and 2 (odd total=5). Median = maxHeap.peek() = 3.
> 5. Integer overflow: if a = Integer.MIN_VALUE and b = 1, then a-b = -2^31 - 1, which wraps around in 32-bit arithmetic to a large positive value, giving the wrong comparison result. Use `Integer.compare(a,b)`.
> 6. parent(4) = (4-1)/2 = 1 (0-indexed). The element at index 4 has its parent at index 1.
> 7. When V is polled from the heap, we check: `if (d > dist[V]) continue` — skip this stale entry. The correct shorter path was already recorded in `dist[V]` and a new heap entry with the shorter distance was added. The stale entry is discarded harmlessly.
> 8. O(n) heapify matters when building a priority queue from a large existing dataset in one shot. For example, Java's `PriorityQueue(Collection c)` constructor uses O(n) heapify. If you're inserting n elements one by one into an initially empty heap (like in a stream), you must use O(log n) per insert → O(n log n) total.

---

**Next →** `../03_Searching/01_Searching_Algorithms.md`

When you repeatedly need the **minimum or maximum** element efficiently.  
Use cases: scheduling, top-K problems, merging sorted lists, Dijkstra's.

---

## 2. Beginner-Friendly Intuition

A heap is like a **tournament bracket**:
- The winner (min or max) is always at the top.
- After removal, the next winner bubbles up automatically.

**Min-Heap:** Root is always the smallest element.  
**Max-Heap:** Root is always the largest element.

---

## 3. Heap Property

A heap is a **complete binary tree** where:
- **Min-Heap:** parent ≤ children
- **Max-Heap:** parent ≥ children

```
Min-Heap:        Max-Heap:
     1                9
   /   \            /   \
  3     5          7     5
 / \   /          / \
4   8 6          2   3
```

Stored as an array:
```
Index: 0  1  2  3  4  5
Value: 1  3  5  4  8  6

Parent of i  = (i-1) / 2
Left child   = 2*i + 1
Right child  = 2*i + 2
```

---

## 4. Operations & Complexity

| Operation | Time |
|-----------|------|
| peek (min/max) | O(1) |
| insert | O(log n) |
| remove min/max | O(log n) |
| build heap | O(n) |
| heapify | O(log n) |
| search | O(n) |

---

## 5. Java PriorityQueue

```java
// Min-Heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
minHeap.offer(5);
minHeap.offer(1);
minHeap.offer(3);
minHeap.peek();   // 1 (minimum)
minHeap.poll();   // 1 (removes minimum)

// Max-Heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// or:
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// Custom comparator (e.g., sort by second element of int[])
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
```

---

## 6. Key Patterns

### Top K Largest Elements
```java
// Use min-heap of size K
// When heap size > K, remove minimum
// After all elements, heap contains K largest
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for (int num : nums) {
    minHeap.offer(num);
    if (minHeap.size() > k) minHeap.poll();
}
return minHeap.peek();  // Kth largest
```

Why min-heap for "largest"? The smallest of the K largest sits at the top, ready to be replaced if something bigger comes.

### K Closest Points to Origin
```java
PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
    (a, b) -> (b[0]*b[0]+b[1]*b[1]) - (a[0]*a[0]+a[1]*a[1])
);
for (int[] p : points) {
    maxHeap.offer(p);
    if (maxHeap.size() > k) maxHeap.poll();
}
return maxHeap.toArray(new int[0][]);
```

### Median from Data Stream
```java
PriorityQueue<Integer> lower = new PriorityQueue<>(Collections.reverseOrder()); // max-heap
PriorityQueue<Integer> upper = new PriorityQueue<>(); // min-heap

void addNum(int num) {
    lower.offer(num);
    upper.offer(lower.poll());          // balance
    if (lower.size() < upper.size())
        lower.offer(upper.poll());      // maintain lower has >= elements
}
double getMedian() {
    return lower.size() == upper.size()
        ? (lower.peek() + upper.peek()) / 2.0
        : lower.peek();
}
```

---

## 7. When to Use Heap

✅ Use when:
- Repeatedly need min or max
- Top K / K closest / K most frequent
- Merging K sorted lists
- Dijkstra's algorithm
- Median maintenance

❌ Don't use when:
- Need sorted output (use Arrays.sort)
- Need O(1) search (use HashMap)
- Only one min/max query needed (use one scan)

---

## 8. Practice Problems

**Easy:**
1. Kth Largest Element in Array.
2. Last Stone Weight.
3. Sort K-sorted array.

**Medium:**
1. Top K Frequent Elements.
2. K Closest Points to Origin.
3. Merge K Sorted Lists.
4. Task Scheduler.
5. Find Median from Data Stream.

**Hard:**
1. Sliding Window Median.
2. IPO (max profit with K projects).
3. Minimum Cost to Connect Ropes.

---

**Next →** `../03_Searching/01_Searching_Algorithms.md`
