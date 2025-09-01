/**
 * -----------------------------------------------------------------------------
 * Author      : Prathmesh Rewale (https://github.com/prathmeshrewale)
 * Date        : 2025-09-01
 * Company     : Webzworld
 * File        : RootController.java
 * Purpose     : Creates RootController for the application, It's used to handle root requests
 * -----------------------------------------------------------------------------
 */

package com.mac.arbitrator.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    @RequestMapping
    public String index() {
        return "Welcome to the Arbitrator API!";
    }
}
