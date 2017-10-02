605. Can Place Flowers
class Solution {
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0 && (i == 0 || flowerbed[i - 1] == 0) &&
               (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                flowerbed[i] = 1;
                n--;
            }
        }
        return n <= 0;
    }
}

152. Maximum Product Subarray
class Solution {
    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;
        int max = nums[0];
        int min = nums[0];
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < 0) {
                int temp = max;
                max = min;
                min = temp;
            }
            max = Math.max(nums[i], max * nums[i]);
            min = Math.min(nums[i], min * nums[i]);
            res = Math.max(res, max);
        }
        return res;
    }
}

339. Nested List Weight Sum
public class Solution {
    public int depthSum(List<NestedInteger> nestedList) {
        return dfs(nestedList, 1);
    }
    private int dfs(List<NestedInteger> nestedList, int depth) {
        int res = 0;
        for (NestedInteger n : nestedList) {
            if (n.isInteger()) {
                res += n.getInteger() * depth;
            }else {
                res += dfs(n.getList(), depth + 1);
            }
        }
        return res;
    }
}

633. Sum of Square Numbers
class Solution {
    public boolean judgeSquareSum(int c) {
        int start = 0;
        int end = (int)Math.sqrt(c);
        while (start <= end) {
            if (start * start + end * end == c) return true;
            else if (start * start + end * end > c) end--;
            else start++;
        }
        return false;
    }
}

671. Second Minimum Node In a Binary Tree
class Solution {
    public int findSecondMinimumValue(TreeNode root) {
        if (root == null) return -1;
        if (root.left == null && root.right == null) return -1;

        int left = root.left.val;
        int right = root.right.val;
        if (left == root.val) {
            left = findSecondMinimumValue(root.left);
        }
        if (right == root.val) {
            right = findSecondMinimumValue(root.right);
        }
        if (left != -1 && right != -1) return Math.min(left, right);
        else if (left != -1) return left;
        else return right;
    }
}