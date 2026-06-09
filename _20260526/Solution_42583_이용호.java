import java.util.Queue;
import java.util.ArrayDeque;
class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        /* 
        스택 큐 문제, 시뮬 방식
        다리 제한 체크 후 트럭 보내기
        보낼수 있으면 트럭 무게 큐에 넣고
        못 보내면 0을 큐에 넣기
        */
        Queue<Integer> bridge = new ArrayDeque<>();
        
        int time = 0;
        int bridgeW = 0;
        int truckIdx = 0;
        
        // 처음 다리 상태 0 으로 초기화
        for(int i = 0; i < bridge_length; i++){
            bridge.offer(0);
        }
        
        // 1time 1poll
        while(truckIdx < truck_weights.length){
            time++;
            
            bridgeW -= bridge.poll();
            
            // 다음 트럭 지나갈수 있는지 확인
            if(bridgeW + truck_weights[truckIdx] <= weight){
                bridge.offer(truck_weights[truckIdx]);
                bridgeW += truck_weights[truckIdx];
                truckIdx++;
            }
            // 못올라가면 0 offer
            else{
                bridge.offer(0);
            }
        }
        // 마지막 트럭 빠지는 시간 추가
        return time + bridge_length;
    }
}