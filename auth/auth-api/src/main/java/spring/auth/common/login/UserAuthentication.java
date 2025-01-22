package spring.auth.common.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import lombok.Data;
import spring.custom.common.vo.Principal;

@Data
public class UserAuthentication implements UserDetails {
  
  private static final long serialVersionUID = 2501815366855398147L;
  
  private Principal principal;
  private String password;
  private String roles;
  
  public UserAuthentication(Principal principal, String roles, String password) {
    Assert.hasText(principal.getUserType(), "'userType' must not be empty");
    
    this.principal = principal;
    this.password = password;
    this.roles = roles;
  }
  
  public String getRoles() {
    return this.roles;
  }
  
  // [UserDetails]
  @Override
  public String getPassword() {
    return password;
  }
  @Override
  public String getUsername() {
    return principal.getName();
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles == null ? 
        AuthorityUtils.NO_AUTHORITIES
        : AuthorityUtils.commaSeparatedStringToAuthorityList(this.roles);
  }
  // --[UserDetails]
}
