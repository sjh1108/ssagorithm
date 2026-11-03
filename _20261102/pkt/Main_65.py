import sys
from collections import deque, defaultdict, Counter
from heapq import heappush, heappop
from itertools import permutations, combinations
from bisect import bisect_left, bisect_right

input = sys.stdin.readline

def main():
    # n은 참가자 수, k는 제거할 순서
    n, k = map(int, sys.stdin.readline().split())

    # 남아있는 참가자를 시계방향으로 담은 리스트
    people = list(range(1, n+1))

    # 이번 라운드 시작 참가자의 인덱스 
    i = 0

    # 제거된 참가자를 담을 리스트
    removed = []

    while people:
        # 제거할 참가자의 인덱스 계산 (현재 위치 + 차이) % 현재 남은 인원수 : 인덱스가 끝을 넘어가면 다시 처음으로 돌아가기 위해 
        i = (i + k - 1) % len(people)
        removed.append(people[i]) # 제거 리스트에 추가
        people.pop(i) # 제거된 참가자를 리스트에서 제거

        if people: # 파이썬은 0이 false
            # 다음 라운드 시작 참가자의 인덱스 계산
            i = i % len(people)

    print(*removed)

main()

# [OJ] 65_예선 무대 탈락 순서 / 실버 / 박기택