/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
$(document).ready(validateForm);
function validateForm() {
  $.validator.addMethod("googleDrivePattern", function (value, element) {
    // Define the regex patterns
    let pattern1 = /^https:\/\/drive\.google\.com\/uc\?export=view&id=.*/;
    let pattern2 = /^https:\/\/drive\.google\.com\/file\/d\/.*\/view\?usp=drive_link$/;

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
        maxlength: 50
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
        maxlength: "Tên món không dài quá 50 kí tự"
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
  
  $(".update-food-form").validate({
    rules: {
      txtFoodTypeID: {
        required: true
      },
      txtFoodName: {
        required: true,
        maxlength: 50
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
        maxlength: "Tên món không dài quá 50 kí tự"
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
        maxlength: 50,
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
        maxlength: "Tên Tài khoản Người dùng không được vượt quá 50 ký tự",
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
        maxlength: 50
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
        maxlength: "Tên Tài khoản Người dùng không được vượt quá 50 ký tự"
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

    $("#login-form").validate({
        rule: {
            txtEmail: {
            required: true,
            maxlength: 255,
            email: true
            },
            txtPassword: {
            required: true,
            minlength: 8
            }
        },
        messages: {
            txtEmail: {
            required: "Vui lòng nhập Email",
            maxlength: "Email không được vượt quá 255 ký tự",
            email: "Vui lòng nhập địa chỉ Email hợp lệ"
            },
            txtPassword: {
            required: "Vui lòng nhập mật khẩu",
            minlength: "Mật khẩu mới phải có ít nhất 8 ký tự"
            }
        },
        submitHandler: function (form) {
        // Handle form submission here
        form.submit();
        }
    });
    
    $("#signup-form").validate({
        rule: {
            txtAccountUsername: {
            required: true,
            maxlength: 50
            },
            txtAccountEmail: {
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
            maxlength: "Tên Tài khoản Người dùng không được vượt quá 50 ký tự"
            },
            txtAccountEmail: {
            required: "Vui lòng nhập Email",
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
        rule: {
            txtLastName: {
            required: true,
            maxlength: 40
            },
            txtFirstName: {
            required: true,
            maxlength: 10
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
            maxlength: "Họ Người dùng không được vượt quá 40 ký tự"
            },
            txtFirstName: {
            required: "Vui lòng nhập tên",
            maxlength: "Tên Người dùng không được vượt quá 40 ký tự"
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
}