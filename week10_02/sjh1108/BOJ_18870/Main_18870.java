package week10_02.sjh1108.BOJ_18870;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int[] origin = new int[N];
        int[] copied = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            int x = Integer.parseInt(st.nextToken());
            
            copied[i] = origin[i] = x;
        }
        Arrays.sort(copied);
        
        HashMap<Integer, Integer> map = new HashMap<>();
        int idx = 0;
        for(int i = 0; i < N; i++){
            if(!map.containsKey(copied[i])){
                map.put(copied[i], idx++);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++){
            sb.append(map.get(origin[i])).append(' ');
        }
        System.out.println(sb);
    }
}