package com.kang.project.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kang.project.R;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private FirebaseAuth mAuth;

    private EditText emailETXT;
    private EditText passwordETXT;
    private EditText passwordCheckETXT;
    private EditText nameETXT;
    private EditText phoneETXT;

    private Button signUpBTN;

    private RadioButton studentBTN;
    private RadioButton mentorBTN;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference();
    private FirebaseUser currentUser;

    private String email = "";
    private String password = "";
    private String passwordCheck = "";
    private String name = "";
    private String phone = "";
    private String role = "";
    private String userId = "";

    // 비밀번호 정규식 : 문자 A-Z a-z 0-9 특수문자 : ~!@#$%^&*? 입력가능 8~15자리
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[~!@#$%^&*?])[A-Za-z[0-9]~!@#$%^&*?]{8,15}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // 레이아웃 요소 초기화
        init();
    }

    private void init() {
        emailETXT = findViewById(R.id.signUp_emailETXT);
        passwordETXT = findViewById(R.id.signUp_passwordETXT);
        passwordCheckETXT = findViewById(R.id.passwordCheckETXT);
        nameETXT = findViewById(R.id.nameETXT);
        phoneETXT = findViewById(R.id.phoneETXT);

        signUpBTN = findViewById(R.id.signUpBTN);

        studentBTN = findViewById(R.id.studentBTN);
        mentorBTN = findViewById(R.id.mentorBTN);

        View.OnClickListener onClickListener = v -> {
            switch (v.getId()) {
                case R.id.signUpBTN:
                    signUp();
                    break;
                default:
                    break;
            }
        };

        signUpBTN.setOnClickListener(onClickListener);

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> {
            switch (buttonView.getId()) {
                case R.id.studentBTN:
                    role = buttonView.getText().toString();
                    break;
                case R.id.mentorBTN:
                    role = buttonView.getText().toString();
                    break;
            }
        };

        studentBTN.setOnCheckedChangeListener(onCheckedChangeListener);
        mentorBTN.setOnCheckedChangeListener(onCheckedChangeListener);

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }


    private void signUp() {
        email = emailETXT.getText().toString();
        password = passwordETXT.getText().toString();
        passwordCheck = passwordCheckETXT.getText().toString();
        name = nameETXT.getText().toString();
        phone = phoneETXT.getText().toString();

        if(isValidEmail()) {
            if(isValidPwd()) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    // 로그인 성공
                                    Log.i(TAG, "createUserWithEmail : Success");

                                    currentUser = mAuth.getCurrentUser();
                                    userId = currentUser.getUid();
                                    Log.d(TAG, userId);

                                    // firebase에 데이터 저장
                                    //HashMap<String, Object> childUpdates = new HashMap<>();
                                    Member member = new Member(email, password, name, phone, role);
                                    //Map<String, Object> postValue = member.toMap();
                                    myRef.child("Member").child(userId).setValue(member);
                                    //childUpdates.put("/Member/" + myRef.child("Member").push().getKey(), postValue);
                                    //myRef.updateChildren(childUpdates);

                                    startToast("회원가입을 성공했습니다.");
                                    startLoginAcivity();
                                }
                                else {
                                    // If sign in fails, display a message to the user
                                    Log.i(TAG, "createUserWithEmail : Fail", task.getException());
                                    startToast("이미 가입된 이메일입니다.");
                                }
                            }
                        });
            }
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startLoginAcivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    // 비밀번호 유효성 검사
    private boolean isValidPwd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            Toast.makeText(this, "비밀번호 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!password.equals(passwordCheck)){
            Toast.makeText(this, "비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
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
