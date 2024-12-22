package spring.sample.app.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.app.vo.CardPaymentVo;
import spring.sample.app.vo.CardVo;

@Mapper
public interface CardStatsDao {

  Optional<CardVo> selectIssueDateByCardNo(@Param("cardNo") String cardNo);
  
  List<CardPaymentVo> selectPaymentsByCardNo(@Param("cardNo") String cardNo);
  
}
