package calculator.common;

import calculator.operator.*;
import org.apache.log4j.Logger;

public class OperatorFactory {

    private static Logger logger = Logger.getLogger(OperatorFactory.class);

    public static BaseOperator getOperator(String operator) throws Exception {
        if (operator.equals("add")) {
            return new AddOperator();
        }else if (operator.equals("mul")) {
            return new MultiplyOperator();
        } else if (operator.equals("div")) {
            return new DivisionOperator();
        } else if (operator.equals("let")) {
            return new LetOperator();
        } else {
            logger.error("Unknown operator: " + operator);
            throw new Exception("Unknown operator: " + operator);
        }
    }
}
