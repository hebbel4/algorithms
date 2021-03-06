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
/* my solution */
public class Solution {
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) return null;
        RandomListNode newHead = new RandomListNode(head.label);
        RandomListNode newCur = newHead;
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode next = cur.next;
            RandomListNode newNext = null;
            if (next != null) newNext = new RandomListNode(next.label);
            newCur.next = newNext;
            RandomListNode random = cur.random;
            RandomListNode newRandom = null;
            if (random != null) newRandom = new RandomListNode(random.label);
            newCur.random = newRandom;
            newCur = newCur.next;
            cur = cur.next;
        }
        return newHead;
    }
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
            //check 1000,000 如果不是num % 1000 != 0 就会变成one million thousand
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
        if (rs <= re) {
            //如果不加if[[2, 3]] 会变成[2, 3, 2]
            for (int i = ce; i >= cs; i--) {
                result.add(matrix[re][i]);
            }
        }
        re--;//放if里面外面都行
        if (cs <= ce) {
            //如果不加if[[7], [9], [6]]会变成 [7, 9, 6, 9]
            for (int i = re; i >= rs; i--) {
                result.add(matrix[i][cs]);
            }
        }
        cs++;//放if里面外面都行
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
/* a simpler version */
class Solution {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<>();
        q.add(root);
        boolean order = true;
        while (!q.isEmpty()) {
            List<Integer> lst = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode t = q.poll();
                if (order) {
                    lst.add(t.val);
                }else {
                    lst.add(0, t.val);
                }
                if (t.left != null) q.add(t.left);
                if (t.right != null) q.add(t.right);
            }
            res.add(lst);
            order = order ? false : true;
        }
        return res;
    }
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
/* another version */
public class Solution {
    public String reverseWords(String s) {
        if (s.length() == 0) return "";
        String[] strs = s.split("\\s+");
        int start = 0;
        int end = strs.length - 1;
        while (start < end) {
            String temp = strs[start];
            strs[start++] = strs[end];
            strs[end--] = temp;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str + " ");
        }
        String res = sb.toString();
        res = res.trim();
        return res;
    }
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
    // trick : i <= end
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
/* while loop */
class Solution {
    public void sortColors(int[] nums) {
        if (nums.length == 0) return;
        int start = 0;
        int end = nums.length - 1;
        int index = 0;
        while (index <= end) {
            if (nums[index] == 0) {
                nums[index] = nums[start];
                nums[start] = 0;
                start++;
            }
            if (nums[index] == 2) {
                nums[index] = nums[end];
                nums[end] = 2;
                end--;
                index--;
            }
            index++;
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
/* recursion */
class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;

        }
    }
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
public int myAtoi(String str) {
    if (str == null || str.length() == 0) return 0;
    long res = 0;
    int sign = 1;
    int ind = 0;
    // "     010"
    str = str.trim();
    if (str.charAt(0) == '+') {
        ind++;
    }
    if (str.charAt(0) == '-') {
        sign = -1;
        ind++;
    }
    while (ind < str.length()) {
        /* eg. +-2 */
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
class Solution {
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        return Math.max(helper(nums, 0, nums.length - 2), helper(nums, 1, nums.length - 1));
    }
    public int helper(int[] nums, int start, int end) {
        int rob = nums[start];
        int notRob = 0;
        for (int i = start + 1; i <= end; i++) {
            int temp = rob;
            rob = notRob + nums[i];
            notRob = Math.max(temp, notRob);
        }
        return Math.max(rob, notRob);
    }
}

212. Word Search II
/* brute force */
class Solution {
    public List<String> findWords(char[][] board, String[] words) {
        List<String> res = new ArrayList<>();
        if (board.length == 0 || board[0].length == 0 || words.length == 0) return res;
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (String s : words) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == s.charAt(0)) {
                        dfs(res, board, i, j, s, 0, visited);
                    }
                }
            }
        }
        return res;
    }
    public void dfs(List<String> res, char[][] board, int i, int j, String s, int ind, boolean[][] visited) {
        if (ind == s.length()) {
            if (!res.contains(s)) res.add(s);
            return;
        }
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
        if (s.charAt(ind) != board[i][j]) return;
        if (visited[i][j]) return;

        visited[i][j] = true;

        dfs(res, board, i + 1, j, s, ind + 1, visited);
        dfs(res, board, i - 1, j, s, ind + 1, visited);
        dfs(res, board, i, j + 1, s, ind + 1, visited);
        dfs(res, board, i, j - 1, s, ind + 1, visited);

        visited[i][j] = false;

    }
}
/* DFS + Trie */
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
        //k is not index, k = 1 means the first one(index = 0)!!!!!!
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

