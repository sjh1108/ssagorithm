import java.io.*;
import java.util.*;


public class Main {

	static int[][] delta = new int[][] {
		{0, 0}, 		// 0
		{0, 0},			// 1
	    {1, 3},			// 2
	    {3, 6},			// 3
	    {6, 10, 6, 14, 6, 18},		// 4
	    {5, 15}			// 5
	};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
        
		int l = Integer.parseInt(br.readLine());
		int r = Integer.parseInt(br.readLine());
		int k = Integer.parseInt(br.readLine());
		
		int result = 0;
		
		//System.out.println("result1 : " + result);
		

		if(delta[k][1] <= r) result += (r-delta[k][1])/delta[k][0]+1;
		if(delta[k][1] < l) result -= (l-1-delta[k][1])/delta[k][0]+1;			
		
		//System.out.println( (l-1-delta[k][1])/delta[k][0] );
		
		//System.out.println("result : " + result);
		
		if(k==4)
		{
			if(delta[k][3] <= r) result += (r-delta[k][3])/delta[k][2]+1;
			if(delta[k][3] < l) result -= (l-1-delta[k][3])/delta[k][2]+1;	
			
			if(delta[k][5] <= r) result += (r-delta[k][5])/delta[k][4]+1;
			if(delta[k][5] < l) result -= (l-1-delta[k][5])/delta[k][4]+1;	
		}
		
//		System.out.println(r/delta[k][0] + " " + Math.max(delta[k][1]-1, l-1)/delta[k][0]);
//		System.out.println(r/delta[k][2] + " " +  Math.max(delta[k][3]-1, l-1)/delta[k][2]);
//		System.out.println(r/delta[k][4] + " " +  Math.max(delta[k][5]-1, l-1)/delta[k][4]);
		
		if(result <0) result =0;
        bw.write(Integer.toString(result));
        bw.flush();
        bw.close();
        br.close();
	}

}
