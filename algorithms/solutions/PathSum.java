/**
 * День 13 — деревья 3/3
 * LeetCode: https://leetcode.com/problems/path-sum/
 *
 * Есть ли путь root → лист, сумма val на пути = targetSum?
 *
 * Класс PathSum. На LeetCode → Solution.
 * done / pick
 */
public class PathSum {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        return helper(root, targetSum, 0);
    }

    private boolean helper(TreeNode root, int targetSum, int sum) {
        if (root == null)
            return false;
        else {
            sum += root.val;
        }
        if (root.left == null && root.right == null) {
            return sum == targetSum;
        } else {
            return helper(root.left, targetSum, sum) || helper(root.right, targetSum, sum);
        }
    }

    public static void main(String[] args) {
        // 5
        // / \
        // 4 8
        // / / \
        // 11 13 4
        // / \ \
        // 7 2 1
        TreeNode root = new TreeNode(5,
                new TreeNode(4, new TreeNode(11, new TreeNode(7), new TreeNode(2)), null),
                new TreeNode(8, new TreeNode(13), new TreeNode(4, null, new TreeNode(1))));
        PathSum s = new PathSum();
        System.out.println(s.hasPathSum(root, 22)); // true (5+4+11+2)
        System.out.println(s.hasPathSum(root, 26)); // true (5+8+13) — проверь сам
        System.out.println(s.hasPathSum(null, 0)); // false
    }
}
