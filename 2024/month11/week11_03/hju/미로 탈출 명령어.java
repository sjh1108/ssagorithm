import java.util.*;

class Solution {
    
    static int N, M;
    static int dx[] = {1, 0, 0, -1};
    static int dy[] = {0, -1, 1, 0};
    static String ds[] = {"d", "l", "r", "u"};
    
    static class Edge implements Comparable<Edge>{
        int x, y;
        String s;
        
        public Edge(int x, int y, String s) {
            this.x = x;
            this.y = y;
            this.s = s;
        }
        
        // 사전 순 정렬
        public int compareTo(Edge o) {
            return this.s.compareTo(o.s);
        }
    }
    
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        String answer = "";
        N = n;
        M = m;
        
        answer = bfs(x, y, r, c, k);
        return answer;
    }
    
    static boolean isIn(int y, int x) {
        return y > 0 && y <= N && x > 0 && x <= M;
    }
    
    static String bfs(int x, int y, int r, int c, int k) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(x, y, ""));
        
        while(!pq.isEmpty()) {
            Edge cur = pq.poll();
            
            int mStep = k - cur.s.length();
            int mDist = Math.abs(r - cur.x) + Math.abs(c - cur.y);
            
            // 가지치기가 핵심
            if(mStep % 2 != mDist % 2)  continue;
            if(mStep < mDist) continue;
            if(cur.x == r && cur.y == c && cur.s.length() == k)   return cur.s;
            
            for(int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                
                if(!isIn(nx, ny))   continue;
                
                pq.add(new Edge(nx, ny, cur.s + ds[d]));
            }
        }
        
        return "impossible";
    }
}