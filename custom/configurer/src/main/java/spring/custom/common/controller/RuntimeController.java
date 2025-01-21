package spring.custom.common.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.dto.RuntimeDto;
import spring.custom.initial.Classpath;

@RestController
@RequiredArgsConstructor
public class RuntimeController {
  
  @GetMapping("/v1/runtime/jars")
  public ResponseEntity<RuntimeDto.BootJarRes> findJars() {
    List<Classpath.BootJarVo> resultVo = Classpath.getBootJars();
    RuntimeDto.BootJarRes resDto = new RuntimeDto.BootJarRes();
    resDto.setList(resultVo);
    return ResponseEntity.ok(resDto);
  }
}
