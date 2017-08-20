654. Maximum Binary Tree
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


206. Reverse Linked List
/* recursive method */
public ListNode reverseList(ListNode head) {
    if (head == null || head.next == null) return head;
    ListNode n = reverseList(head.next);
    head.next.next = head;
    head.next = null;
    return n;
}
/* iterative method */
public ListNode reverseList(ListNode head) {
    ListNode pre = null;
    ListNode cur = head;
    while (cur != null) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
    }
    return pre;
}

138. Copy List with Random Pointer
/* using extra space */
public RandomListNode copyRandomList(RandomListNode head) {
    if (head == null) return null;
    HashMap<RandomListNode, RandomListNode> map = new HashMap<>();
    RandomListNode cur = head;
    while (cur != null) {
        map.put(cur, new RandomListNode(cur.label));
        cur = cur.next;
    }
    cur = head;
    while (cur != null) {
        map.get(cur).random = map.get(cur.random);
        map.get(cur).next = map.get(cur.next);
        cur = cur.next;
    }
    return map.get(head);
}
/* constant space */
public RandomListNode copyRandomList(RandomListNode head) {
    if (head == null) return null;
    RandomListNode cur = head;
    RandomListNode next = null;
    while (cur != null) {
        next = cur.next;
        cur.next = new RandomListNode(cur.label);
        cur.next.next = next;
        cur = next;
    }
    cur = head;
    while (cur != null) {
        if (cur.random != null) cur.next.random = cur.random.next;
        cur = cur.next.next;
    }
    cur = head;
    RandomListNode newHead = new RandomListNode(0);
    RandomListNode newCur = newHead;
    while (cur != null) {
        next = cur.next.next;
        newCur.next = cur.next;
        newCur = newCur.next;
        cur.next = next;
        cur = cur.next;
    }
    return newHead.next;
}

419. Battleships in a Board
public int countBattleships(char[][] board) {
    if (board == null || board.length == 0 || board[0].length == 0) return 0;
    int count = 0;
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (board[i][j] == '.') continue;
            if (i > 0 && board[i - 1][j] == 'X' || j > 0 && board[i][j - 1] == 'X') continue;
            count++;
        }
    }
    return count;
}

273. Integer to English Words
public class Solution {
    private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};

    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        int i = 0;
        String result = "";
        while (num > 0) {
            if (num % 1000 != 0) {
                result = helper(num % 1000) + THOUSANDS[i] + " " + result;
            }
            i++;
            num /= 1000;
        }
        return result.trim();
    }
    private String helper(int num) {
        if (num == 0) return "";
        else if (num < 20) return LESS_THAN_20[num] + " ";
        else if (num < 100) return TENS[num / 10] + " " + helper(num % 10);
        else {
            return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
        }
    }
}

54. Spiral Matrix
public List<Integer> spiralOrder(int[][] matrix) {
    List<Integer> result = new ArrayList<Integer>();
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return result;
    int rs = 0;
    int re = matrix.length - 1;
    int cs = 0;
    int ce = matrix[0].length - 1;
    while (rs <= re && cs <= ce) {
        for (int i = cs; i <= ce; i++) {
            result.add(matrix[rs][i]);
        }
        rs++;
        for (int i = rs; i <= re; i++) {
            result.add(matrix[i][ce]);
        }
        ce--;
        if (rs <= re && cs <= ce) {
            for (int i = ce; i >= cs; i--) {
                result.add(matrix[re][i]);
            }
        }
        re--;
        if (rs <= re && cs <= ce) {
            for (int i = re; i >= rs; i--) {
                result.add(matrix[i][cs]);
            }
        }
        cs++;
    }
    return result;
}

171. Excel Sheet Column Number
public int titleToNumber(String s) {
    if (s == null || s.length() == 0) return 0;
    int result = 0;
    for (int i = 0; i < s.length(); i++) {
        result = result * 26 + s.charAt(i) - 'A' + 1;
    }
    return result;
}

