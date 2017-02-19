package calculator;

import calculator.common.OperandsExtractor;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class OperandsExtractorTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testTwoOperandSimpleExtractor() throws Exception {
        List<String> operands = OperandsExtractor.getOperands("5,3");
        assertEquals(operands.size(), 2);
        assertEquals(operands.get(0), "5");
        assertEquals(operands.get(1), "3");
    }

    @Test
    public void testTwoOperandSimpleComplexExtractor() throws Exception {
        List<String> operands = OperandsExtractor.getOperands("add(2,3),4");
        assertEquals(operands.size(), 2);
        assertEquals(operands.get(0), "add(2,3)");
        assertEquals(operands.get(1), "4");
    }

    @Test
    public void testTwoOperandComplexExtractor() throws Exception {
        List<String> operands = OperandsExtractor.getOperands("add(2,3),add(5,3)");
        assertEquals(operands.size(), 2);
        assertEquals(operands.get(0), "add(2,3)");
        assertEquals(operands.get(1), "add(5,3)");
    }

    @Test
    public void testTwoOperandComplexExtractorWithBraceMismatch() throws Exception {
        exception.expect(Exception.class);
        exception.expectMessage("Braces not closed properly. Unable to extract operands");
        OperandsExtractor.getOperands("add(add(2,3),4");
    }

    @Test
    public void testThreeOperandSimpleExtractor() throws Exception {
        List<String> operands = OperandsExtractor.getOperands("5,3,10");
        assertEquals(operands.size(), 3);
        assertEquals(operands.get(0), "5");
        assertEquals(operands.get(1), "3");
        assertEquals(operands.get(2), "10");
    }
}
