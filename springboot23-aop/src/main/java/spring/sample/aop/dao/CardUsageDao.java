package spring.sample.aop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.aop.vo.CardUsageVo;

@Mapper
public interface CardUsageDao {

  List<CardUsageVo> selectCardUsageList(
      @Param("name") String name,
      @Param("cardNo") String cardNo);
}
