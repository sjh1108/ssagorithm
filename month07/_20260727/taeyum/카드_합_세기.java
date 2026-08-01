package _20260727.taeyum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 숫자 카드 두 장의 합이 X 이하
// 인풋 값들 사악한거 보소 ㅋㅋ
// 출력 64비트면 Long 써야겠노 고맙다 알려줘서~
// 숫자 카드 N, 최대 X

public class 카드_합_세기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long x = Long.parseLong(st.nextToken());

        long[] cards = new long[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            cards[i] = Long.parseLong(st.nextToken());
        }


        Arrays.sort(cards);

        long count = 0;
        int left = 0;
        int right = n - 1;

        while (left < right) {
            if (cards[left] + cards[right] <= x) {
                // 만약 cards[left] + cards[right] 이 X 보다 작거나 같으면
                // 정렬하고 들어왔기 때문에 right - left 구간은 항상 만족함
                // 그래서 right 를 줄일 필요 없이 바로 해당 구간 끝내고
                // 해당 구간 count = right - (left + 1) + 1
                // left 올려서 다음 반복으로 넘어감
                count += right - left;
                left++;
            } else {
                right--;
            }
        }

        System.out.println(count);
    }
}
