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

