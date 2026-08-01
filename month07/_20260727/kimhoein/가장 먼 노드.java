import java.io.*;
import java.util.*;

class Solution {
    
    class Node
    {
        int node;
        int count;
        
        Node(int node, int count)
        {
            this.node = node;
            this.count = count;
        }
    }
    
    static int count =1;
    static int far = 0;
    static List<Integer>[] list_edge;
    static boolean[] visit;
    
    public int solution(int n, int[][] edge) {
        int answer = 0;
        list_edge = new ArrayList[n+1];
        visit = new boolean[n+1];
        
        for(int i=0; i <=n; i++)
        {
            list_edge[i] = new ArrayList<>();
        }
        
        for(int i=0; i<edge.length; i++)
        {
            int a = edge[i][0];
            int b = edge[i][1];
            
            list_edge[a].add(b);
            list_edge[b].add(a);
        }
        
        bfs();
        //System.out.println("far : " + far + " count : " + count);
        answer = count;
        
        return answer;
    }
    
    public void bfs()
    {
        Queue<Node> q = new ArrayDeque();
        //System.out.println("bfs");
        q.add(new Node(1,0));
        visit[1] = true;
        
        while(!q.isEmpty())
        {
            //System.out.println("for in");
            
            Node cur = q.poll();
            for(int next : list_edge[cur.node])
            {
               
                if(visit[next]) continue;
                visit[next] = true;
                
                Node nextnode = new Node(next, cur.count+1);
                if(nextnode.count == far) count++;
                if(nextnode.count > far){
                    far = nextnode.count;
                    count =1;
                }
                
                q.add(nextnode);
                
            }
        }
        
    }
}
