package uz.example.paymentapp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import uz.example.paymentapp.domain.logic.CalculatorEngine;

public class CalculatorEngineTest {

    private CalculatorEngine engine;

    @Before
    public void setUp() {engine = new CalculatorEngine(0, 1_000_000_000);}

    @Test
    public void testAddition() {
        engine.handleInput("1");
        engine.handleInput("2");
        engine.handleInput("+");
        engine.handleInput("3");
        engine.handleInput("4");
        engine.calculateResult();
        assertEquals(46.0, engine.getResult(), 0.001);
    }

    @Test
    public void testOperatorPriority() {
        engine.handleInput("2");
        engine.handleInput("+");
        engine.handleInput("3");
        engine.handleInput("*");
        engine.handleInput("4");
        engine.calculateResult();
        assertEquals(14.0, engine.getResult(), 0.001);
    }

    @Test
    public void testDecimalPrecision() {
        engine.handleInput("0");
        engine.handleInput(".");
        engine.handleInput("1");
        engine.handleInput("1");
        engine.handleInput("+");
        engine.handleInput("0");
        engine.handleInput(".");
        engine.handleInput("2");
        engine.handleInput("2");
        engine.calculateResult();
        assertEquals(0.33, engine.getResult(), 0.001);
    }

    @Test
    public void testDivisionByZero() {
        engine.handleInput("1");
        engine.handleInput("2");
        engine.handleInput("/");
        engine.handleInput("0");
        engine.calculateResult();
        assertEquals(0.0, engine.getResult(), 0.001);
    }

    @Test
    public void testBackspace() {
        engine.handleInput("1");
        engine.handleInput("2");
        engine.backspace();
        assertEquals("1", engine.getDisplayValue());
        engine.backspace();
        assertEquals("0", engine.getDisplayValue());
    }

    @Test
    public void testMultipleBackspaces() {
        engine.handleInput("1");
        engine.handleInput("2");
        engine.handleInput("+");
        engine.handleInput("3");

        engine.backspace();
        assertEquals("12+", engine.getDisplayValue());

        engine.backspace();
        assertEquals("12", engine.getDisplayValue());

        engine.backspace();
        assertEquals("1", engine.getDisplayValue());

        engine.backspace();
        assertEquals("0", engine.getDisplayValue());
    }

    @Test
    public void testMaxValue() {
        engine = new CalculatorEngine(0, 100);
        engine.handleInput("200");
        engine.calculateResult();
        assertEquals(100.0, engine.getResult(), 0.001);
    }

    @Test
    public void testMinValue() {
        engine = new CalculatorEngine(10, 100);
        engine.handleInput("5");
        engine.calculateResult();
        assertEquals(10.0, engine.getResult(), 0.001);
    }

    @Test
    public void testRounding() {
        engine.handleInput("0");
        engine.handleInput(".");
        engine.handleInput("1");
        engine.handleInput("1");
        engine.handleInput("+");
        engine.handleInput("0");
        engine.handleInput(".");
        engine.handleInput("2");
        engine.handleInput("2");
        engine.calculateResult();
        assertEquals(0.33, engine.getResult(), 0.001);
    }

    @Test
    public void testComplexExpressionWithBackspace() {
        CalculatorEngine engine = new CalculatorEngine(0, 1_000_000_000);


        engine.handleInput("6"); engine.handleInput("0"); engine.handleInput("0"); engine.handleInput("0");
        engine.handleInput("0"); engine.handleInput("0"); engine.handleInput("0"); engine.handleInput("0");
        engine.handleInput("0"); engine.handleInput("0"); engine.handleInput("0"); engine.handleInput(".");
        engine.handleInput("4");
        engine.handleInput("*"); engine.handleInput("3");
        engine.handleInput("+"); engine.handleInput("1"); engine.handleInput("0"); engine.handleInput("0");
        engine.handleInput("0"); engine.handleInput("0"); engine.handleInput("0"); engine.handleInput("0");
        engine.handleInput("-"); engine.handleInput("5"); engine.handleInput("0"); engine.handleInput("0");
        engine.handleInput("0"); engine.handleInput("0"); engine.handleInput("0");

        assertEquals("600000000.4*3+1000000-500000", engine.getDisplayValue());

        for (int i = 0; i < 6; i++) {engine.backspace();}
        assertEquals("600000000.4*3+1000000-", engine.getDisplayValue());

        engine.handleInput("2"); engine.handleInput("5"); engine.handleInput("0"); engine.handleInput("0"); engine.handleInput("0");
        assertEquals("600000000.4*3+1000000-25000", engine.getDisplayValue());

        engine.calculateResult();
        double expected = 600000000.4 * 3 + 1000000 - 25000;
        assertEquals(expected, engine.getResult(), 0.01);
    }
}


