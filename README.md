# Pharmacy-Recommendation
 
[외부 API(카카오 API](https://developers.kakao.com/docs/latest/ko/local/dev-guide))와 [공공 데이터(약국 현황 정보)](https://www.data.go.kr/data/15065023/fileData.do)를 활용함으로써 
혼자 개발하고 마무리되는 프로젝트가 아닌 실제 서비스 가능한 수준의 
프로젝트를 경험해본다.

## Process   

<img width="615" alt="스크린샷 2022-04-19 오후 12 58 50" src="https://user-images.githubusercontent.com/26623547/163917670-33f1c8bb-d23f-4552-958d-6096ecb75ebb.png">   


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
- Thymeleaf
- Lombok
- Github
- Docker
- AWS RDS, EC2
- AWS Elastic Cache(Redis)
- Travis CI ( Optional )

