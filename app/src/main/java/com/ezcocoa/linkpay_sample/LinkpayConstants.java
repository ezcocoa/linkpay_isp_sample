package com.ezcocoa.linkpay_sample;

/**
 * Created by hojunbaek on 25/10/2016.
 */

public class LinkpayConstants {
    public static final String PAYMENT_METHOD       = "paymentMethod";
    public static final String PAYMENT_TYPE         = "paymentType";
    public static final String STORE_NUMBER         = "storeNumber";
    public static final String STORE_ROOT_CAT_ID    = "storeRootCatId";
    public static final String STORE_CAT_ID         = "storeCatId";
    public static final String ORDER_SUPPLY         = "orderSupply";
    public static final String ORDER_VAT            = "orderVat";
    public static final String ORDER_TAX            = "orderTax";
    public static final String ORG_AUTH_NUMBER      = "orgAuthNumber";
    public static final String ORG_AUTH_DATE        = "orgAuthDate";
    public static final String ORG_AUTH_TIME        = "orgAuthTime";
    public static final String ORG_INSTALLMENT      = "orgInstallment";
    public static final String RESULT               = "result";
    public static final String MESSAGE              = "message";
    public static final String AUTH_NUMBER          = "authNumber";        // 승인번호
    public static final String AUTH_DATE            = "authDate";          // 승인일자
    public static final String AUTH_TIME            = "authTime";          // 승인시간
    public static final String CARD_NUMBER          = "cardNumber";        // 카드번호
    public static final String NO_SIGNATURE_FLAG    = "noSignatureFlag";   // 무서명거래 여부
    public static final String INSTALLMENT          = "installment";       // 할부개월
    public static final String ISSUING_COMPANY_CODE = "issuingCompanyCode";// 발급사코드
    public static final String ISSUING_COMPANY_NAME = "issuingCompanyName";// 발급사명
    public static final String BUYING_COMPANY_CODE  = "buyingCompanyCode"; // 매입사코드
    public static final String BUYING_COMPANY_NAME  = "buyingCompanyName"; // 매입사명
    public static final String ADDITION_INFO        = "addionInfo"; // 추가정보
    public static final String CALLBACK_URL         = "callbackUrl";
    public static final String SIGN_PATH            = "signPath";

    public static final String CARD    = "1";  // 카드
    public static final String CASH    = "2";  // 현금
    public static final String APPROVE = "1";  // 승인
    public static final String CANCEL  = "2";  // 취소
    public static final String SUCCEED = "1";  // 성공
    public static final String FAILD   = "0";  // 실패
}
