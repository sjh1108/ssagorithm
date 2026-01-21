
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;


public class Main_1503_김회인 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());		
		int scount = Integer.parseInt(st.nextToken());
		int min = Integer.MAX_VALUE;
		
		boolean[] isForbidden = new boolean[1002];
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<scount;i++)
		{
			isForbidden[Integer.parseInt(st.nextToken())] = true;
		}
		
		for (int i = 1; i <= 1001; i++) {
            if(isForbidden[i]) continue;
            for (int w = i; w <= 1001; w++) {
            	if(isForbidden[w]) continue;
                for (int j = w; j <= 1001; j++) {
                	if(isForbidden[j]) continue;
                	long q = i*w*j;
                	int diff = (int)Math.abs(N-q);
                	if(diff < min) min = diff;
                    //min = Math.min(min, Math.abs(N - i * w * j));
                }
            }
        }
		
		bw.write(Integer.toString(min));
		bw.flush();
		bw.close();
		br.close();
	}
	
}
