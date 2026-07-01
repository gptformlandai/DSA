# Section 2.4 — Stack

---

## 1. What Problem Does This Solve?

A stack solves problems where you need **Last-In First-Out (LIFO)** access — the most recently added item must be processed first. Classic applications: undo/redo systems, function call tracking (call stack), expression evaluation, and matching brackets.

---

## 2. Beginner-Friendly Intuition

A stack is like a pile of plates. You add plates to the top (push) and remove from the top (pop). You can never take a plate from the middle without removing those above it.

The key insight for algorithm problems: **anything that requires processing in reverse order of arrival, or matching most-recent with current, uses a stack.**

---

## 3. Real-World Analogy

**Browser back button:** Each page you visit is pushed onto a stack. Clicking "back" pops the most recent page. The history is a stack.

**Undo in text editor:** Each edit is pushed. Ctrl+Z pops the last edit and reverts it.

**Call stack:** When function A calls B calls C, C is at the top. C returns first, then B, then A — LIFO.

---

## 4. Core Concept

### Operations
| Operation | Description | Time |
|-----------|-------------|------|
| push(x) | Add x to top | O(1) |
| pop() | Remove and return top | O(1) |
| peek() / top() | Return top without removing | O(1) |
| isEmpty() | Check if stack is empty | O(1) |
| size() | Number of elements | O(1) |

### Java Stack Implementations
```java
// Option 1: Deque as Stack (preferred in Java)
Deque<Integer> stack = new ArrayDeque<>();
stack.push(1);     // add to front (LIFO)
stack.pop();       // remove from front
stack.peek();      // view front

// Option 2: Legacy Stack class (avoid — synchronized, slow)
Stack<Integer> stack = new Stack<>(); // NOT recommended

// Option 3: ArrayList as stack (manual)
List<Integer> stack = new ArrayList<>();
stack.add(x);                          // push
stack.remove(stack.size() - 1);        // pop
stack.get(stack.size() - 1);           // peek
```

### Monotonic Stack Pattern
```
For each element, while stack top violates the monotonic property:
    pop and process
push current element

Monotonic increasing: stack top is always smallest
Monotonic decreasing: stack top is always largest
```

---

## 5. Pattern Recognition Signals

```
"Valid parentheses / bracket matching" → Stack (push open, pop on close)
"Next greater element" → Monotonic decreasing stack
"Next smaller element" → Monotonic increasing stack
"Largest rectangle in histogram" → Monotonic stack
"Evaluate expression" → Two stacks (values + operators)
"Decode string" → Stack
"Simplify directory path" → Stack
"Daily temperatures" → Monotonic decreasing stack
```

---

## 6. Step-by-Step Algorithm

### Valid Parentheses
```
For each char c:
    if c is opening ('(','[','{'): push c
    if c is closing:
        if stack empty or top doesn't match: return false
        pop top
return stack.isEmpty()
```

### Next Greater Element
```
Initialize result[] = -1 for all
For each i from 0 to n-1:
    While stack not empty AND arr[stack.top()] < arr[i]:
        idx = stack.pop()
        result[idx] = arr[i]   // arr[i] is the next greater for arr[idx]
    Push i onto stack
// All remaining in stack have no greater element → -1
```

---

## 7. Dry Run with Example

### Next Greater Element: [2, 1, 5, 6, 4]
```
stack=[], result=[-1,-1,-1,-1,-1]

i=0, arr[0]=2: stack empty → push 0. stack=[0]
i=1, arr[1]=1: 2 > 1, no pop → push 1. stack=[0,1]
i=2, arr[2]=5: 
  1 < 5 → pop 1, result[1]=5. stack=[0]
  2 < 5 → pop 0, result[0]=5. stack=[]
  push 2. stack=[2]
i=3, arr[3]=6:
  5 < 6 → pop 2, result[2]=6. stack=[]
  push 3. stack=[3]
i=4, arr[4]=4:
  6 > 4, no pop → push 4. stack=[3,4]

Remaining: indices 3,4 → result stays -1.
result = [5, 5, 6, -1, -1] ✓
```

---

## 8. Code Implementation

