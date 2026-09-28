# core \-bit \-operations

## 1\. Number System Conversions

* **Decimal to Binary:** Repeatedly divide by 2 and collect remainders from bottom to top.  
* **Binary to Decimal:** Traverse from right to left, multiplying each bit by 2index.

**Computer Storage:** Integers are stored as fixed-width binary sequences (commonly 32 bits), where the Most Significant Bit (MSB) acts as the sign bit (0 for positive, 1 for negative). Positive numbers are represented in standard binary format, whereas negative numbers are stored using **Two's Complement**. 

To represent a negative integer \-x in Two's Complement:   
(1) Find the binary representation of positive x,   
(2) Invert all bits (One's Complement), and   
(3) Add 1 to the least significant bit. 

This notation simplifies hardware implementation because addition and subtraction can be performed using the same arithmetic logic circuit without needing extra logic for negative sign handling.

## 2\. Complement Operations

* **One's Complement:** Flip all bits of a binary number.  
* **Two's Complement:** Calculate One's Complement and add 1\.

## 3\. Bitwise Operators

* **AND (&):** Result is 1 only if both bits are 1\.  
* **OR (|):** The result is 1 if at least one bit is 1\.  
* **XOR (^):** Result is 1 if the number of 1s is odd.   
* **NOT (\~):** Flips all bits . ( if sign bit (31st-bit- last bit from left to right)  is \-ve \- do the 2’s compliment or else just stop )  
* **Right Shift (\>\>):** Effectively divides the number by 2k. Formula: x \>\> k \= x / 2k. ( shift the left most bits by its number of shift given )  
  Eg: 13\>\>2  
  Binary rep of 13 \= 1 1 0 1   
  Shift by 2 bits \-\> 1 1 **0 1 \-\>** gets converted to \-\> **0 0 1 1 (see 0 1 are removed from last) \- 13/2^2 \-\> 0011 \-\> 3**  
* **Left Shift (\<\<):** Effectively multiplies the number by 2k. Formula: x \<\< k \= x \* 2k.

  Shift the left most bits   
*   
* Eg \-\> 13 \<\< 1

  Binary rep of 13 \= 1 1 0 1  
  Shift end by 1 bit  \-\> 1 1 0 1 0 \-\> 26( bin rep) 13 \* 2^1

## 4\. Code Snippets (Concepts)

* **Decimal to Binary Conversion:** While n \> 0, perform n % 2 to find the bit, then n \= n / 2\.

**Binary to Decimal Conversion:** While traversing, if bit is 1, add powerOfTwo to the result, then update powerOfTwo \*= 2\.

# basic-bit-problems

# | will be used to set 

# ^ will be used to toggle 

# & will be used to check

# &\~ will be used to clear

## 1\. Swap Two Numbers

```
a = a ^ b;
b = a ^ b;
a = a ^ b;
```

**Initial State:** a \= 5 (101), b \= 6 (110)  
**Step 1:** a \= 5 ^ 6 \= 3 (011)  
**Step 2:** b \= 3 ^ 6 \= 5 (101)  
**Step 3:** a \= 3 ^ 5 \= 6 (110)  
**Result:** a \= 6, b \= 5

## 2\. Check if i-th Bit is Set

```
(n & (1 << i)) != 0
```

**Input:** n \= 13 (1101), i \= 2  
**Step 1:** 1 \<\< 2 \= 4 (0100)  
**Step 2:** 13 & 4 \= 1101 & 0100 \= 0100 (4)  
**Result:** 4 \!= 0 (True, bit is set)

## 

## 3\. Set the i-th Bit

```
n | (1 << i)
```

**Input:** n \= 9 (1001), i \= 2  
**Step 1:** 1 \<\< 2 \= 4 (0100)  
**Step 2:** 9 | 4 \= 1001 | 0100 \= 1101 (13)  
**Result:** 13

## 

## 4\. Clear the i-th Bit

```
n & ~(1 << i)
```

**Input:** n \= 13 (1101), i \= 2  
**Step 1:** 1 \<\< 2 \= 4 (0100)  
**Step 2:** \~4 \= \~0100 \= ...1011  
**Step 3:** 13 & \~4 \= 1101 & 1011 \= 1001 (9)  
**Result:** 9

## 5\. Toggle the i-th Bit

```
n ^ (1 << i)
```

**Input:** n \= 13 (1101), i \= 1  
**Step 1:** 1 \<\< 1 \= 2 (0010)  
**Step 2:** 13 ^ 2 \= 1101 ^ 0010 \= 1111 (15)  
**Result:** 15

## 6\. Remove the Last Set Bit

```
n & (n - 1)
```

**Input:** n \= 12 (1100)  
**Step 1:** n \- 1 \= 11 (1011)  
**Step 2:** 12 & 11 \= 1100 & 1011 \= 1000 (8)  
**Result:** 8

## 7\. Check if Power of Two

```java
(n > 0) && ((n & (n - 1)) == 0)
```

**Input:** n \= 16 (10000)  
**Step 1:** n \- 1 \= 15 (01111)  
**Step 2:** 16 & 15 \= 10000 & 01111 \= 00000 (0)  
**Result:** 0 \== 0 (True)

## 8\. Count Set Bits

```
while (n > 0) {
  n &= (n - 1);
  count++;
}
```

**Input:** n \= 13 (1101)  
**Iteration 1:** 13 & 12 \= 12 (1100), count \= 1  
**Iteration 2:** 12 & 11 \= 8 (1000), count \= 2  
**Iteration 3:** 8 & 7 \= 0 (0000), count \= 3  
**Result:** 3

# Minimum Bit Flips to Convert Number

# Minimum Bit Flips to Convert Number

To find the minimum number of bit flips required to convert integer **start** into integer **goal**, we need to count how many bit positions differ between the two numbers.

# 1\. Core Approach

## Step 1: Identify Differing Bits (XOR Operation)

1. **Formula:** xor\_result \= start ^ goal  
2. **Logic:** XOR returns 1 if bits are different and 0 if they are identical. Thus, each set bit (1) in xor\_result represents a required bit flip.

## Step 2: Count Set Bits

After calculating xor\_result, count the number of set bits (1s) using one of the following methods:

Method A: Brian Kernighan’s Algorithm (n & (n \- 1))

* **Code Logic:** n \= n & (n \- 1\)  
* **Why it works:** Each operation n & (n \- 1\) clears the rightmost set bit in n. The total number of iterations before n becomes 0 equals the number of set bits.

#### Dry Run Example (n \= 12 / Binary: 1100):

* **Iteration 1:** n \= 12 & 11 \= 8 (Binary: 1100 & 1011 \= 1000), count \= 1  
* **Iteration 2:** n \= 8 & 7 \= 0 (Binary: 1000 & 0111 \= 0000), count \= 2

**Result:** Total set bits \= 2

### Method B: Right Shift Approach (n & 1\)

* **Code Logic:** while (n \> 0\) { if (n & 1\) count++; n \>\>= 1; }

**Why it works:** n & 1 checks if the least significant bit is 1. Right-shifting n \>\>= 1 processes the next bit in each iteration.

#### Dry Run Example (n \= 13 / Binary: 1101):

* **Iteration 1:** 13 & 1 \= 1 (LSB is 1, count \= 1), shift right: n \= 6 (Binary: 0110\)  
* **Iteration 2:** 6 & 1 \= 0 (LSB is 0, count \= 1), shift right: n \= 3 (Binary: 0011\)  
* **Iteration 3:** 3 & 1 \= 1 (LSB is 1, count \= 2), shift right: n \= 1 (Binary: 0001\)  
* **Iteration 4:** 1 & 1 \= 1 (LSB is 1, count \= 3), shift right: n \= 0  
* **Result:** Total set bits \= 3

## Complete Bit Conversion Dry Run

Example: Convert start \= 29 (11101) to goal \= 15 (01111):

* **Step 1 (XOR):** 29 ^ 15 \= 18 (Binary: 11101 ^ 01111 \= 10010)  
* **Step 2 (Count Set Bits):** 10010 has 2 set bits (positions 1 and 4).  
* **Conclusion:** Minimum 2 bit flips are required.

\---

2\. Quick Revision Tips

### Tip 1: Checking Odd or Even

* **Formula:** (n & 1\) \== 0 (Even) | (n & 1\) \== 1 (Odd)

**Why:** The lowest-order bit (LSB) determines parity. If LSB is 0, the number is even; if 1, it is odd. Faster than modulo n % 2.

### Tip 2: Integer Division by 2 (Finding Middle)

* **Formula:** n \>\> 1

**Why:** Right-shifting by 1 bit drops the LSB, performing integer division by 2 (equivalent to floor(n / 2\)).

# Single Number in an Array

# Single Number in an Array

This guide explains how to find the single element in an array where every other element appears exactly twice.

## 1\. Brute Force Approach (Hash Map)

Concept: Use a Hash Map to store the frequency of each element in the array.  
Traverse the array and increment the occurrence count for each number in the map.  
Iterate through the hash map to find the key with a value equal to 1\.  
Time Complexity: O(N log M), where N is the total number of elements and M is the number of unique elements (\~N/2).  
Space Complexity: O(M) to maintain the frequency counts in memory.

## 2\. Optimal Bit Manipulation Approach (XOR)

Concept: Leverage the core properties of the Bitwise XOR operator (⊕):  
Identity: A ⊕ 0 \= A (XORing with zero leaves the number unchanged)  
Self-Cancellation: A ⊕ A \= 0 (Two identical numbers cancel each other out)  
Commutative & Associative: Order of operations does not alter the result.  
Logic: XORing all elements together cancels out all duplicate pairs, leaving only the single unique number.  
Time Complexity: O(N) — Single linear pass across the array.  
Space Complexity: O(1) — Constant memory usage.

## 3\. Step-by-Step Dry Run Examples

### Example A: XOR Single Number Dry Run

Input Array: \[4, 1, 2, 1, 2\]

| Step | Element | Decimal XOR | Binary XOR | Result |
| :---- | :---- | :---- | :---- | :---- |
| Start | Initial | acc \= 0 | 0000 | 0 |
| 1 | 4 | 0 ⊕ 4 | 0000 ⊕ 0100 | 4 (0100) |
| 2 | 1 | 4 ⊕ 1 | 0100 ⊕ 0001 | 5 (0101) |
| 3 | 2 | 5 ⊕ 2 | 0101 ⊕ 0010 | 7 (0111) |
| 4 | 1 | 7 ⊕ 1 | 0111 ⊕ 0001 | 6 (0110) |
| 5 | 2 | 6 ⊕ 2 | 0110 ⊕ 0010 | 4 (0100) |

Final Result: 4 (The unique element left after pairs of 1s and 2s cancelled out).

### Example B: Negative Number Representation (2's Complement)

In computers, negative integers are stored using Two's Complement representation. Here is the step-by-step conversion of \-5 in an 8-bit system:

| Step | Operation | Binary Value | Note |
| :---- | :---- | :---- | :---- |
| 1 | Write positive magnitude (+5) | 0000 0101 | Standard binary |
| 2 | Invert bits (1's Complement) | 1111 1010 | Flip 0s and 1s |
| 3 | Add 1 (2's Complement) | 1111 1011 | Final \-5 representation |

Summary Rule: Two's Complement (-X) \= (\~X) \+ 1

# subsets

The **Power Set** problem involves generating all possible subsets of a given array. Using a bit manipulation approach provides an elegant, non-recursive alternative to traditional backtracking algorithms.

The Core Approach  
For an array of size *n*, the total number of subsets is 2n. We can represent each subset using a bitmask of length *n*, where the *i*th bit signifies whether the element at index *i* is included in the subset:  
**0:** Exclude the element from the subset.  
**1:** Include the element in the subset.

We iterate from 0 to 2n \- 1\. For each number in this range, its binary representation dictates which elements from the array are included in that specific subset.

Dry Run Example  
For an array nums \= \[1, 2, 3\] (where *n* \= 3, total subsets \= 23 \= 8):

* **Decimal 0 (000):** Subset: \[\]  
* **Decimal 1 (001):** Subset: \[1\]  
* **Decimal 2 (010):** Subset: \[2\]  
* **Decimal 3 (011):** Subset: \[1, 2\]  
* **Decimal 4 (100):** Subset: \[3\]  
* **Decimal 5 (101):** Subset: \[1, 3\]  
* **Decimal 6 (110):** Subset: \[2, 3\]  
* **Decimal 7 (111):** Subset: \[1, 2, 3\]

Algorithm Steps  
To implement this logic, follow these steps:

1. Calculate the total number of subsets: total\_subsets \= 1 \<\< n.  
2. Loop through each integer num from 0 to total\_subsets \- 1.  
3. Initialize a temporary list to store the current subset.  
4. Use an inner loop to check the *i*th bit using (num & (1 \<\< i)).  
5. If the bit is set (non-zero), append nums\[i\] to the current subset.  
6. Append the generated subset to the final result list.

## Implementation

```py
import java.util.ArrayList;
import java.util.List;

public class PowerSet {
    public static List<List<Integer>> subsets(int[] nums) {
        int n = nums.length;
        int totalSubsets = 1 << n;
        List<List<Integer>> res = new ArrayList<>();
        
        for (int num = 0; num < totalSubsets; num++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((num & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            res.add(subset);
        }
        
        return res;
    }
}

```

## Complexity Analysis

* **Time Complexity:** O(n · 2n), as there are 2n total subsets, and we evaluate *n* bits for each subset.

**Space Complexity:** O(n · 2n) to store all 2n subsets, each having an average length of *n*/2.

# numbers appear three times

numbers appear three times. Here are the four approaches discussed:

## Synopsis: Modulo-2 (XOR) vs. Modulo-3 (Optimal Bucket) State Machines

The core distinction between finding a unique number when others appear twice versus three times lies in the number of distinct bit states required for counting:

* **Modulo-2 (Numbers Appearing Twice):** Counting occurrences modulo 2 only requires tracking 2 states per bit position: 0 (appears 0 or 2 times) and 1 (appears 1 time). A single 32-bit integer variable is sufficient because a single bit holds binary states (0 or 1). The bitwise XOR operation naturally performs addition modulo 2, allowing duplicate elements to cancel each other out directly (x ^ x \= 0).  
* **Modulo-3 (Numbers Appearing Three Times):** Counting occurrences modulo 3 requires tracking 3 distinct states per bit position: 0 (appears 3k times), 1 (appears 3k \+ 1 times), and 2 (appears 3k \+ 2 times). Since a single binary bit can only represent 2 states (0 and 1), it cannot represent 3 states. Therefore, a state machine with two integer variables (*ones* and *twos*) is necessary to form a 2-bit state per position (00, 01, and 10), which automatically resets to 00 upon reaching state 3\.

## 1\. HashMap Approach (0:30–4:57)

**Approach:** Store bit/number frequencies in a hash map and identify the element with a frequency of 1\.  
**Complexity:** Time: O(N log M), Space: O(M), where M is the number of unique elements.  
**Java Implementation:**

```java
Map<Integer, Integer> map = new HashMap<>();
for (int n : nums) {
    map.put(n, map.getOrDefault(n, 0) + 1);
}
for (int key : map.keySet()) {
    if (map.get(key) == 1) return key;
}
return -1;
```

## 2\. Bit Manipulation by Counting Bits (5:03–11:17)

**Approach:** Count the set bits at each of the 32 position across all numbers. If a bit position count modulo 3 is non-zero, that bit belongs to the single element.  
**Complexity:** Time: O(32N), Space: O(1).  
**Java Implementation:**

```java
int ans = 0;
for (int i = 0; i < 32; i++) {
    int count = 0;
    for (int n : nums) {
        if ((n & (1 << i)) != 0) count++;
    }
    if (count % 3 != 0) {
        ans |= (1 << i);
    }
}
return ans;
```

## 3\. Sorting Approach (11:27–16:15)

**Approach:** Sort the array so identical numbers group into triplets. Iterate with a step size of 3 and inspect for any mismatch.  
**Complexity:** Time: O(canN log N), Space: O(1) auxiliary.  
**Java Implementation:**

```java
Arrays.sort(nums);
for (int i = 1; i < nums.length; i += 3) {
    if (nums[i] != nums[i - 1]) {
        return nums[i - 1];
    }
}
return nums[nums.length - 1];
```

## 4\. Optimal Bucket Approach (17:11–30:53)

**Approach:** Utilizes bitwise state machines through two integer variables, *ones* and *twos*, to maintain bit counts modulo 3\.  
**Complexity:** Time: O(N), Space: O(1).  
**Java Implementation:**

```java
int ones = 0, twos = 0;
for (int n : nums) {
    ones = (ones ^ n) & (~twos);
    twos = (twos ^ n) & (~ones);
}
return ones;
```

## Dry Run & Conceptual Explanations

### 1\. Detailed Dry Run (Optimal Bucket Method)

Let's trace *nums \= \[2, 2, 2, 3\]* step-by-step:  
**Initial State:** ones \= 0 (0000\_2), twos \= 0 (0000\_2)  
**Step 1 (n \= 2):** ones \= (0 ^ 2\) & (\~0) \= 2; twos \= (0 ^ 2\) & (\~2) \= 0 → *(ones \= 2, twos \= 0\)*  
**Step 2 (n \= 2):** ones \= (2 ^ 2\) & (\~0) \= 0; twos \= (0 ^ 2\) & (\~0) \= 2 → *(ones \= 0, twos \= 2\)*  
**Step 3 (n \= 2):** ones \= (0 ^ 2\) & (\~2) \= 0; twos \= (2 ^ 2\) & (\~0) \= 0 → *(ones \= 0, twos \= 0\)* (3rd occurrence resets states)  
**Step 4 (n \= 3):** ones \= (0 ^ 3\) & (\~0) \= 3; twos \= (0 ^ 3\) & (\~3) \= 0 → *(ones \= 3, twos \= 0\)*  
**Final Result:** returns **ones \= 3**.  
Now, let's trace *nums \= \[2, 1, 2, 2\]* step-by-step:  
**Initial State:** ones \= 0 (0000\_2), twos \= 0 (0000\_2)  
**Step 1 (n \= 2):** ones \= (0 ^ 2\) & (\~0) \= 2; twos \= (0 ^ 2\) & (\~2) \= 0 → *(ones \= 2, twos \= 0\)*  
**Step 2 (n \= 1):** ones \= (2 ^ 1\) & (\~0) \= 3; twos \= (0 ^ 1\) & (\~3) \= 0 → *(ones \= 3, twos \= 0\)*  
**Step 3 (n \= 2):** ones \= (3 ^ 2\) & (\~0) \= 1; twos \= (0 ^ 2\) & (\~1) \= 2 → *(ones \= 1, twos \= 2\)* (2nd occurrence of 2 moves its bit to *twos*)  
**Step 4 (n \= 2):** ones \= (1 ^ 2\) & (\~2) \= 1; twos \= (2 ^ 2\) & (\~1) \= 0 → *(ones \= 1, twos \= 0\)* (3rd occurrence of 2 resets its bits)  
**Final Result:** returns **ones \= 1**.

### 2\. Understanding Two's Complement for Negative Numbers

In computer systems, signed integers are stored using **Two's Complement representation**:  
**Sign Bit:** The Most Significant Bit (MSB, bit 31 in 32-bit integers) acts as the sign indicator (0 for positive, 1 for negative).  
**Conversion Process:** To compute \-X, invert all bits of X (One's Complement) and add 1\.  
**Seamless Bit Operations:** Because bit-counting and bucket operations inspect exact 32-bit representations, negative numbers are processed naturally without needing conditional branches for signs.

## Real-World Applications of Bit Manipulation

Bitwise operations operate at the hardware level, making them crucial for low-level systems, performance-critical software, and domain-specific engineering:  
**Graphics & Game Development:** Used for fast pixel color manipulation (RGB/RGBA packing and unpacking), collision bitmasks, and state flags.  
**Networking & Security:** IP routing with subnet masks, checksum verification, cryptographic algorithms (AES, SHA-256 heavy bitwise XOR/rotations), and protocol header parsing.  
**Database & Search Engines:** Roaring Bitmaps for high-performance set operations, inverted index compression, and bloom filter evaluations.  
**Embedded Systems & Hardware Controls:** Direct register reads/writes, memory-mapped I/O toggling, and micro-controller pin management.

# Xor on range of Numbers

Synopsis  
This guide explains how to efficiently calculate the **XOR of all numbers in a given range \[L, R\]**. While a naive approach iterates through the range in linear time, a **constant time O(1) approach** can be achieved using bitwise patterns discovered in XOR operations from 1 to N.

# Important Points

* **Naive vs. Optimized:** A loop-based approach takes O(N) or O(R \- L) time, which is inefficient for large inputs. An O(1) solution is required for optimal performance.  
* **Pattern Discovery:** XORing numbers from 1 to N follows a repeating pattern every 4 numbers:  
  * **If N % 4 \== 1, XOR is 1\.**  
  * **If N % 4 \== 2, XOR is N \+ 1\.**  
  * **If N % 4 \== 3, XOR is 0\.**  
  * **If N % 4 \== 0, XOR is N.**

**Range XOR Logic:** To find the XOR from L to R, compute XOR(1, R) ^ XOR(1, L \- 1). This cancels out the XOR values from 1 to L \- 1, leaving the result for the range \[L, R\].

Java Implementation  
To solve this, first define the function to calculate XOR from \$1\$ to \$N\$, then use it to find the XOR in range \$\[L, R\]\$.

```java
public class XORRange {
    // Function to find XOR from 1 to n in O(1)
    public static int findXor(int n) {
        int rem = n % 4;
        if (rem == 0) return n;
        if (rem == 1) return 1;
        if (rem == 2) return n + 1;
        return 0; // rem == 3
    }

    // Function to find XOR in range [L, R]
    public static int findXorInRange(int l, int r) {
        return findXor(l - 1) ^ findXor(r);
    }

    public static void main(String[] args) {
        int L = 4, R = 7;
        System.out.println("XOR of range: " + findXorInRange(L, R));
    }
}
```

# Importance of Bitwise Operations

Bitwise operations manipulate data directly at the binary level, offering unparalleled speed and efficiency. Understanding bit manipulation is essential for critical real-world applications:

* **Low-Level Systems Programming:** Direct interaction with hardware, memory management, device drivers, and microcontrollers relies heavily on bitwise operations.  
* **Cryptography & Security:** Encryption algorithms (such as AES) and hashing functions heavily utilize XOR and shift operations for data obfuscation and bit swapping.  
* **Performance Optimization:** Bitwise tricks allow operations to run in constant time O(1) without relying on heavy arithmetic or loop-based iterations.

**Flag Management & Masking:** Instead of storing multiple boolean variables, a single integer bitmask can store multiple binary states (e.g., read/write/execute permissions).

# Two Unique Numbers

# Single Number III (Two Unique Numbers)

This problem asks us to find **two unique numbers** that appear only once in an array where every other number appears exactly twice. We can solve this optimally without extra memory using **Bit Manipulation**.

## 1\. Intuition & Analogy (Explaining to a Kid)

### The "Sock Party" Analogy

Imagine a huge pile of socks at a party. Almost every sock has an identical twin pair. If you put identical socks together, they pair up and leave the party. However, there are **two lonely socks** (let's call them Sock A and Sock B) that don't have matching twins\!

### The Magic Cancelling Property of XOR

In computer science, the **XOR operation (&\#8853;)** works like a magic pairing machine:

* **Self-Cancellation:** Any number XORed with itself becomes zero (*x &\#8853; x \= 0*). Matching pairs completely destroy each other\!  
* **Identity Property:** Any number XORed with zero stays the same (*x &\#8853; 0 \= x*).

### Why XORing Everything Leaves A &\#8853; B

If we XOR all the numbers in the array together, every paired twin cancels itself out to 0\. What remains at the end is the combined XOR of the two unique numbers: **A &\#8853; B**.

## 2\. The Bucket Method (Separating A and B)

### Why A &\#8853; B Isn't Enough

The value *A &\#8853; B* tells us that A and B are tangled together, but it doesn't give us A or B individually. We need a way to untangle them\!

### Finding the Differentiating Bit

Since A and B are different numbers, their binary representations must differ in at least one bit position. Wherever A and B differ, that bit in *A &\#8853; B* will be **1**. We isolate the **rightmost set bit** using the formula: *xor & \-xor*.

### Sorting into Buckets

We use this specific bit position as a rule to divide all array numbers into two buckets:

* **Bucket 1:** Numbers that have a 1 at this bit position.  
* **Bucket 2:** Numbers that have a 0 at this bit position.

### Why This Guarantees Success

* Because A and B have different values at this bit, **A goes to one bucket and B goes to the other**.  
* For any paired numbers, both twin copies share the exact same bits. Therefore, **both twins will always land in the same bucket** and cancel each other out during XORing\!

At the end of the process, Bucket 1 leaves only **A**, and Bucket 2 leaves only **B**.

## 3\. Complete Step-by-Step Dry Run

Example Array: **\[2, 4, 2, 14, 3, 7, 7, 3\]**

### Step 1: Calculate Overall XOR Sum

XORing all elements together:  
2 &\#8853; 4 &\#8853; 2 &\#8853; 14 &\#8853; 3 &\#8853; 7 &\#8853; 7 &\#8853; 3 \= 14 &\#8853; 4 \= **10** (binary **1010**)

### Step 2: Extract Differentiating Bit

Extract the rightmost set bit using *10 & \-10*:  
10 (&\#8213;10) \= 1010 & 0110 \= **2** (binary **0010**)

### Step 3: Bucket Distribution & Cancellation

* **Bucket 1 (2nd bit is 1):** Contains \[2, 2, 14, 3, 7, 7, 3\].  
  * XOR Calculation: (2 &\#8853; 2\) &\#8853; (3 &\#8853; 3\) &\#8853; (7 &\#8853; 7\) &\#8853; 14 \= 0 &\#8853; 0 &\#8853; 0 &\#8853; 14 \= **14**

**Bucket 2 (2nd bit is 0):** Contains \[4\].  
\*Note: Using \`long\` for \`xor\` prevents overflow issues if the result is \`Integer.MIN\_VALUE\` (23:05).\* 

* XOR Calculation: 4 \= **4**

## 4\. Java Solution

```java
public int[] singleNumber(int[] nums) {
    long xor = 0;
    for (int num : nums) {
        xor ^= num;
    }
    
    // Find rightmost set bit
    long rightmost = (xor & -xor);
    
    int b1 = 0, b2 = 0;
    for (int num : nums) {
        if ((num & rightmost) != 0) {
            b1 ^= num;
        } else {
            b2 ^= num;
        }
    }
    return new int[]{b1, b2};
}
```

# Divide Two Integers without \*, /, or %

# Bit Manipulation Revision Notes

## 1\. Core Principles & Negative Number Storage (Two's Complement)

### How Negative Numbers are Stored

Negative integers are stored in binary using **Two's Complement** representation:  
1\. Take the binary representation of the positive integer \+x.  
2\. Invert all the bits (One's Complement: flip 0 → 1 and 1 → 0).  
3\. Add 1 to the least significant bit (LSB).

**Example: Storing \-5 in an 8-bit system**  
\+5 in binary: 0000 0101  
Invert bits (One's Complement): 1111 1010  
Add 1: 1111 1011 → Representation of \-5.

### Key Bitwise Operations Cheat Sheet

**AND (&)**: x & 1 checks if x is odd (1) or even (0).  
**OR (|)**: Used to set specific bits.  
**XOR (^)**: Properties: x ⊕ x \= 0 (Self-cancellation) and x ⊕ 0 \= x (Identity).  
**NOT (\~)**: Flips all bits including the sign bit (\~x \= \-x \- 1).  
**Left Shift (\<\<)**: x \<\< k multiplies x by 2^k.  
**Right Shift (\>\>)**: x \>\> k divides x by 2^k.  
**Extract Rightmost Set Bit**: x & \-x isolates the lowest bit set to 1\.

## 2\. Divide Two Integers without \*, /, or %

### Logic: Power of Two Exponential Subtraction

Instead of subtracting the divisor one by one (slow O(N)), we subtract in powers of two (d × 2^i) using bit-shifting.

### Step-by-Step Dry Run (22 / 3\)

**Initial State**: n \= 22, d \= 3, res \= 0\.  
**Iteration 1**:  
Check maximum power: 3 × 2^0 \= 3, 3 × 2^1 \= 6, 3 × 2^2 \= 12 ≤ 22\.  
Add 2^2 \= 4 to res.  
Subtract 12 from 22: n \= 10\.  
**Iteration 2**:  
Check maximum power: 3 × 2^0 \= 3, 3 × 2^1 \= 6 ≤ 10\.  
Add 2^1 \= 2 to res (res \= 4 \+ 2 \= 6).  
Subtract 6 from 10: n \= 4\.  
**Iteration 3**:  
Check maximum power: 3 × 2^0 \= 3 ≤ 4\.  
Add 2^0 \= 1 to res (res \= 6 \+ 1 \= 7).  
Subtract 3 from 4: n \= 1\.  
**Stop**: n \= 1 \< d, loop terminates. **Final Quotient \= 7**.

### Java Implementation

```java
class Solution {
    public int divide(int dividend, int divisor) {
        if (dividend == divisor) return 1;
        if (dividend == Integer.MIN_VALUE && divisor == -1) return Integer.MAX_VALUE;

        boolean sign = (dividend < 0) == (divisor < 0);
        long n = Math.abs((long) dividend);
        long d = Math.abs((long) divisor);
        long res = 0;

        while (n >= d) {
            int count = 0;
            while (n >= (d << (count + 1))) {
                count++;
            }
            res += (1L << count);
            n -= (d << count);
        }

        return sign ? (int) res : (int) -res;
    }
}
```

## 3\. Single Number Problems & State Machine Approaches

### Comparison Synopsis: Modulo-2 vs Modulo-3 vs Two Uniques

| Scenario | Modulo-2 (Single Number I) | Modulo-3 (Single Number II) | Two Uniques (Single Number III) |
| :---- | :---- | :---- | :---- |
| **Frequency** | Every number appears 2 times, one appears 1 time | Every number appears 3 times, one appears 1 time | Every number appears 2 times, two appear 1 time |
| **State Tracking** | Single variable (ans ^= n) | Two bitmasks (ones, twos) | Single XOR sum → Rightmost set bit bucket split |
| **Reset Trigger** | 2nd occurrence cancels out (x ⊕ x \= 0\) | 3rd occurrence clears both ones & twos | Pairs cancel out inside individual buckets |

### Single Number II: Modulo-3 State Machine (ones & twos)

#### Concept

The variables ones and twos do **not** store whole decimal values from the array. They act as **parallel bitmasks across all 32 bits**, tracking whether a bit position has been seen 1 time (ones), 2 times (twos), or 3 times (reset to 0).

#### Java Implementation

```java
class Solution {
    public int singleNumber(int[] nums) {
        int ones = 0, twos = 0;
        for (int num : nums) {
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }
        return ones;
    }
}
```

### Single Number III: Finding Two Unique Numbers (The Sock Party & Two Buckets Concept)

#### 1\. The Sock Party Analogy (Explained simply)

Imagine a party where every guest brings a twin wearing matching socks, except for **two special unique guests** (A and B) who came alone.  
**The Magic XOR Spell**: When two identical twins touch, POOF\! they vanish into zero (x ⊕ x \= 0).  
When you XOR every guest together, all twins vanish, leaving only A ⊕ B.

#### 2\. Why A ⊕ B Isn't Enough (The Tangled Clue)

A ⊕ B gives us a single combined number where A and B are tangled together.  
In binary, any 1 bit in A ⊕ B means: **"A and B have DIFFERENT bits at this position."**  
We pick the **rightmost set bit** (xor & \-xor) as our sorting rule to separate them.

#### 3\. The Two Buckets Strategy

We create two buckets and check each number's bit at our chosen position:  
**Bucket 1**: Numbers with 1 at this bit position.  
**Bucket 2**: Numbers with 0 at this bit position.

**Why this works**:  
1\. Since A and B disagree at this bit position, A goes to Bucket 1 and B goes to Bucket 2\.  
2\. Since twins have identical bits, both copies of any pair will land in the **exact same bucket**, where they XOR with each other and vanish (x ⊕ x \= 0).  
3\. This leaves only A in Bucket 1 and B in Bucket 2\!

#### Complete Dry Run

**Input**: nums \= \[2, 4, 2, 14, 3, 7, 7, 3\]  
**Total XOR**: 2 ⊕ 4 ⊕ 2 ⊕ 14 ⊕ 3 ⊕ 7 ⊕ 7 ⊕ 3 \= 14 ⊕ 4 \= 10 (Binary 1010\_2)  
**Rightmost Set Bit**: Rightmost \= 10 & \-10 \= 2 (Binary 0010\_2, bit position 1\)  
**Bucket Assignment**:  
2 (0010\_2): Bit 1 set → **Bucket 1**  
4 (0100\_2): Bit 1 not set → **Bucket 2**  
2 (0010\_2): Bit 1 set → **Bucket 1**  
14 (1110\_2): Bit 1 set → **Bucket 1**  
3 (0011\_2): Bit 1 set → **Bucket 1**  
7 (0111\_2): Bit 1 set → **Bucket 1**  
7 (0111\_2): Bit 1 set → **Bucket 1**  
3 (0011\_2): Bit 1 set → **Bucket 1**  
**Bucket XOR Results**:  
**Bucket 1**: 2 ^ 2 ^ 14 ^ 3 ^ 7 ^ 7 ^ 3 \= (2 ⊕ 2\) ⊕ (3 ⊕ 3\) ⊕ (7 ⊕ 7\) ⊕ 14 \= **14**  
**Bucket 2**: \[4\] \= **4**  
**Output**: \[14, 4\]

#### Java Implementation

```java
public class Solution {
    public int[] singleNumber(int[] nums) {
        long xor = 0;
        // Step 1: XOR all elements to get A ^ B
        for (int num : nums) {
            xor ^= num;
        }

        // Step 2: Extract rightmost set bit
        long rightmost = (xor & -xor);

        // Step 3: Divide into two buckets and XOR separately
        int b1 = 0, b2 = 0;
        for (int num : nums) {
            if ((num & rightmost) != 0) {
                b1 ^= num; // Bucket 1
            } else {
                b2 ^= num; // Bucket 2
            }
        }

        return new int[]{b1, b2};
    }
}
```

## 4\. Real-World Importance & Applications of Bit Operations

Bit manipulation is widely used in systems programming, data structures, and competitive programming because bitwise operations execute in a single CPU clock cycle (O(1) time and space):  
**Space Optimization (Bitsets & Flags)**: Managing multiple boolean permissions or states in a single integer rather than boolean\[\] arrays.  
**Fast Arithmetic**: Multiplying/dividing by powers of 2 via bit-shifts (\<\<, \>\>).  
**Cryptography & Hashing**: Constant-time bitwise permutations, XOR ciphers, and hash functions.  
**Graph & Set Algorithms**: Representing subsets as bitmasks (e.g., Dynamic Programming with Bitmasking, Traveling Salesperson Problem).  
**Low-level Hardware Interactions**: Embedded systems, driver development, and memory-mapped register manipulation.