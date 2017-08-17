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
