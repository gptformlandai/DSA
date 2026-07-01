# Section 10 — Queue & Deque Patterns

---

## 1. What Problem Does This Solve?

Queue and Deque patterns solve two major categories:
1. **BFS (Breadth-First Search):** Shortest path in unweighted graphs, level-order traversal, spreading problems (flood fill, word ladder).
2. **Sliding Window Maximum/Minimum:** Finding the max or min in every window of size k in O(n), using a monotonic deque.

Without BFS, shortest-path in unweighted graphs takes O(n²) or uses DFS which finds *a* path, not the *shortest*. Without a deque, sliding window max takes O(n×k).

---

## 2. Beginner-Friendly Intuition

**BFS:** Imagine dropping a pebble in a pond. Ripples expand outward one ring at a time — first all cells 1 step away, then all cells 2 steps away. Each "ring" is a BFS level. You're guaranteed the first time you reach a cell, you've taken the shortest path.

**Monotonic Deque:** Imagine a sliding train window. As the window moves, you want to know the tallest person visible. Keep a list of "potentially tallest" people (from left to right). When a taller person enters from the right, everyone shorter inside is useless — remove them. When the window moves past someone, remove from the left if they're still in the window.

---

## 3. Real-World Analogy

**BFS — Customer service tickets:** Process all priority-1 tickets before priority-2. All tickets at the same priority level are in a queue — FIFO.

**Sliding Window Max — Weather station:** Track the hottest temperature in the last 7 days. Each new day, add it to the deque, remove days more than 7 days ago, remove days with lower temp (they can never be the max while the new day is in the window).

---

## 4. Core Concept

### BFS Mechanics

```
Level 0: starting node(s)
Level 1: all neighbors of level 0
Level 2: all unvisited neighbors of level 1
...
```

Each level is processed completely before moving to the next. A `visited` set prevents revisiting. A Queue implements FIFO order.

### Monotonic Deque for Sliding Max

Maintain a **decreasing** deque of indices:
- Before adding `i`: remove all back elements with value ≤ arr[i] (they can never be max while arr[i] is in window)
- Before reading max: remove front elements that are outside the current window
- Front of deque = index of current window's max

---

## 5. Pattern Recognition Signals

Use BFS when:
```
"Shortest path in unweighted graph/grid"
"Level order traversal"
"All nodes at distance k"
"Word ladder / transformation"
"Rotting Oranges / spreading infection"
"01-BFS (0-cost and 1-cost edges)"
"Multi-source BFS (multiple starting points)"
```

Use Monotonic Deque when:
```
"Maximum/minimum in every window of size k"
"Sliding window maximum"
"Jump Game with deque optimization"
```

---

## 6. Step-by-Step Algorithm

### BFS Template (Graph)
```
Step 1: Add start node to queue, mark as visited
Step 2: While queue is not empty:
    a. node = queue.poll()  (remove from front)
    b. If node is target → return distance/level
    c. For each neighbor of node:
        If not visited:
            Mark visited
            queue.add(neighbor)
Step 3: If target never reached → return -1
```

### BFS Level-by-Level (when level count matters)
```
Step 1: queue.add(start). visited.add(start). level = 0
Step 2: While queue not empty:
    size = queue.size()  ← number of nodes at current level
    For i from 0 to size-1:
        node = queue.poll()
        Process node
        Add unvisited neighbors to queue
    level++
```

### Monotonic Deque for Sliding Max
```
Step 1: deque = new ArrayDeque<>() (stores indices)
Step 2: result = int[n - k + 1]
Step 3: For i from 0 to n-1:
    a. Remove back: while deque not empty AND arr[deque.peekLast()] <= arr[i]: deque.pollLast()
    b. Add current: deque.addLast(i)
    c. Remove front if out of window: if deque.peekFirst() == i - k: deque.pollFirst()
    d. Record max: if i >= k-1: result[i-k+1] = arr[deque.peekFirst()]
```

---

## 7. Dry Run with Example

### Example 1: Shortest Path in Grid

**Grid:**
```
0 0 0
0 1 0
0 0 0
```
0 = open, 1 = blocked. Start=(0,0), End=(2,2).

