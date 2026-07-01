# Section 2.3 — Linked List

---

## 1. What Problem Does This Solve?

Arrays have O(n) insertion and deletion in the middle (because of shifting). Linked lists solve this: insertion and deletion at a known node is O(1) — just update pointers. The trade-off: no random access by index (O(n) traversal required).

---

## 2. Beginner-Friendly Intuition

A linked list is a chain of nodes. Each node holds a value and a pointer to the next node. There's no physical adjacency in memory — the pointer tells you where to find the next node.

**Singly linked:** A → B → C → null. You can only go forward.

**Doubly linked:** null ← A ⇆ B ⇆ C → null. You can go forward and backward.

---

## 3. Real-World Analogy

**Train carriages:** Each carriage knows only what's coupled to it next. To go from carriage 1 to carriage 5, you physically walk through 1→2→3→4→5. You can't teleport to carriage 5 directly (no random access). But uncoupling and re-coupling carriages is fast.

**Browser history:** A doubly linked list. Back button = prev pointer. Forward button = next pointer.

---

## 4. Core Concept

### Node Structure
```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}
```

### Key Operations
| Operation | Singly LL | Doubly LL |
|-----------|-----------|-----------|
| Access by index | O(n) | O(n) |
| Insert at head | O(1) | O(1) |
| Insert at tail | O(1) with tail ptr | O(1) |
| Insert at position i | O(n) to find | O(n) to find |
| Delete head | O(1) | O(1) |
| Delete tail | O(n) — need prev | O(1) with prev ptr |
| Search | O(n) | O(n) |

### Common Pointer Patterns
```
Fast-Slow pointers: fast moves 2, slow moves 1
  → Find middle, detect cycle, find cycle entry

Reverse: prev=null, curr=head
  → Iterate: next=curr.next; curr.next=prev; prev=curr; curr=next

Dummy head: ListNode dummy = new ListNode(0); dummy.next = head
  → Simplifies edge cases (empty list, insert before head)
```

---

## 5. Pattern Recognition Signals

```
"Find middle of linked list" → Fast-Slow pointers
"Detect cycle" → Fast-Slow (Floyd's)
"Reverse linked list" → Three-pointer iterative or recursion
"Merge two sorted lists" → Two pointers + dummy head
"Remove nth from end" → Two pointers with gap of n
"Palindrome linked list" → Find middle + reverse second half
"LRU Cache" → Doubly linked list + HashMap
"Reorder list" → Find middle + reverse + merge
```

---

## 6. Step-by-Step Algorithm

### Reverse Singly Linked List
```
prev = null, curr = head
while curr != null:
    next = curr.next    // save next
    curr.next = prev    // reverse pointer
    prev = curr         // advance prev
    curr = next         // advance curr
return prev             // new head
```

### Detect Cycle (Floyd's)
```
slow = head, fast = head
while fast != null and fast.next != null:
    slow = slow.next
    fast = fast.next.next
    if slow == fast: return true (cycle detected)
return false
```

---

## 7. Dry Run with Example

### Find Middle: 1 → 2 → 3 → 4 → 5
```
slow=1, fast=1
Step 1: slow=2, fast=3
Step 2: slow=3, fast=5
Step 3: fast.next=null → stop
Middle = slow = 3 ✓

For even length: 1 → 2 → 3 → 4
Step 1: slow=2, fast=3
Step 2: slow=3, fast=null → stop (fast.next=null condition first)
Middle = slow = 3 (second of two middles) 

Use `fast != null && fast.next != null` for correct termination.
```

### Merge Two Sorted Lists: [1,3,5] and [2,4,6]
```
dummy → null
curr = dummy
l1=1, l2=2:
  1<2 → take l1=1, curr→1, l1=3
  3>2 → take l2=2, curr→1→2, l2=4
  3<4 → take l1=3, curr→1→2→3, l1=5
  5>4 → take l2=4, curr→1→2→3→4, l2=6
  5<6 → take l1=5, l1=null
  l1=null → attach remaining l2=6
Result: 1→2→3→4→5→6 ✓
```

---

## 8. Code Implementation

