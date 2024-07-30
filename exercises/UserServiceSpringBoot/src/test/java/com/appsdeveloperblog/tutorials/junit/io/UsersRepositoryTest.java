package com.appsdeveloperblog.tutorials.junit.io;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UsersRepository usersRepository;
    private final String userId1 = UUID.randomUUID().toString();
    private final String userId2 = UUID.randomUUID().toString();
    private final String email1 = "test@test.com";
    private final String email2 = "test2@test.com";



    @BeforeEach
    void setup() {
        // Creating first user
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId1);
        userEntity.setEmail(email1);
        userEntity.setEncryptedPassword("12345678");
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopolov");
        testEntityManager.persistAndFlush(userEntity);

        // Creating second user
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setUserId(userId2);
        userEntity2.setEmail(email2);
        userEntity2.setEncryptedPassword("abcdefg1");
        userEntity2.setFirstName("John");
        userEntity2.setLastName("Sears");
        testEntityManager.persistAndFlush(userEntity2);
    }
    @Test
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity(){
        // act
      UserEntity storedUser =  usersRepository.findByEmail(email1);
        // assert
        assertEquals(email1, storedUser.getEmail(), "returned email addy does not match the expected valye");
    }

    @Test
    void testFindByUserId_whenCorrectUserIdIsGiven_returnsUserEntity(){
        UserEntity storedUser =  usersRepository.findByUserId(userId2);

        assertEquals(userId2, storedUser.getUserId(), "Returned User id does NOT match");
    }

    @Test
    void testFindUsersWithEmailEndsWith_whenGivenEmailDomain_returnsUserWithGivenDomain(){
        //arrange
        UserEntity userEntity3 = new UserEntity();
        userEntity3.setUserId(UUID.randomUUID().toString());
        userEntity3.setEmail("ari@gmail.com");
        userEntity3.setEncryptedPassword("abcdefg1");
        userEntity3.setFirstName("John");
        userEntity3.setLastName("AppleSeed");
        testEntityManager.persistAndFlush(userEntity3);
        //act
        String emailDomainName = "@gmail.com";
        //assert
        List<UserEntity> users = usersRepository.findUsersWithEmailEndingWith(emailDomainName);

        assertEquals(1, users.size(), "There should be only one user in the list");

        assertTrue(users.get(0).getEmail().endsWith(emailDomainName));
    }
}
