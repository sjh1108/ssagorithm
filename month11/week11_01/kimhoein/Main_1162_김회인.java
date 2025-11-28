package study;

import java.io.*;
import java.util.*;

/*
 * 다익스트라를 하되 visit을 3차원으로 늘려서 카운트 별 숫자를 세는게 중요
 * + 아래의 식을 이용해서 걸러네는것이 필요한데 아래 수식을 못써서 고생좀 했다.
 *  if(node.d > distance[node.node][node.count]) continue;
 */
class Main_1162_김회인 {
    
    static class Node implements Comparable<Node>
    {
        int node;
        long d;
        int count;

        Node(int node, long d, int count)
        {
            this.node = node;
            this.d = d;
            this.count = count;
        }

        @Override
        public int compareTo(Node o){
            return Long.compare(this.d,o.d);
        }


    }

    static int N;
    static int M;
    static int K;
    static List<Node>[] list;
    static Long[][] distance;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        list = new List[N+1];
        distance = new Long[N+1][K+1];

        for(int i =0; i<=N; i++)
        {
            list[i] = new LinkedList<Node>();
            Arrays.fill(distance[i], Long.MAX_VALUE);
        }

        for(int i=0; i<M;i++)
        {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b= Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            list[a].add(new Node(b, c, K));
            list[b].add(new Node(a, c, K));
        }

        bw.write(Long.toString(Dijkstra()));
        bw.flush();
        bw.close();
        br.close();

    }

    static public Long Dijkstra()
    {
        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.add(new Node(1,0,K));
        Arrays.fill(distance[1],0L);

        while(!pq.isEmpty())
        {
            Node node = pq.poll();

            if(node.d > distance[node.node][node.count]) continue;

            for(Node next : list[node.node])
            {
                //if(distance[next.node][node.count] < distance[node.node][node.count] && (node.count == 0 ||  node.count >0 && distance[next.node][node.count-1] > distance[node.node][node.count])) continue;

                if(distance[next.node][node.count] > distance[node.node][node.count] + next.d)
                {
                    distance[next.node][node.count] = distance[node.node][node.count] + next.d;
                    pq.add(new Node(next.node, distance[next.node][node.count],node.count));
                }

                if(node.count >0 && distance[next.node][node.count-1] > distance[node.node][node.count])
                {
                    distance[next.node][node.count-1] = distance[node.node][node.count];
                    pq.add(new Node(next.node, distance[node.node][node.count],node.count-1));
                }

            }
        }

        long ans = Long.MAX_VALUE;

        for(int i=0; i < distance[N].length; i++) if(ans >  distance[N][i]) ans = distance[N][i];

        return ans;
    }
}