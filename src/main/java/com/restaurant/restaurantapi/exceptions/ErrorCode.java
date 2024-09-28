package com.restaurant.restaurantapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    EXPIRES(400, "Expires", HttpStatus.BAD_REQUEST),
    //storage
    INITIALIZE(400, "Cannot initialize storage", HttpStatus.BAD_REQUEST),

    // Course
    COURSE_NOTFOUND(404, "Course Not Found", HttpStatus.NOT_FOUND),
    COURSE_EXISTED(400, "User existed", HttpStatus.BAD_REQUEST),
    COURSE_PURCHASED(400, "This course has been purchased", HttpStatus.BAD_REQUEST),
    COURSE_NOTPURCHASED(400, "This course has't been purchased", HttpStatus.BAD_REQUEST),
    // Course Online Student
    // Item online

    ITEMONLINE_NOTFOUND(404, "Item Online Not Found", HttpStatus.NOT_FOUND),

    // Class
    CLASS_NOTFOUND(404, "Students do not have classes", HttpStatus.NOT_FOUND),

    // Test
    TESTINPUT_NOTFOUND(404, "Test Input Not Found", HttpStatus.NOT_FOUND),

    NOTFOUND(404, "Not Found", HttpStatus.NOT_FOUND),
    // Student
    STUDENT_NOTFOUND(404, "Student Not Found", HttpStatus.NOT_FOUND),
    // User
    USER_NOTFOUND(404, "User Not Found", HttpStatus.NOT_FOUND),
    // ItemSlot
    ITEMSLOT_NOTFOUND(404, "ItemSlot Not Found", HttpStatus.NOT_FOUND),

    // Answer Item Slot
    ANSWERITEMSLOT_EXISTING(404, "Answer existed", HttpStatus.BAD_REQUEST),
    ANSWERITEMSLOT_NOTFOUND(404, "Answer Not Found", HttpStatus.NOT_FOUND),
    ANSWERITEMSLOT_GRADED(400, "Answers were graded", HttpStatus.NOT_FOUND),

    // Test Offline
    ITNOTTIME(404, "It's not time to do it yet", HttpStatus.NOT_FOUND),

    // Tutor
    TUTOR_NOTFOUND(404, "Tutor Not Found", HttpStatus.NOT_FOUND),

    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALIDEMAILORPASSWORD(400, "Invalid email or password", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_RESETTOKEN(400, "Invalid or expired reset token", HttpStatus.NOT_FOUND),


    ;
    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}