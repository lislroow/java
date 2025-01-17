package spring.market.api.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

public class ProductReqDto {

  @Data
  @JsonIgnoreProperties({"createTime", "modifyTime"})
  public static class ItemReq {
  
    private Integer id;
    private String name;
    private String imgThumbUrl;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createTime;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime modifyTime;
    
    private List<ItemCategory> categoryInfoList;
    
    private ItemQty qtyInfo;
  }
  
  @Data
  public static class ItemListReq {
    private List<ProductReqDto.ItemReq> list;
  }
  
  @Data
  public class ItemCategory {
    private String id;
    private String name;
  }
  
  @Data
  public class ItemQty {
    private Integer totalQty;
    private Integer soldQty;
  }
}
