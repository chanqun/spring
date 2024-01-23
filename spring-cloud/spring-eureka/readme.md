
eureka server는 이 패키지로 하면 되고


client인 경우

eureka discovery client


```
1.
@EnableDiscoveryClient

2.
eureka:
  instance:
    instance-id: ${spring.cloud.client.hostname}:${spring.application.instance_id:${random.value}}
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://127.0.0.1:8761/eureka

```
