package spring.custom.api.entity.spec;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import spring.custom.api.entity.SatelliteEntity;

public class SatelliteSpecification {
  
  // name
  public static Specification<SatelliteEntity> hasName(String name) {
    return (Root<SatelliteEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteria) -> 
      name != null 
        ? criteria.like(
            criteria.lower(root.get("name")),
            name.toLowerCase() + "%")
        : null;
  }

}
