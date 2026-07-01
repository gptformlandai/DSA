# Hot 150 Solutions - Remaining Core Problems

> This file closes the remaining queued Hot 150 problems so the core tracker has solution coverage for every problem.

---

## 523. Continuous Subarray Sum

```java
boolean checkSubarraySum(int[] nums, int k) {
    Map<Integer, Integer> first = new HashMap<>();
    first.put(0, -1);
    int sum = 0;

    for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
        int mod = sum % k;

        if (first.containsKey(mod)) {
            if (i - first.get(mod) >= 2) return true;
        } else {
            first.put(mod, i);
        }
    }

    return false;
}
```

- Pattern: Prefix modulo
- Time: `O(n)`
- Space: `O(k)` or `O(n)`
- Trap: Keep the first index of each modulo to maximize subarray length.
- Interview line: "Two prefix sums with the same modulo form a subarray divisible by `k`."

---

## 974. Subarray Sums Divisible by K

```java
int subarraysDivByK(int[] nums, int k) {
    int[] count = new int[k];
    count[0] = 1;
    int sum = 0;
    int ans = 0;

    for (int num : nums) {
        sum = ((sum + num) % k + k) % k;
        ans += count[sum];
        count[sum]++;
    }

    return ans;
}
```

- Pattern: Prefix modulo count
- Time: `O(n)`
- Space: `O(k)`
- Trap: Normalize negative modulo values.
- Interview line: "Every previous prefix with the same remainder creates a divisible subarray."

---

## 152. Maximum Product Subarray

```java
int maxProduct(int[] nums) {
    int maxHere = nums[0];
    int minHere = nums[0];
    int best = nums[0];

    for (int i = 1; i < nums.length; i++) {
        int num = nums[i];
        if (num < 0) {
            int temp = maxHere;
            maxHere = minHere;
            minHere = temp;
        }

        maxHere = Math.max(num, maxHere * num);
        minHere = Math.min(num, minHere * num);
        best = Math.max(best, maxHere);
    }

    return best;
}
```

- Pattern: Running max/min
- Time: `O(n)`
- Space: `O(1)`
- Trap: Negative numbers can turn the smallest product into the largest.
- Interview line: "I track both extremes because multiplication by a negative flips them."

---

## 41. First Missing Positive

```java
int firstMissingPositive(int[] nums) {
    int n = nums.length;

    for (int i = 0; i < n; i++) {
        while (nums[i] >= 1 && nums[i] <= n
                && nums[nums[i] - 1] != nums[i]) {
            int target = nums[i] - 1;
            int temp = nums[i];
            nums[i] = nums[target];
            nums[target] = temp;
        }
    }

    for (int i = 0; i < n; i++) {
        if (nums[i] != i + 1) return i + 1;
    }

    return n + 1;
}
```

- Pattern: Index placement
- Time: `O(n)`
- Space: `O(1)`
- Trap: The `while` guard must avoid infinite swaps on duplicates.
- Interview line: "Value `x` belongs at index `x - 1`; after placement, the first mismatch is missing."

---

## 713. Subarray Product Less Than K

```java
int numSubarrayProductLessThanK(int[] nums, int k) {
    if (k <= 1) return 0;

    int left = 0;
    int product = 1;
    int ans = 0;

    for (int right = 0; right < nums.length; right++) {
        product *= nums[right];
        while (product >= k) product /= nums[left++];
        ans += right - left + 1;
    }

    return ans;
}
```

- Pattern: Variable sliding window
- Time: `O(n)`
- Space: `O(1)`
- Trap: If `k <= 1`, no positive-product subarray qualifies.
- Interview line: "Every valid window ending at `right` contributes all suffixes inside that window."

---

## 981. Time Based Key-Value Store

```java
class TimeMap {
    class Pair {
        int time;
        String value;

        Pair(int time, String value) {
            this.time = time;
            this.value = value;
        }
    }

    Map<String, List<Pair>> map = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(new Pair(timestamp, value));
    }

    public String get(String key, int timestamp) {
        List<Pair> list = map.getOrDefault(key, new ArrayList<>());
        int left = 0;
        int right = list.size() - 1;
        String ans = "";

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid).time <= timestamp) {
                ans = list.get(mid).value;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return ans;
    }
}
```

- Pattern: Binary search per key
- Time: `O(1)` set, `O(log n)` get
- Space: `O(n)`
- Trap: Timestamps for a key are naturally increasing in the problem.
- Interview line: "For each key, I store timestamped values and binary search the latest timestamp not exceeding the query."

