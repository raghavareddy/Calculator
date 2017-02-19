package calculator;

import calculator.operator.AddOperator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;

public class OperatorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testExpressionWithDivisionByZero() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Operands mismatch for Add on 200. Expected 2 but found 1");
        AddOperator addOperator = new AddOperator();
        ArrayList<String> operands = new ArrayList<String>();
        operands.add("200");
        addOperator.setOperands(operands);
        addOperator.apply(new HashMap<String, Double>());
    }
}
