INSERT INTO au_manager (
  id,
  login_id, login_pwd,
  roles, mgr_name, enable_yn, locked_yn,
  pwd_exp_date,
  create_id, modify_id
) VALUES (
  (select 1||TO_CHAR(NOW(), 'YYYYMM') || LPAD(NEXTVAL('sq_user_id')||'', 9, '0')),
  'myeonggu.kim@kakao.com', '$2a$10$Bt6i86KPki5d2F0a8qvLTu8amelkSknno8U5iYJqaS0RDnI34TqIq',
  'ADMIN,MANAGER', '김매니저', 'Y', 'N',
  CURRENT_DATE + INTERVAL '90 day',
  (select 1||TO_CHAR(NOW(), 'YYYYMM') || LPAD(CURRVAL('sq_user_id')||'', 9, '0')),
  (select 1||TO_CHAR(NOW(), 'YYYYMM') || LPAD(CURRVAL('sq_user_id')||'', 9, '0'))
);



INSERT INTO au_client (
  id,
  contact_name, contact_email,
  create_id, modify_id
) VALUES(
  (select 3||TO_CHAR(NOW(), 'YYYYMM') || LPAD(NEXTVAL('sq_user_id')||'', 9, '0')),
  '홍길동', 'hi@mgkim.net',
  (select 3||TO_CHAR(NOW(), 'YYYYMM') || LPAD(CURRVAL('sq_user_id')||'', 9, '0')),
  (select 3||TO_CHAR(NOW(), 'YYYYMM') || LPAD(CURRVAL('sq_user_id')||'', 9, '0'))
);

INSERT INTO au_client (
  id,
  contact_name, contact_email,
  create_id, modify_id
) VALUES(
  (select 3||TO_CHAR(NOW(), 'YYYYMM') || LPAD(NEXTVAL('sq_user_id')||'', 9, '0')),
  '김철수', 'lislroow@daum.net',
  (select 3||TO_CHAR(NOW(), 'YYYYMM') || LPAD(CURRVAL('sq_user_id')||'', 9, '0')),
  (select 3||TO_CHAR(NOW(), 'YYYYMM') || LPAD(CURRVAL('sq_user_id')||'', 9, '0'))
);


/*
INSERT INTO token (token_id, id, client_ip, use_yn, token) VALUES(
  'openapi:0d7bb0817c3442628683ac42efb02e00',
  (select TO_CHAR(NOW(), 'YYYYMM') || LPAD(CURRVAL('sq_user_id')||'', 5, '0')),
  '::1',
  'Y',
  'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJtYXJrZXQuZGV2ZWxvcC5uZXQiLCJzdWIiOiJtZ2tpbS5uZXRAZ21haWwuY29tIiwiYXR0cmlidXRlcyI6eyJyb2xlIjoiUk9MRV9VU0VSIiwiaXAiOiIxOTIuMTY4LjEuMTgzIiwidXNlckFnZW50IjoiTW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMS4wLjAuMCBTYWZhcmkvNTM3LjM2IiwiZW1haWwiOiJtZ2tpbS5uZXRAZ21haWwuY29tIn0sInVzZXJUeXBlIjoibWVtYmVyIiwiZXhwIjoxNzM2MzkyNjU3fQ.LalBDA_NFc4EqGxFd5AnPgqDnGFj6YEBR2O9poHG3H3DmPbqhEqF6rcXwql2HweiV5McwFapiXGhzlnJYIg2iA-goBECcrOXfZKO6kRQ5ehaUaGhtDGD-MC7JTvDzGUPuxevEHgd6gLVwsWWjfErDUdac4bMYgxxLOx7uvcgMGYPnTAyVUbWso9hZsbZXI5D10xhn-1QnwNkIqidIHMMHbX7DRnVrslpMBYlwKM-UgWwPWHtXh1daXDacPrfqfiMN4MJq7gxnda6GxSBoLwfGpIluhWeCUE766RIfSqx-BgRHVy2k5moyBP7Y_ZcrQ6oWh__bgYX1rcUsUTRuyCTow'
  );
*/
