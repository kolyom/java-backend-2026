/**
 * День 6 — Easy 2/3
 * LeetCode:
 * https://leetcode.com/problems/find-the-index-of-the-first-occurrence-in-a-string/
 *
 * haystack = строка, needle = подстрока.
 * Верни индекс первого вхождения needle в haystack.
 * Если нет — верни -1.
 *
 * Пример: haystack = "sadbutsad", needle = "sad" → 0
 * haystack = "leetcode", needle = "leeto" → -1
 *
 * Класс ImplementStrStr. На LeetCode → Solution.
 * done / pick
 */
public class ImplementStrStr {

    public int strStr(String haystack, String needle) {
        boolean flag = false;
        int index = -1;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                flag = true;
                index = i;
                for (int j = 0; j < needle.length(); j++) {
                    if (i + j < haystack.length()) {
                        if (haystack.charAt(i + j) != needle.charAt(j)) {
                            flag = false;
                            break;
                        }
                    } else {
                        flag = false;
                        break;
                    }

                }
                if (flag)
                    return index;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        ImplementStrStr s = new ImplementStrStr();
        System.out.println(s.strStr("leetcode", "leeto")); // -1
        System.out.println(s.strStr("hello", "ll")); // 2
    }
}
