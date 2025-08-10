package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_1987_김회인 {
	static int delta[][] = {{1,0},{-1,0},{0,1},{0,-1}};
	static boolean [] visit;
	static int r;
	static int c;
	static int count=0;
	static int max_count=0;
	static Stack<Character> s = new Stack<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		char [][] ch= new char[r][c];
		visit = new boolean['Z'-'A'+1];
		
		for(int i=0; i<r; i++)
		{
			String s = br.readLine();
			for(int j=0; j<c;j++)
			{
				ch[i][j] = s.charAt(j);
			}
		}
		count=1;
		visit[ch[0][0]-'A'] =true;
		
		dfs(ch,0,0);
		
		bw.write(Integer.toString(max_count));
		bw.flush();
		bw.close();
	}
	
	static void dfs(char [][]ch,int x, int y)
	{
		if(max_count < count) max_count = count;
		
		for(int i=0; i<delta.length; i++)
		{
			int dx = x + delta[i][0];
			int dy = y + delta[i][1];
			
			if(dx >= 0 && dx<r && dy >=0 && dy<c && visit[ch[dx][dy]-'A'] ==false)
			{
				count++;
				visit[ch[dx][dy]-'A'] = true;
				dfs(ch,dx,dy);
				visit[ch[dx][dy]-'A'] = false;
				count--;
			}
		}
	}
	
}

