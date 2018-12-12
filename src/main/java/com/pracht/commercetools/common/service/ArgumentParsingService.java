package com.pracht.commercetools.common.service;

import com.pracht.commercetools.common.model.Arguments;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.invoke.MethodHandles;

public class ArgumentParsingService {
    private static Logger log = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    public Arguments parseArguments(String[] arguments){
        if(arguments == null || arguments.length==0) {
            log.error("Missing argument list.");
            throw new IllegalArgumentException("Invalid arguments");
        }

        Arguments result = new Arguments();
        int ctr =0;
        boolean shouldContinue = true;

        while(ctr < arguments.length && shouldContinue){
            if(arguments[ctr].equalsIgnoreCase("--help")){
                result.setHelpRequested(true);
                shouldContinue=false;
            } else if(arguments[ctr].equalsIgnoreCase("--directory")) {
                ctr++;
                if(ctr >= arguments.length) {
                    log.error("Missing directory after --directory option");
                    throw new IllegalArgumentException("Invalid shop id number "+arguments[ctr]);
                } else {
                    String directoryString = arguments[ctr];
                    File directory = new File(directoryString);
                    if(!directory.exists() || !directory.isDirectory()) {
                        log.error("Missing or invalid directory {}",directoryString);
                        throw new IllegalArgumentException("Missing or invalid directory "+directoryString);
                    }
                    result.setShopDirectory(directory);
                }
                log.debug("Using directory {}",result.getShopDirectory().getAbsoluteFile().toString());
            } else if(arguments[ctr].equalsIgnoreCase("--shopIds")) {
                ctr++;
                while(ctr <arguments.length && !( arguments[ctr].startsWith("--") )) {
                    try {
                        long shopId = Long.parseLong(arguments[ctr]);
                        result.getShopIds().add(shopId);
                    } catch(NumberFormatException e) {
                        log.error("Invalid shop id {}",arguments[ctr]);
                        throw new IllegalArgumentException("Invalid shop id number "+arguments[ctr]);
                    }
                    ctr++;
                }
            }
            ctr++;
        }

        if(!result.isHelpRequested()) {
            if (result.getShopDirectory() == null) {
                log.error("Missing shop directory");
                throw new IllegalArgumentException("Missing shop directory");
            }

            if (result.getShopIds() == null || result.getShopIds().size() == 0) {
                log.error("Missing shop ids");
                throw new IllegalArgumentException("Missing shop ids");

            }
        }
        return result;

    }
}
