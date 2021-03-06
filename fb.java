44. Wildcard Matching
public boolean isMatch(String s, String p) {
    int ss = 0;
    int pp = 0;
    int pstar = -1;
    int match = 0;
    while (ss < s.length()) {
        if (pp < p.length() && (s.charAt(ss) == p.charAt(pp) || p.charAt(pp) == '?')) {
            pp++;
            ss++;
        }else if (pp < p.length() && p.charAt(pp) == '*') {
            pstar = pp;
            pp++;
            match = ss;
        }else if (pstar != -1) {
            pp = pstar + 1;
            match++;
            ss = match;
        }else return false;
    }
    while (pp < p.length() && p.charAt(pp) == '*') {
        pp++;
    }
    return pp == p.length();
}

158. Read N Characters Given Read4 II - Call multiple times
public class Solution extends Reader4 {
    /**
     * @param buf Destination buffer
     * @param n   Maximum number of characters to read
     * @return    The number of characters read
     */
    char[] buffer = new char[4];
    int read = 0, write = 0;
    public int read(char[] buf, int n) {
        for (int i = 0; i < n; i++) {
            if (read == write) {
                write = read4(buffer);
                read = 0;
                if (write == 0) return i;
            }
            buf[i] = buffer[read++];
        }
        return n;
    }
}

282. Expression Add Operators
public List<String> addOperators(String num, int target) {
    List<String> res = new ArrayList<>();
    dfs(num, "", 0, target, 0, 0, res);
    return res;
}
private void dfs(String num, String op, int ind, int target, long curSum, long preSum, List<String> res) {
    if (ind == num.length() && curSum == target) {
        res.add(op);
        return;
    }else {
        for (int i = ind; i < num.length(); i++) {
            String temp = num.substring(ind, i + 1);
            long l = Long.parseLong(temp);
            if (op.isEmpty()) {
                dfs(num, temp, i + 1, target, curSum + l, curSum, res);
            }else {
                dfs(num, op + "+" + temp, i + 1, target, curSum + l, curSum, res);
                dfs(num, op + "-" + temp, i + 1, target, curSum - l, curSum, res);
                dfs(num, op + "*" + temp, i + 1, target, (curSum - preSum) * l + preSum, preSum, res);
            }
            if (num.charAt(ind) == '0') return;
        }
    }
}

17. Letter Combinations of a Phone Number
/* DFS */
class Solution {
    public List<String> letterCombinations(String digits) {
        String[] strs = new String[]{"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> res = new ArrayList<>();
        if (digits.length() == 0) return res;
        StringBuilder sbuilder = new StringBuilder();
        helper(res, sbuilder, 0, digits, strs);
        return res;
    }
    private void helper(List<String> res, StringBuilder part, int start, String digits, String[] strs) {
        if (part.length() == digits.length()) res.add(part.toString());
        else {
            String s = strs[digits.charAt(start) - '2'];
            for (int i = 0; i < s.length(); i++) {
                part.append(s.charAt(i));
                helper(res, part, start + 1, digits, strs);
                part.setLength(part.length() - 1);
            }
        }
    }
}
/* Similar DFS */
class Solution {
    public List<String> letterCombinations(String digits) {
        String[] strs = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        List<String> res = new ArrayList<>();
        if (digits.length() == 0) return res;
        helper(res, strs, digits, 0, new StringBuilder());
        return res;
    }
    private void helper(List<String> res, String[] strs, String digits, int start, StringBuilder part) {
        if (part.length() == digits.length()) {
            res.add(part.toString());
            return;
        }
        for (int i = start; i < digits.length(); i++) {
            String s = strs[digits.charAt(i) - '0'];
            for (int j = 0; j < s.length(); j++) {
                part.append(s.charAt(j));
                helper(res, strs, digits, i + 1, part);
                part.setLength(part.length() - 1);
            }
        }
    }
}
/* BFS */
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if(digits.length() == 0){
            return res;
        }

