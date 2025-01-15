package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.CommonCodeVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface CommonCodeMngDao {
  
  PageResponse<CommonCodeVo.CodeGroupVo> searchCodeGroups(
      PageRequest pageRequest, CommonCodeVo.SearchCodeGroupVo searchVo);
  
  List<CommonCodeVo.CodeVo> searchCodes(CommonCodeVo.SearchCodeVo searchVo);
  
}
