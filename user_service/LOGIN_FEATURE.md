# Login 

## 목차
1. 회원 기능
2. 로그인 기능
3. 토큰 관리

## 1. 기능 설명

---

### 1-1. 회원 리스트 조회(Get - "api/users/page/{page}")
1. Info <br>
    페이지에 해당하는 회원들을 반환합니다. 페이지 번호는 1번부터 시작합니다. 해당 페이지에 회원이 없다면 빈 배열을 반환합니다.
2. Request <br>
   1. PathVariable: page(int)
3. Response
   1. Body <br>
       List - (userEmail, userNickName, userEnrollDate, userDeleteDate, userIsDeleted, userRole)
   2. Status <br>
      success: 200  
      fail: 400 

### 1-2. 회원 상세 조회(Get - "api/users/info)
1. Info <br>
   로그인한 회원이 자신의 정보를 조회할 때 사용합니다.  
2. Request<br>
   X
3. Response
   1. Body <br>
      (userEmail, userNickName, userEnrollDate, userDeleteDate, userIsDeleted, userRole)
   2. Status <br>
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
      
### 1- . ( - " ")
1. Info <br>

2. Request<br>

3. Response <br>
   1. Body <br>
      
   2. Status <br>
      