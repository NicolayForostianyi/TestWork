package com.example.testTask.ExceptionHanding;

public class NoSuchNoteException extends RuntimeException {
    public NoSuchNoteException(String message) {
        super(message);
    }
}
