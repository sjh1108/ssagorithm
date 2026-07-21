package study;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int date = 1;
        int index = 0;
        int count =0;
        Queue<Integer> que = new LinkedList<Integer>();

        for(date = 1; index<progresses.length; date++) {
            count=0;
            while(progresses[index]+date*speeds[index] >= 100) {
                index++;
                count++;
                if(index==progresses.length) break; // 내부에서 돌다가 벗어날 수도 있어서 넣어줌
            }
            if(count != 0) que.add(count);
        }

        int[] answer = new int[que.size()];
        for (int i = 0; i < answer.length; i++) {
            answer[i] = que.poll();
        }
        return answer;
    }
}