        String[] letters = new String[]{"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        Queue<String> q = new LinkedList<>();
        q.offer("");
        int ind = 0;

        while(ind < digits.length()){
            int size = q.peek().length();

            while(q.peek().length() == size){
                String cur = q.poll();

                for(char c : letters[digits.charAt(ind) - '0'].toCharArray()){
                    q.offer(cur + Character.toString(c));
                }
            }
            ind++;
        }

        for(String str : q){
            res.add(str);
        }

        return res;
    }
}
/* Similar BFS */
class Solution {
    public List<String> letterCombinations(String digits) {
        List<String> res = new ArrayList<>();
        if(digits.length() == 0){
            return res;
        }

        String[] letters = new String[]{" ", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

        Queue<StringBuilder> q = new LinkedList<>();
        q.offer(new StringBuilder());
        int ind = 0;

        while(!q.isEmpty()){
            StringBuilder sb = q.peek();
            if (ind == digits.length()) {
                res.add(sb.toString());
                q.poll();
                continue;
            }

            int size = sb.length();
            while (q.peek().length() == size){
                StringBuilder copy = q.poll();
                for (int i = 0; i < letters[digits.charAt(ind) - '0'].length(); i++) {
                    StringBuilder copy2 = new StringBuilder(copy);
                    copy2.append(letters[digits.charAt(ind) - '0'].charAt(i));
                    q.add(copy2);
                }
            }
            ind++;

        }

        return res;
    }
}

10. Regular Expression Matching
class Solution {
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*') {
                dp[0][i + 1] = dp[0][i - 1];
            }
        }
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                //match
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                //not match but is *
                else if (p.charAt(j) == '*') {
                    //use * zero times
                    if (p.charAt(j - 1) != s.charAt(i) && p.charAt(j - 1) != '.') {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    }
                    //use * zero, one, more than one times
                    else {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1] || dp[i + 1][j] || dp[i][j + 1];
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}

301. Remove Invalid Parentheses
/* BFS */
class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null) return res;
        Queue<String> queue = new LinkedList<>();
        Set<String> set = new HashSet<>();
        queue.add(s);
        set.add(s);
        boolean valid = false;
        while (!queue.isEmpty()) {
            s = queue.poll();
            if (isValid(s)) {
                res.add(s);
                valid = true;
            }
            if (valid) continue;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '(' && s.charAt(i) != ')') continue;
                String t = s.substring(0, i) + s.substring(i + 1);
                if (set.contains(t)) continue;
                queue.add(t);
                set.add(t);
            }
        }
        return res;
    }
    public boolean isValid(String s) {
        int cnt = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') cnt++;
            else if (c == ')' && cnt == 0) return false;
            else if (c == ')') cnt--;
        }
        return cnt == 0;
    }
}
/* DFS */
class Solution {
    public List<String> removeInvalidParentheses(String s) {
    List<String> ans = new ArrayList<>();
    remove(s, ans, 0, 0, new char[]{'(', ')'});
    return ans;
}

    public void remove(String s, List<String> ans, int last_i, int last_j,  char[] par) {
        for (int stack = 0, i = last_i; i < s.length(); ++i) {
            if (s.charAt(i) == par[0]) stack++;
            if (s.charAt(i) == par[1]) stack--;
            if (stack >= 0) continue;
            for (int j = last_j; j <= i; ++j)
                if (s.charAt(j) == par[1] && (j == last_j || s.charAt(j - 1) != par[1]))
                    remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
            return;
        }
        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '(') // finished left to right
            remove(reversed, ans, 0, 0, new char[]{')', '('});
        else // finished right to left
            ans.add(reversed);
    }
}


477. Total Hamming Distance
class Solution {
    public int totalHammingDistance(int[] nums) {
        if (nums.length == 0) return 0;
        int total = 0;

        for (int i = 0; i < 32; i++) {
            int ones = 0;
            for (int n : nums) {
                n >>= i;
                ones += n & 1;
            }
            total += ones * (nums.length - ones);
        }
        return total;
    }

}

461. Hamming Distance
class Solution {
    public int hammingDistance(int x, int y) {
        int cnt = 0;
        for(int i = 0; i < 32; i++){
            int xLastBit = x & 1;
            int yLastBit = y & 1;
            x >>= 1;
            y >>= 1;
            cnt += xLastBit ^ yLastBit;
        }
        return cnt;
    }
}

283. Move Zeroes
public class Solution {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int ind = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[ind] = nums[i];
                ind++;
            }
        }
        for (int i = ind; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
class Solution {
    public void moveZeroes(int[] nums) {
        int j = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] != 0){
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
                j++;
            }
        }
    }
}

