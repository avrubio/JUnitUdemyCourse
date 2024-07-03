import org.junit.jupiter.api.*;

//@TestMethodOrder(MethodOrderer.Random.class)
//@TestMethodOrder(MethodOrderer.MethodName.class) // sorts by the test method name in order, ABCD
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MethodOrderedRandomlyTest {

    StringBuilder completed = new StringBuilder("");

    @AfterEach
    void afterEach(){
        System.out.println("The state of instance object is: " + completed);
    }
    @Order(1)
    @Test
    void TestD(){
        System.out.println("Running Test D");
        completed.append("1");
    }
    @Order(2)
    @Test
    void TestA(){
        System.out.println("Running Test A");
        completed.append("2");
    }
    @Order(3)
    @Test
    void TestB(){
        System.out.println("Running Test B");
        completed.append("3");
    }
    @Order(4)
    @Test
    void TestC(){
        System.out.println("Running Test C");
        completed.append("4");
    }


}
