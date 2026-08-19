/**
 * День 8 — Easy 2/3
 * LeetCode: https://leetcode.com/problems/remove-element/
 *
 * Убери из nums все вхождения val на месте.
 * Верни новую длину k — первые k элементов nums не содержат val.
 * Порядок оставшихся может измениться; хвост не важен.
 *
 * Пример: nums = [3,2,2,3], val = 3 → k = 2, nums начинается с [2,2,...]
 * nums = [0,1,2,2,3,0,4,2], val = 2 → k = 5
 *
 * Класс RemoveElement. На LeetCode → Solution.
 * done / pick
 */
public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        int index = 0;
        int count = 0;
        /*
         * индекс val на который будет поставлен первый !val элемент
         * идем циклом по оставшемуся массиву пока не найдет !val элемент
         * ставим !val элемент на место индекса
         */
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == val) {
                index = i;
                int j = i;
                while (j < nums.length - 1 && nums[j] == val)
                    j++;
                nums[i] = nums[j];

            } else
                count++;
        }
        return count;
    }

    public static void main(String[] args) {
        RemoveElement r = new RemoveElement();

        int[] a = { 3, 2, 2, 3 };
        int k1 = r.removeElement(a, 3);
        System.out.println(k1); // 2
        for (int i = 0; i < k1; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        int[] b = { 0, 1, 2, 2, 3, 0, 4, 2 };
        int k2 = r.removeElement(b, 2);
        System.out.println(k2); // 5
    }
}
