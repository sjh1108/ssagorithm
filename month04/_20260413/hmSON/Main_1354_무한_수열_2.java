import java.io.*;
import java.util.*;

public class Main_1354_무한_수열_2 {

    /*
     * BOJ_1354 : 무한 수열 2 (Gold_5)
     * 자료구조 및 알고리즘 : DP, 재귀, 해시맵
     *
     * [문제 요약]
     * - 수열 A는 다음과 같이 정의된다.
     *   A(i) = A(i / p - x) + A(i / q - y)
     * - 단, i < 1 이면 A(i) = 1 이다.
     * - n, p, q, x, y가 주어졌을 때 A(n)을 구해야 한다.
     *
     * [핵심 아이디어]
     * - 점화식 자체는 DP 형태이지만, n의 범위가 매우 커서 배열을 만들 수 없다.
     * - 또한 i / p - x, i / q - y 과정에서 음수도 나올 수 있어
     *   일반적인 바텀업 방식으로 0부터 채워 가는 접근이 불가능하다.
     * - 따라서 필요한 상태만 재귀적으로 계산하고,
     *   한 번 계산한 값은 HashMap에 저장해서 재사용하는 메모이제이션 방식이 적합하다.
     *
     * [구현 메모]
     * - recursion(cur):
     *   - cur < 1 이면 정의에 따라 1 반환
     *   - 이미 계산된 값이면 dp(HashMap)에서 바로 반환
     *   - 아니면 점화식대로 두 항을 계산해 합산 후 저장
     * - (cur / p - x)와 (cur / q - y)가 같은 경우에는
     *   같은 재귀를 두 번 호출할 필요 없이 한 번 구한 값을 2배 해준다.
     * - 각 항이 1 미만으로 내려가는 경우는 더 재귀하지 않고 바로 1로 처리한다.
     * - 배열 대신 HashMap<Long, Long>을 사용해
     *   실제로 등장하는 상태만 저장한다.
     *
     * [시간 복잡도]
     * - 서로 다른 상태의 개수를 M이라 하면,
     *   각 상태는 한 번만 계산되므로 O(M)
     * - M은 점화식으로 실제 도달하는 값의 수에 따라 결정된다.
     */

    static int p, q, x, y;
    static long n;
    static HashMap<Long, Long> dp = new HashMap<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Long.parseLong(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        System.out.println(recursion(n));
    }

    static long recursion(long cur) {
        if(cur < 1) return 1;

        // 이미 계산한 값이면 재사용
        Long res = dp.get(cur);
        if(res != null) return res;

        long sum = 0;

        // 첫 번째 항: A(cur / p - x)
        sum += (cur/p > x ? recursion(cur/p - x) : 1);

        // 두 항의 인자가 같으면 같은 값을 두 번 더하는 것이므로 2배 처리
        if(cur/p - x == cur/q - y) sum *= 2;
            // 다르면 두 번째 항을 별도로 계산
        else sum += (cur/q > y ? recursion(cur/q - y) : 1);

        dp.put(cur, sum);
        return sum;
    }

}