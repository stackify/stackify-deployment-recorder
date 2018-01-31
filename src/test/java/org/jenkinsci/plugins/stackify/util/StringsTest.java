/*
 * StringsTest.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Strings JUnit Test
 * @author Eric Martin
 */
public class StringsTest {

    /**
     * testHasValue
     */
    @Test
    public void testHasValue() {
        Assert.assertTrue(Strings.hasValue("testHasValue"));
    }

    /**
     * testHasValueWithEmpty
     */
    @Test
    public void testHasValueWithEmpty() {
        Assert.assertFalse(Strings.hasValue(""));
    }

    /**
     * testHasValueWithNull
     */
    @Test
    public void testHasValueWithNull() {
        Assert.assertFalse(Strings.hasValue(null));
    }
    
    /**
     * testIsEmptyWithEmpty
     */
    @Test
    public void testIsEmptyWithEmpty() {
        Assert.assertTrue(Strings.isEmpty(""));
    }

    /**
     * testIsEmptyWithNull
     */
    @Test
    public void testIsEmptyWithNull() {
        Assert.assertTrue(Strings.isEmpty(null));
    }

    /**
     * testIsEmpty
     */
    @Test
    public void testIsEmpty() {
        Assert.assertFalse(Strings.isEmpty("testIsEmptyWithString"));
    }
}
