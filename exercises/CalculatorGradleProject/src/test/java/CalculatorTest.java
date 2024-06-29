import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test
    void integerDivision(){
        //Arrange
        Calculator calculator= new Calculator();
        //Act
        int result = calculator.integerDivision(4,2);
        //Assert
        assertEquals(2, result, "Integer division did not produce expected result");
    }

    @Test
    void integerSubtraction(){
        //Arrange
        Calculator calculator= new Calculator();
        //Act
        int result = calculator.integerSubtraction(6,2);
        //Assert
        assertEquals(4, result, "Integer subtraction did not produce expected result");
    }

}