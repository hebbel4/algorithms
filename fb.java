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