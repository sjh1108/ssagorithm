class Solution {
    static int[] Numbers;
    static int size;
    static int count = 0;
    static int Target = 0;

    public int solution(int[] numbers, int target) {
        int answer = 0;
        Numbers = numbers;
        size = numbers.length;
        Target = target;
        dfs(0, 0);

        return count;
    }

    public void dfs(int num, int depth) {
        if (depth == size) {
            if (Target == num) count++;

            return;
        }
        dfs(num + Numbers[depth], depth + 1);
        dfs(num - Numbers[depth], depth + 1);
    }
}

