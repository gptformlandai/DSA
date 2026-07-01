# Section 9 — Stack Patterns

---

## 1. What Problem Does This Solve?

Stack problems arise when you need to track **history that must be undone in reverse order**, or when you need to find the **nearest greater/smaller element** for each position. The stack's LIFO (Last-In, First-Out) property is the key to elegantly solving problems that would otherwise need O(n²) nested loops.

Stack patterns solve:
- Matching/balancing: parentheses, brackets, tags
- Nearest greater/smaller element in O(n)
- Area under histograms
- Expression evaluation and conversion
- Undo/redo operations
- Monotonic stack problems

---

## 2. Beginner-Friendly Intuition

Think of a **stack of plates**: you can only access the top plate. If you want a specific plate from the middle, you must remove every plate above it first — in reverse order of how you stacked them.

For "next greater element": imagine people standing in a line. Each person looks over their right shoulder for the first taller person. As taller people appear, they "solve" the problem for all shorter people waiting behind them — that's the monotonic stack.

---

## 3. Real-World Analogy

**Browser back button:** Every page you visit is pushed to a stack. Hitting "back" pops the most recent page. You always return to the previous state in reverse order.

**Function call stack:** Each function call is pushed. When it returns, it pops. The program must always finish the innermost (most recent) call before returning to the outer one.

**Undo in a text editor:** Each edit is pushed. Ctrl+Z pops the last edit, undoing it. Perfectly LIFO.

---

## 4. Core Concept

### The Four Key Stack Patterns

| Pattern | Core Idea | Canonical Problem |
|---------|---------|------------------|
| **Balanced Brackets** | Track unmatched openers; match with closers | Valid Parentheses |
| **Monotonic Stack** | Maintain stack in sorted order; pop when violated | Next Greater Element |
| **Stack + Min/Max** | Shadow stack tracks current minimum | Min Stack |
| **Expression Evaluation** | Stack stores operands; operator triggers evaluation | Basic Calculator |

### Monotonic Stack Intuition
When you push a new element and it's larger than the stack top, the top has found its "next greater element" — pop it, record the answer, and continue. After the loop, anything still in the stack has no next greater element.

---

## 5. Pattern Recognition Signals

Use Stack when:
```
"Valid/balanced parentheses/brackets"
"Next greater element" / "Next smaller element"
"Previous greater element" / "Previous smaller element"
"Largest rectangle in histogram"
"Daily temperatures"
"Trapping rain water" (stack variant)
"Evaluate expression / calculate"
"Implement undo/back"
"Decode string / nested structure"
"Remove k digits"
"Asteroid collision"
```

**Key signal:** If you need the most-recent-unsolved element, you need a stack.

---

## 6. Step-by-Step Algorithm

### Balanced Brackets Template
```
Step 1: Initialize empty stack
Step 2: For each character c in string:
    If c is an opener (( [ {): push c
    If c is a closer () ] }):
        If stack is empty OR top doesn't match: return false
        Pop top
Step 3: Return stack.isEmpty() (unmatched openers would remain)
```

### Monotonic Stack Template (Next Greater Element)
```
Step 1: result[] = fill with -1 (default: no NGE)
Step 2: Stack stores indices (not values)
Step 3: For each index i from 0 to n-1:
    While stack not empty AND arr[stack.top()] < arr[i]:
        idx = stack.pop()
        result[idx] = arr[i]  ← arr[i] is the NGE for arr[idx]
    stack.push(i)
Step 4: Elements still in stack have no NGE (result stays -1)
```

### Largest Rectangle in Histogram
```
Step 1: Stack stores indices of bars in increasing height order
Step 2: For each bar i (including sentinel bar of height 0 at end):
    While stack not empty AND height[stack.top()] > height[i]:
        h = height[stack.pop()]
        w = i - stack.top() - 1  (if stack empty, w = i)
        area = h × w
        maxArea = max(maxArea, area)
    stack.push(i)
```

---

## 7. Dry Run with Example

### Example 1: Valid Parentheses

**Input:** `s = "({[]})"`

