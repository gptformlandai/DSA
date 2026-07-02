# Section 24 — Mathematical Algorithms

---

## 1. What Problem Does This Solve?

Mathematical algorithms solve number-theory problems that appear frequently in competitive programming and interviews:
- Finding common factors/multiples efficiently (GCD/LCM)
- Listing all prime numbers up to N (Sieve of Eratosthenes)
- Computing large powers modulo a prime (Fast Exponentiation)
- Counting combinations/permutations (Combinatorics with modular arithmetic)

Without these algorithms, naive approaches time out on large inputs (e.g., checking all numbers up to N for primality).

---

## 2. Beginner-Friendly Intuition

**GCD:** To find the largest number that divides both a and b, you don't need to try all numbers. Instead, use the insight: `gcd(a, b) = gcd(b, a % b)`. Each step shrinks the problem exponentially.

**Sieve of Eratosthenes:** Instead of testing each number for primality, start from 2 and "cross out" all its multiples. Then move to the next uncrossed number (it's prime) and cross out its multiples. After O(n log log n) work, every remaining uncrossed number is prime.

**Fast Exponentiation:** To compute `a^n`, instead of multiplying n times, halve the exponent each step: `a^n = (a^(n/2))^2`. This reduces O(n) multiplications to O(log n).

---

## 3. Real-World Analogy

**GCD — Tile fitting:** To tile a 12×18 room with the largest possible square tiles without cutting, the tile size is gcd(12, 18) = 6. Same principle — largest common divisor.

**Sieve — Party invitations:** You have 100 friends. You want to invite only those NOT related to person #2, #3, #5, #7... Sieve = cross off multiples of each prime from your list.

**Fast Exponentiation — Folding paper:** Folding paper doubles its thickness. After log2(n) folds you achieve 2^n thickness. Same principle in reverse — squaring halves the number of steps.

---

## 4. Core Concept

### GCD (Euclidean Algorithm)
```
gcd(a, b):
    if b == 0: return a
    return gcd(b, a % b)

Proof: gcd(a, b) = gcd(b, a mod b) because any divisor of a and b
       also divides (a mod b), and vice versa.
```

### LCM
```
lcm(a, b) = (a / gcd(a, b)) * b    ← divide first to prevent overflow
```

### Sieve of Eratosthenes
```
isPrime[0..n] = all true (except 0 and 1)
for p = 2 to sqrt(n):
    if isPrime[p]:
        for multiple = p*p to n step p:
            isPrime[multiple] = false
```
Why start at p*p? All smaller multiples (p×2, p×3, ..., p×(p-1)) are already marked by smaller primes.

### Modular Exponentiation
```
power(base, exp, mod):
    result = 1
    base = base % mod
    while exp > 0:
        if exp is odd: result = result * base % mod
        exp = exp / 2
        base = base * base % mod
    return result
```

### Modular Inverse (Fermat's Little Theorem)
When mod is prime: `a^(-1) ≡ a^(mod-2) (mod mod)`

---

## 5. Pattern Recognition Signals

```
"Divide a by b, simplify fraction" → GCD/LCM
"Count primes up to N" → Sieve
"a^b mod p" → Fast Exponentiation
"nCr mod p" → Combinatorics with mod inverse
"Sum of digits, digit problems" → Modular arithmetic
"How many multiples of k in range [l, r]?" → Math: (r/k) - ((l-1)/k)
"Check if N is prime" → Trial division up to sqrt(N)
"GCD of entire array" → Reduce with gcd: gcd(arr[0], arr[1], ..., arr[n-1])
```

---

## 6. Step-by-Step Algorithm

### Combinatorics (nCr mod p)
```
Precompute:
  fact[i] = i! mod p
  inv_fact[i] = modular inverse of i! mod p
              = power(fact[i], p-2, p)   (when p is prime)

Then:
  nCr(n, r) = fact[n] * inv_fact[r] % p * inv_fact[n-r] % p
```

### GCD of Array
```
gcdArray(arr):
    result = arr[0]
    for i = 1 to n-1:
        result = gcd(result, arr[i])
    return result

Note: gcd(a, 0) = a, so gcd of entire array is well-defined.
```

---

## 7. Dry Run with Example

### GCD(48, 18)
```
gcd(48, 18):
  gcd(18, 48%18=12)
  gcd(12, 18%12=6)
  gcd(6, 12%6=0)
  gcd(0 skipped — b=0) → return 6

gcd(48, 18) = 6 ✓
lcm(48, 18) = (48/6) * 18 = 8 * 18 = 144 ✓
```

### Fast Power: 3^10 mod 1000
```
base=3, exp=10, mod=1000, result=1

exp=10 (even): base = 3^2=9 % 1000 = 9, exp=5
exp=5 (odd):  result = 1*9=9, base=9^2=81, exp=2
exp=2 (even): base = 81^2=6561 % 1000 = 561, exp=1
exp=1 (odd):  result = 9*561=5049 % 1000 = 49, base=561^2=..., exp=0
Return 49.

Verify: 3^10 = 59049, 59049 % 1000 = 49 ✓
```

### Sieve for n=20
```
isPrime: [F,F,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T,T]
         [0,1,2,3,4,5,6,7,8,9,10...]

p=2: cross 4,6,8,10,12,14,16,18,20
p=3: cross 9,15 (already: 6,12,18)
p=4: not prime, skip
p=5 (sqrt(20)≈4.47, but check to sqrt): cross 25 > 20

Primes: 2,3,5,7,11,13,17,19 ✓
```

---

## 8. Code Implementation

```java
public class MathAlgorithms {

    // ── GCD / LCM ──────────────────────────────────────────────────────────
    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b; // divide first to prevent overflow
    }

    // ── Sieve of Eratosthenes ─────────────────────────────────────────────
    static boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;
        for (int p = 2; (long) p * p <= n; p++) {
            if (isPrime[p]) {
                for (int m = p * p; m <= n; m += p) {
                    isPrime[m] = false;
                }
            }
        }
        return isPrime;
    }

    // Count primes up to n
    static int countPrimes(int n) {
        boolean[] isPrime = sieve(n);
        int count = 0;
        for (boolean b : isPrime) if (b) count++;
        return count;
    }

    // ── Fast Modular Exponentiation ────────────────────────────────────────
    static long power(long base, long exp, long mod) {
        long result = 1;
        base %= mod;
        while (exp > 0) {
            if ((exp & 1) == 1) result = result * base % mod; // odd exp
            exp >>= 1;         // halve exponent
            base = base * base % mod;
        }
        return result;
    }

    // ── Modular Inverse (p must be prime, Fermat's Little Theorem) ─────────
    static long modInverse(long a, long mod) {
        return power(a, mod - 2, mod); // a^(mod-2) mod p
    }

    // ── Combinatorics: nCr mod p ───────────────────────────────────────────
    static final int MOD = 1_000_000_007;
    static long[] fact, invFact;

    static void precompute(int maxN) {
        fact = new long[maxN + 1];
        invFact = new long[maxN + 1];
        fact[0] = 1;
        for (int i = 1; i <= maxN; i++) fact[i] = fact[i-1] * i % MOD;
        invFact[maxN] = modInverse(fact[maxN], MOD);
        for (int i = maxN - 1; i >= 0; i--) invFact[i] = invFact[i+1] * (i+1) % MOD;
    }

    static long nCr(int n, int r) {
        if (r < 0 || r > n) return 0;
        return fact[n] * invFact[r] % MOD * invFact[n-r] % MOD;
    }

    // ── Is Prime (single number) ──────────────────────────────────────────
    static boolean isPrime(long n) {
        if (n < 2) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (long i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
```

---

## 9. Time Complexity

| Algorithm | Time Complexity | Notes |
|-----------|----------------|-------|
| GCD(a, b) | O(log min(a,b)) | Euclidean — each step: a % b < a/2 |
| LCM(a, b) | O(log min(a,b)) | Uses GCD |
| Sieve up to N | O(N log log N) | Nearly linear |
| Single primality test | O(sqrt(N)) | Trial division |
| Fast power a^b mod m | O(log b) | Binary exponentiation |
| Precompute nCr table | O(N) | After O(N) factorial + O(N) inverse |
| Single nCr query | O(1) | After precomputation |

---

## 10. Space Complexity

| Algorithm | Space |
|-----------|-------|
| GCD | O(log min(a,b)) stack |
| Sieve | O(N) |
| Fast power | O(1) |
| nCr precomputation | O(N) |

---

## 11. Edge Cases

| Scenario | Correct Handling |
|----------|-----------------|
| `gcd(0, b)` | Returns b (gcd(0,b) = b by definition) |
| `gcd(a, a)` | Returns a |
| `lcm` overflow | Divide first: `(a / gcd(a,b)) * b` |
| Sieve for n=1 | No primes — isPrime[0]=isPrime[1]=false |
| `power(0, 0, mod)` | Conventionally 1 (0^0 = 1 in combinatorics) |
| `nCr(n, 0)` | Returns 1 |
| `nCr(n, r)` where r > n | Returns 0 |

---

## 12. Common Mistakes

```java
// MISTAKE 1: LCM overflow
long lcm = a * b / gcd(a, b);  // WRONG: a*b overflows for large a,b
long lcm = (a / gcd(a, b)) * b; // CORRECT: divide first

// MISTAKE 2: Sieve inner loop starting at 2*p instead of p*p
for (int m = 2 * p; m <= n; m += p) // WRONG: redundant work, but not incorrect
for (int m = p * p; m <= n; m += p) // CORRECT: smaller multiples already crossed

// MISTAKE 3: Sieve loop condition
for (int p = 2; p <= n; p++) // WRONG: O(n log n) outer iterations
for (int p = 2; (long)p * p <= n; p++) // CORRECT: only up to sqrt(n), cast to long

// MISTAKE 4: Fast power — modding the base before loop
long result = 1, base = base; // WRONG: base may be >= mod
long result = 1; base %= mod; // CORRECT: always reduce before loop

// MISTAKE 5: Not using long in modular multiplication
result = result * base % mod;  // WRONG if result and base are int (overflow)
result = (long)result * base % mod; // CORRECT
```

---

## 13. Interview-Level Explanation

**Q: "Why does the Euclidean algorithm work?"**

> "The key insight is: any common divisor of a and b also divides (a mod b). This is because a mod b = a - k*b for some integer k, so if d divides both a and b, it also divides a - k*b. Therefore gcd(a, b) = gcd(b, a mod b). Each recursive call reduces the larger number by at least half, so the algorithm terminates in O(log min(a,b)) steps."

**Q: "How does binary exponentiation work?"**

> "The idea is: a^n can be computed by squaring. If n is even, a^n = (a^(n/2))^2. If n is odd, a^n = a × a^(n-1). We express n in binary and process bit by bit. Since n has log2(n) bits, we only need O(log n) multiplications instead of O(n). In modular arithmetic, we take mod at each step to keep numbers small."

---

## 14. Real-World Use Cases

| Application | Algorithm |
|------------|-----------|
| **Cryptography (RSA)** | Fast modular exponentiation (a^e mod n) |
| **Hash functions** | Modular arithmetic |
| **Fraction simplification** | GCD |
| **Number theory libraries** | Sieve for prime generation |
| **Competitive programming** | nCr mod p with precomputed factorials |
| **Calendar algorithms** | LCM (find when two events sync) |
| **Image processing** | GCD for aspect ratio reduction |

---

## 15. Variations

| Variation | Algorithm |
|-----------|----------|
| GCD of array | Reduce with gcd: `for each element: result = gcd(result, arr[i])` |
| All primes in range [l, r] | Segmented Sieve |
| Euler's Totient φ(n) | Count numbers ≤ n coprime to n; sieve-like |
| Factorization | Trial division, or Sieve to get smallest prime factor |
| Matrix exponentiation | Fast power with matrix multiplication (Fibonacci in O(log n)) |
| Extended Euclidean | Solves ax + by = gcd(a,b) for x, y |

---

## 16. Practice Problems

### Easy — Foundation
1. **Count Primes** (LeetCode #204)
   - *Task:* Return the count of primes less than n.
   - *Hint:* Sieve of Eratosthenes.

2. **Sqrt(x)** (LeetCode #69)
   - *Task:* Integer square root of x.
   - *Hint:* Binary search on [0, x].

3. **Power of Three** (LeetCode #326)
   - *Task:* Determine if n is a power of 3.
   - *Hint:* Check if max power of 3 that fits in int is divisible by n.

### Medium — Core
1. **Nth Ugly Number** (LeetCode #264)
   - *Task:* Numbers whose only prime factors are 2, 3, 5.
   - *Hint:* Three pointers — multiply 2, 3, 5 and pick minimum.

2. **Super Pow** (LeetCode #372)
   - *Task:* a^b mod 1337, where b is a large array.
   - *Hint:* Fast power with cyclic property of remainders.

3. **Factorial Trailing Zeroes** (LeetCode #172)
   - *Task:* Count trailing zeroes in n!
   - *Hint:* Count factors of 5: n/5 + n/25 + n/125 + ...

4. **Perfect Squares** (LeetCode #279)
   - *Task:* Min number of perfect squares summing to n.
   - *Hint:* DP, or Lagrange's four-square theorem.

5. **Find GCD of Array** (LeetCode #1979)
   - *Task:* GCD of smallest and largest element.
   - *Hint:* Direct gcd call.

### Hard — Advanced
1. **Count of Range Sum** (LeetCode #327)
   - *Task:* Count range sums that lie in [lower, upper].
   - *Hint:* Merge sort or BIT with coordinate compression.

2. **Largest Component Size by Common Factor** (LeetCode #952)
   - *Task:* Group numbers sharing a common factor, find largest group.
   - *Hint:* DSU + factorization.

3. **Minimum Possible Integer After at Most K Adjacent Swaps on Digits** (LeetCode #1505)
   - *Task:* Math + BIT for efficient swap counting.
   - *Hint:* BIT for position tracking.

---

## 17. How to Know You Have Mastered Mathematical Algorithms

You have mastered this topic when you can:
- [ ] Implement iterative GCD without a reference
- [ ] Implement Sieve of Eratosthenes with the p*p optimization
- [ ] Implement modular fast exponentiation for arbitrary a^b mod m
- [ ] Precompute factorials and inverse factorials for nCr queries
- [ ] Explain Fermat's Little Theorem and why it gives modular inverse
- [ ] Handle all edge cases: gcd(0,b), lcm overflow, nCr with r>n
- [ ] Know when to use long vs int for intermediate calculations
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. What is gcd(0, 5)? What about gcd(12, 12)?

2. To compute lcm(a, b) without overflow, you write `(a / gcd(a, b)) * b`. Why divide first?

3. How many multiplications does `power(2, 100)` require with fast exponentiation?

4. Sieve of Eratosthenes — for n=100, the outer loop runs from 2 to what value?

5. What is the modular inverse of 3 modulo 7? (Hint: use Fermat's Little Theorem)

6. nCr(5, 2) = ? Verify using the formula with factorials.

7. Why does the Sieve mark composites starting from p*p instead of 2*p?

8. In modular arithmetic, (a + b) % m = ((a % m) + (b % m)) % m. Does the same hold for multiplication? For exponentiation?

> **Answers:**
> 1. gcd(0, 5) = 5 (gcd(b, a%b): gcd(5, 0%5=0) → return 5). gcd(12, 12) = 12.
> 2. If a and b are each close to Long.MAX_VALUE/2, their product overflows. Dividing a by gcd(a,b) first produces a smaller number that won't overflow when multiplied by b.
> 3. 100 in binary is 1100100 = 7 bits. Fast power does at most 2×⌊log2(100)⌋ = 2×6 = 12 multiplications (squaring + multiply for odd bits). Actual: 7 squarings + 3 multiplies = 10 total.
> 4. Outer loop runs from 2 to floor(sqrt(100)) = 10.
> 5. By Fermat: 3^(7-2) mod 7 = 3^5 mod 7 = 243 mod 7 = 243 - 34×7 = 243-238 = 5. Check: 3×5=15, 15 mod 7 = 1 ✓
> 6. nCr(5,2) = 5! / (2! × 3!) = 120 / (2 × 6) = 10.
> 7. For prime p, all composite multiples smaller than p*p (i.e., p×2, p×3, ..., p×(p-1)) have a prime factor smaller than p and were already crossed out by that smaller prime. Starting at p*p avoids redundant work.
> 8. Yes for both multiplication and addition: (a×b) % m = ((a%m)×(b%m)) % m. For exponentiation: a^b % m = power(a%m, b, m). This is the foundation of modular exponentiation.

---

## 19. PRO-Level Number Theory

### Extended Euclidean Algorithm
Beyond `gcd`, it finds integers `x, y` with `a·x + b·y = gcd(a, b)` — the basis of modular inverse when the modulus is **not prime**.
```java
long[] extgcd(long a, long b) {          // returns {gcd, x, y}
    if (b == 0) return new long[]{a, 1, 0};
    long[] r = extgcd(b, a % b);
    return new long[]{r[0], r[2], r[1] - (a / b) * r[2]};
}
long modInverse(long a, long m) {         // works for any m coprime to a
    long[] r = extgcd(a, m);
    if (r[0] != 1) return -1;             // inverse exists iff gcd(a,m)==1
    return (r[1] % m + m) % m;
}
```

### Linear Sieve (Euler's Sieve) — O(n)
Computes primes AND the smallest prime factor of every number in true O(n), each composite marked exactly once.
```java
int[] linearSieve(int n) {
    int[] spf = new int[n + 1];           // smallest prime factor
    List<Integer> primes = new ArrayList<>();
    for (int i = 2; i <= n; i++) {
        if (spf[i] == 0) { spf[i] = i; primes.add(i); }
        for (int p : primes) {
            if (p > spf[i] || (long) i * p > n) break;
            spf[i * p] = p;               // marked once, by its smallest prime factor
        }
    }
    return spf;                           // spf enables O(log n) factorization
}
```

### Matrix Exponentiation — Recurrences in O(log n)
Any linear recurrence (Fibonacci, tribonacci, path counts) can be raised to the n-th term via fast matrix power. Fibonacci uses `[[1,1],[1,0]]^n`.
```java
long MOD = 1_000_000_007L;
long[][] matMul(long[][] a, long[][] b) {
    int n = a.length, m = b[0].length, k = b.length;
    long[][] c = new long[n][m];
    for (int i = 0; i < n; i++)
        for (int x = 0; x < k; x++) if (a[i][x] != 0)
            for (int j = 0; j < m; j++)
                c[i][j] = (c[i][j] + a[i][x] * b[x][j]) % MOD;
    return c;
}
long[][] matPow(long[][] base, long e) {
    int n = base.length;
    long[][] res = new long[n][n];
    for (int i = 0; i < n; i++) res[i][i] = 1;   // identity
    while (e > 0) {
        if ((e & 1) == 1) res = matMul(res, base);
        base = matMul(base, base);
        e >>= 1;
    }
    return res;
}
long fib(long n) {                        // O(log n) Fibonacci
    if (n == 0) return 0;
    long[][] m = matPow(new long[][]{{1,1},{1,0}}, n);
    return m[0][1];
}
```

### Euler's Totient φ(n)
Counts integers in `[1, n]` coprime to `n`. Multiplicative; computed from distinct prime factors.
```java
long phi(long n) {
    long result = n;
    for (long p = 2; p * p <= n; p++) {
        if (n % p == 0) {
            while (n % p == 0) n /= p;
            result -= result / p;         // apply (1 - 1/p)
        }
    }
    if (n > 1) result -= result / n;
    return result;
}
```
Euler's theorem generalizes Fermat: `a^φ(m) ≡ 1 (mod m)` when `gcd(a,m)=1`, so `a^(-1) ≡ a^(φ(m)-1)`.

### Chinese Remainder Theorem (CRT)
Given `x ≡ r1 (mod m1)` and `x ≡ r2 (mod m2)` with coprime moduli, CRT reconstructs the unique `x mod (m1·m2)` — useful for combining results computed under several small moduli.

### Miller-Rabin (deterministic for 64-bit)
For primality of large numbers, trial division is too slow. Miller-Rabin with the witness set `{2,3,5,7,11,13,17,19,23,29,31,37}` is deterministic for all `n < 3.3·10^24`. Implement `mulmod`/`powmod` carefully (via `Math.multiplyHigh` or `BigInteger`) to avoid overflow.

---

**Next →** `../25_Problem_Solving/01_Problem_Solving_System.md`
