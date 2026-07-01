# Section 12 — Recursion & Backtracking

---

## 1. What Problem Does This Solve?

**Recursion** solves problems that can be broken into smaller, identical subproblems. **Backtracking** extends recursion to explore a decision tree — trying candidates, detecting when a path is invalid, and undoing ("backtracking") the choice to try the next one.

Problems solved:
- All combinations, permutations, and subsets of a set
- N-Queens, Sudoku, and constraint-satisfaction problems
- Maze and path finding (all paths, not just shortest)
- Parsing nested structures (trees, JSON, expressions)
- Generating all valid sequences (parentheses, etc.)

---

## 2. Beginner-Friendly Intuition

**Recursion:** Think of looking up a word in a dictionary that defines it using another word, which is defined using another word... eventually reaching a simple word you know. That base word is the base case.

**Backtracking:** Think of navigating a maze. At each junction, pick a direction. If you hit a dead end, go back to the last junction and try another direction. You systematically explore all paths until you find the exit (or find all exits).

---

## 3. Real-World Analogy

**Recursion — Russian Nesting Dolls (Matryoshka):** To open the whole set, open the outer doll, then the same process applies to the inner doll, until you reach the smallest doll with nothing inside (base case).

**Backtracking — Trying outfit combinations:** Pick a shirt, then pick pants that go with it, then pick shoes. If the shoes clash with the pants, put the shoes back and try different ones. If no shoes work with these pants, put the pants back and try different pants.

---

## 4. Core Concept

### Recursion Anatomy
```
recursiveFn(problem) {
    if (base case) return base result;      ← stop condition
    result = combine(recursiveFn(subproblem1), recursiveFn(subproblem2));
    return result;
}
```

### Backtracking Anatomy
```
backtrack(path, choices) {
    if (path is complete): record answer; return
    for each choice in choices:
        if choice is valid:
            MAKE choice (add to path)
            backtrack(path, remaining_choices)  ← recurse
            UNDO choice (remove from path)      ← backtrack
}
```

**The key:** Every `add` must have a matching `undo`. The call stack restores state automatically for immutable values; you must manually undo for mutable structures (lists, arrays, visited sets).

---

## 5. Pattern Recognition Signals

Use Recursion/Backtracking when:
```
"All combinations of..."
"All permutations of..."
"All subsets of..."
"N-Queens" / "Sudoku solver"
"Generate all valid parentheses"
"Find all paths in a maze/grid"
"Word search in grid"
"Letter combinations of phone number"
"Partition into k subsets"
"Combination Sum" (reuse elements allowed)
```

**Backtracking keyword:** "all" possible answers (not just one), or constraints on each choice.

---

## 6. Step-by-Step Algorithm

### Subsets Template
```
backtrack(index, currentSubset):
    add copy of currentSubset to result
    For i from index to n-1:
        currentSubset.add(nums[i])
        backtrack(i+1, currentSubset)   ← next start is i+1 (no reuse)
        currentSubset.remove(last)       ← undo
```

### Permutations Template
```
backtrack(used[], currentPerm):
    if currentPerm.size() == n:
        add copy to result; return
    For i from 0 to n-1:
        if not used[i]:
            used[i] = true
            currentPerm.add(nums[i])
            backtrack(used, currentPerm)
            currentPerm.remove(last)     ← undo
            used[i] = false              ← undo
```

### Combination Sum Template (reuse allowed)
```
backtrack(index, target, current):
    if target == 0: add copy to result; return
    if target < 0: return
    For i from index to n-1:
        current.add(candidates[i])
        backtrack(i, target - candidates[i], current)  ← i not i+1 (reuse)
        current.remove(last)
```

---

## 7. Dry Run with Example

### Example 1: Subsets of [1, 2, 3]

```
backtrack(0, []):
  record []
  i=0: add 1 → backtrack(1, [1]):
    record [1]
    i=1: add 2 → backtrack(2, [1,2]):
      record [1,2]
      i=2: add 3 → backtrack(3, [1,2,3]):
        record [1,2,3]
        no more choices → return
      remove 3 → [1,2]
      no more i → return
    remove 2 → [1]
    i=2: add 3 → backtrack(3, [1,3]):
      record [1,3]; return
    remove 3 → [1]
    return
  remove 1 → []
  i=1: add 2 → backtrack(2, [2]):
    record [2]
    i=2: add 3 → backtrack(3, [2,3]):
      record [2,3]; return
    remove 3 → [2]; return
  remove 2 → []
  i=2: add 3 → backtrack(3, [3]):
    record [3]; return
  remove 3 → []

Result: [[], [1], [1,2], [1,2,3], [1,3], [2], [2,3], [3]] ✓ (8 = 2³ subsets)
```

