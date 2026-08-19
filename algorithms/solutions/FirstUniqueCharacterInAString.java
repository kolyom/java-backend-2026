
import java.util.HashMap;
import java.util.Map;

/**
 * День 5 — Easy 3/3
 * LeetCode: https://leetcode.com/problems/first-unique-character-in-a-string/
 *
 * Найди индекс первого символа, который встречается в строке ровно 1 раз.
 * Если такого нет — верни -1.
 *
 * Пример: "leetcode" → 0 (буква 'l')
 * "loveleetcode" → 2 (буква 'v')
 * "aabb" → -1
 *
 * Класс FirstUniqueCharacterInAString. На LeetCode → Solution.
 * done / pick
 */
public class FirstUniqueCharacterInAString {

    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            char key = s.charAt(i);
            int value = map.get(key);
            if (value == 1)
                return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        FirstUniqueCharacterInAString f = new FirstUniqueCharacterInAString();
        System.out.println(f.firstUniqChar("leetcode")); // 0
        System.out.println(f.firstUniqChar("loveleetcode")); // 2
        System.out.println(f.firstUniqChar("aabb")); // -1
    }
}
