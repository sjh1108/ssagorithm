
import java.io.*;
import java.util.*;


public class Main_1655_김회인 {
    public static void main(String[] args) throws IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st;// = new StringTokenizer(br.readLine());
       
       int N = Integer.parseInt(br.readLine());
       PriorityQueue<Integer> q_min = new PriorityQueue<>();			// 중앙값 보다 큰 것 넣기
       PriorityQueue<Integer> q_max = new PriorityQueue<>(Collections.reverseOrder());		// 중앙값 보다 작은 것 넣기
       
       for(int i=0; i<N; i++)
       {
    	   int num = Integer.parseInt(br.readLine());
    	   //q_min.add(Integer.parseInt(br.readLine()));
    	   if(q_max.isEmpty())					// 중앙 값 보다 크면 q_min 입력 작으면 q_max
    	   {
    		   q_max.add(num);    		   
    	   }
    	   else
    	   {
    		   if(q_max.peek() < num)
    		   {
    			   q_min.add(num);
    	
    		   }
    		   else
    		   {
    			   q_max.add(num);
    		   }
    	   }
    	   
    	   if(q_min.size() - q_max.size() >=2)	// q_min q_max 차이가 2 이상 날 경우에는 루트 값 옮겨서 균형을 수호함	
    	   {
    		   q_max.add(q_min.poll());
    	   }
    	   else if(q_max.size() -q_min.size() >=2)
    	   {
    		   q_min.add(q_max.poll());
    	   }
    	   
    	   if(i%2 == 1)							// 만약 총 개수가 짝수 라면 q_max의 루트(같은 값이면 둘중 더 작은것을 고르므로)
    	   {
    		   //System.out.println("a1");
    		   bw.write( q_max.peek()+"\n");
    	   }
    	   else
    	   {
    		   if(q_min.size() > q_max.size())	// 총 개수가 홀 수 라면 좀 더 개수가 많은것에 중앙값 존재 하므로 많은거 찾아서 출력 
    		   {
    			   //System.out.println("a2");
    			   bw.write(q_min.peek() + "\n");
    		   }
    		   else
    		   {
    			   //System.out.println("a3");
    			   bw.write(q_max.peek() + "\n"); 
    		   }
    	   }
    	   
       }
       
       //bw.write(null);
       bw.flush();
       bw.close();
   
       
    }
}

