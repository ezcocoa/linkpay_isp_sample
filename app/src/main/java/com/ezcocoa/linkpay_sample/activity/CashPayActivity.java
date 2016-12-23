package com.ezcocoa.linkpay_sample.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezcocoa.linkpay_sample.BuildConfig;
import com.ezcocoa.linkpay_sample.LinkpayConstants;
import com.ezcocoa.linkpay_sample.R;
import com.ezcocoa.linkpay_sample.ez.EZAlertDialog;
import com.ezcocoa.linkpay_sample.ez.EZToast;
import com.ezcocoa.linkpay_sample.util.URLUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 현금결제 요청 화면
 */
public class CashPayActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String _scheme = "linkpay://default";
    // UI
    @Bind(R.id.a_cash_pay_storeNumberEt) EditText _storeNumberEt;
    @Bind(R.id.a_cash_pay_storeRootCatIdEt) EditText _storeRootCatIdEt;
    @Bind(R.id.a_cash_pay_storeCatIdEt) EditText _storeCatIdEt;
    @Bind(R.id.a_cash_pay_orderSupplyEt) EditText _orderSupplyEt;
    @Bind(R.id.a_cash_pay_orderVatEt) EditText _orderVatEt;
    @Bind(R.id.a_cash_pay_orderTaxEt) EditText _orderTaxEt;
    @Bind(R.id.a_cash_pay_orgAuthNumberEt) EditText _orgAuthNumberEt;
    @Bind(R.id.a_cash_pay_orgAuthDateEt) EditText _orgAuthDateEt;
    @Bind(R.id.a_cash_pay_orgInstallmentEt) EditText _orgInstallmentEt;
    @Bind(R.id.a_cash_pay_cardNoEt) EditText _cardNoEt;
    @Bind(R.id.a_cash_pay_cashPayBtn) Button _cashPayBtn;
    @Bind(R.id.a_cash_pay_cashCancelBtn) Button _cashCancelBtn;

    @Bind(R.id.a_cash_pay_orgAuthNumberLayout) LinearLayout _orgAuthNumberLayout;
    @Bind(R.id.a_cash_pay_orgAuthDateLayout) LinearLayout _orgAuthDateLayout;

    // 추가정보
    @Bind(R.id.a_cash_pay_etcLayout) LinearLayout _etcLayout;
    @Bind(R.id.a_cash_pay_etcTitleTv) TextView _etcTitleTv;
    @Bind(R.id.a_cash_pay_etcTv) TextView _etcTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_pay);
        ButterKnife.bind(this);

        if (BuildConfig.DEBUG) {
            _storeNumberEt.setText("1168143939");
            _storeRootCatIdEt.setText("90100546");
            _storeCatIdEt.setText("90100546");
            _orderSupplyEt.setText("1000");
            _orderVatEt.setText("4");
            _orgInstallmentEt.setText("0");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();

        // 응답데이터 > URL Scheme 방식으로 호출된경우 결과정보를 얻어서 처리..
        if (null != getIntent().getData()) {
            Uri uri = getIntent().getData();
            Log.d(TAG, "uri->"+uri);
             EZAlertDialog.show1(getSupportFragmentManager(), uri.toString());

            try {
                String result           = uri.getQueryParameter(LinkpayConstants.RESULT);             // 결제성공여부
                String paymentMethod    = uri.getQueryParameter(LinkpayConstants.PAYMENT_METHOD);     // 카드현금구분
                String paymentType      = uri.getQueryParameter(LinkpayConstants.PAYMENT_TYPE);       // 거래구분
                String message          = URLDecoder.decode(uri.getQueryParameter(LinkpayConstants.MESSAGE), "UTF-8");  // 결제메시지

                if (LinkpayConstants.SUCCEED.equals(result)) {
                    EZToast.show(getApplicationContext(), getString(R.string.approved));
                } else {
                    EZToast.show(getApplicationContext(), getString(R.string.failed));
                }

                if (paymentMethod.equals(LinkpayConstants.CASH)) { // 현금
                    if (result.equals(LinkpayConstants.SUCCEED)) {
                        _orgAuthNumberEt.setText(uri.getQueryParameter(LinkpayConstants.AUTH_NUMBER));
                        _orgAuthDateEt.setText(uri.getQueryParameter(LinkpayConstants.AUTH_DATE));
                        _orgInstallmentEt.setText(uri.getQueryParameter(LinkpayConstants.INSTALLMENT));
                        _cardNoEt.setText(uri.getQueryParameter(LinkpayConstants.CARD_NUMBER));

                        // 추가 정보
                        _etcLayout.setVisibility(View.VISIBLE);
                        String title = "승인시간\n"+"카드번호\n"+"발급사 코드\n" + "발급사명\n" +"매입사 코드\n" + "매입사명";
                        String value = uri.getQueryParameter(LinkpayConstants.AUTH_TIME) + "\n" +
                                       uri.getQueryParameter(LinkpayConstants.CARD_NUMBER) + "\n" +
                                       uri.getQueryParameter(LinkpayConstants.ISSUING_COMPANY_CODE) + "\n" +
                                       uri.getQueryParameter(LinkpayConstants.ISSUING_COMPANY_NAME) + "\n" +
                                       uri.getQueryParameter(LinkpayConstants.BUYING_COMPANY_CODE) + "\n" +
                                       uri.getQueryParameter(LinkpayConstants.BUYING_COMPANY_NAME);
                        _etcTitleTv.setText(title);
                        _etcTv.setText(value);

                        _orgAuthNumberLayout.setVisibility(View.VISIBLE);
                        _orgAuthDateLayout.setVisibility(View.VISIBLE);

                        _cashPayBtn.setVisibility(View.GONE);
                        _cashCancelBtn.setVisibility(View.VISIBLE);
                    }
                }
            } catch(Exception e) {
                EZToast.show(getApplicationContext(), e.getMessage());
            }
        }
    }



    /**
     * 현금 결제
     * @param view
     */
    public void onClickCashPay(View view){
        String storeNumber    = _storeNumberEt.getText().toString();
        String storeRootCatId = _storeRootCatIdEt.getText().toString();
        String storeCatId     = _storeCatIdEt.getText().toString();
        String orderSupply    = _orderSupplyEt.getText().toString();
        String orderVat       = _orderVatEt.getText().toString();
        String orderTax       = _orderTaxEt.getText().toString();
        String installment    = _orgInstallmentEt.getText().toString();
        String cardNo         = _cardNoEt.getText().toString();
        try {
            String paymentMethod = LinkpayConstants.CASH;
            String paymentType   = LinkpayConstants.APPROVE;
            String callbackUrl   = "callbackapp://default";
            String additionInfo  = URLEncoder.encode("tip=백원","UTF-8");

            StringBuilder sb = new StringBuilder();
            sb = URLUtil.appendStart(sb, _scheme);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.STORE_NUMBER, storeNumber);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.STORE_ROOT_CAT_ID, storeRootCatId);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.STORE_CAT_ID, storeCatId);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.CARD_NUMBER, cardNo);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.PAYMENT_METHOD, paymentMethod);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.PAYMENT_TYPE, paymentType);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORDER_SUPPLY, orderSupply);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORDER_VAT, orderVat);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORDER_TAX, orderTax);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.CALLBACK_URL, callbackUrl);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.INSTALLMENT, installment);
            sb = URLUtil.append(sb, LinkpayConstants.ADDITION_INFO, additionInfo);
            Log.d(TAG, "request cash pay->"+sb.toString());

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
            startActivity(intent);
            finish();
        } catch (UnsupportedEncodingException e) {

        } catch(ActivityNotFoundException e) {
            EZAlertDialog.show1(getSupportFragmentManager(), getString(R.string.install_linkpay_app));
        }
    }

    /**
     * 현금결제취소
     * @param view
     */
    public void onClickCashCancel(View view){
        String storeNumber    = _storeNumberEt.getText().toString();
        String storeRootCatId = _storeRootCatIdEt.getText().toString();
        String storeCatId     = _storeCatIdEt.getText().toString();
        String orderSupply    = _orderSupplyEt.getText().toString();
        String orderVat       = _orderVatEt.getText().toString();
        String orderTax       = _orderTaxEt.getText().toString();
        String installment    = _orgInstallmentEt.getText().toString();
        String orgAuthNumber  = _orgAuthNumberEt.getText().toString();
        String orgAuthDate    = _orgAuthDateEt.getText().toString();
        String cardNo         = _cardNoEt.getText().toString();

        Uri uri = getIntent().getData();

        try {
            String paymentMethod = LinkpayConstants.CASH;
            String paymentType   = LinkpayConstants.CANCEL;
            String callbackUrl   = "callbackapp://default";
            String additionInfo  = URLEncoder.encode("tip=백원","UTF-8");

            StringBuilder sb = new StringBuilder();
            sb = URLUtil.appendStart(sb, _scheme);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.STORE_NUMBER, storeNumber);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.STORE_ROOT_CAT_ID, storeRootCatId);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.STORE_CAT_ID, storeCatId);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.CARD_NUMBER, cardNo);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.PAYMENT_METHOD, paymentMethod);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.PAYMENT_TYPE, paymentType);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORDER_SUPPLY, orderSupply);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORDER_VAT, orderVat);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORDER_TAX, orderTax);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.CALLBACK_URL, callbackUrl);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORG_AUTH_NUMBER, orgAuthNumber);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORG_AUTH_DATE, orgAuthDate);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORG_AUTH_TIME, uri.getQueryParameter(LinkpayConstants.AUTH_TIME));
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ORG_INSTALLMENT, installment);
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ISSUING_COMPANY_CODE, uri.getQueryParameter(LinkpayConstants.ISSUING_COMPANY_CODE));
            sb = URLUtil.appendAnd(sb, LinkpayConstants.ISSUING_COMPANY_NAME, uri.getQueryParameter(LinkpayConstants.ISSUING_COMPANY_NAME));
            sb = URLUtil.appendAnd(sb, LinkpayConstants.BUYING_COMPANY_CODE, uri.getQueryParameter(LinkpayConstants.BUYING_COMPANY_CODE));
            sb = URLUtil.appendAnd(sb, LinkpayConstants.BUYING_COMPANY_NAME, uri.getQueryParameter(LinkpayConstants.BUYING_COMPANY_NAME));
            sb = URLUtil.append(sb, LinkpayConstants.ADDITION_INFO, additionInfo);

            Log.d(TAG, "request cash cancel->"+sb.toString());

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sb.toString()));
            startActivity(intent);
            finish();
        } catch (UnsupportedEncodingException e) {

        } catch (ActivityNotFoundException e) {
            EZAlertDialog.show1(getSupportFragmentManager(), getString(R.string.install_linkpay_app));
        }
    }
}