### Example 2: Permutations of [1, 2, 3]

```
Call tree (abbreviated):
backtrack([], used=[F,F,F]):
  use 1: backtrack([1], [T,F,F]):
    use 2: backtrack([1,2], [T,T,F]):
      use 3: record [1,2,3]; return
    use 3: backtrack([1,3], [T,F,T]):
      use 2: record [1,3,2]; return
  use 2: backtrack([2], [F,T,F]):
    use 1: ... [2,1,3]
    use 3: ... [2,3,1]
  use 3: ... [3,1,2], [3,2,1]

Result: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]] ✓ (6 = 3! perms)
```

---

## 8. Code Implementation

### Subsets

```java
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrackSubsets(nums, 0, new ArrayList<>(), result);
    return result;
}

void backtrackSubsets(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
    result.add(new ArrayList<>(current)); // record at every node
    for (int i = start; i < nums.length; i++) {
        current.add(nums[i]);             // choose
        backtrackSubsets(nums, i + 1, current, result);
        current.remove(current.size() - 1); // unchoose
    }
}
```

### Permutations

```java
List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrackPerms(nums, new boolean[nums.length], new ArrayList<>(), result);
    return result;
}

void backtrackPerms(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current)); return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        current.add(nums[i]);
        backtrackPerms(nums, used, current, result);
        current.remove(current.size() - 1);
        used[i] = false;
    }
}
```

### Combination Sum (reuse elements)

```java
List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
    backtrackCombSum(candidates, 0, target, new ArrayList<>(), result);
    return result;
}

void backtrackCombSum(int[] cands, int start, int target, List<Integer> current, List<List<Integer>> result) {
    if (target == 0) { result.add(new ArrayList<>(current)); return; }
    for (int i = start; i < cands.length; i++) {
        if (cands[i] > target) break; // sorted, so no point continuing
        current.add(cands[i]);
        backtrackCombSum(cands, i, target - cands[i], current, result); // i not i+1: allow reuse
        current.remove(current.size() - 1);
    }
}
```

### Generate Valid Parentheses

```java
List<String> generateParenthesis(int n) {
    List<String> result = new ArrayList<>();
    backtrackParens(n, 0, 0, new StringBuilder(), result);
    return result;
}

void backtrackParens(int n, int open, int close, StringBuilder current, List<String> result) {
    if (current.length() == 2 * n) { result.add(current.toString()); return; }
    if (open < n) {
        current.append('(');
        backtrackParens(n, open + 1, close, current, result);
        current.deleteCharAt(current.length() - 1); // undo
    }
    if (close < open) { // can only close if there's an open to match
        current.append(')');
        backtrackParens(n, open, close + 1, current, result);
        current.deleteCharAt(current.length() - 1); // undo
    }
}
```

### Word Search in Grid

```java
boolean exist(char[][] board, String word) {
    int m = board.length, n = board[0].length;
    for (int r = 0; r < m; r++)
        for (int c = 0; c < n; c++)
            if (dfs(board, word, r, c, 0)) return true;
    return false;
}

boolean dfs(char[][] board, String word, int r, int c, int idx) {
    if (idx == word.length()) return true;
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return false;
    if (board[r][c] != word.charAt(idx)) return false;
    char temp = board[r][c];
    board[r][c] = '#'; // mark visited (in-place, no extra array)
    boolean found = dfs(board, word, r+1, c, idx+1) || dfs(board, word, r-1, c, idx+1)
                 || dfs(board, word, r, c+1, idx+1) || dfs(board, word, r, c-1, idx+1);
    board[r][c] = temp; // restore (backtrack)
    return found;
}
```

### N-Queens

