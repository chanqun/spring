
spring cloud gateway - filter

gateway handler mapping -> predicate -> pre filter 
                             <-         post filter


yml 설정한 것을 필터로도 만들 수 있음

```java
@Configuration
public class FilterConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f -> f.addRequestHeader("first-request", "first-request-header")))
                .build();        
    }
}
```


```
chain 하던 곳에 new OrderedGatewayFilter 를 사용하면 순서 조정할 수 있음
```


API Gateway
- 인증 및 권한 부여
- 서비스 검색 통합
- 응답 캐싱
- 정책, 회로 차단기 및 QoS 다시 시도
- 속도 제한
- 부하 분산
- 로깅, 추적, 상관 관계
- 헤더, 쿼리 문자열 및 청구 변환
- IP 허용 목록에 추가