102. Binary Tree Level Order Traversal
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    int size = 0;
    while (!queue.isEmpty()) {
        size = queue.size();
        List<Integer> lst = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            TreeNode cur = queue.poll();
            if (cur.left != null) queue.offer(cur.left);
            if (cur.right != null) queue.offer(cur.right);
            lst.add(cur.val);
        }
        res.add(lst);
    }
    return res;
}

160. Intersection of Two Linked Lists
/* hash table */
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    HashSet<ListNode> set = new HashSet<>();
    ListNode l1 = headA;
    ListNode l2 = headB;
    while (l1 != null || l2 != null) {
        if (l1 != null && set.contains(l1)) return l1;
        else set.add(l1);
        if (l2 != null && set.contains(l2)) return l2;
        else set.add(l2);
        if (l1 != null) l1 = l1.next;
        if (l2 != null) l2 = l2.next;
    }
    return null;
}
/* two pointer */
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    if (headA == null || headB == null) return null;
    ListNode a = headA;
    ListNode b = headB;
    while (a != b) {
        a = a == null ? headB : a.next;
        b = b == null ? headA : b.next;
    }
    return a;
}

189. Rotate Array
/* O(k % nums.length) space */
public void rotate(int[] nums, int k) {
    if (nums == null || nums.length <= 1) return;
    int step = k % nums.length;
    int[] temp = new int[step];
    for (int i = 0; i < step; i++) {
        temp[i] = nums[nums.length - step + i];
    }
    for (int i = nums.length - step - 1; i >= 0; i--) {
        nums[i + step] = nums[i];
    }
    for (int i = 0; i < step; i++) {
        nums[i] = temp[i];
    }
}
/* constant space */
public void rotate(int[] nums, int k) {
    if (nums == null || nums.length <= 1) return;
    reverse(nums, 0, nums.length - k % nums.length - 1);
    reverse(nums, nums.length - k % nums.length, nums.length - 1);
    reverse(nums, 0, nums.length - 1);
}

private void reverse(int[] nums, int start, int end) {
    while (start < end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        start++;
        end--;
    }
}

26. Remove Duplicates from Sorted Array
public int removeDuplicates(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int start = 1;
    int pre = nums[0];
    for (int i : nums) {
        if (i != pre) {
            nums[start] = i;
            start++;
            pre = i;
        }
    }
    return start;
}

215. Kth Largest Element in an Array
public int findKthLargest(int[] nums, int k) {
    if (nums == null || nums.length == 0) return 0;
    int left = 0;
    int right = nums.length - 1;
    while (true) {
        int p = partition(nums, left, right);
        if (p == k - 1) return nums[p];
        else if (p < k - 1) {
            left = p + 1;
        }else right = p - 1;
    }

}
private int partition(int[] nums, int left, int right) {
    int pivot = nums[left];
    int l = left + 1;
    int r = right;
    while (l <= r) {
        if (nums[l] < pivot && nums[r] >= pivot) {
            swap(nums, l, r);
            l++;
            r--;
        }
        if (nums[l] >= pivot) l++;
        if (nums[r] < pivot) r--;
    }
    swap(nums, left, r);
    return r;
}
private void swap(int[] nums, int left, int right) {
    int temp = nums[left];
    nums[left] = nums[right];
    nums[right] = temp;
}

