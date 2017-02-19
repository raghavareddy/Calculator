package calculator;

import calculator.common.ExpressionValidator;
import calculator.common.OperandsExtractor;
import calculator.common.OperatorExtractor;
import calculator.operator.BaseOperator;
import org.apache.log4j.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculator {

    private static Logger logger = Logger.getLogger(Calculator.class);
    private static Calculator calculator;

    private Calculator(Level logLevel) {
        setupLogger(logLevel);
    }

    public static Calculator getInstance() {
        if (calculator == null) {
            calculator = new Calculator(Level.ERROR);
        }
        return calculator;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please run this program with arguments.");
            System.out.println("1. First argument is log level. Ex: DEBUG");
            System.out.println("2. Second is expression. Ex: add(2,let(a,let(b,10,mul(b,2)),add(a,a)))");
            return;
        }
        try {
            Calculator calculator = new Calculator(Level.toLevel(args[0]));
            String expression = args[1];
            ExpressionValidator.validateExpression(expression);
            logger.info("Evaluating expression: " + expression);
            System.out.println(calculator.evaluateExpression(expression, new HashMap<String, Double>()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Double evaluateExpression(String input, Map<String, Double> contextValues) throws Exception {
        /*if (contextValues.containsKey(input)) {
            return contextValues.get(input);
        }*/
        BaseOperator operator = OperatorExtractor.getOperator(input);
        String subExpression = OperatorExtractor.getSubExpression(input);
        List<String> operands = OperandsExtractor.getOperands(subExpression);
        operator.setOperands(operands);
        return operator.apply(contextValues);
    }

    private void setupLogger(Level logLevel) {
        Appender console = new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN));
        Logger root = Logger.getRootLogger();
        root.addAppender(console);
        root.setLevel(logLevel);
    }
}