---

## 4. Median of Two Sorted Arrays

```java
double findMedianSortedArrays(int[] a, int[] b) {
    if (a.length > b.length) return findMedianSortedArrays(b, a);

    int m = a.length;
    int n = b.length;
    int left = 0;
    int right = m;

    while (left <= right) {
        int cutA = left + (right - left) / 2;
        int cutB = (m + n + 1) / 2 - cutA;

        int l1 = cutA == 0 ? Integer.MIN_VALUE : a[cutA - 1];
        int r1 = cutA == m ? Integer.MAX_VALUE : a[cutA];
        int l2 = cutB == 0 ? Integer.MIN_VALUE : b[cutB - 1];
        int r2 = cutB == n ? Integer.MAX_VALUE : b[cutB];

        if (l1 <= r2 && l2 <= r1) {
            if ((m + n) % 2 == 1) return Math.max(l1, l2);
            return (Math.max(l1, l2) + Math.min(r1, r2)) / 2.0;
        } else if (l1 > r2) {
            right = cutA - 1;
        } else {
            left = cutA + 1;
        }
    }

    return 0.0;
}
```

- Pattern: Partition binary search
- Time: `O(log min(m, n))`
- Space: `O(1)`
- Trap: Always binary search the smaller array.
- Interview line: "I find a partition where every left-side value is <= every right-side value."

---

## 912. Sort an Array

```java
int[] sortArray(int[] nums) {
    int[] temp = new int[nums.length];
    mergeSort(nums, temp, 0, nums.length - 1);
    return nums;
}

void mergeSort(int[] nums, int[] temp, int left, int right) {
    if (left >= right) return;

    int mid = left + (right - left) / 2;
    mergeSort(nums, temp, left, mid);
    mergeSort(nums, temp, mid + 1, right);
    merge(nums, temp, left, mid, right);
}

void merge(int[] nums, int[] temp, int left, int mid, int right) {
    int i = left;
    int j = mid + 1;
    int k = left;

    while (i <= mid && j <= right) {
        temp[k++] = nums[i] <= nums[j] ? nums[i++] : nums[j++];
    }
    while (i <= mid) temp[k++] = nums[i++];
    while (j <= right) temp[k++] = nums[j++];

    for (int p = left; p <= right; p++) nums[p] = temp[p];
}
```

- Pattern: Merge sort
- Time: `O(n log n)`
- Space: `O(n)`
- Trap: QuickSort can degrade without randomized pivots; merge sort is stable and predictable.
- Interview line: "Divide, sort both halves, then merge sorted halves."

---

## 853. Car Fleet

```java
int carFleet(int target, int[] position, int[] speed) {
    int n = position.length;
    int[][] cars = new int[n][2];
    for (int i = 0; i < n; i++) cars[i] = new int[] {position[i], speed[i]};
    Arrays.sort(cars, Comparator.comparingInt(a -> a[0]));

    int fleets = 0;
    double slowestTime = 0;

    for (int i = n - 1; i >= 0; i--) {
        double time = (double) (target - cars[i][0]) / cars[i][1];
        if (time > slowestTime) {
            fleets++;
            slowestTime = time;
        }
    }

    return fleets;
}
```

- Pattern: Monotonic arrival times
- Time: `O(n log n)`
- Space: `O(n)`
- Trap: Process cars from closest to target backward.
- Interview line: "A car joins the fleet ahead if it arrives no later than that fleet."

---

## 85. Maximal Rectangle

```java
int maximalRectangle(char[][] matrix) {
    int cols = matrix[0].length;
    int[] heights = new int[cols];
    int best = 0;

    for (char[] row : matrix) {
        for (int c = 0; c < cols; c++) {
            heights[c] = row[c] == '1' ? heights[c] + 1 : 0;
        }
        best = Math.max(best, largestRectangleArea(heights));
    }

    return best;
}

int largestRectangleArea(int[] heights) {
    Deque<Integer> stack = new ArrayDeque<>();
    int best = 0;

    for (int i = 0; i <= heights.length; i++) {
        int curr = i == heights.length ? 0 : heights[i];
        while (!stack.isEmpty() && curr < heights[stack.peek()]) {
            int h = heights[stack.pop()];
            int leftLess = stack.isEmpty() ? -1 : stack.peek();
            best = Math.max(best, h * (i - leftLess - 1));
        }
        stack.push(i);
    }

    return best;
}
```

- Pattern: Histogram per row
- Time: `O(rows * cols)`
- Space: `O(cols)`
- Trap: Each row becomes the base of a histogram.
- Interview line: "I convert each row into histogram heights and reuse Largest Rectangle in Histogram."

