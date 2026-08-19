/**
 * День 3 — задача 5/5 (последняя за день)
 * LeetCode: https://leetcode.com/problems/move-zeroes/
 *
 * Дан массив nums. Перенеси все нули в конец, порядок ненулевых сохрани.
 * Меняй массив на месте (можно вернуть void; для проверки в main печатай
 * массив).
 *
 * Пример: [0,1,0,3,12] → [1,3,12,0,0]
 *
 * Идея: два индекса — куда писать следующий ненулевой, и проход по массиву.
 * Класс MoveZeroes. На LeetCode → Solution.
 * done / pick
 */
public class MoveZeroes {

    public void moveZeroes(int[] nums) {
        int temp = nums[0];
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[j] != 0) {
                        temp = nums[j];
                        nums[j] = nums[i];
                        nums[i] = temp;
                        break;
                    }

                }

            }
        }
    }

    public static void main(String[] args) {
        MoveZeroes m = new MoveZeroes();
        int[] a = { 0, 1, 0, 3, 12 };
        m.moveZeroes(a);
        System.out.println(java.util.Arrays.toString(a)); // [1, 3, 12, 0, 0]
        int[] b = { 0 };
        m.moveZeroes(b);
        System.out.println(java.util.Arrays.toString(b)); // [0]
    }
}
