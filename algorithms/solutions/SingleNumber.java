
import java.util.HashMap;
import java.util.Map;

/**
 * Бонус после дня 3
 * LeetCode: https://leetcode.com/problems/single-number/
 *
 * Массив nums: каждое число встречается дважды, кроме одного.
 * Найди это одиночное. (Линейный проход / HashMap ок; XOR — если знаешь.)
 *
 * Пример: [2,2,1] → 1
 * [4,1,2,1,2] → 4
 *
 * Класс SingleNumber. На LeetCode → Solution.
 * done / pick
 */
public class SingleNumber {

    public int singleNumber(int[] nums) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : nums) {
            int i = freq.getOrDefault(n, 0);
            freq.put(n, i + 1);
        }
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            if (e.getValue() == 1)
                return e.getKey();
        }

        return 0;
    }

    public static void main(String[] args) {
        SingleNumber s = new SingleNumber();
        System.out.println(s.singleNumber(new int[] { 2, 2, 1 })); // 1
        System.out.println(s.singleNumber(new int[] { 4, 1, 2, 1, 2 })); // 4
        System.out.println(s.singleNumber(new int[] { 1 })); // 1
    }
}
