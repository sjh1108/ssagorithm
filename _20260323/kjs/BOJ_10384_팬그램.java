import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= n; tc++) {
            String str = br.readLine();

            int[] alpha = new int[26];

            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);

                // 대문자를 소문자로 바꾸기
                if (ch >= 'A' && ch <= 'Z') {
                    ch = (char)(ch - 'A' + 'a');
                }

                // 소문자 알파벳이면 개수 세기
                if (ch >= 'a' && ch <= 'z') {
                    alpha[ch - 'a']++;
                }
            }

            int min = alpha[0];
            for (int i = 1; i < 26; i++) {
                if (alpha[i] < min) {
                    min = alpha[i];
                }
            }

            sb.append("Case ").append(tc).append(": ");

            if (min == 0) {
                sb.append("Not a pangram");
            } else if (min == 1) {
                sb.append("Pangram!");
            } else if (min == 2) {
                sb.append("Double pangram!!");
            } else {
                sb.append("Triple pangram!!!");
            }

            sb.append('\n');
        }

        System.out.print(sb.toString());
    }
}
