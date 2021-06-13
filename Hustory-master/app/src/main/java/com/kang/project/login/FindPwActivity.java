package com.kang.project.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kang.project.R;

import java.util.HashMap;

public class FindPwActivity extends AppCompatActivity {

    private static final String TAG = "FindIdActivity";

    private EditText find_Pw_find_nameETXT;
    private EditText find_Pw_find_phoneETXT;
    private EditText find_Pw_find_IdETXT;

    private Button find_Pw_find_PwBTN;

    private FirebaseDatabase db;
    private DatabaseReference myRef;

    private FirebaseAuth mAuth;

    private String find_name;
    private String find_phone;
    private String find_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_find_pw);

        init();
    }

    private void init() {
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference();
        mAuth = FirebaseAuth.getInstance();

        find_Pw_find_nameETXT = findViewById(R.id.find_Pw_find_nameETXT);
        find_Pw_find_phoneETXT = findViewById(R.id.find_Pw_find_phoneETXT);
        find_Pw_find_IdETXT = findViewById(R.id.find_Pw_find_IdETXT);

        find_Pw_find_PwBTN = findViewById(R.id.find_Pw_find_PwBTN);

        View.OnClickListener onClickListener = v -> {
            switch (v.getId()) {
                case R.id.find_Pw_find_PwBTN:
                    find_name = find_Pw_find_nameETXT.getText().toString();
                    find_phone = find_Pw_find_phoneETXT.getText().toString();
                    find_email = find_Pw_find_IdETXT.getText().toString();

                    myRef.child("Member").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            int flag = 0;

                            if (!task.isSuccessful()) {
                                Log.i("firebase", "Error getting data", task.getException());
                            }
                            else {
                                Log.i("firebase", String.valueOf(task.getResult().getValue()));

                                for(DataSnapshot userSnapshot : task.getResult().getChildren()) {

                                    HashMap<String, Object> member = (HashMap<String, Object>) userSnapshot.getValue();

                                    String name = member.get("name").toString();
                                    String phone = member.get("phone").toString();
                                    String email = member.get("email").toString();

                                    if(name.equals(find_name) && phone.equals(find_phone) && email.equals(find_email)) {
                                        flag = 1;
                                        startToast("비밀번호는 " + member.get("pw").toString() + " 입니다.");
                                    }
                                }
                                if(flag == 0)
                                    startToast("입력 내용을 다시 확인해주세요.");
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        };
        find_Pw_find_PwBTN.setOnClickListener(onClickListener);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    private void startToast(String message) {
        Toast.makeText(FindPwActivity.this, message, Toast.LENGTH_SHORT).show();
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