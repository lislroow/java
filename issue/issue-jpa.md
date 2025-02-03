
#### statement has been closed (log4jdbc)

log4jdbc 가 설정되어 있고,

spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
spring.datasource.url=jdbc:log4jdbc:postgresql://rocky8-market:5432/postgres

entity 의 pk 가 GenerationType.IDENTITY 이고,

```
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

jpa 로 save 를 하면,

repo.save(entity);

jpa 저장은 저장은 정상 처리되지만, `statement has been closed` 오류가 발생함

log4jdbc 가 resultset 을 출력하면서 발생을 일으킴

log4jdbc 를 제거하거나, 

spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://rocky8-market:5432/postgres

GenerationType.IDENTITY 를 GenerationType.SEQUENCE 로 변경해야함

```
@Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sy_error_log_seq")
@SequenceGenerator(
  name = "sy_error_log_seq",
  sequenceName = "sy_error_log_id_seq",
  allocationSize = 1
)
private Long id;
```

#### modelmapper 변환

spring.jpa.open-in-view=false 일 경우 아래 오류 발생

```
org.modelmapper.internal.Errors.throwMappingExceptionIfErrorsExist(Errors.java:386) #class org.modelmapper.MappingException #ModelMapper mapping errors:
1) Converter org.modelmapper.internal.converter.MergingCollectionConverter@680bb0f9 failed to convert org.hibernate.collection.spi.PersistentBag to java.util.List.
1 error
```

spring.jpa.open-in-view=true 으로 설정 변경