---

## 32. Longest Valid Parentheses

```java
int longestValidParentheses(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-1);
    int best = 0;

    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            stack.push(i);
        } else {
            stack.pop();
            if (stack.isEmpty()) {
                stack.push(i);
            } else {
                best = Math.max(best, i - stack.peek());
            }
        }
    }

    return best;
}
```

- Pattern: Stack boundary
- Time: `O(n)`
- Space: `O(n)`
- Trap: Seed stack with `-1` as the base boundary.
- Interview line: "The stack stores indices before valid ranges; current index minus top gives valid length."

---

## 735. Asteroid Collision

```java
int[] asteroidCollision(int[] asteroids) {
    Deque<Integer> stack = new ArrayDeque<>();

    for (int asteroid : asteroids) {
        boolean alive = true;

        while (alive && asteroid < 0 && !stack.isEmpty() && stack.peek() > 0) {
            if (stack.peek() < -asteroid) stack.pop();
            else if (stack.peek() == -asteroid) {
                stack.pop();
                alive = false;
            } else {
                alive = false;
            }
        }

        if (alive) stack.push(asteroid);
    }

    int[] ans = new int[stack.size()];
    for (int i = ans.length - 1; i >= 0; i--) ans[i] = stack.pop();
    return ans;
}
```

- Pattern: Stack simulation
- Time: `O(n)`
- Space: `O(n)`
- Trap: Collisions only happen when left asteroid moves right and current moves left.
- Interview line: "The stack holds asteroids that survived so far."

---

## 138. Copy List With Random Pointer

```java
class RandomNode {
    int val;
    RandomNode next;
    RandomNode random;

    RandomNode(int val) {
        this.val = val;
    }
}

RandomNode copyRandomList(RandomNode head) {
    Map<RandomNode, RandomNode> map = new HashMap<>();
    RandomNode curr = head;

    while (curr != null) {
        map.put(curr, new RandomNode(curr.val));
        curr = curr.next;
    }

    curr = head;
    while (curr != null) {
        map.get(curr).next = map.get(curr.next);
        map.get(curr).random = map.get(curr.random);
        curr = curr.next;
    }

    return map.get(head);
}
```

- Pattern: HashMap clone
- Time: `O(n)`
- Space: `O(n)`
- Trap: Create all nodes before assigning random pointers.
- Interview line: "The map connects each original node to its clone."

---

## 2. Add Two Numbers

```java
ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    int carry = 0;

    while (l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }

        tail.next = new ListNode(sum % 10);
        tail = tail.next;
        carry = sum / 10;
    }

    return dummy.next;
}
```

- Pattern: Carry simulation
- Time: `O(max(m, n))`
- Space: `O(max(m, n))` for output
- Trap: Handle final carry after both lists end.
- Interview line: "This is digit-by-digit addition with carry."

---

## 287. Find the Duplicate Number

```java
int findDuplicate(int[] nums) {
    int slow = nums[0];
    int fast = nums[0];

    do {
        slow = nums[slow];
        fast = nums[nums[fast]];
    } while (slow != fast);

    slow = nums[0];
    while (slow != fast) {
        slow = nums[slow];
        fast = nums[fast];
    }

    return slow;
}
```

- Pattern: Floyd cycle detection
- Time: `O(n)`
- Space: `O(1)`
- Trap: Treat array values as next pointers.
- Interview line: "The duplicate creates a cycle in the index-to-value graph."

---

## 1046. Last Stone Weight

```java
int lastStoneWeight(int[] stones) {
    PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
    for (int stone : stones) heap.offer(stone);

    while (heap.size() > 1) {
        int a = heap.poll();
        int b = heap.poll();
        if (a != b) heap.offer(a - b);
    }

    return heap.isEmpty() ? 0 : heap.peek();
}
```

- Pattern: Max heap
- Time: `O(n log n)`
- Space: `O(n)`
- Trap: Reinsert only the positive difference.
- Interview line: "Always smash the two heaviest stones."

---

## 355. Design Twitter

