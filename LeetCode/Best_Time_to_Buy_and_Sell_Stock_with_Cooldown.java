/* Problem Statement
Say you have an array for which the ith element is the price of a given stock on day i.

Design an algorithm to find the maximum profit. You may complete as many transactions as
you like (ie, buy one and sell one share of the stock multiple times) with the following
restrictions:

You may not engage in multiple transactions at the same time (ie, you must sell the stock
before you buy again).
After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
Example:

prices = [1, 2, 3, 0, 2]
maxProfit = 3
transactions = [buy, sell, cooldown, buy, sell]
*/

/** Solution
 * Key: three states of stock: buy, cooldown, sell
 * If we want to buy, then i-1 day must be cooldown, so after buy today our portfolio
 value should be cooldown-prices[i]. if this number is small than buy itself, then we
 don't buy today.
 * to cooldown, the previous state could be either buy, cd, or sell
 * If we want to sell, then before day i, we must have position, so after sell our
 portfolio value should be day i-1's buy+prices[i]. if this value is smaller than sell
 itself, then we don't sell today.

 * At each day, use buy, sell, and cd to represent:
 Max profit till index i. The series of transaction is ending with a buy, sell, or cd
 */

 public class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length<2) return 0;
        int buy = -prices[0], sell = 0, cooldown = 0;
        for(int i=1; i<prices.length; i++) {
            int temp = buy;
            buy = Math.max(buy, cooldown-prices[i]);
            cooldown = Math.max(sell, Math.max(buy,cooldown));
            sell = Math.max(sell, temp+prices[i]);
        }
        return sell>cooldown?sell:cooldown;
    }
}
