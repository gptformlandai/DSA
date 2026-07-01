# Section 11 — Linked List Patterns

---

## 1. What Problem Does This Solve?

Linked list problems test your ability to **manipulate pointer-based data structures in-place** with O(1) extra space. Unlike arrays, linked lists don't support random access, so all patterns rely on careful pointer manipulation — moving, reversing, and restructuring nodes.

Key problem categories:
- Reversal (whole list, segments, k-groups)
- Cycle detection and entry-point finding
- Finding the middle or Kth-from-end node
- Merging, sorting, and reordering
- Palindrome checking

---

## 2. Beginner-Friendly Intuition

A linked list is a **chain of puzzle pieces** — each piece knows only the next piece. You can't jump to piece 7 directly; you must walk from piece 1. But you can reorganize the chain in-place by just changing where pieces point.

Reversing a chain: instead of picking up the whole chain and flipping it, you just redirect each link to point backward instead of forward — no new chain needed.

---

## 3. Real-World Analogy

**Train cars:** Each train car has a coupler pointing to the next car. Reversing the train doesn't require new cars — you just reconnect the couplers in the opposite direction.

**Browser history (forward):** Each page stores a pointer to the next page you visited. Removing a page just requires the previous page to skip over it and point to the page after it.

---

## 4. Core Concept

### The Four Foundational Techniques

| Technique | Core Mechanics | Use Case |
|----------|---------------|---------|
| **Three-pointer reversal** | `prev, curr, next` — redirect each link | Reverse list / segments |
| **Fast & Slow pointers** | Slow moves 1, fast moves 2 | Cycle, middle, Kth from end |
| **Dummy head node** | Dummy before head eliminates edge cases | Merge, remove head |
| **Runner technique** | One pointer races ahead by k steps | Remove Nth from end |

### Three-Pointer Reversal
```
Before: ... → A → B → C → null
After:  null ← A ← B ← C (prev=C, curr=null)
```

---

## 5. Pattern Recognition Signals

Use Linked List patterns when:
```
"Reverse a linked list" / "reverse k-group"
"Detect cycle" / "find cycle entry point"
"Find middle node"
"Remove Nth node from end"
"Merge two sorted lists"
"Palindrome linked list"
"Reorder list" (LeetCode #143)
"Rotate list"
"Sort linked list"
"Flatten multilevel doubly linked list"
```

---

## 6. Step-by-Step Algorithm

### Reverse Linked List (Iterative)
```
prev = null, curr = head
While curr != null:
    next = curr.next    ← save next
    curr.next = prev    ← reverse link
    prev = curr         ← advance prev
    curr = next         ← advance curr
Return prev            ← new head
```

### Find Middle Node (Fast & Slow)
```
slow = head, fast = head
While fast != null AND fast.next != null:
    slow = slow.next
    fast = fast.next.next
Return slow            ← slow is at middle (or left-middle for even length)
```

### Detect Cycle + Find Entry (Floyd's Algorithm)
```
Phase 1 — detect:
  slow = head, fast = head
  While fast != null AND fast.next != null:
      slow = slow.next; fast = fast.next.next
      If slow == fast: CYCLE FOUND, break
  If fast == null: NO CYCLE

Phase 2 — find entry:
  slow = head  (reset slow to head; keep fast at meeting point)
  While slow != fast:
      slow = slow.next; fast = fast.next
  Return slow  (both reach cycle entry at same time)
```

### Remove Nth from End
```
dummy → head
fast = dummy, advance fast by n+1 steps
slow = dummy
While fast != null:
    slow = slow.next; fast = fast.next
slow.next = slow.next.next  ← skip the Nth node
Return dummy.next
```

---

## 7. Dry Run with Example

### Example 1: Reverse Linked List

**Input:** `1 → 2 → 3 → 4 → 5 → null`

```
prev=null, curr=1

Step 1: next=2, 1→null, prev=1, curr=2
        null ← 1  2 → 3 → 4 → 5

Step 2: next=3, 2→1, prev=2, curr=3
        null ← 1 ← 2  3 → 4 → 5

Step 3: next=4, 3→2, prev=3, curr=4
        null ← 1 ← 2 ← 3  4 → 5

Step 4: next=5, 4→3, prev=4, curr=5
        null ← 1 ← 2 ← 3 ← 4  5

Step 5: next=null, 5→4, prev=5, curr=null
        null ← 1 ← 2 ← 3 ← 4 ← 5

curr=null → stop. Return prev=5.
New list: 5 → 4 → 3 → 2 → 1 → null ✓
```

### Example 2: Find Cycle Entry Point

