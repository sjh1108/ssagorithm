package algorithm.d0907;

import java.io.*;
import java.util.*;

public class BOJ_1966_프린터큐 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int T = Integer.parseInt(br.readLine());                


        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());            
            int M = Integer.parseInt(st.nextToken());            

            Integer[] arr = new Integer[N];                      
            ArrayDeque<Integer> q = new ArrayDeque<>();          
            ArrayDeque<Boolean> mark = new ArrayDeque<>();      

            st = new StringTokenizer(br.readLine());             
            for (int i = 0; i < N; i++) {
                int p = Integer.parseInt(st.nextToken());
                arr[i] = p;                                     
                q.addLast(p);                                   
                mark.addLast(i == M);                           
            }

            Arrays.sort(arr, Collections.reverseOrder());       
            int idx = 0;                                        
            int printed = 0;                                    

            while (idx < N) {
                if (!arr[idx].equals(q.peekFirst())) {          
                    q.addLast(q.pollFirst());
                    mark.addLast(mark.pollFirst());
                } else {                                        
                    if (mark.peekFirst()) {                     
                        out.append(printed + 1).append('\n');
                        break;
                    } else {
                        q.pollFirst();
                        mark.pollFirst();
                        idx++;                                  
                        printed++;                             
                    }
                }
            }
        }

        System.out.print(out.toString());
    }
}
