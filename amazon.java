663. Equal Tree Partition
class Solution {
    public boolean checkEqualTree(TreeNode root) {
        if (root == null) return false;
        int sum = sum(root);
        if (sum % 2 != 0) return false;
        return checkEqual(sum / 2, root.left) || checkEqual(sum / 2, root.right);
    }
    private boolean checkEqual(int target, TreeNode root) {
        if (root == null) return false;
        return target == sum(root) ||
            (root.left != null && target == sum(root.left)) ||
            (root.right != null && target == sum(root.right)) ||
            checkEqual(target, root.left) || checkEqual(target, root.right);
    }
    private int sum(TreeNode root) {
        if (root == null) return 0;
        return root.val + sum(root.left) + sum(root.right);
    }
}