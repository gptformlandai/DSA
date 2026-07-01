# Section 2.5 — Queue & Deque

---

## 1. What Problem Does This Solve?

A queue solves problems where you need **First-In First-Out (FIFO)** access — the item added earliest must be processed first. Classic uses: BFS traversal, scheduling, sliding window maximum/minimum, and rate limiting.

A Deque (double-ended queue) solves problems requiring fast insertion and deletion at both ends — making it ideal for sliding window problems.

---

## 2. Beginner-Friendly Intuition

**Queue:** A line at a bank teller. First person to arrive is first to be served. You add to the back, remove from the front.

**Deque:** A line with a VIP entrance at both ends. You can add or remove from either the front or the back. This gives you both queue (FIFO) and stack (LIFO) behavior from a single structure.

---

## 3. Real-World Analogy

**Queue — Print spooler:** Documents sent to a printer wait in a queue. The first document sent prints first. New documents go to the back.

**Deque — Sliding window:** You're watching the last K temperatures. When a new day comes, you add to the back. When the window moves, you remove from the front. When a new temperature is larger than some old values (they can never be the window max), you remove those from the back too.

---

## 4. Core Concept

### Queue Operations
| Operation | Description | Time |
|-----------|-------------|------|
| offer(x) / add(x) | Add to back | O(1) |
| poll() / remove() | Remove from front | O(1) |
| peek() | View front | O(1) |
| isEmpty() | Check empty | O(1) |

### Deque Operations
| Operation | Description | Time |
|-----------|-------------|------|
| offerFirst(x) / addFirst(x) | Add to front | O(1) |
| offerLast(x) / addLast(x) | Add to back | O(1) |
| pollFirst() / removeFirst() | Remove from front | O(1) |
| pollLast() / removeLast() | Remove from back | O(1) |
| peekFirst() / peekLast() | View front/back | O(1) |

### Java Queue/Deque Implementations
```java
// Queue (FIFO)
Queue<Integer> queue = new LinkedList<>();
Queue<Integer> queue = new ArrayDeque<>();  // faster, no null support

// Deque (double-ended)
Deque<Integer> deque = new ArrayDeque<>();

// Priority Queue (min-heap, NOT FIFO)
PriorityQueue<Integer> pq = new PriorityQueue<>();
```

---

## 5. Pattern Recognition Signals

```
"Level-order BFS traversal" → Queue
"Process in order of arrival" → Queue
"Sliding window maximum/minimum" → Monotonic Deque
"Implement stack using queues" → Two queues
"Implement queue using stacks" → Two stacks
"First non-repeating character in stream" → Queue + frequency array
"K elements from deque" → Sliding window Deque
"Moving average of data stream" → Queue of fixed size
```

---

## 6. Step-by-Step Algorithm

### BFS Level-Order Traversal
```
queue.add(root)
while queue not empty:
    size = queue.size()         // nodes at current level
    for i = 0 to size-1:
        node = queue.poll()
        process(node)
        if node.left != null: queue.add(node.left)
        if node.right != null: queue.add(node.right)
```

### Sliding Window Maximum (Monotonic Deque)
```
For each index i from 0 to n-1:
    // Remove elements outside the window
    while deque not empty AND deque.front() < i-k+1:
        deque.pollFirst()
    
    // Maintain monotonic decreasing order
    while deque not empty AND arr[deque.back()] <= arr[i]:
        deque.pollLast()
    
    deque.offerLast(i)
    
    if i >= k-1:
        result[i-k+1] = arr[deque.peekFirst()]  // front = max
```

---

## 7. Dry Run with Example

