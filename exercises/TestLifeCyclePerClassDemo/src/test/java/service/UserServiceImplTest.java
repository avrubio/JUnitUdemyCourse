package service;

import io.UsersDatabase;
import io.UsersDatabaseMapImpl;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId = "";
    @BeforeAll
    void setup() {
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();

        userService = new UserServiceImpl(usersDatabase);

    }

    @AfterAll
    void cleanup() {
        // Close connection
        // Delete database
        usersDatabase.close();
    }

    @Test
    @Order(1)
    @DisplayName("Create User works")
    void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        //Arrange
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "John");
        user.put("lastName", "Smith");

        // Act
        createdUserId = userService.createUser(user);

        //Assert
        assertNotNull(createdUserId, "User Id should not be Null");

    }


    @Test
    @Order(2)
    @DisplayName("Update user works")
    void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
//Arrange
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "Jane");
        newUserDetails.put("lastName", "Doe");

        // Act
        Map updatedUserDetials = userService.updateUser(createdUserId, newUserDetails);
        //Assert
        assertEquals(newUserDetails.get("firstName"), updatedUserDetials.get("firstName"), "Returned value of user's first name is incorrect");
        assertEquals(newUserDetails.get("lastName"), updatedUserDetials.get("lastName"), "Returned value of user's last name is incorrect");
    }

    @Test
    @Order(3)
    @DisplayName("Find user works")
    void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {
    //Act
        Map userDetails = userService.getUserDetails(createdUserId);

        //Assert
        assertNotNull(userDetails, "User Details should not be null");
        assertEquals(createdUserId, userDetails.get("userId"), "Returned user details contains incorrect user id");
    }

    @Test
    @Order(4)
    @DisplayName("Delete user works")
    void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {

        //Act
        userService.deleteUser(createdUserId);

        //Assert
        assertNull(userService.getUserDetails(createdUserId), "User should have been deleted");
    }

}