```java
class Twitter {
    int time = 0;
    Map<Integer, Set<Integer>> followees = new HashMap<>();
    Map<Integer, List<int[]>> tweets = new HashMap<>();

    public void postTweet(int userId, int tweetId) {
        tweets.computeIfAbsent(userId, k -> new ArrayList<>()).add(new int[] {time++, tweetId});
    }

    public List<Integer> getNewsFeed(int userId) {
        follow(userId, userId);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);

        for (int user : followees.get(userId)) {
            List<int[]> list = tweets.getOrDefault(user, new ArrayList<>());
            for (int i = Math.max(0, list.size() - 10); i < list.size(); i++) {
                pq.offer(list.get(i));
            }
        }

        List<Integer> feed = new ArrayList<>();
        while (!pq.isEmpty() && feed.size() < 10) feed.add(pq.poll()[1]);
        return feed;
    }

    public void follow(int followerId, int followeeId) {
        followees.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }

    public void unfollow(int followerId, int followeeId) {
        if (followerId == followeeId) return;
        followees.getOrDefault(followerId, new HashSet<>()).remove(followeeId);
    }
}
```

- Pattern: Design + heap
- Time: `O(f * 10 log(f * 10))` feed with this compact implementation
- Space: `O(users + tweets + follows)`
- Trap: A user should always see their own tweets.
- Interview line: "Tweets are timestamped, and the feed is the 10 newest tweets among followed users."

---

## 1834. Single-Threaded CPU

```java
int[] getOrder(int[][] tasks) {
    int n = tasks.length;
    int[][] jobs = new int[n][3];
    for (int i = 0; i < n; i++) jobs[i] = new int[] {tasks[i][0], tasks[i][1], i};
    Arrays.sort(jobs, Comparator.comparingInt(a -> a[0]));

    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
            a[1] == b[1] ? a[2] - b[2] : a[1] - b[1]);

    int[] ans = new int[n];
    long time = 0;
    int i = 0;
    int idx = 0;

    while (idx < n) {
        if (pq.isEmpty() && time < jobs[i][0]) time = jobs[i][0];
        while (i < n && jobs[i][0] <= time) pq.offer(jobs[i++]);

        int[] job = pq.poll();
        time += job[1];
        ans[idx++] = job[2];
    }

    return ans;
}
```

- Pattern: Sort + min heap
- Time: `O(n log n)`
- Space: `O(n)`
- Trap: If CPU is idle, jump time to next task enqueue time.
- Interview line: "Among available jobs, choose shortest processing time, breaking ties by index."

---

## 767. Reorganize String

```java
String reorganizeString(String s) {
    int[] freq = new int[26];
    for (char ch : s.toCharArray()) freq[ch - 'a']++;

    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
    for (int i = 0; i < 26; i++) {
        if (freq[i] > 0) pq.offer(new int[] {i, freq[i]});
    }

    StringBuilder sb = new StringBuilder();
    int[] prev = null;

    while (!pq.isEmpty()) {
        int[] cur = pq.poll();
        sb.append((char) ('a' + cur[0]));
        cur[1]--;

        if (prev != null && prev[1] > 0) pq.offer(prev);
        prev = cur;
    }

    return sb.length() == s.length() ? sb.toString() : "";
}
```

- Pattern: Greedy max heap
- Time: `O(n log 26)`
- Space: `O(1)`
- Trap: Hold the previous character out for one round to avoid adjacency.
- Interview line: "Always place the most frequent currently allowed character."

---

## 502. IPO

```java
int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
    int n = profits.length;
    int[][] projects = new int[n][2];
    for (int i = 0; i < n; i++) projects[i] = new int[] {capital[i], profits[i]};
    Arrays.sort(projects, Comparator.comparingInt(a -> a[0]));

    PriorityQueue<Integer> available = new PriorityQueue<>(Collections.reverseOrder());
    int i = 0;

    while (k-- > 0) {
        while (i < n && projects[i][0] <= w) available.offer(projects[i++][1]);
        if (available.isEmpty()) break;
        w += available.poll();
    }

    return w;
}
```

- Pattern: Two heaps / sorted capital + max profit
- Time: `O(n log n + k log n)`
- Space: `O(n)`
- Trap: Only choose among projects whose capital requirement is affordable now.
- Interview line: "At each round, unlock all affordable projects and take the most profitable one."

---

## 37. Sudoku Solver

```java
void solveSudoku(char[][] board) {
    solve(board);
}

boolean solve(char[][] board) {
    for (int r = 0; r < 9; r++) {
        for (int c = 0; c < 9; c++) {
            if (board[r][c] != '.') continue;

            for (char ch = '1'; ch <= '9'; ch++) {
                if (valid(board, r, c, ch)) {
                    board[r][c] = ch;
                    if (solve(board)) return true;
                    board[r][c] = '.';
                }
            }
            return false;
        }
    }
    return true;
}

boolean valid(char[][] board, int row, int col, char ch) {
    for (int i = 0; i < 9; i++) {
        if (board[row][i] == ch) return false;
        if (board[i][col] == ch) return false;
        int r = 3 * (row / 3) + i / 3;
        int c = 3 * (col / 3) + i % 3;
        if (board[r][c] == ch) return false;
    }
    return true;
}
```

