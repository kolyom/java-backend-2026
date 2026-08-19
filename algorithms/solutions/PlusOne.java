/**
 * День 3 — задача 4/5
 * LeetCode: https://leetcode.com/problems/plus-one/
 *
 * Дан массив цифр, изображающий число (digits[0] — старший разряд).
 * Прибавь 1 к числу, верни массив цифр результата.
 *
 * Пример: [1,2,3] → [1,2,4]
 * [9] → [1,0]
 * [9,9] → [1,0,0]
 *
 * Идея: иди с конца, пока девятки; если всё девятки — новый массив длиннее.
 * Класс PlusOne. На LeetCode → Solution.
 * done / pick
 */
public class PlusOne {

    public int[] plusOne(int[] digits) {
        int i = digits.length - 1;
        while (i >= 0 && digits[i] == 9) {
            i--;
        }
        if (i < 0) {
            int[] newDigits = new int[digits.length + 1];
            newDigits[0] = 1;
            return newDigits;
        }

        if (i >= 0) {
            digits[i]++;
            if (i < digits.length - 1) {
                for (int j = i + 1; j < digits.length; j++)
                    digits[j] = 0;
            }

        }

        return digits;
    }

    public static void main(String[] args) {
        PlusOne p = new PlusOne();
        System.out.println(java.util.Arrays.toString(p.plusOne(new int[] { 1, 2, 3 }))); // [1, 2, 4]
        System.out.println(java.util.Arrays.toString(p.plusOne(new int[] { 9 }))); // [1, 0]
        System.out.println(java.util.Arrays.toString(p.plusOne(new int[] { 9, 9 }))); // [1, 0, 0]
    }
}
