# Graph Search Patterns Deep Dive (MAANG L5 Coverage)

---

# INDEX

| Category | Patterns |
|----------|----------|
| [Core Concepts](#core-concepts) | BFS vs DFS, Templates |
| [Matrix/Grid](#matrix-grid-patterns) | Patterns 0-8 |
| [Graph Traversal](#graph-traversal-patterns) | Patterns 9-14 |
| [Shortest Path](#shortest-path-patterns) | Patterns 15-18 |
| [Advanced](#advanced-patterns) | Patterns 19-22 |

---

# The One Sentence That Unlocks All Graph Problems

> **"BFS for SHORTEST PATH, DFS for EXPLORING ALL PATHS."**

---

# 🌟 ZERO TO HERO: Understanding Graph Search

## BFS vs DFS - When to Use Which

```
┌─────────────────────────────────────────────────────────────┐
│                    BFS vs DFS                               │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  BFS (Breadth-First):              DFS (Depth-First):       │
│  - Level by level                  - Go deep first          │
│  - Uses QUEUE                      - Uses STACK/Recursion   │
│  - SHORTEST PATH                   - EXPLORE ALL            │
│  - Minimum steps                   - Cycle detection        │
│                                    - Topological sort       │
│                                                             │
│  Use BFS when:                     Use DFS when:            │
│  - "Minimum moves"                 - "Find all paths"       │
│  - "Shortest distance"             - "Does path exist"      │
│  - "Level order"                   - "Connected components" │
│  - "Nearest X"                     - "Cycle detection"      │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

---

# CORE TEMPLATES

## Template 1: BFS (Breadth-First Search)

```java
int bfs(int[][] grid, int startR, int startC) {
    int rows = grid.length, cols = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    boolean[][] visited = new boolean[rows][cols];
    
    queue.offer(new int[]{startR, startC});
    visited[startR][startC] = true;
    int level = 0;
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    
    while (!queue.isEmpty()) {
        int size = queue.size();  // Process level by level
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            int r = curr[0], c = curr[1];
            
            // Check if target reached
            if (isTarget(r, c)) return level;
            
            // Add neighbors
            for (int[] dir : dirs) {
                int nr = r + dir[0], nc = c + dir[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols 
                    && !visited[nr][nc] && grid[nr][nc] == 1) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        level++;
    }
    return -1;  // Not found
}
```

---

## Template 2: DFS (Depth-First Search)

```java
// Recursive DFS
void dfs(int[][] grid, int r, int c, boolean[][] visited) {
    // Bounds check
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) 
        return;
    // Already visited or invalid
    if (visited[r][c] || grid[r][c] == 0) 
        return;
    
    visited[r][c] = true;  // Mark visited BEFORE recursing
    
    // Process current cell
    
    // Explore all 4 directions
    dfs(grid, r + 1, c, visited);
    dfs(grid, r - 1, c, visited);
    dfs(grid, r, c + 1, visited);
    dfs(grid, r, c - 1, visited);
}
```

---

## The 4-Directions Array

```java
// 4 directions: right, down, left, up
int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};

// 8 directions (including diagonals)
int[][] dirs8 = {{0,1}, {1,0}, {0,-1}, {-1,0}, 
                 {1,1}, {1,-1}, {-1,1}, {-1,-1}};

// Bounds check helper
boolean isValid(int r, int c, int rows, int cols) {
    return r >= 0 && r < rows && c >= 0 && c < cols;
}
```

---

# PATTERN 0: Number of Islands (LC 200) ⭐⭐

## Pattern Recognition Signal

**When you see:** "grid", "count connected regions", "islands"

**Instant thought:** "DFS flood fill! Sink each island as you count."

---

## The Mental Model: "The Flood"

```
Imagine flying over an ocean with islands.
You want to count separate land masses.

Strategy:
1. Scan the grid from top-left
2. When you spot land (1) → count it as new island
3. FLOOD that entire island (turn all connected 1s to 0s)
4. Continue scanning

The flooding ensures each island counted exactly once.
```

---

## Visual Dry Run

**Input:**
```
1 1 0 0 0
1 1 0 0 0
0 0 1 0 0
0 0 0 1 1
```

```
═══════════════════════════════════════════════════════════════

Step 1: Found '1' at (0,0)
  count = 1
  
  DFS flood from (0,0):
  - Sink (0,0) → '0'
  - Sink (0,1) → '0'
  - Sink (1,0) → '0'
  - Sink (1,1) → '0'

═══════════════════════════════════════════════════════════════

Step 2: Found '1' at (2,2)
  count = 2
  DFS flood: Sink (2,2)

═══════════════════════════════════════════════════════════════

Step 3: Found '1' at (3,3)
  count = 3
  DFS flood: Sink (3,3), (3,4)

═══════════════════════════════════════════════════════════════

Final count = 3 ✓
```

---

## The Code

```java
int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    
    int count = 0;
    int rows = grid.length, cols = grid[0].length;
    
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == '1') {
                count++;           // Found new island
                dfs(grid, r, c);   // Sink it
            }
        }
    }
    return count;
}

