package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main_2829_김회인 {
    /*
        대각선에 대한 누적합을 하는 문제
        대각선을 할때 어디서 시작하고 어디서 끝나는지를 알면 풀 수 있다.
    */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n =Integer.parseInt(st.nextToken());
        
        int[][] array = new int[n+1][n+1];
        
        for(int i=1; i<=n; i++)
        {
            st = new StringTokenizer(br.readLine());
            for(int j=1; j<=n; j++) {
                array[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        int[][] pref1 = new int[n+2][n+2];
        int[][] pref2 = new int[n+2][n+2];
        int max = Integer.MIN_VALUE;
        
        for(int i=1; i<=n;i++) {
            for(int j=1; j<=n; j++) {
            	pref1[i][j] = array[i][j] + pref1[i-1][j-1];
            	pref2[i][j] = array[i][j] + pref2[i-1][j+1];
            	
            	//System.out.println(pref1[i][j] + " " + pref2[i][j]);
            }
        }
        
        for(int r=1; r<=n; r++) {
        	for(int c=1; c<=n; c++) {
        		for(int k=0; r+k <=n && c+k <= n; k++) {
        			int sumA = pref1[r+k][c+k] - pref1[r-1][c-1];
        			int sumB = pref2[r+k][c] - pref2[r-1][c+k+1];
        			//System.out.println("aa" + sumA +  " "+ sumB);
        			max = Math.max(max, sumA-sumB);
        		}
        	}
        }
        
        bw.write(Integer.toString(max));
        bw.flush();
        bw.close();
        
        
    }
}