package study;

import java.io.*;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;


public class Main_21944_김회인 {
    /*
     * 알고리즘 종류
문제 번호

recommend G x
알고리즘 분류중 가장 어렵거나 쉬운 문제
x 1이면 가장 어려운 같은 조건이면 ID 큰 순
x -1이면 가장 쉬운 같은 조건이면 ID 작은 순

recommend2 x
전체중 가장 쉽거나 어려운 문제
1 가장 어려운 문제 / 같은 조건이면 ID 큰 순
-1 가장 쉬운 문제 / 같은 조건이면 ID 작은 순

recommend3 x
알고리즘 분류 상관 없이 난이도 L 기준으로 크거나 작은 문제
1 L 보다 크면서 가장 쉬운 문제 출력 같은 기준으로 ID 작은 순
-1 L 보다 작으면서 가장 어려운 문제 출력 같은 기준으로 ID 큰순

add P L G
난이가 L
알고리즘 분류 G
문제 번호 P 추가

solved
문제 번호 p 제거


node
int 문제 번호
int 문제 난이도

난이도 순으로 정렬
문제 번호 순으로 정렬

난이도 그리고 알고리즘 종류는 1~100 이라서 101로 배열 선언
문제 번호는 1~100000 100001로 배열 선언

pq_grade_max[101]<node>
pq_difficult_max[101]<node>
pq_all_max<node>

pq_grade_min[101]<node>
pq_difficult_min[101]<node>
pq_all_min<node>

Node[100001] node_all

recommend G 인 경우
x == 1, x == -1 인 경우 각각 나눠서 
pq_grade[] 이용 값 알려줌

recommend2
x == 1, x == -1 인 경우 각각 나눠서
pq_all_min, pq_min_max 이용해서 값 표출

recommend3
x == 1, x == -1 인 경우 각각 나누되
난이도 L for문으로 돌려주되
처음으로 null 이 아닌 pq의 peek 값을 뺀다
이유는 난이도 Lpq로 돌게 되면 어차피 가장 처음에 나온게 가장 쉽다

x==1 일 때는 min을
x==-1 일 때는 max 사용

add
처음 init 할때랑 비슷하게 하되면 된다

solved p all_node 에 저장되어 있는데 new node 해줘서 값 수정 해준다


update (pq)
현재 pq에서 peek 시 all_node와 주소값 같으면 진행
그렇지 않다면 poll 해줘서 값 빼주면서 진행 함



     */
	static class Node implements Comparable<Node>
	{
		int id;
		int difficult;
		
		Node(int id, int difficult)
		{
			this.id = id;
			this.difficult = difficult;
		}
		
		@Override
		public int compareTo(Node o) {
			
			if(this.difficult == o.difficult)
			{
				return this.id - o.id;
			}
			return this.difficult - o.difficult;
		}
		
	}
	
	static PriorityQueue<Node>[] pq_grade_max = new PriorityQueue[101];
	static PriorityQueue<Node>[] pq_difficult_max = new PriorityQueue[101];
	static PriorityQueue<Node> pq_all_max = new PriorityQueue<>(Collections.reverseOrder());
	
	static PriorityQueue<Node>[] pq_grade_min = new PriorityQueue[101];
	static PriorityQueue<Node>[] pq_difficult_min = new PriorityQueue[101];
	static PriorityQueue<Node> pq_all_min = new PriorityQueue<>();
	
	static Node[] node_all = new Node[100001];
	
    public static void main(String[] args) throws InterruptedException, IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st;//  = new StringTokenizer(br.readLine());
       
       for(int i=0; i<=100; i++)
       {
    	   pq_grade_max[i] = new PriorityQueue<>(Collections.reverseOrder());
    	   pq_difficult_max[i] = new PriorityQueue<>(Collections.reverseOrder());
    	   pq_grade_min[i] = new PriorityQueue<>();
    	   pq_difficult_min[i] = new PriorityQueue<>();
       }
       
       int N = Integer.parseInt(br.readLine());
      
