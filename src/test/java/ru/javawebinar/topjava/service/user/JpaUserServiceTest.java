package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = "jpa")
public class JpaUserServiceTest extends UserServiceTest {
}
