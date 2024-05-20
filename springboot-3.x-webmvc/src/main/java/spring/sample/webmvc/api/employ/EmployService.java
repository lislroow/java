package spring.sample.webmvc.api.employ;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import spring.sample.webmvc.api.employ.dto.EmployRegREQ;

@Service
@Slf4j
@Validated
public class EmployService {
  
  public EmployRegREQ save(@Valid EmployRegREQ param) {
    log.info("param = {}", param);
    return param;
  }
}
