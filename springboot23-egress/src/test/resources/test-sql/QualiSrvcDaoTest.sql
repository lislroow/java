INSERT INTO qv_service (
      id,
      srvc_name
    ) VALUE (
      '0001',
      '국가유공자 자격여부'
    )
    ON DUPLICATE KEY UPDATE
      id = '0001';

INSERT 
    INTO qv_service_data (
      id,
      data_name_ko,
      data_name_en,
      req_res,
      data_type,
      qv_service_id
    ) VALUES (
      1,
      '주민등록번호',
      'juminNo',
      '1',
      '2',
      '0001'
    )
    ON DUPLICATE KEY UPDATE
      id = 1;

INSERT 
    INTO qv_service_data (
      id,
      data_name_ko,
      data_name_en,
      req_res,
      data_type,
      qv_service_id
    ) VALUES (
      2,
      '이름',
      'resName',
      '2',
      '4',
      '0001'
    )
    ON DUPLICATE KEY UPDATE
      id = 2;

INSERT 
    INTO qv_service_data (
      id,
      data_name_ko,
      data_name_en,
      req_res,
      data_type,
      qv_service_id
    ) VALUES (
      3,
      '주민등록번호',
      'resSecrNum',
      '2',
      '2',
      '0001'
    )
    ON DUPLICATE KEY UPDATE
      id = 3;