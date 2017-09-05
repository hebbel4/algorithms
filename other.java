84. Largest Rectangle in Histogram
public int largestRectangleArea(int[] heights) {
    Stack<Integer> stack = new Stack<>();
    if (heights.length == 0) return 0;
    int max = -1, area = 0, i = 0;
    for (i = 0; i < heights.length;) {
        if (stack.isEmpty() || heights[i] >= heights[stack.peek()]) {
            stack.push(i++);
        }else {
            int top = stack.pop();
            if (stack.isEmpty()) {
                area = heights[top] * i;
            }else {
                area = heights[top] * (i - stack.peek() - 1);
            }
            max = Math.max(max, area);
        }
    }
    while (!stack.isEmpty()) {
        int top = stack.pop();
        if (stack.isEmpty()) {
            area = heights[top] * i;
        }else {
            area = heights[top] * (i - stack.peek() - 1);
        }
        max = Math.max(max, area);
    }
    return max;
}

264. Ugly Number II
class Solution {
    public int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        int[] ind = new int[3];
        int[] primes = new int[] {2,3,5};
        ugly[0] = 1;
        for (int i = 1; i < n; i++) {
            ugly[i] = Integer.MAX_VALUE;
            for (int j = 0; j < 3; j++) {
                ugly[i] = Math.min(ugly[i], ugly[ind[j]] * primes[j]);
            }
            for (int j = 0; j < 3; j++) {
                if (ugly[i] == ugly[ind[j]] * primes[j]) ind[j]++;
            }
        }
        return ugly[n - 1];
    }
}