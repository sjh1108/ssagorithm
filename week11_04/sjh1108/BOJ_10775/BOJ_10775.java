package week11_04.sjh1108.BOJ_10775;

import java.io.*;
import java.util.*;

class Main{
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();

    static int N, M, K;
    
    static int[] gate;

    static int find(int x){
        if(gate[x] == x) return x;
        return gate[x] = find(gate[x]);
    }

    static void union(int a, int b){
        a = find(a);
        b = find(b);

        if(a != b) gate[a] = b;
    }
    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        gate = new int[N+1];
        for(int i=1; i<=N; i++){
            gate[i] = i;
        }
        
        int count = 0;
        for(int i=0; i<M; i++){
            int n = Integer.parseInt(br.readLine());
            
            int g = find(n);
            if(g == 0) break;

            count++;
            union(g, g-1);
        }

        System.out.println(count);
    }
}