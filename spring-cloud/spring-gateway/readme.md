
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