```
BFS from (0,0):
Level 0: [(0,0)] visited={(0,0)}
Level 1: [(0,1),(1,0)] → from (0,0) neighbors
Level 2: [(0,2),(1,1 blocked → skip),(2,0),(1,1 blocked)] → (0,2),(2,0) added
Actually:
  From (0,1): neighbors (0,0) visited, (0,2), (1,1) blocked
  From (1,0): neighbors (0,0) visited, (2,0), (1,1) blocked
  Level 2: [(0,2),(2,0)]
Level 3:
  From (0,2): neighbors (0,1) visited, (1,2)
  From (2,0): neighbors (1,0) visited, (2,1)
  Level 3: [(1,2),(2,1)]
Level 4:
  From (1,2): neighbors (0,2) visited, (2,2) ← TARGET!
  
Shortest path = 4 moves ✓
```

### Example 2: Sliding Window Maximum

**Input:** `nums = [1, 3, -1, -3, 5, 3, 6, 7]`, `k = 3`

```
i=0(1):  deque=[]. add 0. deque=[0(1)]
i=1(3):  3>1 → remove 0. add 1. deque=[1(3)]
i=2(-1): -1<3 → add 2. deque=[1(3), 2(-1)]. i>=k-1: result[0]=arr[1]=3
i=3(-3): -3<-1 → add 3. deque=[1(3),2(-1),3(-3)]. i=3, front=1, 1=3-3+1? No.
         result[1]=arr[1]=3
i=4(5):  5>-3 → remove 3. 5>-1 → remove 2. 5>3 → remove 1. add 4. deque=[4(5)].
         front=4, i-k=4-3=1≠4. result[2]=arr[4]=5
i=5(3):  3<5 → add 5. deque=[4(5),5(3)]. front=4, i-k=5-3=2≠4. result[3]=arr[4]=5
i=6(6):  6>3 → remove 5. 6>5 → remove 4. add 6. deque=[6(6)]. result[4]=arr[6]=6
i=7(7):  7>6 → remove 6. add 7. deque=[7(7)]. front=7, i-k=7-3=4≠7. result[5]=arr[7]=7

result = [3, 3, 5, 5, 6, 7] ✓
```

---

## 8. Code Implementation

### BFS Shortest Path in Grid

```java
int shortestPath(int[][] grid, int[] start, int[] end) {
    int m = grid.length, n = grid[0].length;
    boolean[][] visited = new boolean[m][n];
    Queue<int[]> queue = new LinkedList<>();
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

    queue.offer(start);
    visited[start[0]][start[1]] = true;
    int steps = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            if (curr[0] == end[0] && curr[1] == end[1]) return steps;
            for (int[] d : dirs) {
                int nr = curr[0] + d[0], nc = curr[1] + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n
                        && !visited[nr][nc] && grid[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        steps++;
    }
    return -1;
}
```

### Rotting Oranges (Multi-Source BFS)

```java
int orangesRotting(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;
    for (int r = 0; r < m; r++)
        for (int c = 0; c < n; c++) {
            if (grid[r][c] == 2) queue.offer(new int[]{r, c});
            if (grid[r][c] == 1) fresh++;
        }
    if (fresh == 0) return 0;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    int minutes = 0;
    while (!queue.isEmpty() && fresh > 0) {
        minutes++;
        for (int size = queue.size(); size > 0; size--) {
            int[] curr = queue.poll();
            for (int[] d : dirs) {
                int nr = curr[0]+d[0], nc = curr[1]+d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    fresh--;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
    }
    return fresh == 0 ? minutes : -1;
}
```

### Sliding Window Maximum (Monotonic Deque)

```java
int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new ArrayDeque<>(); // stores indices

    for (int i = 0; i < n; i++) {
        // Remove elements outside window from front
        if (!deque.isEmpty() && deque.peekFirst() == i - k)
            deque.pollFirst();
        // Remove smaller elements from back (they can never be max)
        while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i])
            deque.pollLast();
        deque.addLast(i);
        // Record max when window is full
        if (i >= k - 1)
            result[i - k + 1] = nums[deque.peekFirst()];
    }
    return result;
}
```

### Word Ladder (BFS on Word Graph)

```java
int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    Set<String> visited = new HashSet<>();
    visited.add(beginWord);
    int steps = 1;
    while (!queue.isEmpty()) {
        for (int size = queue.size(); size > 0; size--) {
            String word = queue.poll();
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char orig = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    chars[i] = c;
                    String next = new String(chars);
                    if (next.equals(endWord)) return steps + 1;
                    if (wordSet.contains(next) && !visited.contains(next)) {
                        visited.add(next);
                        queue.offer(next);
                    }
                }
                chars[i] = orig;
            }
        }
        steps++;
    }
    return 0;
}
```

