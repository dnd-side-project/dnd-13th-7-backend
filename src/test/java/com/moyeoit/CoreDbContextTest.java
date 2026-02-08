package com.moyeoit;

import com.moyeoit.fixture.CleanUp;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class CoreDbContextTest {

    @Autowired
    private CleanUp cleanUp;

    @BeforeEach
    void cleanUp() {
        cleanUp.all();
    }

}