```java
List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    char[][] board = new char[n][n];
    for (char[] row : board) Arrays.fill(row, '.');
    placeQueens(board, 0, new HashSet<>(), new HashSet<>(), new HashSet<>(), result);
    return result;
}

void placeQueens(char[][] board, int row, Set<Integer> cols, Set<Integer> diag, Set<Integer> antiDiag, List<List<String>> result) {
    if (row == board.length) {
        List<String> solution = new ArrayList<>();
        for (char[] r : board) solution.add(new String(r));
        result.add(solution); return;
    }
    for (int col = 0; col < board.length; col++) {
        if (cols.contains(col) || diag.contains(row - col) || antiDiag.contains(row + col)) continue;
        board[row][col] = 'Q';
        cols.add(col); diag.add(row - col); antiDiag.add(row + col);
        placeQueens(board, row + 1, cols, diag, antiDiag, result);
        board[row][col] = '.';
        cols.remove(col); diag.remove(row - col); antiDiag.remove(row + col);
    }
}
```

---

## 9. Time Complexity

| Problem | Complexity | Reason |
|---------|-----------|--------|
| Subsets | O(n × 2ⁿ) | 2ⁿ subsets, O(n) to copy each |
| Permutations | O(n × n!) | n! perms, O(n) to copy each |
| Combination Sum | O(n^(T/min)) | T=target, branching factor n |
| Valid Parentheses | O(4ⁿ / √n) | Catalan number |
| N-Queens | O(n!) | At each row, ~n choices |
| Word Search | O(m×n×4^L) | L=word length, 4 directions |

---

## 10. Space Complexity

| Problem | Space | Reason |
|---------|-------|--------|
| All backtracking | O(n) call stack | Recursion depth ≤ n |
| Subsets/Perms | O(n) current path | Path at any point ≤ n elements |
| N-Queens | O(n) board + 3 sets | One queen per row |
| Word Search | O(L) | Recursion depth = word length |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Empty input | Return [[]] for subsets, [] for permutations |
| Single element | Works — one subset containing it, one permutation |
| Duplicates in input | Sort + skip `if i > start && nums[i] == nums[i-1]` |
| Target = 0 in Combination Sum | Record immediately, return |
| No valid placement (N-Queens n=2,3) | n=2 and n=3 have no solutions; return [] |
| Grid cell visited mid-DFS | Use in-place marking (board[r][c]='#') |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Not copying the current list before adding to results
result.add(current);              // WRONG: adds reference; future changes corrupt it
result.add(new ArrayList<>(current)); // CORRECT: snapshot copy

// MISTAKE 2: Forgetting to undo the choice (the "backtrack" step)
current.add(nums[i]);
backtrack(i + 1, current, result);
// MISSING: current.remove(current.size() - 1); // BUG: list grows forever

// MISTAKE 3: Wrong start index for Combination Sum (reuse allowed)
backtrack(i + 1, ...); // WRONG: skips reusing nums[i]
backtrack(i, ...);     // CORRECT: allow reusing same element

// MISTAKE 4: Not handling duplicates in Subsets II / Combination Sum II
// Sort first, then skip:
if (i > start && nums[i] == nums[i-1]) continue;

