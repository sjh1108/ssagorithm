package study;
import java.io.*;
import java.util.*;

public class Main_1918_김회인 {
	/*
	 * 후위 순회를 생각 하면서 풀면 되는 부분
	 * )가 끝나거나 더 같은 수준의 식이 나와서 pop 해줄때 생각을 잘못해주어서 문제 풀이에 시간이 걸렸다.
	 */
	
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	StringBuilder sb = new StringBuilder();
    	
    	HashMap<Character, Integer> hash = new HashMap<>();
    	Deque<Character> dq = new ArrayDeque<>();
    	
    	hash.put('+', 1);
    	hash.put('-', 1);
    	hash.put('*',2);
    	hash.put('/',2);
    	
    	String s = br.readLine();
    	
    	for(int i=0; i<s.length(); i++) {
    		if(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') 
    		{
    			sb.append(s.charAt(i));
    		}
    		else if(s.charAt(i) == '(')
    		{
    			dq.add(s.charAt(i));
    			//System.out.println("( " + i + " " + dq.toString());
    		}
    		else if(s.charAt(i) == ')') 
    		{
    			//System.out.println(i + " " + dq.toString());
    			while(!dq.isEmpty()) {
    				char c = dq.pollLast();
    				if(c == '(') break;
    				//System.out.println(i + " c : " + c);
    				sb.append(c);
    			}
    		}
    		else if(dq.isEmpty())
    		{
    			dq.add(s.charAt(i));
    		}
    		else { // 4. 사칙 연산자 (+, -, *, /)
                // 현재 스택의 맨 위 연산자가 현재 연산자보다 
                // 우선순위가 높거나 같으면 팝(Pop)하고 출력한다.
    			
    			char cur = s.charAt(i);
    			
    			while(!dq.isEmpty()) {
                    char peek = dq.peekLast();
                    
                    // 여는 괄호는 가장 낮은 우선순위로 간주하여 팝하지 않고 멈춤
                    if(peek == '(') break; 
                    
                    // 스택 맨 위 연산자의 우선순위가 현재 연산자보다 낮으면 멈춤
                    if(hash.get(peek) < hash.get(cur)) break;

                    // 우선순위가 높거나 같으므로 팝하고 출력
    				sb.append(dq.pollLast());
    			}
    			// 팝이 끝난 후 현재 연산자를 스택에 푸시
    			dq.add(cur);
    		}
    	}
    	
    	while(!dq.isEmpty()) {
			char c = dq.pollLast();
			//System.out.println("c : " + c);
			sb.append(c);
		}
    	
    	System.out.println(sb);
//    	bw.write(sb);
//    	bw.flush();
//    	bw.close();
    	br.close();
    }
    
}
