package samples;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class SecurityRoleMain {
  
  public static void main(String[] args) {
    String roles = ", ";
    Collection<String> col = Arrays.stream(roles.split(","))
        .map(String::trim)
        .filter(role -> !role.isEmpty())
        .map(role -> new String("ROLE_" + role))
        .collect(Collectors.toList());
    System.out.println(col);
  }
  
}