### Sliding Window Maximum: [1,3,-1,-3,5,3,6,7], k=3
```
result = []
deque = [] (stores indices, maintains decreasing order of values)

i=0: arr[0]=1, deque=[0]
i=1: arr[1]=3, 1≤3 → pop 0, deque=[1]
i=2: arr[2]=-1, deque=[1,2]. Window complete! result[0]=arr[deque.front()]=arr[1]=3
i=3: arr[3]=-3, deque=[1,2,3]. Front=1 ≥ 3-3+1=1 → ok. result[1]=arr[1]=3
i=4: arr[4]=5, -3≤5→pop 3, -1≤5→pop 2, 3≤5→pop 1. deque=[4]. result[2]=arr[4]=5
i=5: arr[5]=3, deque=[4,5]. Front=4 ≥ 5-3+1=3 → ok. result[3]=arr[4]=5
i=6: arr[6]=6, 3≤6→pop 5, 5≤6→pop 4. deque=[6]. result[4]=arr[6]=6
i=7: arr[7]=7, 6≤7→pop 6. deque=[7]. result[5]=arr[7]=7

result = [3, 3, 5, 5, 6, 7] ✓
```

---

## 8. Code Implementation

```java
import java.util.*;

public class QueueDequeAlgorithms {

    // ── BFS Level Order Traversal ──────────────────────────────────────────
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size(); // nodes at current level
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                level.add(node.val);
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(level);
        }
        return result;
    }

    // ── Sliding Window Maximum ─────────────────────────────────────────────
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new ArrayDeque<>(); // stores indices

        for (int i = 0; i < n; i++) {
            // Remove indices outside window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1)
                deque.pollFirst();
            // Maintain decreasing order — remove smaller elements from back
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i])
                deque.pollLast();
            deque.offerLast(i);
            // Record max when window is full
            if (i >= k - 1) result[i - k + 1] = nums[deque.peekFirst()];
        }
        return result;
    }

    // ── Implement Queue Using Two Stacks ────────────────────────────────────
    class MyQueue {
        Deque<Integer> inbox = new ArrayDeque<>();   // for push
        Deque<Integer> outbox = new ArrayDeque<>();  // for pop/peek

        public void push(int x) { inbox.push(x); }

        public int pop() {
            peek();
            return outbox.pop();
        }

        public int peek() {
            if (outbox.isEmpty())                   // transfer when empty
                while (!inbox.isEmpty()) outbox.push(inbox.pop());
            return outbox.peek();
        }

        public boolean empty() { return inbox.isEmpty() && outbox.isEmpty(); }
    }

    // ── Moving Average ─────────────────────────────────────────────────────
    class MovingAverage {
        Queue<Integer> window;
        int size, sum = 0;

        MovingAverage(int size) {
            this.size = size;
            window = new ArrayDeque<>();
        }

        double next(int val) {
            if (window.size() == size) sum -= window.poll(); // evict oldest
            window.offer(val);
            sum += val;
            return (double) sum / window.size();
        }
    }
}
```

---

## 9. Time Complexity

| Algorithm | Time | Notes |
|-----------|------|-------|
| Queue offer/poll/peek | O(1) | Constant |
| Deque all operations | O(1) | Constant |
| BFS level order | O(n) | Each node processed once |
| Sliding window max | O(n) | Each element added/removed once |
| Queue via two stacks | O(1) amortized | Each element moves twice total |

---

## 10. Space Complexity

| Algorithm | Space |
|-----------|-------|
| Queue | O(n) |
| Deque | O(n) |
| Sliding window deque | O(k) window size |
| BFS | O(w) where w = max width of tree |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Empty queue poll() | Returns null (offer/poll) or throws (add/remove) |
| Window k = 1 | Sliding max = original array |
| Window k = n | Sliding max = single global max |
| All same elements in window | Deque retains only the most recent |
| Single element input | BFS, sliding window all handle correctly |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Using LinkedList as Queue (slower)
Queue<Integer> q = new LinkedList<>(); // OK but slower
Queue<Integer> q = new ArrayDeque<>(); // BETTER: no null support needed

// MISTAKE 2: Calling queue.remove() on empty queue (throws exception)
queue.remove(); // THROWS NoSuchElementException if empty
queue.poll();   // SAFE: returns null if empty

// MISTAKE 3: Sliding window deque — storing values instead of indices
deque.offerLast(nums[i]); // WRONG: can't check if index is out of window
deque.offerLast(i);       // CORRECT: store index, check deque.peekFirst() < i-k+1