// MISTAKE 5: Forgetting to restore board cell in Word Search
board[r][c] = '#';       // mark
dfs(board, word, ...);   // recurse
board[r][c] = temp;      // MUST restore to allow other paths through this cell
```

---

## 13. Interview-Level Explanation

**Q: "What's the difference between Combination Sum I and II?"**

> "Combination Sum I allows reusing the same element unlimited times — so when recursing, I pass `i` (not `i+1`) as the start. Combination Sum II allows each element to be used only once but the input may have duplicates — so I pass `i+1` and add a duplicate-skipping check: if `i > start && candidates[i] == candidates[i-1]`, skip. The sort + skip ensures we don't produce duplicate result combinations."

**Q: "How do you know when to use backtracking vs. DP?"**

> "If the problem asks for ALL solutions (or COUNT of all solutions), backtracking explores the entire search tree. If it asks for OPTIMAL (max/min) or just the COUNT of ways, and subproblems overlap, DP is more efficient. Backtracking is exponential but produces every answer; DP is polynomial but only computes the aggregate."

---

## 14. Real-World Use Cases

| Application | Backtracking Usage |
|------------|------------------|
| **Chess engines** | Minimax with pruning (Alpha-Beta) |
| **Sudoku solvers** | Constraint satisfaction |
| **Regex matching** | Backtrack on failed matches |
| **Route planning** | Find all routes with constraints |
| **Test generation** | Generate all valid input combinations |
| **Cryptography** | Key space exploration |
| **Scheduling** | Assign tasks to workers with constraints |

---

## 15. Variations of This Pattern

| Variation | Key Difference | Example |
|-----------|---------------|---------|
| Subsets I | No duplicates | Subsets |
| Subsets II | With duplicates | Subsets II |
| Combination Sum I | Reuse allowed | Combination Sum |
| Combination Sum II | No reuse, duplicates | Combination Sum II |
| Permutations I | No duplicates | Permutations |
| Permutations II | With duplicates | Permutations II |
| Valid parentheses | Constraint on choice | Generate Parentheses |
| N-Queens | Hard constraint | N-Queens |
| Word Search | 2D grid DFS | Word Search |
| Palindrome partition | Partition constraint | Palindrome Partitioning |

---

## 16. Practice Problems

### Easy — Core Backtracking
1. **Letter Combinations of Phone Number** (LeetCode #17)
   - *Task:* All letter combinations from digit mapping.
   - *Hint:* At each digit, loop over its mapped letters and recurse.

2. **Generate Parentheses** (LeetCode #22)
   - *Task:* All valid combinations of n pairs of parentheses.
   - *Hint:* Add `(` when open < n; add `)` when close < open.

3. **Subsets** (LeetCode #78)
   - *Task:* All subsets of a distinct integer array.
   - *Hint:* Record at every node of the call tree, not just leaves.

### Medium — Classic Backtracking
1. **Permutations** (LeetCode #46)
   - *Task:* All permutations of distinct integers.
   - *Hint:* `used[]` boolean array to track which elements are in current path.

2. **Combination Sum** (LeetCode #39)
   - *Task:* All combos summing to target (reuse allowed).
   - *Hint:* Pass `i` (not `i+1`) to allow reuse. Sort to enable pruning.

3. **Subsets II** (LeetCode #90)
   - *Task:* Subsets with possible duplicates.
   - *Hint:* Sort + `if (i > start && nums[i] == nums[i-1]) continue`.

4. **Word Search** (LeetCode #79)
   - *Task:* Check if word exists in grid.
   - *Hint:* DFS with in-place marking (`'#'`) and restoration.

5. **Palindrome Partitioning** (LeetCode #131)
   - *Task:* All ways to partition string so every part is a palindrome.
   - *Hint:* Try all prefix lengths; only recurse if prefix is a palindrome.

### Hard — Complex Backtracking
1. **N-Queens** (LeetCode #51)
   - *Task:* All valid N-Queens configurations.
   - *Hint:* Track cols, diagonals (row-col), anti-diagonals (row+col) in sets.

2. **Sudoku Solver** (LeetCode #37)
   - *Task:* Fill a 9×9 Sudoku board.
   - *Hint:* For each empty cell, try 1-9, check row/col/box constraints, recurse.

3. **Expression Add Operators** (LeetCode #282)
   - *Task:* Add +, -, * between digits to reach target.
   - *Hint:* Backtrack over positions, track running value and last multiplied value.

---

## 17. How to Know You Have Mastered Recursion & Backtracking

You have mastered this topic when you can:
- [ ] Write the generic backtracking template from memory (make choice, recurse, undo)
- [ ] Implement Subsets, Permutations, and Combination Sum without bugs
- [ ] Always use `new ArrayList<>(current)` when recording solutions
- [ ] Handle duplicates with sort + skip pattern
- [ ] Implement N-Queens including diagonal tracking with sets
- [ ] Identify when backtracking can be pruned (early `break` or `continue`)
- [ ] Distinguish Backtracking vs DP problem types
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. How many subsets does `[1, 2, 3, 4]` have? What formula gives this?

2. You want all combinations of size k from n elements. How is this different from all subsets?

3. In Word Search, you mark `board[r][c] = '#'` before recursing and restore after. What happens if you forget to restore?

4. Why do we need to sort the input array before skipping duplicates in Subsets II?

5. In N-Queens, we track three sets: columns, diagonals (row-col), and anti-diagonals (row+col). Why does `row-col` uniquely identify a diagonal?

6. What is the branching factor of the Permutations recursion tree at depth d (where d is the length of the current path)?

7. Backtracking for Combination Sum has `if (candidates[i] > target) break`. Why `break` instead of `continue`?

8. Generating all valid parentheses of length 2n — the count is the nth Catalan number. What is C(3)?

> **Answers:**
> 1. 16 = 2⁴. Each element is either included or excluded: 2^n subsets.
> 2. Stop recursing when current size reaches k (add only to results at that level). Add `if (current.size() == k) { record; return; }` before the loop.
> 3. Other paths through the same cell are blocked — `board[r][c]` is `'#'` and won't match. The algorithm incorrectly marks that cell as "already used" for all subsequent paths.
> 4. Without sorting, `[1,2,1]` has `nums[0]=1` and `nums[2]=1`. The duplicate check `nums[i] == nums[i-1]` only works if same values are adjacent — sorting guarantees this.
> 5. All cells on the same diagonal from top-left to bottom-right have the same value of `row-col`. E.g., (0,0)→0, (1,1)→0, (2,2)→0.
> 6. At depth d, the used[] has d elements marked true, so branching factor = n - d. Total: n × (n-1) × ... × 1 = n!.
> 7. Because the array is sorted. If `candidates[i] > target`, all subsequent candidates are also > target. `break` exits the loop entirely; `continue` would pointlessly try larger values.
> 8. C(3) = 5. The five valid parenthesizations of 3 pairs: `((()))`, `(()())`, `(())()`, `()(())`, `()()()`.

---

**Next →** `../13_Trees/01_Tree_Algorithms.md`

### 2. Beginner-Friendly Intuition

Russian nesting dolls (Matryoshka): Each doll contains a smaller one. To get to the smallest, you open each one (recurse). You close them on the way back up (return).

### 3. Anatomy of Recursion

```java
returnType solve(parameters) {
    // 1. BASE CASE — stop condition
    if (base condition) return base result;

    // 2. RECURSIVE CASE — smaller subproblem
    return solve(smaller parameters);
}
```

### 4. The Call Stack

```java
factorial(4)
  → factorial(3)
      → factorial(2)
          → factorial(1) = 1
        returns 2×1 = 2
    returns 3×2 = 6
  returns 4×6 = 24
