package bk.silver; // 5

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 클래스 생성 및 정렬 문제
 * - Comparable, compareTo() 에 대한 이해 필요
 * - 클래스 생성 시 각각 객체 할당 해야함
 * - 조건문 조건 확인 필수
 * - 일단 전부를 스캔하는 습관을 가지기
 */
public class _Main_2535_아시아정보올림피아드 {
    static class Student implements Comparable<Student> {
        int country;
        int num;
        int score;

        Student(int country, int num, int score){
            this.country = country;
            this.num = num;
            this.score = score;
        }

        @Override
        public int compareTo(Student o) {
            // 점수 내림차 정렬 (남의 것이 먼저옴)
            return Integer.compare(o.score, this.score);
        }
    }

    static int N;
    static Student[] arr;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        arr = new Student[N];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            int con = Integer.parseInt(st.nextToken());
            int num = Integer.parseInt(st.nextToken());
            int sco = Integer.parseInt(st.nextToken());

            // 이 실수를 두번다시 하다니
            // 오답노트 : 객체를 배열등으로 이용할 떄 각각 객체를 생성해주어야한다.
            arr[i] = new Student(con, num, sco);
        }// 입력 끝

        // 오답노트 : 정렬을 수행해줘야함
        Arrays.sort(arr);

        // 오답노트 : 국가 3명 제한 로직 무식하게 풀어야했음
        int count = 0;
        int[] countryCount = new int[1001];
        StringBuilder sb = new StringBuilder();

        // 오답노트 : 범위를 3으로 해놓은 상태로 방치했음
        // 1,2,3등 전부 같은 나라일 수 있음
        for(int i = 0; i < N; i++){
            if(countryCount[arr[i].country] < 2){
                sb.append(arr[i].country).append(" ").append(arr[i].num).append("\n");
                countryCount[arr[i].country]++;
                count++;
            }

            if(count == 3) break;
        }

        System.out.println(sb.toString());
    }
}
