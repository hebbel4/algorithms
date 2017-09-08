84. Largest Rectangle in Histogram
public int largestRectangleArea(int[] heights) {
    Stack<Integer> stack = new Stack<>();
    if (heights.length == 0) return 0;
    int max = -1, area = 0, i = 0;
    for (i = 0; i < heights.length;) {
        if (stack.isEmpty() || heights[i] >= heights[stack.peek()]) {
            stack.push(i++);
        }else {
            int top = stack.pop();
            if (stack.isEmpty()) {
                area = heights[top] * i;
            }else {
                area = heights[top] * (i - stack.peek() - 1);
            }
            max = Math.max(max, area);
        }
    }
    while (!stack.isEmpty()) {
        int top = stack.pop();
        if (stack.isEmpty()) {
            area = heights[top] * i;
        }else {
            area = heights[top] * (i - stack.peek() - 1);
        }
        max = Math.max(max, area);
    }
    return max;
}

264. Ugly Number II
class Solution {
    public int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        int[] ind = new int[3];
        int[] primes = new int[] {2,3,5};
        ugly[0] = 1;
        for (int i = 1; i < n; i++) {
            ugly[i] = Integer.MAX_VALUE;
            for (int j = 0; j < 3; j++) {
                ugly[i] = Math.min(ugly[i], ugly[ind[j]] * primes[j]);
            }
            for (int j = 0; j < 3; j++) {
                if (ugly[i] == ugly[ind[j]] * primes[j]) ind[j]++;
            }
        }
        return ugly[n - 1];
    }
}

623. Add One Row to Tree
class Solution {
    public TreeNode addOneRow(TreeNode root, int v, int d) {
        if (d == 1) {
            TreeNode t = new TreeNode(v);
            t.left = root;
            return t;
        }
        helper(root, v, d, 1);
        return root;

    }
    private void helper(TreeNode root, int v, int d, int level) {
        if (root == null) return;
        if (level + 1 == d) {
            TreeNode left = root.left;
            TreeNode right = root.right;
            root.left = new TreeNode(v);
            root.right = new TreeNode(v);
            root.left.left = left;
            root.right.right = right;
            return;
        }
        helper(root.left, v, d, level + 1);
        helper(root.right, v, d, level + 1);
    }
}

148. Sort List
class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        return partition(head);
    }
    private ListNode partition(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = slow;

        while(fast != null && fast.next != null){
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = null;
        ListNode l = partition(head);
        ListNode r = partition(slow);
        return merge(l, r);
    }
    private ListNode merge(ListNode left, ListNode right) {
        if (left == null) return right;
        if (right == null) return left;
        if (left.val < right.val) {
            left.next = merge(left.next, right);
            return left;
        }else {
            right.next = merge(left, right.next);
            return right;
        }
    }
}