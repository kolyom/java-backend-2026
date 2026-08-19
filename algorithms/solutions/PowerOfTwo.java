/**
 * День 6 — Easy 3/3
 * LeetCode: https://leetcode.com/problems/power-of-two/
 *
 * Верни true, если n — степень двойки (1, 2, 4, 8, 16, ...).
 * Иначе false.
 *
 * Пример: 1 → true, 16 → true, 3 → false, 0 → false, -1 → false
 *
 * Класс PowerOfTwo. На LeetCode → Solution.
 * done / pick
 */
public class PowerOfTwo {

    public boolean isPowerOfTwo(int n) {

        while (n > 1 && n % 2 == 0) {
            n /= 2;
        }
        if (n == 1)
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        PowerOfTwo p = new PowerOfTwo();
        System.out.println(p.isPowerOfTwo(1)); // true
        System.out.println(p.isPowerOfTwo(16)); // true
        System.out.println(p.isPowerOfTwo(3)); // false
        System.out.println(p.isPowerOfTwo(0)); // false
    }
}
