/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
$(document).ready(validateForm);
function validateForm() {
    $(".add-food-form").validate({
        rules: {
            txtFoodTypeID: {
                required: true
            },
            txtFoodName: {
                required: true,
                maxlength: 100,
                pattern: /^[\p{L}\s]+$/u
            },
            txtFoodDescription: {
                maxlength: 500
            },
            txtFoodPrice: {
                required: true,
                number: true,
                min: 1
            },
            txtFoodQuantity: {
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
                maxlength: 500
            }
        },
        messages: {
            txtFoodTypeID: {
                required: "Vui lòng chọn loại món"
            },
            txtFoodName: {
                required: "Tên món không được để trống",
                maxlength: "Tên món không được dài quá 100 ký tự",
                pattern: "Tên món chỉ chấp nhận chữ"
            },
            txtFoodDescription: {
                maxlength: "Tên món không dài quá 500 kí tự"
            },
            txtFoodPrice: {
                required: "Vui lòng nhập Đơn giá",
                number: "Đơn giá phải có định dạng số",
                min: "Đơn giá phải là số dương lớn hơn hoặc bằng 1"
            },
            txtFoodQuantity: {
                required: "Vui lòng nhập số lượng món",
                number: "Số lượng phải có định dạng số",
                min: "Số lượng phải là số dương lớn hơn hoặc bằng 1"
            },
            txtDiscountPercent: {
                required: "Vui lòng nhập Phần trăm giảm giá",
                digits: "Phần trăm giảm giá phải là số nguyên",
                min: "Phần trăm giảm giá không được nhỏ hơn 0",
                max: "Phần trăm giảm giá không được lớn hơn 100"
            },
            txtImageURL: {
                required: "Vui lòng nhập Đường dẫn ảnh",
                url: "Giá trị nhập vào phải là đường dẫn hợp lệ",
                maxlength: "Đường dẫn không được dài quá 500 ký tự",
            }
        },
        errorElement: "p",
        highlight: function (element, errorClass, validClass) {
            $(element).parents(".col-sm-5").addClass("has-error").removeClass("has-success");
        }
        ,
        unhighlight: function (element, errorClass, validClass) {
            $(element).parents(".col-sm-5").addClass("has-success").removeClass("has-error");
        }
        ,
        submitHandler: function (form) {
            form.submit();
        }
    }
    );

    $(".update-food-form").validate({
        rules: {
            txtFoodTypeID: {
                required: true
            },
            txtFoodName: {
                required: true,
                maxlength: 100,
                pattern: /^[\p{L}\s]+$/u
            },
            txtFoodDescription: {
                maxlength: 500
            },
            txtFoodPrice: {
                required: true,
                number: true,
                min: 1
            },
            txtFoodQuantity: {
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
                maxlength: 500
            }
        },
        messages: {
            txtFoodTypeID: {
                required: "Vui lòng chọn loại món"
            },
            txtFoodName: {
                required: "Tên món không được để trống",
                maxlength: "Tên món không được dài quá 100 ký tự",
                pattern: "Tên món chỉ chấp nhận chữ và khoảng trắng"
            },
            txtFoodDescription: {
                maxlength: "Mô tả món ăn không dài quá 500 kí tự"
            },
            txtFoodPrice: {
                required: "Vui lòng nhập Đơn giá",
                number: "Đơn giá phải có định dạng số",
                min: "Đơn giá phải là số dương lớn hơn hoặc bằng 1"
            },
            txtFoodQuantity: {
                required: "Vui lòng nhập số lượng món",
                number: "Số lượng phải có định dạng số",
                min: "Số lượng phải là số dương lớn hơn hoặc bằng 1"
            },
            txtDiscountPercent: {
                required: "Vui lòng nhập Phần trăm giảm giá",
                digits: "Phần trăm giảm giá phải là số nguyên",
                min: "Phần trăm giảm giá không được nhỏ hơn 0",
                max: "Phần trăm giảm giá không được lớn hơn 100"
            },
            txtImageURL: {
                required: "Vui lòng nhập Đường dẫn ảnh",
                url: "Giá trị nhập vào phải là đường dẫn hợp lệ",
                maxlength: "Đường dẫn không được dài quá 50 ký tự",
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
                maxlength: 50,
                pattern: /^[a-zA-Z0-9-'_]+$/
            },
            txtLastName: {
                required: true,
                maxlength: 50,
                pattern: /^[\p{L}\s]+$/u
            },
            txtFirstName: {
                required: true,
                maxlength: 50,
                pattern: /^[\p{L}\s]+$/u
            },
            txtPhoneNumber: {
                required: true,
                maxlength: 11,
                number: true
            },
            txtEmail: {
                required: true,
                maxlength: 255,
                email: true
            },
            txtAddress: {
                required: true,
                maxlength: 200,
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
                required: "Vui lòng nhập tên tài khoản",
                maxlength: "Tên Tài khoản không được vượt quá 50 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, số, dấu gạch ngang, gạch dưới, nháy đơn và không chứa khoảng trắng"
            },
            txtLastName: {
                required: "Vui lòng nhập Họ",
                maxlength: "Họ người dùng không được vượt quá 50 ký tự",
                pattern: "Họ người dùng chỉ chấp nhận chữ, khoảng trắng"
            },
            txtFirstName: {
                required: "Vui lòng nhập Tên",
                maxlength: "Tên Người dùng không được vượt quá 50 ký tự",
                pattern: "Tên người dùng chỉ chấp nhận chữ, khoảng trắng"
            },
            txtPhoneNumber: {
                required: "Vui lòng nhập số điện thoại",
                maxlength: "Số điện thoại không vượt quá 11 số",
                number: "Số điện thoại chỉ chấp nhận số"
            },
            txtEmail: {
                required: "Vui lòng nhập Email",
                maxlength: "Email không được vượt quá 255 ký tự",
                email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtAddress: {
                required: "Vui lòng nhập địa chỉ",
                maxlength: "Địa chỉ không được vượt quá 200 ký tự",
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
                maxlength: 50,
                pattern: /^[a-zA-Z0-9-'_]+$/
            },
            txtLastName: {
                required: true,
                maxlength: 50,
                pattern: /^[\p{L}\s]+$/u
            },
            txtFirstName: {
                required: true,
                maxlength: 50,
                pattern: /^[\p{L}\s]+$/u
            },
            txtPhoneNumber: {
                required: true,
                maxlength: 11,
                number: true
            },
            txtEmail: {
                required: true,
                maxlength: 255,
                email: true
            },
            txtAddress: {
                required: true,
                maxlength: 200,
            }
        },
        messages: {
            txtAccountUsername: {
                required: "Vui lòng nhập Tên Tài khoản",
                maxlength: "Tên Tài khoản không được vượt quá 50 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, số, dấu gạch ngang, gạch dưới, nháy đơn và không chứa khoảng trắng"
            },
            txtLastName: {
                required: "Vui lòng nhập họ",
                maxlength: "Họ người dùng không được vượt quá 50 ký tự",
                pattern: "Họ người dùng chỉ chấp nhận chữ, khoảng trắng"
            },
            txtFirstName: {
                required: "Vui lòng nhập Tên",
                maxlength: "Tên người dùng không được vượt quá 50 ký tự",
                pattern: "Tên người dùng chỉ chấp nhận chữ, khoảng trắng"
            },
            txtPhoneNumber: {
                required: "Vui lòng nhập số điện thoại",
                maxlength: "Số điện thoại không vượt quá 11 số",
                number: "Số điện thoại chỉ chấp nhận số"
            },
            txtEmail: {
                required: "Vui lòng nhập Email",
                maxlength: "Email không được vượt quá 255 ký tự",
                email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtAddress: {
                required: "Vui lòng nhập địa chỉ",
                maxlength: "Địa chỉ không được vượt quá 200 ký tự",
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });
    
    
    $(".update-user-account-form").validate({
        rules: {
            txtAccountUsername: {
                required: true,
                maxlength: 50,
                pattern: /^[a-zA-Z0-9-'_]+$/
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
                required: "Vui lòng nhập Tên Tài khoản",
                maxlength: "Tên Tài khoản không được vượt quá 50 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, số, dấu gạch ngang, gạch dưới, nháy đơn và không chứa khoảng trắng"
            },           
            txtAccountPassword: {
                required: "Vui lòng nhập mật khẩu",
                minlength: "Mật khẩu phải có ít nhất 8 ký tự"
            },
            txtAccountRePassword: {
                required: "Vui lòng nhập lại mật khẩu",
                minlength: "Mật khẩu phải có ít nhất 8 ký tự",
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
            txtvoucher_name: {
                required: true,
                maxlength: 100,
            },
            txtvoucher_code: {
                required: true,
                maxlength: 16,
                pattern: /^[A-Z0-9]+$/
            },
            txtvoucher_discount_percent: {
                required: true,
                range: [0, 100]
            },
            txtvoucher_quantity: {
                required: true
            }
        },
        messages: {
            txtvoucher_name: {
                required: "Vui lòng nhập tên Voucher",
                maxlength: "Tên Voucher không được vượt quá 100 ký tự"
            },
            txtvoucher_code: {
                required: "Vui lòng nhập mã code",
                maxlength: "Mã code không được vượt quá 16 ký tự",
                pattern: "Mã code chỉ chấp nhận chữ in hoa, số"
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


    $(".update-voucher-form").validate({
        rules: {
            txtvoucher_name: {
                required: true,
                maxlength: 100,
            },
            txtvoucher_code: {
                required: true,
                maxlength: 16,
                pattern: /^[A-Z0-9]+$/
            },
            txtvoucher_discount_percent: {
                required: true,
                range: [0, 100]
            },
            txtvoucher_quantity: {
                required: true
            }
        },
        messages: {
            txtvoucher_name: {
                required: "Vui lòng nhập tên Voucher",
                maxlength: "Tên Voucher không được vượt quá 100 ký tự"
            },
            txtvoucher_code: {
                required: "Vui lòng nhập mã code",
                maxlength: "Mã code không được vượt quá 16 ký tự",
                pattern: "Mã code chỉ chấp nhận chữ in hoa, số"
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
                maxlength: 50,
                pattern: /^[a-zA-Z0-9-'_]+$/
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
                maxlength: "Tên Tài khoản Người dùng không được vượt quá 50 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, số, dấu gạch ngang, gạch dưới, nháy đơn và không chứa khoảng trắng"
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
                maxlength: 50,
                pattern: /^[\p{L}\s]+$/u
            },
            txtFirstName: {
                required: true,
                maxlength: 50,
                pattern: /^[\p{L}\s]+$/u
            },
            txtPhoneNumber: {
                required: true,
                maxlength: 11,
                number: true
            },
            txtAddress: {
                required: true,
                maxlength: 255
            }
        },
        messages: {
            txtLastName: {
                required: "Vui lòng nhập họ",
                maxlength: "Họ Người dùng không được vượt quá 50 ký tự",
                pattern: "Họ Người dùng chỉ chấp nhận chữ, khoảng trắng"
            },
            txtFirstName: {
                required: "Vui lòng nhập tên",
                maxlength: "Tên Người dùng không được vượt quá 50 ký tự",
                pattern: "Tên Người dùng chỉ chấp nhận chữ, khoảng trắng"
            },
            txtPhoneNumber: {
                required: "Vui lòng nhập số điện thoại",
                maxlength: "Số điện thoại không được vượt quá 11 kí tự",
                number: "Số điện thoại chỉ chấp nhận số"
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
    
    $("#voucher-form").validate({
        rules: {
            voucherCode: {
                required: true,
                maxlength: 16,
            }
        },
        messages: {
            voucherCode: {
                required: "Vui lòng nhập mã giảm giá",
                maxlength: "Mã giảm giá không được vượt quá 16 ký tự"
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

    $(".add-role-form").validate({
        rules: {
            txtAccountUsername: {
                required: true,
                maxlength: 50,
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
                minlength: 8,
                equalTo: "#txtAdminAccountPassword"
            }
        },
        messages: {
            txtAccountUsername: {
                required: "Vui lòng nhập Tên Tài khoản (Username)",
                maxlength: "Tên Tài khoản Người dùng không được vượt quá 50 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, số, dấu gạch ngang, gạch dưới, nháy đơn và không chứa khoảng trắng"
            },
            txtAccountFullname: {
                required: "Vui lòng nhập tên đầy đủ",
                maxlength: "Tên đầy đủ không được vượt quá 100 ký tự",
                pattern: "Tên đầy đủ chỉ chấp nhận chữ, khoảng trắng"
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
                minlength: "Mật khẩu phải có ít nhất 8 ký tự",
                equalTo: "Mật khẩu không khớp"
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });

    $(".update-role-form").validate({
        rules: {
            txtAccountUsername: {
                required: true,
                maxlength: 50,
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
                minlength: 8
            },
            txtReAccountPassword: {
                equalTo: "#txtUpdateRoleAccountPassword"
            }
        },
        messages: {
            txtAccountUsername: {
                required: "Vui lòng nhập Tên Tài khoản (Username)",
                maxlength: "Tên Tài khoản không được vượt quá 50 ký tự",
                pattern: "Tên Tài khoản chỉ chấp nhận chữ, số, dấu gạch ngang, gạch dưới, nháy đơn và không chứa khoảng trắng"
            },
            txtAccountFullname: {
                required: "Vui lòng nhập tên đầy đủ",
                maxlength: "Tên đầy đủ không được vượt quá 100 ký tự",
                pattern: "Tên đầy đủ chỉ chấp nhận chữ, khoảng trắng"
            },
            txtEmail: {
                required: "Vui lòng nhập Email",
                maxlength: "Email không được vượt quá 255 ký tự",
                email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtAccountPassword: {
                minlength: "Mật khẩu phải có ít nhất 8 ký tự"
            },
            txtReAccountPassword: {
                equalTo: "Mật khẩu không khớp"
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
    });
}

$(".update-order-form").validate({
    rules: {
        txtOrderNote: {
            maxlength: 500
        },
        txtPhoneNumber: {
            required: true,
            maxlength: 11,
            number: true
        },
        txtAddress: {
            required: true,
            maxlength: 200,
        }

    },
    messages: {
        txtOrderNote: {
            maxlength: "Ghi chú không được vượt quá 50 ký tự",
        },
        txtPhoneNumber: {
            required: "Vui lòng nhập số điện thoại",
            maxlength: "Số điện thoại không vượt quá 11 số",
            number: "Số điện thoại chỉ chấp nhận số"
        },
        txtAddress: {
            required: "Vui lòng nhập địa chỉ đặt hàng",
            maxlength: "Địa chỉ không được vượt quá 200 ký tự",
        }
    },
    submitHandler: function (form) {
        // Handle form submission here
        form.submit();
    }
});

$(".checkout-form").validate({
        rules: {
            txtLastName: {
                required: true,
                maxlength: 50,
                pattern: /^[\p{L}\s]+$/u
            },
            txtFirstName: {
                required: true,
                maxlength: 50,
                pattern: /^[\p{L}\s]+$/u
            },
            txtPhone: {
                required: true,
                maxlength: 11,
                number: true
            },
            txtAddress: {
                required: true,
                maxlength: 255
            },
            txtVoucherCode: {
                maxlength: 16
            }
        },
        messages: {
            txtLastName: {
                required: "Vui lòng nhập họ",
                maxlength: "Họ Người dùng không được vượt quá 50 ký tự",
                pattern: "Họ Người dùng chỉ chấp nhận chữ, khoảng trắng"
            },
            txtFirstName: {
                required: "Vui lòng nhập tên",
                maxlength: "Tên Người dùng không được vượt quá 50 ký tự",
                pattern: "Tên Người dùng chỉ chấp nhận chữ, khoảng trắng"
            },
            txtPhone: {
                required: "Vui lòng nhập số điện thoại",
                maxlength: "Số điện thoại không được vượt quá 11 kí tự",
                number: "Số điện thoại chỉ chấp nhận số"
            },
            txtAddress: {
                required: "Vui lòng nhập địa chỉ của Người dùng",
                maxlength: "Địa chỉ không được vượt quá 255 kí tự"
            },
            txtVoucherCode: {
                maxlength: "Mã giảm giá không được vượt quá 16 kí tự"
            }
        },
        submitHandler: function (form) {
            // Handle form submission here
            form.submit();
        }
});
