
import java.util.HashMap;
import java.util.Map;

/**
 * День 4 — Easy 5/5 (последняя за день)
 * LeetCode: https://leetcode.com/problems/ransom-note/
 *
 * Даны две строки: ransomNote и magazine.
 * Верни true, если ransomNote можно собрать из букв magazine
 * (каждую букву magazine можно использовать не чаще, чем она там встречается).
 *
 * Пример: ransomNote = "a", magazine = "b" → false
 * ransomNote = "aa", magazine = "ab" → false
 * ransomNote = "aa", magazine = "aab" → true
 *
 * Класс RansomNote. На LeetCode → Solution.
 * done / pick
 */
public class RansomNote {

    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> ransom = new HashMap<>();
        Map<Character, Integer> mag = new HashMap<>();
        for (char c : ransomNote.toCharArray()) {
            ransom.put(c, ransom.getOrDefault(c, 0) + 1);
        }
        for (char c : magazine.toCharArray()) {
            mag.put(c, mag.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> e : ransom.entrySet()) {
            char c = e.getKey();
            int need = e.getValue();
            int have = mag.getOrDefault(c, 0);
            if (need > have)
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        RansomNote r = new RansomNote();
        System.out.println(r.canConstruct("a", "b")); // false
        System.out.println(r.canConstruct("aa", "ab")); // false
        System.out.println(r.canConstruct("aa", "aab")); // true
    }
}
// TODO