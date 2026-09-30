# Section 14 — Heap & Priority Queue Patterns Deep Dive (MAANG L5 Coverage)

--

# INDEX — Quick Navigation

## Core Concepts
| Section | Description |
|-----|-------|
| [The "One Sentence"](#the-one-sentence-that-unlocks-all-heap-problems) | Unlocks all heap problems |
| [Heap Mechanics](#heap-mechanics-how-it-actually-works) | Visual understanding of heap operations |
| [The 4 Heap Patterns](#the-4-heap-patterns-your-weapons) | Your weapons |
| [Min vs Max Heap](#the-min-vs-max-heap-decision) | When to use which |
| [The 5-Question Template](#the-5-question-template-for-every-heap-problem) | Solve any heap problem |

--

## Top K Family (Patterns 0-3)
| # | Pattern | LeetCode |
|--|-----|-----|
| 0 | [Kth Largest Element](#pattern-0-kth-largest-element-in-array-leetcode-215) | 215 |
| 1 | [Top K Frequent Elements](#pattern-1-top-k-frequent-elements-leetcode-347) | 347 |
| 2 | [K Closest Points to Origin](#pattern-2-k-closest-points-to-origin-leetcode-973) | 973 |
| 3 | [Kth Smallest in Sorted Matrix](#pattern-3-kth-smallest-element-in-sorted-matrix-leetcode-378) | 378 |

## K-Way Merge Family (Patterns 4-6)
| # | Pattern | LeetCode |
|--|-----|-----|
| 4 | [Merge K Sorted Lists](#pattern-4-merge-k-sorted-lists-leetcode-23) | 23 |
| 5 | [Find K Pairs with Smallest Sums](#pattern-5-find-k-pairs-with-smallest-sums-leetcode-373) | 373 |
| 6 | [Smallest Range Covering K Lists](#pattern-6-smallest-range-covering-elements-from-k-lists-leetcode-632) | 632 |

## Two Heaps Family (Patterns 7-8)
| # | Pattern | LeetCode |
|--|-----|-----|
| 7 | [Find Median from Data Stream](#pattern-7-find-median-from-data-stream-leetcode-295) | 295 |
| 8 | [Sliding Window Median](#pattern-8-sliding-window-median-leetcode-480) | 480 |

## Scheduling & Greedy Family (Patterns 9-13)
| # | Pattern | LeetCode |
|--|-----|-----|
| 9 | [Task Scheduler](#pattern-9-task-scheduler-leetcode-621) | 621 |
| 10 | [Reorganize String](#pattern-10-reorganize-string-leetcode-767) | 767 |
| 11 | [Rearrange String K Distance Apart](#pattern-11-rearrange-string-k-distance-apart-leetcode-358) | 358 |
| 12 | [Meeting Rooms II](#pattern-12-meeting-rooms-ii-leetcode-253) | 253 |
| 13 | [IPO](#pattern-13-ipo-leetcode-502) | 502 |

## Special Patterns (Pattern 14)
| # | Pattern | LeetCode |
|--|-----|-----|
| 14 | [Ugly Number II](#pattern-14-ugly-number-ii-leetcode-264) | 264 |

## Bonus Patterns (15-18) — Additional MAANG Coverage
| # | Pattern | LeetCode |
|--|-----|-----|
| 15 | [Kth Largest in Stream](#pattern-15-kth-largest-element-in-a-stream-leetcode-703) | 703 |
| 16 | [Last Stone Weight](#pattern-16-last-stone-weight-leetcode-1046) | 1046 |
| 17 | [Design Twitter](#pattern-17-design-twitter-leetcode-355) | 355 |
| 18 | [Single-Threaded CPU](#pattern-18-single-threaded-cpu-leetcode-1834) | 1834 |

## Reference Sections
| Section |
|-----|
| [MAANG Coverage Map](#maang-coverage-map) |
| [Heap Cheat Sheet](#heap-cheat-sheet) |
| [Mastery Checklist](#mastery-checklist) |

--

# The "One Sentence That Unlocks All Heap Problems"

> **"A heap answers ONE question instantly: What is the BEST candidate RIGHT NOW?"**

That's the entire subject. Every heap problem is just:
1. **Define "best"** — smallest? largest? most frequent? closest?
2. **Maintain candidates** — add new ones, remove processed ones
3. **Query the best** — peek or poll the top

--

## 📋 THE JUNIOR DEV CHEAT CARD (Memorize This!)

```
╔═══════════════════════════════════════════════════════════════════════╗
║                     HEAP PROBLEM? USE THIS!                            ║
╠═══════════════════════════════════════════════════════════════════════╣
║                                                                        ║
║  STEP 1: "What should LEAVE when heap is full?"                       ║
║          └─→ That thing should be at the ROOT                         ║
║                                                                        ║
║  STEP 2: Pick heap type                                               ║
║          • Smallest should leave → MIN-heap                           ║
║          • Largest should leave  → MAX-heap                           ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  COMMON PATTERNS (just memorize these!):                              ║
║                                                                        ║
║  ┌────────────────────┬──────────────┬─────────────────────────────┐  ║
║  │ Problem Says       │ Use          │ Why                         │  ║
║  ├────────────────────┼──────────────┼─────────────────────────────┤  ║
║  │ "K largest"        │ MIN-heap     │ Kick small, keep large      │  ║
║  │ "K smallest"       │ MAX-heap     │ Kick large, keep small      │  ║
║  │ "K closest"        │ MAX-heap     │ Kick far, keep close        │  ║
║  │ "Merge K sorted"   │ MIN-heap     │ Always get smallest head    │  ║
║  │ "Median"           │ TWO heaps    │ Max-left + Min-right        │  ║
║  │ "Schedule tasks"   │ MAX-heap     │ Pick highest priority       │  ║
║  │ "Meeting rooms"    │ MIN-heap     │ Track earliest end time     │  ║
║  └────────────────────┴──────────────┴─────────────────────────────┘  ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  JAVA CODE TEMPLATES:                                                  ║
║                                                                        ║
║  // MIN-heap (default)                                                ║
║  PriorityQueue<Integer> min = new PriorityQueue<>();                  ║
║                                                                        ║
║  // MAX-heap                                                          ║
║  PriorityQueue<Integer> max = new PriorityQueue<>(                    ║
║      Collections.reverseOrder()                                       ║
║  );                                                                    ║
║                                                                        ║
║  // Top K pattern                                                     ║
║  for (item : items) {                                                 ║
║      heap.offer(item);                                                ║
║      if (heap.size() > k) heap.poll();                                ║
║  }                                                                     ║
║                                                                        ║
╚═══════════════════════════════════════════════════════════════════════╝
```

--

## The Mental Model: Why Heaps Exist

| Without Heap | With Heap |
|-------|------|
| "Find min" → scan all O(n) | "Find min" → peek O(1) |
| "Find min after insert" → scan again O(n) | "Find min after insert" → O(log n) |
| "Find min after delete" → scan again O(n) | "Find min after delete" → O(log n) |

**The Insight:** When you need the "best" repeatedly and data keeps changing, heap gives you O(log n) updates instead of O(n) scans!

--

# Heap Mechanics (How It Actually Works)

## The Physical Structure: Complete Binary Tree as Array

A heap is a **complete binary tree** stored in an **array**. No pointers needed!

```
Visual Tree:              Array Representation:
       10                 [10, 20, 15, 30, 40, 50, 25]
      /  \                 0   1   2   3   4   5   6
    20    15              
   /  \   / \             Index Math:
  30  40 50  25           - Parent of i: (i-1)/2
                          - Left child of i: 2*i + 1
                          - Right child of i: 2*i + 2
```

## The Heap Property

| Min-Heap | Max-Heap |
|-----|-----|
| Parent ≤ Children | Parent ≥ Children |
| Root = Smallest | Root = Largest |
| `new PriorityQueue<>()` | `new PriorityQueue<>(Collections.reverseOrder())` |

--

## The Two Core Operations: Bubble Up & Bubble Down

### Operation 1: INSERT (Bubble Up / Sift Up)

**What happens:** Add at the end, then bubble UP until heap property is restored.

```
Insert 5 into Min-Heap [10, 20, 15, 30, 40]:

Step 1: Add at end
       10                    
      /  \                   Array: [10, 20, 15, 30, 40, 5]
    20    15                              ↑
   /  \   /                            new element
  30  40 5

Step 2: Bubble up (5 < 15, swap)
       10                    
      /  \                   Array: [10, 20, 5, 30, 40, 15]
    20    5                 
   /  \   /                 
  30  40 15

Step 3: Bubble up (5 < 10, swap)
       5                     
      /  \                   Array: [5, 20, 10, 30, 40, 15]
    20    10                
   /  \   /                 
  30  40 15

Done! 5 is now at root (smallest).
```

**Time:** O(log n) — at most tree height swaps

--

### Operation 2: REMOVE MIN/MAX (Bubble Down / Sift Down)

**What happens:** Replace root with last element, then bubble DOWN until heap property is restored.

```
Remove min from [5, 20, 10, 30, 40, 15]:

Step 1: Replace root with last element
       15                    
      /  \                   Array: [15, 20, 10, 30, 40]
    20    10                
   /  \                     
  30  40

Step 2: Bubble down (15 > min(20,10)=10, swap with 10)
       10                    
      /  \                   Array: [10, 20, 15, 30, 40]
    20    15                
   /  \                     
  30  40

Done! 10 is now at root (new smallest).
```

**Time:** O(log n) — at most tree height swaps

--

## Visual Summary of Heap Operations

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HEAP OPERATIONS CHEAT SHEET                       │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  OPERATION        │ HOW IT WORKS              │ TIME    │ DIRECTION │
│  ─────────────────┼───────────────────────────┼─────────┼───────────│
│  peek()           │ Return root               │ O(1)    │ -         │
│  offer(x)/add(x)  │ Add at end, bubble UP     │ O(log n)│ ↑ UP      │
│  poll()/remove()  │ Remove root, bubble DOWN  │ O(log n)│ ↓ DOWN    │
│  heapify (build)  │ Bubble down from n/2 to 0 │ O(n)    │ ↓ DOWN    │
│                                                                      │
│  Memory Trick:                                                       │
│  • INSERT = add at BOTTOM, bubble UP (child wants to be parent)     │
│  • DELETE = put BOTTOM at TOP, bubble DOWN (impostor exposed)       │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

--

# The 4 Heap Patterns (Your "Weapons")

## Pattern Overview

| Pattern | When to Use | Heap Type | Example Problems |
|-----|-------|------|---------|
| **Top K** | Find k largest/smallest/closest | Opposite heap (min for largest) | Kth Largest, Top K Frequent |
| **K-Way Merge** | Merge k sorted sequences | Min-heap of k heads | Merge K Lists, Smallest Range |
| **Two Heaps** | Track median or split data | Max-heap + Min-heap | Median Stream, Sliding Median |
| **Greedy Scheduling** | Optimal ordering with constraints | Max-heap by priority | Task Scheduler, Reorganize String |

--

## Pattern 1: Top K — "Keep the Best K"

### The Mental Model

**Counterintuitive Insight:** To find K LARGEST, use a MIN-heap of size K!

Why? The min-heap acts as a "bouncer" that kicks out the smallest among candidates. After processing all elements, only the K largest survive!

```
Finding Top 3 Largest from [1, 5, 2, 8, 3, 9, 4]:

Min-heap (size limit = 3):

Add 1: [1]
Add 5: [1, 5]
Add 2: [1, 2, 5]         ← heap full
Add 8: [1, 2, 5, 8] → kick smallest → [2, 5, 8]
Add 3: [2, 3, 5, 8] → kick smallest → [3, 5, 8]
Add 9: [3, 5, 8, 9] → kick smallest → [5, 8, 9]
Add 4: [4, 5, 8, 9] → kick smallest → [5, 8, 9]

Result: [5, 8, 9] are the top 3 largest!
Kth largest (k=3) = heap.peek() = 5
```

### The Rule

| Want | Use | Why |
|---|---|---|
| K Largest | Min-heap size K | Kicks out small ones, keeps large |
| K Smallest | Max-heap size K | Kicks out large ones, keeps small |
| Kth Largest | Min-heap size K, peek | Root is smallest among K largest = Kth largest |
| Kth Smallest | Max-heap size K, peek | Root is largest among K smallest = Kth smallest |

--

## Pattern 2: K-Way Merge — "Best of K Streams"

### The Mental Model

You have K sorted streams. At any moment, the global minimum must be one of the K current heads. Use a min-heap to track all K heads!

```
Merge 3 sorted lists:
List 1: [1, 4, 7]
List 2: [2, 5, 8]
List 3: [3, 6, 9]

Heap tracks current heads:

Initial: heap = [(1,L1), (2,L2), (3,L3)]
Poll (1,L1) → output 1, push (4,L1) → heap = [(2,L2), (3,L3), (4,L1)]
Poll (2,L2) → output 2, push (5,L2) → heap = [(3,L3), (4,L1), (5,L2)]
Poll (3,L3) → output 3, push (6,L3) → heap = [(4,L1), (5,L2), (6,L3)]
...continue...

Output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
```

### The Key Insight

- Heap size is always ≤ K (one entry per stream)
- Each element is pushed and popped exactly once
- Time: O(N log K) where N = total elements

--

## Pattern 3: Two Heaps — "Split the World in Half"

### The Mental Model

Maintain two heaps that split the data:
- **Max-heap (left):** Smaller half (we want the LARGEST of the small half)
- **Min-heap (right):** Larger half (we want the SMALLEST of the large half)

```
Data: [1, 2, 3, 4, 5, 6, 7]

Left (max-heap): [1, 2, 3, 4]  ← max = 4
Right (min-heap): [5, 6, 7]    ← min = 5

Median = (4 + 5) / 2 = 4.5

The two heaps meet at the middle!
```

### Balance Rules

1. All elements in left ≤ All elements in right
2. Size difference ≤ 1
3. Left can have one extra (for odd count)

--

## Pattern 4: Greedy Scheduling — "Always Pick the Best Available"

### The Mental Model

When scheduling tasks with constraints (cooldowns, distances), always pick the highest priority task that's currently available.

```
Tasks: A=3, B=2, C=1, cooldown=2

Max-heap by frequency: [A:3, B:2, C:1]

Time 0: Pick A (highest), A:2 remaining, A unavailable until time 3
Time 1: Pick B (highest available), B:1 remaining
Time 2: Pick C (highest available), C:0 remaining
Time 3: Pick A (available again), A:1 remaining
...
```

--

# The Min vs Max Heap Decision

```
┌─────────────────────────────────────────────────────────────────────┐
│                    WHICH HEAP TO USE?                                │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  QUESTION: "What do I need quick access to?"                        │
│                                                                      │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │ Need SMALLEST quickly?  →  MIN-HEAP (default PriorityQueue) │    │
│  │ Need LARGEST quickly?   →  MAX-HEAP (reverseOrder)          │    │
│  └─────────────────────────────────────────────────────────────┘    │
│                                                                      │
│  COUNTERINTUITIVE CASES:                                            │
│                                                                      │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │ "Find K LARGEST"  →  Use MIN-heap size K                    │    │
│  │                       (kick out small, keep large)          │    │
│  │                                                             │    │
│  │ "Find K SMALLEST" →  Use MAX-heap size K                    │    │
│  │                       (kick out large, keep small)          │    │
│  └─────────────────────────────────────────────────────────────┘    │
│                                                                      │
│  WHY? The heap root is what gets REMOVED when full.                 │
│  To keep largest, remove smallest → min-heap.                       │
│  To keep smallest, remove largest → max-heap.                       │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

--

# Java PriorityQueue Cheat Sheet

```java
// MIN-HEAP (default) — smallest at top
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// MAX-HEAP — largest at top
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
// OR
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
// SAFER (no overflow):
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));

// CUSTOM OBJECT — by specific field
PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[0] - b[0]); // by first element
PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[1] - a[1]); // by second element, descending

// OPERATIONS
heap.offer(x);      // Add element, O(log n)
heap.poll();        // Remove and return top, O(log n)
heap.peek();        // Return top without removing, O(1)
heap.size();        // Number of elements, O(1)
heap.isEmpty();     // Check if empty, O(1)
```

--

# The 5-Question Template (For Every Heap Problem)

This template is your **mental checklist** before writing any heap code. Walk through each question, and the solution reveals itself!

--

## 🚀 QUICK START: The 60-Second Heap Approach

**Before diving into the detailed template, here's the simple version:**

### Step 1: Ask "What do I need FAST?"

Every heap problem boils down to: **"I need to quickly find the [smallest/largest/best] thing."**

- "Kth largest" → I need to quickly find the smallest among my K candidates (to kick it out)
- "Merge K lists" → I need to quickly find the smallest among K current heads
- "Median" → I need to quickly find the largest of the small half AND smallest of the large half

### Step 2: Pick Your Heap

**Simple Rule:**
```
Need smallest fast? → MIN-heap (default in Java)
Need largest fast?  → MAX-heap (Collections.reverseOrder())
```

**The Tricky Part (Top K):**
```
Want to KEEP the K largest?  → Use MIN-heap (so you can kick out small ones)
Want to KEEP the K smallest? → Use MAX-heap (so you can kick out large ones)

WHY? The heap root is what gets REMOVED. 
     You want to remove the OPPOSITE of what you're keeping!
```

### Step 3: Code Pattern

**90% of heap problems use one of these:**

```java
// Pattern 1: Top K (most common!)
for (item : items) {
    heap.offer(item);
    if (heap.size() > k) heap.poll();  // Kick out unwanted
}
return heap.peek();  // or heap contents

// Pattern 2: Process in order (merge, BFS)
heap.offer(startingItems);
while (!heap.isEmpty()) {
    best = heap.poll();
    process(best);
    if (best.hasNext()) heap.offer(best.next());
}

// Pattern 3: Two heaps (median)
// Left half: max-heap, Right half: min-heap
// Always rebalance after adding
```

### Step 4: The One Question That Solves Everything

> **"If my heap is full and a new element arrives, which element should LEAVE?"**

Your answer tells you which heap to use:
- "The smallest should leave" → MIN-heap (smallest at root, easy to remove)
- "The largest should leave" → MAX-heap (largest at root, easy to remove)

--

## 📖 Now the Detailed Template (For Deep Understanding)

--

## Question 1: WHAT IS "BEST"?

**The Core Question:** What property makes one element "better" than another?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    DEFINING "BEST"                                   │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Problem Says              │  "Best" Means           │  Example      │
│  ─────────────────────────┼─────────────────────────┼───────────────│
│  "Kth largest"            │  Smallest (to kick out) │  LC 215       │
│  "Kth smallest"           │  Largest (to kick out)  │  LC 378       │
│  "Top K frequent"         │  Lowest freq (kick out) │  LC 347       │
│  "K closest"              │  Farthest (to kick out) │  LC 973       │
│  "Merge K sorted"         │  Smallest value         │  LC 23        │
│  "Median"                 │  Max of left, min of rt │  LC 295       │
│  "Task scheduler"         │  Highest frequency      │  LC 621       │
│  "Meeting rooms"          │  Earliest end time      │  LC 253       │
│                                                                      │
│  💡 KEY INSIGHT:                                                     │
│  For "Top K" problems, "best" is what you want to KICK OUT!         │
│  The heap root is what gets removed when full.                      │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Mental Exercise

Before coding, complete this sentence:
> "I need quick access to the element with the _______ [property] because I want to _______ [action]."

**Examples:**
- "I need quick access to the element with the **smallest value** because I want to **kick it out and keep large ones**."
- "I need quick access to the element with the **earliest end time** because I want to **reuse that room first**."

--

## Question 2: WHICH HEAP TYPE?

**The Decision Tree:**

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HEAP TYPE DECISION TREE                           │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│                    What do you need at the ROOT?                     │
│                              │                                       │
│              ┌───────────────┴───────────────┐                       │
│              ▼                               ▼                       │
│         SMALLEST                         LARGEST                     │
│              │                               │                       │
│              ▼                               ▼                       │
│      ┌──────────────┐               ┌──────────────┐                │
│      │  MIN-HEAP    │               │  MAX-HEAP    │                │
│      │  (default)   │               │ reverseOrder │                │
│      └──────────────┘               └──────────────┘                │
│                                                                      │
│  ════════════════════════════════════════════════════════════════   │
│                                                                      │
│  🔄 THE COUNTERINTUITIVE FLIP (Top K Problems):                     │
│                                                                      │
│     ┌─────────────────────────────────────────────────────────┐     │
│     │                                                         │     │
│     │   Want K LARGEST?  →  Use MIN-heap (kicks small)       │     │
│     │   Want K SMALLEST? →  Use MAX-heap (kicks large)       │     │
│     │   Want K CLOSEST?  →  Use MAX-heap (kicks far)         │     │
│     │   Want K FARTHEST? →  Use MIN-heap (kicks close)       │     │
│     │                                                         │     │
│     │   RULE: Use OPPOSITE heap to kick out the UNWANTED!    │     │
│     │                                                         │     │
│     └─────────────────────────────────────────────────────────┘     │
│                                                                      │
│  ════════════════════════════════════════════════════════════════   │
│                                                                      │
│  🎭 TWO HEAPS (Median / Split Problems):                            │
│                                                                      │
│     ┌─────────────────────────────────────────────────────────┐     │
│     │                                                         │     │
│     │   LEFT HALF (smaller values)  →  MAX-heap              │     │
│     │   RIGHT HALF (larger values)  →  MIN-heap              │     │
│     │                                                         │     │
│     │   WHY? We need the LARGEST of left (max-heap)          │     │
│     │        and SMALLEST of right (min-heap)                │     │
│     │        They meet at the MEDIAN!                        │     │
│     │                                                         │     │
│     └─────────────────────────────────────────────────────────┘     │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### The VIP Room Analogy (For Top K)

```
Imagine a VIP room with only K spots:

┌─────────────────────────────────────────────────────────────────────┐
│                         VIP ROOM (K spots)                           │
│                                                                      │
│   For "K LARGEST" (using MIN-heap):                                 │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │  [8] [9] [10]  ← The K largest values                       │   │
│   │   ↑                                                         │   │
│   │   BOUNCER (min-heap root = smallest VIP = 8)                │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
│   New person arrives with value 7:                                  │
│   - Bouncer checks: 7 < 8? YES → "Sorry, not VIP enough!"          │
│   - 7 is rejected, room unchanged                                   │
│                                                                      │
│   New person arrives with value 11:                                 │
│   - Bouncer checks: 11 > 8? YES → "Welcome! But someone must go!"  │
│   - Bouncer (8) leaves, 11 enters                                   │
│   - New room: [9] [10] [11], new bouncer = 9                        │
│                                                                      │
│   The bouncer (min-heap root) is always the Kth largest!            │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

--

## Question 3: WHAT GOES IN THE HEAP?

**The Data Structure Question:** What information do I need to store?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HEAP ENTRY DESIGN                                 │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Problem Type          │  Heap Entry          │  Comparator          │
│  ──────────────────────┼──────────────────────┼──────────────────────│
│  Simple values         │  Integer             │  natural order       │
│  With index            │  int[]{val, idx}     │  compare by val      │
│  With frequency        │  int[]{val, freq}    │  compare by freq     │
│  Points/distance       │  int[]{x, y}         │  compare by dist     │
│  Linked list nodes     │  ListNode            │  compare by val      │
│  Matrix cell           │  int[]{val, r, c}    │  compare by val      │
│  Task with cooldown    │  int[]{count, time}  │  compare by count    │
│  Interval/meeting      │  int[]{start, end}   │  compare by end      │
│                                                                      │
│  💡 RULE OF THUMB:                                                   │
│  Store everything you need to:                                       │
│  1. Compare elements (for heap ordering)                            │
│  2. Process the element (index, coordinates, next pointer)          │
│  3. Generate next candidates (for BFS-style problems)               │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Java Comparator Patterns

```java
// By first element (ascending) - MIN behavior
(a, b) -> a[0] - b[0]
(a, b) -> Integer.compare(a[0], b[0])  // SAFER!

// By first element (descending) - MAX behavior
(a, b) -> b[0] - a[0]
(a, b) -> Integer.compare(b[0], a[0])  // SAFER!

// By computed value (e.g., distance)
(a, b) -> (a[0]*a[0] + a[1]*a[1]) - (b[0]*b[0] + b[1]*b[1])

// By external map (e.g., frequency)
(a, b) -> freq.get(a) - freq.get(b)

// ⚠️ WARNING: a - b can OVERFLOW! Use Integer.compare for safety!
```

--

## Question 4: WHEN TO ADD / REMOVE?

**The Lifecycle Question:** When do elements enter and leave the heap?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HEAP LIFECYCLE PATTERNS                           │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  PATTERN A: "Bounded Heap" (Top K)                                  │
│  ─────────────────────────────────────────────────────────────────  │
│  for (element : all_elements) {                                     │
│      heap.offer(element);                                           │
│      if (heap.size() > K) heap.poll();  // Kick out unwanted        │
│  }                                                                   │
│  // Heap now contains exactly K "best" elements                     │
│                                                                      │
│  ════════════════════════════════════════════════════════════════   │
│                                                                      │
│  PATTERN B: "Process All" (K-Way Merge)                             │
│  ─────────────────────────────────────────────────────────────────  │
│  // Initialize with starting candidates                             │
│  for (source : sources) heap.offer(source.head);                    │
│                                                                      │
│  while (!heap.isEmpty()) {                                          │
│      best = heap.poll();           // Process best                  │
│      process(best);                                                 │
│      if (best.hasNext()) {                                          │
│          heap.offer(best.next);    // Add next candidate            │
│      }                                                               │
│  }                                                                   │
│                                                                      │
│  ════════════════════════════════════════════════════════════════   │
│                                                                      │
│  PATTERN C: "Two Heaps" (Median)                                    │
│  ─────────────────────────────────────────────────────────────────  │
│  for (element : stream) {                                           │
│      // Add to appropriate heap                                     │
│      if (element <= left.peek()) left.offer(element);               │
│      else right.offer(element);                                     │
│                                                                      │
│      // Rebalance                                                   │
│      if (left.size() > right.size() + 1) right.offer(left.poll()); │
│      if (right.size() > left.size()) left.offer(right.poll());     │
│  }                                                                   │
│                                                                      │
│  ════════════════════════════════════════════════════════════════   │
│                                                                      │
│  PATTERN D: "Greedy with Cooldown" (Scheduling)                     │
│  ─────────────────────────────────────────────────────────────────  │
│  while (!heap.isEmpty() || !cooldown.isEmpty()) {                   │
│      // Release from cooldown                                       │
│      if (cooldown ready) heap.offer(cooldown.poll());               │
│                                                                      │
│      // Process best available                                      │
│      if (!heap.isEmpty()) {                                         │
│          task = heap.poll();                                        │
│          process(task);                                             │
│          if (task.remaining > 0) cooldown.offer(task);              │
│      }                                                               │
│  }                                                                   │
│                                                                      │
│  ════════════════════════════════════════════════════════════════   │
│                                                                      │
│  PATTERN E: "Lazy Deletion" (Sliding Window)                        │
│  ─────────────────────────────────────────────────────────────────  │
│  // Don't remove immediately, just mark                             │
│  toRemove.put(outgoing, toRemove.get(outgoing) + 1);                │
│                                                                      │
│  // Clean up when element reaches top                               │
│  while (toRemove.contains(heap.peek())) {                           │
│      toRemove.remove(heap.poll());                                  │
│  }                                                                   │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

--

## 🎯 WORKED EXAMPLE: How a Junior Dev Should Think

Let's solve a problem step-by-step using the template:

**Problem:** "Find the K closest points to the origin."

### My Thinking Process:

**Step 1: What do I need FAST?**
> "I need to find K closest points. So I need to track which points are close and which are far."

**Step 2: If heap is full and new point arrives, which should LEAVE?**
> "If I have K points and a new closer point arrives, I should kick out the FARTHEST point to make room."

**Step 3: So which heap?**
> "I need to kick out the farthest. So I need the farthest at the root. That's a MAX-heap by distance!"

**Step 4: What goes in the heap?**
> "The points themselves. I compare them by distance to origin."

**Step 5: Write the code:**
```java
int[][] kClosest(int[][] points, int k) {
    // MAX-heap by distance (farthest at top, easy to kick out)
    PriorityQueue<int[]> heap = new PriorityQueue<>(
        (a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1])
    );
    
    for (int[] point : points) {
        heap.offer(point);
        if (heap.size() > k) {
            heap.poll();  // Kick out farthest
        }
    }
    
    return heap.toArray(new int[k][]);
}
```

### The Key Realization:

```
┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│   "K closest" means I want to KEEP close ones.                      │
│                                                                      │
│   To KEEP close ones, I need to KICK OUT far ones.                  │
│                                                                      │
│   To KICK OUT far ones easily, I need far ones at the ROOT.         │
│                                                                      │
│   Far ones at root = MAX-heap by distance.                          │
│                                                                      │
│   ∴ K closest → MAX-heap by distance                                │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Another Example: "Kth Largest Element"

**My Thinking:**
1. "Kth largest" = I want the K largest elements
2. If heap is full and bigger element arrives, kick out the SMALLEST
3. Smallest at root = MIN-heap
4. After processing all, the root (smallest among K largest) = Kth largest!

```java
int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();  // MIN-heap
    for (int num : nums) {
        heap.offer(num);
        if (heap.size() > k) heap.poll();  // Kick smallest
    }
    return heap.peek();  // Smallest among K largest = Kth largest
}
```

--

## Question 5: EDGE CASES?

**The Safety Question:** What can go wrong?

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HEAP EDGE CASES CHECKLIST                         │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  □ EMPTY INPUT                                                       │
│    - What if array is empty?                                        │
│    - What if all lists are null?                                    │
│    - Return empty result or throw?                                  │
│                                                                      │
│  □ K BOUNDARIES                                                      │
│    - k = 0 → usually return empty                                   │
│    - k = 1 → answer is max/min                                      │
│    - k = n → answer is min/max (opposite)                           │
│    - k > n → handle gracefully                                      │
│                                                                      │
│  □ DUPLICATES                                                        │
│    - Heap handles duplicates naturally                              │
│    - But watch out for "unique" requirements                        │
│                                                                      │
│  □ COMPARATOR OVERFLOW                                               │
│    - ❌ (a, b) -> a - b  // Can overflow!                           │
│    - ✅ (a, b) -> Integer.compare(a, b)  // Safe!                   │
│                                                                      │
│  □ EMPTY HEAP OPERATIONS                                             │
│    - peek() on empty → returns null (not exception)                 │
│    - poll() on empty → returns null (not exception)                 │
│    - Always check isEmpty() if input can be empty                   │
│                                                                      │
│  □ INTEGER OVERFLOW IN CALCULATIONS                                  │
│    - Distance: x*x + y*y can overflow for large coordinates         │
│    - Use long: (long)x*x + (long)y*y                                │
│                                                                      │
│  □ MEDIAN AVERAGE OVERFLOW                                           │
│    - ❌ (left.peek() + right.peek()) / 2  // Can overflow!          │
│    - ✅ left.peek() + (right.peek() - left.peek()) / 2.0            │
│    - ✅ ((double)left.peek() + right.peek()) / 2.0                  │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

--

## The Complete Mental Walkthrough

Before coding ANY heap problem, fill in this template:

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HEAP PROBLEM ANALYSIS                             │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  Problem: _________________________________________________         │
│                                                                      │
│  1. WHAT IS "BEST"?                                                 │
│     I need quick access to: _______________________________         │
│     Because I want to: ____________________________________         │
│                                                                      │
│  2. WHICH HEAP?                                                      │
│     □ Min-heap (need smallest)                                      │
│     □ Max-heap (need largest)                                       │
│     □ OPPOSITE for Top K: ______ heap                               │
│     □ Two heaps: left=______, right=______                          │
│                                                                      │
│  3. WHAT GOES IN?                                                    │
│     Entry type: ___________________________________________         │
│     Comparator: ___________________________________________         │
│                                                                      │
│  4. LIFECYCLE?                                                       │
│     □ Bounded (maintain size K)                                     │
│     □ Process all (poll and push next)                              │
│     □ Two heaps (add and rebalance)                                 │
│     □ Greedy with cooldown                                          │
│     □ Lazy deletion                                                 │
│                                                                      │
│  5. EDGE CASES?                                                      │
│     □ Empty input: _______________________________________          │
│     □ k boundaries: ______________________________________          │
│     □ Overflow: __________________________________________          │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

--

# TOP K FAMILY

--

# PATTERN 0: Kth Largest Element in Array (LeetCode 215)

## Pattern Recognition Signal

**When you see:** "Kth largest", "Kth smallest", "find K-th element"

**Instant thought:** "Opposite heap of size K! Kth LARGEST → MIN-heap!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [3, 2, 1, 5, 6, 4], k = 2

Find the 2nd largest element.

Sorted: [1, 2, 3, 4, 5, 6]
                      ↑
                2nd largest = 5
```

### Why Use a Heap?

```
BRUTE FORCE: Sort the array, return nums[n-k]
  Time: O(n log n)

SMART WAY: Use a heap of size K!
  Time: O(n log k) — much better when k << n!

The insight: We don't need to sort EVERYTHING.
We just need to track the K largest elements.
```

### Why MIN-heap for Kth LARGEST? (The Counter-Intuitive Part!)

```
Think of it as a "VIP Room" with K seats:

┌─────────────────────────────────────────────────────────┐
│                                                          │
│  VIP ROOM (capacity = K)                                │
│  ┌─────┬─────┐                                          │
│  │  5  │  6  │  ← Only K=2 people allowed               │
│  └─────┴─────┘                                          │
│     ↑                                                    │
│  BOUNCER (min-heap root)                                │
│  "I'm the smallest VIP. Anyone smaller than me          │
│   doesn't get in. Anyone bigger kicks ME out."          │
│                                                          │
└─────────────────────────────────────────────────────────┘

The bouncer (min-heap root) is always the SMALLEST person in VIP.
After processing everyone, the bouncer = Kth largest overall!

Why? Because there are exactly K-1 people LARGER than the bouncer
     (the other VIPs), making the bouncer the Kth largest!
```

### The Algorithm in Plain English

```
1. Create a min-heap (VIP room)
2. For each number:
   - Add it to the heap (try to enter VIP)
   - If heap size > K: remove the smallest (bouncer kicks someone out)
3. At the end, heap root = Kth largest
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [3, 2, 1, 5, 6, 4]`, `k = 2`

```
Goal: Find 2nd largest (answer should be 5)

═══════════════════════════════════════════════════════════

Process 3:
  Heap: [] → add 3 → [3]
  Size = 1 ≤ k=2, keep all
  
  Heap state: [3]
  "3 enters VIP room"

═══════════════════════════════════════════════════════════

Process 2:
  Heap: [3] → add 2 → [2, 3]  (min-heap: 2 bubbles to top)
  Size = 2 ≤ k=2, keep all
  
  Heap state: [2, 3]
  "2 enters VIP room, becomes bouncer (smallest)"

═══════════════════════════════════════════════════════════

Process 1:
  Heap: [2, 3] → add 1 → [1, 3, 2]
  Size = 3 > k=2, kick smallest!
  Poll 1 → [2, 3]
  
  Heap state: [2, 3]
  "1 tries to enter but bouncer (2) says: You're smaller than me, GET OUT!"

═══════════════════════════════════════════════════════════

Process 5:
  Heap: [2, 3] → add 5 → [2, 3, 5]
  Size = 3 > k=2, kick smallest!
  Poll 2 → [3, 5]
  
  Heap state: [3, 5]
  "5 enters, kicks out bouncer (2). New bouncer is 3."

═══════════════════════════════════════════════════════════

Process 6:
  Heap: [3, 5] → add 6 → [3, 5, 6]
  Size = 3 > k=2, kick smallest!
  Poll 3 → [5, 6]
  
  Heap state: [5, 6]
  "6 enters, kicks out bouncer (3). New bouncer is 5."

═══════════════════════════════════════════════════════════

Process 4:
  Heap: [5, 6] → add 4 → [4, 6, 5]
  Size = 3 > k=2, kick smallest!
  Poll 4 → [5, 6]
  
  Heap state: [5, 6]
  "4 tries to enter but bouncer (5) says: You're smaller than me, GET OUT!"

═══════════════════════════════════════════════════════════

Final heap: [5, 6]
peek() = 5 = 2nd largest ✓

The bouncer (5) is the smallest among the K=2 largest elements.
That makes 5 the Kth largest!
```

--

## The Code (With Line-by-Line Explanation)

```java
int findKthLargest(int[] nums, int k) {
    // Min-heap: smallest at top (the "bouncer")
    PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    
    for (int num : nums) {
        minHeap.offer(num);              // Everyone tries to enter VIP room
        if (minHeap.size() > k) {
            minHeap.poll();              // Bouncer kicks out the smallest
        }
    }
    
    // Smallest VIP = Kth largest overall
    return minHeap.peek();
}
```

--

## Why O(n log k)?

```
For each of n elements:
  - offer(): O(log k) — heap has at most k elements
  - poll(): O(log k) — heap has at most k elements

Total: O(n log k)

Compare to sorting: O(n log n)
When k << n, this is MUCH better!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using MAX-heap | Would need to poll k times | Use MIN-heap size k |
| Forgetting size check | Heap grows unbounded | Check `size > k` after each add |
| Using wrong heap type | Java default is min-heap | For max-heap: `Collections.reverseOrder()` |

--

## Mind-Map Anchor

```
KTH LARGEST
     │
     ▼
┌─────────────────────────┐
│ MIN-heap of size K      │
│ "VIP Room" analogy      │
│ Bouncer = smallest VIP  │
│ Bouncer = Kth largest   │
│ O(n log k) time         │
└─────────────────────────┘
```

**Memory phrase:** "Kth LARGEST → MIN-heap size K → bouncer is the answer"

--

# PATTERN 1: Top K Frequent Elements (LeetCode 347)

## Pattern Recognition Signal

**When you see:** "top K frequent", "K most common", "K elements with highest count"

**Instant thought:** "Two steps: Count with HashMap, then Top K with min-heap by frequency!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [1, 1, 1, 2, 2, 3], k = 2

Find the 2 most frequent elements.

Frequencies:
  1 appears 3 times
  2 appears 2 times
  3 appears 1 time

Top 2 frequent: [1, 2]
```

### Why Two Steps?

```
Step 1: COUNT frequencies
  → Use HashMap: {1: 3, 2: 2, 3: 1}

Step 2: Find TOP K by frequency
  → Use min-heap of size K, ordered by frequency
  → Same "VIP room" concept, but VIP status = frequency!
```

### Why MIN-heap for TOP K?

```
Same logic as Kth Largest!

VIP Room (capacity K=2), ordered by frequency:

Elements try to enter based on their frequency.
Bouncer is the element with LOWEST frequency in VIP.
After processing all, VIP room has K most frequent!
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [1, 1, 1, 2, 2, 3]`, `k = 2`

```
Step 1: Build frequency map

Process [1, 1, 1, 2, 2, 3]:
  1 → count = 3
  2 → count = 2
  3 → count = 1

freqMap = {1: 3, 2: 2, 3: 1}

═══════════════════════════════════════════════════════════

Step 2: Find top K using min-heap (ordered by frequency)

Process entry (1, freq=3):
  Heap: [] → add 1 → [(1, 3)]
  Size = 1 ≤ k=2, keep
  
  Heap: [(1, freq=3)]

Process entry (2, freq=2):
  Heap: [(1, 3)] → add 2 → [(2, 2), (1, 3)]
  (Min-heap by freq: 2 has lower freq, goes to top)
  Size = 2 ≤ k=2, keep
  
  Heap: [(2, freq=2), (1, freq=3)]

Process entry (3, freq=1):
  Heap: [(2, 2), (1, 3)] → add 3 → [(3, 1), (1, 3), (2, 2)]
  Size = 3 > k=2, kick lowest frequency!
  Poll (3, freq=1)
  
  Heap: [(2, freq=2), (1, freq=3)]
  "3 (freq=1) kicked out because freq < bouncer's freq (2)"

═══════════════════════════════════════════════════════════

Final heap: [(2, freq=2), (1, freq=3)]
Extract elements: [1, 2] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int[] topKFrequent(int[] nums, int k) {
    // Step 1: Count frequencies
    Map<Integer, Integer> freq = new HashMap<>();
    for (int num : nums) {
        freq.put(num, freq.getOrDefault(num, 0) + 1);
    }
    
    // Step 2: Min-heap ordered by frequency
    // Heap stores the NUMBER, compares by its FREQUENCY
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(
        (a, b) -> freq.get(a) - freq.get(b)  // Compare by frequency!
    );
    
    for (int num : freq.keySet()) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();  // Kick out lowest frequency
        }
    }
    
    // Extract results
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
        result[i] = minHeap.poll();
    }
    return result;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Storing frequency in heap | Need to return elements, not frequencies | Store elements, compare by freq |
| Wrong comparator | `b - a` gives max-heap | `a - b` for min-heap |
| Forgetting HashMap step | Can't know frequencies | Always count first |

--

## Mind-Map Anchor

```
TOP K FREQUENT
       │
       ▼
┌─────────────────────────┐
│ Step 1: HashMap (count) │
│ Step 2: Min-heap size K │
│ Compare by FREQUENCY    │
│ Store ELEMENTS          │
└─────────────────────────┘
```

**Memory phrase:** "Count with map, top K with min-heap by frequency"

--

| Question | Answer |
|-----|----|
| 1. What is "best"? | Lowest frequency (to kick out) |
| 2. Which heap? | MIN-heap by frequency (keep high freq) |
| 3. What goes in? | The element (compare by its frequency) |
| 4. When add/remove? | Add unique elements, remove when size > K |
| 5. Edge cases? | All same frequency, k = unique count |

## The Code

```java
int[] topKFrequent(int[] nums, int k) {
    // Step 1: Count frequencies
    Map<Integer, Integer> freq = new HashMap<>();
    for (int num : nums) {
        freq.put(num, freq.getOrDefault(num, 0) + 1);
    }
    
    // Step 2: Min-heap by frequency (lowest freq at top = gets kicked)
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(
        (a, b) -> freq.get(a) - freq.get(b)  // Compare by frequency
    );
    
    for (int num : freq.keySet()) {
        minHeap.offer(num);
        if (minHeap.size() > k) {
            minHeap.poll();  // Kick out least frequent
        }
    }
    
    // Step 3: Extract results
    int[] result = new int[k];
    for (int i = 0; i < k; i++) {
        result[i] = minHeap.poll();
    }
    return result;
}
```

## Visual Dry Run

**Input:** `nums = [1,1,1,2,2,3]`, `k = 2`

```
Step 1: Count frequencies
freq = {1: 3, 2: 2, 3: 1}

Step 2: Min-heap by frequency (size limit = 2)

Add 1 (freq=3): [1]
Add 2 (freq=2): [2, 1]       (2 has lower freq, goes to top)
Add 3 (freq=1): [3, 1, 2]    size > 2, kick lowest freq
                → kick 3 (freq=1) → [2, 1]

Final heap: [2, 1] (elements with freq 2 and 3)
Result: [1, 2] ✓
```

## The "Aha!" Line

> "Count first, then min-heap by frequency. Low frequency gets kicked, high frequency survives."

## Mind-Map Anchor

**HashMap freq · min-heap by freq · kick low freq**

--

# PATTERN 2: K Closest Points to Origin (LeetCode 973)

## Pattern Recognition Signal

**When you see:** "K closest points", "K nearest neighbors", "K smallest distances"

**Instant thought:** "MAX-heap by distance, size K! Kick farthest, keep closest!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: points = [[1,3], [-2,2], [5,-1]], k = 2

Find the 2 closest points to origin (0,0).

Distances:
  [1,3]:  √(1² + 3²) = √10 ≈ 3.16
  [-2,2]: √(4 + 4)   = √8  ≈ 2.83  ← closer!
  [5,-1]: √(25 + 1)  = √26 ≈ 5.10

K=2 closest: [[-2,2], [1,3]]
```

### Real-World Analogy: The "Close Friends" List

```
Imagine you have a "Close Friends" list with only K spots:

┌─────────────────────────────────────────────────────────────────────┐
│                    CLOSE FRIENDS LIST (K spots)                      │
│                                                                      │
│   You want to keep the K CLOSEST friends.                           │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │  Friend A (2 miles)   Friend B (3 miles)                    │   │
│   │                  ↑                                          │   │
│   │            BOUNCER (max-heap root = FARTHEST friend = 3mi)  │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
│   New friend arrives (1 mile away):                                 │
│   - Bouncer checks: 1 < 3? YES → "You're closer! Come in!"         │
│   - Bouncer (3 miles) leaves, new friend (1 mile) enters           │
│                                                                      │
│   New friend arrives (5 miles away):                                │
│   - Bouncer checks: 5 > 3? YES → "Sorry, too far!"                 │
│   - 5-mile friend rejected                                          │
│                                                                      │
│   The bouncer (max-heap root) is always the FARTHEST in the list!  │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why MAX-heap for K CLOSEST? (The Counter-Intuitive Part!)

```
The KEY insight:

  "K closest" means I want to KEEP close ones.
  
  To KEEP close ones, I need to KICK OUT far ones.
  
  To KICK OUT far ones easily, I need far ones at the ROOT.
  
  Far ones at root = MAX-heap by distance.
  
  ∴ K closest → MAX-heap by distance!

This is the OPPOSITE of what you might first think!
```

### The Algorithm in Plain English

```
1. Create a MAX-heap ordered by distance (farthest at top)
2. For each point:
   - Add it to the heap
   - If heap size > K: remove the farthest (heap root)
3. At the end, heap contains K closest points
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `points = [[1,3], [-2,2], [5,-1]]`, `k = 2`

```
Goal: Find 2 closest points to origin

Distance formula: d² = x² + y²
(We compare d² to avoid sqrt — same ordering!)

═══════════════════════════════════════════════════════════

Process [1,3]:
  Distance² = 1² + 3² = 10
  Heap: [] → add [1,3] → [[1,3]]
  Size = 1 ≤ k=2, keep all
  
  Heap state: [[1,3] (d²=10)]
  "[1,3] enters close friends list"

═══════════════════════════════════════════════════════════

Process [-2,2]:
  Distance² = (-2)² + 2² = 8
  Heap: [[1,3]] → add [-2,2] → [[1,3], [-2,2]]
  Max-heap by distance: [1,3] (d²=10) at root (it's farther!)
  Size = 2 ≤ k=2, keep all
  
  Heap state: [[1,3] (d²=10), [-2,2] (d²=8)]
  "[-2,2] enters. [1,3] is bouncer (farthest)"

═══════════════════════════════════════════════════════════

Process [5,-1]:
  Distance² = 5² + (-1)² = 26
  Heap: [[1,3], [-2,2]] → add [5,-1] → [[5,-1], [1,3], [-2,2]]
  Max-heap reorders: [5,-1] (d²=26) bubbles to root
  Size = 3 > k=2, kick farthest!
  Poll [5,-1] (d²=26)
  
  Heap state: [[1,3] (d²=10), [-2,2] (d²=8)]
  "[5,-1] tries to enter but bouncer says: You're farther than me, GET OUT!"

═══════════════════════════════════════════════════════════

Final heap: [[1,3], [-2,2]]
These are the 2 closest points! ✓

Verification:
  [1,3]:  d² = 10 ✓
  [-2,2]: d² = 8  ✓
  [5,-1]: d² = 26 (kicked out, too far)
```

--

## The Code (With Line-by-Line Explanation)

```java
int[][] kClosest(int[][] points, int k) {
    // MAX-heap by distance (farthest at top = bouncer)
    // Compare by d² = x² + y² (no need for sqrt!)
    PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
        (a, b) -> (b[0]*b[0] + b[1]*b[1]) - (a[0]*a[0] + a[1]*a[1])
        //        ↑ b - a means LARGER distance at top (max-heap)
    );
    
    for (int[] point : points) {
        maxHeap.offer(point);              // Everyone tries to enter
        if (maxHeap.size() > k) {
            maxHeap.poll();                // Bouncer kicks out farthest
        }
    }
    
    // Heap now contains exactly K closest points
    return maxHeap.toArray(new int[k][]);
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using MIN-heap | Would kick out CLOSEST points! | Use MAX-heap by distance |
| Using sqrt for distance | Unnecessary computation | Compare d² directly |
| `a - b` comparator | That's min-heap! | Use `b - a` for max-heap |
| Integer overflow in distance | x² + y² can overflow | Use `(long)x*x + (long)y*y` for large coords |

--

## Mind-Map Anchor

```
K CLOSEST POINTS
       │
       ▼
┌─────────────────────────────┐
│ MAX-heap by distance        │
│ "Close Friends" analogy     │
│ Bouncer = farthest friend   │
│ Kick farthest, keep closest │
│ Compare d² (skip sqrt)      │
│ O(n log k) time             │
└─────────────────────────────┘
```

**Memory phrase:** "K CLOSEST → MAX-heap by distance → kick far, keep close"

--

# PATTERN 3: Kth Smallest Element in Sorted Matrix (LeetCode 378)

## Pattern Recognition Signal

**When you see:** "sorted matrix", "Kth smallest in matrix", "matrix where rows AND columns are sorted"

**Instant thought:** "Min-heap BFS from (0,0)! Expand right and down, poll K times!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: matrix = [[ 1,  5,  9],
                 [10, 11, 13],
                 [12, 13, 15]], k = 8

Find the 8th smallest element.

The matrix is sorted:
- Each row is sorted left to right
- Each column is sorted top to bottom

Flattened and sorted: [1, 5, 9, 10, 11, 12, 13, 13, 15]
                       1  2  3  4   5   6   7   8   9
                                               ↑
                                        8th smallest = 13
```

### Real-World Analogy: Exploring a Treasure Map

```
Imagine a treasure map where:
- You start at the TOP-LEFT corner (smallest treasure)
- Moving RIGHT or DOWN always leads to BIGGER treasures
- You want to find the Kth smallest treasure

┌─────────────────────────────────────────────────────────────────────┐
│                    TREASURE MAP (Sorted Matrix)                      │
│                                                                      │
│     START                                                            │
│       ↓                                                              │
│     [ 1 ] → [ 5 ] → [ 9 ]                                           │
│       ↓       ↓       ↓                                              │
│     [10 ] → [11 ] → [13 ]                                           │
│       ↓       ↓       ↓                                              │
│     [12 ] → [13 ] → [15 ]                                           │
│                                                                      │
│   From any cell, you can only go RIGHT or DOWN.                     │
│   Both directions lead to LARGER values!                            │
│                                                                      │
│   Strategy: Use a min-heap to always explore the                    │
│   SMALLEST unexplored cell next. Poll K times!                      │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Min-Heap BFS Works

```
Key insight: The Kth smallest element is the Kth element
             we would encounter if we explored in sorted order!

From any cell (r, c), the next candidates are:
  - (r, c+1) — the cell to the RIGHT
  - (r+1, c) — the cell BELOW

Both are guaranteed to be ≥ current cell (matrix is sorted!)

Min-heap ensures we always process the SMALLEST candidate next.
After K polls, we've found the Kth smallest!
```

### The Algorithm in Plain English

```
1. Start at (0,0) — the smallest element
2. Add (0,0) to min-heap
3. Repeat K times:
   a. Poll the smallest from heap (this is the next smallest overall!)
   b. Add its RIGHT neighbor (if exists and not visited)
   c. Add its DOWN neighbor (if exists and not visited)
4. The Kth polled element is the answer!
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `matrix = [[1,5,9], [10,11,13], [12,13,15]]`, `k = 5`

```
Matrix visualization:
     col 0   col 1   col 2
row 0:  1  →   5  →   9
        ↓      ↓      ↓
row 1: 10  →  11  →  13
        ↓      ↓      ↓
row 2: 12  →  13  →  15

═══════════════════════════════════════════════════════════

Initial State:
  Heap: [(1, row=0, col=0)]
  Visited: {(0,0)}
  Count: 0

═══════════════════════════════════════════════════════════

Poll #1:
  Poll (1, 0, 0) → count = 1
  "1 is the 1st smallest!"
  
  Expand RIGHT: (0,1) = 5 → add to heap
  Expand DOWN:  (1,0) = 10 → add to heap
  
  Heap: [(5, 0, 1), (10, 1, 0)]
  Visited: {(0,0), (0,1), (1,0)}

═══════════════════════════════════════════════════════════

Poll #2:
  Poll (5, 0, 1) → count = 2
  "5 is the 2nd smallest!"
  
  Expand RIGHT: (0,2) = 9 → add to heap
  Expand DOWN:  (1,1) = 11 → add to heap
  
  Heap: [(9, 0, 2), (10, 1, 0), (11, 1, 1)]
  Visited: {(0,0), (0,1), (1,0), (0,2), (1,1)}

═══════════════════════════════════════════════════════════

Poll #3:
  Poll (9, 0, 2) → count = 3
  "9 is the 3rd smallest!"
  
  Expand RIGHT: out of bounds
  Expand DOWN:  (1,2) = 13 → add to heap
  
  Heap: [(10, 1, 0), (11, 1, 1), (13, 1, 2)]

═══════════════════════════════════════════════════════════

Poll #4:
  Poll (10, 1, 0) → count = 4
  "10 is the 4th smallest!"
  
  Expand RIGHT: (1,1) already visited
  Expand DOWN:  (2,0) = 12 → add to heap
  
  Heap: [(11, 1, 1), (12, 2, 0), (13, 1, 2)]

═══════════════════════════════════════════════════════════

Poll #5:
  Poll (11, 1, 1) → count = 5 = k
  "11 is the 5th smallest!" ← ANSWER!
  
  Return 11 ✓

═══════════════════════════════════════════════════════════

Verification:
  Sorted order: 1, 5, 9, 10, 11, ...
                1  2  3   4   5
                            ↑
                    5th smallest = 11 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int kthSmallest(int[][] matrix, int k) {
    int n = matrix.length;
    
    // Min-heap stores: [value, row, col]
    // Ordered by value (smallest at top)
    PriorityQueue<int[]> minHeap = new PriorityQueue<>(
        (a, b) -> a[0] - b[0]  // Compare by value
    );
    
    // Track visited cells to avoid duplicates
    boolean[][] visited = new boolean[n][n];
    
    // Start from top-left (smallest element)
    minHeap.offer(new int[]{matrix[0][0], 0, 0});
    visited[0][0] = true;
    
    int count = 0;
    while (!minHeap.isEmpty()) {
        int[] curr = minHeap.poll();
        int val = curr[0], row = curr[1], col = curr[2];
        
        count++;
        if (count == k) return val;  // Found Kth smallest!
        
        // Expand RIGHT (if valid and not visited)
        if (col + 1 < n && !visited[row][col + 1]) {
            minHeap.offer(new int[]{matrix[row][col + 1], row, col + 1});
            visited[row][col + 1] = true;
        }
        
        // Expand DOWN (if valid and not visited)
        if (row + 1 < n && !visited[row + 1][col]) {
            minHeap.offer(new int[]{matrix[row + 1][col], row + 1, col});
            visited[row + 1][col] = true;
        }
    }
    
    return -1;  // Should never reach here if k is valid
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting visited array | Same cell added multiple times | Track visited cells |
| Only expanding one direction | Miss valid candidates | Expand BOTH right and down |
| Returning after first poll | That's just the minimum | Poll K times |
| Using max-heap | Would give largest first | Use min-heap for smallest |

--

## Mind-Map Anchor

```
KTH SMALLEST IN SORTED MATRIX
              │
              ▼
┌─────────────────────────────────┐
│ Start at (0,0) — the minimum    │
│ Min-heap BFS exploration        │
│ Expand: RIGHT and DOWN          │
│ Track visited to avoid dupes    │
│ Poll K times → Kth smallest     │
│ O(k log k) time, O(k) space     │
└─────────────────────────────────┘
```

**Memory phrase:** "Sorted matrix → min-heap BFS → expand right & down → poll K times"

--

# K-WAY MERGE FAMILY

--

# PATTERN 4: Merge K Sorted Lists (LeetCode 23)

## Pattern Recognition Signal

**When you see:** "merge K sorted lists/arrays", "combine K sorted streams", "K-way merge"

**Instant thought:** "Min-heap of K heads! Poll smallest, push its next!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: 3 sorted linked lists
  L1: 1 → 4 → 7
  L2: 2 → 5 → 8
  L3: 3 → 6 → 9

Output: One merged sorted list
  1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9
```

### Real-World Analogy: The Tournament Bracket

```
Imagine a tournament where K teams compete:

┌─────────────────────────────────────────────────────────────────────┐
│                    K-WAY MERGE TOURNAMENT                            │
│                                                                      │
│   Each "team" is a sorted list.                                     │
│   Each team sends their SMALLEST player (head) to compete.          │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │                    ARENA (Min-Heap)                         │   │
│   │                                                             │   │
│   │   Team 1 sends: 1    Team 2 sends: 2    Team 3 sends: 3    │   │
│   │                                                             │   │
│   │   Min-heap picks WINNER: 1 (smallest!)                      │   │
│   │                                                             │   │
│   │   → 1 goes to output                                        │   │
│   │   → Team 1 sends next player: 4                             │   │
│   │                                                             │   │
│   │   Arena now: [2, 3, 4]                                      │   │
│   │   Next winner: 2                                            │   │
│   │   ...and so on...                                           │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
│   The min-heap is the "arena" that always picks the smallest!       │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why This Approach Works

```
Key insight: At any moment, the GLOBAL minimum must be 
             one of the K current heads!

Why? Because each list is sorted, so the smallest element
     in each list is at its head. The global minimum is
     the smallest among these K heads.

Min-heap of K heads:
  - Always gives us the global minimum in O(log K)
  - After removing it, we add the next element from that list
  - Heap size stays at most K (one head per list)
```

### The Algorithm in Plain English

```
1. Add the HEAD of each non-empty list to a min-heap
2. While heap is not empty:
   a. Poll the smallest node (this is the next in merged order!)
   b. Add it to the result list
   c. If that node has a .next, push .next to the heap
3. Return the merged list
```

--

## Visual Dry Run (Step-by-Step)

**Input:** 
```
L1: 1 → 4 → 7
L2: 2 → 5 → 8
L3: 3 → 6 → 9
```

```
═══════════════════════════════════════════════════════════

Initial State:
  Add all heads to heap
  Heap: [1(L1), 2(L2), 3(L3)]  (min-heap: 1 at top)
  Output: (empty)

═══════════════════════════════════════════════════════════

Step 1:
  Poll 1 (from L1) → Output: 1
  L1's next is 4 → push 4 to heap
  
  Heap: [2(L2), 3(L3), 4(L1)]
  Output: 1

═══════════════════════════════════════════════════════════

Step 2:
  Poll 2 (from L2) → Output: 1 → 2
  L2's next is 5 → push 5 to heap
  
  Heap: [3(L3), 4(L1), 5(L2)]
  Output: 1 → 2

═══════════════════════════════════════════════════════════

Step 3:
  Poll 3 (from L3) → Output: 1 → 2 → 3
  L3's next is 6 → push 6 to heap
  
  Heap: [4(L1), 5(L2), 6(L3)]
  Output: 1 → 2 → 3

═══════════════════════════════════════════════════════════

Step 4:
  Poll 4 (from L1) → Output: 1 → 2 → 3 → 4
  L1's next is 7 → push 7 to heap
  
  Heap: [5(L2), 6(L3), 7(L1)]
  Output: 1 → 2 → 3 → 4

═══════════════════════════════════════════════════════════

Step 5:
  Poll 5 (from L2) → Output: 1 → 2 → 3 → 4 → 5
  L2's next is 8 → push 8 to heap
  
  Heap: [6(L3), 7(L1), 8(L2)]
  Output: 1 → 2 → 3 → 4 → 5

═══════════════════════════════════════════════════════════

Step 6:
  Poll 6 (from L3) → Output: ... → 6
  L3's next is 9 → push 9 to heap
  
  Heap: [7(L1), 8(L2), 9(L3)]

═══════════════════════════════════════════════════════════

Step 7:
  Poll 7 (from L1) → Output: ... → 7
  L1's next is null → don't push anything
  
  Heap: [8(L2), 9(L3)]

═══════════════════════════════════════════════════════════

Step 8:
  Poll 8 (from L2) → Output: ... → 8
  L2's next is null → don't push anything
  
  Heap: [9(L3)]

═══════════════════════════════════════════════════════════

Step 9:
  Poll 9 (from L3) → Output: ... → 9
  L3's next is null → don't push anything
  
  Heap: [] (empty!)

═══════════════════════════════════════════════════════════

Final Output: 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
ListNode mergeKLists(ListNode[] lists) {
    // Min-heap ordered by node value (smallest at top)
    PriorityQueue<ListNode> minHeap = new PriorityQueue<>(
        (a, b) -> a.val - b.val
    );
    
    // Add all non-null heads to the heap
    for (ListNode head : lists) {
        if (head != null) {
            minHeap.offer(head);
        }
    }
    
    // Dummy node to build result list
    ListNode dummy = new ListNode(0);
    ListNode tail = dummy;  // Pointer to end of result
    
    while (!minHeap.isEmpty()) {
        // Poll the smallest node
        ListNode smallest = minHeap.poll();
        
        // Add to result
        tail.next = smallest;
        tail = tail.next;
        
        // If this node has a next, push it to heap
        if (smallest.next != null) {
            minHeap.offer(smallest.next);
        }
    }
    
    return dummy.next;  // Skip dummy, return actual head
}
```

--

## Why O(N log K)?

```
N = total number of nodes across all lists
K = number of lists

For each of N nodes:
  - poll(): O(log K) — heap has at most K elements
  - offer(): O(log K) — heap has at most K elements

Total: O(N log K)

Compare to naive approach (merge 2 at a time):
  O(N * K) — much worse!

The heap keeps size bounded to K, making each operation O(log K)!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting null check | NullPointerException on empty lists | Check `head != null` before adding |
| Not pushing .next | Only get first elements | Always push `smallest.next` if exists |
| Using max-heap | Would give largest first | Use min-heap (default in Java) |
| Modifying original lists | May cause issues | This approach is fine (just relinks) |

--

## Mind-Map Anchor

```
MERGE K SORTED LISTS
         │
         ▼
┌─────────────────────────────────┐
│ Min-heap of K heads             │
│ "Tournament" analogy            │
│ Poll smallest → add to result   │
│ Push its .next to heap          │
│ Heap size always ≤ K            │
│ O(N log K) time                 │
└─────────────────────────────────┘
```

**Memory phrase:** "K heads in heap → poll smallest → push its next → O(N log K)"

--

# PATTERN 5: Find K Pairs with Smallest Sums (LeetCode 373)

## Pattern Recognition Signal

**When you see:** "K pairs with smallest/largest sums", "two sorted arrays, find K best pairs", "Cartesian product with K smallest"

**Instant thought:** "Virtual sorted matrix! Min-heap BFS starting from first column!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums1 = [1, 7, 11], nums2 = [2, 4, 6], k = 3

Find 3 pairs (one from each array) with smallest sums.

All possible pairs and their sums:
  (1,2)=3   (1,4)=5   (1,6)=7
  (7,2)=9   (7,4)=11  (7,6)=13
  (11,2)=13 (11,4)=15 (11,6)=17

K=3 smallest: [(1,2), (1,4), (1,6)] with sums [3, 5, 7]
```

### Real-World Analogy: The Virtual Matrix

```
Think of it as a MATRIX where:
  - Rows = elements from nums1
  - Columns = elements from nums2
  - Cell (i,j) = nums1[i] + nums2[j]

┌─────────────────────────────────────────────────────────────────────┐
│                    VIRTUAL SUM MATRIX                                │
│                                                                      │
│              nums2[0]=2   nums2[1]=4   nums2[2]=6                    │
│                  j=0         j=1         j=2                         │
│              ┌─────────┬─────────┬─────────┐                        │
│   nums1[0]=1 │    3    │    5    │    7    │  i=0                   │
│   i=0        │  (1+2)  │  (1+4)  │  (1+6)  │                        │
│              ├─────────┼─────────┼─────────┤                        │
│   nums1[1]=7 │    9    │   11    │   13    │  i=1                   │
│   i=1        │  (7+2)  │  (7+4)  │  (7+6)  │                        │
│              ├─────────┼─────────┼─────────┤                        │
│   nums1[2]=11│   13    │   15    │   17    │  i=2                   │
│   i=2        │ (11+2)  │ (11+4)  │ (11+6)  │                        │
│              └─────────┴─────────┴─────────┘                        │
│                                                                      │
│   This matrix is "sorted":                                          │
│   - Each row increases left to right (nums2 is sorted)              │
│   - Each column increases top to bottom (nums1 is sorted)           │
│                                                                      │
│   Sound familiar? It's like Pattern 3 (Kth Smallest in Matrix)!     │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Start from First Column?

```
Key insight: The smallest sum is ALWAYS at (0,0)!
             (smallest from nums1 + smallest from nums2)

From (i, j), the next candidates are:
  - (i, j+1) — same row, next column (larger sum)

But wait! Unlike Pattern 3, we DON'T expand down from each cell.
Instead, we start with the ENTIRE first column in the heap!

Why? Because any cell (i, 0) could potentially lead to a small sum.
We expand RIGHT from each cell as we process it.

This avoids the need for a visited array!
```

### The Algorithm in Plain English

```
1. Add all cells in the FIRST COLUMN to min-heap: (i, 0) for all i
   (But limit to min(nums1.length, k) to avoid unnecessary work)
2. Repeat K times:
   a. Poll the smallest sum (i, j)
   b. Add pair (nums1[i], nums2[j]) to result
   c. If j+1 < nums2.length, push (i, j+1) to heap
3. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums1 = [1, 7, 11]`, `nums2 = [2, 4, 6]`, `k = 3`

```
Virtual matrix:
        j=0   j=1   j=2
i=0  [   3     5     7  ]
i=1  [   9    11    13  ]
i=2  [  13    15    17  ]

═══════════════════════════════════════════════════════════

Initial State:
  Add first column to heap (all j=0)
  
  Heap entries: (sum, i, j)
    (3, 0, 0)  ← 1+2=3
    (9, 1, 0)  ← 7+2=9
    (13, 2, 0) ← 11+2=13
  
  Heap (min at top): [(3,0,0), (9,1,0), (13,2,0)]
  Result: []

═══════════════════════════════════════════════════════════

Poll #1:
  Poll (3, 0, 0) → sum=3, pair=(nums1[0], nums2[0])=(1,2)
  Result: [(1,2)]
  
  Expand RIGHT: j+1=1 < 3, push (5, 0, 1) ← 1+4=5
  
  Heap: [(5,0,1), (9,1,0), (13,2,0)]

═══════════════════════════════════════════════════════════

Poll #2:
  Poll (5, 0, 1) → sum=5, pair=(nums1[0], nums2[1])=(1,4)
  Result: [(1,2), (1,4)]
  
  Expand RIGHT: j+1=2 < 3, push (7, 0, 2) ← 1+6=7
  
  Heap: [(7,0,2), (9,1,0), (13,2,0)]

═══════════════════════════════════════════════════════════

Poll #3:
  Poll (7, 0, 2) → sum=7, pair=(nums1[0], nums2[2])=(1,6)
  Result: [(1,2), (1,4), (1,6)]
  
  result.size() = 3 = k → DONE!

═══════════════════════════════════════════════════════════

Final Result: [(1,2), (1,4), (1,6)] ✓
Sums: [3, 5, 7] — the 3 smallest!
```

--

## The Code (With Line-by-Line Explanation)

```java
List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
    List<List<Integer>> result = new ArrayList<>();
    
    // Edge case: empty arrays
    if (nums1.length == 0 || nums2.length == 0) return result;
    
    // Min-heap: [sum, index_in_nums1, index_in_nums2]
    PriorityQueue<int[]> minHeap = new PriorityQueue<>(
        (a, b) -> a[0] - b[0]  // Compare by sum
    );
    
    // Initialize: add first column (all pairs with nums2[0])
    // Limit to min(nums1.length, k) — no need for more
    for (int i = 0; i < Math.min(nums1.length, k); i++) {
        minHeap.offer(new int[]{nums1[i] + nums2[0], i, 0});
    }
    
    // Extract K smallest pairs
    while (!minHeap.isEmpty() && result.size() < k) {
        int[] curr = minHeap.poll();
        int i = curr[1], j = curr[2];
        
        // Add this pair to result
        result.add(Arrays.asList(nums1[i], nums2[j]));
        
        // Expand RIGHT: move to next element in nums2
        if (j + 1 < nums2.length) {
            minHeap.offer(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
        }
    }
    
    return result;
}
```

--

## Why Only Expand Right (Not Down)?

```
This is the KEY insight that makes this problem tricky!

If we expanded both right AND down (like Pattern 3), we'd need
a visited array to avoid duplicates.

Instead, we start with the ENTIRE first column in the heap.
This means:
  - Every row is already "seeded" in the heap
  - We only need to expand RIGHT within each row
  - No duplicates possible!

Think of it as K parallel "streams" (one per row), and we're
doing a K-way merge of these streams!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Expanding both right and down | Duplicates without visited array | Only expand right, seed all rows |
| Not limiting initial heap size | Unnecessary work if k < nums1.length | Use `min(nums1.length, k)` |
| Forgetting empty array check | NullPointerException | Check lengths first |
| Using wrong indices | Off-by-one errors | Carefully track i and j |

--

## Mind-Map Anchor

```
K PAIRS WITH SMALLEST SUMS
            │
            ▼
┌─────────────────────────────────────┐
│ Virtual matrix: sum[i][j]           │
│ Seed heap with first column         │
│ Only expand RIGHT (not down!)       │
│ Like K-way merge of rows            │
│ No visited array needed             │
│ O(k log k) time                     │
└─────────────────────────────────────┘
```

**Memory phrase:** "Virtual matrix → seed first column → expand right only → K-way merge"

--

# PATTERN 6: Smallest Range Covering Elements from K Lists (LeetCode 632)

## Pattern Recognition Signal

**When you see:** "smallest range covering all lists", "range containing one element from each list", "minimum window across K sources"

**Instant thought:** "Min-heap + track max! Range = [heap.min, currentMax]. Advance min to shrink!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: 3 sorted lists
  List 0: [4, 10, 15, 24]
  List 1: [0, 9, 12, 20]
  List 2: [5, 18, 22, 30]

Find the smallest range [a, b] such that the range contains
at least one element from EACH list.

Example ranges:
  [0, 5]:  contains 4(L0), 0(L1), 5(L2) ✓ size=5
  [9, 15]: contains 10,15(L0), 9,12(L1), — (L2) ✗ missing L2!
  [20, 24]: contains 24(L0), 20(L1), 22(L2) ✓ size=4 ← smallest!
```

### Real-World Analogy: The TV Channel Surfing Problem

```
Imagine you have K TV channels, each showing a sequence of programs:

┌─────────────────────────────────────────────────────────────────────┐
│                    TV CHANNEL SURFING                                │
│                                                                      │
│   Channel 0: [4, 10, 15, 24]  (program times)                       │
│   Channel 1: [0, 9, 12, 20]                                         │
│   Channel 2: [5, 18, 22, 30]                                        │
│                                                                      │
│   You want to find the SMALLEST time window where you can           │
│   watch at least one program from EACH channel.                     │
│                                                                      │
│   Strategy:                                                          │
│   1. Start with the FIRST program from each channel                 │
│   2. Current window = [earliest program, latest program]            │
│   3. To shrink the window, advance the EARLIEST channel             │
│      (can't shrink by moving the latest — that would only expand!)  │
│   4. Keep track of the smallest window seen                         │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why This Approach Works

```
Key insight: We maintain K "pointers" (one per list).
             The range is [min_pointer, max_pointer].

To shrink the range, we can only move the MIN pointer forward!
  - Moving max backward? Can't — we'd lose coverage of that list.
  - Moving min forward? Yes! We advance to the next element in that list.

Min-heap gives us the minimum pointer in O(log K).
We track the maximum separately (just update when adding to heap).

When any list is exhausted, we stop — can't maintain coverage anymore.
```

### The Algorithm in Plain English

```
1. Initialize:
   - Add first element from each list to min-heap
   - Track the current maximum among these elements
   - Initial range = [heap.min, currentMax]

2. While we can continue:
   a. Poll the minimum (this is from some list i)
   b. If list i has more elements:
      - Push the next element from list i to heap
      - Update currentMax if needed
      - Update best range if [heap.min, currentMax] is smaller
   c. If list i is exhausted: STOP (can't maintain coverage)

3. Return the best range found
```

--

## Visual Dry Run (Step-by-Step)

**Input:**
```
List 0: [4, 10, 15, 24]
List 1: [0, 9, 12, 20]
List 2: [5, 18, 22, 30]
```

```
═══════════════════════════════════════════════════════════

Initial State:
  Add first element from each list:
    (4, list=0, idx=0)
    (0, list=1, idx=0)
    (5, list=2, idx=0)
  
  Heap (min at top): [(0,L1,0), (4,L0,0), (5,L2,0)]
  currentMax = max(4, 0, 5) = 5
  
  Range = [0, 5], size = 5
  Best = [0, 5]

═══════════════════════════════════════════════════════════

Step 1:
  Poll (0, L1, 0) — minimum is from List 1
  
  List 1 has more elements! Next is 9.
  Push (9, L1, 1) to heap
  Update currentMax = max(5, 9) = 9
  
  Heap: [(4,L0,0), (5,L2,0), (9,L1,1)]
  Range = [4, 9], size = 5
  
  Is [4,9] better than [0,5]? Same size, keep [0,5]

═══════════════════════════════════════════════════════════

Step 2:
  Poll (4, L0, 0) — minimum is from List 0
  
  List 0 has more elements! Next is 10.
  Push (10, L0, 1) to heap
  Update currentMax = max(9, 10) = 10
  
  Heap: [(5,L2,0), (9,L1,1), (10,L0,1)]
  Range = [5, 10], size = 5
  
  Same size, keep [0,5]

═══════════════════════════════════════════════════════════

Step 3:
  Poll (5, L2, 0) — minimum is from List 2
  
  List 2 has more elements! Next is 18.
  Push (18, L2, 1) to heap
  Update currentMax = max(10, 18) = 18
  
  Heap: [(9,L1,1), (10,L0,1), (18,L2,1)]
  Range = [9, 18], size = 9
  
  Worse! Keep [0,5]

═══════════════════════════════════════════════════════════

Step 4:
  Poll (9, L1, 1) — minimum is from List 1
  
  Push (12, L1, 2), currentMax stays 18
  
  Heap: [(10,L0,1), (12,L1,2), (18,L2,1)]
  Range = [10, 18], size = 8
  
  Worse! Keep [0,5]

═══════════════════════════════════════════════════════════

Step 5:
  Poll (10, L0, 1) — minimum is from List 0
  
  Push (15, L0, 2), currentMax stays 18
  
  Heap: [(12,L1,2), (15,L0,2), (18,L2,1)]
  Range = [12, 18], size = 6
  
  Worse! Keep [0,5]

═══════════════════════════════════════════════════════════

Step 6:
  Poll (12, L1, 2) — minimum is from List 1
  
  Push (20, L1, 3), currentMax = max(18, 20) = 20
  
  Heap: [(15,L0,2), (18,L2,1), (20,L1,3)]
  Range = [15, 20], size = 5
  
  Same size, keep [0,5]

═══════════════════════════════════════════════════════════

Step 7:
  Poll (15, L0, 2) — minimum is from List 0
  
  Push (24, L0, 3), currentMax = max(20, 24) = 24
  
  Heap: [(18,L2,1), (20,L1,3), (24,L0,3)]
  Range = [18, 24], size = 6
  
  Worse! Keep [0,5]

═══════════════════════════════════════════════════════════

Step 8:
  Poll (18, L2, 1) — minimum is from List 2
  
  Push (22, L2, 2), currentMax stays 24
  
  Heap: [(20,L1,3), (22,L2,2), (24,L0,3)]
  Range = [20, 24], size = 4
  
  BETTER! Update best = [20, 24]

═══════════════════════════════════════════════════════════

Step 9:
  Poll (20, L1, 3) — minimum is from List 1
  
  List 1 is EXHAUSTED (idx 3 was the last element)!
  
  STOP — can't maintain coverage of all lists.

═══════════════════════════════════════════════════════════

Final Answer: [20, 24] ✓

Verification:
  [20, 24] contains: 24(L0), 20(L1), 22(L2) ✓
  Size = 4 (smallest possible!)
```

--

## The Code (With Line-by-Line Explanation)

```java
int[] smallestRange(List<List<Integer>> nums) {
    // Min-heap: [value, listIndex, elementIndex]
    PriorityQueue<int[]> minHeap = new PriorityQueue<>(
        (a, b) -> a[0] - b[0]  // Order by value
    );
    
    int currentMax = Integer.MIN_VALUE;
    
    // Initialize: add first element from each list
    for (int i = 0; i < nums.size(); i++) {
        int val = nums.get(i).get(0);
        minHeap.offer(new int[]{val, i, 0});
        currentMax = Math.max(currentMax, val);
    }
    
    // Initial range
    int[] result = {minHeap.peek()[0], currentMax};
    
    while (true) {
        // Poll the minimum
        int[] curr = minHeap.poll();
        int val = curr[0], listIdx = curr[1], elemIdx = curr[2];
        
        // If this list is exhausted, we can't maintain coverage
        if (elemIdx + 1 >= nums.get(listIdx).size()) {
            break;  // STOP!
        }
        
        // Move to next element in this list
        int nextVal = nums.get(listIdx).get(elemIdx + 1);
        minHeap.offer(new int[]{nextVal, listIdx, elemIdx + 1});
        
        // Update max if needed
        currentMax = Math.max(currentMax, nextVal);
        
        // Check if current range is better
        int currentMin = minHeap.peek()[0];
        if (currentMax - currentMin < result[1] - result[0]) {
            result = new int[]{currentMin, currentMax};
        }
    }
    
    return result;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting to track max | Can't compute range without it | Update max when adding to heap |
| Not stopping when list exhausted | Invalid range (missing a list) | Break when any list runs out |
| Trying to shrink by moving max | Can only shrink by moving min | Always advance the minimum |
| Wrong comparison for "better" | Off-by-one or wrong direction | Use `<` for strictly smaller range |

--

## Mind-Map Anchor

```
SMALLEST RANGE COVERING K LISTS
              │
              ▼
┌─────────────────────────────────────┐
│ K pointers (one per list)           │
│ Min-heap tracks minimum pointer     │
│ Track maximum separately            │
│ Range = [heap.min, currentMax]      │
│ Advance MIN to try shrinking        │
│ Stop when any list exhausted        │
│ O(N log K) time                     │
└─────────────────────────────────────┘
```

**Memory phrase:** "Min-heap + track max → range = [min, max] → advance min to shrink"

--

# TWO HEAPS FAMILY

--

# PATTERN 7: Find Median from Data Stream (LeetCode 295)

## Pattern Recognition Signal

**When you see:** "median from stream", "running median", "middle element dynamically"

**Instant thought:** "Two heaps! Max-heap for left half, min-heap for right half!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Stream: 5, 15, 1, 3, 8

After 5:     [5]           → median = 5
After 15:    [5, 15]       → median = (5+15)/2 = 10
After 1:     [1, 5, 15]    → median = 5
After 3:     [1, 3, 5, 15] → median = (3+5)/2 = 4
After 8:     [1, 3, 5, 8, 15] → median = 5

We need O(log n) insertion and O(1) median lookup!
```

### Why Two Heaps?

```
The median divides data into two halves:
- Left half: all elements ≤ median
- Right half: all elements ≥ median

If we could instantly know:
- The LARGEST element in the left half
- The SMALLEST element in the right half

Then the median is right there at the boundary!

┌─────────────────────────────────────────────────────────────────────┐
│                                                                      │
│   LEFT HALF (smaller)          │          RIGHT HALF (larger)       │
│   ┌─────────────────────┐      │      ┌─────────────────────┐       │
│   │  1   3   5          │      │      │  8   15             │       │
│   └─────────────────────┘      │      └─────────────────────┘       │
│              ↑                 │                ↑                    │
│         MAX-HEAP               │           MIN-HEAP                  │
│     (gives largest: 5)         │      (gives smallest: 8)           │
│                                │                                     │
│   Median = 5 (odd count) or (5+8)/2 (even count)                    │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### The Two Heaps Setup

```
LEFT HEAP (max-heap):
  - Stores the SMALLER half of numbers
  - Root = LARGEST of the small numbers
  - We want the max, so use MAX-heap

RIGHT HEAP (min-heap):
  - Stores the LARGER half of numbers
  - Root = SMALLEST of the large numbers
  - We want the min, so use MIN-heap

BALANCE RULE:
  - Left can have at most 1 more element than right
  - This way, if odd count, left has the median
  - If even count, median = (left.peek() + right.peek()) / 2
```

### The Algorithm in Plain English

```
Adding a number:
1. Decide which heap it belongs to:
   - If num ≤ left.peek() → goes to left (smaller half)
   - Else → goes to right (larger half)

2. Rebalance if needed:
   - If left has more than 1 extra → move one to right
   - If right has more than left → move one to left

Finding median:
- If left.size > right.size → median = left.peek() (odd count)
- Else → median = (left.peek() + right.peek()) / 2 (even count)
```

--

## Visual Dry Run (Step-by-Step)

**Stream:** `5, 15, 1, 3, 8`

```
═══════════════════════════════════════════════════════════

Add 5:
  Left empty, add to left
  
  LEFT (max-heap): [5]
  RIGHT (min-heap): []
  
  Balance: left=1, right=0 → OK (left can have 1 extra)
  Median = left.peek() = 5 ✓

═══════════════════════════════════════════════════════════

Add 15:
  15 > left.peek(5)? YES → add to right
  
  LEFT: [5]
  RIGHT: [15]
  
  Balance: left=1, right=1 → OK
  Median = (5 + 15) / 2 = 10 ✓

═══════════════════════════════════════════════════════════

Add 1:
  1 ≤ left.peek(5)? YES → add to left
  
  LEFT: [5, 1] → max-heap: [5, 1] (5 at root)
  RIGHT: [15]
  
  Balance: left=2, right=1 → OK (left can have 1 extra)
  Median = left.peek() = 5 ✓

═══════════════════════════════════════════════════════════

Add 3:
  3 ≤ left.peek(5)? YES → add to left
  
  LEFT: [5, 1, 3] → max-heap: [5, 3, 1] (5 at root)
  RIGHT: [15]
  
  Balance: left=3, right=1 → UNBALANCED! (left has 2 extra)
  Move left.poll() to right: move 5 to right
  
  LEFT: [3, 1] → max-heap: [3, 1]
  RIGHT: [5, 15] → min-heap: [5, 15]
  
  Balance: left=2, right=2 → OK
  Median = (3 + 5) / 2 = 4 ✓

═══════════════════════════════════════════════════════════

Add 8:
  8 > left.peek(3)? YES → add to right
  
  LEFT: [3, 1]
  RIGHT: [5, 15, 8] → min-heap: [5, 8, 15]
  
  Balance: left=2, right=3 → UNBALANCED! (right has more)
  Move right.poll() to left: move 5 to left
  
  LEFT: [5, 3, 1] → max-heap: [5, 3, 1]
  RIGHT: [8, 15] → min-heap: [8, 15]
  
  Balance: left=3, right=2 → OK
  Median = left.peek() = 5 ✓

═══════════════════════════════════════════════════════════

Final state:
  LEFT (max-heap): [5, 3, 1] → represents [1, 3, 5]
  RIGHT (min-heap): [8, 15] → represents [8, 15]
  
  Sorted data: [1, 3, 5, 8, 15]
  Median = 5 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
class MedianFinder {
    // Max-heap: stores smaller half, gives LARGEST of small
    private PriorityQueue<Integer> left;
    // Min-heap: stores larger half, gives SMALLEST of large
    private PriorityQueue<Integer> right;
    
    public MedianFinder() {
        left = new PriorityQueue<>(Collections.reverseOrder());  // Max-heap
        right = new PriorityQueue<>();                            // Min-heap
    }
    
    public void addNum(int num) {
        // Step 1: Add to appropriate heap
        if (left.isEmpty() || num <= left.peek()) {
            left.offer(num);  // Belongs to smaller half
        } else {
            right.offer(num);  // Belongs to larger half
        }
        
        // Step 2: Rebalance (left can have at most 1 extra)
        if (left.size() > right.size() + 1) {
            // Left too big, move largest to right
            right.offer(left.poll());
        } else if (right.size() > left.size()) {
            // Right too big, move smallest to left
            left.offer(right.poll());
        }
    }
    
    public double findMedian() {
        if (left.size() > right.size()) {
            // Odd count: left has the middle element
            return left.peek();
        }
        // Even count: average of the two middle elements
        return (left.peek() + right.peek()) / 2.0;
    }
}
```

--

## Why This Works

```
After rebalancing:
- left.size() == right.size() (even total)
  OR
- left.size() == right.size() + 1 (odd total)

For odd count:
  left has one extra → left.peek() is the middle

For even count:
  Both same size → median is average of left.peek() and right.peek()
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using two min-heaps | Left needs max-heap | `Collections.reverseOrder()` |
| Wrong balance condition | Off-by-one errors | left can have AT MOST 1 extra |
| Integer division | (5+8)/2 = 6, not 6.5 | Use `/ 2.0` for double |
| Empty heap check | NullPointerException | Check `left.isEmpty()` first |

--

## Mind-Map Anchor

```
FIND MEDIAN FROM STREAM
           │
           ▼
┌─────────────────────────────┐
│ LEFT = max-heap (smaller)   │
│ RIGHT = min-heap (larger)   │
│ Balance: left ≤ right + 1   │
│ Odd: left.peek()            │
│ Even: (left + right) / 2    │
└─────────────────────────────┘
```

**Memory phrase:** "Max-heap left, min-heap right, balance and peek for median"

--

Add 15:
  15 > 5, goes to right
  left = [5], right = [15]
  Median = (5 + 15) / 2 = 10

Add 1:
  1 ≤ 5, goes to left
  left = [5, 1], right = [15]
  Rebalance: left has 2, right has 1 → OK (diff = 1)
  Median = 5 (left.peek)

Add 3:
  3 ≤ 5, goes to left
  left = [5, 3, 1], right = [15]
  Rebalance: left has 3, right has 1 → move 5 to right
  left = [3, 1], right = [5, 15]
  Median = (3 + 5) / 2 = 4

Add 8:
  8 > 3, goes to right
  left = [3, 1], right = [5, 8, 15]
  Rebalance: right has 3, left has 2 → move 5 to left
  left = [5, 3, 1], right = [8, 15]
  Median = 5 ✓

Sorted: [1, 3, 5, 8, 15] → Median = 5 ✓
```

## The "Aha!" Line

> "Two heaps split data at median. Max-heap for left (smaller), min-heap for right (larger). They meet at the middle!"

## Classic Trap

**Trap:** Forgetting to rebalance after every insert.
- Left can have at most 1 more than right
- Right can never have more than left

## Mind-Map Anchor

**max-heap left · min-heap right · meet at median · rebalance**

--

# PATTERN 8: Sliding Window Median (LeetCode 480)

## Pattern Recognition Signal

**When you see:** "sliding window median", "median of each window", "moving median"

**Instant thought:** "Two heaps (like Pattern 7) + lazy deletion! Mark removed, clean when at top!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [1, 3, -1, -3, 5, 3, 6, 7], k = 3

Find the median of each window of size 3:

Window [1, 3, -1]:   sorted = [-1, 1, 3]   → median = 1
Window [3, -1, -3]:  sorted = [-3, -1, 3]  → median = -1
Window [-1, -3, 5]:  sorted = [-3, -1, 5]  → median = -1
Window [-3, 5, 3]:   sorted = [-3, 3, 5]   → median = 3
Window [5, 3, 6]:    sorted = [3, 5, 6]    → median = 5
Window [3, 6, 7]:    sorted = [3, 6, 7]    → median = 6

Output: [1, -1, -1, 3, 5, 6]
```

### Real-World Analogy: The VIP Club with Lazy Bouncer

```
Remember Pattern 7's two-heap median? Now imagine the VIP club
has a SLIDING DOOR — old members leave as new ones enter!

┌─────────────────────────────────────────────────────────────────────┐
│                    SLIDING WINDOW MEDIAN                             │
│                                                                      │
│   The Problem: We need to REMOVE elements from heaps!               │
│   But heaps don't support efficient removal of arbitrary elements!  │
│                                                                      │
│   The Solution: LAZY DELETION                                       │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │                                                             │   │
│   │   Instead of removing immediately:                          │   │
│   │   1. Mark the element as "to be removed" in a HashMap       │   │
│   │   2. When that element reaches the TOP of a heap, remove it │   │
│   │   3. Keep cleaning until the top is a valid element         │   │
│   │                                                             │   │
│   │   It's like a lazy bouncer who only checks IDs at the door! │   │
│   │                                                             │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Lazy Deletion Works

```
Key insight: We only care about the TOPS of the heaps (for median).
             Elements buried deep in the heap don't affect the median!

When an element leaves the window:
  1. We mark it in a "toRemove" map
  2. It might still be in the heap, but that's OK
  3. When it eventually bubbles to the top, we remove it then

This gives us O(log n) amortized removal instead of O(n)!
```

### The Algorithm in Plain English

```
1. Initialize two heaps (like Pattern 7):
   - left: max-heap for smaller half
   - right: min-heap for larger half

2. Add first k elements, balancing the heaps

3. For each window position:
   a. Record the current median
   b. Element leaving window: mark in toRemove map
   c. Element entering window: add to appropriate heap
   d. Rebalance heaps (considering logical sizes)
   e. Clean up: remove invalid tops from both heaps

4. Return all medians
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [1, 3, -1, -3, 5]`, `k = 3`

```
═══════════════════════════════════════════════════════════

Initialize first window [1, 3, -1]:

Add 1: left = [1], right = []
Add 3: 3 > 1, goes to right → left = [1], right = [3]
Add -1: -1 ≤ 1, goes to left → left = [1, -1], right = [3]
        left has 2, right has 1 → balanced (left can have 1 extra)

left (max-heap): [1, -1]  (root = 1)
right (min-heap): [3]     (root = 3)

Median = left.peek() = 1 ✓

═══════════════════════════════════════════════════════════

Slide window: remove 1, add -3
Window becomes [3, -1, -3]

Step 1: Mark 1 for removal
  toRemove = {1: 1}
  
Step 2: Determine balance change
  1 was in left (1 ≤ left.peek()=1) → balance = -1

Step 3: Add -3
  -3 ≤ left.peek()=1? YES → add to left
  balance += 1 → balance = 0

Step 4: Rebalance (balance = 0, no action needed)

Step 5: Clean up tops
  left.peek() = 1, toRemove has 1 → remove it!
  left = [-1], toRemove = {}
  
  right.peek() = 3, not in toRemove → OK

left: [-1], right: [3]
But wait, we also added -3 to left!
left: [-1, -3], right: [3]

Median = left.peek() = -1 ✓

═══════════════════════════════════════════════════════════

Slide window: remove 3, add 5
Window becomes [-1, -3, 5]

Step 1: Mark 3 for removal
  toRemove = {3: 1}
  
Step 2: 3 was in right (3 > left.peek()=-1) → balance = +1

Step 3: Add 5
  5 > left.peek()=-1? YES → add to right
  balance -= 1 → balance = 0

Step 4: Rebalance (balance = 0, no action needed)

Step 5: Clean up tops
  left.peek() = -1, not in toRemove → OK
  right.peek() = 3, toRemove has 3 → remove it!
  right = [5], toRemove = {}

left: [-1, -3], right: [5]

Median = left.peek() = -1 ✓

═══════════════════════════════════════════════════════════

Slide window: remove -1, add 3 (different 3!)
Window becomes [-3, 5, 3]

Step 1: Mark -1 for removal
  toRemove = {-1: 1}

Step 2: -1 was in left → balance = -1

Step 3: Add 3
  3 > left.peek()=-1? YES → add to right
  balance -= 1 → balance = -2

Step 4: Rebalance (balance < 0, move from right to left)
  Move right.poll()=3 to left
  balance += 2 → balance = 0

Step 5: Clean up tops
  left.peek() = 3, not in toRemove → OK
  (Note: -1 is still in left but not at top — that's OK!)

left: [3, -3, -1*], right: [5]
(* marked for removal but buried)

Median = left.peek() = 3 ✓

═══════════════════════════════════════════════════════════

Result: [1, -1, -1, 3] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
double[] medianSlidingWindow(int[] nums, int k) {
    // Two heaps for median (same as Pattern 7)
    PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());  // max-heap
    PriorityQueue<Integer> right = new PriorityQueue<>();  // min-heap
    
    // Lazy deletion tracker: element -> count to remove
    Map<Integer, Integer> toRemove = new HashMap<>();
    
    double[] result = new double[nums.length - k + 1];
    
    // Initialize first window
    for (int i = 0; i < k; i++) {
        left.offer(nums[i]);
    }
    // Balance: move half to right
    for (int i = 0; i < k / 2; i++) {
        right.offer(left.poll());
    }
    
    // Process each window
    for (int i = k; ; i++) {
        // Record median for current window
        if (k % 2 == 1) {
            result[i - k] = left.peek();
        } else {
            result[i - k] = ((double) left.peek() + right.peek()) / 2.0;
        }
        
        if (i >= nums.length) break;  // Done!
        
        int outgoing = nums[i - k];  // Element leaving window
        int incoming = nums[i];       // Element entering window
        
        // Mark outgoing for lazy deletion
        toRemove.put(outgoing, toRemove.getOrDefault(outgoing, 0) + 1);
        
        // Track balance: how the heaps' logical sizes change
        // +1 if outgoing was in left, -1 if in right
        int balance = (outgoing <= left.peek()) ? -1 : 1;
        
        // Add incoming to appropriate heap
        if (!left.isEmpty() && incoming <= left.peek()) {
            left.offer(incoming);
            balance++;
        } else {
            right.offer(incoming);
            balance-;
        }
        
        // Rebalance based on logical sizes
        if (balance < 0) {  // Left lost more, take from right
            left.offer(right.poll());
        } else if (balance > 0) {  // Right lost more, give to right
            right.offer(left.poll());
        }
        
        // Lazy cleanup: remove invalid tops
        while (!left.isEmpty() && toRemove.getOrDefault(left.peek(), 0) > 0) {
            toRemove.put(left.peek(), toRemove.get(left.peek()) - 1);
            left.poll();
        }
        while (!right.isEmpty() && toRemove.getOrDefault(right.peek(), 0) > 0) {
            toRemove.put(right.peek(), toRemove.get(right.peek()) - 1);
            right.poll();
        }
    }
    
    return result;
}
```

--

## Why Track Balance?

```
The tricky part: we can't just check heap.size() because
heaps contain "ghost" elements (marked for removal but not yet removed).

Instead, we track the LOGICAL balance:
  - When outgoing leaves left: balance = -1
  - When outgoing leaves right: balance = +1
  - When incoming joins left: balance += 1
  - When incoming joins right: balance -= 1

If balance < 0: left is short, take from right
If balance > 0: right is short, give to right
If balance = 0: already balanced
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using heap.size() for balance | Includes ghost elements | Track logical balance |
| Forgetting to clean up tops | Median from invalid element | Always clean after rebalance |
| Integer overflow in median | (a + b) can overflow | Use `(double)a + b` or `a + (b-a)/2.0` |
| Not handling duplicates | Same value removed multiple times | Use count in toRemove map |

--

## Mind-Map Anchor

```
SLIDING WINDOW MEDIAN
          │
          ▼
┌─────────────────────────────────────┐
│ Two heaps (like Pattern 7)          │
│ + Lazy deletion with HashMap        │
│                                     │
│ On slide:                           │
│   1. Mark outgoing in toRemove      │
│   2. Track balance (not heap.size)  │
│   3. Add incoming to correct heap   │
│   4. Rebalance by balance value     │
│   5. Clean up invalid tops          │
│                                     │
│ O(n log k) time                     │
└─────────────────────────────────────┘
```

**Memory phrase:** "Two heaps + lazy deletion → mark removed → clean tops → track balance"

--

# SCHEDULING & GREEDY FAMILY

--

# PATTERN 9: Task Scheduler (LeetCode 621)

## Pattern Recognition Signal

**When you see:** "task scheduler with cooldown", "minimum time to complete tasks", "same task must wait n intervals"

**Instant thought:** "Max-heap by count + cooldown queue! Always pick highest count available!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: tasks = ['A','A','A','B','B','B'], n = 2

Schedule tasks such that same tasks are at least n+1 apart.
Find the minimum time to complete all tasks.

Valid schedule: A B _ A B _ A B
                1 2 3 4 5 6 7 8  → 8 time units

The '_' represents idle time (no task available).
```

### Real-World Analogy: The CPU Scheduler

```
Imagine you're a CPU scheduler with a "cooling" constraint:

┌─────────────────────────────────────────────────────────────────────┐
│                    CPU TASK SCHEDULER                                │
│                                                                      │
│   Tasks: A(3), B(3)  — A needs to run 3 times, B needs 3 times     │
│   Cooldown: n=2 — same task must wait 2 intervals before running   │
│                                                                      │
│   Strategy: GREEDY — always run the task with MOST remaining work!  │
│                                                                      │
│   Why? If we don't prioritize high-count tasks, we'll have more    │
│   idle time at the end waiting for them to cool down.               │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │ Time 1: Available=[A(3), B(3)]  Pick A (highest), A→cooldown│   │
│   │ Time 2: Available=[B(3)]        Pick B (only option)        │   │
│   │ Time 3: Available=[]            IDLE (both on cooldown)     │   │
│   │ Time 4: Available=[A(2)]        A returns! Pick A           │   │
│   │ Time 5: Available=[B(2)]        B returns! Pick B           │   │
│   │ Time 6: Available=[]            IDLE                        │   │
│   │ Time 7: Available=[A(1)]        Pick A (last A!)            │   │
│   │ Time 8: Available=[B(1)]        Pick B (last B!)            │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
│   Schedule: A B _ A B _ A B = 8 time units                          │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Max-Heap + Cooldown Queue?

```
Two data structures work together:

1. MAX-HEAP (by remaining count):
   - Stores tasks that are AVAILABLE to run
   - Always gives us the task with highest remaining count
   - Greedy: prioritize tasks with more work left

2. COOLDOWN QUEUE:
   - Stores tasks that are "cooling down"
   - Each entry: (remaining_count, available_at_time)
   - When current_time >= available_at_time, task returns to heap

The flow:
  heap → pick task → decrement count → cooldown queue → wait → heap
```

### The Algorithm in Plain English

```
1. Count frequency of each task
2. Add all tasks (by count) to max-heap
3. While heap or cooldown queue is not empty:
   a. Increment time
   b. If cooldown queue has tasks ready, move them to heap
   c. If heap is not empty:
      - Poll highest count task
      - Decrement its count
      - If count > 0, add to cooldown queue (available at time + n + 1)
   d. If heap is empty, this is IDLE time
4. Return total time
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `tasks = ['A','A','A','B','B','B']`, `n = 2`

```
Frequencies: A=3, B=3
Max-heap (by count): [3, 3]  (we track counts, not letters)

═══════════════════════════════════════════════════════════

Time 1:
  Cooldown ready? No tasks in cooldown yet.
  
  Heap: [3, 3]
  Pick highest: 3 → execute task (count becomes 2)
  Add to cooldown: (count=2, available_at=1+2+1=4)
  
  Heap: [3]
  Cooldown: [(2, time=4)]
  Schedule: A

═══════════════════════════════════════════════════════════

Time 2:
  Cooldown ready? (2, time=4) — not ready (4 > 2)
  
  Heap: [3]
  Pick highest: 3 → execute task (count becomes 2)
  Add to cooldown: (count=2, available_at=2+2+1=5)
  
  Heap: []
  Cooldown: [(2, time=4), (2, time=5)]
  Schedule: A B

═══════════════════════════════════════════════════════════

Time 3:
  Cooldown ready? Neither ready (4>3, 5>3)
  
  Heap: [] — EMPTY!
  No task available → IDLE
  
  Cooldown: [(2, time=4), (2, time=5)]
  Schedule: A B _

═══════════════════════════════════════════════════════════

Time 4:
  Cooldown ready? (2, time=4) — YES! (4 <= 4)
  Move to heap: heap = [2]
  
  Heap: [2]
  Pick highest: 2 → execute (count becomes 1)
  Add to cooldown: (count=1, available_at=4+2+1=7)
  
  Heap: []
  Cooldown: [(2, time=5), (1, time=7)]
  Schedule: A B _ A

═══════════════════════════════════════════════════════════

Time 5:
  Cooldown ready? (2, time=5) — YES! (5 <= 5)
  Move to heap: heap = [2]
  
  Heap: [2]
  Pick highest: 2 → execute (count becomes 1)
  Add to cooldown: (count=1, available_at=5+2+1=8)
  
  Heap: []
  Cooldown: [(1, time=7), (1, time=8)]
  Schedule: A B _ A B

═══════════════════════════════════════════════════════════

Time 6:
  Cooldown ready? Neither ready (7>6, 8>6)
  
  Heap: [] — EMPTY!
  IDLE
  
  Schedule: A B _ A B _

═══════════════════════════════════════════════════════════

Time 7:
  Cooldown ready? (1, time=7) — YES!
  Move to heap: heap = [1]
  
  Pick highest: 1 → execute (count becomes 0)
  Count = 0, don't add to cooldown (task complete!)
  
  Heap: []
  Cooldown: [(1, time=8)]
  Schedule: A B _ A B _ A

═══════════════════════════════════════════════════════════

Time 8:
  Cooldown ready? (1, time=8) — YES!
  Move to heap: heap = [1]
  
  Pick highest: 1 → execute (count becomes 0)
  Task complete!
  
  Heap: []
  Cooldown: []
  Schedule: A B _ A B _ A B

═══════════════════════════════════════════════════════════

Both heap and cooldown empty → DONE!
Total time: 8 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int leastInterval(char[] tasks, int n) {
    // Step 1: Count frequencies
    int[] freq = new int[26];
    for (char task : tasks) {
        freq[task - 'A']++;
    }
    
    // Step 2: Max-heap by frequency (highest count = highest priority)
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
    for (int f : freq) {
        if (f > 0) maxHeap.offer(f);
    }
    
    // Step 3: Cooldown queue stores (remaining_count, available_time)
    Queue<int[]> cooldown = new LinkedList<>();
    
    int time = 0;
    
    // Step 4: Process until all tasks complete
    while (!maxHeap.isEmpty() || !cooldown.isEmpty()) {
        time++;
        
        // Check if any task is now available (cooldown over)
        if (!cooldown.isEmpty() && cooldown.peek()[1] <= time) {
            maxHeap.offer(cooldown.poll()[0]);
        }
        
        if (!maxHeap.isEmpty()) {
            // Execute highest priority task
            int count = maxHeap.poll() - 1;
            
            if (count > 0) {
                // Task not complete, add to cooldown
                cooldown.offer(new int[]{count, time + n + 1});
            }
        }
        // If heap is empty but cooldown has tasks, this is IDLE time
    }
    
    return time;
}
```

--

## Why Greedy (Max Count First) Works

```
Intuition: Tasks with higher counts are "harder" to schedule
           because they need more cooldown periods.

If we delay high-count tasks:
  - We'll have more idle time at the end
  - Waiting for them to cool down with nothing else to do

By always picking the highest count:
  - We spread out the "hard" tasks early
  - Fill gaps with "easier" tasks
  - Minimize total idle time
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using min-heap | Would pick lowest count first | Use max-heap (reverseOrder) |
| Forgetting cooldown check | Tasks never return to heap | Check cooldown at each time step |
| Wrong available time | Off-by-one error | Available at `time + n + 1` |
| Not handling idle | Infinite loop | Increment time even when idle |

--

## Mind-Map Anchor

```
TASK SCHEDULER
      │
      ▼
┌─────────────────────────────────────┐
│ Max-heap by remaining count         │
│ Cooldown queue: (count, ready_time) │
│                                     │
│ Each time step:                     │
│   1. Release ready tasks to heap    │
│   2. Pick highest count (greedy)    │
│   3. Decrement, add to cooldown     │
│   4. If heap empty → IDLE           │
│                                     │
│ O(n) time where n = total tasks     │
└─────────────────────────────────────┘
```

**Memory phrase:** "Max-heap by count + cooldown queue → greedy picks highest → idle when empty"

--

# PATTERN 10: Reorganize String (LeetCode 767)

## Pattern Recognition Signal

**When you see:** "reorganize string", "no two adjacent same characters", "rearrange so no repeats"

**Instant thought:** "Max-heap by frequency + hold previous! Greedy picks highest, holds for one turn!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: s = "aab"

Rearrange so no two adjacent characters are the same.

Possible arrangements:
  "aab" → 'a' and 'a' adjacent ✗
  "aba" → no adjacent same ✓
  "baa" → 'a' and 'a' adjacent ✗

Output: "aba"

Input: s = "aaab"
No valid arrangement possible! (too many 'a's)
Output: ""
```

### Real-World Analogy: The Alternating Playlist

```
Imagine you're a DJ creating a playlist with a rule:
"Never play the same artist twice in a row!"

┌─────────────────────────────────────────────────────────────────────┐
│                    DJ PLAYLIST PROBLEM                               │
│                                                                      │
│   Songs: Artist A (3 songs), Artist B (2 songs)                     │
│   Rule: No same artist back-to-back                                 │
│                                                                      │
│   Strategy: Always pick the artist with MOST songs remaining,       │
│             but NOT the one you just played!                        │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │ Available: [A(3), B(2)]                                     │   │
│   │ Pick A (highest) → Playlist: A                              │   │
│   │ Hold A aside (can't play next)                              │   │
│   │                                                             │   │
│   │ Available: [B(2)]  (A is held)                              │   │
│   │ Pick B → Playlist: A, B                                     │   │
│   │ Release A, hold B                                           │   │
│   │                                                             │   │
│   │ Available: [A(2)]  (B is held)                              │   │
│   │ Pick A → Playlist: A, B, A                                  │   │
│   │ Release B, hold A                                           │   │
│   │                                                             │   │
│   │ ...continue...                                              │   │
│   │                                                             │   │
│   │ Final: A, B, A, B, A ✓                                      │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
│   If at any point the heap is empty but we still have a held        │
│   character with count > 0, it's IMPOSSIBLE!                        │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Max-Heap + Hold Previous?

```
Key insight: This is Task Scheduler with cooldown = 1!

But instead of a cooldown queue, we just need to hold ONE character
(the previous one) for ONE turn.

Why max-heap (highest frequency first)?
  - If we don't prioritize high-frequency chars, we might get stuck
  - Example: "aaab" → if we pick 'b' first, we're left with "aaa" (impossible!)
  - By picking 'a' first: a_a_a → we can fill gaps with 'b': "ababa" (if we had 2 b's)

When is it impossible?
  - If any character appears more than (n+1)/2 times
  - Example: "aaab" (n=4) → 'a' appears 3 times > (4+1)/2 = 2.5 → impossible!
```

### The Algorithm in Plain English

```
1. Count frequency of each character
2. Add all (count, char) to max-heap
3. prev = null (no previous character yet)
4. While heap is not empty OR prev is not null:
   a. If heap is empty but prev exists → IMPOSSIBLE!
   b. Poll highest frequency character
   c. Add it to result
   d. Decrement its count
   e. If prev exists (from last iteration), add it back to heap
   f. If current char still has count > 0, hold it as new prev
5. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `s = "aab"`

```
Frequencies: a=2, b=1
Max-heap: [(2,'a'), (1,'b')]

═══════════════════════════════════════════════════════════

Step 1:
  prev = null
  Heap: [(2,'a'), (1,'b')]
  
  Poll highest: (2,'a')
  Result: "a"
  Decrement: a's count = 2-1 = 1
  
  prev was null, nothing to add back
  a still has count 1 > 0, hold it: prev = (1,'a')
  
  Heap: [(1,'b')]
  prev: (1,'a')
  Result: "a"

═══════════════════════════════════════════════════════════

Step 2:
  prev = (1,'a')
  Heap: [(1,'b')]
  
  Poll highest: (1,'b')
  Result: "ab"
  Decrement: b's count = 1-1 = 0
  
  prev exists! Add (1,'a') back to heap
  b's count = 0, don't hold it: prev = null
  
  Heap: [(1,'a')]
  prev: null
  Result: "ab"

═══════════════════════════════════════════════════════════

Step 3:
  prev = null
  Heap: [(1,'a')]
  
  Poll highest: (1,'a')
  Result: "aba"
  Decrement: a's count = 1-1 = 0
  
  prev was null, nothing to add back
  a's count = 0, don't hold it: prev = null
  
  Heap: []
  prev: null
  Result: "aba"

═══════════════════════════════════════════════════════════

Heap empty AND prev is null → DONE!
Result: "aba" ✓
```

--

**Example of IMPOSSIBLE case:** `s = "aaab"`

```
Frequencies: a=3, b=1
Max-heap: [(3,'a'), (1,'b')]

Step 1: Poll (3,'a'), result="a", hold (2,'a')
        Heap: [(1,'b')], prev: (2,'a')

Step 2: Poll (1,'b'), result="ab", add back (2,'a'), b done
        Heap: [(2,'a')], prev: null

Step 3: Poll (2,'a'), result="aba", hold (1,'a')
        Heap: [], prev: (1,'a')

Step 4: Heap is EMPTY but prev = (1,'a') exists!
        We need to place 'a' but can't (would be adjacent to previous 'a')
        
        Return "" (IMPOSSIBLE!)
```

--

## The Code (With Line-by-Line Explanation)

```java
String reorganizeString(String s) {
    // Step 1: Count frequencies
    int[] freq = new int[26];
    for (char c : s.toCharArray()) {
        freq[c - 'a']++;
    }
    
    // Step 2: Max-heap stores (count, char_index)
    // Ordered by count (highest first)
    PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
        (a, b) -> b[0] - a[0]  // Compare by count, descending
    );
    
    for (int i = 0; i < 26; i++) {
        if (freq[i] > 0) {
            maxHeap.offer(new int[]{freq[i], i});
        }
    }
    
    StringBuilder result = new StringBuilder();
    int[] prev = null;  // Previously used char (on "cooldown")
    
    // Step 3: Build result
    while (!maxHeap.isEmpty() || prev != null) {
        // If heap is empty but prev exists, impossible!
        if (maxHeap.isEmpty() && prev != null) {
            return "";
        }
        
        // Poll highest frequency char
        int[] curr = maxHeap.poll();
        result.append((char) (curr[1] + 'a'));
        curr[0]-;  // Decrement count
        
        // Add previous back to heap (its cooldown is over)
        if (prev != null) {
            maxHeap.offer(prev);
            prev = null;
        }
        
        // Hold current if it still has remaining count
        if (curr[0] > 0) {
            prev = curr;
        }
    }
    
    return result.toString();
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Not holding previous | Same char can be picked twice in a row | Always hold prev for one turn |
| Forgetting to add prev back | Characters get lost | Add prev back before setting new prev |
| Wrong impossible check | Return wrong answer | Check if heap empty but prev exists |
| Using min-heap | Would pick lowest frequency first | Use max-heap (b[0] - a[0]) |

--

## Mind-Map Anchor

```
REORGANIZE STRING
        │
        ▼
┌─────────────────────────────────────┐
│ Max-heap by frequency               │
│ Hold previous for ONE turn          │
│                                     │
│ Each step:                          │
│   1. Check impossible (heap empty,  │
│      prev exists)                   │
│   2. Poll highest frequency         │
│   3. Add to result, decrement       │
│   4. Add prev back to heap          │
│   5. Hold current as new prev       │
│                                     │
│ Like Task Scheduler with n=1        │
│ O(n log 26) = O(n) time             │
└─────────────────────────────────────┘
```

**Memory phrase:** "Max-heap by freq + hold previous → greedy picks highest → impossible if stuck"

--

# PATTERN 11: Rearrange String K Distance Apart (LeetCode 358)

## Pattern Recognition Signal

**When you see:** "rearrange K distance apart", "same characters at least K apart", "reorganize with distance constraint"

**Instant thought:** "Generalized Pattern 10! Max-heap + cooldown queue of size K!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: s = "aabbcc", k = 3

Rearrange so same characters are at least 3 positions apart.

Valid: "abcabc" → 'a' at positions 0,3 (distance=3) ✓
                  'b' at positions 1,4 (distance=3) ✓
                  'c' at positions 2,5 (distance=3) ✓

Invalid: "aabcbc" → 'a' at positions 0,1 (distance=1) ✗
```

### Real-World Analogy: The Extended Cooldown

```
This is Pattern 10 (Reorganize String) but with a LONGER cooldown!

┌─────────────────────────────────────────────────────────────────────┐
│                    PATTERN COMPARISON                                │
│                                                                      │
│   Pattern 10 (Reorganize String):                                   │
│     - Same char can't be adjacent                                   │
│     - Cooldown = 1 position                                         │
│     - Hold ONE previous character                                   │
│                                                                      │
│   Pattern 11 (K Distance Apart):                                    │
│     - Same char must be K positions apart                           │
│     - Cooldown = K positions                                        │
│     - Hold K-1 previous characters (use a QUEUE!)                   │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │                                                             │   │
│   │   Pattern 10: prev = single character                       │   │
│   │   Pattern 11: cooldown = queue of up to K-1 characters      │   │
│   │                                                             │   │
│   │   When queue reaches size K-1, release the oldest!          │   │
│   │                                                             │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Cooldown Queue of Size K?

```
If k = 3, a character placed at position i can't appear again
until position i + 3.

That means after placing a character, it must "wait" for 3 positions.
During those 3 positions, we place 3 other characters.

So we need to hold the last K-1 characters in a queue:
  - When we place a character, add it to the queue
  - When queue size reaches K, release the oldest (it's now K positions away)
  - Released character goes back to the heap

Example with k=3:
  Position 0: place 'a', queue = [a]
  Position 1: place 'b', queue = [a, b]
  Position 2: place 'c', queue = [a, b, c] → size = k = 3!
              Release 'a' (now 3 positions away), queue = [b, c]
  Position 3: 'a' is available again!
```

### The Algorithm in Plain English

```
1. Count frequency of each character
2. Add all (count, char) to max-heap
3. cooldown = empty queue
4. While heap is not empty:
   a. Poll highest frequency character
   b. Add it to result
   c. Decrement its count
   d. Add (count, char) to cooldown queue
   e. If cooldown.size() >= k:
      - Release oldest from queue
      - If its count > 0, add back to heap
5. If result.length() == s.length(), return result
   Else return "" (impossible)
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `s = "aabbcc"`, `k = 3`

```
Frequencies: a=2, b=2, c=2
Max-heap: [(2,'a'), (2,'b'), (2,'c')]

═══════════════════════════════════════════════════════════

Step 1 (position 0):
  Heap: [(2,'a'), (2,'b'), (2,'c')]
  
  Poll highest: (2,'a')
  Result: "a"
  Decrement: a's count = 1
  
  Add to cooldown: (1,'a')
  Cooldown: [(1,'a')]
  Size = 1 < k=3, don't release yet
  
  Heap: [(2,'b'), (2,'c')]

═══════════════════════════════════════════════════════════

Step 2 (position 1):
  Heap: [(2,'b'), (2,'c')]
  
  Poll highest: (2,'b')
  Result: "ab"
  Decrement: b's count = 1
  
  Add to cooldown: (1,'b')
  Cooldown: [(1,'a'), (1,'b')]
  Size = 2 < k=3, don't release yet
  
  Heap: [(2,'c')]

═══════════════════════════════════════════════════════════

Step 3 (position 2):
  Heap: [(2,'c')]
  
  Poll highest: (2,'c')
  Result: "abc"
  Decrement: c's count = 1
  
  Add to cooldown: (1,'c')
  Cooldown: [(1,'a'), (1,'b'), (1,'c')]
  Size = 3 >= k=3, RELEASE oldest!
  
  Release (1,'a'), count > 0, add back to heap
  Cooldown: [(1,'b'), (1,'c')]
  
  Heap: [(1,'a')]

═══════════════════════════════════════════════════════════

Step 4 (position 3):
  Heap: [(1,'a')]
  
  Poll highest: (1,'a')
  Result: "abca"
  Decrement: a's count = 0
  
  Add to cooldown: (0,'a')
  Cooldown: [(1,'b'), (1,'c'), (0,'a')]
  Size = 3 >= k=3, RELEASE oldest!
  
  Release (1,'b'), count > 0, add back to heap
  Cooldown: [(1,'c'), (0,'a')]
  
  Heap: [(1,'b')]

═══════════════════════════════════════════════════════════

Step 5 (position 4):
  Heap: [(1,'b')]
  
  Poll highest: (1,'b')
  Result: "abcab"
  Decrement: b's count = 0
  
  Add to cooldown: (0,'b')
  Cooldown: [(1,'c'), (0,'a'), (0,'b')]
  Size = 3 >= k=3, RELEASE oldest!
  
  Release (1,'c'), count > 0, add back to heap
  Cooldown: [(0,'a'), (0,'b')]
  
  Heap: [(1,'c')]

═══════════════════════════════════════════════════════════

Step 6 (position 5):
  Heap: [(1,'c')]
  
  Poll highest: (1,'c')
  Result: "abcabc"
  Decrement: c's count = 0
  
  Add to cooldown: (0,'c')
  Cooldown: [(0,'a'), (0,'b'), (0,'c')]
  Size = 3 >= k=3, RELEASE oldest!
  
  Release (0,'a'), count = 0, don't add to heap
  
  Heap: []

═══════════════════════════════════════════════════════════

Heap empty, result.length() = 6 = s.length() → SUCCESS!
Result: "abcabc" ✓

Verification:
  'a' at positions 0, 3 → distance = 3 ✓
  'b' at positions 1, 4 → distance = 3 ✓
  'c' at positions 2, 5 → distance = 3 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
String rearrangeString(String s, int k) {
    // Edge case: k <= 1 means no constraint
    if (k <= 1) return s;
    
    // Step 1: Count frequencies
    int[] freq = new int[26];
    for (char c : s.toCharArray()) {
        freq[c - 'a']++;
    }
    
    // Step 2: Max-heap by frequency
    PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
        (a, b) -> b[0] - a[0]  // Highest count first
    );
    for (int i = 0; i < 26; i++) {
        if (freq[i] > 0) {
            maxHeap.offer(new int[]{freq[i], i});
        }
    }
    
    // Step 3: Cooldown queue holds characters for K positions
    Queue<int[]> cooldown = new LinkedList<>();
    StringBuilder result = new StringBuilder();
    
    // Step 4: Build result
    while (!maxHeap.isEmpty()) {
        // Poll highest frequency
        int[] curr = maxHeap.poll();
        result.append((char) (curr[1] + 'a'));
        curr[0]-;  // Decrement count
        
        // Add to cooldown
        cooldown.offer(curr);
        
        // Release from cooldown after K steps
        if (cooldown.size() >= k) {
            int[] released = cooldown.poll();
            if (released[0] > 0) {
                maxHeap.offer(released);  // Back to heap if count > 0
            }
        }
    }
    
    // Step 5: Check if we placed all characters
    return result.length() == s.length() ? result.toString() : "";
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using k=1 logic | k=1 means adjacent OK | Handle k <= 1 as edge case |
| Wrong queue size check | Off-by-one | Release when size >= k |
| Forgetting count > 0 check | Adding exhausted chars to heap | Only add if count > 0 |
| Not checking result length | May return partial result | Verify length == s.length() |

--

## Mind-Map Anchor

```
REARRANGE K DISTANCE APART
            │
            ▼
┌─────────────────────────────────────┐
│ Generalized Pattern 10              │
│ Max-heap by frequency               │
│ Cooldown QUEUE (not single prev)    │
│                                     │
│ Queue size = K-1 (holds K-1 chars)  │
│ When size >= K, release oldest      │
│ Released char goes back to heap     │
│                                     │
│ If result.length < s.length → ""    │
│ O(n log 26) = O(n) time             │
└─────────────────────────────────────┘
```

**Memory phrase:** "Max-heap + cooldown queue size K → release after K steps → back to heap"

--

# PATTERN 12: Meeting Rooms II (LeetCode 253)

## Pattern Recognition Signal

**When you see:** "minimum meeting rooms", "minimum resources for overlapping intervals", "conference room scheduling"

**Instant thought:** "Sort by start time + min-heap of end times! Reuse room if start >= earliest end!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: intervals = [[0,30], [5,10], [15,20]]

Find the minimum number of meeting rooms needed.

Timeline visualization:
  Room 1: |---- Meeting [0,30] ----|
  Room 2:      |-[5,10]-|
                          |-[15,20]-|

Meeting [5,10] overlaps with [0,30] → needs new room
Meeting [15,20] doesn't overlap with [5,10] (starts after it ends)
  → can reuse Room 2!

Minimum rooms: 2
```

### Real-World Analogy: The Hotel Check-in Problem

```
Imagine you're a hotel manager assigning rooms:

┌─────────────────────────────────────────────────────────────────────┐
│                    HOTEL ROOM ASSIGNMENT                             │
│                                                                      │
│   Guests arrive in order of their CHECK-IN time.                    │
│   Each guest has a check-in and check-out time.                     │
│                                                                      │
│   Strategy:                                                          │
│   1. Sort guests by check-in time                                   │
│   2. Track check-out times of occupied rooms (min-heap)             │
│   3. When a new guest arrives:                                      │
│      - Check if ANY room is free (earliest check-out <= arrival)    │
│      - If yes: reuse that room (update its check-out time)          │
│      - If no: assign a new room                                     │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │ Guest 1 arrives at 0, leaves at 30                          │   │
│   │   No rooms occupied → assign Room 1                         │   │
│   │   Rooms: [checkout=30]                                      │   │
│   │                                                             │   │
│   │ Guest 2 arrives at 5, leaves at 10                          │   │
│   │   Earliest checkout = 30 > 5 → can't reuse                  │   │
│   │   Assign Room 2                                             │   │
│   │   Rooms: [checkout=10, checkout=30]                         │   │
│   │                                                             │   │
│   │ Guest 3 arrives at 15, leaves at 20                         │   │
│   │   Earliest checkout = 10 <= 15 → can reuse Room 2!          │   │
│   │   Update Room 2's checkout to 20                            │   │
│   │   Rooms: [checkout=20, checkout=30]                         │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
│   Final room count: 2                                               │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Sort by Start + Min-Heap of Ends?

```
Key insight: We process meetings in chronological order (by start time).
             For each meeting, we check if we can reuse an existing room.

A room can be reused if its meeting has ENDED before the new one starts.
  → We need to quickly find the room with the EARLIEST end time.
  → Min-heap of end times!

If earliest_end <= new_start:
  → Reuse that room (poll old end, push new end)
  
If earliest_end > new_start:
  → Need a new room (just push new end)

The heap size at any point = number of rooms in use!
```

### The Algorithm in Plain English

```
1. Sort meetings by start time
2. Create a min-heap to track end times of ongoing meetings
3. For each meeting:
   a. If heap is not empty AND earliest end <= current start:
      - Room is free! Poll the old end time (reuse room)
   b. Push the current meeting's end time (assign room)
4. Return heap size (= number of rooms needed)
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `intervals = [[0,30], [5,10], [15,20]]`

```
Step 0: Sort by start time
  Already sorted: [[0,30], [5,10], [15,20]]

Min-heap of end times: []

═══════════════════════════════════════════════════════════

Process meeting [0, 30]:
  Start = 0, End = 30
  
  Heap empty? YES → can't reuse any room
  
  Assign new room, push end time 30
  Heap: [30]
  
  Rooms in use: 1

═══════════════════════════════════════════════════════════

Process meeting [5, 10]:
  Start = 5, End = 10
  
  Heap not empty, earliest end = 30
  Is 30 <= 5? NO → can't reuse (meeting [0,30] still ongoing)
  
  Assign new room, push end time 10
  Heap: [10, 30]  (min-heap: 10 at top)
  
  Rooms in use: 2

═══════════════════════════════════════════════════════════

Process meeting [15, 20]:
  Start = 15, End = 20
  
  Heap not empty, earliest end = 10
  Is 10 <= 15? YES → can reuse! (meeting [5,10] has ended)
  
  Poll 10 (room freed), push 20 (room reassigned)
  Heap: [20, 30]
  
  Rooms in use: 2 (same as before!)

═══════════════════════════════════════════════════════════

All meetings processed.
Final heap size: 2

Answer: 2 rooms needed ✓
```

--

**Another example:** `intervals = [[0,5], [1,2], [1,3], [2,4]]`

```
Sorted: [[0,5], [1,2], [1,3], [2,4]]

Process [0,5]: heap=[], assign room → heap=[5], rooms=1
Process [1,2]: heap=[5], 5>1, assign room → heap=[2,5], rooms=2
Process [1,3]: heap=[2,5], 2>1, assign room → heap=[2,3,5], rooms=3
Process [2,4]: heap=[2,3,5], 2<=2, reuse! → heap=[3,4,5], rooms=3

Answer: 3 rooms ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Step 1: Sort by start time
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    
    // Step 2: Min-heap of end times (earliest end at top)
    PriorityQueue<Integer> endTimes = new PriorityQueue<>();
    
    // Step 3: Process each meeting
    for (int[] meeting : intervals) {
        int start = meeting[0];
        int end = meeting[1];
        
        // If earliest ending room is free, reuse it
        if (!endTimes.isEmpty() && endTimes.peek() <= start) {
            endTimes.poll();  // Room freed
        }
        
        // Assign room (new or reused)
        endTimes.offer(end);
    }
    
    // Step 4: Heap size = rooms in use
    return endTimes.size();
}
```

--

## Why This Works

```
The heap tracks ALL ongoing meetings (by their end times).

When we process a new meeting:
  - We check if the EARLIEST ending meeting has finished
  - If yes, we "reuse" that room (poll + push)
  - If no, we need a new room (just push)

The heap size represents the number of concurrent meetings,
which equals the number of rooms needed!

Note: We only check the EARLIEST end time because:
  - If the earliest hasn't ended, no other meeting has ended either
  - If the earliest has ended, we reuse that room (doesn't matter which)
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting to sort | Meetings processed in wrong order | Sort by start time first |
| Using `<` instead of `<=` | Room free at exact end time | Use `<=` (end=10, start=10 is OK) |
| Sorting by end time | Wrong algorithm | Sort by START time |
| Not handling empty input | NullPointerException | Check length == 0 |

--

## Mind-Map Anchor

```
MEETING ROOMS II
       │
       ▼
┌─────────────────────────────────────┐
│ Sort by START time                  │
│ Min-heap of END times               │
│                                     │
│ For each meeting:                   │
│   If earliest_end <= start:         │
│     → Reuse room (poll old end)     │
│   Push new end time                 │
│                                     │
│ Heap size = rooms needed            │
│ O(n log n) time                     │
└─────────────────────────────────────┘
```

**Memory phrase:** "Sort by start → min-heap of ends → reuse if end <= start → heap size = rooms"

--

# PATTERN 13: IPO (LeetCode 502)

## Pattern Recognition Signal

**When you see:** "maximize capital/profit", "pick K projects", "each project requires capital and gives profit"

**Instant thought:** "Two heaps! Min-heap by capital (unlock projects), max-heap by profit (pick best)!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: k = 2, w = 0 (initial capital)
       profits = [1, 2, 3]
       capital = [0, 1, 1]

Pick at most 2 projects to maximize final capital.
Each project i requires capital[i] to start and gives profits[i].

Projects:
  Project 0: needs 0 capital, gives 1 profit
  Project 1: needs 1 capital, gives 2 profit
  Project 2: needs 1 capital, gives 3 profit

Strategy:
  Start with w=0
  Affordable: Project 0 (needs 0)
  Pick Project 0 → w = 0 + 1 = 1
  
  Now w=1
  Affordable: Project 1 (needs 1), Project 2 (needs 1)
  Pick Project 2 (highest profit) → w = 1 + 3 = 4

Final capital: 4
```

### Real-World Analogy: The Startup Investor

```
Imagine you're a startup investor with limited capital:

┌─────────────────────────────────────────────────────────────────────┐
│                    STARTUP INVESTMENT STRATEGY                       │
│                                                                      │
│   You have initial capital W and can invest in K startups.          │
│   Each startup requires minimum capital to invest.                  │
│   Each startup returns a profit.                                    │
│                                                                      │
│   Strategy: GREEDY with UNLOCKING                                   │
│                                                                      │
│   1. Some startups are "locked" (need more capital than you have)   │
│   2. Among "unlocked" startups, pick the one with HIGHEST profit    │
│   3. After investing, your capital grows                            │
│   4. More startups become "unlocked"!                               │
│   5. Repeat K times                                                 │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │                                                             │   │
│   │   LOCKED PROJECTS          UNLOCKED PROJECTS                │   │
│   │   (need more capital)      (can afford now)                 │   │
│   │                                                             │   │
│   │   Min-heap by capital      Max-heap by profit               │   │
│   │   (to find cheapest        (to pick most                    │   │
│   │    to unlock next)          profitable)                     │   │
│   │                                                             │   │
│   │   As capital grows, move projects from LOCKED to UNLOCKED   │   │
│   │                                                             │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Two Heaps?

```
We need to answer two questions efficiently:

1. "Which projects can I afford now?"
   → Min-heap by capital requirement
   → Poll all projects with capital <= current wealth

2. "Among affordable projects, which gives highest profit?"
   → Max-heap by profit
   → Poll the best one

The flow:
  [Min-heap by capital] --(unlock when affordable)--> [Max-heap by profit]
                                                              |
                                                              v
                                                        Pick best profit
                                                              |
                                                              v
                                                        Capital grows
                                                              |
                                                              v
                                                        More projects unlock!
```

### The Algorithm in Plain English

```
1. Create min-heap of all projects, ordered by capital requirement
2. Create empty max-heap for affordable projects, ordered by profit
3. Repeat K times:
   a. Move all affordable projects (capital <= w) from min-heap to max-heap
   b. If max-heap is empty, no affordable projects → stop
   c. Pick the highest profit project from max-heap
   d. Add its profit to current capital (w += profit)
4. Return final capital
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `k = 2`, `w = 0`, `profits = [1, 2, 3]`, `capital = [0, 1, 1]`

```
Projects:
  P0: capital=0, profit=1
  P1: capital=1, profit=2
  P2: capital=1, profit=3

Min-heap by capital: [(0,1), (1,2), (1,3)]
                      P0     P1     P2
Max-heap by profit: []

Current capital w = 0

═══════════════════════════════════════════════════════════

Round 1 (pick project 1 of 2):

Step 1: Unlock affordable projects (capital <= 0)
  P0 has capital=0 <= 0 → move to max-heap
  P1 has capital=1 > 0 → stays locked
  P2 has capital=1 > 0 → stays locked
  
  Min-heap: [(1,2), (1,3)]
  Max-heap: [(1, P0)]  (profit=1)

Step 2: Pick highest profit from max-heap
  Poll (1, P0) → profit = 1
  
  w = 0 + 1 = 1

═══════════════════════════════════════════════════════════

Round 2 (pick project 2 of 2):

Step 1: Unlock affordable projects (capital <= 1)
  P1 has capital=1 <= 1 → move to max-heap
  P2 has capital=1 <= 1 → move to max-heap
  
  Min-heap: []
  Max-heap: [(3, P2), (2, P1)]  (ordered by profit, 3 at top)

Step 2: Pick highest profit from max-heap
  Poll (3, P2) → profit = 3
  
  w = 1 + 3 = 4

═══════════════════════════════════════════════════════════

Picked 2 projects (k=2), done!
Final capital: 4 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
    int n = profits.length;
    
    // Min-heap: projects ordered by capital requirement
    // Entry: [capital_needed, profit]
    PriorityQueue<int[]> byCapital = new PriorityQueue<>(
        (a, b) -> a[0] - b[0]  // Smallest capital first
    );
    
    // Add all projects to min-heap
    for (int i = 0; i < n; i++) {
        byCapital.offer(new int[]{capital[i], profits[i]});
    }
    
    // Max-heap: affordable projects ordered by profit
    PriorityQueue<Integer> byProfit = new PriorityQueue<>(
        Collections.reverseOrder()  // Highest profit first
    );
    
    // Pick up to K projects
    for (int i = 0; i < k; i++) {
        // Unlock all affordable projects
        while (!byCapital.isEmpty() && byCapital.peek()[0] <= w) {
            byProfit.offer(byCapital.poll()[1]);  // Add profit to max-heap
        }
        
        // If no affordable project, stop early
        if (byProfit.isEmpty()) {
            break;
        }
        
        // Pick the most profitable affordable project
        w += byProfit.poll();
    }
    
    return w;
}
```

--

## Why Greedy Works

```
At each step, we pick the project with the highest profit
among all affordable projects.

Why is this optimal?
  - All affordable projects cost us nothing extra (we already have the capital)
  - Picking the highest profit maximizes our capital growth
  - More capital = more projects become affordable
  - This creates a positive feedback loop!

The order of picking doesn't matter for projects with the same
capital requirement — we just want the highest profit ones.
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Single heap | Can't efficiently find affordable + best profit | Use two heaps |
| Not unlocking in a loop | Miss multiple affordable projects | Use while loop, not if |
| Forgetting early termination | Infinite loop if no affordable | Check if byProfit is empty |
| Wrong heap order | Pick wrong projects | Min by capital, max by profit |

--

## Mind-Map Anchor

```
IPO (MAXIMIZE CAPITAL)
          │
          ▼
┌─────────────────────────────────────┐
│ TWO HEAPS:                          │
│   Min-heap by capital (locked)      │
│   Max-heap by profit (unlocked)     │
│                                     │
│ Each round:                         │
│   1. Unlock: capital <= w → move    │
│   2. Pick: highest profit           │
│   3. Grow: w += profit              │
│                                     │
│ Stop if no affordable projects      │
│ O(n log n) time                     │
└─────────────────────────────────────┘
```

**Memory phrase:** "Min-heap capital (unlock) → max-heap profit (pick best) → capital grows → more unlock"

--

# SPECIAL PATTERNS

--

# PATTERN 14: Ugly Number II (LeetCode 264)

## Pattern Recognition Signal

**When you see:** "nth ugly number", "numbers with only factors 2,3,5", "generate sequence in sorted order"

**Instant thought:** "Min-heap + generate candidates! Each ugly number spawns 3 more (×2, ×3, ×5)!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Ugly numbers are positive numbers whose prime factors only include 2, 3, 5.

Sequence: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12, 15, ...

1 = 1 (by convention)
2 = 2
3 = 3
4 = 2×2
5 = 5
6 = 2×3
8 = 2×2×2
9 = 3×3
10 = 2×5
12 = 2×2×3
...

Find the nth ugly number.
```

### Real-World Analogy: The Family Tree of Ugly Numbers

```
Think of ugly numbers as a family tree:

┌─────────────────────────────────────────────────────────────────────┐
│                    UGLY NUMBER FAMILY TREE                           │
│                                                                      │
│   Every ugly number "gives birth" to 3 children:                    │
│     - child1 = parent × 2                                           │
│     - child2 = parent × 3                                           │
│     - child3 = parent × 5                                           │
│                                                                      │
│                           1                                          │
│                        /  |  \                                       │
│                       2   3   5                                      │
│                      /|\  |\  |\                                     │
│                     4 6 10 6 9 15 10 15 25                           │
│                    ...                                               │
│                                                                      │
│   Notice: Some numbers appear multiple times!                       │
│     6 = 2×3 = 3×2                                                   │
│     10 = 2×5 = 5×2                                                  │
│     15 = 3×5 = 5×3                                                  │
│                                                                      │
│   We need to:                                                        │
│   1. Generate in SORTED order (use min-heap)                        │
│   2. Avoid DUPLICATES (use HashSet)                                 │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Min-Heap + Set?

```
We need to generate ugly numbers in sorted order.

Approach:
  1. Start with 1 (the first ugly number)
  2. For each ugly number x we process:
     - Generate x×2, x×3, x×5 (its "children")
     - Add them to a min-heap (to process smallest first)
  3. Use a Set to avoid adding duplicates
  4. The nth number we poll is the answer!

Why min-heap?
  - We always want the SMALLEST unprocessed ugly number
  - Heap gives us O(log n) access to minimum

Why Set?
  - 6 can be generated as 2×3 or 3×2
  - Without Set, we'd count it twice!
```

### The Algorithm in Plain English

```
1. Create min-heap, add 1
2. Create Set to track seen numbers, add 1
3. Repeat n times:
   a. Poll the smallest ugly number from heap
   b. Generate its 3 children: ×2, ×3, ×5
   c. For each child not in Set:
      - Add to Set
      - Add to heap
4. The nth polled number is the answer
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `n = 10`

```
Find the 10th ugly number.

Min-heap: [1]
Seen: {1}

═══════════════════════════════════════════════════════════

Poll #1:
  Poll 1 (1st ugly number)
  Generate children: 1×2=2, 1×3=3, 1×5=5
  All new! Add to heap and seen.
  
  Heap: [2, 3, 5]
  Seen: {1, 2, 3, 5}

═══════════════════════════════════════════════════════════

Poll #2:
  Poll 2 (2nd ugly number)
  Generate children: 2×2=4, 2×3=6, 2×5=10
  All new! Add to heap and seen.
  
  Heap: [3, 4, 5, 6, 10]
  Seen: {1, 2, 3, 4, 5, 6, 10}

═══════════════════════════════════════════════════════════

Poll #3:
  Poll 3 (3rd ugly number)
  Generate children: 3×2=6, 3×3=9, 3×5=15
  6 already seen! Only add 9, 15.
  
  Heap: [4, 5, 6, 9, 10, 15]
  Seen: {1, 2, 3, 4, 5, 6, 9, 10, 15}

═══════════════════════════════════════════════════════════

Poll #4:
  Poll 4 (4th ugly number)
  Generate children: 4×2=8, 4×3=12, 4×5=20
  All new!
  
  Heap: [5, 6, 8, 9, 10, 12, 15, 20]

═══════════════════════════════════════════════════════════

Poll #5:
  Poll 5 (5th ugly number)
  Generate children: 5×2=10, 5×3=15, 5×5=25
  10, 15 already seen! Only add 25.
  
  Heap: [6, 8, 9, 10, 12, 15, 20, 25]

═══════════════════════════════════════════════════════════

Poll #6:
  Poll 6 (6th ugly number)
  Generate children: 6×2=12, 6×3=18, 6×5=30
  12 already seen! Add 18, 30.
  
  Heap: [8, 9, 10, 12, 15, 18, 20, 25, 30]

═══════════════════════════════════════════════════════════

Poll #7:
  Poll 8 (7th ugly number)
  Generate children: 16, 24, 40
  
  Heap: [9, 10, 12, 15, 16, 18, 20, 24, 25, 30, 40]

═══════════════════════════════════════════════════════════

Poll #8:
  Poll 9 (8th ugly number)
  Generate children: 18, 27, 45
  18 already seen! Add 27, 45.
  
  Heap: [10, 12, 15, 16, 18, 20, 24, 25, 27, 30, 40, 45]

═══════════════════════════════════════════════════════════

Poll #9:
  Poll 10 (9th ugly number)
  Generate children: 20, 30, 50
  20, 30 already seen! Add 50.
  
  Heap: [12, 15, 16, 18, 20, 24, 25, 27, 30, 40, 45, 50]

═══════════════════════════════════════════════════════════

Poll #10:
  Poll 12 (10th ugly number) ← ANSWER!

═══════════════════════════════════════════════════════════

The 10th ugly number is 12 ✓

Sequence so far: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12
```

--

## The Code (With Line-by-Line Explanation)

```java
int nthUglyNumber(int n) {
    // Min-heap to always get smallest ugly number
    PriorityQueue<Long> minHeap = new PriorityQueue<>();
    
    // Set to avoid duplicates (6 = 2×3 = 3×2)
    Set<Long> seen = new HashSet<>();
    
    // Start with 1
    minHeap.offer(1L);
    seen.add(1L);
    
    // The three prime factors
    int[] factors = {2, 3, 5};
    
    long ugly = 1;
    
    // Poll n times
    for (int i = 0; i < n; i++) {
        ugly = minHeap.poll();  // Get smallest
        
        // Generate 3 children
        for (int factor : factors) {
            long next = ugly * factor;
            
            // Only add if not seen before
            if (!seen.contains(next)) {
                seen.add(next);
                minHeap.offer(next);
            }
        }
    }
    
    return (int) ugly;  // The nth ugly number
}
```

--

## Why Use Long?

```
Ugly numbers can get large quickly!

For n = 1690 (a common test case), the answer is 2123366400.
Intermediate values can exceed Integer.MAX_VALUE (2^31 - 1).

Using Long prevents overflow during multiplication.
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using int | Overflow for large n | Use Long |
| No duplicate check | Same number counted multiple times | Use HashSet |
| Wrong loop count | Off-by-one error | Poll exactly n times |
| Forgetting to add 1 | Missing the first ugly number | Initialize with 1 |

--

## Mind-Map Anchor

```
UGLY NUMBER II
      │
      ▼
┌─────────────────────────────────────┐
│ Min-heap for sorted order           │
│ HashSet for duplicate prevention    │
│                                     │
│ Start with 1                        │
│ Each ugly number spawns 3 children: │
│   × 2, × 3, × 5                     │
│                                     │
│ Poll n times → nth ugly number      │
│ Use Long to avoid overflow          │
│ O(n log n) time, O(n) space         │
└─────────────────────────────────────┘
```

**Memory phrase:** "Min-heap + Set → each ugly spawns ×2, ×3, ×5 → poll n times"

--

# MAANG Coverage Map

| Pattern | Problem | LeetCode | Difficulty | Frequency |
|-----|-----|-----|------|------|
| 0 | Kth Largest Element | 215 | Medium | 🔥🔥🔥 |
| 1 | Top K Frequent Elements | 347 | Medium | 🔥🔥🔥 |
| 2 | K Closest Points | 973 | Medium | 🔥🔥🔥 |
| 3 | Kth Smallest in Matrix | 378 | Medium | 🔥🔥 |
| 4 | Merge K Sorted Lists | 23 | Hard | 🔥🔥🔥 |
| 5 | K Pairs Smallest Sums | 373 | Medium | 🔥🔥 |
| 6 | Smallest Range K Lists | 632 | Hard | 🔥🔥 |
| 7 | Median from Stream | 295 | Hard | 🔥🔥🔥 |
| 8 | Sliding Window Median | 480 | Hard | 🔥🔥 |
| 9 | Task Scheduler | 621 | Medium | 🔥🔥🔥 |
| 10 | Reorganize String | 767 | Medium | 🔥🔥🔥 |
| 11 | Rearrange K Distance | 358 | Hard | 🔥🔥 |
| 12 | Meeting Rooms II | 253 | Medium | 🔥🔥🔥 |
| 13 | IPO | 502 | Hard | 🔥🔥 |
| 14 | Ugly Number II | 264 | Medium | 🔥🔥 |

--

# Heap Cheat Sheet

## Java PriorityQueue Quick Reference

```java
// MIN-HEAP (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// MAX-HEAP
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// CUSTOM COMPARATOR (safe from overflow)
PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));

// OPERATIONS
heap.offer(x);    // Add: O(log n)
heap.poll();      // Remove top: O(log n)
heap.peek();      // View top: O(1)
heap.size();      // Count: O(1)
heap.isEmpty();   // Check empty: O(1)
```

## The 4 Patterns Quick Reference

| Pattern | Heap Type | Key Insight |
|-----|------|-------|
| **Top K** | Opposite (min for K largest) | Kick out unwanted, keep wanted |
| **K-Way Merge** | Min-heap of K heads | Always process global minimum |
| **Two Heaps** | Max-heap + Min-heap | Split at median |
| **Greedy Schedule** | Max-heap by priority | Always pick best available |

## Common Mistakes

| Mistake | Why It's Wrong | Fix |
|-----|--------|---|
| `(a, b) -> a - b` | Integer overflow | Use `Integer.compare(a, b)` |
| Max-heap for K largest | Kicks out large ones! | Use min-heap size K |
| Forgetting to rebalance two heaps | Median becomes wrong | Always rebalance after insert |
| Not handling empty heap | NullPointerException | Check `isEmpty()` before `peek()/poll()` |

--

# Mastery Checklist

## Tier 1: Must Know (Interview Essentials)
- [ ] Kth Largest Element (min-heap size K)
- [ ] Top K Frequent Elements (HashMap + heap)
- [ ] Merge K Sorted Lists (min-heap of heads)
- [ ] Find Median from Stream (two heaps)
- [ ] Meeting Rooms II (sort + min-heap of ends)

## Tier 2: Interview Favorites
- [ ] K Closest Points (max-heap by distance)
- [ ] Task Scheduler (max-heap + cooldown)
- [ ] Reorganize String (max-heap + hold prev)
- [ ] Kth Smallest in Matrix (min-heap BFS)

## Tier 3: Differentiators
- [ ] Smallest Range K Lists (min-heap + track max)
- [ ] Sliding Window Median (two heaps + lazy deletion)
- [ ] IPO (two heaps: capital + profit)
- [ ] K Pairs Smallest Sums (virtual matrix BFS)
- [ ] Ugly Number II (generate + min-heap)

--

# The Final Mental Model

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HEAP PATTERN MASTERY                              │
├─────────────────────────────────────────────────────────────────────┤
│                                                                      │
│  1. TOP K PROBLEMS                                                   │
│     → Use OPPOSITE heap of size K                                   │
│     → K largest? Min-heap. K smallest? Max-heap.                    │
│                                                                      │
│  2. K-WAY MERGE                                                      │
│     → Min-heap of K current heads                                   │
│     → Poll smallest, push its next                                  │
│                                                                      │
│  3. MEDIAN / SPLIT PROBLEMS                                          │
│     → Two heaps: max-heap (left) + min-heap (right)                 │
│     → They meet at the median                                       │
│                                                                      │
│  4. SCHEDULING / GREEDY                                              │
│     → Max-heap by priority/frequency                                │
│     → Cooldown queue for constraints                                │
│                                                                      │
│  5. MEETING ROOMS / INTERVALS                                        │
│     → Sort by start, min-heap of end times                          │
│     → Heap size = resources needed                                  │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

--

# Interview Explanation Template

When explaining heap solutions:

1. **State why heap:** "I need quick access to the [smallest/largest/best] element, and data keeps changing, so a heap is ideal."

2. **Explain heap choice:** "I use a [min/max]-heap because I need to [kick out small/large ones / track minimum / etc.]"

3. **Walk through key insight:** "The key insight is that [the heap root is exactly what I need / I can maintain K candidates / etc.]"

4. **State complexity:** "Time is O(n log k) because each element is pushed/popped once, and heap operations are O(log k)."

5. **Mention edge cases:** "I handle [empty input / k=0 / k>n / duplicates] by [specific handling]."

--

**Remember:** A heap answers ONE question instantly: "What is the BEST candidate RIGHT NOW?" Define "best" correctly, and the solution follows!

--

# BONUS PATTERNS (Additional MAANG Coverage)

--

## PATTERN 15: Kth Largest Element in a Stream (LeetCode 703)

## Pattern Recognition Signal

**When you see:** "Kth largest in stream", "continuous Kth largest", "add elements and query Kth"

**Instant thought:** "Persistent min-heap of size K! Same as Pattern 0, but heap persists across add() calls!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Design a class that:
  - Initializes with an array and k
  - Has add(val) method that returns Kth largest after adding val

Example:
  KthLargest(3, [4, 5, 8, 2])  // k=3, initial array
  
  Initial sorted: [2, 4, 5, 8]
  3rd largest = 4
  
  add(3) → [2, 3, 4, 5, 8] → 3rd largest = 4
  add(5) → [2, 3, 4, 5, 5, 8] → 3rd largest = 5
  add(10) → [2, 3, 4, 5, 5, 8, 10] → 3rd largest = 5
  add(9) → [2, 3, 4, 5, 5, 8, 9, 10] → 3rd largest = 8
  add(4) → [2, 3, 4, 4, 5, 5, 8, 9, 10] → 3rd largest = 8
```

### Real-World Analogy: The Persistent VIP Room

```
This is Pattern 0's VIP room, but it STAYS OPEN!

┌─────────────────────────────────────────────────────────────────────┐
│                    PERSISTENT VIP ROOM                               │
│                                                                      │
│   Pattern 0: Process all elements ONCE, return Kth largest          │
│   Pattern 15: Keep the VIP room open, handle new arrivals!          │
│                                                                      │
│   The VIP room (min-heap of size K) persists between add() calls.   │
│   The bouncer (heap root) is ALWAYS the Kth largest!                │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │                                                             │   │
│   │   add(val):                                                 │   │
│   │     1. Add val to heap                                      │   │
│   │     2. If heap.size > k, kick out smallest (poll)           │   │
│   │     3. Return heap.peek() (the bouncer = Kth largest)       │   │
│   │                                                             │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### The Algorithm in Plain English

```
Constructor:
  1. Create min-heap
  2. Add all initial elements using add() method

add(val):
  1. Add val to heap
  2. If heap.size() > k, poll (kick out smallest)
  3. Return heap.peek() (Kth largest)
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `k = 3`, `nums = [4, 5, 8, 2]`

```
═══════════════════════════════════════════════════════════

Constructor: KthLargest(3, [4, 5, 8, 2])

add(4): heap = [4], size=1 ≤ 3, keep
add(5): heap = [4, 5], size=2 ≤ 3, keep
add(8): heap = [4, 5, 8], size=3 ≤ 3, keep
add(2): heap = [2, 4, 5, 8], size=4 > 3, poll 2 → heap = [4, 5, 8]

Initial heap: [4, 5, 8] (min-heap, 4 at root)
Kth largest = 4

═══════════════════════════════════════════════════════════

add(3):
  heap = [4, 5, 8] → add 3 → [3, 4, 5, 8]
  size = 4 > 3, poll 3 → [4, 5, 8]
  
  Return peek() = 4 ✓
  (3 wasn't big enough to enter VIP)

═══════════════════════════════════════════════════════════

add(5):
  heap = [4, 5, 8] → add 5 → [4, 5, 5, 8]
  size = 4 > 3, poll 4 → [5, 5, 8]
  
  Return peek() = 5 ✓
  (5 kicked out 4, new Kth largest is 5)

═══════════════════════════════════════════════════════════

add(10):
  heap = [5, 5, 8] → add 10 → [5, 5, 8, 10]
  size = 4 > 3, poll 5 → [5, 8, 10]
  
  Return peek() = 5 ✓

═══════════════════════════════════════════════════════════

add(9):
  heap = [5, 8, 10] → add 9 → [5, 8, 9, 10]
  size = 4 > 3, poll 5 → [8, 9, 10]
  
  Return peek() = 8 ✓

═══════════════════════════════════════════════════════════

add(4):
  heap = [8, 9, 10] → add 4 → [4, 8, 9, 10]
  size = 4 > 3, poll 4 → [8, 9, 10]
  
  Return peek() = 8 ✓
  (4 wasn't big enough)
```

--

## The Code (With Line-by-Line Explanation)

```java
class KthLargest {
    private int k;
    private PriorityQueue<Integer> minHeap;
    
    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.minHeap = new PriorityQueue<>();  // Min-heap
        
        // Add all initial elements
        for (int num : nums) {
            add(num);  // Reuse add() logic
        }
    }
    
    public int add(int val) {
        minHeap.offer(val);  // Add to heap
        
        if (minHeap.size() > k) {
            minHeap.poll();  // Kick out smallest
        }
        
        return minHeap.peek();  // Kth largest = smallest in VIP room
    }
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using max-heap | Would need to poll k times | Use min-heap size k |
| Not reusing add() in constructor | Duplicate logic | Call add() for each initial element |
| Forgetting size check | Heap grows unbounded | Check size > k after each add |
| Returning after poll | Wrong element | Return peek(), not the polled value |

--

## Mind-Map Anchor

```
KTH LARGEST IN STREAM
          │
          ▼
┌─────────────────────────────────────┐
│ Persistent min-heap of size K       │
│ Same as Pattern 0, but continuous   │
│                                     │
│ add(val):                           │
│   1. offer(val)                     │
│   2. if size > k: poll()            │
│   3. return peek()                  │
│                                     │
│ Heap root = Kth largest always!     │
│ O(log k) per add                    │
└─────────────────────────────────────┘
```

**Memory phrase:** "Persistent VIP room → add, kick if full, peek = Kth largest"

--

## PATTERN 16: Last Stone Weight (LeetCode 1046)

## Pattern Recognition Signal

**When you see:** "smash two heaviest", "repeatedly pick largest two", "collision simulation"

**Instant thought:** "Max-heap! Poll two largest, push difference if non-zero!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: stones = [2, 7, 4, 1, 8, 1]

Rules:
  - Pick two HEAVIEST stones
  - Smash them together
  - If equal weight: both destroyed
  - If different: smaller destroyed, larger becomes (larger - smaller)
  - Repeat until 0 or 1 stone left

Simulation:
  [2, 7, 4, 1, 8, 1] → pick 8, 7 → 8-7=1 → [2, 4, 1, 1, 1]
  [2, 4, 1, 1, 1] → pick 4, 2 → 4-2=2 → [1, 1, 1, 2]
  [1, 1, 1, 2] → pick 2, 1 → 2-1=1 → [1, 1, 1]
  [1, 1, 1] → pick 1, 1 → equal, both gone → [1]
  
  Return 1
```

### Real-World Analogy: The Sumo Tournament

```
Imagine a sumo tournament where:

┌─────────────────────────────────────────────────────────────────────┐
│                    SUMO STONE TOURNAMENT                             │
│                                                                      │
│   - Two HEAVIEST wrestlers fight each round                         │
│   - Heavier wrestler wins, but loses weight = opponent's weight     │
│   - If same weight, both are eliminated                             │
│   - Continue until 0 or 1 wrestler remains                          │
│                                                                      │
│   We need to quickly find the two heaviest → MAX-HEAP!              │
│                                                                      │
│   ┌─────────────────────────────────────────────────────────────┐   │
│   │                                                             │   │
│   │   Round 1: [8, 7, 4, 2, 1, 1]                               │   │
│   │            Pick 8 and 7 (heaviest two)                      │   │
│   │            8 wins! New weight = 8-7 = 1                     │   │
│   │            → [4, 2, 1, 1, 1]                                │   │
│   │                                                             │   │
│   │   Round 2: Pick 4 and 2                                     │   │
│   │            4 wins! New weight = 4-2 = 2                     │   │
│   │            → [2, 1, 1, 1]                                   │   │
│   │                                                             │   │
│   │   ...continue until 0 or 1 left...                         │   │
│   │                                                             │   │
│   └─────────────────────────────────────────────────────────────┘   │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### The Algorithm in Plain English

```
1. Add all stones to a max-heap
2. While heap has more than 1 stone:
   a. Poll the two heaviest (a and b)
   b. If a != b, push (a - b) back to heap
   c. If a == b, both destroyed (don't push anything)
3. If heap is empty, return 0
   Else return the last stone's weight
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `stones = [2, 7, 4, 1, 8, 1]`

```
Max-heap (largest at top): [8, 7, 4, 2, 1, 1]

═══════════════════════════════════════════════════════════

Round 1:
  Heap: [8, 7, 4, 2, 1, 1]
  
  Poll heaviest: 8
  Poll second heaviest: 7
  
  8 != 7, so push 8-7 = 1
  
  Heap: [4, 2, 1, 1, 1]

═══════════════════════════════════════════════════════════

Round 2:
  Heap: [4, 2, 1, 1, 1]
  
  Poll heaviest: 4
  Poll second heaviest: 2
  
  4 != 2, so push 4-2 = 2
  
  Heap: [2, 1, 1, 1]

═══════════════════════════════════════════════════════════

Round 3:
  Heap: [2, 1, 1, 1]
  
  Poll heaviest: 2
  Poll second heaviest: 1
  
  2 != 1, so push 2-1 = 1
  
  Heap: [1, 1, 1]

═══════════════════════════════════════════════════════════

Round 4:
  Heap: [1, 1, 1]
  
  Poll heaviest: 1
  Poll second heaviest: 1
  
  1 == 1, both destroyed! Don't push anything.
  
  Heap: [1]

═══════════════════════════════════════════════════════════

Heap size = 1, stop!
Return heap.peek() = 1 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int lastStoneWeight(int[] stones) {
    // Max-heap (largest at top)
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(
        Collections.reverseOrder()
    );
    
    // Add all stones
    for (int stone : stones) {
        maxHeap.offer(stone);
    }
    
    // Smash until 0 or 1 stone left
    while (maxHeap.size() > 1) {
        int a = maxHeap.poll();  // Heaviest
        int b = maxHeap.poll();  // Second heaviest
        
        if (a != b) {
            maxHeap.offer(a - b);  // Remaining piece
        }
        // If a == b, both destroyed (don't push)
    }
    
    // Return last stone or 0 if none left
    return maxHeap.isEmpty() ? 0 : maxHeap.peek();
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using min-heap | Would pick smallest, not largest | Use max-heap (reverseOrder) |
| Pushing when a == b | Creates phantom 0-weight stone | Only push if a != b |
| Not checking empty | NullPointerException | Return 0 if heap empty |
| Wrong subtraction order | Negative weight | Always do larger - smaller (a - b since a >= b) |

--

## Mind-Map Anchor

```
LAST STONE WEIGHT
       │
       ▼
┌─────────────────────────────────────┐
│ Max-heap (heaviest at top)          │
│                                     │
│ While size > 1:                     │
│   Poll two heaviest (a, b)          │
│   If a != b: push (a - b)           │
│   If a == b: both gone              │
│                                     │
│ Return last stone or 0              │
│ O(n log n) time                     │
└─────────────────────────────────────┘
```

**Memory phrase:** "Max-heap → poll two heaviest → push difference → repeat until done"

--

## PATTERN 17: Design Twitter (LeetCode 355)

## Pattern Recognition Signal

**When you see:** "design Twitter/feed", "get most recent posts from followees", "merge multiple sorted streams"

**Instant thought:** "K-way merge! Each user's tweets are a sorted stream. Max-heap by timestamp!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Design a simplified Twitter with these operations:
  - postTweet(userId, tweetId): User posts a tweet
  - getNewsFeed(userId): Get 10 most recent tweets from user + followees
  - follow(followerId, followeeId): User follows another user
  - unfollow(followerId, followeeId): User unfollows another user

Example:
  postTweet(1, 5)  // User 1 posts tweet 5
  getNewsFeed(1)   // Returns [5]
  follow(1, 2)     // User 1 follows user 2
  postTweet(2, 6)  // User 2 posts tweet 6
  getNewsFeed(1)   // Returns [6, 5] (most recent first)
  unfollow(1, 2)
  getNewsFeed(1)   // Returns [5] (no longer sees user 2's tweets)
```

### Real-World Analogy: Merging News Feeds

```
Think of each user as a news channel with their own timeline:

┌─────────────────────────────────────────────────────────────────────┐
│                    TWITTER NEWS FEED                                 │
│                                                                      │
│   User 1 follows: [User 2, User 3, User 4]                          │
│                                                                      │
│   Each user has their own tweet timeline (sorted by time):          │
│                                                                      │
│   User 1's tweets: [tweet@t=10, tweet@t=5, tweet@t=1]               │
│   User 2's tweets: [tweet@t=8, tweet@t=3]                           │
│   User 3's tweets: [tweet@t=9, tweet@t=7, tweet@t=2]                │
│   User 4's tweets: [tweet@t=6]                                      │
│                                                                      │
│   getNewsFeed(1) needs to merge these 4 streams and get top 10!     │
│                                                                      │
│   This is K-WAY MERGE (Pattern 4)!                                  │
│   - K = number of users (self + followees)                          │
│   - Each stream is sorted by timestamp (descending)                 │
│   - Use max-heap by timestamp to get most recent                    │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Data Structures Needed

```
1. tweets: Map<userId, List<(timestamp, tweetId)>>
   - Each user's tweets in chronological order
   - Most recent at the END of the list

2. following: Map<userId, Set<followeeId>>
   - Who each user follows

3. timestamp: Global counter
   - Increments with each tweet
   - Used to order tweets chronologically
```

### The Algorithm for getNewsFeed

```
1. Collect all relevant users: self + all followees
2. For each user, get their tweets
3. Use max-heap by timestamp to merge all tweets
4. Poll up to 10 tweets from heap
5. Return the tweet IDs
```

--

## Visual Dry Run (Step-by-Step)

**Operations:**
```
postTweet(1, 101)  // User 1 posts tweet 101 at t=0
postTweet(2, 201)  // User 2 posts tweet 201 at t=1
follow(1, 2)       // User 1 follows User 2
postTweet(2, 202)  // User 2 posts tweet 202 at t=2
postTweet(1, 102)  // User 1 posts tweet 102 at t=3
getNewsFeed(1)     // Get User 1's feed
```

```
═══════════════════════════════════════════════════════════

After all posts:
  tweets[1] = [(t=0, 101), (t=3, 102)]
  tweets[2] = [(t=1, 201), (t=2, 202)]
  following[1] = {2}

═══════════════════════════════════════════════════════════

getNewsFeed(1):

Step 1: Collect relevant users
  Self: 1
  Followees: {2}
  All users: [1, 2]

Step 2: Add all tweets to max-heap (by timestamp)
  From User 1: (t=0, 101), (t=3, 102)
  From User 2: (t=1, 201), (t=2, 202)
  
  Max-heap: [(t=3, 102), (t=2, 202), (t=1, 201), (t=0, 101)]

Step 3: Poll up to 10 tweets
  Poll (t=3, 102) → result = [102]
  Poll (t=2, 202) → result = [102, 202]
  Poll (t=1, 201) → result = [102, 202, 201]
  Poll (t=0, 101) → result = [102, 202, 201, 101]
  
  Heap empty, stop.

═══════════════════════════════════════════════════════════

Result: [102, 202, 201, 101] ✓
(Most recent first: 102 at t=3, then 202 at t=2, etc.)
```

--

## The Code (With Line-by-Line Explanation)

```java
class Twitter {
    private int timestamp = 0;  // Global timestamp counter
    
    // userId -> list of (timestamp, tweetId)
    private Map<Integer, List<int[]>> tweets;
    
    // userId -> set of followee IDs
    private Map<Integer, Set<Integer>> following;
    
    public Twitter() {
        tweets = new HashMap<>();
        following = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        // Add tweet with current timestamp
        tweets.computeIfAbsent(userId, k -> new ArrayList<>())
              .add(new int[]{timestamp++, tweetId});
    }
    
    public List<Integer> getNewsFeed(int userId) {
        // Max-heap by timestamp (most recent first)
        PriorityQueue<int[]> heap = new PriorityQueue<>(
            (a, b) -> b[0] - a[0]  // Descending by timestamp
        );
        
        // Add own tweets
        if (tweets.containsKey(userId)) {
            for (int[] tweet : tweets.get(userId)) {
                heap.offer(tweet);
            }
        }
        
        // Add followees' tweets
        if (following.containsKey(userId)) {
            for (int followee : following.get(userId)) {
                if (tweets.containsKey(followee)) {
                    for (int[] tweet : tweets.get(followee)) {
                        heap.offer(tweet);
                    }
                }
            }
        }
        
        // Get top 10 most recent
        List<Integer> result = new ArrayList<>();
        while (!heap.isEmpty() && result.size() < 10) {
            result.add(heap.poll()[1]);  // Add tweet ID
        }
        
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) return;  // Can't follow self
        following.computeIfAbsent(followerId, k -> new HashSet<>())
                 .add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (following.containsKey(followerId)) {
            following.get(followerId).remove(followeeId);
        }
    }
}
```

--

## Optimization: True K-Way Merge

```
The above solution adds ALL tweets to the heap, which can be slow
if users have many tweets.

Better approach: Only add the MOST RECENT tweet from each user,
then expand as we poll (like Pattern 4: Merge K Sorted Lists).

This is O(K log K) for K users instead of O(N log N) for N total tweets.
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting self's tweets | User should see own tweets | Include userId in feed |
| Using min-heap | Would get oldest first | Use max-heap (b[0] - a[0]) |
| Allowing self-follow | Duplicate tweets in feed | Check followerId != followeeId |
| Not handling missing users | NullPointerException | Use containsKey checks |

--

## Mind-Map Anchor

```
DESIGN TWITTER
      │
      ▼
┌─────────────────────────────────────┐
│ Data structures:                    │
│   tweets: Map<user, List<(t, id)>>  │
│   following: Map<user, Set<user>>   │
│   timestamp: global counter         │
│                                     │
│ getNewsFeed = K-way merge!          │
│   Max-heap by timestamp             │
│   Merge self + followees' tweets    │
│   Return top 10                     │
│                                     │
│ O(N log N) simple, O(K log K) opt   │
└─────────────────────────────────────┘
```

**Memory phrase:** "K-way merge of tweet streams → max-heap by timestamp → top 10"

--

## PATTERN 18: Single-Threaded CPU (LeetCode 1834)

## Pattern Recognition Signal

**When you see:** "single-threaded CPU", "process tasks by shortest time", "task scheduling with arrival times"

**Instant thought:** "Sort by arrival + min-heap by processing time! Two-phase: unlock by time, pick by duration!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: tasks = [[1,2], [2,4], [3,2], [4,1]]
       Each task: [enqueueTime, processingTime]

Rules:
  - CPU processes ONE task at a time
  - At each moment, pick the task with SHORTEST processing time
    among all available (arrived) tasks
  - If tie, pick the one with smaller index
  - Return the order in which tasks are processed

Task breakdown:
  Task 0: arrives at t=1, takes 2 units
  Task 1: arrives at t=2, takes 4 units
  Task 2: arrives at t=3, takes 2 units
  Task 3: arrives at t=4, takes 1 unit
```

### Real-World Analogy: The Efficient Receptionist

```
Imagine a receptionist handling requests:

┌─────────────────────────────────────────────────────────────────────┐
│                    SINGLE-THREADED CPU                               │
│                                                                      │
│   Requests arrive at different times.                               │
│   Each request takes some time to process.                          │
│   Receptionist can only handle ONE request at a time.               │
│                                                                      │
│   Strategy: Among all WAITING requests, pick the QUICKEST one!      │
│   (Shortest Job First scheduling)                                   │
│                                                                      │
│   Two data structures:                                              │
│                                                                      │
│   1. WAITING ROOM (sorted by arrival time)                          │
│      - Tasks that haven't arrived yet                               │
│      - Sort by enqueueTime to know when they become available       │
│                                                                      │
│   2. READY QUEUE (min-heap by processing time)                      │
│      - Tasks that have arrived and are waiting                      │
│      - Min-heap to quickly get shortest task                        │
│                                                                      │
│   Flow:                                                              │
│   [Waiting Room] --(time passes)--> [Ready Queue] --> [Process]  │
│                                                                      │
└─────────────────────────────────────────────────────────────────────┘
```

### Why Sort + Min-Heap?

```
We need to answer two questions:

1. "Which tasks are available NOW?"
   → Sort tasks by arrival time
   → Move all tasks with arrivalTime <= currentTime to ready queue

2. "Among available tasks, which is shortest?"
   → Min-heap by processing time (then by index for ties)
   → Poll the shortest task

The flow:
  1. Sort all tasks by arrival time
  2. At each step:
     a. Move newly arrived tasks to ready queue
     b. If ready queue empty, jump time to next arrival
     c. Process shortest task, advance time
```

### The Algorithm in Plain English

```
1. Create indexed tasks: [enqueueTime, processingTime, originalIndex]
2. Sort by enqueueTime
3. Create min-heap for ready tasks (by processingTime, then index)
4. time = 0, i = 0 (pointer to sorted tasks)
5. While not all tasks processed:
   a. Move all tasks with enqueueTime <= time to ready queue
   b. If ready queue empty:
      - Jump time to next task's arrival
   c. Else:
      - Poll shortest task from ready queue
      - Add its index to result
      - Advance time by its processing time
6. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `tasks = [[1,2], [2,4], [3,2], [4,1]]`

```
Indexed tasks:
  Task 0: enqueue=1, process=2, index=0
  Task 1: enqueue=2, process=4, index=1
  Task 2: enqueue=3, process=2, index=2
  Task 3: enqueue=4, process=1, index=3

Sorted by enqueue time (already sorted):
  [(1,2,0), (2,4,1), (3,2,2), (4,1,3)]

═══════════════════════════════════════════════════════════

Initial State:
  time = 0
  i = 0 (next task to check)
  Ready queue: []
  Result: []

═══════════════════════════════════════════════════════════

Step 1:
  Move arrived tasks (enqueue <= 0): none
  Ready queue empty!
  
  Jump time to next arrival: time = 1
  
  Move arrived tasks (enqueue <= 1): Task 0 (1,2,0)
  Ready queue: [(process=2, idx=0)]
  
  Poll shortest: Task 0 (process=2)
  Result: [0]
  time = 1 + 2 = 3

═══════════════════════════════════════════════════════════

Step 2:
  Current time = 3
  
  Move arrived tasks (enqueue <= 3):
    Task 1 (enqueue=2): arrived! Add to ready queue
    Task 2 (enqueue=3): arrived! Add to ready queue
  
  Ready queue: [(process=4, idx=1), (process=2, idx=2)]
  Min-heap reorders: [(process=2, idx=2), (process=4, idx=1)]
  
  Poll shortest: Task 2 (process=2)
  Result: [0, 2]
  time = 3 + 2 = 5

═══════════════════════════════════════════════════════════

Step 3:
  Current time = 5
  
  Move arrived tasks (enqueue <= 5):
    Task 3 (enqueue=4): arrived! Add to ready queue
  
  Ready queue: [(process=4, idx=1), (process=1, idx=3)]
  Min-heap reorders: [(process=1, idx=3), (process=4, idx=1)]
  
  Poll shortest: Task 3 (process=1)
  Result: [0, 2, 3]
  time = 5 + 1 = 6

═══════════════════════════════════════════════════════════

Step 4:
  Current time = 6
  
  Move arrived tasks: none left (i = 4)
  
  Ready queue: [(process=4, idx=1)]
  
  Poll shortest: Task 1 (process=4)
  Result: [0, 2, 3, 1]
  time = 6 + 4 = 10

═══════════════════════════════════════════════════════════

All tasks processed!
Result: [0, 2, 3, 1] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int[] getOrder(int[][] tasks) {
    int n = tasks.length;
    
    // Create indexed tasks: [enqueueTime, processingTime, originalIndex]
    int[][] indexed = new int[n][3];
    for (int i = 0; i < n; i++) {
        indexed[i] = new int[]{tasks[i][0], tasks[i][1], i};
    }
    
    // Sort by enqueue time
    Arrays.sort(indexed, (a, b) -> a[0] - b[0]);
    
    // Min-heap: order by processingTime, then by index (for ties)
    PriorityQueue<int[]> ready = new PriorityQueue<>((a, b) -> 
        a[1] != b[1] ? a[1] - b[1] : a[2] - b[2]
    );
    
    int[] result = new int[n];
    int time = 0;      // Current time
    int i = 0;         // Pointer to sorted tasks
    int idx = 0;       // Result index
    
    while (idx < n) {
        // Move all arrived tasks to ready queue
        while (i < n && indexed[i][0] <= time) {
            ready.offer(indexed[i]);
            i++;
        }
        
        if (ready.isEmpty()) {
            // No task available, jump to next arrival
            time = indexed[i][0];
        } else {
            // Process shortest task
            int[] task = ready.poll();
            result[idx++] = task[2];  // Original index
            time += task[1];          // Advance time
        }
    }
    
    return result;
}
```

--

## Why Handle Empty Ready Queue?

```
If the CPU finishes a task and no other task has arrived yet,
we can't just wait at the current time — we need to JUMP forward
to when the next task arrives.

Example:
  Task 0: arrives at t=1, takes 100 units
  Task 1: arrives at t=200, takes 1 unit

  After Task 0 finishes at t=101, ready queue is empty.
  We jump time to 200 (next arrival) instead of incrementing by 1.
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting original index | Can't return correct order | Store index in task tuple |
| Not handling time jump | Infinite loop when queue empty | Jump to next arrival time |
| Wrong tie-breaker | Wrong task selected | Compare by index when processing times equal |
| Sorting by processing time | Wrong arrival order | Sort by enqueue time first |

--

## Mind-Map Anchor

```
SINGLE-THREADED CPU
         │
         ▼
┌─────────────────────────────────────┐
│ Sort tasks by arrival time          │
│ Min-heap by (processingTime, index) │
│                                     │
│ Loop:                               │
│   1. Move arrived tasks to heap     │
│   2. If heap empty: jump time       │
│   3. Else: process shortest task    │
│                                     │
│ Track original indices!             │
│ O(n log n) time                     │
└─────────────────────────────────────┘
```

**Memory phrase:** "Sort by arrival → min-heap by duration → jump time if empty → process shortest"

--

# Updated MAANG Coverage Map (Complete L5)

| Pattern | Problem | LeetCode | Difficulty | Frequency |
|-----|-----|-----|------|------|
| **Top K Family** |
| 0 | Kth Largest Element | 215 | Medium | 🔥🔥🔥 |
| 1 | Top K Frequent Elements | 347 | Medium | 🔥🔥🔥 |
| 2 | K Closest Points | 973 | Medium | 🔥🔥🔥 |
| 3 | Kth Smallest in Matrix | 378 | Medium | 🔥🔥 |
| **K-Way Merge Family** |
| 4 | Merge K Sorted Lists | 23 | Hard | 🔥🔥🔥 |
| 5 | K Pairs Smallest Sums | 373 | Medium | 🔥🔥 |
| 6 | Smallest Range K Lists | 632 | Hard | 🔥🔥 |
| **Two Heaps Family** |
| 7 | Median from Stream | 295 | Hard | 🔥🔥🔥 |
| 8 | Sliding Window Median | 480 | Hard | 🔥🔥 |
| **Scheduling Family** |
| 9 | Task Scheduler | 621 | Medium | 🔥🔥🔥 |
| 10 | Reorganize String | 767 | Medium | 🔥🔥🔥 |
| 11 | Rearrange K Distance | 358 | Hard | 🔥🔥 |
| 12 | Meeting Rooms II | 253 | Medium | 🔥🔥🔥 |
| 13 | IPO | 502 | Hard | 🔥🔥 |
| **Special Patterns** |
| 14 | Ugly Number II | 264 | Medium | 🔥🔥 |
| **Bonus (Stream/Design)** |
| 15 | Kth Largest in Stream | 703 | Easy | 🔥🔥 |
| 16 | Last Stone Weight | 1046 | Easy | 🔥🔥 |
| 17 | Design Twitter | 355 | Medium | 🔥🔥 |
| 18 | Single-Threaded CPU | 1834 | Medium | 🔥🔥 |

--

# Final Mastery Checklist (Complete L5+ Coverage)

## Tier 1: Must Know (8 patterns)
- [ ] Kth Largest Element (min-heap size K)
- [ ] Top K Frequent Elements (HashMap + heap)
- [ ] K Closest Points (max-heap by distance)
- [ ] Merge K Sorted Lists (min-heap of heads)
- [ ] Find Median from Stream (two heaps)
- [ ] Task Scheduler (max-heap + cooldown)
- [ ] Meeting Rooms II (sort + min-heap of ends)
- [ ] Reorganize String (max-heap + hold prev)

## Tier 2: Interview Favorites (6 patterns)
- [ ] Kth Smallest in Matrix (min-heap BFS)
- [ ] K Pairs Smallest Sums (virtual matrix BFS)
- [ ] Kth Largest in Stream (persistent heap)
- [ ] Last Stone Weight (max-heap smash)
- [ ] IPO (two heaps: capital + profit)
- [ ] Ugly Number II (generate + min-heap)

## Tier 3: Differentiators (5 patterns)
- [ ] Smallest Range K Lists (min-heap + track max)
- [ ] Sliding Window Median (two heaps + lazy deletion)
- [ ] Rearrange K Distance (cooldown queue)
- [ ] Design Twitter (K-way merge design)
- [ ] Single-Threaded CPU (sort + available heap)

--

# The Ultimate Heap Decision Flowchart

```
┌─────────────────────────────────────────────────────────────────────┐
│                    HEAP PROBLEM? START HERE!                         │
└─────────────────────────────────────────────────────────────────────┘
                              │
                              ▼
              ┌───────────────────────────────┐
              │  "Top K" or "Kth" in problem? │
              └───────────────────────────────┘
                    │YES              │NO
                    ▼                 ▼
         ┌──────────────────┐  ┌─────────────────────────┐
         │ OPPOSITE HEAP    │  │ "Merge K sorted"?       │
         │ size K           │  └─────────────────────────┘
         │                  │        │YES         │NO
         │ K largest→MIN    │        ▼            ▼
         │ K smallest→MAX   │  ┌───────────┐  ┌─────────────────┐
         │ K closest→MAX    │  │ MIN-HEAP  │  │ "Median" or     │
         └──────────────────┘  │ of K heads│  │ "split data"?   │
                               └───────────┘  └─────────────────┘
                                                  │YES      │NO
                                                  ▼         ▼
                                           ┌───────────┐ ┌─────────────┐
                                           │ TWO HEAPS │ │ "Schedule"  │
                                           │ max+min   │ │ or "greedy"?│
                                           └───────────┘ └─────────────┘
                                                            │YES
                                                            ▼
                                                     ┌─────────────┐
                                                     │ MAX-HEAP by │
                                                     │ priority +  │
                                                     │ cooldown Q  │
                                                     └─────────────┘
```

--

**You are now L5 MAANG ready for Heap & Priority Queue!** 🚀
