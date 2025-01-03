package spring.market.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.ToString;
import spring.market.api.entity.Delivery;

public class DeliveryResDto implements Serializable {
  private static final long serialVersionUID = 1L;
  
  private DeliveryResDto() {}
  
  @Data
  public static class StatusRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer orderId;
    private Integer orderItemId;
    
    private String receiverName;
    private String receiverAddress;
    
    @JsonIgnoreProperties({"delivery"})
    @ToString.Exclude
    private DeliveryResDto.OrderItemRes orderItem;
    
    public static DeliveryResDto.StatusRes create(Delivery entity) {
      DeliveryResDto.StatusRes res = new DeliveryResDto.StatusRes();
      res.id = entity.getId().getId();
      res.orderId = entity.getId().getOrderId();
      res.orderItemId = entity.getId().getOrderItemId();
      res.receiverName = entity.getReceiverName();
      res.receiverAddress = entity.getReceiverAddress();
      return res;
    }
  }
  
  @Data
  public static class OrderItemRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer orderQty;
    private DeliveryResDto.ProductRes product;
  }
  
  @Data
  public static class ProductRes implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String imgThumbUrl;
  }
}
