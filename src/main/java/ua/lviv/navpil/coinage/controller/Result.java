package ua.lviv.navpil.coinage.controller;

public class Result {

    private final Status status;
    private final String message;

    private Result(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Result success(String message) {
        return new Result(Status.SUCCESS, message);
    }

    public static Result success() {
        return success("");
    }

    public static Result failure(String message) {
        return new Result(Status.FAILURE, message);
    }

    public static Result info(String message) {
        return new Result(Status.INFO, message);
    }

    public static Result endOfMove(String message) {
        return new Result(Status.END_OF_MOVE, message);
    }

    public static Result of(Status status, String message) {
        return new Result(status, message);
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    public enum Status {
        SUCCESS, FAILURE, INFO, END_OF_MOVE, END_OF_GAME
    }

}
