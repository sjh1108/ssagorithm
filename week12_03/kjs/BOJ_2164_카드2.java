package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class BOJ_2164_카드2 {
    public static void main(String[] args) throws Exception {
    	
    	
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        ArrayDeque<Integer> dq = new ArrayDeque<>();
        
        for (int i = 1; i <= N; i++) {
        	dq.addLast(i);

        }
        while (dq.size() > 1) {
            dq.pollFirst();
            dq.addLast(dq.pollFirst());
        }

        System.out.println(dq.peekFirst());
    }
}
