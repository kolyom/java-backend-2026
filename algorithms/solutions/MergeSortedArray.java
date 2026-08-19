/**
 * День 8 — Easy 1/3
 * LeetCode: https://leetcode.com/problems/merge-sorted-array/
 *
 * Даны два отсортированных массива nums1 и nums2.
 * nums1 имеет длину m+n: первые m элементов — данные, хвост — нули (место под
 * merge).
 * Слей nums2 в nums1 на месте. Результат должен быть отсортирован в nums1.
 *
 * Пример: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
 * → nums1 = [1,2,2,3,5,6]
 *
 * Класс MergeSortedArray. На LeetCode → Solution.
 * done / pick
 */
public class MergeSortedArray {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index1 = m - 1;
        int index2 = n - 1;

        for (int i = m + n - 1; i >= 0; i--) {
            if (index1 >= 0 && index2 >= 0) {
                if (nums1[index1] > nums2[index2]) {
                    nums1[i] = nums1[index1];
                    index1--;
                } else {
                    nums1[i] = nums2[index2];
                    index2--;
                }
            } else if (index2 >= 0) {
                nums1[i] = nums2[index2];
                index2--;
            }

        }

    }

    public static void main(String[] args) {
        MergeSortedArray s = new MergeSortedArray();
        int[] nums1 = { 1, 2, 5, 0, 0, 0 };
        s.merge(nums1, 3, new int[] { 2, 5, 6 }, 3);
        for (int x : nums1) {
            System.out.print(x + " ");
        }
        // ожидается: 1 2 2 3 5 6
        System.out.println();
    }
}