```java
import java.util.*;

public class StackAlgorithms {

    // ── Valid Parentheses ──────────────────────────────────────────────────
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c); // push opening bracket
            } else {
                if (stack.isEmpty()) return false; // no matching open
                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == ']' && top != '[') return false;
                if (c == '}' && top != '{') return false;
            }
        }
        return stack.isEmpty(); // all brackets matched
    }

    // ── Next Greater Element ───────────────────────────────────────────────
    public int[] nextGreaterElement(int[] arr) {
        int n = arr.length;
        int[] result = new int[n];
        Arrays.fill(result, -1); // default: no greater element
        Deque<Integer> stack = new ArrayDeque<>(); // stores indices

        for (int i = 0; i < n; i++) {
            // Pop all elements smaller than arr[i]
            while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
                result[stack.pop()] = arr[i];
            }
            stack.push(i); // push current index
        }
        return result;
    }

    // ── Largest Rectangle in Histogram ────────────────────────────────────
    public int largestRectangleArea(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int maxArea = 0, n = heights.length;

        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i]; // sentinel 0 at end
            while (!stack.isEmpty() && heights[stack.peek()] > h) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            stack.push(i);
        }
        return maxArea;
    }

    // ── Daily Temperatures ─────────────────────────────────────────────────
    public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>(); // monotonic decreasing

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int idx = stack.pop();
                result[idx] = i - idx; // days until warmer
            }
            stack.push(i);
        }
        return result; // unprocessed indices remain 0 (no warmer day)
    }

    // ── Decode String ──────────────────────────────────────────────────────
    // "3[a2[bc]]" → "abcbcabcbc"
    public String decodeString(String s) {
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> strStack = new ArrayDeque<>();
        StringBuilder current = new StringBuilder();
        int k = 0;

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0'); // build multi-digit number
            } else if (c == '[') {
                countStack.push(k);
                strStack.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                int times = countStack.pop();
                StringBuilder prev = strStack.pop();
                for (int i = 0; i < times; i++) prev.append(current);
                current = prev;
            } else {
                current.append(c);
            }
        }
        return current.toString();
    }
}
```

---

## 9. Time Complexity

| Algorithm | Time | Notes |
|-----------|------|-------|
| Push / Pop / Peek | O(1) | Constant time |
| Valid Parentheses | O(n) | Each char pushed/popped once |
| Next Greater Element | O(n) | Each index pushed/popped once |
| Largest Rectangle | O(n) | Each bar pushed/popped once |
| Decode String | O(n × max_k) | Repeated string building |

---

## 10. Space Complexity

| Algorithm | Space |
|-----------|-------|
| Stack operations | O(n) worst case |
| Monotonic stack | O(n) worst case (all same) |
| Decode String | O(n) for nested stacks |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Empty string in Valid Parentheses | `stack.isEmpty()` returns true correctly |
| Single bracket `(` | Stack not empty → return false |
| Histogram with all same heights | Every bar pushed once, max width = n |
| Monotonic stack: all elements decreasing | Stack fills with all indices |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Using Stack<> instead of Deque
Stack<Integer> stack = new Stack<>(); // WRONG: synchronized, slow
Deque<Integer> stack = new ArrayDeque<>(); // CORRECT: fast

// MISTAKE 2: Checking stack.top() before checking isEmpty()
if (stack.peek() == '(') // WRONG: NullPointerException if stack is empty
if (!stack.isEmpty() && stack.peek() == '(') // CORRECT

// MISTAKE 3: In Next Greater Element — pushing values instead of indices
stack.push(arr[i]); // WRONG: can't find result index later
stack.push(i);      // CORRECT: store index, access arr[stack.peek()] for value

// MISTAKE 4: Forgetting the sentinel in Largest Rectangle
for (int i = 0; i < n; i++) // WRONG: elements at end of stack never processed
for (int i = 0; i <= n; i++) { int h = (i == n) ? 0 : heights[i]; ... } // CORRECT

