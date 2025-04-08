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

public class DonHang_User_Activity extends AppCompatActivity implements DonHang_Adapter.OnOrderDeletedListener {
    private Database database;
    private ListView listView;
    private DonHang_Adapter donHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_user);

        listView = findViewById(R.id.listViewChiTiet);
        database = new Database(this, "banhang.db", null, 1);

        // Tạo bảng nếu chưa tồn tại
        createTableIfNotExists();

        String tenDN = getIntent().getStringExtra("tendn");
        if (tenDN == null || tenDN.isEmpty()) {
            Toast.makeText(this, "Tên đăng nhập không hợp lệ!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setupNavigationButtons();
        loadDonHang(tenDN); // Gọi phương thức loadDonHang với tenDN

//        listView.setOnItemClickListener((parent, view, position, id) -> {
//            Order order = donHangAdapter.getItem(position);
//            if (order != null) {
//                Toast.makeText(DonHang_User_Activity.this, "ID đơn hàng: " + order.getId(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(DonHang_User_Activity.this, ChiTietDonHang_Activity.class);
//                intent.putExtra("donHangId", String.valueOf(order.getId()));
//                startActivity(intent);
//            }
//        });
    }
    private void setupNavigationButtons() {
        ImageButton btntrangchu = findViewById(R.id.btntrangchu);
        btntrangchu.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TrangchuNgdung_Activity.class)));

        ImageButton btncanhan = findViewById(R.id.btncanhan);
        btncanhan.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            Intent intent = isLoggedIn ?
                    new Intent(getApplicationContext(), TrangCaNhan_nguoidung_Activity.class) :
                    new Intent(getApplicationContext(), Login_Activity.class);
            startActivity(intent);
        });

        ImageButton btntimkiem = findViewById(R.id.btntimkiem);
        btntimkiem.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),TimKiemSanPham_Activity.class)));

        ImageButton btncard = findViewById(R.id.btncart);
        btncard.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),GioHang_Activity.class)));

           }
    @Override
    public void onOrderDeleted() {
        // Tải lại danh sách đơn hàng
        loadDonHang(getIntent().getStringExtra("tendn"));
    }

    private void createTableIfNotExists() {
        database.QueryData("CREATE TABLE IF NOT EXISTS Dathang (" +
                "id_dathang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenkh TEXT, " +
                "diachi TEXT, " +
                "sdt TEXT, " +
                "tongthanhtoan REAL, " +
                "ngaydathang DATETIME DEFAULT CURRENT_TIMESTAMP);");
    }

    private void loadDonHang(String tenKh) {
        List<Order> orders = database.getDonHangByTenKh(tenKh);
        if (orders.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy đơn hàng cho khách hàng này!", Toast.LENGTH_SHORT).show();
        } else {
            donHangAdapter = new DonHang_Adapter(this, orders, database, this); // Cài đặt listener
            listView.setAdapter(donHangAdapter);
        }
    }
}