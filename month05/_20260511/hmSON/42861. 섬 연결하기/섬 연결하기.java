import java.util.*;

class Solution {

    // 자료구조 및 알고리즘 : MST(크루스칼)
    // 모든 섬을 연결할 수 있는 최소 비용을 구한다.
    // 건너야 하는 다리의 수와 관계 없이 섬 A로부터 섬 B까지 갈 수 있다면, 두 섬 A와 B는 연결된 것이다.

    // 각 다리를 비용 순으로 오름차순 정렬
    // 비용이 적은 다리부터 연결 시도(유니온)
    // 이미 두 섬간 이동 가능한 상태인 경우(사이클 발생) 그 다리는 설치하지 않음.

    // 각 섬이 소속된 대표 섬 번호를 등록한다. 연결된 섬이 없다면 자기 자신이 대표이다.
    static int[] head;

    // 주어진 섬의 대표 섬 번호를 찾는다.
    // 자기 자신이 대표 섬인 경우 그 값을 그대로 반환한다.
    // 차후 경로 탐색 횟수를 최소화하기 위해 경로 압축을 수행한다.
    static int find(int x) {
        if(head[x] == x) return x;
        return head[x] = find(head[x]);
    }

    // 두 섬을 하나의 그룹으로 연동한다.
    // 이미 같은 그룹에 속한 경우 false 반환
    // 두 섬 중 더 작은 인덱스를 선택하여 대표로 등록 -> 경로 압축 수단 2
    static boolean union(int a, int b) {
        int ra = find(a), rb = find(b);
        if(ra == rb) return false;

        if(ra < rb) head[rb] = ra;
        else head[ra] = rb;
        return true;
    }

    public int solution(int n, int[][] costs) {
        // 주어진 다리들을 비용 오름차순 정렬
        Arrays.sort(costs, Comparator.comparingInt(a -> a[2]));

        // 연결 전에는 모든 섬이 각 그룹의 대표 섬임
        head = new int[n];
        for(int i=0; i<n; i++) head[i] = i;
        
        int cnt = 0, totalCost = 0;
        // cnt : 설치한 다리 수. 다리 수가 n-1이 되면 모든 섬을 연결하게 되어 추가 설치가 불필요하다.
        for(int i=0; i<costs.length && cnt < n-1; i++) {
            // 다리를 설치할 두 섬을 한 그룹으로 연동
            boolean linked = union(costs[i][0], costs[i][1]);
            
            // 연동 성공한 경우 카운트 증가 및 총 비용 합산
            if(linked) {
                cnt++;
                totalCost += costs[i][2];
            }
        }

        // 반환 : 총 설치 비용
        return totalCost;
    }

}