// MISTAKE 5: ArrayDeque.push() pushes to FRONT (behaves as LIFO)
// ArrayDeque.offer() / add() pushes to BACK (behaves as FIFO/Queue)
// For stack: always use push()/pop()/peek()
```

---

## 13. Interview-Level Explanation

**Q: "Why is a monotonic stack O(n) and not O(n²)?"**

> "Each element is pushed onto the stack exactly once and popped at most once. Even though there's a while loop inside the for loop, the total number of pop operations across the entire iteration is bounded by n — because there are only n elements to pop. So the total work is O(n) pushes + O(n) pops = O(n). This is amortized analysis."

**Q: "When should you store indices in the stack vs values?"**

> "Store indices when you need to compute distances (like 'days until warmer temperature' requires `i - stack.top()`) or update a result array at specific positions. Store values when you only care about the actual value for comparison and don't need position information."

---

## 14. Real-World Use Cases

| Application | Stack Usage |
|------------|------------|
| **Compiler / Interpreter** | Call stack, expression parsing |
| **Browser navigation** | Back/forward history |
| **Text editor undo** | Operation stack |
| **DFS traversal** | Explicit stack (or recursion call stack) |
| **Stock span problem** | Monotonic stack |
| **CPU function calls** | Hardware stack register |

---

## 15. Variations

| Variation | Technique |
|-----------|----------|
| Min Stack | Stack of (value, currentMin) pairs |
| Max Stack | Same pattern with max |
| Two stacks → Queue | Enqueue to stack1, dequeue via stack2 |
| Stack sorting | Use second stack to sort |
| Monotonic increasing stack | Pop when top ≥ current |
| Monotonic decreasing stack | Pop when top ≤ current |

---

## 16. Practice Problems

### Easy — Foundation
1. **Valid Parentheses** (LeetCode #20)
   - *Task:* Check balanced brackets.
   - *Hint:* Push open brackets, pop on close.

2. **Baseball Game** (LeetCode #682)
   - *Task:* Simulate scoring with operations +, D, C, numbers.
   - *Hint:* Stack of scores.

3. **Min Stack** (LeetCode #155)
   - *Task:* Stack supporting getMin() in O(1).
   - *Hint:* Each entry stores (value, currentMin).

### Medium — Core
1. **Daily Temperatures** (LeetCode #739)
   - *Task:* Days until warmer temperature.
   - *Hint:* Monotonic decreasing stack of indices.

2. **Decode String** (LeetCode #394)
   - *Task:* "3[a2[bc]]" → "abcbcabcbc".
   - *Hint:* Two stacks: count and partial string.

3. **Asteroid Collision** (LeetCode #735)
   - *Task:* Positive = right, negative = left. Simulate collisions.
   - *Hint:* Stack — push positive, handle collisions for negative.

4. **Simplify Path** (LeetCode #71)
   - *Task:* Canonical Unix path.
   - *Hint:* Split by "/", process with stack.

5. **Next Greater Element II** (LeetCode #503)
   - *Task:* Circular array — next greater element.
   - *Hint:* Process array twice (2n) using modulo index.

### Hard — Advanced
1. **Largest Rectangle in Histogram** (LeetCode #84)
   - *Task:* Max rectangle area in histogram.
   - *Hint:* Monotonic increasing stack of indices.

2. **Trapping Rain Water** (LeetCode #42)
   - *Task:* Water trapped between bars.
   - *Hint:* Stack-based: width × height for each valley.

3. **Basic Calculator II** (LeetCode #227)
   - *Task:* Evaluate arithmetic expression with +,-,*,/.
   - *Hint:* Stack for values; handle operator precedence.

---

## 17. How to Know You Have Mastered Stacks

You have mastered this topic when you can:
- [ ] Use `Deque<Integer> stack = new ArrayDeque<>()` (not `Stack<>`)
- [ ] Implement Valid Parentheses correctly, including edge cases
- [ ] Build a monotonic stack for Next Greater Element from scratch
- [ ] Implement Min Stack with O(1) getMin
- [ ] Explain why monotonic stack operations are O(n) amortized
- [ ] Recognize when a problem needs a stack vs a queue
- [ ] Handle the sentinel element trick in Largest Rectangle
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. What is the output of Next Greater Element for [4, 3, 2, 1]?

2. Why is `Stack<Integer>` not recommended in Java? What should you use instead?

3. Valid Parentheses: `"([])"` — trace the algorithm step by step.

4. In Daily Temperatures `[73,74,75,71,69,72,76,73]`, what is result[4]?

5. How does Min Stack achieve O(1) getMin?

6. What is `ArrayDeque.push(x)` equivalent to on the internal array?

7. Monotonic stack processes [5, 4, 3, 2, 1] for Next Greater Element. What does the stack look like at the end?

8. For Largest Rectangle: heights = [2, 1, 2]. What is the largest rectangle area?

> **Answers:**
> 1. [-1, -1, -1, -1] — strictly decreasing array; no element is greater than any element before it.
> 2. `Stack<>` extends `Vector`, which is synchronized on every operation — unnecessary overhead for single-threaded use. Use `Deque<Integer> stack = new ArrayDeque<>()` for unsynchronized, faster stack.
> 3. '(' → push → stack=['(']. '[' → push → stack=['(','[']. ']' → pop '[', match ✓ → stack=['(']. ')' → pop '(', match ✓ → stack=[]. Return stack.isEmpty() = true ✓.
> 4. result[4] = 2 (days until temperature > 69: day 5 has 72 > 69, so 5-4=1... wait: temperatures = [73,74,75,71,69,72,76,73]. Index 4 = 69. Next warmer is index 5 = 72. result[4] = 5-4 = 1).
> 5. Store pairs `(value, currentMin)` on the stack. When pushing x, currentMin = min(x, stack.peek().currentMin). `getMin()` returns `stack.peek().currentMin` — O(1).
> 6. `ArrayDeque.push(x)` adds to the front (head) of the deque. It's equivalent to `addFirst(x)`. `pop()` removes from the front — LIFO behavior.
> 7. Stack = [0,1,2,3,4] (indices). All 5 elements have no greater element — they all remain. Results = [-1,-1,-1,-1,-1].
> 8. 3. The largest rectangle is width=3, height=1 (spanning all three bars using height of smallest bar=1): area=3. Or width=1, height=2 (left or right bar): area=2. Max=3.

---

**Next →** `05_Queue_Deque.md`

Some problems need you to **remember recent history** and process it in reverse order (Last-In-First-Out).  
Examples: undo operations, matching brackets, expression evaluation, backtracking.

---

## 2. Beginner-Friendly Intuition

A stack is like a **pile of plates**:
- You can only add/remove from the **top**.
- Last plate placed is the first one removed.
- LIFO: Last In, First Out.

```
PUSH 10:  [10]
PUSH 20:  [10, 20]
PUSH 30:  [10, 20, 30]  ← top
POP:      [10, 20]       → returns 30
PEEK:     20              → top is 20
```

---

## 3. Real-World Analogy

- **Browser Back Button:** Pages you visit are pushed. Back pops the last one.
- **Undo in text editor:** Each action is pushed. Ctrl+Z pops the last.
- **Call stack in code:** Functions are pushed when called, popped when returned.

---

## 4. Operations & Complexity

| Operation | Time | Space |
|-----------|------|-------|
| push(x) | O(1) | — |
| pop() | O(1) | — |
| peek() / top() | O(1) | — |
| isEmpty() | O(1) | — |
| size() | O(1) | — |
| Overall space | — | O(n) |

---

## 5. Java Implementation

```java
// Built-in Stack (legacy — prefer Deque)
Stack<Integer> stack = new Stack<>();
stack.push(10);
stack.pop();        // removes and returns top
stack.peek();       // returns top without removing
stack.isEmpty();

