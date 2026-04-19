package feature04_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;


// 나는야 포켓몬 마스터 이다솜 실버4
public class Main_1620 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        HashMap<String, Integer> map = new HashMap<>();
        String[] speakName = new String[N+1]; // 최대 앞에 알려준 이름 갯수

        for (int i = 0; i < N; i++) {
            String name = br.readLine();
            map.put(name, i + 1);
            speakName[i+1] = name; // 1번째부터 시작

        }

        for (int i = 0; i < M; i++) {
            String query = br.readLine();
            if (Character.isDigit(query.charAt(0))) { // 숫자인지 판별
                sb.append(speakName[Integer.parseInt(query)]).append('\n');
            }

            else {
                sb.append(map.get(query)).append('\n');
            }
        }

        System.out.println(sb);
    }
}

// [풀이전략]
// 일단 비효율적인 구현 후에
// 돌아가게 만든 후 의구심이 드는 부분 최적화
// 디버깅 출력문으로 중간중간 디버깅하는 게 중요함 AI로 디버깅문 만들고, 이후 AI쓰지말기.
// 숫자에서 문자 불러올 떄는 배열 이용
// 이름에서 키 불러올 때는 map이용


// [구현 순서]
// map으로 입력하고
// 숫자일때 이름
// 이름일 때 숫자 꺼내기.

// [실수]
// speakName[i+1] = map.get(i+1); get("이름") 키 반환
// speakName[N+1] = name; // 1번째부터 시작 speakName[i+1] = name; // 1번째부터 시작

// [코드 최적화]
// HashMap은 크기를 직접 관리할 필요 없음 알아서 해줌
//map.containsKey("Pikachu")   // O(1) — 해시로 바로 찾음
//map.containsValue(25)         // O(n) — 전체를 순회해서 찾음
//containsKey는 해시 기반이라 빠른데, containsValue는 모든 엔트리를 하나씩 돌면서 비교해. HashMap은 key 기준으로 해싱하니까 value로는 바로 접근이 안 돼.