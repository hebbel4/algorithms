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

617. Merge Two Binary Trees
class Solution {
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) return null;
        if (t1 == null) return t2;
        if (t2 == null) return t1;
        TreeNode added = new TreeNode(t1.val + t2.val);
        added.left = mergeTrees(t1.left, t2.left);
        added.right = mergeTrees(t1.right, t2.right);
        return added;
    }
}

199. Binary Tree Right Side View
class Solution {
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        dfs(res, root, 0);
        return res;
    }
    public void dfs(List<Integer> res, TreeNode root, int level) {
        if (root == null) return;
        if (res.size() == level) {
            res.add(root.val);
        }

        dfs(res, root.right, level + 1);
        dfs(res, root.left, level + 1);
    }
}