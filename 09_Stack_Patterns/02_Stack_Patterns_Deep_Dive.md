# Section 9 — Stack Patterns Deep Dive (MAANG L5 Coverage)

---

# INDEX — Quick Navigation

## Core Concepts
| Section | Description |
|---------|-------------|
| [The "One Sentence"](#the-one-sentence-that-unlocks-all-stack-problems) | Unlocks all stack problems |
| [Zero to Hero](#-zero-to-hero-understanding-stacks-from-scratch) | **START HERE if confused!** |
| [The 5 Stack Types](#the-5-stack-types-your-weapons) | Your weapons |
| [Stack Mechanics](#stack-mechanics-how-to-think) | Visual understanding |
| [The 6-Question Template](#the-6-question-template-for-every-stack-problem) | Solve any stack problem |

---

## Matching Stack Family (Patterns 0-4)
| # | Pattern | LeetCode |
|---|---------|----------|
| 0 | [Valid Parentheses](#pattern-0-valid-parentheses-leetcode-20) | 20 |
| 1 | [Longest Valid Parentheses](#pattern-1-longest-valid-parentheses-leetcode-32) | 32 |
| 2 | [Minimum Add to Make Valid](#pattern-2-minimum-add-to-make-parentheses-valid-leetcode-921) | 921 |
| 3 | [Score of Parentheses](#pattern-3-score-of-parentheses-leetcode-856) | 856 |
| 4 | [Remove Invalid Parentheses](#pattern-4-remove-invalid-parentheses-leetcode-301) | 301 |

## Monotonic Stack Family (Patterns 5-12)
| # | Pattern | LeetCode |
|---|---------|----------|
| 5 | [Daily Temperatures](#pattern-5-daily-temperatures-leetcode-739) | 739 |
| 6 | [Next Greater Element I](#pattern-6-next-greater-element-i-leetcode-496) | 496 |
| 7 | [Next Greater Element II](#pattern-7-next-greater-element-ii-leetcode-503) | 503 |
| 8 | [Next Greater Element III](#pattern-8-next-greater-element-iii-leetcode-556) | 556 |
| 9 | [Largest Rectangle in Histogram](#pattern-9-largest-rectangle-in-histogram-leetcode-84) | 84 |
| 10 | [Trapping Rain Water](#pattern-10-trapping-rain-water-leetcode-42) | 42 |
| 11 | [Stock Span Problem](#pattern-11-online-stock-span-leetcode-901) | 901 |
| 12 | [Sum of Subarray Minimums](#pattern-12-sum-of-subarray-minimums-leetcode-907) | 907 |

## Expression Evaluation Family (Patterns 13-17)
| # | Pattern | LeetCode |
|---|---------|----------|
| 13 | [Decode String](#pattern-13-decode-string-leetcode-394) | 394 |
| 14 | [Basic Calculator](#pattern-14-basic-calculator-leetcode-224) | 224 |
| 15 | [Basic Calculator II](#pattern-15-basic-calculator-ii-leetcode-227) | 227 |
| 16 | [Basic Calculator III](#pattern-16-basic-calculator-iii-leetcode-772) | 772 |
| 17 | [Evaluate Reverse Polish Notation](#pattern-17-evaluate-reverse-polish-notation-leetcode-150) | 150 |

## String Manipulation Family (Patterns 18-22)
| # | Pattern | LeetCode |
|---|---------|----------|
| 18 | [Simplify Path](#pattern-18-simplify-path-leetcode-71) | 71 |
| 19 | [Remove All Adjacent Duplicates](#pattern-19-remove-all-adjacent-duplicates-leetcode-1047) | 1047 |
| 20 | [Remove All Adjacent Duplicates II](#pattern-20-remove-all-adjacent-duplicates-ii-leetcode-1209) | 1209 |
| 21 | [Remove K Digits](#pattern-21-remove-k-digits-leetcode-402) | 402 |
| 22 | [Asteroid Collision](#pattern-22-asteroid-collision-leetcode-735) | 735 |

## Advanced Patterns (Patterns 23-24)
| # | Pattern | LeetCode |
|---|---------|----------|
| 23 | [132 Pattern](#pattern-23-132-pattern-leetcode-456) | 456 |
| 24 | [Sum of Subarray Ranges](#pattern-24-sum-of-subarray-ranges-leetcode-2104) | 2104 |

## Reference Sections
| Section |
|---------|
| [MAANG Coverage Map](#maang-coverage-map) |
| [Stack Cheat Sheet](#stack-cheat-sheet) |
| [Mastery Checklist](#mastery-checklist) |

---

# The "One Sentence That Unlocks All Stack Problems"

> **"A stack remembers HISTORY in reverse order — use it when you need to UNDO, MATCH, or find the NEAREST thing."**

That's the entire subject. Every stack problem is just:
1. **MATCH** — pair openers with closers (parentheses, tags)
2. **NEAREST** — find next/previous greater/smaller element
3. **UNDO** — process nested structures inside-out
4. **EVALUATE** — defer operations until you have enough info

---

# 🌟 ZERO TO HERO: Understanding Stacks From Scratch

**If you're a junior dev and stacks feel confusing, START HERE.**

---

## What IS a Stack? (The Real-World Analogy)

Imagine a **stack of plates** in a cafeteria:

```
    ┌─────────┐
    │ Plate 5 │  ← You can only take THIS one (top)
    ├─────────┤
    │ Plate 4 │
    ├─────────┤
    │ Plate 3 │
    ├─────────┤
    │ Plate 2 │
    ├─────────┤
    │ Plate 1 │  ← This was added first, but you get it LAST
    └─────────┘
```

**The Rule:** Last In, First Out (LIFO)
- The LAST plate you put on top is the FIRST one you take off
- You can't grab a plate from the middle!

---

## Why Do We Need Stacks in Coding?

**Because some problems need you to "remember" things and process them in REVERSE order.**

### Example 1: Matching Parentheses

```
Input: "( [ ] )"

Reading left to right:
- See '(' → Remember it! (might need to match later)
- See '[' → Remember it too!
- See ']' → This should match the MOST RECENT opener... which is '['! ✓
- See ')' → This should match the MOST RECENT opener... which is '('! ✓

The MOST RECENT opener = the one on TOP of the stack!
```

### Example 2: Finding Next Greater Element

```
Array: [2, 1, 2, 4, 3]

For element 1, what's the next element that's GREATER than 1?
Answer: 2 (the one at index 2)

For element 2 (at index 2), what's the next element that's GREATER?
Answer: 4

How do we solve this efficiently? 
We need to "remember" elements that haven't found their answer yet!
```

---

## The 3 Stack Operations (That's ALL You Need!)

```java
Deque<Integer> stack = new ArrayDeque<>();  // Create empty stack

stack.push(5);    // ADD to top:     stack = [5]
stack.push(3);    // ADD to top:     stack = [5, 3]
stack.push(7);    // ADD to top:     stack = [5, 3, 7]

stack.peek();     // LOOK at top:    returns 7 (stack unchanged)

stack.pop();      // REMOVE from top: returns 7, stack = [5, 3]
stack.pop();      // REMOVE from top: returns 3, stack = [5]

stack.isEmpty();  // CHECK if empty: returns false
```

**That's it!** Push, Pop, Peek. Everything else is just combining these.

---

## The 4 Types of Stack Problems (With Plain English)

### Type 1: MATCHING (Parentheses, Brackets)

**The Question:** "Does every opener have a matching closer in the right order?"

**The Trick:** 
- See opener `(`, `[`, `{` → PUSH it
- See closer `)`, `]`, `}` → POP and check if it matches
- At the end, stack should be EMPTY

**Real Example:**
```
Input: "([)]"

Step 1: See '(' → push → stack = ['(']
Step 2: See '[' → push → stack = ['(', '[']
Step 3: See ')' → pop → got '[', but ')' doesn't match '[' → INVALID!

Answer: false
```

---

### Type 2: NEXT GREATER/SMALLER (Monotonic Stack)

**The Question:** "For each element, what's the next element that's bigger/smaller?"

**The Trick (This is the KEY insight!):**

```
┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│  When you find a BIGGER element, it's the answer for ALL            │
│  SMALLER elements waiting in the stack!                             │
│                                                                      │
│  Think of it like this:                                             │
│  - Small elements are "waiting" for a bigger element                │
│  - When a big element arrives, all the small ones say "FOUND IT!"   │
│  - They leave the stack (pop) because they got their answer         │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

**Real Example: Daily Temperatures**

"For each day, how many days until a warmer temperature?"

```
temps = [73, 74, 75, 71, 69, 72, 76, 73]

Day 0 (73°): Stack empty, push 0 → stack = [0]
             "Day 0 is waiting for a warmer day"

Day 1 (74°): 74 > 73! Day 0 found its answer!
             Pop 0, answer[0] = 1 - 0 = 1 day
             Push 1 → stack = [1]

Day 2 (75°): 75 > 74! Day 1 found its answer!
             Pop 1, answer[1] = 2 - 1 = 1 day
             Push 2 → stack = [2]

Day 3 (71°): 71 < 75, no answer yet
             Push 3 → stack = [2, 3]
             "Day 2 and Day 3 are both waiting"

Day 4 (69°): 69 < 71, no answer yet
             Push 4 → stack = [2, 3, 4]

Day 5 (72°): 72 > 69! Day 4 found its answer!
             Pop 4, answer[4] = 5 - 4 = 1 day
             72 > 71! Day 3 found its answer!
             Pop 3, answer[3] = 5 - 3 = 2 days
             72 < 75, Day 2 still waiting
             Push 5 → stack = [2, 5]

Day 6 (76°): 76 > 72! Day 5 found its answer!
             Pop 5, answer[5] = 6 - 5 = 1 day
             76 > 75! Day 2 found its answer!
             Pop 2, answer[2] = 6 - 2 = 4 days
             Push 6 → stack = [6]

Day 7 (73°): 73 < 76, no answer yet
             Push 7 → stack = [6, 7]

End: Days 6 and 7 never found warmer days → answer stays 0

Final: [1, 1, 4, 2, 1, 1, 0, 0]
```

**Why Store INDICES, Not Values?**
```
Because we need to calculate DISTANCE!

If we stored values: stack = [76, 73]
  → We know the temperatures, but not WHICH DAYS they were!

If we stored indices: stack = [6, 7]
  → We can look up temps[6]=76, temps[7]=73
  → AND we can calculate: day 7 - day 5 = 2 days apart
```

---

### Type 3: DECODE/EVALUATE (Context Stack)

**The Question:** "How do I handle nested structures like `3[a2[c]]`?"

**The Trick:**
- When you see `[` → You're going DEEPER. Save your current work!
- When you see `]` → You're coming back UP. Restore your saved work!

**Real Example: Decode String**

```
Input: "3[a2[c]]"
Expected: "accaccacc"

Let's trace through:

See '3': number = 3
See '[': Going deeper! PUSH current state (number=3, string="")
         Reset: number = 0, string = ""

See 'a': string = "a"
See '2': number = 2
See '[': Going deeper! PUSH current state (number=2, string="a")
         Reset: number = 0, string = ""

See 'c': string = "c"
See ']': Coming back up! POP → got (number=2, prevString="a")
         string = "a" + "c".repeat(2) = "a" + "cc" = "acc"

See ']': Coming back up! POP → got (number=3, prevString="")
         string = "" + "acc".repeat(3) = "accaccacc"

Answer: "accaccacc"
```

---

### Type 4: AREA/HISTOGRAM (Boundary Stack)

**The Question:** "What's the largest rectangle I can form?"

**The Trick:**
- Keep bars in INCREASING order
- When a SHORTER bar comes, the taller bars can't extend further right
- Pop them and calculate their area!

**Real Example: Largest Rectangle**

```
heights = [2, 1, 5, 6, 2, 3]

i=0 (h=2): Stack empty, push 0 → stack = [0]

i=1 (h=1): 1 < 2! Bar at index 0 can't extend right anymore!
           Pop 0, height = 2, width = 1, area = 2
           Push 1 → stack = [1]

i=2 (h=5): 5 > 1, push 2 → stack = [1, 2]

i=3 (h=6): 6 > 5, push 3 → stack = [1, 2, 3]

i=4 (h=2): 2 < 6! Bar at index 3 can't extend right!
           Pop 3, height = 6, width = 4-2-1 = 1, area = 6
           2 < 5! Bar at index 2 can't extend right!
           Pop 2, height = 5, width = 4-1-1 = 2, area = 10 ← MAX!
           2 > 1, push 4 → stack = [1, 4]

i=5 (h=3): 3 > 2, push 5 → stack = [1, 4, 5]

End: Process remaining bars with sentinel (height 0)
     Pop 5, height = 3, width = 6-4-1 = 1, area = 3
     Pop 4, height = 2, width = 6-1-1 = 4, area = 8
     Pop 1, height = 1, width = 6, area = 6

Answer: 10
```

---

## The "Aha!" Moment Summary

```
┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│  MATCHING:     Push openers, pop on closers, check if matches       │
│                                                                      │
│  NEXT GREATER: When bigger element arrives, it's the ANSWER         │
│                for all smaller elements waiting in stack!           │
│                                                                      │
│  DECODE:       Push state when going IN, pop when coming OUT        │
│                                                                      │
│  HISTOGRAM:    Pop when shorter bar arrives, calculate area         │
│                Width = right boundary - left boundary - 1           │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

## The Universal Stack Template (Copy This!)

```java
Deque<Integer> stack = new ArrayDeque<>();

for (int i = 0; i < n; i++) {
    // STEP 1: Process elements that need to leave
    while (!stack.isEmpty() && shouldPop(stack.peek(), current)) {
        int popped = stack.pop();
        // Do something with popped element
        // (record answer, calculate area, etc.)
    }
    
    // STEP 2: Add current element
    stack.push(i);  // or push value, depending on problem
}

// STEP 3: Handle remaining elements (if needed)
while (!stack.isEmpty()) {
    int popped = stack.pop();
    // These elements have no answer, use default
}
```

---

## 📋 THE JUNIOR DEV CHEAT CARD (Memorize This!)

```
╔═══════════════════════════════════════════════════════════════════════╗
║                     STACK PROBLEM? USE THIS!                           ║
╠═══════════════════════════════════════════════════════════════════════╣
║                                                                        ║
║  STEP 1: "What type of problem is this?"                              ║
║                                                                        ║
║  ┌────────────────────────────────────────────────────────────────┐   ║
║  │ "Valid/balanced brackets"  → MATCHING STACK                    │   ║
║  │ "Next greater/smaller"     → MONOTONIC STACK                   │   ║
║  │ "Largest rectangle/area"   → MONOTONIC STACK (increasing)      │   ║
║  │ "Decode/evaluate expr"     → CONTEXT STACK (save state)        │   ║
║  │ "Remove duplicates"        → PROCESSING STACK                  │   ║
║  │ "Collision/cancel"         → SIMULATION STACK                  │   ║
║  └────────────────────────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE 5 STACK TYPES:                                                    ║
║                                                                        ║
║  ┌──────────────────┬─────────────────────────────────────────────┐   ║
║  │ 1. MATCHING      │ Push opener, pop when closer matches        │   ║
║  │ 2. MONOTONIC     │ Maintain sorted order, pop when violated    │   ║
║  │ 3. INDEX         │ Store indices (not values) for distances    │   ║
║  │ 4. CONTEXT       │ Save (state) on '[', restore on ']'         │   ║
║  │ 5. BOUNDARY      │ Track left/right boundaries for area calc   │   ║
║  └──────────────────┴─────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  MONOTONIC STACK QUICK RULES:                                          ║
║                                                                        ║
║  ┌────────────────────────────────────────────────────────────────┐   ║
║  │ "Next GREATER" → Maintain DECREASING stack (pop when bigger)   │   ║
║  │ "Next SMALLER" → Maintain INCREASING stack (pop when smaller)  │   ║
║  │                                                                │   ║
║  │ When you POP: the current element is the answer for popped!    │   ║
║  │ What remains in stack at end: has no answer (use -1 or n)      │   ║
║  └────────────────────────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  JAVA STACK CODE:                                                      ║
║                                                                        ║
║  Deque<Integer> stack = new ArrayDeque<>();  // Use Deque, not Stack! ║
║  stack.push(x);     // Add to top                                     ║
║  stack.pop();       // Remove and return top                          ║
║  stack.peek();      // Look at top without removing                   ║
║  stack.isEmpty();   // Check if empty                                 ║
║                                                                        ║
╚═══════════════════════════════════════════════════════════════════════╝
```

---

## 🚀 QUICK START: The 60-Second Stack Approach

### The ONE Question That Solves Most Problems:

> **"Do I need to remember something and process it LATER in REVERSE order?"**

If YES → Use a stack!

### The Simple Decision Tree:

```
1. "Match brackets/parentheses?"
   → Push openers, pop and match when closer arrives
   → Empty stack at end = valid

2. "Find next greater/smaller element?"
   → Monotonic stack! Store INDICES
   → Pop when current violates the order
   → Popped element's answer = current element

3. "Evaluate expression or decode string?"
   → Push current state when entering nested level
   → Pop and combine when exiting nested level

4. "Find largest rectangle/area?"
   → Monotonic INCREASING stack
   → When you pop, calculate area with that height
   → Width = current index - stack top - 1
```

### Why Monotonic Stack is O(n):

```
Each element is pushed ONCE and popped AT MOST ONCE.
Total operations = 2n = O(n)

Even though there's a while loop inside the for loop,
the while loop doesn't run n times per iteration!
It runs at most n times TOTAL across all iterations.
```

---

## 🎯 WORKED EXAMPLE: How a Junior Dev Should Think

**Problem:** "Daily Temperatures — for each day, how many days until warmer?"

### My Thinking Process:

**Step 1: What am I looking for?**
> "For each temperature, I need the NEXT temperature that is GREATER."

**Step 2: Is this a 'next greater' problem?**
> "Yes! This is classic monotonic stack."

**Step 3: Which type of monotonic stack?**
> "Next GREATER → maintain DECREASING stack"
> "When I see a bigger temp, it's the answer for all smaller temps waiting"

**Step 4: Store values or indices?**
> "I need to calculate DISTANCE (days), so I need INDICES!"

**Step 5: Write the code:**
```java
int[] dailyTemperatures(int[] temps) {
    int n = temps.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();  // Store INDICES
    
    for (int i = 0; i < n; i++) {
        // Pop all temps that are SMALLER than current
        while (!stack.isEmpty() && temps[stack.peek()] < temps[i]) {
            int idx = stack.pop();
            result[idx] = i - idx;  // Days until warmer
        }
        stack.push(i);
    }
    // Remaining in stack have no warmer day (result stays 0)
    return result;
}
```

### Visual Dry Run:

```
temps = [73, 74, 75, 71, 69, 72, 76, 73]
result = [0, 0, 0, 0, 0, 0, 0, 0]

i=0 (73): stack=[], push 0 → stack=[0]
i=1 (74): 74>73, pop 0, result[0]=1-0=1, push 1 → stack=[1]
i=2 (75): 75>74, pop 1, result[1]=2-1=1, push 2 → stack=[2]
i=3 (71): 71<75, push 3 → stack=[2,3]
i=4 (69): 69<71, push 4 → stack=[2,3,4]
i=5 (72): 72>69, pop 4, result[4]=5-4=1
          72>71, pop 3, result[3]=5-3=2
          72<75, push 5 → stack=[2,5]
i=6 (76): 76>72, pop 5, result[5]=6-5=1
          76>75, pop 2, result[2]=6-2=4
          push 6 → stack=[6]
i=7 (73): 73<76, push 7 → stack=[6,7]

Final: result = [1, 1, 4, 2, 1, 1, 0, 0] ✓
```

---

# The 5 Stack Types (Your "Weapons")

## Type 1: MATCHING STACK

**Purpose:** Match openers with closers (parentheses, brackets, tags)

**Mental Model:** A coat check — you give your coat (opener), get a ticket. When you leave (closer), you must return the MOST RECENT ticket first.

```
Input: "({[]})"

Push '(' → stack=['(']
Push '{' → stack=['(', '{']
Push '[' → stack=['(', '{', '[']
See ']' → matches '[', pop → stack=['(', '{']
See '}' → matches '{', pop → stack=['(']
See ')' → matches '(', pop → stack=[]

Stack empty at end → VALID!
```

**When to Use:**
- Valid parentheses
- Matching HTML/XML tags
- Balanced expressions

---

## Type 2: MONOTONIC STACK

**Purpose:** Find next/previous greater/smaller element in O(n)

**Mental Model:** A line of people waiting. When a taller person arrives, all shorter people in front can see them — they've found their "next taller person."

```
┌─────────────────────────────────────────────────────────────────────┐
│                    MONOTONIC STACK RULES                             │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  DECREASING STACK (for Next Greater):                               │
│  ─────────────────────────────────────                              │
│  Stack: [5, 3, 2]  (decreasing from bottom to top)                  │
│                                                                      │
│  New element 4 arrives:                                             │
│  - 4 > 2? YES → pop 2, 2's next greater = 4                         │
│  - 4 > 3? YES → pop 3, 3's next greater = 4                         │
│  - 4 > 5? NO → stop, push 4                                         │
│  Stack: [5, 4]                                                       │
│                                                                      │
│  ═══════════════════════════════════════════════════════════════    │
│                                                                      │
│  INCREASING STACK (for Next Smaller):                               │
│  ─────────────────────────────────────                              │
│  Stack: [2, 3, 5]  (increasing from bottom to top)                  │
│                                                                      │
│  New element 4 arrives:                                             │
│  - 4 < 5? YES → pop 5, 5's next smaller = 4                         │
│  - 4 < 3? NO → stop, push 4                                         │
│  Stack: [2, 3, 4]                                                    │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

**When to Use:**
- Next/Previous Greater/Smaller Element
- Daily Temperatures
- Stock Span
- Largest Rectangle in Histogram

---

## Type 3: INDEX STACK

**Purpose:** Store indices instead of values to calculate distances

**Mental Model:** Instead of remembering "the temperature was 73", remember "it was day 0". Then you can calculate "day 5 - day 0 = 5 days apart."

```java
// WRONG for distance problems:
stack.push(temps[i]);  // Can't calculate distance!

// CORRECT:
stack.push(i);  // Store index
// Later: distance = currentIndex - stack.peek()
```

**When to Use:**
- Daily Temperatures (days until warmer)
- Largest Rectangle (width calculation)
- Any problem asking "how far" or "how many"

---

## Type 4: CONTEXT STACK

**Purpose:** Save current state when entering nested level, restore when exiting

**Mental Model:** Reading a book with footnotes. When you hit a footnote, you bookmark your page (push), read the footnote, then return to your bookmark (pop).

```
Decode "3[a2[c]]":

See '3': k=3
See '[': PUSH (k=3, current=""), reset k=0, current=""
See 'a': current="a"
See '2': k=2
See '[': PUSH (k=2, current="a"), reset k=0, current=""
See 'c': current="c"
See ']': POP (k=2, prev="a"), current = "a" + "c".repeat(2) = "acc"
See ']': POP (k=3, prev=""), current = "" + "acc".repeat(3) = "accaccacc"

Result: "accaccacc"
```

**When to Use:**
- Decode String
- Basic Calculator (parentheses)
- Nested structures

---

## Type 5: BOUNDARY STACK

**Purpose:** Track left and right boundaries for area calculations

**Mental Model:** For each bar in a histogram, find how far left and right it can extend as the shortest bar. The stack helps find these boundaries efficiently.

```
Histogram: [2, 1, 5, 6, 2, 3]

For bar height 5 at index 2:
- Left boundary: index 1 (height 1 < 5)
- Right boundary: index 4 (height 2 < 5)
- Width = 4 - 1 - 1 = 2
- Area = 5 × 2 = 10

The stack maintains increasing heights.
When we pop, we know the boundaries!
```

**When to Use:**
- Largest Rectangle in Histogram
- Maximal Rectangle in Matrix
- Trapping Rain Water

---

# The 6-Question Template (For Every Stack Problem)

This template is your **mental checklist** before writing any stack code. Walk through each question, and the solution reveals itself!

---

## Question 1: WHAT TYPE OF STACK PROBLEM?

**The Core Question:** Which of the 5 stack patterns does this problem fit?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    IDENTIFYING THE STACK TYPE                        │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Problem Keywords              │  Stack Type        │  Example       │
│  ─────────────────────────────┼────────────────────┼────────────────│
│  "valid", "balanced", "match" │  MATCHING          │  LC 20         │
│  "next greater", "next smaller"│  MONOTONIC        │  LC 739        │
│  "largest rectangle", "area"  │  BOUNDARY          │  LC 84         │
│  "decode", "evaluate", "calc" │  CONTEXT           │  LC 394, 224   │
│  "remove duplicates", "cancel"│  SIMULATION        │  LC 1047, 735  │
│                                                                      │
│  💡 KEY INSIGHT:                                                     │
│  If you see "next" or "previous" + "greater" or "smaller"           │
│  → It's ALWAYS monotonic stack!                                     │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Mental Exercise

Before coding, complete this sentence:
> "This problem is asking me to find/match/process _______ which means I need a _______ stack."

**Examples:**
- "This problem is asking me to find the **next greater element** which means I need a **monotonic decreasing** stack."
- "This problem is asking me to **match brackets** which means I need a **matching** stack."

---

## Question 2: WHAT SHOULD I STORE IN THE STACK?

**The Data Structure Question:** What information do I need to remember?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    WHAT TO STORE IN STACK                            │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Problem Type          │  Store              │  Why                  │
│  ──────────────────────┼─────────────────────┼───────────────────────│
│  Valid Parentheses     │  Characters         │  Need to match chars  │
│  Daily Temperatures    │  INDICES            │  Need distance (i-j)  │
│  Largest Rectangle     │  INDICES            │  Need width (i-j-1)   │
│  Decode String         │  (count, string)    │  Need both to repeat  │
│  Basic Calculator      │  (result, sign)     │  Need both to combine │
│  Stock Span            │  (price, span)      │  Need both to absorb  │
│  Remove Dups II        │  (char, count)      │  Need count for k     │
│                                                                      │
│  ⚠️ CRITICAL RULE:                                                   │
│  If you need to calculate DISTANCE or WIDTH → Store INDICES!        │
│  If you only need to compare VALUES → Store values                  │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### The Index vs Value Decision

```
┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│  STORE VALUES when:                                                 │
│  - You only need to compare (Next Greater Element I)                │
│  - You're matching characters (Valid Parentheses)                   │
│                                                                      │
│  STORE INDICES when:                                                │
│  - You need to calculate DISTANCE (Daily Temperatures)              │
│  - You need to calculate WIDTH (Largest Rectangle)                  │
│  - You need to know POSITION (Longest Valid Parentheses)            │
│                                                                      │
│  STORE PAIRS when:                                                  │
│  - You need BOTH value and something else (Stock Span: price+span)  │
│  - You need to save STATE (Decode: count+string)                    │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Question 3: WHEN DO I PUSH?

**The Entry Question:** What triggers adding to the stack?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    WHEN TO PUSH                                      │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Stack Type        │  Push When                                      │
│  ──────────────────┼────────────────────────────────────────────────│
│  MATCHING          │  See an OPENER: ( [ {                          │
│  MONOTONIC         │  AFTER popping all that violate order          │
│  CONTEXT           │  See '[' or '(' — entering nested level        │
│  BOUNDARY          │  AFTER calculating area for popped elements    │
│  SIMULATION        │  Element survives (no collision/cancel)        │
│                                                                      │
│  💡 MONOTONIC STACK INSIGHT:                                         │
│  You ALWAYS push after the while loop!                              │
│  The while loop pops violators, then you push the current element.  │
│                                                                      │
│  for (int i = 0; i < n; i++) {                                      │
│      while (!stack.isEmpty() && violates(stack.peek(), arr[i])) {   │
│          // Pop and process                                         │
│      }                                                               │
│      stack.push(i);  // ALWAYS push after while loop                │
│  }                                                                   │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Question 4: WHEN DO I POP?

**The Exit Question:** What triggers removing from the stack?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    WHEN TO POP                                       │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Stack Type        │  Pop When                                       │
│  ──────────────────┼────────────────────────────────────────────────│
│  MATCHING          │  See a CLOSER that matches top: ) ] }          │
│  MONOTONIC (NGE)   │  Current > stack top (violates decreasing)     │
│  MONOTONIC (NSE)   │  Current < stack top (violates increasing)     │
│  CONTEXT           │  See ']' or ')' — exiting nested level         │
│  BOUNDARY          │  Current height < stack top height             │
│  SIMULATION        │  Collision occurs (asteroid, duplicate)        │
│                                                                      │
│  ⚠️ THE MONOTONIC POP INSIGHT:                                       │
│                                                                      │
│  When you POP in monotonic stack:                                   │
│  → The CURRENT element is the ANSWER for the POPPED element!        │
│                                                                      │
│  Example (Next Greater):                                            │
│  Stack: [5, 3, 2], Current: 4                                       │
│  Pop 2 → 4 is 2's next greater                                      │
│  Pop 3 → 4 is 3's next greater                                      │
│  Stop (4 < 5), push 4                                               │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### The Monotonic Stack Pop Visualization

```
DECREASING STACK (for Next Greater):

Before: stack = [10, 7, 5, 3]  (decreasing)
Current element: 6

Step 1: 6 > 3? YES → pop 3, 3's NGE = 6
Step 2: 6 > 5? YES → pop 5, 5's NGE = 6
Step 3: 6 > 7? NO → stop
Step 4: push 6

After: stack = [10, 7, 6]  (still decreasing!)

The popped elements (3, 5) found their answer (6).
The remaining elements (10, 7) are still waiting.
```

---

## Question 5: WHAT DO I DO WHEN I POP?

**The Action Question:** What computation happens at pop time?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    ACTIONS ON POP                                    │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Stack Type        │  Action on Pop                                  │
│  ──────────────────┼────────────────────────────────────────────────│
│  MATCHING          │  Just discard (or check if matches)            │
│  MONOTONIC         │  Record: result[popped] = current              │
│  DAILY TEMPS       │  Record: result[popped] = i - popped           │
│  HISTOGRAM         │  Calculate: area = height × width              │
│  CONTEXT           │  Combine: current = prev + repeat(inner, k)    │
│  CALCULATOR        │  Combine: result = prev + sign × inner         │
│                                                                      │
│  💡 HISTOGRAM WIDTH CALCULATION:                                     │
│                                                                      │
│  When you pop index j with height h:                                │
│  - Right boundary: current index i (first shorter bar to right)    │
│  - Left boundary: new stack top k (first shorter bar to left)      │
│  - Width = i - k - 1                                                │
│  - If stack empty after pop: width = i (extends to left edge)      │
│                                                                      │
│  int height = heights[stack.pop()];                                 │
│  int width = stack.isEmpty() ? i : i - stack.peek() - 1;            │
│  area = height * width;                                             │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

## Question 6: WHAT ABOUT ELEMENTS LEFT IN STACK?

**The Cleanup Question:** How do I handle remaining elements?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HANDLING REMAINING ELEMENTS                       │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Stack Type        │  Remaining Elements Mean                        │
│  ──────────────────┼────────────────────────────────────────────────│
│  MATCHING          │  Unmatched openers → INVALID                   │
│  MONOTONIC         │  No answer found → use default (-1 or 0)       │
│  HISTOGRAM         │  Need to flush → use SENTINEL (height 0)       │
│  CONTEXT           │  Should be empty if input is valid             │
│                                                                      │
│  💡 THE SENTINEL TRICK (Histogram):                                  │
│                                                                      │
│  Problem: After the loop, stack may still have elements.            │
│  Solution: Add a "sentinel" bar of height 0 at the end!             │
│                                                                      │
│  for (int i = 0; i <= n; i++) {  // Note: <= n, not < n             │
│      int h = (i == n) ? 0 : heights[i];  // Sentinel at end         │
│      // ... rest of logic                                           │
│  }                                                                   │
│                                                                      │
│  The sentinel (height 0) is shorter than everything,                │
│  so it forces ALL remaining bars to be popped and processed!        │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 🎯 COMPLETE WORKED EXAMPLE: Largest Rectangle in Histogram

Let's apply ALL 6 questions:

**Problem:** Find the largest rectangle in a histogram.

### Question 1: What type?
> "Largest rectangle" → BOUNDARY stack (need left/right limits)

### Question 2: What to store?
> Need to calculate WIDTH → Store INDICES

### Question 3: When to push?
> After popping all taller bars (maintain increasing order)

### Question 4: When to pop?
> When current bar is SHORTER than stack top

### Question 5: What to do on pop?
> Calculate area: height × width, where width = i - stack.peek() - 1

### Question 6: Remaining elements?
> Use SENTINEL (height 0 at end) to flush all

### The Code:

```java
int largestRectangleArea(int[] heights) {
    int n = heights.length;
    Deque<Integer> stack = new ArrayDeque<>();
    int maxArea = 0;
    
    for (int i = 0; i <= n; i++) {  // Q6: <= n for sentinel
        int h = (i == n) ? 0 : heights[i];  // Q6: sentinel
        
        while (!stack.isEmpty() && heights[stack.peek()] > h) {  // Q4: pop when shorter
            int height = heights[stack.pop()];  // Q5: get height
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;  // Q5: calc width
            maxArea = Math.max(maxArea, height * width);  // Q5: calc area
        }
        stack.push(i);  // Q3: push after while loop
    }
    
    return maxArea;
}
```

---

## The Complete Mental Walkthrough

Before coding ANY stack problem, fill in this template:

```
┌─────────────────────────────────────────────────────────────────────┐
│                    STACK PROBLEM ANALYSIS                            │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Problem: _________________________________________________         │
│                                                                      │
│  1. WHAT TYPE?                                                       │
│     □ Matching  □ Monotonic  □ Context  □ Boundary  □ Simulation   │
│                                                                      │
│  2. WHAT TO STORE?                                                   │
│     □ Characters  □ Values  □ Indices  □ Pairs: (_____, _____)     │
│                                                                      │
│  3. WHEN TO PUSH?                                                    │
│     Trigger: ________________________________________________       │
│                                                                      │
│  4. WHEN TO POP?                                                     │
│     Trigger: ________________________________________________       │
│                                                                      │
│  5. ACTION ON POP?                                                   │
│     Computation: ____________________________________________       │
│                                                                      │
│  6. REMAINING ELEMENTS?                                              │
│     □ Should be empty  □ Default value  □ Use sentinel             │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

# MATCHING STACK FAMILY

---

# PATTERN 0: Valid Parentheses (LeetCode 20)

## Pattern Recognition Signal

**When you see:** "valid", "balanced", "matching brackets/parentheses"

**Instant thought:** "I need to match openers with closers in REVERSE order → STACK!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: "([{}])"

The question is: Does every '(' have a matching ')' in the RIGHT ORDER?

Wrong order example: "([)]" 
- '(' opened first, then '['
- But ')' tries to close before ']'
- That's like taking off your shoes before your socks!
```

### Why Stack?

```
Think about it:
- '(' opens... waiting for ')'
- '[' opens... waiting for ']'
- '{' opens... waiting for '}'

When '}' arrives, which opener should it match?
→ The MOST RECENT one! (which is '{')

MOST RECENT = LAST IN = Stack's specialty!
```

### The Algorithm in Plain English

```
1. See an opener? → Remember it (push to stack)
2. See a closer? → Check if it matches the most recent opener (peek/pop)
3. At the end? → All openers should be matched (stack empty)
```

---

## Visual Dry Run

**Input:** `"([{}])"`

```
Step-by-step:

Character '(':
  → It's an opener!
  → Push to stack
  → Stack: ['(']

Character '[':
  → It's an opener!
  → Push to stack
  → Stack: ['(', '[']

Character '{':
  → It's an opener!
  → Push to stack
  → Stack: ['(', '[', '{']

Character '}':
  → It's a closer!
  → What's on top of stack? '{'
  → Does '}' match '{'? YES! ✓
  → Pop the '{'
  → Stack: ['(', '[']

Character ']':
  → It's a closer!
  → What's on top of stack? '['
  → Does ']' match '['? YES! ✓
  → Pop the '['
  → Stack: ['(']

Character ')':
  → It's a closer!
  → What's on top of stack? '('
  → Does ')' match '('? YES! ✓
  → Pop the '('
  → Stack: []

End of string:
  → Is stack empty? YES! ✓
  → All openers matched!

RESULT: true ✓
```

**Invalid Example:** `"([)]"`

```
Character '(': Push → Stack: ['(']
Character '[': Push → Stack: ['(', '[']
Character ')': 
  → Closer! Top is '['. Does ')' match '['? NO! ✗
  → INVALID immediately!

RESULT: false ✗
```

---

## The Code (With Line-by-Line Explanation)

```java
boolean isValid(String s) {
    // Stack to remember openers we've seen
    Deque<Character> stack = new ArrayDeque<>();
    
    // Process each character
    for (char c : s.toCharArray()) {
        
        // CASE 1: It's an opener → Remember it!
        if (c == '(' || c == '[' || c == '{') {
            stack.push(c);
        } 
        // CASE 2: It's a closer → Try to match!
        else {
            // Edge case: closer but no opener to match
            if (stack.isEmpty()) return false;
            
            // Get the most recent opener
            char top = stack.pop();
            
            // Check if they match
            if (c == ')' && top != '(') return false;
            if (c == ']' && top != '[') return false;
            if (c == '}' && top != '{') return false;
        }
    }
    
    // All openers should be matched (stack empty)
    return stack.isEmpty();
}
```

---

## Cleaner Version (Push Expected Closer)

**The Trick:** Instead of pushing '(' and checking if ')' matches, push ')' directly!

```java
boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();
    
    for (char c : s.toCharArray()) {
        // Push the EXPECTED closer
        if (c == '(') stack.push(')');
        else if (c == '[') stack.push(']');
        else if (c == '{') stack.push('}');
        // For closers: must match what we expect
        else if (stack.isEmpty() || stack.pop() != c) return false;
    }
    
    return stack.isEmpty();
}
```

**Why this is cleaner:** No need for separate matching logic!

---

## Common Traps

| Trap | Example | Why It Fails |
|------|---------|--------------|
| Returning true too early | `"(()"` | Stack not empty at end! |
| Forgetting empty check | `")"` | Pop from empty stack! |
| Wrong order | `"([)]"` | Closers must match in order |

---

## Variations

| Problem | Twist | Key Change |
|---------|-------|------------|
| Valid Parentheses | Basic matching | Just match |
| Longest Valid | Find longest valid substring | Store indices, track length |
| Min Add to Make Valid | Count insertions needed | Count unmatched |
| Remove Invalid | Remove minimum to make valid | BFS/backtracking |

---

## Mind-Map Anchor

```
VALID PARENTHESES
       │
       ▼
┌──────────────────┐
│ Push OPENERS     │
│ Pop on CLOSERS   │
│ Match must be    │
│ MOST RECENT      │
│ Empty at END     │
└──────────────────┘
```

**Memory phrase:** "Push open, pop close, match recent, empty end"

---

# PATTERN 1: Longest Valid Parentheses (LeetCode 32)

## Pattern Recognition Signal

**When you see:** "longest valid", "maximum length", "valid substring"

**Instant thought:** "I need to track POSITIONS to calculate LENGTH → Store INDICES!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: ")()())"

Find the longest substring that has valid parentheses.

Substrings:
- "()" at positions 1-2 → length 2
- "()()" at positions 1-4 → length 4 ← LONGEST!
- "()" at positions 3-4 → length 2
```

### Why Store Indices?

```
To calculate LENGTH, we need POSITIONS!

If we just store '(' characters, we know there's a match,
but we don't know HOW LONG the valid portion is.

By storing INDICES:
- We know WHERE each '(' is
- When we match, we can calculate: current_position - last_unmatched
```

### The Key Insight: Stack Top = Boundary

```
The stack top always represents the BOUNDARY of the current valid substring.

Example: ")()())"
         0123456

After processing:
- Position 0 ')' is unmatched → it's a boundary
- Positions 1-4 "()()" are valid
- Position 5 ')' is unmatched → it's a boundary

Stack stores boundaries (unmatched positions).
Length = current_index - stack_top
```

### The Algorithm in Plain English

```
1. Start with -1 in stack (base boundary before string starts)
2. See '('? → Push its index (potential boundary if unmatched)
3. See ')'? → Pop (try to match)
   - If stack empty after pop: this ')' is unmatched, push its index as new boundary
   - If stack not empty: calculate length = current_index - stack_top
4. Track maximum length
```

---

## Visual Dry Run

**Input:** `")()())"`

```
Initial: stack = [-1]  (base boundary)
         maxLen = 0

i=0, char=')':
  → It's a closer, pop → stack = []
  → Stack is empty! This ')' is unmatched
  → Push 0 as new boundary → stack = [0]
  → maxLen = 0

i=1, char='(':
  → It's an opener, push index 1
  → stack = [0, 1]

i=2, char=')':
  → It's a closer, pop → stack = [0]
  → Stack not empty! We have a valid match
  → Length = 2 - 0 = 2
  → maxLen = max(0, 2) = 2

i=3, char='(':
  → It's an opener, push index 3
  → stack = [0, 3]

i=4, char=')':
  → It's a closer, pop → stack = [0]
  → Stack not empty! Valid match
  → Length = 4 - 0 = 4
  → maxLen = max(2, 4) = 4

i=5, char=')':
  → It's a closer, pop → stack = []
  → Stack is empty! This ')' is unmatched
  → Push 5 as new boundary → stack = [5]
  → maxLen = 4

RESULT: 4 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int longestValidParentheses(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(-1);  // Base boundary (before string starts)
    int maxLen = 0;
    
    for (int i = 0; i < s.length(); i++) {
        if (s.charAt(i) == '(') {
            // Opener: push its index
            stack.push(i);
        } else {
            // Closer: try to match
            stack.pop();
            
            if (stack.isEmpty()) {
                // No match! This ')' becomes new boundary
                stack.push(i);
            } else {
                // Match found! Calculate length
                // Length = current position - last boundary
                maxLen = Math.max(maxLen, i - stack.peek());
            }
        }
    }
    
    return maxLen;
}
```

---

## Why -1 as Base Boundary?

```
Without -1:
  Input: "()"
  i=0 '(': push 0 → stack = [0]
  i=1 ')': pop → stack = []
           Stack empty! Can't calculate length!

With -1:
  Input: "()"
  stack = [-1]
  i=0 '(': push 0 → stack = [-1, 0]
  i=1 ')': pop → stack = [-1]
           Length = 1 - (-1) = 2 ✓
```

---

## Common Traps

| Trap | Why It's Wrong | Fix |
|------|----------------|-----|
| Forgetting -1 base | Can't calculate length for valid prefix | Always start with -1 |
| Storing characters | Can't calculate length | Store indices |
| Not updating boundary | Miss valid substrings after unmatched ')' | Push unmatched ')' index |

---

## Mind-Map Anchor

```
LONGEST VALID PARENTHESES
          │
          ▼
┌─────────────────────┐
│ Store INDICES       │
│ -1 as base boundary │
│ Pop on ')'          │
│ Empty? New boundary │
│ Not empty? Measure! │
│ Length = i - top    │
└─────────────────────┘
```

**Memory phrase:** "Index stack, -1 base, pop and measure, empty means new boundary"

---

# PATTERN 2: Minimum Add to Make Parentheses Valid (LeetCode 921)

## Pattern Recognition Signal

**When you see:** "minimum insertions", "make valid", "balance parentheses"

**Instant thought:** "Count unmatched openers and closers separately!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: "())"

How many parentheses do I need to ADD to make it valid?

Analysis:
- '(' at index 0: waiting for ')'
- ')' at index 1: matches '(' ✓
- ')' at index 2: no '(' to match! Need to add '('

Answer: 1 (add one '(' at the beginning)
```

### The Key Insight

```
Two types of "unmatched":
1. Unmatched '(' → needs ')' added at end
2. Unmatched ')' → needs '(' added at beginning

Answer = count of unmatched '(' + count of unmatched ')'
```

### The Algorithm

```
1. Track unmatchedOpen (count of '(' without ')')
2. Track unmatchedClose (count of ')' without '(')
3. For each '(': increment unmatchedOpen
4. For each ')':
   - If unmatchedOpen > 0: decrement (found a match!)
   - Else: increment unmatchedClose (no '(' to match)
5. Answer = unmatchedOpen + unmatchedClose
```

---

## Visual Dry Run

**Input:** `"())"`

```
Initial: unmatchedOpen = 0, unmatchedClose = 0

char '(':
  It's an opener! unmatchedOpen++
  unmatchedOpen = 1, unmatchedClose = 0

char ')':
  It's a closer! Is there an unmatched '('? YES (unmatchedOpen = 1)
  Match them! unmatchedOpen--
  unmatchedOpen = 0, unmatchedClose = 0

char ')':
  It's a closer! Is there an unmatched '('? NO (unmatchedOpen = 0)
  This ')' has no match! unmatchedClose++
  unmatchedOpen = 0, unmatchedClose = 1

Answer = 0 + 1 = 1 ✓
```

**Another Example:** `"((("`

```
char '(': unmatchedOpen = 1
char '(': unmatchedOpen = 2
char '(': unmatchedOpen = 3

Answer = 3 + 0 = 3 (need to add 3 closing parentheses)
```

---

## The Code (With Line-by-Line Explanation)

```java
int minAddToMakeValid(String s) {
    int unmatchedOpen = 0;   // '(' without matching ')'
    int unmatchedClose = 0;  // ')' without matching '('
    
    for (char c : s.toCharArray()) {
        if (c == '(') {
            unmatchedOpen++;  // New opener, waiting for closer
        } else {
            if (unmatchedOpen > 0) {
                unmatchedOpen--;  // Found a match!
            } else {
                unmatchedClose++;  // No opener to match this closer
            }
        }
    }
    
    // Need to add ')' for each unmatched '('
    // Need to add '(' for each unmatched ')'
    return unmatchedOpen + unmatchedClose;
}
```

---

## Common Traps

| Trap | Example | Why Wrong |
|------|---------|-----------|
| Only counting one type | `"(()"` | Miss unmatched openers |
| Using stack | Overkill | Just need counts! |

---

## Mind-Map Anchor

```
MIN ADD TO MAKE VALID
         │
         ▼
┌─────────────────────────┐
│ Count unmatched '('     │
│ Count unmatched ')'     │
│ Answer = sum of both    │
│ No stack needed!        │
└─────────────────────────┘
```

**Memory phrase:** "Count unmatched opens + unmatched closes"

---

# PATTERN 3: Score of Parentheses (LeetCode 856)

## Pattern Recognition Signal

**When you see:** "score", "nested parentheses", "()" = 1, "(A)" = 2*A

**Instant thought:** "Track score at each depth level → Stack of scores!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Rules:
- "()" has score 1
- "(A)" has score 2 * A (double the inner score)
- "AB" has score A + B (add adjacent scores)

Examples:
- "()" = 1
- "(())" = 2 * 1 = 2
- "()()" = 1 + 1 = 2
- "(()(()))" = 2 * (1 + 2*1) = 2 * 3 = 6
```

### The Key Insight

```
Think of it as DEPTH levels:

Input: "(()(()))"

Depth 0: outer level
Depth 1: first '(' takes us to depth 1
Depth 2: second '(' takes us to depth 2

When we see ')':
- If inner score is 0: this is "()" → score = 1
- If inner score > 0: this is "(A)" → score = 2 * inner

Stack tracks the score at each depth!
```

### The Algorithm

```
1. Start with stack = [0] (score at depth 0)
2. See '(': push 0 (new depth, score starts at 0)
3. See ')': 
   - Pop inner score
   - Pop outer score
   - Push: outer + max(2*inner, 1)
     (If inner=0, it's "()"=1; else it's "(A)"=2*inner)
4. Final answer is stack.pop()
```

---

## Visual Dry Run

**Input:** `"(())"`

```
Initial: stack = [0]  (score at depth 0)

char '(':
  Going deeper! Push 0
  stack = [0, 0]
  "Depth 0 score: 0, Depth 1 score: 0"

char '(':
  Going deeper! Push 0
  stack = [0, 0, 0]
  "Depth 0: 0, Depth 1: 0, Depth 2: 0"

char ')':
  Coming back up!
  inner = pop() = 0  (score at depth 2)
  outer = pop() = 0  (score at depth 1)
  
  inner = 0, so this is "()" → score = 1
  Push: outer + max(2*0, 1) = 0 + 1 = 1
  
  stack = [0, 1]
  "Depth 0: 0, Depth 1: 1"

char ')':
  Coming back up!
  inner = pop() = 1  (score at depth 1)
  outer = pop() = 0  (score at depth 0)
  
  inner = 1 > 0, so this is "(A)" → score = 2*1 = 2
  Push: outer + max(2*1, 1) = 0 + 2 = 2
  
  stack = [2]

Answer = pop() = 2 ✓
```

**Another Example:** `"()()"`

```
stack = [0]

'(': stack = [0, 0]
')': inner=0, outer=0, push 0+1=1 → stack = [1]
'(': stack = [1, 0]
')': inner=0, outer=1, push 1+1=2 → stack = [2]

Answer = 2 ✓ (which is 1 + 1)
```

---

## The Code (With Line-by-Line Explanation)

```java
int scoreOfParentheses(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(0);  // Score at depth 0
    
    for (char c : s.toCharArray()) {
        if (c == '(') {
            // Going deeper, new depth starts with score 0
            stack.push(0);
        } else {
            // Coming back up
            int inner = stack.pop();  // Score inside this pair
            int outer = stack.pop();  // Score at outer level
            
            // "()" = 1, "(A)" = 2*A
            // max(2*inner, 1) handles both cases:
            // - If inner=0: max(0, 1) = 1 (it's "()")
            // - If inner>0: max(2*inner, 1) = 2*inner (it's "(A)")
            stack.push(outer + Math.max(2 * inner, 1));
        }
    }
    
    return stack.pop();
}
```

---

## Common Traps

| Trap | Example | Why Wrong |
|------|---------|-----------|
| Forgetting "()" = 1 | Inner score 0 | Use `max(2*inner, 1)` |
| Not adding to outer | "()()" should be 2 | `outer + ...` |

---

## Mind-Map Anchor

```
SCORE OF PARENTHESES
         │
         ▼
┌─────────────────────────┐
│ Stack = scores at depth │
│ '(' = push 0            │
│ ')' = pop inner, outer  │
│ "()" = 1                │
│ "(A)" = 2*A             │
│ "AB" = A + B            │
└─────────────────────────┘
```

**Memory phrase:** "Stack of depth scores, () is 1, (A) is 2A, AB is A+B"

---

# MONOTONIC STACK FAMILY

---

# PATTERN 5: Daily Temperatures (LeetCode 739)

## Pattern Recognition Signal

**When you see:** "next greater", "next warmer", "days until", "how many until bigger"

**Instant thought:** "Next Greater Element problem → Monotonic DECREASING stack!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: [73, 74, 75, 71, 69, 72, 76, 73]

For each day, find how many days until a WARMER temperature.

Day 0 (73°): Next warmer is Day 1 (74°) → 1 day
Day 1 (74°): Next warmer is Day 2 (75°) → 1 day
Day 2 (75°): Next warmer is Day 6 (76°) → 4 days
Day 3 (71°): Next warmer is Day 5 (72°) → 2 days
```

### Why Monotonic Stack?

```
BRUTE FORCE: For each day, scan forward. O(n²) — too slow!

SMART WAY: Think differently:
- Some days are "waiting" for a warmer day
- When a warm day arrives, ALL cooler days waiting get their answer!

Example:
  Days waiting: [75°, 71°, 69°]
  Day 72° arrives:
    - 69° found answer! (72° > 69°)
    - 71° found answer! (72° > 71°)
    - 75° still waiting (72° < 75°)

This is EXACTLY what a stack does!
```

### Why Store INDICES (not temperatures)?

```
We need DISTANCE (how many days).

Store temps: stack = [75, 71, 69] → Can't calculate distance!
Store indices: stack = [2, 3, 4] → Distance = 5 - 4 = 1 day ✓
```

### Why DECREASING Stack?

```
We want NEXT GREATER (warmer).

Decreasing: [75, 71, 69] → All waiting for something BIGGER
When 72 arrives: 72 > 69 ✓, 72 > 71 ✓, 72 < 75 ✗
Pop 69 and 71 (found answer), 75 keeps waiting.
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `temps = [73, 74, 75, 71, 69, 72, 76, 73]`

```
Initial: stack = [], result = [0,0,0,0,0,0,0,0]

═══════════════════════════════════════════════════════════

Day 0 (73°):
  Stack empty, push 0 → stack = [0]
  "Day 0 waiting for warmer"

═══════════════════════════════════════════════════════════

Day 1 (74°):
  74 > 73? YES! Pop 0, result[0] = 1-0 = 1
  Push 1 → stack = [1]
  Result: [1,0,0,0,0,0,0,0]

═══════════════════════════════════════════════════════════

Day 2 (75°):
  75 > 74? YES! Pop 1, result[1] = 2-1 = 1
  Push 2 → stack = [2]
  Result: [1,1,0,0,0,0,0,0]

═══════════════════════════════════════════════════════════

Day 3 (71°):
  71 > 75? NO! Push 3 → stack = [2,3]
  "Days 2,3 both waiting"

═══════════════════════════════════════════════════════════

Day 4 (69°):
  69 > 71? NO! Push 4 → stack = [2,3,4]

═══════════════════════════════════════════════════════════

Day 5 (72°):
  72 > 69? YES! Pop 4, result[4] = 5-4 = 1
  72 > 71? YES! Pop 3, result[3] = 5-3 = 2
  72 > 75? NO! Push 5 → stack = [2,5]
  Result: [1,1,0,2,1,0,0,0]

═══════════════════════════════════════════════════════════

Day 6 (76°):
  76 > 72? YES! Pop 5, result[5] = 6-5 = 1
  76 > 75? YES! Pop 2, result[2] = 6-2 = 4
  Push 6 → stack = [6]
  Result: [1,1,4,2,1,1,0,0]

═══════════════════════════════════════════════════════════

Day 7 (73°):
  73 > 76? NO! Push 7 → stack = [6,7]

═══════════════════════════════════════════════════════════

End: Days 6,7 never found warmer → stays 0

FINAL: [1, 1, 4, 2, 1, 1, 0, 0] ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int[] dailyTemperatures(int[] temps) {
    int n = temps.length;
    int[] result = new int[n];  // Default 0 (no warmer day)
    
    // Stack stores INDICES of days waiting for warmer
    Deque<Integer> stack = new ArrayDeque<>();
    
    for (int i = 0; i < n; i++) {
        // While current is warmer than waiting days...
        while (!stack.isEmpty() && temps[stack.peek()] < temps[i]) {
            int waitingDay = stack.pop();  // Found its answer!
            result[waitingDay] = i - waitingDay;  // Distance
        }
        stack.push(i);  // Current day now waits
    }
    
    return result;  // Remaining never found warmer (stays 0)
}
```

---

## Why O(n)?

```
Each index: pushed ONCE, popped AT MOST ONCE
Total = n pushes + n pops = 2n = O(n)
```

---

## Common Traps

| Trap | Why Wrong |
|------|-----------|
| Store temps not indices | Can't calculate distance |
| Increasing stack | Wrong! Need decreasing for "next greater" |

---

## Mind-Map Anchor

```
DAILY TEMPERATURES
        │
        ▼
┌────────────────────────┐
│ DECREASING stack       │
│ Store INDICES          │
│ Pop when warmer        │
│ Distance = i - idx     │
│ Remaining = 0          │
└────────────────────────┘
```

**Memory phrase:** "Decreasing stack, store indices, pop when bigger"

---

# PATTERN 6: Next Greater Element I (LeetCode 496)

## Pattern Recognition Signal

**When you see:** "next greater element", "find in another array", "subset lookup"

**Instant thought:** "Build NGE map from nums2, then lookup for nums1!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
nums1 = [4, 1, 2]  (subset of nums2)
nums2 = [1, 3, 4, 2]

For each element in nums1, find its next greater element in nums2.

Element 4: In nums2, after 4 comes [2]. Any > 4? No → -1
Element 1: In nums2, after 1 comes [3, 4, 2]. First > 1? 3 → 3
Element 2: In nums2, after 2 comes []. Nothing → -1

Result: [-1, 3, -1]
```

### The Strategy

```
Step 1: Build a map of "element → its NGE" using nums2
Step 2: Look up each nums1 element in the map

Why this works:
- nums1 is a subset of nums2
- We precompute NGE for ALL elements in nums2
- Then just look up what we need
```

### Why Store VALUES (not indices)?

```
Unlike Daily Temperatures, we don't need distance.
We just need to know WHAT the next greater element IS.

So we can store values directly and use a HashMap!
```

---

## Visual Dry Run

**Input:** `nums1 = [4, 1, 2], nums2 = [1, 3, 4, 2]`

```
Building NGE map from nums2:

Element 1:
  Stack empty, push 1 → stack = [1]

Element 3:
  3 > 1? YES! Pop 1, map[1] = 3
  Stack empty, push 3 → stack = [3]
  Map: {1 → 3}

Element 4:
  4 > 3? YES! Pop 3, map[3] = 4
  Stack empty, push 4 → stack = [4]
  Map: {1 → 3, 3 → 4}

Element 2:
  2 > 4? NO! Push 2 → stack = [4, 2]
  Map: {1 → 3, 3 → 4}

End: 4 and 2 have no NGE (not in map = -1)

═══════════════════════════════════════════════════════════

Lookup for nums1:
  4 → not in map → -1
  1 → map[1] = 3 → 3
  2 → not in map → -1

Result: [-1, 3, -1] ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int[] nextGreaterElement(int[] nums1, int[] nums2) {
    // Map: element → its next greater element
    Map<Integer, Integer> nge = new HashMap<>();
    Deque<Integer> stack = new ArrayDeque<>();
    
    // Build NGE map from nums2
    for (int num : nums2) {
        // Pop all elements smaller than current
        while (!stack.isEmpty() && stack.peek() < num) {
            nge.put(stack.pop(), num);  // num is their NGE
        }
        stack.push(num);
    }
    // Elements left in stack have no NGE (not in map)
    
    // Look up each nums1 element
    int[] result = new int[nums1.length];
    for (int i = 0; i < nums1.length; i++) {
        result[i] = nge.getOrDefault(nums1[i], -1);
    }
    return result;
}
```

---

## Common Traps

| Trap | Why Wrong |
|------|-----------|
| Storing indices | Don't need distance, just values |
| Searching nums2 for each nums1 | O(n²), use map for O(n) |

---

## Mind-Map Anchor

```
NEXT GREATER ELEMENT I
          │
          ▼
┌─────────────────────────┐
│ Build NGE map from nums2│
│ Decreasing stack        │
│ Store VALUES (not idx)  │
│ Lookup for nums1        │
│ Default = -1            │
└─────────────────────────┘
```

**Memory phrase:** "Build map from nums2, lookup for nums1"

---

# PATTERN 7: Next Greater Element II (LeetCode 503)

## Pattern Recognition Signal

**When you see:** "circular array", "wrap around", "next greater circular"

**Instant thought:** "Process array TWICE (2n elements) with modulo!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
nums = [1, 2, 1]  (circular)

For element at index 2 (value 1):
  Looking right: nothing after it
  But it's CIRCULAR! Wrap around to beginning: [1, 2, ...]
  Next greater = 2

Result: [2, -1, 2]
```

### The Trick: Process Twice!

```
Circular array: [1, 2, 1]

Conceptually process: [1, 2, 1, 1, 2, 1]
                       0  1  2  3  4  5
                       
Use i % n to get actual index:
  i=0 → idx=0, i=1 → idx=1, i=2 → idx=2
  i=3 → idx=0, i=4 → idx=1, i=5 → idx=2

Only PUSH in first pass (i < n) to avoid duplicates!
```

---

## Visual Dry Run

**Input:** `nums = [1, 2, 1]`

```
Initial: stack = [], result = [-1, -1, -1]

═══════════════════════════════════════════════════════════
FIRST PASS (i = 0, 1, 2)
═══════════════════════════════════════════════════════════

i=0, idx=0, num=1:
  Stack empty, push 0 → stack = [0]

i=1, idx=1, num=2:
  2 > nums[0]=1? YES! Pop 0, result[0] = 2
  Push 1 → stack = [1]
  Result: [2, -1, -1]

i=2, idx=2, num=1:
  1 > nums[1]=2? NO!
  Push 2 → stack = [1, 2]

═══════════════════════════════════════════════════════════
SECOND PASS (i = 3, 4, 5) - Wrap around!
═══════════════════════════════════════════════════════════

i=3, idx=0, num=1:
  1 > nums[2]=1? NO!
  Don't push (i >= n)

i=4, idx=1, num=2:
  2 > nums[2]=1? YES! Pop 2, result[2] = 2
  2 > nums[1]=2? NO!
  Don't push (i >= n)
  Result: [2, -1, 2]

i=5, idx=2, num=1:
  1 > nums[1]=2? NO!
  Don't push

═══════════════════════════════════════════════════════════

Index 1 (value 2) never found greater → stays -1

FINAL: [2, -1, 2] ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int[] nextGreaterElements(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);  // Default: no NGE
    Deque<Integer> stack = new ArrayDeque<>();
    
    // Process 2n elements (two passes for circular)
    for (int i = 0; i < 2 * n; i++) {
        int idx = i % n;  // Actual index in array
        
        // Pop all elements smaller than current
        while (!stack.isEmpty() && nums[stack.peek()] < nums[idx]) {
            result[stack.pop()] = nums[idx];
        }
        
        // Only push in first pass (avoid duplicates)
        if (i < n) {
            stack.push(idx);
        }
    }
    
    return result;
}
```

---

## Common Traps

| Trap | Why Wrong |
|------|-----------|
| Only one pass | Miss wrap-around cases |
| Push in second pass | Duplicate indices in stack |
| Forget `i % n` | Index out of bounds |

---

## Mind-Map Anchor

```
NEXT GREATER ELEMENT II (CIRCULAR)
              │
              ▼
┌─────────────────────────────┐
│ Process 2n elements         │
│ idx = i % n                 │
│ Push only in first pass     │
│ Second pass catches wrap    │
└─────────────────────────────┘
```

**Memory phrase:** "2n iterations, modulo index, push only first pass"

---

# PATTERN 9: Largest Rectangle in Histogram (LeetCode 84)

## Pattern Recognition Signal

**When you see:** "largest rectangle", "histogram", "maximum area"

**Instant thought:** "For each bar, find boundaries → Monotonic INCREASING stack!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
heights = [2, 1, 5, 6, 2, 3]

     ┌───┐
     │   │
 ┌───┤   │
 │   │   │       ┌───┐
 │   │   │   ┌───┤   │
─┴───┴───┴───┴───┴───┴───
  2   1   5   6   2   3

Find largest rectangle under the bars.

For bar height 5 (index 2):
- Left boundary: index 1 (height 1 < 5)
- Right boundary: index 4 (height 2 < 5)
- Width = 4 - 1 - 1 = 2
- Area = 5 × 2 = 10
```

### Why INCREASING Stack?

```
We need: first SHORTER bar on LEFT and RIGHT

Increasing stack: [1, 5, 6] (heights increasing)

When SHORTER bar (height 2) arrives:
- 6 can't extend right! Pop → right boundary = current
- 5 can't extend right! Pop → right boundary = current
- 1 < 2, stop. 1 can still extend.

When we pop, we know BOTH boundaries!
- Right = current index
- Left = new stack top
```

### The Key Formula

```
When we pop bar at index j:

Right boundary = i (current shorter bar)
Left boundary = stack.peek() (previous shorter bar)

Width = i - stack.peek() - 1

If stack empty: Width = i (extends to left edge)

Area = height × width
```

### The Sentinel Trick

```
Problem: After loop, bars might still be in stack!

Solution: Add height 0 at end (sentinel).
          0 is shorter than everything → forces all to pop.

for (int i = 0; i <= n; i++) {  // <= n, not < n
    int h = (i == n) ? 0 : heights[i];  // Sentinel
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `heights = [2, 1, 5, 6, 2, 3]`

```
Initial: stack = [], maxArea = 0

═══════════════════════════════════════════════════════════

i=0, h=2:
  Stack empty, push 0 → stack = [0]

═══════════════════════════════════════════════════════════

i=1, h=1:
  1 < 2? YES! Bar 0 can't extend right!
  
  Pop 0: height=2, stack empty → width=1
         area = 2×1 = 2, maxArea = 2
  
  Push 1 → stack = [1]

═══════════════════════════════════════════════════════════

i=2, h=5:
  5 > 1? YES, push 2 → stack = [1,2]

═══════════════════════════════════════════════════════════

i=3, h=6:
  6 > 5? YES, push 3 → stack = [1,2,3]

═══════════════════════════════════════════════════════════

i=4, h=2:
  2 < 6? YES! Pop 3: height=6, width=4-2-1=1
                     area=6, maxArea=6
  
  2 < 5? YES! Pop 2: height=5, width=4-1-1=2
                     area=10 ⭐, maxArea=10
  
  2 > 1? YES, stop. Push 4 → stack = [1,4]

═══════════════════════════════════════════════════════════

i=5, h=3:
  3 > 2? YES, push 5 → stack = [1,4,5]

═══════════════════════════════════════════════════════════

i=6, h=0 (SENTINEL):
  0 < 3? Pop 5: height=3, width=6-4-1=1, area=3
  0 < 2? Pop 4: height=2, width=6-1-1=4, area=8
  0 < 1? Pop 1: height=1, stack empty → width=6, area=6

═══════════════════════════════════════════════════════════

FINAL: maxArea = 10 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int largestRectangleArea(int[] heights) {
    int n = heights.length;
    Deque<Integer> stack = new ArrayDeque<>();
    int maxArea = 0;
    
    // Process n+1 elements (sentinel at end)
    for (int i = 0; i <= n; i++) {
        // Sentinel: height 0 forces all to pop
        int h = (i == n) ? 0 : heights[i];
        
        // Pop bars that can't extend right
        while (!stack.isEmpty() && heights[stack.peek()] > h) {
            int height = heights[stack.pop()];
            
            // Width = right - left - 1
            // If stack empty, extends to left edge
            int width = stack.isEmpty() ? i : i - stack.peek() - 1;
            
            maxArea = Math.max(maxArea, height * width);
        }
        
        stack.push(i);
    }
    
    return maxArea;
}
```

---

## Common Traps

| Trap | Fix |
|------|-----|
| Forgetting sentinel | Use `i <= n` with height 0 |
| Wrong width formula | `width = i - stack.peek() - 1` |
| Empty stack crash | Check `stack.isEmpty() ? i : ...` |
| Decreasing stack | Must be INCREASING! |

---

## Mind-Map Anchor

```
LARGEST RECTANGLE
        │
        ▼
┌──────────────────────────┐
│ INCREASING stack         │
│ Store INDICES            │
│ Pop when shorter arrives │
│ Width = i - top - 1      │
│ SENTINEL (h=0) at end    │
└──────────────────────────┘
```

**Memory phrase:** "Increasing stack, pop when shorter, sentinel flushes all"

maxArea = 10 ✓
```

## The Width Calculation Explained

```
When we pop index j with height h:
- Current index i is the FIRST shorter bar to the RIGHT
- New stack top k is the FIRST shorter bar to the LEFT
- Bar h can extend from k+1 to i-1
- Width = (i-1) - (k+1) + 1 = i - k - 1

If stack is empty after pop:
- No shorter bar to the left
- Width extends from 0 to i-1
- Width = i
```

## Mind-Map Anchor

**increasing stack · sentinel 0 at end · pop gives both boundaries · width = i - top - 1**

---

# PATTERN 10: Trapping Rain Water (LeetCode 42)

## Pattern Recognition Signal

**When you see:** "trapping water", "rain water", "elevation map"

**Instant thought:** "Find valleys between walls → Stack approach!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
height = [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]

Visualize:
       █
   █   ██ █
 █ ██ █████ █
─┴─┴──┴─┴───┴─

Water fills the valleys:
       █
   █≈≈≈██≈█
 █≈██≈█████≈█

Total water trapped = 6 units
```

### The Stack Approach Intuition

```
Think of it as finding "valleys":
- A valley needs a LEFT wall, a BOTTOM, and a RIGHT wall
- When we find a taller bar (right wall), we can calculate water

Stack stores indices of bars in DECREASING order.
When a taller bar arrives:
1. Pop the bottom of the valley
2. The new stack top is the left wall
3. Current bar is the right wall
4. Water = width × min(left, right) - bottom
```

### The Key Formula

```
When we pop index 'bottom':
  left wall = stack.peek() (previous bar)
  right wall = current index i
  
  width = i - left - 1
  height = min(height[left], height[i]) - height[bottom]
  water = width × height
```

---

## Visual Dry Run

**Input:** `height = [0, 1, 0, 2]`

```
Initial: stack = [], water = 0

i=0, h=0:
  Push 0 → stack = [0]

i=1, h=1:
  1 > 0? YES! Pop 0 (bottom)
  Stack empty → no left wall, can't trap water
  Push 1 → stack = [1]

i=2, h=0:
  0 > 1? NO!
  Push 2 → stack = [1, 2]

i=3, h=2:
  2 > 0? YES! Pop 2 (bottom = 0)
  Stack not empty! left = 1, height[1] = 1
  width = 3 - 1 - 1 = 1
  h = min(1, 2) - 0 = 1
  water += 1 × 1 = 1
  
  2 > 1? YES! Pop 1 (bottom = 1)
  Stack empty → no left wall
  Push 3 → stack = [3]

FINAL: water = 1 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int trap(int[] height) {
    Deque<Integer> stack = new ArrayDeque<>();
    int water = 0;
    
    for (int i = 0; i < height.length; i++) {
        // While current bar is taller than stack top (found right wall)
        while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
            int bottom = stack.pop();  // Valley bottom
            
            if (stack.isEmpty()) break;  // No left wall
            
            int left = stack.peek();  // Left wall index
            int width = i - left - 1;  // Distance between walls
            int h = Math.min(height[left], height[i]) - height[bottom];
            water += width * h;
        }
        stack.push(i);
    }
    
    return water;
}
```

---

## Common Traps

| Trap | Why Wrong |
|------|-----------|
| Forget empty check after pop | No left wall = can't trap |
| Wrong height formula | Must subtract bottom height |

---

## Mind-Map Anchor

```
TRAPPING RAIN WATER
        │
        ▼
┌─────────────────────────┐
│ Stack finds valleys     │
│ Pop = bottom of valley  │
│ Stack top = left wall   │
│ Current = right wall    │
│ water = w × (min-bottom)│
└─────────────────────────┘
```

**Memory phrase:** "Pop bottom, peek left, current right, water = width × (min walls - bottom)"

---

# PATTERN 11: Online Stock Span (LeetCode 901)

## Pattern Recognition Signal

**When you see:** "consecutive days", "span", "less than or equal"

**Instant thought:** "Absorb smaller elements' spans → Decreasing stack with (price, span)!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Prices: [100, 80, 60, 70, 60, 75, 85]

Span = consecutive days (including today) where price ≤ today's price

Day 0 (100): No previous days → span = 1
Day 1 (80):  80 < 100 → span = 1
Day 2 (60):  60 < 80 → span = 1
Day 3 (70):  70 > 60 → span = 2 (today + day 2)
Day 4 (60):  60 < 70 → span = 1
Day 5 (75):  75 > 60, 75 > 70, 75 < 80 → span = 4 (days 2,3,4,5)
Day 6 (85):  85 > 75, 85 > 80, 85 < 100 → span = 6

Result: [1, 1, 1, 2, 1, 4, 6]
```

### The Key Insight: Absorbing Spans

```
When a higher price arrives, it "absorbs" all smaller prices!

Day 5 (75):
  Stack: [(100,1), (80,1), (70,2), (60,1)]
  
  75 > 60? YES! Pop (60,1), absorb span: 1 + 1 = 2
  75 > 70? YES! Pop (70,2), absorb span: 2 + 2 = 4
  75 < 80? NO! Stop.
  
  Push (75, 4)
  Stack: [(100,1), (80,1), (75,4)]
```

---

## Visual Dry Run

**Prices arriving:** `100, 80, 60, 70, 60, 75, 85`

```
Price 100:
  Stack empty, span = 1
  Push (100, 1) → stack = [(100,1)]
  Return 1

Price 80:
  80 ≤ 100? NO! span = 1
  Push (80, 1) → stack = [(100,1), (80,1)]
  Return 1

Price 60:
  60 ≤ 80? NO! span = 1
  Push (60, 1) → stack = [(100,1), (80,1), (60,1)]
  Return 1

Price 70:
  70 > 60? YES! Pop (60,1), span = 1 + 1 = 2
  70 ≤ 80? NO! Stop.
  Push (70, 2) → stack = [(100,1), (80,1), (70,2)]
  Return 2

Price 60:
  60 ≤ 70? NO! span = 1
  Push (60, 1) → stack = [(100,1), (80,1), (70,2), (60,1)]
  Return 1

Price 75:
  75 > 60? YES! Pop (60,1), span = 1 + 1 = 2
  75 > 70? YES! Pop (70,2), span = 2 + 2 = 4
  75 ≤ 80? NO! Stop.
  Push (75, 4) → stack = [(100,1), (80,1), (75,4)]
  Return 4

Price 85:
  85 > 75? YES! Pop (75,4), span = 1 + 4 = 5
  85 > 80? YES! Pop (80,1), span = 5 + 1 = 6
  85 ≤ 100? NO! Stop.
  Push (85, 6) → stack = [(100,1), (85,6)]
  Return 6

Results: [1, 1, 1, 2, 1, 4, 6] ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
class StockSpanner {
    // Stack stores (price, span) pairs
    Deque<int[]> stack;
    
    public StockSpanner() {
        stack = new ArrayDeque<>();
    }
    
    public int next(int price) {
        int span = 1;  // At minimum, today counts
        
        // Absorb all smaller prices' spans
        while (!stack.isEmpty() && stack.peek()[0] <= price) {
            span += stack.pop()[1];  // Add their span to ours
        }
        
        stack.push(new int[]{price, span});
        return span;
    }
}
```

---

## Mind-Map Anchor

```
STOCK SPAN
    │
    ▼
┌─────────────────────────┐
│ Store (price, span)     │
│ Absorb smaller spans    │
│ Decreasing stack        │
│ span += popped.span     │
└─────────────────────────┘
```

**Memory phrase:** "Store price+span, absorb smaller, add their spans"

---

# PATTERN 12: Sum of Subarray Minimums (LeetCode 907)

## Pattern Recognition Signal

**When you see:** "sum of minimums", "all subarrays", "contribution"

**Instant thought:** "Count how many subarrays each element is minimum of!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
arr = [3, 1, 2, 4]

All subarrays and their minimums:
[3] → min = 3
[3,1] → min = 1
[3,1,2] → min = 1
[3,1,2,4] → min = 1
[1] → min = 1
[1,2] → min = 1
[1,2,4] → min = 1
[2] → min = 2
[2,4] → min = 2
[4] → min = 4

Sum = 3 + 1 + 1 + 1 + 1 + 1 + 1 + 2 + 2 + 4 = 17
```

### The Key Insight: Contribution Counting

```
Instead of finding min of each subarray (O(n²)),
count how many subarrays each element is the minimum of!

For element at index i:
- left[i] = distance to previous SMALLER element
- right[i] = distance to next SMALLER element

Element i is minimum in: left[i] × right[i] subarrays

Why? 
- Can extend left by 0, 1, 2, ... left[i]-1 positions
- Can extend right by 0, 1, 2, ... right[i]-1 positions
- Total combinations = left[i] × right[i]
```

### Example

```
arr = [3, 1, 2, 4]
       0  1  2  3

For element 1 at index 1:
- Previous smaller: none (left boundary = -1)
- Next smaller: none (right boundary = 4)
- left[1] = 1 - (-1) = 2
- right[1] = 4 - 1 = 3
- Subarrays where 1 is min = 2 × 3 = 6
- Contribution = 1 × 6 = 6 ✓

(The 6 subarrays: [3,1], [3,1,2], [3,1,2,4], [1], [1,2], [1,2,4])
```

---

## Visual Dry Run

**Input:** `arr = [3, 1, 2, 4]`

```
Step 1: Find previous smaller (left boundary)

i=0 (3): Stack empty → left[0] = 0 - (-1) = 1
         Push 0 → stack = [0]

i=1 (1): 1 < 3? YES! Pop 0
         Stack empty → left[1] = 1 - (-1) = 2
         Push 1 → stack = [1]

i=2 (2): 2 < 1? NO!
         left[2] = 2 - 1 = 1
         Push 2 → stack = [1, 2]

i=3 (4): 4 < 2? NO!
         left[3] = 3 - 2 = 1
         Push 3 → stack = [1, 2, 3]

left = [1, 2, 1, 1]

═══════════════════════════════════════════════════════════

Step 2: Find next smaller (right boundary) - scan from right

i=3 (4): Stack empty → right[3] = 4 - 3 = 1
         Push 3 → stack = [3]

i=2 (2): 2 < 4? YES! Pop 3
         Stack empty → right[2] = 4 - 2 = 2
         Push 2 → stack = [2]

i=1 (1): 1 < 2? YES! Pop 2
         Stack empty → right[1] = 4 - 1 = 3
         Push 1 → stack = [1]

i=0 (3): 3 < 1? NO!
         right[0] = 1 - 0 = 1
         Push 0 → stack = [1, 0]

right = [1, 3, 2, 1]

═══════════════════════════════════════════════════════════

Step 3: Calculate contributions

i=0: arr[0] × left[0] × right[0] = 3 × 1 × 1 = 3
i=1: arr[1] × left[1] × right[1] = 1 × 2 × 3 = 6
i=2: arr[2] × left[2] × right[2] = 2 × 1 × 2 = 4
i=3: arr[3] × left[3] × right[3] = 4 × 1 × 1 = 4

Sum = 3 + 6 + 4 + 4 = 17 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int sumSubarrayMins(int[] arr) {
    int n = arr.length;
    int MOD = 1_000_000_007;
    int[] left = new int[n];   // Distance to previous smaller
    int[] right = new int[n];  // Distance to next smaller
    
    Deque<Integer> stack = new ArrayDeque<>();
    
    // Find previous smaller (use >= to handle duplicates)
    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
            stack.pop();
        }
        left[i] = stack.isEmpty() ? i + 1 : i - stack.peek();
        stack.push(i);
    }
    
    stack.clear();
    
    // Find next smaller (use > to handle duplicates differently)
    for (int i = n - 1; i >= 0; i--) {
        while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
            stack.pop();
        }
        right[i] = stack.isEmpty() ? n - i : stack.peek() - i;
        stack.push(i);
    }
    
    // Calculate sum of contributions
    long result = 0;
    for (int i = 0; i < n; i++) {
        result = (result + (long) arr[i] * left[i] * right[i]) % MOD;
    }
    
    return (int) result;
}
```

---

## Handling Duplicates

```
Why >= for left and > for right?

arr = [1, 1, 1]

If we use > for both:
  Element at index 1 would count subarrays that include index 0 and 2
  But index 0 and 2 would ALSO count those same subarrays!
  → Double counting!

Solution: Use >= for one direction, > for the other
  This ensures each subarray is counted exactly once.
```

---

## Mind-Map Anchor

```
SUM OF SUBARRAY MINIMUMS
           │
           ▼
┌──────────────────────────┐
│ Contribution counting    │
│ left = prev smaller dist │
│ right = next smaller dist│
│ count = left × right     │
│ contrib = val × count    │
│ Handle duplicates: >=, > │
└──────────────────────────┘
```

**Memory phrase:** "Count subarrays where each is min: left × right"

---

# EXPRESSION EVALUATION FAMILY

---

# PATTERN 13: Decode String (LeetCode 394)

## Pattern Recognition Signal

**When you see:** "decode", "nested brackets", "repeat k times", "k[string]"

**Instant thought:** "Nested structure → Save state on '[', restore on ']' → CONTEXT stack!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: "3[a2[c]]"

Decode the nested pattern:
- 2[c] = "cc"
- a2[c] = a + cc = "acc"
- 3[acc] = "accaccacc"

It's like Russian nesting dolls — solve inner first, then outer!
```

### Why Stack?

```
When you see '[': You're going DEEPER into nesting
  → Save your current work (like bookmarking a page)
  
When you see ']': You're coming back UP
  → Restore your saved work and combine

Stack = perfect for saving/restoring nested state!
```

### What to Store?

```
At each '[', we need to remember:
1. The COUNT (how many times to repeat)
2. The STRING built so far (before this bracket)

So we use TWO stacks (or one stack of pairs):
- countStack: stores the repeat counts
- stringStack: stores the strings before each '['
```

### The Algorithm

```
1. See digit? Build the number (might be multi-digit like "12")
2. See '['? 
   - PUSH count and current string
   - Reset: count=0, string=""
3. See ']'?
   - POP count and previous string
   - current = previous + current.repeat(count)
4. See letter? Append to current string
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `"3[a2[c]]"`

```
Initial: countStack = [], stringStack = []
         current = "", k = 0

═══════════════════════════════════════════════════════════

char '3':
  It's a digit! k = 0*10 + 3 = 3
  
  State: k=3, current=""

═══════════════════════════════════════════════════════════

char '[':
  Going deeper! Save current state.
  
  PUSH: countStack = [3], stringStack = [""]
  RESET: k = 0, current = ""
  
  "Saved: repeat 3 times, previous string was empty"

═══════════════════════════════════════════════════════════

char 'a':
  It's a letter! current = "" + "a" = "a"
  
  State: k=0, current="a"

═══════════════════════════════════════════════════════════

char '2':
  It's a digit! k = 0*10 + 2 = 2
  
  State: k=2, current="a"

═══════════════════════════════════════════════════════════

char '[':
  Going deeper again! Save current state.
  
  PUSH: countStack = [3, 2], stringStack = ["", "a"]
  RESET: k = 0, current = ""
  
  "Saved: repeat 2 times, previous string was 'a'"

═══════════════════════════════════════════════════════════

char 'c':
  It's a letter! current = "" + "c" = "c"
  
  State: k=0, current="c"

═══════════════════════════════════════════════════════════

char ']':
  Coming back up! Restore and combine.
  
  POP: count = 2, prevString = "a"
  COMBINE: current = "a" + "c".repeat(2) = "a" + "cc" = "acc"
  
  countStack = [3], stringStack = [""]
  State: current = "acc"

═══════════════════════════════════════════════════════════

char ']':
  Coming back up again!
  
  POP: count = 3, prevString = ""
  COMBINE: current = "" + "acc".repeat(3) = "accaccacc"
  
  countStack = [], stringStack = []
  State: current = "accaccacc"

═══════════════════════════════════════════════════════════

FINAL: "accaccacc" ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
String decodeString(String s) {
    Deque<Integer> countStack = new ArrayDeque<>();    // Stores repeat counts
    Deque<StringBuilder> stringStack = new ArrayDeque<>();  // Stores previous strings
    StringBuilder current = new StringBuilder();  // Current string being built
    int k = 0;  // Current repeat count
    
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            // Build multi-digit number: "12" = 1*10 + 2
            k = k * 10 + (c - '0');
        } 
        else if (c == '[') {
            // Going deeper! Save state and reset
            countStack.push(k);
            stringStack.push(current);
            current = new StringBuilder();
            k = 0;
        } 
        else if (c == ']') {
            // Coming back up! Restore and combine
            StringBuilder decoded = current;
            current = stringStack.pop();  // Get previous string
            int repeat = countStack.pop();  // Get repeat count
            for (int i = 0; i < repeat; i++) {
                current.append(decoded);  // Append repeated string
            }
        } 
        else {
            // Regular letter, just append
            current.append(c);
        }
    }
    
    return current.toString();
}
```

---

## Common Traps

| Trap | Example | Fix |
|------|---------|-----|
| Single-digit only | "12[a]" should repeat 12 times | `k = k*10 + digit` |
| Forgetting to reset | After '[', k and current must reset | Reset both! |
| Wrong combine order | Should be `prev + inner.repeat(k)` | Append to previous |

---

## Mind-Map Anchor

```
DECODE STRING
      │
      ▼
┌─────────────────────────┐
│ Two stacks: count+string│
│ '[' = PUSH and reset    │
│ ']' = POP and combine   │
│ Multi-digit: k*10+digit │
│ Letter: append          │
└─────────────────────────┘
```

**Memory phrase:** "Push on open, pop on close, combine = prev + inner.repeat(k)"

---

# PATTERN 14: Basic Calculator (LeetCode 224)

## Pattern Recognition Signal

**When you see:** "calculator", "evaluate expression", "parentheses", "+ and -"

**Instant thought:** "Track result and sign, save state on '(', restore on ')'!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: "(1+(4+5+2)-3)+(6+8)"

Evaluate the expression with:
- Addition (+)
- Subtraction (-)
- Parentheses ()
- Spaces (ignore)

= (1 + 11 - 3) + 14
= 9 + 14
= 23
```

### The Key Insight

```
Two things to track:
1. result = running total
2. sign = +1 or -1 (for next number)

When we see '(':
  - Save current result and sign
  - Start fresh inside parentheses

When we see ')':
  - Finish calculating inside
  - Restore previous result and sign
  - Combine: prev_result + prev_sign × inner_result
```

### The Algorithm

```
1. Track: result (running total), num (current number), sign (+1/-1)
2. Digit: build number (num = num*10 + digit)
3. '+': add num to result, reset num, sign = +1
4. '-': add num to result, reset num, sign = -1
5. '(': push result and sign, reset both
6. ')': finish inner, pop sign and result, combine
7. End: don't forget the last number!
```

---

## Visual Dry Run

**Input:** `"1 + (2 - 3)"`

```
Initial: result = 0, num = 0, sign = 1, stack = []

char '1':
  num = 0*10 + 1 = 1

char ' ':
  Skip

char '+':
  result = 0 + 1*1 = 1
  num = 0, sign = 1

char ' ':
  Skip

char '(':
  Push result (1) and sign (1) → stack = [1, 1]
  Reset: result = 0, sign = 1

char '2':
  num = 2

char ' ':
  Skip

char '-':
  result = 0 + 1*2 = 2
  num = 0, sign = -1

char ' ':
  Skip

char '3':
  num = 3

char ')':
  result = 2 + (-1)*3 = 2 - 3 = -1
  num = 0
  Pop sign (1) and prev_result (1)
  result = 1 + 1*(-1) = 0

End: result + sign*num = 0 + 1*0 = 0 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int calculate(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    int result = 0;  // Running total
    int num = 0;     // Current number being built
    int sign = 1;    // +1 or -1
    
    for (char c : s.toCharArray()) {
        if (Character.isDigit(c)) {
            // Build multi-digit number
            num = num * 10 + (c - '0');
        } 
        else if (c == '+') {
            // Add current number to result
            result += sign * num;
            num = 0;
            sign = 1;  // Next number is positive
        } 
        else if (c == '-') {
            result += sign * num;
            num = 0;
            sign = -1;  // Next number is negative
        } 
        else if (c == '(') {
            // Save current state
            stack.push(result);
            stack.push(sign);
            // Reset for inner expression
            result = 0;
            sign = 1;
        } 
        else if (c == ')') {
            // Finish inner expression
            result += sign * num;
            num = 0;
            // Restore and combine
            result *= stack.pop();  // Sign before '('
            result += stack.pop();  // Result before '('
        }
        // Ignore spaces
    }
    
    // Don't forget the last number!
    return result + sign * num;
}
```

---

## Common Traps

| Trap | Example | Fix |
|------|---------|-----|
| Forgetting last number | "1+2" → miss the 2 | `return result + sign*num` |
| Wrong order in stack | Push result then sign | Pop sign first, then result |

---

## Mind-Map Anchor

```
BASIC CALCULATOR
       │
       ▼
┌─────────────────────────┐
│ Track result + sign     │
│ '(' = push both, reset  │
│ ')' = pop, combine      │
│ combine = prev + s*inner│
│ Don't forget last num!  │
└─────────────────────────┘
```

**Memory phrase:** "Push result+sign on '(', pop and combine on ')'"

---

# PATTERN 15: Basic Calculator II (LeetCode 227)

## Pattern Recognition Signal

**When you see:** "calculator", "+ - * /", "no parentheses"

**Instant thought:** "Apply PREVIOUS operator when you see new operator!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: "3+2*2"

Evaluate with operator precedence:
- * and / before + and -

= 3 + (2*2)
= 3 + 4
= 7
```

### The Key Insight

```
We can't immediately apply an operator because * and / have higher precedence.

Solution: Use stack!
- For + and -: push the number (with sign)
- For * and /: pop, compute, push result

At the end, sum everything in the stack.

Example: "3+2*2"
  See 3, op='+': push 3 → stack = [3]
  See 2, op='*': push 2 → stack = [3, 2]
  See 2, op='+' (end): pop 2, compute 2*2=4, push 4 → stack = [3, 4]
  Sum: 3 + 4 = 7 ✓
```

### The Algorithm

```
1. Track: num (current number), op (PREVIOUS operator, start with '+')
2. When we see a new operator (or end of string):
   - Apply the PREVIOUS operator to num
   - '+': push num
   - '-': push -num
   - '*': pop, multiply, push
   - '/': pop, divide, push
3. Update op to current operator
4. At end, sum all values in stack
```

---

## Visual Dry Run

**Input:** `"3+2*2"`

```
Initial: num = 0, op = '+', stack = []

char '3':
  num = 3

char '+':
  Apply previous op '+': push 3 → stack = [3]
  op = '+', num = 0

char '2':
  num = 2

char '*':
  Apply previous op '+': push 2 → stack = [3, 2]
  op = '*', num = 0

char '2':
  num = 2

End of string:
  Apply previous op '*': pop 2, compute 2*2=4, push 4
  stack = [3, 4]

Sum stack: 3 + 4 = 7 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int calculate(String s) {
    Deque<Integer> stack = new ArrayDeque<>();
    int num = 0;
    char op = '+';  // Previous operator (start with '+')
    
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        
        if (Character.isDigit(c)) {
            num = num * 10 + (c - '0');
        }
        
        // Apply previous operator when we see new operator or end
        if ((!Character.isDigit(c) && c != ' ') || i == s.length() - 1) {
            switch (op) {
                case '+': stack.push(num); break;
                case '-': stack.push(-num); break;
                case '*': stack.push(stack.pop() * num); break;
                case '/': stack.push(stack.pop() / num); break;
            }
            op = c;  // Current becomes previous
            num = 0;
        }
    }
    
    // Sum all values in stack
    int result = 0;
    while (!stack.isEmpty()) {
        result += stack.pop();
    }
    return result;
}
```

---

## Common Traps

| Trap | Example | Fix |
|------|---------|-----|
| Applying current operator | Should apply PREVIOUS | Track `op` separately |
| Missing end of string | Last number not processed | Check `i == s.length()-1` |
| Integer division | -3/2 should be -1, not -2 | Java does truncate toward zero |

---

## Mind-Map Anchor

```
BASIC CALCULATOR II
        │
        ▼
┌─────────────────────────┐
│ Apply PREVIOUS operator │
│ +: push num             │
│ -: push -num            │
│ *: pop, multiply, push  │
│ /: pop, divide, push    │
│ Sum stack at end        │
└─────────────────────────┘
```

**Memory phrase:** "Apply PREVIOUS operator, +/- push, */ compute immediately"

---
        result += stack.pop();
    }
    return result;
}
```

## Mind-Map Anchor

**apply PREVIOUS operator · +/- push · */ apply immediately · sum stack at end**

---

# PATTERN 17: Evaluate Reverse Polish Notation (LeetCode 150)

## Pattern Recognition Signal

**When you see:** "Reverse Polish", "postfix notation", "evaluate tokens"

**Instant thought:** "Numbers go on stack, operators pop two and push result!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: ["2", "1", "+", "3", "*"]

Reverse Polish Notation (RPN) = Postfix notation
- Operators come AFTER their operands
- No parentheses needed!

Evaluation:
  "2" → push 2
  "1" → push 1
  "+" → pop 1, pop 2, compute 2+1=3, push 3
  "3" → push 3
  "*" → pop 3, pop 3, compute 3*3=9, push 9

Result: 9
```

### Why RPN is Easy with Stack

```
In RPN, when you see an operator:
- The two operands are ALREADY on the stack!
- Just pop them, compute, push result

No need to worry about precedence or parentheses!
```

### The Algorithm

```
1. See a number? Push it
2. See an operator? Pop two, compute, push result
3. At end, stack has exactly one element = answer
```

---

## Visual Dry Run

**Input:** `["4", "13", "5", "/", "+"]`

```
Token "4":
  It's a number! Push 4
  stack = [4]

Token "13":
  It's a number! Push 13
  stack = [4, 13]

Token "5":
  It's a number! Push 5
  stack = [4, 13, 5]

Token "/":
  It's an operator!
  Pop b = 5, pop a = 13
  Compute: 13 / 5 = 2
  Push 2
  stack = [4, 2]

Token "+":
  It's an operator!
  Pop b = 2, pop a = 4
  Compute: 4 + 2 = 6
  Push 6
  stack = [6]

Result: 6 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();
    
    for (String token : tokens) {
        if ("+-*/".contains(token)) {
            // It's an operator - pop two operands
            int b = stack.pop();  // Second operand (popped first!)
            int a = stack.pop();  // First operand
            
            // Compute and push result
            switch (token) {
                case "+": stack.push(a + b); break;
                case "-": stack.push(a - b); break;
                case "*": stack.push(a * b); break;
                case "/": stack.push(a / b); break;
            }
        } else {
            // It's a number - push it
            stack.push(Integer.parseInt(token));
        }
    }
    
    return stack.pop();  // Final result
}
```

---

## Common Traps

| Trap | Example | Fix |
|------|---------|-----|
| Wrong operand order | "6 2 /" should be 6/2=3, not 2/6 | Pop b first, then a |
| Negative numbers | "-3" is a number, not operator | Check if it's in "+-*/" |

---

## Mind-Map Anchor

```
EVALUATE RPN
     │
     ▼
┌─────────────────────────┐
│ Number → push           │
│ Operator → pop 2        │
│ Order: pop b, pop a     │
│ Compute a op b          │
│ Push result             │
└─────────────────────────┘
```

**Memory phrase:** "Push numbers, pop two for operators, order matters (a op b)"

---

# PATTERN 21: Remove K Digits (LeetCode 402)

## Pattern Recognition Signal

**When you see:** "remove k digits", "smallest number", "monotonic"

**Instant thought:** "Greedy + monotonic stack! Remove larger digits to make smaller number!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: num = "1432219", k = 3

Remove 3 digits to get the smallest possible number.

Which digits to remove?
- We want smaller digits at the front!
- If we see a smaller digit, remove larger digits before it

Remove 4 (1 < 4): "132219"
Remove 3 (1 < 3): "12219"
Remove 2 (1 < 2): "1219"

Result: "1219"
```

### The Key Insight

```
To make the smallest number:
- We want digits in INCREASING order (from left to right)
- When we see a smaller digit, remove all larger digits before it

This is exactly what a MONOTONIC INCREASING stack does!
```

### The Algorithm

```
1. For each digit:
   - While stack not empty AND k > 0 AND stack top > current digit:
     - Pop (remove the larger digit)
     - k--
   - Push current digit
2. If k > 0 after loop, remove from end (stack is increasing, so end has largest)
3. Remove leading zeros
4. Handle empty result
```

---

## Visual Dry Run

**Input:** `num = "1432219", k = 3`

```
Initial: stack = [], k = 3

Digit '1':
  Stack empty, push '1'
  stack = ['1'], k = 3

Digit '4':
  '4' > '1'? YES, but we want increasing, so push
  stack = ['1', '4'], k = 3

Digit '3':
  '3' < '4'? YES! Pop '4', k = 2
  '3' > '1'? YES, push '3'
  stack = ['1', '3'], k = 2

Digit '2':
  '2' < '3'? YES! Pop '3', k = 1
  '2' > '1'? YES, push '2'
  stack = ['1', '2'], k = 1

Digit '2':
  '2' < '2'? NO, push '2'
  stack = ['1', '2', '2'], k = 1

Digit '1':
  '1' < '2'? YES! Pop '2', k = 0
  k = 0, stop removing
  Push '1'
  stack = ['1', '2', '1'], k = 0

Digit '9':
  k = 0, just push
  stack = ['1', '2', '1', '9'], k = 0

Result: "1219" ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
String removeKdigits(String num, int k) {
    Deque<Character> stack = new ArrayDeque<>();
    
    for (char digit : num.toCharArray()) {
        // Remove larger digits before current (greedy)
        while (!stack.isEmpty() && k > 0 && stack.peek() > digit) {
            stack.pop();
            k--;
        }
        stack.push(digit);
    }
    
    // If k > 0, remove from end (largest digits in increasing stack)
    while (k > 0) {
        stack.pop();
        k--;
    }
    
    // Build result (stack is reversed, so build from bottom)
    StringBuilder sb = new StringBuilder();
    while (!stack.isEmpty()) {
        sb.append(stack.pollLast());  // Get from bottom
    }
    
    // Remove leading zeros
    while (sb.length() > 0 && sb.charAt(0) == '0') {
        sb.deleteCharAt(0);
    }
    
    return sb.length() == 0 ? "0" : sb.toString();
}
```

---

## Common Traps

| Trap | Example | Fix |
|------|---------|-----|
| Forgetting k > 0 after loop | "12345", k=2 → should be "123" | Remove k from end |
| Leading zeros | "10200", k=1 → "200" not "0200" | Strip leading zeros |
| Empty result | "10", k=2 → "0" not "" | Return "0" if empty |

---

## Mind-Map Anchor

```
REMOVE K DIGITS
       │
       ▼
┌─────────────────────────┐
│ Monotonic INCREASING    │
│ Pop larger digits       │
│ If k left, pop from end │
│ Remove leading zeros    │
│ Empty → return "0"      │
└─────────────────────────┘
```

**Memory phrase:** "Increasing stack, pop larger, strip zeros, handle empty"

---

# PATTERN 22: Asteroid Collision (LeetCode 735)

## Pattern Recognition Signal

**When you see:** "collision", "moving left/right", "destroy", "survive"

**Instant thought:** "Stack simulation! Positive = right, negative = left, collision when opposite!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: [5, 10, -5]

Asteroids moving:
- Positive = moving RIGHT →
- Negative = moving LEFT ←

5 → (moving right)
10 → (moving right)
← -5 (moving left)

Collision: 10 vs -5
  |10| > |-5|, so -5 explodes
  10 survives

Result: [5, 10]
```

### When Do Collisions Happen?

```
Collision happens ONLY when:
- Stack top is positive (moving right) →
- Current is negative (moving left) ←

No collision when:
- Both positive (both moving right)
- Both negative (both moving left)
- Stack top negative, current positive (moving away from each other)
```

### The Algorithm

```
1. For each asteroid:
   - While collision possible (top > 0 and current < 0):
     - Compare sizes
     - If |current| > top: pop (top explodes), continue checking
     - If |current| < top: current explodes, stop
     - If equal: both explode, pop and stop
   - If current survived, push it
```

---

## Visual Dry Run

**Input:** `[5, 10, -5]`

```
Asteroid 5:
  Stack empty, push 5
  stack = [5]

Asteroid 10:
  10 > 0, no collision with 5 (both moving right)
  Push 10
  stack = [5, 10]

Asteroid -5:
  -5 < 0 and top 10 > 0 → COLLISION!
  |10| vs |-5|: 10 > 5
  -5 explodes, 10 survives
  Don't push -5
  stack = [5, 10]

Result: [5, 10] ✓
```

**Another Example:** `[8, -8]`

```
Asteroid 8:
  Push 8 → stack = [8]

Asteroid -8:
  -8 < 0 and top 8 > 0 → COLLISION!
  |8| vs |-8|: 8 == 8
  BOTH explode!
  Pop 8, don't push -8
  stack = []

Result: [] ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
int[] asteroidCollision(int[] asteroids) {
    Deque<Integer> stack = new ArrayDeque<>();
    
    for (int asteroid : asteroids) {
        boolean alive = true;
        
        // Check for collisions (top moving right, current moving left)
        while (alive && asteroid < 0 && !stack.isEmpty() && stack.peek() > 0) {
            // Compare sizes
            if (stack.peek() < -asteroid) {
                // Top is smaller, it explodes
                stack.pop();
                // Current keeps going, check next collision
            } else if (stack.peek() == -asteroid) {
                // Same size, both explode
                stack.pop();
                alive = false;
            } else {
                // Top is bigger, current explodes
                alive = false;
            }
        }
        
        // If current survived, add it
        if (alive) {
            stack.push(asteroid);
        }
    }
    
    // Convert stack to array (reverse order)
    int[] result = new int[stack.size()];
    for (int i = result.length - 1; i >= 0; i--) {
        result[i] = stack.pop();
    }
    return result;
}
```

---

## Common Traps

| Trap | Example | Fix |
|------|---------|-----|
| Wrong collision condition | Both negative don't collide | Only when top > 0 AND current < 0 |
| Forgetting equal case | [8, -8] → both explode | Check `==` separately |
| Wrong output order | Stack is LIFO | Reverse when building result |

---

## Mind-Map Anchor

```
ASTEROID COLLISION
        │
        ▼
┌─────────────────────────┐
│ + = right, - = left     │
│ Collision: top>0, cur<0 │
│ Compare |sizes|         │
│ Bigger wins             │
│ Equal = both explode    │
│ Reverse stack for result│
└─────────────────────────┘
```

**Memory phrase:** "Collision when opposite directions, bigger wins, equal both die"

---

# PATTERN 23: 132 Pattern (LeetCode 456)

## Pattern Recognition Signal

**When you see:** "132 pattern", "i < j < k", "nums[i] < nums[k] < nums[j]"

**Instant thought:** "Scan from RIGHT, track max 'k' value seen after a larger 'j'!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Find indices i < j < k where nums[i] < nums[k] < nums[j]

Example: [3, 1, 4, 2]
         i=1, j=2, k=3
         nums[1]=1 < nums[3]=2 < nums[2]=4 ✓

The "132" pattern:
- nums[j] is the LARGEST (the "3")
- nums[k] is MEDIUM (the "2")
- nums[i] is SMALLEST (the "1")
```

### The Key Insight

```
Scan from RIGHT to LEFT!

Maintain:
- Stack of potential "j" values (candidates for the largest)
- Variable "third" = the largest "k" value we've seen

When we pop from stack (because current is larger):
- The popped value becomes a candidate for "k" (third)
- Current becomes the new "j"

When we find nums[i] < third:
- We found the pattern! nums[i] < third < some j we passed
```

### The Algorithm

```
1. Scan from right to left
2. If nums[i] < third: found pattern! Return true
3. While stack not empty and nums[i] > stack top:
   - Pop and update third (this is the largest valid "k")
4. Push nums[i] (candidate for "j")
5. If no pattern found, return false
```

---

## Visual Dry Run

**Input:** `[3, 1, 4, 2]`

```
Initial: stack = [], third = MIN_VALUE

i=3, nums[3]=2:
  2 < third (MIN)? NO
  Stack empty, push 2
  stack = [2], third = MIN

i=2, nums[2]=4:
  4 < third (MIN)? NO
  4 > 2? YES! Pop 2, third = 2
  Stack empty, push 4
  stack = [4], third = 2

i=1, nums[1]=1:
  1 < third (2)? YES! ✓
  Found pattern!
  
  (1 is "i", some value > 2 is "j", 2 is "k")

Return true ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
boolean find132pattern(int[] nums) {
    int n = nums.length;
    Deque<Integer> stack = new ArrayDeque<>();
    int third = Integer.MIN_VALUE;  // The "k" value (second largest)
    
    // Scan from right to left
    for (int i = n - 1; i >= 0; i--) {
        // If current < third, we found the pattern!
        // (current is "i", third is "k", something in stack was "j")
        if (nums[i] < third) {
            return true;
        }
        
        // Pop all smaller values - they become candidates for "k"
        while (!stack.isEmpty() && nums[i] > stack.peek()) {
            third = stack.pop();  // Update third to largest valid "k"
        }
        
        // Current is a candidate for "j"
        stack.push(nums[i]);
    }
    
    return false;
}
```

---

## Why Scan from Right?

```
If we scan from left:
- We need to track minimum "i" AND find valid "j" and "k" to the right
- Complex!

If we scan from right:
- Stack holds candidates for "j" (the largest)
- "third" holds the best "k" (second largest, must be < some "j")
- Just check if current < third

Much simpler!
```

---

## Common Traps

| Trap | Example | Fix |
|------|---------|-----|
| Scanning left to right | Much harder | Scan right to left |
| Wrong third update | Should be largest valid k | Update when popping |
| Checking wrong condition | nums[i] < third, not <= | Strict inequality |

---

## Mind-Map Anchor

```
132 PATTERN
     │
     ▼
┌─────────────────────────┐
│ Scan RIGHT to LEFT      │
│ Stack = candidates for j│
│ third = best k value    │
│ Pop when current > top  │
│ Update third on pop     │
│ Found if current < third│
└─────────────────────────┘
```

**Memory phrase:** "Right to left, stack is j, third is k, found when current < third"

---

# MAANG Coverage Map (Complete L5)

| Pattern | Problem | LeetCode | Difficulty | Frequency |
|---------|---------|----------|------------|-----------|
| **Matching** |
| 0 | Valid Parentheses | 20 | Easy | 🔥🔥🔥 |
| 1 | Longest Valid Parentheses | 32 | Hard | 🔥🔥 |
| 2 | Min Add to Make Valid | 921 | Medium | 🔥🔥 |
| 3 | Score of Parentheses | 856 | Medium | 🔥 |
| **Monotonic** |
| 5 | Daily Temperatures | 739 | Medium | 🔥🔥🔥 |
| 6 | Next Greater Element I | 496 | Easy | 🔥🔥 |
| 7 | Next Greater Element II | 503 | Medium | 🔥🔥 |
| 9 | Largest Rectangle | 84 | Hard | 🔥🔥🔥 |
| 10 | Trapping Rain Water | 42 | Hard | 🔥🔥🔥 |
| 11 | Stock Span | 901 | Medium | 🔥🔥 |
| 12 | Sum of Subarray Mins | 907 | Medium | 🔥🔥 |
| **Expression** |
| 13 | Decode String | 394 | Medium | 🔥🔥🔥 |
| 14 | Basic Calculator | 224 | Hard | 🔥🔥🔥 |
| 15 | Basic Calculator II | 227 | Medium | 🔥🔥🔥 |
| 17 | Eval RPN | 150 | Medium | 🔥🔥 |
| **String/Simulation** |
| 21 | Remove K Digits | 402 | Medium | 🔥🔥🔥 |
| 22 | Asteroid Collision | 735 | Medium | 🔥🔥🔥 |
| **Advanced** |
| 23 | 132 Pattern | 456 | Medium | 🔥🔥 |

---

# Mastery Checklist

## Tier 1: Must Know (10 patterns)
- [ ] Valid Parentheses (matching)
- [ ] Daily Temperatures (monotonic)
- [ ] Next Greater Element I (monotonic)
- [ ] Largest Rectangle in Histogram (boundary)
- [ ] Decode String (context)
- [ ] Basic Calculator II (expression)
- [ ] Evaluate RPN (expression)
- [ ] Remove K Digits (greedy monotonic)
- [ ] Asteroid Collision (simulation)
- [ ] Trapping Rain Water (boundary)

## Tier 2: Interview Favorites (6 patterns)
- [ ] Longest Valid Parentheses (index stack)
- [ ] Basic Calculator (nested expression)
- [ ] Stock Span (monotonic)
- [ ] Sum of Subarray Minimums (contribution)
- [ ] Next Greater Element II (circular)
- [ ] Score of Parentheses (depth)

## Tier 3: Differentiators (2 patterns)
- [ ] 132 Pattern (reverse scan)
- [ ] Min Add to Make Valid (counting)

---

# The Final Mental Model

```
┌─────────────────────────────────────────────────────────────────────┐
│                    STACK PATTERN MASTERY                             │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  1. MATCHING: Push openers, pop on closers, empty = valid           │
│                                                                      │
│  2. MONOTONIC: Decreasing for greater, pop when violated            │
│                                                                      │
│  3. HISTOGRAM: Increasing stack, sentinel flushes, w = i-top-1      │
│                                                                      │
│  4. EXPRESSION: Push state on '(', pop and combine on ')'           │
│                                                                      │
│  5. SIMULATION: Stack models the process, pop on collision          │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

---

**Remember:** A stack remembers HISTORY in reverse order. Use it when you need to UNDO, MATCH, or find the NEAREST thing!