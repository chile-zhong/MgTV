package com.hunan.mgtv;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hunan.mgtv.api.ChargeClient;
import com.hunan.mgtv.api.ClientManager;
import com.hunan.mgtv.bean.AnswerResponse;
import com.hunan.mgtv.bean.AnswersItem;
import com.hunan.mgtv.bean.MutiAnswerBody;
import com.hunan.mgtv.bean.Question;
import com.hunan.mgtv.bean.WatermarkBody;
import com.hunan.mgtv.bean.WatermarkResponse;
import com.hunan.mgtv.util.JsonUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE;

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
    private int choosed = -1;
    private ListView mListView;
    private ArrayList<AnswersItem> mTestData;
    private WatermarkResponse watermarkResponse;
    private String selectType;
    private TextView answerResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    private void alertDialog(String waterString) {
        ClientManager cm = ClientManager.getInstance();
        if (cm == null) {
            return;
        }
        ChargeClient chargeClient = cm.getChargeClient();
        if (chargeClient == null) {
            return;
        }
        final WatermarkBody watermarkBody = new WatermarkBody();
        watermarkBody.setWaterMark(waterString);
        String jsonString = JsonUtils.toJson(watermarkBody);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
        Call<WatermarkResponse> call = chargeClient.getResponseCallback(body);
        call.enqueue(new Callback<WatermarkResponse>() {
            @Override
            public void onResponse(Call<WatermarkResponse> call, Response<WatermarkResponse> response) {
                watermarkResponse = response.body();
                if (watermarkResponse != null && response.code() == 200) {
                    final Question question = watermarkResponse.getQuestion();
                    selectType = question.getMs();
                    mTestData = new ArrayList<>();
                    for (int i = 0; i < question.getAnswers().size(); i++) {
                        mTestData.add(question.getAnswers().get(i));
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final AlertDialog mDialog = builder.create();
                    View choiceItemView = getLayoutInflater().inflate(R.layout.dialog_choose, null);
                    mListView = choiceItemView.findViewById(R.id.ListView);
                    Button giveup = choiceItemView.findViewById(R.id.giveup);
                    Button vote = choiceItemView.findViewById(R.id.vote);
                    Button quit = choiceItemView.findViewById(R.id.quit);
                    TextView title = choiceItemView.findViewById(R.id.title);
                    answerResult = choiceItemView.findViewById(R.id.answer_Result);
                    title.setText(question.getTitle());
//                    WebView webView = choiceItemView.findViewById(R.id.webView);
//                    webView.loadUrl("file:///android_asset/1.html");
                    if (selectType.equals("s")) {
                        QuestionSingleAdapter questionAdapter = new QuestionSingleAdapter(MainActivity.this,
                                R.layout.single_choice,
                                mTestData);
                        mListView.setAdapter(questionAdapter);
                    } else if (selectType.equals("m")) {
                        mListView.setChoiceMode(CHOICE_MODE_MULTIPLE);
                        QuestionMutiAdapter questionAdapter = new QuestionMutiAdapter(MainActivity.this,
                                R.layout.muti_choice, mTestData);
                        mListView.setAdapter(questionAdapter);
                    }
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (selectType.equals("s")) {
                                choosed = position;
                                final RadioButton itemCheck = view.findViewById(R.id.checkBox);
                                if (itemCheck.isChecked()) {
                                    itemCheck.setChecked(true);
                                } else {
                                    itemCheck.setChecked(false);
                                }
                                itemCheck.setChecked(true);
                            } else if (selectType.equals("m")) {
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
                            if (selectType.equals("s")) {
                                String[] strings = new String[1];
//                                Toast.makeText(MainActivity.this, choosed + 1 + " choosed", Toast.LENGTH_SHORT).show();
                                strings[0] = question.getAnswers().get(choosed).getAid();
                                submitAnswer(question, strings);
                            } else if (selectType.equals("m")) {
                                StringBuilder sb = new StringBuilder();
                                int stringLength = 0;
                                for (int i = 0; i < mTestData.size(); i++) {
                                    if (mTestData.get(i).isChecked()) {
                                        stringLength++;
                                    }
                                }
                                String[] strings = new String[stringLength];
                                int count = 0;
                                for (int i = 0; i < mTestData.size(); i++) {
                                    if (mTestData.get(i).isChecked()) {
                                        strings[count] = question.getAnswers().get(i).getAid();
                                        count++;
                                    }
                                }
                                submitAnswer(question, strings);
//                                Toast.makeText(MainActivity.this, sb.toString() + " choosed", Toast.LENGTH_SHORT).show();
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

                } else {
                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WatermarkResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void submitAnswer(Question question, String[] answerId) {
        ClientManager cm = ClientManager.getInstance();
        if (cm == null) {
            return;
        }
        ChargeClient chargeClient = cm.getChargeClient();
        if (chargeClient == null) {
            return;
        }
        final MutiAnswerBody answerBody = new MutiAnswerBody();
        answerBody.setAid(answerId);
        answerBody.setMs(question.getMs());
        answerBody.setQid(question.getQid());
        String jsonString = JsonUtils.toJson(answerBody);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonString);
        Call<AnswerResponse> call = chargeClient.getAnswerResponse(body);
        call.enqueue(new Callback<AnswerResponse>() {
            @Override
            public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
//                watermarkResponse = response.body();
//                if (watermarkResponse != null && response.code() == 200) {
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
//                }
                answerResult.setVisibility(View.VISIBLE);
                answerResult.setText("您答对了！");
                Toast.makeText(getApplicationContext(), "good", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<AnswerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Optional
    @OnClick({R.id.single, R.id.muti, R.id.get, R.id.post})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.single:
                alertDialog("q0001");
                /*EasyHttp.post("/ds02")
                        .baseUrl("http://106.15.50.103:4000")
                        .params("water_mark","q0001")
//                        .params("sid","q0001")
//                        .upJson("{\"water_mark\":\"q0001\"}")
                        //这里不想解析，简单只是为了做演示 直接返回String
                        .execute(new SimpleCallBack<WatermarkResponse>() {

                            @Override
                            public void onError(ApiException e) {
                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT)
                                        .show();

                            }

                            @Override
                            public void onSuccess(WatermarkResponse question) {
                                Toast.makeText(MainActivity.this, question.toString(), Toast.LENGTH_SHORT)
                                        .show();

                            }
//                        }) {
//                            @Override
//                            public void onError(ApiException e) {
//                                super.onError(e);
//                                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT)
//                                        .show();
//                            }

//                            @Override
//                            public void onSuccess(Question response) {
//                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT)
//                                        .show();
                                //解析返回的json
//                                questionResponse = response;
//                                if (questionResponse.getQuestion().getMs().equals("s")) {
//                                    alertDialog(CHOICE_MODE_SINGLE);
//                                }
//                            }
                        });*/
                break;

//                break;
            case R.id.muti:
                alertDialog("q0002");
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