106. Construct Binary Tree from Inorder and Postorder Traversal
public TreeNode buildTree(int[] inorder, int[] postorder) {
    if (inorder == null || inorder.length == 0 || postorder == null || postorder.length == 0) return null;
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0 ; i < inorder.length; i++) {
        map.put(inorder[i], i);
    }
    return helper(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1, map);
}
private TreeNode helper(int[] inorder, int is, int ie, int[] postorder, int ps, int pe, HashMap<Integer, Integer> map) {
    if (is > ie || ps > pe) return null;
    TreeNode root = new TreeNode(postorder[pe]);
    int index = map.get(postorder[pe]);
    root.left = helper(inorder, is, index - 1, postorder, ps, ps + index - is - 1, map);
    root.right = helper(inorder, index + 1, ie, postorder, ps + index - is, pe - 1, map);
    return root;
}

5. Longest Palindromic Substring
public String longestPalindrome(String s) {
    if (s == null || s.length() == 0) return "";
    int len = s.length();
    int res = 0;
    int left = 0;
    int right = 0;
    boolean[][] dp = new boolean[len][len];
    for (int i = 0 ; i < len; i++) {
        for (int j = 0 ; j < len; j++) {
            if (j == i) dp[i][j] = true;
        }
    }
    for (int i = 0; i < len; i++) {
        for (int j = 0; j < i; j++) {
            if (s.charAt(i) == s.charAt(j)) {
                if (j + 1 == i) {
                    dp[j][i] = true;
                }
                else {
                    dp[j][i] = dp[j + 1][i - 1] == true? true : false;
                }
            }
            if (dp[j][i] == true && res < i - j + 1) {
                res = i - j + 1;
                left = j;
                right = i;
            }
        }
    }
    return s.substring(left, right + 1);
}

204. Count Primes
public int countPrimes(int n) {
    int count = 0;
    boolean[] notPrime = new boolean[n];
    for (int i = 2; i < n; i++) {
        if (notPrime[i] == false) {
            count++;
            for (int j = 2; i * j < n; j++) {
                notPrime[j * i] = true;
            }
        }
    }
    return count;
}

173. Binary Search Tree Iterator
public class BSTIterator {
    TreeNode head;
    Stack<TreeNode> stack = new Stack<>();
    public BSTIterator(TreeNode root) {
        head = root;
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        return head != null || !stack.isEmpty();
    }

    /** @return the next smallest number */
    public int next() {
        while (head != null) {
            stack.push(head);
            head = head.left;
        }
        head = stack.pop();
        TreeNode result = head;
        head = head.right;
        return result.val;
    }
}

15. 3Sum
public List<List<Integer>> threeSum(int[] nums) {
    List<List<Integer>> res = new ArrayList<>();
    if (nums == null || nums.length == 0) return res;
    Arrays.sort(nums);
    for (int i = 0; i < nums.length - 2; i++) {
        if (i == 0 || nums[i] != nums[i - 1]) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                List<Integer> lst = new ArrayList<>();
                if (nums[i] + nums[left] + nums[right] == 0) {
                    lst.add(nums[i]);
                    lst.add(nums[left]);
                    lst.add(nums[right]);
                    res.add(lst);
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }else if (nums[i] + nums[left] + nums[right] > 0) {
                    right--;
                }else left++;
            }
        }
    }
    return res;
}

25. Reverse Nodes in k-Group
public ListNode reverseKGroup(ListNode head, int k) {
    ListNode cur = head;
    int cnt = 0;
    while (cnt < k && cur != null) {
        cur = cur.next;
        cnt++;
    }
    if (cnt == k) {
        cur = reverseKGroup(cur, k);
        while (cnt > 0) {
            ListNode temp = head.next;
            head.next = cur;
            cur = head;
            head = temp;
            cnt--;
        }
        head = cur;
    }
    return head;
}

