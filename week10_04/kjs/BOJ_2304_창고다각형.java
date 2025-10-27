package Test;

import java.io.*;
import java.util.*;
 
public class BOJ_2304_창고다각형 {
   public static void main(String[] args) throws IOException {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      int N = Integer.parseInt(in.readLine());
      StringTokenizer st;
 
      int[][] map = new int[N][2];
      for (int i = 0; i < N; i++) {
         st = new StringTokenizer(in.readLine());
         map[i][0] = Integer.parseInt(st.nextToken());
         map[i][1] = Integer.parseInt(st.nextToken());
      }
 
      Arrays.sort(map, (o1, o2) -> o1[0] - o2[0]);
 
      int full = 0;
      for(int i=0;i<N;){
         int j=i+1; int max = j;
         while(j<N && map[i][1]>map[j][1]){
            if(map[max][1]<map[j++][1]) max = j-1;
         }
 
         if(j>=N){
        	 full+=map[i][1];
            if(max<N) full+=map[max][1]*(map[max][0]-map[i][0]-1);
            i=max;
         }else{
        	 full+= map[i][1]*(map[j][0]-map[i][0]);
            i=j;
         }
 
      }
      System.out.println(full);
   }
}