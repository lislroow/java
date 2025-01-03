package spring.custom.common.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.dto.ResponseDto;
import spring.custom.dto.RuntimeResDto;
import spring.custom.initial.Classpath;

@RestController
@RequiredArgsConstructor
public class RuntimeController {
  
  @GetMapping("/v1/runtime/jars")
  public ResponseDto<RuntimeResDto.BootJar> findJars() {
    List<Classpath.BootJarVo> result = Classpath.getBootJars();
    RuntimeResDto.BootJar resDto = new RuntimeResDto.BootJar();
    resDto.setList(result);
    return ResponseDto.body(resDto);
  }
}