387. First Unique Character in a String
public int firstUniqChar(String s) {
    if (s == null || s.length() == 0) return -1;
    int[] arr = new int[26];
    for (int i = 0; i < s.length(); i++) {
        int ind = s.charAt(i) - 'a';
        arr[ind]++;
    }
    for (int i = 0; i < s.length(); i++) {
        int ind = s.charAt(i) - 'a';
        if (arr[ind] == 1) return i;
    }
    return -1;
}

79. Word Search
public boolean exist(char[][] board, String word) {
    if (board == null || board.length == 0 || board[0].length == 0) return false;
    boolean[][] visited = new boolean[board.length][board[0].length];
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (word.charAt(0) == board[i][j] && dfs(board, i, j, word, 0, visited)) return true;
        }
    }
    return false;
}
private boolean dfs(char[][] board, int i, int j , String word, int index, boolean[][] visited) {
    if (index == word.length()) return true;
    if (i < 0 || i > board.length - 1 || j < 0 || j > board[0].length - 1 ||
       visited[i][j]== true || word.charAt(index) != board[i][j]) {
        return false;
    }
    visited[i][j] = true;
    if (dfs(board, i + 1, j, word, index + 1, visited) || dfs(board, i - 1, j, word, index + 1, visited) ||
        dfs(board, i, j + 1, word, index + 1, visited) || dfs(board, i, j - 1, word, index + 1, visited)) {
        return true;
    }
    visited[i][j] = false;
    return false;
}
/* no extra space */
public boolean exist(char[][] board, String word) {
    if (board == null || board.length == 0 || board[0].length == 0) return false;
    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[0].length; j++) {
            if (word.charAt(0) == board[i][j] && dfs(board, i, j, word, 0)) return true;
        }
    }
    return false;
}
private boolean dfs(char[][] board, int i, int j , String word, int index) {
    if (index == word.length()) return true;
    if (i < 0 || i > board.length - 1 || j < 0 || j > board[0].length - 1 ||
       board[i][j]== '$' || word.charAt(index) != board[i][j]) {
        return false;
    }
    char c = board[i][j];
    board[i][j] = '$';
    if (dfs(board, i + 1, j, word, index + 1) || dfs(board, i - 1, j, word, index + 1) ||
        dfs(board, i, j + 1, word, index + 1) || dfs(board, i, j - 1, word, index + 1)) {
        return true;
    }
    board[i][j] = c;
    return false;
}

165. Compare Version Numbers
public int compareVersion(String version1, String version2) {
    String[] levels1 = version1.split("\\.");
    String[] levels2 = version2.split("\\.");
    int len = Math.max(levels1.length, levels2.length);
    for (int i = 0; i < len; i++) {
        int v1 = i < levels1.length ? Integer.valueOf(levels1[i]) : 0;
        int v2 = i < levels2.length ? Integer.valueOf(levels2[i]) : 0;
        if (v1 < v2) return -1;
        if (v1 > v2) return 1;
    }
    return 0;
}

94. Binary Tree Inorder Traversal
public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    Stack<TreeNode> stack = new Stack<>();
    while (root != null || !stack.isEmpty()) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        root = stack.pop();
        res.add(root.val);
        root = root.right;
    }
    return res;
}

258. Add Digits
/* naive and trivial */
public int addDigits(int num) {
    while (num / 10 > 0) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        num = sum;
    }
    return num;
}
/* niubi */
public int addDigits(int num) {
    return (num - 1) % 9 + 1;
}

