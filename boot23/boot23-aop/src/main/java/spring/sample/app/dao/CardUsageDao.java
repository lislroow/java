package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.app.vo.CardUsageVo;

@Mapper
public interface CardUsageDao {

  List<CardUsageVo> selectCardUsageList(
      @Param("name") String name,
      @Param("cardNo") String cardNo);
}