- Pattern: Constraint backtracking
- Time: Exponential, heavily pruned
- Space: `O(81)`
- Trap: Return false immediately if no digit works for a blank cell.
- Interview line: "Pick an empty cell, try valid digits, recurse, and undo on failure."

---

## 286. Walls and Gates

```java
void wallsAndGates(int[][] rooms) {
    Queue<int[]> queue = new ArrayDeque<>();
    int rows = rooms.length;
    int cols = rooms[0].length;

    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (rooms[r][c] == 0) queue.offer(new int[] {r, c});
        }
    }

    int[][] dirs = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    while (!queue.isEmpty()) {
        int[] cur = queue.poll();
        for (int[] d : dirs) {
            int nr = cur[0] + d[0];
            int nc = cur[1] + d[1];
            if (nr < 0 || nc < 0 || nr == rows || nc == cols) continue;
            if (rooms[nr][nc] != Integer.MAX_VALUE) continue;
            rooms[nr][nc] = rooms[cur[0]][cur[1]] + 1;
            queue.offer(new int[] {nr, nc});
        }
    }
}
```

- Pattern: Multi-source BFS
- Time: `O(rows * cols)`
- Space: `O(rows * cols)`
- Trap: Start BFS from all gates, not from every room.
- Interview line: "The first time BFS reaches a room is its shortest distance to a gate."

---

## 261. Graph Valid Tree

```java
boolean validTree(int n, int[][] edges) {
    if (edges.length != n - 1) return false;

    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;

    for (int[] e : edges) {
        int a = find(parent, e[0]);
        int b = find(parent, e[1]);
        if (a == b) return false;
        parent[a] = b;
    }

    return true;
}
```

- Pattern: DSU tree validation
- Time: `O(E alpha(V))`
- Space: `O(V)`
- Trap: A tree must have exactly `n - 1` edges and no cycle.
- Interview line: "The edge count plus successful unions proves connected acyclic structure."

---

## 323. Number of Connected Components

```java
int countComponents(int n, int[][] edges) {
    int[] parent = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;

    int count = n;
    for (int[] edge : edges) {
        int a = find(parent, edge[0]);
        int b = find(parent, edge[1]);
        if (a != b) {
            parent[a] = b;
            count--;
        }
    }

    return count;
}

int find(int[] parent, int x) {
    if (parent[x] != x) parent[x] = find(parent, parent[x]);
    return parent[x];
}
```

- Pattern: DSU component counting
- Time: `O(E alpha(V))`
- Space: `O(V)`
- Trap: Decrement count only when union merges two different roots.
- Interview line: "Each successful union reduces the number of connected components by one."

---

## 127. Word Ladder

```java
int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> words = new HashSet<>(wordList);
    if (!words.contains(endWord)) return 0;

    Queue<String> queue = new ArrayDeque<>();
    queue.offer(beginWord);
    Set<String> seen = new HashSet<>();
    seen.add(beginWord);
    int steps = 1;

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            String word = queue.poll();
            if (word.equals(endWord)) return steps;

            char[] arr = word.toCharArray();
            for (int p = 0; p < arr.length; p++) {
                char old = arr[p];
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    arr[p] = ch;
                    String next = new String(arr);
                    if (words.contains(next) && seen.add(next)) queue.offer(next);
                }
                arr[p] = old;
            }
        }
        steps++;
    }

    return 0;
}
```

- Pattern: BFS shortest path
- Time: `O(words * length * 26)`
- Space: `O(words)`
- Trap: BFS is needed because this asks for the shortest transformation length.
- Interview line: "Each word is a graph node; one-letter transformations are edges."

---

## 787. Cheapest Flights Within K Stops

```java
int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    int[] cost = new int[n];
    Arrays.fill(cost, Integer.MAX_VALUE / 2);
    cost[src] = 0;

    for (int step = 0; step <= k; step++) {
        int[] next = cost.clone();
        for (int[] f : flights) {
            if (cost[f[0]] + f[2] < next[f[1]]) {
                next[f[1]] = cost[f[0]] + f[2];
            }
        }
        cost = next;
    }

    return cost[dst] >= Integer.MAX_VALUE / 2 ? -1 : cost[dst];
}
```

