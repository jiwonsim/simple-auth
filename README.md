# simple-auth
Spring boot와 Vuejs를 이용한 로그인 페이지 


## 기능
1. 구글 로그인
2. 이름/아이디/비밀번호로 회원가입/로그인
3. 로그아웃 
4. 로그인 유지


## 화면
|로그인|회원가입|일반 로그인|구글 로그인|
|:-:|:-:|:-:|:-:|
|<img width="612" alt="스크린샷 2020-02-02 오후 5 33 59" src="https://user-images.githubusercontent.com/28748103/73605490-47568780-45e2-11ea-8cd6-2e37545113c4.png">|<img width="612" alt="스크린샷 2020-02-02 오후 5 34 06" src="https://user-images.githubusercontent.com/28748103/73605491-47568780-45e2-11ea-86db-0788d16d4d5d.png">|<img width="612" alt="스크린샷 2020-02-02 오후 5 34 18" src="https://user-images.githubusercontent.com/28748103/73605492-47568780-45e2-11ea-991b-a64e7af05e54.png">|<img width="612" alt="스크린샷 2020-02-02 오후 5 34 23" src="https://user-images.githubusercontent.com/28748103/73605489-47568780-45e2-11ea-90a7-e0cb39431ae9.png">|


## 서비스 아키텍처 
![architecture-of-simple-auth](https://user-images.githubusercontent.com/28748103/73605475-22621480-45e2-11ea-9808-97041c50512b.png)


### 맺으며
1. 소셜 로그인은 생각보다 쉽다. 리다이렉팅 되는 부분만 조금 헷갈릴 뿐!
2. DNS 경험
    - Redirecting URI는 공개 최상위 도메인(.com, .org)로 끝내야하기 때문에 무료 도메인을 발급 받았다. 
    - Amazon Route 53을 이용해 도메인 리소스로 라우팅을 해줬다. 
3. 토큰
    - 토큰을 사용해 로그인/로그아웃 기능을 구현하였다. 
    - 토큰은 개발자가 폐기할 수 없으므로 유효한 토큰을 담는 저장 공간이 필요하다. 로그아웃 기능은 저장 공간에서 토큰을 삭제하여 구현할 수 있다. 
4. 로그인 유지 기능
    - 프론트에서 발급받은 토큰을 LocalStorage에 설정하여 구현하였다. 
5. Vue.js 프로젝트 배포


### 해결해야할 부분
1. 토큰을 저장 공간에 따로 저장하는 것이 맞는 걸까? 세션을 이용했을 때 메모리 차지하는 단점을 토큰이 해결해주었다고 했는데, 토큰마저 어딘가에 저장한다면 토큰이 세션보다 더 효율적인 걸까? 
2. Vue.js를 Nginx로 배포하는 것이 맞을까? Vue.js 프로젝트 전체를 ec2에서 실행시키면 안 되는 걸까? 
3. Spring Security를 이해하지 못하고 만들었다! 


### 추가할 기능
- [ ] 토큰을 저장하는 DB가 관계형일 필요가 없을 것이다. NoSQL을 이용해 토큰을 저장하는 기능을 추가할 것이다!
- [ ] Spring Security로 안전한 인증 기능 만들기
- [ ] Ngrinder를 이용한 성능 체크
- [ ] Jenkins나 Travis를 이용한 배포 자동화
- [ ] Nginx나 HAproxy를 이용한 로드 밸런싱 
- [ ] 무중단 배포 


### 사용 기술과 툴
#### Backend 
- Java 8
- Spring boot
- Spring Security
- OAuth2
- Maven
- Lombok
- JPA
- MySQL
- Google OAuth Service 

#### Frontend
- Vue.js 
- npm
- axios

#### Build
- Nginx
- AWS EC2
- AWS RDS
- AWS Route 53
