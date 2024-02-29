


Thread Runnable

Future Callable 결과 받을때(get)는 blocking

CompletableFuture (thenApplyAsync), isDone을 이용하여 비동기 실행을 할 수 있음 실무에서 거의 이것을 이용


리액티스 스트림은 리액티브 프로그램의 표준 API 사양을 말함

Publisher, Subscriber, Subscription, Processor


- 발행자가 제공할 수 있는 데이터의 양은 무한하고 순차적 처리를 보장

- Subscriber 은 자신이 처리할 수 있는 만큼의 데이터를 요청하고 처리한다.

- Subscription은 발행자와 구독자를 연결하는 매개체이며 구동자가 데이터를 요청하거나 구동을 해지하는 등 데이터 조절에 관련된 역할을 담당



### 프로젝트 리액터
리액티브 스트림의 구현체 중 하나로 스프링의 에코시스템 범주에 포함된 프레임워크


### 모노와 플럭스
Publisher 인터페이스를 구현하는 Mono와 Flux


### 스프링 web flux
리액티브 기반의 웹 스택 프레임워크이다

기본적으로 리액터 기반이며, 리액티브 스트림의 다른 구현체인 RxJava나 코틀린 코루틴으로도 개발이 가능하다.


모두 비동기인게 좋은데
어쩔 수 없을떄만

Mono.fromCallable {}.subscribeOn(Schedulers.boundedElastic()) 사용


### coroutine

suspend
runBlocking
launch


flow
flow 는 emit, collect 를 이용할 수 있음



