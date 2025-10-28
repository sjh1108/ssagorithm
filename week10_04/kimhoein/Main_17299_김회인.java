import java.io.*;
import java.util.*;

public class Main_17299_김회인 {
   
   
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(br.readLine());
       
        int[] count = new int[1000001];
        int[] array = new int[n];
        int[] ans = new int[n];
        Stack<Integer> stack = new Stack<Integer>();
        
        st = new StringTokenizer(br.readLine());
        
        for(int i=0; i<n; i++)
        {
           int num = Integer.parseInt(st.nextToken());
           array[i] = num;
           count[num]++;
        }
        
        for(int i=0; i<n; i++)
        {
//        	if(!stack.isEmpty()) System.out.println(!stack.isEmpty() + " " + stack.peek()  +" "+ count[array[stack.peek()]] + " " + count[array[i]]);
//        	else System.out.println("stack.isEmpty()"); 

        	
			while(!stack.isEmpty() && count[array[stack.peek()]] < count[array[i]])
			{
				//System.out.println("i : " + i);
				int j = stack.pop();
				ans[j] = array[i];
			}
			stack.push(i);
           
        }
        
        for(int i : stack)
        {
        	ans[i] = -1;
        }
        
        for(int i : ans)
        {
        	bw.write(Integer.toString(i) + " ");
        }
        
        //bw.write(Long.toString(count));
        bw.flush();
        bw.close();
        br.close();
    
    }

 
   
}
