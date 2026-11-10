import java.io.*;
import java.util.*;

class Main {
  private static int N, M;
  private static int max;

  private static int[][] map;

  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());

    map = new int[N][M+1];
    for(int i = 0; i < N; i++){
      char[] input = br.readLine().toCharArray();

      for(int j = 0; j < M; j++){
        if(input[j] == '1'){
          map[i][j+1] = map[i][j]+1;
        }
      }
    }
    
    max = 0;
    for(int i = 0; i < N; i++){
      for(int j = 1; j <= M; j++){
        if(map[i][j] == 0) continue;

        int h = i;
        int w = map[i][j];

        while(h < N && map[h][j] != 0){
          w = Math.min(w, map[h][j]);
          int size = w * (h - i + 1);
          if(max < size) max = size;

          if((N - i) * w < max) break;
          ++h;
        }
      }
    }
    System.out.println(max);
  }
}
