package com.example.bistupracticeplatformforclanguage.module;

public class Stage
{
    private String name;
    private boolean isBended;

    public Stage(String name) {
        this.name = name;
        isBended = true;
    }

    public String getName() {
        return name;
    }

    public boolean isBended() {
        return isBended;
    }

    public void setBended(boolean bended) {
        isBended = bended;
    }
}
