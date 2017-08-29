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

297. Serialize and Deserialize Binary Tree
// Encodes a tree to a single string.
public String serialize(TreeNode root) {
    if (root == null) {
        return "";
    }
    StringBuilder res = new StringBuilder();
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    res.append(root.val + " ");
    while (!queue.isEmpty()) {
        TreeNode cur = queue.poll();
        if (cur.left != null) {
            queue.add(cur.left);
            res.append(cur.left.val + " ");
        }else {
            res.append("n ");
        }
        if (cur.right != null) {
            queue.add(cur.right);
            res.append(cur.right.val + " ");
        }else {
            res.append("n ");
        }
    }
    return res.toString();
}

// Decodes your encoded data to tree.
public TreeNode deserialize(String data) {
    if (data == null || data.length() == 0) return null;
    String[] strs = data.split("\\s+");
    if (strs[0] == "n") return null;
    TreeNode root = new TreeNode(Integer.valueOf(strs[0]));
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    for (int i = 1; i < strs.length; i++) {
        TreeNode cur = queue.poll();
        if (!strs[i].equals("n")) {
            TreeNode left = new TreeNode(Integer.valueOf(strs[i]));
            cur.left = left;
            queue.add(left);
        }else {
            cur.left = null;
        }
        i++;
        if (!strs[i].equals("n")) {
            TreeNode right = new TreeNode(Integer.valueOf(strs[i]));
            cur.right = right;
            queue.add(right);
        }else {
            cur.right = null;
        }
    }
    return root;
}

238. Product of Array Except Self
/* use extra space */
public int[] productExceptSelf(int[] nums) {
    if (nums == null || nums.length == 0) return new int[]{};
    int[] fwd = new int[nums.length];
    int[] bwd = new int[nums.length];
    fwd[0] = 1;
    bwd[nums.length - 1] = 1;
    for (int i = 1; i < nums.length; i++) {
        fwd[i] = fwd[i - 1] * nums[i - 1];
    }
    for (int i = nums.length - 2; i >= 0; i--) {
        bwd[i] = bwd[i + 1] * nums[i + 1];
    }
    for (int i = 0; i < nums.length; i++) {
        nums[i] = fwd[i] * bwd[i];
    }
    return nums;
}
/* constant space */
public int[] productExceptSelf(int[] nums) {
    if (nums == null || nums.length == 0) return new int[]{};
    int[] res = new int[nums.length];
    res[0] = 1;
    for (int i = 1; i < nums.length; i++) {
        res[i] = res[i - 1] * nums[i - 1];
    }
    int right = 1;
    for (int i = nums.length - 1; i >= 0; i--) {
        res[i] *= right;
        right *= nums[i];
    }
    return res;
}

237. Delete Node in a Linked List
public void deleteNode(ListNode node) {
    if (node == null) return;
    node.val = node.next.val;
    node.next = node.next.next;
}

121. Best Time to Buy and Sell Stock
public int maxProfit(int[] prices) {
    if (prices == null || prices.length == 0) return 0;
    int max = 0;
    int min = prices[0];
    for (int p : prices) {
        min = Math.min(min, p);
        max = Math.max(max, p - min);
    }
    return max;
}

168. Excel Sheet Column Title
public String convertToTitle(int n) {
    StringBuilder sb = new StringBuilder();
    while (n > 0) {
        n--;
        sb.append((char)('A' + n % 26));
        n /= 26;
    }
    return sb.reverse().toString();
}

141. Linked List Cycle
/* hash table */
public boolean hasCycle(ListNode head) {
    Set<ListNode> set = new HashSet<>();
    if (head == null) return false;
    while (head != null) {
        if (set.contains(head)) return true;
        set.add(head);
        head = head.next;
    }
    return false;
}
/* two pointers no extra space */
public boolean hasCycle(ListNode head) {
    if (head == null) return false;
    ListNode slow = head;
    ListNode fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}

