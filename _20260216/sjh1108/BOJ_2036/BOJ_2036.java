package _20260216.sjh1108.BOJ_2036;

import java.util.*;
import java.io.*;
import java.math.BigInteger;

// 백준 2036 - 수열의 점수 (Greedy, Sorting)
class Main{
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int N; // 수열의 크기

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        // 결과값이 long 범위를 넘을 수 있는지 확인 필요하지만, BigInteger를 사용하여 안전하게 처리
        BigInteger answer = new BigInteger("0");
        
        // 양수(1 초과)와 음수(0 포함)를 분리하여 저장
        // 1은 곱하는 것보다 더하는 것이 무조건 이득이므로 따로 카운트
        ArrayList<Long> pList = new ArrayList<Long>();
        ArrayList<Long> mList = new ArrayList<Long>();

        int oCnt = 0; // 1의 개수
        for(int i = 0; i < N; i++)
        {
            long n = Long.parseLong(br.readLine());
            if(n > 1)
                pList.add(n);
            else if(n < 1) // 0과 음수
                mList.add(n);
            else // n == 1
                oCnt++;
        }
        
        // 양수는 큰 수끼리 곱해야 최대가 됨 -> 내림차순 정렬
        Collections.sort(pList, Collections.reverseOrder());
        // 음수는 작은 수(절대값이 큰 수)끼리 곱해야 큰 양수가 됨 -> 오름차순 정렬
        Collections.sort(mList);
        
        // 1. 양수 처리 (1보다 큰 수)
        // 두 개씩 묶어서 곱해줌
        if(pList.size() % 2 == 0) {
            for(int i = 0; i < pList.size(); i+=2)
                answer = answer.add(BigInteger.valueOf(pList.get(i) * pList.get(i + 1)));
        }
        else {
            for(int i = 0; i < pList.size() - 1; i+=2)
                answer = answer.add(BigInteger.valueOf(pList.get(i) * pList.get(i + 1)));
            // 홀수 개라면 마지막 남은 하나는 그냥 더함
            answer = answer.add(BigInteger.valueOf(pList.get(pList.size()-1)));
        }
        
        // 2. 음수 및 0 처리
        // 두 개씩 묶어서 곱해줌 (음수*음수 = 양수, 음수*0 = 0)
        // 0은 가장 큰 음수(절대값이 작은 음수)와 묶여서 음수를 없애는 역할을 함
        if(mList.size() % 2 == 0)
            for(int i = 0; i < mList.size(); i+=2)
                answer = answer.add(BigInteger.valueOf(mList.get(i) * mList.get(i + 1)));
        
        else {
            for(int i = 0; i < mList.size()-1; i+=2)
                answer = answer.add(BigInteger.valueOf(mList.get(i) * mList.get(i + 1)));
            // 홀수 개라면 마지막 남은 하나(가장 큰 음수 혹은 0)는 그냥 더함
            answer = answer.add(BigInteger.valueOf(mList.get(mList.size() - 1)));
        }
        
        // 3. 1 처리
        // 1은 곱하지 않고 더하는 것이 이득이므로 개수만큼 더함
        answer = answer.add(BigInteger.valueOf(oCnt));
        
        System.out.println(answer);
    }
}