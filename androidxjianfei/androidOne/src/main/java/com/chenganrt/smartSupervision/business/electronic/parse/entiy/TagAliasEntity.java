package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import java.util.Set;

/**
 * Created by Administrator on 2019/4/24.
 */

public class TagAliasEntity {

    private int action;
    private String alias;
    private boolean isAliasAction;
    private Set<String> tags;

    public TagAliasEntity(int action, String alias, boolean isAliasAction, Set<String> tags) {
        this.action = action;
        this.alias = alias;
        this.isAliasAction = isAliasAction;
        this.tags = tags;
    }

    public TagAliasEntity(int action, String alias, boolean isAliasAction) {
        this.action = action;
        this.alias = alias;
        this.isAliasAction = isAliasAction;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isAliasAction() {
        return isAliasAction;
    }

    public void setAliasAction(boolean aliasAction) {
        isAliasAction = aliasAction;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }
}
