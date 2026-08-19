/**
 * День 3 — задача 3/5
 * LeetCode: https://leetcode.com/problems/length-of-last-word/
 *
 * Дана строка s из слов и пробелов.
 * Верни длину последнего слова.
 * Слово = max последовательность символов без пробела.
 *
 * Пример: "Hello World" → 5
 * " fly me to the moon " → 4
 *
 * Класс LengthOfLastWord. На LeetCode → Solution.
 * done / pick
 */
public class LengthOfLastWord {

    public int lengthOfLastWord(String s) {
        if (s.length() == 0)
            return 0;
        int i = s.length() - 1;
        int count = 0;
        while (Character.isWhitespace(s.charAt(i)))
            i--;
        while (i >= 0 && !Character.isWhitespace(s.charAt(i))) {
            i--;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        LengthOfLastWord t = new LengthOfLastWord();
        System.out.println(t.lengthOfLastWord("")); // 5
        System.out.println(t.lengthOfLastWord("   fly me   to   the moon  ")); // 4
        System.out.println(t.lengthOfLastWord("luffy is still joyboy")); // 6
    }
}
