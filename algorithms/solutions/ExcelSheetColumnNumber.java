/**
 * День 9 — Easy 2/3
 * LeetCode: https://leetcode.com/problems/excel-sheet-column-number/
 *
 * Заголовок столбца Excel → номер.
 * A → 1, B → 2, ..., Z → 26, AA → 27, AB → 28
 *
 * Класс ExcelSheetColumnNumber. На LeetCode → Solution.
 * done / pick
 */
public class ExcelSheetColumnNumber {

    public int titleToNumber(String columnTitle) {
        int sum = 0;
        if (columnTitle.length() == 1)
            return columnTitle.charAt(0) - 'A' + 1;
        else
            sum += columnTitle.charAt(0) - 'A' + 1;
        for (int i = 1; i < columnTitle.length(); i++) {
            sum *= 26;
            sum += columnTitle.charAt(i) - 'A' + 1;
        }
        return sum;
    }

    public static void main(String[] args) {
        ExcelSheetColumnNumber e = new ExcelSheetColumnNumber();
        // System.out.println(e.titleToNumber("A")); // 1
        System.out.println(e.titleToNumber("AA")); // 28
        System.out.println(e.titleToNumber("ZY")); // 701
    }
}
