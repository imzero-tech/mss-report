# MSS Report

## 구현 범위에 대한 설명
### 프로젝트 구성   

- Spring boot 3.3.7
- JDK 17
- h2database used memory
- pebble template https://pebbletemplates.io/

### DB   
과제 구현 및 확장성을 위해 총 4개의 테이블로 구성
<p align="center">
  <img src="/src/main/resources/screenshot/mss-table.png" alt="image" width="300" />
</p>
- BRAND : 브랜드 정보 관리
- CATEGORY : 판매 상품 관리 (카테고리)
- COMPANY : 브랜들 보유한 회사정보 (현재는 MSS)
- PRODUCTS : 판매 상품을 관리하며 BRAND_ID/ CATEGORY_ID 를 통해 상세 정보를 알수 있도록 구현

## 코드 빌드, 테스트, 실행 방법
### 코드 빌드 
git - https://github.com/imzero-tech/mss-report.git 

- bash 
~~~
cd [프로젝트]
./gradlew clean build
./gradlew bootRun --args='--spring.profiles.active=test'
~~~
- intellij
<p align="center">
  <img src="/src/main/resources/screenshot/intellij-build.png" alt="image" width="500" />
</p>

### 테스트 
host- http://localhost:8081/  
api   
+ /xpi/v1/mss/lowest-price
  + 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
  + input - 없음
  + method - GET
+ /xpi/v1/mss/total-lowest-price
  + 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
  + input - 없음
  + method - GET
+ /xpi/v1/mss/min-max-price
  + 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
  + input
    + category
      + 상의, 아우터, 바지, 스니커즈, 가방, 모자, 양말, 액서시리
    + method - GET
+ /xpi/v1/mss/{crudType}/{task}
  + 브랜드 및 상품을 추가/업데이트/삭제 하는 API
  + path valuable
    + {crudType}
      + add(추가), fetch(수정), del(삭제)
    + {task}
      + brand(브랜드명), product(상품명)
      + input - jsonbody
        + brand
          + add/brand  
            + url - http://localhost:8081/xpi/v1/mss/add/brand
            + method : POST
        ~~~
        // json 요청 값
        {
            "brand": {
                  "brand_name" : "Z1"
             }
        }
        ~~~
          + fetch/brand   
            + url - http://localhost:8081/xpi/v1/mss/fetch/brand
            + method : POST
        ~~~
        // json 요청 값
        {
            "brand": {
                  "brand_id" : 11, 
                  "brand_name" : "Z12"
             }
        }
        ~~~
          + del/brand   
            + url - http://localhost:8081/xpi/v1/mss/del/brand
            + method : POST
            + DB 삭제는 하지 않고, Status = DELETE 로 변경하여 관리 (상품 판매 후 브랜드 삭제 됐을 경우 히스토리 관리 측면으로 확장해 봄)
        ~~~
        // json 요청 값
        {
            "brand": {
                  "brand_id" : 11
             }
        }
        ~~~
        + product
          + add/product
            + url = http://localhost:8081/xpi/v1/mss/add/product
            + method : POST
        ~~~
        // json 요청 값 
        {
            "product": {
                "product_name" : "싼 비니",
                "brand_name": "Z12",
                "category_name": "비니",
                "price": 700
                }
        }
        ~~~
        ~~~
        // curl 요청 값
        curl --location 'http://localhost:8081/xpi/v1/mss/add/product' \
             --header 'Content-Type: application/json' \
             --data '{
                  "product": {
                  "product_name" : "싼 비니",
                  "brand_name": "Z12",
                  "category_name": "비니",
                  "price": 700
                  }
              }'
        ~~~
        + fetch/product
          + url = http://localhost:8081/xpi/v1/mss/fetch/product
          + method : POST
        ~~~
        // json 요청 값 
        {
            "product": {
                "product_id": 74,
                "product_name" : "비싼 비니",
                "brand_name": "Z12",
                "category_name": "비니",
                "price": 10700
                }
        }
        ~~~
        ~~~
        // curl 요청 값
        curl --location 'http://localhost:8081/xpi/v1/mss/fetch/product' \
             --header 'Content-Type: application/json' \
             --data '{
                  "product": {
                      "product_id": 74,
                      "product_name" : "비싼 비니",
                      "brand_name": "Z12",
                      "category_name": "비니",
                      "price": 10700
                  }
              }'
        ~~~
        + del/product
          + url = http://localhost:8081/xpi/v1/mss/del/product
          + method : POST
          + DB 삭제는 하지 않고, Status = DELETE 로 변경하여 관리 (상품 판매 후 브랜드 삭제 됐을 경우 히스토리 관리 측면으로 확장해 봄)
        ~~~
        // json 요청 값
        {
            "product": {
                "product_id": 74
            }
        }
        ~~~
        ~~~
        // curl 요청 값
        curl --location 'http://localhost:8081/xpi/v1/mss/del/product' \
             --header 'Content-Type: application/json' \
             --data '{
                  "product": {
                      "product_id": 74
                  }
              }'
        ~~~

  
## 기타 추가 정보
### h2-console
+ http://localhost:8081/h2-console
  + jdbc:h2:mem:test 
  + ContextRefreshedEvent 시점에 초기화 데이타를 넣는 WebMvcConfiguration 설정 

### swagger-ui
+ http://localhost:8081/swagger-ui/swagger-ui/index.html

### DSL, JQPL
+ DSL 을 통해 데이타 조회/ 업데이트
+ JQPL 을 통해 데이타 벌크 업데이트

### 관리 페이지
+ http://localhost:8081/admin
+ 상품조회, 브랜드조회, 카테고리조회, 회사조회 > 수정 기능은 구현하지 않음
  + AdminLte, DataTable