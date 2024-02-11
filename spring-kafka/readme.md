# kafka

```
bin/zkServer.sh start-foreground

bin/kafka-server-start.sh config/server.properties
```


## 1. 토픽명
a. Rule을 정해 패턴화하여 의미를 부여
토픽명은 한번 정하면 바꾸기가 매우 어려움

## 2. 토픽의 파티션 개수 계산
- 1초당 메시지 발행 수 / Consumer Thread 1개가 1초당 처리하는 메시지 수
- 1000 / 100 = 10개의 파티션 필요

파티션 수를 늘릴 수 있지만 줄일 수는 없음

## 3. Retention 시간
디스크 크기와 데이터의 중요성에 따라 판단




