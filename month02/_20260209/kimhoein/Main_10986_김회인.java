package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main_10986_김회인 {
    
    /*

        기본적으로 알아야 하는부분 누적 합을 이용해서 푼다
        
        추가적으로 알아야 하는 부분
        같은 나머지를 가진다면
        0으로 수렴 한다
         M % (sumA - sumB) == 0
         M % sumA == M % sumB
         이 성립한다
         이것의 의미는 같은 나머지를 가지는 개수가 n 일때
         nC2는 M으로 나누어 떨어진다고 생각 할 수 있다
    */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        
        long[] arr = new long[n+1];
        
        st = new StringTokenizer(br.readLine());
        
        for(int i=1; i<=n; i++)
        {
        	arr[i] += arr[i-1] + Integer.parseInt(st.nextToken());
        	//System.out.println("arr[i] : " + arr[i]);
        }
        
        long count = 0;
        long[] cnt = new long[m];
        
        for(int i=1; i<=n; i++) {
        	
    		int currentSum = (int) (arr[i] % m);
    		
    		if( currentSum == 0) count ++;
    		
    		cnt[currentSum]++;
        	
        	
        	//System.out.println("count : " + count);
        }
        
        for(int i=0; i<m; i++) {
        	if(cnt[i] > 1) {
        		count += (cnt[i] * (cnt[i] - 1)) /2;
        	}
        }
        
        bw.write(Long.toString(count));
        bw.flush();
        bw.close();
        
        
    }
}