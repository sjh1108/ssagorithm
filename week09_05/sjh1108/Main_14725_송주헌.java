package week09_05.sjh1108;

import java.io.*;
import java.util.*;

class Main {
    // 트라이 알고리즘의 기본 골조
    static class Node{
        Map<String, Node> children;
        public Node(){
            this.children = new HashMap<>();
        }
    }
    private static int N;
    private static Node root;
    private static StringBuilder sb;

    private static void print(Node root, int depth){
        PriorityQueue<String> pq = new PriorityQueue<>(root.children.keySet());
        String depthString = "";

        for(int i = 0; i < depth; i++){
            depthString += "--";
        }

        while(!pq.isEmpty()){
            String cur = pq.poll();

            sb.append(depthString + cur + "\n");
            print(root.children.get(cur), depth+1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        root = new Node();
        sb = new StringBuilder();

        StringTokenizer st;
        while(N-- > 0){
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());

            Node cur = root;
            while(K-- > 0){
                String leaf = st.nextToken();

                // 리프 노드가 아니라면 리프 노드로 추가
                if(!cur.children.containsKey(leaf)){
                    cur.children.put(leaf, new Node());
                }
                cur = cur.children.get(leaf);
            }
        }

        print(root, 0);

        System.out.println(sb);
    }
}