void dfs(char[][] grid, int r, int c) {
    // Bounds + water check
    if (r < 0 || r >= grid.length || 
        c < 0 || c >= grid[0].length ||
        grid[r][c] == '0') {
        return;
    }
    
    grid[r][c] = '0';  // Sink this cell
    
    // Flood in all 4 directions
    dfs(grid, r + 1, c);
    dfs(grid, r - 1, c);
    dfs(grid, r, c + 1);
    dfs(grid, r, c - 1);
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not sinking before recursing | Infinite loop | Sink BEFORE recursive calls |
| Using '1' vs 1 | char vs int | Use '1' and '0' for char grid |

---

## Mind-Map Anchor

```
"COUNT CONNECTED REGIONS"
           │
           ▼
┌─────────────────────────────────────────┐
│ DFS Flood Fill                          │
│ 1. Find '1' → count++                   │
│ 2. DFS to sink entire island            │
│ 3. Continue scanning                    │
│ Key: Sink BEFORE recursing              │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Find land, count it, flood it, move on."

---

# PATTERN 3: Rotting Oranges (LC 994) ⭐⭐

## Pattern Recognition Signal

**When you see:** "spreading", "infection", "minimum time to spread"

**Instant thought:** "Multi-source BFS! Start from ALL sources."

---

## The Mental Model: "The Zombie Apocalypse"

```
Rotten oranges are zombies.
Fresh oranges are humans.
Each minute, zombies infect adjacent humans.

How long until all humans are zombies (or some survive)?

Key insight: Start BFS from ALL zombies simultaneously!
```

---

## The Code

```java
int orangesRotting(int[][] grid) {
    int rows = grid.length, cols = grid[0].length;
    Queue<int[]> queue = new LinkedList<>();
    int fresh = 0;
    
    // Add ALL rotten oranges to queue (multi-source)
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == 2) queue.offer(new int[]{r, c});
            else if (grid[r][c] == 1) fresh++;
        }
    }
    
    if (fresh == 0) return 0;  // No fresh oranges
    
    int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
    int minutes = 0;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        boolean rotted = false;
        
        for (int i = 0; i < size; i++) {
            int[] curr = queue.poll();
            
            for (int[] dir : dirs) {
                int nr = curr[0] + dir[0];
                int nc = curr[1] + dir[1];
                
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols 
                    && grid[nr][nc] == 1) {
                    grid[nr][nc] = 2;  // Rot it
                    fresh--;
                    queue.offer(new int[]{nr, nc});
                    rotted = true;
                }
            }
        }
        if (rotted) minutes++;
    }
    
    return fresh == 0 ? minutes : -1;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Single-source BFS | Wrong time calculation | Add ALL rotten to queue first |
| Incrementing time wrong | Off by one | Only increment when something rotted |
| Forgetting impossible case | Return wrong answer | Check fresh == 0 at end |

---

# PATTERN 9: Course Schedule (LC 207) ⭐⭐

## Pattern Recognition Signal

**When you see:** "prerequisites", "dependencies", "can finish all"

**Instant thought:** "Cycle detection in directed graph!"

---

## The Mental Model: "The Chicken and Egg"

```
Course A requires B, B requires C, C requires A
→ Impossible! Circular dependency.

We need to detect if there's a CYCLE in the dependency graph.
No cycle = can finish all courses.
```

---

## The Code (DFS with 3 colors)

