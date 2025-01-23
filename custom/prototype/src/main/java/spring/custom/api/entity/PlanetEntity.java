package spring.custom.api.entity;

import java.math.BigInteger;
import java.util.List;

//import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.custom.common.entity.AuditEntity;

@Entity
@Table(name = "pt_planet")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanetEntity extends AuditEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Double radius;
  private Double mass;
  private BigInteger distanceFromSun;
  private Double orbitalEccentricity;

  @OneToMany(mappedBy = "planet", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SatelliteEntity> satellites;

}
