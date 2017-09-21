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