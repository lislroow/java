package spring.custom.api.entity.spec;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import spring.custom.api.entity.FundMstEntity;

public class FundMstSpecification {
  
  // fundCd
  public static Specification<FundMstEntity> hasFundCd(String fundCd) {
    return (Root<FundMstEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteria) -> 
      fundCd != null && !"".equals(fundCd) 
        ? criteria.equal(root.get("fundCd"), fundCd)
        : null;
  }

  // fundFnm
  public static Specification<FundMstEntity> hasFundFnm(String fundFnm) {
    return (Root<FundMstEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteria) -> 
      fundFnm != null && !"".equals(fundFnm) 
          ? criteria.like(
              criteria.lower(root.get("fundFnm")),
              fundFnm.toLowerCase() + "%")
          : null;
  }
  
}
