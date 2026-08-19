/**
 * Задание: понять связный список + Reverse Linked List
 * LeetCode: https://leetcode.com/problems/reverse-linked-list/
 *
 * Сначала прочитай docs/theory/03-linked-list-intro.md (коротко).
 * Потом реализуй разворот списка.
 *
 * Локально класс ReverseLinkedList. На LeetCode → Solution.
 * Готово: // done в конце файла + done в чат.
 * Подсказка: pick (только словами).
 */
public class ReverseLinkedList {

    // Узел списка (как на LeetCode)
    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    /** Печать для проверки: 1->2->3->null */
    static void print(ListNode head) {
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + "->");
            cur = cur.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // Список 1 -> 2 -> 3
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3)));
        print(head);
        ReverseLinkedList s = new ReverseLinkedList();
        ListNode rev = s.reverseList(head);
        print(rev); // ожидаешь 3->2->1->null
    }
}
