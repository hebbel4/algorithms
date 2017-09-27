313. Super Ugly Number
class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] ugly = new int[n];
        int[] idx = new int[primes.length];
        ugly[0] = 1;
        for (int i = 1; i < n; i++) {
            ugly[i] = Integer.MAX_VALUE;
            for (int j = 0; j < primes.length; j++) {
                ugly[i] = Math.min(ugly[i], primes[j] * ugly[idx[j]]);
            }
            for (int j = 0; j < primes.length; j++) {
                if (ugly[i] == primes[j] * ugly[idx[j]]) idx[j]++;
            }
        }
        return ugly[n - 1];
    }
}

526. Beautiful Arrangement
class Solution {
    int count = 0;
    public int countArrangement(int N) {
        helper(N, 1, new int[N + 1]);
        return count;
    }
    private void helper(int N, int pos, int[] used) {
        if (pos > N) {
            count++;
            return;
        }
        for (int i = 1; i <= N; i++) {
            if (used[i] == 0 && (i % pos == 0 || pos % i == 0)) {
                used[i] = 1;
                helper(N, pos + 1, used);
                used[i] = 0;
            }
        }
    }
}

280. Wiggle Sort
/* sort */
class Solution {
    public void wiggleSort(int[] nums) {
        if (nums.length == 0) return;
        Arrays.sort(nums);
        for (int i = 2; i < nums.length; i += 2) {
            int temp = nums[i];
            nums[i] = nums[i - 1];
            nums[i - 1] = temp;
        }
    }
}
/* no sort */
class Solution {
    public void wiggleSort(int[] nums) {
        if (nums.length == 0) return;
        // when i is odd, nums[i] >= nums[i - 1], when i is even, nums[i] <= nums[i - 1]
        for (int i = 1; i < nums.length; i++) {
            if (i % 2 == 0) {
                if (nums[i] > nums[i - 1]) {
                    int temp = nums[i];
                    nums[i] = nums[i - 1];
                    nums[i - 1] = temp;
                }
            }else {
                if (nums[i] < nums[i - 1]) {
                    int temp = nums[i];
                    nums[i] = nums[i - 1];
                    nums[i - 1] = temp;
                }
            }
        }
    }
}

324. Wiggle Sort II
class Solution {
    public void wiggleSort(int[] nums) {
        if (nums.length == 0) return;
        int[] temp = Arrays.copyOf(nums, nums.length);
        Arrays.sort(temp);
        int a = (1 + nums.length) / 2 - 1;
        int b = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 0) {
                nums[i] = temp[a--];
            }else {
                nums[i] = temp[b--];
            }
        }
    }
}

133. Clone Graph
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        Map<Integer, UndirectedGraphNode> map = new HashMap<Integer, UndirectedGraphNode>();
        return clone(node, map);

    }
    private UndirectedGraphNode clone(UndirectedGraphNode node, Map<Integer, UndirectedGraphNode> map) {
        if (node == null) return null;
        if (map.containsKey(node.label)) {
            return map.get(node.label);
        }
        UndirectedGraphNode cloned = new UndirectedGraphNode(node.label);
        map.put(cloned.label, cloned);
        for (UndirectedGraphNode u : node.neighbors) {
            cloned.neighbors.add(clone(u, map));
        }
        return cloned;
    }
}

490. The Maze
/* dfs */
class Solution {
    int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        if (maze.length == 0 || maze[0].length == 0) return false;
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        return dfs(maze, visited, start[0], start[1], destination);
    }
    private boolean dfs(int[][] maze, boolean[][] visited, int i, int j, int[] dest) {
        if (visited[i][j]) return false;
        if (i == dest[0] && j == dest[1]) return true;
        visited[i][j] = true;

        for (int[] dir : directions) {
            int[] newSpot = roll(maze, i, j, dir);
            if (dfs(maze, visited, newSpot[0], newSpot[1], dest)) return true;
        }
        return false;

    }
    private int[] roll (int[][] maze, int i, int j, int[] dir) {
        while (canRoll(maze, i + dir[0], j + dir[1])) {
            i += dir[0];
            j += dir[1];
        }
        return new int[]{i, j};
    }
    private boolean canRoll(int[][] maze, int i , int j) {
        if (i < 0 || i >= maze.length || j < 0 || j >= maze[0].length || maze[i][j] == 1) return false;
        return true;
    }
}
/* bfs */
class Solution {
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        if (maze.length == 0 || maze[0].length == 0) return false;
        Queue<int[]> q = new LinkedList<>();
        q.add(start);
        int[][] directions = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        visited[start[0]][start[1]] = true;
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            for (int[] dir : directions) {
                int xx = x;
                int yy = y;
                while (xx + dir[0] >= 0 && xx + dir[0] < maze.length && yy + dir[1] >= 0 && yy + dir[1] < maze[0].length && maze[xx+dir[0]][yy+dir[1]] == 0) {
                    xx += dir[0];
                    yy += dir[1];
                }

                if (visited[xx][yy]) continue;
                visited[xx][yy] = true;
                if (xx == destination[0] && yy == destination[1]) return true;
                q.add(new int[]{xx, yy});

            }
        }
        return false;
    }
}

