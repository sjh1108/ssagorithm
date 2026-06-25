import java.util.*;

class Solution {

    /**
     * 자료구조 및 알고리즘 : 트리맵
     * 정석적인 풀이는 힙(우선순위 큐) + Lazy Deletion이나, 해당 풀이는 TreeMap을 사용
     * 3개의 명령어 존재 : 큐에 삽입, 최대값 제거, 최소값 제거
     * 트리맵의 key를 숫자, value를 해당 숫자의 개수로 등록하여 관리
     * 삽입 쿼리 -> 주어진 숫자가 key인 항목의 value를 1 증가
     * 최대값/ 최소값 제거 -> TreeMap의 lastKey(), firstKey()로 가장 작은 숫자를 찾아내어 value 1 감소
     * 삭제 연산에 의해 value가 0이 되면 해당 key도 함께 제거
     */

    public int[] solution(String[] ops) {
        // key: 숫자, value: 해당 숫자의 개수
        TreeMap<Integer, Integer> map = new TreeMap<>();

        for(int i=0; i<ops.length; i++) {
            StringTokenizer st = new StringTokenizer(ops[i]);
            char op = st.nextToken().charAt(0);
            int val = Integer.parseInt(st.nextToken());

            if(op == 'I') {
                // 삽입 쿼리
                map.put(val, map.getOrDefault(val, 0) + 1);
            } else {
                // 최대값/ 최소값 제거 쿼리
                // 만약 해당 숫자와 일치하는 key가 없으면 쿼리 무시
                if(map.isEmpty()) continue;

                // 1이면 최대값 제거, -1이면 최소값 제거
                int target = val == 1 ? map.lastKey() : map.firstKey();
                if(map.get(target) == 1) map.remove(target);
                else map.put(target, map.get(target) - 1);
            }
        }
        // 모든 쿼리 종료 후 큐가 비어있을 수 있음
        if(map.isEmpty()) return new int[]{0, 0};

        // 하나라도 값이 존재하면 최소값과 최대값 반환
        int max = map.lastKey(), min = map.firstKey();
        return new int[]{max, min};
    }
}