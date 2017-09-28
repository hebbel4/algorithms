122. Best Time to Buy and Sell Stock II
class Solution {
    public int maxProfit(int[] prices) {
        int max = 0;
        if (prices.length == 0) return 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                max += prices[i] - prices[i - 1];
            }
        }
        return max;
    }
}

110. Balanced Binary Tree
class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        if (Math.abs(height(root.left) - height(root.right)) > 1) return false;
        return isBalanced(root.left) && isBalanced(root.right);
    }

    public int height(TreeNode root) {
        if (root == null) return 0;
        return Math.max(height(root.left), height(root.right)) + 1;
    }
}

113. Path Sum II
class Solution {
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, new ArrayList<>(), root, sum, 0);
        return res;
    }
    public void dfs(List<List<Integer>> res, List<Integer> lst, TreeNode root, int sum, int total) {
        if (root == null) return;
        lst.add(root.val);
        if (root.left == null && root.right == null && total + root.val == sum) {
            res.add(new ArrayList<>(lst));
        }
        else {
            dfs(res, lst, root.left, sum, total + root.val);
            dfs(res, lst, root.right, sum, total + root.val);
        }
        lst.remove(lst.size() - 1);
    }
}

547. Friend Circles
/* union find
Complexity Analysis

Time complexity : O(n^3)
​​ We traverse over the complete matrix once. Union and find operations take O(n) time in the worst case.
Space complexity : O(n). parentparent array of size nn is used.
*/
class Solution {
    class UnionFind {
        private int count;
        private int[] parent;
        private int[] rank;

        public UnionFind (int n) {
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find (int p) {
            while (parent[p] != p) {
                p = parent[p];
            }
            return p;
        }
        public void union(int p, int q) {
            int rootp = find(p);
            int rootq = find(q);
            if (rootp == rootq) return;
            if (rank[rootp] > rank[rootq]) {
                parent[rootq] = rootp;
            }else {
                parent[rootp] = rootq;
                if (rank[rootp] == rank[rootq]) {
                    rank[rootq]++;
                }
            }
            count--;
        }
        public int getCount() {
            return count;
        }
    }
    public int findCircleNum(int[][] M) {
        UnionFind uf = new UnionFind(M.length);
        for (int i = 0; i < M.length - 1; i++) {
            for (int j = i + 1; j < M.length; j++) {
                if (M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.getCount();
    }
}
/* DFS O(n^2) */
class Solution {
    public int findCircleNum(int[][] M) {
        int res = 0;
        boolean[] visited = new boolean[M.length];
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                dfs(M, i, visited);
                res++;
            }
        }
        return res;
    }
    private void dfs(int[][] M, int i, boolean[] visited) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                dfs(M, j, visited);
            }
        }

    }
}/* BFS O(n^2) */
class Solution {
    public int findCircleNum(int[][] M) {
        boolean[] visited = new boolean[M.length];
        Queue<Integer> q = new LinkedList<>();
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                q.add(i);
                while (!q.isEmpty()) {
                    int cur = q.poll();
                    for (int j = 0; j < M.length; j++) {
                        if (!visited[j] && M[cur][j] == 1) {
                            visited[j] = true;
                            q.add(j);
                        }
                    }
                }
                count++;
            }
        }
        return count;
    }
}