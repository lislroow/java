package spring.custom.api.func;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public interface CalculatorFunc {
  
  static List<Double> fibonacci(int n) {
    return fibonacci(n, new ArrayList<>());
  }
  
  static List<Double> fibonacci(int n, List<Double> values) {
    if (n <= 1) {
      return DoubleStream.of(0).boxed().collect(Collectors.toList());
    } else if (n <= 2) {
      return DoubleStream.of(0, 1).boxed().collect(Collectors.toList());
    }
    
    if (values == null) {
      values = new ArrayList<>();
    }
    
    if (values.size() < n) {
      values = fibonacci(n-1, values);
    }
    
    values.add(values.get((n-1)-2) + values.get((n-1)-1));
    return values;
  }
  
  static double fibonacci_A(int n) {
    if (n <= 1) {
      return n;
    }
    return fibonacci_A(n-1) + fibonacci_A(n - 2);
  }
  
}
