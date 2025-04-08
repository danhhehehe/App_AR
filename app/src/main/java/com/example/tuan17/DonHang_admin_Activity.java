package com.example.danh22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DonHang_admin_Activity extends AppCompatActivity implements DonHang_Adapter.OnOrderDeletedListener {

    private Database database;
    private ListView listView;
    private DonHang_Adapter donHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_admin);

        // Khởi tạo các thành phần
        listView = findViewById(R.id.listViewChiTiet);
        database = new Database(this, "banhang.db", null, 1);

        // Thiết lập các nút điều hướng
        setupNavigationButtons();

        // Tạo bảng nếu chưa tồn tại
        createTableIfNotExists();
        loadDonHang(); // Gọi phương thức loadDonHang

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Order order = donHangAdapter.getItem(i);
//                if (order != null) {
//                    // Hiển thị Toast với ID đơn hàng
//                    Toast.makeText(DonHang_admin_Activity.this, "ID đơn hàng: " + order.getId(), Toast.LENGTH_SHORT).show();
//
//                    // Gửi thông tin đơn hàng qua Intent
//                    Intent intent = new Intent(DonHang_admin_Activity.this, ChiTietDonHang_Admin_Activity.class);
//                    intent.putExtra("donHangId", String.valueOf(order.getId())); // Đảm bảo rằng ID là chuỗi
//                    startActivity(intent);
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onOrderDeleted() {
        // Tải lại danh sách đơn hàng
        loadDonHang();
    }

    private void setupNavigationButtons() {
        ImageButton btntrangchu = findViewById(R.id.btntrangchu);
        btntrangchu.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TrangchuAdmin_Activity.class)));

        ImageButton btncanhan = findViewById(R.id.btncanhan);
        btncanhan.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            Intent intent = isLoggedIn ?
                    new Intent(getApplicationContext(), TrangCaNhan_admin_Activity.class) :
                    new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);
        });

        ImageButton btndonhang = findViewById(R.id.btndonhang);
        btndonhang.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), DonHang_admin_Activity.class)));

        ImageButton btnsanpham = findViewById(R.id.btnsanpham);
        btnsanpham.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Sanpham_admin_Activity.class)));

        ImageButton btnnhomsp = findViewById(R.id.btnnhomsp);
        btnnhomsp.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Nhomsanpham_admin_Actvity.class)));

        ImageButton btntaikhoan = findViewById(R.id.btntaikhoan);
        btntaikhoan.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), Taikhoan_admin_Activity.class)));
    }

    private void createTableIfNotExists() {
        // Tạo bảng đơn hàng nếu chưa tồn tại
        database.QueryData("CREATE TABLE IF NOT EXISTS Dathang (" +
                "id_dathang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenkh TEXT, " +
                "diachi TEXT, " +
                "sdt TEXT, " +
                "tongthanhtoan REAL, " +
                "ngaydathang DATETIME DEFAULT CURRENT_TIMESTAMP);");
    }

    private void loadDonHang() {
        // Lấy danh sách đơn hàng từ cơ sở dữ liệu
        List<Order> orders = database.getAllDonHang();
        if (orders.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy đơn hàng nào!", Toast.LENGTH_SHORT).show();
        } else {
            // Sử dụng DonHangAdapter để hiển thị danh sách đơn hàng
            donHangAdapter = new DonHang_Adapter(this, orders, database, this); // Truyền database và listener vào adapter
            listView.setAdapter(donHangAdapter); // Gán adapter cho ListView
        }
    }
}