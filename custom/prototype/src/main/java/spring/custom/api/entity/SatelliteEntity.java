package spring.custom.api.entity;

import java.math.BigInteger;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.Column;

//import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import spring.custom.common.entity.AuditEntity;

@Entity
@Table(name = "pt_satellite")
@SQLDelete(sql = "UPDATE pt_satellite SET deleted = true WHERE id = ?")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SatelliteEntity extends AuditEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Double radius;
  private Double mass;
  private BigInteger distanceFromPlanet;
  private Double orbitalEccentricity;
  //private String memo;
  @Column(insertable = false)
  private Boolean deleted;
  
  @ManyToOne
  @JoinColumn(name = "planet_id")
  private PlanetEntity planet;
}