```

**Memory:** Each call occupies stack frame. Deep recursion → StackOverflowError.

### 5. Recursion Tree

Fibonacci(4):
```
              fib(4)
            /        \
        fib(3)       fib(2)
       /     \       /    \
   fib(2)  fib(1) fib(1) fib(0)
   /    \
fib(1) fib(0)
```
This shows the **overlapping subproblems** → solve with memoization (DP).

---

## Part 2: Backtracking

### 1. What Problem Does This Solve?

Problems requiring **exploring all possibilities** while **pruning** invalid paths early.

### 2. Beginner-Friendly Intuition

Maze solving: Move forward. If you hit a dead end, go back (backtrack) and try another direction.

### 3. The Choose-Explore-Unchoose Pattern

```java
void backtrack(result, current, options) {
    if (base case) {
        result.add(copy of current);
        return;
    }
    for (option in options) {
        // CHOOSE
        current.add(option);
        // EXPLORE
        backtrack(result, current, remaining options);
        // UNCHOOSE (backtrack!)
        current.remove(option);
    }
}
```

---

### Pattern 1: Subsets (Power Set)

**All subsets of [1,2,3]:**

```java
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, 0);
    return result;
}

void backtrack(List<List<Integer>> result, List<Integer> curr, int[] nums, int start) {
    result.add(new ArrayList<>(curr));  // add current subset at every level
    for (int i = start; i < nums.length; i++) {
        curr.add(nums[i]);
        backtrack(result, curr, nums, i + 1);  // i+1: no reuse
        curr.remove(curr.size() - 1);          // unchoose
    }
}
```

**Recursion Tree for [1,2,3]:**
```
[]
├── [1]
│   ├── [1,2]
│   │   └── [1,2,3]
│   └── [1,3]
├── [2]
│   └── [2,3]
└── [3]
```

---

### Pattern 2: Permutations

```java
List<List<Integer>> permutations(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums, new boolean[nums.length]);
    return result;
}

void backtrack(List<List<Integer>> result, List<Integer> curr, int[] nums, boolean[] used) {
    if (curr.size() == nums.length) {
        result.add(new ArrayList<>(curr));
        return;
    }
    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        curr.add(nums[i]);
        backtrack(result, curr, nums, used);
        curr.remove(curr.size() - 1);
        used[i] = false;
    }
}
```

---

### Pattern 3: Combination Sum (with repetition allowed)

```java
List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), candidates, target, 0);
    return result;
}

