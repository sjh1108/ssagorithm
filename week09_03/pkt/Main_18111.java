package week0911;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_18111 
{
    public static void main(String[] args) throws NumberFormatException, IOException 
    {
    
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 세로
        int M = Integer.parseInt(st.nextToken()); // 가로
        int B = Integer.parseInt(st.nextToken()); // 블록수


        // 입력
        int[][] arr = new int[N][M];
        for (int i = 0; i < N; i++) 
        {
            st = new StringTokenizer(br.readLine()); 
            for (int j = 0; j < M; j++) 
             {
                 arr[i][j] = Integer.parseInt(st.nextToken());
             }   
        }


        // 브루트포스: 1. 최악의 경우 2. 세밀 잡기 
        // 변수 준비(최적시간, 최적높이)
        long bestTime = Long.MAX_VALUE;
        int bestHeight = 0;
        
        // 높이 후보군 전부 탐색
        
        // 기본 블록 제거 추가 변수 선언
        for (int h = 0; h <= 256; h++) 
        {
            long remove = 0; // 블록제거
            long add = 0; // 블록추가

            // 전체 땅 탐색(세로, 가로)
            for (int i = 0; i < N; i++) 
            {
                for (int j = 0; j < M; j++) 
                {
                    // 원래 상식으로는 기준점 잡고, 이 차이를 고려하여 계산하는데
                    // 브루트포스는 그 생각의 역으로 잡고 들어감.
                    // for문 h + 전체 땅 탐색 모든 경우의 수를 탐색
                    int diff = arr[i][j] - h;
                    if(diff > 0) // 기준점보다 크면 깎아줘야 함.
                    {
                        remove += diff;
                    }
                    else // 기준점보다 작으면 쌓기
                    {
                        add += -diff; // 음수를 양수로 해서 더해주기(중요)
                    }   
                    
                }

                
            }

            // 깎은 블록 + 넣은 블록 >= 쌓을 블록
            if (remove + B >= add) // 가능하게 제어해줌
            {
                long time = remove * 2 + add * 1; // 삭제시 2초 추가시 1초
                // 최적시간보다 적은 시간 or 시간이 최적인 경우 더 큰 높이를 설정(문제 마지막에 쓰여있음)
                if(time < bestTime || (time == bestTime && h > bestHeight))
                {
                    bestTime = time;
                    bestHeight = h;
                }
            }    
        }
        
        // 출력: 시간 땅의 높이
        System.out.println(bestTime + " " + bestHeight);
    } 
}



