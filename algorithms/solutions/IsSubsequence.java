/**
 * День 7 — Easy 1/3
 * LeetCode: https://leetcode.com/problems/is-subsequence/
 *
 * Верни true, если s является подпоследовательностью t.
 * Подпоследовательность = s получается из t удалением части символов
 * без изменения порядка оставшихся.
 *
 * Пример: s = "abc", t = "ahbgdc" → true
 * s = "axc", t = "ahbgdc" → false
 *
 * Класс IsSubsequence. На LeetCode → Solution.
 * done / pick
 */
public class IsSubsequence {

    public boolean isSubsequence(String s, String t) {
        int i = 0;
        if (s.isEmpty())
            return true;
        for (char c : t.toCharArray()) {
            if (i == s.length())
                break;
            if (s.charAt(i) == c)
                i++;
        }
        if (i == s.length())
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        IsSubsequence x = new IsSubsequence();
        System.out.println(x.isSubsequence("abc", "ahbgdc")); // true
        System.out.println(x.isSubsequence("axc", "ahbgdc")); // false
        System.out.println(x.isSubsequence("", "ahbgdc")); // true
    }
}
