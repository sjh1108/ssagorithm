package algostudy.baek.week12_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_9935_이용호 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String st = br.readLine();
		String bomb = br.readLine();
		
		StringBuilder sb = new StringBuilder();
		
		for(char c : st.toCharArray()) {
			sb.append(c);
			if(sb.length() < bomb.length()) continue;
			// 폭탄 마지막 문자 나오면 sb검사
			if(c == bomb.charAt(bomb.length()-1)) {
				System.out.println(sb.substring(sb.length()-bomb.length()));
				if(sb.substring(sb.length()-bomb.length()).equals(bomb)) {
					sb.delete(sb.length() - bomb.length(), sb.length());
				}
			}
		}
		System.out.println(sb.length() > 0 ? sb : "FRULA");

	}

}