101. Symmetric Tree
/* recursive */
public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    return helper(root.left, root.right);
}
private boolean helper(TreeNode t1, TreeNode t2) {
    if (t1 == null && t2 == null) return true;
    else if (t1 != null && t2 != null) {
        if (t1.val == t2.val) {
            return helper(t1.left, t2.right) && helper(t1.right, t2.left);
        }else return false;
    }else return false;
}
/* iterative */
public boolean isSymmetric(TreeNode root) {
    if (root == null) return true;
    Queue<TreeNode> q1 = new LinkedList<>();
    Queue<TreeNode> q2 = new LinkedList<>();
    q1.add(root.left);
    q2.add(root.right);
    while (!q1.isEmpty() && !q2.isEmpty()) {
        TreeNode n1 = q1.poll();
        TreeNode n2 = q2.poll();
        if (n1 != null && n2 == null || n1 == null && n2 != null) return false;
        if (n1 != null && n2 != null) {
            if (n1.val != n2.val) return false;
            q1.add(n1.left);
            q1.add(n1.right);
            q2.add(n2.right);
            q2.add(n2.left);
        }
    }
    return true;
}

91. Decode Ways
public int numDecodings(String s) {
    if (s == null || s.length() == 0) return 0;
    int len = s.length();
    int[] dp = new int[len + 1];
    dp[0] = 1;
    for (int i = 1; i <= len; i++) {
        if (s.charAt(i - 1) - '0' <= 9 && s.charAt(i - 1) - '0' >= 1) {
            dp[i] = dp[i - 1];
        }
        if (s.charAt(i - 1) - '0' == 0) {
            dp[i] = 0;
        }
        if (i >= 2 && (s.charAt(i - 2) - '0' == 1 || s.charAt(i - 2) - '0' == 2 && s.charAt(i - 1) - '0' <= 6)) {
            dp[i] += dp[i - 2];
        }
    }
    return dp[len];
}

28. Implement strStr()
public int strStr(String haystack, String needle) {
    if (haystack.length() < needle.length()) return -1;
    if (needle.length() == 0) return 0;
    for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
        if (haystack.charAt(i) == needle.charAt(0)) {
            int j = 0;
            for (j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) break;
            }
            if (j == needle.length()) return i;
        }
    }
    return -1;
}

162. Find Peak Element
public int findPeakElement(int[] nums) {
    if (nums == null || nums.length == 0) return -1;
    int start = 0;
    int end = nums.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] < nums[mid + 1]) {
            start = mid;
        }else {
            end = mid;
        }
    }
    if (nums[start] > nums[end]) return start;
    return end;
}
/* linear time */
public int findPeakElement(int[] nums) {
    if (nums == null || nums.length == 0) return -1;
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] < nums[i - 1]) return i - 1;
    }
    return nums.length - 1;
}

125. Valid Palindrome
public boolean isPalindrome(String s) {
    s = s.replaceAll("[^A-Za-z0-9]", "");
    s = s.toLowerCase();
    int left = 0;
    int right = s.length() - 1;
    while (left <= right) {
        if (s.charAt(left) != s.charAt(right)) return false;
        left++;
        right--;
    }
    return true;
}

