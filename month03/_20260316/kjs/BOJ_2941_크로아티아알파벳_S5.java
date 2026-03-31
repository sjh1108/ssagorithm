import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        int count = 0;

        for (int i = 0; i < s.length(); i++) {

            // 1. dz= 먼저 확인 (3글자)
            if (i + 2 < s.length() &&
                s.charAt(i) == 'd' &&
                s.charAt(i + 1) == 'z' &&
                s.charAt(i + 2) == '=') {
                
                count++;
                i += 2;   // dz= 3글자니까 2칸 더 이동
            }

            // 2. c=, c-
            else if (i + 1 < s.length() &&
                    s.charAt(i) == 'c' &&
                    (s.charAt(i + 1) == '=' || s.charAt(i + 1) == '-')) {
                
                count++;
                i += 1;
            }

            // 3. d-
            else if (i + 1 < s.length() &&
                    s.charAt(i) == 'd' &&
                    s.charAt(i + 1) == '-') {
                
                count++;
                i += 1;
            }

            // 4. lj
            else if (i + 1 < s.length() &&
                    s.charAt(i) == 'l' &&
                    s.charAt(i + 1) == 'j') {
                
                count++;
                i += 1;
            }

            // 5. nj
            else if (i + 1 < s.length() &&
                    s.charAt(i) == 'n' &&
                    s.charAt(i + 1) == 'j') {
                
                count++;
                i += 1;
            }

            // 6. s=
            else if (i + 1 < s.length() &&
                    s.charAt(i) == 's' &&
                    s.charAt(i + 1) == '=') {
                
                count++;
                i += 1;
            }

            // 7. z=
            else if (i + 1 < s.length() &&
                    s.charAt(i) == 'z' &&
                    s.charAt(i + 1) == '=') {
                
                count++;
                i += 1;
            }

            // 8. 일반 문자
            else {
                count++;
            }
        }

        System.out.println(count);
    }
}
