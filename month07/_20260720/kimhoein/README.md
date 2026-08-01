문제 링크/제목: 프로그래머스 기능 개발

내가 이해한 문제:

- 가장 앞에 있는게 나갈 수 있다면 뒤에 계속 진행?
- 한번 배포할때 배포할 작업의 개수는?
- 가장 앞의 일의 배포 되지 않으면 뒤에 작업은 완료 되어도 배포를 할 수 없다. 가장 앞의 일이 배포될때 그뒤의 작업이 완료 된 상태라면 같이 배포가 가능 하다.

상태:

- 현재 날짜
- 현재 progress 에서 progress의 값? = progresses + 날짜 * speeds >= 100 이라면 배포 가능
- 작업 속도 그리고 진도율은 자연수이므로 아무리 많이 돌아도 99일 이전엔 모든 작업이 완료됨
- 현재 진행 중인 작업 배열의 index
- 배포 할 카운트의 개수를 가져와서 한 날짜가 끝날때마다 더해줌

정리

- 현재 날짜 date
- 현재 인덱스 index
- return 해줄 배포 배열?

전이:

- 현재 날짜 ++
- 인덱스 배포 가능하면 인덱스 이동 ++

제약:

- progresses + 날짜 * speeds >= 100 이라면 인덱스 이동 +1, return 배열에 배포카운트 추가
- 안 되는 경우를 거르는 if문

예:

- progresses 배열 범위를 벗어나면 안됨

목표:

- 언제 배포를 하는지?

예:

- 100 넘으면 카운트 올라감

내 풀이 방향:

- 단순 시뮬?
- 날짜 올라가면 체크하는게 다라서
- 배열 및 return 배열을 위한 que

막힌 부분:

- reutrn 할때 배열로 줘야하는데 배열의 크기를 어떻게 해줘야할지가 고민이었는데 que를 배열로 바꿔주고 있었다.

코드:

```java
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
```
