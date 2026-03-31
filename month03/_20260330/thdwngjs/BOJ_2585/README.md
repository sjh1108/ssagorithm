# [Gold II] 경비행기 - 2585 

[문제 링크](https://www.acmicpc.net/problem/2585) 

### 성능 요약

메모리: 51548 KB, 시간: 396 ms

### 분류

그래프 이론, 그래프 탐색, 이분 탐색, 너비 우선 탐색, 매개 변수 탐색

### 제출 일자

2026년 3월 25일 00:20:55

### 문제 설명

<p>경비행기 독수리호가 출발지 S에서 목적지 T로 가능한 빠른 속도로 안전하게 이동하고자 한다. 이때, 경비행기의 연료통의 크기를 정하는 것이 중요한 문제가 된다. 큰 연료통을 장착하면 중간에 내려서 급유를 받는 횟수가 적은 장점이 있지만 연료통의 무게로 인하여 속도가 느려지고, 안정성에도 문제가 있을 수 있다. 한편 작은 연료통을 장착하면 비행기의 속도가 빨라지는 장점이 있지만 중간에 내려서 급유를 받아야 하는 횟수가 많아지는 단점이 있다. 문제는 중간에 내려서 급유를 받는 횟수가 k이하 일 때 연료통의 최소용량을 구하는 것이다. 아래 예를 보자.</p>

<p style="text-align: center;"><img alt="" src="https://upload.acmicpc.net/74546cce-0762-4546-b2b9-aee04a54f1f2/-/preview/" style="width: 224px; height: 220px;"></p>

<p>위 그림은 S, T와 7개의 중간 비행장의 위치를 나타내고 있는 그림이다. 위 예제에서 중간급유를 위한 착륙 허용 최대횟수 k=2라면 S-a-b-T로 가는 항로가 S-p-q-T로 가는 항로 보다 연료통이 작게 된다. 왜냐하면, S-p-q-T항로에서 q-T의 길이가 매우 길어서 이 구간을 위해서 상당히 큰 연료통이 필요하기 때문이다. 문제는 이와 같이 중간에 최대 K번 내려서 갈 수 있을 때 최소 연료통의 크기가 얼마인지를 결정하여 출력하면 된다. 참고사항은 다음과 같다.</p>

<ol style="list-style-type:lower-alpha">
	<li>모든 비행기는 두 지점 사이를 반드시 직선으로 날아간다. 거리의 단위는 ㎞이고 연료의 단위는 ℓ(리터)이다. 1ℓ당 비행거리는 10㎞이고 연료주입은 ℓ단위로 한다.</li>
	<li>두 위치간의 거리는 평면상의 거리이다. 예를 들면, 두 점 g=(2,1)와 h=(37,43)간의 거리 d(g,h)는 <mjx-container class="MathJax" jax="CHTML" style="font-size: 109%; position: relative;"><mjx-math class="MJX-TEX" aria-hidden="true"><mjx-msqrt><mjx-sqrt><mjx-surd><mjx-mo class="mjx-sop"><mjx-c class="mjx-c221A TEX-S1"></mjx-c></mjx-mo></mjx-surd><mjx-box style="padding-top: 0.103em;"><mjx-mo class="mjx-n"><mjx-c class="mjx-c28"></mjx-c></mjx-mo><mjx-mn class="mjx-n"><mjx-c class="mjx-c32"></mjx-c></mjx-mn><mjx-mo class="mjx-n" space="3"><mjx-c class="mjx-c2212"></mjx-c></mjx-mo><mjx-mn class="mjx-n" space="3"><mjx-c class="mjx-c33"></mjx-c><mjx-c class="mjx-c37"></mjx-c></mjx-mn><mjx-msup><mjx-mo class="mjx-n"><mjx-c class="mjx-c29"></mjx-c></mjx-mo><mjx-script style="vertical-align: 0.289em;"><mjx-mn class="mjx-n" size="s"><mjx-c class="mjx-c32"></mjx-c></mjx-mn></mjx-script></mjx-msup><mjx-mo class="mjx-n" space="3"><mjx-c class="mjx-c2B"></mjx-c></mjx-mo><mjx-mo class="mjx-n" space="3"><mjx-c class="mjx-c28"></mjx-c></mjx-mo><mjx-mn class="mjx-n"><mjx-c class="mjx-c31"></mjx-c></mjx-mn><mjx-mo class="mjx-n" space="3"><mjx-c class="mjx-c2212"></mjx-c></mjx-mo><mjx-mn class="mjx-n" space="3"><mjx-c class="mjx-c34"></mjx-c><mjx-c class="mjx-c33"></mjx-c></mjx-mn><mjx-msup><mjx-mo class="mjx-n"><mjx-c class="mjx-c29"></mjx-c></mjx-mo><mjx-script style="vertical-align: 0.289em;"><mjx-mn class="mjx-n" size="s"><mjx-c class="mjx-c32"></mjx-c></mjx-mn></mjx-script></mjx-msup></mjx-box></mjx-sqrt></mjx-msqrt></mjx-math><mjx-assistive-mml unselectable="on" display="inline"><math xmlns="http://www.w3.org/1998/Math/MathML"><msqrt><mo stretchy="false">(</mo><mn>2</mn><mo>−</mo><mn>37</mn><msup><mo stretchy="false">)</mo><mn>2</mn></msup><mo>+</mo><mo stretchy="false">(</mo><mn>1</mn><mo>−</mo><mn>43</mn><msup><mo stretchy="false">)</mo><mn>2</mn></msup></msqrt></math></mjx-assistive-mml><span aria-hidden="true" class="no-mathjax mjx-copytext">\(\sqrt{(2-37)^2 + (1-43)^2}\)</span></mjx-container> = 54.671... 이고 50＜d(g,h) ≤ 60이므로 필요한 연료는 6ℓ가 된다.</li>
	<li>출발지 S의 좌표는 항상 (0,0)이고 목적지 T의 좌표는 (10000,10000)으로 모든 입력 데이터에서 고정되어 있다.</li>
	<li>출발지와 목적지를 제외한 비행장의 수 n은 3 ≤ n ≤ 1000이고 그 좌표 값 (x,y)의 범위는 0＜x,y＜10000의 정수이다. 그리고 최대 허용 중간급유 횟수 k는 0 ≤ k ≤ 1000이다.</li>
</ol>

### 입력 

 <p>첫 줄에는 n과 k가 하나의 공백을 사이에 두고 주어진다. 그 다음 n개의 줄에는 각 비행장 (급유지)의 정수좌표가 x y 형식으로 주어진다.</p>

### 출력 

 <p>S에서 T까지 k번 이하로 중간급유 하여 갈 수 있는 항로에서의 최소 연료통 용량에 해당되는 정수를 출력한다.</p>
