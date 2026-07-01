# Hot 150 Solutions - Arrays, Two Pointers, Sliding Window, Binary Search

> This batch covers the highest-frequency foundation problems. Each writeup is intentionally compact: enough to revise, explain, and code under interview pressure.

---

## 1. Two Sum

- Pattern: HashMap lookup
- Difficulty: Easy
- Company signal: Baseline hashmap test

### Intuition

For every number, ask: "Have I already seen the number needed to complete the target?"

### Java Solution

```java
int[] twoSum(int[] nums, int target) {
    Map<Integer, Integer> seen = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
        int need = target - nums[i];
        if (seen.containsKey(need)) {
            return new int[] {seen.get(need), i};
        }
        seen.put(nums[i], i);
    }

    return new int[] {-1, -1};
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)`

### Common Mistake

Putting the current number into the map before checking can accidentally reuse the same index.

### Interview Explanation

I store previously seen values with their indices. For each current value, I compute the complement needed to hit the target. If that complement already exists, I return both indices. Otherwise, I save the current value for future numbers.

---

## 49. Group Anagrams

- Pattern: Hash signature
- Difficulty: Medium
- Company signal: String hashing classic

### Intuition

Anagrams have the same characters in different order. If two words share the same sorted form, they belong together.

### Java Solution

```java
List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();

    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }

    return new ArrayList<>(groups.values());
}
```

### Complexity

- Time: `O(n * k log k)`, where `k` is max word length
- Space: `O(n * k)`

### Common Mistake

Using character sum as a key causes collisions. Use sorted string or frequency tuple.

### Interview Explanation

I convert each string to a canonical signature by sorting its characters. All anagrams produce the same signature, so I use that as the hashmap key. Each key maps to the list of original words in that anagram group.

---

## 217. Contains Duplicate

- Pattern: HashSet
- Difficulty: Easy
- Company signal: Basic duplicate detection

### Intuition

If a value appears twice, the second time we see it the set already contains it.

### Java Solution

```java
boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();

    for (int num : nums) {
        if (!seen.add(num)) return true;
    }

    return false;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)`

### Common Mistake

Sorting also works but costs `O(n log n)` and mutates order.

### Interview Explanation

I keep a set of values seen so far. If insertion fails, the value is already present and we found a duplicate. If the loop finishes, all values were unique.

---

## 238. Product of Array Except Self

- Pattern: Prefix/suffix product
- Difficulty: Medium
- Company signal: Must know without division

### Intuition

The answer at index `i` is product of everything left of `i` times everything right of `i`.

### Java Solution

```java
int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] ans = new int[n];

    int prefix = 1;
    for (int i = 0; i < n; i++) {
        ans[i] = prefix;
        prefix *= nums[i];
    }

    int suffix = 1;
    for (int i = n - 1; i >= 0; i--) {
        ans[i] *= suffix;
        suffix *= nums[i];
    }

    return ans;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)` extra, excluding output

### Common Mistake

Using division breaks when zeros appear and violates the common constraint.

### Interview Explanation

I first store the product of all elements to the left of each index. Then I walk from right to left and multiply by the product of all elements to the right. This gives the product except self without division.

---

## 347. Top K Frequent Elements

- Pattern: HashMap + min heap
- Difficulty: Medium
- Company signal: Heap and frequency map

### Intuition

Count frequencies, then keep only the `k` most frequent elements in a min heap.

### Java Solution

```java
int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    for (int num : nums) freq.put(num, freq.getOrDefault(num, 0) + 1);

    PriorityQueue<Integer> heap = new PriorityQueue<>(
            Comparator.comparingInt(freq::get));

    for (int num : freq.keySet()) {
        heap.offer(num);
        if (heap.size() > k) heap.poll();
    }

    int[] ans = new int[k];
    for (int i = k - 1; i >= 0; i--) {
        ans[i] = heap.poll();
    }
    return ans;
}
```

### Complexity

- Time: `O(n log k)`
- Space: `O(n)`

### Common Mistake

