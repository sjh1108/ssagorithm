import java.io.*;
import java.util.*;

public class Solution2001 {
  public static void main(String[] args) throws IOException{
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      
      int T = Integer.parseInt(br.readLine());

      for (int tc = 1; tc <= T; tc++) {

      	  // 입력받기
          StringTokenizer st;
          st = new StringTokenizer(br.readLine());
          int N = Integer.parseInt(st.nextToken()); // 배열 크기
          int M = Integer.parseInt(st.nextToken()); // 파리채 크기
          int[][] arr = new int[N+1][N+1]; // ki) 배열크기를 N에서 N+1로 고치기.
          
          
          for (int i = 1; i <= N; i++) {
        	    st = new StringTokenizer(br.readLine());
        	    for (int j = 1; j <= N; j++) {
        	        arr[i][j] = Integer.parseInt(st.nextToken());
        	    }
        	}
          

          int max = 0;
          
          // (i, j)는 파리채의 왼쪽 위 시작위치
          // 시작위치가 N-M+1까지의 위치까지만 가능 넘어가면 불가
          for (int i = 1; i <= N - M + 1; i++) {
        	    for (int j = 1; j <= N - M + 1; j++) {
        	        int sum = 0;
                    // M x M 영역의 파리 수 합산하기(누적합)
        	        for (int k = 0; k < M; k++) {
        	            for (int p = 0; p < M; p++) {
        	                sum += arr[i + k][j + p];
        	            }
        	        }

                  // 최대값 갱신
        		  if (max < sum) {
        			  max = sum;
        		  }
        	  }
          }
          
          
          System.out.println("#" + tc + " " + max);
          
      }//tc블록.. 헷갈려서 적음
  }
}