```
i=0 '(': push  → stack=['(']
i=1 '{': push  → stack=['(', '{']
i=2 '[': push  → stack=['(', '{', '[']
i=3 ']': closer, top='[', matches! pop → stack=['(', '{']
i=4 '}': closer, top='{', matches! pop → stack=['(']
i=5 ')': closer, top='(', matches! pop → stack=[]

Stack empty → return TRUE ✓
```

**Input:** `s = "([)]"`

```
i=0 '(': push  → stack=['(']
i=1 '[': push  → stack=['(', '[']
i=2 ')': closer, top='[', NO MATCH → return FALSE ✓
```

### Example 2: Next Greater Element

**Input:** `arr = [4, 5, 2, 10, 8]`

```
result = [-1, -1, -1, -1, -1], stack=[]

i=0 (4): stack empty → push 0. stack=[0]
i=1 (5): arr[0]=4 < 5 → pop 0, result[0]=5. stack=[]. push 1. stack=[1]
i=2 (2): arr[1]=5 > 2 → no pop. push 2. stack=[1,2]
i=3 (10): arr[2]=2 < 10 → pop 2, result[2]=10.
          arr[1]=5 < 10 → pop 1, result[1]=10.
          stack=[]. push 3. stack=[3]
i=4 (8): arr[3]=10 > 8 → no pop. push 4. stack=[3,4]

Stack still has [3,4] → result[3]=-1, result[4]=-1 (no NGE)

result = [5, 10, 10, -1, -1] ✓
```

### Example 3: Daily Temperatures

**Input:** `temps = [73, 74, 75, 71, 69, 72, 76, 73]`

```
result=[0,0,0,0,0,0,0,0], stack=[]

i=0(73): stack=[0]
i=1(74): 73<74 → pop 0, result[0]=1-0=1. push 1. stack=[1]
i=2(75): 74<75 → pop 1, result[1]=2-1=1. push 2. stack=[2]
i=3(71): 75>71 → push 3. stack=[2,3]
i=4(69): 71>69 → push 4. stack=[2,3,4]
i=5(72): 69<72 → pop 4, result[4]=5-4=1.
         71<72 → pop 3, result[3]=5-3=2.
         75>72 → push 5. stack=[2,5]
i=6(76): 72<76 → pop 5, result[5]=6-5=1.
         75<76 → pop 2, result[2]=6-2=4.
         push 6. stack=[6]
i=7(73): 76>73 → push 7. stack=[6,7]

result = [1, 1, 4, 2, 1, 1, 0, 0] ✓
```

---

## 8. Code Implementation

### Valid Parentheses

```java
boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '[' || c == '{')
            stack.push(c);
        else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (c == ')' && top != '(') return false;
            if (c == ']' && top != '[') return false;
            if (c == '}' && top != '{') return false;
        }
    }
    return stack.isEmpty();
}
```

### Next Greater Element I

```java
int[] nextGreaterElement(int[] nums1, int[] nums2) {
    Map<Integer, Integer> nge = new HashMap<>();
    Deque<Integer> stack = new ArrayDeque<>(); // stores values
    for (int num : nums2) {
        while (!stack.isEmpty() && stack.peek() < num)
            nge.put(stack.pop(), num); // num is NGE of popped element
        stack.push(num);
    }
    int[] result = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++)
        result[i] = nge.getOrDefault(nums1[i], -1);
    return result;
}
```

### Daily Temperatures

```java
int[] dailyTemperatures(int[] temps) {
    int n = temps.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>(); // stores indices
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temps[stack.peek()] < temps[i]) {
            int idx = stack.pop();
            result[idx] = i - idx; // days until warmer
        }
        stack.push(i);
    }
    return result;
}
```

### Min Stack

```java
class MinStack {
    private Deque<Integer> stack = new ArrayDeque<>();
    private Deque<Integer> minStack = new ArrayDeque<>(); // shadow stack

    void push(int val) {
        stack.push(val);
        int newMin = minStack.isEmpty() ? val : Math.min(val, minStack.peek());
        minStack.push(newMin); // always push current minimum
    }

    void pop() {
        stack.pop();
        minStack.pop(); // both stacks stay synchronized
    }

    int top() { return stack.peek(); }
    int getMin() { return minStack.peek(); } // O(1)!
}
```

