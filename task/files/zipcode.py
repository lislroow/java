import mariadb
from elasticsearch import Elasticsearch, helpers

def fetch_data_from_mariadb():
    connection = mariadb.connect(
        host='rocky8-market',
        user='mkuser',
        password='1',
        database='develop'
    )
    cursor = connection.cursor()
    query = """
    SELECT sido, sigungu
    FROM zipcode
    GROUP BY sido, sigungu
    """
    cursor.execute(query)
    results = cursor.fetchall()
    cursor.close()
    connection.close()

    return [{"sido": row[0], "sigungu": row[1]} for row in results]

def create_es_index(es_client, index_name):
    index_body = {
        "mappings": {
            "properties": {
                "sido": {"type": "keyword"},
                "sigungu": {"type": "keyword"}
            }
        }
    }
    if es_client.indices.exists(index=index_name):
        print(f"Index '{index_name}' already exists. Deleting and recreating...")
        es_client.indices.delete(index=index_name)
    es_client.indices.create(index=index_name, body=index_body)
    print(f"Index '{index_name}' created successfully.")

def bulk_insert_to_es(es_client, index_name, data):
    actions = [
        {
            "_index": index_name,
            "_source": {
                "sido": row["sido"],
                "sigungu": row["sigungu"]
            }
        }
        for row in data
    ]
    helpers.bulk(es_client, actions)
    print(f"Bulk insert completed: {len(actions)} records inserted.")

def main():
    print("Fetching data from MariaDB...")
    data = fetch_data_from_mariadb()
    print(f"Data fetched: {len(data)} rows")

    es_client = Elasticsearch(
        hosts=["https://es01:9280"],
        api_key="WGhGdWJKTUI3eW5rR1YwMFZSeTI6T25MNVp4blpSYVduYWt5R1JXNjhBZw=="
    )
    
    index_name = "zipcode"
    print("Creating Elasticsearch index...")
    create_es_index(es_client, index_name)

    print("Inserting data into Elasticsearch...")
    bulk_insert_to_es(es_client, index_name, data)

    print("All tasks completed successfully.")

if __name__ == "__main__":
    main()
