package study_0523;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class week01_정사각형방_김회인 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int t = Integer.parseInt(br.readLine());
		
		for(int T=1; T<=t;T++)
		{
			
			int n = Integer.parseInt(br.readLine());
			
			
			int delta[][] = {{1,0},{-1,0},{0,1},{0,-1}};
			int array[][] = new int[n][n];
			int sub_array[][];
			int max=0;
			for(int i=0; i<n;i++)
			{
				String s= br.readLine();
				st = new StringTokenizer(s);
				for(int j=0; j<n; j++)
				{
					array[i][j] = Integer.parseInt(st.nextToken());
					if(max < array[i][j]) max = array[i][j];
				}
			}
			
			int max_count=0;
			
			for(int w=1; w<max; w++)
			{
				int count=0;
				for(int i=0; i<n; i++)
				{
					for(int j=0; j<n; j++)
					{
						if(array[i][j] == i) array[i][j] = 0;
					}
				}
				
				for(int i=0; i<n; i++)
				{
					for(int j=0; j<n; j++)
					{
						int x=i;
						int y=j;
						int c=0;
						
						while(true)
	                    {
							if(array[i][j] == 0) break;
							
	                        for(int d=0; d<delta.length; d++)
	                        {
	                            int dx = x + delta[w][0];
	                            int dy = y + delta[w][1];
	                            if(((dx >= 0)&&(dx < n))&&((dy >=0 )&&(dy <n)))
	                            {
	                                if(array[dx][dy] > 0)
	                                {
	                                    c++;
	                                    x = dx;
	                                    y = dy;
	                                     
	                                    break;
	                                }
	                            }
	                        }
	                         
	                    }
						
						if(c>0) count++;
						
					}
				}
				
				if(count > max_count) max_count = count;
					
			}
			
			
			
			
			//bw.write("#"+T+" "+min +" "+ max_count+"\n");
		}
		bw.flush();
		bw.close();
	}
}

