package samsung01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1316_그룹단어체커 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());

		int count = 0;
		for (int i = 0; i < n; i++) {
			String s = br.readLine();
			
			boolean[] visited = new boolean[26];
			boolean isG = true;
			char perv = 0;
			
			for(int j=0; j<s.length(); j++) {
				char c = s.charAt(j);
				int idx = c-'a';
				
				if(c != perv) {
					if(visited[idx]) {
						isG = false;
						break;
					}
					
				}
				
				visited[idx] = true;
				perv = c;
				
			}
			
			if(isG) {
				count++;
			}
			
		}
		System.out.println(count);

	}

}