---

## 9. Time Complexity

| Problem | Algorithm | Complexity |
|---------|----------|-----------|
| BFS shortest path | BFS | O(V + E) |
| BFS on grid (m×n) | BFS | O(m×n) |
| Word Ladder (len L, dict size W) | BFS | O(W × L × 26) |
| Sliding Window Max | Monotonic Deque | O(n) |

---

## 10. Space Complexity

| Problem | Space | Reason |
|---------|-------|--------|
| BFS | O(V) | Queue + visited set |
| BFS on grid | O(m×n) | Visited boolean grid |
| Sliding Window Max | O(k) | Deque holds at most k indices |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Start = end in BFS | Return 0 steps |
| No path exists in BFS | Return -1 after queue empties |
| All oranges already rotten | Return 0 immediately |
| Fresh oranges unreachable | Return -1 |
| k = 1 in sliding max | Every element is its own max |
| k = n in sliding max | Result has one element: max of entire array |
| Disconnected graph | BFS from start only reaches connected component |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Not marking visited BEFORE adding to queue
queue.offer(neighbor);
visited.add(neighbor); // WRONG: may add duplicate in multi-threaded or before processing

// CORRECT: mark visited when adding (not when polling)
if (!visited.contains(neighbor)) {
    visited.add(neighbor); // mark HERE
    queue.offer(neighbor);
}

// MISTAKE 2: Not using level-by-level loop for step counting
// Without inner for(size) loop, you lose track of which level you're on

// MISTAKE 3: Monotonic deque direction error
// For sliding MAXIMUM: remove SMALLER elements from back
while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) // remove smaller
// For sliding MINIMUM: remove LARGER elements from back
while (!deque.isEmpty() && nums[deque.peekLast()] >= nums[i]) // remove larger

