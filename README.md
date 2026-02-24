# ssagorithm

알고리즘 문제 풀이 스터디 저장소입니다.

## 목표
- Java 기반 문제 해결 역량 향상
- SW 역량테스트 A형 달성
  - 추후 B형 달성
- 코드 리뷰를 통한 코드 분석 역량 향상

## 운영 계획
### 기본 목표
- 주 4문제 풀이
- Git 사용에 익숙해지기

### 진행 방식
- 사용 언어: Java
- 매주 14개 문제 중에 1개 이상의 문제 풀이 제출
- 풀이 코드를 GitHub에 업로드하고 상호 코드 리뷰 진행

### 추가 목표
- 매일 알고리즘 1문제 풀이
- 코드 리뷰를 통한 코드 분석 능력 강화
  - 내가 작성하지 않은 코드를 읽고 분석하는 연습
  - 알고리즘/로직/Java 문법 관점에서 인사이트 확장

### 불참 안내
- 건강 이상, 면접/코테 참여 등 불참 이슈가 있으면 사전 연락

## Git 사용 방법
1. 저장소 클론
```bash
git clone <repository-url>
```

2. 최신 변경사항 반영
```bash
git pull
```

3. 현재 브랜치 확인
```bash
git branch
```

4. 작업 브랜치 생성
```bash
git branch feature/weekMM_WW_name
```
- 브랜치명은 팀원이 식별 가능하게 작성해 주세요.

5. 작업 브랜치로 이동
```bash
git checkout feature/weekMM_WW_name
```

6. 문제 풀이 반영
- 풀이 파일은 개인 폴더에 모아 관리
- 문제 단위로 커밋
- 파일명만 보고도 어떤 문제인지 알 수 있게 작성

7. 커밋
- VS Code/IntelliJ 등의 GUI 커밋 사용 가능
- 커밋 메시지 앞에 `[BOJ]`, `[SWEA]`를 붙이면 PR에 문제 리스트가 자동 업로드됨

예시:
```text
[BOJ] 1000_A+B / 브론즈 5 / 송주헌
```

8. 푸시
- 아래와 같은 upstream 오류가 나면:
```bash
fatal: The current branch feature/webhook has no upstream branch.
```
- 다음 명령어로 해결:
```bash
git push --set-upstream origin <브랜치명>
```

9. Pull Request 생성
- GitHub 저장소에서 `Compare & pull request`로 PR 생성
- PR 템플릿에 맞춰 작성
- PR 제목 형식:
```text
[Week] 이름 - N문제 풀이
```