38. Count and Say
public class Solution {
    public String countAndSay(int n) {
        StringBuilder res = new StringBuilder("1");
        StringBuilder pre;
        int count;
        char say;
        for (int i = 1; i < n; i++) {
            pre = res;
            res = new StringBuilder();
            count = 1;
            say = pre.charAt(0);
            for (int j = 1; j < pre.length(); j++) {
                if (pre.charAt(j) == say) {
                    count++;
                }else {
                    res.append(count).append(say);
                    count = 1;
                    say = pre.charAt(j);
                }
            }
            res.append(count).append(say);
        }
        return res.toString();
    }
}
/* recursion */
class Solution {
    String s = "1";
    public String countAndSay(int n) {
        if (n <= 1) {
            return s;
        }
        int ind = 0;
        StringBuilder next = new StringBuilder();
        while (ind < s.length()) {
            int cnt = 0;
            char c = s.charAt(ind);
            while (ind < s.length() && s.charAt(ind) == c) {
                cnt++;
                ind++;
            }
            next.append(cnt).append(c);
        }
        s = next.toString();
        return countAndSay(n - 1);
    }
}

50. Pow(x, n)
public class Solution {
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;
        double t = myPow(x, n / 2);
        if (n % 2 == 0) {
            return t * t;
        }else {
            if (n > 0) return t * t * x;
            else return t * t / x;
        }
    }
}

139. Word Break
public class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        //dp[i] 前i个字符能不能被完美划分
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if(dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;//break不break都行
                }
            }
        }
        return dp[s.length()];
    }
}

325. Maximum Size Subarray Sum Equals k
class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        //key->sum value->index
        //no need to worry duplicate key since we need to get max length, same sum will only record the smallest index
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum == k) max = Math.max(max, i + 1);
            if (map.containsKey(sum - k)) {
                max = Math.max(max, i - map.get(sum - k));
            }
            if (!map.containsKey(sum)) map.put(sum, i);
        }
        return max;
    }
}

67. Add Binary
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder res = new StringBuilder();
        int indA = a.length() - 1;
        int indB = b.length() - 1;
        int carry = 0;
        while(indA >= 0 || indB >= 0){
            int intA = 0;
            int intB = 0;
            if(indA >= 0){
                intA += a.charAt(indA) - '0';
                indA--;
            }
            if(indB >= 0){
                intB += b.charAt(indB) - '0';
                indB--;
            }
            int sum = intA + intB + carry;
            carry = sum / 2;
            res.insert(0, sum % 2);

        }
        if (carry != 0) {
            res.insert(0, 1);
        }
        return res.toString();
    }
}

341. Flatten Nested List Iterator
public class NestedIterator implements Iterator<Integer> {

    Stack<NestedInteger> stack;
    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new Stack<>();
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        NestedInteger n = stack.pop();
        return n.getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty()) {
            NestedInteger n = stack.peek();
            if (n.isInteger()) return true;
            stack.pop();
            for (int i = n.getList().size() - 1; i >= 0; i--) {
                stack.push(n.getList().get(i));
            }
        }
        return false;
    }
}

621. Task Scheduler
class Solution {
    class Node {
        char c;
        int cnt;
        public Node (char c, int cnt) {
            this.c = c;
            this.cnt = cnt;
        }
    }
    public int leastInterval(char[] tasks, int n) {
        int[] map = new int[26];
        for (char c : tasks) {
            map[c - 'A']++;
        }
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> (b.cnt - a.cnt));
        for (int i = 0; i < 26; i++) {
            Node node = new Node((char)('A' + i), map[i]);
            pq.add(node);
        }
        int module = 1;
        int count = pq.poll().cnt;
        while (pq.peek().cnt == count) {
            module++;
            pq.poll();
        }
        //(TopFreq - 1) * (n + 1) + moduleLength
        return Math.max((count - 1) * (n + 1) + module, tasks.length);
    }
}

