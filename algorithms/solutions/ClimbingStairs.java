
/**
 *
 * Задание: Climbing Stairs
 *
 * LeetCode: https://leetcode.com/problems/climbing-stairs/
 *
 *
 *
 * Ты поднимаешься по лестнице из n ступенек.
 *
 * За один шаг можно +1 или +2 ступеньки.
 *
 * Сколько различных способов дойти до верха?
 *
 *
 *
 * Пример: n = 3 → 3 способа: 1+1+1, 1+2, 2+1
 *
 *
 *
 * Подсказка словами (если pick): подумай, сколькими способами можно попасть на ступеньку i,
 *
 * если уже знаешь ответы для i-1 и i-2.
 *
 *
 *
 * Локально класс ClimbingStairs. На LeetCode → Solution.
 *
 * Готово: // done в конце + done в чат.
 *
 */
public class ClimbingStairs {

    public int climbStairs(int n) {

        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return climbStairs(n - 1) + climbStairs(n - 2);

    }

    public static void main(String[] args) {

        ClimbingStairs s = new ClimbingStairs();

        System.out.println(s.climbStairs(5)); // 2

        System.out.println(s.climbStairs(3)); // 3

    }

}