```java
boolean canFinish(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) 
        graph.add(new ArrayList<>());
    
    for (int[] pre : prerequisites)
        graph.get(pre[1]).add(pre[0]);
    
    // 0 = unvisited, 1 = visiting, 2 = visited
    int[] state = new int[numCourses];
    
    for (int i = 0; i < numCourses; i++) {
        if (hasCycle(graph, i, state)) return false;
    }
    return true;
}

boolean hasCycle(List<List<Integer>> graph, int node, int[] state) {
    if (state[node] == 1) return true;   // Cycle! Back edge
    if (state[node] == 2) return false;  // Already processed
    
    state[node] = 1;  // Mark as visiting
    
    for (int neighbor : graph.get(node)) {
        if (hasCycle(graph, neighbor, state)) return true;
    }
    
    state[node] = 2;  // Mark as visited
    return false;
}
```

---

## The 3-Color Approach

```
WHITE (0) = Unvisited
GRAY (1)  = Currently visiting (in recursion stack)
BLACK (2) = Completely processed

If we reach a GRAY node → CYCLE detected!
(We found a back edge to a node still being processed)
```

---

# PATTERN 15: Word Ladder (LC 127) ⭐⭐

## Pattern Recognition Signal

**When you see:** "transform word", "minimum steps", "change one letter"

**Instant thought:** "BFS on implicit graph! Words are nodes."

---

## The Code

```java
int ladderLength(String beginWord, String endWord, List<String> wordList) {
    Set<String> wordSet = new HashSet<>(wordList);
    if (!wordSet.contains(endWord)) return 0;
    
    Queue<String> queue = new LinkedList<>();
    queue.offer(beginWord);
    int level = 1;
    
    while (!queue.isEmpty()) {
        int size = queue.size();
        
        for (int i = 0; i < size; i++) {
            String word = queue.poll();
            
            if (word.equals(endWord)) return level;
            
            // Try changing each character
            char[] chars = word.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char original = chars[j];
                
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == original) continue;
                    chars[j] = c;
                    String newWord = new String(chars);
                    
                    if (wordSet.contains(newWord)) {
                        queue.offer(newWord);
                        wordSet.remove(newWord);  // Mark visited
                    }
                }
                chars[j] = original;  // Restore
            }
        }
        level++;
    }
    return 0;
}
```

---

# MAANG Coverage Map

| Pattern | Problem | Difficulty | Frequency |
|---------|---------|------------|-----------|
| 0 | Number of Islands (LC 200) | Medium | ⭐⭐⭐⭐⭐ |
| 3 | Rotting Oranges (LC 994) | Medium | ⭐⭐⭐⭐⭐ |
| 5 | Word Search (LC 79) | Medium | ⭐⭐⭐⭐ |
| 9 | Course Schedule (LC 207) | Medium | ⭐⭐⭐⭐⭐ |
| 10 | Course Schedule II (LC 210) | Medium | ⭐⭐⭐⭐ |
| 12 | Clone Graph (LC 133) | Medium | ⭐⭐⭐⭐ |
| 15 | Word Ladder (LC 127) | Hard | ⭐⭐⭐⭐ |

---

# Mastery Checklist

## Tier 1: Must Know
- [ ] Number of Islands (LC 200)
- [ ] Rotting Oranges (LC 994)
- [ ] Course Schedule (LC 207)
- [ ] Clone Graph (LC 133)

## Tier 2: Interview Favorites
- [ ] Word Search (LC 79)
- [ ] Word Ladder (LC 127)
- [ ] Pacific Atlantic Water Flow (LC 417)

## Tier 3: Differentiators
- [ ] Alien Dictionary (LC 269)
- [ ] Critical Connections (LC 1192)

---

# Quick Reference Card

```
┌─────────────────────────────────────────────────────────────┐
│                 GRAPH SEARCH CHEAT SHEET                    │
├─────────────────────────────────────────────────────────────┤
│ BFS (Queue):                                                │
│   - Shortest path in unweighted graph                       │
│   - Level-order traversal                                   │
│   - Multi-source: add ALL sources to queue first            │
├─────────────────────────────────────────────────────────────┤
│ DFS (Stack/Recursion):                                      │
│   - Explore all paths                                       │
│   - Cycle detection (3 colors)                              │
│   - Topological sort                                        │
│   - Connected components                                    │
├─────────────────────────────────────────────────────────────┤
│ 4-DIRECTIONS:                                               │
│   int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};            │
├─────────────────────────────────────────────────────────────┤
│ CYCLE DETECTION (Directed):                                 │
│   WHITE(0) → GRAY(1) → BLACK(2)                             │
│   GRAY → GRAY = CYCLE                                       │
└─────────────────────────────────────────────────────────────┘
```

---

*End of Graph Search Patterns Deep Dive*
