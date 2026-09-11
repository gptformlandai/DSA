class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}


public class Solution{
    public static void main(String[] args) {

        ListNode head=new ListNode(1);
        head.next=new ListNode(2);
        head.next.next=new ListNode(3);
        head.next.next.next=new ListNode(4);
        head.next.next.next.next=new ListNode(5);
        head.next.next.next.next.next=new ListNode(6);

        int left=2,right=4, count=0;

        // Initialize pointers
        ListNode dummy = new ListNode(0); // Handles cases where left = 1 (reversing the head)
        dummy.next = head;
        ListNode before = dummy;
// Step 1: Move 'before' to the node right before the 'left' position
        while (count < left - 1 && before != null) {
            before = before.next;
            count++;
        }

// 'cur' is the node at the 'left' position
        ListNode cur = before.next;

// Step 2: Reverse the sublist
// If left=2 and right=4, we need to perform exactly (right - left) operations (2 swaps)
        for (int i = 0; i < right - left; i++) {
            ListNode ch = cur.next;     // Identify the node to move to the front
            cur.next = ch.next;         // Bridge cur to the node after ch
            ch.next = before.next;      // Point ch to the current front of the sublist
            before.next = ch;           // Connect before to the new front
        }

// Return the actual head (handles if head itself was reversed)
        printList(dummy.next);
//        printList(head);
    }

    public static void printList(ListNode head){
        while(head!=null){
            System.out.printf(head.val+" -> ");
            head=head.next;
        }
    }


}