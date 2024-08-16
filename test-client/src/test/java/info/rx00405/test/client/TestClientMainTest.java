/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 */

 package info.rx00405.test.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestClientMainTest {
    @Test void appHasAGreeting() {
        TestClientMain classUnderTest = new TestClientMain();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
    }
}
