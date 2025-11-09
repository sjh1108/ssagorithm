package study;
import java.io.*;
import java.util.*;

public class Main_2164_김회인 {

    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	
    	int n = Integer.parseInt(br.readLine());
    	Queue<Integer> q = new LinkedList<Integer>();
    	
    	for(int i=1; i<=n; i++)
    	{
    		q.add(i);
    	}
    	
    	while(true)
    	{
    		if(q.size() == 1)
			{
    			System.out.println(q.peek());
    			break;
			}
    		
    		q.poll();
    		
    		int temp = q.poll();
    		q.add(temp);
    	}
    	
    	//bw.write(Integer.toString(ans));
    	bw.flush();
    	bw.close();
    	br.close();
    }
    

}
