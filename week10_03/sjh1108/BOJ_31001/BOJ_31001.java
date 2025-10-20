package week10_03.sjh1108.BOJ_31001;

import java.io.*; // 입출력을 위한 클래스 임포트
import java.util.*; // 유틸리티 클래스(HashMap, List, StringTokenizer 등) 임포트

class Main {
    private static long M; // 보유 현금 (Money)

    private static StringBuilder sb; // 출력을 효율적으로 처리하기 위한 StringBuilder

    // 종목별 보유 수량을 저장하는 맵 (Key: 종목 이름, Value: 보유 수량)
    private static HashMap<String, Integer> havingMap;
    // 그룹별 종목 리스트를 저장하는 맵 (Key: 그룹 번호, Value: 해당 그룹의 종목 이름 리스트)
    private static HashMap<Integer, List<String>> groupMap;
    // 종목별 현재 가격을 저장하는 맵 (Key: 종목 이름, Value: 가격)
    private static HashMap<String, Long> priceMap;

    /**
     * 1번 연산: 종목 A를 B개 매수합니다.
     * @param A 종목 이름
     * @param B 매수할 수량
     */
    private static void buy(String A, int B){
        long price = priceMap.get(A) * B; // 총 구매 비용 계산

        // 가진 돈(M)이 구매 비용(price)보다 적으면 구매할 수 없으므로 함수 종료
        if(M < price) return;

        M -= price; // 현금에서 구매 비용 차감
        // 보유 수량을 (원래 있던 수량 + 새로 산 수량)으로 갱신
        havingMap.put(A, havingMap.get(A) + B);
    }

    /**
     * 2번 연산: 종목 A를 B개 매도합니다.
     * @param A 종목 이름
     * @param B 매도할 수량
     */
    private static void sell(String A, int B){
        int having = havingMap.get(A); // 현재 보유 수량 확인

        // 보유하고 있지 않으면 매도할 수 없으므로 함수 종료
        if (having == 0) return;

        int sell; // 실제 매도할 수량

        if (B >= having) {
            // 팔려는 수량(B)이 보유 수량(having)보다 많거나 같으면, 보유한 만큼(전부) 매도
            sell = having;
        } else {
            // 팔려는 수량이 보유 수량보다 적으면, 요청한 수량(B)만큼 매도
            sell = B;
        }

        // 보유 수량을 (원래 수량 - 판 수량)으로 갱신
        havingMap.put(A, having - sell);
        // 현금에 매도 금액(현재가 * 실제 판 수량)을 더함
        M += priceMap.get(A) * sell;
    }

    /**
     * 3번 연산: 종목 A의 가격을 B만큼 변동시킵니다.
     * @param A 종목 이름
     * @param B 변동시킬 가격 (양수면 상승, 음수면 하락)
     */
    private static void price(String A, long B){
        // 현재 가격에 B를 더해 가격 정보 갱신
        priceMap.put(A, priceMap.get(A) + B);
    }

    /**
     * 4번 연산: D번 그룹의 모든 종목 가격을 C만큼 변동시킵니다.
     * @param D 그룹 번호
     * @param C 변동시킬 가격
     */
    private static void groupPrice(int D, int C){
        // groupMap에서 D번 그룹에 속한 모든 종목(h)을 순회
        for(String h: groupMap.get(D)){
            // 각 종목에 대해 3번 연산(price)을 수행
            price(h, C);
        }
    }

    /**
     * 5번 연산: D번 그룹의 모든 종목 가격을 E%만큼 변동시킵니다. (10원 단위 미만 절삭)
     * @param D 그룹 번호
     * @param E 변동시킬 퍼센트
     */
    private static void groupPricePercent(int D, int E){
        // groupMap에서 D번 그룹에 속한 모든 종목(h)을 순회
        for(String h: groupMap.get(D)){
            long price = priceMap.get(h); // 해당 종목의 현재 가격

            // E% 변동된 가격 계산
            // (100.0 + E) / 100.0 을 곱하여 퍼센트 변동을 적용 (소수점 계산을 위해 100.0 사용)
            long C = (long)(price * (100.0 + E) / 100.0);
            
            // 10원 단위 미만 절삭 (10으로 나눈 나머지를 뺀다)
            C -= C%10;

            // 갱신된 가격을 맵에 저장
            priceMap.put(h, C);
        }
    }