13. Roman to Integer
public int romanToInt(String s) {
    if (s == null || s.length() == 0) return 0;
    HashMap<Character, Integer> hm = new HashMap<>();
    hm.put('M', 1000);
    hm.put('D', 500);
    hm.put('C', 100);
    hm.put('L', 50);
    hm.put('X', 10);
    hm.put('V', 5);
    hm.put('I', 1);
    int sum = 0;
    for (int i = 0; i < s.length(); i++) {
        int val = hm.get(s.charAt(i));
        if (i == s.length() - 1 || hm.get(s.charAt(i)) >= hm.get(s.charAt(i + 1))) sum += val;
        else sum -= val;
    }
    return sum;
}

98. Validate Binary Search Tree
/* recursion */
public boolean isValidBST(TreeNode root) {
    return helper(root, Long.MIN_VALUE, Long.MAX_VALUE);
}

private boolean helper(TreeNode root, long start, long end) {
    if (root == null) return true;
    if (root.val <= start || root.val >= end) return false;
    return helper(root.left, start, root.val) && helper(root.right, root.val, end);
}
/* inorder traversal */
public boolean isValidBST(TreeNode root) {
    ArrayList<Integer> array = new ArrayList<>();
    if (root == null) return true;
    inorder(root, array);
    for (int i = 1; i < array.size(); i++) {
        if (array.get(i) <= array.get(i - 1)) return false;
    }
    return true;
}

private void inorder(TreeNode root, ArrayList<Integer> array) {
    if (root == null) return;
    inorder(root.left, array);
    array.add(root.val);
    inorder(root.right, array);
}

20. Valid Parentheses
/* ")}" "([])" "()}" "((" */
public boolean isValid(String s) {
    if (s == null || s.length() == 0) return false;
    Stack<Character> stack = new Stack<>();
    for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (c == '(') stack.push(')');
        else if (c == '[') stack.push(']');
        else if (c == '{') stack.push('}');
        else if (c == ')' || c == ']' || c == '}') {
            if (stack.isEmpty() || !stack.isEmpty() && stack.pop() != c) {
                return false;
            }
        }
    }
    return stack.isEmpty();
}

21. Merge Two Sorted Lists
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null && l2 == null) return null;
    if (l1 == null) return l2;
    if (l2 == null) return l1;
    ListNode dummy = new ListNode(0);
    ListNode dummyHead = dummy;
    while (l1 != null && l2 != null) {
        if (l1.val > l2.val) {
            dummyHead.next = l2;
            l2 = l2.next;
        } else {
            dummyHead.next = l1;
            l1 = l1.next;
        }
        dummyHead = dummyHead.next;
    }
    if (l1 != null) dummyHead.next = l1;
    if (l2 != null) dummyHead.next = l2;
    return dummy.next;
}

174. Dungeon Game
public int calculateMinimumHP(int[][] dungeon) {
    if (dungeon == null || dungeon.length == 0 || dungeon[0].length == 0) return 0;
    int row = dungeon.length;
    int col = dungeon[0].length;
    int[][] dp = new int[row][col];
    dp[row - 1][col - 1] = Math.max(1, 1 - dungeon[row - 1][col - 1]);
    for (int i = col - 2; i >= 0; i--) {
        dp[row - 1][i] = Math.max(1, dp[row - 1][i+1] - dungeon[row - 1][i]);
    }
    for (int i = row - 2; i >= 0; i--) {
        dp[i][col - 1] = Math.max(1, dp[i+1][col - 1] - dungeon[i][col - 1]);
    }
    for (int i = row - 2; i >= 0; i--) {
        for (int j = col - 2; j >= 0; j--) {
            int right = Math.max(1, dp[i][j+1] - dungeon[i][j]);
            int down = Math.max(1, dp[i+1][j] - dungeon[i][j]);
            dp[i][j] = Math.min(right, down);
        }
    }
    return dp[0][0];
}

146. LRU Cache
class LRUCache {
    class Node {
        int key;
        int value;
        Node pre;
        Node next;
        public Node (int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public void addToHead(Node node) {
        node.next = head.next;
        head.next.pre = node;
        head.next = node;
        node.pre = head;
    }

    public void deleteNode(Node node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }

    HashMap<Integer, Node> map;
    int capacity, count;
    Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.pre = head;
        head.pre = null;
        tail.next = null;
        count = 0;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        else {
            Node cur = map.get(key);
            deleteNode(cur);
            addToHead(cur);
            return cur.value;
        }
    }

