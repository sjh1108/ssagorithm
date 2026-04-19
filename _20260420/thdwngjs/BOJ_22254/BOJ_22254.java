package _20260420.thdwngjs.BOJ_22254;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        StringTokenizer st;

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int e = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());

            map.put(e, map.getOrDefault(e, 0) + 1);
            map.put(x, map.getOrDefault(x, 0) - 1);
        }

        int sum = 0;
        int max = 0;
        int start = 0, end = 0;
        boolean flag = true;
        while(!map.isEmpty()){
            Map.Entry<Integer, Integer> entry = map.pollFirstEntry();
            int time = entry.getKey();
            sum += entry.getValue();

            if(sum > max){
                max = sum;
                start = time;
                flag = true;
            } else if(sum < max && flag){
                end = time;
                flag = false;
            }
        }

        System.out.println(max);
        System.out.println(start + " " + end);
    }
}