124. Binary Tree Maximum Path Sum
int max = Integer.MIN_VALUE;
public int maxPathSum(TreeNode root) {
    pathSum(root);
    return max;
}
private int pathSum(TreeNode root) {
    if (root == null) return 0;
    int left = Math.max(0, pathSum(root.left));
    int right = Math.max(0, pathSum(root.right));
    max = Math.max(max, left + right + root.val);
    return Math.max(left, right) + root.val;
}
/* without global variable */
class Solution {
    public int maxPathSum(TreeNode root) {
        if (root == null) return 0;
        int[] max = new int[1];
        max[0] = Integer.MIN_VALUE;
        maxChildPath(root, max);
        return max[0];

    }
    public int maxChildPath(TreeNode root, int[] max) {
        if (root == null) return 0;

        int left = Math.max(0, maxChildPath(root.left, max));
        int right = Math.max(0, maxChildPath(root.right, max));
        max[0] = Math.max(max[0], left + right + root.val);
        return Math.max(left, right) + root.val;
    }
}
// Version 2:
// SinglePath也定义为，至少包含一个点。
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: An integer.
     */
    private class ResultType {
        int singlePath, maxPath;
        ResultType(int singlePath, int maxPath) {
            this.singlePath = singlePath;
            this.maxPath = maxPath;
        }
    }

    private ResultType helper(TreeNode root) {
        if (root == null) {
            return new ResultType(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        // Divide
        ResultType left = helper(root.left);
        ResultType right = helper(root.right);

        // Conquer
        int singlePath =
            Math.max(0, Math.max(left.singlePath, right.singlePath)) + root.val;

        int maxPath = Math.max(left.maxPath, right.maxPath);
        maxPath = Math.max(maxPath,
                           Math.max(left.singlePath, 0) +
                           Math.max(right.singlePath, 0) + root.val);

        return new ResultType(singlePath, maxPath);
    }

    public int maxPathSum(TreeNode root) {
        ResultType result = helper(root);
        return result.maxPath;
    }

}

300. Longest Increasing Subsequence
public int lengthOfLIS(int[] nums) {
    if(nums.length == 0){
        return 0;
    }
    int[] dp = new int[nums.length];
    Arrays.fill(dp, 1);
    int max = 1;
    for(int i = 0; i < nums.length; i++){
        for(int j = 0; j < i; j++){
            if(nums[i] > nums[j]){
                dp[i] = Math.max(dp[i], dp[j] + 1);
                max = Math.max(max, dp[i]);
            }
        }
    }
    return max;
}
/* binary search
Arrays.binarySearch() Returns: index of the search key, if it is contained in the array within the specified range;
otherwise, (-(insertion point) - 1).
The insertion point is defined as the point at which the key would be inserted into the array:
the index of the first element in the range greater than the key,
or toIndex if all elements in the range are less than the specified key.
Note that this guarantees that the return value will be >= 0 if and only if the key is found. */
public int lengthOfLIS(int[] nums) {
    int[] dp = new int[nums.length];
    int len = 0;
    for (int i = 0; i < nums.length; i++) {
        int ind = Arrays.binarySearch(dp, 0, len, nums[i]);
        if (ind < 0) {
            ind = - (ind + 1);
        }
        dp[ind] = nums[i];
        if (ind == len) {
            len++;
        }
    }
    return len;
}

56. Merge Intervals
public List<Interval> merge(List<Interval> intervals) {
    List<Interval> res = new ArrayList<>();
    if (intervals.size() == 0) return res;
    Collections.sort(intervals, (a, b) -> (a.start - b.start));
    Interval last = intervals.get(0);
    for (int i = 1; i < intervals.size(); i++) {
        Interval cur = intervals.get(i);
        if (cur.end > last.end) {
            if (cur.start > last.end) {
                res.add(last);
                last = cur;
            }
            else {
                last.end = cur.end;
            }
        }
    }
    res.add(last);
    return res;
}

112. Path Sum
public boolean hasPathSum(TreeNode root, int sum) {
    if (root == null) return false;
    if (root.val == sum && root.left == null && root.right == null) return true;
    return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
}

114. Flatten Binary Tree to Linked List
/* recursive */
public void flatten(TreeNode root) {
    if (root == null) return;
    flatten(root.left);
    flatten(root.right);
    TreeNode temp = root.right;
    root.right = root.left;
    root.left = null;
    while (root.right != null) {
        root = root.right;
    }
    root.right = temp;
}
/* iteration */
public void flatten(TreeNode root) {
    TreeNode cur = root;
    while (cur != null) {
        if (cur.left != null) {
            TreeNode temp = cur.left;
            while (temp.right != null) temp = temp.right;
            temp.right = cur.right;
            cur.right = cur.left;
            cur.left = null;
        }
        cur = cur.right;
    }
}

650. 2 Keys Keyboard
public int minSteps(int n) {
    int[] dp = new int[n + 1];
    for (int i = 2; i <= n; i++) {
        dp[i] = i;
        for (int j = i - 1; j > 1; j--) {
            if (i % j == 0) {
                dp[i] = dp[j] + i / j;
                break;
            }
        }
    }
    return dp[n];
}

567. Permutation in String
public boolean checkInclusion(String s1, String s2) {
    int[] arr = new int[26];
    if (s1.length() > s2.length()) return false;
    for (int i = 0; i < s1.length(); i++) {
        arr[s1.charAt(i) - 'a']++;
        arr[s2.charAt(i) - 'a']--;
    }
    if (allZero(arr)) return true;
    for (int i = s1.length(); i < s2.length(); i++) {
        arr[s2.charAt(i) - 'a']--;
        arr[s2.charAt(i - s1.length()) - 'a']++;
        if (allZero(arr)) return true;
    }
    return false;
}
private boolean allZero(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
        if (arr[i] != 0) return false;
    }
    return true;
}
/* sliding window */
public boolean checkInclusion(String s1, String s2) {
    int len1 = s1.length();
    int len2 = s2.length();
    if (len1 > len2) return false;
    int[] map = new int[128];
    for (char c : s1.toCharArray()) {
        map[c]++;
    }
    int right = 0, left = 0, count = 0;
    while (right < s2.length()) {
        if (map[s2.charAt(right)] > 0) count++;
        map[s2.charAt(right)]--;
        right++;
        while (count == s1.length()) {
            if (right - left == count) {
                return true;
            }
            map[s2.charAt(left)]++;
            if (map[s2.charAt(left)] > 0) count--;
            left++;
        }
    }
    return false;
}

