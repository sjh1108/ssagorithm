package week12_03;

import java.io.BufferedReader;
import java.io.InputStreamReader;


// 괄호 실버4
public class Main_9012 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            String s = br.readLine();
            int balance = 0;
            boolean ok = true;

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '(') balance++;
                else { // ')'라면
                    if (balance == 0) { // 닫을 '('가 없음
                        ok = false;
                        break;
                    }
                    balance--; // 닫을 '('로 하나 처리하기. 
                }
            }

            if (ok && balance == 0) sb.append("YES\n"); //  둘 다 완성. 
            else sb.append("NO\n");
        }

        System.out.print(sb.toString());
    }
}

// 문자를 문자로 단순히 매칭해서 삭제하는게 아니라, 계산해서 처리하기. 