    /**
     * 6번 연산: 현재 보유 현금을 출력합니다.
     */
    private static void printMoney(){
        // StringBuilder에 현금(M)과 줄바꿈 문자 추가
        sb.append(M).append('\n');
    }

    /**
     * 7번 연산: 모든 종목을 현재가에 매도했을 때의 총 자산(현금 + 주식 가치)을 출력합니다.
     */
    private static void ifSellAll(){
        long m = M; // 총 자산을 계산할 변수 (초기값 = 현재 현금)

        // 보유한 모든 종목(havingMap의 keySet)을 순회
        for(String h: havingMap.keySet()){
            int cnt = havingMap.get(h); // 보유 수량
            long price = priceMap.get(h); // 현재 가격

            // 총 자산에 (보유 수량 * 현재 가격)을 더함
            m += cnt * price;
        }

        // 계산된 총 자산을 StringBuilder에 추가
        sb.append(m).append('\n');
    }

    public static void main(String[] args) throws IOException {
        // 빠른 입력을 위한 BufferedReader 설정
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력을 공백 단위로 끊어 읽기 위한 StringTokenizer
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 종목의 수
        M = Integer.parseInt(st.nextToken()); // 초기 현금
        int Q = Integer.parseInt(st.nextToken()); // 쿼리(명령)의 수

        // 맵(HashMap)들 초기화
        havingMap = new HashMap<>();
        groupMap = new HashMap<>();
        priceMap = new HashMap<>();

        // N개의 종목 정보 입력받기
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int g = Integer.parseInt(st.nextToken()); // 그룹 번호
            String h = st.nextToken(); // 종목 이름
            long p = Long.parseLong(st.nextToken()); // 초기 가격

            // groupMap에 종목 추가
            // getOrDefault: g번 그룹이 이미 있으면 해당 리스트를 가져오고, 없으면 새 ArrayList를 생성
            List<String> group = groupMap.getOrDefault(g, new ArrayList<String>());
            group.add(h); // 해당 그룹 리스트에 종목 추가
            // putIfAbsent: g번 키가 맵에 없었을 경우에만 (즉, 새 그룹일 경우에만) 맵에 (g, group)을 추가
            groupMap.putIfAbsent(g, group); 

            // priceMap에 종목의 초기 가격 정보 추가
            priceMap.put(h, p);
            // havingMap에 종목의 초기 보유 수량(0) 추가
            havingMap.put(h, 0);
        }

        sb = new StringBuilder(); // 출력용 StringBuilder 초기화

        // Q개의 쿼리(명령)를 하나씩 처리
        for(int q = 0; q < Q; q++){
            st = new StringTokenizer(br.readLine()); // 다음 줄 읽기

            int cmd = Integer.parseInt(st.nextToken()); // 명령 번호

            // switch문에 사용할 변수들 미리 선언
            String A; // 종목 이름
            int B, C, D, E; // 연산에 필요한 정수값들

            switch(cmd){ // 명령 번호(cmd)에 따라 적절한 함수 호출
                case 1: // 매수
                    A = st.nextToken();
                    B = Integer.parseInt(st.nextToken());
                    buy(A, B);
                    break;
                
                case 2: // 매도
                    A = st.nextToken();
                    B = Integer.parseInt(st.nextToken());
                    sell(A, B);
                    break;
                
                case 3: // 개별 종목 가격 변동
                    A = st.nextToken();
                    C = Integer.parseInt(st.nextToken());
                    price(A, C);
                    break;

                case 4: // 그룹 가격 변동
                    D = Integer.parseInt(st.nextToken());
                    C = Integer.parseInt(st.nextToken());
                    groupPrice(D, C);
                    break;

                case 5: // 그룹 가격 퍼센트 변동
                    D = Integer.parseInt(st.nextToken());
                    E = Integer.parseInt(st.nextToken());
                    groupPricePercent(D, E);
                    break;

                case 6: // 현금 출력
                    printMoney();
                    break;
                case 7: // 총 자산 출력
                    ifSellAll();
                    break;
            }
        }

        // 쿼리 처리가 모두 끝난 후, StringBuilder에 모아둔 내용을 한 번에 출력
        System.out.println(sb);
    }
}