// MISTAKE 4: Not removing out-of-window indices BEFORE adding new element
// Correct order:
// 1. Remove expired front
// 2. Remove smaller elements from back (maintain monotonic property)
// 3. Add new element to back
// 4. Record result if window full

// MISTAKE 5: Checking window condition wrong
if (i >= k)   // WRONG: first valid window is at i = k-1
if (i >= k-1) // CORRECT
```

---

## 13. Interview-Level Explanation

**Q: "Why does ArrayDeque outperform LinkedList for Queue/Stack operations?"**

> "LinkedList allocates a separate node object for each element, leading to scattered memory and cache misses. ArrayDeque uses a resizable circular array — elements are stored contiguously in memory. Modern CPUs prefetch contiguous memory efficiently, so ArrayDeque has better cache performance. Additionally, ArrayDeque avoids per-element object allocation overhead."

**Q: "How does the monotonic deque achieve O(n) for sliding window maximum?"**

> "Each element is pushed onto the deque exactly once and popped at most once — either from the front when it leaves the window, or from the back when a larger element arrives. So the total operations are 2n: n pushes and at most n pops. Even though there's a while loop inside the for loop, the amortized cost per element is O(1), giving O(n) total."

---

## 14. Real-World Use Cases

| Application | Queue/Deque |
|------------|------------|
| **BFS graph traversal** | Queue (FIFO) |
| **OS task scheduling** | Queue (FIFO, priority variants) |
| **Web server request handling** | Request queue |
| **Sliding window monitoring** | Monotonic deque |
| **Undo/Redo with size limit** | Bounded deque |
| **Producer-consumer pattern** | BlockingQueue in Java |

---

## 15. Variations

| Variation | Technique |
|-----------|----------|
| Priority Queue (min/max heap) | `PriorityQueue<>` in Java |
| Circular Queue | Fixed-size array with head/tail pointers |
| Sliding window min | Monotonic increasing deque |
| Queue with max | Deque tracking maximum |
| Bounded Queue | ArrayDeque with size check |
| Two-stack queue | Amortized O(1) per operation |

---

## 16. Practice Problems

### Easy — Foundation
1. **Implement Queue using Stacks** (LeetCode #232)
   - *Task:* FIFO queue using two LIFO stacks.
   - *Hint:* inbox/outbox pattern; transfer on empty outbox.

2. **Number of Islands (BFS version)** (LeetCode #200)
   - *Task:* Count connected '1' regions.
   - *Hint:* BFS from each unvisited '1'.

3. **Moving Average from Data Stream** (LeetCode #346)
   - *Task:* Moving average over last k elements.
   - *Hint:* Queue of size k + running sum.

### Medium — Core
1. **Sliding Window Maximum** (LeetCode #239)
   - *Task:* Max in each window of size k.
   - *Hint:* Monotonic decreasing deque of indices.

2. **Binary Tree Level Order Traversal** (LeetCode #102)
   - *Task:* Level-by-level traversal.
   - *Hint:* Queue; record size at each level start.

3. **Design Hit Counter** (LeetCode #362)
   - *Task:* Count hits in last 300 seconds.
   - *Hint:* Queue with timestamps; evict old entries.

4. **Open the Lock** (LeetCode #752)
   - *Task:* Minimum turns to reach target combination.
   - *Hint:* BFS — each state is a 4-digit string.

5. **Perfect Squares** (LeetCode #279)
   - *Task:* Minimum perfect squares summing to n.
   - *Hint:* BFS level = number of squares used.

### Hard — Advanced
1. **Shortest Path in Binary Matrix** (LeetCode #1091)
   - *Task:* Shortest path from top-left to bottom-right.
   - *Hint:* BFS with 8-directional movement.

2. **Jump Game VI** (LeetCode #1696)
   - *Task:* Max score jumping 1 to k steps.
   - *Hint:* DP + monotonic deque for window maximum.

3. **Longest Continuous Subarray with Abs Diff ≤ Limit** (LeetCode #1438)
   - *Task:* Longest subarray with max-min ≤ limit.
   - *Hint:* Two monotonic deques (max and min) + sliding window.

---

## 17. How to Know You Have Mastered Queue & Deque

You have mastered this topic when you can:
- [ ] Implement BFS with level-order tracking using a Queue
- [ ] Implement Queue using Two Stacks correctly
- [ ] Implement Sliding Window Maximum using a monotonic deque
- [ ] Explain the difference between `offer/poll` vs `add/remove`
- [ ] Know when to use ArrayDeque vs LinkedList
- [ ] Recognize sliding window max/min as a monotonic deque problem
- [ ] Explain O(n) amortized analysis of monotonic deque
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. `ArrayDeque.offer(x)` adds to which end? `push(x)` adds to which end?

2. For Sliding Window Maximum with k=3 on [5,4,3,2,1], what is the result?

3. In the two-stack Queue, what is the worst-case time for a single `pop()` call? What is the amortized time?

4. Queue operations: `offer/poll` vs `add/remove`. What is the difference in behavior on empty/full queues?

5. BFS on a grid: why use a Queue instead of a Stack?

6. Monotonic deque for sliding window max stores what in the deque — values or indices?

7. After processing [8, 5, 6, 2, 7], k=3 with a monotonic decreasing deque, what does the deque contain at i=4 (value 7)?

8. Why does `peekFirst()` of the monotonic deque give the window maximum?

> **Answers:**
> 1. `offer(x)` adds to the BACK (tail) — FIFO queue behavior. `push(x)` adds to the FRONT (head) — LIFO stack behavior.
> 2. [5, 4, 3] — strictly decreasing, so max of any window is always the leftmost element. Windows: [5,4,3]→5, [4,3,2]→4, [3,2,1]→3.
> 3. Worst case for a single pop: O(n) — if outbox is empty, all n elements transfer from inbox to outbox. Amortized: O(1) — each element crosses from inbox to outbox exactly once over its lifetime.
> 4. `offer/poll`: return false/null gracefully on failure. `add/remove`: throw exceptions (IllegalStateException / NoSuchElementException). Use offer/poll for safe code.
> 5. BFS finds the shortest path (minimum hops) because it explores level by level (all nodes at distance 1, then 2, etc.). A Stack gives DFS, which doesn't guarantee shortest path.
> 6. Indices — so we can check if an index is outside the current window (`deque.peekFirst() < i-k+1`).
> 7. deque = [4] (index of value 7). When processing i=4 (value=7): remove 2 (idx=3, val≤7), remove 6 (idx=2, val≤7), remove 8 would be checked but was already the max — let me re-trace: i=0:val=8,deque=[0]. i=1:val=5,5≤8? No→deque=[0,1]. i=2:val=6,5≤6→pop1,6≤8?no→deque=[0,2]. i=3:val=2,deque=[0,2,3]. i=4:val=7, front=0<4-3+1=2→pollFirst→deque=[2,3]. 2≤7→pop3. 6≤7→pop2. deque=[4]. Answer: deque=[4] (index 4, value 7).
> 8. The deque maintains a monotonically decreasing order of values. The largest value in the current window is always at the front because: any element smaller than a new arriving element gets removed from the back (it can never be the window max for any future window). The front holds the maximum seen so far that's still within the window.

---

**Next →** `06_HashMap_HashSet.md`

Some problems need processing in **arrival order** (First-In-First-Out).  
Examples: BFS traversal, task scheduling, sliding window maximum.

---

## 2. Queue — FIFO

```
ENQUEUE 10: [10]
ENQUEUE 20: [10, 20]
ENQUEUE 30: [10, 20, 30]
DEQUEUE:    [20, 30]  → returns 10
PEEK:       20        → front
```

Real-world: Ticket line, print queue, CPU task scheduler.

```java
// Java Queue using LinkedList
Queue<Integer> queue = new LinkedList<>();
queue.offer(10);      // enqueue
queue.poll();         // dequeue (removes front)
queue.peek();         // peek front
queue.isEmpty();

