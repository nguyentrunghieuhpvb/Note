package com.example.hieunt.note.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

@SuppressLint("AppCompatCustomView")
public class MySpinner extends Spinner {

    private OnItemSelectedListener listener;

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);

        if (position == getSelectedItemPosition()) {
            if (listener != null) {
                listener.onItemSelected(null, null, position, 0);
            }

        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }
}