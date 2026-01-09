package week01_02.thdwngjs.BOJ_5021;

import java.util.*;
import java.io.*;

class Main{
    private static HashMap<String, String[]> family;
    private static HashMap<String, Double> blood;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        family = new HashMap<>();
        blood = new HashMap<>();

        String king = br.readLine();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            String kid = st.nextToken();
            String p1 = st.nextToken();
            String p2 = st.nextToken();

            family.put(kid, new String[]{p1, p2});

            blood.put(kid, -1D);
            blood.put(p1, -1D);
            blood.put(p2, -1D);
        }

        blood.put(king, 1d);

        for(String s: blood.keySet()) dfs(s);

        String succeed = br.readLine();
        double max = blood.get(succeed);
        
        for(int i = 0; i < M-1; i++){
            String s = br.readLine();

            if(blood.getOrDefault(s, -1d) > max){
                max = blood.get(s);
                succeed = s;
            }
        }

        System.out.println(succeed);
    }

    private static double dfs(String kid){
        if(blood.get(kid) != -1){
            return blood.get(kid);
        }

        if(family.get(kid) == null){
            blood.put(kid, 0d);
            return 0;
        }

        double p1 = dfs(family.get(kid)[0]);
        double p2 = dfs(family.get(kid)[1]);

        blood.put(kid, (p1+p2) / 2);

        return blood.get(kid);
    }
}