235. Lowest Common Ancestor of a Binary Search Tree
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return null;
    if (root == p || root == q) return root;
    else if (root.val > p.val && root.val < q.val || root.val < p.val && root.val > q.val) return root;
    else if (root.val > p.val && root.val > q.val) return lowestCommonAncestor(root.left, p, q);
    else return lowestCommonAncestor(root.right, p, q);
}

200. Number of Islands
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    boolean[][] visited = new boolean[grid.length][grid[0].length];
    int res = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1' && !visited[i][j]) {
                helper(grid, i, j, visited);
                res++;
            }
        }
    }
    return res;
}
private void helper(char[][] grid, int i , int j , boolean[][] visited) {
    if (i < 0 || i > grid.length - 1 || j < 0 || j > grid[0].length - 1) return;
    if (grid[i][j] == '0' || visited[i][j]) return;
    visited[i][j] = true;
    helper(grid, i - 1, j, visited);
    helper(grid, i, j - 1, visited);
    helper(grid, i + 1, j, visited);
    helper(grid, i, j + 1, visited);
}
/* no extra space */
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    int res = 0;
    for (int i = 0; i < grid.length; i++) {
        for (int j = 0; j < grid[0].length; j++) {
            if (grid[i][j] == '1') {
                helper(grid, i, j);
                res++;
            }
        }
    }
    return res;
}
private void helper(char[][] grid, int i , int j) {
    if (i < 0 || i > grid.length - 1 || j < 0 || j > grid[0].length - 1) return;
    if (grid[i][j] == '0') return;
    grid[i][j] = '0';
    helper(grid, i - 1, j);
    helper(grid, i, j - 1);
    helper(grid, i + 1, j);
    helper(grid, i, j + 1);
}

88. Merge Sorted Array
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int ind = m + n - 1;
    int i = m - 1;
    int j = n - 1;
    while (i >= 0 && j >= 0) {
        if (nums1[i] > nums2[j]) nums1[ind--] = nums1[i--];
        else nums1[ind--] = nums2[j--];
    }
    while (j >= 0) nums1[ind--] = nums2[j--];
}

236. Lowest Common Ancestor of a Binary Tree
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null) return root;
    if (root == p || root == q) return root;
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) return root;
    else if (left != null) return left;
    else return right;
}

445. Add Two Numbers II
/* reverse list */
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode n1 = reverse(l1);
    ListNode n2 = reverse(l2);
    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;
    int carry = 0;
    while (n1 != null || n2 != null) {
        int d1 = n1 == null ? 0 : n1.val;
        int d2 = n2 == null ? 0 : n2.val;
        int sum = d1 + d2 + carry;
        carry = sum > 9 ? 1 : 0;
        ListNode next = new ListNode(sum % 10);
        cur.next = next;
        cur = cur.next;
        if (n1 != null) n1 = n1.next;
        if (n2 != null) n2 = n2.next;
    }
    if (carry == 1) {
        ListNode n = new ListNode(1);
        cur.next = n;
    }
    return reverse(dummy.next);

}

private ListNode reverse(ListNode head) {
    ListNode pre = null;
    ListNode cur = head;
    while (cur != null) {
        ListNode next = cur.next;
        cur.next = pre;
        pre = cur;
        cur = next;
    }
    return pre;
}
/* without reverse */
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();
    while (l1 != null) {
        stack1.push(l1.val);
        l1 = l1.next;
    }
    while (l2 != null) {
        stack2.push(l2.val);
        l2 = l2.next;
    }

    int sum = 0;
    ListNode head = new ListNode(0);

    while(!stack1.isEmpty() || !stack2.isEmpty()){
        if(!stack1.isEmpty()){
            sum += stack1.pop();
        }
        if(!stack2.isEmpty()){
            sum += stack2.pop();
        }
        ListNode next = new ListNode(sum / 10);
        head.val = sum % 10;
        next.next = head;
        head = next;
        sum /= 10;
    }
    return head.val == 0 ? head.next : head;
}

