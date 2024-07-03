import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

//@TestMethodOrder(MethodOrderer.Random.class)
//@TestMethodOrder(MethodOrderer.MethodName.class) // sorts by the test method name in order, ABCD
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodOrderedRandomlyTest {

    @Order(1)
    @Test
    void TestD(){
        System.out.println("Running Test D");
    }
    @Order(2)
    @Test
    void TestA(){
        System.out.println("Running Test A");
    }
    @Order(3)
    @Test
    void TestB(){
        System.out.println("Running Test B");
    }
    @Order(4)
    @Test
    void TestC(){
        System.out.println("Running Test C");
    }


}