// MISTAKE 4: Out-of-bounds check in grid BFS
// Check bounds BEFORE accessing grid
if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 0)
// NOT: if (grid[nr][nc] == 0 && nr >= 0 ...) → ArrayIndexOutOfBounds
```

---

## 13. Interview-Level Explanation

**Q: "Why does BFS find the shortest path in an unweighted graph?"**

> "BFS expands outward in layers — first all nodes at distance 1, then all at distance 2, etc. The first time a node is reached, it's via the shortest path, because any other path to it must be at least as long (it would have been found in a previous or same layer). This is fundamentally different from DFS, which finds *a* path but not necessarily the shortest."

**Q: "Why is the Monotonic Deque O(n) for sliding window maximum?"**

> "Each element is added to the deque exactly once and removed at most once — either from the back when a larger element arrives, or from the front when it leaves the window. So total add + remove operations = 2n, giving us O(n)."

---

## 14. Real-World Use Cases

| Application | Pattern |
|------------|---------|
| **Network routing** | BFS finds shortest hop-count path |
| **Social networks** | Degrees of separation (BFS) |
| **Web crawlers** | BFS explores links level by level |
| **GPS navigation** | BFS for unweighted roads (Dijkstra for weighted) |
| **Rate limiting** | Sliding window on time axis |
| **Real-time analytics** | Sliding window max/min for dashboards |
| **Video streaming** | Sliding buffer max bandwidth |

---

## 15. Variations of This Pattern

| Variation | Description | Example |
|-----------|-------------|---------|
| Standard BFS | Shortest path in graph | Shortest Path in Grid |
| Multi-source BFS | Multiple starting points at distance 0 | Rotting Oranges |
| BFS on implicit graph | Generate neighbors dynamically | Word Ladder |
| Bidirectional BFS | From both start and end | Word Ladder II (optimization) |
| 0-1 BFS | Edge weights 0 or 1, use deque | Minimum Cost Path |
| Sliding window max | Decreasing deque | Sliding Window Maximum |
| Sliding window min | Increasing deque | Minimum in Window |

---

## 16. Practice Problems

### Easy — Foundation
1. **Binary Tree Level Order Traversal** (LeetCode #102)
   - *Task:* Return node values grouped by level.
   - *Hint:* BFS with inner for(size) loop — each iteration is one level.

2. **Flood Fill** (LeetCode #733)
   - *Task:* Fill connected region with new color.
   - *Hint:* BFS from starting cell, expand to same-color neighbors.

3. **Find if Path Exists in Graph** (LeetCode #1971)
   - *Task:* BFS/DFS to check connectivity.
   - *Hint:* BFS from source, return true if destination visited.

### Medium — Applied BFS
1. **Rotting Oranges** (LeetCode #994)
   - *Task:* Min minutes until all fresh oranges rot.
   - *Hint:* Multi-source BFS from all rotten oranges simultaneously.

2. **Word Ladder** (LeetCode #127)
   - *Task:* Min transformations from beginWord to endWord.
   - *Hint:* BFS where neighbors = words differing by one letter.

3. **Pacific Atlantic Water Flow** (LeetCode #417)
   - *Task:* Find cells that can flow to both oceans.
   - *Hint:* Reverse BFS from each ocean (water flows uphill in reverse).

4. **Sliding Window Maximum** (LeetCode #239)
   - *Task:* Max in every window of size k.
   - *Hint:* Monotonic decreasing deque storing indices.

5. **Jump Game III** (LeetCode #1306)
   - *Task:* Can you reach index with value 0?
   - *Hint:* BFS from start, add i+arr[i] and i-arr[i] as neighbors.

### Hard — Complex BFS
1. **Word Ladder II** (LeetCode #126)
   - *Task:* All shortest transformation sequences.
   - *Hint:* BFS to find distances + DFS/backtrack to enumerate paths.

2. **Trapping Rain Water II** (LeetCode #407)
   - *Task:* 3D water trapping.
   - *Hint:* Min-heap BFS from borders — O(mn log mn).

3. **Shortest Path in Binary Matrix** (LeetCode #1091)
   - *Task:* Shortest clear path from top-left to bottom-right (8 directions).
   - *Hint:* BFS with 8-directional movement.

---

## 17. How to Know You Have Mastered Queue & Deque Patterns

You have mastered this topic when you can:
- [ ] Write the BFS template for a graph and a grid from memory
- [ ] Explain why BFS guarantees shortest path in unweighted graphs
- [ ] Implement multi-source BFS (Rotting Oranges) correctly
- [ ] Write the Monotonic Deque for sliding window max including all edge cases
- [ ] Distinguish BFS vs DFS choice criteria
- [ ] Implement Word Ladder with the "generate all one-letter neighbors" trick
- [ ] Mark visited before queuing (not after dequeuing)
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. In BFS, why do you mark a node as visited when you **add it to the queue** rather than when you **process it**?

2. What's the difference between a regular Queue and a Deque? When do you need a Deque?

3. In Multi-source BFS (Rotting Oranges), how is it different from running BFS from each rotten orange separately?

4. The monotonic deque removes elements from the back when `nums[deque.peekLast()] <= nums[i]`. Why `<=` instead of `<`?

5. If a grid is all zeros (all passable), what is the BFS time complexity for finding shortest path?

6. In Word Ladder, you change each letter of the current word from 'a' to 'z'. Worst case, how many neighbors does a word of length L have?

7. Can BFS be used on a weighted graph for shortest path? If not, what should you use?

8. For sliding window minimum (not maximum), does the deque maintain increasing or decreasing order?

> **Answers:**
> 1. If you mark when processing, you may add the same node multiple times to the queue before processing it — wasting memory and time. Marking when adding ensures each node enters the queue at most once.
> 2. Queue is FIFO (add rear, remove front). Deque supports both ends. You need Deque for sliding window (remove from front when out of window, remove from back when maintaining monotonic order).
> 3. Multi-source BFS adds ALL rotten oranges at level 0 and expands simultaneously — correctly finds minimum time for all. Sequential BFS from each rotten orange overcounts (each gives max time from one source, not minimum from all).
> 4. With `<=`, duplicates are removed — the newer duplicate is retained. This is correct: equal elements don't need to coexist, and the newer one will stay in the window longer.
> 5. O(m×n) — each cell visited at most once.
> 6. L × 26 neighbors. Each of L positions can be any of 26 letters.
> 7. No. BFS assumes equal edge weights. Use Dijkstra's for non-negative weights, or Bellman-Ford for negative weights.
> 8. Increasing — remove larger elements from the back. Front is always the minimum.

---

**Next →** `../11_Linked_List/01_Linked_List_Patterns.md`

```java
// Generic BFS Template
void bfs(Node start) {
    Queue<Node> queue = new LinkedList<>();
    Set<Node> visited = new HashSet<>();
    queue.offer(start);
    visited.add(start);
    int level = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();  // snapshot of current level size
        for (int i = 0; i < size; i++) {
            Node curr = queue.poll();
            // process curr
            for (Node neighbor : curr.neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }
        level++;
    }
}
```

---

## Pattern 2: Multi-Source BFS

Start BFS from multiple sources simultaneously. Used in "distance from nearest X" problems.

**Problem:** Rotten Oranges — minimum minutes to rot all fresh oranges.

```java
int orangesRotting(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;

    // Enqueue all rotten oranges as starting points
    for (int r = 0; r < m; r++)
        for (int c = 0; c < n; c++) {
            if (grid[r][c] == 2) queue.offer(new int[]{r, c});
            else if (grid[r][c] == 1) fresh++;
        }

    if (fresh == 0) return 0;

    int minutes = 0;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    while (!queue.isEmpty()) {
        minutes++;
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            for (int[] d : dirs) {
                int nr = curr[0] + d[0], nc = curr[1] + d[1];
                if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;
                    fresh--;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
    }
    return fresh == 0 ? minutes - 1 : -1;
}
```

---

## Pattern 3: 0-1 BFS

When edge weights are either 0 or 1, use a **deque** instead of priority queue:
- 0-weight edge: push to front (like free move)
- 1-weight edge: push to back

Time: O(V + E) instead of O((V+E) log V) with Dijkstra.

```java
int[] zeroOneBFS(int n, int[][] edges, int src) {
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[src] = 0;
    Deque<Integer> deque = new ArrayDeque<>();
    deque.offerFirst(src);

    while (!deque.isEmpty()) {
        int u = deque.pollFirst();
        for (int[] edge : adj[u]) {
            int v = edge[0], w = edge[1];
            if (dist[u] + w < dist[v]) {
                dist[v] = dist[u] + w;
                if (w == 0) deque.offerFirst(v);
                else deque.offerLast(v);
            }
        }
    }
    return dist;
}
```

---

## Pattern 4: BFS Shortest Path in Grid

```java
int shortestPath(int[][] grid, int[] start, int[] end) {
    int m = grid.length, n = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] visited = new boolean[m][n];
    queue.offer(new int[]{start[0], start[1], 0});
    visited[start[0]][start[1]] = true;

    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
    while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int r = curr[0], c = curr[1], dist = curr[2];
        if (r == end[0] && c == end[1]) return dist;
        for (int[] d : dirs) {
            int nr = r + d[0], nc = c + d[1];
            if (nr >= 0 && nr < m && nc >= 0 && nc < n
                    && !visited[nr][nc] && grid[nr][nc] == 0) {
                visited[nr][nc] = true;
                queue.offer(new int[]{nr, nc, dist + 1});
            }
        }
    }
    return -1;
}
```

---

## Pattern 5: LRU Cache (Deque + HashMap)

**LRU Cache:** Evict least recently used when at capacity.

```java
class LRUCache {
    int capacity;
    Map<Integer, Integer> map = new LinkedHashMap<Integer, Integer>(16, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry e) {
            return size() > capacity;
        }
    };

    LRUCache(int capacity) { this.capacity = capacity; }

    int get(int key) { return map.getOrDefault(key, -1); }

    void put(int key, int value) { map.put(key, value); }
}

// Or manual with HashMap + Doubly Linked List:
class LRUCacheManual {
    // See full implementation in 11_Linked_List section
}
```

---

## Practice Problems

**Easy:**
1. Binary Tree Level Order Traversal.
2. Average of Levels in Binary Tree.
3. N-ary Tree Level Order Traversal.

**Medium:**
1. Rotten Oranges.
2. Word Ladder.
3. Open the Lock.
4. Shortest Path in Binary Matrix.
5. Walls and Gates.

**Hard:**
1. Sliding Window Maximum (deque).
2. Jump Game VI (deque DP).
3. Shortest Path Visiting All Nodes.

---

**Next →** `../11_Linked_List/01_Linked_List_Patterns.md`
