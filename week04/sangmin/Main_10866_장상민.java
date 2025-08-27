package java_home_work.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main_10866_장상민 {
	static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine());
		ArrayDeque<Integer> dq = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            String line = input.readLine();
            if (line.startsWith("push_front")) {
                int x = Integer.parseInt(line.substring(11).trim());
                dq.addFirst(x);
            } else if (line.startsWith("push_back")) {
                int x = Integer.parseInt(line.substring(10).trim());
                dq.addLast(x);
            } else if (line.equals("pop_front")) {
                Integer v = dq.pollFirst();
                sb.append(v == null ? -1 : v).append('\n');
            } else if (line.equals("pop_back")) {
                Integer v = dq.pollLast();
                sb.append(v == null ? -1 : v).append('\n');
            } else if (line.equals("size")) {
                sb.append(dq.size()).append('\n');
            } else if (line.equals("empty")) {
                sb.append(dq.isEmpty() ? 1 : 0).append('\n');
            } else if (line.equals("front")) {
                Integer v = dq.peekFirst();
                sb.append(v == null ? -1 : v).append('\n');
            } else { // back
                Integer v = dq.peekLast();
                sb.append(v == null ? -1 : v).append('\n');
            }
        }
        System.out.print(sb.toString());
    }
}