```java
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

public class LinkedListAlgorithms {

    // ── Reverse Linked List ────────────────────────────────────────────────
    public ListNode reverse(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode next = curr.next; // save next
            curr.next = prev;          // reverse
            prev = curr;               // move prev forward
            curr = next;               // move curr forward
        }
        return prev; // new head
    }

    // ── Find Middle ───────────────────────────────────────────────────────
    public ListNode findMiddle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow; // for even length: returns second middle
    }

    // ── Detect Cycle (Floyd's) ─────────────────────────────────────────────
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // ── Merge Two Sorted Lists ─────────────────────────────────────────────
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0); // sentinel avoids null checks
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) { curr.next = l1; l1 = l1.next; }
            else { curr.next = l2; l2 = l2.next; }
            curr = curr.next;
        }
        curr.next = (l1 != null) ? l1 : l2; // attach remaining
        return dummy.next;
    }

    // ── Remove Nth Node From End ────────────────────────────────────────────
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy, slow = dummy;
        for (int i = 0; i <= n; i++) fast = fast.next; // advance fast by n+1
        while (fast != null) { slow = slow.next; fast = fast.next; }
        slow.next = slow.next.next; // remove the node
        return dummy.next;
    }

    // ── Check Palindrome ──────────────────────────────────────────────────
    public boolean isPalindrome(ListNode head) {
        ListNode mid = findMiddle(head);
        ListNode secondHalf = reverse(mid);
        ListNode p1 = head, p2 = secondHalf;
        boolean result = true;
        while (p2 != null) {
            if (p1.val != p2.val) { result = false; break; }
            p1 = p1.next; p2 = p2.next;
        }
        reverse(secondHalf); // restore (good practice)
        return result;
    }
}
```

---

## 9. Time Complexity

| Operation | Time | Notes |
|-----------|------|-------|
| Access by index | O(n) | Must traverse from head |
| Insert/Delete at head | O(1) | Just pointer update |
| Insert/Delete at tail | O(1) | With tail pointer; O(n) without |
| Insert/Delete at middle | O(n) | Traversal + O(1) pointer update |
| Reverse | O(n) | Single pass |
| Find middle | O(n) | Fast-slow pointers |
| Detect cycle | O(n) | Floyd's algorithm |
| Merge two sorted | O(m+n) | Process each node once |

---

## 10. Space Complexity

| Algorithm | Space |
|-----------|-------|
| Iterative reverse | O(1) |
| Recursive reverse | O(n) stack |
| Detect cycle | O(1) |
| Find middle | O(1) |
| Merge two lists | O(1) |

---

## 11. Edge Cases

| Scenario | Handling |
|----------|---------|
| Empty list (head = null) | Check null before any operation |
| Single node | Fast-slow: fast.next==null immediately |
| Two nodes | Test merge, palindrome, remove-nth carefully |
| List with cycle | Floyd's will loop forever if null check is missing |
| Odd vs even length | Find-middle behavior differs — test both |

---

## 12. Common Mistakes

```java
// MISTAKE 1: Losing the original next pointer before redirect
curr.next = prev;  // WRONG if you didn't save next yet
ListNode next = curr.next; // CORRECT: save first, then redirect

// MISTAKE 2: Off-by-one in remove-nth-from-end
for (int i = 0; i < n; i++) fast = fast.next; // advances fast by n
// To remove node, slow must stop at the node BEFORE target
// Use dummy head and advance fast by n+1 from dummy

// MISTAKE 3: Forgetting dummy head when deleting could remove head
ListNode dummy = new ListNode(0);
dummy.next = head; // always use dummy for delete operations

// MISTAKE 4: Fast pointer null check order
while (fast.next != null && fast != null) // WRONG: NPE if fast is null
while (fast != null && fast.next != null) // CORRECT: null check first

// MISTAKE 5: Not restoring reversed half after palindrome check
// Always reverse back to restore the original list
```

---

## 13. Interview-Level Explanation

**Q: "Why use a dummy head node?"**

> "A dummy head (sentinel) node simplifies code by eliminating special cases for operations on the head node. For example, when inserting, you don't need to check 'is this the first node?' — the dummy is always before the real head. When removing, you don't need to handle 'removing the head' separately. It makes the logic uniform: every node has a predecessor."

**Q: "How does Floyd's cycle detection work?"**

> "Two pointers start at head. Slow moves one step, fast moves two steps. If there's no cycle, fast reaches null. If there's a cycle, fast will eventually catch slow (they'll meet inside the cycle). Why? Once both are in the cycle, fast is gaining one position per step relative to slow. So they meet in at most 'cycle length' steps."

---

## 14. Real-World Use Cases

| Application | Linked List |
|------------|------------|
| **Browser history** | Doubly linked list (back/forward) |
| **LRU Cache** | Doubly linked list + HashMap |
| **Undo/Redo** | Doubly linked list of states |
| **Memory allocator** | Free list — singly linked |
| **Music playlist** | Circular doubly linked list |
| **Java LinkedList** | `java.util.LinkedList` = doubly linked list |

---

## 15. Variations

| Variation | Technique |
|-----------|----------|
| Doubly linked list | Add `prev` pointer; delete tail O(1) |
| Circular linked list | tail.next = head (cycle) |
| Skip list | Multiple levels of linked lists for O(log n) search |
| XOR linked list | Memory-efficient doubly LL using XOR of addresses |
| Merge k sorted lists | Priority queue (min-heap) of list heads |

---

