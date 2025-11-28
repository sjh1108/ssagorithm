import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_4195_친구_네트워크 {
	
	/*
	 * BOJ_4195 : 친구 네트워크 (Gold_2)
	 * 자료구조 및 알고리즘 : 유니온 파인드, 해시맵
	 *
	 * [문제 요약]
	 * - 친구 관계(F)가 주어질 때마다, 두 사람이 속한 '친구 네트워크'를 합친다.
	 * - 관계가 추가될 때마다 두 사람이 속한 네트워크의 "총 사람 수"를 출력해야 한다.
	 *
	 * [핵심 아이디어]
	 * - 사람의 이름이 문자열로 주어지므로, 일반적인 배열 인덱스를 사용할 수 없다.
	 * - 대신 HashMap<String, Node>을 사용하여 이름과 노드를 매핑한다.
	 * - 두 집합을 합칠 때(Union), 루트 노드가 되는 유저 객체에 해당 집합의 크기(friendCnt)를 누적하여 관리한다.
	 *
	 * [구현 메모]
	 * - Union 연산 시, 집합의 크기가 더 큰 쪽으로 작은 쪽을 붙이는  경로 압축을 적용하여 트리 깊이를 최소화한다.
	 * - 새로운 이름이 등장할 때만 객체를 생성하여 Map에 등록한다.
	 * - 기존에 이미 등장했던 이름이 다시 등장한 경우 Map에서 User 객체를 찾는다.
	 *
	 * [시간 복잡도]
	 * - 해시맵 접근 O(1), 유니온 파인드 연산 O(α(N)) (거의 상수 시간)
	 * - 전체 복잡도 : O(F)
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int f;
	// 이름(String)을 Key로, 해당 유저의 노드 정보(User)를 Value로 관리
	static HashMap<String, User> map;
	
	// 유니온 파인드 노드 클래스
	static class User {
		String name;
		User head;      // 대표 노드를 가리키는 포인터
		int friendCnt;  // 이 노드가 루트일 경우, 집합에 속한 친구의 수
		
		public User(String name) {
			this.name = name;
			this.friendCnt = 1; // 초기 생성 시 자기 자신 1명
		}
	}
	
	// Find : 경로 압축(Path Compression) 적용
	static User find(User u) {
		if(u.head == u) return u;
		// 재귀적으로 부모를 찾아 head를 갱신 및 경로 압축
		return u.head = find(u.head);
	}
	
	// Union : 두 집합을 합치고 합쳐진 집합의 크기를 반환
	static int union(User u1, User u2) {
		User h1 = find(u1);
		User h2 = find(u2);
		
		// 이미 같은 네트워크라면 현재 크기 반환
		if(h1 == h2) return h1.friendCnt;
		
		// 최적화: 크기가 더 큰 집합 쪽으로 작은 집합을 붙임
		if(h1.friendCnt < h2.friendCnt) {
			h1.head = h2; // h1을 h2 밑으로
			h2.friendCnt += h1.friendCnt; // h2의 사이즈 갱신
			return h2.friendCnt;
		} else {
			h2.head = h1; // h2를 h1 밑으로
			h1.friendCnt += h2.friendCnt; // h1의 사이즈 갱신
			return h1.friendCnt;
		}
	}
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		
		while(tc-- > 0) {
			map = new HashMap<>();
			f = Integer.parseInt(br.readLine()); // 친구 관계 수
			
			for(int i=0; i<f; i++) {
				st = new StringTokenizer(br.readLine());
				
				// 첫 번째 사람 처리
				String name1 = st.nextToken();
				User u1;
				// 이미 등장한 유저의 객체는 map에서 찾음
				// 새로 등장한 유저의 객체는 신규 생성
				if(map.containsKey(name1)) {
					u1 = map.get(name1);
				} else {
					u1 = new User(name1);
					u1.head = u1; // 초기화: 자기 자신이 대표
					map.put(name1, u1);
				}
				
				// 두 번째 사람 처리
				String name2 = st.nextToken();
				User u2;
				// 이미 등장한 유저의 객체는 map에서 찾음
				// 새로 등장한 유저의 객체는 신규 생성
				if(map.containsKey(name2)) {
					u2 = map.get(name2);
				} else {
					u2 = new User(name2);
					u2.head = u2; // 초기화: 자기 자신이 대표
					map.put(name2, u2);
				}
				
				// 두 사람 연결 및 현재 네트워크 크기 기록
				sb.append(union(u1, u2)).append("\n");
			}
		}
		System.out.println(sb);
	}
}