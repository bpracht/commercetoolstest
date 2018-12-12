package com.pracht.commercetools.common.model;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

public class Arguments {
    private File shopDirectory;
    private Level logLevel = Level.INFO;
    private List<Long> shopIds = new LinkedList<>();
    private boolean helpRequested;

    public Arguments() {
    }

    public Arguments withShopDirectory(File shopDirectory) {
        this.shopDirectory = shopDirectory;
        return this;
    }

    public Arguments withLogLevel(Level logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public Arguments withShopId(long shopId) {
        shopIds.add(shopId);
        return this;
    }

    public Arguments withHelpRequested(boolean helpRequested) {
        this.helpRequested = helpRequested;
        return this;
    }

    public Arguments withHelpRequested() {
        this.helpRequested = true;
        return this;
    }


    public File getShopDirectory() {
        return shopDirectory;
    }

    public void setShopDirectory(File shopDirectory) {
        this.shopDirectory = shopDirectory;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public List<Long> getShopIds() {
        return shopIds;
    }

    public void setShopIds(List<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public boolean isHelpRequested() {
        return helpRequested;
    }

    public void setHelpRequested(boolean helpRequested) {
        this.helpRequested = helpRequested;
    }

}