Sorting all unique elements is simpler but costs `O(u log u)`.

### Interview Explanation

I count every number first. Then I maintain a min heap by frequency, removing the smallest whenever the heap grows beyond `k`. At the end, the heap contains exactly the `k` most frequent elements.

---

## 128. Longest Consecutive Sequence

- Pattern: HashSet sequence start
- Difficulty: Medium
- Company signal: Frequent `O(n)` hashmap/set trick

### Intuition

Only start counting from numbers that have no previous neighbor.

### Java Solution

```java
int longestConsecutive(int[] nums) {
    Set<Integer> set = new HashSet<>();
    for (int num : nums) set.add(num);

    int best = 0;
    for (int num : set) {
        if (set.contains(num - 1)) continue;

        int curr = num;
        int len = 1;
        while (set.contains(curr + 1)) {
            curr++;
            len++;
        }
        best = Math.max(best, len);
    }

    return best;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)`

### Common Mistake

Starting a scan from every number can become `O(n^2)`.

### Interview Explanation

I put all values into a set. A number is the start of a sequence only if `num - 1` is absent. From such starts, I count forward. Since each sequence is counted once, the total work is linear.

---

## 560. Subarray Sum Equals K

- Pattern: Prefix sum + HashMap
- Difficulty: Medium
- Company signal: Core prefix-sum interview pattern

### Intuition

If current prefix sum is `sum`, then a previous prefix of `sum - k` creates a subarray summing to `k`.

### Java Solution

```java
int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> count = new HashMap<>();
    count.put(0, 1);

    int sum = 0;
    int ans = 0;

    for (int num : nums) {
        sum += num;
        ans += count.getOrDefault(sum - k, 0);
        count.put(sum, count.getOrDefault(sum, 0) + 1);
    }

    return ans;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(n)`

### Common Mistake

Sliding window fails with negative numbers. Prefix sums handle negatives.

### Interview Explanation

I track how often each prefix sum has appeared. At each index, I need a previous prefix sum equal to `current - k`. Every occurrence of that prefix creates a valid subarray ending here.

---

## 53. Maximum Subarray

- Pattern: Kadane's algorithm
- Difficulty: Medium
- Company signal: Classic one-pass DP/greedy

### Intuition

At each number, decide whether to extend the previous subarray or start fresh.

### Java Solution

```java
int maxSubArray(int[] nums) {
    int curr = nums[0];
    int best = nums[0];

    for (int i = 1; i < nums.length; i++) {
        curr = Math.max(nums[i], curr + nums[i]);
        best = Math.max(best, curr);
    }

    return best;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Initializing best to `0` fails for all-negative arrays.

### Interview Explanation

The best subarray ending at the current index either starts at the current value or extends the previous best ending here. I keep the best ending sum and the global best. This handles negative numbers naturally.

---

## 125. Valid Palindrome

- Pattern: Opposite pointers
- Difficulty: Easy
- Company signal: Basic string cleanup and pointer movement

### Intuition

Compare useful characters from both ends and skip anything that is not alphanumeric.

### Java Solution

```java
boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;

        if (Character.toLowerCase(s.charAt(left))
                != Character.toLowerCase(s.charAt(right))) {
            return false;
        }
        left++;
        right--;
    }

    return true;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Building a cleaned string is okay but uses extra memory.

### Interview Explanation

I use two pointers from both ends. Each pointer skips non-alphanumeric characters. Then I compare lowercase versions of the characters. If all mirrored comparisons match, the string is a palindrome.

---

## 167. Two Sum II

- Pattern: Sorted two pointers
- Difficulty: Medium
- Company signal: Sorted-array pointer invariant

### Intuition

If the sum is too small, move left up. If too large, move right down.

### Java Solution

