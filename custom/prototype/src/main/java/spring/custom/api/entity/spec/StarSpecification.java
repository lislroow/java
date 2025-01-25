package spring.custom.api.entity.spec;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import spring.custom.api.entity.StarEntity;

public class StarSpecification {
  
  // name
  public static Specification<StarEntity> hasName(String name) {
    return (Root<StarEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteria) -> 
      // 기본형: name != null ? criteria.equal(root.get("name"), name) : null;
      name != null && !"".equals(name)
        ? criteria.like(
            criteria.lower(root.get("name")),
            name.toLowerCase() + "%")
        : null;
  }
  
  // distance
  public static Specification<StarEntity> filterByDistance(
      Double distance, boolean greater) {
    return (root, query, criteria) -> {
      if (distance != null) {
        return greater
            ? criteria.greaterThanOrEqualTo(root.get("distance"), distance)
            : criteria.lessThanOrEqualTo(root.get("distance"), distance);
      }
      return null;
    };
  }
  
  public static Specification<StarEntity> filterByDistanceGreaterThanOrEqualTo(
      Double distance) {
    return (root, query, criteria) -> 
      distance != null 
        ? criteria.greaterThan(root.get("distance"), distance)
        : null;
  }
  
  public static Specification<StarEntity> filterByDistanceLessThanOrEqualTo(
      Double distance) {
    return (root, query, criteria) -> 
      distance != null
        ? criteria.lessThanOrEqualTo(root.get("distance"), distance)
        : null;
  }
  
  // distance - sort
  public static Specification<StarEntity> filterByDistanceWithSorting(
      Double distance, boolean greater) {
    return (root, query, criteria) -> {
      boolean ascending = false;
      if (distance != null) {
        query.orderBy(ascending
            ? criteria.asc(root.get("distance"))
            : criteria.desc(root.get("distance")));
        return greater
            ? criteria.greaterThanOrEqualTo(root.get("distance"), distance)
            : criteria.lessThanOrEqualTo(root.get("distance"), distance);
      }
      return null;
    };
  }
  
  // temperature
  public static Specification<StarEntity> filterByTemperature(
      Integer temperature, boolean greater) {
    return (root, query, criteria) -> {
      if (temperature != null) {
        return greater
            ? criteria.greaterThanOrEqualTo(root.get("temperature"), temperature)
            : criteria.lessThanOrEqualTo(root.get("temperature"), temperature);
      }
      return null;
    };
  }
}
