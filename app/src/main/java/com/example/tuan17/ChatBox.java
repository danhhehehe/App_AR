package com.example.danh22;

public class ChatBox {
    public String getResponse(String userInput) {
        // Chuyển đổi input về chữ thường để dễ so sánh
        String input = userInput.toLowerCase();

        // Logic phản hồi với từ ngữ gần đúng
        if (input.contains("xin chào")) {
            return "Chào bạn!";
        } else if (input.contains("giá trái cây")) {
            return "Giá trái cây hôm nay có nhiều loại bạn muốn hỏi loại nào!";
        } else if (input.contains("hoa quả nhập từ đâu")) {
            return "Hoa quả nhập từ nhiều quốc gia khác nhau.Bạn muốn hỏi về trái cây nào";
        } else if (input.contains("nhập từ")) {
            return "Trái cây của shop thường được nhập từ các nước như Mỹ, Úc, và các nước trong khu vực Đông Nam Á.";
        } else if (input.contains("giá nho")) {
            return "Giá Nho hôm nay là nho đỏ 75000đ/1kg , nho xanh là 60000đ/ 1kg.";
        }else if (input.contains("giá cam")) {
            return "Giá cam hôm nay là cam sành 30000đ/ 1kg  , cam canh 22000đ/ 1kg.";
        }else if (input.contains("giá táo")) {
            return "Giá táo tây bắc hôm nay là 23000đ/ 1kg , táo mỹ 40000đ/ 1kg.";
        }else if (input.contains("giá dưa hấu")) {
            return "Giá dưa hấu hôm nay là 12000đ/ 1kg.";
        }else if (input.contains("giá lựu")) {
            return "Giá lựu đỏ hôm nay là 45000đ/ 1kg.";
        }else if (input.contains("giá kiwi")) {
            return "Giá kiwi hôm nay là 35000đ/ 1kg.";
        }else if (input.contains("giá đu đủ")) {
            return "Giá đu đủ hôm nay là 16000đ/ 1kg.";
        }else if (input.contains("giá đào")) {
            return "Giá đào hôm nay là  đào mỹ 55000đ/1kg, đào hồng là 45000đ/ 1kg.";
        }else if (input.contains("giá bơ")) {
            return "Giá bơ hôm nay là bơ 34 là 21000đ/1kg, bơ tròn là 17000đ/ 1kg.";
        }else if (input.contains("thông tin liên hệ")) {
            return "số điện thoại : 0367456697";
        }else if (input.contains("tên của")) {
            return "Quý Bà Rupbis";
        }


        return "Xin lỗi, tôi không hiểu bạn nói gì?.Vui Lòng liên hệ sdt của chủ doanh nghiệp :0367456697 để được biết thêm chi tiết";
    }
}