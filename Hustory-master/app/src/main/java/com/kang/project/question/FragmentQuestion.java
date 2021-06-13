package com.kang.project.question;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kang.project.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentQuestion extends Fragment {
    int flag = 1;
    private final String TAG = "FragmentQuestion";
    View view;

    private Context mContext;
    private FloatingActionButton fab_main, fab_sub1, fab_sub2;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = db.getReference();
    private FirebaseUser currentUser;
    private String uId;

    private ArrayList<String> key_Arr = new ArrayList<>();
    private ArrayList<String> name_Arr = new ArrayList<>();
    private ArrayList<String> count_Arr = new ArrayList<>();
    private ArrayList<String> id_Arr = new ArrayList<>();

    TextView new_orderTXT;
    TextView hot_orderTXT;
    TextView my_orderTXT;
    LinearLayout sort;

    QuestionAdapter questionAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (flag == 1) {
            view = inflater.inflate(R.layout.fragment_question, container, false);
            init_student();
        } else if (flag == 2) {
            view = inflater.inflate(R.layout.fragment_question_professor, container, false);
            //init_professor();
        }

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        arrayClear();
//        changeBTN_bg(R.id.new_orderTXT);
//
//        myRef.child("Question").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.i(TAG, "onDataChange()");
//                if(snapshot != null) {
//                    for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        HashMap<String, Object> member = (HashMap<String, Object>) dataSnapshot.getValue();
//                        String id = member.get("id").toString();
//                        String q_Num = member.get("q_Num").toString();
//                        String q_title = member.get("q_title").toString();
//                        String q_content = member.get("q_content").toString();
//                        String q_date = member.get("q_date").toString();
//                        String q_time = member.get("q_time").toString();
//                        String q_count = member.get("q_count").toString();
//                        String q_like = member.get("q_like").toString();
//                        String q_dislike = member.get("q_dislike").toString();
//
//                        myRef.child("Member").child(id).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                String name = (String) task.getResult().getValue();
//                                Log.i("name ", name);
//                                name_Arr.add(0, name);
//                            }
//                        });
//
//                        questionAdapter.addItemDESC(0, id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);
//                        key_Arr.add(0, q_Num);
//                        count_Arr.add(0, q_count);
//                        id_Arr.add(0, id);
//                    }
//                }
//                else {
//                    Log.i(TAG, "Firebase Data 찾지 못함!!");
//                    questionAdapter.clear();
//                }
//                questionAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init_student() {
        Bundle bundle = getArguments();

        questionAdapter = new QuestionAdapter();
        ListView listview1 = (ListView) view.findViewById(R.id.list_question);
        listview1.setAdapter(questionAdapter);

        new_orderTXT = view.findViewById(R.id.new_orderTXT);
        hot_orderTXT = view.findViewById(R.id.hot_orderTXT);
        my_orderTXT = view.findViewById(R.id.my_orderTXT);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uId = currentUser.getUid();

        arrayClear();

        // 처음 리스트뷰 세팅
        if(bundle == null) {
            myRef.child("Question").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i(TAG, "onDataChange()");
                    if (snapshot != null) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            HashMap<String, Object> member = (HashMap<String, Object>) dataSnapshot.getValue();
                            String id = member.get("id").toString();
                            String q_Num = member.get("q_Num").toString();
                            String q_title = member.get("q_title").toString();
                            String q_content = member.get("q_content").toString();
                            String q_date = member.get("q_date").toString();
                            String q_time = member.get("q_time").toString();
                            String q_count = member.get("q_count").toString();
                            String q_like = member.get("q_like").toString();
                            String q_dislike = member.get("q_dislike").toString();

                            myRef.child("Member").child(id).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    String name = (String) task.getResult().getValue();
                                    name_Arr.add(0, name);
                                }
                            });

                            questionAdapter.addItemDESC(0, id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);
                            key_Arr.add(0, q_Num);
                            count_Arr.add(0, q_count);
                            id_Arr.add(0, id);
                        }
                    } else {
                        Log.i(TAG, "Firebase Data 찾지 못함!!");
                        questionAdapter.clear();
                    }
                    questionAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        // 최신순, 조회순, 내질문 버튼 클릭 리스너
        View.OnClickListener onClickListener = v -> {
            switch (v.getId()) {
                // 최신순 버튼을 누르면
                case R.id.new_orderTXT:
                    // 각 arrayList와 adapter 클리어
                    arrayClear();

                    // 버튼 색 변경
                    changeBTN_bg(v.getId());

                    // Question에 있는 질문들 다 가져와서 최신순으로 정렬하여 출력
                    myRef.child("Question").addValueEventListener(new ValueEventListener() {
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i(TAG, "onDataChange()");
                            if (snapshot != null) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    HashMap<String, Object> member = (HashMap<String, Object>) dataSnapshot.getValue();
                                    String id = member.get("id").toString();
                                    String q_Num = member.get("q_Num").toString();
                                    String q_title = member.get("q_title").toString();
                                    String q_content = member.get("q_content").toString();
                                    String q_date = member.get("q_date").toString();
                                    String q_time = member.get("q_time").toString();
                                    String q_count = member.get("q_count").toString();
                                    String q_like = member.get("q_like").toString();
                                    String q_dislike = member.get("q_dislike").toString();

                                    myRef.child("Member").child(id).child("name").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String name = (String) snapshot.getValue();
                                            name_Arr.add(0, name);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    questionAdapter.addItemDESC(0, id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);
                                    key_Arr.add(0, q_Num);
                                    count_Arr.add(0, q_count);
                                    id_Arr.add(0, id);
                                }
                                questionAdapter.notifyDataSetChanged();
                            }
                            else {
                                Log.i(TAG, "Firebase Data 찾지 못함!!");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    break;
                case R.id.hot_orderTXT:
                    arrayClear();
                    changeBTN_bg(v.getId());

                    // 모든 질문을 조회수가 높은 순으로 정렬하여 출력
                    myRef.child("Question").orderByChild("q_count").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i(TAG, "onDataChange()");
                            if (snapshot != null) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    HashMap<String, Object> member = (HashMap<String, Object>) dataSnapshot.getValue();
                                    String id = member.get("id").toString();
                                    String q_Num = member.get("q_Num").toString();
                                    String q_title = member.get("q_title").toString();
                                    String q_content = member.get("q_content").toString();
                                    String q_date = member.get("q_date").toString();
                                    String q_time = member.get("q_time").toString();
                                    String q_count = member.get("q_count").toString();
                                    String q_like = member.get("q_like").toString();
                                    String q_dislike = member.get("q_dislike").toString();

                                    myRef.child("Member").child(id).child("name").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String name = (String) snapshot.getValue();
                                            name_Arr.add(0, name);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    questionAdapter.addItemDESC(0, id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);
                                    key_Arr.add(0, q_Num);
                                    count_Arr.add(0, q_count);
                                    id_Arr.add(0, id);
                                }
                                questionAdapter.notifyDataSetChanged();
                            }
                            else {
                                Log.i(TAG, "Firebase Data 찾지 못함!!");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    break;
                // 내가 작성한 질문만 볼 수 있도록 함
                case R.id.my_orderTXT:
                    arrayClear();
                    changeBTN_bg(v.getId());

                    myRef.child("Member").child(uId).child("Q_List").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i(TAG, "onDataChange()");
                            if (snapshot != null) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    HashMap<String, Object> member = (HashMap<String, Object>) dataSnapshot.getValue();
                                    String id = member.get("id").toString();
                                    String q_Num = member.get("q_Num").toString();
                                    String q_title = member.get("q_title").toString();
                                    String q_content = member.get("q_content").toString();
                                    String q_date = member.get("q_date").toString();
                                    String q_time = member.get("q_time").toString();
                                    String q_count = member.get("q_count").toString();
                                    String q_like = member.get("q_like").toString();
                                    String q_dislike = member.get("q_dislike").toString();

                                    myRef.child("Member").child(id).child("name").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String name = (String) snapshot.getValue();
                                            name_Arr.add(0, name);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    questionAdapter.addItemDESC(0, id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);
                                    key_Arr.add(0, q_Num);
                                    count_Arr.add(0, q_count);
                                    id_Arr.add(0, id);
                                }
                                questionAdapter.notifyDataSetChanged();
                            }
                            else {
                                Log.i(TAG, "Firebase Data 찾지 못함!!");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    break;
            }
        };

        new_orderTXT.setOnClickListener(onClickListener);
        hot_orderTXT.setOnClickListener(onClickListener);
        my_orderTXT.setOnClickListener(onClickListener);

        // 질문 리스트 아이템 클릭 이벤트 리스너
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), AnswerActivity.class);
                String q_Num = key_Arr.get(position);
                String name = name_Arr.get(position);
                String writer_id = id_Arr.get(position);

                // 아이템을 클릭하면 조회수 1 증가
                int count = Integer.parseInt(count_Arr.get(position)) + 1;
                Log.i("count", "" + count);
                myRef.child("Member").child(writer_id).child("Q_List").child(q_Num).child("q_count").setValue(count);
                myRef.child("Question").child(q_Num).child("q_count").setValue(count);

                // 현재 클릭된 아이템의 질문번호(q_Num)과 작성자 이름을 넘겨줌
                intent.putExtra("q_Num", q_Num);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });


        // search ------------
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(20 * dm.density);

        Typeface typeface = getResources().getFont(R.font.jalnan);
        sort = (LinearLayout) view.findViewById(R.id.sort);

        if(bundle != null){
            String data = bundle.getString("KEY_SEARCH");

            TextView sort_search = new TextView(getContext());
            sort_search.setText("Q  " + data);
            sort_search.setTextColor(Color.parseColor("#FFFFFF"));
            sort_search.setTextSize(12);
            sort_search.setTypeface(typeface);
            sort_search.setBackgroundResource(R.drawable.round5_background);
            sort_search.setPadding((int) (10 * dm.density), (int) (5 * dm.density),
                    (int) (10 * dm.density), (int) (5 * dm.density));
            sort.addView(sort_search);

            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) sort_search.getLayoutParams();
            layoutParams1.leftMargin = (int) (5 * dm.density);
            layoutParams1.topMargin = (int) (5 * dm.density);
            layoutParams1.bottomMargin = (int) (10 * dm.density);

            sort_search.setLayoutParams(layoutParams1);

            new_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
            hot_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
            my_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
            new_orderTXT.setTextColor(getResources().getColor(R.color.main_color));
            hot_orderTXT.setTextColor(getResources().getColor(R.color.main_color));
            my_orderTXT.setTextColor(getResources().getColor(R.color.main_color));

            Log.i("data ", data);

            arrayClear();

            myRef.child("Question").orderByChild("q_title").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i(TAG, "onDataChange()");
                    if (snapshot != null) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            HashMap<String, Object> member = (HashMap<String, Object>) dataSnapshot.getValue();
                            String id = member.get("id").toString();
                            String q_Num = member.get("q_Num").toString();
                            String q_title = member.get("q_title").toString();
                            String q_content = member.get("q_content").toString();
                            String q_date = member.get("q_date").toString();
                            String q_time = member.get("q_time").toString();
                            String q_count = member.get("q_count").toString();
                            String q_like = member.get("q_like").toString();
                            String q_dislike = member.get("q_dislike").toString();

                            myRef.child("Member").child(id).child("name").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String name = (String) snapshot.getValue();
                                    name_Arr.add(0, name);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            if (q_title.indexOf(data) != -1) {
                                questionAdapter.addItemDESC(0, id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);
                                key_Arr.add(0, q_Num);
                                count_Arr.add(0, q_count);
                                id_Arr.add(0, id);
                            }
                        }
                        questionAdapter.notifyDataSetChanged();
                    }
                    else {
                        Log.i(TAG, "Firebase Data 찾지 못함!!");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            sort_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sort_search.setVisibility(View.GONE);
                    new_orderTXT.setBackground(getResources().getDrawable(R.drawable.round5_background));
                    new_orderTXT.setTextColor(getResources().getColor(R.color.white));

                    myRef.child("Question").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.i(TAG, "onDataChange()");
                            if (snapshot != null) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    HashMap<String, Object> member = (HashMap<String, Object>) dataSnapshot.getValue();
                                    String id = member.get("id").toString();
                                    String q_Num = member.get("q_Num").toString();
                                    String q_title = member.get("q_title").toString();
                                    String q_content = member.get("q_content").toString();
                                    String q_date = member.get("q_date").toString();
                                    String q_time = member.get("q_time").toString();
                                    String q_count = member.get("q_count").toString();
                                    String q_like = member.get("q_like").toString();
                                    String q_dislike = member.get("q_dislike").toString();

                                    myRef.child("Member").child(id).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            String name = (String) task.getResult().getValue();
                                            Log.i("name ", name);
                                            name_Arr.add(0, name);
                                        }
                                    });

                                    questionAdapter.addItemDESC(0, id, q_Num, q_title, q_content, q_date, q_time, q_count, q_like, q_dislike);
                                    key_Arr.add(0, q_Num);
                                    count_Arr.add(0, q_count);
                                    id_Arr.add(0, id);
                                }
                            } else {
                                Log.i(TAG, "Firebase Data 찾지 못함!!");
                                questionAdapter.clear();
                            }
                            questionAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

        } else
            Log.i("data", "null");
        // search ------------



        mContext = getContext();
        fab_open = AnimationUtils.loadAnimation(mContext, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(mContext, R.anim.fab_close);
        fab_main = (FloatingActionButton) view.findViewById(R.id.fab_main);
        fab_sub1 = (FloatingActionButton) view.findViewById(R.id.fab_sub1);
        fab_sub2 = (FloatingActionButton) view.findViewById(R.id.fab_sub2);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFabOpen) {
                    fab_main.setImageResource(R.drawable.booking);
                    fab_sub1.startAnimation(fab_close);
                    fab_sub2.startAnimation(fab_close);
                    fab_sub1.setClickable(false);
                    fab_sub2.setClickable(false);
                    isFabOpen = false;
                } else {
                    fab_main.setImageResource(R.drawable.booking);
                    fab_sub1.startAnimation(fab_open);
                    fab_sub2.startAnimation(fab_open);
                    fab_sub1.setClickable(true);
                    fab_sub2.setClickable(true);
                    isFabOpen = true;
                }
            }
        });

        // floating 버튼의 돋보기 버튼 클릭시
        fab_sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddQuestion();
            }
        });
    } // init()

    // 버튼 클릭시 배경화면 + 글자 색 변경
    private void changeBTN_bg(int id) {
        switch (id) {
            case R.id.new_orderTXT:
                new_orderTXT.setBackground(getResources().getDrawable(R.drawable.round5_background));
                hot_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
                my_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
                new_orderTXT.setTextColor(getResources().getColor(R.color.white));
                hot_orderTXT.setTextColor(getResources().getColor(R.color.main_color));
                my_orderTXT.setTextColor(getResources().getColor(R.color.main_color));
                break;
            case R.id.hot_orderTXT:
                new_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
                hot_orderTXT.setBackground(getResources().getDrawable(R.drawable.round5_background));
                my_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
                new_orderTXT.setTextColor(getResources().getColor(R.color.main_color));
                hot_orderTXT.setTextColor(getResources().getColor(R.color.white));
                my_orderTXT.setTextColor(getResources().getColor(R.color.main_color));
                break;
            case R.id.my_orderTXT:
                new_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
                hot_orderTXT.setBackground(getResources().getDrawable(R.drawable.round6_background));
                my_orderTXT.setBackground(getResources().getDrawable(R.drawable.round5_background));
                new_orderTXT.setTextColor(getResources().getColor(R.color.main_color));
                hot_orderTXT.setTextColor(getResources().getColor(R.color.main_color));
                my_orderTXT.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getActivity().getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return view.dispatchTouchEvent(ev);
    }

    // Intent => AddQuestionActivity
    private void startAddQuestion() {
        Intent intent = new Intent(getActivity(), AddQuestionActivity.class);
        startActivity(intent);
    }

    // 각 arrayList와 adapter 클리어
    private void arrayClear() {
        questionAdapter.clear();
        key_Arr.clear();
        name_Arr.clear();
        id_Arr.clear();
        count_Arr.clear();
    }
}
