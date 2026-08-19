/**
 * День 10 — Easy 2/3
 * LeetCode: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 *
 * prices[i] — цена акции в день i.
 * Одна покупка и одна продажа после неё (или ничего).
 * Максимальная прибыль. Если прибыль невозможна — 0.
 *
 * Пример: [7,1,5,3,6,4] → 5  (купил за 1, продал за 6)
 *          [7,6,4,3,1] → 0
 *
 * Класс BestTimeToBuyAndSellStock. На LeetCode → Solution.
 * done / pick
 */
public class BestTimeToBuyAndSellStock {

    public int maxProfit(int[] prices) {
        // TODO
        return 0;
    }

    public static void main(String[] args) {
        BestTimeToBuyAndSellStock b = new BestTimeToBuyAndSellStock();
        System.out.println(b.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 5
        System.out.println(b.maxProfit(new int[]{7, 6, 4, 3, 1}));    // 0
    }
}
