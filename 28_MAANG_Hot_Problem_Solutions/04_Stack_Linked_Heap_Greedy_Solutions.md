# Hot 150 Solutions - Stack, Linked List, Heap, Intervals, Greedy

> This batch covers implementation-heavy topics where interviews often test clean invariants and edge cases.

---

## Shared Linked List Model

```java
class ListNode {
    int val;
    ListNode next;

    ListNode(int val) {
        this.val = val;
    }
}
```

---

## 20. Valid Parentheses

- Pattern: Stack matching
- Difficulty: Easy
- Company signal: Stack baseline

### Java Solution

```java
boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (char ch : s.toCharArray()) {
        if (ch == '(') stack.push(')');
        else if (ch == '[') stack.push(']');
        else if (ch == '{') stack.push('}');
        else if (stack.isEmpty() || stack.pop() != ch) return false;
    }

    return stack.isEmpty();
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)`

### Common Mistake

Returning true before checking the stack is empty misses unmatched opening brackets.

### Interview Explanation

I push the expected closing bracket whenever I see an opening bracket. For every closing bracket, it must match the top expected bracket. At the end, the stack must be empty.

---

## 155. Min Stack

- Pattern: Stack with current minimum
- Difficulty: Medium
- Company signal: Design with `O(1)` operations

### Java Solution

```java
class MinStack {
    Deque<int[]> stack = new ArrayDeque<>();

    public void push(int val) {
        int min = stack.isEmpty() ? val : Math.min(val, stack.peek()[1]);
        stack.push(new int[] {val, min});
    }

    public void pop() {
        stack.pop();
    }

    public int top() {
        return stack.peek()[0];
    }

    public int getMin() {
        return stack.peek()[1];
    }
}
```

### Complexity

- Time: `O(1)` for all operations
- Space: `O(n)`

### Common Mistake

Keeping only one global min fails after popping the min value.

### Interview Explanation

Each stack entry stores both the value and the minimum at that moment. When I pop, the previous minimum is automatically restored because it is stored with the next stack entry.

---

## 150. Evaluate Reverse Polish Notation

- Pattern: Operand stack
- Difficulty: Medium
- Company signal: Stack expression evaluation

### Java Solution

```java
int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();

    for (String token : tokens) {
        if ("+-*/".contains(token) && token.length() == 1) {
            int b = stack.pop();
            int a = stack.pop();

            if (token.equals("+")) stack.push(a + b);
            else if (token.equals("-")) stack.push(a - b);
            else if (token.equals("*")) stack.push(a * b);
            else stack.push(a / b);
        } else {
            stack.push(Integer.parseInt(token));
        }
    }

    return stack.pop();
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)`

### Common Mistake

Reversing operands for `-` and `/`.

### Interview Explanation

Numbers go onto the stack. When I see an operator, I pop the right operand first, then the left operand, apply the operation, and push the result back.

---

## 22. Generate Parentheses

- Pattern: Backtracking with counts
- Difficulty: Medium
- Company signal: Clean recursion constraints

### Java Solution

```java
List<String> generateParenthesis(int n) {
    List<String> ans = new ArrayList<>();
    dfs(n, 0, 0, new StringBuilder(), ans);
    return ans;
}

void dfs(int n, int open, int close, StringBuilder path, List<String> ans) {
    if (path.length() == 2 * n) {
        ans.add(path.toString());
        return;
    }

    if (open < n) {
        path.append('(');
        dfs(n, open + 1, close, path, ans);
        path.deleteCharAt(path.length() - 1);
    }

    if (close < open) {
        path.append(')');
        dfs(n, open, close + 1, path, ans);
        path.deleteCharAt(path.length() - 1);
    }
}
```

### Complexity

- Time: `O(Catalan(n) * n)`
- Space: `O(n)` recursion depth, excluding output

### Common Mistake

Allowing `close > open` creates invalid prefixes.

### Interview Explanation

