import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main_1270 {
	
	// BOJ_1270 전쟁(Silver_3)
	// 사용 자료구조 및 알고리즘 : 해시맵, 정렬
	// 각 idx의 개수를 카운트한 후 가장 카운트가 높은 key의 카운트가 전체의 절반을 초과하는지 확인
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 병사 수
	static int n;
	// 각 군대별 병사 수 카운트 해시맵. 병사의 idx는 int의 범위를 초과할 수 있음
	static HashMap<Long, Integer> soldiers = new HashMap<>();
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		
		// 입력된 병사 번호가 맵에 있다면 1 추가, 없다면 카운트 0인 상태로 생성 후 1 추가
		for(int i=0; i<n; i++) {
			long id = Long.parseLong(st.nextToken());
			soldiers.put(id, soldiers.getOrDefault(id, 0) + 1);
		}
	}
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc-- > 0) {
			init();
			
			// 해시맵의 키셋을 카운트 크기 순으로 정렬
			List<Long> keys = new ArrayList<>(soldiers.keySet());
			keys.sort(Comparator.comparingInt(a -> soldiers.get(a)));
			
			// 가장 카운트가 높은 키의 값을 기록하고
			// 해당 키에 해당하는 카운트가 절반을 "초과"하는지 확인
			long maxId = keys.get(keys.size() - 1);
			if(soldiers.get(maxId) > n/2) sb.append(maxId + "\n");
			else sb.append("SYJKGW\n");
			
			// 매 테스트 케이스 수행 후 해시맵 초기화
			soldiers.clear();
		}
		
		System.out.println(sb);
	}

}
