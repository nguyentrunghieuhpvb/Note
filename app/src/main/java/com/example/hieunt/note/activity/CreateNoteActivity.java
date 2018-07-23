package com.example.hieunt.note.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.hieunt.note.adapter.ListImageAdapter;
import com.example.hieunt.note.customview.LinedEditText;
import com.example.hieunt.note.R;
import com.example.hieunt.note.base.BaseActivity;
import com.example.hieunt.note.customview.MySpinner;
import com.example.hieunt.note.database.DatabaseQuery;
import com.example.hieunt.note.model.Note;
import com.example.hieunt.note.utils.DateManager;
import com.example.hieunt.note.utils.MyAlarmManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;

public class CreateNoteActivity extends BaseActivity implements View.OnClickListener, Serializable {

    public static final String TAG = "CreateNoteActivity";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvCurrentTime;
    @BindView(R.id.et_title)
    LinedEditText etTitle;
    @BindView(R.id.et_content)
    LinedEditText etContent;
    @BindView(R.id.sp_date)
    MySpinner spDate;
    @BindView(R.id.sp_time)
    MySpinner spTime;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.cl_choose_time_alarm)
    ConstraintLayout clChooeTime;
    @BindView(R.id.cl_create_note)
    ConstraintLayout clCreatNote;
    @BindView(R.id.rv_list_image)
    RecyclerView rvListImage;


    private int flagGalery = 1, flagCamera = 2;
    private ArrayList<String> listDay = new ArrayList();
    private String listTime[];
    private Dialog mDialogPickColor;
    private Dialog mDialogInsertPicture;
    private Note note = new Note();
    private int color = Color.WHITE;
    private Bitmap imageBitmap;
    private boolean isAlarm = false;
    private ArrayAdapter<String> adapterDay;
    private ArrayAdapter<String> adapterTime;
    private Realm realm;
    private DatabaseQuery db;
    private int flag = 0;
    private ArrayList<String> listImagePath = new ArrayList<>();
    private ListImageAdapter listImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DatabaseQuery.getInstance(this);
        Calendar c = Calendar.getInstance();
        note.setTitle("Untitle");

        String timeCreated = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
        tvCurrentTime.setText(timeCreated);
        note.setDayCreate(timeCreated);

        mDialogInsertPicture = new Dialog(this);
        mDialogInsertPicture.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogInsertPicture.setContentView(R.layout.dialog_pick_picture);
        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvTitle.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etTitle.getText().toString().trim().length() < 1) {
                    tvTitle.setText("Note");
                    note.setTitle("Untitle");
                } else {
                    note.setTitle(etTitle.getText().toString().trim());
                }
            }
        });
        listImageAdapter = new ListImageAdapter(this, new ArrayList<String>(), new ListImageAdapter.IIvCancelClickListener() {
            @Override
            public void IvCancelClick(int pos) {
                listImagePath.remove(pos);
                listImageAdapter.removeImage(pos);
                if (listImagePath.size() == 0) {
                    rvListImage.setVisibility(View.GONE);
                }
            }
        });
        rvListImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvListImage.setAdapter(listImageAdapter);
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
        llChooseCamera.setOnClickListener(this);
        llChooseGalery.setOnClickListener(this);
    }


    @OnClick(R.id.iv_choose_color)
    public void pickColor() {
        mDialogPickColor = new Dialog(this);
        mDialogPickColor.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.iv_accept)
    public void pickAccept() {
        note.setId(db.getNoteId());
        note.setContent(etContent.getText().toString());
        note.setColor(color);
        note.setAlarm(isAlarm);
        RealmList<String> listImage = new RealmList<>();
        for (String s : listImagePath) {
            listImage.add(s);
        }
        note.setListImage(listImage);
        if (isAlarm) {
            MyAlarmManager myAlarmManager = new MyAlarmManager(this);
            myAlarmManager.addAlarm(note);
        }
        db.addNote(note);
        onBackPressed();

    }


    @OnClick(R.id.iv_cancel)
    public void pickCancelAlarm() {
        isAlarm = false;
        tvAlarm.setVisibility(View.VISIBLE);
        clChooeTime.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_alarm)
    public void tvAlarmClick() {
        note.setDate(DateManager.getCurrentDate());
        note.setTime("09:00");
        isAlarm = true;
        tvAlarm.setVisibility(View.GONE);
        clChooeTime.setVisibility(View.VISIBLE);

        Calendar calendar = Calendar.getInstance();
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        listDay.add(getResources().getString(R.string.today));
        listDay.add(getResources().getString(R.string.tomorow));
        listDay.add("next " + dayLongName);
        listDay.add(getResources().getString(R.string.other));
        adapterDay = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listDay);
        adapterDay.notifyDataSetChanged();
        spDate.setAdapter(adapterDay);

        listTime = getResources().getStringArray(R.array.listTime);
        adapterTime = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listTime);
        adapterTime.notifyDataSetChanged();
        spTime.setAdapter(adapterTime);

        LocalDate date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDate.now();
            DayOfWeek dow = date.getDayOfWeek();
            String dayName = dow.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault());
            Log.d("xxxx", dayName);
        }

        setActionTimeSelected();
        setActionDateSelected();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_white:
                clCreatNote.setBackgroundColor(getResources().getColor(R.color.white));
                color = getResources().getColor(R.color.white);
                mDialogPickColor.dismiss();
                break;
            case R.id.iv_yellow:
                clCreatNote.setBackgroundColor(getResources().getColor(R.color.yellow));
                color = getResources().getColor(R.color.yellow);
                mDialogPickColor.dismiss();
                break;
            case R.id.iv_green:
                clCreatNote.setBackgroundColor(getResources().getColor(R.color.green));
                color = getResources().getColor(R.color.green);
                mDialogPickColor.dismiss();
                break;
            case R.id.iv_blue:
                clCreatNote.setBackgroundColor(getResources().getColor(R.color.blue));
                color = getResources().getColor(R.color.blue);
                mDialogPickColor.dismiss();
                break;
            case R.id.ll_choose_camera:
                flag = 1;
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    takePhotoFromCamera();
                }
                mDialogInsertPicture.dismiss();
                break;
            case R.id.ll_choose_galery:
                flag = 0;
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                } else {
                    choosePhotoFromGallary();
                }
                mDialogInsertPicture.dismiss();
                break;
            default:
                break;
        }
    }


    public void choosePhotoFromGallary() {
        Log.d(TAG, "choosePhotoFromGallary");
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, 1);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                Log.d(TAG, " galery result");
                if (resultCode == RESULT_OK) {
                    final InputStream imageStream;
                    try {
                        imageStream = getContentResolver().openInputStream(data.getData());
                        final Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        String path = getRealPathFromURI(tempUri);
                        listImagePath.add(path);
                        listImageAdapter.addImage(path);
                        rvListImage.setVisibility(View.VISIBLE);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    if (data.getExtras().get("data") != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        String path = getRealPathFromURI(tempUri);
                        listImagePath.add(path);
                        listImageAdapter.addImage(path);
                        rvListImage.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (flag == 1) {
                        takePhotoFromCamera();
                    } else {
                        choosePhotoFromGallary();
                    }
                } else {
                }
                break;
            default:
                break;
        }
    }


    private void setActionTimeSelected() {
        final int currentPos = spTime.getSelectedItemPosition();
        spTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "pos : " + i);
                if (i == 4) {
                    final Dialog dialogTimerPicker = new Dialog(CreateNoteActivity.this);
                    dialogTimerPicker.requestWindowFeature(1);
                    dialogTimerPicker.setContentView(R.layout.dialog_timer_picker);
                    dialogTimerPicker.show();

                    TextView tvCancel, tvOk;
                    final TimePicker timePicker;
                    tvCancel = dialogTimerPicker.findViewById(R.id.tv_cancel);
                    tvOk = dialogTimerPicker.findViewById(R.id.tv_ok);
                    timePicker = dialogTimerPicker.findViewById(R.id.time_picker);
                    timePicker.setIs24HourView(true);
                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogTimerPicker.dismiss();
                            spTime.setSelection(currentPos);
                        }
                    });

                    tvOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            listTime[4] = timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute();
                            note.setTime(listTime[4]);
                            adapterTime.notifyDataSetChanged();

                            dialogTimerPicker.dismiss();

                        }
                    });
                } else {
                    listTime[4] = getResources().getString(R.string.other);
                    String time = listTime[i];
                    note.setTime(spTime.getSelectedItem().toString());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setActionDateSelected() {
        final int currentPos = spDate.getSelectedItemPosition();
        spDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "pos : " + i);
                if (i == listDay.size() - 1) {
                    final Dialog dialogDatePicker = new Dialog(CreateNoteActivity.this);
                    dialogDatePicker.requestWindowFeature(1);
                    dialogDatePicker.setContentView(R.layout.dialog_date_picker);
                    dialogDatePicker.show();

                    TextView tvCancel, tvOk;
                    final DatePicker datePicker;
                    tvCancel = dialogDatePicker.findViewById(R.id.tv_cancel);
                    tvOk = dialogDatePicker.findViewById(R.id.tv_ok);
                    datePicker = dialogDatePicker.findViewById(R.id.date_picker);
                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogDatePicker.dismiss();
                            spDate.setSelection(currentPos);
                        }
                    });

                    tvOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogDatePicker.dismiss();
                            String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
                            listDay.set(listDay.size() - 1, date);
                            note.setDate(date);
                            adapterDay.notifyDataSetChanged();
                        }
                    });
                } else {
                    listDay.set(listDay.size() - 1, getResources().getString(R.string.other));
                    adapterDay.notifyDataSetChanged();
                    String tmpDate = spDate.getSelectedItem().toString();
                    Log.d(TAG, "tmpdate : " + tmpDate);
                    String date;
                    if (tmpDate.equals(getResources().getString(R.string.today))) {
                        date = DateManager.getCurrentDate();
                    } else if (tmpDate.equals(getResources().getString(R.string.tomorow))) {
                        date = DateManager.getDateTomorow();
                    } else if (tmpDate.contains("next")) {
                        date = DateManager.getDateNextWeek();
                    } else {
                        date = listDay.get(listDay.size() - 1);
                    }
                    note.setDate(date);
                    Log.d(TAG, "date: " + date);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}
