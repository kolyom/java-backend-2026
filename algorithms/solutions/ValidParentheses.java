/**
 * День 10 — Easy 1/3
 * LeetCode: https://leetcode.com/problems/valid-parentheses/
 *
 * Строка из скобок '()[]{}' — корректна ли?
 * Открывающая должна закрываться той же парой, в правильном порядке.
 *
 * Пример: "()[]{}" → true, "(]" → false, "([)]" → false, "{[]}" → true
 *
 * Подсказка по идее: стек (Deques / Stack).
 * Класс ValidParentheses. На LeetCode → Solution.
 * done / pick
 */
public class ValidParentheses {

    public boolean isValid(String s) {
        // TODO
        return false;
    }

    public static void main(String[] args) {
        ValidParentheses v = new ValidParentheses();
        System.out.println(v.isValid("()"));       // true
        System.out.println(v.isValid("()[]{}"));   // true
        System.out.println(v.isValid("(]"));       // false
        System.out.println(v.isValid("([)]"));     // false
        System.out.println(v.isValid("{[]}"));     // true
    }
}
