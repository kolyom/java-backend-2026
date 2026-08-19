
/**
 * День 5 — Easy 2/3
 * LeetCode: https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 *
 * Массив nums отсортирован по возрастанию.
 * Убери дубликаты НА МЕСТЕ (in-place).
 * Верни новую длину k — первые k элементов nums должны быть уникальными.
 *
 * Пример: [1,1,2] → вернуть 2, массив станет [1,2,_]
 * [0,0,1,1,1,2,2,3,3,4] → вернуть 5, массив [0,1,2,3,4,_...]
 *
 * Класс RemoveDuplicatesFromSortedArray. На LeetCode → Solution.
 * done / pick
 */
public class RemoveDuplicatesFromSortedArray {

    public int removeDuplicates(int[] nums) {
        int write = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1])
                continue;
            else {
                nums[write] = nums[i];
                write++;
            }
        }
        return write;
    }

    public static void main(String[] args) {
        RemoveDuplicatesFromSortedArray s = new RemoveDuplicatesFromSortedArray();

        int[] a = { 1, 1, 2 };
        int k1 = s.removeDuplicates(a);
        System.out.println(k1); // 2
        for (int i = 0; i < k1; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();

        int[] b = { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };
        int k2 = s.removeDuplicates(b);
        System.out.println(k2); // 5
        for (int i = 0; i < k2; i++) {
            System.out.print(b[i] + " ");
        }
        System.out.println();
    }
}
