import java.util.HashSet;
import java.util.Set;

/**
 * День 10 — Easy 3/3
 * LeetCode: https://leetcode.com/problems/linked-list-cycle/
 *
 * Дан head односвязного списка. Есть ли цикл?
 * (какой-то next указывает на уже посещённый узел)
 *
 * Следуй определению ListNode ниже (как на LeetCode).
 * Класс LinkedListCycle. На LeetCode → Solution.
 * done / pick
 */
public class LinkedListCycle {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        ListNode cur = head;
        Set<ListNode> hashSet = new HashSet<>();
        if (cur.next == null)
            return false;
        while (cur != null) {
            if (!hashSet.contains(cur)) {
                hashSet.add(cur);
                cur = cur.next;
            } else {
                return true;
            }

        }
        return false;
    }

    public static void main(String[] args) {
        // 3 → 2 → 0 → -4 → обратно на 2 → true
        ListNode n0 = new ListNode(3);
        ListNode n1 = new ListNode(2);
        ListNode n2 = new ListNode(0);
        ListNode n3 = new ListNode(-4);
        n0.next = n1;
        n1.next = n2;
        n2.next = n3;
        n3.next = n1;

        LinkedListCycle s = new LinkedListCycle();
        System.out.println(s.hasCycle(n0)); // true

        // 1 → 2 → null → false
        ListNode a = new ListNode(1);
        ListNode b = new ListNode(2);
        a.next = b;
        System.out.println(s.hasCycle(a)); // false
    }
}
