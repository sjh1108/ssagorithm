# 단속카메라 42884 lv3

def solution(routes):
    # 풀이 방법
    # 1. 선을 그어 겹치는 지점을 파악
    # 2. 이어지는 선들 간에 연결을 해줘야 되는 지점 파악
    # 3. 어떤 선의 끝점이 어떤 선의 시작점과 맞닿게 계속
    # -----
    #     ----   1개
    #           ------- 1개
    #                        ---------  1개
    routes.sort(key=lambda x: x[1]) # routes[1] 기준으로 정렬
    
    ans = 0 # 카메라 갯수: 최소 몇 대의 카메라를 설치할지 
    c = -30001 # 카메라의 최소값 for 값비교. 진출지점 기준으로 최소값을 정함
    
    for start, end in routes:
        if start > c: # 처음 이후, "다음꺼의 시작점"이 "앞에꺼의 끝점"보다 더 크면 발동
            c = end
            ans += 1 # 카메라 추가
            
    return ans
    

'''
# 언패킹 사용
for s, e in routes:
    print(s, e)

# 언패킹 없이 쓰면 이렇게 됨 (동작은 완전히 같음) - 길어짐
for route in routes:
    s = route[0]
    e = route[1]
    print(s, e)
'''
# lamda x lambda 오타 조심

    