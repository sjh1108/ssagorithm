package month08.week04.jangsun;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class BOJ10866_덱 {
	static Deque<Integer> deque = new ArrayDeque<>();
	static public void push_front(int x) {
		deque.addFirst(x);
	}
	static public void push_back(int x) {
		deque.addLast(x);
	}
	static public void pop_front() {
		if(deque.isEmpty() == true) {
			System.out.println("-1");
		}else {
			System.out.println(deque.poll()); 
		}

	}
	static public void pop_back() {
		if(deque.isEmpty() == true) {
			System.out.println("-1");
		}else {
			System.out.println(deque.pollLast()); 
		}
		
	}
	static public void size() {
		System.out.println(deque.size());
	}
	static public void empty() {
		if (deque.isEmpty() == true) {
			System.out.println("1");
		}else {
			System.out.println("0");
		}
	}
	static public void front() {
		if (deque.peekFirst() == null) {
			System.out.println("-1");
		}else {
			System.out.println(deque.peekFirst());
		}
	}
	static public void back() {
		if (deque.peekLast() == null) {
			System.out.println("-1");
		}else {
			System.out.println(deque.peekLast());
		}
	}
	

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String command = st.nextToken();
			
			if(command.equals("push_front") ) {
				int x = Integer.parseInt(st.nextToken());
				push_front(x);
			}else if(command.equals("push_back")) {
				int x = Integer.parseInt(st.nextToken());
				push_back(x);
			}else if(command.equals("pop_front")) {
				pop_front();
			}else if(command.equals("pop_back")) {
				pop_back();
			}else if(command.equals("size")) {
				size();
			}else if(command.equals("empty")) {
				empty();
			}else if(command.equals("front")) {
				front();
			}else if(command.equals("back")) {
				back();
			}
		}
	}

}
