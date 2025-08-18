
import java.io.*;
import java.util.*;


public class Main_1202_김회인 {
    public static void main(String[] args) throws IOException {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
       StringTokenizer st = new StringTokenizer(br.readLine());
       
       int N = Integer.parseInt(st.nextToken());
       int K = Integer.parseInt(st.nextToken());
       
       ArrayList<ArrayList<Integer>> j = new ArrayList<>();
       /*
       PriorityQueue<ArrayList<Integer>> j = new PriorityQueue<>((o1,o2) ->{
    	   if(o1.get(0) == o2.get(0))
    	   {
    		   return o1.get(1) - o2.get(1);
    	   }
    	   return o1.get(0) - o2.get(0);
       });
       */
       
       PriorityQueue<Integer> bag = new PriorityQueue<>();
       
       for(int i=0; i<N; i++)
       {
    	   st = new StringTokenizer(br.readLine());
    	   int a = Integer.parseInt(st.nextToken());
    	   int b = Integer.parseInt(st.nextToken());
    	   
    	   ArrayList<Integer> arrayList = new ArrayList<>(); 
    	   arrayList.add(a);
    	   arrayList.add(b);
    	   j.add(arrayList);
       }
       
       Collections.sort(j,(o1,o2) ->{
    	   if(o1.get(0).equals(o2.get(0)))
    	   {
    		   return o2.get(1) - o1.get(1); 
    	   }
    	   return o1.get(0) - o2.get(0);
       });
       
       for(int i=0; i<K; i++)
       {
    	   bag.add(Integer.parseInt(br.readLine()));
       }
       
       PriorityQueue<Integer> q = new PriorityQueue<>(Collections.reverseOrder());
       int startindex=0;
       long sum=0;
       
       while(!bag.isEmpty())
       {
    	   int bagweight = bag.poll();
    	   
    	   while(startindex < N && j.get(startindex).get(0) <= bagweight)
    	   {
    		   q.add(j.get(startindex).get(1));
    		   startindex++;
    	   }
    	   
    	   if(!q.isEmpty()) {
    		   sum += q.poll();
    	   }
       }
       
       bw.write(Long.toString(sum));
       bw.flush();
       bw.close();
   
       
    }
}

