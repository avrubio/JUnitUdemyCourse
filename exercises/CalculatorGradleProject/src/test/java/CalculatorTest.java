import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @DisplayName("Test integer subtraction [minuend, subtrahend, expectedResult]")
    @ParameterizedTest
//    @MethodSource()
//    @CsvSource({"33,1,32","24,1,23"})
    @CsvFileSource(resources = "/integerSubtraction.csv")
    void integerSubtraction(int minuend, int subtrahend, int expectedResult){
        //Arrange
System.out.println("Running Test " +minuend+ "-" + subtrahend + "= " + expectedResult);

        //Act
        int actualResult = calculator.integerSubtraction(minuend,subtrahend);
        //Assert
        assertEquals(expectedResult, actualResult,
                ()->
            minuend + "-" + subtrahend + " did not produce "+ expectedResult
                );
    }
//private static Stream<Arguments> integerSubtraction(){
//        return Stream.of(
//                Arguments.of(33,1,32),
//                Arguments.of(24,1,23),
//                Arguments.of(54,1,53)
//
//        );
//}

}