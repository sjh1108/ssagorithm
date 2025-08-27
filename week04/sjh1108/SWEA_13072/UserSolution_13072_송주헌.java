package week04.sjh1108.SWEA_13072;

import java.util.HashMap;

class UserSolution_13072_송주헌 {

    // 각 병사를 나타내는 이중 연결 리스트의 노드
    static class SoldierNode {
        int mID;
        int mTeam;
        int mScore; // 현재 점수를 노드에 저장하여 O(1) 시간 복잡도로 updateSoldier 구현
        SoldierNode prev, next;

        public SoldierNode(int mID, int mTeam, int mScore) {
            this.mID = mID;
            this.mTeam = mTeam;
            this.mScore = mScore;
            this.prev = null;
            this.next = null;
        }
    }

    // 각 [팀][점수] 그룹에 대한 이중 연결 리스트
    static class SoldierList {
        SoldierNode head, tail; // 리스트의 시작과 끝을 감시하는 센티널 노드

        public SoldierList() {
            head = new SoldierNode(0, 0, 0); // 더미 헤드
            tail = new SoldierNode(0, 0, 0); // 더미 테일
            head.next = tail;
            tail.prev = head;
        }
    }

    // 문제 제약 조건에 따른 상수
    final int MAX_TEAMS = 6;  // 팀 번호 1~5 사용
    final int MAX_SCORES = 6; // 점수 1~5 사용

    // 핵심 데이터 구조
    HashMap<Integer, SoldierNode> soldierMap; // mID로 노드를 바로 찾기 위한 맵
    SoldierList[][] teamLists;                // [팀][점수]별 병사 리스트

    /**
     * 각 테스트 케이스 시작 시 호출되어 자료구조를 초기화합니다.
     */
    public void init() {
        soldierMap = new HashMap<>();
        teamLists = new SoldierList[MAX_TEAMS][MAX_SCORES];

        for (int i = 1; i < MAX_TEAMS; i++) {
            for (int j = 1; j < MAX_SCORES; j++) {
                teamLists[i][j] = new SoldierList();
            }
        }
    }

    /**
     * 병사를 고용하고 알맞은 리스트에 추가합니다. (O(1))
     */
    public void hire(int mID, int mTeam, int mScore) {
        SoldierNode newNode = new SoldierNode(mID, mTeam, mScore);
        soldierMap.put(mID, newNode);

        // 해당 팀/점수 리스트의 맨 앞에 노드 추가
        SoldierList list = teamLists[mTeam][mScore];
        newNode.next = list.head.next;
        list.head.next.prev = newNode;
        list.head.next = newNode;
        newNode.prev = list.head;
    }

    /**
     * mID를 가진 병사를 해고합니다. (O(1))
     */
    public void fire(int mID) {
        SoldierNode nodeToFire = soldierMap.remove(mID);
        if (nodeToFire != null) {
            // 노드의 prev/next 포인터를 사용하여 리스트에서 제거
            nodeToFire.prev.next = nodeToFire.next;
            nodeToFire.next.prev = nodeToFire.prev;
        }
    }

    /**
     * 특정 병사의 점수를 변경합니다. (O(1))
     */
    public void updateSoldier(int mID, int mScore) {
        SoldierNode nodeToUpdate = soldierMap.get(mID);
        if (nodeToUpdate != null) {
            // 1. 기존 리스트에서 노드 제거
            nodeToUpdate.prev.next = nodeToUpdate.next;
            nodeToUpdate.next.prev = nodeToUpdate.prev;

            // 2. 노드의 점수 업데이트
            nodeToUpdate.mScore = mScore;

            // 3. 새로운 점수에 맞는 리스트의 맨 앞에 추가
            SoldierList newList = teamLists[nodeToUpdate.mTeam][mScore];
            nodeToUpdate.next = newList.head.next;
            newList.head.next.prev = nodeToUpdate;
            newList.head.next = nodeToUpdate;
            nodeToUpdate.prev = newList.head;
        }
    }

    /**
     * 특정 팀의 모든 병사 점수를 일괄 변경합니다. (O(1))
     * 개별 병사를 옮기는 대신, 리스트 자체를 합치거나 포인터를 교체합니다.
     */
    public void updateTeam(int mTeam, int mChangeScore) {
        if (mChangeScore == 0) {
            return;
        }

        // 1. 기존 리스트들을 임시 변수에 저장합니다.
        SoldierList[] originalLists = teamLists[mTeam];

        // 2. 결과를 담을 새로운 리스트 배열을 준비합니다. (모두 빈 리스트로 시작)
        SoldierList[] newLists = new SoldierList[MAX_SCORES];
        for (int i = 1; i < MAX_SCORES; i++) {
            newLists[i] = new SoldierList();
        }

        // 3. 단일 for문으로 1점부터 5점까지 순회하며 병사들을 새 리스트로 옮깁니다.
        for (int s = 1; s < MAX_SCORES; s++) {
            // 변경될 점수를 계산하고 1~5점 범위로 맞춥니다.
            int newScore = s + mChangeScore;
            if (newScore > 5) newScore = 5;
            if (newScore < 1) newScore = 1;

            // 기존 s점 리스트의 모든 병사를 newScore점 리스트로 합칩니다.
            concatenate(newLists[newScore], originalLists[s]);
        }

        // 4. 작업이 완료된 새로운 리스트 배열을 원래 위치에 할당합니다.
        teamLists[mTeam] = newLists;
    }

    /**
     * listB의 모든 노드를 listA의 끝에 연결합니다. listB는 비워집니다.
     */
    private void concatenate(SoldierList listA, SoldierList listB) {
        if (listB.head.next == listB.tail) return; // listB가 비어있으면 할 일 없음

        // A의 끝과 B의 시작을 연결
        listA.tail.prev.next = listB.head.next;
        listB.head.next.prev = listA.tail.prev;

        // A의 끝을 B의 끝으로 변경
        listA.tail = listB.tail;

        // B 리스트는 비움 (새로운 센티널 노드 할당)
        listB.head = new SoldierNode(0, 0, 0);
        listB.tail = new SoldierNode(0, 0, 0);
        listB.head.next = listB.tail;
        listB.tail.prev = listB.head;
    }

    /**
     * 특정 팀에서 최고의 병사를 찾습니다. (최악의 경우 O(K))
     */
    public int bestSoldier(int mTeam) {
        int maxID = 0;
        // 5점부터 1점까지 순회
        for (int s = MAX_SCORES - 1; s >= 1; s--) {
            SoldierList currentList = teamLists[mTeam][s];
            if (currentList.head.next != currentList.tail) { // 리스트에 병사가 있으면
                // 해당 리스트를 순회하며 가장 큰 ID를 찾음
                SoldierNode curr = currentList.head.next;
                while (curr != currentList.tail) {
                    if (curr.mID > maxID) {
                        maxID = curr.mID;
                    }
                    curr = curr.next;
                }
                // 가장 높은 점수대의 병사들을 확인했으므로 바로 반환
                return maxID;
            }
        }
        return 0; // 문제 제약상 이 경우는 없음
    }
}