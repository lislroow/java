import json

with open('zipcode_es_202411271044.json', 'r', encoding='utf-8') as f:
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
    f.write('\n'.join(bulk_data) + '\n')
