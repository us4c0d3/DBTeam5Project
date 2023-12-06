# 개발 환경
- Eclipse IDE를 사용하여 개발하였습니다.
- Window 환경을 사용했습니다.
- Window 환경에서는 강의자료에 나오는 것처럼 Oracle을 설치하여 진행하였습니다.
- Java 11 
- OJDBC 10  
- Database 접속을 위한 USER_NAME = teamproject, USER_PASSWD = comp322

# 실행 방법
1. Database 접속
 - USER_NAME = teamproject, USER_PASSWD = comp322.
2. `Team5-Phase4-1.sql` 실행 - DDL.
3. `Team5-Phase4-2.sql` 실행 - Insert 구문.
4. Phase4 > src > main > webapp > static > html > Login.html을 실행.
5. localhost:8080 접속.
6. 로그인.
 - customer의 경우 
   - id : CU000001 ~ CU000200 (insert시, CU000201생성.)
   - pw : customer 테이블 참고. 
 - manager의 경우 
   - id : MA000001 ~ MA000200 (insert시, MA000201생성.) 
   - pw : manager 테이블 참고.

# 시스템이 제공하는 서비스 소개.
- 식당의 재고 소진 여부 확인 및 식당 메뉴, 주문 내역을 확인하는 사이트.
- 사용자(1) 고객
   - 고객은 로그인을 통해 주문 정보, 메뉴 조회, 메뉴 아이템 조회를 할 수 있다.
   - 고객은 비로그인 상태에서 메뉴 조회 및 메뉴 아이템 조회만 할 수 있다.
- 사용자(2) 매니저
   - 매니저는 로그인을 통해 추가로적으로 메뉴 추가, 메뉴 수정, 메뉴 아이템 추가, 재료 수정을 할 수 있다.

# 데모 링크
- https://youtu.be/C6IjAldVNO0