- Pattern: Bellman-Ford with edge limit
- Time: `O(k * E)`
- Space: `O(V)`
- Trap: Clone the previous costs each round so one round uses at most one extra edge.
- Interview line: "With at most `k` stops, I can use at most `k + 1` edges."

---

## 332. Reconstruct Itinerary

```java
List<String> findItinerary(List<List<String>> tickets) {
    Map<String, PriorityQueue<String>> graph = new HashMap<>();
    for (List<String> t : tickets) {
        graph.computeIfAbsent(t.get(0), x -> new PriorityQueue<>()).offer(t.get(1));
    }

    LinkedList<String> route = new LinkedList<>();
    visit("JFK", graph, route);
    return route;
}

void visit(String airport, Map<String, PriorityQueue<String>> graph,
           LinkedList<String> route) {
    PriorityQueue<String> nexts = graph.get(airport);
    while (nexts != null && !nexts.isEmpty()) {
        visit(nexts.poll(), graph, route);
    }
    route.addFirst(airport);
}
```

- Pattern: Eulerian path DFS
- Time: `O(E log E)`
- Space: `O(E)`
- Trap: Add airport after exploring outgoing edges.
- Interview line: "This is Hierholzer's algorithm with lexical priority queues."

---

## 1192. Critical Connections in a Network

```java
int time;

List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    for (List<Integer> e : connections) {
        graph.get(e.get(0)).add(e.get(1));
        graph.get(e.get(1)).add(e.get(0));
    }

    int[] disc = new int[n];
    int[] low = new int[n];
    List<List<Integer>> ans = new ArrayList<>();
    time = 1;
    tarjan(0, -1, graph, disc, low, ans);
    return ans;
}

void tarjan(int node, int parent, List<List<Integer>> graph, int[] disc,
            int[] low, List<List<Integer>> ans) {
    disc[node] = low[node] = time++;

    for (int nei : graph.get(node)) {
        if (nei == parent) continue;
        if (disc[nei] == 0) {
            tarjan(nei, node, graph, disc, low, ans);
            low[node] = Math.min(low[node], low[nei]);
            if (low[nei] > disc[node]) ans.add(Arrays.asList(node, nei));
        } else {
            low[node] = Math.min(low[node], disc[nei]);
        }
    }
}
```

- Pattern: Tarjan bridge-finding
- Time: `O(V + E)`
- Space: `O(V + E)`
- Trap: An edge is a bridge if child low-link cannot reach the current node or above.
- Interview line: "`low` tells the earliest discovery time reachable from a subtree."

---

## 252. Meeting Rooms

```java
boolean canAttendMeetings(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] < intervals[i - 1][1]) return false;
    }

    return true;
}
```

- Pattern: Interval overlap
- Time: `O(n log n)`
- Space: `O(1)`
- Trap: End equal to next start is not overlap.
- Interview line: "After sorting by start, only adjacent intervals can conflict."

---

## 452. Minimum Number of Arrows

```java
int findMinArrowShots(int[][] points) {
    Arrays.sort(points, Comparator.comparingInt(a -> a[1]));
    int arrows = 0;
    long end = Long.MIN_VALUE;

    for (int[] p : points) {
        if (p[0] > end) {
            arrows++;
            end = p[1];
        }
    }

    return arrows;
}
```

- Pattern: Greedy by end
- Time: `O(n log n)`
- Space: `O(1)`
- Trap: Use `>` because touching intervals can share an arrow.
- Interview line: "Shoot at the earliest possible end to cover the most future balloons."

---

## 134. Gas Station

```java
int canCompleteCircuit(int[] gas, int[] cost) {
    int total = 0;
    int tank = 0;
    int start = 0;

    for (int i = 0; i < gas.length; i++) {
        int diff = gas[i] - cost[i];
        total += diff;
        tank += diff;

        if (tank < 0) {
            start = i + 1;
            tank = 0;
        }
    }

    return total >= 0 ? start : -1;
}
```

- Pattern: Greedy reset
- Time: `O(n)`
- Space: `O(1)`
- Trap: If total gas is less than total cost, no start works.
- Interview line: "If tank becomes negative, no station in the failed segment can be the start."

---

## 5. Longest Palindromic Substring

