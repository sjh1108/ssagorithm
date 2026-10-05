import java.util.Arrays;

class Solution {

    /*
     * 자료구조 및 알고리즘 : 매개변수 탐색, 트리, DFS(스택), 그리디, 구현
     * 시험장 전체를 k개 그룹으로 분리하였을 때, 최대 그룹 인원수의 최소값을 구해야 함
     * -> 각 그룹의 인원수를 x로 제한한다고 할 때, 그룹의 수가 k 이하가 되게 하는 x의 최소값을 구해야 함
     *
     * 그룹별 제한 인원수 x를 구하는 과정은 매개변수 탐색으로 진행
     * 임의의 제한 x 지정 -> 만들어지는 그룹 수가 k 이하인지 확인 -> 결과 확인 후 탐색 범위 축솨
     * 그룹 수 판정 로직은 스택 DFS로 진행
     * 1. 현 노드와 두 서브트리의 인원수 합치기
     * 2-1. 제한 인원수 x를 초과하는가? 두 서브트리 중 인원수가 많은 쪽을 다른 그룹으로 분리
     * 2-2. 그래도 제한 인원수 x를 초과하는가? 양쪽 서브트리 다 별개 그룹으로 분리
     * 3. 위 과정 이후 남은 값만 상위 노드로 반환
     * 4. 모든 과정이 끝났을 때, 생성된 그룹 수가 k 이하인지 아닌지 반환
     *
     * 문제에서 이진 트리임을 전제로 하고 있어 위 로직이 성립 가능
     */

    // 만들어야 할 그룹 수, 트리의 루트 인덱스
    static int group, root = -1;
    // 노드별 인원수, 본인을 루트로 하는 트리의 인원수 합, DFS 스택 배열
    static int[] cnt, sum, stack;
    // DFS 과정에서 진입 여부 판단, 하위 트리로 갔다가 돌아오는 과정 때문에 한 노드에 2번 접근하기 때문
    static boolean[] entered;
    // 노드별 자식 노드 번호(없으면 -1)
    static int[][] child;

    public int solution(int k, int[] num, int[][] links) {
        // 미리 static으로 옮겨두기
        group = k;
        cnt = num;
        child = links;

        // 루트 노드 찾기
        // 단 한 번도 자식 노드로 지정되지 않은 노드 하나를 찾아야 함
        int n = cnt.length;
        boolean[] isChild = new boolean[n];
        for(int i=0; i<n; i++) {
            if(child[i][0] != -1) isChild[child[i][0]] = true;
            if(child[i][1] != -1) isChild[child[i][1]] = true;
        }

        sum = new int[n];
        stack = new int[n];
        entered = new boolean[n];

        for(int i=0; i<n; i++) {
            if(!isChild[i]) {
                root = i; break;
            }
        }

        // 매개변수 탐색의 범위 제한
        // 최소값 : 가장 인원수가 많은 시험장의 인원수, 최대값 : 모든 시험장의 인원수 합
        int left = 0, right = 0;
        for(int x : cnt) {
            if(x > left) left = x;
            right += x;
        }
        // 그룹 수가 1이면 이후 로직이 필요 없음. 그냥 전체 인원수 합 반환
        // 생각해보니까 k == n일 때에는 left 반환하고 끝낼 수도 있었음
        // if(k == n) return left;
        if(k == 1) return right;

        // 매개변수 탐색
        // 임의의 그룹별 제한 인원수 지정
        // 그룹이 k개 이하로 생성되면 right를 감소시켜 더 작은 값 탐색
        // 그룹이 k개 초과로 생성되면 left를 증가시켜 더 큰 값 탐색
        while(left <= right) {
            int mid = left + (right - left) / 2;
            if(check(mid)) right = mid - 1;
            else left = mid + 1;
        }

        return left;
    }

    // 그룹 수 검증용 스택 DFS 메서드
    // 임의의 제한 인원수를 매개변수로 받아 DFS 수행하면서 그룹 수가 k개 이하로 생성되는지 확인
    private boolean check(int limit) {
        // 초기화
        Arrays.fill(sum, 0);
        Arrays.fill(entered, false);
        int top = 0;
        int cutCnt = 0; // 분리 횟수. DFS가 끝났을 때 분리 횟수는 그룹 수 미만이어야 함

        stack[top++] = root;

        while(top > 0) {
            int cur = stack[top-1];

            // 스택 DFS에서 한 노드에 총 2번 접근함(진입 시점, 탈출 시점)
            // 진입 시점 : 진입 여부 체크하고 자식 노드를 스택에 추가
            // 탈출 시점 : 노드 자신을 포함한 트리의 인원수 합을 구하고, 그에 따른 그룹 분리 및 반환 진행
            if(!entered[cur]) {
                entered[cur] = true;
                if(child[cur][0] != -1) stack[top++] = child[cur][0];
                if(child[cur][1] != -1) stack[top++] = child[cur][1];
            } else {
                top--;

                // 양쪽 하위 트리의 인원수 합 가져오기(없으면 0, 분리된 그룹의 인원수가 감소된 채로 반환된 값임
                int left = child[cur][0], right = child[cur][1];
                int cntLeft = left == -1 ? 0 : sum[left];
                int cntRight = right == -1 ? 0 : sum[right];
                int curSum = cnt[cur] + cntLeft + cntRight;

                // 1. 총 인원수가 제한 인원수 초과인가?
                // -> 두 서브트리 중 인원수가 더 많은 쪽을 별도 그룹으로 분리
                // 분리된 그룹의 인원수 차감 및 그룹 카운트 증가
                if(curSum > limit) {
                    curSum -= Math.max(cntLeft, cntRight);
                    cutCnt++;
                }

                // 2. 그럼에도 총 인원수가 제한 인원수 초과인가?
                // -> 남은 서브트리도 별도 그룹으로 분리
                // 양쪽 서브트리가 전부 떨어졌으므로 노드 자신의 인원수를 저장 및 반환해야 함
                // 그룹 카운트 추가
                if(curSum > limit) {
                    curSum = cnt[cur];
                    cutCnt++;
                }

                sum[cur] = curSum;
            }
        }

        return cutCnt < group;
    }

}