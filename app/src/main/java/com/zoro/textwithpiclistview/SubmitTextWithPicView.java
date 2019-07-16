package com.zoro.textwithpiclistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zoro
 * Created on 2019/7/12
 * description:
 */
public class SubmitTextWithPicView extends LinearLayout {

    EditText etContent;
    TextView tvContentNumber;
    RecyclerView recyclerPic;
    TextView tvPicNumber;
    //限制上传图片数量，默认为9张 在构造函数中初始化
    private int limit_Pic_Number = 9;
    private String hint = "";
    private View view;
    private SubmitTextWithPicAdapter adapter;
    private List<String> list = new ArrayList<>();
    private SubmitTextWithPcListener listener;


    protected InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals(" ")) {
                ToastUtils.showShort("不能输入空格");
                return "";
            } else {
                return null;
            }
        }
    };

    public SubmitTextWithPcListener getListener() {
        return listener;
    }

    public void setListener(SubmitTextWithPcListener listener) {
        this.listener = listener;
    }

    public SubmitTextWithPicView(Context context) {
        this(context, null);
    }

    public SubmitTextWithPicView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SubmitTextWithPicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SubmitTextWithPicView, defStyleAttr, 0);
        limit_Pic_Number = a.getInteger(R.styleable.SubmitTextWithPicView_picLimit, 9);
        hint = a.getString(R.styleable.SubmitTextWithPicView_hintContent);
        initView(context);
    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.submit_textwithpic, this);
        tvPicNumber = view.findViewById(R.id.tv_Pic_Number);
        recyclerPic = view.findViewById(R.id.recycler_Pic);
        tvContentNumber = view.findViewById(R.id.tv_Content_Number);
        etContent = view.findViewById(R.id.et_Content);
        tvPicNumber.setText("最多上传" + limit_Pic_Number + "张");
        etContent.setHint(hint);
        etContent.setFilters(new InputFilter[]{filter, new InputFilter.LengthFilter(100)});
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvContentNumber.setText(etContent.getText().toString().trim().length() + "/100");
            }
        });
        adapter = new SubmitTextWithPicAdapter(context, limit_Pic_Number);
        adapter.setListener(new SubmitTextWithPicAdapter.PhotoListener() {
            @Override
            public void deltetPhoto(int position) {
                list.remove(position);
                adapter.addRefreshData(list);
            }

            @Override
            public void addPhoto() {
                listener.addPhoto();
            }
        });
        recyclerPic.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerPic.setAdapter(adapter);
        adapter.addRefreshData(list);
    }


    //返回所需内容
    public String getContent() {
        return etContent.getText().toString().trim();
    }

    public List<String> getPicList() {
        return list;
    }

    //添加成功图片之后从外部设置进来
    public void setPic(String url) {
        list.add(url);
        adapter.addRefreshData(list);
    }

    public interface SubmitTextWithPcListener {
        void addPhoto();
    }

}
