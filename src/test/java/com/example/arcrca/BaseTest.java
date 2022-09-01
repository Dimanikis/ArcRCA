package com.example.arcrca;

import org.apache.log4j.Logger;

public abstract class BaseTest {
    final static Logger logger = Logger.getLogger(BaseTest.class);

    protected void log(String message) {
        logger.trace(message);
    }
}

