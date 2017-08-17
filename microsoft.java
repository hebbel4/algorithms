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