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