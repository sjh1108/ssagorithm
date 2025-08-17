import java.io.*;

public class Boj11720 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // N 입력
        int N = Integer.parseInt(br.readLine());
        String s = br.readLine();

        
        int sum = 0;

        // 공백없이 여러 줄 입력
        // 숫자 N개의 합을 구하기

        for (int i = 0; i < N; i++) {
            sum += s.charAt(i) - '0'; // 문자 하나씩 숫자로 변경해서 0빼주고, 더해줌.
            // 실수2: charAt을 chatAt으로 써서 오류떴었어요 ㅠㅠ
        }


        // 실수1: 한 줄로 입력받는게 아니라, 여러 줄로 입력받음
        // for (int i = 0; i < N; i++) {
        //     num[i] = Integer.parseInt(br.readLine());
        //     sum += num[i];
        // }

        System.out.println(sum);

        
    }
}
