package io.quarkus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestCase {

//    @Test
    public void someTest(){
        SomeClass someClass = new SomeClass();

        assertNotEquals(0, someClass.getAllObjects().size());
    }
}
