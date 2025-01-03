package spring.auth.common.security;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import spring.custom.common.enumcode.Role;
import spring.custom.common.vo.MemberVo;

@Getter
public class SocialOauthAttribute {
  
  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String oauth2Id;
  private String registrationId;
  private String email;
  private String nickname;
  private String picture;
  
  @Builder
  public SocialOauthAttribute(Map<String, Object> attributes,
      String nameAttributeKey, 
      String oauth2Id, String registrationId, String email, String nickname, String picture) {
    
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
    this.oauth2Id = oauth2Id;
    this.registrationId = registrationId;
    this.email = email;
    this.nickname = nickname;
    this.picture = picture;
  }
  
  public static SocialOauthAttribute of(String registrationId,
      String userNameAttributeName, Map<String, Object> attributes) {
    SocialOauthAttribute result = null;
    if ("google".equals(registrationId)) {
      result = ofGoogle(registrationId, userNameAttributeName, attributes);
    } else if ("kakao".equals(registrationId)) {
      result = ofKakao(registrationId, userNameAttributeName, attributes);
    } else if ("naver".equals(registrationId)) {
      result = ofNaver(registrationId, userNameAttributeName, attributes);
    }
    return result;
  }
  
  private static SocialOauthAttribute ofGoogle(String registrationId,
      String userNameAttributeName, Map<String, Object> attributes) {
    return SocialOauthAttribute.builder()
            .oauth2Id((String) attributes.get("sub"))
            .registrationId(registrationId)
            .email((String) attributes.get("email"))
            .nickname((String) attributes.get("name"))
            .picture((String) attributes.get("picture"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }
  
  private static SocialOauthAttribute ofKakao(String registrationId,
      String userNameAttributeName, Map<String, Object> attributes) {
    // properties [nickname, profile_image, thumbnail_image]
    @SuppressWarnings("unchecked")
    Map<String, Object> properties = (Map<String, Object>) attributes.get("properties"); 
    // kakao_account [profile_nickname_needs_agreement, profile_image_needs_agreement, properties ... ]
    return SocialOauthAttribute.builder()
            .oauth2Id(((Long) attributes.get("id")).toString())
            .registrationId(registrationId)
            //.email((String) attributes.get("email")) // kakao 는 사이트 인증이되어야 email 제공함
            .nickname((String) properties.get("nickname"))
            .picture((String) properties.get("profile_image"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }
  
  private static SocialOauthAttribute ofNaver(String registrationId,
      String userNameAttributeName, Map<String, Object> attributes) {
    // id=nwtYEDZWgcIBeXRpkgYf61KBwAESLFvPftUDI5mlyaI, nickname=mgk, profile_image=https://ssl.pstatic.net/static/pwe/address/img_profile.png,
    // age, gender, mobile, mobile_e164, name, birthday, birthyear
    @SuppressWarnings("unchecked")
    Map<String, Object> response = (Map<String, Object>) attributes.get("response"); 
    return SocialOauthAttribute.builder()
            .oauth2Id((String) response.get("id"))
            .registrationId(registrationId)
            .nickname((String) response.get("nickname"))
            .email((String) response.get("email"))
            .picture((String) response.get("profile_image"))
            .attributes(attributes)
            .nameAttributeKey(userNameAttributeName)
            .build();
  }
  
  public MemberVo toVo() {
    return MemberVo.builder()
        .registrationId(registrationId)
        .oauth2Id(oauth2Id)
        .email(email)
        .nickname(nickname)
        .build();
  }
  
  //public UserEntity toEntity() {
  //  return UserEntity.builder()
  //      .id(Uuid.create())
  //      .email(email)
  //      .nickname(nickname)
  //      .picture(picture)
  //      .role(Role.ROLE_USER)
  //      .lockedYn(YN.N)
  //      .dormantYn(YN.N)
  //      .passwordExpireDate(LocalDateTime.now().plusDays(90))
  //      .oauth2Id(oauth2Id)
  //      .registrationId(registrationId)
  //      .createDate(LocalDateTime.now())
  //      .modifyDate(LocalDateTime.now())
  //      .build();
  //}
  
}
