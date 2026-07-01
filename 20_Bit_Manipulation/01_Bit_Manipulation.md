# Section 20 — Bit Manipulation

---

## 1. What Problem Does This Solve?

Bit manipulation operates directly on binary representations of integers, enabling O(1) operations that would otherwise require O(n) loops or O(n) space. It's used in:
- Finding single/unique numbers (XOR tricks)
- Counting set bits efficiently
- Checking power-of-two properties
- Generating all subsets using bitmask
- Implementing fast arithmetic without `+`, `-`, `*`

---

## 2. Beginner-Friendly Intuition

Every integer is just a sequence of 0s and 1s in binary. Bit operations work on these bits directly — like toggling individual light switches in a row. XOR is especially powerful: `a XOR a = 0` (same values cancel each other out) and `a XOR 0 = a` (XOR with 0 is identity).

---

## 3. Real-World Analogy

**Light switches (XOR):** Toggle a switch twice returns it to original state. If you toggle multiple switches simultaneously, the ones you toggle an even number of times cancel out. XOR tells you which switches were toggled an odd number of times.

**Access control (bitmask flags):** A user's permissions are stored as a bitmask — bit 0 = read, bit 1 = write, bit 2 = execute. Set, check, or clear permissions with AND, OR, XOR.

---

## 4. Core Concept

### Essential Bit Operations

| Operation | Syntax | Effect |
|-----------|--------|--------|
| AND | `a & b` | 1 only if both bits are 1 |
| OR | `a \| b` | 1 if either bit is 1 |
| XOR | `a ^ b` | 1 if bits differ (toggles) |
| NOT | `~a` | Flip all bits |
| Left shift | `a << k` | Multiply by 2^k |
| Right shift | `a >> k` | Divide by 2^k (arithmetic) |
| Unsigned right shift | `a >>> k` | Divide by 2^k (logical, fills 0) |

### Critical Identities

```
a ^ a = 0          (same value cancels)
a ^ 0 = a          (identity)
a & (a-1) = 0      iff a is power of 2
a & (a-1)          removes lowest set bit
a & (-a)           isolates lowest set bit
a ^ b ^ a = b      (XOR twice cancels)
```

---

## 5. Pattern Recognition Signals

Use Bit Manipulation when:
```
"Single number" / "unique element" (others appear twice)
"Missing number" in array
"Power of 2" check
"Count set bits" (Hamming weight/distance)
"Subset generation" (all 2^n subsets)
"XOR tricks" for pairs
"Swap without temp variable"
"Fast multiply/divide by powers of 2"
"Check if k-th bit is set"
```

---

## 6. Step-by-Step Algorithm

### Single Number (XOR)
```
result = 0
For each num in array:
    result ^= num
Return result   ← only the number appearing odd times remains
```

### Count Set Bits (Brian Kernighan)
```
count = 0
While n != 0:
    n = n & (n-1)   ← removes lowest set bit
    count++
Return count
```

### Subset Generation via Bitmask
```
n = array.length
For mask from 0 to 2^n - 1:
    subset = []
    For bit from 0 to n-1:
        If (mask >> bit) & 1 == 1:
            subset.add(array[bit])
    process(subset)
```

---

## 7. Dry Run with Example

### Example 1: Single Number via XOR

**Input:** `[4, 1, 2, 1, 2]`

```
result = 0
0 ^ 4 = 4
4 ^ 1 = 5    (0100 ^ 0001 = 0101)
5 ^ 2 = 7    (0101 ^ 0010 = 0111)
7 ^ 1 = 6    (0111 ^ 0001 = 0110)
6 ^ 2 = 4    (0110 ^ 0010 = 0100)

Result = 4 ✓ (all duplicates canceled via XOR)
```

### Example 2: Count Set Bits (n = 13 = 1101₂)

