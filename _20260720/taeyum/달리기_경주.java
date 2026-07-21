package _20260720.taeyum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

// 달리기 경주
// 배열 받아서 위치 바꿔주기만 잘하면 될 듯?
// 선수 50,000명 까지 가능, 부르는 선수 1,000,000명 까지 가능
// 50,000 * 1,000,000 은 걍 터지겠노 ㅋㅋ
// 부른 선수 스왑 (1등 선수는 스왑 당하지 않음)

public class 달리기_경주 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        String[] players = new String[n];
        HashMap<String, Integer> rank = new HashMap<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            players[i] = st.nextToken();
            rank.put(players[i], i);
        }

        st = new StringTokenizer(br.readLine());

        for (int c = 0; c < m; c++) {
            String name = st.nextToken();
            // HashMap의 get으로 바로 인덱스에 해당하는 위치를 받아옴 O(1)
            int i = rank.get(name);
            // 해당 플레이어 앞 front
            String front = players[i-1];

            //앞 사람과 스왑하고
            players[i-1] = name;
            players[i] = front;
            //HashMap도 갱신
            rank.put(name, i-1);
            rank.put(front, i);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(players[i]);
            if (i < n-1) sb.append(' ');
        }
        System.out.println(sb);
    }
}
