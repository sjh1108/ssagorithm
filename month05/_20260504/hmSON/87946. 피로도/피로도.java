class Solution {

    // 자료구조 및 알고리즘 : 백트래킹
    // 매 재귀마다 입장 조건을 충족하면 해당 던전을 방문 처리하고 사용한 피로도만큼 현재 피로도 차감

    static int max = 0;
    static int[][] d;
    static boolean[] visited;

    public int solution(int k, int[][] dungeons) {
        d = dungeons;
        visited = new boolean[d.length];
        bt(0, k);
        return max;
    }
    
    private static void bt(int cnt, int curr) {
        // 더 이상 탐색할 던전이 없으면
        // 현재까지 탐색한 최대 던전 수 갱신 후 리턴
        if(cnt >= d.length) {
            max = cnt;
            return;
        }
        
        for(int i=0; i<d.length; i++) {
            if(visited[i]) continue;
            int limit = d[i][0];
            if(curr < limit) {
                max = Math.max(cnt, max);
                continue;
            }
            visited[i] = true;
            bt(cnt+1, curr - d[i][1]);
            visited[i] = false;
        }
    }
}