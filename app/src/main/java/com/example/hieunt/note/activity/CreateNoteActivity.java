package com.example.hieunt.note.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hieunt.note.customview.LinedEditText;
import com.example.hieunt.note.R;
import com.example.hieunt.note.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class CreateNoteActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_time)
    TextView tvCurrentTime;
    @BindView(R.id.et_title)
    LinedEditText etTitle;
    @BindView(R.id.et_content)
    LinedEditText etContent;
    @BindView(R.id.sp_day)
    Spinner spDay;
    @BindView(R.id.sp_time)
    Spinner spTime;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.cl_choose_time_alarm)
    ConstraintLayout clChooeTime;
    @BindView(R.id.cl_create_note)
    ConstraintLayout clCreatNote;

    String TAG = "CreateNoteActivity";
    private int GALLERY = 1, CAMERA = 2;
    private ArrayList<String> mListDay = new ArrayList();
    private String listTime[];
    private Dialog mDialogPickColor;
    private Dialog mDialogInsertPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(c.getTime());
        tvCurrentTime.setText(formattedDate);

        mDialogInsertPicture = new Dialog(this);
        mDialogInsertPicture.setContentView(R.layout.dialog_pick_picture);

    }

    @Override
    public int setlayoutId() {
        return R.layout.activity_creat_note;
    }

    @OnClick(R.id.iv_back)
    public void backToHome() {
        onBackPressed();
    }

    @OnClick(R.id.iv_camera)
    public void pickCamera() {
        mDialogInsertPicture.show();
        LinearLayout llChooseCamera, llChooseGalery;
        llChooseCamera = mDialogInsertPicture.findViewById(R.id.ll_choose_camera);
        llChooseGalery = mDialogInsertPicture.findViewById(R.id.ll_choose_galery);
    }


    @OnClick(R.id.iv_choose_color)
    public void pickColor() {

        mDialogPickColor = new Dialog(this);
        mDialogPickColor.setContentView(R.layout.dialog_choose_color);
        mDialogPickColor.show();

        ImageView ivWhite, ivYellow, ivGreen, ivblue;
        ivWhite = mDialogPickColor.findViewById(R.id.iv_white);
        ivYellow = mDialogPickColor.findViewById(R.id.iv_yellow);
        ivGreen = mDialogPickColor.findViewById(R.id.iv_green);
        ivblue = mDialogPickColor.findViewById(R.id.iv_blue);
        ivWhite.setOnClickListener(this);
        ivYellow.setOnClickListener(this);
        ivGreen.setOnClickListener(this);
        ivblue.setOnClickListener(this);

    }

    @OnClick(R.id.iv_accept)
    public void pickAccept() {

    }

    @OnClick(R.id.iv_cancel)
    public void pickCancel() {
        tvAlarm.setVisibility(View.VISIBLE);
        clChooeTime.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_alarm)
    public void tvAlarmClick() {
        tvAlarm.setVisibility(View.GONE);
        clChooeTime.setVisibility(View.VISIBLE);

        listTime = getResources().getStringArray(R.array.listTime);
        ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listTime);
//        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTime.setAdapter(adapterTime);

        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
            DayOfWeek dow = date.getDayOfWeek();
            String dayName = dow.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
            Log.d("xxxx", dayName);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_white:
                clCreatNote.setBackgroundColor(getResources().getColor(R.color.white));
                mDialogPickColor.dismiss();
                break;
            case R.id.iv_yellow:
                clCreatNote.setBackgroundColor(getResources().getColor(R.color.yellow));
                mDialogPickColor.dismiss();
                break;
            case R.id.iv_green:
                clCreatNote.setBackgroundColor(getResources().getColor(R.color.green));
                mDialogPickColor.dismiss();
                break;
            case R.id.iv_blue:
                clCreatNote.setBackgroundColor(getResources().getColor(R.color.blue));
                mDialogPickColor.dismiss();
                break;
        }
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
}
