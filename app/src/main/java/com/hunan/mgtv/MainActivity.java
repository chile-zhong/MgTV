package com.hunan.mgtv;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.hunan.mgtv.bean.Question;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE;
import static android.widget.AbsListView.CHOICE_MODE_SINGLE;

/**
 * 创建日期：
 *
 * @author chile
 *         version 1.0
 *         文件名称：
 *         类说明：
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.get)
    Button get;
    @BindView(R.id.post)
    Button post;
    @BindView(R.id.single)
    Button single;
    @BindView(R.id.muti)
    Button muti;
    //    @Nullable
//    @BindView(R.id.tv_name)
//    TextView tvName;
//    @Nullable
//    @BindView(R.id.webView)
//    WebView webView;
//    @Nullable
//    @BindView(R.id.please)
//    TextView please;
//    @Nullable
//    @BindView(R.id.ListView)
//    android.widget.ListView ListView;
//    @Nullable
//    @BindView(R.id.giveup)
//    Button giveup;
//    @Nullable
//    @BindView(R.id.vote)
//    Button vote;
//    @Nullable
//    @BindView(R.id.quit)
//    Button quit;
    private int choosed = -1;
    private ListView mListView;
    private ArrayList<Question> mTestData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private ArrayList<Question> initData() {
        ArrayList<Question> mTestData = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Question question = new Question(i , "问题" + i, false);
            mTestData.add(question);
        }
        return mTestData;
    }

    private void alertDialog(final int isSingle) {
        mTestData = initData();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog mDialog = builder.create();
        View choiceItemView = getLayoutInflater().inflate(R.layout.dialog_choose, null);
        mListView = choiceItemView.findViewById(R.id.ListView);
        Button giveup = choiceItemView.findViewById(R.id.giveup);
        Button vote = choiceItemView.findViewById(R.id.vote);
        Button quit = choiceItemView.findViewById(R.id.quit);
        WebView webView = choiceItemView.findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/1.html");
        if (isSingle == CHOICE_MODE_SINGLE) {
            QuestionSingleAdapter questionAdapter = new QuestionSingleAdapter(this, R.layout.single_choice, mTestData);
            mListView.setAdapter(questionAdapter);
        } else {
            mListView.setChoiceMode(CHOICE_MODE_MULTIPLE);
            QuestionMutiAdapter questionAdapter = new QuestionMutiAdapter(this, R.layout.muti_choice, mTestData);
            mListView.setAdapter(questionAdapter);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isSingle == CHOICE_MODE_SINGLE) {
                    choosed = position;
                    final RadioButton itemCheck = view.findViewById(R.id.checkBox);
                    if (itemCheck.isChecked()) {
                        itemCheck.setChecked(true);
                    } else {
                        itemCheck.setChecked(false);
                    }
                    itemCheck.setChecked(true);
                } else {
                    SparseBooleanArray checkedItemPosition = mListView.getCheckedItemPositions();
                    boolean isChecked = checkedItemPosition.get(position);
                    ((CheckBox) view.findViewById(R.id.checkBox)).setChecked(isChecked);
                    mTestData.get(position).setChecked(isChecked);
                }

            }
        });
        giveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "giveup", Toast.LENGTH_SHORT).show();
            }
        });
        vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSingle == CHOICE_MODE_SINGLE) {
                    Toast.makeText(MainActivity.this, choosed + 1 + " choosed", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < mTestData.size(); i++) {
                        if (mTestData.get(i).isChecked()) {
                            sb.append(i + 1).append(",");
                        }
                    }
                    Toast.makeText(MainActivity.this, sb.toString() + " choosed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.setView(choiceItemView);
        mDialog.show();
    }

    @Optional
    @OnClick({R.id.single, R.id.muti, R.id.get, R.id.post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.single:
                alertDialog(CHOICE_MODE_SINGLE);
                break;
            case R.id.muti:
                alertDialog(CHOICE_MODE_MULTIPLE);
                break;
            //get请求
            case R.id.get:
                EasyHttp.get("jq01")
                        .baseUrl("http://106.15.50.103:4000")
                        .readTimeOut(30 * 1000)
                        .writeTimeOut(30 * 1000)
                        .connectTimeout(30 * 1000)
                        .params("name", "张三")
                        .timeStamp(true)
                        .execute(new SimpleCallBack<SkinTestResult>() {
                            @Override
                            public void onError(ApiException e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onSuccess(SkinTestResult response) {
                                Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                break;
            case R.id.post:
                EasyHttp.post("api/")
                        .baseUrl("http://106.15.50.103:4000")
                        .upJson("{\"\":\"\",\"\":\"\",\"\":\"\",\"swry_dm\":\"127053096\",\"version\":\"1.0.0\"}")
                        //这里不想解析，简单只是为了做演示 直接返回String
                        .execute(new SimpleCallBack<String>() {
                            @Override
                            public void onError(ApiException e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT)
                                        .show();
                            }

                            @Override
                            public void onSuccess(String s) {
                                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT)
                                        .show();
                                //解析返回的json
                            }
                        });
                break;
            default:
        }
    }

}
