package spring.component.common.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.component.common.dto.ResponseDto;
import spring.component.common.dto.RuntimeResDto;
import spring.component.common.runtime.ClasspathLibs;
import spring.component.common.vo.BootJarVo;

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
