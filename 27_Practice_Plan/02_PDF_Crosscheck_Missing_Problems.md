# PDF Cross-Check Missing Problems

> Source: `DSA.pdf`
> Purpose: add the PDF problems that were not already present in the notes, grouped in the same beginner-to-pro learning style.

---

## How to Use This Addendum

This is not a replacement for `01_Staged_Practice_Plan.md`. Treat it as the **upgrade queue** after each topic.

For every section:
1. Finish the main topic note.
2. Solve the Foundation problems first.
3. Move to Core only when you can explain the pattern out loud.
4. Move to Pro when the implementation feels automatic.
5. Add mistakes to your mistake journal.

The goal is not volume. The goal is pattern recognition, clean implementation, and interview communication.

---

## 1. Arrays, Sorting, and In-Place Control

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Find Duplicate Number | Cycle detection / binary search on value | Teaches non-obvious array-as-linked-list thinking. |
| Squares of Sorted Array | Two pointers | Builds sorted-array merge intuition. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Remove Duplicates from Sorted Array II | Slow/fast pointer | Adds count constraints to basic duplicate removal. |
| Subarray Product Less Than K | Variable sliding window | Shows why product windows only work cleanly with positive numbers. |
| 3-Way QuickSort | Dutch National Flag partition | Deepens partition logic beyond two-way quicksort. |

### Pro

| Problem | Pattern | Why It Matters |
|---|---|---|
| Max Points on a Line | Hashing + math normalization | Forces careful slope reduction, duplicate handling, and precision avoidance. |

---

## 2. String and Hashing Additions

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Palindrome Permutation | Frequency parity | Teaches the "at most one odd count" invariant. |
| Zigzag Conversion | Simulation | Trains index movement and direction flipping. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Word Pattern | HashMap bijection | Same shape as Isomorphic Strings, but word-token based. |
| String to Integer edge cases | Parsing | Builds defensive coding for signs, overflow, and invalid characters. |

---

## 3. Linked List Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Intersection of Two Linked Lists | Two pointers | Elegant pointer reset trick; strong interview favorite. |
| Remove Duplicates from Sorted List | Single pass | Reinforces pointer rewiring without dummy complexity. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Add Two Numbers II | Stack / reverse list | Same arithmetic idea, harder digit order. |
| Swap Nodes in Pairs | Local pointer reversal | Small groups before full k-group reversal. |
| Odd Even Linked List | Stable partition by position | Tests multi-tail pointer management. |
| Remove Duplicates from Sorted List II | Dummy node + skip runs | Harder because duplicate groups must disappear completely. |

---

## 4. Stack Pattern Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Minimum Add to Make Parentheses Valid | Balance counter / stack | Simpler form of parentheses repair. |
| Score of Parentheses | Stack / depth counting | Builds nested-context thinking. |
| Remove All Adjacent Duplicates | Stack simulation | Teaches cancellation patterns. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Longest Valid Parentheses | Index stack / DP | Classic hard stack boundary problem. |
| Next Greater Element III | Monotonic idea + next permutation | Bridges stack thinking and permutation logic. |
| Sum of Subarray Ranges | Monotonic stack contribution | Pair with Sum of Subarray Minimums. |
| Asteroid Collision | Stack simulation | Great for collision and direction-state logic. |
| Online Stock Span | Monotonic decreasing stack | Streaming version of stock span. |

### Pro

| Problem | Pattern | Why It Matters |
|---|---|---|
| Basic Calculator III | Recursive stack parser | Full expression parsing with nested parentheses. |
| Remove All Adjacent Duplicates II | Stack of character counts | Extends simple cancellation with run lengths. |
| 132 Pattern | Monotonic stack from right | Non-obvious pattern recognition problem. |

---

## 5. Heap / Priority Queue Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Find K Pairs with Smallest Sums | Min-heap frontier | K-way expansion without generating all pairs. |
| Ugly Number II | Min-heap or three pointers | Teaches duplicate avoidance in generated sequences. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Reorganize String | Max-heap by frequency | Greedy placement under adjacency constraint. |
| Rearrange String K Distance Apart | Max-heap + cooldown queue | Harder scheduling version of Reorganize String. |
| Smallest Range Covering Elements from K Lists | Min-heap + current max | Pro-level k-way merge range tracking. |

---

