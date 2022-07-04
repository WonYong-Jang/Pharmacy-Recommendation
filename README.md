# Pharmacy-Recommendation   

해당 코드는 [패스트 캠퍼스 강의](https://fastcampus.co.kr/dev_online_befinal)에서 사용됩니다.   
 
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
  - 주소는 정확한 상세주소(동, 호수)를 제외한 주소 정보를 이용하여 추천 한다.   
    - ex) 서울 성북구 종암로 10길
  - 입력 받은 주소를 위도, 경도로 변환 하여 기존 약국 데이터와 비교 및 가까운 약국을 찾는다.    
  - 입력한 주소정보에서 정해진 반경(10km) 내에 있는 약국만 추천 한다.   
  - 추출한 약국 데이터는 길안내 URL 및 로드뷰 URL로 제공한다.   
    - ex) 
    https://map.kakao.com/link/to/카카오판교오피스,37.402056,127.108212    
    https://map.kakao.com/link/roadview/37.402056,127.108212     

  - 길안내 URL은 고객에게 제공되기 때문에 가독성을 위해 shorten url로 제공한다.
  - shorten url에 사용되는 key값은 인코딩하여 제공한다.
    - ex) http://localhost:8080/dir/nqxtX
  - shorten url의 유효기간은 30일로 제한한다.   
  
## Pharmacy Recommendation Process   

<img width="615" alt="스크린샷 2022-07-01 오후 6 47 18" src="https://user-images.githubusercontent.com/26623547/176872242-8d893c57-3973-4e3e-b822-3beaeada26ec.png">   

## Direction Shorten Url Process

<img width="615" alt="스크린샷 2022-06-23 오후 9 42 58" src="https://user-images.githubusercontent.com/26623547/175301168-ee35793c-18ff-4a4a-8610-7a9455e9fef7.png">  


## Feature List   

- Spring Data JPA를 이용한 CRUD 메서드 구현하기      
- Spock를 이용한 테스트 코드 작성하기     
- Testcontainers를 이용하여 독립 테스트 환경 구축하기
- 카카오 주소검색 API 연동하여 주소를 위도, 경도로 변환하기   
- 추천 결과를 카카오 지도 URL로 연동하여 제공하기   
- 공공 데이터를 활용하여 개발하기 (약국 현황 데이터)
- Handlebars를 이용한 간단한 View 만들기   
- 도커를 사용하여 다중 컨테이너 애플리케이션 만들기   
- 애플리케이션을 클라우드 서비스에 배포하기   
- Spring retry를 이용한 재처리 구현하기 (카카오 API의 네트워크 오류 등에 대한 재처리)   
- base62를 이용한 shorten url 개발하기 (길안내 URL)   
- redis를 이용하여 성능 최적화하기   

## Tech Stack   

- JDK 11
- Spring Boot 2.6.7
- Spring Data JPA
- Gradle
- Handlebars
- Lombok
- Github
- Docker
- AWS EC2
- Redis
- MariaDB
- Spock   
- Testcontainers   

## Result   

<img width="1300" alt="스크린샷 2022-06-23 오후 8 04 48" src="https://user-images.githubusercontent.com/26623547/175286356-deefcf5d-f94e-49c3-b0f0-4f2c2e3ed50a.png">

<img width="1300" alt="스크린샷 2022-07-04 오후 11 35 48" src="https://user-images.githubusercontent.com/26623547/177176044-ec4d7809-94d1-4fe8-9837-16a9c8c0cd7a.png">
