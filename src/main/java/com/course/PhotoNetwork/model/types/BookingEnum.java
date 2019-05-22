package com.course.PhotoNetwork.model.types;

public enum BookingEnum {
    NEW(1,"ожидает оплаты"),
    PAID_CLIENT(2,"оплачено"),
    PAID_MASTER(3,"оплата подтверждена"),
    FINISH_MASTER(4,"завершено (ожидает подтверждения)"),
    FINISH_CLIENT(5,"завершено (ожидает подтверждения)"),
    FINISHED(6,"завершено");

    private String val;
    private long id;
    BookingEnum(long id,String val) {
        this.val = val;
        this.id = id;
    }

    public String getVal() {
        return val;
    }

    public long getId() {
        return id;
    }
}