```java
String longestPalindrome(String s) {
    int start = 0;
    int end = 0;

    for (int i = 0; i < s.length(); i++) {
        int len1 = palLen(s, i, i);
        int len2 = palLen(s, i, i + 1);
        int len = Math.max(len1, len2);

        if (len > end - start + 1) {
            start = i - (len - 1) / 2;
            end = i + len / 2;
        }
    }

    return s.substring(start, end + 1);
}

int palLen(String s, int left, int right) {
    while (left >= 0 && right < s.length()
            && s.charAt(left) == s.charAt(right)) {
        left--;
        right++;
    }
    return right - left - 1;
}
```

- Pattern: Expand around centers
- Time: `O(n^2)`
- Space: `O(1)`
- Trap: Check both odd and even centers.
- Interview line: "Every palindrome expands from a center."

---

## 10. Regular Expression Matching

```java
boolean isMatch(String s, String p) {
    int m = s.length();
    int n = p.length();
    boolean[][] dp = new boolean[m + 1][n + 1];
    dp[0][0] = true;

    for (int j = 2; j <= n; j++) {
        if (p.charAt(j - 1) == '*') dp[0][j] = dp[0][j - 2];
    }

    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            char pc = p.charAt(j - 1);
            if (pc == '.' || pc == s.charAt(i - 1)) {
                dp[i][j] = dp[i - 1][j - 1];
            } else if (pc == '*') {
                dp[i][j] = dp[i][j - 2];
                char prev = p.charAt(j - 2);
                if (prev == '.' || prev == s.charAt(i - 1)) {
                    dp[i][j] |= dp[i - 1][j];
                }
            }
        }
    }

    return dp[m][n];
}
```

- Pattern: String DP
- Time: `O(m * n)`
- Space: `O(m * n)`
- Trap: `*` modifies the previous pattern character, not itself.
- Interview line: "`*` means zero occurrences or one more occurrence of the previous token."

---

## 208. Implement Trie

```java
class Trie {
    class TrieNode {
        TrieNode[] child = new TrieNode[26];
        boolean word;
    }

    TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int i = ch - 'a';
            if (node.child[i] == null) node.child[i] = new TrieNode();
            node = node.child[i];
        }
        node.word = true;
    }

    public boolean search(String word) {
        TrieNode node = find(word);
        return node != null && node.word;
    }

    public boolean startsWith(String prefix) {
        return find(prefix) != null;
    }

    private TrieNode find(String s) {
        TrieNode node = root;
        for (char ch : s.toCharArray()) {
            int i = ch - 'a';
            if (node.child[i] == null) return null;
            node = node.child[i];
        }
        return node;
    }
}
```

- Pattern: Trie basics
- Time: `O(length)` per operation
- Space: `O(total characters)`
- Trap: Prefix existence and full-word existence are different.
- Interview line: "Trie nodes represent prefixes, and terminal flags represent complete words."

---

## 211. Design Add and Search Words

```java
class WordDictionary {
    class TrieNode {
        TrieNode[] child = new TrieNode[26];
        boolean word;
    }

    TrieNode root = new TrieNode();

    public void addWord(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            int i = ch - 'a';
            if (node.child[i] == null) node.child[i] = new TrieNode();
            node = node.child[i];
        }
        node.word = true;
    }

    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    boolean dfs(String word, int index, TrieNode node) {
        if (node == null) return false;
        if (index == word.length()) return node.word;

        char ch = word.charAt(index);
        if (ch != '.') return dfs(word, index + 1, node.child[ch - 'a']);

        for (TrieNode next : node.child) {
            if (dfs(word, index + 1, next)) return true;
        }
        return false;
    }
}
```

- Pattern: Trie + wildcard DFS
- Time: `O(26^dots * length)` worst case
- Space: `O(total characters)`
- Trap: `.` can branch to any child.
- Interview line: "Normal letters follow one edge; wildcard searches all possible child edges."

---

## 212. Word Search II

```java
class TrieWordNode {
    TrieWordNode[] child = new TrieWordNode[26];
    String word;
}

List<String> findWords(char[][] board, String[] words) {
    TrieWordNode root = new TrieWordNode();
    for (String word : words) insert(root, word);

    List<String> ans = new ArrayList<>();
    for (int r = 0; r < board.length; r++) {
        for (int c = 0; c < board[0].length; c++) {
            search(board, r, c, root, ans);
        }
    }
    return ans;
}

void insert(TrieWordNode root, String word) {
    TrieWordNode node = root;
    for (char ch : word.toCharArray()) {
        int i = ch - 'a';
        if (node.child[i] == null) node.child[i] = new TrieWordNode();
        node = node.child[i];
    }
    node.word = word;
}

void search(char[][] board, int r, int c, TrieWordNode node, List<String> ans) {
    if (r < 0 || c < 0 || r == board.length || c == board[0].length) return;
    char ch = board[r][c];
    if (ch == '#' || node.child[ch - 'a'] == null) return;

    node = node.child[ch - 'a'];
    if (node.word != null) {
        ans.add(node.word);
        node.word = null;
    }

    board[r][c] = '#';
    search(board, r + 1, c, node, ans);
    search(board, r - 1, c, node, ans);
    search(board, r, c + 1, node, ans);
    search(board, r, c - 1, node, ans);
    board[r][c] = ch;
}
```

