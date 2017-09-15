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
            else if (map.containsKey(sum - k)) {
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