## 6. Binary Search Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Search in Rotated Sorted Array II | Modified binary search with duplicates | Shows how duplicates weaken search-space decisions. |
| Find Minimum in Rotated Sorted Array II | Boundary binary search with duplicates | Forces careful `right--` duplicate handling. |
| Valid Perfect Square | Binary search on answer | Safer than floating-point sqrt. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Minimum Days to Make M Bouquets | Binary search on answer | Monotonic feasibility on days. |
| Capacity to Ship Packages Within D Days | Binary search on answer | Canonical capacity-minimization problem. |
| Find K Closest Elements | Binary search + window | Combines sorted array with fixed-size answer window. |
| Allocate Books (GFG) | Binary search on answer | Same mental model as Split Array Largest Sum. |

### Pro

| Problem | Pattern | Why It Matters |
|---|---|---|
| Minimize Max Distance to Gas Station | Binary search on double answer | Introduces precision-based termination. |

---

## 7. Tree Traversal and Tree DP Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Binary Tree Inorder Traversal | DFS / iterative stack | Base traversal pattern. |
| Binary Tree Preorder Traversal | DFS / iterative stack | Root-first traversal. |
| Binary Tree Postorder Traversal | DFS / reverse preorder | Most useful for bottom-up reasoning. |
| Minimum Depth of Binary Tree | BFS shortest leaf | Shows why BFS is natural for nearest-level answers. |
| Merge Two Binary Trees | Recursive merge | Simple tree construction by recursion. |
| Binary Tree Paths | Root-to-leaf backtracking | Builds path-state handling. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Binary Tree Zigzag Level Order | BFS with direction | Adds output-order control to level traversal. |
| Binary Tree Left Side View | BFS / DFS by level | Pair with right-side view. |
| Maximum Width of Binary Tree | BFS with indexed positions | Teaches overflow-safe positional indexing. |
| Populating Next Right Pointers | BFS / O(1) pointer linking | Tests level linkage without extra output arrays. |
| Path Sum III | Prefix sum on tree | Powerful transfer from array prefix sum to tree paths. |
| Sum Root to Leaf Numbers | DFS carrying state | Classic numeric path aggregation. |
| Longest Univalue Path | Bottom-up path length | Similar structure to diameter. |
| Lowest Common Ancestor of Deepest Leaves | Depth + LCA | Combines height and ancestor logic. |
| All Nodes Distance K | Parent map + BFS | Turns a tree into an undirected graph. |

### Pro

| Problem | Pattern | Why It Matters |
|---|---|---|
| House Robber III | Tree DP include/exclude | First serious tree DP. |
| Binary Tree Cameras | Greedy tree states | Postorder state machine. |
| Distribute Coins in Binary Tree | Postorder balance flow | Teaches subtree surplus/deficit reasoning. |
| Maximum Product of Splitted Binary Tree | Subtree sums | Requires two-pass tree aggregation. |
| Pseudo-Palindromic Paths | Bitmask path parity | Combines trees and bit manipulation. |
| Sum of Distances in Tree | Rerooting DP | Advanced graph/tree DP. |
| Delete Nodes and Return Forest | DFS deletion + roots | Tests mutation and result construction. |

---

## 8. BST Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Insert into BST | Recursive BST operation | Basic ordered-tree mutation. |
| Convert Sorted Array to BST | Divide and conquer | Balanced construction from sorted data. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Inorder Successor in BST | BST search / inorder | Tests successor/predecessor reasoning. |
| Convert Sorted List to BST | Slow/fast middle split | Combines linked list and BST construction. |
| Two Sum IV - Input is BST | HashSet / inorder two pointers | Multiple valid approaches; good discussion problem. |
| Serialize and Deserialize BST | Preorder + bounds | Specializes serialization using BST property. |
| Balance a BST | Inorder + rebuild | Shows transform from unbalanced to balanced. |

### Pro

| Problem | Pattern | Why It Matters |
|---|---|---|
| Largest BST Subtree | Postorder validity tuple | Advanced bottom-up state passing. |
| Unique Binary Search Trees | Catalan DP | Important combinatorics DP. |
| Unique Binary Search Trees II | Recursive generation | Harder structural generation problem. |

---

