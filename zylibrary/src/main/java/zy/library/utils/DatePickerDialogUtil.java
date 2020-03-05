package zy.library.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by WINDOWS on 2017/2/15.
 */

public class DatePickerDialogUtil implements DatePickerDialog.OnDateSetListener {

    private Activity mActivity;
    private String initTime;
    private PickerType mType;
    private TextView mTvTime;
    private String startTime;
    private String endtTime;
    private boolean isEndtime = false;
    private boolean isStarttime = false;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        if (mType == PickerType.YEAR_MONTH) {
            if (month < 10) {
                mTvTime.setText(year + "-0" + month);
            } else {
                mTvTime.setText(year + "-" + month);
            }
        } else {
            if (isEndtime || isStarttime) {
                if (month < 10 && dayOfMonth < 10) {
                    mTvTime.setText(year + "-0" + month + "-0" + dayOfMonth);
                } else if (dayOfMonth < 10) {
                    mTvTime.setText(year + "-" + month + "-0" + dayOfMonth);
                } else if (month < 10) {
                    mTvTime.setText(year + "-0" + month + "-" + dayOfMonth);
                } else {
                    mTvTime.setText(year + "-" + month + "-" + dayOfMonth);
                }
                if (isEndtime) {
                    mTvTime.setText(Utils.getendTime(startTime, mTvTime.getText().toString()));
                }
                if (isStarttime) {
                    mTvTime.setText(Utils.getStartTime(mTvTime.getText().toString(),endtTime));
                }

            } else {
                if (month < 10 && dayOfMonth < 10) {
                    mTvTime.setText(year + "-0" + month + "-0" + dayOfMonth);
                } else if (dayOfMonth < 10) {
                    mTvTime.setText(year + "-" + month + "-0" + dayOfMonth);
                } else if (month < 10) {
                    mTvTime.setText(year + "-0" + month + "-" + dayOfMonth);
                } else {
                    mTvTime.setText(year + "-" + month + "-" + dayOfMonth);
                }
            }
        }
    }

    public enum PickerType {
        YEAR_MONTH, ALL
    }

    public DatePickerDialogUtil(Activity mActivity, String initTime, PickerType type) {
        this.mActivity = mActivity;
        this.initTime = initTime;
        mType = type;
    }

    public void init() {
        Calendar calendar = Calendar.getInstance();
        if (!StringUtils.isSpace(initTime)) {
            calendar = getCalendarByInitData(initTime);
        } else {
            initTime = calendar.get(Calendar.YEAR) + "-"
                    + calendar.get(Calendar.MONTH) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog pickerDialog = new DatePickerDialog(mActivity, this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

    private Calendar getCalendarByInitData(String initTime) {
        SimpleDateFormat sdf;
        if (mType == PickerType.YEAR_MONTH) {
            sdf = new SimpleDateFormat("yyyy-MM");
        } else {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
        }
        Date initDate = null;
        try {
            initDate = sdf.parse(initTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (initDate == null) {
            return Calendar.getInstance();
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(initDate);
            return calendar;
        }
    }

    public void setValue(TextView tvTime) {
        this.isEndtime = false;
        this.isStarttime = false;
        mTvTime = tvTime;
        init();
    }

    public void setStartTime(String endTime, TextView tvTime) {
        this.endtTime = endTime;
        isStarttime = true;
        isEndtime = false;
        mTvTime = tvTime;
        init();

    }

    public void setEndValue(String startTime, TextView tvTime) {
        this.startTime = startTime;
        isEndtime = true;
        isStarttime = false;
        mTvTime = tvTime;
        init();
    }
}
