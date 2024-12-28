package spring.custom.common.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.dto.ResponseDto;
import spring.custom.common.dto.RuntimeResDto;
import spring.custom.common.runtime.ClasspathLibs;
import spring.custom.common.vo.BootJarVo;

@RestController
@RequiredArgsConstructor
public class RuntimeController {
  
  @GetMapping("/v1/runtime/jars")
  public ResponseDto<RuntimeResDto.BootJar> findJars() {
    List<BootJarVo> result = ClasspathLibs.getBootJars();
    RuntimeResDto.BootJar resDto = new RuntimeResDto.BootJar();
    resDto.setList(result);
    return ResponseDto.body(resDto);
  }
}
