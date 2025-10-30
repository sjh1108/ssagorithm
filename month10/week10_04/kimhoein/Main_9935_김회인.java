import java.io.*;
import java.util.*;

public class Main_9935_김회인 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		String s = br.readLine();
		String find = br.readLine();
		Stack<Character> stack = new Stack<>();
		boolean flag  = false;
		
		
		for(int i=0; i<s.length(); i++)
		{
			stack.push(s.charAt(i));
			
			if(stack.size() >= find.length())
			{
				flag = true;
				for(int j=0; j<find.length(); j++)
				{
					//System.out.println((stack.size()-find.length() + j) + "  " + (j));
					if(flag && stack.get(stack.size()-find.length() + j) != find.charAt(j)) flag = false;
				}
				
				if(flag)
				{
					for(int j=0; j<find.length(); j++) stack.pop();
				}
			}
		}
		
		if(stack.isEmpty()) 
		{
			bw.write("FRULA");
		}
		
		for(char c : stack)
		{
			bw.write(c);
		}
		//bw.write(stack.pop());
        bw.flush();
		bw.close();
		br.close();
	}
	

}
