# Section 14 — Heap Patterns

---

## 1. What Problem Does This Solve?

Heap patterns solve problems where you repeatedly need the **current smallest**, **current largest**, or a controlled top/bottom `k` set while data is changing.

Common problem types:
- Find kth largest/smallest without fully sorting
- Maintain top K elements from a stream
- Merge K sorted lists/arrays
- Find median from a stream
- Schedule tasks by priority, cooldown, or earliest finish time

---

## 2. Beginner-Friendly Intuition

A heap is a **priority line**. You do not fully sort everyone in line; you only guarantee that the most urgent item is always at the front.

That is the key interview intuition:
- Sorting gives a full order.
- A heap gives fast access to the best next candidate.
- If you only care about one extreme or top K, heap is often cheaper than sorting.

---

## 3. Real-World Analogy

**Emergency room triage:** Patients are not sorted into a perfect total order forever. The hospital only needs to quickly identify the highest-priority patient right now. When a new patient arrives, the priority line updates.

**Delivery dispatch:** Drivers can be assigned using a heap ordered by earliest availability, nearest distance, or highest priority package.

---

## 4. Core Concept

### Heap Property

| Heap Type | Root Contains | Java PriorityQueue Setup |
|---|---|---|
| Min-heap | Smallest element | `new PriorityQueue<>()` |
| Max-heap | Largest element | `new PriorityQueue<>((a, b) -> Integer.compare(b, a))` |

In Java, `PriorityQueue` is a min-heap by default.

### Why Heap Is Useful

| Operation | Time |
|---|---|
| Peek best item | O(1) |
| Insert item | O(log n) |
| Remove best item | O(log n) |
| Build heap from n items | O(n) |

### Heap vs Sorting

| Need | Better Choice |
|---|---|
| Need all elements in sorted order | Sorting |
| Need kth largest only | Heap or Quickselect |
| Need top K from a stream | Heap |
| Need dynamic priority changes | Heap |
| Need exact rank of every element | Sorting / balanced tree |

---

## 5. Pattern Recognition Signals

Use a heap when the problem says:

```text
"top k"
"kth largest" / "kth smallest"
"merge k sorted"
"smallest/largest available"
"median from stream"
"minimum cost by combining two smallest"
"schedule by earliest end time"
"repeatedly pick max/min"
```

---

## 6. Step-by-Step Algorithms

### Pattern 1: Kth Largest with Min-Heap of Size K

```text
1. Create a min-heap.
2. Add each number.
3. If heap size exceeds k, remove the smallest.
4. At the end, heap.peek() is the kth largest.
```

Why this works: the heap always keeps the largest `k` numbers seen so far. The smallest among those `k` is the kth largest overall.

### Pattern 2: Top K Frequent

```text
1. Count frequency with HashMap.
2. Keep a min-heap ordered by frequency.
3. Push each unique item.
4. If heap size exceeds k, pop the least frequent.
5. Remaining heap entries are top k frequent items.
```

### Pattern 3: Merge K Sorted Lists

```text
1. Put the head of each list into a min-heap ordered by node value.
2. Pop the smallest node.
3. Append it to the result.
4. If that node has a next node, push next into heap.
5. Continue until heap is empty.
```

### Pattern 4: Median from Stream

```text
1. Max-heap left stores smaller half.
2. Min-heap right stores larger half.
3. Keep sizes balanced: left.size >= right.size and difference <= 1.
4. Median is:
   - left.peek() if odd count
   - average(left.peek(), right.peek()) if even count
```

---

## 7. Dry Run with Example

### Kth Largest

Input: `nums = [3, 2, 1, 5, 6, 4]`, `k = 2`

```text
heap=[]

add 3 -> [3]
add 2 -> [2,3]
add 1 -> [1,3,2], size>2 -> pop 1 -> [2,3]
add 5 -> [2,3,5], size>2 -> pop 2 -> [3,5]
add 6 -> [3,5,6], size>2 -> pop 3 -> [5,6]
add 4 -> [4,6,5], size>2 -> pop 4 -> [5,6]

heap.peek() = 5
2nd largest = 5
```

### Median Stream

Input stream: `5, 15, 1, 3`

```text
add 5:
left=[5], right=[]
median=5

add 15:
left=[5], right=[15]
median=(5+15)/2=10

add 1:
left=[5,1], right=[15]
median=5

add 3:
left=[3,1], right=[5,15]
median=(3+5)/2=4
```

