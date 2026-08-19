/**
 * День 7 — Easy 2/3
 * LeetCode: https://leetcode.com/problems/valid-perfect-square/
 *
 * Верни true, если num — полный квадрат (1, 4, 9, 16, ...).
 * Иначе false.
 * Не используй библиотечный sqrt в решении (для тренировки).
 *
 * Пример: 16 → true, 14 → false, 1 → true
 *
 * Класс ValidPerfectSquare. На LeetCode → Solution.
 * done / pick
 */
public class ValidPerfectSquare {

    public boolean isPerfectSquare(int num) {
        if (num == 1)
            return true;
        for (int i = 1; i <= num / 2; i++) {
            if (i * i > num)
                return false;
            if (i * i == num)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        ValidPerfectSquare v = new ValidPerfectSquare();
        System.out.println(v.isPerfectSquare(4)); // true
        System.out.println(v.isPerfectSquare(14)); // false
        System.out.println(v.isPerfectSquare(1)); // true
        System.out.println(v.isPerfectSquare(2147483647)); // false
    }
}
