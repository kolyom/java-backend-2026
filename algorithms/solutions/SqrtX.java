/**
 * День 9 — Easy 1/3
 * LeetCode: https://leetcode.com/problems/sqrtx/
 *
 * Верни целый квадратный корень x (округление вниз).
 * Не используй Math.sqrt в решении (для тренировки).
 *
 * Пример: 4 → 2, 8 → 2 (потому что 2*2=4 <= 8, 3*3=9 > 8)
 *
 * Класс SqrtX. На LeetCode → Solution.
 * done / pick
 */
public class SqrtX {

    public int mySqrt(int x) {
        for (int i = 1; i <= x; i++) {
            if (i * i == x)
                return i;
            else if (i * i <= x && (i + 1) * (i + 1) > x)
                return i;
        }
        return 0;
    }

    public static void main(String[] args) {
        SqrtX s = new SqrtX();
        // System.out.println(s.mySqrt(4)); // 2
        System.out.println(s.mySqrt(8)); // 2
        System.out.println(s.mySqrt(9)); // 0
        System.out.println(s.mySqrt(1)); // 1
    }
}