### Largest Rectangle in Histogram

```java
int largestRectangleArea(int[] heights) {
    int n = heights.length;
    Deque<Integer> stack = new ArrayDeque<>();
    int maxArea = 0;
    for (int i = 0; i <= n; i++) {
        int h = (i == n) ? 0 : heights[i]; // sentinel 0 at end flushes stack
        while (!stack.isEmpty() && heights[stack.peek()] > h) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        stack.push(i);
    }
    return maxArea;
}
```

### Decode String

```java
String decodeString(String s) {
    Deque<Integer> countStack = new ArrayDeque<>();
    Deque<StringBuilder> strStack = new ArrayDeque<>();
    StringBuilder current = new StringBuilder();
    int k = 0;
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            k = k * 10 + (c - '0'); // handles multi-digit
        } else if (c == '[') {
            countStack.push(k);
            strStack.push(current);
            current = new StringBuilder();
            k = 0;
        } else if (c == ']') {
            StringBuilder temp = current;
            current = strStack.pop();
            int repeat = countStack.pop();
            current.append(temp.toString().repeat(repeat));
        } else {
            current.append(c);
        }
    }
    return current.toString();
}
```

---

## 9. Time Complexity

| Problem | Brute Force | Stack Solution |
|---------|------------|---------------|
| Valid Parentheses | O(n) (both) | O(n) |
| Next Greater Element | O(n²) | O(n) |
| Daily Temperatures | O(n²) | O(n) |
| Largest Rectangle | O(n²) | O(n) |
| Decode String | — | O(n) |

**Why is Monotonic Stack O(n)?** Each element is pushed once and popped at most once → total 2n operations.

---

## 10. Space Complexity

| Problem | Space | Reason |
|---------|-------|--------|
| Valid Parentheses | O(n) | Stack holds at most n openers |
| Next Greater Element | O(n) | Stack + result map |
| Min Stack | O(n) | Two stacks synchronized |
| Largest Rectangle | O(n) | Stack holds at most n indices |

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Empty string | Return true for valid parentheses |
| Single character | Not balanced if it's an opener |
| All same element | Monotonic stack — all pop when larger arrives |
| Decreasing sequence | Stack never empties until end (NGE = -1 for all) |
| Histogram with h=0 | Acts as natural boundary in largest rectangle |
| Nested structures | Stack depth = nesting depth |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Not checking stack.isEmpty() before stack.peek() or stack.pop()
char top = stack.pop(); // WRONG if stack could be empty
if (!stack.isEmpty()) char top = stack.pop(); // CORRECT

// MISTAKE 2: Pushing values instead of indices in monotonic stack
// Indices let you compute distances (Daily Temperatures)
stack.push(arr[i]);  // WRONG for distance problems
stack.push(i);       // CORRECT — index lets you compute i - stack.peek()

// MISTAKE 3: Wrong direction of monotonic stack
// For "next GREATER": pop when current > top (maintain decreasing stack)
// For "next SMALLER": pop when current < top (maintain increasing stack)

// MISTAKE 4: Not using sentinel in Largest Rectangle
// Without sentinel, elements remaining in stack after loop are missed
// FIX: Add height[n] = 0 or iterate i from 0 to n (inclusive)

