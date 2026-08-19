
/**
 * День 11 — Easy 1/3
 * LeetCode: https://leetcode.com/problems/merge-two-sorted-lists/
 *
 * Два отсортированных связных списка → один отсортированный.
 * Узлы перецепляй (те же объекты), не создавай новые с теми же числами.
 *
 * Теория: docs/theory/04-merge-two-lists.md
 *
 * Класс MergeTwoSortedLists. На LeetCode → Solution.
 * done / pick
 */
public class MergeTwoSortedLists {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode cur1 = list1;
        ListNode cur2 = list2;
        ListNode dummy = list1;
        if (cur1 == null && cur2 == null)
            return null;
        else if (cur1 == null)
            return list2;
        else if (cur2 == null)
            return list1;
        if (cur1.val < cur2.val) {
            dummy = cur1;
            cur1 = cur1.next;
        } else {
            dummy = cur2;
            cur2 = cur2.next;
        }
        ListNode tail = dummy;
        while (cur1 != null && cur2 != null) {
            if (cur1.val <= cur2.val) {
                tail.next = cur1;
                tail = cur1;
                cur1 = cur1.next;
            } else {
                tail.next = cur2;
                tail = cur2;
                cur2 = cur2.next;
            }
        }
        if (cur2 != null) {
            tail.next = cur2;
            tail = cur2;
            cur2 = cur2.next;

        } else {

            tail.next = cur1;
            tail = cur1;
            cur1 = cur1.next;

        }
        return dummy;
    }

    static void print(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + "->");
            cur = cur.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // 1->2->4 и 1->3->4 → 1->1->2->3->4->4
        ListNode a = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode b = new ListNode(1, new ListNode(3, new ListNode(4)));
        MergeTwoSortedLists s = new MergeTwoSortedLists();
        print(s.mergeTwoLists(a, b));
    }
}
