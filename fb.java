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