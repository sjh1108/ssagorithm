package gold_5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main_1759 {

    // 각각 암호 길이, 주어지는 알파벳 종류, 인덱스 활용 여부 탐지용 비트
    static int n, k, bit;
    // 모음 리스트
    static List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u');
    // 주어지는 알파벳 리스트
    static char[] chars;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception {
        init(); bt(0, 0, 0, 0);
        System.out.println(sb);
    }

    public static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        chars = new char[k];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<k; i++) {
            chars[i] = st.nextToken().charAt(0);
        }
        // 오름차순 출력을 위한 정렬
        Arrays.sort(chars);
        bit = 0;
    }

    public static void bt(int start, int cnt, int vowelCnt, int consCnt) {
        // kCn 조합 완성한 경우
        // 모음 최소 1개 이상, 자음 최소 2개 이상 조건을 충족하면 암호 출력
        // 조건 미충족시 그냥 리턴
        if(cnt == n) {
            if(vowelCnt >= 1 && consCnt >= 2) {
                for(int i=0; i<k; i++) {
                    if((bit & (1 << i)) != 0) sb.append(chars[i]);
                }
                sb.append("\n");
            }
            return;
        }

        for(int i=start; i<=k-n+cnt; i++) {
            // 해당 인덱스의 알파벳을 사용 상태로 전환
            bit = bit | (1 << i);
            // 자음 또는 모음 카운트 후 다음 단계로 재귀
            if(vowels.contains(chars[i])) {
                bt(i+1, cnt+1, vowelCnt+1, consCnt);
            } else {
                bt(i+1, cnt+1, vowelCnt, consCnt+1);
            }
            // 사용한 인덱스의 알파벳을 다시 미사용 상태로 전환
            bit = bit & ~(1 << i);
        }
    }

}
