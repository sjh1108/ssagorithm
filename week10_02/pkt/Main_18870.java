package practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main_18870 {
	public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // arr[] 배열에 값을 세팅함.
        for (int i = 0; i < N; i++) {
        	arr[i] = Integer.parseInt(st.nextToken());
        }
        
        // 나중에 해시맵배열을 사용하기 위해 arr[]을 정렬한 배열인 sorted를 생성 -> arr을 복사
        int[] sorted = arr.clone(); // 복사해서
        Arrays.sort(sorted);//정렬
        
        // 해시멥을 생성해서 키-> 정렬된 값 , 키 값은 0부터해서 for문으로 인덱스 처리하면 됨.  
        Map<Integer, Integer> rank = new HashMap<>();
        int idx = 0;
        for (int x : sorted) {
			if(!rank.containsKey(x)){// 만약 키가 없으면 키값을 해시맵에 추가해줄 것. - 키가 있으면 중복해서 넣지 않기 위함
				rank.put(x, idx++);// 키랑 값을 넣기. 참고로 값은 넣고나서 +1 해줌.
			}
			// -20, -10, 1, 2, 2 -> (-20, 0), (-10, 1), (1, 2) ...(키, 값)
		}
        
        //  arr배열의 순서 정보를 받아서  rank의 키에 대칭되는 값을 출력
        StringBuilder sb = new StringBuilder();
        for (int x : arr) {
			sb.append(rank.get(x) + " ");
		}
        System.out.println(sb);
    }
}

// 기존 단순 코드: 시간초과 나서 해시맵 사용
// 회고: 그 전에는 코드를 암기해서 숙달해야 했는데, 이제 한 계단이 넘은 것 같다. 더이상 암기하지 않아도, 코드가 이해되고, 그냥 해설코드보고, 이해되면 바로 적을 수 있다.
// 그리고, 더이상 매서드를 암기하지 않아도 된다는 것을 깨달았다. 그냥 이해만하고, 객체.하면 영어로 설명도 나오니까.. 하하하.. 
// 스트링빌더 안하니까, 시간초과 실화냐 하.. 