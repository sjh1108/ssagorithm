import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class BOJ_25192 {
	static int N, count;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		count = 0;
		
		Set<String> hashSet = new HashSet<>();
		
		for(int i = 0; i < N; i++) {
			String s = br.readLine();
			
			if(s.equals("ENTER")) {
				hashSet.clear();
			} else {
				if(hashSet.contains(s)) {
					continue;
				}
				hashSet.add(s);
				count++;	
			}
		}
		
		System.out.println(count);
	}

}