/**
 * День 7 — Easy 3/3
 * LeetCode: https://leetcode.com/problems/number-of-1-bits/
 *
 * Верни количество единичных битов в двоичной записи n
 * (также называют Hamming Weight).
 *
 * Пример: n = 11 (1011) → 3
 * n = 128 (10000000) → 1
 *
 * Класс NumberOf1Bits. На LeetCode → Solution.
 * done / pick
 */
public class NumberOf1Bits {

    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            if (n % 2 == 1)
                count++;
            n /= 2;
        }
        return count;
    }

    public static void main(String[] args) {
        NumberOf1Bits x = new NumberOf1Bits();
        System.out.println(x.hammingWeight(11)); // 3
        System.out.println(x.hammingWeight(128)); // 1
        System.out.println(x.hammingWeight(2147483645)); // 30
    }
}
