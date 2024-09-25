/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validation;

public class ValidationUtils {

    public static boolean loginValidation(String email, String password) {
        if (isValidEmail(email) && isValidPassword(password)) {
            return true;
        }
        return false;
    }
    
    public static boolean signUpValidation(String username, String email, String password) {
        if (isValidUsername(username) && isValidEmail(email) && isValidPassword(password)) {
            return true;
        }
        return false;
    }
    
    public static boolean isValidUsername(String username) {
        // Check if username is not empty
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        // Check minimum and maximum length
        if (username.length() < 8 || username.length() > 50) {
            return false;
        }

        // Check if username matches the specified pattern
        if (!username.matches("^[a-zA-Z0-9-'_]+$")) {
            return false;
        }

        // Username is valid
        return true;
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        } else if (email.length() > 255) {
            return false;
        } 
        // Kiểm tra định dạng email sử dụng regular expression
        // Trả về true nếu email hợp lệ, ngược lại trả về false
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return true;
    }
}

