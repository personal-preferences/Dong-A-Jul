package org.personal.user_service.config;

public enum TOKENTIME {
    ACCESS(600000L),
    REFRESH(86000000L);

    private final long l;
    TOKENTIME(long l) {
        this.l = l;
    }
    public long label(){
        return l;
    }

}
