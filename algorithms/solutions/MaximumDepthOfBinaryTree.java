/**
 * День 11 — Easy 2/3
 * LeetCode: https://leetcode.com/problems/maximum-depth-of-binary-tree/
 *
 * Глубина дерева = число узлов на самом длинном пути от корня до листа.
 * Пустое дерево → 0.
 *
 * Пример:
 * 3
 * / \
 * 9 20
 * / \
 * 15 7
 * ответ: 3
 *
 * Подсказка: рекурсия — max(глубина слева, глубина справа) + 1
 * Класс MaximumDepthOfBinaryTree. На LeetCode → Solution.
 * done / pick
 */
public class MaximumDepthOfBinaryTree {

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

    public int maxDepth(TreeNode root) {
        if (root == null)
            return 0;
        else
            return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        MaximumDepthOfBinaryTree s = new MaximumDepthOfBinaryTree();
        System.out.println(s.maxDepth(root)); // 3
        System.out.println(s.maxDepth(null)); // 0
    }
}
