package com.example.sqlitedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebHistoryItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Database database;

    ListView listViewCongViec;
    ArrayList<CongViec> arrayCongViec;
    CongViecAdapter adapter;
    int abc = 2;
    float xyz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewCongViec = findViewById(R.id.listviewCongViec);
        arrayCongViec = new ArrayList<>();

        adapter = new CongViecAdapter(this,R.layout.dong_cong_viec,arrayCongViec);
        listViewCongViec.setAdapter(adapter);
        // tạo database
        // context, namedata, con trỏ(factory), phiên bản
        database = new Database(this, "ghichu.sqlite", null,1);
        // tạo bảng
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");
        // insert data để thêm data
//        database.QueryData("INSERT INTO CongViec VALUES(null, 'Viet Ung dung ghi chu')");
        getData();
        Toast.makeText(this, "changed", Toast.LENGTH_SHORT).show();
    }

    private void getData(){
        arrayCongViec.clear();
        // select data bằng con trỏ cursor để đọc dữ liệu
        Cursor dataCongViec = database.GetData("SELECT * FROM CongViec");
        // moveToNext duyệt data tiếp theo mặc định true
        while (dataCongViec.moveToNext()){
            // id = 1
            String ten = dataCongViec.getString(1);
            int id = dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id,ten));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_cong_viec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuAdd){
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    private void DialogThem(){
        // lấy đường dẫn layout
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        View custom_view  = inflater.inflate(R.layout.dialog_them_cong_viec, null);

        // khởi tạo dialog
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setView(custom_view);// view layout new

        // nút khẳng định
        builder1.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText edtThem = custom_view.findViewById(R.id.edtThem);
                String tencv = edtThem.getText().toString();

                if (tencv.equals("") ){
                    Toast.makeText(MainActivity.this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    database.QueryData("INSERT INTO CongViec VALUES(null, '"+ tencv +"')");
                    getData();
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // nút phủ định
        builder1.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setCancelable(false); // ấn ngẫu nhiên bên ngoài dialog k bị tắt

        // build dialog
        builder1.create().show();
    }
    // final truy cập vào trong các hàm sâu bên trong
    public void DialogSua(String TenCV,final int IdCV){
        // lấy đường dẫn layout
        LayoutInflater inflater2 = MainActivity.this.getLayoutInflater();
        View custom_view2  = inflater2.inflate(R.layout.dialog_sua_cong_viec, null);

        // khởi tạo dialog
        AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
        builder2.setView(custom_view2);// view layout new


        EditText edtSua = custom_view2.findViewById(R.id.edtSua);
        edtSua.setText(TenCV);


        // nút khẳng định
        builder2.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenMoi = edtSua.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '" + tenMoi + "' WHERE Id = '" + IdCV + "' ");
                getData();
                Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setCancelable(false);
        // nút phủ định
        // ấn ngẫu nhiên bên ngoài dialog k bị tắt

        // build dialog
        builder2.create().show();
    }

    public void DialogXoa(String TenCV, final int IdCV){

        // khởi tạo dialog
        AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this);
        builder3.setTitle("Bạn có muốn xóa "+TenCV);

        // nút khẳng định
        builder3.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+ IdCV +"'");
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                getData();
            }
        });
        // nút phủ định
        builder3.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).setCancelable(false); // ấn ngẫu nhiên bên ngoài dialog k bị tắt

        // build dialog
        builder3.create().show();
    }

}
