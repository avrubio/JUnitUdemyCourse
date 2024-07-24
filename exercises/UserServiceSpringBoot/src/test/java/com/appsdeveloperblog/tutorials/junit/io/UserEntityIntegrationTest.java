package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserEntityIntegrationTest {
    @Autowired
    private TestEntityManager testEntityManager;
    UserEntity userEntity;
    @BeforeEach
    void setUp(){
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("Ari");
        userEntity.setLastName("Vanegas");
        userEntity.setEmail("test@aol.com");
        userEntity.setEncryptedPassword("123456789");
    }
    @Test
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnStoredUserDetails(){
        //arrange

        //act
        UserEntity storedUserEntity = testEntityManager.persistAndFlush(userEntity);
        //assert
        assertTrue(storedUserEntity.getId() > 0);
        assertEquals(userEntity.getUserId(), storedUserEntity.getUserId());
        assertEquals(userEntity.getFirstName(), storedUserEntity.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserEntity.getLastName());
        assertEquals(userEntity.getEmail(), storedUserEntity.getEmail());
        assertEquals(userEntity.getEncryptedPassword(), storedUserEntity.getEncryptedPassword());
    }

    @Test
    void testUserEntity_whenFirstNameIsTooLong_shouldThrowException(){
        // arrange
        userEntity.setFirstName("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZa");
        // act
        // assert
        assertThrows(PersistenceException.class, ()-> {
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a Persistenceexception");

    }

    @Test
    void testUserEntity_whenUserIdIsNotUnique_shouldThrowException() {
        UserEntity newEntity = new UserEntity();
        newEntity.setUserId("1");
        newEntity.setEmail("test2@test.com");
        newEntity.setFirstName("Ari");
        newEntity.setLastName("test");
        newEntity.setEncryptedPassword("test");
        testEntityManager.persistAndFlush(newEntity);

        userEntity.setUserId("1");
        assertThrows(PersistenceException.class, ()-> {
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a Persistence Exception");


    }
}
