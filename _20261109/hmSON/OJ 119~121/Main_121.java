import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 구현, 누적, 단조 스택
     *
     * 정상 패널(1)로만 이루어진 모든 직사각형의 개수를 반환한다. 1*1 크기의 직사각형, 즉 각각의 정상 패널도 직사각형으로 인정한다
     * 119번 문제와 동일하게 세로 방향으로 연속 구간 누적값을 기록하고
     * 같은 행의 두 패널을 모서리로 고정하면 정사각형의 최대 높이(두 패널이 아래쪽 모서리인 정사각형의 개수)를 구할 수 있다
     *
     * 그러나 맵의 최대 크기가 1500*1500이므로 3중 반복문을 이용한 풀이는 불가능하다
     * 120번 문제의 단조 스택을 이용한 구간 최소값 로직이 위의 과정을 대체한다
     */

    static int[][] panel;
    static int[] stack;
    static int top = 1; // stack[0] = 0 을 왼쪽 가상 경계로 쓰기 위해 1부터 시작
    static long cnt;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        panel = new int[r+1][c+1];
        for(int i=1; i<=r; i++) {
            char[] line = br.readLine().toCharArray();
            for(int j=1; j<=c; j++) {
                char ch = line[j-1];

                // 정상 패널이면 위쪽 연속 패널 개수에 1 추가
                // 고장 패널이면 0으로 초기화
                if(ch == '1') panel[i][j] = panel[i-1][j] + 1;
            }
        }

        // 인덱스를 1부터 쓰면 0번 자리가 자연스럽게 왼쪽 경계 역할을 함
        // 따라서 패널 배열과 스택, 2중 루프 모두 1-based로 작성
        stack = new int[c+1];

        for(int i=1; i<=r; i++) {
            for(int j=1; j<=c; j++) {
                int val = panel[i][j];
                // 스택 peek가 현재 값보다 크면, 그 원소의 오른쪽 경계가 i로 확정됨
                // 등호를 포함하지 않아 같은 값이 여러 개일 때 중복 집계되지 않음
                while(top > 1 && panel[i][stack[top-1]] > val) {
                    popAndCount(i, j);
                }

                stack[top++] = j;
            }

            // 끝까지 남은 원소들은 오른쪽에 자기보다 작은 값이 없다는 뜻
            // 배열 끝 너머인 c+1을 가상 경계로 삼아 처리
            while(top > 1) {
                popAndCount(i, c+1);
            }
        }

        System.out.println(cnt);
    }

    // 스택에서 하나 꺼내고, 그 원소가 최솟값인 구간들의 기여분을 누적
    // col : 오른쪽에서 자기보다 작은 값이 처음 나오는 열
    static void popAndCount(int row, int col) {
        top--;
        int idx = stack[top];
        long leftWidth = idx - stack[top-1]; // 왼쪽 경계부터 자신까지의 칸 수
        long rightWidth = col - idx;         // 자신부터 오른쪽 경계 직전까지의 칸 수

        // 구간 개수는 두 너비의 곱, 직사각형의 수는 구간 수 * 높이
        cnt += (long)panel[row][idx] * leftWidth * rightWidth;
    }

}