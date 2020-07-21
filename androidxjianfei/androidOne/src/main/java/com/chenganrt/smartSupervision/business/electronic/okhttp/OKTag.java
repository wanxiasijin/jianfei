package com.chenganrt.smartSupervision.business.electronic.okhttp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class OKTag {
    public long TAGID = 0L;
    public Map<String, Object> map;

    public OKTag() {
        this.TAGID = creatTagID();
        this.map = new HashMap<String, Object>();
    }

    public OKTag(OKTag tag) {
        this.TAGID = tag.TAGID;
        this.map = tag.map;
    }

    public Object put(String key, Object obj) {
        return map.put(key, obj);
    }

    public Object get(String key, Object def) {
        if (map.containsKey(key) && map.get(key) != null)
            return map.get(key);
        return def;
    }

    private long creatTagID() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        builder.append(System.currentTimeMillis());
        builder.append(random.nextInt(999));
        return Long.parseLong(builder.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj != null && obj instanceof OKTag) {
            OKTag o = (OKTag) obj;
            return this.TAGID == o.TAGID;
        }
        return super.equals(obj);
    }

    public static class Builder {
        private OKTag tag;

        public Builder() {
            this.tag = new OKTag();
        }

        public Builder put(String key, Object obj) {
            tag.put(key, obj);
            return this;
        }

        public OKTag builder() {
            return tag;
        }
    }

    @Override
    public String toString() {
        return "OKTag{" + "TAGID=" + TAGID + '}';
    }
}
