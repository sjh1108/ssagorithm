package study;

import java.io.*;
import java.util.*;

public class Main_1744_김회인{

	
	
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	
    	PriorityQueue<Integer> pq_p = new PriorityQueue<>(Collections.reverseOrder());
    	PriorityQueue<Integer> pq_m = new PriorityQueue<>();
    	int zero = 1;
    	
    	int n = Integer.parseInt(br.readLine());
    	
    	for(int i=0; i<n; i++) {
    		int num = Integer.parseInt(br.readLine());
    		if(num == 0) zero = 0;
    		else if(num < 0) pq_m.add(num);
    		else pq_p.add(num);	
    	}
    	int total = 0;
    	
    	while(!pq_p.isEmpty()) {
    		int a = pq_p.poll();
    		
    		if(pq_p.isEmpty()) {
    			total += a;
    			break;
    		}
    		int b = pq_p.poll();
    		
    		total += Math.max(a+b, a*b);
    	}
    	
    	while(!pq_m.isEmpty()) {
    		int a = pq_m.poll();
    		
    		if(pq_m.isEmpty()) {
    			total += a * zero;
    			break;
    		}
    		
    		int b = pq_m.poll();
    		
    		total += Math.max(a+b, a*b);
    	}
    	
    	bw.write(Integer.toString(total));
    	bw.flush();
    	bw.close();
    	br.close();
    }
	

}