```
n=13 (1101):
  13 & 12 = 1101 & 1100 = 1100 = 12. count=1
n=12 (1100):
  12 & 11 = 1100 & 1011 = 1000 = 8.  count=2
n=8 (1000):
  8 & 7  = 1000 & 0111 = 0000 = 0.   count=3
n=0: stop

Set bits in 13 = 3 ✓ (1+1+0+1=3)
```

### Example 3: All Subsets of [a, b, c]

```
n=3, 2^3=8 masks

mask=0 (000): no bits set → {}
mask=1 (001): bit 0 → {a}
mask=2 (010): bit 1 → {b}
mask=3 (011): bits 0,1 → {a,b}
mask=4 (100): bit 2 → {c}
mask=5 (101): bits 0,2 → {a,c}
mask=6 (110): bits 1,2 → {b,c}
mask=7 (111): bits 0,1,2 → {a,b,c}

All 8 = 2³ subsets enumerated ✓
```

---

## 8. Code Implementation

### Single Number (All appear twice, one appears once)

```java
int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) result ^= num; // XOR all: duplicates cancel
    return result;
}
```

### Single Number II (All appear three times, one appears once)

```java
int singleNumber(int[] nums) {
    int ones = 0, twos = 0;
    for (int num : nums) {
        ones = (ones ^ num) & ~twos; // first occurrence: add to 'ones'
        twos = (twos ^ num) & ~ones; // second occurrence: move to 'twos'; third: reset
    }
    return ones;
}
```

### Count Set Bits (Hamming Weight)

```java
int hammingWeight(int n) {
    int count = 0;
    while (n != 0) {
        n &= (n - 1); // removes lowest set bit
        count++;
    }
    return count;
}
```

### Power of Two Check

```java
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
    // Power of 2 has exactly one set bit: n=0100, n-1=0011, AND=0000
}
```

### Missing Number (0 to n, one missing)

```java
int missingNumber(int[] nums) {
    int xor = 0;
    for (int i = 0; i <= nums.length; i++) xor ^= i;    // XOR with 0..n
    for (int num : nums) xor ^= num;                     // XOR with array
    return xor; // missing number remains (everything else cancels)
}
```

### Hamming Distance

```java
int hammingDistance(int x, int y) {
    int xor = x ^ y; // differing bits become 1
    int count = 0;
    while (xor != 0) { xor &= (xor - 1); count++; }
    return count;
}
```

### Generate All Subsets Using Bitmask

```java
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int n = nums.length;
    for (int mask = 0; mask < (1 << n); mask++) {
        List<Integer> subset = new ArrayList<>();
        for (int i = 0; i < n; i++)
            if ((mask >> i & 1) == 1) subset.add(nums[i]);
        result.add(subset);
    }
    return result;
}
```

### Add Two Integers Without `+` Operator

```java
int getSum(int a, int b) {
    while (b != 0) {
        int carry = (a & b) << 1; // carry bits
        a = a ^ b;                // sum without carry
        b = carry;                // process carry in next iteration
    }
    return a;
}
```

---

## 9. Time Complexity

| Operation | Complexity | Reason |
|-----------|-----------|--------|
| XOR single number | O(n) | Single pass |
| Count set bits | O(k) | k = number of set bits |
| Power of two check | O(1) | One bitwise operation |
| Missing number (XOR) | O(n) | Single pass |
| Subset generation | O(n × 2ⁿ) | 2ⁿ masks, n bits each |

---

## 10. Space Complexity

All bit manipulation operations use **O(1)** extra space — no auxiliary arrays needed.

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| n = 0 for power of two | `n > 0 &&` check required |
| Negative integers | Java uses two's complement; `>>> ` for unsigned right shift |
| All zeros array | XOR gives 0 (correct if 0 is the single number) |
| n = Integer.MIN_VALUE | Beware overflow with `-n` operations |
| Large n for subset generation | n ≤ 20 is practical (2²⁰ = 1M subsets) |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Using >> (arithmetic) instead of >>> (logical) for unsigned operations
// >> preserves the sign bit. >>> fills with 0.
int reversed = reverse(n >>> 1); // for unsigned operations, use >>>

