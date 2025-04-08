package com.example.danh22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList; // Import ArrayList
import java.util.List;

public class DanhMucSanPham_Activity extends AppCompatActivity {
    private GridView grv;
    private ArrayList<SanPham> productList; // Change to ArrayList
    private SanPham_DanhMuc_Adapter productAdapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_san_pham);

        // Initialize the GridView and DatabaseHelper
        grv = findViewById(R.id.grv);
        dbHelper = new DatabaseHelper(this);

        // Retrieve nhomSpId from the Intent
        String nhomSpId = getIntent().getStringExtra("nhomSpId");

        // Check if nhomSpId is not null
        if (nhomSpId != null) {
            // Get the list of products by nhomSpId
            List<SanPham> tempProductList = dbHelper.getProductsByNhomSpId(nhomSpId); // Use a temporary variable
            if (tempProductList != null && !tempProductList.isEmpty()) {
                // Convert List to ArrayList
                productList = new ArrayList<>(tempProductList);
                // Initialize and set the adapter with the product list
                productAdapter = new SanPham_DanhMuc_Adapter(this, productList, false);
                grv.setAdapter(productAdapter);
            } else {
                Toast.makeText(this, "Không tìm thấy sản phẩm nào trong nhóm này!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "ID nhóm sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
        }

        setupNavigationButtons();
        grv.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy sản phẩm tại vị trí được click
            SanPham sanPham = productList.get(position);

            // Tạo Intent để chuyển sang ChiTietSanPham_Activity
            Intent intent = new Intent(DanhMucSanPham_Activity.this, ChiTietSanPham_Activity.class);

            // Truyền dữ liệu sản phẩm qua Intent
            intent.putExtra("masp", sanPham.getMasp());
            intent.putExtra("tensp", sanPham.getTensp());
            intent.putExtra("dongia", sanPham.getDongia());
            intent.putExtra("mota", sanPham.getMota());
            intent.putExtra("ghichu", sanPham.getGhichu());
            intent.putExtra("soluongkho", sanPham.getSoluongkho());
            intent.putExtra("maso", sanPham.getMansp());
            intent.putExtra("anh", sanPham.getAnh());

            // Chuyển đến trang ChiTietSanPham_Activity
            startActivity(intent);
        });

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
}
