import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Random.class)
public class MethodOrderedRandomlyTest {

    @Test
    void TestA(){
        System.out.println("Running Test A");
    }
    @Test
    void TestB(){
        System.out.println("Running Test B");
    }
    @Test
    void TestC(){
        System.out.println("Running Test C");
    }
    @Test
    void TestD(){
        System.out.println("Running Test D");
    }

}
