package calculator.operator;

import java.util.Map;

public class DivisionOperator extends BaseOperator {

    public double apply(Map<String, Double> contextValues) throws Exception {
        super.apply("Division", 2);
        Double denominator = getValue(operands.get(1), contextValues);
        if (denominator != 0) {
            return getValue(operands.get(0), contextValues) / denominator;
        } else {
            String input = String.join(",", operands);
            logger.error("Unable to evaluate expression. Expression contains division with 0 : " + input);
            throw new Exception("Unable to evaluate expression. Expression contains division with 0 : " + input);
        }
    }
}