I build only valid prefixes. I can add an opening bracket if I still have openings left. I can add a closing bracket only if it will not exceed the number of openings used.

---

## 739. Daily Temperatures

- Pattern: Monotonic decreasing stack
- Difficulty: Medium
- Company signal: Next greater element

### Java Solution

```java
int[] dailyTemperatures(int[] temperatures) {
    int n = temperatures.length;
    int[] ans = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
            int prev = stack.pop();
            ans[prev] = i - prev;
        }
        stack.push(i);
    }

    return ans;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)`

### Common Mistake

Storing temperatures only loses the index needed to compute days waited.

### Interview Explanation

The stack stores indices waiting for a warmer day. When the current temperature is warmer than the top index, it resolves that previous day. Each index is pushed and popped at most once.

---

## 84. Largest Rectangle in Histogram

- Pattern: Monotonic increasing stack
- Difficulty: Hard
- Company signal: Must-know monotonic stack hard

### Java Solution

```java
int largestRectangleArea(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int best = 0;

    for (int i = 0; i <= heights.length; i++) {
        int curr = i == heights.length ? 0 : heights[i];

        while (!stack.isEmpty() && curr < heights[stack.peek()]) {
            int h = heights[stack.pop()];
            int leftLess = stack.isEmpty() ? -1 : stack.peek();
            int width = i - leftLess - 1;
            best = Math.max(best, h * width);
        }

        stack.push(i);
    }

    return best;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)`

### Common Mistake

Forgetting the sentinel zero at the end leaves bars unresolved.

### Interview Explanation

The stack keeps indices with increasing heights. When a lower height appears, it means the popped bar's right boundary is known. The new stack top gives the previous smaller bar on the left, so I can compute that bar's maximum rectangle.

---

## 206. Reverse Linked List

- Pattern: Pointer reversal
- Difficulty: Easy
- Company signal: Linked list baseline

### Java Solution

```java
ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;

    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }

    return prev;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Overwriting `curr.next` before saving the original next node loses the rest of the list.

### Interview Explanation

I keep previous, current, and next pointers. For each node, I save the next node, reverse the current pointer, then move both pointers forward. At the end, `prev` is the new head.

---

## 21. Merge Two Sorted Lists

- Pattern: Dummy node merge
- Difficulty: Easy
- Company signal: Pointer discipline

### Java Solution

```java
ListNode mergeTwoLists(ListNode a, ListNode b) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;

    while (a != null && b != null) {
        if (a.val <= b.val) {
            tail.next = a;
            a = a.next;
        } else {
            tail.next = b;
            b = b.next;
        }
        tail = tail.next;
    }

    tail.next = a != null ? a : b;
    return dummy.next;
}
```

### Complexity

- Time: `O(m + n)`
- Space: `O(1)`

### Common Mistake

Forgetting to attach the remaining non-empty list.

### Interview Explanation

I use a dummy head to simplify edge cases. At each step, I attach the smaller current node and advance that list. Once one list ends, I attach the rest of the other list.

---

## 143. Reorder List

- Pattern: Middle + reverse + merge
- Difficulty: Medium
- Company signal: Multi-step linked-list reasoning

### Java Solution

```java
void reorderList(ListNode head) {
    if (head == null || head.next == null) return;

    ListNode slow = head;
    ListNode fast = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    ListNode second = reverse(slow.next);
    slow.next = null;

    ListNode first = head;
    while (second != null) {
        ListNode t1 = first.next;
        ListNode t2 = second.next;
        first.next = second;
        second.next = t1;
        first = t1;
        second = t2;
    }
}

