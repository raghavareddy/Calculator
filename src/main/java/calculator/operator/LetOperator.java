package calculator.operator;

import java.util.HashMap;
import java.util.Map;

public class LetOperator extends BaseOperator {

    public double apply(Map<String, Double> contextValues) throws Exception {
        super.apply("Let", 3);
        Map<String, Double> contextValuesCopy = new HashMap<String, Double>(contextValues);
        contextValuesCopy.put(operands.get(0), getValue(operands.get(1), contextValues));
        return getValue(operands.get(2), contextValuesCopy);
    }
}
