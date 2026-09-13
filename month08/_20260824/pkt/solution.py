def dfs(numbers, target, idx, total):
    global answer # 파이썬은 둘 다 매서드에 전역처리해줘야 함. 

    if idx == len(numbers): # 만약에 길이가 같고, 
        if total == target: #  타겟이 성립하면
            answer += 1 # 답안에 추가해주세요
        return # 끝

    dfs(numbers, target, idx + 1, total + numbers[idx]) # DFS적으로 끝까지 탐색해주세요. 
    dfs(numbers, target, idx + 1, total - numbers[idx])


def solution(numbers, target):
    global answer # 전역으로 설정
    answer = 0

    dfs(numbers, target, 0, 0) # 시작

    return answer # 끝나면 출력