# 메가박스 클론코딩
### 시연영상 링크
https://drive.google.com/file/d/1xMaaIKn_NJTiiyG54ICLCFpf_ud6klqJ/view?usp=sharing
### MainActivity
![image](https://github.com/yerim425/RisingCamp5/assets/111108212/4be24a45-39a2-48f9-81b9-bdeb91265682)
#### 1. **영화진흥위원회 API**
##### - 타겟날짜를 요청변수로 담아서 **일별 박스오피스 데이터**를 요청해 받아옴
###### -   활용 데이터: **영화 제목**, 순위, 매출율(예매율 데이터가 없어서 매출율 데이터로 대체), **영화 코드**
##### - 위에서 받아온 **영화 코드**를 요청변수로 담아서 **영화 상세정보 데이터**를 요청해 받아옴
###### -   활용 데이터: 관람등급
#### 2. **네이버 영화 검색 API**
##### - 영화진흥위원회 API로 받아온 **영화 제목**을 요청변수로 담아서 **영화 정보 데이터**를 요청해 받아옴
###### -   활용 데이터: 영화 포스터 이미지, 관객 평점
### MunuActivity
![image](https://github.com/yerim425/RisingCamp5/assets/111108212/48742397-c3af-4283-83fb-c79b07b52d39)
#### 1. 로그인 텍스트뷰 클릭 시 로그인 Activity으로 이동
![image](https://github.com/yerim425/RisingCamp5/assets/111108212/e14441c6-c7ab-4c92-9130-b920475295f8)
###### - 로그인 성공 시 메뉴 화면에 로그인한 유저의 이름을 표시함
###### - 로그아웃 버튼 클릭 시 로그인 이전의 메뉴 화면을 표시함
###### - **네이버 아이콘** 클릭시 네이버 계정으로 연동 가능
#### 2. “영화별 예매” 메뉴 아이템 클릭시 영화 선택 Activity으로 이동
![image](https://github.com/yerim425/RisingCamp5/assets/111108212/00a84088-4d38-44fc-bf89-3ba1da3a6e75)
##### (현재 상영중인 영화를 모두 출력하고 싶었지만 활용한 API에서는 데이터를 찾지 못해서 시간 관계상 일단 박스오피스 영화 리스트 출력)
