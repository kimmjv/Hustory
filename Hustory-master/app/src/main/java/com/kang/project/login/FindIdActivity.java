package com.kang.project.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kang.project.R;

import java.util.HashMap;

public class FindIdActivity extends AppCompatActivity {

    private static final String TAG = "FindIdActivity";

    private EditText find_nameETXT;
    private EditText find_phoneETXT;

    private Button find_IdBTN;

    private FirebaseDatabase db;
    private DatabaseReference myRef;

    private String find_name;
    private String find_phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_find_id);

        init();
    }

    private void init() {
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();

        find_nameETXT = findViewById(R.id.find_nameETXT);
        find_phoneETXT = findViewById(R.id.find_phoneETXT);

        find_IdBTN = findViewById(R.id.find_IdBTN);

        View.OnClickListener onClickListener = v -> {
            switch (v.getId()) {
                case R.id.find_IdBTN:
                    find_name = find_nameETXT.getText().toString();
                    find_phone = find_phoneETXT.getText().toString();

                    myRef.child("Member").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            int flag = 0;

                            if (!task.isSuccessful()) {
                                Log.i("firebase", "Error getting data", task.getException());
                            }
                            else {
                                //Log.i("firebase", String.valueOf(task.getResult().getValue()));

                                for(DataSnapshot userSnapshot : task.getResult().getChildren()) {
                                    HashMap<String, Object> member = (HashMap<String, Object>) userSnapshot.getValue();

                                    String name = member.get("name").toString();
                                    String phone = member.get("phone").toString();

                                    if(name.equals(find_name) && phone.equals(find_phone)) {
                                        flag = 1;
                                        startToast("아이디는 " + member.get("email").toString() + " 입니다.");
                                    }
                                }
                                if(flag == 0)
                                    startToast("일치하는 아이디가 없습니다.");
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        };

        find_IdBTN.setOnClickListener(onClickListener);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startToast(String message) {
        Toast.makeText(FindIdActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}