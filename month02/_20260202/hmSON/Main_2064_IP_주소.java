import java.io.*;
import java.util.*;

public class Main_2064_IP_주소 {

    /*
     * BOJ_2064 : IP 주소 (Gold_4)
     * 자료구조 및 알고리즘 : 비트마스킹
     *
     * [문제 요약]
     * - 여러 개의 IP 주소가 주어졌을 때, 이들을 모두 포함하는 가장 작은 네트워크 주소와 서브넷 마스크를 구하라.
     * - IP 주소는 8비트 정수 4개(0~255)로 구성된다.
     *
     * [핵심 아이디어]
     * - 같은 네트워크에 속하려면 Network ID(Prefix)가 동일해야 한다.
     * - 주어진 모든 IP 주소를 비교하여, 최상위 비트(MSB)부터 시작해 값이 달라지는 지점을 찾는다.
     * - 값이 달라지기 시작한 비트부터 그 뒤의 모든 비트는 Host ID 영역이므로 마스크를 0으로 처리한다.
     * - 1. 첫 번째 IP를 기준으로 잡고(address), 마스크는 모두 1(255.255.255.255)로 초기화한다.
     * - 2. 다음 IP들과 비교하며 비트가 달라지는 순간 마스크를 갱신하고(1->0), 네트워크 주소도 마스킹 처리한다.
     *
     * [구현 메모]
     * - address[] : 최종 네트워크 주소를 저장할 배열 (초기값은 첫 번째 IP).
     * - mask[] : 최종 서브넷 마스크를 저장할 배열 (초기값은 255.255.255.255).
     * - flag : 상위 옥텟(Byte)에서 이미 차이가 발생했는지 여부를 체크하는 변수.
     * - 255 - ((1 << (idx+1)) - 1) : 달라진 비트(idx) 이하를 0으로 만드는 마스크 생성 공식.
     *
     * [시간 복잡도]
     * - N개의 IP에 대해 4개의 옥텟, 각 8비트를 확인하므로 O(N * 4 * 8) = O(N).
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 4개의 옥텟(8bit * 4)을 저장할 배열
        // address는 네트워크 주소를 저장할 배열
        int[] address = new int[4];
        // mask는 서브넷 마스크를 저장할 배열
        int[] mask = new int[4];

        // 초기 마스크는 모든 비트가 1인 상태(255.255.255.255)로 설정
        Arrays.fill(mask, 255);

        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), ".");

            // 첫 번째 IP 주소를 기준값으로 설정
            if(i == 0) {
                for(int j=0; j<4; j++) {
                    address[j] = Integer.parseInt(st.nextToken());
                }
                continue;
            }

            // flag: 이전 옥텟에서 이미 차이가 존재했는가?
            boolean flag = false;
            for(int j=0; j<4; j++) {
                // 이미 상위 옥텟에서 차이가 났다면, 하위 옥텟은 모두 Host ID 영역이므로 0 처리
                if(flag) {
                    address[j] = 0;
                    mask[j] = 0;
                    continue;
                }

                int part = Integer.parseInt(st.nextToken());

                // 현재 마스크를 적용했을 때 값이 같다면(공통 부분) 넘어감
                if((part & mask[j]) == (address[j] & mask[j])) continue;

                // 값이 다르다면, 상위 비트부터 비교하여 최초로 다른 비트 위치(idx) 탐색
                int idx = 7;
                while(idx >= 0) {
                    // 비트가 같다면 다음 비트(하위)로 이동
                    if((part & (1<<idx)) == (address[j] & (1<<idx))) {
                        idx--; continue;
                    }

                    // 다른 비트 발견 시
                    flag = true;

                    // idx 비트 포함 그 하위 비트를 모두 0으로 만드는 새 마스크 생성
                    // 예: idx=4 -> 00011111을 빼서 11100000 생성
                    int newMask = 255 - ((1 << (idx+1)) - 1);

                    address[j] &= newMask; // 네트워크 주소 갱신 (Host ID 부분 0으로)
                    mask[j] = newMask;     // 서브넷 마스크 갱신
                    break;
                }
            }
        }

        System.out.println(address[0] + "." + address[1] + "." + address[2] + "." + address[3]);
        System.out.println(mask[0] + "." + mask[1] + "." + mask[2] + "." + mask[3]);
    }

}