package com.bigbaws.hanganimals.backend.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.bigbaws.hanganimals.R;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ShippingAddress;


/**
 * Created by Silas on 04-05-2017.
 */

public class PayPalController {
    public static final String TAG = "paymentController";
    /**
     * - Set to PayPalConfiguration.ENVIRONMENT_PRODUCTION to move real money.
     *
     * - Set to PayPalConfiguration.ENVIRONMENT_SANDBOX to use your test credentials
     * from https://developer.paypal.com
     *
     * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
     * without communicating to PayPal's servers.
     */
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    // current client ID is a sandbox ID
    private static final String CONFIG_CLIENT_ID = "AcCmzGPuvFqoosGlapFZmDou_3SDC9a8ctAYtpo7UATjQl2pER3FAOIgBqXmUwcMBgSihZS1KRtDcKp0";
    public static final int REQUEST_CODE_PAYMENT = 1;
    public static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    public static final int REQUEST_CODE_PROFILE_SHARING = 3;

    public static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID);




    /*
     * This method shows use of optional payment details and item list.
     */
//    private PayPalPayment getStuffToBuy(String paymentIntent) {
//        //--- include an item list, payment amount details
//
//
//
//        PayPalItem[] items =
//                {
//                        new PayPalItem("sample item #1", 2, new BigDecimal("87.50"), "USD",
//                                "sku-12345678"),
//                        new PayPalItem("free sample item #2", 1, new BigDecimal("0.00"),
//                                "USD", "sku-zero-price"),
//                        new PayPalItem("sample item #3 with a longer name", 6, new BigDecimal("37.99"),
//                                "USD", "sku-33333")
//                };
//        BigDecimal subtotal = PayPalItem.getItemTotal(items);
//        BigDecimal shipping = new BigDecimal("7.21");
//        BigDecimal tax = new BigDecimal("4.67");
//        PayPalPaymentDetails paymentDetails = new PayPalPaymentDetails(shipping, subtotal, tax);
//        BigDecimal amount = subtotal.add(shipping).add(tax);
//
//        BigDecimal total = PayPalItem.getItemTotal(items);
//        PayPalPayment payment = new PayPalPayment(amount, "USD", "sample item", paymentIntent);
//        payment.items(items).paymentDetails(paymentDetails);
//
//        //--- set other optional fields like invoice_number, custom field, and soft_descriptor
//        payment.custom("This is text that will be associated with the payment that the app can use.");
//
//        return payment;
//    }

    /*
     * Add app-provided shipping address to payment
     */
//    private void addAppProvidedShippingAddress(PayPalPayment paypalPayment) {
//        ShippingAddress shippingAddress =
//                new ShippingAddress().recipientName("Mom Parker").line1("52 North Main St.")
//                        .city("Austin").state("TX").postalCode("78729").countryCode("US");
//        paypalPayment.providedShippingAddress(shippingAddress);
//    }

//    /*
//     * Enable retrieval of shipping addresses from buyer's PayPal account
//     */
//    private void enableShippingAddressRetrieval(PayPalPayment paypalPayment, boolean enable) {
//        paypalPayment.enablePayPalShippingAddressesRetrieval(enable);
//    }

//    public void onFuturePaymentPressed(View pressed) {
//        Intent intent = new Intent(SampleActivity.this, PayPalFuturePaymentActivity.class);
//
//        // send the same configuration for restart resiliency
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
//    }

//    public void onProfileSharingPressed(View pressed) {
//        Intent intent = new Intent(SampleActivity.this, PayPalProfileSharingActivity.class);
//
//        // send the same configuration for restart resiliency
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        intent.putExtra(PayPalProfileSharingActivity.EXTRA_REQUESTED_SCOPES, getOauthScopes());
//
//        startActivityForResult(intent, REQUEST_CODE_PROFILE_SHARING);
//    }

//    private PayPalOAuthScopes getOauthScopes() {
//        /* create the set of required scopes
//         * Note: see https://developer.paypal.com/docs/integration/direct/identity/attributes/ for mapping between the
//         * attributes you select for this app in the PayPal developer portal and the scopes required here.
//         */
//        Set<String> scopes = new HashSet<String>(
//                Arrays.asList(PayPalOAuthScopes.PAYPAL_SCOPE_EMAIL, PayPalOAuthScopes.PAYPAL_SCOPE_ADDRESS) );
//        return new PayPalOAuthScopes(scopes);
//    }

//    protected void displayResultText(String result) {
//        ((TextView)findViewById(R.id.txtResult)).setText("Result : " + result);
//        Toast.makeText(
//                getApplicationContext(),
//                result, Toast.LENGTH_LONG)
//                .show();
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_CODE_PAYMENT) {
//            if (resultCode == Activity.RESULT_OK) {
//                PaymentConfirmation confirm =
//                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//                if (confirm != null) {
//                    try {
//                        Log.i(TAG, confirm.toJSONObject().toString(4));
//                        Log.i(TAG, confirm.getPayment().toJSONObject().toString(4));
//                        /**
//                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
//                         * or consent completion.
//                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
//                         * for more details.
//                         *
//                         * For sample mobile backend interactions, see
//                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
//                         */
//                        displayResultText("PaymentConfirmation info received from PayPal");
//
//
//                    } catch (JSONException e) {
//                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i(TAG, "The user canceled.");
//            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
//                Log.i(
//                        TAG,
//                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
//            }
//        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
//            if (resultCode == Activity.RESULT_OK) {
//                PayPalAuthorization auth =
//                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
//                if (auth != null) {
//                    try {
//                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));
//
//                        String authorization_code = auth.getAuthorizationCode();
//                        Log.i("FuturePaymentExample", authorization_code);
//
//                        sendAuthorizationToServer(auth);
//                        displayResultText("Future Payment code received from PayPal");
//
//                    } catch (JSONException e) {
//                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i("FuturePaymentExample", "The user canceled.");
//            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
//                Log.i(
//                        "FuturePaymentExample",
//                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
//            }
//        } else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
//            if (resultCode == Activity.RESULT_OK) {
//                PayPalAuthorization auth =
//                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
//                if (auth != null) {
//                    try {
//                        Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));
//
//                        String authorization_code = auth.getAuthorizationCode();
//                        Log.i("ProfileSharingExample", authorization_code);
//
//                        sendAuthorizationToServer(auth);
//                        displayResultText("Profile Sharing code received from PayPal");
//
//                    } catch (JSONException e) {
//                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                Log.i("ProfileSharingExample", "The user canceled.");
//            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
//                Log.i(
//                        "ProfileSharingExample",
//                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
//            }
//        }
//    }

//    private void sendAuthorizationToServer(PayPalAuthorization authorization) {
//
//        /**
//         * TODO: Send the authorization response to your server, where it can
//         * exchange the authorization code for OAuth access and refresh tokens.
//         *
//         * Your server must then store these tokens, so that your server code
//         * can execute payments for this user in the future.
//         *
//         * A more complete example that includes the required app-server to
//         * PayPal-server integration is available from
//         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
//         */
//
//    }

//    public void onFuturePaymentPurchasePressed(View pressed) {
//        // Get the Client Metadata ID from the SDK
//        String metadataId = PayPalConfiguration.getClientMetadataId(this);
//
//        Log.i("FuturePaymentExample", "Client Metadata ID: " + metadataId);
//
//        // TODO: Send metadataId and transaction details to your server for processing with
//        // PayPal...
//        displayResultText("Client Metadata Id received from SDK");
//    }

//    @Override
//    public void onDestroy() {
//        // Stop service when done
//        stopService(new Intent(this, PayPalService.class));
//        super.onDestroy();
//    }

}