311. Sparse Matrix Multiplication
class Solution {
    public int[][] multiply(int[][] A, int[][] B) {
        int aRow = A.length;
        int aCol = A[0].length;
        int bRow = B.length;
        int bCol = B[0].length;
        //aCol = bRow, so make a matrix of aRow and bCol
        int[][] resMatrix = new int[aRow][bCol];
        if (A.length == 0 || A[0].length == 0 || B.length == 0 || B[0].length == 0) return resMatrix;
        //C[i][j]是怎么来的，起始是A[i][0]*B[0][j] + A[i][1]*B[1][j] + ... + A[i][k]*B[k][j]
        for (int i = 0; i < aRow; i++) {
            for (int k = 0; k < bRow; k++) {
                if (A[i][k] != 0) {
                    for (int j = 0; j < bCol; j++){
                        if (B[k][j] != 0)
                            resMatrix[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
        return resMatrix;
    }
}

252. Meeting Rooms
class Solution {
    public boolean canAttendMeetings(Interval[] intervals) {
        if (intervals.length == 0) return true;
        Arrays.sort(intervals, (a, b) -> (a.start - b.start));
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].start < intervals[i - 1].end) return false;
        }
        return true;
    }
}

253. Meeting Rooms II
class Solution {
    public int minMeetingRooms(Interval[] intervals) {
        if (intervals.length == 0) return 0;
        Arrays.sort(intervals, (a, b) -> (a.start - b.start));
        PriorityQueue<Interval> pq = new PriorityQueue<>((a, b) -> (a.end - b.end));
        pq.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            Interval cur = pq.poll();
            if (intervals[i].start >= cur.end) {
                cur.end = intervals[i].end;
            }else {
                pq.add(intervals[i]);
            }
            pq.add(cur);
        }
        return pq.size();
    }
}

90. Subsets II
/* iterative */
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        res.add(new ArrayList<Integer>());
        int startIndex = 0;
        int size = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i >= 1 && nums[i] == nums[i - 1]) {
                startIndex = size;
            }else {
                startIndex = 0;
            }
            size = res.size();
            for (int j = startIndex; j < size; j++) {
                List<Integer> temp = new ArrayList<>(res.get(j));
                temp.add(nums[i]);
                res.add(temp);
            }
        }
        return res;
    }
}
/* recursive */
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        helper(res, new ArrayList<>(), nums, 0);
        return res;
    }
    private void helper(List<List<Integer>> res, List<Integer> lst, int[] nums, int start) {
        List<Integer> temp = new ArrayList<>(lst);
        res.add(temp);
        for (int i = start; i < nums.length; i++) {
            if (i == start || nums[i] != nums[i - 1]) {
                temp.add(nums[i]);
                helper(res, temp, nums, i + 1);
                temp.remove(temp.size() - 1);
            }
        }
    }
}

26. Remove Duplicates from Sorted Array
class Solution {
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int len = 1;
        int start = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                len++;
                nums[start] = nums[i];
                start++;
            }
        }
        return len;
    }
}

285. Inorder Successor in BST
class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if(root == null)
            return null;

        return binarySearch(root, p);
    }

    private TreeNode binarySearch(TreeNode root, TreeNode p) {
        if(root == null)
            return null;

        if(root.val <= p.val)
            return binarySearch(root.right, p);

        TreeNode candidate = binarySearch(root.left, p);
        return candidate == null ? root : candidate;
    }
}
/* get successor */
class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) return null;
        if (root.val <= p.val) {
            return inorderSuccessor(root.right, p);
        }
        TreeNode left = inorderSuccessor(root.left, p);
        return left != null ? left : root;
    }
}
/* get predecessor */
public TreeNode predecessor(TreeNode root, TreeNode p) {
  if (root == null)
    return null;

  if (root.val >= p.val) {
    return predecessor(root.left, p);
  } else {
    TreeNode right = predecessor(root.right, p);
    return (right != null) ? right : root;
  }
}
/* very slow solution... */
class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        Stack<TreeNode> stack = new Stack<>();
        List<TreeNode> lst = new ArrayList<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            TreeNode cur = stack.pop();
            lst.add(cur);
            root = cur.right;
        }
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).val == p.val && i != lst.size() - 1) {
                return lst.get(i + 1);
            }
        }
        return null;
    }
}

314. Binary Tree Vertical Order Traversal
class Solution {
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<>();
        Queue<Integer> cols = new LinkedList<>();
        //key: cols value: lst
        Map<Integer, ArrayList<Integer>> map = new HashMap<>();
        q.add(root);
        cols.add(0);
        int min = 0;
        int max = 0;

        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            int col = cols.poll();

            if (!map.containsKey(col)) {
                map.put(col, new ArrayList<>());
            }
            map.get(col).add(cur.val);

            if (cur.left != null) {
                q.add(cur.left);
                cols.add(col - 1);
                min = Math.min(min, col - 1);
            }
            if (cur.right != null) {
                q.add(cur.right);
                cols.add(col + 1);
                max = Math.max(max, col + 1);
            }

        }
        for (int i = min; i <= max; i++) {
            res.add(map.get(i));
        }
        return res;
    }
}

