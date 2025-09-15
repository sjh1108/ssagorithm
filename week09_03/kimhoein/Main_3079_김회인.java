package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main_3079_김회인 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st= new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		long array[] = new long[N];
		
		//st= new StringTokenizer(br.readLine());
		long high=0;
		
		for(int i=0; i<N; i++)
		{
			array[i] = Integer.parseInt(br.readLine());
			if(high < array[i]) high = array[i];
		}
		
		long min=1;
		long mid=0;
		long result=0;
		high *= M;
		//System.out.println("high : " + high);
		
		while(min <= high)
		{
			Long temp=(long) 0;
			mid = (min+high)/2;
			
			for(int i=0; i<N; i++)
			{
				if(temp>=M) continue;
				temp += mid/array[i]; 
			}
			
			if(temp>=M)
			{
				result = mid;
				high = mid-1;
			}
			else
			{
				min = mid+1;
			}
		}
		
		bw.write(Long.toString(result));
		bw.flush();
		bw.close();
		br.close();
		
	}

}