void backtrack(List<List<Integer>> result, List<Integer> curr,
               int[] candidates, int remaining, int start) {
    if (remaining == 0) { result.add(new ArrayList<>(curr)); return; }
    if (remaining < 0) return;  // pruning!

    for (int i = start; i < candidates.length; i++) {
        curr.add(candidates[i]);
        backtrack(result, curr, candidates, remaining - candidates[i], i); // i, not i+1 (allow reuse)
        curr.remove(curr.size() - 1);
    }
}
```

---

### Pattern 4: N-Queens

**Place N queens on N×N board so no two attack each other.**

```java
List<List<String>> solveNQueens(int n) {
    List<List<String>> result = new ArrayList<>();
    char[][] board = new char[n][n];
    for (char[] row : board) Arrays.fill(row, '.');
    backtrack(result, board, 0, n);
    return result;
}

void backtrack(List<List<String>> result, char[][] board, int row, int n) {
    if (row == n) {
        List<String> sol = new ArrayList<>();
        for (char[] r : board) sol.add(new String(r));
        result.add(sol);
        return;
    }
    for (int col = 0; col < n; col++) {
        if (isValid(board, row, col, n)) {
            board[row][col] = 'Q';
            backtrack(result, board, row + 1, n);
            board[row][col] = '.';
        }
    }
}

boolean isValid(char[][] board, int row, int col, int n) {
    for (int i = 0; i < row; i++) if (board[i][col] == 'Q') return false;
    for (int i=row-1, j=col-1; i>=0 && j>=0; i--,j--) if(board[i][j]=='Q') return false;
    for (int i=row-1, j=col+1; i>=0 && j<n; i--,j++) if(board[i][j]=='Q') return false;
    return true;
}
```

---

### Pattern 5: Word Search in Grid

```java
boolean exist(char[][] board, String word) {
    int m = board.length, n = board[0].length;
    for (int r = 0; r < m; r++)
        for (int c = 0; c < n; c++)
            if (dfs(board, word, r, c, 0)) return true;
    return false;
}

boolean dfs(char[][] board, String word, int r, int c, int idx) {
    if (idx == word.length()) return true;
    if (r < 0 || r >= board.length || c < 0 || c >= board[0].length) return false;
    if (board[r][c] != word.charAt(idx)) return false;

    char temp = board[r][c];
    board[r][c] = '#';  // mark visited
    boolean found = dfs(board, word, r+1, c, idx+1)
                 || dfs(board, word, r-1, c, idx+1)
                 || dfs(board, word, r, c+1, idx+1)
                 || dfs(board, word, r, c-1, idx+1);
    board[r][c] = temp;  // restore (backtrack)
    return found;
}
```

---

### Pattern 6: Palindrome Partitioning

```java
List<List<String>> partition(String s) {
    List<List<String>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), s, 0);
    return result;
}

void backtrack(List<List<String>> result, List<String> curr, String s, int start) {
    if (start == s.length()) { result.add(new ArrayList<>(curr)); return; }
    for (int end = start + 1; end <= s.length(); end++) {
        String sub = s.substring(start, end);
        if (isPalin(sub)) {
            curr.add(sub);
            backtrack(result, curr, s, end);
            curr.remove(curr.size() - 1);
        }
    }
}
```

---

### Pattern 7: Generate Parentheses

```java
List<String> generateParentheses(int n) {
    List<String> result = new ArrayList<>();
    backtrack(result, new StringBuilder(), 0, 0, n);
    return result;
}

