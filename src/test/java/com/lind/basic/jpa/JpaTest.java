package com.lind.basic.jpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTest {
  @Autowired
  TestEntityRepository testEntityRepository;
  @Autowired
  TestBuilderEntityRepository testBuilderEntityRepository;

  TestEntityBuilder getTestEntityBuilder() {
    TestEntityBuilder testEntityBuilder = TestEntityBuilder.builder()
        .title("lind")
        .description("lind is @builder and inherit")
        .build();
    return testEntityBuilder;
  }

  @Test
  public void updateListen() {
    TestEntity testEntity = TestEntity.builder()
        .title("test")
        .description("you are good")
        .build();
    testEntityRepository.save(testEntity);
    TestEntity old = testEntityRepository.findOne(testEntity.getId());
    old = old.toBuilder().description("modify@@@").build();
    testEntityRepository.save(old);
    Assert.assertNotNull(old);
  }

  /**
   * 测试：在实体使用继承时，如何使用@Builder注解.
   */
  @Test
  public void insertBuilderAndInherit() {
    TestEntityBuilder testEntityBuilder = getTestEntityBuilder();
    testBuilderEntityRepository.save(testEntityBuilder);
    TestEntityBuilder entity = testBuilderEntityRepository.findOne(
        testEntityBuilder.getId());
    System.out.println("userinfo:" + entity.toString());

    entity = entity.toBuilder().description("修改了").build();
    testBuilderEntityRepository.save(entity);
    System.out.println("userinfo:" + entity.toString());
  }

}
