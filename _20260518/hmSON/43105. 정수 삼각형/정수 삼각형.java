class Solution {

    /*
    * 자료구조 및 알고리즘 : DP
    * 정삼각형 내 한 칸의 크기는 현재 위치의 값 + 왼쪽 위와 오른쪽 위 값 중 더 큰 값
    * -> 점화식 : dp[i][j] = Math.max(dp[i-1][j-1], dp[i-1][j]) + arr[i][j];
    * 테두리 쪽에 해당하는 각 줄의 0번쨰 값은 오른쪽 위의 값만, 마지막 값은 왼쪽 위의 값만 확인
    * 해당 규칙에 따라 정삼각형의 제일 아래쪽까지 연산을 적용한 후 가장 큰 값을 반환
    * */

    public int solution(int[][] tri) {
        // 정삼각형 크기가 따로 주어지지 않았음
        // 명시적으로 표현하기 위해 구분했으나, 어차피 r == c이므로 따로 구분할 필요 없음
        int r = tri.length, c = tri[r-1].length;

        for(int i=1; i<r; i++) {
            int len = tri[i].length;

            // 0번째와 마지막 값은 점화식 그대로 적용할 수 없음
            tri[i][0] += tri[i-1][0];
            tri[i][len-1] += tri[i-1][len-2];
            for(int j=1; j<len-1; j++) {
                // 점화식
                tri[i][j] += Math.max(tri[i-1][j], tri[i-1][j-1]);
            }
        }

        // 정삼각형 밑변에서 제일 큰 값을 반환
        int max = 0;
        for(int i=0; i<c; i++) {
            int val = tri[r-1][i];
            if(val > max) max = val;
        }
        return max;
    }

}