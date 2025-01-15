package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.CommonCodeVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface CommonCodeMngDao {
  
  // feature: 파라미터가 1개 일 경우에는 flat 형식으로 binding 변수가 생성되는데,
  // 2개 이상이고, @Param 이 없을 경우에는 인자의 변수명으로 binding 변수가 생성됨
  // 이를 개선하고자 여러 방안을 검토함
  //  1) ThreadLocal 사용 > 해당 sql 에 binding 되는 페이징 변수인지 명확하지가 않음
  //  2) PagingInterceptor 에서 'PageRequest + 일반Vo' 일 경우, Map 으로 파라미터를 전달하지 않고 단일 객체로 처리되도록 수정 
  //     org.apache.ibatis.reflection.ParamNameResolver.getNamedParams(Object[]) 에서 처리 과정 분석
  PageResponse<CommonCodeVo.CodeGroupVo> searchCodeGroups(
      PageRequest pageRequest, CommonCodeVo.SearchCodeGroupVo searchVo);
  
  List<CommonCodeVo.CodeVo> searchCodes(CommonCodeVo.SearchCodeVo searchVo);
  
}
