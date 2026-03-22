package _20260323.thdwngjs.BOJ_28277;

import java.io.*;
import java.util.*;

class Main {

    private static Set<Integer>[] set;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        set = new Set[N+1];
        for(int i = 1; i <= N; i++){
            set[i] = new HashSet<>();

            st = new StringTokenizer(br.readLine());
            int size = Integer.parseInt(st.nextToken());

            while(size-- > 0){
                set[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        StringBuilder sb = new StringBuilder();
        while(Q-- > 0){
            st = new StringTokenizer(br.readLine());

            int op = Integer.parseInt(st.nextToken());

            int a = Integer.parseInt(st.nextToken());
            if(op == 1){
                int b = Integer.parseInt(st.nextToken());

                if(set[a].size() < set[b].size()){
                    swap(a, b);
                }

                for(int i: set[b]){
                    set[a].add(i);
                }

                set[b].clear();
            } else{
                sb.append(set[a].size()).append('\n');
            }
        }

        System.out.println(sb);
    }

    private static void swap(int a, int b){
        Set<Integer> tmp = set[b];
        set[b] = set[a];
        set[a] = tmp;
    }
}