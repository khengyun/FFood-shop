/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
$(document).ready(validateForm);
function validateForm() {
    $.validator.addMethod("googleDrivePattern", function (value, element) {
        // Define the regex patterns
        let pattern1 = /^https:\/\/drive\.google\.com\/uc\?id=.*/; // Used to embed images on websites
        let pattern2 = /^https:\/\/drive\.google\.com\/file\/d\/.*\/view\?usp=drive_link$/; // Initial image link

        // Test the value against the regex patterns
        return pattern1.test(value) || pattern2.test(value);
    }, "Định dạng đường dẫn không đúng");

    $(".add-food-form").validate({
        rules: {
            txtFoodTypeID: {
                required: true
            },
            txtFoodName: {
                required: true,
                maxlength: 100
            },
            txtFoodDescription: {
                required: true,
                maxlength: 500
            },
            txtFoodPrice: {
                required: true,
                number: true,
                min: 1
            },
            txtDiscountPercent: {
                required: true,
                digits: true,
                min: 0,
                max: 100
            },
            txtImageURL: {
                required: true,
                url: true,
                maxlength: 255,
                googleDrivePattern: true
            }
        },
        messages: {
            txtFoodTypeID: "Vui lòng chọn loại món",
            txtFoodName: {
                required: "Tên món không được để trống",
                maxlength: "Tên món không dài quá 100 kí tự"
            },
            txtFoodDescription: {
                required: "Tên món không được để trống",
                maxlength: "Tên món không dài quá 500 kí tự"
            },
            txtFoodPrice: {
                required: "Vui lòng nhập Đơn giá",
                number: "Đơn giá phải là định dạng số",
                min: "Đơn giá phải là số dương"
            },
            txtImageURL: {
                required: "Vui lòng nhập Đường dẫn ảnh",
                url: "Giá trị nhập vào phải là đường dẫn",
                maxlength: "Đường dẫn không dài quá 255 kí tự",
            }
        },
        errorElement: "p",
        highlight: function (element, errorClass, validClass) {
            $(element).parents(".col-sm-5").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parents(".col-sm-5").addClass("has-success").removeClass("has-error");
        },
        submitHandler: function (form) {
            form.submit();
        }
    });

    $(".update-food-form").validate({
        rules: {
            txtFoodTypeID: {
                required: true
            },
            txtFoodName: {
                required: true,
                maxlength: 100
            },
            txtFoodDescription: {
                required: true,
                maxlength: 500
            },
            txtFoodPrice: {
                required: true,
                number: true,
                min: 1
            },
            txtDiscountPercent: {
                required: true,
                digits: true,
                min: 0,
                max: 100
            },
            txtImageURL: {
                required: true,
                url: true,
                maxlength: 255,
                googleDrivePattern: true
            }
        },
        messages: {
            txtFoodTypeID: {
                required: "Vui lòng chọn loại món"
            },
            txtFoodName: {
                required: "Tên món không được để trống",
                maxlength: "Tên món không dài quá 100 kí tự"
            },
            txtFoodDescription: {
                required: "Tên món không được để trống",
                maxlength: "Tên món không dài quá 500 kí tự"
            },
            txtFoodPrice: {
                required: "Vui lòng nhập Đơn giá",
                number: "Đơn giá phải là định dạng số",
                min: "Đơn giá phải là số dương"
            },
            txtDiscountPercent: {
                required: "Vui lòng nhập Giá trị giảm giá",
                digits: "Giá trị giảm giá phải là số nguyên",
                min: "Giá trị giảm giá không được dưới 0",
                max: "Giá trị giảm giá không được quá 100"
            },
            txtImageURL: {
                required: "Vui lòng nhập Đường dẫn ảnh",
                url: "Giá trị nhập vào phải là đường dẫn",
                maxlength: "Đường dẫn không dài quá 255 kí tự",
            }
        },
        errorElement: "p",
        highlight: function (element, errorClass, validClass) {
            $(element).parents(".col-sm-5").addClass("has-error").removeClass("has-success");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).parents(".col-sm-5").addClass("has-success").removeClass("has-error");
        },
        submitHandler: function (form) {
            form.submit();
        }
    });

    $(".add-user-form").validate({
        rules: {
            txtAccountUsername: {
                required: true,
                maxlength: 100,
                pattern: /^[a-zA-Z0-9-'_]+$/
            },
            txtEmail: {
                required: true,
                maxlength: 255,
                email: true
            },
            txtAccountPassword: {
                required: true,
                minlength: 8
            },
            txtAccountRePassword: {
                required: true,
                minlength: 8,
                equalTo: "#txtAccountPassword"
            }
        },
        messages: {
            txtAccountUsername: {
                required: "Vui lòng nhập Tên Tài khoản Người dùng",
                maxlength: "Tên Tài khoản Người dùng không được vượt quá 100 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, số, dấu gạch ngang, gạch dưới, nháy đơn và không chứa khoảng trắng"
            },
            txtEmail: {
                required: "Vui lòng nhập Email",
                maxlength: "Email không được vượt quá 255 ký tự",
                email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtAccountPassword: {
                required: "Vui lòng nhập Mật khẩu",
                minlength: "Mật khẩu phải có ít nhất 8 ký tự"
            },
            txtAccountRePassword: {
                required: "Vui lòng nhập lại Mật khẩu",
                minlength: "Mật khẩu phải có ít nhất 8 ký tự",
                equalTo: "Mật khẩu không khớp"
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });
    

    $(".update-user-form").validate({
        rules: {
            txtAccountUsername: {
                required: true,
                maxlength: 100
            },
            txtEmail: {
                required: true,
                maxlength: 255,
                email: true
            },
            txtAccountPassword: {
                minlength: 8
            },
            txtAccountRePassword: {
                minlength: 8,
                equalTo: "#txtUpdateAccountPassword"
            }
        },
        messages: {
            txtAccountUsername: {
                required: "Vui lòng nhập Tên Tài khoản Người dùng",
                maxlength: "Tên Tài khoản Người dùng không được vượt quá 100 ký tự"
            },
            txtEmail: {
                required: "Vui lòng nhập Email",
                maxlength: "Email không được vượt quá 255 ký tự",
                email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtAccountPassword: {
                minlength: "Mật khẩu mới phải có ít nhất 8 ký tự"
            },
            txtAccountRePassword: {
                minlength: "Mật khẩu mới phải có ít nhất 8 ký tự",
                equalTo: "Mật khẩu không khớp"
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });

   
    $(".add-voucher-form").validate({
        rules: {
            txtAccountUsername: {
                required: true,
                maxlength: 100,
                pattern: /^[a-zA-Z0-9-'_]+$/
            },
            txtAccountFullname: {
                required: true,
                maxlength: 100,
                pattern: /^[\p{L}\s]+$/u
            },
            txtEmail: {
                required: true,
                maxlength: 255,
                email: true
            },
            txtAccountPassword: {
                required: true,
                minlength: 8
            },
            txtReAccountPassword: {
                required: true,
                equalTo: "#txtAdminAccountPassword"
            }
        },
        messages: {
            txtAccountUsername: {
                required: "Vui lòng nhập Tên Tài khoản Người dùng",
                maxlength: "Tên Tài khoản Người dùng không được vượt quá 100 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, số, dấu gạch ngang, gạch dưới, nháy đơn và không chứa khoảng trắng"
            },
            txtAccountFullname: {
                required: "Vui lòng nhập tên đầy đủ",
                maxlength: "Tên đầy đủ không được vượt quá 100 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, khoảng trắng"
            },
            txtEmail: {
                required: "Vui lòng nhập Email",
                maxlength: "Email không được vượt quá 255 ký tự",
                email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtAccountPassword: {
                required: "Vui lòng nhập Mật khẩu",
                minlength: "Mật khẩu phải có ít nhất 8 ký tự"
            },
            txtReAccountPassword: {
                required: "Vui lòng nhập lại Mật khẩu",
                equalTo: "Mật khẩu không khớp"
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });

  
    $(".update-voucher-form").validate({
        rules: {
            txtvoucher_name: {
                required: true,
                maxlength: 100,
            },
            txtvoucher_code: {
                required: true,
                maxlength: 16,
            },
            txtvoucher_discount_percent: {
                required: true,
                range: [0, 100]
            },
            txtvoucher_quantity: {
                required: true,
            }
        },
        messages: {
            txtvoucher_name: {
                required: "Vui lòng nhập tên Voucher",
                maxlength: "Tên Voucher không được vượt quá 100 ký tự"
            },
            txtvoucher_code: {
                required: "Vui lòng nhập mã code",
                maxlength: "Tên Voucher không được vượt quá 16 ký tự"
            },
            txtvoucher_discount_percent: {
                required: "Vui lòng nhập phần trăm giảm giá",
                range: "Vui lòng nhập từ 0% đến 100%"
            },
            txtvoucher_quantity: {
                required: "Vui lòng nhập số lượng giảm giá",
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });

    $("#login-form").validate({
        rules: {
            txtEmail: {
                required: true,
                maxlength: 255,
                email: true
            },
            txtPassword: {
                required: true
            }
        },
        messages: {
            txtEmail: {
                required: "Vui lòng nhập Email",
                maxlength: "Email không được vượt quá 255 ký tự",
                email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtPassword: {
                required: "Vui lòng nhập mật khẩu"
            }
        }
    });

    $("#signup-form").validate({
        rules: {
            txtAccountUsername: {
                required: true,
                minlength: 8,
                maxlength: 100
            },
            txtAccountEmail: {
                required: true,
                minlength: 8,
                maxlength: 255,
                email: true
            },
            txtAccountPassword: {
                required: true,
                minlength: 8
            },
            txtAccountRePassword: {
                required: true,
                minlength: 8,
                equalTo: "#txtAccountPassword"
            }
        },
        messages: {
            txtAccountUsername: {
                required: "Vui lòng nhập Tên Tài khoản Người dùng",
                minlength: "Tên tài khoản mới phải có ít nhất 8 ký tự",
                maxlength: "Tên Tài khoản Người dùng không được vượt quá 100 ký tự"
            },
            txtAccountEmail: {
                required: "Vui lòng nhập Email",
                minlength: "Email mới phải có ít nhất 8 ký tự",
                maxlength: "Email không được vượt quá 255 ký tự",
                email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtAccountPassword: {
                required: "Vui lòng nhập mật khẩu",
                minlength: "Mật khẩu mới phải có ít nhất 8 ký tự"
            },
            txtAccountRePassword: {
                required: "Vui lòng nhập lại mật khẩu",
                minlength: "Mật khẩu mới phải có ít nhất 8 ký tự",
                equalTo: "Mật khẩu không khớp"
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });

    $(".update-info-form").validate({
        rules: {
            txtLastName: {
                required: true,
                maxlength: 100
            },
            txtFirstName: {
                required: true,
                maxlength: 100
            },
            txtPhoneNumber: {
                required: true,
                maxlength: 11
            },
            txtAddress: {
                required: true,
                maxlength: 255
            }
        },
        messages: {
            txtLastName: {
                required: "Vui lòng nhập họ",
                maxlength: "Họ Người dùng không được vượt quá 100 ký tự"
            },
            txtFirstName: {
                required: "Vui lòng nhập tên",
                maxlength: "Tên Người dùng không được vượt quá 100 ký tự"
            },
            txtPhoneNumber: {
                required: "Vui lòng nhập số điện thoại",
                maxlength: "Số điện thoại không được vượt quá 11 kí tự"
            },
            txtAddress: {
                required: "Vui lòng nhập địa chỉ của Người dùng",
                maxlength: "Địa chỉ không được vượt quá 255 kí tự"
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });

    $("#otp-form").validate({
        rules: {
            otp: {
                required: true,
                number: true
            }
        },
        messages: {
            otp: {
                required: "Vui lòng nhập mã OTP",
                number: "Vui lòng nhập số"
            }

        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });

    $("#changePass-form").validate({
        rules: {
            txtPassword: {
                required: true,
                minlength: 8
            },
            txtRePassword: {
                required: true,
                minlength: 8,
                equalTo: "#txtChangePassword"
            }
        },
        messages: {
            txtPassword: {
                required: "Vui lòng mật khẩu mới",
                minlength: "Mật khẩu phải có ít nhất 8 ký tự"
            },
            txtRePassword: {
                required: "Vui lòng lại mật khẩu mới",
                minlength: "Mật khẩu phải có ít nhất 8 ký tự",
                equalTo: "Mật khẩu không khớp"
            }

        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });
}
