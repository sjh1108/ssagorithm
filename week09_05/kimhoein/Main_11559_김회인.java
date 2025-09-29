package study;
import java.io.*;
import java.util.*;

public class Main_11559_김회인 {
   
   static class Node
   {
      int x;
      int y;
      
      Node(int x, int y)
      {
         this.x = x;
         this.y = y;
      }
   }
   static char map[][] = new char[12][6];
   static int delta[][] = {{-1,0},{1,0},{0,-1},{0,1}};
   static List<Node> puyo = new LinkedList<>();
   static int pung_count=0;
   static boolean visit[][] = new boolean[12][6];
   
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        for(int i=0; i<12;i++)
        {
           String s = br.readLine();
           
           for(int j=0; j<6;j++)
           {
              //char c = s.charAt(j);
              map[i][j] = s.charAt(j);
           }
        }
        int count=0;
        while(true)
        {
        	visit = new boolean[12][6];
           puyo_game();
           if(pung_count == 0) break;
           
//           for(int i=0; i<12;i++)
//           {
//               for(int j=0; j<6;j++)
//               {
//                 System.out.print(map[i][j]);
//               }
//               System.out.println("");
//            }
           
           puyo_turn();           
           pung_count=0;
           count++;
           
//           for(int i=0; i<12;i++)
//           {
//               for(int j=0; j<6;j++)
//               {
//                 System.out.print(map[i][j]);
//               }
//               System.out.println("");
//            }
        }
        
        bw.write(Long.toString(count));
        bw.flush();
        bw.close();
        br.close();
    }
    
    static void puyo_game()
    {
       int count=0;
       
        for(int i=0; i<12; i++)
         {
            for(int j=0; j<6;j++)
            {
               //System.out
               if(map[i][j]=='.' || visit[i][j]) continue;
               visit[i][j] = true;
               puyo.add(new Node(i,j));
               dfs(map[i][j],i,j);
               
               if(puyo.size()>=4) pung();      // 4개 이상 뿌요가 모인다면 펑 함수 실행
               
               puyo.clear();
            }
         }
         
    }
    
    static void dfs(char c,int x, int y)
    {
       for(int i=0; i<4; i++)
       {
          int dx = x + delta[i][0];
          int dy = y + delta[i][1];
          
          if(!(dx >=0 && dx<map.length && dy >=0 && dy < map[0].length))continue;
          
          if(map[dx][dy] != c || visit[dx][dy]) continue;
          
          //System.out.println("c : " + c  + " dx : " + dx + " dy : " + dy);
          puyo.add(new Node(dx,dy));
          visit[dx][dy] = true;
          dfs(c,dx,dy);
       }
    }
    
    static void pung()
    {
       pung_count++;
       for(Node n :puyo)
       {
          map[n.x][n.y] = '.';
       }
    }
    
    static void puyo_turn()
    {
       for(int i=11; i>=0; i--)
       {
          for(int j=0; j<6;j++)
          {
             if(map[i][j]=='.') continue;
             
             
             puyo_down(map[i][j],i,j);
          }
       }
    }
    
    static void puyo_down(char c,int x, int y)
    {
       if(x==11) return;
       
       if(map[x+1][y] =='.')
       {
          map[x+1][y] = c;
          map[x][y] = '.';
          puyo_down(c,x+1,y);
       }
    }

 
   
}
