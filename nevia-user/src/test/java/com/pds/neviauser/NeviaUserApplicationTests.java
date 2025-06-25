package com.pds.neviauser;

import com.pds.neviauser.dto.UserCreateDto;
import com.pds.neviauser.dto.UserDto;
import com.pds.neviauser.entities.UserEntity;
import com.pds.neviauser.repositories.UserRepository;
import com.pds.neviauser.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

@ActiveProfiles("localhost")
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class NeviaUserApplicationTests {

    private static final Logger logger = Logger.getLogger(NeviaUserApplicationTests.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private static Instant startChrono;

    @BeforeAll
    public static void initChrono() {
        logger.info("Debut des tests");
        startChrono = Instant.now();
    }
    @AfterAll
    public static void durationTest() {
        logger.info("Fin de tous les tests");
        Instant endChrono = Instant.now();
        long duration = Duration.between(startChrono, endChrono).toMillis();
        logger.info("Duree des test : " + duration + "ms");
    }

    @BeforeEach
    public void initBooking() {
        logger.info("Debut du test");
    }

    @AfterEach
    public void tearDown() throws Exception {
        logger.info("Fin du test");
    }

    @Test
    @Tag("SaveUserWithSuccess")
    @DisplayName("Teste une simple insertion")
    public void saveUserWithSuccess() {
//
        // GIVEN
        //
        UserCreateDto userCreateDto = new UserCreateDto("test","test","test");
        //
        // WHEN
        //
        UserDto userDto = userService.createUser(userCreateDto);

        //
        // THEN
        //
        Assertions.assertNotNull(userDto);
        Assertions.assertNotNull((userDto.getIdUser()));
        Assertions.assertEquals(userDto.getEmail(), userCreateDto.getEmail());
    }
}
