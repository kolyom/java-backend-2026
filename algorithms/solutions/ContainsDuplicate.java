import java.util.HashSet;
import java.util.Set;

/**
 * День 4 — Easy 1/5
 * LeetCode: https://leetcode.com/problems/two-sum/ (если уже есть — ок
 * повторить HashMap-версию)
 * Альтернатива если Two Sum уже в репо:
 * https://leetcode.com/problems/contains-duplicate/
 *
 * Повтори Contains Duplicate через HashSet (O(n)), не двумя циклами.
 * true если есть дубликат.
 *
 * Класс ContainsDuplicate. На LeetCode → Solution.
 * done / pick
 */
public class ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int i : nums) {
            if (seen.contains(i))
                return true;
            else
                seen.add(i);
        }
        return false;
    }

    public static void main(String[] args) {
        ContainsDuplicate c = new ContainsDuplicate();
        System.out.println(c.containsDuplicate(new int[] { 1, 2, 3, 1 })); // true
        System.out.println(c.containsDuplicate(new int[] { 1, 2, 3, 4 })); // false
        System.out.println(c.containsDuplicate(new int[] { 1, 1, 1, 3, 3, 4, 3, 2, 4, 2 })); // true
    }
}
