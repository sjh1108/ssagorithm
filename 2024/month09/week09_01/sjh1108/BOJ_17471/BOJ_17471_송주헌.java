package week09_01.sjh1108.BOJ_17471;

import java.util.*;
import java.io.*;

public class BOJ_17471_송주헌{
    private static int N, min = Integer.MAX_VALUE;
    private static int[] cnt;
    private static boolean[][] link;

    // bit mask the group members
    private static boolean checkLink(int mask){
        int aStart = -1, bStart = -1;

        // A group
        for(int i = 0; i < N; i++){
            if((mask & (1 << i)) != 0){
                aStart = i;
                break;
            }
        }

        // verifying masked members are in the group A
        Queue<Integer> q = new ArrayDeque<>();
        q.add(aStart);

        boolean[] visited = new boolean[N];
        visited[aStart] = true;
        while(!q.isEmpty()){
            int cur = q.poll();
            for(int i = 0; i < N; i++){
                if(visited[i] || !link[cur][i]) continue;
                if((mask & (1 << i)) != 0){
                    visited[i] = true;
                    q.add(i);
                }
            }
        }
        for(int i = 0; i < N; i++){
            if(!visited[i] && (mask & (1 << i)) != 0){
                return false;
            }
        }

        // B group
        for(int i = 0; i < N; i++){
            if((mask & (1 << i)) == 0){
                bStart = i;
                break;
            }
        }

        // verifying unmasked members are in the group B
        q.clear();
        q.add(bStart);
        visited = new boolean[N+1];
        visited[bStart] = true;
        while(!q.isEmpty()){
            int cur = q.poll();
            for(int i = 0; i < N; i++){
                if(visited[i] || !link[cur][i]) continue;
                if((mask & (1 << i)) == 0){
                    visited[i] = true;
                    q.add(i);
                }
            }
        }
        for(int i = 0; i < N; i++){
            if(!visited[i] && (mask & (1 << i)) == 0){
                return false;
            }
        }

        return true;
    }

    // get difference from group of members count
    private static void getDiff(int mask){
        int a = 0, b = 0;

        for(int i = 0; i < N; i++){
            if((mask & (1 << i)) == 0){
                a += cnt[i];
            } else{
                b += cnt[i];
            }
        }

        min = Math.min(min, Math.abs(a - b));
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        cnt = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            cnt[i] = Integer.parseInt(st.nextToken());
        }

        link = new boolean[N][N];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int count = Integer.parseInt(st.nextToken());

            for(int j = 0; j < count; j++){
                link[i][Integer.parseInt(st.nextToken())-1] = true;
            }
        }

        // bit mask with brute-force algorithm
        for(int mask = 1; mask < (1 << N) - 1; mask++){
            if(checkLink(mask)){
                getDiff(mask);
            }
        }
        System.out.println(min == Integer.MAX_VALUE ? -1 : min);
    }
}