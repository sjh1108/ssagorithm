import sys
import os

# 로컬에서는 같은 폴더의 input.txt를 입력으로 사용 (SWEA 제출 시 그대로 둬도 동작)
try:
    sys.stdin = open(os.path.join(os.path.dirname(os.path.abspath(__file__)), "input.txt"), "r", encoding="utf-8-sig")
except (NameError, OSError):
    pass

input = sys.stdin.readline

T = int(input())
for test_case in range(1, T+1):
    N, M = map(int, input().split())
    arr = [[0]*(N+1) for _ in range(N+1)]
    m = N // 2
    arr[m][m] = arr[m+1][m+1] = 2
    arr[m][m+1] = arr[m+1][m] = 1 # 초기 상태 중앙 좌표에 1,2 흑, 백 문제가 원하는대로 돌을 세팅

    for _ in range(M):
        si, sj, d = map(int, input().split())
        arr[si][sj] = d
        for di, dj in ((-1,-1),(-1,0),(-1,1),(1,-1),(1,0),(1,1),(0,-1),(0,0),(0,1)):
            r = []
            for mul in range(1, N):
                ni, nj = si + di * mul, sj + dj * mul
                if 1<=ni<=N and 1<=nj<=N:
                    if arr[ni][nj]==0:
                        break
                    elif arr[ni][nj]==d:
                        while r:
                            ti, tj = r.pop()
                            arr[ti][tj]=d
                        break
                    else:
                        r.append((ni,nj))
                else:
                    break

    # 흑돌과 백돌 세기
    bcnt = wcnt = 0
    for lst in arr:
        bcnt += lst.count(1)
        wcnt += lst.count(2)

    print(f'#{test_case} {bcnt} {wcnt}')