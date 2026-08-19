
/**
 * День 4 — Easy 2/5
 * LeetCode: https://leetcode.com/problems/missing-number/
 *
 * Массив nums длины n: содержит числа из диапазона [0, n], но одно число пропущено.
 * Верни пропущенное.
 *
 * Пример: [3,0,1] → 2
 *         [0,1] → 2
 *         [9,6,4,2,3,5,7,0,1] → 8
 *
 * Класс MissingNumber. На LeetCode → Solution.
 * done / pick
 */
import java.util.Arrays;

public class MissingNumber {

    public int missingNumber(int[] nums) {
        int maxsum = ((nums.length * (nums.length + 1)) / 2);
        int sum = Arrays.stream(nums).sum();

        return maxsum - sum;
    }

    public static void main(String[] args) {
        MissingNumber m = new MissingNumber();
        System.out.println(m.missingNumber(new int[] { 3, 0, 1 })); // 2
        System.out.println(m.missingNumber(new int[] { 0, 1 })); // 2
        System.out.println(m.missingNumber(new int[] { 9, 6, 4, 2, 3, 5, 7, 0, 1 })); // 8
    }
}
