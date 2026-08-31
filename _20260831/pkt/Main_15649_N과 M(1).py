import sys
input = sys.stdin.readline

n, m = map(int, input().split())
arr = [0] * m
visited = [False] * (n + 1)
result = []

def dfs(depth):
    if depth == m:
        result.append(' '.join(map(str, arr)))
        return
    for i in range(1, n+1):
        if visited[i]:
            continue
        visited[i] = True
        arr[depth] = i
        dfs(depth + 1)
        visited[i] = False

dfs(0)
print('\n'.join(result))

