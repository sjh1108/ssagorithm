import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 단조 스택
     *
     * 모든 구간의 최소값 합산 결과를 반환한다
     * 구간 수가 N(N+1)/2 로 최대 200억 개라 직접 순회는 불가능
     *
     * "각 원소가 최소값으로 뽑히는 구간이 몇 개인가"를 세는 방식으로 진행
     * 어떤 원소가 구간의 최소값이 되려면 그 구간이 좌우로 자신보다 작은 값을 넘지 말아야 함
     * 따라서 각 원소마다 (왼쪽으로 뻗을 수 있는 칸 수) * (오른쪽으로 뻗을 수 있는 칸 수)가 각 원소가 최소값인 구간의 개수이다
     *
     * 단조 스택 : 왼쪽 방향에서 자신보다 작은 값의 첫 위치를 확인하기 위함
     * 따라서 스택의 peek보다 작은 값이 들어오면 peek를 스택에서 꺼내고 기여하는 구간을 계산한다
     * -> 오른쪽 경계 : 지금 들어온 값의 위치(또는 종료 시점)
     * -> 왼쪽 경계 : 스택에서 자기 바로 아래에 있는 인덱스(또는 0)
     *
     * 원소값 * 구간 개수를 모두 더하면 답
     */

    static int[] amount, stack;
    static int top = 1; // stack[0] = 0 을 왼쪽 가상 경계로 쓰기 위해 1부터 시작
    static long sum = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 인덱스를 1부터 쓰면 0번 자리가 자연스럽게 왼쪽 경계 역할을 함
        amount = new int[n+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1; i<=n; i++) {
            amount[i] = Integer.parseInt(st.nextToken());
        }

        stack = new int[n+1];

        for(int i=1; i<=n; i++) {
            int val = amount[i];
            // 스택 peek가 현재 값보다 크면, 그 원소의 오른쪽 경계가 i로 확정됨
            // 등호를 포함하지 않아 같은 값이 여러 개일 때 중복 집계되지 않음
            while(top > 1 && amount[stack[top-1]] > val) {
                popAndAccumulate(i);
            }

            stack[top++] = i;
        }

        // 끝까지 남은 원소들은 오른쪽에 자기보다 작은 값이 없다는 뜻
        // 배열 끝 너머인 n+1을 가상 경계로 삼아 처리
        while(top > 1) {
            popAndAccumulate(n+1);
        }

        System.out.println(sum);
    }

    // 스택에서 하나 꺼내고, 그 원소가 최솟값인 구간들의 기여분을 누적
    // current : 오른쪽에서 자기보다 작은 값이 처음 나오는 위치
    static void popAndAccumulate(int current) {
        top--;
        int idx = stack[top];
        long leftWidth = idx - stack[top-1]; // 왼쪽 경계부터 자신까지의 칸 수
        long rightWidth = current - idx;     // 자신부터 오른쪽 경계 직전까지의 칸 수

        // 구간 개수는 두 너비의 곱, 값이 최대 10^6이고 개수가 커서 long 필수
        sum += (long)amount[idx] * leftWidth * rightWidth;
    }

}