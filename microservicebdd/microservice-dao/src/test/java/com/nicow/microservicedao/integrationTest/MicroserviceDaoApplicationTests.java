package com.nicow.microservicedao.integrationTest;

import com.nicow.microservicedao.conf.MongoConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoConfig.class)
class MicroserviceDaoApplicationTests {

    @Test
    void contextLoads() {
    }

}