513. Find Bottom Left Tree Value
public int findBottomLeftValue(TreeNode root) {
    if (root == null) return 0;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    int res = 0;
    while (!queue.isEmpty()) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            TreeNode cur = queue.poll();
            if (i == 0) res = cur.val;
            if (cur.left != null) queue.add(cur.left);
            if (cur.right != null) queue.add(cur.right);
        }
    }
    return res;
}

452. Minimum Number of Arrows to Burst Balloons
public int findMinArrowShots(int[][] points) {
    if (points.length == 0) return 0;
    Arrays.sort(points, (a, b) -> a[1] - b[1]);
    int pos = points[0][1];
    int count = 1;
    for (int i = 1; i < points.length; i++) {
        if (pos >= points[i][0]) continue;
        count++;
        pos = points[i][1];
    }
    return count;
}

365. Water and Jug Problem
class Solution {
    public boolean canMeasureWater(int x, int y, int z) {
        if (x + y < z) return false;
        if (x == z || y == z || x + y == z) return true;
        return z % gcd(x, y) == 0;
    }
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}

348. Design Tic-Tac-Toe
class TicTacToe {
    int diagonal = 0;
    int antiDiagonal = 0;
    int[] rows;
    int[] cols;
    int total;
    /** Initialize your data structure here. */
    public TicTacToe(int n) {
        rows = new int[n];
        cols = new int[n];
        total = n;
    }

    /** Player {player} makes a move at ({row}, {col}).
        @param row The row of the board.
        @param col The column of the board.
        @param player The player, can be either 1 or 2.
        @return The current winning condition, can be either:
                0: No one wins.
                1: Player 1 wins.
                2: Player 2 wins. */
    public int move(int row, int col, int player) {
        int point = player == 1 ? 1 : -1;
        rows[row] += point;
        cols[col] += point;
        if (row == col) diagonal += point;
        if (row + col + 1 == total) antiDiagonal += point;
        if (Math.abs(rows[row]) == total || Math.abs(cols[col]) == total ||
           Math.abs(diagonal) == total || Math.abs(antiDiagonal) == total) return player;
        return 0;
    }
}

186. Reverse Words in a String II
public class Solution {
    public void reverseWords(char[] s) {
        reverse(s, 0, s.length - 1);
        int r = 0;
        while (r < s.length) {
            int l = r;
            while (r < s.length && s[r] != ' ') {
                r++;
            }
            reverse(s, l, r-1);
            r++;
        }
    }
    public void reverse(char[] chars, int start, int end) {
        while (start < end) {
            char temp = chars[start];
            chars[start++] = chars[end];
            chars[end--] = temp;
        }
    }
}

