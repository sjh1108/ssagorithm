package week01_02.thdwngjs.BOJ_5021;

import java.util.*;
import java.io.*;

// 백준 5021 - 왕위 계승 (DFS + Memoization)
class Main{
    // 자식 이름을 키로, 부모 이름 배열(String[2])을 값으로 저장하는 맵
    private static HashMap<String, String[]> family;
    // 각 사람의 혈통 비율을 저장하는 맵 (Memoization용)
    private static HashMap<String, Double> blood;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken()); // 가족 정보의 개수
        int M = Integer.parseInt(st.nextToken()); // 왕위 계승 후보자의 수

        family = new HashMap<>();
        blood = new HashMap<>();

        String king = br.readLine(); // 왕의 이름
        
        // 가족 정보 입력
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            String kid = st.nextToken();
            String p1 = st.nextToken();
            String p2 = st.nextToken();

            family.put(kid, new String[]{p1, p2});

            // 초기값 -1.0으로 설정 (방문하지 않았음을 표시)
            // 부모들도 맵에 키를 등록해두어야 나중에 DFS에서 참조할 때 안전함
            blood.put(kid, -1D);
            blood.put(p1, -1D);
            blood.put(p2, -1D);
        }

        // 왕의 혈통은 1.0 (100%)
        blood.put(king, 1d);

        // 맵에 등록된 모든 사람에 대해 혈통 비율 계산
        for(String s: blood.keySet()) dfs(s);

        // 첫 번째 후보자 처리
        String succeed = br.readLine();
        // 후보자가 blood 맵에 없을 수도 있으므로 getOrDefault 사용 (없으면 0.0)
        double max = blood.getOrDefault(succeed, 0.0);
        
        // 나머지 M-1명의 후보자 처리
        for(int i = 0; i < M-1; i++){
            String s = br.readLine();
            double currentBlood = blood.getOrDefault(s, 0.0);

            // 더 높은 혈통 비율을 가진 후보자가 나타나면 갱신
            if(currentBlood > max){
                max = currentBlood;
                succeed = s;
            }
        }

        System.out.println(succeed);
    }

    // DFS를 이용해 혈통 비율 계산 (Top-Down 방식)
    private static double dfs(String kid){
        // Memoization: 이미 계산된 값이 있다면(-1이 아니라면) 그 값을 반환
        // 왕의 경우 초기값이 1.0이므로 여기서 바로 반환됨
        if(blood.get(kid) != -1){
            return blood.get(kid);
        }

        // 부모 정보가 없는 경우 (왕이 아니면서 족보에 없는 시조 등)
        // 혈통 비율은 0.0
        if(family.get(kid) == null){
            blood.put(kid, 0d);
            return 0;
        }

        // 부모의 혈통 비율을 재귀적으로 계산
        double p1 = dfs(family.get(kid)[0]);
        double p2 = dfs(family.get(kid)[1]);

        // 본인의 혈통 비율 = (부모1 혈통 + 부모2 혈통) / 2
        blood.put(kid, (p1+p2) / 2);

        return blood.get(kid);
    }
}