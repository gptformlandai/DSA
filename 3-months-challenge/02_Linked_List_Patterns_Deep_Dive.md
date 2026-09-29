# Section 11 — Linked List Patterns Deep Dive (MAANG L5 Coverage)

---

# INDEX — Quick Navigation (23 Patterns)

## Core Concepts
| Section | Description |
|---------|-------------|
| [The "One Sentence"](#the-one-sentence-that-unlocks-all-linked-list-problems) | Unlocks all LL problems |
| [5 Pointer Setups](#the-5-pointer-setups-your-configurations) | Your configurations |
| [4 Core Moves](#the-4-core-moves-your-actions) | Your actions |
| [Decision Tree](#the-master-decision-tree--pick-your-weapon-in-10-seconds) | Pick your weapon |

---

## Reversal Family (0-3)
| # | Pattern | LeetCode |
|---|---------|----------|
| 0 | [Reverse Linked List](#pattern-0-reverse-linked-list-leetcode-206) | 206 |
| 1 | [Reverse Linked List II](#pattern-1-reverse-linked-list-ii-leetcode-92) | 92 |
| 2 | [Reverse Nodes in k-Group](#pattern-2-reverse-nodes-in-k-group-leetcode-25) | 25 |
| 3 | [Swap Nodes in Pairs](#pattern-3-swap-nodes-in-pairs-leetcode-24) | 24 |

## Cycle Family (4-5)
| # | Pattern | LeetCode |
|---|---------|----------|
| 4 | [Linked List Cycle](#pattern-4-linked-list-cycle-leetcode-141) | 141 |
| 5 | [Linked List Cycle II](#pattern-5-linked-list-cycle-ii-leetcode-142) | 142 |

## Position Finding (6-8)
| # | Pattern | LeetCode |
|---|---------|----------|
| 6 | [Middle of Linked List](#pattern-6-middle-of-linked-list-leetcode-876) | 876 |
| 7 | [Remove Nth from End](#pattern-7-remove-nth-node-from-end-leetcode-19) | 19 |
| 8 | [Intersection of Two Lists](#pattern-8-intersection-of-two-linked-lists-leetcode-160) | 160 |

## Merge & Sort Family (9-11)
| # | Pattern | LeetCode |
|---|---------|----------|
| 9 | [Merge Two Sorted Lists](#pattern-9-merge-two-sorted-lists-leetcode-21) | 21 |
| 10 | [Merge K Sorted Lists](#pattern-10-merge-k-sorted-lists-leetcode-23) | 23 |
| 11 | [Sort List](#pattern-11-sort-list-leetcode-148) | 148 |

## Structural Manipulation (12-15, 22)
| # | Pattern | LeetCode |
|---|---------|----------|
| 12 | [Reorder List](#pattern-12-reorder-list-leetcode-143) | 143 |
| 13 | [Palindrome Linked List](#pattern-13-palindrome-linked-list-leetcode-234) | 234 |
| 14 | [Partition List](#pattern-14-partition-list-leetcode-86) | 86 |
| 15 | [Odd Even Linked List](#pattern-15-odd-even-linked-list-leetcode-328) | 328 |
| 22 | [Rotate List](#pattern-22-rotate-list-leetcode-61) | 61 |

## Removal & Deduplication (16-17)
| # | Pattern | LeetCode |
|---|---------|----------|
| 16 | [Remove Duplicates I](#pattern-16-remove-duplicates-from-sorted-list-leetcode-83) | 83 |
| 17 | [Remove Duplicates II](#pattern-17-remove-duplicates-from-sorted-list-ii-leetcode-82) | 82 |

## Math on Lists (18-19)
| # | Pattern | LeetCode |
|---|---------|----------|
| 18 | [Add Two Numbers](#pattern-18-add-two-numbers-leetcode-2) | 2 |
| 19 | [Add Two Numbers II](#pattern-19-add-two-numbers-ii-leetcode-445) | 445 |

## Advanced / Design (20-21)
| # | Pattern | LeetCode |
|---|---------|----------|
| 20 | [Copy List with Random Pointer](#pattern-20-copy-list-with-random-pointer-leetcode-138) | 138 |
| 21 | [LRU Cache](#pattern-21-lru-cache-leetcode-146) | 146 |

## Reference Sections
| Section |
|---------|
| [MAANG Coverage Map](#updated-maang-coverage-map) |
| [Pattern Recognition Cheat Sheet](#pattern-recognition-cheat-sheet) |
| [Mastery Checklist](#mastery-checklist) |

---

# The "One Sentence That Unlocks All Linked List Problems"

> **"Which POINTERS do I need, and what do I REDIRECT each step?"**

That's the entire subject. Every linked list problem — from easy to hard — is just:
1. **Pick** the right pointer configuration
2. **Know** what to SAVE before you break a link
3. **Redirect** pointers in the right order
4. **Move** forward correctly

---

## 📋 THE JUNIOR DEV CHEAT CARD (Memorize This!)

```
╔═══════════════════════════════════════════════════════════════════════╗
║                     LINKED LIST PROBLEM? USE THIS!                     ║
╠═══════════════════════════════════════════════════════════════════════╣
║                                                                        ║
║  STEP 1: "What type of problem is this?"                              ║
║                                                                        ║
║  ┌────────────────────────────────────────────────────────────────┐   ║
║  │ "Reverse something"    → Use prev, curr, next (3 pointers)     │   ║
║  │ "Find middle/cycle"    → Use slow, fast (2 pointers)           │   ║
║  │ "Merge lists"          → Use dummy head + tail pointer         │   ║
║  │ "Remove nth from end"  → Use two pointers with gap of n        │   ║
║  │ "Modify structure"     → Usually need dummy head               │   ║
║  └────────────────────────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE GOLDEN RULE: "SAVE BEFORE YOU BREAK!"                            ║
║                                                                        ║
║  Before changing any .next pointer, ask:                              ║
║  "Will I lose access to something I still need?"                      ║
║  If YES → Save it first!  next = curr.next; // THEN change           ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE 5 SETUPS (Pick one based on problem type):                       ║
║                                                                        ║
║  ┌──────────────────┬─────────────────────────────────────────────┐   ║
║  │ Reversal         │ prev=null, curr=head, next=curr.next        │   ║
║  │ Fast-Slow        │ slow=head, fast=head (or head.next)         │   ║
║  │ Runner/Gap       │ first=head, second=head, move first n times │   ║
║  │ Dummy Head       │ dummy.next=head, work with dummy            │   ║
║  │ Two Lists        │ p1=head1, p2=head2, compare and advance     │   ║
║  └──────────────────┴─────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  COMMON PATTERNS CODE:                                                 ║
║                                                                        ║
║  // Reverse a list                                                    ║
║  while (curr != null) {                                               ║
║      next = curr.next;    // 1. Save                                  ║
║      curr.next = prev;    // 2. Reverse                               ║
║      prev = curr;         // 3. Move prev                             ║
║      curr = next;         // 4. Move curr                             ║
║  }                                                                     ║
║  return prev;                                                          ║
║                                                                        ║
║  // Find middle (slow-fast)                                           ║
║  while (fast != null && fast.next != null) {                          ║
║      slow = slow.next;                                                ║
║      fast = fast.next.next;                                           ║
║  }                                                                     ║
║  return slow;  // slow is at middle                                   ║
║                                                                        ║
╚═══════════════════════════════════════════════════════════════════════╝
```

---

## 🚀 QUICK START: The 60-Second Linked List Approach

### The ONE Question That Solves Most Problems:

> **"What should each node point to AFTER this operation, and will I lose anything if I change it now?"**

### The Simple Decision Process:

```
1. "Need to reverse?"
   → Setup: prev = null, curr = head
   → Loop: save next, flip arrow, move forward
   → Return: prev (new head)

2. "Need to find middle or detect cycle?"
   → Setup: slow = head, fast = head
   → Loop: slow moves 1, fast moves 2
   → When fast reaches end, slow is at middle
   → If fast meets slow, there's a cycle!

3. "Need to remove something?"
   → Use DUMMY HEAD! (handles edge case of removing first node)
   → dummy.next = head, work from dummy
   → Return dummy.next

4. "Need to merge two lists?"
   → Use dummy head + tail pointer
   → Compare, attach smaller, move that pointer
   → Attach remaining list at end
```

### The "Save Before You Break" Mantra

This is the #1 mistake in linked list problems:

```
WRONG:
curr.next = prev;      // Oops! Lost access to the rest of the list!
next = curr.next;      // Too late, curr.next is now prev!

RIGHT:
next = curr.next;      // Save the next node FIRST
curr.next = prev;      // NOW safe to redirect
```

---

## 🎯 WORKED EXAMPLE: How a Junior Dev Should Think

**Problem:** "Reverse a linked list."

### My Thinking Process:

**Step 1: What do I have?**
```
1 → 2 → 3 → 4 → null
```

**Step 2: What do I want?**
```
null ← 1 ← 2 ← 3 ← 4
```

**Step 3: Focus on ONE node. What needs to happen to node 2?**
```
Before: 1 → [2] → 3
After:  1 ← [2]    3  (2 now points backward to 1, not forward to 3)
```

**Step 4: If I change 2's pointer, will I lose something?**
> "Yes! If 2 points to 1, I lose access to 3!"
> "Solution: SAVE 3 first, then change 2's pointer."

**Step 5: What pointers do I need?**
- `prev`: what curr should point to (starts as null)
- `curr`: the node I'm currently processing
- `next`: saved reference to not lose the rest

**Step 6: Write the code:**
```java
ListNode reverse(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    
    while (curr != null) {
        ListNode next = curr.next;  // 1. SAVE (don't lose the rest!)
        curr.next = prev;           // 2. FLIP (point backward)
        prev = curr;                // 3. MOVE prev forward
        curr = next;                // 4. MOVE curr forward
    }
    
    return prev;  // prev is now the new head
}
```

### Visual Dry Run:

```
Initial: prev=null, curr=1

Step 1: next=2, 1→null, prev=1, curr=2
        null ← 1    2 → 3 → 4

Step 2: next=3, 2→1, prev=2, curr=3
        null ← 1 ← 2    3 → 4

Step 3: next=4, 3→2, prev=3, curr=4
        null ← 1 ← 2 ← 3    4

Step 4: next=null, 4→3, prev=4, curr=null
        null ← 1 ← 2 ← 3 ← 4

curr is null, loop ends. Return prev (which is 4, the new head).
```

---

### Another Example: "Find if linked list has a cycle"

**My Thinking:**
1. If there's a cycle, a fast runner will eventually lap a slow runner
2. Like two people running on a circular track — fast will catch slow!
3. If no cycle, fast will reach the end (null)

```java
boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    
    while (fast != null && fast.next != null) {
        slow = slow.next;        // Move 1 step
        fast = fast.next.next;   // Move 2 steps
        
        if (slow == fast) {
            return true;  // They met! Cycle exists!
        }
    }
    
    return false;  // Fast reached end, no cycle
}
```

---

## The Mental Model: Linked Lists vs Arrays vs Trees

| Data Structure | Mental Model | Core Question |
|----------------|--------------|---------------|
| **Array** | A row of numbered boxes | "Which INDEX do I need?" |
| **Tree** | A family tree (parent/children) | "What do I need from ABOVE vs BELOW?" |
| **Linked List** | A chain of handholding people | "Who should hold whose hand next?" |

**The Linked List Insight:** You can't jump to position 5. You must walk from the start. But you CAN instantly change who's holding whose hand (redirect pointers) — that's the superpower!

---

## Why Linked Lists Feel Hard (And How to Fix It)

**The Problem:** You're trying to visualize the WHOLE list changing at once.

**The Fix:** Focus on ONE node at a time. Ask:
1. What is `curr` pointing to RIGHT NOW?
2. What SHOULD `curr` point to AFTER this step?
3. Will I lose access to anything if I change it? (If yes, SAVE it first!)

---

# The 5 Pointer Setups (Your "Configurations")

Think of these as your **5 weapons**. Every linked list problem uses one (or a combination). Learn to recognize which weapon to draw!

---

## Setup 1: The Reversal Setup (prev, curr, next)

**Mental Trigger:** "Three dancers passing a baton backward"

**When to Use:**
- Reverse entire list
- Reverse a segment
- Reverse in groups
- Any time arrows need to flip direction

**The Visual Story:**
```
Imagine a conga line: A → B → C → D
Everyone is holding the shoulder of the person IN FRONT.

To reverse, each person:
1. Lets go of the person in front
2. Turns around
3. Grabs the person who WAS behind them

Result: D → C → B → A
No one moved position — they just changed who they're holding!
```

**The Three Dancers:**
```
     prev    curr    next
       ↓       ↓       ↓
... ← [A]    [B] → [C] → [D] → ...
       
Step 1: Save next     → next = curr.next (save C before we lose it!)
Step 2: Flip arrow    → curr.next = prev (B now points to A)
Step 3: Move prev     → prev = curr (prev moves to B)
Step 4: Move curr     → curr = next (curr moves to C)

After one iteration:
... ← [A] ← [B]    [C] → [D] → ...
             ↑       ↑
           prev    curr
```

**The Code Pattern:**
```java
ListNode prev = null;
ListNode curr = head;

while (curr != null) {
    ListNode next = curr.next;  // 1. SAVE (before you lose it!)
    curr.next = prev;           // 2. FLIP (redirect backward)
    prev = curr;                // 3. MARCH prev forward
    curr = next;                // 4. MARCH curr forward
}
return prev;  // prev is the new head!
```

**Memory Trick:** "Save, Flip, March, March" or "SFMM"

---

## Setup 2: The Fast-Slow Setup (Tortoise & Hare)

**Mental Trigger:** "Two runners on a track — one runs twice as fast"

**When to Use:**
- Find the MIDDLE of a list
- Detect if there's a CYCLE
- Find where a cycle STARTS
- Check if list is a PALINDROME (need middle first)

**The Visual Story:**
```
Imagine a running track. Tortoise runs 1 lap while Hare runs 2.

SCENARIO 1: Straight track (no cycle)
  Start: [T,H]─────────────────────[END]
  
  After some time:
  [START]────[T]────────[H]────────[END]
  
  When Hare reaches END, Tortoise is at MIDDLE!

SCENARIO 2: Circular track (has cycle)
  If there's a loop, Hare will eventually LAP Tortoise.
  They WILL meet inside the cycle!
  
  Why? Hare gains 1 position per step. Eventually catches up.
```

**The Code Pattern:**
```java
ListNode slow = head;  // Tortoise
ListNode fast = head;  // Hare

while (fast != null && fast.next != null) {
    slow = slow.next;        // Tortoise: 1 step
    fast = fast.next.next;   // Hare: 2 steps
    
    // For cycle detection:
    if (slow == fast) {
        // They met! There's a cycle!
    }
}
// For finding middle: slow is now at the middle
return slow;
```

**Memory Trick:** "Slow by 1, Fast by 2, Meet means Loop, End means Middle"

**The Math Behind Cycle Entry (Floyd's Phase 2):**
```
When they meet inside the cycle:
- Let F = distance from head to cycle entry
- Let a = distance from cycle entry to meeting point
- Slow traveled: F + a
- Fast traveled: F + a + (some complete cycles)

The magic: If you reset ONE pointer to head and move BOTH at speed 1,
they will meet EXACTLY at the cycle entry!

Why? The math works out: F = (cycle_length - a)
Both pointers travel F steps to reach the entry point.
```

---

## Setup 3: The Runner/Gap Setup (Head Start)

**Mental Trigger:** "Give one runner a head start, then both run together"

**When to Use:**
- Find Nth node from the END
- Remove Nth node from the END
- Any problem involving "from the end" without knowing length

**The Visual Story:**
```
Problem: Find 2nd node from end, but you don't know the length!

Solution: Give fast a HEAD START of 2 nodes.
Then move both at the same speed.
When fast hits the end, slow is at the target!

Step 1: Advance fast by N
  slow                fast
    ↓                   ↓
   [1] → [2] → [3] → [4] → [5] → null
   
Step 2: Move both until fast hits null
   slow         fast
     ↓           ↓
   [1] → [2] → [3] → [4] → [5] → null
   
         slow         fast
           ↓           ↓
   [1] → [2] → [3] → [4] → [5] → null
   
               slow         fast
                 ↓           ↓
   [1] → [2] → [3] → [4] → [5] → null
   
                     slow         fast=null
                       ↓           
   [1] → [2] → [3] → [4] → [5] → null

slow is at node 4 = 2nd from end! ✓
```

**The Code Pattern:**
```java
ListNode slow = head;
ListNode fast = head;

// Step 1: Give fast a head start of N
for (int i = 0; i < n; i++) {
    fast = fast.next;
}

// Step 2: Move both until fast hits null
while (fast != null) {
    slow = slow.next;
    fast = fast.next;
}

return slow;  // slow is at Nth from end
```

**Pro Tip for REMOVAL:** Use gap of N+1 so slow stops BEFORE the target (then you can skip it).

**Memory Trick:** "Head start N, then march together, fast hits null = slow at target"

---

## Setup 4: The Dummy Head Setup (Fake First Node)

**Mental Trigger:** "Add a fake node before the real head to eliminate edge cases"

**When to Use:**
- Merging lists (need somewhere to attach first node)
- Deleting nodes (what if we delete the head?)
- Any operation where the head might change
- Partitioning lists

**The Visual Story:**
```
Problem: Delete node with value 1 from [1] → [2] → [3]
         But wait... 1 IS the head! Who points to the new head?

Without dummy:
  head → [1] → [2] → [3]
  
  If we delete 1, we need special code:
  "if deleting head, head = head.next"
  
With dummy:
  dummy → [1] → [2] → [3]
     ↑
   fake node (value doesn't matter)
  
  Now deleting 1 is just: dummy.next = dummy.next.next
  Return dummy.next as the real head!
  
  No special cases needed!
```

**The Code Pattern:**
```java
ListNode dummy = new ListNode(0);  // Value doesn't matter
dummy.next = head;

// ... do your operations using dummy ...

return dummy.next;  // Return the REAL head
```

**When You MUST Use Dummy:**
1. Merging two lists (where does the first node attach?)
2. Removing nodes (what if head is removed?)
3. Partitioning (building two new chains)
4. Any time you're not sure if head will change

**Memory Trick:** "When in doubt, dummy it out!"

---

## Setup 5: The Two-List Setup (Two Fingers Walking)

**Mental Trigger:** "Two fingers, one on each list, walking together"

**When to Use:**
- Merging two sorted lists
- Finding intersection of two lists
- Comparing two lists
- Adding two numbers (digit by digit)

**The Visual Story:**
```
Merging two sorted lists:

List1: [1] → [3] → [5]
        ↑
       p1

List2: [2] → [4] → [6]
        ↑
       p2

Compare p1 and p2:
- 1 < 2, so take 1, move p1
- 2 < 3, so take 2, move p2
- 3 < 4, so take 3, move p1
- ... and so on

Result: [1] → [2] → [3] → [4] → [5] → [6]
```

**The Code Pattern:**
```java
ListNode p1 = list1;
ListNode p2 = list2;
ListNode dummy = new ListNode(0);
ListNode tail = dummy;

while (p1 != null && p2 != null) {
    if (p1.val <= p2.val) {
        tail.next = p1;
        p1 = p1.next;
    } else {
        tail.next = p2;
        p2 = p2.next;
    }
    tail = tail.next;
}

// Attach remaining
tail.next = (p1 != null) ? p1 : p2;

return dummy.next;
```

**Memory Trick:** "Two fingers, compare, attach smaller, advance that finger"

---

# The 4 Core Moves (Your "Actions")

Every pointer manipulation is one of these 4 moves. Master these and you can solve ANY linked list problem!

| Move | What It Does | Code | Visual | When to Use |
|------|--------------|------|--------|-------------|
| **REDIRECT** | Change where a pointer points | `curr.next = prev` | `A → B` becomes `A ← B` | Reversing |
| **SKIP** | Jump over a node | `prev.next = curr.next` | `A → B → C` becomes `A ──→ C` | Deleting |
| **ATTACH** | Connect to another node | `tail.next = node` | `A` + `B` = `A → B` | Merging, building |
| **SWAP** | Exchange two nodes | Multiple redirects | `A ⇄ B` | Swapping pairs |

---

## Move 1: REDIRECT (Flip the Arrow)

**The Essence:** Make a node point somewhere DIFFERENT than it currently points.

```
Before: A.next = B    (A → B)
After:  A.next = X    (A → X)

The arrow from A now points to X instead of B.
```

**Critical Warning:** Once you redirect, the OLD target is LOST unless you saved it!

```java
// WRONG: Lost access to B forever!
A.next = X;

// CORRECT: Save B first!
ListNode savedB = A.next;  // Save B
A.next = X;                // Now safe to redirect
// savedB still points to B
```

---

## Move 2: SKIP (Jump Over a Node)

**The Essence:** Make a node's next pointer skip over the immediate next node.

```
Before: A → B → C
After:  A ──────→ C  (B is skipped/deleted)

Code: A.next = A.next.next
      or: A.next = B.next
```

**Use Case:** Deleting a node when you have access to the node BEFORE it.

---

## Move 3: ATTACH (Connect Two Nodes)

**The Essence:** Make one node point to another node (usually building a new list).

```
Before: tail → null,  newNode exists separately
After:  tail → newNode

Code: tail.next = newNode
      tail = tail.next  // Move tail forward
```

**Use Case:** Building result lists, merging.

---

## Move 4: SWAP (Exchange Two Nodes)

**The Essence:** Make two adjacent nodes switch positions. Requires multiple redirects!

```
Before: prev → A → B → C
After:  prev → B → A → C

Steps:
1. prev.next = B       (prev now points to B)
2. A.next = B.next     (A now points to C)
3. B.next = A          (B now points to A)
```

**Use Case:** Swap pairs, certain reordering problems.

---

# The Master Decision Tree — Pick Your Weapon in 10 Seconds

When you see a linked list problem, ask these questions IN ORDER:

```
┌─────────────────────────────────────────────────────────────┐
│                    LINKED LIST PROBLEM                       │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
        ┌─────────────────────────────────────────┐
        │  1. Do I need to REVERSE anything?      │
        │     (whole list, segment, groups)       │
        └─────────────────────────────────────────┘
                    │YES                │NO
                    ▼                   ▼
        ┌───────────────────┐   ┌─────────────────────────────┐
        │ REVERSAL SETUP    │   │ 2. Find MIDDLE or CYCLE?    │
        │ prev, curr, next  │   └─────────────────────────────┘
        │                   │           │YES            │NO
        │ Patterns: 0,1,2,3 │           ▼               ▼
        └───────────────────┘   ┌───────────────┐  ┌──────────────────────┐
                                │ FAST-SLOW     │  │ 3. Nth from END?     │
                                │ slow=1,fast=2 │  │    or maintain GAP?  │
                                │               │  └──────────────────────┘
                                │ Patterns:4-6  │      │YES         │NO
                                └───────────────┘      ▼            ▼
                                               ┌────────────┐ ┌─────────────────┐
                                               │ RUNNER     │ │ 4. HEAD might   │
                                               │ gap of N   │ │    change? or   │
                                               │            │ │    MERGING?     │
                                               │ Pattern: 7 │ └─────────────────┘
                                               └────────────┘     │YES      │NO
                                                                  ▼         ▼
                                                          ┌────────────┐ ┌──────────┐
                                                          │ DUMMY HEAD │ │ TWO-LIST │
                                                          │ dummy→head │ │ p1, p2   │
                                                          │            │ │          │
                                                          │ Patterns:  │ │ Patterns:│
                                                          │ 9,14,16,17 │ │ 8,9,18   │
                                                          └────────────┘ └──────────┘
```

---

## Compound Patterns (Multiple Techniques Combined)

Some problems require COMBINING techniques. Recognize these patterns:

| Problem | Techniques Combined | Pattern |
|---------|---------------------|---------|
| **Reorder List** | Middle + Reverse + Interleave | 12 |
| **Palindrome** | Middle + Reverse + Compare | 13 |
| **Sort List** | Middle + Recurse + Merge | 11 |
| **Cycle Entry** | Fast-Slow + Reset + Walk | 5 |

---

# The 6-Question Template (Ask For EVERY Pattern)

Before writing ANY code, answer these 6 questions:

```
┌────────────────────────────────────────────────────────────────────┐
│ 1. SETUP: Which pointers do I need?                                │
│    □ prev, curr, next (reversal)                                   │
│    □ slow, fast (middle/cycle)                                     │
│    □ slow, fast with gap (Nth from end)                            │
│    □ dummy → head (merge/delete head)                              │
│    □ p1, p2 (two lists)                                            │
├────────────────────────────────────────────────────────────────────┤
│ 2. TERMINATION: When do I stop?                                    │
│    □ curr == null (processed all nodes)                            │
│    □ fast == null || fast.next == null (fast reached end)          │
│    □ slow == fast (cycle detected)                                 │
│    □ count == k (processed k nodes)                                │
├────────────────────────────────────────────────────────────────────┤
│ 3. SAVE BEFORE BREAK: What must I save before redirecting?         │
│    □ next = curr.next (before flipping curr.next)                  │
│    □ tail = segment_start (before reversing segment)               │
│    □ Nothing (not redirecting existing pointers)                   │
├────────────────────────────────────────────────────────────────────┤
│ 4. CORE MOVE: What changes each iteration?                         │
│    □ REDIRECT: curr.next = prev (flip arrow)                       │
│    □ SKIP: prev.next = curr.next (delete node)                     │
│    □ ATTACH: tail.next = node (build list)                         │
│    □ SWAP: multiple redirects (exchange nodes)                     │
├────────────────────────────────────────────────────────────────────┤
│ 5. ADVANCE: How do I move forward?                                 │
│    □ prev = curr; curr = next (reversal march)                     │
│    □ slow = slow.next; fast = fast.next.next (tortoise/hare)       │
│    □ slow = slow.next; fast = fast.next (same speed)               │
│    □ tail = tail.next (building list)                              │
├────────────────────────────────────────────────────────────────────┤
│ 6. EDGE CASES: What special cases exist?                           │
│    □ Empty list (head == null)                                     │
│    □ Single node (head.next == null)                               │
│    □ Two nodes                                                     │
│    □ Even vs odd length                                            │
│    □ Target is the head                                            │
│    □ Target doesn't exist                                          │
└────────────────────────────────────────────────────────────────────┘
```

---

# The #1 Linked List Mistake: "Save Before You Break"

This mistake causes 90% of linked list bugs. Burn this into your brain:

> **Once you redirect a pointer, the old value is GONE FOREVER.**

## The Wrong Way (Loses Data)

```java
// You want to reverse: A → B → C
// curr is at B

curr.next = prev;      // B now points backward to A
curr = curr.next;      // DISASTER! curr.next is now A, not C!
                       // You just went BACKWARD, not forward!
                       // C is lost forever!
```

## The Right Way (Save First)

```java
// You want to reverse: A → B → C
// curr is at B

ListNode next = curr.next;  // SAVE C before we lose it!
curr.next = prev;           // B now points backward to A (safe now!)
curr = next;                // Move to C (which we saved!)
```

## The Mantra

Before EVERY redirect, ask yourself:

> **"Am I about to lose access to something I still need?"**

If YES → **SAVE IT FIRST!**

## Visual Memory Aid

```
WRONG:
  curr → [B] → [C]
  curr.next = prev  →  curr → [B] → [A]  (C is GONE!)
  
RIGHT:
  next = curr.next  →  next points to [C] (SAVED!)
  curr.next = prev  →  curr → [B] → [A]  (C still accessible via next!)
```

---

# Quick Reference Card

## The 5 Setups at a Glance

| Setup | Pointers | Trigger Words | Memory Trick |
|-------|----------|---------------|--------------|
| Reversal | `prev, curr, next` | "reverse", "flip" | "Save, Flip, March, March" |
| Fast-Slow | `slow, fast` | "middle", "cycle" | "1 and 2, meet means loop" |
| Runner | `slow, fast` (gap) | "from end", "Nth last" | "Head start, march together" |
| Dummy | `dummy → head` | "merge", "delete head" | "When in doubt, dummy out" |
| Two-List | `p1, p2` | "two lists", "merge" | "Two fingers walking" |

## The 4 Moves at a Glance

| Move | Code | Use For |
|------|------|---------|
| Redirect | `A.next = X` | Reversing |
| Skip | `A.next = A.next.next` | Deleting |
| Attach | `tail.next = node` | Building |
| Swap | Multiple redirects | Exchanging |

---

# PATTERN 0: Reverse Linked List (LeetCode 206)

## Pattern Recognition Signal

**When you see:** "reverse a linked list", "flip the order", "return in reverse"

**Instant thought:** "Reversal Setup: prev, curr, next — redirect arrows!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4 → 5 → null
Output: 5 → 4 → 3 → 2 → 1 → null

We need to flip ALL the arrows!

Before: 1 → 2 → 3 → 4 → 5 → null
After:  null ← 1 ← 2 ← 3 ← 4 ← 5
```

### The Conga Line Analogy

```
Imagine a conga line where everyone holds the shoulder of the person IN FRONT:

  [Alice] → [Bob] → [Carol] → [Dave] → null
  
To reverse: each person turns around and grabs who WAS behind them!

  null ← [Alice] ← [Bob] ← [Carol] ← [Dave]

No one MOVES position — they just change WHO they're holding!
```

### Why 3 Pointers?

```
The problem: When you redirect curr.next, you LOSE access to the rest of the list!

  curr → next → [rest of list]
  
  If you do: curr.next = prev
  
  Now: prev ← curr    next → [rest of list]
                      ↑ How do we get here?!

Solution: SAVE next BEFORE you redirect!

  next = curr.next;   // Save it!
  curr.next = prev;   // Now safe to redirect
  curr = next;        // Use saved value to move forward
```

### The 3 Pointers Explained

```
prev: The already-reversed portion (starts as null)
      "Everything behind me is already flipped"

curr: The node we're currently processing
      "I'm about to flip my arrow"

next: Saved reference to curr.next
      "I saved this before curr.next got redirected"
```

### The Algorithm in Plain English

```
1. Start: prev = null, curr = head
2. While curr is not null:
   a. SAVE: next = curr.next (before we lose it!)
   b. REDIRECT: curr.next = prev (flip the arrow)
   c. ADVANCE: prev = curr, curr = next (move forward)
3. Return prev (it's now pointing to the old tail = new head)
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → null`

```
Initial State:
  prev = null
  curr = 1
  
  null    1 → 2 → 3 → null
   ↑      ↑
  prev   curr

═══════════════════════════════════════════════════════════

Step 1: Process node 1

  1. SAVE:     next = curr.next = 2
  2. REDIRECT: curr.next = prev → 1.next = null
  3. ADVANCE:  prev = curr = 1
               curr = next = 2

  State after Step 1:
  
  null ← 1    2 → 3 → null
         ↑    ↑
        prev curr
  
  "Node 1 now points backward to null"

═══════════════════════════════════════════════════════════

Step 2: Process node 2

  1. SAVE:     next = curr.next = 3
  2. REDIRECT: curr.next = prev → 2.next = 1
  3. ADVANCE:  prev = curr = 2
               curr = next = 3

  State after Step 2:
  
  null ← 1 ← 2    3 → null
              ↑    ↑
             prev curr
  
  "Node 2 now points backward to 1"

═══════════════════════════════════════════════════════════

Step 3: Process node 3

  1. SAVE:     next = curr.next = null
  2. REDIRECT: curr.next = prev → 3.next = 2
  3. ADVANCE:  prev = curr = 3
               curr = next = null

  State after Step 3:
  
  null ← 1 ← 2 ← 3    null
                 ↑      ↑
                prev   curr
  
  "Node 3 now points backward to 2"

═══════════════════════════════════════════════════════════

Loop ends: curr == null

Return prev = 3 (the new head!)

Output: 3 → 2 → 1 → null ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode reverseList(ListNode head) {
    ListNode prev = null;   // Nothing reversed yet
    ListNode curr = head;   // Start at the head
    
    while (curr != null) {
        // 1. SAVE the next node (before we lose it!)
        ListNode next = curr.next;
        
        // 2. REDIRECT: flip the arrow backward
        curr.next = prev;
        
        // 3. ADVANCE prev: move prev one step right
        prev = curr;
        
        // 4. ADVANCE curr: move curr one step right
        curr = next;
    }
    
    // prev is now the new head (was the last node)
    return prev;
}
```

---

## The Golden Rule: SAVE BEFORE YOU BREAK!

```java
// ❌ WRONG: Forgetting to save next
curr.next = prev;
curr = curr.next;  // BUG! curr.next is now prev, not the original next!

// ✅ CORRECT: Always save first
ListNode next = curr.next;  // Save
curr.next = prev;           // Redirect
curr = next;                // Move using saved value
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not saving next | Lose rest of list | `next = curr.next` FIRST |
| Returning head | Head is now the tail! | Return `prev` |
| Wrong loop condition | Off-by-one | `while (curr != null)` |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list (`null`) | Loop never runs, return `prev` (null) ✓ |
| Single node | One iteration, returns that node ✓ |
| Two nodes | Two iterations, works correctly ✓ |

---

## Mind-Map Anchor

```
REVERSE LINKED LIST
        │
        ▼
┌─────────────────────────┐
│ 3 pointers: prev,curr,next│
│ SAVE before REDIRECT    │
│ Flip arrow: curr→prev   │
│ Advance both pointers   │
│ Return prev (new head)  │
└─────────────────────────┘
```

**Memory phrase:** "Save next, flip arrow, move forward, return prev"

---

## 🔄 Variations & Twists

| Variation | Twist |
|-----------|-------|
| Reverse recursively | O(n) space due to call stack |
| Reverse in groups | Pattern 2 (k-Group) |
| Reverse a segment | Pattern 1 (Reverse II) |

## 🧠 Mind-Map Anchor

**`prev, curr, next` · save before break · flip arrow · return prev**

---

# PATTERN 1: Reverse Linked List II (LeetCode 92)

## Pattern Recognition Signal

**When you see:** "reverse between positions left and right", "reverse from index m to n", "reverse a portion/segment"

**Instant thought:** "Reversal Setup + Anchors (conn, tail) — find the node BEFORE the segment, mark the first node of segment, reverse, reconnect!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4 → 5, left=2, right=4
Output: 1 → 4 → 3 → 2 → 5

We need to reverse ONLY the segment from position 2 to 4!

Before: 1 → [2 → 3 → 4] → 5
After:  1 → [4 → 3 → 2] → 5
             ↑ reversed ↑
```

### The Train Car Analogy

```
Imagine a train where you only want to reverse cars 2 through 4,
leaving car 1 and car 5 in place:

  [Engine] → [Car1] → [Car2] → [Car3] → [Car4] → [Car5] → [Caboose]
                       ↑ reverse this segment ↑

You need:
1. A CONNECTOR: The car BEFORE the segment (Car1) — to reconnect after
2. A TAIL MARKER: The first car of segment (Car2) — becomes LAST after reversal
3. Standard reversal within the segment
4. Reconnect: Connector→NewFirst, OldFirst→RestOfTrain
```

### Why Do We Need conn and tail?

```
The problem: After reversing 2→3→4, we have:
  
  1 → ?    null ← 2 ← 3 ← 4    5 → null
  
  How do we reconnect?
  - We need to know what comes BEFORE the segment (node 1 = conn)
  - We need to know the OLD first node (node 2 = tail, now last)
  
  conn.next = 4 (new head of reversed segment)
  tail.next = 5 (rest of the list)
```

### Why Dummy Head?

```
What if left = 1? (Reverse from the very beginning)

  Input: 1 → 2 → 3 → 4 → 5, left=1, right=3
  
  There's NO node before position 1!
  
  Solution: Create a dummy node that points to head.
  Now dummy is "before" position 1.
  
  dummy → 1 → 2 → 3 → 4 → 5
    ↑
   conn (when left=1)
```

### The Algorithm in Plain English

```
1. Create dummy node pointing to head (handles left=1 edge case)
2. Move conn to position left-1 (the node BEFORE the segment)
3. Mark tail = conn.next (first node of segment, will become last)
4. Reverse exactly (right - left + 1) nodes using standard reversal
5. Reconnect:
   - conn.next = prev (new head of reversed segment)
   - tail.next = curr (the rest of the list after the segment)
6. Return dummy.next
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 4 → 5`, left=2, right=4

```
═══════════════════════════════════════════════════════════

Initial State (with dummy):

  dummy → 1 → 2 → 3 → 4 → 5 → null
  
  conn = dummy (will move to position left-1)

═══════════════════════════════════════════════════════════

Step 1: Move conn to position left-1 = 1

  Loop: for i = 1; i < 2; i++ → one iteration
        conn = conn.next = node 1
  
  dummy → 1 → 2 → 3 → 4 → 5 → null
          ↑
         conn
  
  "conn is now at the node BEFORE the segment"

═══════════════════════════════════════════════════════════

Step 2: Mark tail = conn.next = node 2

  dummy → 1 → 2 → 3 → 4 → 5 → null
          ↑    ↑
         conn tail
  
  "tail marks the first node of segment (will become last)"

═══════════════════════════════════════════════════════════

Step 3: Reverse the segment [positions 2,3,4]

  Setup: prev = null, curr = conn.next = node 2
  Iterations: right - left + 1 = 4 - 2 + 1 = 3

  ─────────────────────────────────────────────────────────
  
  Iteration 1 (i=0): Process node 2
  
    Before:
      prev = null
      curr = 2
      
      null    2 → 3 → 4 → 5 → null
       ↑      ↑
      prev   curr
    
    Actions:
      next = curr.next = 3         // SAVE
      curr.next = prev → 2.next = null  // REDIRECT
      prev = curr = 2              // ADVANCE prev
      curr = next = 3              // ADVANCE curr
    
    After:
      null ← 2    3 → 4 → 5 → null
             ↑    ↑
            prev curr
  
  ─────────────────────────────────────────────────────────
  
  Iteration 2 (i=1): Process node 3
  
    Before:
      prev = 2
      curr = 3
    
    Actions:
      next = curr.next = 4         // SAVE
      curr.next = prev → 3.next = 2  // REDIRECT
      prev = curr = 3              // ADVANCE prev
      curr = next = 4              // ADVANCE curr
    
    After:
      null ← 2 ← 3    4 → 5 → null
                 ↑    ↑
                prev curr
  
  ─────────────────────────────────────────────────────────
  
  Iteration 3 (i=2): Process node 4
  
    Before:
      prev = 3
      curr = 4
    
    Actions:
      next = curr.next = 5         // SAVE
      curr.next = prev → 4.next = 3  // REDIRECT
      prev = curr = 4              // ADVANCE prev
      curr = next = 5              // ADVANCE curr
    
    After:
      null ← 2 ← 3 ← 4    5 → null
                     ↑    ↑
                    prev curr
  
  "Segment is now reversed: 4 → 3 → 2 → null"

═══════════════════════════════════════════════════════════

Step 4: Reconnect

  Current state:
    dummy → 1 → ?    null ← 2 ← 3 ← 4    5 → null
            ↑              ↑         ↑    ↑
           conn          tail      prev  curr
  
  Action 1: conn.next = prev
    1.next = 4 (connect to new head of reversed segment)
    
  Action 2: tail.next = curr
    2.next = 5 (connect old first to rest of list)
  
  Final state:
    dummy → 1 → 4 → 3 → 2 → 5 → null

═══════════════════════════════════════════════════════════

Return dummy.next = node 1

Output: 1 → 4 → 3 → 2 → 5 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode reverseBetween(ListNode head, int left, int right) {
    // Create dummy to handle edge case where left = 1
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    // Step 1: Move conn to the node BEFORE position 'left'
    // After this loop, conn is at position (left - 1)
    ListNode conn = dummy;
    for (int i = 1; i < left; i++) {
        conn = conn.next;
    }
    
    // Step 2: Mark the tail (first node of segment, will become last)
    // CRITICAL: Save this BEFORE we start reversing!
    ListNode tail = conn.next;
    
    // Step 3: Reverse the segment [left, right]
    // Standard reversal, but only for (right - left + 1) nodes
    ListNode prev = null;
    ListNode curr = conn.next;  // Start at first node of segment
    for (int i = 0; i <= right - left; i++) {
        ListNode next = curr.next;  // SAVE before we lose it
        curr.next = prev;           // REDIRECT arrow backward
        prev = curr;                // ADVANCE prev
        curr = next;                // ADVANCE curr
    }
    // After loop: prev = new head of segment, curr = first node after segment
    
    // Step 4: Reconnect the segment to the rest of the list
    conn.next = prev;   // Connect node before segment to new head
    tail.next = curr;   // Connect old first (now last) to rest of list
    
    return dummy.next;  // Return actual head (dummy.next)
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting to save `tail` before reversing | After reversal, you can't find where segment started! | `tail = conn.next` BEFORE the reversal loop |
| Off-by-one finding `conn` | conn should be at position left-1, not left | Loop condition: `i < left` (not `i <= left`) |
| Not using dummy head | If left=1, there's no node before segment | Always use dummy: `dummy.next = head` |
| Wrong number of iterations | Reversing too many or too few nodes | Exactly `right - left + 1` iterations |
| Forgetting to reconnect both ends | List becomes disconnected | Both `conn.next = prev` AND `tail.next = curr` |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| `left = 1` | Dummy handles it — conn = dummy |
| `left = right` | Reverse 1 node = no change (still works) |
| `right = length` | tail.next = null (curr is null after loop) |
| Single node list | If left=right=1, returns same node |

---

## Mind-Map Anchor

```
REVERSE LINKED LIST II
         │
         ▼
┌─────────────────────────────────┐
│ 1. Create dummy (handles left=1)│
│ 2. Move conn to position left-1 │
│ 3. Mark tail = conn.next        │
│ 4. Reverse (right-left+1) nodes │
│ 5. Reconnect:                   │
│    conn.next = prev (new head)  │
│    tail.next = curr (rest)      │
└─────────────────────────────────┘
```

**Memory phrase:** "Dummy, conn, tail, reverse, reconnect both ends"

---

# PATTERN 2: Reverse Nodes in k-Group (LeetCode 25)

## Pattern Recognition Signal

**When you see:** "reverse every k nodes", "reverse in groups of k", "leave remainder as-is"

**Instant thought:** "Pattern 1 in a loop! For each group: count k, reverse, reconnect, move to next group."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4 → 5, k=2
Output: 2 → 1 → 4 → 3 → 5

Reverse every GROUP of k nodes!
If the last group has fewer than k nodes, leave them as-is.

Groups: [1,2] [3,4] [5]
After:  [2,1] [4,3] [5]  ← 5 stays because it's alone
```

### The Marching Band Analogy

```
Imagine a marching band in rows of k people:

  Row 1: [A] [B]     ← k=2 people, reverse!
  Row 2: [C] [D]     ← k=2 people, reverse!
  Row 3: [E]         ← only 1 person, stay as-is!

After reversal:
  Row 1: [B] [A]
  Row 2: [D] [C]
  Row 3: [E]         ← unchanged

Key insight: Each row is independent!
We just need to reverse each row and reconnect them.
```

### Why This is Pattern 1 in a Loop

```
Pattern 1: Reverse a segment once
Pattern 2: Reverse multiple segments (each of size k)

For each group:
1. CHECK: Do we have k nodes? If not, stop.
2. REVERSE: Use standard reversal on this group
3. RECONNECT: Link previous group's tail to this group's new head
4. ADVANCE: Move to the next group
```

### The Key Pointers

```
groupPrev: The node BEFORE the current group
           (Used to reconnect after reversal)

kth:       The k-th node of current group
           (Will become the new head after reversal)

groupNext: The node AFTER the current group
           (Where the reversed group should point to)

After reversing group [A,B,C] with k=3:
  Before: groupPrev → A → B → C → groupNext
  After:  groupPrev → C → B → A → groupNext
```

### The Algorithm in Plain English

```
1. Create dummy node, set groupPrev = dummy
2. Loop forever:
   a. Count k nodes from groupPrev.next
   b. If fewer than k nodes exist, BREAK (we're done)
   c. Mark kth node and groupNext (node after group)
   d. Reverse the group (standard reversal)
   e. Reconnect: groupPrev.next = kth (new head)
   f. Update groupPrev = old first node (now last)
3. Return dummy.next
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 4 → 5`, k=2

```
═══════════════════════════════════════════════════════════

Initial State:

  dummy → 1 → 2 → 3 → 4 → 5 → null
    ↑
  groupPrev

═══════════════════════════════════════════════════════════

GROUP 1: Process nodes 1 and 2

  Step 1a: Count k=2 nodes from groupPrev.next
    kth starts at groupPrev (dummy)
    i=0: kth = kth.next = 1
    i=1: kth = kth.next = 2
    kth = 2 (not null), so we have k nodes ✓
  
  Step 1b: Mark groupNext = kth.next = 3
  
    dummy → 1 → 2 → 3 → 4 → 5 → null
      ↑          ↑    ↑
   groupPrev   kth  groupNext
  
  Step 1c: Reverse the group
    
    Setup: prev = groupNext = 3
           curr = groupPrev.next = 1
    
    ─────────────────────────────────────────────────────
    
    Iteration 1: curr=1, curr != groupNext(3)
      next = curr.next = 2
      curr.next = prev → 1.next = 3
      prev = curr = 1
      curr = next = 2
      
      State: 1 → 3    2 → 3 → 4 → 5
             ↑        ↑
            prev    curr
    
    ─────────────────────────────────────────────────────
    
    Iteration 2: curr=2, curr != groupNext(3)
      next = curr.next = 3
      curr.next = prev → 2.next = 1
      prev = curr = 2
      curr = next = 3
      
      State: 2 → 1 → 3 → 4 → 5
             ↑       ↑
            prev   curr (= groupNext, stop!)
    
    ─────────────────────────────────────────────────────
    
    curr = groupNext, exit reversal loop
  
  Step 1d: Reconnect
    newGroupPrev = groupPrev.next = 1 (old first, now last)
    groupPrev.next = kth = 2 (new head)
    groupPrev = newGroupPrev = 1
    
    State after Group 1:
    
    dummy → 2 → 1 → 3 → 4 → 5 → null
                ↑
             groupPrev

═══════════════════════════════════════════════════════════

GROUP 2: Process nodes 3 and 4

  Step 2a: Count k=2 nodes from groupPrev.next (node 3)
    kth starts at groupPrev (node 1)
    i=0: kth = kth.next = 3
    i=1: kth = kth.next = 4
    kth = 4 (not null), so we have k nodes ✓
  
  Step 2b: Mark groupNext = kth.next = 5
  
    dummy → 2 → 1 → 3 → 4 → 5 → null
                ↑       ↑    ↑
             groupPrev kth groupNext
  
  Step 2c: Reverse the group
    
    Setup: prev = groupNext = 5
           curr = groupPrev.next = 3
    
    Iteration 1: curr=3
      next = 4, 3.next = 5, prev = 3, curr = 4
      
    Iteration 2: curr=4
      next = 5, 4.next = 3, prev = 4, curr = 5 (= groupNext, stop!)
    
    Reversed: 4 → 3 → 5
  
  Step 2d: Reconnect
    newGroupPrev = groupPrev.next = 3 (old first, now last)
    groupPrev.next = kth = 4 (new head)
    groupPrev = newGroupPrev = 3
    
    State after Group 2:
    
    dummy → 2 → 1 → 4 → 3 → 5 → null
                        ↑
                     groupPrev

═══════════════════════════════════════════════════════════

GROUP 3: Check for k=2 nodes

  Count from groupPrev.next (node 5):
    kth starts at groupPrev (node 3)
    i=0: kth = kth.next = 5
    i=1: kth = kth.next = null
    
  kth = null! Fewer than k nodes remain.
  BREAK out of loop.
  
  Node 5 stays as-is (not reversed).

═══════════════════════════════════════════════════════════

Return dummy.next = node 2

Output: 2 → 1 → 4 → 3 → 5 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode reverseKGroup(ListNode head, int k) {
    // Dummy node simplifies edge cases (like reversing from head)
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    // groupPrev: always points to the node BEFORE current group
    ListNode groupPrev = dummy;
    
    while (true) {
        // Step 1: Check if k nodes exist from groupPrev.next
        // We count by moving kth pointer k times
        ListNode kth = groupPrev;
        for (int i = 0; i < k && kth != null; i++) {
            kth = kth.next;
        }
        
        // If kth is null, fewer than k nodes remain — stop!
        if (kth == null) break;
        
        // Step 2: Mark the node AFTER this group
        ListNode groupNext = kth.next;
        
        // Step 3: Reverse the group
        // Trick: set prev = groupNext so last node of reversed group points there
        ListNode prev = groupNext;
        ListNode curr = groupPrev.next;  // First node of group
        
        // Reverse until we reach groupNext
        while (curr != groupNext) {
            ListNode next = curr.next;  // SAVE
            curr.next = prev;           // REDIRECT
            prev = curr;                // ADVANCE prev
            curr = next;                // ADVANCE curr
        }
        // After loop: prev = kth (new head), curr = groupNext
        
        // Step 4: Reconnect
        // Save the old first node (now last) — this becomes groupPrev for next iteration
        ListNode newGroupPrev = groupPrev.next;
        
        // Connect previous group to new head of this group
        groupPrev.next = kth;
        
        // Move groupPrev to the last node of this reversed group
        groupPrev = newGroupPrev;
    }
    
    return dummy.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not checking if k nodes exist | Reverses partial groups incorrectly | Count k nodes first, break if `kth == null` |
| Wrong groupPrev update | Loses track of where to reconnect | `groupPrev = groupPrev.next` (old first, now last) |
| Setting prev = null in reversal | Last node of group points to null instead of groupNext | Set `prev = groupNext` before reversing |
| Off-by-one in counting | Reverses wrong number of nodes | Count exactly k times: `for (i = 0; i < k; ...)` |
| Forgetting dummy head | Can't handle k=1 or reversing from head | Always use dummy |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| `k = 1` | No reversal needed (each group is 1 node) |
| `k > length` | No reversal (kth becomes null immediately) |
| `k = length` | Entire list reversed once |
| Empty list | Loop never runs, returns null |

---

## Mind-Map Anchor

```
REVERSE K-GROUP
       │
       ▼
┌─────────────────────────────────┐
│ For each group:                 │
│ 1. Count k nodes (kth pointer)  │
│ 2. If kth=null, BREAK           │
│ 3. Mark groupNext = kth.next    │
│ 4. Reverse with prev=groupNext  │
│ 5. Reconnect: groupPrev→kth     │
│ 6. groupPrev = old first (now   │
│    last)                        │
└─────────────────────────────────┘
```

**Memory phrase:** "Count k, reverse group, reconnect, advance groupPrev, repeat"

---

# PATTERN 3: Swap Nodes in Pairs (LeetCode 24)

## Pattern Recognition Signal

**When you see:** "swap every two adjacent nodes", "swap pairs", "exchange neighboring nodes"

**Instant thought:** "This is k-Group with k=2! Or simpler: for each pair, do 3 pointer redirects."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4
Output: 2 → 1 → 4 → 3

Swap every PAIR of adjacent nodes!

Before: [1 → 2] → [3 → 4]
After:  [2 → 1] → [4 → 3]
```

### The Dance Partner Swap Analogy

```
Imagine pairs of dance partners in a line:

  [Alice-Bob] → [Carol-Dave] → [Eve]
  
Each pair swaps positions:

  [Bob-Alice] → [Dave-Carol] → [Eve]
  
Eve has no partner, so she stays in place.
```

### The Three Redirects

```
For a pair (A, B) with prev pointing before them:

  Before: prev → A → B → C
  After:  prev → B → A → C

We need THREE pointer changes:
  1. A.next = C      (A now points to what's after B)
  2. B.next = A      (B now points to A)
  3. prev.next = B   (prev now points to B)

Order matters! If we change prev.next first, we lose access to A!
```

### Why Dummy Head?

```
What if we need to swap the first pair?

  Input: 1 → 2 → 3 → 4
  
  There's no "prev" before node 1!
  
  Solution: Create dummy that points to head.
  
  dummy → 1 → 2 → 3 → 4
    ↑
   prev (for first pair)
```

### The Algorithm in Plain English

```
1. Create dummy node, set prev = dummy
2. While prev.next AND prev.next.next exist (we have a pair):
   a. Identify: first = prev.next, second = prev.next.next
   b. Redirect 1: first.next = second.next (A → C)
   c. Redirect 2: second.next = first (B → A)
   d. Redirect 3: prev.next = second (prev → B)
   e. Move prev to first (which is now second in the swapped pair)
3. Return dummy.next
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 4`

```
═══════════════════════════════════════════════════════════

Initial State:

  dummy → 1 → 2 → 3 → 4 → null
    ↑
   prev

═══════════════════════════════════════════════════════════

PAIR 1: Swap nodes 1 and 2

  Step 1: Identify the pair
    first = prev.next = 1
    second = prev.next.next = 2
    
    dummy → 1 → 2 → 3 → 4 → null
      ↑     ↑    ↑
     prev first second
  
  Step 2: Redirect first.next = second.next
    1.next = 3
    
    dummy → 1 → 3 → 4 → null
            ↓
            2 → 3 (2 still points to 3)
    
    "First now points past second"
  
  Step 3: Redirect second.next = first
    2.next = 1
    
    dummy → 1 → 3 → 4 → null
            ↑
            2
    
    "Second now points to first"
  
  Step 4: Redirect prev.next = second
    dummy.next = 2
    
    dummy → 2 → 1 → 3 → 4 → null
    
    "Prev now points to second (new head of pair)"
  
  Step 5: Move prev = first
    prev = 1
    
    dummy → 2 → 1 → 3 → 4 → null
                ↑
               prev
    
    "Prev moves to first (now second in the swapped pair)"

═══════════════════════════════════════════════════════════

PAIR 2: Swap nodes 3 and 4

  Check: prev.next = 3 (exists), prev.next.next = 4 (exists)
  We have a pair! ✓
  
  Step 1: Identify the pair
    first = prev.next = 3
    second = prev.next.next = 4
    
    dummy → 2 → 1 → 3 → 4 → null
                ↑    ↑    ↑
               prev first second
  
  Step 2: Redirect first.next = second.next
    3.next = null
    
    "First now points to null (end of list)"
  
  Step 3: Redirect second.next = first
    4.next = 3
    
    "Second now points to first"
  
  Step 4: Redirect prev.next = second
    1.next = 4
    
    dummy → 2 → 1 → 4 → 3 → null
    
    "Prev now points to second"
  
  Step 5: Move prev = first
    prev = 3
    
    dummy → 2 → 1 → 4 → 3 → null
                        ↑
                       prev

═══════════════════════════════════════════════════════════

Check for more pairs:

  prev.next = null
  
  Condition: prev.next != null? NO
  
  Loop exits.

═══════════════════════════════════════════════════════════

Return dummy.next = node 2

Output: 2 → 1 → 4 → 3 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode swapPairs(ListNode head) {
    // Dummy node handles edge case of swapping first pair
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    // prev always points to the node BEFORE the current pair
    ListNode prev = dummy;
    
    // Continue while we have at least 2 nodes to swap
    while (prev.next != null && prev.next.next != null) {
        // Identify the pair
        ListNode first = prev.next;       // A (will become second)
        ListNode second = prev.next.next; // B (will become first)
        
        // THREE REDIRECTS (order matters!):
        
        // Redirect 1: A → C (first points past second)
        first.next = second.next;
        
        // Redirect 2: B → A (second points to first)
        second.next = first;
        
        // Redirect 3: prev → B (prev points to new head of pair)
        prev.next = second;
        
        // Move prev to first (which is now the second node of swapped pair)
        // This positions prev right before the next pair
        prev = first;
    }
    
    return dummy.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Wrong redirect order | Lose access to nodes before redirecting | Do first.next first, then second.next, then prev.next |
| Moving prev incorrectly | prev should be at first (now second in pair) | `prev = first` not `prev = second` |
| Not checking both conditions | NPE if only one node left | Check `prev.next != null && prev.next.next != null` |
| Forgetting dummy head | Can't swap first pair | Always use dummy |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list | Loop never runs, returns null |
| Single node | prev.next.next is null, loop never runs |
| Odd number of nodes | Last node stays in place |
| Two nodes | One swap, returns second → first |

---

## Alternative: Using k-Group with k=2

```java
// This problem is literally Pattern 2 with k=2!
// But the direct approach above is simpler and more efficient.
```

---

## Mind-Map Anchor

```
SWAP PAIRS
     │
     ▼
┌─────────────────────────────────┐
│ For each pair (first, second):  │
│                                 │
│ THREE REDIRECTS:                │
│ 1. first.next = second.next    │
│ 2. second.next = first         │
│ 3. prev.next = second          │
│                                 │
│ Then: prev = first             │
└─────────────────────────────────┘
```

**Memory phrase:** "First→past, Second→first, Prev→second, move prev to first"

---

# PATTERN 4: Linked List Cycle (LeetCode 141)

## Pattern Recognition Signal

**When you see:** "detect cycle", "is there a loop", "does list circle back"

**Instant thought:** "Fast-Slow pointers! If they meet → cycle. If fast hits null → no cycle."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input with cycle:
  1 → 2 → 3 → 4
      ↑       ↓
      └───────┘
  
  Node 4 points back to node 2 — there's a cycle!
  
Input without cycle:
  1 → 2 → 3 → null
  
  Normal list, ends at null — no cycle.
```

### The Running Track Analogy

```
Imagine two runners on a track:
- SLOW runner (tortoise): runs 1 lap per hour
- FAST runner (hare): runs 2 laps per hour

SCENARIO 1: Circular track (CYCLE)
┌─────────────────────────────────────────────────────────┐
│                                                          │
│  The fast runner will eventually LAP the slow runner!   │
│  They MUST meet at some point.                          │
│                                                          │
│  Why? Fast gains 1 position per step.                   │
│  Eventually, fast catches up from behind.               │
│                                                          │
└─────────────────────────────────────────────────────────┘

SCENARIO 2: Straight track (NO CYCLE)
┌─────────────────────────────────────────────────────────┐
│                                                          │
│  The fast runner just reaches the finish line first!    │
│  They never meet because fast is always ahead.          │
│                                                          │
│  Fast hits the end (null) → no cycle.                   │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

### Why Does Fast Catch Slow in a Cycle?

```
Let's say slow is at position S, fast is at position F.
Both are inside the cycle of length C.

Each step:
- Slow moves to S + 1
- Fast moves to F + 2

Distance between them decreases by 1 each step!

If fast is 5 positions behind slow (in circular terms):
  Step 1: 4 behind
  Step 2: 3 behind
  Step 3: 2 behind
  Step 4: 1 behind
  Step 5: 0 behind → THEY MEET!

Fast ALWAYS catches slow within C steps (cycle length).
```

### The Algorithm in Plain English

```
1. Start both pointers at head
2. Move slow by 1, fast by 2
3. If they meet → cycle exists!
4. If fast reaches null → no cycle
```

---

## Visual Dry Run (Step-by-Step)

**Input with cycle:** `1 → 2 → 3 → 4 → (back to 2)`

```
        1 → 2 → 3 → 4
            ↑       ↓
            └───────┘

═══════════════════════════════════════════════════════════

Initial:
  slow = 1
  fast = 1

═══════════════════════════════════════════════════════════

Step 1:
  slow = slow.next = 2
  fast = fast.next.next = 3
  
  slow == fast? 2 == 3? NO, continue
  
  Position: slow at 2, fast at 3

═══════════════════════════════════════════════════════════

Step 2:
  slow = slow.next = 3
  fast = fast.next.next = 4.next.next = 2.next = 3
  (fast went: 3 → 4 → 2, then 2 → 3)
  
  Wait, let me redo: fast at 3, moves to 3.next.next = 4.next = 2
  
  slow = 3
  fast = 2 (wrapped around!)
  
  slow == fast? 3 == 2? NO, continue

═══════════════════════════════════════════════════════════

Step 3:
  slow = slow.next = 4
  fast = fast.next.next = 2.next.next = 3.next = 4
  
  slow = 4
  fast = 4
  
  slow == fast? 4 == 4? YES! ✓

═══════════════════════════════════════════════════════════

Return TRUE — cycle detected!
```

**Input without cycle:** `1 → 2 → 3 → null`

```
═══════════════════════════════════════════════════════════

Initial:
  slow = 1
  fast = 1

Step 1:
  slow = 2
  fast = 3
  
  fast != null && fast.next != null? 
  fast = 3, fast.next = null → CONDITION FAILS!

═══════════════════════════════════════════════════════════

Loop exits, return FALSE — no cycle!
```

---

## The Code (With Line-by-Line Explanation)

```java
boolean hasCycle(ListNode head) {
    // Both start at head
    ListNode slow = head;
    ListNode fast = head;
    
    // Fast needs to move 2 steps, so check fast AND fast.next
    while (fast != null && fast.next != null) {
        slow = slow.next;        // Tortoise: 1 step
        fast = fast.next.next;   // Hare: 2 steps
        
        if (slow == fast) {
            return true;  // They met inside the cycle!
        }
    }
    
    // Fast reached the end — no cycle
    return false;
}
```

---

## Why Check `fast != null && fast.next != null`?

```
fast moves 2 steps: fast = fast.next.next

For this to be safe:
1. fast must not be null (can't call .next on null)
2. fast.next must not be null (can't call .next.next if .next is null)

If either is null → we've reached the end → no cycle!
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Only checking `fast != null` | `fast.next.next` crashes if `fast.next` is null | Check both conditions |
| Comparing values instead of references | Two nodes can have same value but be different | Use `slow == fast` (reference equality) |
| Moving before comparing | Might miss the meeting point | Compare AFTER moving |

---

## Mind-Map Anchor

```
LINKED LIST CYCLE
        │
        ▼
┌─────────────────────────┐
│ Fast-Slow (Tortoise-Hare)│
│ slow: 1 step            │
│ fast: 2 steps           │
│ Meet → cycle exists     │
│ fast=null → no cycle    │
└─────────────────────────┘
```

**Memory phrase:** "Fast catches slow in a cycle, hits null if no cycle"

---

```java
// WRONG: Checking in wrong order
while (fast.next != null && fast != null)  // NPE if fast is null!

// CORRECT: Check fast first
while (fast != null && fast.next != null)
```

## 🧠 Mind-Map Anchor

**slow=1, fast=2 · meet = cycle · null = no cycle**

---

# PATTERN 5: Linked List Cycle II (LeetCode 142)

## Pattern Recognition Signal

**When you see:** "find where the cycle begins", "return the node where the cycle starts", "find the entry point of the loop"

**Instant thought:** "Floyd's Algorithm Phase 2! First detect cycle (fast-slow meet), then reset slow to head and move both at speed 1 — they meet at cycle entry!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:
        1 → 2 → 3 → 4 → 5
                ↑       ↓
                └───────┘

Output: Node 3 (where the cycle begins)

Pattern 4 tells us IF there's a cycle.
Pattern 5 tells us WHERE it starts!
```

### The Two-Phase Detective Analogy

```
PHASE 1: "Is there a crime scene?" (Detect cycle)
  - Send two detectives: Slow (walks) and Fast (runs)
  - If they meet → crime scene exists (cycle found)
  - If Fast reaches dead end → no crime scene

PHASE 2: "Where's the entrance?" (Find cycle entry)
  - Reset Slow to the starting point (head)
  - Both detectives now WALK at the same speed
  - Where they meet = the entrance to the crime scene!
```

### Why Does Phase 2 Work? (The Math)

```
Let's define:
  F = distance from head to cycle entry
  C = cycle length
  a = distance from cycle entry to meeting point

When slow and fast meet:
  - Slow traveled: F + a
  - Fast traveled: F + a + nC (went around cycle n times)

Since fast moves 2x speed:
  2(F + a) = F + a + nC
  F + a = nC
  F = nC - a

This means:
  Distance from HEAD to entry = Distance from MEETING POINT to entry!
  (going forward, possibly wrapping around the cycle)

So if we start one pointer at head and one at meeting point,
both moving at speed 1, they'll meet at the cycle entry!
```

### Visual Proof

```
Head ────F──── Entry ────a──── Meeting Point
                  │                    │
                  └────── C - a ───────┘
                  
From meeting point to entry (going forward) = C - a
From head to entry = F

Since F = nC - a:
  F = nC - a = (n-1)C + (C - a)
  
Both distances are equivalent (mod C)!
They meet at the entry point.
```

### The Algorithm in Plain English

```
PHASE 1: Detect cycle (same as Pattern 4)
  1. slow = head, fast = head
  2. Move slow by 1, fast by 2
  3. If they meet → cycle exists, go to Phase 2
  4. If fast reaches null → no cycle, return null

PHASE 2: Find cycle entry
  1. Reset slow to head (fast stays at meeting point)
  2. Move BOTH at speed 1
  3. When they meet → that's the cycle entry!
  4. Return that node
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 4 → 5 → (back to 3)`

```
        1 → 2 → 3 → 4 → 5
                ↑       ↓
                └───────┘

F (head to entry) = 2 (nodes 1, 2)
Cycle entry = node 3
Cycle length C = 3 (nodes 3, 4, 5)

═══════════════════════════════════════════════════════════

PHASE 1: Detect Cycle

Initial:
  slow = 1
  fast = 1

─────────────────────────────────────────────────────────

Step 1:
  slow = slow.next = 2
  fast = fast.next.next = 3
  
  slow == fast? 2 == 3? NO
  
  Position: slow at 2, fast at 3

─────────────────────────────────────────────────────────

Step 2:
  slow = slow.next = 3
  fast = fast.next.next = 3.next.next = 4.next = 5
  
  slow == fast? 3 == 5? NO
  
  Position: slow at 3, fast at 5

─────────────────────────────────────────────────────────

Step 3:
  slow = slow.next = 4
  fast = fast.next.next = 5.next.next = 3.next = 4
  
  slow == fast? 4 == 4? YES! ✓
  
  Meeting point = node 4

═══════════════════════════════════════════════════════════

PHASE 2: Find Cycle Entry

  Reset slow to head:
    slow = 1
    fast = 4 (stays at meeting point)

─────────────────────────────────────────────────────────

Step 1:
  slow = slow.next = 2
  fast = fast.next = 5
  
  slow == fast? 2 == 5? NO

─────────────────────────────────────────────────────────

Step 2:
  slow = slow.next = 3
  fast = fast.next = 3 (5.next wraps to 3)
  
  slow == fast? 3 == 3? YES! ✓
  
  They meet at node 3!

═══════════════════════════════════════════════════════════

Return node 3 (the cycle entry) ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode detectCycle(ListNode head) {
    // Initialize both pointers at head
    ListNode slow = head;
    ListNode fast = head;
    
    // PHASE 1: Detect cycle (same as Pattern 4)
    while (fast != null && fast.next != null) {
        slow = slow.next;        // Tortoise: 1 step
        fast = fast.next.next;   // Hare: 2 steps
        
        if (slow == fast) {
            // Cycle detected! Now find the entry point.
            
            // PHASE 2: Find cycle entry
            // Reset slow to head, keep fast at meeting point
            slow = head;
            
            // Move both at speed 1 until they meet
            while (slow != fast) {
                slow = slow.next;  // Both move at speed 1 now!
                fast = fast.next;
            }
            
            // They meet at the cycle entry!
            return slow;
        }
    }
    
    // Fast reached null — no cycle exists
    return null;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting to reset slow to head | Phase 2 won't work — both start at meeting point | `slow = head` after detecting cycle |
| Moving fast at speed 2 in Phase 2 | They won't meet at entry | Both must move at speed 1 in Phase 2 |
| Returning meeting point instead of entry | Meeting point ≠ cycle entry | Continue to Phase 2 to find actual entry |
| Not handling no-cycle case | Returns garbage | Return null if fast reaches null |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| No cycle | Fast reaches null, return null |
| Cycle at head | Entry = head, Phase 2 returns immediately |
| Single node with self-loop | Entry = that node |
| Two nodes with cycle | Works correctly |

---

## Why Phase 2 Works: Intuitive Explanation

```
Think of it this way:

When slow and fast meet inside the cycle:
- Slow has traveled some distance D
- Fast has traveled 2D (twice as far)

The extra distance fast traveled (D) is exactly
the cycle length (or a multiple of it).

Now, the distance from head to entry (F) plus
the distance from entry to meeting point (a) equals D.

So: F + a = D = nC (multiple of cycle length)
    F = nC - a

This means if you walk F steps from the meeting point,
you end up at the entry (after going around the cycle).

And F steps from head also reaches the entry!

So both pointers meet at the entry. QED.
```

---

## Mind-Map Anchor

```
LINKED LIST CYCLE II
         │
         ▼
┌─────────────────────────────────┐
│ PHASE 1: Detect cycle           │
│   slow=1, fast=2                │
│   If meet → cycle exists        │
│                                 │
│ PHASE 2: Find entry             │
│   Reset slow to HEAD            │
│   Both move at speed 1          │
│   Meet at cycle ENTRY!          │
└─────────────────────────────────┘
```

**Memory phrase:** "Phase 1: detect (fast=2). Phase 2: reset slow to head, both speed 1, meet at entry."

---

# PATTERN 6: Middle of Linked List (LeetCode 876)

## Pattern Recognition Signal

**When you see:** "find the middle node", "split the list in half", "return the middle"

**Instant thought:** "Fast-Slow! When fast reaches the end, slow is at the middle."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4 → 5
Output: Node 3 (the middle)

Input:  1 → 2 → 3 → 4 → 5 → 6
Output: Node 4 (the SECOND middle for even-length lists)

We need to find the middle without knowing the length!
```

### The Race Track Analogy

```
Imagine two runners on a track:
- SLOW runs at speed 1
- FAST runs at speed 2

When FAST finishes the race, SLOW is exactly HALFWAY!

Why? Fast covers 2x the distance in the same time.
So when fast covers the full length, slow covers half.

  START ─────────────────────────────────── END
    │                                        │
    └── slow is here when fast reaches end ──┘
              (halfway point)
```

### Odd vs Even Length

```
ODD LENGTH (5 nodes): 1 → 2 → 3 → 4 → 5
  
  slow: 1 → 2 → 3
  fast: 1 → 3 → 5 → (can't move, fast.next = null)
  
  Middle = 3 (the exact middle) ✓

EVEN LENGTH (6 nodes): 1 → 2 → 3 → 4 → 5 → 6
  
  slow: 1 → 2 → 3 → 4
  fast: 1 → 3 → 5 → (can't move, fast.next.next would be null)
  
  Middle = 4 (the SECOND of two middles) ✓

Note: LeetCode 876 asks for the second middle for even-length lists.
```

### Why This Works Mathematically

```
Let n = list length

Fast moves 2 steps per iteration.
When fast reaches the end:
  - Fast has moved n steps (approximately)
  - Slow has moved n/2 steps
  
Slow is at position n/2 = the middle!
```

### The Algorithm in Plain English

```
1. Start both slow and fast at head
2. While fast can move 2 steps (fast != null AND fast.next != null):
   - Move slow by 1
   - Move fast by 2
3. When loop ends, slow is at the middle
4. Return slow
```

---

## Visual Dry Run (Step-by-Step)

**Input (Odd):** `1 → 2 → 3 → 4 → 5`

```
═══════════════════════════════════════════════════════════

Initial:
  slow = 1
  fast = 1
  
  1 → 2 → 3 → 4 → 5 → null
  ↑
  slow, fast

═══════════════════════════════════════════════════════════

Step 1:
  Check: fast(1) != null ✓, fast.next(2) != null ✓
  
  slow = slow.next = 2
  fast = fast.next.next = 3
  
  1 → 2 → 3 → 4 → 5 → null
      ↑   ↑
     slow fast

═══════════════════════════════════════════════════════════

Step 2:
  Check: fast(3) != null ✓, fast.next(4) != null ✓
  
  slow = slow.next = 3
  fast = fast.next.next = 5
  
  1 → 2 → 3 → 4 → 5 → null
          ↑       ↑
         slow    fast

═══════════════════════════════════════════════════════════

Step 3:
  Check: fast(5) != null ✓, fast.next(null) != null ✗
  
  Loop exits!

═══════════════════════════════════════════════════════════

Return slow = 3 (the middle) ✓
```

**Input (Even):** `1 → 2 → 3 → 4`

```
═══════════════════════════════════════════════════════════

Initial:
  slow = 1
  fast = 1

═══════════════════════════════════════════════════════════

Step 1:
  Check: fast(1) != null ✓, fast.next(2) != null ✓
  
  slow = 2
  fast = 3

═══════════════════════════════════════════════════════════

Step 2:
  Check: fast(3) != null ✓, fast.next(4) != null ✓
  
  slow = 3
  fast = null (3.next.next = 4.next = null)

═══════════════════════════════════════════════════════════

Step 3:
  Check: fast(null) != null ✗
  
  Loop exits!

═══════════════════════════════════════════════════════════

Return slow = 3 (the second middle) ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode middleNode(ListNode head) {
    // Both pointers start at head
    ListNode slow = head;
    ListNode fast = head;
    
    // Continue while fast can move 2 steps
    // fast != null: fast hasn't gone past the end
    // fast.next != null: fast can move one more step (for .next.next)
    while (fast != null && fast.next != null) {
        slow = slow.next;        // Slow moves 1 step
        fast = fast.next.next;   // Fast moves 2 steps
    }
    
    // When fast reaches end, slow is at middle
    return slow;
}
```

---

## Getting the FIRST Middle (Left-Middle)

```java
// For even-length lists, to get the FIRST middle instead of second:
// Start fast one step ahead!

ListNode getLeftMiddle(ListNode head) {
    ListNode slow = head;
    ListNode fast = head.next;  // Start fast one ahead!
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    return slow;  // Returns left-middle for even-length lists
}

// Example: 1 → 2 → 3 → 4
// With fast = head.next:
//   slow: 1 → 2
//   fast: 2 → 4 → null
// Returns 2 (left-middle) instead of 3 (right-middle)
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Only checking `fast != null` | `fast.next.next` crashes if `fast.next` is null | Check both `fast != null && fast.next != null` |
| Wrong order of conditions | `fast.next != null && fast != null` causes NPE | Check `fast != null` FIRST |
| Confusing left vs right middle | Different problems want different middles | Know which one you need |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Single node | Loop never runs, returns head |
| Two nodes | One iteration, returns second node |
| Empty list | Would crash — add null check if needed |

---

## Mind-Map Anchor

```
MIDDLE OF LINKED LIST
         │
         ▼
┌─────────────────────────────────┐
│ Fast-Slow Technique             │
│                                 │
│ slow = 1 step                   │
│ fast = 2 steps                  │
│                                 │
│ When fast reaches end,          │
│ slow is at middle!              │
│                                 │
│ For left-middle: fast = head.next│
└─────────────────────────────────┘
```

**Memory phrase:** "Slow by 1, fast by 2, fast finishes → slow at middle"

---

# PATTERN 7: Remove Nth Node from End (LeetCode 19)

## Pattern Recognition Signal

**When you see:** "remove Nth from end", "delete the kth last node", "one pass only"

**Instant thought:** "Runner/Gap technique! Give fast a head start of n+1, then move both. When fast hits null, slow is right BEFORE the target."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4 → 5, n=2
Output: 1 → 2 → 3 → 5

Remove the 2nd node from the END (which is node 4).

The challenge: We don't know the list length!
We can't just go to position (length - n).
```

### The Head Start Analogy

```
Imagine two runners on a track:
- FAST gets a HEAD START of n steps
- Then BOTH run at the same speed

When FAST reaches the finish line,
SLOW is exactly n steps behind!

  START ─────────────────────────────────── END
    │                                        │
    slow                                   fast
    │←──────────── n steps ────────────────→│
```

### Why Gap of n+1 (Not n)?

```
We want to DELETE a node, not just FIND it.
To delete node X, we need the node BEFORE X.

If gap = n:
  slow lands AT the target → can't delete (need prev)

If gap = n+1:
  slow lands BEFORE the target → can delete!
  
  slow.next = slow.next.next  // Skip the target
```

### Why Dummy Head?

```
What if n = list length? (Remove the first node)

  Input: 1 → 2 → 3, n=3 (remove node 1)
  
  There's no node before node 1!
  
  Solution: Create dummy that points to head.
  
  dummy → 1 → 2 → 3
    ↑
   slow can land here to delete node 1
```

### The Algorithm in Plain English

```
1. Create dummy node pointing to head
2. Set slow = dummy, fast = dummy
3. Advance fast by (n + 1) steps (create the gap)
4. Move both slow and fast until fast hits null
5. Now slow is right BEFORE the target
6. Delete: slow.next = slow.next.next
7. Return dummy.next
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 4 → 5`, n=2 (remove 4, the 2nd from end)

```
═══════════════════════════════════════════════════════════

Initial State (with dummy):

  dummy → 1 → 2 → 3 → 4 → 5 → null
    ↑
  slow, fast

═══════════════════════════════════════════════════════════

Step 1: Advance fast by n+1 = 3 steps

  i=0: fast = fast.next = 1
  i=1: fast = fast.next = 2
  i=2: fast = fast.next = 3
  
  dummy → 1 → 2 → 3 → 4 → 5 → null
    ↑              ↑
   slow          fast
   
  Gap between slow and fast = 3 nodes

═══════════════════════════════════════════════════════════

Step 2: Move both until fast = null

  ─────────────────────────────────────────────────────────
  
  Iteration 1: fast != null
    slow = slow.next = 1
    fast = fast.next = 4
    
    dummy → 1 → 2 → 3 → 4 → 5 → null
            ↑           ↑
           slow       fast
  
  ─────────────────────────────────────────────────────────
  
  Iteration 2: fast != null
    slow = slow.next = 2
    fast = fast.next = 5
    
    dummy → 1 → 2 → 3 → 4 → 5 → null
                ↑           ↑
               slow       fast
  
  ─────────────────────────────────────────────────────────
  
  Iteration 3: fast != null
    slow = slow.next = 3
    fast = fast.next = null
    
    dummy → 1 → 2 → 3 → 4 → 5 → null
                    ↑           ↑
                   slow       fast
  
  ─────────────────────────────────────────────────────────
  
  fast = null, loop exits

═══════════════════════════════════════════════════════════

Step 3: Delete the target node

  slow is at node 3
  slow.next is node 4 (the target!)
  slow.next.next is node 5
  
  Action: slow.next = slow.next.next
          3.next = 5
  
  Result:
    dummy → 1 → 2 → 3 → 5 → null
                    ↑
                   slow

═══════════════════════════════════════════════════════════

Return dummy.next = node 1

Output: 1 → 2 → 3 → 5 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode removeNthFromEnd(ListNode head, int n) {
    // Dummy node handles edge case of removing head
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    // Both pointers start at dummy
    ListNode slow = dummy;
    ListNode fast = dummy;
    
    // Step 1: Advance fast by n+1 steps (create the gap)
    // Why n+1? So slow lands BEFORE the target, not AT it
    for (int i = 0; i <= n; i++) {
        fast = fast.next;
    }
    
    // Step 2: Move both until fast hits null
    // The gap is maintained throughout
    while (fast != null) {
        slow = slow.next;
        fast = fast.next;
    }
    
    // Step 3: slow is now right BEFORE the target
    // Skip the target node
    slow.next = slow.next.next;
    
    // Return actual head (might have changed if we removed original head)
    return dummy.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Gap of n instead of n+1 | slow lands AT target, can't delete | Use gap of n+1: `for (i = 0; i <= n; ...)` |
| Not using dummy head | Can't handle removing head node | Always use dummy |
| Off-by-one in gap creation | Wrong node gets deleted | Trace through with small example |
| Forgetting to return dummy.next | Returns wrong head if head was removed | Always return `dummy.next` |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| n = list length | Remove head, dummy handles it |
| n = 1 | Remove last node |
| Single node, n=1 | Remove only node, return null |

---

## Mind-Map Anchor

```
REMOVE NTH FROM END
         │
         ▼
┌─────────────────────────────────┐
│ Runner/Gap Technique            │
│                                 │
│ 1. Create dummy                 │
│ 2. Advance fast by n+1          │
│ 3. Move both until fast=null    │
│ 4. slow is BEFORE target        │
│ 5. slow.next = slow.next.next   │
└─────────────────────────────────┘
```

**Memory phrase:** "Dummy, gap of n+1, move together, slow before target, skip it"

---

# PATTERN 8: Intersection of Two Linked Lists (LeetCode 160)

## Pattern Recognition Signal

**When you see:** "find intersection point", "where do two lists merge", "common node of two lists"

**Instant thought:** "Two pointers with path swapping! When one reaches null, switch to the other list's head. They'll meet at intersection!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:
  List A: 1 → 2 → 3
                    ↘
                      6 → 7 → null
                    ↗
  List B:     4 → 5

Output: Node 6 (where they merge)

Find the node where two lists PHYSICALLY merge.
(Same node in memory, not just same value!)
```

### The Two Paths Analogy

```
Imagine two people walking on different paths that merge:

  Alice's path: A ─────────────────┐
                                   ├──── Shared path ────→ END
  Bob's path:   B ─────────────────┘

If they walk at the same speed, they'll reach the merge point
at different times (because paths have different lengths).

THE TRICK: When one reaches the end, they START OVER on the OTHER path!

  Alice: A-path → END → B-path → ... → MERGE POINT
  Bob:   B-path → END → A-path → ... → MERGE POINT

Both travel the SAME total distance: (A-length) + (B-length)
So they meet at the merge point!
```

### Why Does This Work? (The Math)

```
Let:
  a = length of A's unique part
  b = length of B's unique part
  c = length of shared part

List A total = a + c
List B total = b + c

Pointer 1 travels: (a + c) + (b + c) = a + b + 2c
Pointer 2 travels: (b + c) + (a + c) = a + b + 2c

SAME DISTANCE! They meet at the intersection.

If no intersection (c = 0):
  Both pointers reach null at the same time.
  They "meet" at null → return null.
```

### Visual Proof

```
pA's journey: [A's unique] → [shared] → [B's unique] → [shared] → MEET
pB's journey: [B's unique] → [shared] → [A's unique] → [shared] → MEET

After switching:
  pA has traveled: a + c + b
  pB has traveled: b + c + a

Both are at the same position in the shared part!
```

### The Algorithm in Plain English

```
1. Start pA at headA, pB at headB
2. Move both forward one step at a time
3. When pA reaches null, redirect to headB
4. When pB reaches null, redirect to headA
5. When pA == pB, that's the intersection (or both are null)
6. Return pA (or pB, they're the same)
```

---

## Visual Dry Run (Step-by-Step)

**Input:**
```
List A: 1 → 2 → 3
                  ↘
                    6 → 7 → null
                  ↗
List B:     4 → 5

a = 3 (nodes 1, 2, 3)
b = 2 (nodes 4, 5)
c = 2 (nodes 6, 7)
```

```
═══════════════════════════════════════════════════════════

Initial:
  pA = 1
  pB = 4

═══════════════════════════════════════════════════════════

Step 1:
  pA = pA.next = 2
  pB = pB.next = 5
  
  pA == pB? 2 == 5? NO

═══════════════════════════════════════════════════════════

Step 2:
  pA = pA.next = 3
  pB = pB.next = 6
  
  pA == pB? 3 == 6? NO

═══════════════════════════════════════════════════════════

Step 3:
  pA = pA.next = 6
  pB = pB.next = 7
  
  pA == pB? 6 == 7? NO

═══════════════════════════════════════════════════════════

Step 4:
  pA = pA.next = 7
  pB = pB.next = null → SWITCH to headA = 1
  
  pA == pB? 7 == 1? NO

═══════════════════════════════════════════════════════════

Step 5:
  pA = pA.next = null → SWITCH to headB = 4
  pB = pB.next = 2
  
  pA == pB? 4 == 2? NO

═══════════════════════════════════════════════════════════

Step 6:
  pA = pA.next = 5
  pB = pB.next = 3
  
  pA == pB? 5 == 3? NO

═══════════════════════════════════════════════════════════

Step 7:
  pA = pA.next = 6
  pB = pB.next = 6
  
  pA == pB? 6 == 6? YES! ✓

═══════════════════════════════════════════════════════════

Return node 6 (the intersection) ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    // Handle edge cases
    if (headA == null || headB == null) return null;
    
    // Start both pointers at their respective heads
    ListNode pA = headA;
    ListNode pB = headB;
    
    // Walk until they meet (or both become null)
    while (pA != pB) {
        // When pA reaches end of A, switch to head of B
        // Otherwise, move to next node
        pA = (pA == null) ? headB : pA.next;
        
        // When pB reaches end of B, switch to head of A
        // Otherwise, move to next node
        pB = (pB == null) ? headA : pB.next;
    }
    
    // pA == pB: either the intersection node, or both are null
    return pA;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Comparing values instead of references | Two nodes can have same value but be different | Use `pA == pB` (reference equality) |
| Switching before reaching null | Switches too early, wrong distance | Switch when `pA == null`, not `pA.next == null` |
| Infinite loop if no intersection | Both keep switching forever | The math guarantees they meet at null if no intersection |
| Not handling null inputs | NPE on empty lists | Check `headA == null || headB == null` first |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| No intersection | Both reach null at same time, return null |
| Same list (headA == headB) | Return headA immediately |
| One list is empty | Return null |
| Intersection at head | Works correctly |

---

## Why Not Just Compare Lengths?

```java
// Alternative approach: Calculate lengths, align starts, then walk together
// This works but requires TWO passes through the lists

// The path-swapping approach is more elegant:
// - Single conceptual pass
// - No need to calculate lengths
// - Same O(m+n) time complexity
```

---

## Mind-Map Anchor

```
INTERSECTION OF TWO LISTS
           │
           ▼
┌─────────────────────────────────┐
│ Two Pointers + Path Swapping    │
│                                 │
│ pA: A → B                       │
│ pB: B → A                       │
│                                 │
│ Both travel same total distance │
│ Meet at intersection (or null)  │
└─────────────────────────────────┘
```

**Memory phrase:** "Walk both paths, switch at end, meet at intersection"

---

# PATTERN 9: Merge Two Sorted Lists (LeetCode 21)

## Pattern Recognition Signal

**When you see:** "merge two sorted lists", "combine two sorted sequences"

**Instant thought:** "Dummy head + two pointers! Compare heads, attach smaller, advance that pointer. Attach remainder at end."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  list1: 1 → 3 → 5
        list2: 2 → 4 → 6
        
Output: 1 → 2 → 3 → 4 → 5 → 6

Merge two SORTED lists into one SORTED list.
```

### The Card Deck Analogy

```
Imagine two sorted piles of cards face-up:

  Pile 1: [1] [3] [5]  (top to bottom)
  Pile 2: [2] [4] [6]

To merge into one sorted pile:
1. Compare the TOP cards of both piles
2. Take the SMALLER one, put it in the result pile
3. Repeat until one pile is empty
4. Put the remaining pile on top of result

Result: [1] [2] [3] [4] [5] [6]
```

### Why Dummy Head?

```
Without dummy, we'd need special logic for the first node:
  - Which list's head becomes the result's head?
  - Extra if-else before the main loop

With dummy:
  - dummy.next will be the actual head
  - We just attach nodes to tail, no special cases
  - Return dummy.next at the end
```

### The Two Pointers + Tail

```
We need THREE pointers:
  - list1: current position in first list
  - list2: current position in second list
  - tail: where to attach the next node in result

  dummy → [attached nodes...] → tail
                                  ↓
                              (next node goes here)
```

### The Algorithm in Plain English

```
1. Create dummy node, set tail = dummy
2. While both lists have nodes:
   a. Compare list1.val and list2.val
   b. Attach the smaller one to tail.next
   c. Advance that list's pointer
   d. Advance tail
3. Attach whichever list still has nodes (the remainder)
4. Return dummy.next
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `list1: 1 → 3 → 5`, `list2: 2 → 4 → 6`

```
═══════════════════════════════════════════════════════════

Initial:
  dummy → null
  tail = dummy
  list1 = 1
  list2 = 2

═══════════════════════════════════════════════════════════

Step 1: Compare 1 vs 2

  1 < 2, so attach list1's node (1)
  
  tail.next = list1 (node 1)
  list1 = list1.next = 3
  tail = tail.next = 1
  
  Result: dummy → 1
                  ↑
                 tail
  
  list1 = 3, list2 = 2

═══════════════════════════════════════════════════════════

Step 2: Compare 3 vs 2

  3 > 2, so attach list2's node (2)
  
  tail.next = list2 (node 2)
  list2 = list2.next = 4
  tail = tail.next = 2
  
  Result: dummy → 1 → 2
                      ↑
                     tail
  
  list1 = 3, list2 = 4

═══════════════════════════════════════════════════════════

Step 3: Compare 3 vs 4

  3 < 4, so attach list1's node (3)
  
  tail.next = list1 (node 3)
  list1 = list1.next = 5
  tail = tail.next = 3
  
  Result: dummy → 1 → 2 → 3
                          ↑
                         tail
  
  list1 = 5, list2 = 4

═══════════════════════════════════════════════════════════

Step 4: Compare 5 vs 4

  5 > 4, so attach list2's node (4)
  
  tail.next = list2 (node 4)
  list2 = list2.next = 6
  tail = tail.next = 4
  
  Result: dummy → 1 → 2 → 3 → 4
                              ↑
                             tail
  
  list1 = 5, list2 = 6

═══════════════════════════════════════════════════════════

Step 5: Compare 5 vs 6

  5 < 6, so attach list1's node (5)
  
  tail.next = list1 (node 5)
  list1 = list1.next = null
  tail = tail.next = 5
  
  Result: dummy → 1 → 2 → 3 → 4 → 5
                                  ↑
                                 tail
  
  list1 = null, list2 = 6

═══════════════════════════════════════════════════════════

Step 6: list1 is null, exit loop

  Attach remainder: tail.next = list2 (node 6)
  
  Result: dummy → 1 → 2 → 3 → 4 → 5 → 6

═══════════════════════════════════════════════════════════

Return dummy.next = node 1

Output: 1 → 2 → 3 → 4 → 5 → 6 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    // Dummy node simplifies edge cases (no special handling for first node)
    ListNode dummy = new ListNode(0);
    
    // Tail points to where we'll attach the next node
    ListNode tail = dummy;
    
    // Compare and attach while both lists have nodes
    while (list1 != null && list2 != null) {
        if (list1.val <= list2.val) {
            // list1's node is smaller (or equal), attach it
            tail.next = list1;
            list1 = list1.next;  // Advance list1
        } else {
            // list2's node is smaller, attach it
            tail.next = list2;
            list2 = list2.next;  // Advance list2
        }
        tail = tail.next;  // Move tail forward
    }
    
    // Attach the remaining nodes (one list might still have nodes)
    // This is O(1) — we're just linking, not copying!
    tail.next = (list1 != null) ? list1 : list2;
    
    // Return the actual head (skip dummy)
    return dummy.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting to advance tail | All nodes point to same place | `tail = tail.next` after each attach |
| Creating new nodes | Wastes memory, not needed | Just redirect pointers |
| Complex remainder handling | Unnecessary complexity | `tail.next = (list1 != null) ? list1 : list2` |
| Not using dummy | Special case for first node | Always use dummy for merge |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| One list empty | Attach the other list entirely |
| Both lists empty | Return null (dummy.next = null) |
| Lists of different lengths | Remainder attached at end |
| Duplicate values | `<=` ensures stable merge |

---

## Time & Space Complexity

```
Time:  O(n + m) where n, m are list lengths
       - We visit each node exactly once

Space: O(1)
       - Only using a few pointers
       - NOT creating new nodes, just redirecting
```

---

## Mind-Map Anchor

```
MERGE TWO SORTED LISTS
          │
          ▼
┌─────────────────────────────────┐
│ Dummy + Tail + Two Pointers     │
│                                 │
│ While both have nodes:          │
│   Compare heads                 │
│   Attach smaller to tail        │
│   Advance that pointer          │
│   Advance tail                  │
│                                 │
│ Attach remainder                │
└─────────────────────────────────┘
```

**Memory phrase:** "Compare heads, attach smaller, advance, attach remainder"

---

# PATTERN 10: Merge K Sorted Lists (LeetCode 23)

## Pattern Recognition Signal

**When you see:** "merge k sorted lists", "combine multiple sorted sequences"

**Instant thought:** "Min-Heap of heads (O(n log k)) OR Divide & Conquer (merge pairs recursively)!"

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  [1→4→5, 1→3→4, 2→6]
Output: 1→1→2→3→4→4→5→6

Merge k sorted lists into one sorted list.
```

### Why Not Just Compare All K Heads?

```
Naive approach: Each step, compare all k heads, pick smallest.

Time: O(n × k) where n = total nodes
      - For each of n nodes, we compare k heads

This is SLOW for large k!

Better approaches:
1. Min-Heap: O(n log k) — heap operations are O(log k)
2. Divide & Conquer: O(n log k) — merge pairs, halving k each round
```

### Approach 1: Min-Heap

```
The Idea:
- Keep a min-heap of size k (one head from each list)
- Always extract the smallest head
- Add its next node to the heap
- Repeat until heap is empty

Why O(n log k)?
- Each of n nodes is added/removed from heap once
- Heap operations are O(log k)
- Total: O(n log k)
```

### Approach 2: Divide & Conquer

```
The Idea:
- Merge lists in pairs: [0,1], [2,3], [4,5], ...
- Now we have k/2 lists
- Repeat until one list remains

Why O(n log k)?
- log k rounds of merging
- Each round processes all n nodes
- Total: O(n log k)

This is like merge sort, but on lists instead of elements!
```

### The Algorithm in Plain English

**Min-Heap Approach:**
```
1. Add all non-null heads to a min-heap
2. Create dummy node, tail = dummy
3. While heap is not empty:
   a. Extract smallest node from heap
   b. Attach it to tail
   c. If that node has a next, add next to heap
4. Return dummy.next
```

**Divide & Conquer Approach:**
```
1. If 0 lists, return null
2. If 1 list, return that list
3. Split lists into left half and right half
4. Recursively merge left half → one list
5. Recursively merge right half → one list
6. Merge the two resulting lists (Pattern 9!)
```

---

## Visual Dry Run (Step-by-Step) — Min-Heap

**Input:** `[1→4→5, 1→3→4, 2→6]`

```
═══════════════════════════════════════════════════════════

Initial:
  Add all heads to min-heap:
  Heap: [1, 1, 2] (min-heap, smallest at top)
  
  dummy → null
  tail = dummy

═══════════════════════════════════════════════════════════

Step 1: Extract min (1 from list 0)

  Heap before: [1, 1, 2]
  Extract: 1 (from list 0: 1→4→5)
  
  Attach to tail: dummy → 1
  tail = 1
  
  Add 1.next (4) to heap
  Heap after: [1, 2, 4]

═══════════════════════════════════════════════════════════

Step 2: Extract min (1 from list 1)

  Heap before: [1, 2, 4]
  Extract: 1 (from list 1: 1→3→4)
  
  Attach: dummy → 1 → 1
  tail = 1 (second one)
  
  Add 1.next (3) to heap
  Heap after: [2, 3, 4]

═══════════════════════════════════════════════════════════

Step 3: Extract min (2)

  Heap before: [2, 3, 4]
  Extract: 2 (from list 2: 2→6)
  
  Attach: dummy → 1 → 1 → 2
  tail = 2
  
  Add 2.next (6) to heap
  Heap after: [3, 4, 6]

═══════════════════════════════════════════════════════════

Step 4: Extract min (3)

  Heap before: [3, 4, 6]
  Extract: 3
  
  Attach: dummy → 1 → 1 → 2 → 3
  
  Add 3.next (4) to heap
  Heap after: [4, 4, 6]

═══════════════════════════════════════════════════════════

Step 5: Extract min (4 from list 0)

  Heap before: [4, 4, 6]
  Extract: 4 (from list 0)
  
  Attach: dummy → 1 → 1 → 2 → 3 → 4
  
  Add 4.next (5) to heap
  Heap after: [4, 5, 6]

═══════════════════════════════════════════════════════════

Step 6: Extract min (4 from list 1)

  Heap before: [4, 5, 6]
  Extract: 4 (from list 1)
  
  Attach: dummy → 1 → 1 → 2 → 3 → 4 → 4
  
  4.next = null, don't add to heap
  Heap after: [5, 6]

═══════════════════════════════════════════════════════════

Step 7: Extract min (5)

  Extract: 5
  Attach: dummy → 1 → 1 → 2 → 3 → 4 → 4 → 5
  
  5.next = null
  Heap after: [6]

═══════════════════════════════════════════════════════════

Step 8: Extract min (6)

  Extract: 6
  Attach: dummy → 1 → 1 → 2 → 3 → 4 → 4 → 5 → 6
  
  6.next = null
  Heap after: [] (empty)

═══════════════════════════════════════════════════════════

Heap empty, return dummy.next

Output: 1 → 1 → 2 → 3 → 4 → 4 → 5 → 6 ✓
```

---

## The Code — Approach 1: Min-Heap (With Line-by-Line Explanation)

```java
ListNode mergeKLists(ListNode[] lists) {
    // Min-heap ordered by node value
    // Comparator: (a, b) -> a.val - b.val means smaller values have higher priority
    PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
    
    // Add all non-null heads to the heap
    for (ListNode head : lists) {
        if (head != null) {
            heap.offer(head);
        }
    }
    
    // Dummy node for result list
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    
    // Process until heap is empty
    while (!heap.isEmpty()) {
        // Extract the smallest node
        ListNode smallest = heap.poll();
        
        // Attach it to the result
        tail.next = smallest;
        tail = tail.next;
        
        // If this node has a next, add it to the heap
        if (smallest.next != null) {
            heap.offer(smallest.next);
        }
    }
    
    return dummy.next;
}
```

---

## The Code — Approach 2: Divide & Conquer (With Line-by-Line Explanation)

```java
ListNode mergeKLists(ListNode[] lists) {
    // Base case: no lists
    if (lists == null || lists.length == 0) return null;
    
    // Recursively merge the range [0, length-1]
    return mergeRange(lists, 0, lists.length - 1);
}

ListNode mergeRange(ListNode[] lists, int left, int right) {
    // Base case: single list
    if (left == right) return lists[left];
    
    // Divide: find middle
    int mid = left + (right - left) / 2;
    
    // Conquer: recursively merge left and right halves
    ListNode l1 = mergeRange(lists, left, mid);
    ListNode l2 = mergeRange(lists, mid + 1, right);
    
    // Combine: merge the two resulting lists (Pattern 9!)
    return mergeTwoLists(l1, l2);
}

// Pattern 9's merge function
ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            tail.next = l1;
            l1 = l1.next;
        } else {
            tail.next = l2;
            l2 = l2.next;
        }
        tail = tail.next;
    }
    
    tail.next = (l1 != null) ? l1 : l2;
    return dummy.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Adding null to heap | NPE when comparing | Check `if (head != null)` before adding |
| Wrong comparator | Max-heap instead of min-heap | Use `(a, b) -> a.val - b.val` for min-heap |
| Forgetting to add next node | Loses rest of that list | `if (smallest.next != null) heap.offer(...)` |
| Off-by-one in divide & conquer | Wrong split | `mid = left + (right - left) / 2` |

---

## Complexity Comparison

| Approach | Time | Space |
|----------|------|-------|
| Min-Heap | O(n log k) | O(k) for heap |
| Divide & Conquer | O(n log k) | O(log k) for recursion stack |
| Naive (compare all k) | O(n × k) | O(1) |

---

## Mind-Map Anchor

```
MERGE K SORTED LISTS
          │
          ▼
┌─────────────────────────────────┐
│ Two Approaches:                 │
│                                 │
│ 1. MIN-HEAP                     │
│    - Heap of k heads            │
│    - Extract min, add its next  │
│    - O(n log k)                 │
│                                 │
│ 2. DIVIDE & CONQUER             │
│    - Merge pairs recursively    │
│    - Reuse Pattern 9            │
│    - O(n log k)                 │
└─────────────────────────────────┘
```

**Memory phrase:** "Heap of heads, extract min, add next" OR "Merge pairs, halve k, repeat"

---

# PATTERN 11: Sort List (LeetCode 148)

## Pattern Recognition Signal

**When you see:** "sort a linked list", "O(n log n) time", "O(1) space sorting"

**Instant thought:** "Merge Sort! Find middle (Pattern 6), split, recursively sort both halves, merge (Pattern 9)."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  4 → 2 → 1 → 3
Output: 1 → 2 → 3 → 4

Sort a linked list in O(n log n) time.
Bonus: O(1) space (excluding recursion stack).
```

### Why Merge Sort for Linked Lists?

```
Quick Sort: Bad for linked lists
  - Needs random access for pivot selection
  - Partitioning is awkward without indices

Merge Sort: Perfect for linked lists!
  - Finding middle: O(n) with fast-slow pointers
  - Splitting: O(1) — just cut the link!
  - Merging: O(n) — Pattern 9
  - No random access needed
```

### The Three-Pattern Combo

```
Sort List = Pattern 6 + Recursion + Pattern 9

1. FIND MIDDLE (Pattern 6)
   Use fast-slow to find the middle node

2. SPLIT
   Cut the list at the middle

3. RECURSE
   Sort left half, sort right half

4. MERGE (Pattern 9)
   Merge the two sorted halves
```

### Why Use Left-Middle?

```
For even-length lists, we need the LEFT middle to avoid infinite recursion.

Example: 1 → 2 (two nodes)

If we get RIGHT middle (node 2):
  Left half: 1 → 2 (same as original!)
  Right half: null
  INFINITE RECURSION!

If we get LEFT middle (node 1):
  Left half: 1
  Right half: 2
  Both are base cases ✓

To get left-middle: start fast at head.next, not head
```

### The Algorithm in Plain English

```
1. Base case: if head is null or single node, return head
2. Find the middle node (left-middle for even lists)
3. Split: save mid.next as right half, set mid.next = null
4. Recursively sort left half
5. Recursively sort right half
6. Merge the two sorted halves
7. Return merged result
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `4 → 2 → 1 → 3`

```
═══════════════════════════════════════════════════════════

sortList(4 → 2 → 1 → 3)

Step 1: Find middle
  slow = 4, fast = 2 (fast starts at head.next)
  
  Iteration 1:
    fast(2) != null ✓, fast.next(1) != null ✓
    slow = 2, fast = 3
  
  Iteration 2:
    fast(3) != null ✓, fast.next(null) != null ✗
    Loop exits
  
  Middle = 2

Step 2: Split
  left = 4 → 2 → null (cut at middle)
  right = 1 → 3 → null

═══════════════════════════════════════════════════════════

sortList(4 → 2)  [LEFT HALF]

Step 1: Find middle
  slow = 4, fast = 2
  
  Iteration 1:
    fast(2) != null ✓, fast.next(null) != null ✗
    Loop exits
  
  Middle = 4

Step 2: Split
  left = 4 → null
  right = 2 → null

Step 3: Recurse
  sortList(4) → returns 4 (base case: single node)
  sortList(2) → returns 2 (base case: single node)

Step 4: Merge(4, 2)
  Compare 4 vs 2: 2 is smaller
  Compare 4 vs null: attach 4
  Result: 2 → 4

Return: 2 → 4

═══════════════════════════════════════════════════════════

sortList(1 → 3)  [RIGHT HALF]

Step 1: Find middle
  slow = 1, fast = 3
  
  Iteration 1:
    fast(3) != null ✓, fast.next(null) != null ✗
    Loop exits
  
  Middle = 1

Step 2: Split
  left = 1 → null
  right = 3 → null

Step 3: Recurse
  sortList(1) → returns 1 (base case)
  sortList(3) → returns 3 (base case)

Step 4: Merge(1, 3)
  Compare 1 vs 3: 1 is smaller
  Compare null vs 3: attach 3
  Result: 1 → 3

Return: 1 → 3

═══════════════════════════════════════════════════════════

Back to sortList(4 → 2 → 1 → 3)

Step 4: Merge(2 → 4, 1 → 3)

  Compare 2 vs 1: 1 is smaller → attach 1
  Compare 2 vs 3: 2 is smaller → attach 2
  Compare 4 vs 3: 3 is smaller → attach 3
  Compare 4 vs null: attach 4
  
  Result: 1 → 2 → 3 → 4

═══════════════════════════════════════════════════════════

Return: 1 → 2 → 3 → 4 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode sortList(ListNode head) {
    // Base case: empty or single node is already sorted
    if (head == null || head.next == null) {
        return head;
    }
    
    // Step 1: Find middle (left-middle for even-length lists)
    ListNode mid = getMid(head);
    
    // Step 2: Split the list
    ListNode left = head;
    ListNode right = mid.next;
    mid.next = null;  // CUT! This separates the two halves
    
    // Step 3: Recursively sort both halves
    left = sortList(left);
    right = sortList(right);
    
    // Step 4: Merge the sorted halves
    return merge(left, right);
}

// Find middle node (left-middle for even-length lists)
ListNode getMid(ListNode head) {
    ListNode slow = head;
    ListNode fast = head.next;  // Start fast one ahead for LEFT-middle!
    
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    
    return slow;  // slow is at the left-middle
}

// Merge two sorted lists (Pattern 9)
ListNode merge(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;
    
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) {
            tail.next = l1;
            l1 = l1.next;
        } else {
            tail.next = l2;
            l2 = l2.next;
        }
        tail = tail.next;
    }
    
    // Attach remainder
    tail.next = (l1 != null) ? l1 : l2;
    
    return dummy.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Using `fast = head` for getMid | Gets right-middle, causes infinite recursion for 2-node lists | Use `fast = head.next` for left-middle |
| Forgetting to cut the list | Both halves point to same nodes | `mid.next = null` after finding middle |
| Not handling base case | Infinite recursion | Check `head == null || head.next == null` |
| Wrong merge implementation | Unsorted result | Use Pattern 9 correctly |

---

## Time & Space Complexity

```
Time:  O(n log n)
       - log n levels of recursion
       - Each level processes all n nodes (finding middle + merging)

Space: O(log n) for recursion stack
       - Not truly O(1), but much better than O(n)
       - Can be made O(1) with bottom-up iterative merge sort
```

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list | Returns null (base case) |
| Single node | Returns that node (base case) |
| Two nodes | Split into two single nodes, merge |
| Already sorted | Still O(n log n), no optimization |

---

## Mind-Map Anchor

```
SORT LIST (Merge Sort)
          │
          ▼
┌─────────────────────────────────┐
│ 1. Base case: null or 1 node   │
│ 2. Find middle (left-middle!)  │
│ 3. Cut: mid.next = null        │
│ 4. Recurse on both halves      │
│ 5. Merge sorted halves         │
│                                 │
│ KEY: fast = head.next          │
│      (for left-middle)         │
└─────────────────────────────────┘
```

**Memory phrase:** "Find middle, cut, sort both, merge — use left-middle!"

---

# PATTERN 12: Reorder List (LeetCode 143)

## Pattern Recognition Signal

**When you see:** "reorder list", "L0→Ln→L1→Ln-1→...", "interleave first and last"

**Instant thought:** "Three patterns combined! Find middle (Pattern 6), reverse second half (Pattern 0), interleave the two halves."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4 → 5
Output: 1 → 5 → 2 → 4 → 3

Reorder: L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → ...

Interleave the first half with the REVERSED second half!
```

### The Zipper Analogy

```
Imagine a zipper with two sides:

  First half:  1 → 2 → 3
  Second half: 5 → 4     (reversed!)

Now zip them together:
  1 → 5 → 2 → 4 → 3

Each tooth from the first half alternates with a tooth from the second half.
```

### The Three-Pattern Combo

```
Reorder List = Pattern 6 + Pattern 0 + Interleave

1. FIND MIDDLE (Pattern 6)
   Split the list into two halves

2. REVERSE SECOND HALF (Pattern 0)
   1 → 2 → 3 and 4 → 5 becomes
   1 → 2 → 3 and 5 → 4

3. INTERLEAVE
   Merge alternating: first, second, first, second...
```

### The Algorithm in Plain English

```
1. Find the middle of the list
2. Split: first half ends at middle, second half starts after middle
3. Reverse the second half
4. Interleave: take one from first, one from second, repeat
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 4 → 5`

```
═══════════════════════════════════════════════════════════

Step 1: Find Middle

  slow = 1, fast = 1
  
  Iteration 1: slow = 2, fast = 3
  Iteration 2: slow = 3, fast = 5
  
  fast.next = null, loop exits
  Middle = 3

═══════════════════════════════════════════════════════════

Step 2: Split and Reverse Second Half

  First half:  1 → 2 → 3 → null (cut at middle)
  Second half: 4 → 5 → null
  
  Reverse second half:
    prev = null, curr = 4
    
    Iteration 1: 4.next = null, prev = 4, curr = 5
    Iteration 2: 5.next = 4, prev = 5, curr = null
    
  Reversed second half: 5 → 4 → null

  State:
    first:  1 → 2 → 3 → null
    second: 5 → 4 → null

═══════════════════════════════════════════════════════════

Step 3: Interleave

  first = 1, second = 5

  ─────────────────────────────────────────────────────────
  
  Iteration 1:
    Save: tmp1 = first.next = 2
          tmp2 = second.next = 4
    
    Redirect:
      first.next = second → 1.next = 5
      second.next = tmp1 → 5.next = 2
    
    Advance:
      first = tmp1 = 2
      second = tmp2 = 4
    
    State: 1 → 5 → 2 → 3 → null
                   ↑
                 first
           4 → null
           ↑
         second

  ─────────────────────────────────────────────────────────
  
  Iteration 2:
    Save: tmp1 = first.next = 3
          tmp2 = second.next = null
    
    Redirect:
      first.next = second → 2.next = 4
      second.next = tmp1 → 4.next = 3
    
    Advance:
      first = tmp1 = 3
      second = tmp2 = null
    
    State: 1 → 5 → 2 → 4 → 3 → null

  ─────────────────────────────────────────────────────────
  
  second = null, loop exits

═══════════════════════════════════════════════════════════

Output: 1 → 5 → 2 → 4 → 3 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
void reorderList(ListNode head) {
    // Edge cases
    if (head == null || head.next == null) return;
    
    // Step 1: Find middle (use left-middle condition)
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    // slow is now at the middle (or left-middle for even length)
    
    // Step 2: Reverse second half
    ListNode second = reverse(slow.next);
    slow.next = null;  // Cut the first half
    ListNode first = head;
    
    // Step 3: Interleave the two halves
    while (second != null) {
        // Save next pointers (before we lose them!)
        ListNode tmp1 = first.next;
        ListNode tmp2 = second.next;
        
        // Interleave: first → second → first's old next
        first.next = second;
        second.next = tmp1;
        
        // Move to next pair
        first = tmp1;
        second = tmp2;
    }
}

// Standard reverse (Pattern 0)
ListNode reverse(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Wrong middle for even lists | Interleaving breaks | Use `fast.next != null && fast.next.next != null` |
| Forgetting to cut first half | Creates cycle | `slow.next = null` after finding middle |
| Not saving tmp pointers | Lose access to rest of lists | Save `tmp1` and `tmp2` before redirecting |
| Loop condition `first != null` | First half might be longer | Use `while (second != null)` |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list | Return immediately |
| Single node | Return immediately |
| Two nodes | Swap them |
| Odd length | Middle node stays in place |

---

## Mind-Map Anchor

```
REORDER LIST
      │
      ▼
┌─────────────────────────────────┐
│ THREE PATTERNS COMBINED:        │
│                                 │
│ 1. Find middle (Pattern 6)     │
│ 2. Reverse second half (P0)    │
│ 3. Interleave:                 │
│    first→second→first.next...  │
│                                 │
│ KEY: Save tmp1, tmp2 before    │
│      redirecting!              │
└─────────────────────────────────┘
```

**Memory phrase:** "Middle, reverse second, interleave — save before redirect!"

---

# PATTERN 13: Palindrome Linked List (LeetCode 234)

## Pattern Recognition Signal

**When you see:** "is the list a palindrome?", "reads same forward and backward", "O(1) space palindrome check"

**Instant thought:** "Same as Pattern 12! Find middle, reverse second half, compare both halves."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 2 → 1
Output: true (it's a palindrome!)

Input:  1 → 2 → 3
Output: false (not a palindrome)

Check if the list reads the same forward and backward.
```

### The Mirror Analogy

```
A palindrome is like looking in a mirror:

  First half:  1 → 2
  Second half: 2 → 1 (reversed = 1 → 2)

If first half equals reversed second half → palindrome!

  1 → 2  vs  1 → 2  → MATCH! ✓
```

### The Three-Pattern Combo (Same as Pattern 12!)

```
Palindrome = Pattern 6 + Pattern 0 + Compare

1. FIND MIDDLE (Pattern 6)
   Split the list into two halves

2. REVERSE SECOND HALF (Pattern 0)
   Now both halves go in the same direction

3. COMPARE (instead of interleave)
   Walk both halves, compare values
```

### The Algorithm in Plain English

```
1. Find the middle of the list
2. Reverse the second half
3. Compare first half with reversed second half
4. (Optional) Restore the list by reversing second half again
5. Return true if all values matched, false otherwise
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 2 → 1`

```
═══════════════════════════════════════════════════════════

Step 1: Find Middle

  slow = 1, fast = 1
  
  Iteration 1: slow = 2, fast = 2 (second one)
  Iteration 2: fast.next = 1, fast.next.next = null, loop exits
  
  Middle = 2 (first one)

═══════════════════════════════════════════════════════════

Step 2: Reverse Second Half

  Second half before: 2 → 1 → null
  
  Reverse:
    prev = null, curr = 2
    Iteration 1: 2.next = null, prev = 2, curr = 1
    Iteration 2: 1.next = 2, prev = 1, curr = null
  
  Second half after: 1 → 2 → null

  State:
    First half:  1 → 2 → (still points to old 2, but we'll use p1)
    Second half: 1 → 2 → null

═══════════════════════════════════════════════════════════

Step 3: Compare

  p1 = head = 1
  p2 = reversed second half = 1

  ─────────────────────────────────────────────────────────
  
  Iteration 1:
    p1.val = 1, p2.val = 1
    1 == 1? YES ✓
    p1 = 2, p2 = 2

  ─────────────────────────────────────────────────────────
  
  Iteration 2:
    p1.val = 2, p2.val = 2
    2 == 2? YES ✓
    p1 = ?, p2 = null

  ─────────────────────────────────────────────────────────
  
  p2 = null, loop exits
  All values matched!

═══════════════════════════════════════════════════════════

Return: true ✓
```

**Input:** `1 → 2 → 3`

```
═══════════════════════════════════════════════════════════

Step 1: Find Middle = 2

Step 2: Reverse Second Half
  Second half: 3 → null
  Reversed: 3 → null (single node, unchanged)

Step 3: Compare
  p1 = 1, p2 = 3
  
  Iteration 1:
    p1.val = 1, p2.val = 3
    1 == 3? NO ✗

═══════════════════════════════════════════════════════════

Return: false ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
boolean isPalindrome(ListNode head) {
    // Edge cases
    if (head == null || head.next == null) return true;
    
    // Step 1: Find middle
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    // slow is now at the middle (or right-middle for even length)
    
    // Step 2: Reverse second half
    ListNode secondHalf = reverse(slow);
    
    // Step 3: Compare first half with reversed second half
    ListNode p1 = head;
    ListNode p2 = secondHalf;
    boolean result = true;
    
    while (p2 != null) {  // p2 is shorter or equal length
        if (p1.val != p2.val) {
            result = false;
            break;
        }
        p1 = p1.next;
        p2 = p2.next;
    }
    
    // Step 4 (Optional): Restore the list
    // reverse(secondHalf);
    
    return result;
}

// Standard reverse (Pattern 0)
ListNode reverse(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Comparing wrong halves | First half might be longer | Use `while (p2 != null)` — p2 is shorter or equal |
| Returning immediately on mismatch | Might want to restore list first | Save result, restore, then return |
| Wrong middle for odd lists | Middle node compared with itself | It's fine — middle node is in second half |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list | Return true |
| Single node | Return true |
| Two same nodes | Return true |
| Two different nodes | Return false |
| Odd length palindrome | Middle node is in second half, works correctly |

---

## Mind-Map Anchor

```
PALINDROME LINKED LIST
          │
          ▼
┌─────────────────────────────────┐
│ THREE PATTERNS COMBINED:        │
│                                 │
│ 1. Find middle (Pattern 6)     │
│ 2. Reverse second half (P0)    │
│ 3. Compare values              │
│                                 │
│ Same as Pattern 12, but        │
│ COMPARE instead of interleave! │
└─────────────────────────────────┘
```

**Memory phrase:** "Middle, reverse second, compare — same as reorder but compare!"

---

# PATTERN 14: Partition List (LeetCode 86)

## Pattern Recognition Signal

**When you see:** "partition around a value", "all nodes less than x before nodes >= x", "preserve relative order"

**Instant thought:** "Two dummy heads! Build two separate chains (less and greater), then connect them."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 4 → 3 → 2 → 5 → 2, x=3
Output: 1 → 2 → 2 → 4 → 3 → 5

All nodes with value < 3 come before nodes with value >= 3.
Relative order within each group is preserved!
```

### The Two Buckets Analogy

```
Imagine sorting items into two buckets:

  LESS bucket (< x):    Items with value < 3
  GREATER bucket (>= x): Items with value >= 3

Walk through the list, put each item in the right bucket:
  1 → LESS
  4 → GREATER
  3 → GREATER
  2 → LESS
  5 → GREATER
  2 → LESS

LESS bucket:    1 → 2 → 2
GREATER bucket: 4 → 3 → 5

Connect them: 1 → 2 → 2 → 4 → 3 → 5
```

### Why Two Dummy Heads?

```
Without dummy heads:
  - Need special logic for first node of each chain
  - Lots of null checks

With dummy heads:
  - lessHead → (less chain)
  - greaterHead → (greater chain)
  - Just attach nodes, no special cases
  - Return lessHead.next
```

### The Algorithm in Plain English

```
1. Create two dummy heads: lessHead, greaterHead
2. Create two tail pointers: less, greater
3. Walk through the original list:
   - If node.val < x: attach to less chain
   - Else: attach to greater chain
4. IMPORTANT: Terminate greater chain (greater.next = null)
5. Connect: less.next = greaterHead.next
6. Return lessHead.next
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 4 → 3 → 2 → 5 → 2`, x=3

```
═══════════════════════════════════════════════════════════

Initial:
  lessHead → null
  greaterHead → null
  less = lessHead
  greater = greaterHead
  head = 1

═══════════════════════════════════════════════════════════

Process node 1: val=1 < 3

  Add to LESS chain:
    less.next = 1
    less = 1
  
  LESS:    lessHead → 1
  GREATER: greaterHead → null
  
  head = 4

═══════════════════════════════════════════════════════════

Process node 4: val=4 >= 3

  Add to GREATER chain:
    greater.next = 4
    greater = 4
  
  LESS:    lessHead → 1
  GREATER: greaterHead → 4
  
  head = 3

═══════════════════════════════════════════════════════════

Process node 3: val=3 >= 3

  Add to GREATER chain:
    greater.next = 3
    greater = 3
  
  LESS:    lessHead → 1
  GREATER: greaterHead → 4 → 3
  
  head = 2

═══════════════════════════════════════════════════════════

Process node 2: val=2 < 3

  Add to LESS chain:
    less.next = 2
    less = 2
  
  LESS:    lessHead → 1 → 2
  GREATER: greaterHead → 4 → 3
  
  head = 5

═══════════════════════════════════════════════════════════

Process node 5: val=5 >= 3

  Add to GREATER chain:
    greater.next = 5
    greater = 5
  
  LESS:    lessHead → 1 → 2
  GREATER: greaterHead → 4 → 3 → 5
  
  head = 2 (last node)

═══════════════════════════════════════════════════════════

Process node 2: val=2 < 3

  Add to LESS chain:
    less.next = 2
    less = 2
  
  LESS:    lessHead → 1 → 2 → 2
  GREATER: greaterHead → 4 → 3 → 5
  
  head = null, loop exits

═══════════════════════════════════════════════════════════

Connect the chains:

  Step 1: Terminate greater chain
    greater.next = null
    (CRITICAL! Otherwise 5 might still point to old next)
  
  Step 2: Connect less to greater
    less.next = greaterHead.next = 4
  
  Result: lessHead → 1 → 2 → 2 → 4 → 3 → 5 → null

═══════════════════════════════════════════════════════════

Return lessHead.next = 1

Output: 1 → 2 → 2 → 4 → 3 → 5 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode partition(ListNode head, int x) {
    // Two dummy heads for two chains
    ListNode lessHead = new ListNode(0);
    ListNode greaterHead = new ListNode(0);
    
    // Tail pointers for building the chains
    ListNode less = lessHead;
    ListNode greater = greaterHead;
    
    // Walk through the original list
    while (head != null) {
        if (head.val < x) {
            // Add to less chain
            less.next = head;
            less = less.next;
        } else {
            // Add to greater chain
            greater.next = head;
            greater = greater.next;
        }
        head = head.next;
    }
    
    // CRITICAL: Terminate greater chain!
    // Without this, greater's last node might still point to a node in less chain
    // This would create a CYCLE!
    greater.next = null;
    
    // Connect less chain to greater chain
    less.next = greaterHead.next;
    
    return lessHead.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting `greater.next = null` | Creates a cycle! Last greater node might point to a less node | Always terminate: `greater.next = null` |
| Using one dummy head | Can't build two separate chains | Use TWO dummy heads |
| Not preserving order | Problem requires relative order preserved | Just append to chains, don't sort |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| All nodes < x | Greater chain empty, return less chain |
| All nodes >= x | Less chain empty, return greater chain |
| Empty list | Return null |
| x larger than all values | All go to less chain |

---

## Mind-Map Anchor

```
PARTITION LIST
       │
       ▼
┌─────────────────────────────────┐
│ TWO DUMMY HEADS                 │
│                                 │
│ lessHead → (nodes < x)          │
│ greaterHead → (nodes >= x)      │
│                                 │
│ Walk list, distribute nodes     │
│                                 │
│ CRITICAL: greater.next = null   │
│ Connect: less → greater         │
└─────────────────────────────────┘
```

**Memory phrase:** "Two dummies, distribute by value, terminate greater, connect"

---

# PATTERN 15: Odd Even Linked List (LeetCode 328)

## Pattern Recognition Signal

**When you see:** "group odd and even indexed nodes", "odd positions first, then even"

**Instant thought:** "Two pointers alternating! Build odd chain and even chain, then connect odd→even."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4 → 5
        ↑   ↑   ↑   ↑   ↑
       odd even odd even odd
       (1)  (2) (3)  (4) (5)  ← positions

Output: 1 → 3 → 5 → 2 → 4
        ↑   ↑   ↑   ↑   ↑
       odd odd odd even even

Group by POSITION (index), not by VALUE!
```

### The Skip Rope Analogy

```
Imagine two people picking items from a line, alternating:

  Items: [1] [2] [3] [4] [5]
  
  Odd picker:  takes 1, skips 2, takes 3, skips 4, takes 5
  Even picker: skips 1, takes 2, skips 3, takes 4, skips 5

  Odd's pile:  1 → 3 → 5
  Even's pile: 2 → 4

  Final: Odd's pile → Even's pile
         1 → 3 → 5 → 2 → 4
```

### The Two Pointers Dance

```
odd and even pointers alternate through the list:

  1 → 2 → 3 → 4 → 5
  ↑   ↑
 odd even

After one step:
  odd.next = even.next (skip even, point to next odd)
  odd = odd.next
  
  1 → 3 → 4 → 5
      ↑   ↑
     odd even (even hasn't moved yet)
  
  even.next = odd.next (skip odd, point to next even)
  even = even.next
  
  1 → 3 → 5
      ↑   ↑
     odd even
  2 → 4
```

### Why Save evenHead?

```
After building the chains, we need to connect them:
  odd.next = evenHead

But even pointer has moved! We need the ORIGINAL head of even chain.
So we save it at the start: evenHead = head.next
```

### The Algorithm in Plain English

```
1. odd = head (first node)
2. even = head.next (second node)
3. evenHead = even (save for later connection)
4. While even and even.next exist:
   a. odd.next = even.next (odd skips to next odd)
   b. odd = odd.next
   c. even.next = odd.next (even skips to next even)
   d. even = even.next
5. Connect: odd.next = evenHead
6. Return head
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 4 → 5`

```
═══════════════════════════════════════════════════════════

Initial:
  odd = 1
  even = 2
  evenHead = 2 (saved!)
  
  1 → 2 → 3 → 4 → 5 → null
  ↑   ↑
 odd even

═══════════════════════════════════════════════════════════

Iteration 1:

  Check: even(2) != null ✓, even.next(3) != null ✓
  
  Step 1: odd.next = even.next
    1.next = 3
    
    1 → 3 → 4 → 5 → null
    ↑   
   odd
    2 → 3 (2 still points to 3)
        ↑
      even
  
  Step 2: odd = odd.next
    odd = 3
    
    1 → 3 → 4 → 5 → null
        ↑
       odd
  
  Step 3: even.next = odd.next
    2.next = 4
    
    1 → 3 → 4 → 5 → null
        ↑
       odd
    2 → 4 → 5 → null
        ↑
      even
  
  Step 4: even = even.next
    even = 4
    
    Odd chain:  1 → 3
    Even chain: 2 → 4

═══════════════════════════════════════════════════════════

Iteration 2:

  Check: even(4) != null ✓, even.next(5) != null ✓
  
  Step 1: odd.next = even.next
    3.next = 5
    
    Odd chain: 1 → 3 → 5
  
  Step 2: odd = odd.next
    odd = 5
  
  Step 3: even.next = odd.next
    4.next = null (5.next is null)
    
    Even chain: 2 → 4 → null
  
  Step 4: even = even.next
    even = null

═══════════════════════════════════════════════════════════

Check: even(null) != null ✗

Loop exits.

═══════════════════════════════════════════════════════════

Connect: odd.next = evenHead
  5.next = 2
  
  Result: 1 → 3 → 5 → 2 → 4 → null

═══════════════════════════════════════════════════════════

Return head = 1

Output: 1 → 3 → 5 → 2 → 4 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode oddEvenList(ListNode head) {
    // Edge case: empty or single node
    if (head == null) return null;
    
    // Initialize pointers
    ListNode odd = head;           // First node (position 1 = odd)
    ListNode even = head.next;     // Second node (position 2 = even)
    ListNode evenHead = even;      // Save even's head for later connection!
    
    // Build odd and even chains simultaneously
    while (even != null && even.next != null) {
        // Odd skips to next odd (which is even.next)
        odd.next = even.next;
        odd = odd.next;
        
        // Even skips to next even (which is now odd.next)
        even.next = odd.next;
        even = even.next;
    }
    
    // Connect odd chain to even chain
    odd.next = evenHead;
    
    return head;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Forgetting to save evenHead | Can't connect chains at the end | `evenHead = even` at the start |
| Wrong loop condition | NPE or wrong termination | `while (even != null && even.next != null)` |
| Confusing position vs value | Problem is about position, not value | Position 1,3,5 are odd; 2,4,6 are even |
| Not handling null head | NPE | Check `if (head == null)` first |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list | Return null |
| Single node | Return that node (no even nodes) |
| Two nodes | Swap them: 1→2 becomes 1→2 (no change needed) |
| Three nodes | 1→2→3 becomes 1→3→2 |

---

## Mind-Map Anchor

```
ODD EVEN LINKED LIST
          │
          ▼
┌─────────────────────────────────┐
│ Two pointers: odd, even         │
│ Save: evenHead = even           │
│                                 │
│ While even and even.next exist: │
│   odd.next = even.next (skip)   │
│   odd = odd.next                │
│   even.next = odd.next (skip)   │
│   even = even.next              │
│                                 │
│ Connect: odd.next = evenHead    │
└─────────────────────────────────┘
```

**Memory phrase:** "Odd skips even, even skips odd, connect at end"

---

# PATTERN 16: Remove Duplicates from Sorted List (LeetCode 83)

## Pattern Recognition Signal

**When you see:** "remove duplicates from sorted list", "keep one copy of each value"

**Instant thought:** "Sorted means duplicates are adjacent! Skip nodes with same value as current."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 1 → 2 → 3 → 3
Output: 1 → 2 → 3

Keep ONE copy of each value, remove the extras.
```

### The Sorted List Insight

```
Because the list is SORTED, duplicates are always ADJACENT!

  1 → 1 → 2 → 3 → 3
      ↑       ↑
  duplicates are next to each other

This makes the problem easy:
  - If curr.val == curr.next.val → skip curr.next
  - Otherwise → move to curr.next
```

### The Skip vs Move Decision

```
At each node, ask: "Is my next node a duplicate?"

  curr.val == curr.next.val?
    YES → Skip: curr.next = curr.next.next
          (Don't move curr! There might be more duplicates)
    NO  → Move: curr = curr.next
```

### Why Don't We Move After Skipping?

```
Example: 1 → 1 → 1 → 2

If we move after skipping:
  curr = 1, skip → 1 → 1 → 2
  curr = 1 (moved), skip → 1 → 2
  curr = 2 (moved), done
  Result: 1 → 2 ✓

But if we moved BEFORE checking again:
  curr = 1, skip → 1 → 1 → 2
  curr = 1 (moved to second 1!)
  We'd miss the third 1!

So: DON'T move after skipping. Check again.
```

### The Algorithm in Plain English

```
1. Start curr at head
2. While curr and curr.next exist:
   a. If curr.val == curr.next.val:
      - Skip: curr.next = curr.next.next
      - DON'T move curr (check for more duplicates)
   b. Else:
      - Move: curr = curr.next
3. Return head
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 1 → 2 → 3 → 3`

```
═══════════════════════════════════════════════════════════

Initial:
  curr = 1
  
  1 → 1 → 2 → 3 → 3 → null
  ↑
 curr

═══════════════════════════════════════════════════════════

Step 1: curr.val(1) == curr.next.val(1)?

  YES! Skip curr.next
  
  curr.next = curr.next.next
  1.next = 2
  
  1 → 2 → 3 → 3 → null
  ↑
 curr
  
  DON'T move curr (check for more duplicates)

═══════════════════════════════════════════════════════════

Step 2: curr.val(1) == curr.next.val(2)?

  NO! Move curr
  
  curr = curr.next = 2
  
  1 → 2 → 3 → 3 → null
      ↑
     curr

═══════════════════════════════════════════════════════════

Step 3: curr.val(2) == curr.next.val(3)?

  NO! Move curr
  
  curr = curr.next = 3
  
  1 → 2 → 3 → 3 → null
          ↑
         curr

═══════════════════════════════════════════════════════════

Step 4: curr.val(3) == curr.next.val(3)?

  YES! Skip curr.next
  
  curr.next = curr.next.next
  3.next = null
  
  1 → 2 → 3 → null
          ↑
         curr
  
  DON'T move curr

═══════════════════════════════════════════════════════════

Step 5: curr.next == null

  Loop exits (curr.next is null)

═══════════════════════════════════════════════════════════

Return head = 1

Output: 1 → 2 → 3 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode deleteDuplicates(ListNode head) {
    // Start at head
    ListNode curr = head;
    
    // Continue while curr and curr.next exist
    while (curr != null && curr.next != null) {
        if (curr.val == curr.next.val) {
            // Duplicate found! Skip the next node
            curr.next = curr.next.next;
            // DON'T move curr — there might be more duplicates!
        } else {
            // No duplicate, safe to move forward
            curr = curr.next;
        }
    }
    
    return head;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Moving curr after skipping | Might miss consecutive duplicates | Only move in the `else` branch |
| Using `while (curr.next != null)` only | NPE if head is null | Check `curr != null && curr.next != null` |
| Creating new nodes | Unnecessary, just redirect pointers | Use `curr.next = curr.next.next` |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list | Return null |
| Single node | Return that node |
| All duplicates | Return single node |
| No duplicates | Return original list |

---

## Mind-Map Anchor

```
REMOVE DUPLICATES (SORTED)
            │
            ▼
┌─────────────────────────────────┐
│ Sorted = duplicates adjacent!   │
│                                 │
│ If curr.val == curr.next.val:   │
│   SKIP: curr.next = curr.next.next│
│   DON'T move curr!              │
│ Else:                           │
│   MOVE: curr = curr.next        │
└─────────────────────────────────┘
```

**Memory phrase:** "Same? Skip and stay. Different? Move forward."

---

# PATTERN 17: Remove Duplicates from Sorted List II (LeetCode 82)

## Pattern Recognition Signal

**When you see:** "remove all duplicates", "delete all nodes that have duplicate numbers", "leave only distinct numbers"

**Instant thought:** "Unlike Pattern 16, remove ALL copies! Need dummy + prev pointer to skip entire duplicate sequences."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 3 → 4 → 4 → 5
Output: 1 → 2 → 5

Remove ALL nodes that have duplicates, not just the extras!

Pattern 16: 1 → 2 → 3 → 3 → 4 → 4 → 5 → becomes → 1 → 2 → 3 → 4 → 5 (keep one)
Pattern 17: 1 → 2 → 3 → 3 → 4 → 4 → 5 → becomes → 1 → 2 → 5 (remove all)
```

### The Bouncer Analogy

```
Imagine a bouncer at a club with a strict "no duplicates" policy:

  Line: [1] [2] [3] [3] [4] [4] [5]

Pattern 16 bouncer: "Only one of each allowed"
  Lets in: 1, 2, 3, 4, 5

Pattern 17 bouncer: "If you have a twin, NEITHER of you gets in!"
  Lets in: 1, 2, 5
  Rejects: 3, 3, 4, 4 (both copies!)
```

### Why Do We Need prev?

```
In Pattern 16, we skip ONE node at a time.
In Pattern 17, we skip ENTIRE sequences of duplicates.

To skip a sequence, we need the node BEFORE the sequence:
  prev.next = (first node after the duplicate sequence)

Example: 1 → 2 → 3 → 3 → 4
         ↑       ↑───↑
        prev   duplicates

After skipping: prev.next = 4
Result: 1 → 2 → 4
```

### Why Dummy Head?

```
What if the head itself is a duplicate?

  Input: 1 → 1 → 2
  
  There's no node before the first 1!
  
  Solution: Create dummy that points to head.
  
  dummy → 1 → 1 → 2
    ↑
   prev can start here
```

### The Algorithm in Plain English

```
1. Create dummy, dummy.next = head
2. prev = dummy (last confirmed unique node)
3. While head exists:
   a. If head.next exists AND head.val == head.next.val:
      - This is the START of a duplicate sequence
      - Skip ALL nodes with this value
      - prev.next = head.next (skip the entire sequence)
   b. Else:
      - head is unique, move prev forward
   c. Move head forward
4. Return dummy.next
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 3 → 4 → 4 → 5`

```
═══════════════════════════════════════════════════════════

Initial:
  dummy → 1 → 2 → 3 → 3 → 4 → 4 → 5 → null
    ↑     ↑
   prev  head

═══════════════════════════════════════════════════════════

Step 1: head=1, head.next=2

  1 == 2? NO (not a duplicate)
  
  Move prev forward:
    prev = prev.next = 1
  
  Move head forward:
    head = head.next = 2
  
  dummy → 1 → 2 → 3 → 3 → 4 → 4 → 5 → null
          ↑   ↑
         prev head

═══════════════════════════════════════════════════════════

Step 2: head=2, head.next=3

  2 == 3? NO (not a duplicate)
  
  Move prev forward:
    prev = prev.next = 2
  
  Move head forward:
    head = head.next = 3
  
  dummy → 1 → 2 → 3 → 3 → 4 → 4 → 5 → null
              ↑   ↑
             prev head

═══════════════════════════════════════════════════════════

Step 3: head=3, head.next=3

  3 == 3? YES! DUPLICATE SEQUENCE FOUND!
  
  Skip ALL 3s:
    Inner loop: while head.next != null && head.val == head.next.val
      head = 3, head.next = 3, 3 == 3? YES
      head = head.next = 3 (second one)
      
      head = 3, head.next = 4, 3 == 4? NO
      Inner loop exits
    
    head is now at the LAST duplicate (second 3)
  
  Skip the entire sequence:
    prev.next = head.next = 4
  
  Move head forward:
    head = head.next = 4
  
  dummy → 1 → 2 → 4 → 4 → 5 → null
              ↑   ↑
             prev head
  
  (Note: prev did NOT move — we're not sure if 4 is unique yet)

═══════════════════════════════════════════════════════════

Step 4: head=4, head.next=4

  4 == 4? YES! DUPLICATE SEQUENCE FOUND!
  
  Skip ALL 4s:
    Inner loop:
      head = 4, head.next = 4, 4 == 4? YES
      head = head.next = 4 (second one)
      
      head = 4, head.next = 5, 4 == 5? NO
      Inner loop exits
  
  Skip the entire sequence:
    prev.next = head.next = 5
  
  Move head forward:
    head = head.next = 5
  
  dummy → 1 → 2 → 5 → null
              ↑   ↑
             prev head

═══════════════════════════════════════════════════════════

Step 5: head=5, head.next=null

  head.next is null, so no duplicate check needed
  
  Move prev forward:
    prev = prev.next = 5
  
  Move head forward:
    head = head.next = null
  
  dummy → 1 → 2 → 5 → null
                  ↑   ↑
                 prev head(null)

═══════════════════════════════════════════════════════════

head = null, loop exits

Return dummy.next = 1

Output: 1 → 2 → 5 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode deleteDuplicates(ListNode head) {
    // Dummy node handles edge case where head is a duplicate
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    
    // prev points to the last confirmed unique node
    ListNode prev = dummy;
    
    while (head != null) {
        // Check if this is the start of a duplicate sequence
        if (head.next != null && head.val == head.next.val) {
            // Skip ALL nodes with this value
            while (head.next != null && head.val == head.next.val) {
                head = head.next;
            }
            // head is now at the LAST duplicate
            // Skip the entire sequence: prev.next jumps over all duplicates
            prev.next = head.next;
        } else {
            // No duplicate, this node is unique
            // Move prev forward (we've confirmed head is unique)
            prev = prev.next;
        }
        // Move head forward
        head = head.next;
    }
    
    return dummy.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Only skipping one duplicate (Pattern 16) | Need to skip ALL copies | Use inner while loop to skip entire sequence |
| Forgetting dummy head | Can't handle duplicate at head | Always use dummy |
| Moving prev when duplicates found | prev should stay at last unique | Only move prev in the `else` branch |
| Off-by-one in inner loop | Might skip too few or too many | Inner loop stops when `head.val != head.next.val` |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list | Return null |
| All duplicates | Return null (dummy.next = null) |
| No duplicates | Return original list |
| Duplicates at head | Dummy handles it |

---

## Mind-Map Anchor

```
REMOVE DUPLICATES II (ALL COPIES)
              │
              ▼
┌─────────────────────────────────┐
│ Dummy + prev pointer            │
│                                 │
│ If duplicate sequence found:    │
│   1. Skip ALL with same value   │
│   2. prev.next = head.next      │
│   3. DON'T move prev            │
│                                 │
│ If unique:                      │
│   Move prev forward             │
└─────────────────────────────────┘
```

**Memory phrase:** "Duplicate? Skip ALL, prev stays. Unique? Move prev."

---

# PATTERN 18: Add Two Numbers (LeetCode 2)

## Pattern Recognition Signal

**When you see:** "add two numbers represented as linked lists", "digits in reverse order", "return sum as linked list"

**Instant thought:** "Reverse order = easy! Add digit by digit with carry, like elementary school addition."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  l1: 2 → 4 → 3  (represents 342)
        l2: 5 → 6 → 4  (represents 465)
        
Output: 7 → 0 → 8  (represents 807)

342 + 465 = 807

Digits are stored in REVERSE order (least significant first).
This is actually EASIER than normal order!
```

### Why Reverse Order is Easy

```
Normal addition (elementary school):
    342
  + 465
  -----
    807

We add from RIGHT to LEFT (least significant first).

In reverse order linked lists:
  l1: 2 → 4 → 3  (2 is least significant)
  l2: 5 → 6 → 4  (5 is least significant)

We can add from LEFT to RIGHT (head to tail)!
This matches how we traverse linked lists naturally.
```

### The Elementary School Addition

```
Step 1: Add ones place
  2 + 5 = 7, carry = 0
  Result: 7

Step 2: Add tens place
  4 + 6 + 0 = 10, carry = 1
  Result: 7 → 0

Step 3: Add hundreds place
  3 + 4 + 1 = 8, carry = 0
  Result: 7 → 0 → 8

Done! (carry is 0)
```

### The Algorithm in Plain English

```
1. Create dummy node, curr = dummy
2. carry = 0
3. While l1 OR l2 OR carry is non-zero:
   a. sum = carry
   b. If l1 exists: sum += l1.val, l1 = l1.next
   c. If l2 exists: sum += l2.val, l2 = l2.next
   d. carry = sum / 10
   e. digit = sum % 10
   f. Create new node with digit, attach to curr
   g. curr = curr.next
4. Return dummy.next
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `l1: 2 → 4 → 3`, `l2: 5 → 6 → 4`

```
═══════════════════════════════════════════════════════════

Initial:
  dummy → null
  curr = dummy
  carry = 0
  l1 = 2, l2 = 5

═══════════════════════════════════════════════════════════

Step 1: Add first digits

  sum = carry + l1.val + l2.val = 0 + 2 + 5 = 7
  carry = 7 / 10 = 0
  digit = 7 % 10 = 7
  
  Create node(7), attach to curr
  curr = node(7)
  l1 = 4, l2 = 6
  
  Result: dummy → 7

═══════════════════════════════════════════════════════════

Step 2: Add second digits

  sum = carry + l1.val + l2.val = 0 + 4 + 6 = 10
  carry = 10 / 10 = 1
  digit = 10 % 10 = 0
  
  Create node(0), attach to curr
  curr = node(0)
  l1 = 3, l2 = 4
  
  Result: dummy → 7 → 0

═══════════════════════════════════════════════════════════

Step 3: Add third digits

  sum = carry + l1.val + l2.val = 1 + 3 + 4 = 8
  carry = 8 / 10 = 0
  digit = 8 % 10 = 8
  
  Create node(8), attach to curr
  curr = node(8)
  l1 = null, l2 = null
  
  Result: dummy → 7 → 0 → 8

═══════════════════════════════════════════════════════════

Step 4: Check loop condition

  l1 = null, l2 = null, carry = 0
  
  All are false/zero, loop exits

═══════════════════════════════════════════════════════════

Return dummy.next = 7

Output: 7 → 0 → 8 ✓ (represents 807)
```

**Edge case with final carry:** `l1: 9 → 9`, `l2: 1`

```
═══════════════════════════════════════════════════════════

Step 1: 9 + 1 + 0 = 10
  carry = 1, digit = 0
  Result: 0

Step 2: 9 + 0 + 1 = 10
  carry = 1, digit = 0
  Result: 0 → 0

Step 3: l1=null, l2=null, but carry=1!
  sum = 1 + 0 + 0 = 1
  carry = 0, digit = 1
  Result: 0 → 0 → 1

═══════════════════════════════════════════════════════════

Output: 0 → 0 → 1 ✓ (represents 100 = 99 + 1)
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    // Dummy node simplifies building the result list
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    
    // Carry for addition
    int carry = 0;
    
    // Continue while there are digits to add OR carry to process
    while (l1 != null || l2 != null || carry != 0) {
        // Start with carry from previous step
        int sum = carry;
        
        // Add l1's digit if it exists
        if (l1 != null) {
            sum += l1.val;
            l1 = l1.next;
        }
        
        // Add l2's digit if it exists
        if (l2 != null) {
            sum += l2.val;
            l2 = l2.next;
        }
        
        // Calculate new carry and digit
        carry = sum / 10;
        int digit = sum % 10;
        
        // Create new node and attach
        curr.next = new ListNode(digit);
        curr = curr.next;
    }
    
    return dummy.next;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Stopping when both lists are null | Misses final carry! | Include `carry != 0` in loop condition |
| Forgetting to handle different lengths | One list might be longer | Check each list separately with `if (l1 != null)` |
| Integer overflow | sum can be at most 9+9+1=19, fits in int | Not an issue for single digits |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Different lengths | Shorter list treated as having 0s |
| Final carry | Creates extra node (e.g., 99+1=100) |
| One list empty | Just copy the other list (with carry) |
| Both empty | Return 0 (if carry is 0) |

---

## Mind-Map Anchor

```
ADD TWO NUMBERS (REVERSE ORDER)
              │
              ▼
┌─────────────────────────────────┐
│ Reverse order = EASY!           │
│                                 │
│ While l1 OR l2 OR carry:        │
│   sum = carry + l1.val + l2.val │
│   carry = sum / 10              │
│   digit = sum % 10              │
│   Create node(digit)            │
│                                 │
│ KEY: Include carry in condition!│
└─────────────────────────────────┘
```

**Memory phrase:** "Add digits + carry, new carry = sum/10, digit = sum%10, don't forget final carry!"

---

# PATTERN 19: Add Two Numbers II (LeetCode 445)

## Pattern Recognition Signal

**When you see:** "add two numbers", "digits in normal order (not reversed)", "don't modify input lists"

**Instant thought:** "Normal order = hard! Use STACKS to access digits from the end, or REVERSE both lists first."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  l1: 7 → 2 → 4 → 3  (represents 7243)
        l2: 5 → 6 → 4      (represents 564)
        
Output: 7 → 8 → 0 → 7  (represents 7807)

7243 + 564 = 7807

Digits are in NORMAL order (most significant first).
This is HARDER than Pattern 18!
```

### Why Normal Order is Hard

```
Normal addition requires starting from the LEAST significant digit.

  l1: 7 → 2 → 4 → 3  (need to start from 3)
  l2: 5 → 6 → 4      (need to start from 4)

But linked lists only go forward!
We can't easily access the last digit first.
```

### Two Approaches

```
APPROACH 1: Use Stacks
  - Push all digits onto stacks
  - Pop from stacks (gives least significant first)
  - Add like Pattern 18
  - Build result by prepending (most significant first)

APPROACH 2: Reverse + Pattern 18 + Reverse
  - Reverse both input lists
  - Use Pattern 18 (add reversed lists)
  - Reverse the result
  - (Optional: reverse inputs back to restore them)
```

### Stack Approach Explained

```
l1: 7 → 2 → 4 → 3
    Push all: stack1 = [7, 2, 4, 3] (top = 3)

l2: 5 → 6 → 4
    Push all: stack2 = [5, 6, 4] (top = 4)

Now pop and add:
  3 + 4 = 7, prepend 7 → head = 7
  4 + 6 = 10, carry=1, prepend 0 → head = 0 → 7
  2 + 5 + 1 = 8, prepend 8 → head = 8 → 0 → 7
  7 + 0 + 0 = 7, prepend 7 → head = 7 → 8 → 0 → 7

Result: 7 → 8 → 0 → 7 ✓
```

### The Algorithm in Plain English (Stack Approach)

```
1. Push all digits of l1 onto stack1
2. Push all digits of l2 onto stack2
3. carry = 0, head = null
4. While stack1 OR stack2 OR carry:
   a. sum = carry
   b. If stack1 not empty: sum += stack1.pop()
   c. If stack2 not empty: sum += stack2.pop()
   d. carry = sum / 10
   e. digit = sum % 10
   f. PREPEND: create node(digit), node.next = head, head = node
5. Return head
```

---

## Visual Dry Run (Step-by-Step) — Stack Approach

**Input:** `l1: 7 → 2 → 4 → 3`, `l2: 5 → 6 → 4`

```
═══════════════════════════════════════════════════════════

Step 1: Build stacks

  Push l1: stack1 = [7, 2, 4, 3] (top = 3)
  Push l2: stack2 = [5, 6, 4] (top = 4)
  
  carry = 0
  head = null

═══════════════════════════════════════════════════════════

Step 2: Pop and add (iteration 1)

  sum = carry = 0
  sum += stack1.pop() = 3 → sum = 3
  sum += stack2.pop() = 4 → sum = 7
  
  carry = 7 / 10 = 0
  digit = 7 % 10 = 7
  
  Prepend: node(7).next = null, head = node(7)
  
  Result: head → 7

═══════════════════════════════════════════════════════════

Step 3: Pop and add (iteration 2)

  sum = carry = 0
  sum += stack1.pop() = 4 → sum = 4
  sum += stack2.pop() = 6 → sum = 10
  
  carry = 10 / 10 = 1
  digit = 10 % 10 = 0
  
  Prepend: node(0).next = head(7), head = node(0)
  
  Result: head → 0 → 7

═══════════════════════════════════════════════════════════

Step 4: Pop and add (iteration 3)

  sum = carry = 1
  sum += stack1.pop() = 2 → sum = 3
  sum += stack2.pop() = 5 → sum = 8
  
  carry = 8 / 10 = 0
  digit = 8 % 10 = 8
  
  Prepend: node(8).next = head(0), head = node(8)
  
  Result: head → 8 → 0 → 7

═══════════════════════════════════════════════════════════

Step 5: Pop and add (iteration 4)

  sum = carry = 0
  sum += stack1.pop() = 7 → sum = 7
  stack2 is empty
  
  carry = 7 / 10 = 0
  digit = 7 % 10 = 7
  
  Prepend: node(7).next = head(8), head = node(7)
  
  Result: head → 7 → 8 → 0 → 7

═══════════════════════════════════════════════════════════

Step 6: Check loop condition

  stack1 empty, stack2 empty, carry = 0
  
  Loop exits

═══════════════════════════════════════════════════════════

Return head = 7

Output: 7 → 8 → 0 → 7 ✓ (represents 7807)
```

---

## The Code — Approach 1: Stacks (With Line-by-Line Explanation)

```java
ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    // Create stacks to reverse the order of digits
    Stack<Integer> s1 = new Stack<>();
    Stack<Integer> s2 = new Stack<>();
    
    // Push all digits onto stacks
    while (l1 != null) {
        s1.push(l1.val);
        l1 = l1.next;
    }
    while (l2 != null) {
        s2.push(l2.val);
        l2 = l2.next;
    }
    
    int carry = 0;
    ListNode head = null;  // We'll build the result by prepending
    
    // Process while there are digits or carry
    while (!s1.isEmpty() || !s2.isEmpty() || carry != 0) {
        int sum = carry;
        
        if (!s1.isEmpty()) sum += s1.pop();
        if (!s2.isEmpty()) sum += s2.pop();
        
        carry = sum / 10;
        int digit = sum % 10;
        
        // PREPEND: new node points to current head, becomes new head
        ListNode node = new ListNode(digit);
        node.next = head;
        head = node;
    }
    
    return head;
}
```

---

## The Code — Approach 2: Reverse (With Line-by-Line Explanation)

```java
ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    // Reverse both lists
    l1 = reverse(l1);
    l2 = reverse(l2);
    
    // Use Pattern 18 to add reversed lists
    ListNode result = addReversed(l1, l2);
    
    // Reverse the result to get normal order
    return reverse(result);
}

// Pattern 18's add function
ListNode addReversed(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    int carry = 0;
    
    while (l1 != null || l2 != null || carry != 0) {
        int sum = carry;
        if (l1 != null) { sum += l1.val; l1 = l1.next; }
        if (l2 != null) { sum += l2.val; l2 = l2.next; }
        carry = sum / 10;
        curr.next = new ListNode(sum % 10);
        curr = curr.next;
    }
    
    return dummy.next;
}

// Standard reverse
ListNode reverse(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }
    return prev;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Using Pattern 18 directly | Digits are in wrong order | Use stacks or reverse first |
| Appending instead of prepending (stack approach) | Result would be reversed | Prepend: `node.next = head; head = node` |
| Forgetting final carry | Missing most significant digit | Include `carry != 0` in loop condition |

---

## Complexity Comparison

| Approach | Time | Space |
|----------|------|-------|
| Stacks | O(n + m) | O(n + m) for stacks |
| Reverse | O(n + m) | O(1) extra (modifies input) |

---

## Mind-Map Anchor

```
ADD TWO NUMBERS II (NORMAL ORDER)
              │
              ▼
┌─────────────────────────────────┐
│ Normal order = HARD!            │
│                                 │
│ APPROACH 1: STACKS              │
│   Push all digits               │
│   Pop and add (least sig first) │
│   PREPEND to result             │
│                                 │
│ APPROACH 2: REVERSE             │
│   Reverse both lists            │
│   Use Pattern 18                │
│   Reverse result                │
└─────────────────────────────────┘
```

**Memory phrase:** "Normal order? Use stacks and prepend, or reverse everything!"

---

# UPDATED MAANG Coverage Map

| # | Problem | LeetCode | Pattern Family | Difficulty |
|---|---------|----------|----------------|------------|
| 0 | Reverse Linked List | 206 | Reversal | Easy |
| 1 | Reverse Linked List II | 92 | Reversal | Medium |
| 2 | Reverse Nodes in k-Group | 25 | Reversal | Hard |
| 3 | Swap Nodes in Pairs | 24 | Reversal | Medium |
| 4 | Linked List Cycle | 141 | Cycle | Easy |
| 5 | Linked List Cycle II | 142 | Cycle | Medium |
| 6 | Middle of Linked List | 876 | Position | Easy |
| 7 | Remove Nth from End | 19 | Position | Medium |
| 8 | Intersection of Two Lists | 160 | Position | Easy |
| 9 | Merge Two Sorted Lists | 21 | Merge & Sort | Easy |
| 10 | Merge K Sorted Lists | 23 | Merge & Sort | Hard |
| 11 | Sort List | 148 | Merge & Sort | Medium |
| 12 | Reorder List | 143 | Structural | Medium |
| 13 | Palindrome Linked List | 234 | Structural | Easy |
| 14 | Partition List | 86 | Structural | Medium |
| 15 | Odd Even Linked List | 328 | Structural | Medium |
| 16 | Remove Duplicates I | 83 | Removal | Easy |
| 17 | Remove Duplicates II | 82 | Removal | Medium |
| 18 | Add Two Numbers | 2 | Math | Medium |
| 19 | Add Two Numbers II | 445 | Math | Medium |
| **20** | **Copy List with Random Pointer** | **138** | **Deep Copy** | **Medium** |
| **21** | **LRU Cache** | **146** | **Design (DLL + HashMap)** | **Medium** |
| **22** | **Rotate List** | **61** | **Structural** | **Medium** |

---

# Pattern Recognition Cheat Sheet

## By Problem Type

| When you see... | Think... | Pattern # |
|-----------------|----------|-----------|
| "Reverse" | prev, curr, next | 0, 1, 2, 3 |
| "Cycle" / "Loop" | Fast-Slow (Tortoise & Hare) | 4, 5 |
| "Middle" | Fast-Slow | 6 |
| "Nth from end" | Runner with gap | 7 |
| "Intersection" | Two pointers, swap at end | 8 |
| "Merge sorted" | Dummy + compare + attach | 9, 10 |
| "Sort list" | Merge sort (middle + merge) | 11 |
| "Reorder" / "Interleave" | Middle + Reverse + Merge | 12 |
| "Palindrome" | Middle + Reverse + Compare | 13 |
| "Partition" | Two dummy heads | 14 |
| "Odd/Even positions" | Two pointers alternating | 15 |
| "Remove duplicates" | Skip equal nodes | 16, 17 |
| "Add numbers" | Digit by digit + carry | 18, 19 |
| **"Deep copy" / "Clone"** | **HashMap or Interweave** | **20** |
| **"LRU Cache"** | **Doubly LL + HashMap** | **21** |
| **"Rotate"** | **Make circular, cut** | **22** |
| "Add numbers" | Digit by digit + carry | 18, 19 |

## The 5 Setups Quick Reference

| Setup | Pointers | Use For |
|-------|----------|---------|
| Reversal | `prev, curr, next` | Flipping arrows |
| Fast-Slow | `slow, fast` | Middle, cycle |
| Runner | `slow, fast` (gap) | Nth from end |
| Dummy | `dummy → head` | Merge, delete head |
| Two-List | `p1, p2` | Merge, intersect |

---

# Mastery Checklist

## Must Know Cold (Foundation)
- [ ] Reverse Linked List (Pattern 0) — The foundation of everything
- [ ] Fast-Slow for middle (Pattern 6) — Used in many compound patterns
- [ ] Cycle detection (Pattern 4) — Classic interview question
- [ ] Merge Two Sorted (Pattern 9) — Building block for harder problems

## Must Know Well (Core)
- [ ] Reverse II (Pattern 1) — Segment reversal
- [ ] Cycle II entry point (Pattern 5) — Floyd's Phase 2
- [ ] Remove Nth from End (Pattern 7) — Runner technique
- [ ] Sort List (Pattern 11) — Merge sort on linked list
- [ ] Reorder List (Pattern 12) — Combines 3 patterns
- [ ] Palindrome (Pattern 13) — Combines 3 patterns

## Know the Approach (Advanced)
- [ ] Reverse k-Group (Pattern 2) — Hard but common
- [ ] Merge K Lists (Pattern 10) — Heap or divide & conquer
- [ ] Partition List (Pattern 14) — Two dummy heads
- [ ] Remove Duplicates II (Pattern 17) — Skip ALL duplicates

## Understand the Concept (Math)
- [ ] Add Two Numbers (Pattern 18) — Digit by digit
- [ ] Add Two Numbers II (Pattern 19) — Stacks or reverse

---

# The 5 Most Common Mistakes

| Mistake | Why It's Wrong | Fix |
|---------|----------------|-----|
| Not saving `next` before redirect | Lose rest of list forever | `next = curr.next` FIRST |
| Wrong null check order | NPE on `fast.next` | `fast != null && fast.next != null` |
| Forgetting dummy head | Can't handle delete/change head | Always use dummy for merge/delete |
| Gap of n instead of n+1 | Land AT target, not before | Advance n+1 for removal |
| Not terminating chains | Creates cycles | `greater.next = null` in partition |

---

# PATTERN 20: Copy List with Random Pointer (LeetCode 138)

## Pattern Recognition Signal

**When you see:** "deep copy a linked list", "clone with random/arbitrary pointers", "copy list with extra pointers"

**Instant thought:** "HashMap (easy) or Interweaving (O(1) space)! The challenge is setting random pointers when copy nodes don't exist yet."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:
  A → B → C → null
  ↓   ↓   ↓
  C   A   B   (random pointers)

Output: A deep copy where:
  - Each node is a NEW node (not the same object)
  - next pointers form the same structure
  - random pointers point to the COPY of the original's random target
```

### The Challenge

```
When we create copy of A, we want to set A'.random = C'.
But C' might not exist yet!

  Original: A → B → C
            ↓
            C (A.random points to C)
  
  Copy: A' → ? (we're creating A', but C' doesn't exist yet!)
        ↓
        ? (can't set A'.random = C' because C' doesn't exist)
```

### Two Approaches

```
APPROACH 1: HashMap
  - First pass: Create all copy nodes, map original → copy
  - Second pass: Wire next and random using the map
  - Space: O(n) for the map

APPROACH 2: Interweaving (O(1) space)
  - Insert each copy RIGHT AFTER its original
  - Now copy.random = original.random.next (the copy!)
  - Separate the two lists
```

### Interweaving Explained

```
Step 1: INTERWEAVE
  Original: A → B → C
  After:    A → A' → B → B' → C → C'
  
  Each copy is right after its original!

Step 2: SET RANDOM
  If A.random = C, then A'.random = C.next = C'
  
  A → A' → B → B' → C → C'
  ↓   ↓
  C   C' (A.random.next is C', which is what A'.random should be!)

Step 3: SEPARATE
  Extract: A' → B' → C' (the copy)
  Restore: A → B → C (the original)
```

### The Algorithm in Plain English (Interweaving)

```
1. INTERWEAVE: For each node, create copy and insert after it
   A → B → C becomes A → A' → B → B' → C → C'

2. SET RANDOM: For each original node:
   If original.random != null:
     copy.random = original.random.next

3. SEPARATE: Extract copy list, restore original list
```

---

## Visual Dry Run (Step-by-Step) — Interweaving

**Input:** `A(random→C) → B(random→A) → C(random→B)`

```
═══════════════════════════════════════════════════════════

Step 1: INTERWEAVE

  Original: A → B → C → null
  
  Process A:
    Create A', insert after A
    A → A' → B → C
  
  Process B:
    Create B', insert after B
    A → A' → B → B' → C
  
  Process C:
    Create C', insert after C
    A → A' → B → B' → C → C' → null

═══════════════════════════════════════════════════════════

Step 2: SET RANDOM

  Process A:
    A.random = C
    A'.random = A.random.next = C.next = C'
    
  Process B:
    B.random = A
    B'.random = B.random.next = A.next = A'
    
  Process C:
    C.random = B
    C'.random = C.random.next = B.next = B'

  State:
    A → A' → B → B' → C → C' → null
    ↓   ↓    ↓   ↓    ↓   ↓
    C   C'   A   A'   B   B'

═══════════════════════════════════════════════════════════

Step 3: SEPARATE

  Extract copies, restore originals:
  
  curr = A
  
  Iteration 1:
    copy = A.next = A'
    A.next = A'.next = B (restore original)
    A'.next = B.next = B' (link copies)
    curr = B
    
  Iteration 2:
    copy = B.next = B'
    B.next = B'.next = C (restore original)
    B'.next = C.next = C' (link copies)
    curr = C
    
  Iteration 3:
    copy = C.next = C'
    C.next = C'.next = null (restore original)
    C'.next = null (already null)
    curr = null

  Original: A → B → C → null (restored)
  Copy:     A' → B' → C' → null (extracted)

═══════════════════════════════════════════════════════════

Return A' (head of copy list)

Output: Deep copy with correct random pointers ✓
```

---

## The Code — Interweaving (O(1) Space)

```java
Node copyRandomList(Node head) {
    if (head == null) return null;
    
    // Step 1: INTERWEAVE - Create copies and insert after originals
    Node curr = head;
    while (curr != null) {
        Node copy = new Node(curr.val);
        copy.next = curr.next;      // Copy points to original's next
        curr.next = copy;           // Original points to copy
        curr = copy.next;           // Move to next original
    }
    
    // Step 2: SET RANDOM POINTERS
    curr = head;
    while (curr != null) {
        if (curr.random != null) {
            // Copy's random = original's random's next (which is the copy!)
            curr.next.random = curr.random.next;
        }
        curr = curr.next.next;      // Skip to next original
    }
    
    // Step 3: SEPARATE - Extract copy list, restore original
    Node dummy = new Node(0);
    Node copyTail = dummy;
    curr = head;
    
    while (curr != null) {
        Node copy = curr.next;      // The copy node
        
        // Extract copy
        copyTail.next = copy;
        copyTail = copy;
        
        // Restore original's next
        curr.next = copy.next;
        curr = curr.next;
    }
    
    return dummy.next;
}
```

---

## The Code — HashMap (Easier to Understand)

```java
Node copyRandomList(Node head) {
    if (head == null) return null;
    
    // Map: original node → copy node
    Map<Node, Node> map = new HashMap<>();
    
    // First pass: Create all copy nodes
    Node curr = head;
    while (curr != null) {
        map.put(curr, new Node(curr.val));
        curr = curr.next;
    }
    
    // Second pass: Wire next and random pointers
    curr = head;
    while (curr != null) {
        Node copy = map.get(curr);
        copy.next = map.get(curr.next);      // May be null
        copy.random = map.get(curr.random);  // May be null
        curr = curr.next;
    }
    
    return map.get(head);
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Setting random before interweaving | Copy nodes don't exist yet | Interweave first, then set random |
| Forgetting to restore original list | Interviewer might require it | Separate step restores originals |
| NPE on null random | Not all nodes have random pointers | Check `if (curr.random != null)` |

---

## Complexity Comparison

| Approach | Time | Space |
|----------|------|-------|
| HashMap | O(n) | O(n) for map |
| Interweaving | O(n) | O(1) extra |

---

## Mind-Map Anchor

```
COPY LIST WITH RANDOM POINTER
              │
              ▼
┌─────────────────────────────────┐
│ INTERWEAVING (O(1) space):      │
│                                 │
│ 1. Insert copy after original   │
│    A → A' → B → B' → C → C'     │
│                                 │
│ 2. Set random:                  │
│    copy.random = orig.random.next│
│                                 │
│ 3. Separate lists               │
│                                 │
│ OR use HashMap (easier)         │
└─────────────────────────────────┘
```

**Memory phrase:** "Interweave, set random via .next, separate"

---

# PATTERN 21: LRU Cache (LeetCode 146)

## Pattern Recognition Signal

**When you see:** "LRU Cache", "Least Recently Used", "cache with eviction policy", "O(1) get and put"

**Instant thought:** "HashMap + Doubly Linked List! Map for O(1) lookup, DLL for O(1) add/remove and maintaining order."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Design a cache with capacity limit that:
- get(key): Return value in O(1), mark as recently used
- put(key, value): Insert/update in O(1), evict LRU if full

When cache is full and we add a new item,
remove the LEAST recently used item.
```

### Why Two Data Structures?

```
HashMap alone:
  ✓ O(1) lookup by key
  ✗ Can't track usage order
  ✗ Can't find LRU item quickly

Linked List alone:
  ✓ Can track order (head = most recent, tail = least recent)
  ✗ O(n) lookup by key

HashMap + Doubly Linked List:
  ✓ O(1) lookup by key (HashMap)
  ✓ O(1) add/remove (DLL)
  ✓ O(1) find LRU (tail of DLL)
  ✓ O(1) move to front (remove + add to head)
```

### The Data Structure

```
HashMap: key → Node
  - O(1) lookup to find the node

Doubly Linked List: HEAD ⟷ [nodes] ⟷ TAIL
  - HEAD.next = most recently used
  - TAIL.prev = least recently used
  - O(1) remove any node (given the node)
  - O(1) add to head

Node contains: key, value, prev, next
  - Why store key? When evicting, we need the key to remove from HashMap!
```

### The Operations

```
GET(key):
  1. If key not in map → return -1
  2. Get node from map
  3. Move node to head (most recently used)
  4. Return node.value

PUT(key, value):
  1. If key exists:
     - Update node.value
     - Move node to head
  2. If key doesn't exist:
     - Create new node
     - Add to map
     - Add to head
     - If over capacity:
       - Remove tail.prev (LRU)
       - Remove from map using node.key
```

### Why Dummy Head and Tail?

```
Without dummies:
  - Special cases for empty list
  - Special cases for single node
  - Lots of null checks

With dummies:
  - HEAD and TAIL are always present
  - Real nodes are between them
  - No special cases!
  
  HEAD ⟷ [real nodes] ⟷ TAIL
```

---

## Visual Dry Run (Step-by-Step)

**Operations:** `LRUCache(2), put(1,1), put(2,2), get(1), put(3,3), get(2)`

```
═══════════════════════════════════════════════════════════

LRUCache(2): Create cache with capacity 2

  Map: {}
  List: HEAD ⟷ TAIL
  
  (Empty cache)

═══════════════════════════════════════════════════════════

put(1, 1): Add key=1, value=1

  Create node(1, 1)
  Add to map: {1 → node}
  Add to head of list
  
  Map: {1 → node(1,1)}
  List: HEAD ⟷ [1:1] ⟷ TAIL
        most recent    least recent

═══════════════════════════════════════════════════════════

put(2, 2): Add key=2, value=2

  Create node(2, 2)
  Add to map: {1 → node, 2 → node}
  Add to head of list
  
  Map: {1 → node(1,1), 2 → node(2,2)}
  List: HEAD ⟷ [2:2] ⟷ [1:1] ⟷ TAIL
        most recent      least recent

═══════════════════════════════════════════════════════════

get(1): Get key=1

  Find node(1,1) in map
  Move to head (mark as recently used)
  Return 1
  
  Map: {1 → node(1,1), 2 → node(2,2)}
  List: HEAD ⟷ [1:1] ⟷ [2:2] ⟷ TAIL
        most recent      least recent
  
  (1 is now most recent, 2 is now least recent)

═══════════════════════════════════════════════════════════

put(3, 3): Add key=3, value=3

  Cache is FULL (size=2, capacity=2)!
  
  Step 1: Evict LRU (tail.prev = node(2,2))
    Remove node(2,2) from list
    Remove key=2 from map
    
    Map: {1 → node(1,1)}
    List: HEAD ⟷ [1:1] ⟷ TAIL
  
  Step 2: Add new node
    Create node(3, 3)
    Add to map: {1 → node, 3 → node}
    Add to head of list
    
    Map: {1 → node(1,1), 3 → node(3,3)}
    List: HEAD ⟷ [3:3] ⟷ [1:1] ⟷ TAIL
          most recent      least recent

═══════════════════════════════════════════════════════════

get(2): Get key=2

  Key=2 not in map (was evicted!)
  Return -1

═══════════════════════════════════════════════════════════

Final State:
  Map: {1 → node(1,1), 3 → node(3,3)}
  List: HEAD ⟷ [3:3] ⟷ [1:1] ⟷ TAIL
```

---

## The Code (With Line-by-Line Explanation)

```java
class LRUCache {
    
    // Doubly Linked List Node
    class Node {
        int key, value;
        Node prev, next;
        
        Node(int k, int v) {
            key = k;
            value = v;
        }
    }
    
    private int capacity;
    private Map<Integer, Node> map;
    private Node head, tail;  // Dummy head and tail
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        
        // Initialize dummy head and tail
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        // Key not found
        if (!map.containsKey(key)) return -1;
        
        // Get the node
        Node node = map.get(key);
        
        // Move to head (mark as most recently used)
        removeNode(node);
        addToHead(node);
        
        return node.value;
    }
    
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            // Update existing node
            Node node = map.get(key);
            node.value = value;
            
            // Move to head
            removeNode(node);
            addToHead(node);
        } else {
            // Create new node
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            addToHead(newNode);
            
            // Check capacity
            if (map.size() > capacity) {
                // Evict LRU (tail.prev)
                Node lru = tail.prev;
                removeNode(lru);
                map.remove(lru.key);  // Need key to remove from map!
            }
        }
    }
    
    // Helper: Remove a node from the list (O(1) with doubly linked)
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    // Helper: Add a node right after head (most recent position)
    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Using singly linked list | Can't remove node in O(1) without prev | Use doubly linked list |
| Not storing key in node | Can't remove from map when evicting | Node must have key field |
| Not using dummy head/tail | Edge cases become messy | Always use dummies |
| Forgetting to move to head on get | get() should mark as recently used | Always move to head |

---

## Why Doubly Linked List?

```
Singly Linked: To remove node X, need node BEFORE X
               Finding prev is O(n)!

Doubly Linked: Node X has X.prev
               Remove in O(1):
                 X.prev.next = X.next
                 X.next.prev = X.prev
```

---

## Mind-Map Anchor

```
LRU CACHE
    │
    ▼
┌─────────────────────────────────┐
│ HashMap + Doubly Linked List    │
│                                 │
│ Map: key → Node (O(1) lookup)   │
│ DLL: HEAD ⟷ [nodes] ⟷ TAIL     │
│      most recent   least recent │
│                                 │
│ GET: find, move to head, return │
│ PUT: add/update, move to head   │
│      if full: evict tail.prev   │
│                                 │
│ Node stores KEY for eviction!   │
└─────────────────────────────────┘
```

**Memory phrase:** "Map for lookup, DLL for order, head=recent, tail=LRU, node stores key"

---

# PATTERN 22: Rotate List (LeetCode 61)

## Pattern Recognition Signal

**When you see:** "rotate list to the right by k places", "move last k nodes to front"

**Instant thought:** "Make it circular, then cut at the right place! Find length, connect tail to head, find new tail, cut."

---

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input:  1 → 2 → 3 → 4 → 5, k=2
Output: 4 → 5 → 1 → 2 → 3

Rotate RIGHT by 2:
- Move last 2 nodes (4,5) to the front
- Or equivalently: cut after position (len - k) and move tail to front
```

### The Circular List Trick

```
Instead of moving nodes, think of it as:
1. Make the list circular (connect tail to head)
2. Find the new tail (at position len - k - 1)
3. Cut after the new tail

Before (circular):
  1 → 2 → 3 → 4 → 5 → (back to 1)
  
New tail at position (5 - 2 - 1) = 2 (node 3)
New head is node 4

After cutting:
  4 → 5 → 1 → 2 → 3 → null
```

### Handling k >= length

```
If k = 7 and length = 5:
  Rotating 7 times = rotating 2 times (7 % 5 = 2)
  
If k = 5 and length = 5:
  Rotating 5 times = no rotation (5 % 5 = 0)
  
Always use: k = k % length
```

### The Algorithm in Plain English

```
1. Handle edge cases (null, single node, k=0)
2. Find the length and the tail node
3. k = k % length (handle k >= length)
4. If k == 0, no rotation needed
5. Connect tail to head (make circular)
6. Find new tail: move (length - k - 1) steps from head
7. New head = newTail.next
8. Cut: newTail.next = null
9. Return new head
```

---

## Visual Dry Run (Step-by-Step)

**Input:** `1 → 2 → 3 → 4 → 5`, k=2

```
═══════════════════════════════════════════════════════════

Step 1: Find length and tail

  Walk through list:
    1 → 2 → 3 → 4 → 5 → null
    
  length = 5
  tail = node 5

═══════════════════════════════════════════════════════════

Step 2: Handle k >= length

  k = 2 % 5 = 2
  
  k != 0, so we need to rotate

═══════════════════════════════════════════════════════════

Step 3: Make circular

  tail.next = head
  5.next = 1
  
  1 → 2 → 3 → 4 → 5 → (back to 1)
  ↑               ↓
  └───────────────┘

═══════════════════════════════════════════════════════════

Step 4: Find new tail

  New tail position = length - k - 1 = 5 - 2 - 1 = 2
  
  Start at head (node 1), move 2 steps:
    Step 0: at node 1
    Step 1: at node 2
    Step 2: at node 3
  
  newTail = node 3

═══════════════════════════════════════════════════════════

Step 5: Find new head and cut

  newHead = newTail.next = node 4
  
  Cut: newTail.next = null
       3.next = null
  
  Result:
    4 → 5 → 1 → 2 → 3 → null
    ↑
  newHead

═══════════════════════════════════════════════════════════

Return newHead = node 4

Output: 4 → 5 → 1 → 2 → 3 ✓
```

---

## The Code (With Line-by-Line Explanation)

```java
ListNode rotateRight(ListNode head, int k) {
    // Edge cases
    if (head == null || head.next == null || k == 0) {
        return head;
    }
    
    // Step 1: Find length and tail
    int length = 1;
    ListNode tail = head;
    while (tail.next != null) {
        tail = tail.next;
        length++;
    }
    
    // Step 2: Handle k >= length
    k = k % length;
    if (k == 0) {
        return head;  // No rotation needed
    }
    
    // Step 3: Make circular
    tail.next = head;
    
    // Step 4: Find new tail
    // New tail is at position (length - k - 1) from head
    int stepsToNewTail = length - k - 1;
    ListNode newTail = head;
    for (int i = 0; i < stepsToNewTail; i++) {
        newTail = newTail.next;
    }
    
    // Step 5: Find new head and cut
    ListNode newHead = newTail.next;
    newTail.next = null;  // Cut the circular list
    
    return newHead;
}
```

---

## Common Traps

| Trap | Why Wrong | Fix |
|------|-----------|-----|
| Not handling k >= length | Unnecessary rotations or wrong result | Use `k = k % length` |
| Off-by-one finding new tail | Cut at wrong position | New tail is at `length - k - 1` steps |
| Forgetting to cut | Returns circular list | `newTail.next = null` |
| Not handling k = 0 after mod | Unnecessary work | Check `if (k == 0) return head` |

---

## Edge Cases

| Case | What Happens |
|------|--------------|
| Empty list | Return null |
| Single node | Return that node (no rotation possible) |
| k = 0 | Return original list |
| k = length | No rotation (k % length = 0) |
| k > length | Use k % length |

---

## Alternative Thinking

```
Rotate right by k = Move last k nodes to front

Another way to think about it:
  - Find the (length - k)th node (new tail)
  - Everything after it moves to front
  
  1 → 2 → 3 → 4 → 5, k=2
      ↑
  (length - k) = 3rd node = new tail
  
  Cut after 3: [1,2,3] and [4,5]
  Reconnect: [4,5] + [1,2,3] = 4 → 5 → 1 → 2 → 3
```

---

## Mind-Map Anchor

```
ROTATE LIST
     │
     ▼
┌─────────────────────────────────┐
│ 1. Find length and tail         │
│ 2. k = k % length               │
│ 3. Make circular: tail→head     │
│ 4. Find new tail at (len-k-1)   │
│ 5. New head = newTail.next      │
│ 6. Cut: newTail.next = null     │
└─────────────────────────────────┘
```

**Memory phrase:** "Find length, mod k, make circular, find new tail, cut"

---

# UPDATED MAANG Coverage Map

When explaining a linked list solution:

1. **State the technique:** "I'll use the fast-slow pointer technique..."
2. **Explain why:** "...because we need to find the middle without knowing the length."
3. **Walk through the setup:** "I'll have slow move 1 step and fast move 2 steps..."
4. **Mention edge cases:** "For empty list, I return null. For single node..."
5. **State complexity:** "Time is O(n), space is O(1) since we only use pointers."

---

**Prev →** `01_Linked_List_Patterns.md`  ·  **Next →** `../12_Recursion_Backtracking/01_Recursion_Backtracking.md`
