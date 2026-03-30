package _20260330.thdwngjs.BOJ_2313;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        long max = 0;

        StringBuilder sb = new StringBuilder();
        while(N-- > 0){
            int L = Integer.parseInt(br.readLine());
            
            int ans = -1_000_000_000, sum = -1_000_000_000;
            int al, l, ar, r;
            al = l = ar = r = 1;
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= L; i++){
                int d = Integer.parseInt(st.nextToken());

                if(sum < 0){
                    sum = d;
                    l = r = i;
                } else{
                    if(sum != 0) r++;
                    else l = r = i;
                    sum += d;
                }

                if(ans < sum || (ans == sum && r-l < ar - al)){
                    ans = sum;
                    al = l; ar = r;
                }
            }
            
            sb.append(al).append(" ").append(ar);
            sb.append('\n');

            max += ans;
        }
        System.out.println(max);
        System.out.println(sb);
    }
}