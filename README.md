# instagram
## 프로젝트 설명
Instagram Clone Project  
이미지 기반 소셜 미디어 인스타그램 서비스를 구현하는 프로젝트입니다.

## 개발 목표
+ “네카라쿠배 개발자와 함께 Spring으로 인스타그램 서버 만들기” 라는 딥다이브(클론 코딩 챌린지) 에 참여하게 되었습니다.
+ 챌린지의 주된 목표는 인스타그램 API 개발과 함께 
  + Spring Security & JWT 사용
  + github action을 활용해 CI/CD 파이프라인을 구축하여 자동화 배포합니다.

## 개발 기간
23.03.24 ~ 23.04.13 (3주)

## 개발 스택
|                     |                           |
|:--------------------|:--------------------------|
| Language            | Java 17                   |
| Framework           | Spring Boot 3.0.5         |
| Database            | MySQL, H2                 |
| ORM                 | Spring Data JPA, Querydsl |
| Authorization       | Spring Security, JWT      |
| Messaging           | WebSocket, STOMP          |
| API Documentation   | Swagger                   |
| Test                | JUnit5                    |


## 도메인 모델
![domain-modeling](https://user-images.githubusercontent.com/79685920/231748200-7e791443-dd35-4153-8a3c-11811b0b5f66.png)

## 데이터베이스 모델
<img width="771" alt="Screen Shot 2023-04-13 at 9 37 09 PM" src="https://user-images.githubusercontent.com/79685920/231760531-9225a924-ba88-4795-a6c1-a589b6ffa32a.png">

## Api 명세서
| no  | 분류    | API 분류  |  method   |URL| description                              |
|-----|-------|---------|:---------:|---|------------------------------------------|
| 1   | 회원    | Auth    |   POST    |/auth/login| 로그인 (access_token, refresh_token 발급)|
|2 |회원 |Auth   |   POST    |/auth/refresh               |access_token, refresh_token 재발급       |
|3 |회원 |Auth   |   POST    |/auth/logout                |로그아웃 (access_token, refresh_token 무효화)|
|4 |회원 |User   |   POST    |/signup                     |회원 가입                                 |
|5 |회원 |User   |  DELETE   |/users/me                   |회원 탈퇴                                 |
|6 |프로필|User   |    GET    |/users/{userId}             |회원 프로필 조회                             |
|7 |프로필|User   |    PUT    |/users/me                   |프로필 수정                                |
|8 |팔로우|User   |   POST    |/users/{userId}/follow      |팔로우                                   |
|9 |팔로우|User   |  DELETE   |/users/follow/{userId}      |팔로우 취소                                |
|10|글  |post   |   POST    |/posts                      |글 생성                                  |
|11|글  |post   |    PUT    |/posts/{postId}             |글 수정                                  |
|12|글  |post   |  DELETE   |/posts/{postId}             |글 삭제                                  |
|13|글  |post   |    GET    |/posts/{userId}             |피드 조회 - 회원 별 글 전체 목록 조회한다. (커서 페이지네이션)|
|14|댓글 |Comment|   POST    |/comment                    |댓글 생성                                 |
|15|댓글 |Comment|    PUT    |/comment/{commentId}        |댓글 수정                                 |
|16|댓글 |Comment|  DELETE   |/comment/{commentId}        |댓글 삭제                                 |
|17|답글 |Comment|   POST    |/comment/{commentId}/replies|답글 생성                                 |
|18|답글 |Comment|    PUT    |/replies/{commentId}        |답글 수정                                 |
|19|답글 |Comment|  DELETE   |/replies/{commentId}        |답글 삭제                                 |
|20|메세지|DM     |    GET    |/messages                   |DM 목록 조회                              |
|21|메세지|DM     |    GET    |/message                    |DM 상세 내역 조회 - 각 개인별 채팅 내용을 보여준다.      |
|22|메세지|DM     |   POST    |/message                    |DM 전송                                 |

## 업데이트 내역
+ 0.0.1