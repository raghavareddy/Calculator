package calculator.operator;

import java.util.Map;

public interface IOperator {

    double apply(Map<String, Double> contextValues) throws Exception;
}