       for(int i=0; i<N; i++)
       {
    	   st = new StringTokenizer(br.readLine());
    	   
    	   int P = Integer.parseInt(st.nextToken());
    	   int L = Integer.parseInt(st.nextToken());
    	   int G = Integer.parseInt(st.nextToken());
    	   
    	   Node node = new Node(P,L);
    	   
    	   pq_all_max.add(node);
    	   pq_grade_max[G].add(node);
    	   pq_difficult_max[L].add(node);
    	   
    	   pq_all_min.add(node);
    	   pq_grade_min[G].add(node);
    	   pq_difficult_min[L].add(node);
    	   
    	   node_all[P] = node;
       }
       
       int M = Integer.parseInt(br.readLine());
       
       for(int i=0; i<M; i++)
       {
    	   st = new StringTokenizer(br.readLine());
    	   
    	   String s = st.nextToken();
    	   
    	   int ans = -1;
    	   
    	   if(s.equals("recommend"))
    	   {
    		   int G = Integer.parseInt(st.nextToken());
    		   int x = Integer.parseInt(st.nextToken());
    		   
    		   if(x == 1)
    		   {
    			   update(pq_grade_max[G]);
    			   if(!pq_grade_max[G].isEmpty()) ans = pq_grade_max[G].peek().id;
    			   
    		   }
    		   else
    		   {
    			   update(pq_grade_min[G]);
    			   if(!pq_grade_min[G].isEmpty()) ans = pq_grade_min[G].peek().id;
    		   }
    	   }
    	   else if(s.equals("recommend2"))
    	   {
    		   int x = Integer.parseInt(st.nextToken());
    		   
    		   if(x == 1)
    		   {
    			   update(pq_all_max);
    			   if(!pq_all_max.isEmpty()) ans = pq_all_max.peek().id;
    			   
    		   }
    		   else
    		   {
    			   update(pq_all_min);
    			   if(!pq_all_min.isEmpty()) ans = pq_all_min.peek().id;
    		   }
    	   }
    	   else if(s.equals("recommend3"))
    	   {
    		   int x = Integer.parseInt(st.nextToken());
    		   int L = Integer.parseInt(st.nextToken());
    		   
    		   if(x == 1)
    		   {
    			   for(int j=L; j <=100; j++)
    			   {
    				   update(pq_difficult_min[j]);
    				   
    				   if(pq_difficult_min[j].isEmpty()) continue;
    				   
    				   ans = pq_difficult_min[j].peek().id;
    				   
    				   break;
    			   }
    		   }
    		   else
    		   {
    			   for(int j=L-1; j >=1; j--)
    			   {
    				   update(pq_difficult_max[j]);
    				   
    				   if(pq_difficult_max[j].isEmpty())continue;
    				   
    				   ans = pq_difficult_max[j].peek().id;
    				   
    				   break;
    			   }
    		   }
    	   }
    	   else if(s.equals("solved"))
    	   {
    		   int P = Integer.parseInt(st.nextToken());
    		   
    		   node_all[P] = new Node(0,0);
    		   continue;
    	   }
    	   else if(s.equals("add"))
    	   {
    		   int P = Integer.parseInt(st.nextToken());
        	   int L = Integer.parseInt(st.nextToken());
        	   int G = Integer.parseInt(st.nextToken());
        	   
        	   Node node = new Node(P,L);
        	   
        	   pq_all_max.add(node);
        	   pq_grade_max[G].add(node);
        	   pq_difficult_max[L].add(node);
        	   
        	   pq_all_min.add(node);
        	   pq_grade_min[G].add(node);
        	   pq_difficult_min[L].add(node);
        	   
        	   node_all[P] = node;
    		   continue;
    	   }
    	   
    	   bw.write(Integer.toString(ans) + "\n");
    	   
       }
       
       bw.flush();
       bw.close();

    }
    
    static void update(PriorityQueue<Node> pq)
    {
    	while(!pq.isEmpty())
    	{
    		if(pq.peek() == node_all[pq.peek().id]) break;
    		pq.poll();
    	}
    }
    
    
}