ListNode reverse(ListNode head) {
    ListNode prev = null;
    while (head != null) {
        ListNode next = head.next;
        head.next = prev;
        prev = head;
        head = next;
    }
    return prev;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Not cutting the first half before merging can create a cycle.

### Interview Explanation

I split the list at the middle, reverse the second half, then weave the two halves together. Cutting after the first half prevents cycles during the merge.

---

## 19. Remove Nth Node From End

- Pattern: Fast/slow pointers
- Difficulty: Medium
- Company signal: One-pass linked-list deletion

### Java Solution

```java
ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;

    ListNode fast = dummy;
    ListNode slow = dummy;

    for (int i = 0; i < n; i++) fast = fast.next;

    while (fast.next != null) {
        fast = fast.next;
        slow = slow.next;
    }

    slow.next = slow.next.next;
    return dummy.next;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Without a dummy node, deleting the head becomes a special case.

### Interview Explanation

I create a gap of `n` nodes between fast and slow. When fast reaches the end, slow is right before the node to remove. The dummy node handles removing the original head cleanly.

---

## 141. Linked List Cycle

- Pattern: Floyd slow/fast pointers
- Difficulty: Easy
- Company signal: Cycle detection baseline

### Java Solution

```java
boolean hasCycle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }

    return false;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Checking `fast.next.next` without verifying `fast.next != null`.

### Interview Explanation

If there is a cycle, the fast pointer eventually laps the slow pointer. If there is no cycle, fast reaches null. This detects cycles using constant extra space.

---

## 146. LRU Cache

- Pattern: HashMap + doubly linked list
- Difficulty: Medium
- Company signal: Top design DSA problem

### Java Solution

```java
class LRUCache {
    class Node {
        int key;
        int val;
        Node prev;
        Node next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(0, 0);
    private final Node tail = new Node(0, 0);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        remove(node);
        addFirst(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) remove(map.get(key));

        Node node = new Node(key, value);
        map.put(key, node);
        addFirst(node);

        if (map.size() > capacity) {
            Node lru = tail.prev;
            remove(lru);
            map.remove(lru.key);
        }
    }

    private void addFirst(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
```

### Complexity

- Time: `O(1)` for `get` and `put`
- Space: `O(capacity)`

### Common Mistake

Updating an existing key without moving it to most-recent position.

### Interview Explanation

The hashmap gives direct access to nodes by key. The doubly linked list maintains recency, with most recent near the head and least recent near the tail. Every access moves the node to the front, and eviction removes from the tail.

---

## 23. Merge k Sorted Lists

- Pattern: Min heap k-way merge
- Difficulty: Hard
- Company signal: Heap linked-list classic

### Java Solution

```java
ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> heap =
            new PriorityQueue<>(Comparator.comparingInt(a -> a.val));

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

### Complexity

- Time: `O(n log k)`, where `n` is total nodes
- Space: `O(k)`

### Common Mistake

Putting every node into the heap works but wastes memory.

### Interview Explanation

I put the head of each list into a min heap. Each time I pop the smallest node, I append it to the result and push its next node if it exists. This is the standard k-way merge pattern.

---

## 703. Kth Largest Element in a Stream

- Pattern: Size-k min heap
- Difficulty: Easy
- Company signal: Streaming top-k

### Java Solution

```java
class KthLargest {
    private final int k;
    private final PriorityQueue<Integer> heap = new PriorityQueue<>();

    public KthLargest(int k, int[] nums) {
        this.k = k;
        for (int num : nums) add(num);
    }

    public int add(int val) {
        heap.offer(val);
        if (heap.size() > k) heap.poll();
        return heap.peek();
    }
}
```

### Complexity

- Time: `O(log k)` per add
- Space: `O(k)`

### Common Mistake

Keeping all stream values makes each query heavier than needed.

### Interview Explanation

I keep a min heap containing only the largest `k` values seen so far. The smallest among those values is the kth largest, so it is always at the heap root.

---

## 973. K Closest Points to Origin

- Pattern: Max heap of size k
- Difficulty: Medium
- Company signal: Heap comparator practice

### Java Solution

```java
int[][] kClosest(int[][] points, int k) {
    PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) ->
            Integer.compare(dist(b), dist(a)));

    for (int[] point : points) {
        heap.offer(point);
        if (heap.size() > k) heap.poll();
    }

    int[][] ans = new int[k][2];
    for (int i = 0; i < k; i++) ans[i] = heap.poll();
    return ans;
}

