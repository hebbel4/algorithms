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