// Preferred: ArrayDeque
Deque<Integer> queue = new ArrayDeque<>();
queue.offerLast(10);  // enqueue at back
queue.pollFirst();    // dequeue from front
queue.peekFirst();
```

---

## 3. Deque (Double-Ended Queue)

Add/remove from **both ends** in O(1).

```java
Deque<Integer> deque = new ArrayDeque<>();
deque.addFirst(1);   // or push
deque.addLast(2);    // or offer
deque.removeFirst(); // or poll
deque.removeLast();
deque.peekFirst();
deque.peekLast();
```

---

## 4. BFS Uses Queue

```java
void bfs(int[][] grid, int startRow, int startCol) {
    Queue<int[]> queue = new LinkedList<>();
    queue.offer(new int[]{startRow, startCol});
    boolean[][] visited = new boolean[rows][cols];
    visited[startRow][startCol] = true;

    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int r = curr[0], c = curr[1];
        // process (r, c)
        for (int[] dir : new int[][]{{0,1},{0,-1},{1,0},{-1,0}}) {
            int nr = r + dir[0], nc = c + dir[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && !visited[nr][nc]) {
                visited[nr][nc] = true;
                queue.offer(new int[]{nr, nc});
            }
        }
    }
}
```

---

## 5. Sliding Window Maximum (Monotonic Deque)

**Problem:** Find max in every window of size k.

**Key Idea:** Maintain a decreasing deque of indices. Front is always the max.

```java
int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new ArrayDeque<>(); // stores indices

    for (int i = 0; i < n; i++) {
        // Remove indices outside window
        while (!deque.isEmpty() && deque.peekFirst() < i - k + 1)
            deque.pollFirst();
        // Remove smaller elements from back (they'll never be max)
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i])
            deque.pollLast();
        deque.offerLast(i);
        // Record result once first window is complete
        if (i >= k - 1)
            result[i - k + 1] = nums[deque.peekFirst()];
    }
    return result;
}
```

**Dry Run:** nums=[1,3,-1,-3,5,3,6,7], k=3
```
i=0: deque=[0]
i=1: 3>1→ remove 0, deque=[1]
i=2: deque=[1,2], result[0] = nums[1] = 3
i=3: deque=[1,2,3], result[1] = nums[1] = 3
i=4: 5>all → clear deque, deque=[4], result[2] = 5
...
```

---

## 6. Circular Queue

```java
class CircularQueue {
    int[] data;
    int head = 0, tail = 0, size = 0, capacity;

