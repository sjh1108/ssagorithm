package bk.silver; //실버 5

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 이해
 * - Up&Down 게임
 * - 근데 상태가 상태가 거짓말 하는지를 판별해야 함
 * - 게임은 진행되다가 정답을 맞추면 -> 새게임 시작
 * - 0이 입력으로 들어오면 완전히 종료
 *
 * 입력
 * - 외친 숫자 (1~10)
 * - 숫자가 너무 큰지, 작은지, 맞는지 알려주는 문자열
 * - 위의 내용을 반복하다가 0이 나오면 끝
 *
 * 출력
 * - 거짓말 : Stan is dishonest
 * - 진실됨 : Stan may be honest
 *
 * 전략
 * - 몇번 외치는지 알려준 게 없어서 시간 걱정을 안 해도 되는 건가 싶음
 * - 복잡한 로직 없는 것 같으니 노가다 가보겠습니다
 *
 * - 10개의 bool 배열을 준비해서 거기에 가능한 숫자가 하나도 없는 경우 거짓말
 *
 * 오답 노트
 * - 무조건 정답을 맞추면서 끝나는 패턴임을 확인
 * - for in문 제발 그거 복사되는거니까 값 변경해야할 때 쓰지좀 말자
 * - 꼬이면 답도없다. 진짜 이런 문제에 시간을 너무 잡아먹음
 * - 조건문 범벅으로 해결하지말고 최대한 심플하게 작성하는 방법을 생각하자
 */
public class _Main_4335_숫자맞추기 {

    // 업, 다운이 이해하기 편해 변경함
    static final String down = "too high";
    static final String up = "too low";
    static final String ok = "right on";

    static int num;
    static boolean[] arr;
    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 전체종료 트리거
        boolean isPlaying = true;

        while(isPlaying){
            // 가능한 숫자는 true로 설정
            arr = new boolean[11];

            // 오답노트 : 제발 for in 문은 값 복사라고. 제발 기억하라고.
            for(int i = 1; i <= 10; i++){
                arr[i] = true;
            }

            // 다음 게임 시작 트리거
            boolean needNextGame = false;

            // 질의응답 반복 수행
            while(true){
                // 다음 게임으로 넘어가야하는지 판단 (배열 갱신을 위해)
                if(needNextGame){
                    break;
                }

                num = Integer.parseInt(br.readLine());// 수 입력

                if(num == 0){// 종료 조건
                    isPlaying = false; //전체종료
                    break;
                }

                String ans = br.readLine();

                switch (ans){
                    case down :
                        for(int i = num; i <= 10; i++){
                            arr[i] = false;
                        }
                        break;
                    case up :
                        for(int i = 1; i <= num; i++){
                            arr[i] = false;
                        }
                        break;
                    case ok :
                        boolean honest = true; // 진실 여부

                        // 정답이라고 한 숫자가 이미 불가능한 숫자면 거짓
                        if(!arr[num]) {
                            honest = false;
                        }

                        // 헷갈리지 말자
                        if(honest) {
                            System.out.println("Stan may be honest");
                        }else {
                            System.out.println("Stan is dishonest");
                        }

                        needNextGame = true;
                        break;
                }


            }
        }

    }
}