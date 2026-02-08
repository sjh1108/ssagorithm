package ssagorithm._20260209.sjh1108.BOJ_2829;

import java.util.*;
import java.io.*;

// 백준 2829 - 아름다운 행렬 (누적 합)
class Main
{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine().trim()); // 행렬의 크기
        
        // pls: 주대각선(\) 방향 누적 합
        // sub: 부대각선(/) 방향 누적 합
        int[][] pls = new int[N+1][N+1];
        int[][] sub = new int[N+1][N+1];

        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < N; j++){
                int input = Integer.parseInt(st.nextToken());
                
                // [주대각선 누적 합]
                // 현재 칸(i+1, j+1)의 누적 합 = 왼쪽 위 칸(i, j)의 누적 합 + 현재 값
                pls[i+1][j+1] = pls[i][j] + input;
                
                // [부대각선 누적 합]
                // 현재 칸(i+1, j)의 누적 합 = 오른쪽 위 칸(i, j+1)의 누적 합 + 현재 값
                // (인덱스 조절에 주의: sub[i+1][j]에 저장하고 있음)
                sub[i+1][j] = sub[i][j+1] + input;
            }
        }

        int ans = -400000001; // 정답 초기화 (최소값 고려: -1000 * 400 * 400 보다 작은 값)

        // 모든 가능한 부분 행렬의 크기(size)에 대해 반복 (size: 2 ~ N)
        // size는 정사각형의 한 변의 길이를 의미함
        for(int size = 1; size <= N; size++){
            // 부분 행렬의 왼쪽 위 좌표 (x, y) 순회
            for(int x = 0; x < N+1 - size; x++){
                for(int y = 0; y < N+1 - size; y++){
                    // [주대각선 합 계산]
                    // (x, y)에서 시작해 size만큼 떨어진 (x+size, y+size)까지의 합
                    // 누적 합 배열 정의상: pls[x+size][y+size] - pls[x][y]
                    int a = pls[x + size][y + size] - pls[x][y];
                    
                    // [부대각선 합 계산]
                    // (x, y+size) 부근에서 시작해 (x+size, y) 부근까지의 합
                    // 누적 합 배열 정의상: sub[x+size][y] - sub[x][y+size]
                    int b = sub[x + size][y] - sub[x][y + size];

                    // 아름다운 정도: (주대각선 합) - (부대각선 합)
                    ans = Math.max(ans, a - b);
                }
            }
        }

        System.out.println(ans);
    }
}