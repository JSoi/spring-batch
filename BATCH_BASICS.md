# Spring Batch

### Definition

유저 상호작용이 덜한 대용량 데이터 처리를 위한 경량, 포괄적인 배치 프레임워크

### Pros

- 주기적인 배치 커밋
- 잡의 병렬적인 수행

### Cons

- 역할을 분리함으로써 관심사의 분리도 이루어진다.



## Archtiecture Layer

### Application

- 직접 작성하는 비즈니스 로직 부분
- Job 구성
- Job, Step, Reader, Processor, Writer, Listener

### Batch Core

- Job, Step 실행 제어 (JobLauncher, JobRepository 등)
- Chunk, Step, Flow 등 배치 실행 구조 정dㅡ

### Batch Infrastructure

- 데이터 저장과 관리, 트랜잭션 처리 담당 
- TransactionManager (트랜잭션 관리)
- JobRepository 테이블 (배치 메타데이터 저장용 테이블)



## Domain

### Job

인스턴스를 위한 컨테이너

- 이름 :  Job을 식별하는 이름
- 스탭(Step) :  Job을 구성하는 단계들 (데이터 읽기, 처리, 저장 등)
- 재시작 가능 여부 : 실패 시 재시작할 수 있는지 여부 설정

### JobInstance

Job과 JobInstance는 마치 **클래스와 인스턴스**의 관계와 유사하다.

- **Job**: 실행 구조와 흐름을 정의한 ‘설계도’ 역할
- **JobInstance**: 실제로 실행되며, 각 실행마다 서로 다른 JobParameters(파라미터)를 가질 수 있다.
  - 동일한 Job이라도, **JobParameter 값이 다르면 서로 다른 JobInstance**로 구분된다.

**예시**

예를 들어, 같은 Job이라도

- JobParameter가 “2025-02-20”일 때 생성된 JobInstance와
- JobParameter가 “2025-02-21”일 때 생성된 JobInstance는

서로 다른 인스턴스로 취급된다.



이처럼 JobInstance는 Job + JobParameters로 구성되며,
같은 Job에서 실행돼도, JobParameters가 다르면 다른 실행으로 인식한다.



### Step



### ItemReader



### ItemWriter



### Processor





## Reference

[Spring.io reference](https://docs.spring.io/spring-batch/reference/spring-batch-intro.html)