// Preferred: ArrayDeque as Stack
Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);     // addFirst
stack.pop();        // removeFirst
stack.peek();       // peekFirst
```

---

## 6. Key Pattern: Matching Brackets

**Problem:** Given string of brackets, check if valid.

```
"(())" → valid
"([)]" → invalid
"{[]}" → valid
```

**Algorithm:**
- Push open brackets.
- On close bracket: check if top matches.

```java
boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (c == ')' && top != '(') return false;
            if (c == '}' && top != '{') return false;
            if (c == ']' && top != '[') return false;
        }
    }
    return stack.isEmpty();
}
```

**Dry Run:** `"([])"` → push `(`, push `[`, see `]`→ pop `[` ✓, see `)` → pop `(` ✓ → empty ✓

---

## 7. Monotonic Stack

A stack where elements are always in increasing or decreasing order. Used for "next greater/smaller element" patterns.

```java
// Next Greater Element for each position
int[] nextGreater(int[] arr) {
    int n = arr.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>();  // stores indices

    for (int i = 0; i < n; i++) {
        // Pop elements smaller than current
        while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
            result[stack.pop()] = arr[i];
        }
        stack.push(i);
    }
    return result;
}
```

**Dry Run:** arr = [2, 1, 4, 3]
```
i=0: stack=[0]  (index of 2)
i=1: 1 < 2, just push: stack=[0,1]
i=2: 4 > 1 → pop 1, result[1]=4
     4 > 2 → pop 0, result[0]=4
     push 2: stack=[2]
i=3: 3 < 4, push: stack=[2,3]
End: result = [4, 4, -1, -1]
```

---

## 8. Min Stack (O(1) getMin)

```java
class MinStack {
    Deque<Integer> stack = new ArrayDeque<>();
    Deque<Integer> minStack = new ArrayDeque<>();

    void push(int val) {
        stack.push(val);
        int min = minStack.isEmpty() ? val : Math.min(val, minStack.peek());
        minStack.push(min);
    }
    void pop() {
        stack.pop();
        minStack.pop();
    }
    int getMin() { return minStack.peek(); }
    int top() { return stack.peek(); }
}
```

---

## 9. Practice Problems

**Easy:**
1. Valid Parentheses.
2. Implement a stack using arrays.
3. Reverse a string using stack.

**Medium:**
1. Min Stack.
2. Next Greater Element I & II.
3. Daily Temperatures.
4. Evaluate Reverse Polish Notation.
5. Largest Rectangle in Histogram.

**Hard:**
1. Maximal Rectangle in Binary Matrix.
2. Trapping Rain Water using stack.
3. Basic Calculator (with +, -, (, )).

---

**Next →** `05_Queue_Deque.md`