    public void put(int key, int value) {
        if (!map.containsKey(key)) {
            Node cur = new Node(key, value);
            map.put(key, cur);
            if (count < this.capacity) {
                count++;
                addToHead(cur);
            }else {
                map.remove(tail.pre.key);
                deleteNode(tail.pre);
                addToHead(cur);
            }
        }else {
            Node cur = map.get(key);
            cur.value = value;
            deleteNode(cur);
            addToHead(cur);
        }
    }
}

460. LFU Cache
class LFUCache {
    HashMap<Integer, Integer> values;
    HashMap<Integer, Integer> counts;
    HashMap<Integer, LinkedHashSet<Integer>> sameCount;
    int capacity, minFreq;
    public LFUCache(int capacity) {
        values = new HashMap<>();
        counts = new HashMap<>();
        sameCount = new HashMap<>();
        this.capacity = capacity;
        minFreq = 1;
        sameCount.put(1, new LinkedHashSet<>());
    }

    public int get(int key) {
        if (!values.containsKey(key)) return -1;
        else {
            int curCount = counts.get(key);
            counts.put(key, curCount + 1);
            sameCount.get(curCount).remove(key);
            if(curCount==minFreq && sameCount.get(curCount).size()==0)
                minFreq++;
            if (sameCount.get(curCount + 1) == null) {
                LinkedHashSet<Integer> set = new LinkedHashSet<>();
                set.add(key);
                sameCount.put(curCount + 1, set);
            }else {
                sameCount.get(curCount + 1).add(key);
            }
            return values.get(key);
        }
    }

