package feature04_03;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;


// 실버4 덱
public class Main_10866 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Deque<Integer> q = new ArrayDeque<>();

        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {

            // String cmd = br.readLine(); // 노
            st = new StringTokenizer(br.readLine()); // 끊어먹기.
            String cmd = st.nextToken();

            // push_front X
            if (cmd.equals("push_front")) {
                int x = Integer.parseInt(st.nextToken()); // br.readLine 이면 다음줄을 읽어버리니 안되지.
                q.addFirst(x);
            }

            // push_back X
            else if (cmd.equals("push_back")) {
                int x = Integer.parseInt(st.nextToken()); // br.readLine 이면 다음줄을 읽어버리니 안되지.
                q.addLast(x);
            }

            // pop_front
            else if (cmd.equals("pop_front")) {
                if (q.isEmpty()) {
                    System.out.println(-1);
                } else {
                    int x = q.pollFirst();
                    System.out.println(x);
                }
            }

            // pop_back
            else if (cmd.equals("pop_back")) {
                if (q.isEmpty()) {
                    System.out.println(-1);
                } else {
                    int x = q.pollLast();
                    System.out.println(x);
                }
            }

            // size
            else if (cmd.equals("size")) {
                System.out.println(q.size());
            }

            // empty
            else if (cmd.equals("empty")) {
                if (q.isEmpty()) {
                    System.out.println(1);
                } else {
                    System.out.println(0);
                }
            }

            // front
            else if (cmd.equals("front")) {
                if (q.isEmpty()) {
                    System.out.println(-1);
                } else {
                    int x = q.peekFirst();
                    System.out.println(x);
                }
            }

            // back
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
// 이전과 비슷

// [구현 순서]
// 입력
// 분기 처리

// [실수]


// [코드 최적화]


// [참고]
//BufferedReader br = null;
//try {
//br = new BufferedReader(new InputStreamReader(System.in));
//        // 기존 코드
//        } finally {
//        if (br != null) br.close();
//}

//
//StringBuilder sb = new StringBuilder();
//
//// 예: pop_front
//else if (cmd.equals("pop_front")) {
//        if (q.isEmpty()) {
//        sb.append(-1).append('\n');
//    } else {
//            sb.append(q.pollFirst()).append('\n');
//    }
//            }
//
//// 예: size
//            else if (cmd.equals("size")) {
//        sb.append(q.size()).append('\n');
//}
//
//// for문 끝나고 마지막에 한 번만
//        System.out.print(sb);