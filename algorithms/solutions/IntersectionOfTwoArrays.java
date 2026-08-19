
import java.util.HashSet;
import java.util.Set;

/**
 * День 4 — Easy 3/5
 * LeetCode: https://leetcode.com/problems/intersection-of-two-arrays/
 *
 * Даны два массива nums1 и nums2.
 * Верни пересечение: числа, которые есть в обоих (каждый такой число — один раз
 * в ответе).
 * Порядок в ответе любой.
 *
 * Пример: nums1 = [1,2,2,1], nums2 = [2,2] → [2]
 * nums1 = [4,9,5], nums2 = [9,4,9,8,4] → [9,4] (или [4,9])
 *
 * Класс IntersectionOfTwoArrays. На LeetCode → Solution.
 * done / pick
 */
public class IntersectionOfTwoArrays {

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> n1 = new HashSet<>();
        Set<Integer> n2 = new HashSet<>();
        Set<Integer> n3 = new HashSet<>();

        for (int i : nums1) {
            n1.add(i);
        }
        for (int i : nums2) {
            n2.add(i);
        }

        for (int x : n1) {
            if (n2.contains(x))
                n3.add(x);
        }
        int[] result = n3.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        return result;
    }

    public static void main(String[] args) {
        IntersectionOfTwoArrays t = new IntersectionOfTwoArrays();
        System.out.println(java.util.Arrays.toString(
                t.intersection(new int[] { 1, 2, 2, 1 }, new int[] { 2, 2 }))); // [2]
        System.out.println(java.util.Arrays.toString(
                t.intersection(new int[] { 4, 9, 5 }, new int[] { 9, 4, 9, 8, 4 }))); // [4, 9] в любом порядке
    }
}