int dist(int[] point) {
    return point[0] * point[0] + point[1] * point[1];
}
```

### Complexity

- Time: `O(n log k)`
- Space: `O(k)`

### Common Mistake

Using square root is unnecessary; squared distance preserves ordering.

### Interview Explanation

I keep a max heap of the best `k` points. If the heap grows beyond `k`, I remove the farthest point. The remaining points are the `k` closest.

---

## 621. Task Scheduler

- Pattern: Frequency greedy
- Difficulty: Medium
- Company signal: Greedy counting formula

### Java Solution

```java
int leastInterval(char[] tasks, int n) {
    int[] freq = new int[26];
    for (char task : tasks) freq[task - 'A']++;
    Arrays.sort(freq);

    int max = freq[25] - 1;
    int idle = max * n;

    for (int i = 24; i >= 0 && freq[i] > 0; i--) {
        idle -= Math.min(max, freq[i]);
    }

    return tasks.length + Math.max(0, idle);
}
```

### Complexity

- Time: `O(n + 26 log 26)`, effectively `O(n)`
- Space: `O(1)`

### Common Mistake

Forgetting that multiple tasks can tie for max frequency.

### Interview Explanation

The most frequent task creates gaps that must be filled by other tasks or idles. I compute the initial idle slots from the max frequency, then subtract slots filled by other tasks. If idle goes negative, no idle time is needed.

---

## 295. Find Median From Data Stream

- Pattern: Two heaps
- Difficulty: Hard
- Company signal: Streaming median classic

### Java Solution

```java
class MedianFinder {
    PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> large = new PriorityQueue<>();

    public void addNum(int num) {
        small.offer(num);
        large.offer(small.poll());

        if (large.size() > small.size()) {
            small.offer(large.poll());
        }
    }

