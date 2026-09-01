import sys
from collections import deque # O(1) 양쪽 끝 삽입

input = sys.stdin.readline # readline으로 더 빠르게 ㅇ

n, m = map(int, input().split())
maze = [list(map(int, input().strip())) for _ in range(n)]

dx = [0,0,-1,1]
dy = [-1,1,0,0]

def bfs():
    q = deque([(0, 0)]) # q넣고
    visited = [[False] * m for _ in range(n)]
    visited[0][0] = True

    while q :
        x, y = q.popleft() # 데큐라서
        if x == n - 1 and y == m - 1:
            return maze[x][y]

        for d in range(4):
            nx, ny = x + dx[d], y + dy[d]
            if not (0 <= nx < n and 0 <= ny < m):
                continue
            if visited[nx][ny] or maze[nx][ny] == 0:
                continue
            visited[nx][ny] = True
            maze[nx][ny] = maze[x][y] + 1
            q.append((nx, ny))
print(bfs())

# 에러 sys.stdin(), input().strip, deque([0,0]). pring