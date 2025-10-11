package week10_02.sjh1108.BOJ_16928;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        N += Integer.parseInt(st.nextToken());

        HashMap<Integer, Integer> ladderSnake = new HashMap<>();
        while(N-- > 0){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            ladderSnake.put(a, b);
        }

        boolean[] visited = new boolean[101];
        int[] arr = new int[101];

        Queue<Integer> q = new ArrayDeque<>();
        q.add(1);
        arr[1] = 0;
        visited[1] = true;

        while(!q.isEmpty()){
            int cur = q.poll();

            for(int i = 1; i <= 6; i++){
                int nxt = cur + i;

                if(nxt > 100) break;
                if(visited[nxt]) continue;
                
                visited[nxt] = true;

                if(ladderSnake.containsKey(nxt)){
                    int ls = ladderSnake.get(nxt);
                    if(!visited[ls]){
                        visited[ls] = true;
                        q.add(ls);
                        arr[ls] = arr[cur]+1;
                    }
                } else{
                    if(nxt == 100){
                        System.out.println(arr[cur] + 1);
                        return;
                    }

                    q.add(nxt);
                    arr[nxt] = arr[cur] + 1;
                }
            }
        }
    }
}