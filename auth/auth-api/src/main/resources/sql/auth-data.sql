INSERT INTO manager (
  id,
  login_id, mgr_name, disabled_yn, locked_yn,
  pwd_exp_time,
  create_id, modify_id
) VALUES (
  (select TO_CHAR(NOW(), 'YYYYMMDDHH') || LPAD(NEXTVAL('sq_user_id')||'', 5, '0')),
  'myeonggu.kim@kakao.com', '김매니저', 'N', 'N',
  now() + INTERVAL '1 day',
  (select TO_CHAR(NOW(), 'YYYYMMDDHH') || LPAD(CURRVAL('sq_user_id')||'', 5, '0')),
  (select TO_CHAR(NOW(), 'YYYYMMDDHH') || LPAD(CURRVAL('sq_user_id')||'', 5, '0')));



INSERT INTO openapi_user (id, login_id, user_name, create_id, modify_id) VALUES(
  (select TO_CHAR(NOW(), 'YYYYMMDDHH') || LPAD(NEXTVAL('sq_user_id')||'', 5, '0')),
  'lislroow@daum.net', '김길동',
  (select TO_CHAR(NOW(), 'YYYYMMDDHH') || LPAD(CURRVAL('sq_user_id')||'', 5, '0')),
  (select TO_CHAR(NOW(), 'YYYYMMDDHH') || LPAD(CURRVAL('sq_user_id')||'', 5, '0')));


/*
INSERT INTO token (token_id, id, client_ip, use_yn, token) VALUES(
  'openapi:0d7bb0817c3442628683ac42efb02e00',
  (select TO_CHAR(NOW(), 'YYYYMMDDHH') || LPAD(CURRVAL('sq_user_id')||'', 5, '0')),
  '::1',
  'Y',
  'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJtYXJrZXQuZGV2ZWxvcC5uZXQiLCJzdWIiOiJtZ2tpbS5uZXRAZ21haWwuY29tIiwiYXR0cmlidXRlcyI6eyJyb2xlIjoiUk9MRV9VU0VSIiwiaXAiOiIxOTIuMTY4LjEuMTgzIiwidXNlckFnZW50IjoiTW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzEzMS4wLjAuMCBTYWZhcmkvNTM3LjM2IiwiZW1haWwiOiJtZ2tpbS5uZXRAZ21haWwuY29tIn0sInVzZXJUeXBlIjoibWVtYmVyIiwiZXhwIjoxNzM2MzkyNjU3fQ.LalBDA_NFc4EqGxFd5AnPgqDnGFj6YEBR2O9poHG3H3DmPbqhEqF6rcXwql2HweiV5McwFapiXGhzlnJYIg2iA-goBECcrOXfZKO6kRQ5ehaUaGhtDGD-MC7JTvDzGUPuxevEHgd6gLVwsWWjfErDUdac4bMYgxxLOx7uvcgMGYPnTAyVUbWso9hZsbZXI5D10xhn-1QnwNkIqidIHMMHbX7DRnVrslpMBYlwKM-UgWwPWHtXh1daXDacPrfqfiMN4MJq7gxnda6GxSBoLwfGpIluhWeCUE766RIfSqx-BgRHVy2k5moyBP7Y_ZcrQ6oWh__bgYX1rcUsUTRuyCTow'
  );
*/
