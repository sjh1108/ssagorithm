package week01_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

// 덱2 실버4
public class Main_28279 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        
        ArrayDeque<Integer> q = new ArrayDeque<>(); 
        
        int n = Integer.parseInt(br.readLine());
        
        for (int i = 0; i < n; i++) {
        	StringTokenizer st = new StringTokenizer(br.readLine());
        	int cmd = Integer.parseInt(st.nextToken());
        	
        	switch(cmd) {
        		
        		case 1:{
        			int x = Integer.parseInt(st.nextToken());
        			q.addFirst(x);
        			break;
        		}
        		
        		case 2: {
        			int x = Integer.parseInt(st.nextToken());
        			q.addLast(x);
        			break;
        		}
        		
        		case 3:{
        			sb.append(q.isEmpty() ? -1 : q.pollFirst()).append("\n");
        			break;
        		}
        		
        		case 4:{
        			sb.append(q.isEmpty() ? -1 : q.pollLast()).append("\n");
        			break;
        		}
        		
        		case 5:{
        			sb.append(q.size()).append("\n");
        			break;
        		}
        		
        		case 6:{
        			sb.append(q.isEmpty() ? 1 : 0).append("\n");
        			break;
        		}
        		
        		case 7:{
        			sb.append(q.isEmpty() ? -1 : q.peekFirst()).append("\n");
        			break;
        		}
        		
        		case 8:{
        			sb.append(q.isEmpty() ? -1 : q.peekLast()).append("\n");
        		}
        	}
		}
        System.out.println(sb);
	}
}