48. Rotate Image
public void rotate(int[][] matrix) {
    if (matrix == null || matrix.length == 0) return;
    int m = matrix.length;
    for (int i = 0; i < m / 2; i++) {
        for (int j = i; j < m - i - 1; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[m - j - 1][i];
            matrix[m - j - 1][i] = matrix[m - i - 1][m - j - 1];
            matrix[m - i - 1][m - j - 1] = matrix[j][m - i - 1];
            matrix[j][m - i - 1] = temp;
        }
    }
}

1. Two Sum
public int[] twoSum(int[] nums, int target) {
    HashMap<Integer,Integer> map = new HashMap<>();
    int[] res = new int[2];
    for (int i = 0; i < nums.length; i++) {
        if (map.containsKey(target - nums[i])) {
            res[0] = i;
            res[1] = map.get(target - nums[i]);
            return res;
        }
        map.put(nums[i], i);
    }
    return res;

}

53. Maximum Subarray
/* O(n) */
public int maxSubArray(int[] nums) {
    int[] dp = new int[nums.length]; //dp[i] means the max subarray ending with nums[i]
    dp[0] = nums[0];
    int max = nums[0];
    for (int i = 1; i < nums.length; i++) {
        dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        max = Math.max(max, dp[i]);
    }
    return max;
}
/* divide and conquer */
public int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    return helper(nums, 0, nums.length - 1);
}
private int helper(int[] nums, int start, int end) {
    if (start >= end) return nums[start];
    int mid = start + (end - start) / 2;
    int lmax = helper(nums, start, mid - 1);
    int rmax = helper(nums, mid + 1, end);
    int mmax = nums[mid];
    int t = mmax;
    for (int i = mid - 1; i >= start; i--) {
        t += nums[i];
        mmax = Math.max(t, mmax);
    }
    t = mmax;
    for (int i = mid + 1; i <= end; i++) {
        t += nums[i];
        mmax = Math.max(mmax, t);
    }
    return Math.max(mmax, Math.max(lmax, rmax));
}

103. Binary Tree Zigzag Level Order Traversal
public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> q1 = new LinkedList<>();
    Queue<TreeNode> q2 = new LinkedList<>();
    q1.add(root);
    q2.add(root);
    int level = 0;
    int size = 0;
    while (!q1.isEmpty() && !q2.isEmpty()) {
        List<Integer> oneLevel = new ArrayList<>();
        if (level % 2 == 0) {
            size = q1.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q1.poll();
                TreeNode nextLevel = q2.poll();
                if (cur.left != null) q1.add(cur.left);
                if (cur.right != null) q1.add(cur.right);
                if (nextLevel.right != null) q2.add(nextLevel.right);
                if (nextLevel.left != null) q2.add(nextLevel.left);
                oneLevel.add(cur.val);
            }
        }else {
            size = q2.size();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q2.poll();
                TreeNode nextLevel = q1.poll();
                if (cur.right != null) q2.add(cur.right);
                if (cur.left != null) q2.add(cur.left);
                if (nextLevel.left != null) q1.add(nextLevel.left);
                if (nextLevel.right != null) q1.add(nextLevel.right);
                oneLevel.add(cur.val);
            }
        }
        level++;
        res.add(oneLevel);
    }
    return res;
}

151. Reverse Words in a String
public String reverseWords(String s) {
    if (s == null || s.length() == 0) return "";
    s = s.trim();
    String[] strs = s.split("\\s+");
    for (int i = 0; i < strs.length / 2; i++) {
        String temp = strs[i];
        strs[i] = strs[strs.length - i - 1];
        strs[strs.length - i - 1] = temp;
    }
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < strs.length; i++) {
        if (i != strs.length - 1) res.append(strs[i] + " ");
        else res.append(strs[i]);
    }
    return res.toString();
}

