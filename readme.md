# Simple 커뮤니티 게시판 서비스

## 프로젝트 개요
- 단순 CRUD 게시판으로 시작하여 지속적인 기능 확장과 리팩토링

## 주요기능

1. 유저 관리

- 회원가입 (아이디, 비밀번호, 이메일, 닉네임)
- JWT 기반 로그인
- 회원정보 변경
- 회원 탈퇴

2. 게시글 관리

- 게시글 CRUD
- 게시글 페이징
- 게시글 상세 조회
- 게시글 작성자만 수정/삭제 가능

3. 댓글 관리

- 댓글 CRUD
- 댓글 작성자만 수정/삭제 가능

4. 검색

- 제목/내용/작성자로 게시글 검색
- 최신순/좋아요순 정렬

5. 좋아요 기능

- 게시글 좋아요 추가/취소
- 댓글 좋아요 추가/취소

## API 설계

|     기능     | HttpMethod |               URL               |                                        요청예시                                        |                                                                                                  응답예시                                                                                                   |       기타        |
|:----------:|:----------:|:-------------------------------:|:----------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------:|
|   중복 체크    |    GET     |   /api/users?nickname={param}   |                                                                                    |                                                                                             {"exist":true}                                                                                              |                 |
|    회원가입    |    POST    |        /api/users/signup        | {"userid":"test", "password":"test", "email":"email@email.com", "nickname":"test"} |                                                                 {"id":1, "userId":"test", "email":"email@email.com", "nickname":"test"}                                                                 |                 |
|    로그인     |    POST    |        /api/users/login         |                        {"userId":"test", "password":"test"}                        |                                                                                                                                                                                                         |   쿠키에 JWT 추가    |
|  유저 정보 조회  |    GET     |       /api/users/{userid}       |                                                                                    |                                                                  {"id":1 "userid":"test","email":"email@email.com", "nickname":"test"}                                                                  |                 |
| 유저 이메일 변경  |   PATCH    |    /api/users/{userid}/email    |                             {"email":"test@test.com"}                              |                                                                                                                                                                                                         |     본인만 가능      |
| 유저 닉네임 변경  |   PATCH    |  /api/users/{userid}/nickname   |                                {"nickname":"test"}                                 |                                                                                                                                                                                                         |     본인만 가능      |
| 유저 비밀번호 변경 |   PATCH    |  /api/users/{userid}/password   |                                {"password":"test"}                                 |                                                                                                                                                                                                         |     본인만 가능      |
|   회원 탈퇴    |   DELETE   |       /api/users/{userid}       |                                {"password": "test"}                                |                                                                                                                                                                                                         |     본인만 가능      |
|            |            |                                 |                                                                                    |                                                                                                                                                                                                         |                 |
|   게시글 작성   |    POST    |           /api/posts            |                       {"title":"title", "content":"content"}                       |                                                                                              {"postId":1}                                                                                               |      토큰필요       |
|   게시글 수정   |   PATCH    |       /api/posts/{postId}       |                       {"title":"title", "content":"content"}                       |                                                                                                                                                                                                         | 토큰필요<br> 본인만 가능 |
|   게시글 삭제   |   DELETE   |       /api/posts/{postId}       |                                            |                                                                                                                                                                                                         | 토큰필요<br> 본인만 가능 |
|            |            |                                 |                                                                                    |                                                                                                                                                                                                         |                 |
|   댓글 작성    |    POST    |  /api/posts/{postId}/comments   |                               {"comment":"comment"}                                |                                                                                                                                                                                                         |      토큰필요       |
|   댓글 수정    |   PATCH    |    /api/comments/{commentId}    |                               {"comment":"comment"}                                |                                                                                                                                                                                                         | 토큰필요<br> 본인만 가능 |
|   댓글 삭제    |   DELETE   |    /api/comments/{commentId}    |                                                                                    |                                                                                                                                                                                                         | 토큰필요<br> 본인만 가능 |
|            |            |                                 |                                                                                    |                                                                                                                                                                                                         |                 |
|   게시판 조회   |    GET     |           /api/posts            |                                                                                    |                                                        [{"title":"title","comments":1, "like":1, "nickname":"nickname", "lastModifiedAt":수정시간}]                                                         |                 |
|   게시글 조회   |    GET     |       /api/posts/{postId}       |                                                                                    | {"title":"title", "content":"content", "like":1, "myLike":false,"lastModifiedAt":수정시간, "modified":false "comments":["comment":"comment", "nickname":"nickname","lastModifiedAt":수정시간, "modified":true]} |                 |
|            |            |                                 |                                                                                    |                                                                                                                                                                                                         |                 |
| 게시글 좋아요 추가 |    POST    |    /api/posts/{postId}/likes    |                                                                                    |                                                                                                                                                                                                         |      토큰필요       |
| 게시글 좋아요 삭제 |   DELETE   |    /api/posts/{postId}/likes    |                                                                                    |                                                                                                                                                                                                         |      토큰필요       |
| 댓글 좋아요 추가  |    POST    | /api/comments/{commentId}/likes |                                                                                    |                                                                                                                                                                                                         |              토큰필요   |
| 댓글 좋아요 삭제  |   DELETE   | /api/comments/{commentId}/likes |                                                                                    |                                                                                                                                                                                                         |              토큰필요   |

## 도전과제

- 성능 최적화 및 대용량 처리