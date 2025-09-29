package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_10942_김회인 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(br.readLine());
		
		HashSet<Integer> hash = new HashSet<>();
		int array[] = new int[n];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++)
		{
			int N = Integer.parseInt(st.nextToken());
			hash.add(N);
			array[i] = N;
		}
		
		Arrays.sort(array);
		int count=0;
		for(int i =0; i<array.length;i++)
		{
			//System.out.println("count : " + count);
			int w=0,j=array.length-1;
			
			//if(w == j) break;
			
			while(w<j)
			{
				if(w==i) {w++; continue;}
				if(j==i) {j--; continue;}
				if(array[i] == array[w] + array[j])
				{
					//System.out.println(array[i] + " = " + array[w] + " " + array[j]);
					count++;
					break;
				}
				else if(array[i] > array[w] + array[j])   // 더한게 더 작다면? 크기를 키운다.
				{
					w++;
				}
				else if(array[i] < array[w] + array[j])		// 더한게 더 크다면? 줄여줌
				{
					j--;
				}
				
			}
		}
		
		//System.out.println("count : " + count);
		bw.write(Integer.toString(count));
		bw.flush();
		bw.close();
		br.close();
	}

}