**List:** `1 → 2 → 3 → 4 → 5 → 3` (cycle back to node 3)

```
Phase 1 — Floyd detection:
slow: 1→2→3→4→5→3→4→5→3...
fast: 1→3→5→4→3→5→4...
Meeting point: they meet at node 4

Phase 2 — reset slow to head:
slow=1, fast=4 (meeting point)
Step 1: slow=2, fast=5
Step 2: slow=3, fast=3 ← MEET at node 3 (cycle entry) ✓

Why does this work? If cycle starts at distance F from head,
and meeting point is distance C_diff from start of cycle,
then: slow traveled F + a, fast traveled F + a + nC for some n.
Since fast = 2×slow: F + a + nC = 2(F + a) → nC - a = F.
So from meeting point, C-a more steps = F steps from head.
Both pointers arrive at cycle entry simultaneously.
```

### Example 3: Remove Nth from End (n=2)

**Input:** `1 → 2 → 3 → 4 → 5`, n=2

```
dummy → 1 → 2 → 3 → 4 → 5 → null

Advance fast by n+1=3 steps from dummy:
  fast: dummy → 1 → 2 → 3
  fast is at node 3

slow = dummy

Move both until fast = null:
  Step 1: slow=1, fast=4
  Step 2: slow=2, fast=5
  Step 3: slow=3, fast=null → STOP

slow is at node 3. slow.next is node 4 (the 2nd from end).
slow.next = slow.next.next = node 5

Result: dummy → 1 → 2 → 3 → 5 → null ✓
```

---

## 8. Code Implementation

### Reverse Linked List (Iterative)

```java
ListNode reverse(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next; // save next
        curr.next = prev;          // reverse link
        prev = curr;               // advance prev
        curr = next;               // advance curr
    }
    return prev; // new head
}
```

### Reverse Linked List (Recursive)

```java
ListNode reverseRecursive(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode newHead = reverseRecursive(head.next);
    head.next.next = head; // reverse the link
    head.next = null;      // break old forward link
    return newHead;
}
```

### Find Middle (Fast & Slow)

```java
ListNode findMiddle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow; // middle for odd length, left-middle for even
}
```

### Detect Cycle + Find Entry

```java
ListNode detectCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) {          // cycle detected
            slow = head;             // reset slow to head
            while (slow != fast) {   // move both at speed 1
                slow = slow.next;
                fast = fast.next;
            }
            return slow;             // cycle entry point
        }
    }
    return null; // no cycle
}
```

### Remove Nth Node from End

```java
ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0, head);
    ListNode fast = dummy, slow = dummy;
    for (int i = 0; i <= n; i++) fast = fast.next; // advance n+1 steps
    while (fast != null) { slow = slow.next; fast = fast.next; }
    slow.next = slow.next.next; // skip the target node
    return dummy.next;
}
```

### Merge Two Sorted Lists

```java
ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    ListNode dummy = new ListNode(0);
    ListNode curr = dummy;
    while (l1 != null && l2 != null) {
        if (l1.val <= l2.val) { curr.next = l1; l1 = l1.next; }
        else                   { curr.next = l2; l2 = l2.next; }
        curr = curr.next;
    }
    curr.next = (l1 != null) ? l1 : l2; // attach remaining
    return dummy.next;
}
```

### Palindrome Linked List (O(1) space)

```java
boolean isPalindrome(ListNode head) {
    // Step 1: Find middle
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next; fast = fast.next.next;
    }
    // Step 2: Reverse second half
    ListNode prev = null, curr = slow;
    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev; prev = curr; curr = next;
    }
    // Step 3: Compare first half and reversed second half
    ListNode left = head, right = prev;
    while (right != null) {
        if (left.val != right.val) return false;
        left = left.next; right = right.next;
    }
    return true;
}
```

### Reorder List (LeetCode #143)

```java
void reorderList(ListNode head) {
    // Find middle
    ListNode slow = head, fast = head;
    while (fast.next != null && fast.next.next != null) {
        slow = slow.next; fast = fast.next.next;
    }
    // Reverse second half
    ListNode second = reverse(slow.next);
    slow.next = null; // split at middle
    // Interleave
    ListNode first = head;
    while (second != null) {
        ListNode tmp1 = first.next, tmp2 = second.next;
        first.next = second;
        second.next = tmp1;
        first = tmp1; second = tmp2;
    }
}
```

---

## 9. Time Complexity