## 9. Backtracking Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Combination Sum III | Bounded combination | Adds fixed size and fixed sum constraints. |
| Letter Combinations of a Phone Number | Product recursion | Classic branching template. |
| Restore IP Addresses | Segment backtracking | Strong pruning and validation exercise. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Combination Sum IV | DP counting variant | Same name family, different paradigm. |
| Palindrome Permutation II | Backtracking with counts | Avoids duplicate permutations. |
| N-Queens II | Count-only backtracking | Same search, lighter output. |
| Partition to K Equal Sum Subsets | Bucket backtracking | High-value pruning problem. |
| Matchsticks to Square | Bucket backtracking | Same pattern with geometric framing. |
| Split Array into Fibonacci Sequence | Constructive backtracking | Requires overflow and prefix constraints. |

---

## 10. Matrix-as-Graph Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Flood Fill | DFS/BFS coloring | Simplest grid traversal mutation. |
| Walls and Gates | Multi-source BFS | Base distance-fill pattern. |
| 01 Matrix | Multi-source BFS | Same as Walls and Gates with binary cells. |
| Coloring A Border | DFS component boundary | Distinguishes component interior from boundary. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Surrounded Regions | Boundary flood fill | "Eliminate disqualified components" pattern. |
| Shortest Bridge | DFS + BFS expansion | Component marking plus shortest expansion. |
| Shortest Path in Binary Matrix | BFS shortest path | 8-direction grid BFS. |
| Shortest Path with Obstacles Elimination | BFS with state | Adds remaining eliminations to visited state. |
| As Far from Land as Possible | Multi-source BFS | Distance from nearest source. |
| Unique Paths III | Backtracking on grid | Visit every non-obstacle square exactly once. |

### Pro

| Problem | Pattern | Why It Matters |
|---|---|---|
| Minimum Knight Moves | BFS on implicit graph | Infinite-grid shortest path with symmetry. |

---

## 11. Graph BFS/DFS Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Keys and Rooms | DFS reachability | Clean graph traversal warm-up. |
| Find if Path Exists in Graph | BFS/DFS reachability | Minimal undirected graph problem. |
| Find the Town Judge | In-degree / out-degree | Teaches graph degree counting. |
| Find Center of Star Graph | Degree / direct inspection | Fast graph property recognition. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Reorder Routes to Make All Paths Lead to City Zero | DFS with directed-edge cost | Edge orientation over an undirected traversal. |
| Minimum Number of Vertices to Reach All Nodes | In-degree zero nodes | DAG source reasoning. |
| Count Unreachable Pairs of Nodes | Connected components sizes | Component counting with combinatorics. |
| Possible Bipartition | Bipartite coloring | Same pattern as Is Graph Bipartite with constraints. |
| Detonate the Maximum Bombs | Build graph + DFS from every node | Geometry-to-graph conversion. |
| Similar String Groups | DFS/DSU grouping | Pairwise similarity component problem. |

---

## 12. Topological Sort Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Find Eventual Safe States | Reverse topo / DFS colors | Cycle detection with safe terminal states. |
| Parallel Courses | Kahn's algorithm by semester | Adds level counting to topo BFS. |
| All Ancestors of a Node in DAG | Topo + set propagation | DAG reachability aggregation. |
| Loudest and Richest | Graph direction + memo DFS | Topological dependency thinking. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Course Schedule IV | Transitive prerequisite queries | Reachability over DAG. |
| Sequence Reconstruction | Unique topological order | Tests whether only one topo ordering exists. |
| Minimum Height Trees | Trim leaves | Topological peeling on an undirected tree. |
| Build a Matrix With Conditions | Two topological sorts | Row and column constraints combine. |
| Longest Increasing Path in Matrix | DFS memo / DAG DP | Converts matrix ordering into DAG longest path. |
| Sort Items by Groups Respecting Dependencies | Hierarchical topological sort | Pro-level dependency orchestration. |

---

## 13. DSU Upgrade Queue

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Number of Connected Components in Undirected Graph | DSU basics | Direct component counting. |
| Number of Operations to Make Network Connected | DSU + edge count | Connectivity feasibility. |
| Friend Circles / Number of Provinces | DSU / DFS | Classic matrix-to-components problem. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Redundant Connection II | Directed DSU | Harder than undirected redundant edge. |
| Most Stones Removed with Same Row or Column | DSU by row/column identity | Teaches modeling, not just union calls. |
| Regions Cut By Slashes | DSU cell subdivision | Advanced modeling of geometry as connectivity. |
| Smallest String With Swaps | DSU groups + sorting | Component post-processing. |
| Min Cost to Connect All Points | Kruskal MST | DSU plus greedy edge ordering. |
| Earliest Moment When Everyone Become Friends | Sort events + DSU | Dynamic connectivity over time. |

