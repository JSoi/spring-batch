# Spring Batch

## Configuration

### build.gradle

- spring boot 3.4.1
    - java 17
    - querydsl, jpa, mysql
- spring quartz - for scheduling

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.4.1'
    id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.soi'
version = '0.0.1-SNAPSHOT'

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-batch'
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'

		implementation 'org.hibernate.validator:hibernate-validator'
    implementation 'org.springframework.boot:spring-boot-starter-quartz'
    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'com.mysql:mysql-connector-j'
    annotationProcessor 'org.projectlombok:lombok'

    implementation 'com.querydsl:querydsl-jpa:5.1.0:jakarta'
    annotationProcessor "com.querydsl:querydsl-apt:${dependencyManagement.importedProperties['querydsl.version']}:jakarta"
    annotationProcessor "jakarta.annotation:jakarta.annotation-api"
    annotationProcessor "jakarta.persistence:jakarta.persistence-api"
  
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.batch:spring-batch-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
    useJUnitPlatform()
}

```



### application.yml

quartz, batch 설정만 포함. 나머지는 생략

```yml
spring:
  batch:
    job:
      enabled: false # 애플리케이션 기동 시 자동으로 등록된 배치 Job을 실행할지 여부 (false면 자동 실행 안 함)
    jdbc:
      initialize-schema: always # 애플리케이션 기동 시 배치 관련 메타데이터 테이블 생성 (기존 테이블 있어도 재생성)
      # - always : 항상 테이블 생성 (권장 X, 개발환경용)
      # - embedded : 임베디드 DB에서만 생성 (기본값)
      # - never : 생성 안 함 (운영환경 권장)

  quartz: # 스프링 배치와 함께 Quartz 스케줄러를 사용할 경우 Quartz 설정
    job-store-type: jdbc # Quartz Job 정보를 DB에 저장 (JDBC 방식 스토리지 사용)
    jdbc:
      initialize-schema: always # Quartz 관련 테이블을 항상 생성
      # - always : 항상 테이블 생성 (개발 단계에서 사용)
      # - embedded : 임베디드 DB에서만 생성
      # - never : 테이블 생성 안 함 (운영환경 권장)
```



## 목표

상품별 리뷰 평점의 하루 수치 데이터(ex. 평균, 개수)를 저장하는 배치 생성

JobParameter로 특정 날짜(혹은 시간) 이 주어진다면, 해당 날짜에 생성된 리뷰 데이터를 수치화한다



## 구성

### Reader

JobParameter에 해당하는 row 데이터를를 RDB로부터 조회해 읽어온다.

### Processor

DAO를 변환하여 통계 처리에 사용할 수 있는 DTO 클래스로 가공

### Writer

processor를 통해 반환받은 DTO 클래스를 RDB에 병합하여 저장

### Listener

- DailyReviewListener : JobParameter 처리 및 검증 로직 담당
- DailyReviewCleanUpListener : 멱등성 보장을 위해 기존에 저장된 통계 데이터가 있을 경우 해당 데이터를 비활성화 처리
- DefaultRetryListener : 재시작 로깅 처리를 구현한 추상 클래스 (RetryListener 인터페이스 구현)
- DefaultSkipListener : 스킵 로깅 처리를 구현한 추상 클래스 (SkipListener 인터페이스 구현)

### Configuration Code

```java
@Configuration
@RequiredArgsConstructor
public class ReviewStatJobConfig {
    public static final String triggerDate = "REVIEW_TARGET_DATE";

    private final DailyReviewReader dailyReviewReader;
    private final DailyReviewProcessor dailyReviewProcessor;
    private final DailyReviewWriter dailyReviewWriter;
    private final DailyReviewWriterListener dailyReviewWriterListener;
    // listeners
    private final DailyReviewListener dailyReviewListener;
    private final DailyReviewCleanUpListener dailyReviewCleanUpListener;
    private final DefaultSkipListener<RateDto, ReviewBatchDto.ReviewCount> defaultSkipListener;
    private final DefaultRetryListener defaultRetryListener;

    @Bean
    public Job reviewStatisticsJob(JobRepository jobRepository, Step reviewStatStep) {
        return new JobBuilder(DAILY_STATISTICS.name(), jobRepository)
                .start(reviewStatStep)
                .build();
    }

    @Bean
    public Step reviewStatisticsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("reviewStatisticsStep", jobRepository)
                .<RateDto, ReviewBatchDto.ReviewCount>chunk(10, transactionManager)
                .listener(dailyReviewListener)
                .reader(dailyReviewReader.getReader())
                .processor(dailyReviewProcessor)
                .writer(dailyReviewWriter)
                .listener(dailyReviewCleanUpListener)
                .listener(dailyReviewWriterListener)
                .faultTolerant()
                .skip(SkippableException.class).listener(defaultSkipListener)
                .retry(RetryableException.class).retryLimit(3).listener(defaultRetryListener)
                .build();
    }
}
```





## 결과

ex. 2025-02-20이 매개 변수로 주어졌을 경우

**input: review data**

| created_at                 | value | product_id |
| -------------------------- | ----- | ---------- |
| 2025-02-20 04:44:17.403365 | 5     | 2          |
| 2025-02-20 04:44:17.422354 | 4     | 2          |
| 2025-02-20 04:44:17.430995 | 3     | 2          |
| 2025-02-20 04:44:17.436668 | 1     | 2          |
| 2025-02-20 04:44:17.441209 | 1     | 2          |
| 2025-02-20 04:44:17.446454 | 1     | 2          |
| 2025-02-20 04:44:17.451391 | 5     | 1          |
| 2025-02-20 04:44:17.457480 | 4     | 1          |
| 2025-02-20 04:44:17.461903 | 3     | 1          |
| 2025-02-20 04:44:17.466527 | 1     | 1          |
| 2025-02-20 04:44:17.470182 | 1     | 1          |
| 2025-02-20 04:44:17.473922 | 1     | 1          |
| 2025-02-20 04:44:22.779780 | 5     | 2          |
| 2025-02-20 04:44:22.795141 | 4     | 2          |
| 2025-02-20 04:44:22.798746 | 3     | 2          |
| 2025-02-20 04:44:22.802673 | 1     | 2          |
| 2025-02-20 04:44:22.806441 | 1     | 2          |
| 2025-02-20 04:44:22.810562 | 1     | 2          |
| 2025-02-20 04:44:22.814285 | 5     | 1          |
| 2025-02-20 04:44:22.818035 | 4     | 1          |
| 2025-02-20 04:44:22.821051 | 3     | 1          |
| 2025-02-20 04:44:22.823944 | 1     | 1          |
| 2025-02-20 04:44:22.827496 | 1     | 1          |
| 2025-02-20 04:44:22.831124 | 1     | 1          |



**output: daily statistics**

| average | count | created_at                 | end_time                   | start_time                 | product |
| ------- | ----- | -------------------------- | -------------------------- | -------------------------- | ------- |
| 2.5     | 12    | 2025-02-20 05:27:30.070377 | 2025-02-20 04:44:22.831124 | 2025-02-20 04:44:17.451391 | 1       |
| 2.5     | 12    | 2025-02-20 05:27:30.081281 | 2025-02-20 04:44:22.810562 | 2025-02-20 04:44:17.403365 | 2       |