---

## 8. Code Implementation

### Kth Largest Element

```java
int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    for (int num : nums) {
        minHeap.offer(num);
        if (minHeap.size() > k) minHeap.poll();
    }

    return minHeap.peek();
}
```

### Top K Frequent Elements

```java
int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int num : nums) freq.put(num, freq.getOrDefault(num, 0) + 1);

    PriorityQueue<Integer> heap =
        new PriorityQueue<>((a, b) -> Integer.compare(freq.get(a), freq.get(b)));

    for (int num : freq.keySet()) {
        heap.offer(num);
        if (heap.size() > k) heap.poll();
    }

    int[] result = new int[k];
    for (int i = k - 1; i >= 0; i--) result[i] = heap.poll();
    return result;
}
```

### Merge K Sorted Lists

```java
ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> heap =
        new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

    for (ListNode node : lists) {
        if (node != null) heap.offer(node);
    }

    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    while (!heap.isEmpty()) {
        ListNode node = heap.poll();
        tail.next = node;
        tail = tail.next;

        if (node.next != null) heap.offer(node.next);
    }

    return dummy.next;
}
```

### Median Finder

```java
class MedianFinder {
    private PriorityQueue<Integer> left;  // max-heap
    private PriorityQueue<Integer> right; // min-heap

    public MedianFinder() {
        left = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
        right = new PriorityQueue<>();
    }

    public void addNum(int num) {
        if (left.isEmpty() || num <= left.peek()) left.offer(num);
        else right.offer(num);

        if (left.size() > right.size() + 1) right.offer(left.poll());
        else if (right.size() > left.size()) left.offer(right.poll());
    }

    public double findMedian() {
        if (left.size() > right.size()) return left.peek();
        return ((double) left.peek() + right.peek()) / 2.0;
    }
}
```

### Minimum Cost to Connect Ropes

```java
int minCost(int[] ropes) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    for (int rope : ropes) heap.offer(rope);

    int cost = 0;
    while (heap.size() > 1) {
        int a = heap.poll();
        int b = heap.poll();
        int merged = a + b;
        cost += merged;
        heap.offer(merged);
    }

    return cost;
}
```

---

## 9. Time Complexity

| Problem | Time | Space | Why |
|---|---|---|---|
| Kth largest with heap | O(n log k) | O(k) | Heap keeps only k elements |
| Top K frequent | O(n + u log k) | O(u + k) | u unique values |
| Merge K lists | O(n log k) | O(k) | One heap entry per list |
| Median stream insert | O(log n) | O(n) | Rebalance two heaps |
| Connect ropes | O(n log n) | O(n) | Repeatedly combine two smallest |

---

## 10. Space Complexity

Heap space depends on what the heap stores:
- Top K problems: O(k)
- K-way merge: O(k)
- Median stream: O(n)
- Scheduling all tasks/events: often O(n)

---

## 11. Edge Cases

| Scenario | How to Handle |
|---|---|
| `k = 1` | Heap still works; answer is max/min |
| `k = nums.length` | Kth largest is min element |
| Empty input | Decide return value or throw exception based on problem |
| Duplicate values | Heap handles duplicates naturally |
| Comparator overflow | Use `Integer.compare(a, b)` instead of `a - b` |
| Median average overflow | Cast before addition: `((double)a + b) / 2` |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Using a max-heap for kth largest and popping k times
// This is O(n + k log n). It works, but min-heap size k is better for streaming.

// MISTAKE 2: Comparator overflow
(a, b) -> a - b              // risky
(a, b) -> Integer.compare(a, b) // safer

// MISTAKE 3: Keeping all elements for top K
// If only top K is needed, keep heap size <= k.

// MISTAKE 4: Forgetting to rebalance two heaps in MedianFinder
// left.size should be equal to right.size OR exactly one larger.

