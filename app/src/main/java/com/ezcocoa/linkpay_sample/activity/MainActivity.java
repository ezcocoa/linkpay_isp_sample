package com.ezcocoa.linkpay_sample.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ezcocoa.linkpay_sample.LinkpayConstants;
import com.ezcocoa.linkpay_sample.R;
import com.ezcocoa.linkpay_sample.ez.EZAlertDialog;
import com.ezcocoa.linkpay_sample.ez.EZToast;

import java.net.URLDecoder;

import butterknife.ButterKnife;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 응답데이터 > URL Scheme 방식으로 호출된경우 결과정보를 얻어서 처리..
        if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            Uri uri = intent.getData();
            Log.d(TAG, "uri->"+uri);

            try {
                String result           = uri.getQueryParameter(LinkpayConstants.RESULT);             // 결제성공여부
                String paymentMethod    = uri.getQueryParameter(LinkpayConstants.PAYMENT_METHOD);     // 카드현금구분
                String paymentType      = uri.getQueryParameter(LinkpayConstants.PAYMENT_TYPE);       // 거래구분
                String message          = URLDecoder.decode(uri.getQueryParameter(LinkpayConstants.MESSAGE), "UTF-8");  // 결제메시지

                if (LinkpayConstants.SUCCEED.equals(result) && LinkpayConstants.APPROVE.equals(paymentType)) {
                    EZToast.show(getApplicationContext(), getString(R.string.approved));
                } else if (LinkpayConstants.SUCCEED.equals(result) && LinkpayConstants.CANCEL.equals(paymentType)) {
                    EZToast.show(getApplicationContext(), getString(R.string.canceled));
                    EZAlertDialog.show1(getSupportFragmentManager(), uri.toString());
                    return;
                } else {
                    EZToast.show(getApplicationContext(), getString(R.string.failed));
                    EZAlertDialog.show1(getSupportFragmentManager(), uri.toString());
                    return;
                }

                if (paymentMethod.equals(LinkpayConstants.CARD)) { // 카드
                    if (result.equals(LinkpayConstants.SUCCEED)) {
                        Intent newIntent = new Intent(this, CardPayActivity.class);
                        newIntent.setData(uri);
                        startActivity(newIntent);
                    }
                } else if (paymentMethod.equals(LinkpayConstants.CASH)) { // 현금
                    if (result.equals(LinkpayConstants.SUCCEED)) {
                        Intent newIntent = new Intent(this, CashPayActivity.class);
                        newIntent.setData(uri);
                        startActivity(newIntent);
                    }
                }
            } catch(Exception e) {
                EZToast.show(getApplicationContext(), e.getMessage());
            }
        }
    }

    public void onClickClear(View view) {
    }

    public void onClickSave(View view) {

    }

    /**
     * 카드 결제
     * @param view
     */
    public void onClickCardPay(View view){
        Intent intent = new Intent(this, CardPayActivity.class);
        startActivity(intent);
    }

    /**
     * 현금 결제
     * @param view
     */
    public void onClickCashPay(View view){
        Intent intent = new Intent(this, CashPayActivity.class);
        startActivity(intent);
    }
}
