package algostudy.baek.b14891;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/*
 * 톱니바퀴 클래스 만들고 리스트로 관리
 * 톱니바퀴클래스에는 톱니 상태, 회전 함수 구현
 * 선택된 바퀴 기준 왼쪽 처리, 오른쪽 처리
 * 총 스코어 계산(비트마스킹으로 될듯)
 */
public class Main_14891_이용호 {

    static class Wheel {
        int[] NS = new int[8];
        Wheel(String st) {
            for (int i = 0; i < 8; i++) NS[i] = st.charAt(i) - '0';
        }
        void spinLeft() { //반시계 회전
            int tmp = NS[0];
            for (int i = 0; i < 7; i++) NS[i] = NS[i+1];
            NS[7] = tmp;
        }
        void spinRight() { //시계 회전
            int tmp = NS[7];
            for (int i = 7; i > 0; i--) NS[i] = NS[i-1];
            NS[0] = tmp;
        }
    }

    static Wheel[] wheels = new Wheel[4];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 4; i++) {
            wheels[i] = new Wheel(br.readLine());
        }

        int K = Integer.parseInt(br.readLine());
        for (int k = 0; k < K; k++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int num = Integer.parseInt(st.nextToken()) - 1; //0base
            int dir = Integer.parseInt(st.nextToken());   

            int[] rotate = new int[4]; // 각 바퀴 회전 방향 (0=안함, 1=시계, -1=반시계)
            rotate[num] = dir;

            //오른쪽 전파
            for (int i = num; i < 3; i++) {
                if (wheels[i].NS[2] != wheels[i+1].NS[6]) { //오른쪽과 다르면 회전
                    rotate[i+1] = -rotate[i];//다음톱니 반대방향으로 회전
                } else break; //같으면 탈출
            }

            //왼쪽 전파
            for (int i = num; i > 0; i--) {
                if (wheels[i].NS[6] != wheels[i-1].NS[2]) {
                    rotate[i-1] = -rotate[i];
                } else break;
            }

            //회전 실행
            for (int i = 0; i < 4; i++) {
                if (rotate[i] == 1) wheels[i].spinRight();
                else if (rotate[i] == -1) wheels[i].spinLeft();
            }
        }

        // 점수 계산
        int score = 0;
        for (int i = 0; i < 4; i++) {
            if (wheels[i].NS[0] == 1) score += (1 << i); // 1 2 4 8 -> 비트마스킹
        }
        System.out.println(score);
    }
}
