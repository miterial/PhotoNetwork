package com.course.PhotoNetwork.model.types;

public enum BookingEnum {
    NEW(1,"ожидает оплаты"),
    PAID_CLIENT(2,"оплачено"),
    PAID_MASTER(3,"оплата подтверждена"),
    AWAITS_FINISH(4,"завершено (не закрыто)"),
    FINISHED(5,"завершено");

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
