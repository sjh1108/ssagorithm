package feature04_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;


// 괄호 실버4
public class Main_9012 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int i = 0; i < T; i++) {
            String str = br.readLine();
            Deque<Character> stack = new ArrayDeque<>();
            boolean VPS = true; // () 성립 가능하게 한다.

            for (char c : str.toCharArray()) {
                if (c == '(') {
                    stack.push(c); // 1. (이면 넣고,
                } else {
                    if (stack.isEmpty()) { // 3. 비어있으면 = 들어온 게 ( 아니면
                        VPS = false; // 4. 바로 컷
                        break;
                    }
                    stack.pop(); // 2. 아니면 빼고
                }
            }
            if (!stack.isEmpty()) VPS = false; // 5. 짝이 매칭되지 않았으니.
            System.out.println(VPS ? "YES" : "NO");
        }
    }
}

// [풀이전략]
// (이면 +1 )이면 -1 -> 이렇게 되면 )( 음수가 되면 문제가 될 수 있음, 혹시 음수가 되는 순간 NO되어야 함.
// 스택 팝이 더 정석적이야.


// [구현 순서]
// 입력
// 점검

// [실수]
// if (str.charAt(i) == '(') { i가 아니라 j여야 함
// 중간에 )( 이렇게 나오는 경우는 안됨.

// [최적화 전]
//int cnt = 0;
//
//            for (int j = 0; j < str.length(); j++) {
//        if (str.charAt(j) == '(') {
//cnt++;
//        } else {
//cnt--;
//        }
//        if(cnt < 0) {
//        break;
//        }
//
//        }
//
//        if(cnt == 0) {
//        System.out.println("YES");
//            } else {
//                    System.out.println("NO");
//            }