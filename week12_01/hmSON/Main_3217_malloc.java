import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_3217_malloc {
	
	/*
	 * BOJ_3217 : malloc(Gold_1)
	 * 자료구조 및 알고리즘 : 이중 연결 리스트, 해시맵
	 *
	 * [문제 요약]
	 * - 크기 100,000의 메모리 공간에서 변수 할당(malloc), 해제(free), 출력(print) 명령을 수행한다.
	 * - malloc(size): 앞에서부터 탐색하여 크기가 size 이상인 첫 번째 빈 공간에 할당한다.
	 * 남는 공간이 있다면 해당 공간은 빈 공간으로 유지(분할)된다. 할당할 공간이 없다면 해당 변수에는 0이 입력되고 참조값을 잃는다.
	 * - free(name): 할당된 메모리를 해제한다. 이때 좌우에 인접한 빈 공간이 있다면 하나로 병합(Merge)해야 한다.
	 * - print(name): 변수명 name에 해당하는 메모리 구간을 찾아 주소값을 반환한다. 0이 입력되어 있거나 없는 변수일 경우 0을 반환한다.
	 *
	 * [핵심 아이디어]
	 * - 메모리 구간 정보를 담은 노드를 이중 연결 리스트로 관리한다.
	 * - 할당 시: 리스트를 순회하며 적절한 빈 노드를 찾아 쪼갠다.
	 * - 해제 시: 해당 노드를 Free로 바꾸고, prev나 next가 Free라면 노드를 합친다.
	 * - HashMap을 사용하여 변수명으로 해당 메모리 노드에 O(1) 접근이 가능하게 한다.
	 *
	 * [구현 메모]
	 * - maxLen: 현재 힙에 존재하는 가장 큰 빈 공간의 크기를 추적하여, 
	 * 할당 불가능한 요청이 들어오면 리스트 순회 없이 즉시 무시하는 최적화를 적용했다.
	 * - Dummy Node: head와 tail에 더미를 두어 null 체크 등의 경계 처리를 제거했다.
	 *
	 * [시간 복잡도]
	 * - 할당: O(N) (N은 메모리 단편화 조각의 개수)
	 * - 해제: O(1) (HashMap 조회 후 인접 노드 병합)
	 */
	
	static Memory head, tail; // 더미 헤드, 테일
	static int maxLen = 100_000; // 최적화를 위한 최대 빈 공간 크기 캐싱
	static HashMap<String, Memory> varMap = new HashMap<>(); // 변수명 -> 메모리 노드 매핑
	
	// 메모리 구간 정보를 담는 노드 클래스
	static class Memory {
		int start, len;
		char state; // d: dummy, a: allocated, f: free
		Memory prev, next; // 이중 연결 리스트 포인터
		
		public Memory(int start, int len, char state) {
			this.start = start;
			this.len = len;
			this.state = state;
		}
	}
	
	// 메모리 할당 메서드
	static void memoryAlloc(String name, int size) {
		Memory m = head.next;
		Memory target = null;
		int curMax = 0; // 탐색하면서 갱신될 실제 최대 빈 공간 크기
		
		// 리스트를 순회하며 할당 가능한 첫 번째 빈 공간 탐색
		while(m != tail) {
			if(m.state == 'f') {
				if(m.len >= size && target == null) {
					target = m; // 할당할 공간 발견
					// 이 공간을 쓰고 남은 크기와 현재까지의 최대 크기 비교
					curMax = Math.max(curMax, target.len - size);
				} else {
					// 선택되지 않은 다른 빈 공간들의 크기로 maxLen 갱신 준비
					curMax = Math.max(curMax, m.len);
				}
			}
			m = m.next;
		}
		
		maxLen = curMax; // 최대 빈 공간 크기 업데이트
		
		// 딱 맞는 공간이면 상태만 변경 (분할 불필요)
		if(size == target.len) {
			target.state = 'a';
			varMap.put(name, target);
			return;
		}
		
		// 공간이 남는 경우: 노드 분할
		// target: 기존 빈 공간 노드 (사이즈 줄여서 뒤쪽 빈 공간으로 재사용)
		// allocated: 새로 할당될 노드 (target 앞에 삽입)
		Memory allocated = new Memory(target.start, size, 'a');
		target.start += size; // 기존 빈 공간의 시작 주소 밀기
		target.len -= size;   // 기존 빈 공간의 크기 줄이기
		
		// 연결 리스트에 allocated 노드 삽입 (target 앞에 위치)
		allocated.prev = target.prev;
		allocated.next = target;
		target.prev.next = allocated;
		target.prev = allocated;
		
		varMap.put(name, allocated);
	}
	
	// 메모리 해제 메서드
	static void memoryFree(String name) {
		if(!varMap.containsKey(name)) return;
		Memory target = varMap.get(name);
		varMap.remove(name);
		target.state = 'f'; // 상태를 Free로 변경
		
		// 왼쪽(이전) 노드가 빈 공간이면 병합
		if(target.prev.state == 'f') {
			target.start -= target.prev.len; // 시작 주소를 이전 노드 것으로 당김
			target.len += target.prev.len;   // 길이를 합침
			
			// 이전 노드 삭제
			target.prev.prev.next = target;
			target.prev = target.prev.prev;
		}
		
		// 오른쪽(다음) 노드가 빈 공간이면 병합
		if(target.next.state == 'f') {
			target.len += target.next.len; // 길이를 합침 (시작 주소는 변함 없음)
			
			// 다음 노드 삭제
			target.next.next.prev = target;
			target.next = target.next.next;
		}
		
		// 해제 후 생성된 빈 공간이 현재 maxLen보다 클 수 있으므로 갱신
		maxLen = Math.max(maxLen, target.len);
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		// 초기화: Head(Dummy) <-> InitMemory(100k Free) <-> Tail(Dummy)
		head = new Memory(-1, 0, 'd');
		tail = new Memory(100_001, 0, 'd');
		Memory initMemory = new Memory(0, 100_000, 'f');
		
		initMemory.prev = head;
		initMemory.next = tail;
		head.next = initMemory;
		tail.prev = initMemory;
		
		while(n-- > 0) {
			String cmd = br.readLine();
			
			// 할당 명령 파싱 (예: aaaa=malloc(100))
			if(cmd.contains("=")) {
				String name = cmd.substring(0, 4); // 변수명은 항상 4글자
				
				// 사이즈 파싱
				int size = 0, idx = 12; // "malloc(" 이후부터 숫자 파싱
				while(true) {
					char ch = cmd.charAt(idx++);
					if(ch < '0' || ch > '9') break;
					size *= 10;
					size += (ch - '0');
				}
				
				// 최적화: 현재 최대 빈 공간보다 요청 사이즈가 크면 즉시 무시
				if(maxLen < size) {
					varMap.remove(name); // 기존에 있던 변수라면 0으로 초기화(삭제)됨
					continue;
				}
				memoryAlloc(name, size);
				
			} else {
				// free 또는 print 명령
				StringTokenizer st = new StringTokenizer(cmd, "()");
				String type = st.nextToken();
				
				if(type.equals("free")) {
					memoryFree(st.nextToken());
				} else { // print
					Memory target = varMap.get(st.nextToken());
					// 할당되지 않은 경우 0, 아니면 시작 주소 +1 출력
					sb.append(target == null ? 0 : target.start + 1).append("\n");
				}
			}
		}
		
		System.out.println(sb);
	}

}