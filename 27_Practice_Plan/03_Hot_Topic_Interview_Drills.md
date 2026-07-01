# Hot Topic Interview Drills

> Goal: turn the hardest MAANG topics into repeatable interview reflexes.

Use this after reading the topic notes. These drills are intentionally active: speak, write state, code, test, and revise.

---

## 1. Daily Drill Format

Use this 45-minute loop for any hot topic problem.

| Minute | Action |
|---:|---|
| 0-3 | Clarify input, output, constraints, edge cases. |
| 3-7 | Identify pattern and explain brute force. |
| 7-12 | Write state/invariant/algorithm choice before code. |
| 12-30 | Code clean solution. |
| 30-38 | Dry run on normal and edge cases. |
| 38-42 | State time and space complexity. |
| 42-45 | Write one mistake/prevention note. |

Do not start coding before writing the state or invariant.

---

## 2. Recursion and Backtracking Drills

### Must Be Automatic

- Define recursive state in one sentence.
- Identify base case before loop.
- Pair every mutation with an undo.
- Explain duplicate skipping after sorting.
- Estimate complexity from decision-tree leaves.

### Drill Ladder

| Level | Problems | Target Skill |
|---|---|---|
| Foundation | Subsets, Permutations, Letter Combinations | Recursion tree and undo discipline |
| Core | Combination Sum I/II, Generate Parentheses, Palindrome Partitioning | Constraint-driven generation |
| Advanced | N-Queens, Word Search, Partition to K Equal Sum Subsets | Pruning and visited state |
| Pro | Sudoku Solver, Expression Add Operators, Word Search II | Constraint propagation and heavy pruning |

### Pre-Code Script

Say:

> "This is a decision tree. My state is [...]. My choices are [...]. I record an answer when [...]. I prune when [...]. I mutate [...], recurse, then undo [...]."

### Red Flag Checklist

- [ ] Did I copy the path before storing it?
- [ ] Did I undo every mutation?
- [ ] Did I sort before duplicate skipping?
- [ ] Did I skip duplicates only at the same depth?
- [ ] Did I prune impossible branches early?

---

## 3. Trees and BST Drills

### Must Be Automatic

- Choose preorder, inorder, postorder, or BFS quickly.
- Separate global answer update from returned value.
- Explain O(h) tree complexity, not blindly O(log n).
- Validate BST using min/max bounds.
- Handle BST delete cases: 0, 1, or 2 children.

### Drill Ladder

| Level | Problems | Target Skill |
|---|---|---|
| Foundation | Max Depth, Invert Tree, Same Tree, Symmetric Tree | Basic recursion |
| Core | Level Order, Right Side View, Kth Smallest, Validate BST | Traversal choice |
| Advanced | Diameter, LCA, Path Sum III, Count Good Nodes | Return-state design |
| Pro | Max Path Sum, Binary Tree Cameras, Largest BST Subtree, Recover BST | Tree DP and tricky state |

### Pre-Code Script

Say:

> "The parent needs each subtree to return [...]. At the current node I combine left and right by [...]. If the answer can pass through both children, I update the global answer here but return only what the parent can extend."

### Red Flag Checklist

- [ ] Does null return the correct base value?
- [ ] Is leaf logic separate from null logic?
- [ ] Does the returned value mean the same thing at every node?
- [ ] If BST, did I use bounds or inorder property correctly?
- [ ] If BFS, did I freeze `queue.size()` per level?

---

## 4. Graph and DSU Drills

### Must Be Automatic

- Classify graph: directed/undirected, weighted/unweighted, cyclic/DAG.
- Choose BFS, DFS, Dijkstra, 0-1 BFS, topo sort, or DSU.
- Define visited state correctly.
- Build adjacency list without reversing edge direction.
- Explain why Dijkstra requires non-negative weights.

### Algorithm Selection Drill

For 10 random graph statements, write only this:

```text
Graph type:
Goal:
Algorithm:
Visited state:
Complexity:
One failure mode:
```

Do not code during this drill. This trains recognition speed.

### Drill Ladder

