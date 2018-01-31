/*
 * Strings.java
 * Copyright 2018 Stackify
 */
package org.jenkinsci.plugins.stackify.util;

import lombok.experimental.UtilityClass;

/**
 * Strings
 * @author Eric Martin
 */
@UtilityClass
public class Strings {

    /**
     * Determines if the string isn't null or empty
     * @param string The string
     * @return True if the string isn't null or empty, false otherwise
     */
    public boolean hasValue(final String string) {        
        if (string != null) {
            if (0 < string.length()) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Determines if the string is null or empty
     * @param string The string
     * @return True if the string is null or empty, false otherwise
     */
    public boolean isEmpty(final String string) {
        if (string == null) {
            return true;
        }
        
        if (string.length() == 0) {
            return true;
        }
        
        return false;
    }
}
