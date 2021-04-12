package com.opay.app.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.opay.account.base.BaseActivity;
import com.opay.account.constant.Constant;
import com.opay.account.core.LoginTask;
import com.opay.account.core.PayTask;
import com.opay.account.iinterface.LoginResultCallback;
import com.opay.account.iinterface.PayResultCallback;
import com.opay.account.iinterface.PaymentType;
import com.opay.account.iinterface.ResultStatus;
import com.opay.account.model.LoginResult;
import com.opay.app.R;

import java.util.Random;

public class MainActivity extends BaseActivity {
  private Button mLoginButton;
  private Button mPayButton;
  private Button mFrozenPayButton;
  private String publicKey = "OPAYPUB15706995628370.46385359588504393";
  private String merchantId = "256619101012001";
  private String aesKey = "VQR8GJmDPOxwuph2";

  @Override
  public void onCreate(@Nullable Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.activity_main);
    initView();
    initData();
    Constant.packageList.add("com.opay.app");
  }

  private void initView() {
    mLoginButton = findViewById(R.id.login);
    mPayButton = findViewById(R.id.pay);
    mFrozenPayButton = findViewById(R.id.pay_frozen);
  }

  private void initData() {
    mLoginButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            testLogin();
          }
        });
    mPayButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            testPay();
          }
        });
      mFrozenPayButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              testPayForNewGateway();
          }
        });
  }

  private void testPay() {
    showLoading();
    new PayTask.GatewayBuilder(
            10,//order amount
            "NGN",//Currency
            "superbetorder" + new Random().nextInt(),//Merchant order number
            "superbet",//Business Name
             publicKey)//publicKey，Query from the merchant background
        .merchantUserId("123456")//Merchant user id，Optional parameters，For display at the cash register
        .description("我是sdk，需要支付……")//Order description，Optional parameters，For display at the cash register
        .build()
        .pay(
            MainActivity.this,
            new PayResultCallback() {
              @Override
              public void onResult(ResultStatus status) {
                  //do something
              }
            });
  }

  private void testPayForNewGateway() {
    showLoading();
    new PayTask.GatewayCommitBuilder(
            10,//order amount
            "NGN",//Currency
            "superbetorder" + new Random().nextInt(),//Merchant order number
            "superbet",//Business Name
             publicKey,//publicKey，Query from the merchant background
            "524352435243",//opay order number
            PaymentType.ESCROW)//payment method，1、ESCROW（Guaranteed payment）2、ORDINARY（New version of merchant acquiring）
        .merchantUserId("123456")//Merchant user id，Optional parameters，For display at the cash register
        .description("description……")//Order description，Optional parameters，For display at the cash register
        .build()
        .pay(
            MainActivity.this,
            new PayResultCallback() {
              @Override
              public void onResult(ResultStatus status) {
               //do something
              }
            });
  }

  private void testLogin() {
    showLoading();
    new LoginTask(publicKey, merchantId, aesKey)
        .login(
            MainActivity.this,
            new LoginResultCallback() {
              @Override
              public void onResult(LoginResult status) {
                hideLoading();
                Toast.makeText(MainActivity.this, status.getStatus().getMsg(), Toast.LENGTH_SHORT)
                    .show();
              }
            });
  }
}
