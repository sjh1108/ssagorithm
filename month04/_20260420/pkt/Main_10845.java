package feature04_03;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

// 큐 실버4
public class Main_10845 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Deque<Integer> q = new ArrayDeque<>();

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {

            // String cmd = br.readLine(); // 노
            st = new StringTokenizer(br.readLine()); // 끊어먹기.
            String cmd = st.nextToken();

            // 푸쉬
            if (cmd.equals("push")) {
                int x = Integer.parseInt(st.nextToken()); // br.readLine 이면 다음줄을 읽어버리니 안되지.
                q.addLast(x);
            }

            // 팝
            else if (cmd.equals("pop")) {
                if (q.isEmpty()) {
                    System.out.println("-1");
                } else {
                    int x = q.pollFirst();
                    System.out.println(x);
                }
            }

            // 사이즈
            else if (cmd.equals("size")) {
                System.out.println(q.size());
            }

            // 엠티
            else if (cmd.equals("empty")) {
                if (q.isEmpty()) {
                    System.out.println(1);
                } else {
                    System.out.println(0);
                }
            }

            // 프론트
            else if (cmd.equals("front")) {
                if (q.isEmpty()) {
                    System.out.println(-1);
                } else {
                    int x = q.peekFirst();
                    System.out.println(x);
                }
            }

            // 백
            else if (cmd.equals("back")) {
                if (q.isEmpty()) {
                    System.out.println(-1);
                } else {
                    int x = q.peekLast();
                    System.out.println(x);
                }
            }
        }
    }
}

// [풀이전략]
// 각 분기에 맞게 처리해주기

// [구현 순서]
// 입력
// push조건이면 1을 넣는다.
// 각 조건에 맞게 구현

// [실수]
// 문자열 비교: == .equals()  ==는 참조(주소) 비교
// push 입력 파싱이 잘못 -> 한번에 들어오는데, 두번 읽음
// push/pop 메서드가 스택 동작 push(x)  addFirst(x)   // 앞에 넣음 pop()  removeFirst()  // 앞에서 뺌

// [코드 최적화]
// switch case문의 경우 분기가 6개 이상이면서 하나의 값에 정확히 일치하는 분기가 있는 경우 적절
// 여기는 push 등 정확히 하나로 떨어지는 분기가 아니기에 if-else가 굿.
// 차후 sb.append 처리하기.