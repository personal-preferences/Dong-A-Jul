# USER_SERVICE

## 목차

---
1. User feature
2. Login feature 
3. Access, Refresh Token 

## 1. User Feature

---

### 1-1. 회원 리스트 조회(Get - "api/users/page/{page}")
1. Info <br>
    페이지에 해당하는 회원들을 반환합니다. 페이지 번호는 1번부터 시작합니다. 해당 페이지에 회원이 없다면 빈 배열을 반환합니다.
2. Request <br>
   - PathVariable: page(int)
3. Response
   - Body <br>
       List - (userEmail, userNickName, userEnrollDate, userDeleteDate, userIsDeleted, userRole)
   - Status <br>
         success: 200  
         fail: 400 

### 1-2. 회원 상세 조회(Get - "api/users/info)
1. Info <br>
   로그인한 회원이 자신의 정보를 조회할 때 사용합니다.  
2. Request<br>
   X
3. Response
   - Body <br>
      (userEmail, userNickName, userEnrollDate, userDeleteDate, userIsDeleted, userRole)
   - Status <br>
      success: 200  
      fail: 400 

### 1-3. 회원가입 (Post- "api/users")
1. Info <br>
   새로운 회원을 추가합니다. 신규 회원의 권한은 ROLE_USER입니다.
2. Request<br>
   1. Body: userEmail(String), userNickname(String), userPassword(String)
3. Response <br>
   1. Body <br>
      요청 Body 검증 실패, 중복 발견 시 문제되는 부분들을 반환합니다.    
   2. Status <br>
      success: 200 <br>
      fail: 400
   
### 1-4. 비밀번호 수정 (Put- "api/users/password")
1. Info <br>
   회원의 비밀번호를 수정합니다.
2. Request<br>
   - Body: userId(Long), userPassword(Stirng)
3. Response <br>
   1. Body <br>
      요청 Body 검증 실패 시 잘못된 부분을 응답합니다.
   2. Status <br>
      success: 200 <Br>
      fail: 400, 404
### 1- . ( - " ")
1. Info <br>

2. Request<br>

3. Response <br>
   1. Body <br>
      
   2. Status <br>
      
### 1- . ( - " ")
1. Info <br>

2. Request<br>

3. Response <br>
   1. Body <br>
      
   2. Status <br>
      
### 1- . ( - " ")
1. Info <br>

2. Request<br>

3. Response <br>
   1. Body <br>
      
   2. Status <br>
      