package spring.custom.common.enumcode;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface Code {
  
  String value() default "";
  
}
