package com.course.PhotoNetwork.model.types;

public enum BookingEnum {
    NEW("ожидает оплаты"),
    PAID_CLIENT("оплачено"),
    PAID_MASTER("оплата подтверждена"),
    AWAITS_FINISH("завершено (не закрыто)"),
    FINISHED("завершено");

    private String val;
    BookingEnum(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }
}
