package study;
import java.io.*;
import java.util.*;

public class Main_1062_김회인 {
	
	static int N, K;
	static int[] words;	// 단어 비트 저장용
	static int maxCount = 0;
	
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	N = Integer.parseInt(st.nextToken());
    	K = Integer.parseInt(st.nextToken());
    	
    	// antic은 배워야 한다
    	if(K < 5) {
    		System.out.println(0);
    		return;
    	} else if(K == 26) {
    		System.out.println(N);
    		return;
    	}
    	
    	words = new int[N];
    	
    	// 단어 비트 저장 중
    	for(int i=0; i<N; i++) {
    		String s = br.readLine();
    		int bit =0;
    		for(int j=0; j<s.length(); j++) {
    			bit|= (1<<(s.charAt(j)-'a'));
    		}
    		words[i] = bit;
    	}
    	
    	int initialMast = 0;
    	
    	initialMast |= (1 << ('a' - 'a'));
    	initialMast |= (1 << ('n' - 'a'));
    	initialMast |= (1 << ('t' - 'a'));
    	initialMast |= (1 << ('i' - 'a'));
    	initialMast |= (1 << ('c' - 'a'));
    	
    	solve(0,5,initialMast);
    	
    	bw.write(Integer.toString(maxCount));
    	bw.flush();
    	bw.close();
    	br.close();
    }
    
	static void solve(int index, int count, int mask) {
		if(count == K) {
			int readableCount = 0;
			for(int wordBit : words) {
				if((wordBit & mask) == wordBit) {
					readableCount++;
				}
			}
			
			maxCount = Math.max(maxCount, readableCount);
			return;
		}
		//System.out.println("solve");
		for(int i= index; i < 26; i++) {
			if((mask & (1 << i)) == 0) {
				solve(i + 1, count + 1, mask | (1 << i));
			}
		}
	}
}
