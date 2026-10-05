import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Solution {

    /*
     * 자료구조 및 알고리즘 : 문자열, 완전 탐색, BFS
     * 모든 문자열은 동일한 길이로 주어지고, words 배열에 있는 단어 중 한 개의 알파벳만 다른 단어로 변환 가능
     * 총 몇 번의 변환을 거쳐 target 단어를 만들 수 있는지 구해야 함
     *
     * begin 문자열과 words 배열 내 단어들을 전부 미리 비교해서 Integer 타입 인접 리스트 전처리
     * BFS 역시 동일하게 인덱스와 변환 횟수를 관리하는 int[] 배열로 동일하게 처리
     * words 배열 내에 target 단어 자체가 없거나 변환 과정이 존재하지 않으면 0 출력
     */

    // 인접 리스트
    static List<Integer>[] graph;

    public int solution(String begin, String target, String[] words) {
        int len = words.length;
        // begin을 인덱스 0으로, words 배열의 문자열들을 인덱스 1~len으로 지정하여 인접 리스트 생성
        graph = new ArrayList[len+1];
        for(int i=0; i<=len; i++) graph[i] = new ArrayList<>();

        for(int i=0; i<len; i++) {
            String word = words[i];
            // 시작 단어와 words 단어 비교
            // 변환 가능할 경우 단방향으로 리스트 등록(시작점으로 돌아가는 링크를 추가할 필요 없음)
            boolean flag = isConvertable(begin, word);
            if(flag) graph[0].add(i+1);

            for(int j=i+1; j<len; j++) {
                // words 내 두 단어간 비교
                // 변환 가능할 경우 양방향으로 리스트 등록
                flag = isConvertable(word, words[j]);
                if(!flag) continue;

                graph[i+1].add(j+1);
                graph[j+1].add(i+1);
            }
        }

        return bfs(target, words);
    }

    // 변환 가능 여부 판단 메서드
    // 두 문자열을 한 자씩 비교하여 다른 알파벳이 정확히 1개인 경우만 변환 가능하다고 판단
    private boolean isConvertable(String src, String target) {
        int cnt = 0;
        for(int i=0; i<src.length(); i++) {
            if(src.charAt(i) != target.charAt(i)) cnt++;
            if(cnt > 1) return false;
        }

        return cnt == 1;
    }

    // 단어 변환 BFS 메서드
    // begin을 target으로 변환하기 위한 단계 수 반환
    // 불가능할 경우 0 반환
    private int bfs(String target, String[] words) {
        // 방문 처리 배열
        boolean[] visited = new boolean[words.length + 1];
        visited[0] = true;
        // BFS용 큐. 각 배열은 { 단어 인덱스, 변환 횟수 } 관리
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0});

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for(int next : graph[cur[0]]) {
                if(visited[next]) continue;
                visited[next] = true;

                // 변환할 다음 단어가 target이면 변환 횟수 반환
                if(words[next-1].equals(target)) return cur[1] + 1;
                q.add(new int[]{next, cur[1] + 1});
            }
        }

        // target에 도달 불가능하면 0 반환
        return 0;
    }

}