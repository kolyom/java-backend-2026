/**
 * День 11 — Easy 3/3
 * LeetCode: https://leetcode.com/problems/invert-binary-tree/
 *
 * «Переверни» дерево: у каждого узла поменяй местами левое и правое поддерево.
 *
 * Было: Стало:
 * 4 4
 * / \ / \
 * 2 7 7 2
 * / \ / \ / \ / \
 * 1 3 6 9 9 6 3 1
 *
 * Класс InvertBinaryTree. На LeetCode → Solution.
 * done / pick
 */
public class InvertBinaryTree {

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

    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    static void print(TreeNode node) {
        if (node == null) {
            System.out.print("null ");
            return;
        }
        System.out.print(node.val + " ");
        print(node.left);
        print(node.right);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4,
                new TreeNode(2, new TreeNode(1), new TreeNode(3)),
                new TreeNode(7, new TreeNode(6), new TreeNode(9)));
        InvertBinaryTree s = new InvertBinaryTree();
        print(s.invertTree(root));
        // ожидается: 4 7 9 6 2 3 1 (preorder после инверта)
    }
}
