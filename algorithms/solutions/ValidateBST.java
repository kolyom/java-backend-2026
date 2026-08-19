public class ValidateBST {

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

    public boolean isValidBST(TreeNode root) {
        return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean helper(TreeNode node, long min, long max) {
        if (node == null)
            return true;
        if (node.val < min || node.val > max)
            return false;
        return helper(node.left, min, node.val) && helper(node.right, node.val, max);
    }

    public static void main(String[] args) {
        // Валидное BST:
        // 5
        // / \
        // 3 8
        // / \
        // 1 4
        TreeNode valid = new TreeNode(5,
                new TreeNode(3, new TreeNode(1), new TreeNode(4)),
                new TreeNode(8));

        // Невалидное — нарушение видно только через диапазон,
        // не через сравнение с прямым родителем:
        // 5
        // / \
        // 1 8
        // / \
        // 6 10
        // /
        // 4 <- 4 < 5, но лежит в правом поддереве корня
        TreeNode invalid = new TreeNode(5,
                new TreeNode(1),
                new TreeNode(8,
                        new TreeNode(6, new TreeNode(4), null),
                        new TreeNode(10)));

        ValidateBST s = new ValidateBST();
        System.out.println(s.isValidBST(valid)); // true
        System.out.println(s.isValidBST(invalid)); // false
    }
}