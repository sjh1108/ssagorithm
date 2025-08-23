public class UserSolution_13072 {
	
	// 모든 과정이 최대 3초(연산 횟수 대략 300_000_000) 내에 이루어져야 함  
	
	Team[] teams;
	Soldier[] soldiers;
	
	static class Team {
		// 1~5점을 가진 병사들의 배열을 연결리스트로 표현
		// 각 연결리스트의 헤드와 테일의 인덱스는 -1. 리스트 추적용 객체로만 활용
		Soldier[] heads = new Soldier[6];
		Soldier[] tails = new Soldier[6];
	}
	
	// 병사 클래스
	static class Soldier {
		// 병사의 고유번호, 팀 번호 및 이전, 다음 노드 관리(이중 연결리스트)
		// 스코어는 Team에서만 관리할 것
		int id;
		int team;
		Soldier prev;
		Soldier next;
		
		public Soldier(int id, int team) {
			this.id = id;
			this.team = team;
		}
	}
	
	void init() {
		// 이 시점에 팀 배열 객체 생성
		// 그리고 각 팀별로 병사들을 연결리스트로 관리할 것
		// 평판 업데이트의 편의를 위해 평판 점수에 따른 버킷 정렬을 이용
		teams = new Team[6];
		for(int i=1; i<=5; i++) {
			teams[i] = new Team();
			for(int j=1; j<=5; j++) {
				teams[i].heads[j] = new Soldier(-1, i);
				teams[i].tails[j] = new Soldier(-1, i);
				teams[i].heads[j].next = teams[i].tails[j];
				teams[i].tails[j].prev = teams[i].heads[j];
			}
		}
		
		// 병사 해고 및 병사 개인의 평점 업데이트를 빠르게 하기 위한 인덱스 추적용 배열
		soldiers = new Soldier[100001];
	}
	
	void hire(int mID, int mTeam, int mScore) {
		// 최대 호출 횟수 100_000
		// 병사 정보를 받아서, mTeam으로 팀 조회 후 소속 팀의 mScore번째 리스트에 병사 추가
		// 시간복잡도 O(1) * 100_000 = 100_000
		
		// 팀 조회, 병사 객체 생성
		Team team = teams[mTeam];
		Soldier soldier = new Soldier(mID, mTeam);
		
		// 팀 객체의 mScore번째 리스트에 병사 추가
		team.tails[mScore].prev.next = soldier;
		soldier.prev = team.tails[mScore].prev;
		team.tails[mScore].prev = soldier;
		soldier.next = team.tails[mScore];
		
		// 팀 객체와 별개로 병사 배열에 해당 병사 객체 등록
		soldiers[mID] = soldier;
		
	}
	
	void fire(int mID) {
		// 최대 호출 횟수 100_000
		// id만으로 병사를 즉시 찾아서 제거해야 함
		// 병사 배열에서 인덱스로 병사 객체를 찾아서 제거
		// 시간복잡도 O(1) * 100_000 = 100_000
		Soldier target = soldiers[mID];
		
		// 이중 연결리스트이기 때문에 따로 병사가 소속된 팀 객체를 찾지 않아도 관리 가능
		target.prev.next = target.next;
		target.next.prev = target.prev;
		target.prev = null;
		target.next = null;
		soldiers[mID] = null;
	}
	
	void updateSoldier(int mID, int mScore) {
		// 최대 호출 횟수 100_000
		// 고유번호로 병사를 찾아서 평판을 mScore로 업데이트
		// 팀 번호가 주어지지 않음. 따라서 병사 고유번호로 병사 객체를 찾고 그 안의 팀 번호로 팀 객체를 찾아야 함
		// 조회한 팀의 mScore번째 리스트로 병사 객체를 이동시킴
		// 시간복잡도 O(1) * 100_000
		
		// 대상 병사를 찾아 기존 스코어 리스트에서 제거해주고,
		Soldier target = soldiers[mID];
		target.prev.next = target.next;
		target.next.prev = target.prev;
		
		// 그 팀의 mScore에 해당하는 리스트를 찾아서 해당 병사를  옮긴다
		Team team = teams[target.team];
		team.tails[mScore].prev.next = target;
		target.prev = team.tails[mScore].prev;
		team.tails[mScore].prev = target;
		target.next = team.tails[mScore];
	}
	
	void updateTeam(int mTeam, int mChangeScore) {
		// 최대 호출 횟수 100_000
		// 연결리스트를 이용해 동점수대의 병사 평점을 한꺼번에 업데이트해야 함
		// 각 점수대의 병사 전체를 옮기려는 대상 점수대의 병사 리스트의 헤드와 첫 병사 사이로 옮김
		// 최대 4 * 100_000 = 400_000
		
		// 변화 없음
		if(mChangeScore == 0) return;
		
		Team team = teams[mTeam];
		if(mChangeScore > 0) {
			// 추가할 평점이 양수일 때
			for(int i=4; i>=1; i--) {
				// 아무것도 없는 리스트는 무시
				if(team.heads[i].next == team.tails[i]) continue;
				// 평점 변화 후 점수 계산
				int finalScore = i + mChangeScore;
				if(finalScore > 5) finalScore = 5;
				// 리스트 전체를 목적 버킷의 헤더와 첫 병사 사이로 연결
				updateConnect(team, i, finalScore);
			}
		} else {
			// 추가할 평점이 음수일 때
			for(int i=2; i<=5; i++) {
				// 아무것도 없는 리스트는 무시
				if(team.heads[i].next == team.tails[i]) continue;
				// 평점 변화 후 점수 계산
				int finalScore = i + mChangeScore;
				if(finalScore < 1) finalScore = 1;
				// 리스트 전체를 목적 버킷의 헤더와 첫 병사 사이로 연결
				updateConnect(team, i, finalScore);
			}
		}
	}
	
	void updateConnect(Team team, int before, int after) {
		team.heads[before].next.prev = team.heads[after];
		team.tails[before].prev.next = team.heads[after].next;
		team.heads[after].next.prev = team.tails[before].prev;
		team.tails[before].prev = team.heads[before];
		team.heads[after].next = team.heads[before].next;
		team.heads[before].next = team.tails[before];
	}
	
	int bestSoldier(int mTeam) {
		// 최대 호출 횟수 100
		// 높은 평판의 병사 리스트부터 내림차순으로 탐색
		// 탐색중인 점수대의 병사 리스트에 병사가 한 명이라도 있다면 즉시 순차 탐색하여 가장 고유번호가 높은 병사를 확인
		// 최대 100_000 * 100 = 10_000_000
		
		Team team = teams[mTeam];
		
		int maxId = -1;
		for(int i=5; i>=1; i--) {
			Soldier target = team.heads[i].next;
			// 헤더 다음 테일 -> 그 사이에 병사 없음 -> 무시
			if(target == team.tails[i]) continue;
			// 리스트 끝까지 순차 탐색하며 고유 번호가 가장 높은 병사 탐색
			while(target != team.tails[i]) {
				if(target.id > maxId) maxId = target.id;
				target = target.next;
			}
			// 그 밑의 리스트는 확인할 필요 없음
			break;
		}
		
		return maxId;
	}
	
}
