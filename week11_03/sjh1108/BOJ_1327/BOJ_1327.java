package week11_03.sjh1108.BOJ_1327;

import java.util.*;
import java.io.*;

class Main {
    private static int N, K;

    private static String num, ans;
    private static Set<String> visited = new HashSet<>();
    
    static class Node{
        String n;
        int cnt;
        public Node(String n, int cnt) {
            this.n = n;
            this.cnt = cnt;
        }
    }
    
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        num = "";
        for(int i = 0; i < N; i++){
            num += st.nextToken();
            sb.append(i+1);
        }
        ans = sb.toString();

        System.out.println(bfs());
    }

    private static int bfs(){
        Queue<Node> q = new ArrayDeque<>();

        q.add(new Node(num, 0));

        while(!q.isEmpty()){
            Node cur = q.poll();

            if(cur.n.equals(ans)){
                return cur.cnt;
            }

            for(int i = 0; i <= N-K; i++){
                char[] arr = cur.n.toCharArray();

                for(int j = 0; j < K/2; j++){
                    char tmp = arr[i+j];
                    arr[i+j] = arr[i + K - 1 - j];
                    arr[i + K - 1 - j] = tmp;
                }

                String nxt = new String(arr);

                if(!visited.contains(nxt)){
                    q.add(new Node(nxt, cur.cnt+1));
                    visited.add(nxt);
                }
            }
        }
        
        return -1;
    }
}