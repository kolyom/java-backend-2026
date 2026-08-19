
/**
 * День 12 — деревья 1/3
 * LeetCode: https://leetcode.com/problems/binary-tree-level-order-traversal/
 * (на LC это Medium; идея — очередь / BFS, не «сложная математика»)
 *
 * Верни значения узлов по уровням: List<List<Integer>>
 * Пример: [3,9,20,null,null,15,7] → [[3],[9,20],[15,7]]
 *
 * Теория: docs/theory/27-tree-level-order.md
 *
 * Класс BinaryTreeLevelOrderTraversal. На LeetCode → Solution.
 * done / pick
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class BinaryTreeLevelOrderTraversal {

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

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null)
            return result;
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int count = queue.size();
            while (count != 0) {
                TreeNode cur = queue.poll();

                level.add(cur.val);

                if (cur.left != null)
                    queue.offer(cur.left);
                if (cur.right != null)
                    queue.offer(cur.right);
                count--;
            }
            result.add(level);
        }

        return result;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
                new TreeNode(9),
                new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        BinaryTreeLevelOrderTraversal s = new BinaryTreeLevelOrderTraversal();
        System.out.println(s.levelOrder(root));
        // ожидается: [[3], [9, 20], [15, 7]]
    }
}
