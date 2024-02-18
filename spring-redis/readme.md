# redis bulk test

클라이언트의 스레드 수 * 파드 수 * 요청 얼마나 날리는지 해서 테스트
```
redis-benchmark -n 50000 -d 100000 -t hset -r 5000 -c 500
```