// MISTAKE 5: Wrong width calculation in Largest Rectangle
int width = stack.isEmpty() ? i : i - stack.peek() - 1;
// NOT: width = i - stack.peek()  (off by one — peek is the boundary, not included)
```

---

## 13. Interview-Level Explanation

**Q: "Why is the Monotonic Stack O(n) despite the while loop inside the for loop?"**

> "Each element is pushed onto the stack exactly once and popped at most once. The inner while loop doesn't run n times per outer iteration — it's amortized. The total number of push + pop operations across all iterations is bounded by 2n. Think of it as a budget: each element spends one 'credit' being pushed, and one 'credit' being popped. So total work is O(n)."

**Q: "How would you find the largest rectangle in a histogram?"**

> "I use a monotonic increasing stack storing indices. For each bar, while the current bar is shorter than the stack top, I pop and compute the rectangle with that height. The width extends from the current index back to the new stack top (exclusive). The key insight is: when we pop a bar, we know the first shorter bar to its right (current index) and to its left (new stack top), giving us exact width."

---

## 14. Real-World Use Cases

| Application | Stack Usage |
|------------|------------|
| **Compilers** | Parsing expressions, matching brackets in source code |
| **JVM** | Method call stack — each frame is a stack entry |
| **Text editors** | Undo/redo stack |
| **Web browsers** | Back/forward navigation history |
| **OS** | System call stack, interrupt handling |
| **XML/HTML parsers** | Tag matching validation |
| **Financial** | Stock span problem (how long has price been ≤ today's) |

---

## 15. Variations of This Pattern

| Variation | Stack Type | Example |
|-----------|-----------|---------|
| Valid brackets | Regular stack | Valid Parentheses |
| Next greater (right) | Decreasing monotonic | Next Greater Element I |
| Next smaller (right) | Increasing monotonic | Daily Temperatures |
| Previous greater (left) | Scan left to right, find last pop | Largest Rectangle |
| Min/Max tracking | Shadow min-stack | Min Stack |
| Expression evaluation | Dual stack (op + value) | Basic Calculator |
| Nested decode | Stack of (count, string) | Decode String |
| Remove k digits | Monotonic stack + greed | Remove K Digits |
| Collision | Stack stores surviving elements | Asteroid Collision |

---

## 16. Practice Problems

### Easy — Foundation
1. **Valid Parentheses** (LeetCode #20)
   - *Task:* Check if brackets are properly matched.
   - *Hint:* Push openers, match with closers. Empty stack at end = valid.

2. **Implement Stack Using Queues** (LeetCode #225)
   - *Task:* Simulate stack with queue operations.
   - *Hint:* Push is O(n) — rotate queue so newest is at front.

3. **Min Stack** (LeetCode #155)
   - *Task:* Stack that supports push, pop, top, and getMin in O(1).
   - *Hint:* Shadow min-stack that always stores current minimum.

### Medium — Monotonic Stack
1. **Daily Temperatures** (LeetCode #739)
   - *Task:* For each day, how many days until a warmer temperature?
   - *Hint:* Monotonic decreasing stack of indices.

2. **Next Greater Element II** (LeetCode #503)
   - *Task:* Next greater in a circular array.
   - *Hint:* Process 2n elements (or traverse twice) with modulo indexing.

3. **Largest Rectangle in Histogram** (LeetCode #84)
   - *Task:* Largest rectangular area under histogram bars.
   - *Hint:* Monotonic increasing stack. Pop when current bar is shorter.

4. **Asteroid Collision** (LeetCode #735)
   - *Task:* Simulate asteroid collisions based on direction.
   - *Hint:* Stack stores survivors. Only right-moving asteroids are "waiting."

5. **Decode String** (LeetCode #394)
   - *Task:* Expand encoded string like "3[a2[c]]" → "accaccacc".
   - *Hint:* Stack of (count, currentString) pairs.

### Hard — Advanced Stack
1. **Trapping Rain Water** (LeetCode #42)
   - *Task:* Water trapped between bars.
   - *Hint:* Stack variant: pop when taller bar found, compute trapped water in "pit."

2. **Maximal Rectangle** (LeetCode #85)
   - *Task:* Largest rectangle in binary matrix of 1s.
   - *Hint:* Convert each row to histogram heights, apply #84.

3. **Remove K Digits** (LeetCode #402)
   - *Task:* Remove k digits to form smallest number.
   - *Hint:* Monotonic increasing stack; pop larger digits greedily.

---

## 17. How to Know You Have Mastered Stack Patterns

You have mastered this topic when you can:
- [ ] Identify within 30 seconds whether a problem needs stack vs monotonic stack
- [ ] Implement Valid Parentheses including all three bracket types
- [ ] Build a Monotonic Stack from scratch and explain why it's O(n)
- [ ] Solve Largest Rectangle in Histogram including the sentinel trick
- [ ] Implement Min Stack with O(1) getMin
- [ ] Explain the difference between Next Greater (right-looking) and Previous Greater (left-looking)
- [ ] Recognize when to push values vs indices
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. For `s = "()[]{}"`, what does the stack look like at each step? What's the final result?

2. The monotonic stack for "Next Greater Element" maintains a **decreasing** stack. Why decreasing and not increasing?

3. In Largest Rectangle, after popping height `h` with current index `i` and new top index `j`, the width is `i - j - 1`. Why subtract 1?

4. For Min Stack, you push (3, 5, 2, 1, 4). What does the minStack look like after all pushes?

5. In Daily Temperatures `[89, 62, 70, 58, 47, 47, 46, 76, 100, 70]`, what is `result[1]`?

6. If you need "Previous Greater Element" (the last element greater than me to my LEFT), do you scan left-to-right or right-to-left?

7. The "at most" trick (`atMostK(k) - atMostK(k-1)`) is for Sliding Window. Is there an analogous trick for stack problems?

8. In Asteroid Collision, asteroids moving right (+) and left (-) collide. If you have `[5, -5]`, what happens? What about `[-5, 5]`?

> **Answers:**
> 1. `(` pushed; `)` matches, pop → empty; `[` pushed; `]` matches, pop → empty; `{` pushed; `}` matches, pop → empty. Final: stack empty → TRUE.
> 2. When a larger element arrives, it can be the NGE for all smaller elements on the stack. We keep a decreasing stack so we know which elements are still "waiting" for their NGE.
> 3. Index `j` is the stack top (the last bar that is shorter than `h`). The rectangle spans from j+1 to i-1 — width = (i-1) - (j+1) + 1 = i - j - 1.
> 4. minStack = [3, 3, 2, 1, 1]. Each level records the minimum so far.
> 5. `result[1] = 6` — temperature 62, next warmer is 76 at index 7, so 7-1=6 days.
> 6. Left-to-right! As you scan, push to stack. Each element pops all stack elements that are smaller than it (they found their "next greater going right"). Elements remaining in stack have no next-greater-right.
> 7. Not directly analogous. Stack problems don't generally decompose this way.
> 8. `[5, -5]`: 5 and -5 have same size → both destroyed. `[-5, 5]`: -5 moves left, 5 moves right, moving away from each other → no collision.

---

**Next →** `../10_Queue_Deque/01_Queue_Deque_Patterns.md`

**Idea:** Maintain a stack where elements are always in increasing (or decreasing) order.  
Elements that violate the order are **popped**, and their "answer" is recorded at that moment.

---

### Next Greater Element (NGE)

**Problem:** For each element, find the next element that is greater than it.

```java
int[] nextGreater(int[] arr) {
    int n = arr.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>();  // stores indices

    for (int i = 0; i < n; i++) {
        // Pop all elements smaller than current — their NGE is arr[i]
        while (!stack.isEmpty() && arr[stack.peek()] < arr[i])
            result[stack.pop()] = arr[i];
        stack.push(i);
    }
    return result;
}
```

**Dry Run:** arr=[2, 1, 5, 3, 4]
```
i=0(2): stack=[0]
i=1(1): 1<2, push: stack=[0,1]
i=2(5): 5>1→ pop 1, result[1]=5; 5>2→ pop 0, result[0]=5; push 2: stack=[2]
i=3(3): 3<5, push: stack=[2,3]
i=4(4): 4>3→ pop 3, result[3]=4; 4<5, push: stack=[2,4]
End: remaining stack [2,4] → result[-1,-1] (already filled)
Result: [5, 5, -1, 4, -1]
```

---

### Previous Greater Element (PGE)

Same idea but scan left to right, maintaining decreasing stack. When you push, the current stack top (before push) is the PGE.

```java
int[] prevGreater(int[] arr) {
    int n = arr.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && arr[stack.peek()] <= arr[i])
            stack.pop();
        result[i] = stack.isEmpty() ? -1 : arr[stack.peek()];
        stack.push(i);
    }
    return result;
}
```

---

### NGE in Circular Array

```java
int[] nextGreaterCircular(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < 2 * n; i++) {
        int idx = i % n;
        while (!stack.isEmpty() && nums[stack.peek()] < nums[idx])
            result[stack.pop()] = nums[idx];
        if (i < n) stack.push(idx);
    }
    return result;
}
```

---

## Pattern 2: Largest Rectangle in Histogram

One of the hardest stack problems. Key insight: for each bar as the shortest, find how far left and right it extends.

```java
int largestRectangle(int[] heights) {
    int n = heights.length;
    int maxArea = 0;
    Deque<Integer> stack = new ArrayDeque<>();  // increasing stack

    for (int i = 0; i <= n; i++) {
        int h = (i == n) ? 0 : heights[i];  // sentinel 0 at end
        while (!stack.isEmpty() && heights[stack.peek()] > h) {
            int height = heights[stack.pop()];
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            maxArea = Math.max(maxArea, height * width);
        }
        stack.push(i);
    }
    return maxArea;
}
```

**Dry Run:** heights=[2,1,5,6,2,3]
```
i=0(2): stack=[0]
i=1(1): 1<2→ pop 0(h=2), width=1(stack empty), area=2; push 1: stack=[1]
i=2(5): stack=[1,2]
i=3(6): stack=[1,2,3]
i=4(2): 2<6→ pop 3(h=6), w=4-2-1=1, area=6
         2<5→ pop 2(h=5), w=4-1-1=2, area=10
         2>1, push 4: stack=[1,4]