| Operation | Complexity | Reason |
|-----------|-----------|--------|
| Reverse | O(n) | Single pass |
| Find middle | O(n) | Fast pointer reaches end |
| Detect cycle | O(n) | At most n + cycle_length steps |
| Remove Nth from end | O(n) | Two-pass or one-pass with two pointers |
| Merge two sorted lists | O(n + m) | Single pass through both |
| Palindrome check | O(n) | Find middle + reverse + compare |
| Sort linked list | O(n log n) | Merge sort on linked list |

---

## 10. Space Complexity

| Approach | Space | Why O(1) is achievable |
|----------|-------|----------------------|
| Iterative reverse | O(1) | Only 3 pointers |
| Recursive reverse | O(n) | Call stack depth n |
| Cycle detection | O(1) | Floyd's uses only 2 pointers |
| Palindrome (reverse-and-compare) | O(1) | Reverse in-place |

> **Prefer iterative over recursive** for linked list problems to achieve O(1) space.

---

## 11. Edge Cases

| Scenario | How to Handle |
|----------|--------------|
| Empty list (head = null) | Return null immediately |
| Single node | `head.next = null` → already reversed, no cycle, is palindrome |
| Two nodes | Reversal: prev/curr dance works correctly |
| Even vs odd length | Fast & slow: slow lands on right-middle for even |
| n = list length (remove head) | Dummy node handles this — dummy.next = dummy.next.next |
| No cycle | Floyd: fast reaches null → return null |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Losing reference to next before redirecting
curr.next = prev; // WRONG: now curr.next is lost!
// CORRECT:
ListNode next = curr.next; // save first
curr.next = prev;          // then redirect

// MISTAKE 2: Wrong null checks for fast pointer
while (fast.next != null && fast != null) // WRONG: NPE on fast.next if fast is null
while (fast != null && fast.next != null) // CORRECT: check fast first

// MISTAKE 3: Not using dummy node for merge/remove head
// Without dummy, "remove head" is a special case
// With dummy: dummy.next = head, remove dummy.next same as any other node

// MISTAKE 4: Off-by-one in "Remove Nth from end"
// Advance fast n steps: slow ends at Nth node (need to handle its prev)
// Advance fast n+1 steps: slow ends at (N+1)th node (can do slow.next = skip)
// Always advance n+1 from dummy for clean removal

