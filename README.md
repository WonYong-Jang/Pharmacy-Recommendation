# Pharmacy-Recommendation
 
[외부 API(카카오 주소 검색 API](https://developers.kakao.com/docs/latest/ko/local/dev-guide))와 [공공 데이터(약국 현황 정보)](https://www.data.go.kr/data/15065023/fileData.do)를 활용함으로써 
혼자 개발하고 마무리되는 프로젝트가 아닌 실제 서비스 가능한 수준의 
프로젝트를 경험해본다.

추천된 약국의 길 안내는 [카카오 지도 URL](https://apis.map.kakao.com/web/guide/#routeurl)로 
제공 된다.

## 요구사항 분석 

- 약국 찾기 서비스 요구사항
  - 약국 현황 데이터(공공 데이터)를 관리하고 있다 라고 가정하고, 약국 현황 데이터는 위도 경도의 위치 정보 데이터를 가지고 있다.
  - 해당 서비스로 주소정보를 입력하여 요청하면 위치 기준에서 가까운 약국 3 곳을 추출한다.
  - 주소는 도로명 주소 또는 지번을 입력하여 요청 받는다.
  - 주소는 정확한 상세주소(동, 호수)를 입력하지 않아도 출발지로 지정할 수 있다.
    - ex) 경기도 성남시 분당구 분당동 66
  - 입력 받은 주소를 위도, 경도로 변환하여 기존 정류장 데이터와 비교 및 가까운 정류장을 추출한다.
  - 추출한 정류장 데이터는 출발지와 도착지를 안내하는 길안내 URL로 제공한다.
    - ex) https://map.kakao.com/?sX=500000&sY=1084098&sName=startName&eX=523953&eY=1084098&eName=destinationName
  - 길안내 URL은 고객에게 제공되기 때문에 가독성을 위해 shorten url로 제공한다.
  - shorten url에 사용되는 key값은 인코딩하여 제공한다.
    - ex) http://localhost:8080/dir/nqxtX
  - shorten url의 유효기간은 30일로 제한한다.
  
## Pharmacy Recommendation Process   

<img width="615" alt="스크린샷 2022-04-19 오후 12 58 50" src="https://user-images.githubusercontent.com/26623547/163917670-33f1c8bb-d23f-4552-958d-6096ecb75ebb.png">   

## Direction Shorten Url Process

<img width="615" alt="스크린샷 2022-04-19 오후 12 59 03" src="https://user-images.githubusercontent.com/26623547/163917679-e79dd3ef-17fc-4f48-b77c-64cc4715c1f3.png">  

- 카카오 주소검색 API 연동해보기    
- Spring retry를 이용한 재처리 구현하기 ( 카카오 API의 네트워크 오류 등에 대한 재처리 )   
- 공공 데이터를 활용하여 개발하기 (약국 현황 데이터)   
- base62를 이용한 shorten url 개발하기 (길안내 URL)   
- redis를 이용하여 성능 최적화하기   

## Tech Stack   

- JDK 8
- Spring Boot
- Spring Data JPA
- Gradle
- Handlebars
- Lombok
- Github
- Docker
- AWS EC2
- Redis
- MariaDB
- Travis CI ( Optional )

## Result   

<img width="1320" alt="스크린샷 2022-04-25 오후 11 22 32" src="https://user-images.githubusercontent.com/26623547/165109489-b30d6266-7ea8-4137-baeb-c20042625143.png">   
