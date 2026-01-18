import java.util.*;

class Solution {
    public int solution(int coin, int[] cards) {
        int answer = 0;
        
        int i = 0;
        int n = cards.length;
        HashSet<Integer> hand = new HashSet<>();
        HashSet<Integer> temp = new HashSet<>();
        
        while(i < n / 3) 
            hand.add(cards[i++]);   
        
        while(true) {
            answer++;
            
            if(i < n) {
                temp.add(cards[i++]);
                temp.add(cards[i++]);
            }
            else break;
            
            boolean flag = false;
            
            // 1. 손에 남은 카드로만 해결 (0 코인)
            int handCard1 = -1;
            for(int h : hand) {
                if(hand.contains(n + 1 - h)) {
                    handCard1 = h;
                    break;
                }
            }
            if(handCard1 != -1) {
                hand.remove(handCard1);
                hand.remove(n + 1 - handCard1);
                flag = true;
                continue; // 다음 라운드로
            }
            
            // 2. 손 카드 1 + 후보 카드 1 (1 코인)
            int handCard = -1, tempCard1 = -1;
            if(coin >= 1) {
                for(int t : temp) {
                    if(hand.contains(n + 1 - t)) {
                        handCard = n + 1 - t;
                        tempCard1 = t;
                        flag = true;
                        break;
                    }
                }
            }
            if(flag) {
                coin--;
                hand.remove(handCard);
                temp.remove(tempCard1);
                continue; // 다음 라운드로
            }
            
            // 3. 후보 카드 2개로 해결 (2 코인)
            int tempCardA = -1, tempCardB = -1;
            if(coin >= 2) {
                for(int t : temp) {
                    if(temp.contains(n + 1 - t)) {
                        tempCardA = t;
                        tempCardB = n + 1 - t;
                        flag = true;
                        break;
                    }
                }
            }
            if(flag) {
                coin -= 2;
                temp.remove(tempCardA);
                temp.remove(tempCardB);
                continue; // 다음 라운드로
            }
            
            // 1, 2, 3순위 모두 실패 시 게임 종료
            if(!flag)   break;
        }
        
        return answer;
    }
}