| Level | Problems | Target Skill |
|---|---|---|
| Foundation | Number of Islands, Flood Fill, Clone Graph, Find Path Exists | DFS/BFS basics |
| Core | Course Schedule, Bipartite Graph, Pacific Atlantic, Rotting Oranges | Graph states and topo/BFS |
| Advanced | Network Delay, Path With Minimum Effort, 0-1 BFS problems | Shortest path selection |
| Pro | Alien Dictionary, Word Ladder II, Critical Connections, SCCs | Dependency and low-link reasoning |
| DSU | Provinces, Redundant Connection, Accounts Merge, Kruskal MST | Connectivity modeling |

### Pre-Code Script

Say:

> "This graph is [type]. The goal is [connectivity/shortest path/order]. Because [reason], I will use [algorithm]. My visited state is [state], because reaching the same node with different [resource] changes/does not change future choices."

### Red Flag Checklist

- [ ] Did I include isolated nodes?
- [ ] Is the edge direction correct?
- [ ] Is visited state too small?
- [ ] For weighted graph, are weights non-negative?
- [ ] For topo sort, do I detect cycles by processed count?
- [ ] For DSU, did I define what each DSU node represents?

---

## 5. Dynamic Programming Drills

### Must Be Automatic

- Write brute force recurrence before DP.
- Define `dp[...]` precisely.
- Identify base cases and answer cell.
- Choose fill order from dependencies.
- Know when 1D optimization is safe.

### Recurrence Drill

For each DP problem, write this before code:

```text
Decision:
State:
Meaning of dp:
Transition:
Base cases:
Answer:
Fill order:
Time / space:
```

### Drill Ladder

| Level | Problems | Target Skill |
|---|---|---|
| Foundation | Climbing Stairs, Min Cost Climbing Stairs, House Robber | 1D recurrence |
| Core | Coin Change, Word Break, Partition Equal Subset Sum | Choices and feasibility |
| Advanced | LCS, Edit Distance, Unique Paths II, Maximal Square | 2D state |
| Pro | Burst Balloons, Regex Matching, Cherry Pickup, Shortest Superstring | Interval/bitmask/hard states |

### Pre-Code Script

Say:

> "`dp[...]` means [...]. The last decision is [...]. If I take it, I transition from [...]. If I skip it, I transition from [...]. The base case is [...]. I fill in this order because [...]."

### Red Flag Checklist

- [ ] Does `dp` meaning include all constraints?
- [ ] Did I choose min/max/count/boolean correctly?
- [ ] Did I initialize impossible states?
- [ ] Is loop direction correct for 0/1 vs unbounded?
- [ ] Did I return the right cell?
- [ ] Did I optimize space only after correctness?

---

## 6. Weekly Hot Topic Schedule

| Day | Focus | Output |
|---|---|---|
| Monday | Recursion/backtracking | 2 medium + 1 state-tree drawing |
| Tuesday | Trees/BST | 2 medium + 1 postorder return-state explanation |
| Wednesday | Graph BFS/DFS/topo | 2 medium + 10 recognition drills |
| Thursday | DSU/shortest path | 2 medium + algorithm selector notes |
| Friday | DP | 2 medium + recurrence sheet for each |
| Saturday | Mixed timed set | 3 problems in 90 minutes |
| Sunday | Revision | Re-solve 3 failed problems and update mistake journal |

---

## 7. Promotion Criteria

Move from beginner to intermediate when:
- [ ] You can identify the pattern in under 90 seconds.
- [ ] You can explain brute force before optimized solution.
- [ ] You can finish most medium problems in 25-35 minutes.

Move from intermediate to pro when:
- [ ] You can derive state/recurrence without hints.
- [ ] You can explain correctness while coding.
- [ ] You can handle edge cases without interviewer prompts.
- [ ] You can compare two valid approaches and choose one.
- [ ] You can re-solve old mistakes in half the original time.

---

## 8. Mistake Journal Fields

Use these fields for every missed hot-topic problem:

```text
Problem:
Topic:
Pattern:
What I first thought:
Why that failed:
Correct state/invariant:
Bug I made:
Edge case missed:
One-sentence takeaway:
Revisit dates:
```

---

**Next ->** `02_PDF_Crosscheck_Missing_Problems.md`