```java
int[] twoSum(int[] numbers, int target) {
    int left = 0;
    int right = numbers.length - 1;

    while (left < right) {
        int sum = numbers[left] + numbers[right];
        if (sum == target) return new int[] {left + 1, right + 1};
        if (sum < target) left++;
        else right--;
    }

    return new int[] {-1, -1};
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Returning zero-based indices even though the problem asks for one-based indices.

### Interview Explanation

Because the array is sorted, increasing the left pointer increases the sum, and decreasing the right pointer decreases the sum. That lets me eliminate one side at every step.

---

## 15. 3Sum

- Pattern: Sort + two pointers
- Difficulty: Medium
- Company signal: Duplicate handling

### Intuition

Fix one number, then solve two-sum on the sorted suffix.

### Java Solution

```java
List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> ans = new ArrayList<>();

    for (int i = 0; i < nums.length - 2; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) continue;

        int left = i + 1;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];
            if (sum == 0) {
                ans.add(Arrays.asList(nums[i], nums[left], nums[right]));
                left++;
                right--;
                while (left < right && nums[left] == nums[left - 1]) left++;
                while (left < right && nums[right] == nums[right + 1]) right--;
            } else if (sum < 0) {
                left++;
            } else {
                right--;
            }
        }
    }

    return ans;
}
```

### Complexity

- Time: `O(n^2)`
- Space: `O(1)` extra, excluding output

### Common Mistake

Skipping duplicates before moving pointers can skip valid triples. Move first, then skip repeats.

### Interview Explanation

After sorting, I fix each first value and use two pointers to find pairs that make the total zero. Sorting lets me move pointers based on whether the sum is too small or too large. I skip duplicate fixed values and duplicate pair values to avoid repeated triplets.

---

## 11. Container With Most Water

- Pattern: Greedy two pointers
- Difficulty: Medium
- Company signal: Tests pointer movement proof

### Intuition

Area is limited by the shorter wall. Moving the taller wall cannot help if the shorter wall stays.

### Java Solution

```java
int maxArea(int[] height) {
    int left = 0;
    int right = height.length - 1;
    int best = 0;

    while (left < right) {
        int h = Math.min(height[left], height[right]);
        best = Math.max(best, h * (right - left));

        if (height[left] < height[right]) left++;
        else right--;
    }

    return best;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Moving the taller pointer loses width without improving the limiting height.

### Interview Explanation

The current area is constrained by the shorter side. To possibly get a better area with smaller width, I must try to find a taller shorter side. So I move the pointer at the shorter wall inward.

---

## 42. Trapping Rain Water

- Pattern: Two pointers with max boundaries
- Difficulty: Hard
- Company signal: Classic hard pointer problem

### Intuition

Water above a bar is limited by the smaller of the best wall on the left and right.

### Java Solution

```java
int trap(int[] height) {
    int left = 0;
    int right = height.length - 1;
    int leftMax = 0;
    int rightMax = 0;
    int water = 0;

    while (left < right) {
        if (height[left] < height[right]) {
            leftMax = Math.max(leftMax, height[left]);
            water += leftMax - height[left];
            left++;
        } else {
            rightMax = Math.max(rightMax, height[right]);
            water += rightMax - height[right];
            right--;
        }
    }

    return water;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Adding negative water if max is updated after subtraction.

### Interview Explanation

I keep the highest wall seen from each side. The side with the smaller current height determines the safe water level, because the other side already has a boundary at least that high. I process that side, accumulate water, and move inward.

---

## 121. Best Time to Buy and Sell Stock

- Pattern: One-pass min price
- Difficulty: Easy
- Company signal: Greedy baseline

### Intuition

To sell today, the best buy day is the lowest price before today.

### Java Solution

```java
int maxProfit(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int best = 0;

    for (int price : prices) {
        minPrice = Math.min(minPrice, price);
        best = Math.max(best, price - minPrice);
    }

    return best;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Allowing sell before buy. Updating min as you scan left to right prevents that.

### Interview Explanation

I scan prices once. At each day, I track the cheapest price seen so far and compute profit if I sell today. The best of those profits is the answer.

---

## 3. Longest Substring Without Repeating Characters

- Pattern: Sliding window with last seen index
- Difficulty: Medium
- Company signal: Core sliding window

### Intuition

Keep a window with no duplicate characters. If a character repeats inside the window, move the left boundary past its previous position.

### Java Solution

```java
int lengthOfLongestSubstring(String s) {
    Map<Character, Integer> last = new HashMap<>();
    int left = 0;
    int best = 0;

    for (int right = 0; right < s.length(); right++) {
        char ch = s.charAt(right);
        if (last.containsKey(ch)) {
            left = Math.max(left, last.get(ch) + 1);
        }
        last.put(ch, right);
        best = Math.max(best, right - left + 1);
    }

    return best;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(min(n, charset))`

### Common Mistake

Moving `left` backward when the repeated character is outside the current window.

### Interview Explanation

The window always represents a substring without duplicates. I store the last index of each character. When a duplicate appears within the current window, I jump `left` past the old occurrence.

---

## 424. Longest Repeating Character Replacement

- Pattern: Sliding window with max frequency
- Difficulty: Medium
- Company signal: Window validity by replacement count

### Intuition

In a window, keep the most frequent character and replace everything else. The window is valid if `windowSize - maxFreq <= k`.

### Java Solution

```java
int characterReplacement(String s, int k) {
    int[] freq = new int[26];
    int left = 0;
    int maxFreq = 0;
    int best = 0;

    for (int right = 0; right < s.length(); right++) {
        int idx = s.charAt(right) - 'A';
        freq[idx]++;
        maxFreq = Math.max(maxFreq, freq[idx]);

        while (right - left + 1 - maxFreq > k) {
            freq[s.charAt(left) - 'A']--;
            left++;
        }

        best = Math.max(best, right - left + 1);
    }

    return best;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(1)`

### Common Mistake

Recomputing max frequency on every shrink is unnecessary for this problem.

### Interview Explanation

The best character to keep in a window is the most frequent one. All other characters would need replacement. If replacements exceed `k`, I shrink from the left. The max frequency may be stale, but it never causes a missed optimal answer because it only delays shrinking.

---

## 567. Permutation in String

- Pattern: Fixed sliding window frequency
- Difficulty: Medium
- Company signal: Anagram window

### Intuition

A permutation of `s1` exists in `s2` if some window of length `s1.length()` has the same character counts.

### Java Solution

```java
boolean checkInclusion(String s1, String s2) {
    if (s1.length() > s2.length()) return false;

    int[] need = new int[26];
    int[] window = new int[26];

    for (char ch : s1.toCharArray()) need[ch - 'a']++;

    for (int i = 0; i < s2.length(); i++) {
        window[s2.charAt(i) - 'a']++;

        if (i >= s1.length()) {
            window[s2.charAt(i - s1.length()) - 'a']--;
        }

        if (Arrays.equals(need, window)) return true;
    }

    return false;
}
```

### Complexity

- Time: `O(26 * n)`, effectively `O(n)`
- Space: `O(1)`

### Common Mistake

Using a variable-size window when the permutation length is fixed.

### Interview Explanation

Every valid permutation has exactly the same length as `s1`, so I slide a fixed-size window across `s2`. I maintain character counts for the window and compare them to the target counts.

---

## 76. Minimum Window Substring

- Pattern: Need/have sliding window
- Difficulty: Hard
- Company signal: Most important advanced sliding window

### Intuition

Expand until the window has all required characters, then shrink to make it minimal.

### Java Solution

```java
String minWindow(String s, String t) {
    if (t.length() > s.length()) return "";

    Map<Character, Integer> need = new HashMap<>();
    for (char ch : t.toCharArray()) {
        need.put(ch, need.getOrDefault(ch, 0) + 1);
    }

    Map<Character, Integer> window = new HashMap<>();
    int required = need.size();
    int formed = 0;
    int left = 0;
    int bestLen = Integer.MAX_VALUE;
    int bestStart = 0;

    for (int right = 0; right < s.length(); right++) {
        char add = s.charAt(right);
        window.put(add, window.getOrDefault(add, 0) + 1);

        if (need.containsKey(add) && window.get(add).intValue() == need.get(add).intValue()) {
            formed++;
        }

        while (formed == required) {
            if (right - left + 1 < bestLen) {
                bestLen = right - left + 1;
                bestStart = left;
            }

            char remove = s.charAt(left++);
            window.put(remove, window.get(remove) - 1);
            if (need.containsKey(remove) && window.get(remove) < need.get(remove)) {
                formed--;
            }
        }
    }

    return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
}
```

### Complexity

- Time: `O(n + m)`
- Space: `O(charset)`

### Common Mistake

Counting total matched characters incorrectly when duplicates exist in `t`.

### Interview Explanation

I count required frequencies from `t`. The window is valid only when every required character meets its required count. I expand right until valid, then move left while preserving validity to find the smallest window.

---

## 239. Sliding Window Maximum

- Pattern: Monotonic deque
- Difficulty: Hard
- Company signal: Core deque problem

### Intuition

Keep possible maximum indices in decreasing value order. The front is always the window maximum.

### Java Solution

```java
int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] ans = new int[n - k + 1];
    Deque<Integer> dq = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
        while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();

        dq.offerLast(i);
        if (i >= k - 1) ans[i - k + 1] = nums[dq.peekFirst()];
    }

    return ans;
}
```

### Complexity

- Time: `O(n)`
- Space: `O(k)`

### Common Mistake

Storing values instead of indices makes it hard to remove expired elements.

### Interview Explanation

The deque stores indices whose values are decreasing. Smaller values behind a new larger value can never become maximum, so I remove them. I also remove indices outside the window. The front is the max for each completed window.

---

## 704. Binary Search

- Pattern: Classic binary search
- Difficulty: Easy
- Company signal: Must be bug-free

### Intuition

Each comparison removes half of the remaining search space.

### Java Solution

```java
int search(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;
        if (nums[mid] < target) left = mid + 1;
        else right = mid - 1;
    }

    return -1;
}
```

### Complexity

- Time: `O(log n)`
- Space: `O(1)`

### Common Mistake

Using `mid = (left + right) / 2` can overflow in some languages.

### Interview Explanation

I keep a closed interval `[left, right]`. If the middle is too small, the answer must be right of it. If too large, it must be left. The interval shrinks until the target is found or empty.

---

## 74. Search a 2D Matrix

- Pattern: Flattened binary search
- Difficulty: Medium
- Company signal: Index conversion

### Intuition

Because every row continues after the previous row, the matrix behaves like one sorted array.

### Java Solution

```java
boolean searchMatrix(int[][] matrix, int target) {
    int rows = matrix.length;
    int cols = matrix[0].length;
    int left = 0;
    int right = rows * cols - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        int value = matrix[mid / cols][mid % cols];

        if (value == target) return true;
        if (value < target) left = mid + 1;
        else right = mid - 1;
    }

    return false;
}
```

### Complexity

- Time: `O(log(rows * cols))`
- Space: `O(1)`

### Common Mistake

Mixing up row and column conversion: row is `mid / cols`, column is `mid % cols`.

### Interview Explanation

I map the matrix to virtual sorted-array indices. Binary search gives a mid index, and I convert it back to row and column. This avoids a separate row search.

---

## 875. Koko Eating Bananas

- Pattern: Binary search on answer
- Difficulty: Medium
- Company signal: Common "minimum feasible value" problem

### Intuition

If Koko can finish at speed `x`, she can also finish at any faster speed. That monotonic property enables binary search.

### Java Solution

```java
int minEatingSpeed(int[] piles, int h) {
    int left = 1;
    int right = 0;
    for (int pile : piles) right = Math.max(right, pile);

    while (left < right) {
        int mid = left + (right - left) / 2;
        if (canFinish(piles, h, mid)) right = mid;
        else left = mid + 1;
    }

    return left;
}

boolean canFinish(int[] piles, int h, int speed) {
    long hours = 0;
    for (int pile : piles) {
        hours += (pile + speed - 1) / speed;
    }
    return hours <= h;
}
```

### Complexity

- Time: `O(n log maxPile)`
- Space: `O(1)`

### Common Mistake

Using floating-point `ceil`; integer ceiling is cleaner and safer.

### Interview Explanation

The search space is possible eating speeds. For a given speed, I compute required hours. If the speed works, I try smaller speeds; otherwise I need a larger speed. This finds the smallest feasible speed.

---

## 153. Find Minimum in Rotated Sorted Array

- Pattern: Rotated binary search
- Difficulty: Medium
- Company signal: Sorted invariant reasoning

### Intuition

The minimum is in the unsorted half. Compare middle with right boundary.

### Java Solution

```java
int findMin(int[] nums) {
    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] > nums[right]) {
            left = mid + 1;
        } else {
            right = mid;
        }
    }

    return nums[left];
}
```

### Complexity

- Time: `O(log n)`
- Space: `O(1)`

### Common Mistake

Using `right = mid - 1` when `nums[mid] <= nums[right]` can discard the minimum at `mid`.

### Interview Explanation

If `nums[mid] > nums[right]`, the rotation point is to the right of mid. Otherwise, mid could be the minimum, so I keep it by moving `right` to `mid`. The loop ends at the minimum.

---

## 33. Search in Rotated Sorted Array

- Pattern: Rotated binary search
- Difficulty: Medium
- Company signal: Must know sorted-half detection

### Intuition

At every step, one half is sorted. Decide whether the target lies in that sorted half.

### Java Solution

```java
int searchRotated(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] == target) return mid;

        if (nums[left] <= nums[mid]) {
            if (nums[left] <= target && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else {
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }

    return -1;
}
```

### Complexity

- Time: `O(log n)`
- Space: `O(1)`

### Common Mistake

Not checking which side is sorted before comparing target ranges.

### Interview Explanation

The rotated array still has one sorted side around the middle. I detect whether the left or right side is sorted, then check whether the target belongs to that side's range. If yes, I keep that side; otherwise, I discard it.

---

## 34. Find First and Last Position

- Pattern: Lower/upper bound
- Difficulty: Medium
- Company signal: Binary search boundaries

### Intuition

Find the first index where value is at least target, and the first index where value is greater than target.

### Java Solution

```java
int[] searchRange(int[] nums, int target) {
    int first = lowerBound(nums, target);
    int last = lowerBound(nums, target + 1) - 1;

    if (first == nums.length || nums[first] != target) {
        return new int[] {-1, -1};
    }
    return new int[] {first, last};
}

int lowerBound(int[] nums, int target) {
    int left = 0;
    int right = nums.length;

    while (left < right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] < target) left = mid + 1;
        else right = mid;
    }

    return left;
}
```

### Complexity

- Time: `O(log n)`
- Space: `O(1)`

### Common Mistake

Using `target + 1` can overflow for `Integer.MAX_VALUE`; a separate upper-bound helper is safest in production.

### Interview Explanation

I use binary search to find boundaries, not just existence. The lower bound gives the first possible target position. The lower bound of the next value gives one past the last target. Then I validate that the target exists.

---

## 215. Kth Largest Element in an Array

- Pattern: Size-k min heap
- Difficulty: Medium
- Company signal: Heap/quickselect classic

### Intuition

Keep only the largest `k` numbers seen so far. The smallest among them is the kth largest.

### Java Solution

```java
int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> heap = new PriorityQueue<>();

    for (int num : nums) {
        heap.offer(num);
        if (heap.size() > k) heap.poll();
    }

    return heap.peek();
}
```

### Complexity

- Time: `O(n log k)`
- Space: `O(k)`

### Common Mistake

Using a max heap and popping `k` times works, but costs `O(n + k log n)` and stores all values.

### Interview Explanation

I maintain a min heap of size `k`. Whenever it grows beyond `k`, I remove the smallest. After scanning all numbers, the heap contains the top `k` largest values, so the root is the kth largest.

---

**Next:** `04_Stack_Linked_Heap_Greedy_Solutions.md`
