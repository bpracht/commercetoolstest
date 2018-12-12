package com.pracht.commercetools.common.service;

import com.pracht.commercetools.common.model.Arguments;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ArgumentParsingServiceTest {

    /**
     * This should have worked with a @BeforeAll annotation, but Jupiter does not
     * build with Maven and the @BeforeAll method never ran.
     */
    private static ArgumentParsingService argumentParsingService = new ArgumentParsingService();


    @Test
    public void failOnEmptyArguments() {
        try {
            argumentParsingService.parseArguments(null);
            fail("Expected failure did not occur for missing arguments");
        } catch (IllegalArgumentException e) {
            // no op
        }
        String empty[] = new String[0];
        try {
            argumentParsingService.parseArguments(empty);
            fail("Expected failure did not occur for missing arguments");
        } catch (IllegalArgumentException e) {
            // no op
        }
    }

    @Test
    public void testMinimumArgumentsPresent() {
        String[] argumentArray = {"--directory", "/tmp", "--shopIds", "1","2"};
        Arguments arguments = argumentParsingService.parseArguments(argumentArray);
        assertNotNull(arguments, "Arguments should not be null");
        assertNotNull(arguments.getShopDirectory(), "Unexpected null shop directory");
        assertTrue(arguments.getShopDirectory().exists() && arguments.getShopDirectory().isDirectory(), "A bad directory was allowed");
        List<Long> expectedShopIds = Arrays.asList(new Long[]{1L,2L});
        assertEquals(expectedShopIds,arguments.getShopIds(),"Unexpected shop ids");
    }

    @Test
    public void testMissingDirectory() {
        String[] argumentArray = {"--shopIds","1","2"};
        try {
            Arguments arguments = argumentParsingService.parseArguments(argumentArray);
            fail("Expected failure for missing directory but did not occur");
        } catch(IllegalArgumentException e) {
            // no op
        }
        argumentArray = new String[]{"--shopIds","1","--directory"};
        try {
            Arguments arguments = argumentParsingService.parseArguments(argumentArray);
            fail("Expected failure for missing directory but did not occur");
        } catch(IllegalArgumentException e) {
            // no op
        }
    }

    @Test
    public void testMissingShopIds() {
        String[] argumentArray = {"--directory","/tmp","--shopIds"};
        try {
            Arguments arguments = argumentParsingService.parseArguments(argumentArray);
            fail("Expected failure for missing shop ids but did not occur");
        } catch(IllegalArgumentException e) {
            // no op
        }
        argumentArray = new String[]{"--directory","/tmp"};
        try {
            Arguments arguments = argumentParsingService.parseArguments(argumentArray);
            fail("Expected failure for missing shop ids but did not occur");
        } catch(IllegalArgumentException e) {
            // no op
        }
        argumentArray = new String[]{"--directory","/tmp","--shopIds","1","BADID"};
        try {
            Arguments arguments = argumentParsingService.parseArguments(argumentArray);
            fail("Expected failure for bad shop id but did not occur");
        } catch(IllegalArgumentException e) {
            // no op
        }
    }

    @Test
    public void testHelpRequested() {
        String[] argumentArray = {"--help"};
        Arguments arguments = argumentParsingService.parseArguments(argumentArray);
        assertTrue(arguments.isHelpRequested(),"Expected help to be requested but was not");
    }

}
