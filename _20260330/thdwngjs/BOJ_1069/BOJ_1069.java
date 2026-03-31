/**
 * BOJ 1069 - 집으로 (골드 3)
 *
 * [풀이] 수학 (경우의 수 비교)
 * 원점까지의 거리 dist에 대해 가능한 이동 전략을 비교하여 최솟값을 구한다.
 *
 * dist >= D인 경우:
 *   case1: 점프를 최대한 한 뒤, 남은 거리는 걸어감 (jump * T + 나머지 거리)
 *   case2: 전부 걸어감 (dist 그 자체)
 *   case3: 점프를 한 번 더 해서 넘어간 뒤, 걸어서 돌아옴 대신 점프 비용만 계산
 *
 * dist < D인 경우:
 *   case1: 한 번 점프 후 초과 거리만큼 걸어서 되돌아옴
 *   case2: 전부 걸어감
 *   case3: 두 번 점프로 정확히 원점 도달 (한 번 넘어갔다가 한 번 돌아옴)
 */
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int X = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken()); // 점프 거리
        int T = Integer.parseInt(st.nextToken()); // 점프 소요 시간

        double dist = Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2)); // 원점까지의 직선 거리

        int jump = (int)dist / D; // 점프 가능 횟수

        double case1, case2, case3;

        if(dist >= D){
            case1 = T * jump + (dist - (D * jump)); // 점프 + 남은 거리 걷기
            case2 = dist;                            // 전부 걷기
            case3 = T * (jump + 1);                  // 점프 한 번 더 (오버슈팅)
        } else{
            case1 = T + (D - dist);  // 한 번 점프 후 초과분 걷기
            case2 = dist;            // 전부 걷기
            case3 = T * 2;           // 두 번 점프로 정확히 도달
        }

        System.out.println(Math.min(case1, Math.min(case2, case3)));
    }
}