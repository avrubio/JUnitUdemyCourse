import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class DemoRepeatedTest {
    Calculator calculator;
    @BeforeEach
    void beforeEachTestMethod(){
        calculator = new Calculator();
        System.out.println("Executing @BeforeEach method");
    }
    @DisplayName("Division By Zero")
    @RepeatedTest(value = 3, name ="{displayName}. Repetition {currentRepetition} of " + "{totalRepetitions}")
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmethicException(
            RepetitionInfo repetitionInfo,
            TestInfo testInfo){

        System.out.println("Running " +testInfo.getTestMethod().get().getName() );
        System.out.println("Repetition number:" + repetitionInfo.getCurrentRepetition() + " of " + repetitionInfo.getTotalRepetitions());
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
}