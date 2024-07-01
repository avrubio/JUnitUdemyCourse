import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math Operations in Calculator Class")
class CalculatorTest {
    Calculator calculator;

    @BeforeAll
    static void setUp(){
        System.out.println("executing @BeforeAll method.");
    }

    @AfterAll
    static void cleanUp(){
        System.out.println("Executing @AfterAll Method");
    }

    @BeforeEach
    void beforeEachTestMethod(){
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach method");
    }

    @AfterEach
    void afterEachTestMethod(){
        System.out.println("Executing @AfterEach method");
    }
    @DisplayName("Test 4/2 = 2")
    @Test
    void testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo(){
        //Arrange // GIVEN that we have these preconditions
        int dividend = 4;
        int divisor =2;
        int expectedResult = 2;
        //Act //WHEN use these conditions and invoke our method
        int actualResult = calculator.integerDivision(dividend,divisor);
        //Assert //THEN the following should be true
        assertEquals(expectedResult, actualResult, "Integer division did not produce expected result");
    }
    @DisplayName("Division By Zero")
    @Test
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmethicException(){
        System.out.println("Runing Division By Zero");
        //Arrange
        int dividend = 4;
        int divisor = 0;
        String expectedMessage = "/ by zero";
        //Act & Assert
       ArithmeticException actualMessage= assertThrows(ArithmeticException.class, () -> {
                calculator.integerDivision(dividend, divisor);
        }, "Division by zero should have thrown arithmetic exception ");

        //Assert

        assertEquals(expectedMessage, actualMessage.getMessage());
    }


    @Test
    void integerSubtraction(){
        //Arrange
//        Calculator calculator= new Calculator();
        //Act
        int result = calculator.integerSubtraction(6,2);
        //Assert
        assertEquals(4, result, "Integer subtraction did not produce expected result");
    }

}