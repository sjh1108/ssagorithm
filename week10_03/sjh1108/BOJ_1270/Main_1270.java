package week10_03.sjh1108.BOJ_1270;
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            
            int t = Integer.parseInt(st.nextToken());
            HashMap<Long, Integer> map = new HashMap<>();

            for(int j = 0; j < t; j++){
                long soldier = Long.parseLong(st.nextToken());

                map.put(soldier, map.getOrDefault(soldier, 0) + 1);
            }

            long army = -1;
            for(long key: map.keySet()){
                int cnt = map.get(key);

                if(cnt > (t/2)){
                    army = key;
                }
            }

            if(army == -1){
                sb.append("SYJKGW");
            } else{
                sb.append(army);
            }
            sb.append('\n');
        }

        System.out.println(sb);
    }
}