    CircularQueue(int k) { data = new int[k]; capacity = k; }

    boolean enQueue(int val) {
        if (isFull()) return false;
        data[tail] = val;
        tail = (tail + 1) % capacity;
        size++;
        return true;
    }
    int deQueue() {
        if (isEmpty()) return -1;
        int val = data[head];
        head = (head + 1) % capacity;
        size--;
        return val;
    }
    boolean isEmpty() { return size == 0; }
    boolean isFull() { return size == capacity; }
}
```

---

## 7. Operations Complexity

| Operation | Queue (LinkedList) | ArrayDeque |
|-----------|------------------|------------|
| offer/enqueue | O(1) | O(1) amortized |
| poll/dequeue | O(1) | O(1) |
| peek | O(1) | O(1) |
| Access by index | O(n) | O(n) |

---

## 8. Practice Problems

**Easy:**
1. Implement Queue using two stacks.
2. Number of recent calls.
3. Moving average from data stream.

**Medium:**
1. Sliding Window Maximum.
2. First negative number in every window of size k.
3. Design Hit Counter.
4. Task Scheduler (use queue + greedy).
5. Binary Tree Level Order Traversal (BFS).

**Hard:**
1. LRU Cache (HashMap + Deque).
2. Jump Game VI (Deque DP).
3. Shortest Subarray with Sum at Least K.

---

**Next →** `06_HashMap_HashSet.md`
