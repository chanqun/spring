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



컨텍스트 초기화할 때 생성
```java
@Bean
public NewTopic clip2() {
    return TopicBuilder.name("clip2").build();
}

@Bean
public KafkaAdmin.NewTopics clip2s() {
        return new KafkaAdmin.NewTopics(
        TopicBuilder.name("clip2-part1").build(),
        TopicBuilder.name("clip2-part2")
        .partitions(3)
        .replicas(1)
        .config(TopicConfig.RETENTION_MS_CONFIG, String.valueOf(1000 * 60 * 60))
        .build()
    );
}


@Bean
public ApplicationRunner runner(AdminClient adminClient) {
    return args -> {
        Map<String, TopicListing> topics = adminClient.listTopics().namesToListings().get();
    
        for (String topicName : topics.keySet()) {
        TopicListing topicListing = topics.get(topicName);
        System.out.println(topicListing);
    
        Map<String, TopicDescription> description = adminClient.describeTopics(Collections.singleton(topicName)).allTopicNames().get();
    
        System.out.println(description);
    
        adminClient.deleteTopics(Collections.singleton(topicName));
        }
    };
}
```

