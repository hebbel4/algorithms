654. Maximum Binary Tree
public class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return helper(0, nums.length - 1, nums);
    }

    private TreeNode helper(int start, int end, int[] nums) {
        if (start == end) {
            TreeNode t = new TreeNode(nums[start]);
            return t;
        }
        if (start > end) return null;
        int max = nums[start];
        int ind = start;
        for (int i = start; i <= end; i++) {
            if (nums[i] > max) {
                max = nums[i];
                ind = i;
            }
        }
        TreeNode root = new TreeNode(max);
        root.left = helper(start, ind - 1, nums);
        root.right = helper(ind + 1, end, nums);
        return root;
    }
}