    public double findMedian() {
        if (small.size() > large.size()) return small.peek();
        return (small.peek() + large.peek()) / 2.0;
    }
}
```

### Complexity

- Time: `O(log n)` add, `O(1)` median
- Space: `O(n)`

### Common Mistake

Letting heap sizes differ by more than one.

### Interview Explanation

The max heap stores the lower half, and the min heap stores the upper half. I rebalance so the lower half has equal size or one extra. The median is either the top of the lower half or the average of both tops.

---

## 56. Merge Intervals

- Pattern: Sort + merge
- Difficulty: Medium
- Company signal: Interval foundation

### Java Solution

```java
int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    List<int[]> ans = new ArrayList<>();

    for (int[] interval : intervals) {
        if (ans.isEmpty() || ans.get(ans.size() - 1)[1] < interval[0]) {
            ans.add(interval);
        } else {
            ans.get(ans.size() - 1)[1] =
                    Math.max(ans.get(ans.size() - 1)[1], interval[1]);
        }
    }

    return ans.toArray(new int[ans.size()][]);
}
```

### Complexity

- Time: `O(n log n)`
- Space: `O(n)` for output

### Common Mistake

Sorting by end time does not make merging straightforward; sort by start.

### Interview Explanation

After sorting by start, overlapping intervals appear next to each other. I compare each interval to the last merged interval. If it overlaps, I extend the end; otherwise, I start a new merged interval.

---

## 57. Insert Interval

- Pattern: Three-phase interval merge
- Difficulty: Medium
- Company signal: Clean interval edge handling

### Java Solution

```java
int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> ans = new ArrayList<>();
    int i = 0;

    while (i < intervals.length && intervals[i][1] < newInterval[0]) {
        ans.add(intervals[i++]);
    }

    while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    ans.add(newInterval);

    while (i < intervals.length) ans.add(intervals[i++]);

    return ans.toArray(new int[ans.size()][]);
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)` for output

### Common Mistake

Trying to insert first and sort again loses the `O(n)` advantage of already sorted intervals.

### Interview Explanation

I add all intervals ending before the new interval. Then I merge every interval that overlaps it. Finally, I append the remaining intervals that start after it.

---

## 435. Non-overlapping Intervals

- Pattern: Greedy by earliest end
- Difficulty: Medium
- Company signal: Greedy proof

### Java Solution

```java
int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
    int kept = 0;
    int end = Integer.MIN_VALUE;

    for (int[] interval : intervals) {
        if (interval[0] >= end) {
            kept++;
            end = interval[1];
        }
    }

    return intervals.length - kept;
}
```

### Complexity

- Time: `O(n log n)`
- Space: `O(1)` extra

### Common Mistake

Sorting by start can make you keep long intervals that block many future intervals.

### Interview Explanation

To maximize kept non-overlapping intervals, I always keep the interval that ends earliest. That leaves the most room for future intervals. Removals equal total intervals minus kept intervals.

---

## 253. Meeting Rooms II

- Pattern: Min heap of ending times
- Difficulty: Medium
- Company signal: Interval resource allocation

### Java Solution

```java
int minMeetingRooms(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    PriorityQueue<Integer> ends = new PriorityQueue<>();

    for (int[] meeting : intervals) {
        if (!ends.isEmpty() && ends.peek() <= meeting[0]) {
            ends.poll();
        }
        ends.offer(meeting[1]);
    }

    return ends.size();
}
```

### Complexity

- Time: `O(n log n)`
- Space: `O(n)`

### Common Mistake

Using `<` instead of `<=`; a meeting ending at time `t` frees a room for one starting at `t`.

### Interview Explanation

I sort meetings by start time. The heap stores end times of rooms currently in use. If the earliest ending room is free before this meeting starts, I reuse it; otherwise, I need a new room.

---

## 55. Jump Game

- Pattern: Greedy farthest reach
- Difficulty: Medium
- Company signal: Reachability greedy

### Java Solution

```java
boolean canJump(int[] nums) {
    int farthest = 0;

    for (int i = 0; i < nums.length; i++) {
        if (i > farthest) return false;
        farthest = Math.max(farthest, i + nums[i]);
    }

    return true;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Trying all jumps recursively causes exponential work.

### Interview Explanation

I track the farthest index reachable so far. If I ever reach an index beyond that, it is impossible. Otherwise, I update the reach using the current jump length.

---

## 45. Jump Game II

- Pattern: Greedy BFS range
- Difficulty: Medium
- Company signal: Minimum jumps without explicit BFS

### Java Solution

```java
int jump(int[] nums) {
    int jumps = 0;
    int currEnd = 0;
    int farthest = 0;

    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);

        if (i == currEnd) {
            jumps++;
            currEnd = farthest;
        }
    }

    return jumps;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Incrementing jumps at every index instead of only at the end of the current reachable layer.

### Interview Explanation

I treat the current reachable range as one BFS layer. While scanning that layer, I compute the farthest next reach. When I hit the end of the current layer, I take one jump and move to the next layer.

---

## 763. Partition Labels

- Pattern: Greedy last occurrence
- Difficulty: Medium
- Company signal: String interval greedy

### Java Solution

```java
List<Integer> partitionLabels(String s) {
    int[] last = new int[26];
    for (int i = 0; i < s.length(); i++) {
        last[s.charAt(i) - 'a'] = i;
    }

    List<Integer> ans = new ArrayList<>();
    int start = 0;
    int end = 0;

    for (int i = 0; i < s.length(); i++) {
        end = Math.max(end, last[s.charAt(i) - 'a']);
        if (i == end) {
            ans.add(end - start + 1);
            start = i + 1;
        }
    }

    return ans;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Ending a partition before all characters inside it have reached their final occurrence.

### Interview Explanation

Every character in a partition must finish inside that partition. I track the farthest last occurrence of all characters seen in the current partition. When the current index reaches that farthest point, the partition can close.

---

**Next:** `05_Trees_Backtracking_Graph_DP_Expansion.md`
