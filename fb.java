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
                if (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.') {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                else if (p.charAt(j) == '*') {
                    if (p.charAt(j - 1) != s.charAt(i) && p.charAt(j - 1) != '.') {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    }else {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1] || dp[i + 1][j] || dp[i][j + 1];
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }
}