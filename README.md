<h1 align="center">OPay sdk for android</h1>
The third-party App connects to the Opay SDK, allowing users to call up the Opay application from the App for login and payment operations. Developers can obtain this permission after applying for an account in the backstage of the opay merchant and passing the review.
Developers can integrate the Opay SDK in the App. The following is an example of calling login and payment.  

## Download
Note: The opay app only supports third-party login and payment functions from version 2.15.0 or higher, please download the new version of the app for testing
You can use Gradle:
```xml
dependencies {
    implementation 'com.opayweb:android-pay-sdk:1.0.0'
}

```

## Samples

1、Example of third-party login
```java
new LoginTask(publicKey, merchantId, aesKey)
        .login(
            MainActivity.this,
            new LoginResultCallback() {
              @Override
              public void onResult(LoginResult status) {
                //hideLoading();
                Toast.makeText(MainActivity.this, status.getStatus().getMsg(), Toast.LENGTH_SHORT)
                    .show();
              }
            });
```

2、Example of payment
```java
new PayTask.GatewayBuilder(
            10,//order amount
            "NGN",//Currency
            "superbetorder" + new Random().nextInt(),//Merchant order number
            "superbet",//Business Name
             publicKey)//publicKey，Query from the merchant background
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
```
3、Example of Gateway payment
```java
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
```

Payment result acquisition and processing
After calling the pay method to pay, the payment result will be obtained in two ways:
● Synchronous return
The merchant application client obtains the payment result through its callback function in the Activity that currently calls the payment. You can refer to the above code
● Asynchronous notification
Merchants need to provide an http protocol interface. After the payment is completed, the opay server will call this interface to transfer data​.
