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
/* using tushar's */
class Solution {
    public int maxProfit(int k, int[] prices) {
        if (prices.length == 0) return 0;
        int[][] dp = new int[k + 1][prices.length];
        for (int i = 1; i <= k; i++) {
            int tempMax = -prices[0];
            for (int j = 1; j < prices.length; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], prices[j] + tempMax);
                tempMax = Math.max(tempMax, dp[i - 1][j] - prices[j]);
            }
        }
        return dp[k][prices.length - 1];
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

check whether a graph is Bipartite
/* graph is represented as adjacency matrix
only works for strongly-connected graph, if graph is not connected, repeatedly call
this method on all not yet visited vertices
time complexity is V^2, if represented as adjacency list, O(V + E)
*/
public boolean isBipartite(int[][] G, int src) {
    int V = G.length;
    int[] colorArr = new int[V];
    //-1: not assigned color, 1: first color 0: second color
    for (int i = 0; i < V; i++) {
        colorArr[i] = -1;
    }
    Queue<Integer> queue = new LinkedList<>();
    queue.add(src);

    while (!queue.isEmpty()) {
        int cur = queue.poll();

        //check if it's self loop
        if (colorArr[cur] == 1) return false;

        for (int v = 0; v < V; v++) {
            if (G[cur][v] == 1 && colorArr[v] == -1) {
                colorArr[v] = 1 - colorArr[cur];
                queue.add(v);
            }
            if (G[cur][v] == 1 && colorArr[v] == colorArr[cur]) {
                return false;
            }
        }
    }
    return true;

}

51. N-Queens
class Solution {
    class Position {
        int row;
        int col;
        public Position(int r, int c) {
            row = r;
            col = c;
        }
    }
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        Position[] positions = new Position[n];
        helper(res, positions, 0, n);
        return res;
    }
    public void helper(List<List<String>> res, Position[] positions, int row, int n) {
        if (row == n) {
            List<String> lst = new ArrayList<>();
            for (Position p : positions) {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < n; i++) {
                    if (p.col == i) {
                        s.append("Q");
                    }
                    else {
                        s.append(".");
                    }
                }
                lst.add(s.toString());
            }
            res.add(lst);
            return ;
        }
        for (int col = 0; col < n; col++) {
            boolean foundSafe = true;
            for (int queen = 0; queen < row; queen++) {
                if (positions[queen].col == col || positions[queen].row - positions[queen].col == row - col || positions[queen].row + positions[queen].col == row + col) {
                    foundSafe = false;
                    break;
                }
            }
            if (foundSafe) {
                Position newp = new Position(row, col);
                positions[row] = newp;
                helper(res, positions, row + 1, n);
            }

        }

    }
}

52. N-Queens II
/* use hashset */
class Solution {
    public int totalNQueens(int n) {
        HashSet<Integer> cols = new HashSet<>();
        HashSet<Integer> diag = new HashSet<>();
        HashSet<Integer> antiDiag = new HashSet<>();
        return check(0, 0, cols, diag, antiDiag, n);
    }
    public int check(int count, int row, HashSet<Integer> cols, HashSet<Integer> diag, HashSet<Integer> anti, int n) {
        for (int col = 0; col < n; col++) {
            if (cols.contains(col)) continue;
            if (diag.contains(row - col)) continue;
            if (anti.contains(row + col)) continue;
            if (row == n - 1) count++;
            else {
                cols.add(col);
                diag.add(row - col);
                anti.add(row + col);
                count = check(count, row + 1, cols, diag, anti, n);
                cols.remove(col);
                diag.remove(row - col);
                anti.remove(row + col);
            }

        }
        return count;
    }
}
/* use boolean[] faster than set */
class Solution {
    public int totalNQueens(int n) {
        boolean[] cols = new boolean[n];
        boolean[] diag = new boolean[n * 2];
        boolean[] anti = new boolean[n * 2];
        return check(0, 0, n, cols, diag, anti);
    }
    public int check(int count, int row, int n, boolean[] cols, boolean[] diag, boolean[] anti) {
        for (int col = 0; col < n; col++) {
            int diagnal = col - row + n;
            int antidiagnal = row + col;
            if (cols[col] || diag[diagnal] || anti[antidiagnal]) continue;
            if (row == n - 1) {
                count++;
            }else {
                cols[col] = true;
                diag[diagnal] = true;
                anti[antidiagnal] = true;
                count = check(count, row + 1, n, cols, diag, anti);
                cols[col] = false;
                diag[diagnal] = false;
                anti[antidiagnal] = false;
            }

        }
        return count;
    }
}

99. Recover Binary Search Tree
class Solution {
    TreeNode first = null;
    TreeNode second = null;
    TreeNode prev = new TreeNode(Integer.MIN_VALUE);

    public void recoverTree(TreeNode root) {
        if (root == null) return;

        traverse(root);

        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }
    private void traverse(TreeNode root) {
        if (root == null) return;
        traverse(root.left);

        if (first == null && prev.val >= root.val) {
            first = prev;
        }
        if (first != null && prev.val >= root.val) {
            second = root;
        }
        prev = root;

        traverse(root.right);
    }

}

337. House Robber III
class Solution {
    HashMap<TreeNode, Integer> map = new HashMap<>();
    public int rob(TreeNode root) {
        if (map.containsKey(root)) return map.get(root);
        if (root == null) return 0;

        int res = 0;
        if (root.left != null) {
            res += rob(root.left.left) + rob(root.left.right);
        }
        if (root.right != null) {
            res += rob(root.right.left) + rob(root.right.right);
        }
        res = Math.max(res + root.val, rob(root.left) + rob(root.right));
        map.put(root, res);
        return res;
    }
}

207. Course Schedule
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        ArrayList<Integer>[] neighbours = new ArrayList[numCourses];
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < numCourses; i++) {
            neighbours[i] = new ArrayList<>();
        }
        for (int i = 0; i < prerequisites.length; i++) {
            int[] cur = prerequisites[i];
            indegree[cur[1]]++;
            neighbours[cur[0]].add(cur[1]);
        }
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.add(i);
            }
        }

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int i = 0; i < neighbours[cur].size(); i++) {
                indegree[neighbours[cur].get(i)]--;
                if (indegree[neighbours[cur].get(i)] == 0) {
                    q.add(neighbours[cur].get(i));
                }
            }
        }
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] != 0) return false;
        }
        return true;

    }
}

29. Divide Two Integers
public class Solution {
    public int divide(int dividend, int divisor) {
        // check divisor == 0 || dividend == Integer.MAX_VALUE && divisor = -1
        if (dividend == Integer.MIN_VALUE && divisor == -1 || divisor == 0) return Integer.MAX_VALUE;
        // change to long
        long m = Math.abs((long)dividend);
        long n = Math.abs((long)divisor);
        // get sign
        int sign = (dividend < 0 && divisor < 0 || dividend > 0 && divisor > 0) ? 1 : -1;
        int res = 0;
        // check dividend == divisor and divisor == 1
        if (n == 1) return (int)(sign * m);
        if (m == n) return sign;

        while (m > n) {
            int p = 1;
            long t = n;
            while (m >= (t << 1)) {
                t <<= 1;
                p <<= 1;
            }
            res += p;
            m -= t;
        }

        return sign * res;

    }
}