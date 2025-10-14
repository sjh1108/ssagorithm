package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_15686_김회인 {
	static class Node
	{
		int x;
		int y;
		
		public Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		
	}
	static int min=Integer.MAX_VALUE;
	static int m;
	static List<Node> chicken_list = new LinkedList<>();
	static List<Node> home_list = new LinkedList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<n; i++)
		{
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++)
			{
				int a = Integer.parseInt(st.nextToken());
				if(a == 1) home_list.add(new Node(i,j));
				if(a == 2) chicken_list.add(new Node(i,j));
			}
		}
		
		List<Node> list = new LinkedList<Node>();
		
		combination(0,list);
		bw.write(Integer.toString(min));
		bw.flush();
		bw.close();
		br.close();
	}
	
	static public void combination(int depth,List<Node> list)
	{
		if(list.size() == m)
		{
			//System.out.println("depth : " + depth);
			int cd = chicken_distance(list);
			if(cd < min) min = cd;
			return;
		}
		
		if(depth == chicken_list.size()) return;
			
		list.add(chicken_list.get(depth));
		combination(depth+1,list);
		
		//System.out.println("list.size(222 : ");
		list.remove(chicken_list.get(depth));
		combination(depth+1,list);
		
	}
	
	static public int chicken_distance(List<Node> list)
	{
		int total_min=0;
		
		for(Node home : home_list)
		{
			int min=Integer.MAX_VALUE;
			for(Node chicken : list)
			{
				//System.out.println("min : " + home.x + " " + home.y + " " + chicken.x + " " + chicken.y);
				int distance =  Math.abs( home.x - chicken.x) + Math.abs(home.y - chicken.y);
				if(min > distance) min = distance;
			}
			//System.out.println("min : " + min);
			total_min += min;
		}
		//System.out.println("total_min : " + total_min);
		return total_min;
	}
}
