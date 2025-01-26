package spring.custom.api.entity.spec;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import spring.custom.api.entity.FundMstEntity;

public class FundMstSpecification {

  // fundFnm
  public static Specification<FundMstEntity> hasFundFnm(String fundFnm) {
    return (Root<FundMstEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteria) -> 
    fundFnm != null 
        ? criteria.like(
            criteria.lower(root.get("fundFnm")),
            fundFnm.toLowerCase() + "%")
        : null;
  }
  
}