---

## 14. Shortest Path and Advanced Graphs

### Foundation

| Problem | Pattern | Why It Matters |
|---|---|---|
| The Maze | BFS/DFS rolling movement | Grid movement with non-unit transitions. |
| Maze II | Dijkstra | Rolling movement with shortest distance. |
| Find the City With Smallest Number of Neighbors | Floyd-Warshall | All-pairs shortest path practice. |

### Core

| Problem | Pattern | Why It Matters |
|---|---|---|
| Path With Minimum Effort | Dijkstra / binary search + BFS | Minimize maximum edge cost. |
| Minimum Cost to Make at Least One Valid Path | 0-1 BFS | Teaches deque-based shortest path. |
| Swim in Rising Water | Dijkstra / union find | Minimax path over grid. |
| Shortest Path to Get All Keys | BFS + bitmask state | Combines shortest path and state compression. |
| Minimum Obstacle Removal to Reach Corner | 0-1 BFS | Another clean deque shortest-path drill. |

### Pro

| Problem | Pattern | Why It Matters |
|---|---|---|
| Minimum Weighted Subgraph With Required Paths | Multi-source Dijkstra | Advanced shortest path composition. |
| Reachable Nodes In Subdivided Graph | Dijkstra + edge accounting | Requires distance plus partial-edge counting. |
| Critical Connections in Network | Tarjan bridges | Core low-link algorithm. |
| Articulation Points (GFG) | Tarjan articulation points | Companion to bridge finding. |
| Strongly Connected Components (Kosaraju's) | SCC decomposition | Directed graph component mastery. |
| Strongly Connected Components (Tarjan's) | Low-link SCC | One-pass SCC method. |
| Reconstruct Itinerary | Eulerian path | Hierholzer's algorithm with lexical order. |
| Valid Arrangement of Pairs | Eulerian path | Edge arrangement as path construction. |
| Graph Connectivity With Threshold | DSU by factors | Number theory plus connectivity. |
| Checking Existence of Edge Length Limited Paths | Offline sorting + DSU | Query processing pattern. |
| Maximum Bipartite Matching (GFG) | Matching | Foundation for assignment/flow thinking. |
| Maximum Flow (Ford-Fulkerson) | Flow network | Advanced graph design pattern. |
| Count Subtrees With Max Distance Between Cities | Tree subsets | Exponential graph enumeration. |
| Number of Restricted Paths From First to Last Node | Dijkstra + DP | Shortest-distance DAG counting. |

---

## 15. Dynamic Programming Upgrade Queue

### 1D DP

| Problem | Pattern | Why It Matters |
|---|---|---|
| Min Cost Climbing Stairs | 1D transition | Beginner DP with cost minimization. |
| Delete and Earn | Transform to House Robber | Pattern transformation practice. |
| Decode Ways II | DP with wildcard states | Harder counting with `*`. |
| Divisor Game | Boolean DP / math | Simple game DP. |

### Knapsack DP

| Problem | Pattern | Why It Matters |
|---|---|---|
| Ones and Zeroes | 2D 0/1 knapsack | Capacity in two dimensions. |
| Last Stone Weight II | Subset sum minimization | Partition mindset. |
| Coin Change II | Count combinations | Different from min-coin optimization. |
| Minimum Subset Sum Difference (GFG) | Subset sum | Partition closest to half. |
| Count of Subset Sum (GFG) | Counting DP | Count ways, not feasibility. |
| Rod Cutting (GFG) | Unbounded knapsack | Reuse choices. |
| Minimum Cost For Tickets | Calendar DP | Time-indexed choice DP. |

### 2D and String DP

| Problem | Pattern | Why It Matters |
|---|---|---|
| Unique Paths II | Grid DP with obstacles | Adds blocked cells to base grid DP. |
| Maximal Square | 2D local recurrence | Classic square-size DP. |
| Longest Palindromic Subsequence | Interval/string DP | LCS cousin. |
| Interleaving String | 2D string DP | Prefix feasibility. |
| Dungeon Game | Reverse grid DP | Minimum health from destination backward. |
| Cherry Pickup | 3D / two-walker DP | Pro grid DP. |

### LIS and Sequence DP

| Problem | Pattern | Why It Matters |
|---|---|---|
| Number of Longest Increasing Subsequence | LIS counting | Track length and count together. |
| Longest Continuous Increasing Subsequence | Linear run tracking | Simpler LIS variant. |
| Longest Bitonic Subsequence (GFG) | LIS + LDS | Combine increasing and decreasing passes. |
| Maximum Sum Increasing Subsequence (GFG) | Weighted LIS | Optimize sum instead of length. |
| Increasing Triplet Subsequence | Greedy LIS length 3 | O(1) state recognition. |
| Russian Doll Envelopes | Sort + LIS | Multi-dimensional LIS. |
| Maximum Length of Pair Chain | Greedy / DP | Interval-like LIS. |
| Box Stacking (GFG) | 3D LIS | Advanced ordering DP. |
| Building Bridges (GFG) | Sort + LIS | Crossing constraints become LIS. |
| Largest Divisible Subset | Divisibility DP | Sequence construction with parent recovery. |
| Longest Arithmetic Subsequence | HashMap DP by difference | DP over pair differences. |
| Longest Arithmetic Subsequence of Given Difference | HashMap DP | Simpler fixed-difference version. |
| Wiggle Subsequence | State DP / greedy | Alternating sign transitions. |
| Delete Columns to Make Sorted III | LIS on columns | String-array DP. |

### Interval DP

| Problem | Pattern | Why It Matters |
|---|---|---|
| Minimum Cost to Cut a Stick | Interval DP | Same spirit as matrix chain multiplication. |
| Minimum Cost Tree From Leaf Values | Interval DP / monotonic stack | Compare DP and greedy-stack options. |
| Palindrome Partitioning II | Cut DP | Minimum cuts. |
| Palindrome Partitioning III | DP with changes | Partition plus edit cost. |
| Palindrome Partitioning IV | Boolean partition DP | Exactly three palindromic pieces. |
| Strange Printer | Interval DP | Hard overlapping subproblem structure. |
| Remove Boxes | 3D interval DP | Elite-level interval state design. |
| Boolean Parenthesization (GFG) | Interval count DP | Count ways to evaluate true/false. |
| Optimal Binary Search Tree (GFG) | Interval optimization | Classic CS DP. |
| Stone Game | Interval game DP | Difference-based state. |
| Stone Game II | Game DP with variable move limit | State includes dynamic parameter. |
| Stone Game III | Game DP with score difference | Compact state-machine thinking. |
| Minimum Score Triangulation of Polygon | Interval DP | Geometry as interval splitting. |

### Bitmask and State-Machine DP

| Problem | Pattern | Why It Matters |
|---|---|---|
| Traveling Salesman Problem (GFG) | Bitmask DP | Canonical subset-state problem. |
| Assignment Problem (GFG) | Bitmask DP | Worker-job matching. |
| Shortest Superstring | Bitmask DP + overlap | Hard string/state compression. |
| Smallest Sufficient Team | Bitmask DP | Skills as bits. |
| Maximum Students Taking Exam | Row mask DP | Grid constraints with bitmasks. |
| Fair Distribution of Cookies | Backtracking / bitmask | Minimize maximum bucket load. |
| Number of Ways to Wear Different Hats | Inverted bitmask DP | Assign hats to people. |
| Find Minimum Cost to Reach Destination | State DP | Cost minimization with constrained transitions. |
| Best Time to Buy and Sell Stock II | State machine / greedy | Unlimited transactions. |
| Best Time to Buy and Sell Stock III | State machine DP | At most two transactions. |
| Best Time to Buy and Sell Stock IV | State machine DP | At most k transactions. |
| Best Time to Buy and Sell Stock with Cooldown | State machine DP | Adds cooldown state. |
| Best Time to Buy and Sell Stock with Transaction Fee | State machine DP | Adds transaction cost. |

---

## 16. Trie and Advanced String Upgrade Queue

### Trie

| Problem | Pattern | Why It Matters |
|---|---|---|
| Design Add and Search Words Data Structure | Trie + wildcard DFS | Tests branching search. |
| Map Sum Pairs | Trie with prefix sums | Store aggregate values in trie nodes. |
| Prefix and Suffix Search | Trie / encoded keys | Advanced prefix-suffix indexing. |
| Maximum XOR of Two Numbers in Array | Binary trie | Converts numbers into bit paths. |

### Advanced Strings

| Problem | Pattern | Why It Matters |
|---|---|---|
| Implement strStr() / Find Index of First Occurrence | KMP | Core linear pattern matching. |
| Longest Happy Prefix | KMP / Z-algorithm | Prefix-suffix table reuse. |
| Repeated DNA Sequences | Rolling hash | Fixed-length duplicate detection. |
| Longest Duplicate Substring | Binary search + rolling hash | Pro string hashing. |
| Distinct Echo Substrings | Rolling hash / string compare | Advanced substring structure. |

---

## 17. Intervals, Greedy, and Scheduling Upgrade Queue

### Intervals

| Problem | Pattern | Why It Matters |
|---|---|---|
| My Calendar II | Sweep line / interval overlap | Allows double booking, rejects triple booking. |
| My Calendar III | Sweep line max overlap | Dynamic maximum overlap. |
| Divide Intervals Into Minimum Number of Groups | Min-heap / sweep line | Meeting Rooms variant. |

### Greedy

| Problem | Pattern | Why It Matters |
|---|---|---|
| Minimum Platforms (GFG) | Sweep line / two sorted arrays | Classic interval-overlap count for train platforms. |
| Job Sequencing (GFG) | Greedy + DSU/slot scheduling | Deadline-profit scheduling. |

---

## 18. Design Data Structures and Math Upgrade Queue

### Design Data Structures

| Problem | Pattern | Why It Matters |
|---|---|---|
| Design HashMap | Array buckets / chaining | Core hash table design. |
| Design HashSet | Array buckets / chaining | Set API design. |
| Design Twitter | Heap + timestamp feeds | Mini system-design flavored data structure problem. |
| Design Browser History | Stack / list pointer | Navigation state design. |
| Design Circular Queue | Fixed array ring buffer | Queue internals. |
| Design Circular Deque | Ring buffer with two ends | Deque internals. |
| Time-based Key-Value Store | HashMap + binary search | Versioned data access. |
| Design Rate Limiter | Token bucket / sliding window | System design bridge problem. |
| Design Distributed Cache | Cache design | System design bridge problem. |

### Math

| Problem | Pattern | Why It Matters |
|---|---|---|
| Integer to Roman | Greedy conversion | Ordered symbol mapping. |
| Roman to Integer | Parsing with subtractive rules | Direction-aware parsing. |
| Nth Digit | Math by digit blocks | Avoids building the sequence. |
| Excel Sheet Column Number | Base-26 conversion | String-to-number mapping. |
| Excel Sheet Column Title | Reverse base-26 conversion | Careful 1-indexed conversion. |

---

## 19. Bit Manipulation and Advanced Sorting Upgrade Queue

### Bit Manipulation

| Problem | Pattern | Why It Matters |
|---|---|---|
| Power of Four | Bit + modulo / mask | Refines power-of-two check. |
| Bitwise AND of Numbers Range | Common prefix | Range bit behavior. |
| Gray Code | Bit pattern generation | Sequence where neighbors differ by one bit. |

### Advanced Sorting / Counting

| Problem | Pattern | Why It Matters |
|---|---|---|
| Pancake Sorting | Constructive sorting | Teaches operation-constrained sorting. |
| Reverse Pairs | Merge sort counting | Hard inversion-count variant. |

---

## 20. Company-Specific Additions from PDF

These are not separate patterns; they are final-round targeting sets.

| Company | Missing Additions | Focus |
|---|---|---|
| Meta | Word Break II | Backtracking + DP reconstruction. |
| Netflix | Design Rate Limiter, Design Distributed Cache, Time-based Key-Value Store | Design-heavy data structure/system design bridge. |

---

## Revision Notes

- One-line summary: this file is the PDF-derived upgrade queue for problems absent from the existing notes.
- Three keywords: cross-check, coverage, progression.
- One interview trap: solving advanced problems before the underlying pattern is automatic.
- One memory trick: every problem belongs to a small pattern family; name the family before coding.
