import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 알고리즘 재활소 최소, 최대 브3
public class Main_10818 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;


        // 최대, 최소 구하기
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            if (min > num) {
                min = num;
            }

            if (max < num) {
                max = num;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(min).append(" ").append(max);
        System.out.println(sb);
    }
}

// [풀이전략]
// 일단 문제에서 만들라는 거 만들고, fixed 유형부터 깔아놓고, 이후 unfixed를 찾아서 하기.
// 최대, 최소 확인만 하기 때문에 굳이 자료구조를 만들어서 담을 필요 없음

// [구현 순서]
// 입력
// 최대, 최소 받기
// 출력

// [실수]
// int num = Integer.parseInt(br.readLine());  at java.base/java.util.StringTokenizer.nextToken(StringTokenizer.java:349)