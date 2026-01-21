package month08.week02.kimhoein;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_1759_김회인 {
	static int L;
	static int C;
	static char[] check_gather = {'a','e','i','o','u'};
	static ArrayList<Character> ch_list = new ArrayList<>();
	static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	public static void main(String[] args) throws IOException {
		BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
		//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		L = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		char[] ch = new char[C];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<C; i++)
		{
			ch[i] = st.nextToken().charAt(0);
		}
		
		Arrays.sort(ch);
		
		depth(ch,0);
		bw.flush();
		bw.close();
	}	
	
	static void depth(char[] ch, int d) throws IOException
	{
		if(ch_list.size()==L)		// 요구한 만큼의 단어 개수가 충족 되었을때
		{
			//System.out.println("aa");
			int gather_count=0;
			
			for(int i =0; i < ch_list.size(); i++)
			{
				for(int j=0; j<5; j++)
				{
					
					if(ch_list.get(i) == check_gather[j])
					{
						gather_count++;
					}
				}
			}
			
			//System.out.println("gather_count : " + gather_count);
			
			if(gather_count >= 1 && gather_count <= L-2)
			{
				//System.out.println("aa");
				for(int i=0; i< ch_list.size(); i++)
				{
					bw.write(Character.toString(ch_list.get(i)));				
				}
				bw.write("\n");
				//bw.flush();
			}
			return;
		}
		
		if(d < C)
		{
			ch_list.add(ch[d]);
			depth(ch, d+1);
			ch_list.remove(ch_list.size()-1);
			
			depth(ch, d+1);			
		}
		
	}

}

