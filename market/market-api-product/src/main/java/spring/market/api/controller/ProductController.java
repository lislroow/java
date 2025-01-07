package spring.market.api.controller;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import spring.custom.common.annotation.Login;
import spring.custom.common.annotation.UserInfo;
import spring.custom.common.enumcode.ERROR_CODE;
import spring.custom.common.exception.AppException;
import spring.market.api.dto.ProductReqDto;
import spring.market.api.dto.ProductResDto;
import spring.market.api.service.ProductService;
import spring.market.common.vo.UserVo;

@RestController
@RequiredArgsConstructor
public class ProductController {
  
  private final ProductService productService;
  
  @GetMapping("/v1/list")
  //@Login
  //public ResponseEntity<ProductResDto.ItemListRes> list(@UserInfo UserVo user) { // [O]
  //public ResponseEntity<ProductResDto.ItemListRes> list(UserVo user) { // [X]
  public ResponseEntity<ProductResDto.ItemListRes> list() {
    ProductResDto.ItemListRes resDto = new ProductResDto.ItemListRes();
    resDto.setList(productService.list());
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/{productId}")
  public ResponseEntity<ProductResDto.ItemRes> detail(
      @PathVariable Integer productId) {
    ProductResDto.ItemRes resDto = productService.detail(productId);
    return ResponseEntity.ok(resDto);
  }
  
  @PutMapping("/v1/products")
  @Login
  public ResponseEntity<ProductResDto.ItemListRes> saveProducts(@UserInfo UserVo user,
      @RequestBody ProductReqDto.ItemListReq request) {
    ProductResDto.ItemListRes resDto = new ProductResDto.ItemListRes();
    resDto.setList(productService.saveProductList(request));
    return ResponseEntity.ok(resDto);
  }
  
  @PutMapping(value = "/v1/product",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  @Login
  public ResponseEntity<ProductResDto.ItemRes> saveProduct(
      @UserInfo UserVo user,
      @RequestPart(name = "req") ProductReqDto.ItemReq request,
      @RequestPart(name = "imgThumb", required = false) MultipartFile imgThumb
      ) {
    ProductResDto.ItemRes resDto = null;
    try {
      resDto = productService.saveProduct(request, imgThumb);
    } catch (IllegalStateException|IOException e) {
      throw new AppException(ERROR_CODE.E903);
    }
    return ResponseEntity.ok(resDto);
  }
  
  @DeleteMapping(value = "/v1/product",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  @Login
  public ResponseEntity<ProductResDto.ItemRes> deleteProduct(
      @UserInfo UserVo user,
      @RequestBody ProductReqDto.ItemReq request) {
    ProductResDto.ItemRes resDto = productService.deleteProduct(request);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/add")
  public ResponseEntity<Serializable> add() {
    productService.add();
    return ResponseEntity.ok().build();
  }
}
