import java.io.*;
import java.util.*;

class Solution {
    static boolean[] visit;
    static int Ecount;
    static List<Integer>[] listwires;
    static int minCount; // 송전선 차이가 가장 적은 변수 저장

    
    public int solution(int n, int[][] wires) {
        int answer = -1;
        minCount = n;
        
        listwires = new ArrayList[n + 1];

        for(int i=0; i<=n; i++)
        {
            listwires[i] = new ArrayList<>();
        }
        
        for(int i=0; i<wires.length; i++)
        {
            
            int a = wires[i][0];
            int b = wires[i][1];
            
            listwires[a].add(b);
            listwires[b].add(a);
            
        }
        
        for(int i=0; i<wires.length; i++)
        {
            int start = wires[i][0];
            
             // [수정 3] 전선을 새로 끊어볼 때마다 방문 배열 초기화
            visit = new boolean[n + 1];

            // [수정 4] 송전탑 개수도 다시 0부터 계산
            Ecount = 0;


            // [수정 5] 시작 정점 방문 처리
            visit[start] = true;
            
            visit[wires[i][1]] = true;
            
            dfs(start, 0);
            
            // (총합 - 구한 값) - 구한 값 abs
            
            int total = Math.abs(n-Ecount*2);
            
            minCount = Math.min(minCount, total);
        }
        
        answer = minCount;
        
        return answer;
    }
    
    public void dfs(int start, int count)
    {
          Ecount++;
        
           for(int i : listwires[start])
           {
               if(visit[i]) continue;
               
               visit[i] = true;
               
               dfs(i, count+1);
           }
    }
}
