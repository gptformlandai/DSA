# Section 20 — Bit Manipulation Patterns Deep Dive (MAANG L5 Coverage)

--

# INDEX — Quick Navigation

## Core Concepts
| Section | Description |
|-----|-------|
| [The "One Sentence"](#the-one-sentence-that-unlocks-all-bit-manipulation) | Unlocks all bit problems |
| [The 4 Bit Operations](#the-4-bit-operations-your-weapons) | Your weapons |
| [The 6 Magic Formulas](#the-6-magic-formulas-memorize-these) | Must memorize |
| [Decision Tree](#the-master-decision-tree) | Pick your technique |

--

## Foundational Patterns (0-5)
| # | Pattern | LeetCode |
|--|-----|-----|
| 0 | [Single Number (XOR)](#pattern-0-single-number-leetcode-136) | 136 |
| 1 | [Number of 1 Bits](#pattern-1-number-of-1-bits-hamming-weight-leetcode-191) | 191 |
| 2 | [Counting Bits](#pattern-2-counting-bits-leetcode-338) | 338 |
| 3 | [Reverse Bits](#pattern-3-reverse-bits-leetcode-190) | 190 |
| 4 | [Missing Number](#pattern-4-missing-number-leetcode-268) | 268 |
| 5 | [Power of Two](#pattern-5-power-of-two-leetcode-231) | 231 |
| 5b | [Power of Four](#pattern-5b-power-of-four-leetcode-342) | 342 |

## XOR Patterns (6-9)
| # | Pattern | LeetCode |
|--|-----|-----|
| 6 | [Single Number II](#pattern-6-single-number-ii-leetcode-137) | 137 |
| 7 | [Single Number III](#pattern-7-single-number-iii-leetcode-260) | 260 |
| 8 | [Hamming Distance](#pattern-8-hamming-distance-leetcode-461) | 461 |
| 9 | [Total Hamming Distance](#pattern-9-total-hamming-distance-leetcode-477) | 477 |

## Arithmetic with Bits (10-12)
| # | Pattern | LeetCode |
|--|-----|-----|
| 10 | [Sum of Two Integers](#pattern-10-sum-of-two-integers-leetcode-371) | 371 |
| 11 | [Divide Two Integers](#pattern-11-divide-two-integers-leetcode-29) | 29 |
| 12 | [Bitwise AND of Range](#pattern-12-bitwise-and-of-numbers-range-leetcode-201) | 201 |

## Advanced Patterns (13-15)
| # | Pattern | LeetCode |
|--|-----|-----|
| 13 | [Subsets (Bitmask)](#pattern-13-subsets-using-bitmask-leetcode-78) | 78 |
| 14 | [Gray Code](#pattern-14-gray-code-leetcode-89) | 89 |
| 15 | [Maximum XOR](#pattern-15-maximum-xor-of-two-numbers-leetcode-421) | 421 |

## Bonus Patterns (16-20) — Extra Safety Net
| # | Pattern | LeetCode |
|--|-----|-----|
| 16 | [UTF-8 Validation](#pattern-16-utf-8-validation-leetcode-393) | 393 |
| 17 | [Integer Replacement](#pattern-17-integer-replacement-leetcode-397) | 397 |
| 18 | [Binary Watch](#pattern-18-binary-watch-leetcode-401) | 401 |
| 19 | [Complement of Base 10](#pattern-19-complement-of-base-10-integer-leetcode-1009) | 1009 |
| 20 | [Concatenation Binary](#pattern-20-concatenation-of-consecutive-binary-numbers-leetcode-1680) | 1680 |

## Reference Sections
| Section |
|-----|
| [MAANG Coverage Map](#maang-coverage-map) |
| [Bit Manipulation Cheat Sheet](#bit-manipulation-cheat-sheet) |
| [Mastery Checklist](#mastery-checklist) |

--

# The "One Sentence That Unlocks All Bit Manipulation"

> **"Every bit problem is asking: Which BITS differ, and how do I ISOLATE or COMBINE them?"**

That's the entire subject. Every bit manipulation problem is just:
1. **Identify** which bits matter (set bits, differing bits, common bits)
2. **Isolate** them using masks (AND, shifts)
3. **Combine** or **cancel** them using XOR, OR
4. **Count** or **extract** the result

--

## THE JUNIOR DEV CHEAT CARD (Memorize This!)

```
╔═══════════════════════════════════════════════════════════════════════╗
║                     BIT MANIPULATION? USE THIS!                        ║
╠═══════════════════════════════════════════════════════════════════════╣
║                                                                        ║
║  STEP 1: "What am I trying to find out about the bits?"               ║
║                                                                        ║
║  ┌────────────────────────────────────────────────────────────────┐   ║
║  │ "Find unique element"     → XOR everything (pairs cancel!)     │   ║
║  │ "Count set bits"          → n & (n-1) removes rightmost 1      │   ║
║  │ "Is power of 2?"          → n & (n-1) == 0 (only one 1 bit)    │   ║
║  │ "Check if bit i is set"   → (n & (1 << i)) != 0                │   ║
║  │ "Set bit i"               → n | (1 << i)                       │   ║
║  │ "Clear bit i"             → n & ~(1 << i)                      │   ║
║  │ "Toggle bit i"            → n ^ (1 << i)                       │   ║
║  └────────────────────────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE 4 OPERATIONS (What question does each answer?):                  ║
║                                                                        ║
║  ┌──────────┬────────────────────────────────────────────────────┐   ║
║  │ AND (&)  │ "Which bits are BOTH 1?"      → Use to CHECK/CLEAR │   ║
║  │ OR  (|)  │ "Which bits have ANY 1?"      → Use to SET         │   ║
║  │ XOR (^)  │ "Which bits are DIFFERENT?"   → Use to FIND/TOGGLE │   ║
║  │ NOT (~)  │ "Flip all bits"               → Use to CREATE MASK │   ║
║  └──────────┴────────────────────────────────────────────────────┘   ║
║                                                                        ║
║  ═══════════════════════════════════════════════════════════════════  ║
║                                                                        ║
║  THE 2 MAGIC FORMULAS (Memorize these!):                              ║
║                                                                        ║
║  n & (n-1)  →  Removes the rightmost 1 bit                            ║
║                Example: 1100 & 1011 = 1000 (rightmost 1 gone!)        ║
║                                                                        ║
║  n & (-n)   →  Isolates the rightmost 1 bit                           ║
║                Example: 1100 & 0100 = 0100 (only rightmost 1 left!)   ║
║                                                                        ║
╚═══════════════════════════════════════════════════════════════════════╝
```

--

## QUICK START: The 60-Second Bit Manipulation Approach

### The ONE Question That Solves Most Problems:

> **"What do I need to know about the bits, and which operation answers that question?"**

### The Simple Decision Tree:

```
1. "Find the unique/single element?"
   → XOR everything. Pairs cancel (a ^ a = 0), unique survives.

2. "Count something about bits?"
   → Use n & (n-1) in a loop. Each iteration removes one 1-bit.

3. "Check if number has special property (power of 2, etc.)?"
   → Usually involves n & (n-1). Power of 2 has only one 1-bit.

4. "Manipulate a specific bit?"
   → Create a mask with (1 << i), then use AND/OR/XOR.
```

### The XOR Superpower (Most Important!)

XOR has THREE magic properties that solve tons of problems:

```
Property 1: a ^ a = 0     (same values cancel out!)
Property 2: a ^ 0 = a     (XOR with 0 does nothing)
Property 3: a ^ b ^ a = b (XOR is reversible)

WHY THIS MATTERS:
- Array has pairs except one unique? XOR all → pairs cancel → unique remains!
- Array has pairs except TWO uniques? XOR all → get a^b → find differing bit → split!
```

--

## WORKED EXAMPLE: How a Junior Dev Should Think

**Problem:** "Every element appears twice except one. Find the single one."

### My Thinking Process:

**Step 1: What do I know?**
> "Pairs of numbers. One unique. I need to find the unique."

**Step 2: What operation helps with pairs?**
> "XOR! Because a ^ a = 0. Pairs will cancel out!"

**Step 3: What happens if I XOR everything?**
```
[4, 1, 2, 1, 2]

4 ^ 1 ^ 2 ^ 1 ^ 2
= 4 ^ (1 ^ 1) ^ (2 ^ 2)    // Rearrange (XOR is commutative)
= 4 ^ 0 ^ 0                 // Pairs cancel!
= 4                         // Only unique survives!
```

**Step 4: Write the code:**
```java
int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) {
        result ^= num;  // XOR everything
    }
    return result;  // Pairs cancelled, unique remains
}
```

### Another Example: "Check if n is a power of 2"

**My Thinking:**
1. Power of 2 in binary: 1, 10, 100, 1000... (only ONE bit is 1)
2. What if I remove that one 1-bit? I get 0!
3. n & (n-1) removes the rightmost 1-bit
4. If result is 0, there was only one 1-bit → power of 2!

```java
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

**Why n > 0?** Because 0 & (-1) = 0, but 0 is not a power of 2!

--

## The Mental Model: Why Bits Matter

| What You See | What the Computer Sees |
|-------|------------|
| Number 13 | `1101` (four switches: ON-ON-OFF-ON) |
| Number 5 | `0101` (four switches: OFF-ON-OFF-ON) |
| 13 XOR 5 | `1000` (which switches DIFFER?) |
| 13 AND 5 | `0101` (which switches are BOTH on?) |
| 13 OR 5 | `1101` (which switches are AT LEAST ONE on?) |

**The Insight:** Numbers are just rows of ON/OFF switches. Bit operations ask questions about these switches!

--

# The 4 Bit Operations (Your "Weapons")

## The Big Picture

| Operation | Symbol | Question It Answers | Memory Trick |
|------|----|-----------|-------|
| **AND** | `&` | "Which bits are BOTH 1?" | "Both must agree" |
| **OR** | `\|` | "Which bits have AT LEAST ONE 1?" | "Either is enough" |
| **XOR** | `^` | "Which bits are DIFFERENT?" | "Difference detector" |
| **NOT** | `~` | "Flip all bits" | "Opposite day" |

--

## Operation 1: AND (`&`) — "Both Must Agree"

**What it does:** Returns 1 only if BOTH bits are 1.

```
  1 1 0 1  (13)
& 0 1 0 1  (5)
-----
  0 1 0 1  (5)
```

**Use Cases:**
- **Check if bit is set:** `(n & (1 << i)) != 0`
- **Clear bits:** `n & mask` keeps only bits where mask is 1
- **Check odd/even:** `(n & 1) == 0` means even

**Memory Trick:** AND is like a strict bouncer — both must have ID (both must be 1).

--

## Operation 2: OR (`|`) — "Either Is Enough"

**What it does:** Returns 1 if AT LEAST ONE bit is 1.

```
  1 1 0 1  (13)
| 0 1 0 1  (5)
-----
  1 1 0 1  (13)
```

**Use Cases:**
- **Set a bit:** `n | (1 << i)` turns on bit i
- **Combine flags:** `READ | WRITE | EXECUTE`

**Memory Trick:** OR is like a lenient bouncer — either one having ID is enough.

--

## Operation 3: XOR (`^`) — "Difference Detector"

**What it does:** Returns 1 if bits are DIFFERENT.

```
  1 1 0 1  (13)
^ 0 1 0 1  (5)
-----
  1 0 0 0  (8)
```

**The Three Magic Properties of XOR:**
1. **Self-cancellation:** `a ^ a = 0` (same values cancel out!)
2. **Identity:** `a ^ 0 = a` (XOR with 0 does nothing)
3. **Commutative:** `a ^ b ^ c = c ^ a ^ b` (order doesn't matter)

**Use Cases:**
- **Find single unique number:** XOR all elements, pairs cancel
- **Toggle a bit:** `n ^ (1 << i)` flips bit i
- **Swap without temp:** `a ^= b; b ^= a; a ^= b;`
- **Find differing bits:** `a ^ b` shows where a and b differ

**Memory Trick:** XOR is like a "spot the difference" game — it highlights what's different.

--

## Operation 4: NOT (`~`) — "Opposite Day"

**What it does:** Flips ALL bits (0→1, 1→0).

```
~ 0 0 0 0 1 1 0 1  (13)
= 1 1 1 1 0 0 1 0  (-14 in two's complement)
```

**Important:** `~n = -(n+1)` due to two's complement!

**Use Cases:**
- **Clear a bit:** `n & ~(1 << i)` clears bit i
- **Create mask:** `~0` gives all 1s

--

## Shift Operations — "Move the Bits"

### Left Shift (`<<`) — Multiply by 2

```
13 << 1 = 26    (1101 → 11010)
13 << 2 = 52    (1101 → 110100)

Formula: n << k = n × 2^k
```

### Right Shift (`>>`) — Divide by 2

```
13 >> 1 = 6     (1101 → 0110)
13 >> 2 = 3     (1101 → 0011)

Formula: n >> k = n / 2^k (integer division)
```

**Memory Trick:** 
- Left shift = bits move LEFT, number gets BIGGER (multiply)
- Right shift = bits move RIGHT, number gets SMALLER (divide)

--

# The 6 Magic Formulas (MEMORIZE THESE!)

These 6 formulas appear in **90% of bit manipulation problems**. Master them and you've mastered bit manipulation.

--

## Formula 1: Check if i-th bit is set

```java
(n & (1 << i)) != 0
```

### The Mental Picture

Imagine n as a row of light bulbs. You want to check if bulb #i is ON.

**Step 1:** Create a "checker" with only bulb i ON → `1 << i`
**Step 2:** AND with n → if bulb i was ON in n, result is non-zero

### Visual Dry Run

**Question:** Is bit 2 set in n = 13?

```
n = 13 in binary:

Position:   3   2   1   0
            ↓   ↓   ↓   ↓
n       =   1   1   0   1   (13)

Step 1: Create mask (1 << 2)
1       =   0   0   0   1
1 << 2  =   0   1   0   0   (shift left 2 positions)

Step 2: AND them
n       =   1   1   0   1
mask    =   0   1   0   0
            ─────────────
n & mask=   0   1   0   0   = 4 (non-zero!)

Result: 4 != 0 → YES, bit 2 is set ✓
```

**Another example:** Is bit 1 set in n = 13?

```
n       =   1   1   0   1   (13)
1 << 1  =   0   0   1   0   (mask for bit 1)
            ─────────────
n & mask=   0   0   0   0   = 0

Result: 0 == 0 → NO, bit 1 is NOT set ✓
```

### Mind Map

```
┌─────────────────────────────────────────────────────────┐
│           CHECK IF BIT i IS SET                         │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Formula: (n & (1 << i)) != 0                          │
│                                                         │
│   Mental Model:                                         │
│   ┌─────────────────────────────────────┐               │
│   │  n = [?][?][?][?]  ← row of bulbs   │               │
│   │         ↑                           │               │
│   │      bit i                          │               │
│   │                                     │               │
│   │  mask = [0][0][1][0] ← spotlight    │               │
│   │              ↑       on bit i       │               │
│   │           only 1                    │               │
│   │                                     │               │
│   │  n & mask → shows ONLY bit i        │               │
│   │  If non-zero → bit was ON           │               │
│   └─────────────────────────────────────┘               │
│                                                         │
│   Memory Trick: "Spotlight on bit i"                    │
│   1 << i creates a spotlight, AND reveals the truth     │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Use Cases
- Check if a number is odd: `(n & 1) != 0` (bit 0 set = odd)
- Check permissions: `(userFlags & READ_PERMISSION) != 0`
- Iterate through set bits

--

## Formula 2: Set the i-th bit (Turn ON)

```java
n | (1 << i)
```

### The Mental Picture

You want to turn ON bulb #i, regardless of its current state.

**Step 1:** Create a mask with only bit i ON → `1 << i`
**Step 2:** OR with n → forces bit i to become 1

### Visual Dry Run

**Question:** Set bit 1 in n = 13

```
n = 13 in binary:

Position:   3   2   1   0
            ↓   ↓   ↓   ↓
n       =   1   1   0   1   (13)
                ↑
            bit 1 is OFF, we want it ON

Step 1: Create mask (1 << 1)
1 << 1  =   0   0   1   0

Step 2: OR them (OR = "at least one 1 wins")
n       =   1   1   0   1
mask    =   0   0   1   0
            ─────────────
n | mask=   1   1   1   1   = 15

Result: 15 (bit 1 is now ON!) ✓
```

**What if bit was already ON?** Set bit 2 in n = 13

```
n       =   1   1   0   1   (13, bit 2 already ON)
1 << 2  =   0   1   0   0
            ─────────────
n | mask=   1   1   0   1   = 13 (unchanged, still ON)

Result: No harm done! OR is safe to use even if bit is already set.
```

### Mind Map

```
┌─────────────────────────────────────────────────────────┐
│           SET BIT i (TURN ON)                           │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Formula: n | (1 << i)                                 │
│                                                         │
│   Mental Model:                                         │
│   ┌─────────────────────────────────────┐               │
│   │  Before: [1][1][0][1]               │               │
│   │                 ↑                   │               │
│   │              bit 1 OFF              │               │
│   │                                     │               │
│   │  mask:    [0][0][1][0]              │               │
│   │                 ↑                   │               │
│   │           "turn this ON"            │               │
│   │                                     │               │
│   │  After:   [1][1][1][1]              │               │
│   │                 ↑                   │               │
│   │              bit 1 ON!              │               │
│   └─────────────────────────────────────┘               │
│                                                         │
│   Memory Trick: "OR forces ON"                          │
│   OR with 1 = always becomes 1                          │
│   OR with 0 = stays the same                            │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Use Cases
- Grant permission: `userFlags | WRITE_PERMISSION`
- Mark element as visited: `visited | (1 << index)`
- Build a number bit by bit

--

## Formula 3: Clear the i-th bit (Turn OFF)

```java
n & ~(1 << i)
```

### The Mental Picture

You want to turn OFF bulb #i, regardless of its current state.

**Step 1:** Create mask with bit i ON → `1 << i`
**Step 2:** Flip it (NOT) → now bit i is OFF, all others ON → `~(1 << i)`
**Step 3:** AND with n → bit i becomes 0, others unchanged

### Visual Dry Run

**Question:** Clear bit 2 in n = 13

```
n = 13 in binary:

Position:   3   2   1   0
            ↓   ↓   ↓   ↓
n       =   1   1   0   1   (13)
                ↑
            bit 2 is ON, we want it OFF

Step 1: Create mask (1 << 2)
1 << 2  =   0   1   0   0

Step 2: Flip it with NOT
~(1<<2) =   1   0   1   1   (all 1s EXCEPT bit 2)

Step 3: AND them (AND = "both must be 1")
n       =   1   1   0   1
~mask   =   1   0   1   1
            ─────────────
n & ~m  =   1   0   0   1   = 9

Result: 9 (bit 2 is now OFF!) ✓
```

### Why NOT then AND?

```
Think of ~(1 << i) as a "hole puncher":

Original mask:  [0][0][1][0]  ← has a 1 at position i
After NOT:      [1][1][0][1]  ← has a HOLE at position i

When you AND with this:
- Bits at positions with 1 → PASS THROUGH unchanged
- Bit at position with 0 → BLOCKED (becomes 0)

It's like a stencil that blocks only bit i!
```

### Mind Map

```
┌─────────────────────────────────────────────────────────┐
│           CLEAR BIT i (TURN OFF)                        │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Formula: n & ~(1 << i)                                │
│                                                         │
│   Mental Model: "Stencil with a hole"                   │
│   ┌─────────────────────────────────────┐               │
│   │                                     │               │
│   │  1 << i   = [0][0][1][0]  (marker)  │               │
│   │                   ↑                 │               │
│   │                                     │               │
│   │  ~(1 << i)= [1][1][0][1]  (stencil) │               │
│   │                   ↑                 │               │
│   │               "hole"                │               │
│   │                                     │               │
│   │  n & stencil → bit i falls through  │               │
│   │                  the hole (becomes 0)│               │
│   └─────────────────────────────────────┘               │
│                                                         │
│   Memory Trick: "NOT creates hole, AND punches through" │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Use Cases
- Revoke permission: `userFlags & ~WRITE_PERMISSION`
- Unmark element: `visited & ~(1 << index)`
- Clear specific flags

--

## Formula 4: Toggle the i-th bit (Flip)

```java
n ^ (1 << i)
```

### The Mental Picture

You want to FLIP bulb #i: if ON → OFF, if OFF → ON.

**Key insight:** XOR with 1 flips, XOR with 0 keeps same.

### Visual Dry Run

**Question:** Toggle bit 2 in n = 13 (bit 2 is ON)

```
Position:   3   2   1   0
n       =   1   1   0   1   (13, bit 2 is ON)
1 << 2  =   0   1   0   0
            ─────────────
n ^ mask=   1   0   0   1   = 9 (bit 2 flipped to OFF!)
```

**Question:** Toggle bit 1 in n = 13 (bit 1 is OFF)

```
n       =   1   1   0   1   (13, bit 1 is OFF)
1 << 1  =   0   0   1   0
            ─────────────
n ^ mask=   1   1   1   1   = 15 (bit 1 flipped to ON!)
```

### Why XOR Flips

```
XOR Truth Table:
  0 ^ 0 = 0  (same → 0)
  0 ^ 1 = 1  (different → 1)
  1 ^ 0 = 1  (different → 1)
  1 ^ 1 = 0  (same → 0)

Key insight:
  bit ^ 0 = bit  (XOR with 0 = no change)
  bit ^ 1 = ~bit (XOR with 1 = flip!)

So when mask has 1 at position i:
  - That bit gets FLIPPED
  - All other bits (XOR with 0) stay SAME
```

### Mind Map

```
┌─────────────────────────────────────────────────────────┐
│           TOGGLE BIT i (FLIP)                           │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Formula: n ^ (1 << i)                                 │
│                                                         │
│   Mental Model: "Light switch"                          │
│   ┌─────────────────────────────────────┐               │
│   │                                     │               │
│   │   XOR with 1 = FLIP the switch      │               │
│   │   XOR with 0 = LEAVE it alone       │               │
│   │                                     │               │
│   │   Before: [1][1][0][1]              │               │
│   │                  ↑                  │               │
│   │               bit 1 OFF             │               │
│   │                                     │               │
│   │   mask:   [0][0][1][0]              │               │
│   │                  ↑                  │               │
│   │            "flip this"              │               │
│   │                                     │               │
│   │   After:  [1][1][1][1]              │               │
│   │                  ↑                  │               │
│   │               bit 1 ON!             │               │
│   └─────────────────────────────────────┘               │
│                                                         │
│   Memory Trick: "XOR = eXclusive OR = flip if different"│
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Use Cases
- Toggle feature flag: `settings ^ DARK_MODE`
- Swap without temp: `a ^= b; b ^= a; a ^= b;`
- Flip specific bits in encryption

--

## Formula 5: Remove the Rightmost Set Bit

```java
n & (n - 1)
```

**This is the MOST IMPORTANT formula. It appears everywhere!**

### The Mental Picture

Imagine n as a row of bulbs. You want to turn OFF the rightmost bulb that's currently ON.

### Why n - 1 is Magic

When you subtract 1 from a number:
- The rightmost 1 becomes 0
- All 0s to its right become 1s
- Everything to the left stays the same

```
n     = 1 0 1 0 0   (20)
              ↑
        rightmost 1

n - 1 = 1 0 0 1 1   (19)
              ↑
        this 1 became 0, and 0s to right became 1s

n & (n-1):
n     = 1 0 1 0 0
n-1   = 1 0 0 1 1
        ─────────
result= 1 0 0 0 0   (16) ← rightmost 1 is GONE!
```

### Visual Dry Run (Multiple Examples)

**Example 1:** n = 12 (1100)

```
n     = 1 1 0 0   (12)
            ↑
      rightmost 1 at position 2

n - 1 = 1 0 1 1   (11)
            ↑
      this became 0, bits to right flipped

n & (n-1):
n     = 1 1 0 0
n-1   = 1 0 1 1
        ───────
result= 1 0 0 0   (8) ✓
```

**Example 2:** n = 7 (0111)

```
n     = 0 1 1 1   (7)
              ↑
      rightmost 1 at position 0

n - 1 = 0 1 1 0   (6)

n & (n-1):
n     = 0 1 1 1
n-1   = 0 1 1 0
        ───────
result= 0 1 1 0   (6) ✓
```

**Example 3:** n = 8 (1000) - Power of 2!

```
n     = 1 0 0 0   (8)
        ↑
      ONLY one 1 (it's a power of 2!)

n - 1 = 0 1 1 1   (7)

n & (n-1):
n     = 1 0 0 0
n-1   = 0 1 1 1
        ───────
result= 0 0 0 0   (0) ← Result is 0 because there was only one 1!

This is why n & (n-1) == 0 means n is a power of 2!
```

### Mind Map

```
┌─────────────────────────────────────────────────────────┐
│           REMOVE RIGHTMOST SET BIT                      │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Formula: n & (n - 1)                                  │
│                                                         │
│   The Magic of n - 1:                                   │
│   ┌─────────────────────────────────────┐               │
│   │                                     │               │
│   │   n     = X X X 1 0 0 0             │               │
│   │               ↑                     │               │
│   │         rightmost 1                 │               │
│   │                                     │               │
│   │   n - 1 = X X X 0 1 1 1             │               │
│   │               ↑ ↑ ↑ ↑               │               │
│   │            flipped!                 │               │
│   │                                     │               │
│   │   n & (n-1) = X X X 0 0 0 0         │               │
│   │                   ↑                 │               │
│   │             rightmost 1 GONE!       │               │
│   └─────────────────────────────────────┘               │
│                                                         │
│   Use Cases:                                            │
│   • Count set bits: loop until n becomes 0              │
│   • Check power of 2: n & (n-1) == 0                    │
│   • DP for counting bits: dp[i] = dp[i & (i-1)] + 1     │
│                                                         │
│   Memory Trick: "n-1 flips from rightmost 1 onwards,    │
│                  AND kills that rightmost 1"            │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Use Cases (This formula is EVERYWHERE!)

| Problem | How It's Used |
|-----|--------|
| Count set bits | Loop: `while(n) { n &= n-1; count++; }` |
| Power of 2 | `n > 0 && (n & (n-1)) == 0` |
| Counting Bits DP | `dp[i] = dp[i & (i-1)] + 1` |

--

## Formula 6: Isolate the Rightmost Set Bit

```java
n & (-n)
```

### The Mental Picture

You want to KEEP only the rightmost 1, turning all other bits to 0.

### Understanding Two's Complement (-n)

In two's complement, `-n` is computed as:
1. Flip all bits of n (NOT)
2. Add 1

This has a magical effect: it creates a number where only the rightmost 1 of n survives!

### Visual Dry Run

**Example 1:** n = 12 (1100)

```
Step 1: What is -n?
n      = 0...0 1 1 0 0   (12)
~n     = 1...1 0 0 1 1   (flip all bits)
~n + 1 = 1...1 0 1 0 0   (-12 in two's complement)

Step 2: n & (-n)
n      = 0...0 1 1 0 0
-n     = 1...1 0 1 0 0
         ─────────────
n & -n = 0...0 0 1 0 0   (4) ← only rightmost 1 survives!
```

**Example 2:** n = 10 (1010)

```
n      = 1 0 1 0   (10)
-n     = 0 1 1 0   (-10, simplified)

Actually in full:
n      = 0...0 1 0 1 0
~n     = 1...1 0 1 0 1
~n + 1 = 1...1 0 1 1 0

n & -n:
n      = 0...0 1 0 1 0
-n     = 1...1 0 1 1 0
         ─────────────
result = 0...0 0 0 1 0   (2) ← rightmost 1 isolated!
```

**Example 3:** n = 8 (1000)

```
n      = 1 0 0 0   (8)
-n     = 1 0 0 0   (-8, in two's complement for this range)

n & -n = 1 0 0 0   (8) ← the only 1 is isolated (same as n!)
```

### Why Does This Work?

```
The key insight:

n      = X X X 1 0 0 0   (some bits, then rightmost 1, then 0s)
              ↑
        rightmost 1

~n     = Y Y Y 0 1 1 1   (flipped)
              ↑
        this became 0

~n + 1 = Y Y Y 1 0 0 0   (adding 1 ripples through)
              ↑
        back to 1!

But Y = ~X, so when we AND:
n      = X X X 1 0 0 0
-n     = Y Y Y 1 0 0 0   (where Y = ~X)
         ─────────────
n & -n = 0 0 0 1 0 0 0   (X & ~X = 0, but 1 & 1 = 1!)
```

### Mind Map

```
┌─────────────────────────────────────────────────────────┐
│           ISOLATE RIGHTMOST SET BIT                     │
├─────────────────────────────────────────────────────────┤
│                                                         │
│   Formula: n & (-n)                                     │
│                                                         │
│   Two's Complement Magic:                               │
│   ┌─────────────────────────────────────┐               │
│   │                                     │               │
│   │   n    = X X X 1 0 0 0              │               │
│   │               ↑                     │               │
│   │         rightmost 1                 │               │
│   │                                     │               │
│   │   -n   = Y Y Y 1 0 0 0              │               │
│   │         (where Y = ~X)              │               │
│   │               ↑                     │               │
│   │         same position!              │               │
│   │                                     │               │
│   │   n&-n = 0 0 0 1 0 0 0              │               │
│   │               ↑                     │               │
│   │         ONLY this 1 survives!       │               │
│   └─────────────────────────────────────┘               │
│                                                         │
│   Use Cases:                                            │
│   • Find differing bit (Single Number III)              │
│   • Binary Indexed Tree (Fenwick Tree)                  │
│   • Find lowest set bit position                        │
│                                                         │
│   Memory Trick: "Negative mirrors around rightmost 1,   │
│                  AND keeps only that mirror point"      │
│                                                         │
└─────────────────────────────────────────────────────────┘
```

### Use Cases

| Problem | How It's Used |
|-----|--------|
| Single Number III | Find a bit where two unique numbers differ |
| Fenwick Tree | Navigate tree structure |
| Find bit position | `Integer.numberOfTrailingZeros(n & -n)` |

--

# The 6 Formulas - Quick Reference Card

```
┌────────────────────────────────────────────────────────────────────┐
│                    THE 6 MAGIC FORMULAS                            │
├────────────────────────────────────────────────────────────────────┤
│                                                                    │
│  ┌──────────────────┬─────────────────┬─────────────────────────┐  │
│  │ OPERATION        │ FORMULA         │ MEMORY TRICK            │  │
│  ├──────────────────┼─────────────────┼─────────────────────────┤  │
│  │ 1. Check bit i   │ n & (1 << i)    │ "Spotlight on bit i"    │  │
│  │                  │                 │                         │  │
│  │ 2. Set bit i     │ n | (1 << i)    │ "OR forces ON"          │  │
│  │                  │                 │                         │  │
│  │ 3. Clear bit i   │ n & ~(1 << i)   │ "Stencil with hole"     │  │
│  │                  │                 │                         │  │
│  │ 4. Toggle bit i  │ n ^ (1 << i)    │ "XOR = flip switch"     │  │
│  │                  │                 │                         │  │
│  │ 5. Remove right  │ n & (n - 1)     │ "n-1 flips, AND kills"  │  │
│  │    most 1        │                 │ ⭐ MOST IMPORTANT!      │  │
│  │                  │                 │                         │  │
│  │ 6. Isolate right │ n & (-n)        │ "Negative mirrors,      │  │
│  │    most 1        │                 │  AND keeps mirror point"│  │
│  └──────────────────┴─────────────────┴─────────────────────────┘  │
│                                                                    │
│  VISUAL SUMMARY:                                                   │
│                                                                    │
│  n = 1 0 1 1 0 0  (44)                                             │
│          ↑                                                         │
│      rightmost 1                                                   │
│                                                                    │
│  n & (n-1) = 1 0 1 0 0 0  (40)  ← rightmost 1 REMOVED              │
│  n & (-n)  = 0 0 0 1 0 0  (4)   ← rightmost 1 ISOLATED             │
│                                                                    │
└────────────────────────────────────────────────────────────────────┘
```

--

# The Master Decision Tree

When you see a bit manipulation problem, ask:

```
┌─────────────────────────────────────────────────────────────┐
│                    BIT MANIPULATION PROBLEM                  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
        ┌─────────────────────────────────────────┐
        │  1. "Find unique/single element?"       │
        └─────────────────────────────────────────┘
                    │YES                │NO
                    ▼                   ▼
        ┌───────────────────┐   ┌─────────────────────────────┐
        │ XOR all elements  │   │ 2. "Count bits or check     │
        │ Pairs cancel out  │   │    power of 2?"             │
        │                   │   └─────────────────────────────┘
        │ Patterns: 0,4,6,7 │           │YES            │NO
        └───────────────────┘           ▼               ▼
                                ┌───────────────┐  ┌──────────────────────┐
                                │ n & (n-1)     │  │ 3. "Arithmetic       │
                                │ removes       │  │    without +/-/*/%?" │
                                │ rightmost bit │  └──────────────────────┘
                                │               │      │YES         │NO
                                │ Patterns: 1,5 │      ▼            ▼
                                └───────────────┘  ┌────────────┐ ┌─────────────┐
                                               │ Bit shifts │ │ 4. Generate │
                                               │ + carry    │ │    subsets? │
                                               │            │ └─────────────┘
                                               │ Patterns:  │     │YES
                                               │ 10, 11     │     ▼
                                               └────────────┘ ┌────────────┐
                                                              │ Bitmask    │
                                                              │ 0 to 2^n-1│
                                                              │            │
                                                              │ Pattern:13 │
                                                              └────────────┘
```

--

# The 5-Question Template (For Every Bit Problem)

```
┌────────────────────────────────────────────────────────────────────┐
│ 1. WHAT BITS MATTER?                                               │
│    □ All bits (count them)                                         │
│    □ Specific position (i-th bit)                                  │
│    □ Differing bits (XOR)                                          │
│    □ Common bits (AND)                                             │
│    □ Rightmost set bit                                             │
├────────────────────────────────────────────────────────────────────┤
│ 2. WHICH OPERATION?                                                │
│    □ XOR (find differences, cancel pairs)                          │
│    □ AND (check/clear bits, find common)                           │
│    □ OR (set bits, combine)                                        │
│    □ Shifts (multiply/divide by 2, iterate bits)                   │
├────────────────────────────────────────────────────────────────────┤
│ 3. WHICH MAGIC FORMULA?                                            │
│    □ n & (n-1) — remove rightmost set bit                          │
│    □ n & (-n) — isolate rightmost set bit                          │
│    □ n & (1 << i) — check i-th bit                                 │
│    □ n ^ (1 << i) — toggle i-th bit                                │
├────────────────────────────────────────────────────────────────────┤
│ 4. EDGE CASES?                                                     │
│    □ n = 0                                                         │
│    □ n = negative (two's complement)                               │
│    □ Overflow (use long)                                           │
│    □ 32-bit vs 64-bit                                              │
├────────────────────────────────────────────────────────────────────┤
│ 5. COMPLEXITY?                                                     │
│    □ O(1) — single operation                                       │
│    □ O(log n) — iterate through bits                               │
│    □ O(32) — fixed 32 bits                                         │
│    □ O(n) — iterate through array                                  │
└────────────────────────────────────────────────────────────────────┘
```

--

# PATTERN 0: Single Number (LeetCode 136)

## Pattern Recognition Signal

**When you see:** "every element appears twice except one", "find the unique element", "O(1) space"

**Instant thought:** "XOR everything! Pairs cancel, unique survives!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: [4, 1, 2, 1, 2]

Every number appears TWICE except one.
Find the one that appears only ONCE.

4 appears: 1 time ← THIS IS THE ANSWER
1 appears: 2 times
2 appears: 2 times
```

### Why XOR?

```
XOR has THREE magic properties:

1. a ^ a = 0     (same numbers cancel!)
2. a ^ 0 = a     (XOR with 0 does nothing)
3. a ^ b = b ^ a (order doesn't matter)

So if we XOR all numbers:
  4 ^ 1 ^ 2 ^ 1 ^ 2
= 4 ^ (1 ^ 1) ^ (2 ^ 2)   (reorder - pairs together)
= 4 ^ 0 ^ 0               (pairs cancel to 0)
= 4                       (XOR with 0 does nothing)

The unique number survives!
```

### The XOR Truth Table (Memorize This!)

```
A | B | A ^ B
-|--|---
0 | 0 |   0    (same → 0)
0 | 1 |   1    (different → 1)
1 | 0 |   1    (different → 1)
1 | 1 |   0    (same → 0)

Key insight: XOR answers "Are these bits DIFFERENT?"
```

### The Algorithm in Plain English

```
1. Start with result = 0
2. XOR every number into result
3. Pairs cancel out (a ^ a = 0)
4. Only the unique number remains
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `[4, 1, 2, 1, 2]`

```
Let's trace in binary:
  4 = 100
  1 = 001
  2 = 010

═══════════════════════════════════════════════════════════

Initial: result = 0 (binary: 000)

═══════════════════════════════════════════════════════════

XOR with 4:
  result = 0 ^ 4
  
    000
  ^ 100
  ---
    100  = 4
  
  result = 4

═══════════════════════════════════════════════════════════

XOR with 1:
  result = 4 ^ 1
  
    100
  ^ 001
  ---
    101  = 5
  
  result = 5

═══════════════════════════════════════════════════════════

XOR with 2:
  result = 5 ^ 2
  
    101
  ^ 010
  ---
    111  = 7
  
  result = 7

═══════════════════════════════════════════════════════════

XOR with 1 (second occurrence):
  result = 7 ^ 1
  
    111
  ^ 001
  ---
    110  = 6
  
  result = 6
  
  "The first 1 is being cancelled out!"

═══════════════════════════════════════════════════════════

XOR with 2 (second occurrence):
  result = 6 ^ 2
  
    110
  ^ 010
  ---
    100  = 4
  
  result = 4
  
  "The first 2 is being cancelled out!"

═══════════════════════════════════════════════════════════

Final result = 4 ✓

The pairs (1,1) and (2,2) cancelled out!
Only 4 (the unique number) remains!
```

--

## The Code (With Line-by-Line Explanation)

```java
int singleNumber(int[] nums) {
    int result = 0;  // Start with 0 (XOR identity)
    
    for (int num : nums) {
        result ^= num;  // XOR each number into result
        // Pairs will cancel: a ^ a = 0
        // Unique survives: a ^ 0 = a
    }
    
    return result;  // Only the unique number remains
}
```

--

## Why O(1) Space?

```
We only use ONE variable (result).
No HashMap, no sorting, no extra array.
Just XOR magic!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using HashMap | Works but O(n) space | XOR is O(1) space |
| Sorting first | Works but O(n log n) time | XOR is O(n) time |
| Forgetting XOR properties | Can't solve without them | Memorize: a^a=0, a^0=a |

--

## Mind-Map Anchor

```
SINGLE NUMBER
      │
      ▼
┌─────────────────────────┐
│ XOR all elements        │
│ a ^ a = 0 (pairs cancel)│
│ a ^ 0 = a (identity)    │
│ Unique survives!        │
│ O(n) time, O(1) space   │
└─────────────────────────┘
```

**Memory phrase:** "XOR all, pairs cancel, unique survives"

--

# PATTERN 1: Number of 1 Bits / Hamming Weight (LeetCode 191)

## Pattern Recognition Signal

**When you see:** "count set bits", "number of 1s", "Hamming weight"

**Instant thought:** "n & (n-1) removes rightmost 1! Count iterations!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 11 (binary: 1011)

Count how many 1s are in the binary representation.

1011 has three 1s → answer = 3
```

### The Magic Formula: n & (n-1)

```
This formula REMOVES the rightmost 1 bit!

Let's see why:

n     = 1100  (12 in decimal)
n-1   = 1011  (11 in decimal)

When you subtract 1:
- The rightmost 1 becomes 0
- All 0s to its right become 1s

n     = 1100
n-1   = 1011
        ↑↑↑↑
        These bits are DIFFERENT from rightmost 1 onward!

n & (n-1):
    1100
  & 1011
  ---
    1000  ← Rightmost 1 is GONE!
```

### Visual Proof

```
n     = 101100  (44)
n-1   = 101011  (43)
n&n-1 = 101000  (40)  ← rightmost 1 removed!

n     = 101000  (40)
n-1   = 100111  (39)
n&n-1 = 100000  (32)  ← rightmost 1 removed!

n     = 100000  (32)
n-1   = 011111  (31)
n&n-1 = 000000  (0)   ← rightmost 1 removed!

We did 3 operations → 3 ones in original number!
```

### The Algorithm in Plain English

```
1. Initialize count = 0
2. While n is not 0:
   a. Remove rightmost 1: n = n & (n-1)
   b. Increment count
3. Return count
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `n = 11` (binary: `1011`)

```
═══════════════════════════════════════════════════════════

Initial: n = 1011, count = 0

═══════════════════════════════════════════════════════════

Iteration 1:
  n     = 1011
  n-1   = 1010
  n&n-1 = 1010
  
  n = 1010, count = 1
  "Removed the rightmost 1 (at position 0)"

═══════════════════════════════════════════════════════════

Iteration 2:
  n     = 1010
  n-1   = 1001
  n&n-1 = 1000
  
  n = 1000, count = 2
  "Removed the rightmost 1 (at position 1)"

═══════════════════════════════════════════════════════════

Iteration 3:
  n     = 1000
  n-1   = 0111
  n&n-1 = 0000
  
  n = 0000, count = 3
  "Removed the rightmost 1 (at position 3)"

═══════════════════════════════════════════════════════════

n == 0, loop exits

Return count = 3 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int hammingWeight(int n) {
    int count = 0;
    
    while (n != 0) {
        n = n & (n - 1);  // Remove rightmost 1
        count++;          // Count it
    }
    
    return count;
}
```

--

## Why This is Better Than Checking Each Bit

```
Method 1: Check each of 32 bits → O(32) = O(1) but always 32 iterations

Method 2: n & (n-1) → O(number of 1s)
  - If n = 1000000000 (one 1), only 1 iteration!
  - If n = 1111111111 (ten 1s), only 10 iterations!

n & (n-1) is faster when there are few 1s!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using n >> 1 in loop | Works but always 32 iterations | n & (n-1) is faster |
| Forgetting unsigned | Java int is signed | Use `n != 0` not `n > 0` |

--

## Mind-Map Anchor

```
COUNT SET BITS
      │
      ▼
┌─────────────────────────┐
│ n & (n-1) removes       │
│ rightmost 1 bit         │
│ Count iterations        │
│ O(number of 1s) time    │
└─────────────────────────┘
```

**Memory phrase:** "n & (n-1) kills rightmost 1, count how many kills"

--

> "n & (n-1) removes rightmost 1. Count iterations until n = 0."

## Mind-Map Anchor

**n & (n-1) · removes rightmost 1 · count iterations**

--

# PATTERN 2: Counting Bits (LeetCode 338)

## Pattern Recognition Signal

**When you see:** "count 1 bits for all numbers 0 to n", "return array of bit counts", "O(n) solution for counting bits"

**Instant thought:** "DP with n & (n-1)! Each number has one more 1 than the number with its rightmost 1 removed!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 5

For each number from 0 to 5, count how many 1s are in its binary:
  0 = 000 → 0 ones
  1 = 001 → 1 one
  2 = 010 → 1 one
  3 = 011 → 2 ones
  4 = 100 → 1 one
  5 = 101 → 2 ones

Output: [0, 1, 1, 2, 1, 2]
```

### Why DP with n & (n-1)?

```
The magic insight: i & (i-1) removes the rightmost 1 bit!

So if I know the bit count for (i & (i-1)), then:
  countBits[i] = countBits[i & (i-1)] + 1
                 ↑                      ↑
                 "number with one       "add back the
                  less 1 bit"            1 we removed"

Example:
  i = 5 (101)
  i & (i-1) = 5 & 4 = 101 & 100 = 100 = 4
  
  countBits[5] = countBits[4] + 1
               = 1 + 1
               = 2 ✓
```

### The Algorithm in Plain English

```
1. Create array ans of size n+1
2. ans[0] = 0 (base case: 0 has no 1 bits)
3. For each i from 1 to n:
   - Remove rightmost 1: j = i & (i-1)
   - ans[i] = ans[j] + 1 (one more 1 than j)
4. Return ans
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `n = 5`

```
═══════════════════════════════════════════════════════════

Base case: ans[0] = 0

  0 = 000 → 0 ones (no 1 bits to count)

═══════════════════════════════════════════════════════════

i = 1:
  Binary: 001
  
  i & (i-1) = 1 & 0 = 001 & 000 = 000 = 0
  
  ans[1] = ans[0] + 1 = 0 + 1 = 1 ✓
  
  "1 has one more 1-bit than 0"

═══════════════════════════════════════════════════════════

i = 2:
  Binary: 010
  
  i & (i-1) = 2 & 1 = 010 & 001 = 000 = 0
  
  ans[2] = ans[0] + 1 = 0 + 1 = 1 ✓
  
  "2 has one more 1-bit than 0"

═══════════════════════════════════════════════════════════

i = 3:
  Binary: 011
  
  i & (i-1) = 3 & 2 = 011 & 010 = 010 = 2
  
  ans[3] = ans[2] + 1 = 1 + 1 = 2 ✓
  
  "3 has one more 1-bit than 2"

═══════════════════════════════════════════════════════════

i = 4:
  Binary: 100
  
  i & (i-1) = 4 & 3 = 100 & 011 = 000 = 0
  
  ans[4] = ans[0] + 1 = 0 + 1 = 1 ✓
  
  "4 has one more 1-bit than 0"

═══════════════════════════════════════════════════════════

i = 5:
  Binary: 101
  
  i & (i-1) = 5 & 4 = 101 & 100 = 100 = 4
  
  ans[5] = ans[4] + 1 = 1 + 1 = 2 ✓
  
  "5 has one more 1-bit than 4"

═══════════════════════════════════════════════════════════

Final Result: [0, 1, 1, 2, 1, 2] ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int[] countBits(int n) {
    int[] ans = new int[n + 1];  // Array to store bit counts for 0 to n
    
    // ans[0] = 0 by default (0 has no 1 bits)
    
    for (int i = 1; i <= n; i++) {
        // i & (i-1) removes rightmost 1 bit
        // So ans[i] = ans[number with one less 1] + 1
        ans[i] = ans[i & (i - 1)] + 1;
    }
    
    return ans;
}
```

--

## Why This is O(n) and Not O(n log n)

```
Naive approach: For each number, count its bits → O(n × 32) = O(n log n)

DP approach: For each number, ONE lookup + ONE operation → O(n)

The DP recurrence:
  ans[i] = ans[i & (i-1)] + 1

We're reusing previously computed results!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Counting bits for each number separately | O(n × 32) instead of O(n) | Use DP with n & (n-1) |
| Forgetting base case | ans[0] must be 0 | Array is 0-initialized by default in Java |
| Off-by-one in array size | Need n+1 elements for 0 to n | Use `new int[n + 1]` |

--

## Mind-Map Anchor

```
COUNTING BITS (DP)
       │
       ▼
┌─────────────────────────────────┐
│ ans[i] = ans[i & (i-1)] + 1     │
│                                 │
│ i & (i-1) removes rightmost 1   │
│ So i has ONE more 1 than that   │
│                                 │
│ O(n) time, O(n) space           │
└─────────────────────────────────┘
```

**Memory phrase:** "Remove one 1, add 1 to that count"

--

# PATTERN 3: Reverse Bits (LeetCode 190)

## Pattern Recognition Signal

**When you see:** "reverse bits", "flip bit order", "mirror binary representation"

**Instant thought:** "Extract from right, place on left! Use n & 1 to get rightmost bit, shift result left, OR the bit in!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 43261596 (binary: 00000010100101000001111010011100)

Reverse all 32 bits:

Original: 00000010100101000001111010011100
Reversed: 00111001011110000010100101000000

Output: 964176192
```

### Why This Approach Works

```
Think of it like reversing a string, but with bits:

Original string: "HELLO"
Reversed:        "OLLEH"

For bits:
1. Take the LAST bit of n (rightmost)
2. Put it as the NEXT bit of result (building from left)
3. Move to the next bit of n
4. Repeat 32 times

n & 1      → extracts rightmost bit (0 or 1)
result <<= 1 → makes room for new bit on the right
result |= bit → places the bit there
n >>= 1    → moves to next bit of n
```

### Visual Explanation

```
Imagine two conveyor belts:

n (input):      [bit31][bit30]...[bit1][bit0] →→→ extracting from right
                                          ↓
result (output): ←←← [bit0][bit1]...[bit30][bit31] building from left

Each iteration:
1. Extract rightmost bit from n
2. Shift result left (make room)
3. Place extracted bit on result's right
4. Shift n right (move to next bit)
```

### The Algorithm in Plain English

```
1. Initialize result = 0
2. Repeat 32 times:
   a. Shift result left by 1 (make room)
   b. Extract n's rightmost bit: bit = n & 1
   c. Add bit to result: result |= bit
   d. Shift n right by 1 (move to next bit)
3. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `n = 11` (binary: `00001011` for 8-bit example)

```
═══════════════════════════════════════════════════════════

Initial State:
  n      = 00001011 (11)
  result = 00000000 (0)

═══════════════════════════════════════════════════════════

Iteration 0:
  Step 1: result <<= 1
          result = 00000000 (still 0)
  
  Step 2: Extract bit = n & 1 = 00001011 & 00000001 = 1
  
  Step 3: result |= bit
          result = 00000000 | 00000001 = 00000001
  
  Step 4: n >>= 1
          n = 00001011 >> 1 = 00000101 (5)
  
  State: result = 00000001, n = 00000101

═══════════════════════════════════════════════════════════

Iteration 1:
  Step 1: result <<= 1
          result = 00000001 << 1 = 00000010
  
  Step 2: Extract bit = n & 1 = 00000101 & 00000001 = 1
  
  Step 3: result |= bit
          result = 00000010 | 00000001 = 00000011
  
  Step 4: n >>= 1
          n = 00000101 >> 1 = 00000010 (2)
  
  State: result = 00000011, n = 00000010

═══════════════════════════════════════════════════════════

Iteration 2:
  Step 1: result <<= 1
          result = 00000011 << 1 = 00000110
  
  Step 2: Extract bit = n & 1 = 00000010 & 00000001 = 0
  
  Step 3: result |= bit
          result = 00000110 | 00000000 = 00000110
  
  Step 4: n >>= 1
          n = 00000010 >> 1 = 00000001 (1)
  
  State: result = 00000110, n = 00000001

═══════════════════════════════════════════════════════════

Iteration 3:
  Step 1: result <<= 1
          result = 00000110 << 1 = 00001100
  
  Step 2: Extract bit = n & 1 = 00000001 & 00000001 = 1
  
  Step 3: result |= bit
          result = 00001100 | 00000001 = 00001101
  
  Step 4: n >>= 1
          n = 00000001 >> 1 = 00000000 (0)
  
  State: result = 00001101, n = 00000000

═══════════════════════════════════════════════════════════

Iterations 4-7: (n is 0, so bit = 0 each time)
  result keeps shifting left, adding 0s
  
  After iteration 4: result = 00011010
  After iteration 5: result = 00110100
  After iteration 6: result = 01101000
  After iteration 7: result = 11010000

═══════════════════════════════════════════════════════════

Final Result:
  Original: 00001011 (11)
  Reversed: 11010000 (208)
  
  Verification: bits are in reverse order ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int reverseBits(int n) {
    int result = 0;  // Build reversed number here
    
    for (int i = 0; i < 32; i++) {  // Process all 32 bits
        result <<= 1;       // Make room: shift result left by 1
        result |= (n & 1);  // Extract n's rightmost bit, add to result
        n >>= 1;            // Move to n's next bit (shift right)
    }
    
    return result;
}
```

### Alternative: More Explicit Version

```java
int reverseBits(int n) {
    int result = 0;
    
    for (int i = 0; i < 32; i++) {
        int bit = n & 1;           // Extract rightmost bit
        result = (result << 1) | bit;  // Shift and add
        n = n >> 1;                // Move to next bit
    }
    
    return result;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using `>>>` vs `>>` for n | For this problem both work, but `>>>` is safer for unsigned | Use `n >>>= 1` for clarity |
| Forgetting to process all 32 bits | Leading zeros matter in reversal | Always loop exactly 32 times |
| Wrong order of operations | Must shift result BEFORE adding new bit | `result <<= 1` comes first |
| Returning n instead of result | n is destroyed during the loop | Return result |

--

## Mind-Map Anchor

```
REVERSE BITS
     │
     ▼
┌─────────────────────────────────────┐
│ For each of 32 bits:                │
│                                     │
│   1. result <<= 1  (make room)      │
│   2. result |= (n & 1) (add bit)    │
│   3. n >>= 1  (next bit)            │
│                                     │
│ Extract from RIGHT of n             │
│ Build from LEFT of result           │
└─────────────────────────────────────┘
```

**Memory phrase:** "Extract right, place left, 32 times"

--

# PATTERN 4: Missing Number (LeetCode 268)

## Pattern Recognition Signal

**When you see:** "array contains n numbers from 0 to n", "one number is missing", "find the missing number", "O(1) space"

**Instant thought:** "XOR all indices AND all values! Everything pairs up except the missing number!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [3, 0, 1]

Array has 3 numbers, should contain 0, 1, 2, 3 (n = 3)
But one is missing!

Present: 0, 1, 3
Missing: 2

Output: 2
```

### Why XOR Works Here

```
Key insight: XOR has these properties:
  a ^ a = 0  (same numbers cancel)
  a ^ 0 = a  (XOR with 0 does nothing)

If we XOR:
  - All indices: 0, 1, 2, 3 (0 to n)
  - All values:  3, 0, 1    (what's in the array)

Everything that EXISTS in the array will appear in BOTH sets!
  - Index 0 and value 0 → cancel
  - Index 1 and value 1 → cancel
  - Index 3 and value 3 → cancel
  - Index 2 has no matching value → SURVIVES!

XOR all = missing number!
```

### Visual Proof

```
nums = [3, 0, 1], n = 3

Indices: 0, 1, 2, 3 (we include n as an index)
Values:  3, 0, 1

XOR everything:
  (0 ^ 1 ^ 2 ^ 3) ^ (3 ^ 0 ^ 1)

Rearrange (XOR is commutative):
  = (0 ^ 0) ^ (1 ^ 1) ^ (3 ^ 3) ^ 2
  =    0    ^    0    ^    0    ^ 2
  = 2 ✓

The missing number survives!
```

### The Algorithm in Plain English

```
1. Start with xor = n (the last index)
2. For each index i from 0 to n-1:
   - XOR with i (the index)
   - XOR with nums[i] (the value)
3. Return xor (the missing number)
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [3, 0, 1]` (n = 3, missing = 2)

```
═══════════════════════════════════════════════════════════

Initial: xor = n = 3 (binary: 11)

Why start with n? Because indices go 0 to n-1, but we need 0 to n.
Starting with n includes it in our XOR.

═══════════════════════════════════════════════════════════

i = 0:
  xor = xor ^ i ^ nums[i]
      = 3 ^ 0 ^ 3
      
  Binary:
    011 (3)
  ^ 000 (0)
  ----
    011
  ^ 011 (3)
  ----
    000 (0)
  
  xor = 0
  
  "3 cancelled with 3, 0 XOR'd in"

═══════════════════════════════════════════════════════════

i = 1:
  xor = xor ^ i ^ nums[i]
      = 0 ^ 1 ^ 0
      
  Binary:
    000 (0)
  ^ 001 (1)
  ----
    001
  ^ 000 (0)
  ----
    001 (1)
  
  xor = 1
  
  "0 cancelled with 0, 1 XOR'd in"

═══════════════════════════════════════════════════════════

i = 2:
  xor = xor ^ i ^ nums[i]
      = 1 ^ 2 ^ 1
      
  Binary:
    001 (1)
  ^ 010 (2)
  ----
    011
  ^ 001 (1)
  ----
    010 (2)
  
  xor = 2
  
  "1 cancelled with 1, 2 XOR'd in (and survives!)"

═══════════════════════════════════════════════════════════

Final Result: xor = 2 ✓

Summary of what happened:
  - 0 appeared as index AND value → cancelled
  - 1 appeared as index AND value → cancelled
  - 3 appeared as initial xor AND value → cancelled
  - 2 appeared as index but NOT as value → SURVIVED!
```

--

## The Code (With Line-by-Line Explanation)

```java
int missingNumber(int[] nums) {
    int xor = nums.length;  // Start with n (include it in XOR)
    
    for (int i = 0; i < nums.length; i++) {
        xor ^= i;        // XOR with index
        xor ^= nums[i];  // XOR with value
        // Pairs cancel, missing survives
    }
    
    return xor;  // Only the missing number remains
}
```

### Alternative: Cleaner One-Liner Loop

```java
int missingNumber(int[] nums) {
    int xor = nums.length;
    for (int i = 0; i < nums.length; i++) {
        xor ^= i ^ nums[i];  // XOR index and value together
    }
    return xor;
}
```

--

## Why Not Use Sum Formula?

```
Sum approach: expected - actual = missing
  expected = n(n+1)/2
  actual = sum of array

Problem: Can OVERFLOW for large n!

XOR approach: No overflow, always works!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting to include n | n is a valid number that could be missing | Start with `xor = nums.length` |
| Using sum formula | Can overflow for large n | Use XOR instead |
| Off-by-one errors | Array has n elements, numbers are 0 to n | Loop from 0 to n-1, start xor at n |

--

## Mind-Map Anchor

```
MISSING NUMBER
      │
      ▼
┌─────────────────────────────────────┐
│ XOR all indices (0 to n)            │
│ XOR all values (nums[i])            │
│                                     │
│ Present numbers: appear TWICE       │
│   (once as index, once as value)    │
│   → They CANCEL (a ^ a = 0)         │
│                                     │
│ Missing number: appears ONCE        │
│   (only as index, not as value)     │
│   → It SURVIVES!                    │
│                                     │
│ O(n) time, O(1) space               │
└─────────────────────────────────────┘
```

**Memory phrase:** "XOR indices and values, pairs cancel, missing survives"

--

# PATTERN 5: Power of Two (LeetCode 231)

## Pattern Recognition Signal

**When you see:** "check if power of 2", "single bit set", "n is 2^k"

**Instant thought:** "Power of 2 has exactly ONE bit set! n & (n-1) removes it → result should be 0!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 16

Is 16 a power of 2? (Is 16 = 2^k for some k?)

16 = 2^4 = 10000 in binary

Yes! It has exactly ONE bit set.

Output: true
```

### Why n & (n-1) Works

```
Key insight: Powers of 2 in binary have EXACTLY ONE 1-bit:

  1 = 2^0 = 00001
  2 = 2^1 = 00010
  4 = 2^2 = 00100
  8 = 2^3 = 01000
 16 = 2^4 = 10000

Non-powers of 2 have MULTIPLE 1-bits:
  3 = 00011 (two 1s)
  5 = 00101 (two 1s)
  6 = 00110 (two 1s)

n & (n-1) removes the rightmost 1-bit.
If there's only ONE 1-bit, removing it gives 0!
If there are MULTIPLE 1-bits, removing one leaves others!
```

### Visual Proof

```
Power of 2 (n = 8):
  n     = 1000
  n-1   = 0111
  n&n-1 = 0000 ✓ (result is 0!)

Not power of 2 (n = 6):
  n     = 0110
  n-1   = 0101
  n&n-1 = 0100 ✗ (result is NOT 0!)
```

### The Algorithm in Plain English

```
1. Check if n > 0 (0 and negatives are not powers of 2)
2. Check if n & (n-1) == 0 (only one bit set)
3. If both true, n is a power of 2
```

--

## Visual Dry Run (Step-by-Step)

**Test Case 1:** `n = 16` (power of 2)

```
═══════════════════════════════════════════════════════════

n = 16 (binary: 10000)

Step 1: Is n > 0?
  16 > 0 ✓

Step 2: Calculate n & (n-1)
  n     = 10000 (16)
  n-1   = 01111 (15)
  
  Bit-by-bit AND:
    1 0 0 0 0
  & 0 1 1 1 1
  ------
    0 0 0 0 0 = 0

Step 3: Is result == 0?
  0 == 0 ✓

═══════════════════════════════════════════════════════════

Result: TRUE (16 is a power of 2) ✓
```

**Test Case 2:** `n = 6` (not power of 2)

```
═══════════════════════════════════════════════════════════

n = 6 (binary: 110)

Step 1: Is n > 0?
  6 > 0 ✓

Step 2: Calculate n & (n-1)
  n     = 110 (6)
  n-1   = 101 (5)
  
  Bit-by-bit AND:
    1 1 0
  & 1 0 1
  ----
    1 0 0 = 4

Step 3: Is result == 0?
  4 == 0 ✗

═══════════════════════════════════════════════════════════

Result: FALSE (6 is NOT a power of 2) ✓
```

**Test Case 3:** `n = 0` (edge case)

```
═══════════════════════════════════════════════════════════

n = 0

Step 1: Is n > 0?
  0 > 0 ✗

═══════════════════════════════════════════════════════════

Result: FALSE (0 is NOT a power of 2) ✓

Note: Without the n > 0 check:
  0 & (0-1) = 0 & (-1) = 0
  This would incorrectly return true!
```

--

## The Code (With Line-by-Line Explanation)

```java
boolean isPowerOfTwo(int n) {
    // n > 0: Must be positive (0 and negatives aren't powers of 2)
    // n & (n-1) == 0: Only one bit is set
    return n > 0 && (n & (n - 1)) == 0;
}
```

### Why Each Condition?

```java
// Condition 1: n > 0
// - 0 is not a power of 2 (2^k is always positive)
// - Negative numbers are not powers of 2
// - Without this, 0 & (-1) = 0 would incorrectly pass

// Condition 2: (n & (n-1)) == 0
// - Removes the rightmost 1-bit
// - If result is 0, there was only ONE 1-bit
// - One 1-bit = power of 2
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting n > 0 check | 0 & (-1) = 0, but 0 isn't power of 2 | Always check n > 0 first |
| Using n >= 0 | 0 is not a power of 2 | Use n > 0 |
| Checking n & (n-1) != 0 | Logic is inverted | Check n & (n-1) == 0 |
| Using while loop to divide by 2 | Works but O(log n) | n & (n-1) is O(1) |

--

## Mind-Map Anchor

```
POWER OF TWO
     │
     ▼
┌─────────────────────────────────────┐
│ Power of 2 = exactly ONE 1-bit      │
│                                     │
│   1 = 00001                         │
│   2 = 00010                         │
│   4 = 00100                         │
│   8 = 01000                         │
│                                     │
│ n & (n-1) removes that one bit      │
│ Result = 0 means it was power of 2  │
│                                     │
│ Formula: n > 0 && (n & (n-1)) == 0  │
│                                     │
│ O(1) time, O(1) space               │
└─────────────────────────────────────┘
```

**Memory phrase:** "One bit set, n & (n-1) gives zero"

--

# PATTERN 5b: Power of Four (LeetCode 342)

## Pattern Recognition Signal

**When you see:** "check if power of 4", "n is 4^k", "power of 4 without loops"

**Instant thought:** "Power of 4 is power of 2 with the 1-bit at an EVEN position! Use mask 0x55555555!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 16

Is 16 a power of 4? (Is 16 = 4^k for some k?)

16 = 4^2 = 10000 in binary

Yes! The single 1-bit is at position 4 (even position).

Output: true
```

### Why Power of 4 Needs Extra Check

```
Powers of 2 vs Powers of 4:

Powers of 2:        Powers of 4:
  1 = 2^0 = 00001     1 = 4^0 = 00001 (position 0) ✓
  2 = 2^1 = 00010     4 = 4^1 = 00100 (position 2) ✓
  4 = 2^2 = 00100    16 = 4^2 = 10000 (position 4) ✓
  8 = 2^3 = 01000    
 16 = 2^4 = 10000    

Notice: Powers of 4 have their 1-bit at EVEN positions (0, 2, 4, 6...)
        Powers of 2 that aren't powers of 4 have 1-bit at ODD positions

So: Power of 4 = Power of 2 + 1-bit at even position
```

### The Magic Mask: 0x55555555

```
0x55555555 in binary:
  0101 0101 0101 0101 0101 0101 0101 0101

This has 1s at all EVEN positions (0, 2, 4, 6, 8, ...)

If n is a power of 4:
  - It has exactly one 1-bit (power of 2 check)
  - That 1-bit is at an even position
  - So n & 0x55555555 will be non-zero!

If n is power of 2 but NOT power of 4:
  - It has exactly one 1-bit
  - That 1-bit is at an ODD position
  - So n & 0x55555555 will be ZERO!
```

### Visual Proof

```
n = 16 (power of 4):
  n           = 0001 0000 (1-bit at position 4, even)
  0x55555555  = 0101 0101
  n & mask    = 0001 0000 ≠ 0 ✓

n = 8 (power of 2, NOT power of 4):
  n           = 0000 1000 (1-bit at position 3, odd)
  0x55555555  = 0101 0101
  n & mask    = 0000 0000 = 0 ✗
```

### The Algorithm in Plain English

```
1. Check if n > 0 (must be positive)
2. Check if n & (n-1) == 0 (must be power of 2)
3. Check if n & 0x55555555 != 0 (1-bit at even position)
4. If all three true, n is a power of 4
```

--

## Visual Dry Run (Step-by-Step)

**Test Case 1:** `n = 16` (power of 4)

```
═══════════════════════════════════════════════════════════

n = 16 (binary: 00010000)

Step 1: Is n > 0?
  16 > 0 ✓

Step 2: Is n & (n-1) == 0? (power of 2 check)
  n     = 00010000 (16)
  n-1   = 00001111 (15)
  n&n-1 = 00000000 = 0 ✓

Step 3: Is n & 0x55555555 != 0? (even position check)
  n           = 00010000
  0x55555555  = 01010101 (showing 8 bits)
  n & mask    = 00010000 ≠ 0 ✓
  
  The 1-bit at position 4 (even) passes the mask!

═══════════════════════════════════════════════════════════

Result: TRUE (16 is a power of 4) ✓
```

**Test Case 2:** `n = 8` (power of 2, NOT power of 4)

```
═══════════════════════════════════════════════════════════

n = 8 (binary: 00001000)

Step 1: Is n > 0?
  8 > 0 ✓

Step 2: Is n & (n-1) == 0? (power of 2 check)
  n     = 00001000 (8)
  n-1   = 00000111 (7)
  n&n-1 = 00000000 = 0 ✓

Step 3: Is n & 0x55555555 != 0? (even position check)
  n           = 00001000
  0x55555555  = 01010101
  n & mask    = 00000000 = 0 ✗
  
  The 1-bit at position 3 (odd) is blocked by the mask!

═══════════════════════════════════════════════════════════

Result: FALSE (8 is NOT a power of 4) ✓
```

**Test Case 3:** `n = 64` (power of 4)

```
═══════════════════════════════════════════════════════════

n = 64 (binary: 01000000)

64 = 4^3 = 4 × 4 × 4

Step 1: Is n > 0?
  64 > 0 ✓

Step 2: Is n & (n-1) == 0?
  n     = 01000000 (64)
  n-1   = 00111111 (63)
  n&n-1 = 00000000 = 0 ✓

Step 3: Is n & 0x55555555 != 0?
  n           = 01000000
  0x55555555  = 01010101
  n & mask    = 01000000 ≠ 0 ✓
  
  The 1-bit at position 6 (even) passes the mask!

═══════════════════════════════════════════════════════════

Result: TRUE (64 is a power of 4) ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
boolean isPowerOfFour(int n) {
    // Condition 1: n > 0 (must be positive)
    // Condition 2: (n & (n-1)) == 0 (must be power of 2)
    // Condition 3: (n & 0x55555555) != 0 (1-bit at even position)
    
    return n > 0 && (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
}
```

### Understanding the Mask

```java
// 0x55555555 in binary:
// 0101 0101 0101 0101 0101 0101 0101 0101
//  ^    ^    ^    ^    ^    ^    ^    ^
// pos: 30   26   22   18   14   10   6    2
//       28   24   20   16   12   8    4    0
//
// All EVEN positions have 1s
// All ODD positions have 0s
//
// Powers of 4: 4^0=1, 4^1=4, 4^2=16, 4^3=64...
// Positions:    0      2       4        6
// All EVEN!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Only checking power of 2 | 8 is power of 2 but not power of 4 | Add mask check |
| Wrong mask value | Using 0xAAAAAAAA checks odd positions | Use 0x55555555 for even |
| Forgetting n > 0 | 0 would pass the other checks | Always check n > 0 first |
| Using loop to divide by 4 | Works but O(log n) | Bit operations are O(1) |

--

## Mind-Map Anchor

```
POWER OF FOUR
      │
      ▼
┌─────────────────────────────────────────┐
│ Power of 4 = Power of 2 + even position │
│                                         │
│ Check 1: n > 0                          │
│ Check 2: n & (n-1) == 0 (power of 2)    │
│ Check 3: n & 0x55555555 != 0 (even pos) │
│                                         │
│ Mask 0x55555555 = 0101...0101           │
│ Has 1s at positions 0, 2, 4, 6, 8...    │
│                                         │
│ O(1) time, O(1) space                   │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Power of 2 at even position, mask 0x55555555"

--

# PATTERN 6: Single Number II (LeetCode 137)

## Pattern Recognition Signal

**When you see:** "every element appears THREE times except one", "find the unique element", "elements appear k times"

**Instant thought:** "XOR won't work (a^a^a = a)! Count bits at each position, take mod 3!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [2, 2, 3, 2]

Every number appears THREE times except one.
Find the one that appears only ONCE.

2 appears: 3 times
3 appears: 1 time ← THIS IS THE ANSWER

Output: 3
```

### Why XOR Doesn't Work Here

```
In Single Number I: a ^ a = 0 (pairs cancel)
But here: a ^ a ^ a = a (triples DON'T cancel!)

Example:
  2 ^ 2 ^ 2 = 2 (not 0!)

So we need a different approach.
```

### The Bit Counting Approach

```
Key insight: Look at each bit position INDEPENDENTLY.

For each bit position (0 to 31):
  - Count how many numbers have a 1 at that position
  - If count % 3 != 0, the unique number has a 1 there
  - If count % 3 == 0, the unique number has a 0 there

Why? Because numbers appearing 3 times contribute 0 or 3 to the count.
     3 % 3 = 0, so they don't affect the result!
     Only the unique number's bits remain (count % 3 = 1).
```

### Visual Proof

```
nums = [2, 2, 3, 2]

Binary representations:
  2 = 10
  2 = 10
  3 = 11
  2 = 10

Count bits at each position:
  Position 0 (rightmost): 0 + 0 + 1 + 0 = 1
  Position 1:             1 + 1 + 1 + 1 = 4

Take mod 3:
  Position 0: 1 % 3 = 1 → unique has 1 here
  Position 1: 4 % 3 = 1 → unique has 1 here

Result: 11 = 3 ✓
```

### The Algorithm in Plain English

```
1. Initialize result = 0
2. For each bit position i (0 to 31):
   a. Count how many numbers have bit i set
   b. If count % 3 != 0, set bit i in result
3. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [5, 5, 5, 3]` (unique = 3)

```
Binary representations:
  5 = 101
  5 = 101
  5 = 101
  3 = 011

═══════════════════════════════════════════════════════════

Position 0 (rightmost bit):
  
  5 → bit 0 = 1
  5 → bit 0 = 1
  5 → bit 0 = 1
  3 → bit 0 = 1
  
  Count = 4
  4 % 3 = 1 ≠ 0 → result bit 0 = 1

═══════════════════════════════════════════════════════════

Position 1:
  
  5 → bit 1 = 0
  5 → bit 1 = 0
  5 → bit 1 = 0
  3 → bit 1 = 1
  
  Count = 1
  1 % 3 = 1 ≠ 0 → result bit 1 = 1

═══════════════════════════════════════════════════════════

Position 2:
  
  5 → bit 2 = 1
  5 → bit 2 = 1
  5 → bit 2 = 1
  3 → bit 2 = 0
  
  Count = 3
  3 % 3 = 0 → result bit 2 = 0

═══════════════════════════════════════════════════════════

Positions 3-31: All counts are 0, so all bits are 0

═══════════════════════════════════════════════════════════

Final Result:
  result = 011 = 3 ✓
  
  The 5s contributed 3 to positions 0 and 2 (cancelled by mod 3)
  Only 3's bits survived!
```

--

## The Code (With Line-by-Line Explanation)

### Approach 1: Bit Counting (Easier to Understand)

```java
int singleNumber(int[] nums) {
    int result = 0;
    
    // Check each of the 32 bit positions
    for (int i = 0; i < 32; i++) {
        int bitSum = 0;
        
        // Count how many numbers have bit i set
        for (int num : nums) {
            bitSum += (num >> i) & 1;  // Extract bit i and add to sum
        }
        
        // If count % 3 != 0, unique number has this bit set
        if (bitSum % 3 != 0) {
            result |= (1 << i);  // Set bit i in result
        }
    }
    
    return result;
}
```

### Approach 2: State Machine (Advanced, O(n) time, O(1) space)

```java
int singleNumber(int[] nums) {
    int ones = 0, twos = 0;
    
    for (int num : nums) {
        // ones: bits that have appeared 1 time (mod 3)
        // twos: bits that have appeared 2 times (mod 3)
        
        ones = (ones ^ num) & ~twos;  // Add to ones if not in twos
        twos = (twos ^ num) & ~ones;  // Add to twos if not in ones
    }
    
    return ones;  // Bits that appeared exactly once
}
```

--

## Understanding the State Machine (Advanced)

```
The state machine tracks bit counts mod 3:

State: (ones, twos) represents count mod 3
  (0, 0) → count = 0
  (1, 0) → count = 1
  (0, 1) → count = 2
  (0, 0) → count = 3 (wraps back to 0)

Transitions when we see a 1 bit:
  (0, 0) + 1 → (1, 0)  // 0 → 1
  (1, 0) + 1 → (0, 1)  // 1 → 2
  (0, 1) + 1 → (0, 0)  // 2 → 0 (mod 3)

The formulas achieve this:
  ones = (ones ^ num) & ~twos
  twos = (twos ^ num) & ~ones
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using XOR like Single Number I | a^a^a = a, not 0 | Use bit counting mod 3 |
| Forgetting negative numbers | Bit 31 is sign bit | Algorithm handles it correctly |
| Using HashMap | Works but O(n) space | Bit counting is O(1) space |
| Wrong mod value | Must match repetition count | Use % 3 for triples |

--

## Generalizing to k Repetitions

```
If every element appears k times except one:
  - Count bits at each position
  - Take count % k
  - If result != 0, unique has that bit set

This works for any k!
```

--

## Mind-Map Anchor

```
SINGLE NUMBER II (TRIPLES)
         │
         ▼
┌─────────────────────────────────────┐
│ XOR doesn't work: a^a^a = a         │
│                                     │
│ Solution: Count bits at each pos    │
│                                     │
│ For each bit position (0-31):       │
│   count = sum of that bit           │
│   if count % 3 != 0:                │
│     unique has this bit set         │
│                                     │
│ O(32n) = O(n) time, O(1) space      │
└─────────────────────────────────────┘
```

**Memory phrase:** "Count each bit, mod 3, non-zero means unique has it"

--

# PATTERN 7: Single Number III (LeetCode 260)

## Pattern Recognition Signal

**When you see:** "TWO numbers appear once, all others twice", "find both unique elements", "O(1) space"

**Instant thought:** "XOR all gives a^b. Find a differing bit, split array into two groups, XOR each group!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [1, 2, 1, 3, 2, 5]

Every number appears TWICE except TWO numbers.
Find both unique numbers.

1 appears: 2 times
2 appears: 2 times
3 appears: 1 time ← UNIQUE
5 appears: 1 time ← UNIQUE

Output: [3, 5] (or [5, 3])
```

### Why Simple XOR Isn't Enough

```
If we XOR everything:
  1 ^ 2 ^ 1 ^ 3 ^ 2 ^ 5
= (1 ^ 1) ^ (2 ^ 2) ^ 3 ^ 5
= 0 ^ 0 ^ 3 ^ 5
= 3 ^ 5
= 011 ^ 101
= 110 = 6

We get a ^ b, but how do we separate a and b?
```

### The Key Insight: Find a Differing Bit

```
If xor = a ^ b, then xor has 1s where a and b DIFFER.

xor = 6 = 110

This means a and b differ at bit positions 1 and 2.

Pick ANY differing bit (easiest: rightmost 1 using n & -n).
diffBit = 6 & (-6) = 010 = 2

Now split the array:
  - Group 1: numbers where this bit is 0
  - Group 2: numbers where this bit is 1

a and b will be in DIFFERENT groups!
Pairs will be in the SAME group (same number = same bits)!

XOR each group → get a and b separately!
```

### Visual Proof

```
nums = [1, 2, 1, 3, 2, 5]

Binary:
  1 = 001
  2 = 010
  1 = 001
  3 = 011
  2 = 010
  5 = 101

xor = 3 ^ 5 = 011 ^ 101 = 110 = 6
diffBit = 6 & (-6) = 010 (bit 1)

Split by bit 1:
  Bit 1 = 0: [1, 1, 5] → 001, 001, 101
  Bit 1 = 1: [2, 3, 2] → 010, 011, 010

XOR Group 1: 1 ^ 1 ^ 5 = 5 ✓
XOR Group 2: 2 ^ 3 ^ 2 = 3 ✓

Result: [5, 3] ✓
```

### The Algorithm in Plain English

```
1. XOR all numbers → get xor = a ^ b
2. Find a differing bit: diffBit = xor & (-xor)
3. Split numbers into two groups based on diffBit
4. XOR each group to get a and b
5. Return [a, b]
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [1, 2, 1, 3, 2, 5]`

```
═══════════════════════════════════════════════════════════

STEP 1: XOR all numbers to get a ^ b

  1 ^ 2 ^ 1 ^ 3 ^ 2 ^ 5
  
  Binary calculation:
    001 (1)
  ^ 010 (2)
  ----
    011
  ^ 001 (1)
  ----
    010
  ^ 011 (3)
  ----
    001
  ^ 010 (2)
  ----
    011
  ^ 101 (5)
  ----
    110 = 6
  
  xor = 6 = 3 ^ 5

═══════════════════════════════════════════════════════════

STEP 2: Find a differing bit using xor & (-xor)

  xor = 6 = 110
  
  -xor = -6 (two's complement)
       = ~6 + 1
       = 001 + 1
       = 010
  
  xor & (-xor) = 110 & 010 = 010 = 2
  
  diffBit = 2 (bit position 1)
  
  This means 3 and 5 differ at bit 1:
    3 = 011 → bit 1 is 1
    5 = 101 → bit 1 is 0

═══════════════════════════════════════════════════════════

STEP 3: Split array by diffBit

  For each number, check: (num & diffBit) == 0?
  
  1 = 001: 001 & 010 = 000 = 0 → Group A (bit 1 is 0)
  2 = 010: 010 & 010 = 010 ≠ 0 → Group B (bit 1 is 1)
  1 = 001: 001 & 010 = 000 = 0 → Group A
  3 = 011: 011 & 010 = 010 ≠ 0 → Group B
  2 = 010: 010 & 010 = 010 ≠ 0 → Group B
  5 = 101: 101 & 010 = 000 = 0 → Group A
  
  Group A (bit 1 = 0): [1, 1, 5]
  Group B (bit 1 = 1): [2, 3, 2]

═══════════════════════════════════════════════════════════

STEP 4: XOR each group

  Group A: 1 ^ 1 ^ 5
    001 ^ 001 = 000
    000 ^ 101 = 101 = 5
  
  Group B: 2 ^ 3 ^ 2
    010 ^ 011 = 001
    001 ^ 010 = 011 = 3

═══════════════════════════════════════════════════════════

Final Result: [5, 3] ✓

Why it works:
  - Pairs (1,1) and (2,2) are in the SAME group → they cancel
  - 3 and 5 are in DIFFERENT groups → they survive
```

--

## The Code (With Line-by-Line Explanation)

```java
int[] singleNumber(int[] nums) {
    // Step 1: XOR all numbers to get a ^ b
    int xor = 0;
    for (int num : nums) {
        xor ^= num;
    }
    // xor now equals a ^ b (the two unique numbers XOR'd)
    
    // Step 2: Find rightmost set bit (where a and b differ)
    // n & (-n) isolates the rightmost 1 bit
    int diffBit = xor & (-xor);
    
    // Step 3: Split numbers and XOR each group
    int a = 0, b = 0;
    for (int num : nums) {
        if ((num & diffBit) == 0) {
            a ^= num;  // Group where diffBit is 0
        } else {
            b ^= num;  // Group where diffBit is 1
        }
    }
    // Pairs cancel within each group, unique survives
    
    return new int[]{a, b};
}
```

--

## Why n & (-n) Isolates Rightmost 1

```
n = 6 = 110

Two's complement of -n:
  ~n = 001
  ~n + 1 = 010 = -6

n & (-n):
  110 & 010 = 010

The rightmost 1 is isolated!

This works because:
  - ~n flips all bits
  - Adding 1 causes a ripple that stops at the rightmost 1
  - Only that bit position has 1 in both n and -n
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Trying to use XOR alone | XOR gives a^b, can't separate | Split by differing bit |
| Using wrong bit isolation | Need rightmost 1 of xor | Use xor & (-xor) |
| Splitting by wrong condition | Must use the diffBit | Check (num & diffBit) == 0 |
| Forgetting pairs cancel | Pairs in same group cancel | That's why this works! |

--

## Mind-Map Anchor

```
SINGLE NUMBER III (TWO UNIQUES)
            │
            ▼
┌─────────────────────────────────────────┐
│ Step 1: XOR all → get a ^ b             │
│                                         │
│ Step 2: Find differing bit              │
│         diffBit = xor & (-xor)          │
│                                         │
│ Step 3: Split by diffBit                │
│         Group A: (num & diffBit) == 0   │
│         Group B: (num & diffBit) != 0   │
│                                         │
│ Step 4: XOR each group                  │
│         a and b in different groups     │
│         Pairs in same group → cancel    │
│                                         │
│ O(n) time, O(1) space                   │
└─────────────────────────────────────────┘
```

**Memory phrase:** "XOR all, find diff bit, split and XOR groups"

--

# PATTERN 8: Hamming Distance (LeetCode 461)

## Pattern Recognition Signal

**When you see:** "Hamming distance", "number of differing bits", "bit positions where two numbers differ"

**Instant thought:** "XOR shows differences! XOR the numbers, then count the 1s!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: x = 1, y = 4

Hamming distance = number of positions where bits differ.

x = 1 = 001
y = 4 = 100
        ↑ ↑
        differ at positions 0 and 2

Output: 2
```

### Why XOR is Perfect Here

```
XOR answers: "Which bits are DIFFERENT?"

x = 1 = 001
y = 4 = 100

x ^ y:
  001
^ 100
---
  101 = 5

The result has 1s exactly where x and y DIFFER!
Count the 1s in 101 → 2 differences → Hamming distance = 2
```

### The Algorithm in Plain English

```
1. XOR x and y → result has 1s where they differ
2. Count the 1s in the result (using n & (n-1))
3. Return the count
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `x = 1, y = 4`

```
═══════════════════════════════════════════════════════════

STEP 1: XOR to find differing bits

  x = 1 = 001
  y = 4 = 100
  
  x ^ y:
    0 0 1
  ^ 1 0 0
  ----
    1 0 1 = 5
  
  xor = 5
  
  The 1s are at positions 0 and 2 (where x and y differ)

═══════════════════════════════════════════════════════════

STEP 2: Count 1s using n & (n-1)

  xor = 5 = 101, count = 0
  
  Iteration 1:
    xor     = 101
    xor - 1 = 100
    xor & (xor-1) = 101 & 100 = 100 = 4
    
    xor = 4, count = 1
    "Removed rightmost 1 (at position 0)"

═══════════════════════════════════════════════════════════

  Iteration 2:
    xor     = 100
    xor - 1 = 011
    xor & (xor-1) = 100 & 011 = 000 = 0
    
    xor = 0, count = 2
    "Removed rightmost 1 (at position 2)"

═══════════════════════════════════════════════════════════

  xor = 0, loop exits

═══════════════════════════════════════════════════════════

Final Result: count = 2 ✓

Verification:
  x = 001
  y = 100
      ↑ ↑
  Positions 0 and 2 differ → Hamming distance = 2
```

--

## The Code (With Line-by-Line Explanation)

```java
int hammingDistance(int x, int y) {
    int xor = x ^ y;  // 1s where x and y differ
    int count = 0;
    
    // Count 1s using n & (n-1) trick
    while (xor != 0) {
        xor = xor & (xor - 1);  // Remove rightmost 1
        count++;                 // Count it
    }
    
    return count;
}
```

### Alternative: Using Built-in Function

```java
int hammingDistance(int x, int y) {
    return Integer.bitCount(x ^ y);  // XOR then count 1s
}
```

### Alternative: Check Each Bit

```java
int hammingDistance(int x, int y) {
    int xor = x ^ y;
    int count = 0;
    
    while (xor != 0) {
        count += xor & 1;  // Add rightmost bit (0 or 1)
        xor >>= 1;         // Shift right
    }
    
    return count;
}
```

--

## Why n & (n-1) is Better Than Checking Each Bit

```
Method 1: Check each bit
  - Always checks 32 bits
  - O(32) = O(1), but 32 iterations

Method 2: n & (n-1)
  - Only iterates for each 1-bit
  - If xor = 5 (101), only 2 iterations!
  - O(number of 1s) ≤ O(32)

n & (n-1) is faster when there are few differing bits!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting to XOR first | Need to find differences first | Always start with x ^ y |
| Using subtraction | Hamming distance isn't x - y | Use XOR to find bit differences |
| Counting wrong bits | Must count 1s in XOR result | Use n & (n-1) or bitCount |

--

## Mind-Map Anchor

```
HAMMING DISTANCE
       │
       ▼
┌─────────────────────────────────────┐
│ Step 1: XOR the two numbers         │
│         xor = x ^ y                 │
│         1s show where they differ   │
│                                     │
│ Step 2: Count the 1s                │
│         Use n & (n-1) loop          │
│         Or Integer.bitCount()       │
│                                     │
│ Result = number of differing bits   │
│                                     │
│ O(1) time (at most 32 iterations)   │
└─────────────────────────────────────┘
```

**Memory phrase:** "XOR shows differences, count the 1s"

--

# PATTERN 9: Total Hamming Distance (LeetCode 477)

## Pattern Recognition Signal

**When you see:** "sum of Hamming distances between ALL pairs", "total bit differences", "pairwise Hamming distance"

**Instant thought:** "Don't compare pairs (O(n²))! Count 0s and 1s at each bit position, multiply them!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [4, 14, 2]

Find the sum of Hamming distances between ALL pairs:
  - Hamming(4, 14) = ?
  - Hamming(4, 2) = ?
  - Hamming(14, 2) = ?
  
Sum them all up.

Output: 6
```

### Why Brute Force is Too Slow

```
Brute force: Compare every pair
  - n numbers → n(n-1)/2 pairs
  - For each pair, compute Hamming distance
  - O(n² × 32) = O(n²)

For n = 10,000, that's 50 million pairs!
```

### The Key Insight: Think Per-Bit

```
Instead of comparing pairs, think about each BIT POSITION independently.

At each bit position:
  - Some numbers have 0
  - Some numbers have 1
  
Every (0, 1) pair at this position contributes 1 to total distance!

If k numbers have 1 and (n-k) have 0:
  - Number of (0, 1) pairs = k × (n-k)
  - Each pair contributes 1 to Hamming distance
  - Total contribution from this bit = k × (n-k)

Sum across all 32 bit positions!
```

### Visual Proof

```
nums = [4, 14, 2]

Binary:
  4  = 0100
  14 = 1110
  2  = 0010

Position 0 (rightmost):
  4→0, 14→0, 2→0
  ones = 0, zeros = 3
  contribution = 0 × 3 = 0

Position 1:
  4→0, 14→1, 2→1
  ones = 2, zeros = 1
  contribution = 2 × 1 = 2

Position 2:
  4→1, 14→1, 2→0
  ones = 2, zeros = 1
  contribution = 2 × 1 = 2

Position 3:
  4→0, 14→1, 2→0
  ones = 1, zeros = 2
  contribution = 1 × 2 = 2

Total = 0 + 2 + 2 + 2 = 6 ✓
```

### Verification with Brute Force

```
Hamming(4, 14):
  4  = 0100
  14 = 1110
  XOR = 1010 → 2 ones → distance = 2

Hamming(4, 2):
  4 = 0100
  2 = 0010
  XOR = 0110 → 2 ones → distance = 2

Hamming(14, 2):
  14 = 1110
  2  = 0010
  XOR = 1100 → 2 ones → distance = 2

Total = 2 + 2 + 2 = 6 ✓ (matches!)
```

### The Algorithm in Plain English

```
1. Initialize total = 0
2. For each bit position i (0 to 31):
   a. Count how many numbers have bit i set (countOnes)
   b. countZeros = n - countOnes
   c. Add countOnes × countZeros to total
3. Return total
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [4, 14, 2]` (n = 3)

```
Binary representations:
  4  = 0100
  14 = 1110
  2  = 0010

═══════════════════════════════════════════════════════════

Position 0 (rightmost bit):

  Extract bit 0 from each number:
    4 >> 0 & 1  = 0100 & 0001 = 0
    14 >> 0 & 1 = 1110 & 0001 = 0
    2 >> 0 & 1  = 0010 & 0001 = 0
  
  countOnes = 0
  countZeros = 3 - 0 = 3
  
  contribution = 0 × 3 = 0
  total = 0

═══════════════════════════════════════════════════════════

Position 1:

  Extract bit 1 from each number:
    4 >> 1 & 1  = 0010 & 0001 = 0
    14 >> 1 & 1 = 0111 & 0001 = 1
    2 >> 1 & 1  = 0001 & 0001 = 1
  
  countOnes = 2
  countZeros = 3 - 2 = 1
  
  contribution = 2 × 1 = 2
  total = 0 + 2 = 2
  
  "2 numbers have 1, 1 number has 0 → 2 differing pairs"

═══════════════════════════════════════════════════════════

Position 2:

  Extract bit 2 from each number:
    4 >> 2 & 1  = 0001 & 0001 = 1
    14 >> 2 & 1 = 0011 & 0001 = 1
    2 >> 2 & 1  = 0000 & 0001 = 0
  
  countOnes = 2
  countZeros = 3 - 2 = 1
  
  contribution = 2 × 1 = 2
  total = 2 + 2 = 4

═══════════════════════════════════════════════════════════

Position 3:

  Extract bit 3 from each number:
    4 >> 3 & 1  = 0000 & 0001 = 0
    14 >> 3 & 1 = 0001 & 0001 = 1
    2 >> 3 & 1  = 0000 & 0001 = 0
  
  countOnes = 1
  countZeros = 3 - 1 = 2
  
  contribution = 1 × 2 = 2
  total = 4 + 2 = 6

═══════════════════════════════════════════════════════════

Positions 4-31: All bits are 0, so countOnes = 0
  contribution = 0 × 3 = 0 for each

═══════════════════════════════════════════════════════════

Final Result: total = 6 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int totalHammingDistance(int[] nums) {
    int total = 0;
    int n = nums.length;
    
    // Check each of the 32 bit positions
    for (int i = 0; i < 32; i++) {
        int countOnes = 0;
        
        // Count how many numbers have bit i set
        for (int num : nums) {
            countOnes += (num >> i) & 1;  // Extract bit i (0 or 1)
        }
        
        int countZeros = n - countOnes;
        
        // Each (0,1) pair contributes 1 to Hamming distance
        // Number of such pairs = countOnes × countZeros
        total += countOnes * countZeros;
    }
    
    return total;
}
```

--

## Why This is O(32n) = O(n)

```
Brute force: O(n² × 32)
  - Compare every pair: n(n-1)/2 pairs
  - Each comparison: 32 bit operations

Per-bit counting: O(32 × n) = O(n)
  - 32 bit positions
  - For each position: scan n numbers once
  - No pair comparisons needed!

For n = 10,000:
  Brute force: ~1.6 billion operations
  Per-bit: ~320,000 operations
  
  That's 5000x faster!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Comparing all pairs | O(n²) is too slow | Use per-bit counting |
| Forgetting to multiply | Need countOnes × countZeros | Each (0,1) pair contributes 1 |
| Integer overflow | countOnes × countZeros can be large | Use long if needed |
| Only checking some bits | Numbers can use all 32 bits | Always check all 32 positions |

--

## Mind-Map Anchor

```
TOTAL HAMMING DISTANCE
          │
          ▼
┌─────────────────────────────────────────┐
│ Don't compare pairs (O(n²))!            │
│                                         │
│ For each bit position (0-31):           │
│   count how many have 0 vs 1            │
│                                         │
│   contribution = countOnes × countZeros │
│                                         │
│ Why? Every (0,1) pair at this position  │
│      contributes 1 to total distance    │
│                                         │
│ O(32n) = O(n) time, O(1) space          │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Per bit: count 0s and 1s, multiply them"

--

# PATTERN 10: Sum of Two Integers (LeetCode 371)

## Pattern Recognition Signal

**When you see:** "add without using + or -", "sum using bit operations", "implement addition with bits"

**Instant thought:** "XOR gives sum without carry! AND shifted left gives the carry! Loop until no carry!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: a = 5, b = 3

Add 5 + 3 without using + or - operators.

Output: 8
```

### How Addition Works in Binary

```
When you add two bits:
  0 + 0 = 0 (no carry)
  0 + 1 = 1 (no carry)
  1 + 0 = 1 (no carry)
  1 + 1 = 0 with carry 1

This is exactly what XOR and AND do!
  XOR: gives the sum without considering carry
  AND: shows where both bits are 1 (where carry happens)
```

### The Two-Step Process

```
Step 1: XOR gives "sum without carry"
  5 = 101
  3 = 011
  XOR = 110 = 6

  But wait, 5 + 3 = 8, not 6!
  We're missing the carry.

Step 2: AND gives "where carry happens"
  5 = 101
  3 = 011
  AND = 001 = 1

  Carry happens at position 0.
  But carry goes to the NEXT position!
  So shift left: 001 << 1 = 010 = 2

Step 3: Add the carry to the sum
  sum = 6, carry = 2
  Now add 6 + 2 (repeat the process!)
  
  6 = 110
  2 = 010
  XOR = 100 = 4 (new sum)
  AND = 010, shifted = 100 = 4 (new carry)
  
  4 = 100
  4 = 100
  XOR = 000 = 0 (new sum)
  AND = 100, shifted = 1000 = 8 (new carry)
  
  0 = 000
  8 = 1000
  XOR = 1000 = 8 (new sum)
  AND = 000 = 0 (no more carry!)
  
  Done! Result = 8 ✓
```

### The Algorithm in Plain English

```
1. While there's a carry (b != 0):
   a. Calculate carry: (a & b) << 1
   b. Calculate sum without carry: a ^ b
   c. Set a = sum, b = carry
2. Return a (the final sum)
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `a = 5, b = 3`

```
═══════════════════════════════════════════════════════════

Initial: a = 5 (101), b = 3 (011)

═══════════════════════════════════════════════════════════

Iteration 1:

  Calculate carry:
    a & b = 101 & 011 = 001
    (a & b) << 1 = 001 << 1 = 010 = 2
    
    "Carry happens at position 0, moves to position 1"
  
  Calculate sum without carry:
    a ^ b = 101 ^ 011 = 110 = 6
    
    "XOR gives sum ignoring carry"
  
  Update:
    a = 6 (110)
    b = 2 (010)  ← the carry
  
  b != 0, so continue...

═══════════════════════════════════════════════════════════

Iteration 2:

  Calculate carry:
    a & b = 110 & 010 = 010
    (a & b) << 1 = 010 << 1 = 100 = 4
  
  Calculate sum without carry:
    a ^ b = 110 ^ 010 = 100 = 4
  
  Update:
    a = 4 (100)
    b = 4 (100)  ← the carry
  
  b != 0, so continue...

═══════════════════════════════════════════════════════════

Iteration 3:

  Calculate carry:
    a & b = 100 & 100 = 100
    (a & b) << 1 = 100 << 1 = 1000 = 8
  
  Calculate sum without carry:
    a ^ b = 100 ^ 100 = 000 = 0
  
  Update:
    a = 0 (000)
    b = 8 (1000)  ← the carry
  
  b != 0, so continue...

═══════════════════════════════════════════════════════════

Iteration 4:

  Calculate carry:
    a & b = 000 & 1000 = 0000
    (a & b) << 1 = 0000 << 1 = 0000 = 0
  
  Calculate sum without carry:
    a ^ b = 000 ^ 1000 = 1000 = 8
  
  Update:
    a = 8 (1000)
    b = 0 (0000)  ← no more carry!
  
  b == 0, loop exits!

═══════════════════════════════════════════════════════════

Final Result: a = 8 ✓

5 + 3 = 8 (computed without + operator!)
```

--

## The Code (With Line-by-Line Explanation)

```java
int getSum(int a, int b) {
    while (b != 0) {
        // Step 1: Calculate carry
        // AND finds where both bits are 1 (carry happens)
        // Shift left because carry goes to next position
        int carry = (a & b) << 1;
        
        // Step 2: Calculate sum without carry
        // XOR adds bits without considering carry
        a = a ^ b;
        
        // Step 3: The carry becomes the new b
        // We'll add it in the next iteration
        b = carry;
    }
    
    // When b = 0, no more carry, a is the answer
    return a;
}
```

--

## Why This Works for Negative Numbers Too

```
Two's complement representation handles negatives automatically!

Example: 5 + (-3) = 2

-3 in two's complement (32-bit): 11111111111111111111111111111101

The XOR and AND operations work the same way.
The loop terminates when carry becomes 0.
Result is correct!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting to shift carry | Carry goes to NEXT position | Always use (a & b) << 1 |
| Wrong loop condition | Loop until no carry | Use while (b != 0) |
| Infinite loop with negatives | In some languages, can loop forever | Java handles this correctly |
| Confusing XOR and AND roles | XOR = sum, AND = carry | Remember: XOR adds, AND finds carry |

--

## The Math Behind It

```
Binary addition truth table:

A | B | Sum | Carry
-|--|---|---
0 | 0 |  0  |   0
0 | 1 |  1  |   0
1 | 0 |  1  |   0
1 | 1 |  0  |   1

Notice:
  Sum column = A XOR B
  Carry column = A AND B

So:
  XOR gives the sum (without carry)
  AND gives where carry happens
  Shift AND left to move carry to correct position
```

--

## Mind-Map Anchor

```
SUM WITHOUT + OPERATOR
         │
         ▼
┌─────────────────────────────────────────┐
│ While there's a carry (b != 0):         │
│                                         │
│   carry = (a & b) << 1                  │
│           ↑         ↑                   │
│     where both    shift to              │
│     bits are 1    next position         │
│                                         │
│   a = a ^ b                             │
│       ↑                                 │
│     sum without carry                   │
│                                         │
│   b = carry                             │
│       ↑                                 │
│     add carry in next iteration         │
│                                         │
│ Return a when b = 0                     │
└─────────────────────────────────────────┘
```

**Memory phrase:** "XOR for sum, AND-shift for carry, loop until done"

--

# PATTERN 11: Divide Two Integers (LeetCode 29)

## Pattern Recognition Signal

**When you see:** "divide without using /, *, or %", "implement division with bits", "integer division"

**Instant thought:** "Division is repeated subtraction! Use bit shifts to subtract in powers of 2 for efficiency!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: dividend = 43, divisor = 8

Compute 43 / 8 without using /, *, or %.

43 = 8 × 5 + 3

Output: 5 (integer division, truncate toward zero)
```

### The Naive Approach (Too Slow)

```
Subtract divisor repeatedly:
  43 - 8 = 35 (count = 1)
  35 - 8 = 27 (count = 2)
  27 - 8 = 19 (count = 3)
  19 - 8 = 11 (count = 4)
  11 - 8 = 3  (count = 5)
  3 < 8, stop

Result: 5

Problem: If dividend = 2^31 and divisor = 1, we need 2^31 subtractions!
```

### The Key Insight: Subtract in Powers of 2

```
Instead of subtracting 8 one at a time, subtract the LARGEST multiple of 8 that fits!

43 ÷ 8:
  Can we subtract 8 × 4 = 32? Yes! (43 ≥ 32)
    43 - 32 = 11, count = 4
  
  Can we subtract 8 × 2 = 16? No (11 < 16)
  
  Can we subtract 8 × 1 = 8? Yes! (11 ≥ 8)
    11 - 8 = 3, count = 4 + 1 = 5
  
  3 < 8, stop

Result: 5 (only 2 subtractions instead of 5!)
```

### Using Bit Shifts

```
8 × 1 = 8 = 8 << 0
8 × 2 = 16 = 8 << 1
8 × 4 = 32 = 8 << 2
8 × 8 = 64 = 8 << 3

Shifting left by k = multiplying by 2^k

So we find the largest k where (divisor << k) ≤ dividend
Then subtract and add 2^k to result
```

### The Algorithm in Plain English

```
1. Handle edge cases (overflow, signs)
2. Work with absolute values
3. While dividend ≥ divisor:
   a. Find largest k where (divisor << k) ≤ dividend
   b. Subtract (divisor << k) from dividend
   c. Add 2^k to result
4. Apply sign and return
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `dividend = 43, divisor = 8`

```
═══════════════════════════════════════════════════════════

Setup:
  a = |43| = 43
  b = |8| = 8
  result = 0
  
  Both positive, so result will be positive.

═══════════════════════════════════════════════════════════

Iteration 1: Find largest multiple of 8 that fits in 43

  Start: temp = 8, multiple = 1
  
  Can we double? 8 << 1 = 16 ≤ 43? Yes!
    temp = 16, multiple = 2
  
  Can we double? 16 << 1 = 32 ≤ 43? Yes!
    temp = 32, multiple = 4
  
  Can we double? 32 << 1 = 64 ≤ 43? No! (64 > 43)
    Stop doubling.
  
  Subtract: a = 43 - 32 = 11
  Add to result: result = 0 + 4 = 4
  
  State: a = 11, result = 4

═══════════════════════════════════════════════════════════

Iteration 2: Find largest multiple of 8 that fits in 11

  Start: temp = 8, multiple = 1
  
  Can we double? 8 << 1 = 16 ≤ 11? No! (16 > 11)
    Stop doubling.
  
  Subtract: a = 11 - 8 = 3
  Add to result: result = 4 + 1 = 5
  
  State: a = 3, result = 5

═══════════════════════════════════════════════════════════

Check: a = 3 < 8 = b

  3 < 8, so we can't subtract anymore.
  Loop exits.

═══════════════════════════════════════════════════════════

Final Result: 5 ✓

43 / 8 = 5 (with remainder 3)
```

--

## The Code (With Line-by-Line Explanation)

```java
int divide(int dividend, int divisor) {
    // Edge case: overflow when dividing MIN_VALUE by -1
    if (dividend == Integer.MIN_VALUE && divisor == -1) {
        return Integer.MAX_VALUE;  // Would overflow to 2^31
    }
    
    // Determine the sign of the result
    // XOR of signs: negative if exactly one is negative
    boolean negative = (dividend < 0) ^ (divisor < 0);
    
    // Work with absolute values (use long to handle MIN_VALUE)
    long a = Math.abs((long) dividend);
    long b = Math.abs((long) divisor);
    
    int result = 0;
    
    // Keep subtracting while dividend >= divisor
    while (a >= b) {
        long temp = b;      // Start with divisor
        long multiple = 1;  // Start with multiplier 1
        
        // Double temp while it still fits in a
        // temp << 1 = temp × 2
        while (a >= (temp << 1)) {
            temp <<= 1;      // Double temp
            multiple <<= 1;  // Double multiple
        }
        
        // Subtract the largest multiple that fits
        a -= temp;
        result += multiple;
    }
    
    // Apply sign
    return negative ? -result : result;
}
```

--

## Why We Use Long

```
Problem: Integer.MIN_VALUE = -2147483648

Math.abs(Integer.MIN_VALUE) = -2147483648 (overflow!)

Because in two's complement:
  MIN_VALUE = -2^31
  MAX_VALUE = 2^31 - 1
  
  There's no positive int that equals |MIN_VALUE|!

Solution: Cast to long first, then take abs.
  Math.abs((long) Integer.MIN_VALUE) = 2147483648L ✓
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Integer overflow | MIN_VALUE / -1 overflows | Check and return MAX_VALUE |
| Using int for abs | abs(MIN_VALUE) overflows | Use long |
| Infinite loop | If divisor = 0 | Problem guarantees divisor ≠ 0 |
| Wrong sign handling | Must handle all 4 sign combinations | Use XOR of signs |
| Shifting too far | temp << 1 can overflow | Check before shifting |

--

## Time Complexity Analysis

```
Naive subtraction: O(dividend / divisor)
  - Worst case: 2^31 / 1 = 2^31 iterations

Bit shift approach: O(log(dividend / divisor))
  - Each iteration at least halves the remaining dividend
  - At most 32 iterations (for 32-bit integers)

Example: 1000000 / 3
  Naive: ~333,333 iterations
  Bit shift: ~20 iterations
```

--

## Mind-Map Anchor

```
DIVIDE WITHOUT / * %
        │
        ▼
┌─────────────────────────────────────────┐
│ Division = repeated subtraction         │
│ But subtract in POWERS OF 2!            │
│                                         │
│ While dividend >= divisor:              │
│   1. Find largest k where               │
│      (divisor << k) <= dividend         │
│                                         │
│   2. Subtract (divisor << k)            │
│                                         │
│   3. Add 2^k to result                  │
│                                         │
│ Handle: overflow, signs, use long       │
│                                         │
│ O(log n) time                           │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Shift divisor up, subtract largest fit, accumulate powers of 2"

--

# PATTERN 12: Bitwise AND of Numbers Range (LeetCode 201)

## Pattern Recognition Signal

**When you see:** "AND all numbers in a range", "bitwise AND from left to right", "common prefix in binary"

**Instant thought:** "Any bit that changes in the range becomes 0! Find the common prefix of left and right!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: left = 5, right = 7

Compute: 5 AND 6 AND 7

5 = 101
6 = 110
7 = 111

5 & 6 & 7 = ?

Output: 4
```

### Why Most Bits Become 0

```
Key insight: If a bit EVER changes from 0 to 1 (or 1 to 0) in the range,
the AND of that bit position will be 0.

Why? Because:
  - If it's 0 at any point, AND with 0 = 0
  - If it's 1 at any point and 0 at another, AND = 0

Only bits that are ALWAYS 1 throughout the range survive!
```

### The Common Prefix Insight

```
left = 5 = 101
right = 7 = 111

Let's see all numbers in between:
  5 = 101
  6 = 110
  7 = 111

AND them:
  101
& 110
---
  100
& 111
---
  100 = 4

Notice: The result is the COMMON PREFIX of left and right!

left  = 101
right = 111
        ↑
        common prefix is "1" (the leftmost bit)
        
Result = 100 = 4 (common prefix, rest is 0s)
```

### Why Common Prefix?

```
If left and right have different bits at position i:
  - There must be a number in between where bit i flips
  - AND of that position = 0

If left and right have same bits at position i AND all positions to the left:
  - All numbers in between have the same bits there
  - AND of those positions = those bits

So: Find where left and right first differ (from the left)
    Everything to the left is the common prefix
    Everything at and to the right becomes 0
```

### The Algorithm in Plain English

```
1. While left != right:
   a. Right-shift both left and right by 1
   b. Count how many shifts
2. Left-shift the common prefix back by the shift count
3. Return the result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `left = 5, right = 7`

```
═══════════════════════════════════════════════════════════

Initial:
  left  = 5 = 101
  right = 7 = 111
  shift = 0

═══════════════════════════════════════════════════════════

Iteration 1: left != right (5 != 7)

  left  = 101 >> 1 = 010 = 2
  right = 111 >> 1 = 011 = 3
  shift = 1
  
  "Removed rightmost bit from both"

═══════════════════════════════════════════════════════════

Iteration 2: left != right (2 != 3)

  left  = 010 >> 1 = 001 = 1
  right = 011 >> 1 = 001 = 1
  shift = 2
  
  "Removed another bit, now they're equal!"

═══════════════════════════════════════════════════════════

left == right (1 == 1), loop exits

Common prefix found: 1 (binary: 001 after shifts)

═══════════════════════════════════════════════════════════

Restore: Shift the common prefix back

  result = left << shift
         = 1 << 2
         = 001 << 2
         = 100
         = 4

═══════════════════════════════════════════════════════════

Final Result: 4 ✓

Verification:
  5 & 6 & 7 = 101 & 110 & 111 = 100 = 4 ✓
```

--

## Another Example: Larger Range

**Input:** `left = 10, right = 15`

```
═══════════════════════════════════════════════════════════

Binary representations:
  10 = 1010
  11 = 1011
  12 = 1100
  13 = 1101
  14 = 1110
  15 = 1111

AND all: 1010 & 1011 & 1100 & 1101 & 1110 & 1111 = 1000 = 8

═══════════════════════════════════════════════════════════

Using our algorithm:

  left = 10 = 1010, right = 15 = 1111, shift = 0
  
  10 != 15:
    left = 0101 = 5, right = 0111 = 7, shift = 1
  
  5 != 7:
    left = 0010 = 2, right = 0011 = 3, shift = 2
  
  2 != 3:
    left = 0001 = 1, right = 0001 = 1, shift = 3
  
  1 == 1, done!
  
  result = 1 << 3 = 1000 = 8 ✓

═══════════════════════════════════════════════════════════
```

--

## The Code (With Line-by-Line Explanation)

```java
int rangeBitwiseAnd(int left, int right) {
    int shift = 0;  // Count how many bits we shift off
    
    // Keep shifting until left and right are equal
    // This finds the common prefix
    while (left < right) {
        left >>= 1;   // Remove rightmost bit from left
        right >>= 1;  // Remove rightmost bit from right
        shift++;      // Count the shift
    }
    
    // left now contains the common prefix
    // Shift it back to its original position
    return left << shift;
}
```

### Alternative: Using Brian Kernighan's Trick

```java
int rangeBitwiseAnd(int left, int right) {
    // Keep removing rightmost 1 from right until right <= left
    while (right > left) {
        right = right & (right - 1);  // Remove rightmost 1
    }
    return right;  // What remains is the common prefix
}
```

--

## Why the Alternative Works

```
right & (right - 1) removes the rightmost 1 bit.

If right > left, there's at least one bit position where:
  - right has a 1
  - left has a 0 (or right has more 1s to the right)

Removing rightmost 1s from right eventually makes right <= left.
What remains is the common prefix!

Example: left = 5 (101), right = 7 (111)
  7 & 6 = 111 & 110 = 110 = 6
  6 > 5, continue
  6 & 5 = 110 & 101 = 100 = 4
  4 < 5, stop
  
  Result: 4 ✓
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| ANDing all numbers in loop | O(right - left) is too slow | Use common prefix approach |
| Forgetting to shift back | Common prefix is shifted right | Multiply by 2^shift |
| Using left > right | Should be left < right | Check condition carefully |
| Integer overflow when shifting | left << shift can overflow | Not an issue for valid inputs |

--

## Time Complexity

```
O(log(max(left, right))) = O(32) = O(1)

We shift at most 32 times (for 32-bit integers).
Much better than O(right - left) which could be billions!
```

--

## Mind-Map Anchor

```
BITWISE AND OF RANGE
         │
         ▼
┌─────────────────────────────────────────┐
│ Key insight: Find COMMON PREFIX         │
│                                         │
│ Any bit that changes in range → 0       │
│ Only common prefix bits survive         │
│                                         │
│ Algorithm:                              │
│   While left != right:                  │
│     left >>= 1                          │
│     right >>= 1                         │
│     shift++                             │
│                                         │
│   Return left << shift                  │
│                                         │
│ O(log n) = O(32) = O(1) time            │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Shift until equal, that's the common prefix, shift back"

--

# PATTERN 13: Subsets using Bitmask (LeetCode 78)

## Pattern Recognition Signal

**When you see:** "generate all subsets", "power set", "all combinations", "2^n possibilities"

**Instant thought:** "Each element is either IN or OUT! Use numbers 0 to 2^n-1 as bitmasks!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [1, 2, 3]

Generate ALL possible subsets (including empty set).

Output: [[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]]

That's 2^3 = 8 subsets!
```

### The Bitmask Insight

```
For n elements, each element has 2 choices: IN or OUT.
Total combinations = 2 × 2 × 2 × ... (n times) = 2^n

We can represent each subset as a binary number!
  - Bit i = 1 means element i is IN the subset
  - Bit i = 0 means element i is OUT

For nums = [1, 2, 3]:
  000 = 0 → [] (no elements)
  001 = 1 → [1] (element 0 only)
  010 = 2 → [2] (element 1 only)
  011 = 3 → [1, 2] (elements 0 and 1)
  100 = 4 → [3] (element 2 only)
  101 = 5 → [1, 3] (elements 0 and 2)
  110 = 6 → [2, 3] (elements 1 and 2)
  111 = 7 → [1, 2, 3] (all elements)
```

### Why This Works

```
Numbers from 0 to 2^n - 1 cover ALL possible bit patterns of length n.

Each bit pattern = one unique subset.

So iterating 0 to 2^n - 1 gives us ALL subsets!
```

### The Algorithm in Plain English

```
1. Calculate total = 2^n (number of subsets)
2. For each mask from 0 to total - 1:
   a. Create an empty subset
   b. For each bit position i (0 to n-1):
      - If bit i is set in mask, add nums[i] to subset
   c. Add subset to result
3. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [a, b, c]` (using letters for clarity)

```
n = 3, total = 2^3 = 8

═══════════════════════════════════════════════════════════

mask = 0 (binary: 000)

  Check each bit:
    bit 0: (0 & 1) = 0 → don't include nums[0]
    bit 1: (0 & 2) = 0 → don't include nums[1]
    bit 2: (0 & 4) = 0 → don't include nums[2]
  
  Subset: []

═══════════════════════════════════════════════════════════

mask = 1 (binary: 001)

  Check each bit:
    bit 0: (1 & 1) = 1 → include nums[0] = 'a'
    bit 1: (1 & 2) = 0 → don't include nums[1]
    bit 2: (1 & 4) = 0 → don't include nums[2]
  
  Subset: [a]

═══════════════════════════════════════════════════════════

mask = 2 (binary: 010)

  Check each bit:
    bit 0: (2 & 1) = 0 → don't include nums[0]
    bit 1: (2 & 2) = 2 → include nums[1] = 'b'
    bit 2: (2 & 4) = 0 → don't include nums[2]
  
  Subset: [b]

═══════════════════════════════════════════════════════════

mask = 3 (binary: 011)

  Check each bit:
    bit 0: (3 & 1) = 1 → include nums[0] = 'a'
    bit 1: (3 & 2) = 2 → include nums[1] = 'b'
    bit 2: (3 & 4) = 0 → don't include nums[2]
  
  Subset: [a, b]

═══════════════════════════════════════════════════════════

mask = 4 (binary: 100)

  Check each bit:
    bit 0: (4 & 1) = 0 → don't include nums[0]
    bit 1: (4 & 2) = 0 → don't include nums[1]
    bit 2: (4 & 4) = 4 → include nums[2] = 'c'
  
  Subset: [c]

═══════════════════════════════════════════════════════════

mask = 5 (binary: 101)

  Check each bit:
    bit 0: (5 & 1) = 1 → include nums[0] = 'a'
    bit 1: (5 & 2) = 0 → don't include nums[1]
    bit 2: (5 & 4) = 4 → include nums[2] = 'c'
  
  Subset: [a, c]

═══════════════════════════════════════════════════════════

mask = 6 (binary: 110)

  Check each bit:
    bit 0: (6 & 1) = 0 → don't include nums[0]
    bit 1: (6 & 2) = 2 → include nums[1] = 'b'
    bit 2: (6 & 4) = 4 → include nums[2] = 'c'
  
  Subset: [b, c]

═══════════════════════════════════════════════════════════

mask = 7 (binary: 111)

  Check each bit:
    bit 0: (7 & 1) = 1 → include nums[0] = 'a'
    bit 1: (7 & 2) = 2 → include nums[1] = 'b'
    bit 2: (7 & 4) = 4 → include nums[2] = 'c'
  
  Subset: [a, b, c]

═══════════════════════════════════════════════════════════

Final Result: [[], [a], [b], [a,b], [c], [a,c], [b,c], [a,b,c]]

All 8 subsets generated! ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int n = nums.length;
    int total = 1 << n;  // 2^n subsets (1 << n = 2^n)
    
    // Iterate through all possible masks (0 to 2^n - 1)
    for (int mask = 0; mask < total; mask++) {
        List<Integer> subset = new ArrayList<>();
        
        // Check each bit position
        for (int i = 0; i < n; i++) {
            // If bit i is set in mask, include nums[i]
            if ((mask & (1 << i)) != 0) {
                subset.add(nums[i]);
            }
        }
        
        result.add(subset);
    }
    
    return result;
}
```

--

## Understanding the Bit Check

```java
(mask & (1 << i)) != 0

1 << i creates a mask with only bit i set:
  1 << 0 = 001 (check bit 0)
  1 << 1 = 010 (check bit 1)
  1 << 2 = 100 (check bit 2)

mask & (1 << i):
  - If bit i is set in mask, result is non-zero
  - If bit i is not set, result is zero

Example: mask = 5 (101), i = 2
  1 << 2 = 100
  101 & 100 = 100 ≠ 0 → bit 2 is set!
```

--

## Bitmask vs Backtracking

```
Bitmask approach:
  - Iterative
  - Easy to understand
  - Works well for n ≤ 20 (2^20 = 1 million)
  - O(n × 2^n) time, O(n × 2^n) space

Backtracking approach:
  - Recursive
  - More flexible (can add constraints)
  - Same complexity

Both are valid! Bitmask is often cleaner for simple subset generation.
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using 2^n directly | Integer overflow for large n | Use 1 << n |
| Wrong bit check | Must use (mask & (1 << i)) != 0 | Don't forget != 0 |
| Off-by-one in total | Should be 2^n, not 2^n - 1 | Loop from 0 to total - 1 |
| Large n | 2^30 is too many subsets | Bitmask works for n ≤ ~20 |

--

## Applications of Bitmask Subsets

```
1. Generate all subsets (this problem)
2. Subset sum problems
3. Traveling Salesman Problem (TSP)
4. Set cover problems
5. Game state representation
6. Feature selection in ML
```

--

## Mind-Map Anchor

```
SUBSETS WITH BITMASK
         │
         ▼
┌─────────────────────────────────────────┐
│ n elements → 2^n subsets                │
│                                         │
│ Each number 0 to 2^n-1 = one subset     │
│   bit i = 1 → include element i         │
│   bit i = 0 → exclude element i         │
│                                         │
│ For each mask in [0, 2^n):              │
│   For each bit i in [0, n):             │
│     if (mask & (1 << i)) != 0:          │
│       add nums[i] to subset             │
│                                         │
│ O(n × 2^n) time and space               │
└─────────────────────────────────────────┘
```

**Memory phrase:** "0 to 2^n-1, each bit = include/exclude"

--

# PATTERN 14: Gray Code (LeetCode 89)

## Pattern Recognition Signal

**When you see:** "Gray code", "consecutive numbers differ by 1 bit", "reflected binary code"

**Instant thought:** "Magic formula: Gray(i) = i XOR (i >> 1)!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 2

Generate a sequence of 2^n numbers where:
  - Each number appears exactly once
  - Consecutive numbers differ by exactly 1 bit
  - The sequence starts with 0

For n = 2, we need 4 numbers: 0, 1, 2, 3 (in some order)

Valid Gray code: [0, 1, 3, 2]
  0 = 00
  1 = 01  (differs from 00 by 1 bit) ✓
  3 = 11  (differs from 01 by 1 bit) ✓
  2 = 10  (differs from 11 by 1 bit) ✓

Output: [0, 1, 3, 2]
```

### Why Gray Code Matters

```
Real-world use: Rotary encoders, error correction, genetic algorithms

Problem with normal binary:
  Going from 7 (0111) to 8 (1000) changes 4 bits!
  If bits don't change simultaneously, you might read 0000 or 1111 briefly.

Gray code: Only 1 bit changes at a time, preventing glitches.
```

### The Magic Formula: i XOR (i >> 1)

```
Gray code of i = i ^ (i >> 1)

Why does this work?

i >> 1 shifts i right by 1 (divides by 2, drops rightmost bit)
XOR with original creates a pattern where only 1 bit differs between consecutive values.

Let's verify:
  i = 0: 0 ^ 0 = 0 (00)
  i = 1: 1 ^ 0 = 1 (01)
  i = 2: 2 ^ 1 = 3 (11)
  i = 3: 3 ^ 1 = 2 (10)

Check differences:
  00 → 01: 1 bit differs ✓
  01 → 11: 1 bit differs ✓
  11 → 10: 1 bit differs ✓
```

### Visual Proof of the Formula

```
i     i>>1   i^(i>>1)  Binary
0     0      0         00
1     0      1         01
2     1      3         11
3     1      2         10
4     2      6         110
5     2      7         111
6     3      5         101
7     3      4         100

Notice: Each consecutive pair differs by exactly 1 bit!
```

### The Algorithm in Plain English

```
1. Calculate total = 2^n (number of codes)
2. For each i from 0 to total - 1:
   - Gray code = i ^ (i >> 1)
   - Add to result
3. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `n = 3`

```
total = 2^3 = 8

═══════════════════════════════════════════════════════════

i = 0:
  i >> 1 = 0 >> 1 = 0
  i ^ (i >> 1) = 0 ^ 0 = 0
  
  Binary: 000
  Gray code: 0

═══════════════════════════════════════════════════════════

i = 1:
  i >> 1 = 1 >> 1 = 0
  i ^ (i >> 1) = 1 ^ 0 = 1
  
  Binary: 001
  Gray code: 1
  
  Difference from previous (000 → 001): 1 bit ✓

═══════════════════════════════════════════════════════════

i = 2:
  i >> 1 = 2 >> 1 = 1
  i ^ (i >> 1) = 2 ^ 1 = 3
  
  Binary: 010 ^ 001 = 011
  Gray code: 3
  
  Difference from previous (001 → 011): 1 bit ✓

═══════════════════════════════════════════════════════════

i = 3:
  i >> 1 = 3 >> 1 = 1
  i ^ (i >> 1) = 3 ^ 1 = 2
  
  Binary: 011 ^ 001 = 010
  Gray code: 2
  
  Difference from previous (011 → 010): 1 bit ✓

═══════════════════════════════════════════════════════════

i = 4:
  i >> 1 = 4 >> 1 = 2
  i ^ (i >> 1) = 4 ^ 2 = 6
  
  Binary: 100 ^ 010 = 110
  Gray code: 6
  
  Difference from previous (010 → 110): 1 bit ✓

═══════════════════════════════════════════════════════════

i = 5:
  i >> 1 = 5 >> 1 = 2
  i ^ (i >> 1) = 5 ^ 2 = 7
  
  Binary: 101 ^ 010 = 111
  Gray code: 7
  
  Difference from previous (110 → 111): 1 bit ✓

═══════════════════════════════════════════════════════════

i = 6:
  i >> 1 = 6 >> 1 = 3
  i ^ (i >> 1) = 6 ^ 3 = 5
  
  Binary: 110 ^ 011 = 101
  Gray code: 5
  
  Difference from previous (111 → 101): 1 bit ✓

═══════════════════════════════════════════════════════════

i = 7:
  i >> 1 = 7 >> 1 = 3
  i ^ (i >> 1) = 7 ^ 3 = 4
  
  Binary: 111 ^ 011 = 100
  Gray code: 4
  
  Difference from previous (101 → 100): 1 bit ✓

═══════════════════════════════════════════════════════════

Final Result: [0, 1, 3, 2, 6, 7, 5, 4]

In binary: [000, 001, 011, 010, 110, 111, 101, 100]

Each consecutive pair differs by exactly 1 bit! ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
List<Integer> grayCode(int n) {
    List<Integer> result = new ArrayList<>();
    int total = 1 << n;  // 2^n codes
    
    for (int i = 0; i < total; i++) {
        // Gray code formula: i XOR (i shifted right by 1)
        int gray = i ^ (i >> 1);
        result.add(gray);
    }
    
    return result;
}
```

### One-liner Version

```java
List<Integer> grayCode(int n) {
    return IntStream.range(0, 1 << n)
                    .map(i -> i ^ (i >> 1))
                    .boxed()
                    .collect(Collectors.toList());
}
```

--

## Why i ^ (i >> 1) Works (Mathematical Proof)

```
Consider consecutive numbers i and i+1.

Case 1: i ends in 0 (i = ...x0)
  i+1 = ...x1 (just the last bit changes)
  
  Gray(i)   = ...x0 ^ ...0x = ...?0 (some pattern ending in 0)
  Gray(i+1) = ...x1 ^ ...0x = ...?1 (same pattern ending in 1)
  
  Only the last bit differs! ✓

Case 2: i ends in 1 (i = ...01...1)
  i+1 = ...10...0 (carry propagates)
  
  The XOR with shifted version ensures only 1 bit changes.
  (Full proof involves induction on bit positions)

Key insight: XOR with shifted self "smooths" the transitions!
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Forgetting the formula | Hard to derive on the spot | Memorize: i ^ (i >> 1) |
| Using wrong shift direction | Must be right shift | i >> 1, not i << 1 |
| Off-by-one in total | Should be 2^n codes | Use 1 << n |
| Returning binary strings | Problem asks for integers | Return the integer values |

--

## Alternative: Reflection Method

```
Build Gray code by reflection:

n=1: [0, 1]

n=2: Take n=1, prefix with 0: [00, 01]
     Take n=1 reversed, prefix with 1: [11, 10]
     Result: [00, 01, 11, 10] = [0, 1, 3, 2]

n=3: Take n=2, prefix with 0: [000, 001, 011, 010]
     Take n=2 reversed, prefix with 1: [110, 111, 101, 100]
     Result: [0, 1, 3, 2, 6, 7, 5, 4]

This is why it's called "reflected binary code"!
```

--

## Mind-Map Anchor

```
GRAY CODE
    │
    ▼
┌─────────────────────────────────────────┐
│ Gray code: consecutive numbers differ   │
│            by exactly 1 bit             │
│                                         │
│ Magic formula: Gray(i) = i ^ (i >> 1)   │
│                                         │
│ For n bits:                             │
│   Generate 2^n codes (0 to 2^n - 1)     │
│   Apply formula to each                 │
│                                         │
│ Example (n=2):                          │
│   0^0=0, 1^0=1, 2^1=3, 3^1=2            │
│   [0, 1, 3, 2] = [00, 01, 11, 10]       │
│                                         │
│ O(2^n) time and space                   │
└─────────────────────────────────────────┘
```

**Memory phrase:** "i XOR (i >> 1) gives Gray code"

--

# PATTERN 15: Maximum XOR of Two Numbers (LeetCode 421)

## Pattern Recognition Signal

**When you see:** "maximum XOR of two numbers", "find pair with largest XOR", "optimize XOR search"

**Instant thought:** "Build a Trie of binary representations! For each number, greedily pick opposite bits!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: nums = [3, 10, 5, 25, 2, 8]

Find two numbers whose XOR is maximum.

Best pair: 5 XOR 25 = 00101 XOR 11001 = 11100 = 28

Output: 28
```

### Why Brute Force is Too Slow

```
Brute force: Try all pairs
  - n numbers → n(n-1)/2 pairs
  - O(n²) time

For n = 20,000, that's 200 million pairs!
We need something faster.
```

### The Key Insight: Maximize Bit by Bit

```
To maximize XOR, we want as many 1s as possible in the result.
Start from the MOST SIGNIFICANT BIT (MSB) and work down.

For each bit position (from left to right):
  - If we can make this bit 1 in the XOR, do it!
  - A bit is 1 in XOR when the two numbers have DIFFERENT bits there

Strategy: For each number, find another number that has OPPOSITE bits
          (especially at higher positions).
```

### The Trie Approach

```
Build a Trie where:
  - Each path from root to leaf represents a number's binary form
  - Each node has two children: 0 and 1

For each number, traverse the Trie trying to go the OPPOSITE direction:
  - If current bit is 0, try to go to child 1
  - If current bit is 1, try to go to child 0
  - If opposite child doesn't exist, go to same child

This greedily maximizes XOR!
```

### Visual Example

```
nums = [3, 10, 5]

Binary (5 bits):
  3  = 00011
  10 = 01010
  5  = 00101

Build Trie:
                root
               /    \
              0      (1 not used yet)
             / \
            0   1
           /     \
          0       0
         / \       \
        1   1       1
       /     \       \
      1       0       0
     (3)     (5)     (10)

For num = 5 (00101), find max XOR:
  bit 4: 0, want 1, only 0 exists → go 0, XOR bit = 0
  bit 3: 0, want 1, 1 exists (path to 10) → go 1, XOR bit = 1
  bit 2: 1, want 0, 0 exists → go 0, XOR bit = 1
  bit 1: 0, want 1, 1 exists → go 1, XOR bit = 1
  bit 0: 1, want 0, 0 exists → go 0, XOR bit = 1
  
  XOR = 01111 = 15

Actually, let me recalculate with correct Trie...
```

### The Algorithm in Plain English

```
1. Build a Trie of all numbers (binary representation, MSB first)
2. For each number in array:
   a. Traverse Trie, at each bit try to go OPPOSITE direction
   b. Track the XOR value being built
   c. Update max XOR if current is larger
3. Return max XOR
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `nums = [3, 10, 5, 25, 2, 8]`

```
Binary (5 bits for simplicity):
  3  = 00011
  10 = 01010
  5  = 00101
  25 = 11001
  2  = 00010
  8  = 01000

═══════════════════════════════════════════════════════════

STEP 1: Build Trie (insert all numbers)

After inserting all numbers, Trie has paths for:
  00011 (3)
  01010 (10)
  00101 (5)
  11001 (25)
  00010 (2)
  01000 (8)

═══════════════════════════════════════════════════════════

STEP 2: For each number, find max XOR partner

Processing num = 5 (00101):

  Bit 4 (MSB): num has 0, want 1
    Does child 1 exist? YES (path to 25)
    Go to child 1, XOR bit = 1
    currXOR = 10000
  
  Bit 3: num has 0, want 1
    Does child 1 exist? YES
    Go to child 1, XOR bit = 1
    currXOR = 11000
  
  Bit 2: num has 1, want 0
    Does child 0 exist? YES
    Go to child 0, XOR bit = 1
    currXOR = 11100
  
  Bit 1: num has 0, want 1
    Does child 1 exist? NO (only 0 on this path)
    Go to child 0, XOR bit = 0
    currXOR = 11100
  
  Bit 0: num has 1, want 0
    Does child 0 exist? NO (only 1 on this path)
    Go to child 1, XOR bit = 0
    currXOR = 11100 = 28
  
  Max XOR with 5 = 28 (partner is 25)
  5 XOR 25 = 00101 XOR 11001 = 11100 = 28 ✓

═══════════════════════════════════════════════════════════

Continue for other numbers...

After processing all:
  Max XOR found = 28 (from 5 XOR 25)

═══════════════════════════════════════════════════════════

Final Result: 28 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
class Solution {
    // Trie node with two children (for bits 0 and 1)
    class TrieNode {
        TrieNode[] children = new TrieNode[2];
    }
    
    public int findMaximumXOR(int[] nums) {
        // Build Trie
        TrieNode root = new TrieNode();
        
        for (int num : nums) {
            TrieNode node = root;
            // Insert each bit from MSB (bit 31) to LSB (bit 0)
            for (int i = 31; i >= 0; i-) {
                int bit = (num >> i) & 1;  // Extract bit i
                if (node.children[bit] == null) {
                    node.children[bit] = new TrieNode();
                }
                node = node.children[bit];
            }
        }
        
        // Find max XOR
        int maxXor = 0;
        
        for (int num : nums) {
            TrieNode node = root;
            int currXor = 0;
            
            // For each bit, try to go opposite direction
            for (int i = 31; i >= 0; i-) {
                int bit = (num >> i) & 1;
                int oppositeBit = 1 - bit;  // 0→1, 1→0
                
                if (node.children[oppositeBit] != null) {
                    // Can go opposite! This bit will be 1 in XOR
                    currXor |= (1 << i);
                    node = node.children[oppositeBit];
                } else {
                    // Must go same direction, this bit will be 0 in XOR
                    node = node.children[bit];
                }
            }
            
            maxXor = Math.max(maxXor, currXor);
        }
        
        return maxXor;
    }
}
```

--

## Why Greedy Works

```
XOR maximization is greedy-safe because:

1. Higher bits contribute more to the value
   - Bit 31 contributes 2^31
   - Bit 0 contributes 2^0 = 1

2. Making bit i = 1 is ALWAYS better than making it 0
   - Even if all lower bits are 1, they sum to less than bit i

3. So we greedily maximize from MSB to LSB
   - At each bit, if we CAN make it 1, we SHOULD

This is why the Trie approach works!
```

--

## Time and Space Complexity

```
Time: O(n × 32) = O(n)
  - Insert n numbers, each takes 32 steps
  - Query n numbers, each takes 32 steps

Space: O(n × 32) = O(n)
  - Trie has at most n × 32 nodes
  - In practice, many paths are shared
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Processing LSB first | MSB matters more for max | Process from bit 31 down to 0 |
| Forgetting to check null | Opposite child might not exist | Always check before traversing |
| Using HashMap instead | Works but slower | Trie is O(1) per bit |
| Not handling negative numbers | Bit 31 is sign bit | Algorithm handles it correctly |

--

## Mind-Map Anchor

```
MAXIMUM XOR (TRIE)
        │
        ▼
┌─────────────────────────────────────────┐
│ Goal: Maximize XOR of two numbers       │
│                                         │
│ Insight: To maximize XOR, want OPPOSITE │
│          bits, especially at high pos   │
│                                         │
│ Algorithm:                              │
│   1. Build Trie of all numbers (binary) │
│   2. For each number:                   │
│      - Traverse Trie                    │
│      - At each bit, try OPPOSITE child  │
│      - Track XOR value being built      │
│   3. Return max XOR found               │
│                                         │
│ O(32n) = O(n) time                      │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Trie of bits, greedily pick opposite, maximize from MSB"

--

# MAANG Coverage Map

| Pattern | Problem | Company Tags | Difficulty |
|-----|-----|-------|------|
| 0 | Single Number | Amazon, Google, Facebook | Easy |
| 1 | Number of 1 Bits | Microsoft, Apple | Easy |
| 2 | Counting Bits | Google, Amazon | Easy |
| 3 | Reverse Bits | Apple, Microsoft | Easy |
| 4 | Missing Number | Amazon, Microsoft, Facebook | Easy |
| 5 | Power of Two | Google, Amazon | Easy |
| 5b | Power of Four | Google | Easy |
| 6 | Single Number II | Google, Amazon | Medium |
| 7 | Single Number III | Facebook, Google | Medium |
| 8 | Hamming Distance | Facebook | Easy |
| 9 | Total Hamming Distance | Facebook | Medium |
| 10 | Sum of Two Integers | Facebook, Microsoft | Medium |
| 11 | Divide Two Integers | Facebook, Microsoft | Medium |
| 12 | Bitwise AND of Range | Amazon, Microsoft | Medium |
| 13 | Subsets (Bitmask) | Amazon, Facebook, Google | Medium |
| 14 | Gray Code | Amazon, Microsoft | Medium |
| 15 | Maximum XOR | Google, Amazon | Medium |

--

# Bit Manipulation Cheat Sheet

## The 6 Magic Formulas

| Formula | What It Does | Use Case |
|-----|-------|-----|
| `n & (1 << i)` | Check if bit i is set | Test specific bit |
| `n \| (1 << i)` | Set bit i | Turn on a bit |
| `n & ~(1 << i)` | Clear bit i | Turn off a bit |
| `n ^ (1 << i)` | Toggle bit i | Flip a bit |
| `n & (n-1)` | Remove rightmost 1 | Count bits, power of 2 |
| `n & (-n)` | Isolate rightmost 1 | Find lowest set bit |

## XOR Properties

| Property | Formula | Use Case |
|-----|-----|-----|
| Self-cancel | `a ^ a = 0` | Find unique element |
| Identity | `a ^ 0 = a` | No change |
| Commutative | `a ^ b = b ^ a` | Order doesn't matter |

## Quick Patterns

| Problem Type | Technique |
|-------|------|
| Find unique (others appear 2x) | XOR all |
| Find unique (others appear 3x) | Count bits mod 3 |
| Find two uniques | XOR all, split by diff bit |
| Count set bits | `n & (n-1)` loop |
| Power of 2 | `n & (n-1) == 0` |
| Generate subsets | Bitmask 0 to 2^n-1 |
| Add without + | XOR + (AND << 1) loop |
| Common prefix in range | Shift until equal |

--

# Mastery Checklist

## Foundational (Must Know)
- [ ] Single Number (XOR all)
- [ ] Number of 1 Bits (n & (n-1))
- [ ] Power of Two (single bit check)
- [ ] Missing Number (XOR indices and values)
- [ ] Reverse Bits (extract right, place left)

## Intermediate (Interview Favorites)
- [ ] Single Number II (bit counting mod 3)
- [ ] Single Number III (split by diff bit)
- [ ] Hamming Distance (XOR + count)
- [ ] Subsets with Bitmask (0 to 2^n-1)
- [ ] Sum of Two Integers (XOR + carry)

## Advanced (Differentiators)
- [ ] Total Hamming Distance (per-bit counting)
- [ ] Bitwise AND of Range (common prefix)
- [ ] Maximum XOR (Trie approach)
- [ ] Gray Code (i ^ (i >> 1))
- [ ] Divide without operators (bit shifts)

--

# Common Mistakes

| Mistake | Why It's Wrong | Fix |
|-----|--------|---|
| Forgetting `n > 0` in power of 2 | 0 & (0-1) = 0, but 0 is not power of 2 | Always check `n > 0` |
| Using `>>` for negative numbers | Sign bit gets extended | Use `>>>` for unsigned shift |
| Integer overflow in shifts | `1 << 31` overflows | Use `1L << i` for long |
| Forgetting two's complement | `~n = -(n+1)`, not just flipped bits | Remember negative representation |

--

# Interview Explanation Template

When explaining bit manipulation:

1. **State the insight:** "The key insight is that [XOR cancels pairs / n&(n-1) removes rightmost 1 / etc.]"

2. **Explain the operation:** "When we XOR all elements, pairs become 0, leaving only the unique element."

3. **Walk through example:** "For [2,1,2], we get 2^1^2 = 0^1 = 1."

4. **State complexity:** "Time O(n), Space O(1) since we only use a single variable."

5. **Mention edge cases:** "We handle negative numbers correctly because XOR works on all bits including sign bit."

--

# The Final Mental Model

```
┌─────────────────────────────────────────────────────────────────┐
│                    BIT MANIPULATION MASTERY                      │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  1. FIND UNIQUE → XOR (pairs cancel)                            │
│                                                                  │
│  2. COUNT BITS → n & (n-1) loop                                 │
│                                                                  │
│  3. CHECK POWER OF 2 → n & (n-1) == 0                           │
│                                                                  │
│  4. ARITHMETIC → XOR for sum, AND<<1 for carry                  │
│                                                                  │
│  5. GENERATE SUBSETS → bitmask 0 to 2^n-1                       │
│                                                                  │
│  6. FIND COMMON → shift until equal (common prefix)             │
│                                                                  │
│  7. MAXIMIZE XOR → Trie, pick opposite bits                     │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

**Remember:** Every bit problem asks "Which bits matter?" and "How do I isolate/combine them?"

--

# BONUS PATTERNS (Nice-to-Have for Extra Safety)

These patterns are less frequently asked but good to know for complete coverage.

--

## PATTERN 16: UTF-8 Validation (LeetCode 393)

## Pattern Recognition Signal

**When you see:** "validate UTF-8 encoding", "check byte sequence", "multi-byte character validation"

**Instant thought:** "Count leading 1s to determine byte length! Then verify continuation bytes start with 10!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: data = [197, 130, 1]

Check if this sequence of bytes represents valid UTF-8 encoding.

197 = 11000101 → starts with 110, so 2-byte character
130 = 10000010 → starts with 10, valid continuation byte
1   = 00000001 → starts with 0, so 1-byte character

All valid! Output: true
```

### UTF-8 Encoding Rules

```
UTF-8 uses 1 to 4 bytes per character:

1-byte: 0xxxxxxx (ASCII, 0-127)
2-byte: 110xxxxx 10xxxxxx
3-byte: 1110xxxx 10xxxxxx 10xxxxxx
4-byte: 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx

Key patterns:
  - 1-byte starts with 0
  - 2-byte starts with 110
  - 3-byte starts with 1110
  - 4-byte starts with 11110
  - Continuation bytes start with 10
```

### How to Check Leading Bits

```
To check if byte starts with specific pattern:

0xxxxxxx: (byte >> 7) == 0        (shift 7, check if 0)
110xxxxx: (byte >> 5) == 0b110    (shift 5, check if 110 = 6)
1110xxxx: (byte >> 4) == 0b1110   (shift 4, check if 1110 = 14)
11110xxx: (byte >> 3) == 0b11110  (shift 3, check if 11110 = 30)
10xxxxxx: (byte >> 6) == 0b10     (shift 6, check if 10 = 2)
```

### The Algorithm in Plain English

```
1. Track how many continuation bytes we expect (remaining)
2. For each byte:
   a. If remaining == 0 (starting new character):
      - Check leading bits to determine character length
      - Set remaining = (length - 1)
   b. If remaining > 0 (expecting continuation):
      - Verify byte starts with 10
      - Decrement remaining
3. Return true if remaining == 0 at end (all characters complete)
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `data = [197, 130, 1]`

```
═══════════════════════════════════════════════════════════

Byte 0: 197 = 11000101

  remaining = 0 (starting new character)
  
  Check pattern:
    197 >> 7 = 1 ≠ 0 (not 1-byte)
    197 >> 5 = 110 = 6 = 0b110 ✓ (2-byte character!)
  
  remaining = 2 - 1 = 1 (expect 1 continuation byte)

═══════════════════════════════════════════════════════════

Byte 1: 130 = 10000010

  remaining = 1 (expecting continuation byte)
  
  Check if starts with 10:
    130 >> 6 = 10 = 2 = 0b10 ✓ (valid continuation!)
  
  remaining = 1 - 1 = 0 (character complete)

═══════════════════════════════════════════════════════════

Byte 2: 1 = 00000001

  remaining = 0 (starting new character)
  
  Check pattern:
    1 >> 7 = 0 ✓ (1-byte character!)
  
  remaining = 0 (1-byte needs no continuation)

═══════════════════════════════════════════════════════════

End of data: remaining = 0 ✓

All characters are complete!

Result: true ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
boolean validUtf8(int[] data) {
    int remaining = 0;  // Continuation bytes expected
    
    for (int num : data) {
        if (remaining == 0) {
            // Starting a new character - determine its length
            if ((num >> 7) == 0) {
                // 0xxxxxxx: 1-byte character (ASCII)
                remaining = 0;
            } else if ((num >> 5) == 0b110) {
                // 110xxxxx: 2-byte character
                remaining = 1;
            } else if ((num >> 4) == 0b1110) {
                // 1110xxxx: 3-byte character
                remaining = 2;
            } else if ((num >> 3) == 0b11110) {
                // 11110xxx: 4-byte character
                remaining = 3;
            } else {
                // Invalid start byte (e.g., 10xxxxxx or 11111xxx)
                return false;
            }
        } else {
            // Expecting continuation byte: must start with 10
            if ((num >> 6) != 0b10) {
                return false;
            }
            remaining-;
        }
    }
    
    // All characters must be complete
    return remaining == 0;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Not checking continuation bytes | 10xxxxxx is required | Verify (num >> 6) == 0b10 |
| Accepting 5+ byte sequences | UTF-8 max is 4 bytes | Reject patterns like 111110xx |
| Not checking end state | Incomplete character at end | Return remaining == 0 |
| Using wrong bit masks | Easy to mess up shifts | Double-check shift amounts |

--

## Mind-Map Anchor

```
UTF-8 VALIDATION
       │
       ▼
┌─────────────────────────────────────────┐
│ UTF-8 byte patterns:                    │
│   1-byte: 0xxxxxxx                      │
│   2-byte: 110xxxxx 10xxxxxx             │
│   3-byte: 1110xxxx 10xxxxxx 10xxxxxx    │
│   4-byte: 11110xxx 10xxxxxx 10xxxxxx 10 │
│                                         │
│ Algorithm:                              │
│   1. Count leading 1s → byte length     │
│   2. Track remaining continuation bytes │
│   3. Verify continuations start with 10 │
│   4. Check all characters complete      │
│                                         │
│ O(n) time, O(1) space                   │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Count leading 1s for length, verify 10 continuations"

--

## PATTERN 17: Integer Replacement (LeetCode 397)

## Pattern Recognition Signal

**When you see:** "minimum operations to reach 1", "n/2 if even, n±1 if odd", "optimal path to 1"

**Instant thought:** "Even → divide by 2. Odd → check second bit to decide +1 or -1!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 7

Reach 1 using minimum operations:
  - If n is even: n = n / 2
  - If n is odd: n = n + 1 OR n = n - 1

Find the minimum number of operations.

7 → 8 → 4 → 2 → 1 (4 operations)
or
7 → 6 → 3 → 2 → 1 (4 operations)

Output: 4
```

### The Key Insight: Trailing Zeros Matter

```
Dividing by 2 = right shift = removes trailing zeros.
More trailing zeros = fewer operations!

For odd numbers, we choose +1 or -1 based on which creates more trailing zeros.

n = 7 (111):
  n - 1 = 6 (110) → one trailing zero
  n + 1 = 8 (1000) → three trailing zeros!
  
  Choose +1 because 8 has more trailing zeros!

n = 5 (101):
  n - 1 = 4 (100) → two trailing zeros
  n + 1 = 6 (110) → one trailing zero
  
  Choose -1 because 4 has more trailing zeros!
```

### The Second Bit Rule

```
For odd n, look at the SECOND bit (bit 1):

If second bit is 1 (n ends in ...11):
  n + 1 creates ...00 pattern → more trailing zeros
  Choose +1

If second bit is 0 (n ends in ...01):
  n - 1 creates ...00 pattern → more trailing zeros
  Choose -1

Exception: n = 3
  3 - 1 = 2 → 1 (2 operations)
  3 + 1 = 4 → 2 → 1 (3 operations)
  Always choose -1 for n = 3!
```

### The Algorithm in Plain English

```
1. While n != 1:
   a. If n is even: n = n / 2 (or n >>= 1)
   b. If n is odd:
      - If n == 3 OR second bit is 0: n = n - 1
      - Else: n = n + 1
   c. Increment count
2. Return count
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `n = 7`

```
═══════════════════════════════════════════════════════════

n = 7 (binary: 111), count = 0

  Is n odd? Yes (7 & 1 = 1)
  
  Check second bit: (7 >> 1) & 1 = (011) & 1 = 1
  Second bit is 1, so choose +1
  
  n = 7 + 1 = 8
  count = 1

═══════════════════════════════════════════════════════════

n = 8 (binary: 1000), count = 1

  Is n odd? No (8 & 1 = 0)
  
  n = 8 / 2 = 4
  count = 2

═══════════════════════════════════════════════════════════

n = 4 (binary: 100), count = 2

  Is n odd? No (4 & 1 = 0)
  
  n = 4 / 2 = 2
  count = 3

═══════════════════════════════════════════════════════════

n = 2 (binary: 10), count = 3

  Is n odd? No (2 & 1 = 0)
  
  n = 2 / 2 = 1
  count = 4

═══════════════════════════════════════════════════════════

n = 1, loop exits

Final Result: 4 ✓
```

**Another Example:** `n = 5`

```
═══════════════════════════════════════════════════════════

n = 5 (binary: 101), count = 0

  Is n odd? Yes
  
  Check second bit: (5 >> 1) & 1 = (010) & 1 = 0
  Second bit is 0, so choose -1
  
  n = 5 - 1 = 4
  count = 1

═══════════════════════════════════════════════════════════

n = 4 → 2 → 1 (2 more operations)

Final Result: 3 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int integerReplacement(int n) {
    int count = 0;
    long num = n;  // Use long to avoid overflow at Integer.MAX_VALUE
    
    while (num != 1) {
        if ((num & 1) == 0) {
            // Even: divide by 2
            num >>= 1;
        } else if (num == 3 || ((num >> 1) & 1) == 0) {
            // Odd with second bit 0, OR n == 3: subtract 1
            // This creates more trailing zeros (usually)
            num-;
        } else {
            // Odd with second bit 1: add 1
            // This creates pattern ...100...0 (more trailing zeros)
            num++;
        }
        count++;
    }
    
    return count;
}
```

--

## Why the n == 3 Exception?

```
n = 3 (11):
  Path with -1: 3 → 2 → 1 (2 operations)
  Path with +1: 3 → 4 → 2 → 1 (3 operations)

The second bit rule says +1 (since second bit is 1).
But -1 is actually better for n = 3!

Why? Because 3 - 1 = 2 = 10 (one operation to reach 1)
     But 3 + 1 = 4 = 100 (two operations to reach 1)

This is the only exception to the second bit rule.
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Integer overflow | n + 1 at MAX_VALUE overflows | Use long |
| Forgetting n == 3 exception | Second bit rule fails for 3 | Special case it |
| Always choosing -1 for odd | Not optimal for ...11 patterns | Check second bit |
| Using recursion without memo | Exponential time | Use iterative or memoize |

--

## Mind-Map Anchor

```
INTEGER REPLACEMENT
        │
        ▼
┌─────────────────────────────────────────┐
│ Goal: Reach 1 with minimum operations   │
│                                         │
│ Even: n >>= 1 (divide by 2)             │
│                                         │
│ Odd: Check second bit                   │
│   - Second bit = 0: n- (creates ...00) │
│   - Second bit = 1: n++ (creates ...00) │
│   - Exception: n == 3, always n-       │
│                                         │
│ Why? More trailing zeros = fewer ops    │
│                                         │
│ Use long to avoid overflow              │
│                                         │
│ O(log n) time, O(1) space               │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Even divide, odd check second bit, 3 is special"

--

## PATTERN 18: Binary Watch (LeetCode 401)

## Pattern Recognition Signal

**When you see:** "binary watch", "LEDs turned on", "count set bits for time"

**Instant thought:** "Brute force all valid times! Count set bits in hour + minute, check if equals target!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: turnedOn = 1

A binary watch has:
  - 4 LEDs for hours (0-11): 8, 4, 2, 1
  - 6 LEDs for minutes (0-59): 32, 16, 8, 4, 2, 1

If exactly 1 LED is on, what times are possible?

Hour LEDs (1 on): 1, 2, 4, 8 → hours 1, 2, 4, 8
Minute LEDs (1 on): 1, 2, 4, 8, 16, 32 → minutes 1, 2, 4, 8, 16, 32

Combinations with total 1 LED:
  - Hour has 1 LED, minute has 0: "1:00", "2:00", "4:00", "8:00"
  - Hour has 0 LEDs, minute has 1: "0:01", "0:02", "0:04", "0:08", "0:16", "0:32"

Output: ["0:01", "0:02", "0:04", "0:08", "0:16", "0:32", "1:00", "2:00", "4:00", "8:00"]
```

### The Key Insight: Brute Force is Fine!

```
Total possibilities:
  - Hours: 0-11 (12 values)
  - Minutes: 0-59 (60 values)
  - Total: 12 × 60 = 720 combinations

That's tiny! Just check all of them.

For each (hour, minute) pair:
  - Count set bits in hour
  - Count set bits in minute
  - If total equals turnedOn, add to result
```

### Why Counting Bits Works

```
Binary watch LEDs represent binary numbers:

Hour 5 = 0101 in binary → 2 LEDs on (bits 0 and 2)
Minute 37 = 100101 in binary → 3 LEDs on (bits 0, 2, and 5)

Total LEDs on = bitCount(hour) + bitCount(minute)
              = 2 + 3 = 5
```

### The Algorithm in Plain English

```
1. For each hour from 0 to 11:
   For each minute from 0 to 59:
     a. Count set bits in hour
     b. Count set bits in minute
     c. If total equals turnedOn:
        - Format as "h:mm" and add to result
2. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `turnedOn = 2`

```
═══════════════════════════════════════════════════════════

Checking all (hour, minute) pairs where bitCount(h) + bitCount(m) = 2

═══════════════════════════════════════════════════════════

Hour = 0 (binary: 0000, bits = 0)
  Need minute with 2 bits set.
  
  Minute 3 = 000011 → 2 bits ✓ → "0:03"
  Minute 5 = 000101 → 2 bits ✓ → "0:05"
  Minute 6 = 000110 → 2 bits ✓ → "0:06"
  Minute 9 = 001001 → 2 bits ✓ → "0:09"
  ... (more minutes with 2 bits)

═══════════════════════════════════════════════════════════

Hour = 1 (binary: 0001, bits = 1)
  Need minute with 1 bit set.
  
  Minute 1 = 000001 → 1 bit ✓ → "1:01"
  Minute 2 = 000010 → 1 bit ✓ → "1:02"
  Minute 4 = 000100 → 1 bit ✓ → "1:04"
  Minute 8 = 001000 → 1 bit ✓ → "1:08"
  Minute 16 = 010000 → 1 bit ✓ → "1:16"
  Minute 32 = 100000 → 1 bit ✓ → "1:32"

═══════════════════════════════════════════════════════════

Hour = 2 (binary: 0010, bits = 1)
  Need minute with 1 bit set.
  
  Same minutes as hour 1: "2:01", "2:02", "2:04", "2:08", "2:16", "2:32"

═══════════════════════════════════════════════════════════

Hour = 3 (binary: 0011, bits = 2)
  Need minute with 0 bits set.
  
  Only minute 0 has 0 bits → "3:00"

═══════════════════════════════════════════════════════════

... continue for all hours ...

═══════════════════════════════════════════════════════════

Final Result includes times like:
  "0:03", "0:05", "0:06", "0:09", "0:10", ...
  "1:01", "1:02", "1:04", "1:08", "1:16", "1:32", ...
  "3:00", "5:00", "6:00", "9:00", "10:00", ...
```

--

## The Code (With Line-by-Line Explanation)

```java
List<String> readBinaryWatch(int turnedOn) {
    List<String> result = new ArrayList<>();
    
    // Try all possible hours (0-11)
    for (int h = 0; h < 12; h++) {
        // Try all possible minutes (0-59)
        for (int m = 0; m < 60; m++) {
            // Count total LEDs on (set bits in hour + minute)
            int totalBits = Integer.bitCount(h) + Integer.bitCount(m);
            
            // If matches target, add formatted time
            if (totalBits == turnedOn) {
                // Format: "h:mm" (hour without leading zero, minute with)
                result.add(String.format("%d:%02d", h, m));
            }
        }
    }
    
    return result;
}
```

### Alternative: Manual Bit Counting

```java
List<String> readBinaryWatch(int turnedOn) {
    List<String> result = new ArrayList<>();
    
    for (int h = 0; h < 12; h++) {
        for (int m = 0; m < 60; m++) {
            // Manual bit count using n & (n-1)
            if (countBits(h) + countBits(m) == turnedOn) {
                result.add(h + ":" + (m < 10 ? "0" : "") + m);
            }
        }
    }
    
    return result;
}

int countBits(int n) {
    int count = 0;
    while (n != 0) {
        n &= (n - 1);  // Remove rightmost 1
        count++;
    }
    return count;
}
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Wrong time format | Minutes need leading zero | Use "%d:%02d" format |
| Invalid hours/minutes | Hour > 11 or minute > 59 | Loop bounds handle this |
| Forgetting edge cases | turnedOn = 0 → only "0:00" | Algorithm handles it |
| Overcomplicating | Trying to generate combinations | Brute force is simpler |

--

## Mind-Map Anchor

```
BINARY WATCH
     │
     ▼
┌─────────────────────────────────────────┐
│ Binary watch: 4 hour LEDs + 6 min LEDs  │
│                                         │
│ Brute force all 720 times:              │
│   For h in 0..11:                       │
│     For m in 0..59:                     │
│       if bitCount(h) + bitCount(m)      │
│          == turnedOn:                   │
│         add "h:mm" to result            │
│                                         │
│ Format: hour no leading 0, minute has   │
│                                         │
│ O(1) time (720 iterations max)          │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Brute force hours × minutes, count bits, filter by total"

--

## PATTERN 19: Complement of Base 10 Integer (LeetCode 1009)

## Pattern Recognition Signal

**When you see:** "complement of number", "flip bits", "bitwise complement without leading zeros"

**Instant thought:** "Create a mask of all 1s with same bit length, then XOR!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 5

Find the complement of 5 (flip all bits, but only significant bits).

5 = 101 in binary

Complement: flip each bit
  101 → 010 = 2

Output: 2

Note: We don't flip leading zeros!
  5 is NOT 00000101 → 11111010
  5 IS 101 → 010
```

### Why NOT (~) Doesn't Work Directly

```
~5 in Java:
  5 = 00000000 00000000 00000000 00000101
  ~5 = 11111111 11111111 11111111 11111010 = -6

That's not what we want! We only want to flip the significant bits.
```

### The Key Insight: XOR with All-1s Mask

```
XOR with 1 flips a bit:
  0 ^ 1 = 1
  1 ^ 1 = 0

So if we XOR n with a mask of all 1s (same length as n):
  n = 101
  mask = 111
  n ^ mask = 010 ✓

The trick is building the right mask!
```

### Building the Mask

```
For n = 5 (101), we need mask = 111 (same number of bits)

Method: Start with 1, keep adding 1s until mask >= n

mask = 1 (1)
1 < 5? Yes → mask = (1 << 1) | 1 = 11 = 3
3 < 5? Yes → mask = (3 << 1) | 1 = 111 = 7
7 < 5? No → done!

mask = 7 = 111 ✓
```

### The Algorithm in Plain English

```
1. Handle edge case: if n == 0, return 1
2. Build mask of all 1s with same bit length as n:
   - Start with mask = 1
   - While mask < n: mask = (mask << 1) | 1
3. Return n XOR mask
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `n = 5`

```
═══════════════════════════════════════════════════════════

n = 5 (binary: 101)

Step 1: Build mask of all 1s

  mask = 1 (binary: 1)
  
  Is mask < n? 1 < 5? Yes
    mask = (1 << 1) | 1 = 10 | 1 = 11 = 3
  
  Is mask < n? 3 < 5? Yes
    mask = (3 << 1) | 1 = 110 | 1 = 111 = 7
  
  Is mask < n? 7 < 5? No
    Stop!
  
  mask = 7 (binary: 111)

═══════════════════════════════════════════════════════════

Step 2: XOR n with mask

  n    = 101
  mask = 111
  
  XOR bit by bit:
    1 ^ 1 = 0
    0 ^ 1 = 1
    1 ^ 1 = 0
  
  n ^ mask = 010 = 2

═══════════════════════════════════════════════════════════

Final Result: 2 ✓

Verification:
  5 = 101
  complement = 010 = 2 ✓
```

**Another Example:** `n = 10`

```
═══════════════════════════════════════════════════════════

n = 10 (binary: 1010)

Step 1: Build mask

  mask = 1
  1 < 10? Yes → mask = 3
  3 < 10? Yes → mask = 7
  7 < 10? Yes → mask = 15
  15 < 10? No → Stop!
  
  mask = 15 (binary: 1111)

═══════════════════════════════════════════════════════════

Step 2: XOR

  n    = 1010
  mask = 1111
  
  n ^ mask = 0101 = 5

═══════════════════════════════════════════════════════════

Final Result: 5 ✓

Verification:
  10 = 1010
  complement = 0101 = 5 ✓
```

--

## The Code (With Line-by-Line Explanation)

```java
int bitwiseComplement(int n) {
    // Edge case: complement of 0 is 1
    if (n == 0) return 1;
    
    // Build mask with all 1s, same bit length as n
    int mask = 1;
    while (mask < n) {
        mask = (mask << 1) | 1;  // Shift left and add a 1
        // Equivalent to: mask = mask * 2 + 1
    }
    
    // XOR flips all bits
    return n ^ mask;
}
```

### Alternative: Using Bit Length

```java
int bitwiseComplement(int n) {
    if (n == 0) return 1;
    
    // Find number of bits in n
    int bits = (int)(Math.log(n) / Math.log(2)) + 1;
    
    // Create mask with 'bits' number of 1s
    int mask = (1 << bits) - 1;  // 2^bits - 1 = all 1s
    
    return n ^ mask;
}
```

### Alternative: Using Integer Methods

```java
int bitwiseComplement(int n) {
    if (n == 0) return 1;
    
    // highestOneBit gives the highest power of 2 <= n
    // Multiply by 2 and subtract 1 to get all 1s mask
    int mask = (Integer.highestOneBit(n) << 1) - 1;
    
    return n ^ mask;
}
```

--

## Why n == 0 is Special

```
For n = 0:
  - 0 has no significant bits
  - But complement of 0 should be 1 (flip the "0" to "1")
  
Our mask-building loop doesn't work for 0:
  mask = 1
  1 < 0? No → loop doesn't run
  mask stays 1
  0 ^ 1 = 1 ✓

Actually, it works! But it's clearer to handle explicitly.
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Using ~n directly | Flips all 32 bits, not just significant | Use XOR with mask |
| Forgetting n == 0 | Edge case needs special handling | Return 1 for n == 0 |
| Wrong mask building | Must have same bit length as n | Use while (mask < n) |
| Integer overflow | mask << 1 can overflow | Not an issue for valid inputs |

--

## Mind-Map Anchor

```
COMPLEMENT OF BASE 10
         │
         ▼
┌─────────────────────────────────────────┐
│ Goal: Flip significant bits only        │
│                                         │
│ Problem: ~n flips ALL 32 bits           │
│                                         │
│ Solution: XOR with all-1s mask          │
│                                         │
│ Build mask:                             │
│   mask = 1                              │
│   while mask < n:                       │
│     mask = (mask << 1) | 1              │
│                                         │
│ Result = n ^ mask                       │
│                                         │
│ Edge case: n = 0 → return 1             │
│                                         │
│ O(log n) time, O(1) space               │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Build all-1s mask, XOR to flip, handle zero"

--

## PATTERN 20: Concatenation of Consecutive Binary Numbers (LeetCode 1680)

## Pattern Recognition Signal

**When you see:** "concatenate binary representations", "join binary numbers", "binary string to decimal mod 10^9+7"

**Instant thought:** "Track bit length! Shift result left by length, OR with current number!"

--

## The Mental Model (Before Coding!)

### What's the problem REALLY asking?

```
Input: n = 3

Concatenate binary representations of 1 to n:
  1 = "1"
  2 = "10"
  3 = "11"
  
Concatenated: "1" + "10" + "11" = "11011"

Convert to decimal: 11011 = 27

Output: 27 (mod 10^9 + 7)
```

### The Key Insight: Shift and OR

```
Instead of building a string, work with numbers directly!

To concatenate binary of 'next' to 'result':
  1. Shift result left by (bit length of next)
  2. OR with next

Example: result = 1 (binary: 1), next = 2 (binary: 10)
  - Bit length of 2 = 2
  - result << 2 = 1 << 2 = 100
  - 100 | 10 = 110 = 6

Verify: "1" + "10" = "110" = 6 ✓
```

### Tracking Bit Length

```
Bit length increases at powers of 2:
  1 = 1 bit
  2, 3 = 2 bits
  4, 5, 6, 7 = 3 bits
  8-15 = 4 bits
  ...

How to detect power of 2? Use n & (n-1) == 0!

When i is a power of 2, increment the bit length.
```

### The Algorithm in Plain English

```
1. Initialize result = 0, length = 0
2. For each i from 1 to n:
   a. If i is power of 2: length++
   b. Shift result left by length
   c. OR result with i
   d. Take mod 10^9 + 7
3. Return result
```

--

## Visual Dry Run (Step-by-Step)

**Input:** `n = 3`

```
═══════════════════════════════════════════════════════════

Initial: result = 0, length = 0

═══════════════════════════════════════════════════════════

i = 1:

  Is 1 a power of 2? 1 & 0 = 0 ✓ Yes!
    length = 0 + 1 = 1
  
  Shift result: result << 1 = 0 << 1 = 0
  OR with i: 0 | 1 = 1
  
  result = 1 (binary: 1)
  
  "Concatenated so far: 1"

═══════════════════════════════════════════════════════════

i = 2:

  Is 2 a power of 2? 2 & 1 = 0 ✓ Yes!
    length = 1 + 1 = 2
  
  Shift result: result << 2 = 1 << 2 = 100 = 4
  OR with i: 4 | 2 = 100 | 010 = 110 = 6
  
  result = 6 (binary: 110)
  
  "Concatenated so far: 1 + 10 = 110"

═══════════════════════════════════════════════════════════

i = 3:

  Is 3 a power of 2? 3 & 2 = 2 ≠ 0. No.
    length stays 2
  
  Shift result: result << 2 = 6 << 2 = 11000 = 24
  OR with i: 24 | 3 = 11000 | 00011 = 11011 = 27
  
  result = 27 (binary: 11011)
  
  "Concatenated so far: 110 + 11 = 11011"

═══════════════════════════════════════════════════════════

Final Result: 27 ✓

Verification:
  "1" + "10" + "11" = "11011"
  11011 in binary = 16 + 8 + 0 + 2 + 1 = 27 ✓
```

**Another Example:** `n = 12`

```
═══════════════════════════════════════════════════════════

Concatenate 1 to 12:
  1, 10, 11, 100, 101, 110, 111, 1000, 1001, 1010, 1011, 1100

Binary string: "110111001011101111000100110101011100"

That's a huge number! We need mod 10^9 + 7.

Using our algorithm with mod at each step:
  result = 505379714

═══════════════════════════════════════════════════════════
```

--

## The Code (With Line-by-Line Explanation)

```java
int concatenatedBinary(int n) {
    long result = 0;  // Use long to avoid overflow before mod
    int MOD = 1_000_000_007;
    int length = 0;   // Current bit length
    
    for (int i = 1; i <= n; i++) {
        // Check if i is a power of 2 (bit length increases)
        if ((i & (i - 1)) == 0) {
            length++;
        }
        
        // Shift result left by 'length' bits, then add i
        // This concatenates binary of i to result
        result = ((result << length) | i) % MOD;
    }
    
    return (int) result;
}
```

### Understanding the Shift and OR

```java
result = ((result << length) | i) % MOD;

Step by step:
  1. result << length: Make room for i's bits
  2. | i: Place i's bits in the space we made
  3. % MOD: Keep result manageable

Example: result = 6 (110), i = 3 (11), length = 2
  result << 2 = 11000
  11000 | 00011 = 11011
  
  We've concatenated "110" + "11" = "11011"
```

--

## Why Power of 2 Detection Works

```
Bit length of i:
  1 → 1 bit
  2, 3 → 2 bits
  4, 5, 6, 7 → 3 bits
  8-15 → 4 bits
  
Bit length increases exactly when i is a power of 2!

i & (i-1) == 0 detects powers of 2:
  1 & 0 = 0 ✓ (power of 2)
  2 & 1 = 0 ✓ (power of 2)
  3 & 2 = 2 ≠ 0 (not power of 2)
  4 & 3 = 0 ✓ (power of 2)
```

--

## Common Traps

| Trap | Why Wrong | Fix |
|---|------|---|
| Integer overflow | Result grows exponentially | Use long, mod at each step |
| Forgetting mod | Result exceeds int range | Apply % MOD after each operation |
| Wrong bit length | Must track when length increases | Check power of 2 |
| Building string | Too slow and memory-intensive | Use shift and OR |

--

## Time and Space Complexity

```
Time: O(n)
  - Single pass through 1 to n
  - Each iteration is O(1)

Space: O(1)
  - Only a few variables
  - No string building
```

--

## Mind-Map Anchor

```
CONCATENATION OF BINARY NUMBERS
              │
              ▼
┌─────────────────────────────────────────┐
│ Goal: Concatenate binary of 1 to n      │
│                                         │
│ Key insight: Shift and OR               │
│   result = (result << length) | i       │
│                                         │
│ Track bit length:                       │
│   Increases at powers of 2              │
│   Detect with: i & (i-1) == 0           │
│                                         │
│ Don't forget:                           │
│   - Use long to avoid overflow          │
│   - Apply mod 10^9+7 at each step       │
│                                         │
│ O(n) time, O(1) space                   │
└─────────────────────────────────────────┘
```

**Memory phrase:** "Track bit length, shift and OR, mod at each step"

--

# Updated MAANG Coverage Map (Complete)

| Pattern | Problem | LeetCode | Difficulty | Frequency |
|-----|-----|-----|------|------|
| 0 | Single Number | 136 | Easy | 🔥🔥🔥 |
| 1 | Number of 1 Bits | 191 | Easy | 🔥🔥🔥 |
| 2 | Counting Bits | 338 | Easy | 🔥🔥 |
| 3 | Reverse Bits | 190 | Easy | 🔥🔥 |
| 4 | Missing Number | 268 | Easy | 🔥🔥🔥 |
| 5 | Power of Two | 231 | Easy | 🔥🔥 |
| 5b | Power of Four | 342 | Easy | 🔥 |
| 6 | Single Number II | 137 | Medium | 🔥🔥 |
| 7 | Single Number III | 260 | Medium | 🔥🔥 |
| 8 | Hamming Distance | 461 | Easy | 🔥🔥 |
| 9 | Total Hamming Distance | 477 | Medium | 🔥 |
| 10 | Sum of Two Integers | 371 | Medium | 🔥🔥🔥 |
| 11 | Divide Two Integers | 29 | Medium | 🔥🔥 |
| 12 | Bitwise AND of Range | 201 | Medium | 🔥🔥 |
| 13 | Subsets (Bitmask) | 78 | Medium | 🔥🔥🔥 |
| 14 | Gray Code | 89 | Medium | 🔥 |
| 15 | Maximum XOR | 421 | Medium | 🔥🔥 |
| **BONUS** | | | | |
| 16 | UTF-8 Validation | 393 | Medium | 🔥 |
| 17 | Integer Replacement | 397 | Medium | 🔥 |
| 18 | Binary Watch | 401 | Easy | 🔥 |
| 19 | Complement of Base 10 | 1009 | Easy | 🔥 |
| 20 | Concatenation Binary | 1680 | Medium | 🔥 |

--

# Final Mastery Checklist (Complete L5+ Coverage)

## Tier 1: Must Know (Patterns 0-5)
- [ ] Single Number (XOR all)
- [ ] Number of 1 Bits (n & (n-1))
- [ ] Counting Bits (DP with bits)
- [ ] Reverse Bits (extract right, place left)
- [ ] Missing Number (XOR indices and values)
- [ ] Power of Two/Four (single bit check)

## Tier 2: Interview Favorites (Patterns 6-11)
- [ ] Single Number II (bit counting mod 3)
- [ ] Single Number III (split by diff bit)
- [ ] Hamming Distance (XOR + count)
- [ ] Total Hamming Distance (per-bit counting)
- [ ] Sum of Two Integers (XOR + carry)
- [ ] Divide Two Integers (bit shift division)

## Tier 3: Differentiators (Patterns 12-15)
- [ ] Bitwise AND of Range (common prefix)
- [ ] Subsets with Bitmask (0 to 2^n-1)
- [ ] Gray Code (i ^ (i >> 1))
- [ ] Maximum XOR (Trie approach)

## Tier 4: Bonus Safety Net (Patterns 16-20)
- [ ] UTF-8 Validation (count leading 1s)
- [ ] Integer Replacement (check second bit)
- [ ] Binary Watch (brute force + bitCount)
- [ ] Complement of Base 10 (all-1s mask XOR)
- [ ] Concatenation Binary (shift and OR)
