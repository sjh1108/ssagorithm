package week01_04.sjh1108.BOJ_1744;

import java.util.*;
import java.io.*;

// 백준 1744 - 수 묶기 (Greedy)
class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static int N;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        // 양수와 0을 포함한 음수를 분리하여 저장
        // 0은 음수 리스트에 넣는 것이 유리함 (가장 작은 음수와 곱해서 0으로 만들어 없앨 수 있으므로)
        // 하지만 여기서는 단순히 양수 / 0이하로 나누는 로직이 아니라
        // 양수는 pList, 0과 음수는 nList로 분류합니다.
        List<Integer> pList = new ArrayList<>();
        List<Integer> nList = new ArrayList<>();

        for(int i = 0; i < N; i++){
            int num = Integer.parseInt(br.readLine());
            if(num > 0) pList.add(num);
            else nList.add(num);
        }

        // 양수는 내림차순 정렬 (큰 수끼리 묶어야 이득)
        Collections.sort(pList, Collections.reverseOrder());
        // 음수(및 0)는 오름차순 정렬 (작은 수(절댓값이 큰 음수)끼리 묶어야 양수가 되어 이득)
        Collections.sort(nList);

        int sum = 0;
        
        // 1. 양수 처리
        int i = 0;
        while(i < pList.size()){
            // 다음 수가 있고, 현재 수와 다음 수 모두 1이 아닐 때만 묶음
            // (1은 곱하는 것보다 더하는 것이 이득: 1*x = x < 1+x)
            if(i + 1 < pList.size() && pList.get(i) != 1 && pList.get(i + 1) != 1)
                sum += pList.get(i++) * pList.get(i++);
            else
                sum += pList.get(i++);
        }

        // 2. 음수(및 0) 처리
        i = 0;
        while(i < nList.size()){
            // 다음 수가 있으면 무조건 묶음
            // (음수 * 음수 = 양수, 음수 * 0 = 0 으로 되어 이득)
            // 남은 하나는 그냥 더함 (음수일 경우 손해지만 어쩔 수 없음)
            if(i + 1 < nList.size())
                sum += nList.get(i++) * nList.get(i++);
            else
                sum += nList.get(i++);
        }

        System.out.println(sum);
    }
}