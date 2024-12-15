package spring.sample.common.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.sample.common.dto.ResponseDto;
import spring.sample.common.dto.RuntimeResDto;
import spring.sample.common.runtime.ClasspathLibs;
import spring.sample.common.vo.BootJarVo;

@RestController
@RequiredArgsConstructor
public class RuntimeController {
  
  @GetMapping("/mybatis/v1/runtime/jars")
  public ResponseDto<RuntimeResDto.BootJar> findJars() {
    List<BootJarVo> result = ClasspathLibs.getBootJars();
    RuntimeResDto.BootJar resDto = new RuntimeResDto.BootJar();
    resDto.setList(result);
    return ResponseDto.body(resDto);
  }
}
