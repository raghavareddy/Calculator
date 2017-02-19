package calculator.common;

import org.apache.log4j.Logger;

import java.util.Stack;

public class ExpressionValidator {

    private static Logger logger = Logger.getLogger(ExpressionValidator.class);

    public static void validateExpression(String expression) throws Exception {
        validateBraces(expression);
        //TODO - Atleast one operator and end should be ). String before ( should be operator
    }

    private static void validateBraces(String expression) throws Exception {
        Stack<Character> stack = new Stack<Character>();
        for (char aChar : expression.toCharArray()) {
            if (aChar == Constants.openBrace) {
                stack.push(Constants.openBrace);
            } else if (aChar == Constants.closedBrace) {
                stack.pop();
            }
        }
        if (!stack.isEmpty()) {
            logger.error("Invalid expression. Braces not closed properly.");
            throw new Exception("Invalid expression. Braces not closed properly");
        }
    }
}


