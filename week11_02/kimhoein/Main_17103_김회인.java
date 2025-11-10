package study;
import java.io.*;
import java.util.*;

public class Main_17103_김회인 {

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        int T = Integer.parseInt(br.readLine());
        
        boolean[] isNotPrime = new boolean[1000000];
        
        isNotPrime[0] = true;
        isNotPrime[1] = true;
        
        //isNotPrime[999991] = true;
        for(int i=2; i*i<1000000; i++)
        {
        	if(!isNotPrime[i])
        	{
        		for(int j=i*i; j<1000000 ; j+=i)
        		{
        			//if(j < 0) System.out.println("j : " + j + " i : " + i);
        			//System.out.println("j : " + j + " i : " + i);
        			isNotPrime[j] = true;
        		}
        	}
        }
        
        List<Integer> list = new ArrayList<Integer>();
        
        for(int i=2; i<1000000; i++)
        {
        	if(!isNotPrime[i])
        	{
        		list.add(i);
        	}
        }
        //System.out.println("aa");
        for(int t=0; t<T; t++)
        {
        	
        	int start=0;
        	int end =0;
        	
        	
        	int count=0;
        	int n = Integer.parseInt(br.readLine());
        	
        	end = Collections.binarySearch(list, n);;
        	if(end < 0) end = -1*end-2;
        	
        	//System.out.println("n : " + n + "end : " + end);
        	while(start <= end)
        	{
        		//System.out.println("end : " + end);
        		int sum = list.get(start) + list.get(end);
        		
        		if(sum < n)
        		{
        			start++;
        		}
        		else if(sum > n)
        		{
        			end--;
        		}
        		else
        		{
        			start++;
        			end--;
        			count++;
        		}
        	}
        	
        	//System.out.println(list.get(start) + " " + list.get(end));
        	//if(list.get(start) + list.get(end) == n) count++;
        	
        	bw.write(Integer.toString(count) + "\n");
        }
        
        bw.flush();
        bw.close();
        br.close();
	}

}
