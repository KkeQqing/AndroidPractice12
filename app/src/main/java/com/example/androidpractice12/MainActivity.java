package com.example.androidpractice12;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MySQLiteHelper dbHelper;
    private ListView listView;
    private Button btnAdd;
    private List<User> userList;
    private ArrayAdapter<User> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MySQLiteHelper(this);
        listView = findViewById(R.id.listViewUsers);
        btnAdd = findViewById(R.id.btnAdd);

        // 加载用户数据
        loadUserData();

        // 点击 "添加" 按钮，跳转到 AddUserActivity
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });

        // 长按列表项，弹出删除确认对话框
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = userList.get(position);
                showDeleteDialog(selectedUser);
                return true; // 返回 true 表示事件已被消费
            }
        });

        // 点击列表项，跳转到 UserDetailActivity 查看详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User selectedUser = userList.get(position);
                Intent intent = new Intent(MainActivity.this, UserDetailActivity.class);
                intent.putExtra("user_detail", selectedUser);
                startActivity(intent);
            }
        });
    }

    // 从数据库加载数据并更新列表
    private void loadUserData() {
        userList = dbHelper.getAllUsers();
        // 使用一个简单的布局 android.R.layout.simple_list_item_1 来显示 User 对象的 toString() 结果
        // 为了更自定义的显示，我们使用我们创建的 list_item.xml 和一个自定义的 Adapter
        // 这里为了简化，先使用一个简单的 ArrayAdapter 并重写 User 的 toString() 方法
        // 更好的方式是创建一个自定义的 Adapter，但为了快速实现，我们先这样处理

        // 让我们使用自定义的 list_item.xml
        adapter = new ArrayAdapter<User>(this, R.layout.list_item, R.id.textViewUsername, userList) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                User user = userList.get(position);
                android.widget.TextView tvUsername = view.findViewById(R.id.textViewUsername);
                android.widget.TextView tvPhone = view.findViewById(R.id.textViewPhone);
                tvUsername.setText(user.getUsername());
                tvPhone.setText(user.getPhone());
                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    // 显示删除确认对话框
    private void showDeleteDialog(final User user) {
        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("确定要删除用户 " + user.getUsername() + " 吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean isDeleted = dbHelper.deleteUser(user.getUsername());
                        if (isDeleted) {
                            Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            loadUserData(); // 重新加载数据以刷新列表
                        } else {
                            Toast.makeText(MainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    // 当从 AddUserActivity 返回时，刷新列表
    @Override
    protected void onResume() {
        super.onResume();
        loadUserData();
    }
}