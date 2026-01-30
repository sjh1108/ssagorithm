package week01_02.thdwngjs.BOJ_2643;

import java.io.*;
import java.util.*;

// 백준 2643 - 색종이 올려 놓기 (DP, LIS 응용)
class Main {

    static class Shape implements Comparable<Shape>{
        int x, y;

        Shape(int x, int y){
            this.x = x;
            this.y = y;
        }

        // 정렬 기준: 너비(x) 기준 내림차순, 같다면 높이(y) 기준 내림차순
        // 이렇게 정렬하면, 나중에 나오는 색종이는 앞에 나온 색종이보다
        // 크기가 작거나 같을 확률이 높으므로 LIS(Longest Increasing Subsequence) 알고리즘을 적용하기 용이함
        @Override
        public int compareTo(Shape o){
            if(this.x == o.x){
                return Integer.compare(o.y, this.y);
            }
            return Integer.compare(o.x, this.x);
        }
    }

    private static List<Shape> shapes;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N  = Integer.parseInt(br.readLine()); // 색종이의 장수
        int ans = 0; // 쌓을 수 있는 최대 색종이 장수
        int[] dp = new int[N]; // dp[i]: i번째 색종이를 가장 위에 놓았을 때의 최대 높이

        shapes = new ArrayList<>();
        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());

            // 색종이는 90도 회전이 가능하므로,
            // 더 긴 변을 x(너비), 짧은 변을 y(높이)로 통일하여 저장 (정규화)
            if(x > y) shapes.add(new Shape(x, y)); 
            else      shapes.add(new Shape(y, x));
        }

        // 크기 순으로 정렬 (큰 색종이가 앞에 오도록)
        Collections.sort(shapes);

        for(int i = 0; i < N; i++){
            dp[i] = 1; // 자기 자신만 놓는 경우 1장

            // i번째 색종이보다 앞에 있는(크기가 크거나 같은) 색종이 j들을 탐색
            for(int j = 0; j < i; j++){
                // j번째 색종이 위에 i번째 색종이를 올릴 수 있는지 확인
                // (가로, 세로 모두 작거나 같아야 함)
                if(shapes.get(i).x <= shapes.get(j).x
                && shapes.get(i).y <= shapes.get(j).y)
                    // 올릴 수 있다면, j번째까지 쌓은 높이 + 1과 비교하여 최댓값 갱신
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            // 전체 최대 높이 갱신
            ans = Math.max(ans, dp[i]);
        }

        System.out.println(ans);
    }
}