import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main_1021 {
    public static void main(String[] args) throws IOException {
        // 1. 첫 번째 줄 입력 (10과 3)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // N = 10 (큐의 크기)
        int M = Integer.parseInt(st.nextToken()); // M = 3 (뽑을 개수)

        // 2. 큐 초기화 (1부터 10까지 채움)
        // deque = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
        LinkedList<Integer> deque = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            deque.add(i);
        }

        // 3. 두 번째 줄 입력 (2, 9, 5)
        // targets 배열 = {2, 9, 5}
        int[] targets = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            targets[i] = Integer.parseInt(st.nextToken());
        }

        // 이 사이에서 2, 9, 5를 찾기 위해 큐를 돌리는 로직이 실행되며 count가 올라갑니다 ---
        int count = 0;

        for (int target : targets) {
            // 1. 타겟 숫자가 큐의 어느 위치(인덱스)에 있는지 확인
            int targetIdx = deque.indexOf(target);

            // 2. 현재 큐의 중간 인덱스 계산 (짝수일 땐 절반보다 1 작게 설정)
            int halfIdx = deque.size() % 2 == 0 ? deque.size() / 2 - 1 : deque.size() / 2;

            // 3. 타겟이 중간 지점보다 앞이거나 중간이라면 -> 왼쪽으로 회전 (2번 연산)
            if (targetIdx <= halfIdx) {
                for (int j = 0; j < targetIdx; j++) {
                    int temp = deque.pollFirst();
                    deque.addLast(temp);
                    count++;
                }
            }
            // 4. 타겟이 중간 지점보다 뒤에 있다면 -> 오른쪽으로 회전 (3번 연산)
            else {
                for (int j = 0; j < deque.size() - targetIdx; j++) {
                    int temp = deque.pollLast();
                    deque.addFirst(temp);
                    count++;
                }
            }

            // 5. 원하는 숫자가 맨 앞으로 왔으니 뽑아냄 (1번 연산)
            deque.pollFirst();
        }


        // 4. 최종 출력
        System.out.println(count); // 회전시킨 총 횟수(8) 출력
    }
}

// 회전하는 큐
// 10 3
// 2 9 5
// 1부터 10까지 나열된 10개의 숫자 중에서, 처음에 2번째, 9번째, 5번째에 있던 숫자(즉, 숫자 2, 9, 5)를 순서대로 뽑아내고 싶어
// 결과가 8이면 2,9,5를 순서대로 뽑아내려면 큐를 왼쪽/오른쪽으로 총 8번 돌려야 함.
