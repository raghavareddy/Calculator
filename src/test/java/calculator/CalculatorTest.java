package calculator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;

public class CalculatorTest {

    private final double EPSILON = 0.00001;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testTwoOperandSimpleExtractor() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("add(2,3)", new HashMap<String, Double>());
        assertTrue(Math.abs(value - 5) < EPSILON);
    }

    @Test
    public void testTwoOperandSimpleComplexExtractor() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("add(add(2,3),4)", new HashMap<String, Double>());
        assertTrue(Math.abs(value - 9) < EPSILON);
    }

    @Test
    public void testTwoOperandComplexExtractor() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("add(add(2,3),add(4,5))", new HashMap<String, Double>());
        assertTrue(Math.abs(value - 14) < EPSILON);
    }

    @Test
    public void testLetOperandComplexExtractor() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))", new HashMap<String, Double>());
        assertTrue(Math.abs(value - 40) < EPSILON);
    }

    @Test
    public void testMultiplicationWhichInvolveZero() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("let(b, 2, let(a, 0, mul(a, b)))", new HashMap<String, Double>());
        assertTrue(Math.abs(value - 0) < EPSILON);
    }

    @Test
    public void testExpressionWithoutMatchingBraces() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Unable to split operands. Expression contains closed brace with out its matching open brace: 2,)");
        Calculator.getInstance().evaluateExpression("mul(2,)7", new HashMap<String, Double>());
    }

    @Test
    public void testExpressionWhichContainsVariableWhoseScopeExpired() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Unable to extract value or operator from input: b");
        Calculator.getInstance().evaluateExpression("mul(2,let(a,div(7,let(b,3,add(b,b))),mul(a,b)))", new HashMap<String, Double>());
    }

    @Test
    public void testSimpleExpressionWithAllOperators() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("mul(2,let(a,div(6,2),add(a,2)))", new HashMap<String, Double>());
        assertTrue(Math.abs(value - 10) < EPSILON);
    }

    @Test
    public void testSimpleExpressionWithNegativeNumber() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("add(3, mul(-2, 3))", new HashMap<String, Double>());
        assertTrue(Math.abs(value + 3) < EPSILON);
    }

    @Test
    public void testDivAddMulCombinationWithNegativeNumbers() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("div(add(3, mul(-2, 3)), -3)", new HashMap<String, Double>());
        assertTrue(Math.abs(value - 1) < EPSILON);
    }

    @Test
    public void testWithOutVariableInitialization() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Unable to extract value or operator from input: a");
        Calculator.getInstance().evaluateExpression("div(add(a,mul(-2,3)),-3)", new HashMap<String, Double>());
    }

    @Test
    public void testUnknownOperatorTest() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Unknown operator: did");
        Calculator.getInstance().evaluateExpression("did(add(90,mul(-2,3)),-3)", new HashMap<String, Double>());
    }

    @Test
    public void testExpressionWithDivisionByZero() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Unable to evaluate expression. Expression contains division with 0 : add(5,mul(2,3)),0");
        Calculator.getInstance().evaluateExpression("div(add(5,mul(2,3)),0)", new HashMap<String, Double>());
    }

    @Test
    public void testExpressionWhichContainsoperatorAsVariableName() throws Exception {
        double value = Calculator.getInstance().evaluateExpression("let(add,add(9,mul(-2,3)),mul(add,add))", new HashMap<String, Double>());
        assertTrue(Math.abs(value - 9) < EPSILON);
    }
}