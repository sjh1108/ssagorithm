package week10_03.sjh1108.BOJ_7568;
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[][] person = new int[N][2];
        
        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            person[i][0] = Integer.parseInt(st.nextToken());
            person[i][1] = Integer.parseInt(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++){
            int rank = 1;

            for(int j = 0; j < N; j++){
                if(i == j) continue;
                if(person[i][0] < person[j][0] && person[i][1] < person[j][1]){
                    rank++;
                }
            }

            sb.append(rank).append(' ');
        }

        System.out.println(sb);
    }
}