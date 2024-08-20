/*
 * Copyright (c) 2024 Battle Road Consulting. All rights reserved.
 * 
 * Refactor this test to test all of the current interfaces of TestClientMain
 * 
 */

package info.rx00405.test.client;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class TestClientMainTest {

    @Test
    void testMainWithHelpArgument() {
        String[] args = {"--help"};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TestClientMain.main(args);

        String output = outContent.toString();
        assertTrue(output.contains("Java Behavior-Driven Test Client"));
        assertTrue(output.contains("## DESCRIPTION"));
        assertTrue(output.contains("## USAGE"));
        assertTrue(output.contains("## OPTIONS"));
    }

    @Test
    void testMainWithRunQuietArgument() {
        String[] args = {"--run-quiet"};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TestClientMain.main(args);

        String output = outContent.toString();
        assertFalse(output.contains("Java Behavior-Driven Test Client"));
    }

    @Test
    void testMainWithNoArguments() {
        String[] args = {};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        TestClientMain.main(args);

        String output = outContent.toString();
        assertTrue(output.contains("Java Behavior-Driven Test Client"));
        assertTrue(output.contains("Tests found:"));
        assertTrue(output.contains("Tests succeeded:"));
        assertTrue(output.contains("Tests failed:"));
        assertTrue(output.contains("Tests skipped:"));
    }

    @Test
    void testMainWithInvalidArgument() {
        String[] args = {"--invalid"};
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        TestClientMain.main(args);

        String output = outContent.toString();
        assertTrue(output.contains("Java Behavior-Driven Test Client"));
        String errOutput = errContent.toString();
        assertTrue(errOutput.contains("Invalid argument"));
    }
}