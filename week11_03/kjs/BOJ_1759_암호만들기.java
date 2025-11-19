package Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Arrays;

public class BOJ_1759_암호만들기 {

    static int L;          // 암호 길이
    static int C;          // 문자 개수
    static char[] letters; // 입력받은 문자들
    static char[] code;    // 지금 만들고 있는 암호

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        L = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        letters = new char[C];
        code = new char[L];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < C; i++) {
            letters[i] = st.nextToken().charAt(0);
        }


        Arrays.sort(letters);
        dfs(0, 0);
        
        
    }

    // depth : 지금까지 선택한 글자 수
    // start : 다음 선택 시작 위치
    static void dfs(int depth, int start) {


        if (depth == L) {
            int vCount = 0; // 모음 개수
            int cCount = 0; // 자음 개수

            // code 배열에 담긴 문자들 검사
            for (int i = 0; i < L; i++) {
                char ch = code[i];

                // 모음인지 확인
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                	vCount = vCount + 1;
                } else {
                	cCount = cCount + 1;
                }
            }

            if (vCount >= 1 && cCount >= 2) {
                for (int i = 0; i < L; i++) {
                    System.out.print(code[i]);
                }
                System.out.println();
            }

            return;
        }

        for (int i = start; i < C; i++) {
            code[depth] = letters[i];
            dfs(depth + 1, i + 1);
        }
    }
}
