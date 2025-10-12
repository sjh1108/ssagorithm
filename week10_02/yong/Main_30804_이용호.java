package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main_30804_이용호 {
	/*
	 * 조건에 만족하는 연속된 구간의 최대길이를 구해야함
	 * 투 포인터 / 슬라이딩 윈도우
	 */
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] fruits = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            fruits[i] = Integer.parseInt(st.nextToken());
        }

        Map<Integer, Integer> countMap = new HashMap<>();
        int left = 0, maxLen = 0;

        for (int right = 0; right < N; right++) {
            // 오른쪽 과일 추가
            countMap.put(fruits[right], countMap.getOrDefault(fruits[right], 0) + 1);// 현재 과일 있으면 불러오고 없으면 0+1

            // 과일 종류가 3개 이상이면 왼쪽 포인터 이동
            while (countMap.size() > 2) {// key개수 3개 이상이면
            	//왼쪽 끝 과일 개수 줄이기, 0되면 과일 종류 삭제, left이동
                countMap.put(fruits[left], countMap.get(fruits[left]) - 1);
                if (countMap.get(fruits[left]) == 0)
                    countMap.remove(fruits[left]);
                left++;
            }

            // 최대 길이 갱신
            maxLen = Math.max(maxLen, right - left + 1);
        }

        System.out.println(maxLen);
    }
}