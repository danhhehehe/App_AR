package com.example.danh22;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DonHang_Adapter extends ArrayAdapter<Order> {
    private ArrayList<Order> orderList;
    private Database database;
    private Context context;
    private OnOrderDeletedListener listener; // Thêm listener

    public DonHang_Adapter(Context context, List<Order> orders, Database database, OnOrderDeletedListener listener) {
        super(context, 0, orders);
        this.orderList = new ArrayList<>(orders);
        this.database = database;
        this.context = context;
        this.listener = listener; // Khởi tạo listener
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ds_donhang, parent, false);
        }

        Order order = getItem(position);
        TextView txtMadh = convertView.findViewById(R.id.txtMahd);
        TextView txtTenKh = convertView.findViewById(R.id.txtTenKh);
        TextView txtDiaChi = convertView.findViewById(R.id.txtDiaChi);
        TextView txtSdt = convertView.findViewById(R.id.txtSdt);
        TextView txtTongThanhToan = convertView.findViewById(R.id.txtTongThanhToan);
        TextView txtNgayDatHang = convertView.findViewById(R.id.txtNgayDatHang);
        ImageButton btnhuydon = convertView.findViewById(R.id.imgxoa);
        ImageButton btnchitiet = convertView.findViewById(R.id.imgnext);
        txtTenKh.setText(order.getTenKh());
        txtDiaChi.setText(order.getDiaChi());
        txtSdt.setText(order.getSdt());
        txtTongThanhToan.setText(String.valueOf(order.getTongTien()));
        txtNgayDatHang.setText(order.getNgayDatHang());
        txtMadh.setText(String.valueOf(order.getId()));
        btnchitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order != null) {
                    // Gửi thông tin đơn hàng qua Intent
                    Intent intent = new Intent(context, ChiTietDonHang_Admin_Activity.class);
                    intent.putExtra("donHangId", String.valueOf(order.getId())); // Đảm bảo rằng ID là chuỗi
                    context.startActivity(intent);
                }
            }
        });
        btnhuydon.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xóa đơn hàng này?")
                    .setPositiveButton("Có", (dialog, which) -> {
                        SQLiteDatabase db = database.getWritableDatabase();
                        int rowsAffected = db.delete("dathang", "id_dathang = ?", new String[]{String.valueOf(order.getId())});
                        if (rowsAffected > 0) {
                            orderList.remove(position); // Xóa từ danh sách
                            Toast.makeText(context, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                            listener.onOrderDeleted(); // Gọi listener để cập nhật dữ liệu
                        } else {
                            Toast.makeText(context, "Không tìm thấy đơn hàng để xóa", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        return convertView;
    }

    // Interface để lắng nghe sự kiện xóa
    public interface OnOrderDeletedListener {
        void onOrderDeleted();
    }
}