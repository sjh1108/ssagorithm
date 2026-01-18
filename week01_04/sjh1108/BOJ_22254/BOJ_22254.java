package week01_04.sjh1108.BOJ_22254;

import java.io.*;
import java.util.*;

class Main {
    private static int N, X;
    private static int[] process_time;
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
 
        st = new StringTokenizer(br.readLine());
        process_time = new int[N + 1];
        for(int i = 1; i <= N; i++) {
            process_time[i] = Integer.parseInt(st.nextToken());
        }
 
        int l = 1;
        int r = N;
        while(l <= r) {
            int mid = (l + r) / 2;
 
            if(can_process(mid)) {
                r = mid - 1; 
            } else {
                l = mid + 1;
            }
        }

        System.out.println(l);
    }
 
    public static boolean can_process(int mid) {
        PriorityQueue<Integer> q = new PriorityQueue<>();
        
        for(int i = 0; i < mid; i++) {
            q.offer(0);
        }
 
        for(int i = 1; i <= N; i++) {
            int time = q.poll();
            if(time + process_time[i] > X) return false;
            q.offer(time + process_time[i]);
        }
        return true;
    }
}