package com.restaurant.restaurantapi.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    //feedback
    FEEDBACK_NOTFOUND(404, "Feedback Not Found", HttpStatus.NOT_FOUND),
    FEEDBACK_EXISTED(400, "Feedback with this email already exists", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED (400,"You are not authorized to delete this wishlist.",HttpStatus.BAD_REQUEST) ,
    USER_NOT_FOUND (404, "User Not Found", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND (404, "Review Not Found", HttpStatus.NOT_FOUND),
    MENU_NOTFOUND (404, "Menu Not Found", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND (404, "Order Not Found", HttpStatus.NOT_FOUND),
    ORDER_DETAIL_NOT_FOUND (404, "Order Detail Not Found", HttpStatus.NOT_FOUND),
    MENU_FOOD_NOTFOUND (404, "Menu Food Not Found", HttpStatus.NOT_FOUND),
    ALREADY_PAID( 400,"Payment already exists for this order", HttpStatus.NOT_FOUND) ,
    INVALID_TOKEN(400, "Invalid Token", HttpStatus.BAD_REQUEST) ,
    CART_NOTFOUND( 404, "Cart Not Found", HttpStatus.NOT_FOUND) ,

    FOOD_NOTFOUND(404, "Food Not Found", HttpStatus.NOT_FOUND),
    FOOD_EXISTED (400, "Food with this  already exists", HttpStatus.BAD_REQUEST),
    REVIEW_EXISTED(400, "Review already exists", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    EXPIRES(400, "Expires", HttpStatus.BAD_REQUEST),
    //storage
    INITIALIZE(400, "Cannot initialize storage", HttpStatus.BAD_REQUEST),
    CART_EMPTY(400, "Cart is empty", HttpStatus.BAD_REQUEST),
    // Category
    CATEGORY_NOTFOUND(404, "Category Not Found", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(400, "Category name existed", HttpStatus.BAD_REQUEST),

    // Course
    COURSE_NOTFOUND(404, "Course Not Found", HttpStatus.NOT_FOUND),
    COURSE_EXISTED(400, "Course existed", HttpStatus.BAD_REQUEST),
    COURSE_PURCHASED(400, "This course has been purchased", HttpStatus.BAD_REQUEST),
    COURSE_NOTPURCHASED(400, "This course has't been purchased", HttpStatus.BAD_REQUEST),
    // Topic Online
    TOPIC_NOTFOUND(404, "Topic Not Found", HttpStatus.NOT_FOUND),
    TOPIC_EXISTED(400, "Topic existed", HttpStatus.BAD_REQUEST),

    // Item Online
    ITEMONLINE_NOTFOUND(404, "Item Online Not Found", HttpStatus.NOT_FOUND),
    ITEMONLINE_EXISTED(400, "Item Online existed", HttpStatus.BAD_REQUEST),
    // Course Online Student
    // Item online

    INVALID_ORDER_STATUS_CHANGE(404, "Invalid Order Status Change", HttpStatus.BAD_REQUEST),
    // Class
    CLASS_NOTFOUND(404, "Students do not have classes", HttpStatus.NOT_FOUND),

    // Test
    TESTINPUT_NOTFOUND(404, "Test Input Not Found", HttpStatus.NOT_FOUND),

    NOTFOUND(404, "Not Found", HttpStatus.NOT_FOUND),
    // Student
    STUDENT_NOTFOUND(404, "Student Not Found", HttpStatus.NOT_FOUND),
    // User
    USER_NOTFOUND(404, "User Not Found", HttpStatus.NOT_FOUND),
    // certificate
    CERTIFICATE_QUALIFIED(400, "You are not qualified yet", HttpStatus.BAD_REQUEST),
    CERTIFICATE_NOTFOUND(404, "Certificate not found", HttpStatus.NOT_FOUND),
    // ItemSlot
    ITEMSLOT_NOTFOUND(404, "ItemSlot Not Found", HttpStatus.NOT_FOUND),
    ITEMSLOT_EXISTED(404, "ItemSlot existed", HttpStatus.BAD_REQUEST),

    // Slot
    SLOT_NOTFOUND(404, "Slot Not Found", HttpStatus.NOT_FOUND),
    SLOT_EXISTED(404, "Slot existed", HttpStatus.BAD_REQUEST),

    // Answer Item Slot
    ANSWERITEMSLOT_EXISTING(404, "Answer existed", HttpStatus.BAD_REQUEST),
    ANSWERITEMSLOT_NOTFOUND(404, "Answer Not Found", HttpStatus.NOT_FOUND),
    ANSWERITEMSLOT_GRADED(400, "Answers were graded", HttpStatus.NOT_FOUND),

    // Test Offline
    ITNOTTIME(404, "It's not time to do it yet", HttpStatus.NOT_FOUND),

    // Tutor
    TUTOR_NOTFOUND(404, "Tutor Not Found", HttpStatus.NOT_FOUND),

    // Subject
    SUBJECT_NOTFOUND(404, "Subject Not Found", HttpStatus.NOT_FOUND),
    SUBJECT_EXISTED(400, "Subject existed", HttpStatus.BAD_REQUEST),

    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALIDEMAILORPASSWORD(400, "Invalid email or password", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(401, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_RESETTOKEN(400, "Invalid or expired reset token", HttpStatus.NOT_FOUND),
    FILE_UPLOAD_FAILED (400, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR),


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