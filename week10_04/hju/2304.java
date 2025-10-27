import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // 1. 기둥 정보 저장 및 가장 높은 기둥 찾기
        int[] heights = new int[1001];
        int maxL = 0; // 가장 오른쪽 기둥의 위치
        int maxH = 0; // 가장 높은 기둥의 높이
        int maxH_L = 0; // 가장 높은 기둥의 위치

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int H = Integer.parseInt(st.nextToken());
            
            heights[L] = H;
            if (maxH < H) {
                maxH = H;
                maxH_L = L;
            }
            maxL = Math.max(maxL, L);
        }

        int totalArea = 0;

        // 2. 왼쪽 면적 계산
        int currentMaxHeight = 0;
        // 가장 왼쪽부터 가장 높은 기둥까지 스캔
        for (int i = 0; i <= maxH_L; i++) {
            // 현재까지의 최대 높이를 지붕 높이로 사용
            currentMaxHeight = Math.max(currentMaxHeight, heights[i]);
            totalArea += currentMaxHeight;
        }

        // 3. 오른쪽 면적 계산
        currentMaxHeight = 0;
        // 가장 오른쪽부터 가장 높은 기둥 바로 옆까지 스캔
        for (int i = maxL; i > maxH_L; i--) {
            currentMaxHeight = Math.max(currentMaxHeight, heights[i]);
            totalArea += currentMaxHeight;
        }

        System.out.println(totalArea);
    }
}