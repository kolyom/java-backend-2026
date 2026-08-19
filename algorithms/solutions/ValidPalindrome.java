/**
 * День 3 — задача 1/5
 * LeetCode: https://leetcode.com/problems/valid-palindrome/
 *
 * Строка — палиндром, если после удаления всех не-буквенно-цифровых
 * символов и приведения к одному регистру читается одинаково слева и справа.
 *
 * Пример: "A man, a plan, a canal: Panama" → true
 * "race a car" → false
 *
 * Подсказка словами при pick: два индекса с краёв к центру.
 * Класс ValidPalindrome. На LeetCode → Solution.
 * done / pick
 */
public class ValidPalindrome {

    public boolean isPalindrome(String s) {
        // TODO
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            char c1 = s.charAt(left);
            char c2 = s.charAt(right);
            if (!Character.isLetterOrDigit(c1)) {
                left++;
                continue;
            }
            if (!Character.isLetterOrDigit(c2)) {
                right--;
                continue;
            }
            if ((Character.isLetterOrDigit(c1) && Character.isLetterOrDigit(c2))
                    && Character.toLowerCase(c1) == Character.toLowerCase(c2)) {
                left++;
                right--;
            } else
                return false;

        }
        return true;
    }

    public static void main(String[] args) {
        ValidPalindrome v = new ValidPalindrome();
        System.out.println(v.isPalindrome("A man, a plan, a canal: Panama")); // true
        System.out.println(v.isPalindrome("race a car")); // false
        System.out.println(v.isPalindrome(" ")); // true
    }
}