    public void put(int key, int value) {
        if (capacity <= 0) return;
        if (!values.containsKey(key)) {
            if (values.size() >= this.capacity) {
                int evit = sameCount.get(minFreq).iterator().next();
                sameCount.get(minFreq).remove(evit);
                values.remove(evit);
            }
            values.put(key, value);
            counts.put(key, 1);
            minFreq = 1;
            sameCount.get(1).add(key);
        }else {
            values.put(key, value);
            get(key);
        }
    }
}

8. String to Integer (atoi)
/* eg. +-2 */
public int myAtoi(String str) {
    if (str == null || str.length() == 0) return 0;
    long res = 0;
    int sign = 1;
    int ind = 0;
    str = str.trim();
    if (str.charAt(0) == '+') {
        ind++;
    }
    if (str.charAt(0) == '-') {
        sign = -1;
        ind++;
    }
    while (ind < str.length()) {
        if (!Character.isDigit(str.charAt(ind))) {
            return (int) res*sign;
        }
        res = res * 10 + str.charAt(ind) - '0';
        if (sign == 1 && res > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (sign == -1 && (-1) * res < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        ind++;
    }
    return (int) res*sign;
}

2. Add Two Numbers
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    if (l1 == null && l2 == null) return null;
    if (l1 == null) return l2;
    if (l2 == null) return l1;
    ListNode dummyHead = new ListNode(0);
    ListNode dummy = dummyHead;
    int carry = 0;
    int sum = 0;
    while (l1 != null || l2 != null) {
        int d1 = (l1 == null) ? 0 : l1.val;
        int d2 = (l2 == null) ? 0 : l2.val;
        sum = d1 + d2 + carry;
        carry = (sum > 9)? 1 : 0;
        ListNode node = new ListNode(sum % 10);
        dummy.next = node;
        dummy = dummy.next;
        sum /= 10;
        if (l1 != null) l1 = l1.next;
        if (l2 != null) l2 = l2.next;
    }
    if (sum > 0) {
        ListNode tail = new ListNode(1);
        dummy.next = tail;
    }
    return dummyHead.next;
}
/* recursion */
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    int carry = 0;
    return helper(l1, l2, carry);
}

private ListNode helper(ListNode l1, ListNode l2, int carry) {
    if (l1 == null && l2 == null) {
        if (carry == 0) return null;
        else {
            ListNode head = new ListNode(1);
            return head;
        }
    }else if (l1 != null && l2 != null) {
        int sum = l1.val + l2.val + carry;
        carry = (sum > 9) ? 1:0;
        ListNode head = new ListNode(sum % 10);
        head.next = helper(l1.next, l2.next, carry);
        return head;
    }else if (l1 != null) {
        int sum = l1.val + carry;
        carry = (sum > 9)? 1:0;
        ListNode head = new ListNode(sum % 10);
        head.next = helper(l1.next, null, carry);
        return head;
    }else {
        int sum = l2.val + carry;
        carry = (sum > 9)? 1:0;
        ListNode head = new ListNode(sum % 10);
        head.next = helper(null, l2.next, carry);
        return head;
    }
}

112. Path Sum
public boolean hasPathSum(TreeNode root, int sum) {
    if (root == null) return false;
    if (root.left == null && root.right == null && root.val == sum) return true;
    return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
}

56. Merge Intervals
public List<Interval> merge(List<Interval> intervals) {
    List<Interval> res = new ArrayList<>();
    if (intervals == null || intervals.size() == 0) {
        return res;
    }
    intervals.sort((a, b) -> (a.start - b.start));
    Interval last = intervals.get(0);
    for (int i = 1; i < intervals.size(); i++) {
        Interval cur = intervals.get(i);
        if (cur.start <= last.end) {
            if (cur.end > last.end) {
                last.end = cur.end;
            }
        }else {
            res.add(last);
            last = cur;
        }
    }
    res.add(last);
    return res;
}

23. Merge k Sorted Lists
public ListNode mergeKLists(ListNode[] lists) {
    return partition(lists, 0, lists.length - 1);
}
private ListNode partition(ListNode[] lists, int start, int end) {
    if (start > end) {
        return null;
    }else if (start == end) {
        return lists[start];
    }else {
        int mid = start + (end - start) / 2;
        ListNode left = partition(lists, start, mid);
        ListNode right = partition(lists, mid + 1, end);
        return merge(left, right);
    }
}
private ListNode merge(ListNode n1, ListNode n2) {
    if (n1 == null) return n2;
    if (n2 == null) return n1;
    if (n1.val < n2.val) {
        n1.next = merge(n1.next, n2);
        return n1;
    }else {
        n2.next = merge(n1, n2.next);
        return n2;
    }
}

213. House Robber II
public int rob(int[] nums) {
    if (nums.length == 1) return nums[0];
    int n1 = helper(nums, 0, nums.length - 2);
    int n2 = helper(nums, 1, nums.length - 1);
    return Math.max(n1, n2);
}
private int helper(int[] nums, int start, int end) {
    int rob = 0;
    int notRob = 0;
    for (int i = start; i <= end; i++) {
        int temp = rob;
        rob = notRob + nums[i];
        notRob = Math.max(notRob, temp);
    }
    return Math.max(rob, notRob);
}

212. Word Search II
class TrieNode {
    String word;
    TrieNode[] children = new TrieNode[26];
}

private TrieNode buildTrie(String[] strs) {
    TrieNode root = new TrieNode();
    for (String s : strs) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            int i = c - 'a';
            if (cur.children[i] == null) {
                TrieNode temp = new TrieNode();
                cur.children[i] = temp;
            }
            cur = cur.children[i];
        }
        cur.word = s;
    }
    return root;
}

private void dfs(char[][] board, int i, int j, TrieNode root, List<String> res){
    char c = board[i][j];
    int ind = c - 'a';
    if (c == '#' || root.children[ind] == null) return;
    root = root.children[ind];
    if (root.word != null) {
        res.add(root.word);
        root.word = null;
    }
    board[i][j] = '#';
    if (i < board.length - 1) dfs(board, i + 1, j, root, res);
    if (i > 0) dfs(board, i - 1, j, root, res);
    if (j < board[0].length - 1) dfs(board, i, j + 1, root, res);
    if (j > 0) dfs(board, i, j - 1, root, res);
    board[i][j] = c;
}

public List<String> findWords(char[][] board, String[] words) {
    List<String> res = new ArrayList<>();
    TrieNode root = buildTrie(words);
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            dfs(board, i, j, root, res);
        }
    }
    return res;
}