void backtrack(List<String> result, StringBuilder curr, int open, int close, int n) {
    if (curr.length() == 2 * n) { result.add(curr.toString()); return; }
    if (open < n) {
        curr.append('(');
        backtrack(result, curr, open + 1, close, n);
        curr.deleteCharAt(curr.length() - 1);
    }
    if (close < open) {
        curr.append(')');
        backtrack(result, curr, open, close + 1, n);
        curr.deleteCharAt(curr.length() - 1);
    }
}
```

---

### When to Use Backtracking vs DP

| Situation | Use |
|-----------|-----|
| Need ALL solutions | Backtracking |
| Need count of solutions | DP (often faster) |
| Need one optimal solution | DP or Greedy |
| Solution space is a tree | Backtracking |
| Overlapping subproblems | DP |

---

## MAANG Pro Upgrade: Backtracking That Does Not TLE

### The 6-Part Interview Checklist

Before coding any backtracking solution, say these out loud:

1. **State:** What variables define one node in the decision tree?
2. **Choices:** What candidates can I try from this state?
3. **Validity:** Which choices are illegal immediately?
4. **Goal:** When is one complete answer formed?
5. **Undo:** What mutable state must be restored after recursion?
6. **Pruning:** What branch can never lead to a valid/better answer?

If you cannot answer all six, the code will usually become messy.

### State Design Patterns

| Problem Family | State to Carry | Why |
|---|---|---|
| Subsets / combinations | `start`, `path` | Prevents reusing earlier elements. |
| Permutations | `used[]`, `path` | Tracks which elements are already placed. |
| Combination Sum | `start`, `remaining`, `path` | Supports target pruning and optional reuse. |
| Parentheses | `open`, `close`, `StringBuilder` | Enforces validity while generating. |
| Grid word search | `r`, `c`, `index`, visited marks | Position plus matched prefix length. |
| N-Queens | `row`, used columns/diagonals | Place one queen per row. |
| Sudoku | `cell index`, row/col/box masks | Fast constraint lookup. |
| K equal subsets | bucket sums / used mask | Tracks load balance and used elements. |

### Pruning Rules That Matter

| Situation | Prune |
|---|---|
| Sorted candidates and `candidate > remaining` | `break`, because later values are larger. |
| Duplicate candidates at same recursion depth | Skip `i > start && nums[i] == nums[i-1]`. |
| Current partial answer already violates constraint | Return immediately. |
| Remaining choices cannot fill required length | Return early. |
| Bucket/backpack problem has symmetric empty buckets | Try one empty bucket, then break. |
| Best-known answer exists and current cost already worse | Branch-and-bound return. |

### Duplicate Handling Template

Use this for `Subsets II`, `Combination Sum II`, and similar problems:

```java
Arrays.sort(nums);

void backtrack(int start, List<Integer> path) {
    result.add(new ArrayList<>(path));
    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue; // skip duplicate at same depth
        path.add(nums[i]);
        backtrack(i + 1, path);
        path.remove(path.size() - 1);
    }
}
```

**Key distinction:** skip duplicates at the same depth, not globally. The same value can still be used in deeper levels when it represents a different position in the input.

### Complexity Mental Model

| Pattern | Rough Number of Leaves | Time |
|---|---:|---|
| Subsets | `2^n` | O(n * 2^n) if copying each subset |
| Permutations | `n!` | O(n * n!) |
| Combinations choose k | `C(n, k)` | O(k * C(n, k)) |
| Phone digits | `3^a * 4^b` | Branching depends on digits |
| N-Queens | Much less than `n!` after pruning | Often described as O(n!) upper bound |
| Word Search | `m*n*4*3^(L-1)` | First step has 4 dirs, later avoid going back |

### Interview Explanation Template

> "I will model this as a decision tree. Each recursive call represents a partial answer. At each level I try valid candidates, mutate the path, recurse, and undo the mutation. I prune branches that violate constraints early. The time is proportional to the number of states explored times the cost to copy/build each answer."

### Common Pro Mistakes

| Mistake | Why It Hurts | Fix |
|---|---|---|
| Forgetting to copy `path` | All answers point to same mutable list | Use `new ArrayList<>(path)`. |
| Using global visited but not undoing | Later branches are blocked incorrectly | Always pair mark/unmark. |
| Skipping duplicates without sorting | Equal values are not adjacent | Sort first. |
| Skipping all duplicate values globally | Removes valid answers | Skip only at same depth. |
| Generating invalid states then filtering | Explodes search space | Enforce validity while building. |
| Using string concatenation in deep recursion | Extra O(n) per call | Use `StringBuilder` and undo. |

---

## Practice Problems

**Easy:**
1. Subsets.
2. Letter Combinations of Phone Number.
3. Generate Parentheses.

**Medium:**
1. Permutations I & II (with duplicates).
2. Combination Sum I & II.
3. Palindrome Partitioning.
4. Subsets II (with duplicates).
5. Word Search.

**Hard:**
1. N-Queens.
2. Sudoku Solver.
3. Expression Add Operators.

---

**Next →** `../13_Trees/01_Tree_Algorithms.md`