116. Populating Next Right Pointers in Each Node
/* recusive */
public void connect(TreeLinkNode root) {
    if (root == null) return;
    if (root.left != null) root.left.next = root.right;
    if (root.right != null) {
        if (root.next != null) {
            root.right.next = root.next.left;
        }else {
            root.right.next = null;
        }
    }
    connect(root.left);
    connect(root.right);
}
/* iterative */
public void connect(TreeLinkNode root) {
    if (root == null) return;
    Queue<TreeLinkNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        TreeLinkNode cur = queue.poll();
        if (cur.left != null) {
            cur.left.next = cur.right;
            queue.add(cur.left);
        }
        if (cur.right != null) {
            if (cur.next != null) cur.right.next = cur.next.left;
            else cur.right.next = null;
            queue.add(cur.right);
        }
    }
}

75. Sort Colors
public void sortColors(int[] nums) {
    if (nums == null || nums.length == 0) return;
    int start = 0;
    int end = nums.length - 1;
    for (int i = 0; i <= end; i++) {
        if (nums[i] == 0) {
            int temp = nums[i];
            nums[i] = nums[start];
            nums[start] = temp;
            start++;
        }
        if (nums[i] == 2) {
            int temp = nums[i];
            nums[i] = nums[end];
            nums[end] = temp;
            end--;
            i--;
        }
    }
}

73. Set Matrix Zeroes
public void setZeroes(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return;
    boolean row = false;
    boolean col = false;
    for (int i = 0; i < matrix.length; i++) {
        if (matrix[i][0] == 0) {
            col = true;
            break;
        }
    }
    for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[0][j] == 0) {
            row = true;
            break;
        }
    }
    for (int i = 1; i < matrix.length; i++) {
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[i][j] == 0) {
                matrix[i][0] = 0;
                matrix[0][j] = 0;
            }
        }
    }
    for (int i = 1; i < matrix.length; i++) {
        for (int j = 1; j < matrix[0].length; j++) {
            if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                matrix[i][j] = 0;
            }
        }
    }
    if (row) {
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[0][j] = 0;
        }
    }
    if (col) {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = 0;
        }
    }
}

218. The Skyline Problem
class BuildingPoint implements Comparable<BuildingPoint> {
    int x;
    boolean isStart;
    int height;

    @Override
    public int compareTo(BuildingPoint o) {
        if (this.x != o.x) {
            return this.x - o.x; //the lower x comes before
        }
        else {
            if (this.isStart && o.isStart) {
                return o.height - this.height; //if both of them are start, the higher height comes before
            } else if (!this.isStart && !o.isStart) {
                return this.height - o.height; //if both of them are end, the lower height comes before
            } else if (this.isStart) {
                return -1;
            } else return 1;
        }
    }
}
public List<int[]> getSkyline(int[][] buildings) {
    List<int[]> result = new ArrayList<>();
    if (buildings == null || buildings.length == 0 || buildings[0].length == 0) {
        return result;
    }
    BuildingPoint[] buildingpoints = new BuildingPoint[buildings.length * 2];
    int index = 0;
    for (int building[] : buildings) {
        buildingpoints[index] = new BuildingPoint();
        buildingpoints[index].x = building[0];
        buildingpoints[index].isStart = true;
        buildingpoints[index].height = building[2];

        buildingpoints[index + 1] = new BuildingPoint();
        buildingpoints[index + 1].x = building[1];
        buildingpoints[index + 1].isStart = false;
        buildingpoints[index + 1].height = building[2];
        index += 2;
    }
    Arrays.sort(buildingpoints);
    TreeMap<Integer, Integer> queue = new TreeMap<>();
    queue.put(0, 1);
    int preMax = 0;
    for (BuildingPoint buildingP : buildingpoints) {
        if (buildingP.isStart) {
            queue.compute(buildingP.height, (key, value) -> {
                if (value == null) {
                    return 1;
                }else return value + 1;
            });
        } else {
            queue.compute(buildingP.height, (key, value) -> {
                if (value == 1) {
                    return null;
                }else {
                    return value - 1;
                }
            });
        }
        int currentMax = queue.lastKey();
        if (preMax != currentMax) {
            result.add(new int[]{buildingP.x, currentMax});
            preMax = currentMax;
        }
    }
    return result;
}