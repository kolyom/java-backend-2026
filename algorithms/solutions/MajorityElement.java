
import java.util.HashMap;
import java.util.Map;

/**
 * День 5 — Easy 1/5
 * LeetCode: https://leetcode.com/problems/majority-element/
 *
 * Элемент, который встречается больше чем n/2 раз. Верни его.
 * Гарантируется, что такой элемент есть.
 *
 * Пример: [3,2,3] → 3
 * [2,2,1,1,1,2,2] → 2
 *
 * Класс MajorityElement. На LeetCode → Solution.
 * done / pick
 */
public class MajorityElement {

    public int majorityElement(int[] nums) {
        Map<Integer, Integer> countEl = new HashMap<>();
        for (int x : nums) {
            countEl.put(x, countEl.getOrDefault(x, 0) + 1);
        }
        for (Map.Entry<Integer, Integer> e : countEl.entrySet()) {
            if (e.getValue() > nums.length / 2)
                return e.getKey();
        }
        return 0;
    }

    public static void main(String[] args) {
        MajorityElement m = new MajorityElement();
        System.out.println(m.majorityElement(new int[] { 3, 2, 3 })); // 3
        System.out.println(m.majorityElement(new int[] { 2, 2, 1, 1, 1, 2, 2 })); // 2
    }
}