- Pattern: Trie + backtracking
- Time: `O(rows * cols * 4^L)` worst case, pruned by trie
- Space: `O(total word characters)`
- Trap: Set `word = null` after finding to avoid duplicate output.
- Interview line: "The trie prunes grid paths that cannot lead to any word."

---

## 242. Valid Anagram

```java
boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;
    int[] count = new int[26];

    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }

    for (int value : count) {
        if (value != 0) return false;
    }
    return true;
}
```

- Pattern: Frequency count
- Time: `O(n)`
- Space: `O(1)`
- Trap: Lengths must match.
- Interview line: "Anagrams have identical character frequency counts."

---

## 271. Encode and Decode Strings

```java
String encode(List<String> strs) {
    StringBuilder sb = new StringBuilder();
    for (String s : strs) {
        sb.append(s.length()).append('#').append(s);
    }
    return sb.toString();
}

List<String> decode(String s) {
    List<String> ans = new ArrayList<>();
    int i = 0;

    while (i < s.length()) {
        int j = i;
        while (s.charAt(j) != '#') j++;
        int len = Integer.parseInt(s.substring(i, j));
        ans.add(s.substring(j + 1, j + 1 + len));
        i = j + 1 + len;
    }

    return ans;
}
```

- Pattern: Length-prefix encoding
- Time: `O(total characters)`
- Space: `O(total characters)`
- Trap: Delimiter-only encoding fails if strings contain the delimiter.
- Interview line: "Length prefix makes decoding unambiguous for any character content."

---

## 191. Number of 1 Bits

```java
int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        n &= n - 1;
        count++;
    }
    return count;
}
```

- Pattern: Clear lowest set bit
- Time: `O(number of set bits)`
- Space: `O(1)`
- Trap: In Java, this still works with signed ints because bits are manipulated directly.
- Interview line: "`n & (n - 1)` removes the lowest set bit."

---

## 338. Counting Bits

```java
int[] countBits(int n) {
    int[] dp = new int[n + 1];

    for (int i = 1; i <= n; i++) {
        dp[i] = dp[i >> 1] + (i & 1);
    }

    return dp;
}
```

- Pattern: Bit DP
- Time: `O(n)`
- Space: `O(n)`
- Trap: Each number reuses the count of half itself.
- Interview line: "Right shifting removes the last bit, and `(i & 1)` tells whether that bit was one."

---

## 371. Sum of Two Integers

```java
int getSum(int a, int b) {
    while (b != 0) {
        int carry = (a & b) << 1;
        a = a ^ b;
        b = carry;
    }
    return a;
}
```

- Pattern: Bitwise addition
- Time: `O(1)` for fixed-width integers
- Space: `O(1)`
- Trap: XOR is sum without carry; AND shifted is carry.
- Interview line: "I repeat partial sum and carry until no carry remains."

---

## 268. Missing Number

```java
int missingNumber(int[] nums) {
    int xor = nums.length;

    for (int i = 0; i < nums.length; i++) {
        xor ^= i;
        xor ^= nums[i];
    }

    return xor;
}
```

- Pattern: XOR cancellation
- Time: `O(n)`
- Space: `O(1)`
- Trap: Include `n` in the XOR.
- Interview line: "All matching indices and values cancel, leaving the missing number."

---

## 202. Happy Number

```java
boolean isHappy(int n) {
    int slow = n;
    int fast = next(n);

    while (fast != 1 && slow != fast) {
        slow = next(slow);
        fast = next(next(fast));
    }

    return fast == 1;
}

int next(int n) {
    int sum = 0;
    while (n > 0) {
        int digit = n % 10;
        sum += digit * digit;
        n /= 10;
    }
    return sum;
}
```

- Pattern: Cycle detection
- Time: `O(log n)` per transformation, bounded sequence in practice
- Space: `O(1)`
- Trap: Non-happy numbers eventually cycle.
- Interview line: "This is Floyd cycle detection on repeated digit-square sums."

---

**Back:** `01_Hot_150_Index.md`