// MISTAKE 5: Not resetting slow to head in Floyd's Phase 2
// Phase 2 requires slow=head, fast=meetingPoint
// Both moving at speed 1 guarantees they meet at cycle entry
```

---

## 13. Interview-Level Explanation

**Q: "How do you find the middle of a linked list in one pass?"**

> "I use the Fast & Slow pointer technique. The slow pointer moves one step at a time, the fast pointer moves two. When the fast pointer reaches the end (or null), the slow pointer is exactly at the middle. It's one pass, O(n) time, O(1) space. For even-length lists, slow lands on the left-middle node."

**Q: "Why does Floyd's algorithm find the cycle entry point correctly?"**

> "After the two pointers meet inside the cycle, the math shows that the distance from the meeting point to the cycle entry equals the distance from the head to the cycle entry. So if we reset one pointer to head and move both one step at a time, they meet exactly at the cycle entry point."

---

## 14. Real-World Use Cases

| Application | Linked List Usage |
|------------|-----------------|
| **Memory allocators** | Free list maintains blocks as linked list |
| **LRU Cache** | Doubly linked list + HashMap (O(1) eviction) |
| **Browser history** | Doubly linked list for back/forward |
| **Undo/Redo** | Two stacks (or doubly linked list) |
| **File systems** | inode linked lists for file blocks |
| **Music playlist** | Circular linked list |
| **Hash chaining** | Linked list for collision resolution |

---

## 15. Variations of This Pattern

| Variation | Key Difference | Example |
|-----------|---------------|---------|
| Reverse entire list | Basic 3-pointer | Reverse Linked List |
| Reverse k-group | Group reversal + reconnect | Reverse Nodes in k-Group |
| Reverse between l and r | Partial reversal | Reverse Linked List II |
| Fast & slow (middle) | Speed difference | Middle of Linked List |
| Fast & slow (cycle) | Floyd's 2-phase | Linked List Cycle II |
| Runner (Kth from end) | Offset by k | Remove Nth from End |
| Merge sorted | Dummy node + pointer walk | Merge Two Sorted Lists |
| Palindrome | Reverse + compare | Palindrome Linked List |
| Sort | Merge sort on linked list | Sort List |
| Reorder | Middle + reverse + interleave | Reorder List |

---

## 16. Practice Problems

### Easy — Foundation
1. **Reverse Linked List** (LeetCode #206)
   - *Task:* Reverse the entire list.
   - *Hint:* Three pointers: prev, curr, next. Move forward redirecting links.

2. **Middle of the Linked List** (LeetCode #876)
   - *Task:* Return the middle node.
   - *Hint:* Fast & slow — slow is at middle when fast reaches end.

3. **Linked List Cycle** (LeetCode #141)
   - *Task:* Detect if a cycle exists.
   - *Hint:* Fast & slow — if they ever meet, there's a cycle.

### Medium — Combine Techniques
1. **Remove Nth Node from End** (LeetCode #19)
   - *Task:* Remove the Nth node from the end in one pass.
   - *Hint:* Dummy head + advance fast by n+1 steps first.

2. **Merge Two Sorted Lists** (LeetCode #21)
   - *Task:* Merge two sorted lists into one sorted list.
   - *Hint:* Dummy head + compare and attach smaller at each step.

3. **Palindrome Linked List** (LeetCode #234)
   - *Task:* Check if list is a palindrome in O(n) time, O(1) space.
   - *Hint:* Find middle + reverse second half + compare.

4. **Reorder List** (LeetCode #143)
   - *Task:* Reorder 1→2→3→4→5 to 1→5→2→4→3.
   - *Hint:* Find middle + reverse second half + interleave.

5. **Linked List Cycle II** (LeetCode #142)
   - *Task:* Find the node where the cycle begins.
   - *Hint:* Floyd's algorithm Phase 1 (detect) + Phase 2 (find entry).

### Hard — Advanced Manipulation
1. **Reverse Nodes in k-Group** (LeetCode #25)
   - *Task:* Reverse every k consecutive nodes.
   - *Hint:* Reverse segments of size k, reconnect to previous tail.

2. **Sort List** (LeetCode #148)
   - *Task:* Sort the list in O(n log n) time, O(1) space.
   - *Hint:* Merge sort: split at middle, recursively sort, merge.

3. **Copy List with Random Pointer** (LeetCode #138)
   - *Task:* Deep copy list where each node has a random pointer.
   - *Hint:* Three passes: interleave clones, set random pointers, separate.

---

## 17. How to Know You Have Mastered Linked List Patterns

You have mastered this topic when you can:
- [ ] Write iterative reversal from memory with three pointers correctly
- [ ] Implement Fast & Slow pointers for middle, cycle detection, and removal
- [ ] Explain why Floyd's Phase 2 finds the cycle entry point
- [ ] Use a dummy head node to simplify edge cases in merge/remove
- [ ] Solve Palindrome Linked List in O(1) space
- [ ] Implement Reorder List (middle + reverse + interleave)
- [ ] Recognize that O(1) space requires iterative, not recursive, approach
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Reversing `1 → 2 → 3 → null`. After step 1 (processing node 1), what do `prev`, `curr`, and `next` point to?

2. For an even-length list of 4 nodes [1,2,3,4], where does the slow pointer land after the Fast & Slow loop?

3. In "Remove Nth from End" with n=1 (remove the last node), where does the slow pointer end up if you advance fast by n+1=2 steps?

4. Why is the dummy head node useful in Merge Two Sorted Lists?

5. Recursive reversal has O(n) space. Why? What's the iterative space complexity?

6. Floyd's cycle detection: if both pointers start at head, can they meet at the head if head itself is in a cycle (i.e., the list is entirely one big cycle)?

7. To check palindrome in O(1) space, you reverse the second half. Does this permanently modify the input list?

8. In "Reverse Nodes in k-Group," if the list has 7 nodes and k=3, what happens to the last group (only 1 node remaining)?

> **Answers:**
> 1. `next=2`, `1→null` (prev=1), `curr=2`. So prev=1(pointing to null), curr=2, next=2(saved before).
> 2. Node 2 (the left-middle). Fast reaches node 4 (last), slow is at node 2.
> 3. Slow ends at node (n-1)th from end = second-to-last node. It can then skip the last node.
> 4. Without dummy, removing the head is a special case (no "previous" node). With dummy, dummy.next = original head, and removing head is the same as removing any other node.
> 5. Recursive: O(n) call stack depth. Iterative: O(1) — just 3 pointers.
> 6. Yes — they'll both start at head and fast moves 2 steps, slow 1. They'll meet at some cycle point, not necessarily head. Phase 2 then finds the actual entry (head in this case).
> 7. Yes, it modifies the list. Good practice is to restore the list after checking (reverse the second half again).
> 8. The last 1-node group is left as-is (not reversed, since it has fewer than k nodes).

---

**Next →** `../12_Recursion_Backtracking/01_Recursion_Backtracking.md`
