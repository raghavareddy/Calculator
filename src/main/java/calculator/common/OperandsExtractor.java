package calculator.common;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class OperandsExtractor {

    private static Logger logger = Logger.getLogger(OperandsExtractor.class);

    public static List<String> getOperands(String expression) throws Exception {
        List<String> operands = new ArrayList<String>();
        expression = expression.trim();
        if (expression.isEmpty()) {
            return operands;
        }
        int indexOfComma = expression.indexOf(',');
        if (indexOfComma == -1) {
            operands.add(expression);
            return operands;
        }
        int indexOfOpenBrace = expression.indexOf(Constants.openBrace);
        if (indexOfOpenBrace == -1) {
            int indexOfClosedBrace = expression.indexOf(Constants.closedBrace);
            if (indexOfClosedBrace != -1) {
                logger.error("Unable to split operands. Expression contains closed brace with out its matching open brace: " + expression);
                throw new Exception("Unable to split operands. Expression contains closed brace with out its matching open brace: " + expression);
            }
            String[] subStrings = expression.split(",");
            for(String subString: subStrings){
                operands.add(subString.trim());
            }
        } else {
            if (indexOfComma < indexOfOpenBrace) {
                String operand = expression.substring(0, indexOfComma);
                operands.add(operand.trim());
                operands.addAll(getOperands(expression.substring(indexOfComma + 1)));
            } else {
                extractNestedOperands(operands, expression, indexOfOpenBrace);
            }
        }
        return operands;
    }

    private static void extractNestedOperands(List<String> operands, String expression, int indexOfOpenBrace) throws Exception {
        Stack<Character> stack = new Stack<Character>();
        stack.push(expression.charAt(indexOfOpenBrace));
        StringBuilder sb = new StringBuilder(expression.substring(0, indexOfOpenBrace + 1));
        char[] chars = expression.toCharArray();
        for (int index = indexOfOpenBrace + 1; index < chars.length; index++) {
            boolean operandExtracted = appendOrExtractOperand(operands, stack, sb, chars[index]);
            if (operandExtracted) {
                String substring = expression.substring(index+1).trim();
                if(!substring.isEmpty()) {
                    if (substring.charAt(0) == ',') {
                        operands.addAll(getOperands(substring.substring(1)));

                    } else {
                        operands.addAll(getOperands(substring));
                    }
                }
                break;
            } else {
                if(index == chars.length-1){
                    logger.error("Braces not closed properly. Unable to extract operands");
                    throw new Exception("Braces not closed properly. Unable to extract operands");
                }
            }
        }
    }

    private static boolean appendOrExtractOperand(List<String> operands, Stack<Character> stack, StringBuilder sb, char aChar) {
        boolean operandExtracted = false;
        switch (aChar) {
            case Constants.closedBrace:
                stack.pop();
                sb.append(Constants.closedBrace);
                if (stack.isEmpty()) {
                    operands.add(sb.toString().trim());
                    operandExtracted = true;
                }
                break;
            case Constants.openBrace:
                stack.push(Constants.openBrace);
                sb.append(Constants.openBrace);
                break;
            default:
                sb.append(aChar);
        }
        return operandExtracted;
    }
}