// MISTAKE 2: Forgetting parentheses around bit operations (precedence issues)
if (n & n-1 == 0)   // WRONG: parsed as n & (n-1 == 0) = n & 0 or n & 1
if ((n & (n-1)) == 0) // CORRECT

// MISTAKE 3: Using n & n-1 without checking n > 0 for power of two
// n=0: 0 & -1 = 0, so isPowerOfTwo(0) = true — WRONG! 0 is not a power of 2
return n > 0 && (n & (n-1)) == 0; // CORRECT

// MISTAKE 4: Integer overflow in subset generation
// For n=30, 1 << 30 = 2^30 which is fine, but 1 << 31 = negative (overflow)
// Limit n <= 20 for practical subset generation

// MISTAKE 5: Wrong bit check direction
(mask >> i & 1)   // WRONG precedence — parsed as mask >> (i & 1)
((mask >> i) & 1) // CORRECT — shift first, then AND with 1
```

---

## 13. Interview-Level Explanation

**Q: "Why does XOR find the single number in an array where every other element appears twice?"**

> "XOR has two key properties: `a ^ a = 0` (same values cancel) and `a ^ 0 = a` (identity with zero). If every number appears twice except one, all the pairs cancel each other out via XOR, and only the unique number remains. XOR is also commutative and associative, so the order doesn't matter — we just XOR everything together."

**Q: "How does `n & (n-1)` remove the lowest set bit?"**

> "The lowest set bit in n is at position k. n has bit k = 1 and all lower bits = 0. n-1 has bit k = 0 and all lower bits = 1 (borrows from bit k). So `n & (n-1)` has bit k = 0 and all lower bits = 0, with upper bits unchanged — the lowest set bit is gone. This is why counting set bits by repeatedly applying `n & (n-1)` runs in O(set_bit_count) instead of O(32)."

---

## 14. Real-World Use Cases

| Application | Bit Manipulation Usage |
|------------|----------------------|
| **Permissions systems** | Unix file permissions (rwx = 3 bits) |
| **Network masks** | IP address subnet masking |
| **Compression** | Huffman coding, bit packing |
| **Cryptography** | XOR-based cipher, hash functions |
| **Graphics** | Color channel manipulation (RGBA) |
| **Hardware** | CPU flag registers, interrupt masks |
| **Database** | Bloom filters, bitmap indexes |

---

## 15. Variations of This Pattern

| Variation | Key Trick | Example |
|-----------|----------|---------|
| Single number (×2 others) | XOR all | Single Number |
| Single number (×3 others) | ones/twos state machine | Single Number II |
| Missing number | XOR with 0..n | Missing Number |
| Two single numbers | XOR, split by differing bit | Single Number III |
| Count set bits | Brian Kernighan | Hamming Weight |
| Hamming distance | XOR + count bits | Hamming Distance |
| Power of two | `n & (n-1) == 0` | Power of Two |
| Subset enumeration | Bitmask 0..2^n-1 | Subsets |
| Add without + | Carry simulation | Sum of Two Integers |

---

## 16. Practice Problems

### Easy — Foundation
1. **Single Number** (LeetCode #136)
   - *Task:* Find the element appearing once (others appear twice).
   - *Hint:* XOR all elements.

2. **Counting Bits** (LeetCode #338)
   - *Task:* For each i from 0 to n, count set bits.
   - *Hint:* `dp[i] = dp[i >> 1] + (i & 1)`.

3. **Power of Two** (LeetCode #231)
   - *Task:* Check if n is a power of 2.
   - *Hint:* `n > 0 && (n & (n-1)) == 0`.

### Medium — Core Patterns
1. **Missing Number** (LeetCode #268)
   - *Task:* Find missing number in [0,n].
   - *Hint:* XOR 0..n with all array values.

2. **Number of 1 Bits** (LeetCode #191)
   - *Task:* Count set bits in an integer.
   - *Hint:* Brian Kernighan's trick: `n &= (n-1)` repeatedly.

3. **Single Number III** (LeetCode #260)
   - *Task:* Two numbers appear once, all others twice.
   - *Hint:* XOR all → get a^b. Use rightmost set bit to split into two groups.

4. **Sum of Two Integers** (LeetCode #371)
   - *Task:* Add two integers without + or -.
   - *Hint:* Carry = `(a&b)<<1`, sum = `a^b`. Repeat until carry=0.

5. **Reverse Bits** (LeetCode #190)
   - *Task:* Reverse all 32 bits of an unsigned integer.
   - *Hint:* Extract LSB, shift into result from left to right.

### Hard — Advanced
1. **Maximum XOR of Two Numbers** (LeetCode #421)
   - *Task:* Find max XOR of two numbers in array.
   - *Hint:* Trie-based approach: for each number, greedily find complement.

2. **Total Hamming Distance** (LeetCode #477)
   - *Task:* Sum of Hamming distances between all pairs.
   - *Hint:* For each bit position, count zeros and ones. Contribution = zeros × ones.

3. **Minimum XOR Sum of Two Arrays** (LeetCode #1879)
   - *Task:* Assign elements to minimize XOR sum.
   - *Hint:* Bitmask DP on assignment.

---

## 17. How to Know You Have Mastered Bit Manipulation

You have mastered this topic when you can:
- [ ] Explain `a ^ a = 0` and `a ^ 0 = a` and use them for Single Number
- [ ] Implement count set bits using Brian Kernighan's trick
- [ ] Check power of two in O(1) with correct null guard
- [ ] Generate all 2ⁿ subsets using bitmask enumeration
- [ ] Implement addition without + using carry simulation
- [ ] Know when `>>` vs `>>>` matters in Java
- [ ] Explain the `n & (n-1)` operation in plain English
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. What is `5 ^ 3`? Show the binary calculation.

2. What is `12 & (12-1)`? What bit does this remove?

3. How many times does `n & (n-1)` execute before n becomes 0 for n=100?

4. `1 << 10` = ? What does left shift by k represent?

5. For Single Number III: after XOR-ing all elements you get `xor = a ^ b`. If `xor = 6 (110)`, what is the rightmost set bit? How do you use it to separate a and b?

6. Why is `(n & -n)` the same as isolating the lowest set bit?

7. Power of 4 check: `n = 16 = 10000₂`. Is it a power of 4? What additional check beyond power-of-two test distinguishes powers of 4?

8. What is `~0` in Java? What is `~0 + 1`?

> **Answers:**
> 1. 5=101, 3=011. 101 XOR 011 = 110 = 6.
> 2. 12=1100, 11=1011. 1100 & 1011 = 1000 = 8. Removes the lowest set bit (the 4s bit, position 2).
> 3. 100 = 1100100₂ has 3 set bits. So 3 times.
> 4. 1024. Left shift by k multiplies by 2^k.
> 5. Rightmost set bit of 6(110) is bit position 1 (value 2): `bit = xor & (-xor) = 2`. Group 1: elements where (bit & num) != 0. Group 2: elements where (bit & num) == 0. XOR within each group gives a and b separately.
> 6. In two's complement, `-n = ~n + 1`. The lowest set bit of n is preserved; all bits below it flip and then carry propagates, all bits above it also flip. The AND isolates only the original lowest set bit.
> 7. 16=10000₂: power of 2 check passes. Extra check for power of 4: the set bit must be at an even position (0, 2, 4, ...). Check: `(n & 0xAAAAAAAA) == 0` (eliminates odd positions) OR `(n-1) % 3 == 0`.
> 8. `~0 = -1` (all bits 1 in two's complement = -1). `~0 + 1 = 0` (overflow wraps around).

---

**Next →** `../21_Strings/01_String_Algorithms.md`
