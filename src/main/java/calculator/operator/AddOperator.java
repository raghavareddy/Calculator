package calculator.operator;

import java.util.Map;

public class AddOperator extends BaseOperator {

    public double apply(Map<String, Double> contextValues) throws Exception {
        super.apply("Add", 2);
        return getValue(operands.get(0), contextValues) + getValue(operands.get(1), contextValues);
    }
}
