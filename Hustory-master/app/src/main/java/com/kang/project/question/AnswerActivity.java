package com.kang.project.question;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kang.project.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AnswerActivity extends AppCompatActivity {

    AnswerAdapter answerAdapter;
    Button button_answer;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private String uId;

    private ArrayList<String> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_answer);
        uId = currentUser.getUid();
        init();
    }

    public void init() {
        answerAdapter = new AnswerAdapter();
        ListView listView = (ListView) findViewById(R.id.list_answer);
        listView.setAdapter(answerAdapter);

        TextView question_name = findViewById(R.id.question_name);
        TextView q_person_name = findViewById(R.id.q_person_name);
        TextView question_time = findViewById(R.id.question_time);
        TextView question_content = findViewById(R.id.question_content);

        Intent intent = getIntent();

        // 앞의 질문 아이템 포지션 값 가져옴
        String q_Num = intent.getStringExtra("q_Num");
        String name = intent.getStringExtra("name");

        Log.i("q_Num ", q_Num);
        Log.i("name ", name);

        myRef.child("Question").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.i("!task.isSuccessful() ", q_Num);
                }
                else {
                    if (String.valueOf(task.getResult().getValue()).equals("null")) {
                        Log.i("null ", q_Num);
                        Log.i("null ", String.valueOf(task.getResult().getValue()));
                        return;
                    }
                    else {
                        for(DataSnapshot userSnapshot : task.getResult().getChildren()) {
                            HashMap<String, Object> member = (HashMap<String, Object>) userSnapshot.getValue();
                            String q_title = member.get("q_title").toString();
                            String q_content = member.get("q_content").toString();
                            String q_time = member.get("q_time").toString();

                            String q_date = member.get("q_date").toString();
                            String firebase_q_Num = member.get("q_Num").toString();
                            String q_count = member.get("q_count").toString();

                            Log.i("firebase_q_Num ", firebase_q_Num);

                            if(q_Num.equals(firebase_q_Num)){
                                question_name.setText(q_title);
                                q_person_name.setText(name);
                                question_time.setText(q_date);
                                question_content.setText(q_content);
                            }
                        }
                        answerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        answerAdapter.addItem("프로젝트 평가 요소가 궁금합니다. 프로젝트 평가 요소가 궁금합니다. 프로젝트 평가 요소가 궁금합니다.", "홍길동", "1분 전");
        answerAdapter.addItem("프로젝트 평가 요소가 궁금합니다. 프로젝트 평가 요소가 궁금합니다. 프로젝트 평가 요소가 궁금합니다.", "홍길동", "5분 전");

        button_answer = (Button) findViewById(R.id.button_answer);
        button_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AnswerActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_question, null);
                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });



        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