277. Find the Celebrity
/* 用一个一位数组做标记 */
public class Solution extends Relation {
    public int findCelebrity(int n) {
        boolean[] peoples = new boolean[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if (knows(i, j) || !knows(j, i)) {
                    peoples[i] = false;
                    break;
                }else {
                    peoples[i] = true;
                }
            }
            if (peoples[i]) return i;
        }
        return -1;
    }
}
/* without boolean array */
public class Solution extends Relation {
    public int findCelebrity(int n) {
        for (int i = 0; i < n; i++) {
            int j;
            for (j = 0; j < n; j++) {
                if (i != j && (knows(i, j) || !knows(j, i))) break;
            }
            if (j == n) return i;
        }
        return -1;
    }
}
/* a popular solution */
public class Solution extends Relation {
    public int findCelebrity(int n) {
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (knows(res, i)) res = i;
        }
        for (int i = 0; i < n; i++) {
            if (res != i && (knows(res, i) || !knows(i, res))) return -1;
        }
        return res;
    }
}

257. Binary Tree Paths
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;
        helper(res, root, "");
        return res;
    }
    private void helper(List<String> res, TreeNode root, String s) {
        if (root.left == null && root.right == null) {
            res.add(s + root.val);
        }
        if (root.left != null) {
            helper(res, root.left, s + root.val + "->");
        }
        if (root.right != null) {
            helper(res, root.right, s + root.val + "->");
        }
    }
}
/* use stringbuilder */
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        if (root == null) return res;
        helper(res, root, new StringBuilder());
        return res;
    }
    private void helper(List<String> res, TreeNode root, StringBuilder s) {
        int len = s.length();
        s.append(root.val);
        if (root.left == null && root.right == null) {
            res.add(s.toString());
        }else {
            s.append("->");
        }
        if (root.left != null) {
            helper(res, root.left, s);
        }
        if (root.right != null) {
            helper(res, root.right, s);
        }
        s.setLength(len);
    }
}
/* without helper function */
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null) return paths;
        if (root.left == null && root.right == null) {
            paths.add(root.val + "");
            return paths;
        }
        for (String s : binaryTreePaths(root.left)) {
            paths.add(root.val + "->" + s);
        }
        for (String s : binaryTreePaths(root.right)) {
            paths.add(root.val + "->" + s);
        }
        return paths;
    }
}

140. Word Break II
/* regular DFS but time limit exceed */
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        if (s.length() == 0) return res;
        helper(res, 0, s, "", wordDict);
        return res;
    }
    private void helper(List<String> res, int start, String s, String part, List<String> wordDict) {
        if (start == s.length()) {
            part = part.trim();
            res.add(part);
            return;
        }
        for (int i = start; i < s.length(); i++) {
            String temp = s.substring(start, i + 1);
            if (wordDict.contains(temp)) {
                helper(res, i + 1, s, part + temp + " ", wordDict);
            }
        }
    }
}
/* memorized DFS */
class Solution {
    public List<String> wordBreak(String s, List<String> wordDict) {
        return helper(s, wordDict, new HashMap<String, List<String>>());
    }
    private List<String> helper(String s, List<String> wordDict, HashMap<String, List<String>> map) {
        if (map.containsKey(s)) {
            return map.get(s);
        }
        List<String> res = new ArrayList<>();
        if (s.length() == 0) {
            //must have res.add("")
            res.add("");
            return res;
        }
        for (String word : wordDict) {
            if (s.startsWith(word)) {
                List<String> subLst = helper(s.substring(word.length()), wordDict, map);
                for (String sub : subLst) {
                    res.add(word + (sub.length() == 0 ? "" : " ") + sub);
                }
            }
        }
        map.put(s, res);
        return res;
    }
}


class Solution {
    public String minWindow(String s, String t) {
        int len1 = t.length();
        int len2 = s.length();
        if (len1 > len2) return "";
        int[] arr = new int[256];
        for (int i = 0; i < len1; i++) {
            arr[t.charAt(i)]++;
        }
        int right = 0, left = 0, cnt = 0, len = Integer.MAX_VALUE;
        String res = "";

        while (right < s.length()) {
            arr[s.charAt(right)]--;
            if(arr[s.charAt(right)] >= 0){
                cnt++;
            }
            right++;

            while(cnt == t.length()){
                if(right - left < len){
                    len = right - left;
                    res = s.substring(left, right);
                }
                arr[s.charAt(left)]++;

                if(arr[s.charAt(left)] > 0){
                    cnt--;
                }
                left++;
            }
        }
        return res;
    }
}

