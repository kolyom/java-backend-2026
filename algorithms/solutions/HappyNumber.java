
import java.util.HashSet;
import java.util.Set;

/**
 * День 4 — Easy 4/5
 * LeetCode: https://leetcode.com/problems/happy-number/
 *
 * Число счастливое, если заменяя его суммой квадратов цифр
 * рано или поздно получишь 1.
 * Если зациклишься и никогда не получишь 1 — не счастливое.
 *
 * Пример: 19 → true
 * 1^2 + 9^2 = 82
 * 8^2 + 2^2 = 68
 * ...
 * → 1
 * Пример: 2 → false
 *
 * Класс HappyNumber. На LeetCode → Solution.
 * done / pick
 */
public class HappyNumber {

    public boolean isHappy(int n) {
        Set<Integer> seen = new HashSet<>();
        int sum;
        while (!seen.contains(n)) {
            seen.add(n);
            sum = 0;
            while (n != 0) {
                int cur = n % 10;
                sum += cur * cur;
                n /= 10;
            }
            if (sum == 1)
                return true;
            else {
                n = sum;
            }
        }
        return false;

    }

    public static void main(String[] args) {
        HappyNumber h = new HappyNumber();
        System.out.println(h.isHappy(19)); // true
        System.out.println(h.isHappy(2)); // false
    }
}