i=5(3): stack=[1,4,5]
i=6(0): 0<3→ pop 5(h=3), w=6-4-1=1, area=3
         0<2→ pop 4(h=2), w=6-1-1=4, area=8
         0<1→ pop 1(h=1), w=6, area=6
Result: 10
```

---

## Pattern 3: Daily Temperatures

**Problem:** For each day, how many days until warmer temperature?

```java
int[] dailyTemperatures(int[] temps) {
    int n = temps.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();  // indices
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && temps[stack.peek()] < temps[i]) {
            int j = stack.pop();
            result[j] = i - j;
        }
        stack.push(i);
    }
    return result;
}
```

---

## Pattern 4: Valid Parentheses

(Covered in Data Structures section — see `../02_Data_Structures/04_Stack.md`)

---

## Pattern 5: Expression Evaluation

**Evaluate Reverse Polish Notation:**
```java
int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();
    for (String token : tokens) {
        if ("+-*/".contains(token)) {
            int b = stack.pop(), a = stack.pop();
            switch (token) {
                case "+": stack.push(a + b); break;
                case "-": stack.push(a - b); break;
                case "*": stack.push(a * b); break;
                case "/": stack.push(a / b); break;
            }
        } else {
            stack.push(Integer.parseInt(token));
        }
    }
    return stack.pop();
}
```

---

## Pattern 6: Sum of Subarray Minimums (Hard)

**Key:** For each element, find how many subarrays have it as the minimum.  
Use NGE (next smaller) and PGE (previous smaller) to count.

```java
int sumSubarrayMins(int[] arr) {
    int n = arr.length;
    int MOD = 1_000_000_007;
    int[] left = new int[n];   // distance to previous smaller (or left boundary)
    int[] right = new int[n];  // distance to next smaller (or right boundary)

    Deque<Integer> stack = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) stack.pop();
        left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
        stack.push(i);
    }
    stack.clear();
    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) stack.pop();
        right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
        stack.push(i);
    }

    long result = 0;
    for (int i = 0; i < n; i++)
        result = (result + (long) arr[i] * left[i] * right[i]) % MOD;
    return (int) result;
}
```

---

## Practice Problems

**Easy:**
1. Valid Parentheses.
2. Min Stack.
3. Baseball Game.

**Medium:**
1. Next Greater Element I & II.
2. Daily Temperatures.
3. Evaluate Reverse Polish Notation.
4. Decode String.
5. Remove K Digits.

**Hard:**
1. Largest Rectangle in Histogram.
2. Maximal Rectangle (in binary matrix).
3. Sum of Subarray Minimums.

---

**Next →** `../10_Queue_Deque/01_Queue_Deque_Patterns.md`