33. Search in Rotated Sorted Array
public int search(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;
    int start = 0;
    int end = nums.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) return mid;
        else if (nums[mid] < nums[end]) {
            if (target >= nums[mid] && target <= nums[end]) {
                start = mid;
            }else {
                end = mid;
            }
        }else {
            if (target >= nums[start] && target <= nums[mid]) {
                end = mid;
            }else {
                start = mid;
            }
        }
    }
    if (nums[start] == target) return start;
    if (nums[end] == target) return end;
    return -1;
}

232. Implement Queue using Stacks
class MyQueue {
    Stack<Integer> stack1;
    Stack<Integer> stack2;
    /** Initialize your data structure here. */
    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        stack1.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        if (!stack2.isEmpty()) {
            return stack2.pop();
        }else if (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            return stack2.pop();
        }
        else return -1;
    }

    /** Get the front element. */
    public int peek() {
        if (!stack2.isEmpty()) {
            return stack2.peek();
        }else if (!stack1.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            return stack2.peek();
        }
        else return -1;
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }
}

654. Maximum Binary Tree
public TreeNode constructMaximumBinaryTree(int[] nums) {
    if (nums == null || nums.length == 0) return null;
    return helper(nums, 0, nums.length - 1);
}
private TreeNode helper(int[] nums, int s, int e) {
    if (s > e) return null;
    int maxInd = s;
    int max = nums[s];
    for (int i = s; i <= e; i++) {
        if (nums[i] > max) {
            max = nums[i];
            maxInd = i;
        }
    }
    TreeNode root = new TreeNode(max);
    root.left = helper(nums, s, maxInd - 1);
    root.right = helper(nums, maxInd + 1, e);
    return root;
}

24. Swap Nodes in Pairs
public ListNode swapPairs(ListNode head) {
    ListNode dummy = new ListNode(0);
    dummy.next = head;
    ListNode pre = dummy;
    while (head != null && head.next != null) {
        ListNode temp = head.next.next;
        head.next.next = pre.next;
        pre.next = head.next;
        head.next = temp;
        pre = head;
        head = temp;
    }
    return dummy.next;
}
/* recursion */
public ListNode swapPairs(ListNode head) {
    if (head == null || head.next == null) return head;
    else{
        ListNode left = head;
        ListNode right = head.next;
        ListNode temp = right.next;
        right.next = left;
        left.next = swapPairs(temp);
        return right;
    }
}

153. Find Minimum in Rotated Sorted Array
public int findMin(int[] nums) {
    if (nums == null || nums.length == 0) return -1;
    int start = 0;
    int end = nums.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] < nums[end]) {
            end = mid;
        }else start = mid;
    }
    if (nums[end] < nums[start]) return nums[end];
    return nums[start];
}

191. Number of 1 Bits
// you need to treat n as an unsigned value
public int hammingWeight(int n) {
    int ones = 0;
    while (n != 0) {
        ones += (n & 1);
        n >>>= 1;
    }
    return ones;
}

4. Median of Two Sorted Arrays
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
    int total = nums1.length + nums2.length;
    if (total % 2 == 0) {
        return (
        findKth(nums1, 0, nums2, 0, total / 2) +
        findKth(nums1, 0, nums2, 0, total / 2 + 1)
        ) / 2.0;
    }else {
        return findKth(nums1, 0, nums2, 0, total / 2 + 1);
    }
}
//k is not index, k = 1 means the first one(index = 0)
private int findKth(int[] A, int indexA, int[] B, int indexB, int k) {
    if (indexA >= A.length) {
        return B[indexB + k - 1];
    }
    if (indexB >= B.length) {
        return A[indexA + k - 1];
    }

    if (k == 1) {
        return Math.min(A[indexA], B[indexB]);
    }

    int keyA = Integer.MAX_VALUE;
    int keyB = Integer.MAX_VALUE;
    if (indexA + k / 2 - 1 < A.length) {
        keyA = A[indexA + k / 2 - 1];
    }
    if (indexB + k / 2 - 1 < B.length) {
        keyB = B[indexB + k / 2 - 1];
    }

    if (keyA < keyB) {
        return findKth(A, indexA + k / 2, B, indexB, k - k / 2);
    }else {
        return findKth(A, indexA, B, indexB + k / 2, k - k / 2);
    }
}