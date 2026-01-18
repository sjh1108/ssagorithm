package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_12015_김회인 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st; //= new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(br.readLine());
		int arr[] = new int[N];
		
		st = new StringTokenizer(br.readLine());
		
		for(int i=0; i<N; i++)
		{
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		List<Integer> list = new ArrayList<>();
		
		for(int x : arr)
		{
			if(list.isEmpty() || list.get(list.size()-1) < x) list.add(x);
			else
			{
				int idx = Collections.binarySearch(list,x);
				if(idx<0) idx = -(idx+1);
				list.set(idx, x);
			}
		}
		
		
		bw.write(Integer.toString(list.size()));
		bw.flush();
		bw.close();
		br.close();
		
	}

}
