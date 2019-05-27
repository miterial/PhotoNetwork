package com.course.PhotoNetwork.model.types;

public enum BookingEnum {
    NEW(1,"ожидает оплаты"),
    PAID_CLIENT(2,"оплачено"),
    PAID_MASTER(3,"оплата подтверждена"),
    FINISH_AWAITS(4,"завершено (ожидает подтверждения)"),
    FINISHED(100,"завершено"),
    DELETE_AWAITS(101,"отменено (ожидает подтверждения)"),
    DELETED(102,"отменено"),
    HAS_REVIEW(200,"завершено");

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
