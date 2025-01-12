# MSS Report

## 구현 범위에 대한 설명
### 프로젝트 구성   

- Spring boot 3.3.7
- JDK 17
- h2database used memory
- pebble template https://pebbletemplates.io/

### DB   
과제 구현 및 확장성을 위해 총 4개의 테이블로 구성   
![image](/src/main/resources/screenshot/mss-table.png) 

- BRAND : 브랜드 정보 관리
- CATEGORY : 판매 상품 관리 (카테고리)
- COMPANY : 브랜들 보유한 회사정보 (현재는 MSS)
- PRODUCTS : 판매 상품을 관리하며 BRAND_ID/ CATEGORY_ID 를 통해 상세 정보를 알수 있도록 구현 

~~~

~~~

## 코드 빌드, 테스트, 실행 방법
~~~
~~~

## 기타 추가 정보
~~~
~~~
