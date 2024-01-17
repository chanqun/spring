### springboot-jpa-kotlin

#### 프로젝트 환경설정


#### 주의점 애플리케이션 구현 준비
- Setter를 사용하지 말고 사용하는 method를 만들자
- 모든 연관관계는 LAZY로 진행 
    lazy 로딩 exception -> fetch join 으로 한다.
- list 초기화 언제 할까? 하이버네이트는 엔티티를 영속화 할 때 컬렉션을 감싸서 하이버네이트카
제공하는 내장 컬렉션으로 변경한다.

테이블 camel case -> underscore case로 바꿈

- cascade = CascadeType.ALL 종속되어 저장 삭제 가능


회원 도메인
상품 도메인
주문 도메인

#### 회원 도메인 개발 참고할점
class @Repository에 등록하고

@PersistenceContext
private EntityManager em;

- Autowired, setter, constructor injection을 사용할 수 있다.
 --> 필드는 바꾸기 힘듬
  -> setter은 런타임에서 바뀌면 큰일남
  -> consturctor 은 컴파일 시점에 잡을 수 있음
  

@AllArgsConstructor 모든 필드를 가지고 생성자를 만든다.
@RequiredArgsConstructor은 final만 가지고 생성한다.


#### 상품 도메인 개발

#### 주문 도메인 개발
상품 주문, 주문 내역 조회, 주문 취소


!!! 
강의를 보면 OrderItem...이라는 가변인자를 받는 부분이 나온다

kotlin 에서는 vararg로 해결할 수 있다


현재 서비스 계층은 단순히 엔티티에 필요한 요청을 위임하는 역할을 함
이처럼 엔티티가 비지니스 로직을 가지고 객체 지향의 특성을 적극 활용하는 것을
도메인 모델 패턴이라 한다.

<-> transaction script pattern


#### 변경 감지와 병합(merge) !!!!!! 

##### 준영속 엔티티?
영속성 컨텍스트가 더이상 관리하지 않는 엔티티

> 영속성 컨텍스트가 더는 관리하지 않는 엔티티
> 디비에 가서 엔티티를 가져오는 것은 준영속 상태에 엔티티다.
> 영속성 컨텍스트가 관리하지 않기 때문에 아무리 값을 바꿔도 update가 일어나지 않는다.

해결방법
1. 변경감지 (보통 더 나은 방법)
    - findItem 을 해서 가져와서 setting 하고 set한다.
    - transaction안에서 변경해야 더티 채킹 ㅅㅅ
2. merge
    - merge하면 db에서 가져와서 파라미터 데이터를 다 set해서 동작한다.
    바꿔치기 된 것을 반환해준다.
      

#### API 개발 고급
- 조회용 샘플 데이터 입력
- 지연 로딩과 조회 성능 최적화
- 컬렉션 조회 회적화
- 페이징과 한계 돌파
- OSIV와 성능 최적화 -> open session in view 

#### 지연 로딩과 조회 성능 최적화


#### 컬렉션 조회 최적화
주문내역에서 추가로 주문한 상품 정보를 추가로 조회
Order 기준으로 OrderItem과 Item 이 필요하다


권장 순서
1. 엔티티조회방식으로우선접근
    1. 페치조인으로 쿼리 수를 최적화

2. 컬렉션 최적화
    1. 페이징 필요 hibernate.default_batch_fetch_size , @BatchSize 로 최적화 2. 페이징 필요X 페치 조인 사용
    2. 엔티티 조회방식으로 해결이 안 되면 DTO 조회 방식 사용
3. DTO 조회 방식으로 해결이 안되면 NativeSQL or 스프링 JdbcTemplate

> 참고: 엔티티 조회 방식은 페치 조인이나, hibernate.default_batch_fetch_size , @BatchSize 같이 코드를 거의 수정하지 않고, 옵션만 약간 변경해서, 
> 다양한 성능 최적화를 시도할 수 있다. 반면에 DTO를 직접 조회하는 방식은 성능을 최적화 하거나 성능 최적화 방식을 변경할 때 많은 코드를 변경해야 한다.
> -->엔티티 그래프도 있음

이렇게도 안 되면 캐시를 써야함!!!!!!!! -- 캐시는 dto로 로컬이나 redis

> 참고: 개발자는 성능 최적화와 코드 복잡도 사이에서 줄타기를 해야 한다. 항상 그런 것은 아니지만, 보통 성능 최적화는 단순한 코드를 복잡한 코드로 몰고간다.
> 엔티티 조회 방식은 JPA가 많은 부분을 최적화 해주기 때문에, 단순한 코드를 유지하면서, 성능을 최적화 할 수 있다.
> 반면에 DTO 조회 방식은 SQL을 직접 다루는 것과 유사하기 때문에, 둘 사이에 줄타기를 해야 한다.

DTO 조회 방식의 선택지
DTO로 조회하는 방법도 각각 장단이 있다. V4, V5, V6에서 단순하게 쿼리가 1번 실행된다고 V6이 항상 좋은 방법인 것은 아니다.
V4는 코드가 단순하다. 특정 주문 한건만 조회하면 이 방식을 사용해도 성능이 잘 나온다. 예를 들어서 조회한 Order 데이터가 1건이면 OrderItem을 찾기 위한 쿼리도 1번만 실행하면 된다.
V5는 코드가 복잡하다. 여러 주문을 한꺼번에 조회하는 경우에는 V4 대신에 이것을 최적화한 V5 방식을 사용해야 한다. 예를 들어서 조회한 Order 데이터가 1000건인데, V4 방식을 그대로 사용하면, 쿼리가 총 1 + 1000번 실행된다. 여기서 1은 Order 를 조회한 쿼리고, 1000은 조회된 Order의 row 수다. V5 방식으로 최적화 하면 쿼리가 총 1 + 1번만 실행된다. 상황에 따라 다르겠지만 운영 환경에서 100배 이상의 성능 차이가 날 수 있다.
V6는 완전히 다른 접근방식이다. 쿼리 한번으로 최적화 되어서 상당히 좋아보이지만, Order를 기준으로 페이징이 불가능하다. 실무에서는 이정도 데이터면 수백이나, 수천건 단위로 페이징 처리가 꼭 필요하므로, 이 경우 선택하기 어려운 방법이다. 그리고 데이터가 많으면 중복 전송이 증가해서 V5와 비교해서 성능 차이도 미비하다.


### OSIV 성능 최적화
spring.jpa.open-in-view : default true

```
2021-08-15 02:31:45.452  WARN 64894 --- [  restartedMain] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
```
data base transaction을 시작할 때 db connection을 가져온다.

OSIV 가 true 이면 api 끝날때까지 영속성 컨텍스트를 유지해준다.
-> 지연로딩이 가능한 이유

실시간 트래픽이 중요한 애플리케이션에서는 커넥션이 모자랄 수 있다.

>커맨드와 쿼리 분리 ?

OrderService
    OrderService: 핵심 비즈니스 로직
    OrderQueryService: 화면이나 API에 맞춘 서비스 (주로 읽기 전용 트랜잭션 사용)

필자는 고객 서비스의 실시간 API는 OSIV를 끄고, ADMIN 처럼 커넥션을 많이 사용하지 않는 곳에서는 OSIV를 켠다.
