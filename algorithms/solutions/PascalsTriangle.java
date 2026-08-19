
/**
 * День 9 — Easy 3/3
 * LeetCode: https://leetcode.com/problems/pascals-triangle/
 *
 * Построй первые numRows рядов треугольника Паскаля.
 * Каждый элемент = сумма двух чисел над ним (с краёв — 1).
 *
 * Пример numRows = 5:
 *      [1]
 *     [1,1]
 *    [1,2,1]
 *   [1,3,3,1]
 *  [1,4,6,4,1]
 *
 * Класс PascalsTriangle. На LeetCode → Solution.
 * done / pick
 */
import java.util.ArrayList;
import java.util.List;

public class PascalsTriangle {

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> row = new ArrayList<>();
        row.add(1);
        result.add(row);
        if (numRows == 1)
            return result;

        for (int i = 1; i < numRows; i++) {

            row = new ArrayList<>();
            row.add(1);
            for (int j = 1; j < result.get(i - 1).size(); j++) {
                row.add(j, result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
            }
            row.add(1);
            result.add(row);

        }

        return result;
    }

    public static void main(String[] args) {
        PascalsTriangle p = new PascalsTriangle();
        System.out.println(p.generate(5));
        // ожидается: [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1]]
    }
}