270. Closest Binary Search Tree Value
/* inorder traversal */
class Solution {
    public int closestValue(TreeNode root, double target) {
        Stack<TreeNode> stack = new Stack<>();
        double min = Double.MAX_VALUE;
        int res = 0;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            TreeNode cur = stack.pop();
            if (Math.abs(cur.val - target) <= min) {
                min = Math.abs(cur.val - target);
                res = cur.val;
            }
            root = cur.right;
        }
        return res;
    }
}
/* BST 特性 */
class Solution {
    public int closestValue(TreeNode root, double target) {
        int res = root.val;
        while (root != null) {
            if (Math.abs(root.val - target) < Math.abs(res - target)) {
                res = root.val;
            }
            root = target < root.val? root.left : root.right;
        }
        return res;
    }
}

333. Largest BST Subtree
/* O(n^2) */
class Solution {
    public int largestBSTSubtree(TreeNode root) {
        if (root == null) return 0;
        if (isValid(root, Integer.MIN_VALUE, Integer.MAX_VALUE)) return count(root);
        return Math.max(largestBSTSubtree(root.left), largestBSTSubtree(root.right));
    }

    private boolean isValid(TreeNode root, int start, int end) {
        if (root == null) return true;
        if (root.val <= start || root.val >= end) return false;
        return isValid(root.left, start, root.val) && isValid(root.right, root.val, end);
    }

    private int count(TreeNode root) {
        if (root == null) return 0;
        return count(root.left) + count(root.right) + 1;
    }
}

651. 4 Keys Keyboard
class Solution {
    public int maxA(int N) {
        int max = N;
        for (int i = 1; i <= N - 3; i++) {
            max = Math.max(max, maxA(i) * (N - i - 1));
        }
        return max;
    }
}
/* dp array */
class Solution {
    public int maxA(int N) {
        int[] dp = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            dp[i] = i;
            for (int j = 1; j <= i - 3; j++) {
                dp[i] = Math.max(dp[i], dp[j] * (i - j - 1));
            }
        }
        return dp[N];
    }
}

672. Bulb Switcher II
class Solution {
    public int flipLights(int n, int m) {
        if (m == 0) return 1;
        if (n == 1) return 2;
        if (n == 2 && m == 1) return 3;
        if (n == 2) return 4;

        if (m == 1) return 4;
        else if (m == 2) return 7;
        else return 8;

    }
}

591. Tag Validator
class Solution {
    public boolean isValid(String code) {
        if (code.length() == 0) return true;
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < code.length(); i++) {
            //开头结尾不是标签的情况，以及没有标签的情况，和开头的标签在中间就闭合了情况等等
            if (i > 0 && stack.isEmpty()) return false;
            if (code.startsWith("<![CDATA[", i)) {
                int j = i + 9;
                i = code.indexOf("]]>", j);
                //j = 9 means the string starts with CONTENT
                if (i < 0 || j == 9) return false;
                i += 2;
            }else if (code.startsWith("</", i)) {
                int j = i + 2;
                i = code.indexOf(">", j);
                //<DIV><></></DIV> cannot be empty
                if (i < 0 || i - j > 9 || i == j) return false;
                for (int k = j; k < i; k++) {
                    if (!Character.isUpperCase(code.charAt(k))) return false;
                }
                if (stack.isEmpty() || !stack.pop().equals(code.substring(j, i))) return false;
            }else if (code.startsWith("<", i)) {
                int j = i + 1;
                i = code.indexOf(">", j);
                if (i < 0 || i - j > 9 || i == j) return false;
                for (int k = j; k < i; k++) {
                    if (!Character.isUpperCase(code.charAt(k))) return false;
                }
                String s = code.substring(j, i);
                stack.push(s);
            }
        }
        return stack.isEmpty();
    }
}