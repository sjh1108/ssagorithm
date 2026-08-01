import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 회의실 배정_ 그리디 : 끝시간이 빠른 것부터 나열하면 뒤에 최대의 회의실 가능.
public class Main_1931 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[][] arr= new int[N][2];

        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        // 시작시간 정렬은 원래는 필요없으나, 나열하지 않으면
        Arrays.sort(arr, (a, b) -> {
            if (a[1] == b[1]) {
                return Integer.compare(a[0], b[0]); // (1,2), (2,2) 같은 점 순간도 체크하기 위해 필요.
            }
            return Integer.compare(a[1], b[1]);
        });

        // 여기까지가 문제 세팅을 위한 정렬 전처리.. 하하.

        int cnt = 0;
        int multiEnd = 0;

        // 이제 시간을 돌려봄.
        for (int i = 0; i < N; i++) {
            int start  = arr[i][0]; // 시작시간
            int finish = arr[i][1]; // 끝시간

            // 여기 내에서 조건문을 통해 값을 가져가서 실제 cnt 최대횟수를 구하기.
            if (start >= multiEnd) { // 앞에서 정렬을 했으니까, 여기서 끝점, 시작점 비교 (1,1), (1,2), (2,2), (2,3), (2,5), (1,4) .. 누적끝시간
                cnt++; // (1,1) +1, (1,2) +1, (2,2) ***** 만약 위에서 끝점 기준으로 정렬하고, 시작점 기준 정렬 안했으면 여기서 못잡았음. (2,2), (2,1)로 들어온 경우.
                multiEnd = finish; // 1, 2,
            }
        }
        System.out.println(cnt);
    }
}


