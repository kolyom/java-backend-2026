/**
 * День 3 — задача 2/5
 * LeetCode: https://leetcode.com/problems/Longest-common-prefix/
 *
 * Массив строк. Найди самый длинный общий префикс.
 * Если общего нет — верни "".
 *
 * Пример: ["flower","flow","flight"] → "fl"
 * ["dog","racecar","car"] → ""
 *
 * Идея словами: можно взять первую строку и укорачивать / идти по символам,
 * пока все строки совпадают в этой позиции.
 *
 * Класс LongestCommonPrefix. На LeetCode → Solution.
 * done / pick
 */
public class LongestCommonPrefix {

    public String LongestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";
        String first = strs[0];
        if (first.isEmpty())
            return "";
        for (int i = 0; i < first.length(); i++) {
            char c = first.charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if ((strs[j].length() <= i) || strs[j].charAt(i) != c)
                    return first.substring(0, i);
            }
        }
        return first;

    }

    public static void main(String[] args) {
        LongestCommonPrefix s = new LongestCommonPrefix();
        System.out.println(s.LongestCommonPrefix(new String[] { "flower", "flow", "flight" })); // fl
        System.out.println(s.LongestCommonPrefix(new String[] { "dog", "racecar", "car" })); // (пусто)
        System.out.println(s.LongestCommonPrefix(new String[] { "a" })); // a
    }
}
