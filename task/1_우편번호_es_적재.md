#### 1) 테이블 준비

우편번호 전체 중 elastic 으로 적재할 대상을 별도 테이블로 분리

```sql
CREATE TABLE zipcode_es AS
SELECT sido, sigungu
FROM zipcode
where sigungu like '%구'
GROUP BY sido, sigungu
;
```

#### 2) dbeaver 데이터 export (format: json)

zipcode_es 테이블을 json 형식이 파일로 export 실행


#### 3) json 파일 > es bulk 적재 형식 변환 (by: python)

```python
import json

with open('zipcode_es_202411262258.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

bulk_data = []
for idx, item in enumerate(data, start=1):
    action = {
        "index": {
            "_index": "zipcode_es",
            "_id": idx
        }
    }
    bulk_data.append(json.dumps(action, ensure_ascii=False))
    bulk_data.append(json.dumps(item, ensure_ascii=False))

with open('zipcode_es_bulk.json', 'w', encoding='utf-8') as f:
    f.write('\n'.join(bulk_data))
```

#### 4) es 에 index 생성 (index: zipcode_es)

```curl
curl -X PUT "https://es01:9280/zipcode_es" -k -u elastic:trustno1 -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "sido": { "type": "keyword" },
      "sigungu": { "type": "keyword" }
    }
  }
}'
```

#### 5) es 에 적재 (방식: bulk)

```curl
curl -X POST "https://es01:9280/zipcode_es/_bulk" -H 'Content-Type: application/json' --data-binary @files/zipcode_es_bulk.json -k -u elastic:trustno1
```

#### 6) es 적재 데이터 확인

```curl
curl -X GET "https://es01:9280/zipcode_es/_search?pretty=true&q=*:*" -k -u elastic:trustno1
```


#### 7) kibana 에서 data view 추가

- `Home` > `Management` 메뉴 클릭 > Kibana `Data Views` 메뉴 클릭
- `Create data view` 버튼 클릭
- `zipcode_es` 추가

#### 8) kibana 에서 Pie Chart 생성하기

- `Home` > Analytics `Visualize Library` 메뉴 클릭
- 좌측 상단 콤보박스 `zipcode_es` 선택
- Buckets `Add` 클릭 > `Split slices` 선택 > Aggregation `Terms` 선택 > Field `sido` 선택 > 우측 하단 `Update` 버튼 클릭
