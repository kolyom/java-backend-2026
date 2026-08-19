
import java.util.HashMap;
import java.util.Map;

/**
 * День 6 — Easy 1/3
 * LeetCode: https://leetcode.com/problems/roman-to-integer/
 *
 * Римское число → int.
 * Пример: "III" → 3, "IV" → 4, "IX" → 9, "LVIII" → 58
 *
 * Класс RomanToInteger. На LeetCode → Solution.
 * done / pick
 */
public class RomanToInteger {

    public int romanToInt(String s) {
        Map<Character, Integer> rimNum = new HashMap<>();
        rimNum.put('I', 1);
        rimNum.put('V', 5);
        rimNum.put('X', 10);

        rimNum.put('L', 50);

        rimNum.put('C', 100);

        rimNum.put('D', 500);

        rimNum.put('M', 1000);
        int sum = 0;
        for (int i = 0; i <= s.length() - 1; i++) {
            char c1 = s.charAt(i);
            if (i + 1 < s.length() && rimNum.get(c1) < rimNum.get(s.charAt(i + 1))) {

                sum -= rimNum.get(c1);
            } else
                sum += rimNum.get(c1);

        }
        return sum;
    }

    public static void main(String[] args) {
        RomanToInteger r = new RomanToInteger();
        System.out.println(r.romanToInt("III")); // 3
        System.out.println(r.romanToInt("IV")); // 4
        System.out.println(r.romanToInt("IX")); // 9
        System.out.println(r.romanToInt("LVIII")); // 58
    }
}
