package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1107_김회인 {
	static boolean visit[] = new boolean[10];
	static int num;
	static int min;
	static int digits;
	public static void main(String[] args) throws IOException {
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = null;// = new StringTokenizer(br.readLine());
		
		
		num = Integer.parseInt(br.readLine());
		digits = Integer.toString(num).length();
		
		int N = Integer.parseInt(br.readLine());
		
		if(N!=0) st = new StringTokenizer(br.readLine());
		for(int i=0; i<N; i++)
		{
			int a= Integer.parseInt(st.nextToken());
			visit[a] = true;
		}
		 
		
		min = Math.abs(num-100);
				//Integer.parseInt(s)
		
		depth(0,0);
		
		bw.write(Integer.toString(min));
		bw.flush();
		bw.close();
	}
	
	static void depth(int d, int number)
	{
		//System.out.println("a");
		int total = Math.abs(num - number) + d;
		//System.out.println("number : " + number + "d : " + d);
		//if(d!=0 && total > min) return;
		if(d!=0 && total < min)
		{
			//System.out.println("number : " + number);
			//if(number==0) total++;
			min = total;
		}
		
		if(digits < d) return;
		
		for(int i=0; i<10; i++)
		{
			if(visit[i]) continue;
			
			depth(d+1, number*10 + i);
		}
	}
}
