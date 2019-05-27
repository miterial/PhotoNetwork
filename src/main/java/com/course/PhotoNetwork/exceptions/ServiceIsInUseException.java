package com.course.PhotoNetwork.exceptions;

public class ServiceIsInUseException extends RuntimeException {
    public ServiceIsInUseException() {
        super("Невозможно удалить услугу, которую ещё предоставляют мастера");
    }
}