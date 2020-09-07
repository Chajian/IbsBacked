package com.ibs.backed.data;

public enum  StringRule {
    VERIFYPASS("密码匹配","^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,16}$"),
    VERIFYACCOUNT("账号匹配","^[a-zA-Z0-9]{6,16}");

    private String name;
    private String rule;


    StringRule(String name, String rule) {
        this.name = name;
        this.rule = rule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
