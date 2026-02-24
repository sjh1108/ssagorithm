import java.io.*;
import java.util.*;

public class Main_2036_수열의_점수 {

    /*
     * BOJ_2036 : 수열의 점수 (Gold_4)
     * 자료구조 및 알고리즘 : 그리디, 정렬
     *
     * [문제 요약]
     * - N개의 정수가 주어진다.
     * - 각 수는 "그대로 더하거나", "두 개를 묶어 곱한 뒤 더하는" 방식으로 점수를 만든다.
     * - 최종 합을 최대로 만들었을 때의 값을 출력한다.
     *
     * [핵심 아이디어]
     * - 곱셈은 특정 구간에서 덧셈보다 유리해질 수 있다.
     *   1) 2 이상의 양수는 큰 수끼리 곱하면 합보다 커지므로 내림차순으로 2개씩 묶는 것이 이득.
     *   2) 0 이하(음수 포함)는 작은 수(절댓값 큰 음수)끼리 곱하면 양수가 되거나 손해가 줄어드므로 오름차순으로 2개씩 묶는 것이 이득.
     *   3) 1은 어떤 수와 곱해도 이득이 없다(1*x = x보다 1 + x가 더 큼).
     *      -> 1은 묶지 않고 그냥 더하는 것이 최선.
     * - 따라서 정렬 후, 왼쪽(작은 값)에서 음수/0 묶기, 오른쪽(큰 값)에서 2 이상 양수 묶기를 처리하고
     *   남은 값(특히 1 포함)은 모두 그대로 더한다.
     *
     * [구현 메모]
     * - 배열을 오름차순 정렬한다.
     * - left 포인터:
     *   - arr[left+1] < 1 인 동안(즉, 두 번째 값이 0 이하인 동안) 두 개씩 곱해 sum에 더한다.
     *   - 이 조건 덕분에 (음수, 음수) 또는 (음수, 0) / (0, 0)처럼 0 이하 묶기만 수행한다.
     * - right 포인터:
     *   - arr[right-1] > 1 인 동안(즉, 두 번째 값이 2 이상인 동안) 두 개씩 곱해 sum에 더한다.
     *   - 1은 제외되므로 1과의 곱을 피하게 된다.
     * - 남은 구간 [left, right]는 더 이상 "곱으로 이득 볼 쌍"이 없으므로 전부 개별 합산한다.
     * - 곱셈 결과가 int 범위를 넘을 수 있어 long으로 누적한다.
     *
     * [시간 복잡도]
     * - 정렬: O(N log N)
     * - 포인터 순회: O(N)
     * - 총: O(N log N)
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        for(int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        Arrays.sort(arr);

        long sum = 0;
        int left = 0, right = n-1;

        // 0 이하(음수/0)는 작은 값부터 2개씩 묶어서 곱하는 게 유리
        while(left < right && arr[left+1] < 1) {
            sum += (long)arr[left] * arr[left+1];
            left += 2;
        }

        // 2 이상의 양수는 큰 값부터 2개씩 묶어서 곱하는 게 유리
        while(left < right && arr[right-1] > 1) {
            sum += (long)arr[right] * arr[right-1];
            right -= 2;
        }

        // 남은 값들(특히 1 포함)은 곱으로 더해도 이득이 없으므로 각각 더한다.
        for(int i=left; i<=right; i++) {
            sum += arr[i];
        }

        System.out.println(sum);
    }

}
