/**
 * This class manages the front end's reference to the back end through a
 * singleton pattern.
 * 
 * @author Andrew Curry
 */
package com.revature.service;

public class BackEndUtil {
    
    // class / static variables
    private static boolean sfInstantiated;
    private static ServiceFront instance;

    public static ServiceFront getBackEnd(){
        if (sfInstantiated) return instance;
        else{
            // TODO create DAOs, create handlers, create service front
            return null;
        }
    }
}