505. The Maze II
/* dfs */
class Solution {
    int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        if (maze.length == 0 || maze[0].length == 0) return -1;
        int[][] dist = new int[maze.length][maze[0].length];
        dist[start[0]][start[1]] = 1;
        dfs(maze, start[0], start[1], destination, dist);
        return dist[destination[0]][destination[1]] - 1;
    }
    private void dfs(int[][] maze, int i, int j, int[] dest, int[][] dist) {
        if (i == dest[0] && j == dest[1]) return;
        for (int[] dir : directions) {
            int ii = i;
            int jj = j;
            int len = dist[i][j];
            while (ii + dir[0] >= 0 && ii + dir[0] < maze.length && jj + dir[1] >= 0 && jj + dir[1] < maze[0].length && maze[ii + dir[0]][jj + dir[1]] == 0) {
                ii += dir[0];
                jj += dir[1];
                len++;
            }
            if (dist[ii][jj] > 0 && len >= dist[ii][jj]) continue;
            dist[ii][jj] = len;
            dfs(maze, ii, jj, dest, dist);

        }

    }
}
/* BFS */
class Solution {
    int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        if (maze.length == 0 || maze[0].length == 0) return -1;
        int[][] dist = new int[maze.length][maze[0].length];
        dist[start[0]][start[1]] = 1;
        Queue<int[]> q = new LinkedList<>();
        q.add(start);
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int i = cur[0];
            int j = cur[1];
            for (int[] dir : directions) {
                int ii = i;
                int jj = j;
                int len = dist[ii][jj];
                while (ii + dir[0] >= 0 && ii + dir[0] < maze.length && jj + dir[1] >= 0 && jj + dir[1] < maze[0].length && maze[ii + dir[0]][jj + dir[1]] == 0) {
                    ii += dir[0];
                    jj += dir[1];
                    len++;
                }
                if (dist[ii][jj] > 0 && len >= dist[ii][jj]) continue;
                dist[ii][jj] = len;
                q.add(new int[]{ii, jj});
            }
        }
        return dist[destination[0]][destination[1]] - 1;

    }
}

31. Next Permutation
class Solution {
    public void nextPermutation(int[] nums) {
        if (nums.length == 0) return;
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                for (int j = nums.length - 1; j >= i; j--) {
                    if (nums[j] > nums[i - 1]) {
                        int temp = nums[i - 1];
                        nums[i - 1] = nums[j];
                        nums[j] = temp;
                        reverse(nums, i, nums.length - 1);
                        return;
                    }
                }
            }
        }
        reverse(nums, 0, nums.length - 1);
    }

    public void reverse(int[] nums, int s, int e) {
        while (s < e) {
            int temp = nums[s];
            nums[s] = nums[e];
            nums[e] = temp;
            s++;
            e--;
        }
    }
}

394. Decode String
/* recursion */
class Solution {
    public String decodeString(String s) {
        if (s.length() == 0) return "";
        StringBuilder res = new StringBuilder();
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {
                res.append(s.charAt(i));
            }else if (Character.isDigit(s.charAt(i))) {
                num = num * 10 + s.charAt(i) - '0';
            }else if (s.charAt(i) == '[') {
                int start = i;
                int count = 1;
                i++;
                while (count != 0) {
                    if (s.charAt(i) == '[') {
                        count++;
                    }
                    if (s.charAt(i) == ']') {
                        count--;
                    }
                    i++;
                }
                i--;
                String subS = decodeString(s.substring(start + 1, i));
                for (int k = 0; k < num; k++) {
                    res.append(subS);
                }
                num = 0;
            }
        }
        return res.toString();
    }
}
/* stack */
class Solution {
    public String decodeString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<String> strStack = new Stack<>();
        int ind = 0;
        String res = "";
        while (ind < s.length()) {
            if (Character.isDigit(s.charAt(ind))) {
                int num = 0;
                while (Character.isDigit(s.charAt(ind))) {
                    num = num * 10 + s.charAt(ind) - '0';
                    ind++;
                }
                countStack.push(num);
            }else if (Character.isLetter(s.charAt(ind))) {
                res += s.charAt(ind);
                ind++;
            }else if (s.charAt(ind) == '[') {
                strStack.push(res);
                res = "";
                ind++;
            }else if (s.charAt(ind) == ']') {
                String last = strStack.pop();
                int times = countStack.pop();
                for (int i = 0; i < times; i++) {
                    last += res;
                }
                res = last;
                ind++;
            }
        }
        return res;
    }
}

329. Longest Increasing Path in a Matrix
class Solution {
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return 0;
        int[][] dp = new int[matrix.length][matrix[0].length];
        int max = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                max = Math.max(max, dfs(matrix, i, j, dp));
            }
        }
        return max;
    }
    private int dfs(int[][] matrix, int i, int j, int[][] dp) {
        if (dp[i][j] != 0) return dp[i][j];
        int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int mx = 1;
        for (int[] dir : directions) {
            int m = i + dir[0];
            int n = j + dir[1];
            if (m < 0 || m >= matrix.length || n < 0 || n >= matrix[0].length || matrix[m][n] <= matrix[i][j]) continue;
            mx = Math.max(mx, 1 + dfs(matrix, m, n, dp));
        }
        dp[i][j] = mx;
        return dp[i][j];

    }
}

332. Reconstruct Itinerary
class Solution {
    public List<String> findItinerary(String[][] tickets) {
        Map<String, PriorityQueue<String>> map = new HashMap<>();
        for (String[] str : tickets) {
            map.computeIfAbsent(str[0], k -> new PriorityQueue<>()).add(str[1]);
        }
        List<String> res = new ArrayList<>();
        visit("JFK", res, map);
        reverse(res);
        return res;
    }
    private void visit(String input, List<String> res, Map<String, PriorityQueue<String>> map) {
        while (map.containsKey(input) && map.get(input).peek() != null) {
            visit(map.get(input).poll(), res, map);
        }
        res.add(input);
    }
    private void reverse(List<String> res) {
        for (int i = 0; i < res.size() / 2; i++) {
            String temp = res.get(i);
            res.set(i, res.get(res.size() - 1 - i));
            res.set(res.size() - 1 - i, temp);
        }
    }
}