// MISTAKE 5: Polling from an empty heap
// Always check heap size when input can be empty.
```

---

## 13. Interview-Level Explanation

**Q: "Why use a min-heap for kth largest?"**

> "I keep a min-heap of size k containing the k largest values seen so far. If a new value enters and the size becomes k+1, I remove the smallest among those candidates. At the end, the heap root is the smallest value among the top k, which is exactly the kth largest."

**Q: "How do two heaps maintain the median?"**

> "A max-heap stores the smaller half and a min-heap stores the larger half. I rebalance so the smaller half has either the same count or one extra. That makes the median available in O(1): either the max of the left half or the average of both roots."

---

## 14. Real-World Use Cases

| Application | Heap Usage |
|---|---|
| Operating systems | Process scheduling by priority |
| Search engines | Top results by score |
| Analytics dashboards | Top K events/users/errors |
| Streaming systems | Running median or percentile approximation |
| Distributed systems | Merge sorted logs from many machines |
| Graph algorithms | Dijkstra and Prim priority selection |

---

## 15. Variations of This Pattern

| Variation | Key Idea | Example |
|---|---|---|
| Top K | Min-heap size k | Kth Largest, Top K Frequent |
| K-way merge | Min-heap of current heads | Merge K Sorted Lists |
| Two heaps | Split lower/upper halves | Median from Data Stream |
| Greedy combine | Always combine two smallest | Connect Ropes |
| Scheduling | Heap by end time/priority | Meeting Rooms, Task Scheduler |
| Graph shortest path | Heap by tentative distance | Dijkstra |

---

## 16. Practice Problems

### Easy — Foundation

1. **Last Stone Weight** (LeetCode #1046)
   - Task: Repeatedly smash two largest stones.
   - Hint: Max-heap.

2. **Kth Largest Element in a Stream** (LeetCode #703)
   - Task: Maintain kth largest after each insertion.
   - Hint: Min-heap of size k.

3. **Relative Ranks** (LeetCode #506)
   - Task: Rank scores.
   - Hint: Heap or sorting.

### Medium — Core Heap Patterns

1. **Kth Largest Element in an Array** (LeetCode #215)
   - Task: Return kth largest.
   - Hint: Min-heap size k, or Quickselect.

2. **Top K Frequent Elements** (LeetCode #347)
   - Task: Return k most frequent values.
   - Hint: Frequency map + min-heap.

3. **K Closest Points to Origin** (LeetCode #973)
   - Task: Return k closest points.
   - Hint: Max-heap size k by distance.

4. **Merge K Sorted Lists** (LeetCode #23)
   - Task: Merge sorted linked lists.
   - Hint: Min-heap of list heads.

5. **Find Median from Data Stream** (LeetCode #295)
   - Task: Support add and median query.
   - Hint: Two heaps.

### Hard — Advanced

1. **Sliding Window Median** (LeetCode #480)
   - Task: Median for each window.
   - Hint: Two heaps + lazy deletion.

2. **IPO** (LeetCode #502)
   - Task: Pick at most k projects to maximize capital.
   - Hint: Min-heap by capital, max-heap by profit.

3. **The Skyline Problem** (LeetCode #218)
   - Task: Track active building heights.
   - Hint: Sweep line + max-heap / TreeMap.

---

## 17. How to Know You Have Mastered Heap Patterns

You have mastered this topic when you can:
- [ ] Decide when heap beats sorting
- [ ] Implement kth largest with a min-heap of size k
- [ ] Explain why top K often uses the opposite heap type
- [ ] Merge K sorted streams in O(n log k)
- [ ] Implement MedianFinder with two heaps
- [ ] Avoid comparator overflow in Java
- [ ] Recognize when Quickselect is a better alternative
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Why does kth largest use a min-heap instead of a max-heap?
2. What is the time complexity of merging K sorted lists with total N nodes?
3. In MedianFinder, why does the left heap usually store one extra item?
4. For Top K Frequent, what should the heap comparator prioritize?
5. Why is `(a, b) -> a - b` risky?
6. If data arrives as an infinite stream, why is a heap useful?
7. When would Quickselect be better than heap for kth largest?
8. What invariant must two heaps maintain for median stream?

> **Answers:**
> 1. A min-heap of size k keeps the largest k values; its root is the kth largest.
> 2. O(N log K), because each node is pushed/popped once and the heap size is at most K.
> 3. So odd-sized streams can return `left.peek()` directly as the median.
> 4. Frequency ascending for a min-heap, so the least frequent candidate is removed first.
> 5. Integer subtraction can overflow; `Integer.compare` is safer.
> 6. It keeps only the important candidates instead of sorting everything repeatedly.
> 7. For one offline kth-largest query where average O(n) is desired and mutation is allowed.
> 8. Every left value <= every right value, and sizes differ by at most one.

---

**Next →** `../15_Graphs/01_Graph_Algorithms.md`
