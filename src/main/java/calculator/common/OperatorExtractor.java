package calculator.common;

import calculator.operator.BaseOperator;
import org.apache.log4j.Logger;

public class OperatorExtractor {

    private static Logger logger = Logger.getLogger(OperatorExtractor.class);

    public static BaseOperator getOperator(String expression) throws Exception {

        int index = expression.indexOf(Constants.openBrace);
        if (index == -1) {
            logger.error("Unable to extract value or operator from input: " + expression);
            throw new Exception("Unable to extract value or operator from input: " + expression);
        }
        String operatorString = expression.substring(0, index);
        logger.info("Found operator: " + operatorString);
        return OperatorFactory.getOperator(operatorString);
    }

    public static String getSubExpression(String input) {
        int index = input.indexOf(Constants.openBrace);
        int lastIndex = input.length() - 1;
        return input.substring(index + 1, lastIndex);
    }
}