## 16. Practice Problems

### Easy — Foundation
1. **Reverse Linked List** (LeetCode #206)
   - *Task:* Reverse in-place, iteratively.
   - *Hint:* Three pointers: prev, curr, next.

2. **Merge Two Sorted Lists** (LeetCode #21)
   - *Task:* Merge into one sorted list.
   - *Hint:* Dummy head + two pointers.

3. **Linked List Cycle** (LeetCode #141)
   - *Task:* Detect if cycle exists.
   - *Hint:* Floyd's fast-slow pointers.

### Medium — Core
1. **Remove Nth Node From End** (LeetCode #19)
   - *Task:* One-pass removal.
   - *Hint:* Two pointers with n+1 gap from dummy.

2. **Reorder List** (LeetCode #143)
   - *Task:* L0→L1→L2→...→Ln becomes L0→Ln→L1→Ln-1→...
   - *Hint:* Find middle + reverse second half + merge.

3. **Linked List Cycle II** (LeetCode #142)
   - *Task:* Find cycle entry node.
   - *Hint:* After Floyd's meeting point, move one pointer to head, advance both at speed 1 — they meet at cycle entry.

4. **Sort List** (LeetCode #148)
   - *Task:* Sort in O(n log n) with O(1) space.
   - *Hint:* Merge Sort — find mid, split, merge.

5. **Palindrome Linked List** (LeetCode #234)
   - *Task:* Check palindrome in O(n) time O(1) space.
   - *Hint:* Find middle + reverse second half + compare.

### Hard — Advanced
1. **Merge K Sorted Lists** (LeetCode #23)
   - *Task:* Merge k sorted linked lists.
   - *Hint:* Min-heap of (value, node) pairs, O(n log k).

2. **Reverse Nodes in K-Group** (LeetCode #25)
   - *Task:* Reverse every k nodes.
   - *Hint:* Count k nodes, reverse group, recurse/iterate.

3. **Copy List with Random Pointer** (LeetCode #138)
   - *Task:* Deep copy with random pointers.
   - *Hint:* Three passes: interweave copies, set random, extract.

---

## 17. How to Know You Have Mastered Linked Lists

You have mastered this topic when you can:
- [ ] Reverse a singly linked list iteratively (three-pointer)
- [ ] Find the middle using fast-slow pointers
- [ ] Detect a cycle with Floyd's algorithm
- [ ] Remove the nth node from the end in one pass
- [ ] Use a dummy head node to simplify insertion/deletion
- [ ] Check palindrome in O(n) time O(1) space
- [ ] Implement merge sort on a linked list
- [ ] Have solved all 11 practice problems above

---

## 18. Mini Quiz — Test Yourself

1. Linked list: 1 → 2 → 3 → 4 → 5. After `reverse()`, what is the new head?

2. For `hasCycle`, why does checking `fast != null && fast.next != null` work for both even and odd length lists?

3. What is the dummy head pattern and why is it useful?

4. `removeNthFromEnd(head, 2)` on list [1,2,3,4,5]. Which node is removed? Trace the algorithm.

5. For `findMiddle([1,2,3,4])`, which node does it return — 2 or 3?

6. Merging [1,3,5] and [2,4,6] — how many pointer assignments are made total?

7. To find the cycle entry node after Floyd's meeting, you move one pointer to head and step both at speed 1. Why does this work mathematically?

8. In reverse a linked list, what is the role of the three variables `prev`, `curr`, and `next`?

> **Answers:**
> 1. 5 (new head). The list becomes 5 → 4 → 3 → 2 → 1 → null.
> 2. For even length, fast will land on the last node (not null) and fast.next == null triggers stop. For odd length, fast lands on null. Both conditions are safe.
> 3. Dummy head is a sentinel node before the real head with value 0. It ensures every "real" node has a predecessor, eliminating special cases for deleting/inserting at the head. Return `dummy.next` as the new head.
> 4. Node with value 4 is removed. Fast advances from dummy 3 steps (n+1=3) to reach node 3. Then both advance until fast is null: slow ends at node 3. slow.next = slow.next.next skips node 4. Result: [1,2,3,5].
> 5. Node 3 (second middle). With [1,2,3,4]: slow=1→2→3, fast=1→3→null. Returns 3.
> 6. 6 comparisons + 6 pointer assignments (one per node in result) + 1 to attach remaining = 7. Plus 1 assignment for dummy.next → 8 total (implementation-dependent, but linear O(m+n)).
> 7. Let F = distance from head to cycle entry, C = cycle length, and the meeting point is D steps into the cycle. It can be proven that F = C - D (mod C). So moving one pointer to head and stepping both at speed 1, they both travel F steps and meet at the cycle entry.
> 8. `curr`: the node being processed. `next`: saves curr.next before redirecting (otherwise lost). `prev`: the already-reversed portion (curr.next = prev reverses the link). At end, prev is the new head.

---

**Next →** `04_Stack.md`

Arrays require contiguous memory and shifting for insertions/deletions.  
Linked Lists store elements **anywhere in memory** and connect them with pointers — enabling O(1) insert/delete at known positions.

---

## 2. Beginner-Friendly Intuition

A linked list is like a **treasure hunt**:
- Each clue (node) tells you: "The value is X, and the next clue is at location Y."
- You can't jump to clue #5 directly — you follow the chain.

```
[10 | →] → [20 | →] → [30 | →] → [40 | null]
  head
```

---

## 3. Types of Linked Lists

```
Singly:   A → B → C → null
Doubly:   null ← A ⇄ B ⇄ C → null
Circular: A → B → C → A (loops back)
```

---

## 4. Java Node Implementation

```java
// Singly Linked List Node
class ListNode {
    int val;
    ListNode next;
    ListNode(int val) { this.val = val; }
}

// Doubly Linked List Node
class DNode {
    int val;
    DNode prev, next;
    DNode(int val) { this.val = val; }
}
```

---

## 5. Operations & Complexity

| Operation | Time | Notes |
|-----------|------|-------|
| Access by index | O(n) | Must traverse from head |
| Search | O(n) | Must traverse |
| Insert at head | O(1) | Rewire head pointer |
| Insert at tail | O(n) or O(1) | O(1) if tail pointer kept |
| Insert at position | O(n) | Find node first |
| Delete at head | O(1) | |
| Delete at position | O(n) | Find node first |

---

## 6. Core Operations — Java Code

```java
// Insert at head
ListNode newNode = new ListNode(val);
newNode.next = head;
head = newNode;

// Insert at tail
ListNode curr = head;
while (curr.next != null) curr = curr.next;
curr.next = new ListNode(val);

// Delete a node with given value
if (head.val == val) { head = head.next; return; }
ListNode prev = head;
while (prev.next != null && prev.next.val != val)
    prev = prev.next;
if (prev.next != null) prev.next = prev.next.next;
```

---

## 7. Key Linked List Algorithms

### Reverse a Linked List
```java
ListNode reverse(ListNode head) {
    ListNode prev = null, curr = head;
    while (curr != null) {
        ListNode next = curr.next;  // save next
        curr.next = prev;           // reverse pointer
        prev = curr;                // advance prev
        curr = next;                // advance curr
    }
    return prev;  // new head
}
```

**Dry Run:** 1 → 2 → 3 → null
```
Step 1: prev=null, curr=1
        next=2, 1.next=null, prev=1, curr=2
Step 2: prev=1, curr=2
        next=3, 2.next=1, prev=2, curr=3
Step 3: prev=2, curr=3
        next=null, 3.next=2, prev=3, curr=null
Result: 3 → 2 → 1 → null  (prev=3 is new head)
```

### Find Middle (Fast/Slow Pointers)
```java
ListNode findMiddle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }
    return slow;  // slow is at middle
}
```

**Why it works:** Fast moves 2x speed. When fast reaches end, slow is at middle.

### Detect Cycle (Floyd's Algorithm)
```java
boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;  // cycle!
    }
    return false;
}
```

---

## 8. Dummy Node Technique

Many linked list problems become easier with a **dummy node**:

```java
// Remove all nodes with value = val
ListNode dummy = new ListNode(0);
dummy.next = head;
ListNode curr = dummy;
while (curr.next != null) {
    if (curr.next.val == val)
        curr.next = curr.next.next;  // skip it
    else
        curr = curr.next;
}
return dummy.next;
```

The dummy node prevents special handling of head removal.

---

## 9. vs Array Comparison

| Feature | Array | Linked List |
|---------|-------|-------------|
| Access by index | O(1) | O(n) |
| Insert at middle | O(n) | O(1) if pointer known |
| Delete at middle | O(n) | O(1) if pointer known |
| Memory | Contiguous | Non-contiguous |
| Cache performance | Better | Worse |
| Dynamic size | Resizing needed | Natural |

---

## 10. Edge Cases

- Empty list (head == null)
- Single node list
- Two node list (for reverse, cycle detection)
- Cycle at tail
- All same values

---

## 11. Practice Problems

**Easy:**
1. Reverse a linked list.
2. Find the middle node.
3. Check if linked list has a cycle.

**Medium:**
1. Merge two sorted linked lists.
2. Remove Nth node from end in one pass.
3. Find the start of a cycle (Floyd's).
4. Add two numbers represented as linked lists.
5. Reorder list: L0→Ln→L1→Ln-1→...

**Hard:**
1. Reverse in groups of K.
2. Merge K sorted lists.
3. Clone linked list with random pointer.

---

**Next →** `04_Stack.md`
