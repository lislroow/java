DROP TABLE IF EXISTS sy_error_log CASCADE ;
CREATE TABLE IF NOT EXISTS sy_error_log
(
  id SERIAL,
  tx_time TIMESTAMP DEFAULT NOW(),
  trace_id VARCHAR(32),
  span_id VARCHAR(16),
  class_name VARCHAR(255),
  stacktrace VARCHAR(255),
  hostname VARCHAR(16),
  host_ip VARCHAR(28),
  service_name VARCHAR(20),
  client_ip VARCHAR(28),
  request_body TEXT,
  request_param TEXT,
  request_uri TEXT,
  create_time TIMESTAMP DEFAULT NOW(),
  modify_time TIMESTAMP DEFAULT NOW(),
  create_id VARCHAR(16) DEFAULT '1',
  modify_id VARCHAR(16) DEFAULT '1',
  PRIMARY KEY (id)
);
