///////////////////////////////////////////////////////////////
// MOVIESHELF
// CSC 210 Final Project, Fall 2017
// Chris Dalke, Nate Conroy, Andrew Gutierrez, Daniel Stegink
///////////////////////////////////////////////////////////////

package com.grup.movieshelf.Utility;

/////////////////////////////////////////////////////////////
// Module Imports
/////////////////////////////////////////////////////////////

import java.util.concurrent.ThreadLocalRandom;

/////////////////////////////////////////////////////////////
// RandomStringUtility
// Generates various types of random strings.
/////////////////////////////////////////////////////////////

public class RandomStringUtility {

    //------------------------------------------------
    // Static Variables & Constants
    //------------------------------------------------

    private static final String RANDOM_CREDENTIAL_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
    private static final String SESSION_RANDOM_CREDENTIAL_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //------------------------------------------------
    // Private Functions
    //------------------------------------------------

    // Given an alphabet and length, generate a random string
    private static String generateRandomString(int length, String alphabet){
        String randomString = "";
        while (randomString.length() < length) {
            randomString += RANDOM_CREDENTIAL_ALPHABET.charAt(ThreadLocalRandom.current().nextInt(0,RANDOM_CREDENTIAL_ALPHABET.length()));
        }
        return randomString;
    }

    //------------------------------------------------
    // Public Functions
    //------------------------------------------------

    public static String generateCredentialString(int length){
        return generateRandomString(length,RANDOM_CREDENTIAL_ALPHABET);
    }

    public static String generateSessionCodeString(int length){
        return generateRandomString(length,SESSION_RANDOM_CREDENTIAL_ALPHABET);
    }
}

/////////////////////////////////////////////////////////////
// End of File
/////////////////////////////////////////////////////////////