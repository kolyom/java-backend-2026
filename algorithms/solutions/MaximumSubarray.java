/**
 * Задание: Maximum Subarray (остаток дня 2)
 * LeetCode: https://leetcode.com/problems/maximum-subarray/
 *
 * Дан массив nums. Найди непрерывный подотрезок с максимальной суммой.
 * Верни эту сумму.
 *
 * Пример: [-2,1,-3,4,-1,2,1,-5,4] → 6 (подотрезок [4,-1,2,1])
 *
 * Можно brute force двумя циклами (для дня 2 достаточно).
 * Локально класс MaximumSubarray. На LeetCode → Solution.
 *
 * done / pick. Готовых решений нет.
 */
public class MaximumSubarray {

    public int maxSubArray(int[] nums) {
        int sum = 0;
        int maxsum = nums[0];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                if (maxsum < sum)
                    maxsum = sum;
            }
            sum = 0;
        }
        return maxsum;
    }

    public static void main(String[] args) {
        MaximumSubarray s = new MaximumSubarray();
        System.out.println(s.maxSubArray(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 })); // 6
        System.out.println(s.maxSubArray(new int[] { 1 })); // 1
        System.out.println(s.maxSubArray(new int[] { 5, 4, -1, 7, 8 })); // 23
    }
}
