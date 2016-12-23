package com.ezcocoa.linkpay_sample.ez;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezcocoa.linkpay_sample.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 커스텀 다이얼 로그
 */
public class EZAlertDialog extends DialogFragment implements View.OnClickListener {
    private static final String TAG = EZAlertDialog.class.getSimpleName();
    public interface EZAlertDialogListener {
        void onClickConfirm(int selected); // 멀티 선택 후 확인 버튼 클릭 시 호출
        void onClickConfirm();             // 확인 버튼 클릭 시 호출
        void onClickCancel();              // 취소 버튼 클릭 시 호출
    }

    // 리스너
    private EZAlertDialogListener _listener;

    // 뷰
    @Bind(R.id.d_alert_titleTv)
    TextView _titleTv;
    @Bind(R.id.d_alert_messageTv)
    TextView _messageTv;
    @Bind(R.id.d_alert_msgHorizontalLineView)
    View _msgVerticalLineView;
    @Bind(R.id.d_alert_containerLayout)
    LinearLayout _containerLayout;
    @Bind(R.id.d_alert_cancelBtn)
    Button _cancelBtn;
    @Bind(R.id.d_alert_confirmBtn)
    Button _confirmBtn;
    @Bind(R.id.d_alert_verSpaceView)
    View _verSpaceView;

    // 모델
    private String _title; // 제목
    private String _message; // 메시지 (Null이면 메시지 창을 숨김)
    private String[] _buttons; // 가운데 버튼 표시 선택 시 비트 연산으로 처리해야 함 (ex, 1,2,4,8,16)
    private Drawable _image;    // 컨텐츠에 표시될 이미지
    private String _confirm; // 확인 버튼
    private String _cancel;  // 취소 버튼
    private int _selectedButtons = 0;
    private View _view;

    public void setListener(EZAlertDialogListener listener) {
        _listener = listener;
    }

    public void setTitle(String title) {
        this._title = title;
    }
    public void setMessage(String message) {
        this._message = message;
    }

    public void setButtons(String[] buttons) {
        this._buttons = buttons;
    }

    public void setImage(Drawable image) {
        this._image = image;
    }

    public void setConfirm(String confirm) {
        this._confirm = confirm;
    }

    public void setCancel(String cancel) {
        this._cancel = cancel;
    }

    public void setView(View v) {
        _view = v;
    }

    public static void show1(final FragmentManager fm, final String message, final Drawable image, final String[] buttons, final EZAlertDialogListener listener, final String cancel, final String confirm) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                EZAlertDialog pd = new EZAlertDialog();
                pd.setMessage(message);
                pd.setImage(image);
                pd.setButtons(buttons);
                pd.setListener(listener);
                if(cancel == null && confirm == null) {
                    pd.setConfirm("확인");
                } else {
                    pd.setConfirm(confirm);
                }
                pd.setCancel(cancel);
                pd.setCancelable(false);
                pd.show(fm,null);
            }
        });
    }

    public static void show1(FragmentManager fm, String message, Drawable image, EZAlertDialogListener listener) {
        show1(fm, message, image, null, listener, null, null);
    }

    public static void show1(FragmentManager fm, String message, String[] buttons, EZAlertDialogListener listener, String cancel, String confirm) {
        show1(fm, message, null, buttons, listener, cancel, confirm);
    }

    public static void show1(FragmentManager fm, String message, String[] buttons, EZAlertDialogListener listener) {
        show1(fm, message, null, buttons, listener, null, null);
    }

    public static void show1(FragmentManager fm, String message, EZAlertDialogListener listener, String cancel, String confirm) {
        show1(fm, message, null, null, listener, cancel, confirm);
    }

    public static void show1(FragmentManager fm, String message, EZAlertDialogListener listener) {
        show1(fm, message, null, null, listener, null, null);
    }

    public static void show1(FragmentManager fm, String message, String cancel, String confirm) {
        show1(fm, message, null, null, null, cancel, confirm);
    }

    public static void show1(FragmentManager fm, String message) {
        show1(fm, message, null, null, null, null, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.dialog_alert, container, false);

        ButterKnife.bind(this, rootView);

        Log.d(TAG, "cancel->"+_cancel);
        Log.d(TAG, "confirm->"+_confirm);

        // 버튼 이벤트 등록
        _cancelBtn.setOnClickListener(this);
        _confirmBtn.setOnClickListener(this);

        if (_title != null) {
            _titleTv.setText(_title);
        }
        // 메시지
        if (_message != null) {
            _messageTv.setText(_message);
        } else {
            _messageTv.setVisibility(View.GONE);
            _msgVerticalLineView.setVisibility(View.GONE);
        }

        if (_view != null) {
            _containerLayout.addView(_view);
        }

        // 이미지 추가
        if (_image != null) {
            ImageView iv = new ImageView(getActivity());
            iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            iv.setImageDrawable(_image);
            _containerLayout.addView(iv);
        }

        // 취소 버튼
        if (_cancel != null) {
            _cancelBtn.setText(_cancel);
            _cancelBtn.setVisibility(View.VISIBLE);
        } else {
            _cancelBtn.setVisibility(View.GONE);
            _verSpaceView.setVisibility(View.GONE);
        }

        // 확인 버튼
        if (_confirm != null) {
            _confirmBtn.setText(_confirm);
            _confirmBtn.setVisibility(View.VISIBLE);
        } else {
            _confirmBtn.setVisibility(View.GONE);
            _verSpaceView.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.d_alert_cancelBtn:
                if (_listener != null) {
                    _listener.onClickCancel();
                }
                dismiss();
                break;
            case R.id.d_alert_confirmBtn:
                if (_listener != null) {
                    if (_buttons != null) {
                        _listener.onClickConfirm(_selectedButtons);
                    } else {
                        _listener.onClickConfirm();
                    }
                }
                dismiss();
                break;

            default:
            {
                if (_listener != null) {
                    _listener.onClickConfirm(v.getId());
                }
                dismiss();
                break;
            }
        }
    }
}
