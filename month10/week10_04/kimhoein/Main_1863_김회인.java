import java.io.*;
import java.util.*;

public class Main_1863_김회인 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(br.readLine());
		Stack<Integer> stack = new Stack<>();
		int count=0;
		int height=0;
		
		for(int i=0; i<n; i++)
		{
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			if(height < b)
			{
				stack.push(b - height);
				height = b;
			}
			else if(height > b)
			{
				int down = height -b;
				//System.out.println("pop " + height + " " + b + " " + down + " " + stack.size());
				height = b;
				while(!stack.isEmpty() && 0 < down)
				{
					//System.out.println("pop in : " + i + "stack.peek() : " + stack.peek() + " " + down);
					down-= stack.pop();
					//System.out.println("down : " + down);
					if(down <0) stack.push(down*-1);
					count++;
				}
				
			}
		}
		//System.out.println("count : " + count + "height : " + height + "stack.size : " + stack.size());
		
		if(!stack.isEmpty()) count+= stack.size();
		
		bw.write(Integer.toString(count));
        bw.flush();
		bw.close();
		br.close();
	}
	

}
