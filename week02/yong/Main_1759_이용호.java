package algostudy.baek.b1759;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1759_이용호 {
    static int L, C;
    static char[] word;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        L = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        word = new char[C]; // 암호 베이스 알파벳들

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < C; i++) {
            word[i] = st.nextToken().charAt(0);
        }
        Arrays.sort(word); //정렬
        backtrack(0, 0, new char[L]);
        System.out.print(sb);
    }

    static void backtrack(int start, int depth, char[] out) {
        if (depth == L) {// L개 원소 선택됐으면
            if (isValid(out)) { // 자음 들어가 있으면 sb에 추가
                sb.append(out).append('\n');
            }
            return;
        }

        for (int i = start; i < C; i++) {
            out[depth] = word[i]; //현재 원소 출력배열에 추가
            backtrack(i + 1, depth + 1, out); //i + 1 -> 다음원소부터 선택(중복선택방지 + 증가하는순)
        }
    }

    static boolean isValid(char[] arr) {
        int vowel = 0, consonant = 0;
        for (char c : arr) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') vowel++;
            else consonant++;
        }
        return vowel >= 1 && consonant >= 2; //모음 하나 이상이고 자음 2개 이상이면 true(모음 최대 2개니까 자음은 최소 2개 이상)
    }
}
