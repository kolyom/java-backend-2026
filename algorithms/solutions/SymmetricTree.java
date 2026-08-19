/**
 * День 13 — деревья 2/3
 * LeetCode: https://leetcode.com/problems/symmetric-tree/
 *
 * Дерево симметрично, если левое и правое поддеревья — зеркала друг друга.
 *
 * 1
 * / \
 * 2 2
 * / \ / \
 * 3 4 4 3 → true
 *
 * 1
 * / \
 * 2 2
 * \ \
 * 3 3 → false
 *
 * Идея: сравнивать пару узлов (левый с правым зеркально).
 * Класс SymmetricTree. На LeetCode → Solution.
 * done / pick
 */
public class SymmetricTree {

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

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return true;
        else if (!helper(root.left, root.right))
            return false;
        return true;
    }

    public boolean helper(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        else if (left == null || right == null)
            return false;
        if (left.val != right.val)
            return false;
        else if (helper(left.left, right.right) && helper(left.right, right.left))
            return true;
        return false;
    }

    public static void main(String[] args) {
        TreeNode ok = new TreeNode(1,
                new TreeNode(2, new TreeNode(3), new TreeNode(4)),
                new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        TreeNode bad = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(3)),
                new TreeNode(2, null, new TreeNode(3)));
        SymmetricTree s = new SymmetricTree();
        System.out.println(s.isSymmetric(ok)); // true
        System.out.println(s.isSymmetric(bad)); // false
    }
}
