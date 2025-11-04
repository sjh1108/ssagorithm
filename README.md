# ssagorithm

## 목표
    - 자바 역량 향상
    - SW 역량 테스트 A형 달성
        - 추후 B형 달성
    - 코드 리뷰를 통한 코드 분석 역량 향상
## 계획
    - 목표 : 
        - 7일 4솔
        - 추가 목표
            - git과 친해지기
    - 진행
        - 언어 제한 Java
        - 매 주 본인 역량에 맞는 문제 4개 ( 과제 포함 가능 )를 풀어 깃허브 업로드
            - 다른 사람이 작성한 코드 리뷰
            - 문제 선정 기준
                - 수학 태그 문제 제외
                    - SW 역량 테스트에는 해당 태그는 잘 나오지 않음
        - 추가 목표
            - 매일 알고리즘 1문제 풀이
            - 코드 리뷰를 통한 코드 분석 역량 향상
                - 내가 짠 코드가 아닌 다른 사람이 짠 코드를 읽으며 코드 분석 능력 향상
                - 다른 사람의 코드를 보며 알고리즘과 로직, 자바 문법에 대한 인사이트 넓히기
        - 벌금
            - 문제를 덜 풀었을 경우 문제당 5000원
                - 못 푼 경우가 아닌 안 푼 경우
                - 못 풀었을 경우 푼 흔적으로 ( 작성했던 코드, 제출 기록, 필기 등 ) 대체
        - 건강 이상, 면접/코테 참여 등 불참 이슈 있을 시 사전 연락

## 사용 방법

    1. git clone
        - git repository를 클론해옴
    2. git pull
        - 이전 변경사항들을 pull 해옵니다.
    3. git branch
        - git branch 명령어를 통해서 현재 branch가 무엇인지 확인
    4. git branch [branch명]
        - ex) git branch feature/weekMM_WW_name
        - 이름은 branch에 한글이 들어가는지 몰라서 영어로 했습니다
        - 한글이 되면 한글을 쓰셔도 되며, 한글을 쓰지 않더라도 다른 인원이 식별 할 수 있도록만 해주시면 됩니다
    5. git checkout [branch명]
        - ex) git checkout feature/weekMM_WW_name
        - 문제 풀이를 업로드 할 브랜치로 이동
    6. 문제 풀이
        - 문제를 푼 파일들을 개인 폴더에 모아 각 문제 별로 커밋 해주세요
        - 이때 문제가 어떤 문제를 풀었는지 확인할 수 있도록 파일명을 작성해주세요
    7. git commit
        - vsc, intelliJ 등 사용 시 gui 활용해서 커밋과 푸시를 해도 됩니다
        - eclipse에선 아직 사용해보지 않아 알려드리기 힘드니 유의해주세요
        - 커밋 메세지에 [BOJ], [SWEA] 를 앞에 두면 문제 리스트를 PR에 자동으로 업로드 해줍니다 이점 참고하셔서 커밋 메세지 작성해주세요
        - ex)
        [BOJ] 1000_A+B / 브론즈 5 / 송주헌
    8. git push
        - 만약 다음과 같은 오류 메세지가 발생할 경우
            - 오류 메세지
                
                ```bash
                fatal: The current branch feature/webhook has no upstream branch.
                To push the current branch and set the remote as upstream, use
                 git push --set-upstream origin feature/webhook
                To have this happen automatically for branches without a tracking
                upstream, see 'push.autoSetupRemote' in 'git help config
                ```
                
        - 단순히 remote repository에 현재 사용중인 브랜치가 없다는 오류 메세지이므로 메세지 내에 있는 명령어 사용하시면 됩니다
            
            ```bash
            git push --set-upstream origin [브랜치명]
            ```
            
    9. 이제 github의 레포지토리로 이동하면 Compare & pull request가 뜹니다 이제 PR(Pull Request)을 생성해서 작성하시면 됩니다.
        - PR 생성 시 기본 템플릿을 작성해두었습니다. 해당 템플릿에 맞춰서 작성해주시면 됩니다.
        - PR의 title 형식
            - [Week] 이름 - N 문제 풀이
