/*
StringBuffer has the same methods as the StringBuilder, but each method in StringBuffer
is synchronized that is StringBuffer is thread safe.

Due to this it does not allow two threads to simultaneously access the same method.
Each method can be accessed by one thread at a time.

But being thread safe has disadvantages too as the performance of the StringBuffer hits
due to thread safe property. Thus StringBuilder is faster than the StringBuffer when
calling the same methods of each class.
*/
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

123. Best Time to Buy and Sell Stock III
/*
我们维护两种量，一个是当前到达第i天可以最多进行j次交易，最好的利润是多少（global[i][j]），另一个是当前到达第i天，最多可进行j次交易，并且最后一次交易在当天卖出的最好的利润是多少（local[i][j]）。下面我们来看递推式，全局的比较简单，
global[i][j]=max(local[i][j],global[i-1][j])，
也就是去当前局部最好的，和过往全局最好的中大的那个（因为最后一次交易如果包含当前天一定在局部最好的里面，否则一定在过往全局最优的里面）。对于局部变量的维护，递推式是
local[i][j]=max(global[i-1][j-1]+max(diff,0),local[i-1][j]+diff)，
也就是看两个量，第一个是全局到i-1天进行j-1次交易，然后加上今天的交易，如果今天是赚钱的话（也就是前面只要j-1次交易，最后一次交易取当前天），
第二个量则是取local第i-1天j次交易，然后加上今天的差值（这里因为local[i-1][j]比如包含第i-1天卖出的交易，所以现在变成第i天卖出，并不会增加交易次数，而且这里无论diff是不是大于0都一定要加上，因为否则就不满足local[i][j]必须在最后一天卖出的条件了）。
*/
class Solution {
    public int maxProfit(int[] prices) {
        if (prices.length == 0) return 0;
        int[][] global = new int[prices.length][3];
        int[][] local = new int[prices.length][3];
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            for (int j = 1; j <= 2; j++) {
                local[i][j] = Math.max(local[i - 1][j] + diff, global[i - 1][j - 1] + Math.max(diff, 0));
                global[i][j] = Math.max(local[i][j], global[i - 1][j]);
            }
        }
        return global[prices.length - 1][2];
    }
}

188. Best Time to Buy and Sell Stock IV
/* memory limit exceeded */
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) return 0;
        int[][] global = new int[prices.length][k + 1];
        int[][] local = new int[prices.length][k + 1];
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            for (int j = 1; j <= k; j++) {
                local[i][j] = Math.max(local[i - 1][j] + diff, global[i - 1][j - 1] + Math.max(diff, 0));
                global[i][j] = Math.max(global[i - 1][j], local[i][j]);
            }
        }
        return global[prices.length - 1][k];
    }
}
/* still exceeded */
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) return 0;
        int[] global = new int[k + 1];
        int[] local = new int[k + 1];
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            for (int j = k; j >= 1; j--) {
                local[j] = Math.max(global[j - 1] + Math.max(diff, 0), local[j] + diff);
                global[j] = Math.max(global[j], local[j]);
            }
        }
        return global[k];
    }
}

65. Valid Number
class Solution {
    public boolean isNumber(String s) {
        s = s.trim();
        if (s.length() == 0) return false;
        boolean numberSeen = false;
        boolean numberAfterE = false;
        boolean eSeen = false;
        boolean pointSeen = false;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                numberSeen = true;
                numberAfterE = true;
            }else if (s.charAt(i) == '.') {
                if (eSeen || pointSeen) return false;
                pointSeen = true;
            }else if (s.charAt(i) == 'e') {
                if (eSeen || !numberSeen) return false;
                eSeen = true;
                numberAfterE = false;
            }else if (s.charAt(i) == '-' || s.charAt(i) == '+') {
                if (i != 0 && s.charAt(i - 1) != 'e') return false;
            }else {
                return false;
            }
        }
        return numberSeen && numberAfterE;
    }
}

349. Intersection of Two Arrays
/* O(n) */
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0) return new int[]{};
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        for (int i : nums1) {
            set1.add(i);
        }
        for (int i : nums2) {
            if (set1.contains(i)) {
                set2.add(i);
            }
        }
        int[] res = new int[set2.size()];
        int ind = 0;
        for (int i : set2) {
            res[ind] = i;
            ind++;
        }
        return res;
    }
}
/* two poiners O(nlogn) */
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        if (nums1.length == 0 || nums2.length == 0) return new int[]{};
        int i = 0;
        int j = 0;
        Set<Integer> set = new HashSet<>();
        while (i < nums1.length || j < nums2.length) {
            int n1 = i < nums1.length ? nums1[i] : Integer.MAX_VALUE;
            int n2 = j < nums2.length ? nums2[j] : Integer.MAX_VALUE;
            if (n1 < n2) {
                i++;
            }
            else if (n1 > n2) {
                j++;
            }else {
                set.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] res = new int[set.size()];
        int index = 0;
        for (int num : set) {
            res[index++] = num;
        }
        return res;
    }
}