161. One Edit Distance
class Solution {
    public boolean isOneEditDistance(String s, String t) {
        for (int i = 0; i < Math.min(s.length(), t.length()); i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (s.length() == t.length()) {
                    return s.substring(i+1).equals(t.substring(i+1));
                }else {
                    if (s.length() > t.length()) {
                        return s.substring(i+1).equals(t.substring(i));
                    }
                    else return s.substring(i).equals(t.substring(i+1));
                }
            }
        }
        return Math.abs(s.length() - t.length()) == 1;
    }
}

127. Word Ladder
/* BFS */
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<String> q = new LinkedList<>();
        q.add(beginWord);
        q.add(null);

        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        int level = 1;

        Set<String> dict = new HashSet<>();
        for (String s : wordList) {
            dict.add(s);
        }
        if (!dict.contains(endWord)) return 0;

        while (!q.isEmpty()) {
            String cur = q.poll();

            if (cur != null) {
                char[] chars = cur.toCharArray();
                for (int i = 0; i < cur.length(); i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char old = chars[i];
                        chars[i] = c;
                        String altered = new String(chars);

                        if (altered.equals(endWord) && dict.contains(altered)) {
                            return level + 1;
                        }

                        if (dict.contains(altered) && !visited.contains(altered)) {
                            visited.add(altered);
                            q.add(altered);
                        }
                        chars[i] = old;
                    }
                }
            }else {
                level++;

                if (!q.isEmpty()) {
                    q.add(null);
                }
            }
        }
        return 0;
    }
}
/* without adding null to mark level */
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Queue<String> q = new LinkedList<>();
        q.add(beginWord);

        Set<String> visited = new HashSet<>();
        visited.add(beginWord);

        int level = 1;

        Set<String> dict = new HashSet<>();
        for (String s : wordList) {
            dict.add(s);
        }
        if (!dict.contains(endWord)) return 0;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int j = 0; j < size; j++) {
                String cur = q.poll();

                char[] chars = cur.toCharArray();
                for (int i = 0; i < cur.length(); i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char old = chars[i];
                        chars[i] = c;
                        String altered = new String(chars);

                        if (altered.equals(endWord) && dict.contains(altered)) {
                            return level + 1;
                        }

                        if (dict.contains(altered) && !visited.contains(altered)) {
                            visited.add(altered);
                            q.add(altered);
                        }
                        chars[i] = old;
                    }
                }
            }

            level++;
        }
        return 0;
    }
}
/* two-end set */
class Solution {

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        if (!wordList.contains(endWord)) {
            return 0;
        }

        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);

        Set<String> visited = new HashSet<>();

        Set<String> wordSet = new HashSet<>();
        for (String str : wordList) {
            wordSet.add(str);
        }

        int len = 1;

        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> set = beginSet;
                beginSet = endSet;
                endSet = set;
            }

            Set<String> temp = new HashSet<String>();
            for (String word : beginSet) {
                char[] chars = word.toCharArray();

                for (int i = 0; i < chars.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char old = chars[i];
                        chars[i] = c;
                        String target = String.valueOf(chars);

                        if (endSet.contains(target)) {
                            return len + 1;
                        }

                        if (!visited.contains(target) && wordSet.contains(target)) {
                            temp.add(target);
                            visited.add(target);
                        }
                        chars[i] = old;
                    }
                }
            }

            beginSet = temp;
            len++;
        }

        return 0;
    }
}

117. Populating Next Right Pointers in Each Node II
public class Solution {
    public void connect(TreeLinkNode root) {
        if (root == null) return;
        while (root != null) {
            TreeLinkNode dummyHead = new TreeLinkNode(0);
            TreeLinkNode dummy = dummyHead;
            while (root != null) {
                if (root.left != null) {
                    dummy.next = root.left;
                    dummy = dummy.next;
                }
                if (root.right != null) {
                    dummy.next = root.right;
                    dummy = dummy.next;
                }
                root = root.next;
            }
            root = dummyHead.next;
        }
    }
}