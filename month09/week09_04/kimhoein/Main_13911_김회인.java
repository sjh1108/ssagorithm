package study;

import java.io.*;
import java.util.*;

public class Main_13911_김회인 {
	static class Edge implements Comparable<Edge>
	{
		int to;
		int cost;
		int mac_distance;
		int star_distance;
		boolean mac;
		boolean star;
		
		Edge(int to, int cost)
		{
			this.to = to;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Edge o) {
			return this.cost - o.cost;
		}
		
	}
	
   static int V,E;
   static ArrayList<Edge>[] alist;
   static int m_distance;
   static int s_distance;
   static HashSet<Integer> mac =new HashSet();
   static HashSet<Integer> star = new HashSet();
   static boolean mac_visit;
   static boolean star_visit;
   static boolean home_visit;
   //static int distance[];
   static int min = -1;
   
   public static void main(String[] args) throws IOException {
	   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st = new StringTokenizer(br.readLine());
        
       V = Integer.parseInt(st.nextToken());
       E = Integer.parseInt(st.nextToken());
        
       alist = new ArrayList[V+1];
      
       
       for(int i=0; i<V+1; i++)
       {
    	   alist[i] = new ArrayList<>();
       }
        
       for(int i=0; i<E; i++)
       {
    	   st = new StringTokenizer(br.readLine());
    	   int a = Integer.parseInt(st.nextToken());
    	   int b = Integer.parseInt(st.nextToken());
    	   int c = Integer.parseInt(st.nextToken());
        	
    	   alist[a].add(new Edge(b,c));
    	   alist[b].add(new Edge(a,c));
        	
       }
       
       st = new StringTokenizer(br.readLine());
       
       int m_count = Integer.parseInt(st.nextToken());
       m_distance = Integer.parseInt(st.nextToken());
       st = new StringTokenizer(br.readLine());
       
       for(int i=0; i<m_count; i++)
       {
    	   mac.add(Integer.parseInt(st.nextToken()));
       }
       
       st = new StringTokenizer(br.readLine());
       
       int s_count = Integer.parseInt(st.nextToken());
       s_distance = Integer.parseInt(st.nextToken());
       st = new StringTokenizer(br.readLine());
       
       for(int i=0; i<s_count; i++)
       {
    	   star.add(Integer.parseInt(st.nextToken()));
       }
       
       //for(int i : mac)
       {
    	   //System.out.println("mac : " + i);
       }
       da();	
       
       for(int i=1; i<=V;i++)
       {
    	   //System.out.print("a");
    	   //System.out.print(i + " " + mac.contains(i)+ " ");
       }
       
       bw.write(Integer.toString(min));
       bw.flush();
       bw.close();
       br.close();
    }
   
   static void da()		// 처음에 맥날 혹은 star의 주소 가져옴
   {
	   int distance_mac[] = new int[V+1];
	   int distance_star[] = new int[V+1];
	   
	   Arrays.fill(distance_mac, Integer.MAX_VALUE);
	   Arrays.fill(distance_star, Integer.MAX_VALUE);
	   
	   PriorityQueue<Edge> pq = new PriorityQueue<>();
	   for(int i : mac) 
	   {
		  Edge e = new Edge(i,0);
		  e.mac = true;
		  distance_mac[i] = 0;
		  pq.add(e);
	   }
	   
	   for(int i : star)
	   {
		   Edge e = new Edge(i,0);
		   e.star = true;
		   distance_star[i] = 0;
		   pq.add(e);
	   }
	   
	   while(!pq.isEmpty())
	   {
		   Edge cur = pq.poll();
		   
		   for(Edge next : alist[cur.to])
		   {
			   //System.out.println("cur.star : " + cur.star + " cur.mac " + cur.mac + " 바깥 cur.to : " + cur.to + " mac : next.to :" + next.to);
			   
			   if(cur.mac && distance_mac[next.to] > next.cost + distance_mac[cur.to] && next.cost + distance_mac[cur.to] <= m_distance) // 맥도날드 라면?
			   {
				   distance_mac[next.to] = next.cost + distance_mac[cur.to];
				   Edge e = new Edge(next.to,next.cost);
				   e.mac = true;
				   if(min!=-1 && min < distance_star[next.to] + distance_mac[next.to]) continue;
				   pq.add(e);
				   //System.out.println("cur.to : " + cur.to + " mac : next.to :" + next.to);
			   }
			   
			   if(cur.star && distance_star[next.to] > next.cost + distance_star[cur.to] && next.cost + distance_star[cur.to] <= s_distance) // 맥도날드 라면?
			   {
				   distance_star[next.to] = next.cost + distance_star[cur.to];
				   Edge e = new Edge(next.to,next.cost);
				   e.star = true;
				   if(min!=-1 && min < distance_star[next.to] + distance_mac[next.to]) continue;
				   pq.add(e);
				  //System.out.println("cur.to : " + cur.to + "star : next.to :" + next.to);
			   }
			   
			   if(distance_mac[next.to] == Integer.MAX_VALUE || distance_star[next.to] == Integer.MAX_VALUE) continue;
			   if(mac.contains(next.to) || star.contains(next.to)) continue;
			   int temp = distance_star[next.to] + distance_mac[next.to];
			   if(min == -1 || min > temp) min = temp;
			   
		   }
	   }
	   
   }
   
   static boolean check_visit(int node)
   {
	   return mac_visit && star_visit && home_visit;
   }
}
