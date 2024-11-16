#### 1) 후방탐색(Lookbehind)

```
(?<= ...) 특정 패턴이 앞에 있는지 확인

(?<=@)\w+ → @ 뒤에 오는 단어를 추출
```

```java
{
  String urlPath = "jar:nested:/C:/project/java/boot3x/boot3x-mybatis/target/boot3x-mybatis-0.1-SNAPSHOT.jar/!BOOT-INF/classes/!/";
  String regex = "(?<=nested:)(.*)/!BOOT-INF/classes/!/";
  Pattern pattern = Pattern.compile(regex);
  Matcher matcher = pattern.matcher(urlPath);
  if (matcher.find()) {
    String path = matcher.group(1);
    System.err.println(path);
  } else {
    System.out.println("not matched");
  }
}
```

#### 2) 비캡처 그룹(Non-capturing Group)

```
(?: ...) 그룹화는 하지만 캡처하지 않음

(?:\d{3})-\d{2}-\d{4} → 하이픈을 포함한 숫자 묶음, 캡처는 하지 않음
```

#### 3) 전방탐색(Lookahead)

```
(?= ...) 특정 패턴이 뒤에 오는지 확인

\\d(?=\\D) → 숫자 뒤에 비숫자가 오는 경우만 숫자 추출
```
