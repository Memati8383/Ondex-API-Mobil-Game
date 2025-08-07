package com.my;

// Android core and UI
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.*;

// File and network operations
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// Data and date handling
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

// JSON handling
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

	// ScrollView referansı
	private ScrollView scrollView;

	// Ana UI thread üzerinde işlem yapmak için Handler
	private Handler mainHandler = new Handler(Looper.getMainLooper());

	// Butonlar (API çağrıları için)
	private Button btnApi1, btnApi2, btnApi3, btnApi4, btnApi5, btnApi6, btnApi7, btnApi8, btnApi9, btnApi10, btnApi11,
			btnApi12, btnApi13, btnApi14;

	// API'ler için ayrı ayrı LinearLayout'lar (arayüz düzenleri)
	private LinearLayout layoutApi1, layoutApi2, layoutApi3, layoutApi4, layoutApi5, layoutApi6, layoutApi7, layoutApi8,
			layoutApi9, layoutApi10, layoutApi11, layoutApi12, layoutApi13, layoutApi14;

	// API'lere özgü EditText alanları (kullanıcıdan veri girişi için)
	private EditText etTcApi1, etAdApi2, etSoyadApi2, etIlApi2, etIlceApi2, etTcApi3, etTcApi4, etTcApi5, etTcApi6,
			etTcApi7, etTcApi8, etTcApi9, etGsmApi9, etTcApi10, etTcApi11, etTcApi12, etTcApi13, etTcApi14;

	// Sorgu butonları (API çağrılarını başlatmak için)
	private Button btnFetchApi1, btnFetchApi2, btnFetchApi3, btnFetchApi4, btnFetchApi5, btnFetchApi6, btnFetchApi7,
			btnFetchApi8, btnFetchApi9, btnFetchApi10, btnFetchApi11, btnFetchApi12, btnFetchApi13, btnFetchApi14;

	// API çağrısı sonuçlarının gösterileceği LinearLayout container'ları
	private LinearLayout resultContainerApi1, resultContainerApi2, resultContainerApi3, resultContainerApi4,
			resultContainerApi5, resultContainerApi6, resultContainerApi7, resultContainerApi8, resultContainerApi9,
			resultContainerApi10, resultContainerApi11, resultContainerApi12, resultContainerApi13,
			resultContainerApi14;

	private TextView tvApi1Baslik, tvApi2Baslik, tvApi3Baslik, tvApi4Baslik, tvApi5Baslik, tvApi6Baslik, tvApi7Baslik,
			tvApi8Baslik, tvApi9Baslik, tvApi10Baslik, tvApi11Baslik, tvApi12Baslik, tvApi13Baslik, tvApi14Baslik;

	// En son alınan API sonuçlarını tutan JSONArray ya da JSONObject değişkenleri
	private JSONArray lastApi2Results, lastApi3Results, lastApi4Results, lastApi5Results, lastApi6Results,
			lastApi7Results, lastApi8Results, lastApi9Results, lastApi10Results, lastApi11Results, lastApi12Results,
			lastApi13Results, lastApi14Results;
	private JSONObject lastApi1Results;

	private EditText etFilterApi1, etFilterApi2, etFilterApi3, etFilterApi4, etFilterApi5, etFilterApi6, etFilterApi7,
			etFilterApi8, etFilterApi9, etFilterApi10, etFilterApi11, etFilterApi12, etFilterApi13, etFilterApi14;

	private static final String CHANNEL_ID = "api_notifications_channel";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Durum çubuğu (StatusBar) ayarları, Android 5.0 (Lollipop) ve üzeri sürümler için geçerli
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			// Durum çubuğundaki saydamlık bayrağını kaldır
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// Durum çubuğu arka planını kendimiz çizmek için bayrak ekle
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			// Durum çubuğunu tam şeffaf yap
			window.setStatusBarColor(Color.TRANSPARENT);
			// Sistem UI görünüm modunu ayarla, layout'un durum çubuğunun arkasına kadar uzanmasını sağlar
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
		}

		// Activity'nin layout'unu belirt
		setContentView(R.layout.activity_main);

		createNotificationChannel();

		// Eğer aksiyon çubuğu (ActionBar) varsa
		if (getActionBar() != null) {
			ActionBar actionBar = getActionBar();
			// Aksiyon çubuğunun arka planını tam şeffaf yap
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			// Başlığı gizle (yani actionBar üzerinde başlık gösterilmesin)
			actionBar.setDisplayShowTitleEnabled(false);
			// Geri butonunu göster ve tıklama olayına hazırla
			actionBar.setDisplayHomeAsUpEnabled(true);

		}

		// Butonlar (Ana menü butonları)
		btnApi1 = findViewById(R.id.btnMenuApi1);
		btnApi2 = findViewById(R.id.btnMenuApi2);
		btnApi3 = findViewById(R.id.btnMenuApi3);
		btnApi4 = findViewById(R.id.btnMenuApi4);
		btnApi5 = findViewById(R.id.btnMenuApi5);
		btnApi6 = findViewById(R.id.btnMenuApi6);
		btnApi7 = findViewById(R.id.btnMenuApi7);
		btnApi8 = findViewById(R.id.btnMenuApi8);
		btnApi9 = findViewById(R.id.btnMenuApi9);
		btnApi10 = findViewById(R.id.btnMenuApi10);
		btnApi11 = findViewById(R.id.btnMenuApi11);
		btnApi12 = findViewById(R.id.btnMenuApi12);
		btnApi13 = findViewById(R.id.btnApi13);
		btnApi14 = findViewById(R.id.btnMenuApi14);

		// Layoutlar (Her API için ayrı layoutlar)
		layoutApi1 = findViewById(R.id.layoutApi1);
		layoutApi2 = findViewById(R.id.layoutApi2);
		layoutApi3 = findViewById(R.id.layoutApi3);
		layoutApi4 = findViewById(R.id.layoutApi4);
		layoutApi5 = findViewById(R.id.layoutApi5);
		layoutApi6 = findViewById(R.id.layoutApi6);
		layoutApi7 = findViewById(R.id.layoutApi7);
		layoutApi8 = findViewById(R.id.layoutApi8);
		layoutApi9 = findViewById(R.id.layoutApi9);
		layoutApi10 = findViewById(R.id.layoutApi10);
		layoutApi11 = findViewById(R.id.layoutApi11);
		layoutApi12 = findViewById(R.id.layoutApi12);
		layoutApi13 = findViewById(R.id.layoutApi13);
		layoutApi14 = findViewById(R.id.layoutApi14);

		// EditTextler (Kullanıcıdan veri alınan alanlar)
		etTcApi1 = findViewById(R.id.etTcApi1);

		etAdApi2 = findViewById(R.id.etAdApi2);
		etSoyadApi2 = findViewById(R.id.etSoyadApi2);
		etIlApi2 = findViewById(R.id.etIlApi2);
		etIlceApi2 = findViewById(R.id.etIlceApi2);

		etTcApi3 = findViewById(R.id.etTcApi3);
		etTcApi4 = findViewById(R.id.etTcApi4);
		etTcApi5 = findViewById(R.id.etTcApi5);
		etTcApi6 = findViewById(R.id.etTcApi6);
		etTcApi7 = findViewById(R.id.etTcApi7);
		etTcApi8 = findViewById(R.id.etTcApi8);
		etTcApi9 = findViewById(R.id.etTcApi9);
		etTcApi10 = findViewById(R.id.etTcOrGsm);
		etTcApi11 = findViewById(R.id.etTcApi11);
		etTcApi12 = findViewById(R.id.etTcApi12);
		etTcApi13 = findViewById(R.id.etTcApi13);
		etTcApi14 = findViewById(R.id.etTcApi14);

		etFilterApi1 = findViewById(R.id.etFilterApi1);
		etFilterApi2 = findViewById(R.id.etFilterApi2);
		etFilterApi3 = findViewById(R.id.etFilterApi3);
		etFilterApi4 = findViewById(R.id.etFilterApi4);
		etFilterApi5 = findViewById(R.id.etFilterApi5);
		etFilterApi6 = findViewById(R.id.etFilterApi6);
		etFilterApi7 = findViewById(R.id.etFilterApi7);
		etFilterApi8 = findViewById(R.id.etFilterApi8);
		etFilterApi9 = findViewById(R.id.etFilterApi9);
		etFilterApi10 = findViewById(R.id.etFilterApi10);
		etFilterApi11 = findViewById(R.id.etFilterApi11);
		etFilterApi12 = findViewById(R.id.etFilterApi12);
		etFilterApi13 = findViewById(R.id.etFilterApi13);
		etFilterApi14 = findViewById(R.id.etFilterApi14);

		tvApi1Baslik = findViewById(R.id.tvApi1Baslik);
		tvApi2Baslik = findViewById(R.id.tvApi2Baslik);
		tvApi3Baslik = findViewById(R.id.tvApi3Baslik);
		tvApi4Baslik = findViewById(R.id.tvApi4Baslik);
		tvApi5Baslik = findViewById(R.id.tvApi5Baslik);
		tvApi6Baslik = findViewById(R.id.tvApi6Baslik);
		tvApi7Baslik = findViewById(R.id.tvApi7Baslik);
		tvApi8Baslik = findViewById(R.id.tvApi8Baslik);
		tvApi9Baslik = findViewById(R.id.tvApi9Baslik);
		tvApi10Baslik = findViewById(R.id.tvApi10Baslik);
		tvApi11Baslik = findViewById(R.id.tvApi11Baslik);
		tvApi12Baslik = findViewById(R.id.tvApi12Baslik);
		tvApi13Baslik = findViewById(R.id.tvApi13Baslik);
		tvApi14Baslik = findViewById(R.id.tvApi14Baslik);

		// Sorgu butonları (API isteklerini tetikleyen butonlar)
		btnFetchApi1 = findViewById(R.id.btnFetchApi1);
		btnFetchApi2 = findViewById(R.id.btnFetchApi2);
		btnFetchApi3 = findViewById(R.id.btnFetchApi3);
		btnFetchApi4 = findViewById(R.id.btnFetchApi4);
		btnFetchApi5 = findViewById(R.id.btnFetchApi5);
		btnFetchApi6 = findViewById(R.id.btnFetchApi6);
		btnFetchApi7 = findViewById(R.id.btnFetchApi7);
		btnFetchApi8 = findViewById(R.id.btnFetchApi8);
		btnFetchApi9 = findViewById(R.id.btnFetchApi9);
		btnFetchApi10 = findViewById(R.id.btnFetchApi10);
		btnFetchApi11 = findViewById(R.id.btnFetchApi11);
		btnFetchApi12 = findViewById(R.id.btnFetchApi12);
		btnFetchApi13 = findViewById(R.id.btnFetchApi13);
		btnFetchApi14 = findViewById(R.id.btnFetchApi14);

		// Sonuç konteynerleri (API sonuçlarının gösterileceği alanlar)
		resultContainerApi1 = findViewById(R.id.resultContainerApi1);
		resultContainerApi2 = findViewById(R.id.resultContainerApi2);
		resultContainerApi3 = findViewById(R.id.resultContainerApi3);
		resultContainerApi4 = findViewById(R.id.resultContainerApi4);
		resultContainerApi5 = findViewById(R.id.resultContainerApi5);
		resultContainerApi6 = findViewById(R.id.resultContainerApi6);
		resultContainerApi7 = findViewById(R.id.resultContainerApi7);
		resultContainerApi8 = findViewById(R.id.resultContainerApi8);
		resultContainerApi9 = findViewById(R.id.resultContainerApi9);
		resultContainerApi10 = findViewById(R.id.resultContainerApi10);
		resultContainerApi11 = findViewById(R.id.resultContainerApi11);
		resultContainerApi12 = findViewById(R.id.resultContainerApi12);
		resultContainerApi13 = findViewById(R.id.resultContainerApi13);
		resultContainerApi14 = findViewById(R.id.resultContainerApi14);

		// ScrollView bileşenini tanımla
		scrollView = findViewById(R.id.scrollView);

		// Butona tıklanınca oynatılacak animasyonu yükle (bounce efekti)
		Animation clickAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

		// btnApi1 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi1.setOnClickListener(v -> {
			v.startAnimation(clickAnim); // Tıklama animasyonunu oynat
			showApiLayout(layoutApi1); // Api1 layoutunu göster
		});

		// btnApi2 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi2.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi2);
		});

		// btnApi3 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi3.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi3);
		});

		// btnApi4 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi4.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi4);
		});

		// btnApi5 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi5.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi5);
		});

		// btnApi6 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi6.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi6);
		});

		// btnApi7 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi7.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi7);
		});

		// btnApi8 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi8.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi8);
		});

		// btnApi9 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi9.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi9);
		});

		// btnApi10 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi10.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi10);
		});

		// btnApi11 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi11.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi11);
		});

		// btnApi12 butonuna tıklanınca animasyon başlatılır ve ilgili layout gösterilir
		btnApi12.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi12);
		});

		btnApi13.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi13);
		});
		btnApi14.setOnClickListener(v -> {
			v.startAnimation(clickAnim);
			showApiLayout(layoutApi14);
		});

		// Başlangıçta tüm API layoutlarını gizle
		hideAllApiLayouts();

		// API1 - TC ile sorgu
		btnFetchApi1.setOnClickListener(v -> {
			String tc = etTcApi1.getText().toString().trim();
			EditText etFilterApi1 = findViewById(R.id.etFilterApi1);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}
			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/tcpro.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi1, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("data")) {
								JSONObject veri = json.getJSONObject("data");
								lastApi1Results = veri;
								JSONArray arr = new JSONArray();
								arr.put(veri);
								String filter = etFilterApi1.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("TC Sorgulama Başarılı", arr.length() + " kişi bulundu.", 1001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi1Results = null;
								sendNotification("TC Sorgulama Sonuç Yok", "Kişi bulunamadı.", 1002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("TC Sorgulama JSON Hatası", e.getMessage(), 1003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi1Results = null;
						sendNotification("TC Sorgulama Hatası", errorMessage, 1004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API2 - Ad, Soyad, İl, İlçe ile sorgu
		btnFetchApi2.setOnClickListener(v -> {
			String ad = etAdApi2.getText().toString().trim();
			String soyad = etSoyadApi2.getText().toString().trim();
			String il = etIlApi2.getText().toString().trim();
			String ilce = etIlceApi2.getText().toString().trim();
			EditText etFilterApi2 = findViewById(R.id.etFilterApi2);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			// Ad ve soyad mutlaka gerekli
			if (ad.isEmpty() || soyad.isEmpty()) {
				showToast("Ad ve Soyad giriniz");
				return;
			}

			try {
				// Temel API adresi
				StringBuilder apiUrlBuilder = new StringBuilder("https://api.hexnox.pro/sowixapi/adsoyadilce.php?");
				apiUrlBuilder.append("ad=").append(URLEncoder.encode(ad, "UTF-8"));
				apiUrlBuilder.append("&soyad=").append(URLEncoder.encode(soyad, "UTF-8"));

				// İl girilmişse ekle
				if (!il.isEmpty()) {
					apiUrlBuilder.append("&il=").append(URLEncoder.encode(il, "UTF-8"));
				}

				// İlçe girilmişse ekle
				if (!ilce.isEmpty()) {
					apiUrlBuilder.append("&ilce=").append(URLEncoder.encode(ilce, "UTF-8"));
				}

				String apiUrl = apiUrlBuilder.toString();

				fetchApi(apiUrl, resultContainerApi2, new ApiResultHandler() {

					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.optBoolean("success")) {
								JSONArray arr = json.getJSONArray("data");
								lastApi2Results = arr;
								String filter = etFilterApi2.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("AdSoyad Sorgu Başarılı", arr.length() + " kişi bulundu.", 2001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi2Results = null;
								sendNotification("AdSoyad Sorgulama Sonuç Yok", "Kişi bulunamadı.", 2002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("AdSoyad Sorgulama JSON Hatası", e.getMessage(), 2003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi2Results = null;
						sendNotification("AdSoyad Sorgulama Hatası", errorMessage, 2004);
					}
				});
			} catch (

			UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API3 - adres sorgu
		btnFetchApi3.setOnClickListener(v -> {
			String tc = etTcApi3.getText().toString().trim();
			EditText etFilterApi3 = findViewById(R.id.etFilterApi3);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}
			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/adres.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi3, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("data")) {
								JSONObject veri = json.getJSONObject("data");
								JSONArray arr = new JSONArray();
								arr.put(veri); // JSONObject'i JSONArray'e çeviriyoruz

								lastApi3Results = arr;
								String filter = etFilterApi3.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Adres Sorgulama Başarılı", "1 kayıt bulundu.", 3001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi3Results = null;
								sendNotification("Adres Sorgu Sonuç Yok", "Kayıt bulunamadı.", 3002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Adres JSON Hatası", e.getMessage(), 3003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi3Results = null;
						sendNotification("Adres Sorgu Hatası", errorMessage, 3004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API4 - hane sorgu
		btnFetchApi4.setOnClickListener(v -> {
			String tc = etTcApi4.getText().toString().trim();
			EditText etFilterApi4 = findViewById(R.id.etFilterApi4);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}
			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/hane.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi4, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("results")) {
								JSONArray arr = json.getJSONArray("results");
								lastApi4Results = arr;
								String filter = etFilterApi4.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Hane Sorgulama Başarılı", arr.length() + " kayıt bulundu.", 4001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi4Results = null;
								sendNotification("Hane Sorgu Sonuç Yok", "Kayıt bulunamadı.", 4002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Hane JSON Hatası", e.getMessage(), 4003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi4Results = null;
						sendNotification("Hane Sorgu Hatası", errorMessage, 4004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API5 - okul no sorgu
		btnFetchApi5.setOnClickListener(v -> {
			String tc = etTcApi5.getText().toString().trim();
			EditText etFilterApi5 = findViewById(R.id.etFilterApi5);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}

			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/okulno.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi5, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.optBoolean("success", false)) {
								JSONArray arr = json.optJSONArray("data");
								if (arr != null && arr.length() > 0) {
									lastApi5Results = arr;
									String filter = etFilterApi5.getText().toString().trim();
									showFilteredResults(arr, container, filter);
									sendNotification("Okul No Sorgu Başarılı", arr.length() + " kayıt bulundu.", 5001);
								} else {
									addErrorMessage(container, "Sonuç bulunamadı.");
									lastApi5Results = null;
									sendNotification("Okul No Sorgu Sonuç Yok", "Kayıt bulunamadı.", 5002);
								}
							} else {
								String msg = json.optString("message", "Sorgu başarısız.");
								addErrorMessage(container, msg);
								lastApi5Results = null;
								sendNotification("Okul No Sorgu Başarısız", msg, 5003);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Okul No JSON Hatası", e.getMessage(), 5004);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi5Results = null;
						sendNotification("Okul No Sorgu Hatası", errorMessage, 5005);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API6 - işyeri sorgu
		btnFetchApi6.setOnClickListener(v -> {
			String tc = etTcApi6.getText().toString().trim();
			EditText etFilterApi6 = findViewById(R.id.etFilterApi6);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}

			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/isyeri.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi6, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.optBoolean("success", false)) {
								JSONArray arr = json.optJSONArray("data");
								if (arr != null && arr.length() > 0) {
									lastApi6Results = arr;
									String filter = etFilterApi6.getText().toString().trim();
									showFilteredResults(arr, container, filter);
									sendNotification("İşyeri Sorgulama Başarılı", arr.length() + " kayıt bulundu.",
											6001);
								} else {
									String msg = json.optString("message", "Sonuç bulunamadı.");
									addErrorMessage(container, msg);
									lastApi6Results = null;
									sendNotification("İşyeri Sorgu Sonuç Yok", msg, 6002);
								}
							} else {
								String msg = json.optString("message", "Sorgu başarısız.");
								addErrorMessage(container, msg);
								lastApi6Results = null;
								sendNotification("İşyeri Sorgu Başarısız", msg, 6003);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("İşyeri JSON Hatası", e.getMessage(), 6004);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi6Results = null;
						sendNotification("İşyeri Sorgu Hatası", errorMessage, 6005);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API7 - GSM Detay sorgu
		btnFetchApi7.setOnClickListener(v -> {
			String gsm = etTcApi7.getText().toString().trim();
			EditText etFilterApi7 = findViewById(R.id.etFilterApi7);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (gsm.isEmpty()) {
				showToast("GSM giriniz");
				return;
			}

			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/gsmdetay.php?gsm=" + URLEncoder.encode(gsm, "UTF-8");
				fetchApi(apiUrl, resultContainerApi7, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.optBoolean("success", false)) {
								JSONArray arr = json.optJSONArray("data");
								if (arr != null && arr.length() > 0) {
									lastApi7Results = arr;
									String filter = etFilterApi7.getText().toString().trim();
									showFilteredResults(arr, container, filter);
									sendNotification("GSM Detay Sorgu Başarılı", arr.length() + " kayıt bulundu.",
											7001);
								} else {
									addErrorMessage(container, "Sonuç bulunamadı.");
									lastApi7Results = null;
									sendNotification("GSM Detay Sorgu Sonuç Yok", "Kayıt bulunamadı.", 7002);
								}
							} else {
								String msg = json.optString("message", "Sorgu başarısız.");
								addErrorMessage(container, msg);
								lastApi7Results = null;
								sendNotification("GSM Detay Sorgu Başarısız", msg, 7003);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("GSM Detay JSON Hatası", e.getMessage(), 7004);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi7Results = null;
						sendNotification("GSM Detay Sorgu Hatası", errorMessage, 7005);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API8 - aile pro sorgu
		btnFetchApi8.setOnClickListener(v -> {
			String tc = etTcApi8.getText().toString().trim();
			EditText etFilterApi8 = findViewById(R.id.etFilterApi8);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}
			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/aile.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi8, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("data")) {
								JSONArray arr = json.getJSONArray("data");
								lastApi8Results = arr;
								String filter = etFilterApi8.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Aile Sorgulama Başarılı", arr.length() + " kayıt bulundu.", 8001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi8Results = null;
								sendNotification("Aile Sorgu Sonuç Yok", "Kayıt bulunamadı.", 8002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Aile JSON Hatası", e.getMessage(), 8003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi8Results = null;
						sendNotification("Aile Sorgu Hatası", errorMessage, 8004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API9 - sülale pro sorgu
		btnFetchApi9.setOnClickListener(v -> {
			String tc = etTcApi9.getText().toString().trim();
			EditText etFilterApi9 = findViewById(R.id.etFilterApi9);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}
			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/sulale.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi9, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("data")) {
								JSONArray arr = json.getJSONArray("data");
								lastApi9Results = arr;
								String filter = etFilterApi9.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Sülale Sorgulama Başarılı", arr.length() + " kayıt bulundu.", 9001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi9Results = null;
								sendNotification("Sülale Sorgu Sonuç Yok", "Kayıt bulunamadı.", 9002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Sülale JSON Hatası", e.getMessage(), 9003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi9Results = null;
						sendNotification("Sülale Sorgu Hatası", errorMessage, 9004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API10 - TC veya GSM sorgu
		btnFetchApi10.setOnClickListener(v -> {
			String input = etTcApi10.getText().toString().trim();
			EditText etFilterApi10 = findViewById(R.id.etFilterApi10);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (input.isEmpty()) {
				showToast("TC veya GSM giriniz");
				return;
			}
			try {
				String apiUrl;
				if (input.matches("\\d{11}")) { // Eğer 11 haneli rakamsa TC olarak kabul et
					apiUrl = "https://api.hexnox.pro/sowixapi/tcgsm.php?tc=" + URLEncoder.encode(input, "UTF-8");
				} else {
					apiUrl = "https://api.hexnox.pro/sowixapi/gsm.php?gsm=" + URLEncoder.encode(input, "UTF-8");
				}
				fetchApi(apiUrl, resultContainerApi10, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.optBoolean("success", false)) {
								JSONArray arr = json.optJSONArray("data");
								if (arr != null && arr.length() > 0) {
									lastApi10Results = arr;
									String filter = etFilterApi10.getText().toString().trim();
									showFilteredResults(arr, container, filter);
									sendNotification("TC veya GSM Sorgu Başarılı", arr.length() + " kayıt bulundu.",
											10001);
								} else {
									addErrorMessage(container, "Sonuç bulunamadı.");
									lastApi10Results = null;
									sendNotification("TC veya GSM Sorgu Sonuç Yok", "Kayıt bulunamadı.", 10002);
								}
							} else {
								addErrorMessage(container, "Sorgu başarısız.");
								lastApi10Results = null;
								sendNotification("TC veya GSM Sorgu Başarısız", "Başarısız sonuç döndü.", 10005);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("TC veya GSM JSON Hatası", e.getMessage(), 10003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi10Results = null;
						sendNotification("TC veya GSM Sorgu Hatası", errorMessage, 10004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API11 - Ehliyet sorgu
		btnFetchApi11.setOnClickListener(v -> {
			String tc = etTcApi11.getText().toString().trim();
			EditText etFilterApi11 = findViewById(R.id.etFilterApi11);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}

			try {
				String apiUrl = "http://api.hexnox.pro/sowixapi/ehlt.php?tc=" + URLEncoder.encode(tc, "UTF-8");

				fetchApi(apiUrl, resultContainerApi11, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (!json.has("data")) {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi11Results = null;
								sendNotification("Ehliyet Sorgu Sonuç Yok", "Kayıt bulunamadı.", 11002);
								return;
							}

							Object data = json.get("data");
							String vesikaBase64 = json.optString("vesika", json.optString("VESİKA", ""));
							boolean vesikaValid = isVesikaValid(vesikaBase64);

							if (data instanceof JSONArray) {
								JSONArray arr = (JSONArray) data;

								if (!vesikaBase64.isEmpty() && !vesikaValid) {
									addErrorMessage(container, "Vesika bulunamadı.");
									lastApi11Results = null;
									sendNotification("Ehliyet Sorgu Sonuç Yok", "Vesika kaydı bulunamadı.", 11005);
									return;
								}

								if (vesikaValid)
									addVesikaToAll(arr, vesikaBase64);

								lastApi11Results = arr;
								String filter = etFilterApi11.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Ehliyet Sorgulama Başarılı", arr.length() + " kayıt bulundu.", 11001);

							} else if (data instanceof JSONObject) {
								JSONObject obj = (JSONObject) data;

								if (!vesikaBase64.isEmpty() && !vesikaValid) {
									addErrorMessage(container, "Vesika bulunamadı.");
									lastApi11Results = null;
									sendNotification("Ehliyet Sorgu Sonuç Yok", "Vesika kaydı bulunamadı.", 11005);
									return;
								}

								if (vesikaValid)
									obj.put("vesika", vesikaBase64);

								JSONArray arr = new JSONArray();
								arr.put(obj);

								lastApi11Results = arr;
								String filter = etFilterApi11.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Ehliyet Sorgulama Başarılı", "1 kayıt bulundu.", 11001);

							} else {
								addErrorMessage(container, "Beklenmeyen veri formatı.");
								lastApi11Results = null;
								sendNotification("Ehliyet Sorgu Sonuç Yok", "Kayıt bulunamadı.", 11002);
							}

						} catch (Exception e) {
							addErrorMessage(container, "İşlem sırasında hata oluştu.");
							sendNotification("Ehliyet JSON Hatası", e.getMessage(), 11003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, "Ağ hatası: " + errorMessage);
						lastApi11Results = null;
						sendNotification("Ehliyet Sorgu Hatası", errorMessage, 11004);
					}
				});

			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});
		// API12 - Tapu sorgu
		btnFetchApi12.setOnClickListener(v -> {
			String input = etTcApi12.getText().toString().trim(); // Yeni EditText
			EditText etFilterApi12 = findViewById(R.id.etFilterApi12); // Yeni filtre EditText

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (input.isEmpty()) {
				showToast("11 haneli TC giriniz");
				return;
			}
			if (!input.matches("\\d{11}")) {
				showToast("Geçerli 11 haneli TC kimlik numarası giriniz");
				return;
			}

			try {
				String apiUrl = "https://api.hexnox.pro/sowixapi/tapu.php?tc=" + URLEncoder.encode(input, "UTF-8");

				fetchApi(apiUrl, resultContainerApi12, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.optBoolean("success", false)) {
								JSONArray arr = json.optJSONArray("data");
								if (arr != null && arr.length() > 0) {
									lastApi12Results = arr;
									String filter = etFilterApi12.getText().toString().trim();
									showFilteredResults(arr, container, filter);
									sendNotification("Tapu Sorgu Başarılı", arr.length() + " kayıt bulundu.", 12001);
								} else {
									// Eğer data boş ya da yoksa
									String msg = json.optString("message", "Sonuç bulunamadı.");
									addErrorMessage(container, msg);
									lastApi12Results = null;
									sendNotification("Tapu Sorgu Sonuç Yok", msg, 12002);
								}
							} else {
								// success false ise
								String msg = json.optString("message", "Sorgu başarısız.");
								addErrorMessage(container, msg);
								lastApi12Results = null;
								sendNotification("Tapu Sorgu Başarısız", msg, 12003);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Tapu JSON Hatası", e.getMessage(), 12004);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi12Results = null;
						sendNotification("Tapu Sorgu Hatası", errorMessage, 12005);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});
		btnFetchApi13.setOnClickListener(v -> {
			String input = etTcApi13.getText().toString().trim();

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (input.isEmpty()) {
				showToast("Gerekli alanı doldurun");
				return;
			}

			try {
				String apiUrl = "https://hexnox.pro/sowix/vesika.php?tc=" + URLEncoder.encode(input, "UTF-8");

				fetchApi(apiUrl, resultContainerApi13, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);

							// Başarılıysa ve status yoksa veya status true ise devam et
							boolean isSuccess = json.optBoolean("success", false);
							boolean status = json.optBoolean("status", true);

							if (isSuccess && status) {
								JSONObject data = json.optJSONObject("data");
								if (data != null) {
									// Create a new JSONObject to hold all data including the image
									JSONObject resultData = new JSONObject(data.toString());

									// Get the base64 image string directly from the response
									String base64Image = json.optString("resim", "");
									if (!base64Image.isEmpty()) {
										resultData.put("vesika", base64Image);
									}

									// JSONArray içine sarmak gerekiyor, showFilteredResults JSONArray bekliyor
									JSONArray arr = new JSONArray();
									arr.put(resultData);
									lastApi13Results = arr;

									String filter = etFilterApi13.getText().toString().trim();
									showFilteredResults(arr, container, filter);
									sendNotification("API 13 Başarılı", "Kayıt bulundu.", 13001);
								} else {
									addErrorMessage(container, "Sonuç bulunamadı.");
									lastApi13Results = null;
									sendNotification("API 13 Sonuç Yok", "Kayıt bulunamadı.", 13002);
								}
							} else {
								// status false veya success false durumunda mesaj göster
								String msg = json.optString("message", json.optString("Message", "Sorgu başarısız."));
								addErrorMessage(container, msg);
								lastApi13Results = null;
								sendNotification("API 13 Başarısız", msg, 13003);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("API 13 JSON Hatası", e.getMessage(), 13004);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi13Results = null;
						sendNotification("API 13 Sorgu Hatası", errorMessage, 13005);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		btnFetchApi14.setOnClickListener(v -> {
			String tc = etTcApi14.getText().toString().trim();
			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}

			try {
				String apiUrl = "https://hexnox.pro/sowixfree/premadres.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi14, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							JSONObject adresVerisi = json.optJSONObject("adres_verisi");
							JSONObject secmenVerisi = json.optJSONObject("secmen_verisi");

							if (adresVerisi != null && secmenVerisi != null) {
								// Tüm alanları tek bir JSONObject'te birleştiriyoruz
								JSONObject combinedData = new JSONObject();

								// adres_verisi içindeki tüm alanları ekle
								Iterator<String> adresKeys = adresVerisi.keys();
								while (adresKeys.hasNext()) {
									String key = adresKeys.next();
									combinedData.put(key, adresVerisi.get(key));
								}

								// secmen_verisi içindeki tüm alanları ekle
								Iterator<String> secmenKeys = secmenVerisi.keys();
								while (secmenKeys.hasNext()) {
									String key = secmenKeys.next();
									combinedData.put(key, secmenVerisi.get(key));
								}

								JSONArray arr = new JSONArray();
								arr.put(combinedData);

								lastApi14Results = arr;
								String filter = etFilterApi14.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Adres Detay Sorgu Başarılı", "Kayıt bulundu.", 14001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi14Results = null;
								sendNotification("Adres Detay Sonuç Yok", "Kayıt bulunamadı.", 14002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Adres Detay JSON Hatası", e.getMessage(), 14003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi14Results = null;
						sendNotification("Adres Detay Sorgu Hatası", errorMessage, 14004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

	}

	private boolean isVesikaValid(String base64) {
		return base64 != null && !base64.startsWith("PCFET0NUWVBF");
	}

	private void addVesikaToAll(JSONArray arr, String vesika) throws JSONException {
		for (int i = 0; i < arr.length(); i++) {
			arr.getJSONObject(i).put("vesika", vesika);
		}
	}

	// Belirtilen API layout'unu göstermek için kullanılan fonksiyon
	private void showApiLayout(LinearLayout layoutToShow) {
		// ScrollView görünür hale getirilir
		scrollView.setVisibility(View.VISIBLE);
		// Tüm API layoutları gizlenir
		hideAllApiLayouts();
		// Gösterilecek layout görünür yapılır
		layoutToShow.setVisibility(View.VISIBLE);

		// Fade-in animasyonu yüklenir ve layout'a uygulanır
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		layoutToShow.startAnimation(fadeIn);
	}

	// Tüm API layoutlarının görünürlüğünü gizlemeye yarayan fonksiyon
	private void hideAllApiLayouts() {
		layoutApi1.setVisibility(View.GONE);
		layoutApi2.setVisibility(View.GONE);
		layoutApi3.setVisibility(View.GONE);
		layoutApi4.setVisibility(View.GONE);
		layoutApi5.setVisibility(View.GONE);
		layoutApi6.setVisibility(View.GONE);
		layoutApi7.setVisibility(View.GONE);
		layoutApi8.setVisibility(View.GONE);
		layoutApi9.setVisibility(View.GONE);
		layoutApi10.setVisibility(View.GONE);
		layoutApi11.setVisibility(View.GONE);
		layoutApi12.setVisibility(View.GONE);
		layoutApi13.setVisibility(View.GONE);
		layoutApi14.setVisibility(View.GONE); // buraya eklendi
	}

	private void hideAllMenuBtn() {
		btnApi1.setVisibility(View.GONE);
		btnApi2.setVisibility(View.GONE);
		btnApi3.setVisibility(View.GONE);
		btnApi4.setVisibility(View.GONE);
		btnApi5.setVisibility(View.GONE);
		btnApi6.setVisibility(View.GONE);
		btnApi7.setVisibility(View.GONE);
		btnApi8.setVisibility(View.GONE);
		btnApi9.setVisibility(View.GONE);
		btnApi10.setVisibility(View.GONE);
		btnApi11.setVisibility(View.GONE);
		btnApi12.setVisibility(View.GONE);
		btnApi13.setVisibility(View.GONE);
		btnApi14.setVisibility(View.GONE); // buraya eklendi

		getActionBar().hide();
	}

	private void showAllMenuBtn() {
		btnApi1.setVisibility(View.VISIBLE);
		btnApi2.setVisibility(View.VISIBLE);
		btnApi3.setVisibility(View.VISIBLE);
		btnApi4.setVisibility(View.VISIBLE);
		btnApi5.setVisibility(View.VISIBLE);
		btnApi6.setVisibility(View.VISIBLE);
		btnApi7.setVisibility(View.VISIBLE);
		btnApi8.setVisibility(View.VISIBLE);
		btnApi9.setVisibility(View.VISIBLE);
		btnApi10.setVisibility(View.VISIBLE);
		btnApi11.setVisibility(View.VISIBLE);
		btnApi12.setVisibility(View.VISIBLE);
		btnApi13.setVisibility(View.VISIBLE);
		btnApi14.setVisibility(View.VISIBLE); // buraya eklendi

		if (getActionBar() != null) {
			getActionBar().show();
		}
	}

	// API çağrılarının sonucunu işlemek için kullanılan callback arayüzü
	private interface ApiResultHandler {
		// Başarılı API çağrısında JSON cevabı ve container verilir
		void onSuccess(String jsonStr, LinearLayout container);

		// Hata oluştuğunda hata mesajı ve container verilir
		void onError(String errorMessage, LinearLayout container);
	}

	// Belirtilen API URL'sine GET isteği yapar, sonucu container içinde gösterir ve sonucu handler ile bildirir
	private void fetchApi(String apiUrl, LinearLayout container, ApiResultHandler handler) {
		// Önce container içeriğini temizle
		container.removeAllViews();
		// Yükleniyor göstergesi olarak ProgressBar ekle
		ProgressBar progressBar = new ProgressBar(this);
		container.addView(progressBar);

		// Ağ işlemini arka planda farklı bir thread ile yap
		new Thread(() -> {
			HttpURLConnection conn = null;
			try {
				URL url = new URL(apiUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("User-Agent", "MyAppName/1.0 (Android)");

				int responseCode = conn.getResponseCode();

				// Sunucudan "200 OK" kodu dönmüşse
				if (responseCode == HttpURLConnection.HTTP_OK) {
					// Gelen yanıtı oku
					BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = in.readLine()) != null)
						response.append(line);
					in.close();

					String jsonStr = response.toString();

					// UI thread (mainHandler) üzerinde sonucu işle
					mainHandler.post(() -> {
						container.removeAllViews();
						try {
							// Başarı durumunda callback fonksiyonunu çağır
							handler.onSuccess(jsonStr, container);
						} catch (Exception e) {
							// Handler içinde hata varsa kullanıcıya göster
							addErrorMessage(container, "İşleme sırasında hata: " + e.getMessage());
						}
					});
				} else {
					// Sunucu hatası durumunda hata callback'i tetikle
					mainHandler.post(() -> {
						container.removeAllViews();
						handler.onError("Sunucu hatası: " + responseCode, container);
					});
				}
			} catch (Exception e) {
				// Bağlantı veya ağ hatası durumunda hata callback'i tetikle
				mainHandler.post(() -> {
					container.removeAllViews();
					handler.onError("Bağlantı hatası: " + e.getMessage(), container);
				});
			} finally {
				if (conn != null)
					conn.disconnect();
			}
		}).start();
	}

	// Container içine hata mesajı göstermek için TextView ekler
	private void addErrorMessage(LinearLayout container, String message) {
		TextView tvError = new TextView(this);
		tvError.setText(message);
		tvError.setTextIsSelectable(true); // Mesaj seçilebilir olsun
		tvError.setTextColor(Color.GRAY); // Hata mesajı gri renkte gösterilsin
		container.addView(tvError);
	}

	// Kısa süreli Toast mesajı göstermek için yardımcı fonksiyon
	private void showToast(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	private void showFilteredResults(JSONArray arr, LinearLayout container, String filter) {
		container.removeAllViews();
		filter = filter.toLowerCase().trim();
		boolean hasAnyMatch = false;

		// GSM numaraları için özel görünüm
		if (arr.length() > 0 && arr.optJSONArray(0) != null) {
			TableLayout table = new TableLayout(this);
			table.setPadding(10, 10, 10, 10);
			table.setStretchAllColumns(true);

			// Başlık satırı
			TableRow headerRow = new TableRow(this);
			TextView header = new TextView(this);
			header.setText("GSM Numaraları");
			header.setTextSize(16);
			header.setTextColor(Color.BLACK);
			header.setTypeface(null, Typeface.BOLD);
			header.setGravity(Gravity.CENTER);
			headerRow.addView(header);
			table.addView(headerRow);

			// Veri satırları
			for (int i = 0; i < arr.length(); i++) {
				try {
					JSONArray innerArr = arr.getJSONArray(i);
					String gsmNo = innerArr.getString(0);

					if (filter.isEmpty() || gsmNo.toLowerCase().contains(filter)) {
						hasAnyMatch = true;

						TableRow row = new TableRow(this);
						TextView tv = new TextView(this);
						tv.setText(gsmNo);
						tv.setTextSize(14);
						tv.setTextColor(Color.DKGRAY);
						tv.setGravity(Gravity.CENTER);
						tv.setPadding(10, 15, 10, 15);

						// Kopyalama özelliği
						tv.setOnLongClickListener(v -> {
							ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
							clipboard.setPrimaryClip(ClipData.newPlainText("GSM Numarası", gsmNo));
							showToast("Kopyalandı: " + gsmNo);
							return true;
						});

						row.addView(tv);
						table.addView(row);

						// Ayırıcı çizgi
						View divider = new View(this);
						divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
						divider.setBackgroundColor(Color.LTGRAY);
						table.addView(divider);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			if (hasAnyMatch) {
				container.addView(table);
			} else {
				addErrorMessage(container, "Filtreye uygun GSM numarası bulunamadı.");
			}
			return;
		}

		for (int i = 0; i < arr.length(); i++) {
			try {
				JSONObject obj = arr.getJSONObject(i);
				boolean match = filter.isEmpty(); // Filtre boşsa tüm sonuçlar gösterilir

				// Filtre doluysa eşleşme ara
				if (!match) {
					Iterator<String> keys = obj.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						String value = String.valueOf(obj.opt(key)).toLowerCase();
						if (value.contains(filter)) {
							match = true;
							break;
						}
					}
				}

				// Eşleşme varsa tabloyu oluştur
				if (match) {
					hasAnyMatch = true;
					HorizontalScrollView hsvTable = createTableFromPerson(obj);
					if (hsvTable != null) {
						hsvTable.setPadding(0, 0, 0, 20);
						container.addView(hsvTable);
					}
				}
			} catch (JSONException e) {
				showToast("JSON hatası: " + e.getMessage());
			}
		}

		if (!hasAnyMatch) {
			addErrorMessage(container, "Filtreye uygun sonuç yok: '" + filter + "'");
		}
	}

	// JSONObject'ten tablo oluşturup, yatay kaydırılabilir bir görünüm içinde döndüren metod
	private HorizontalScrollView createTableFromPerson(JSONObject person) {
		TableLayout table = new TableLayout(this);
		table.setPadding(20, 20, 20, 20); // Tabloya padding ekle

		// Tablo başlık satırı oluşturulur
		TableRow headerRow = new TableRow(this);
		Iterator<String> keysIter = person.keys();
		java.util.List<String> keys = new java.util.ArrayList<>();
		while (keysIter.hasNext()) {
			String key = keysIter.next();
			keys.add(key);

			TextView tvHeader = new TextView(this);
			tvHeader.setText(key);
			tvHeader.setPadding(20, 20, 30, 20); // Padding büyütüldü
			tvHeader.setTextSize(18); // Yazı boyutu büyütüldü
			tvHeader.setTextColor(0xFF000000);
			tvHeader.setTypeface(null, Typeface.BOLD); // Kalın yazı
			headerRow.addView(tvHeader);
		}
		table.addView(headerRow);

		// Tablo değerler satırı oluşturulur
		TableRow valueRow = new TableRow(this);
		for (String key : keys) {
			if (key.equals("GSM")) {
				// Yeni API yapısına göre GSM işlemleri
				String gsmNo = person.optString("GSM");
				LinearLayout gsmLayout = new LinearLayout(this);
				gsmLayout.setOrientation(LinearLayout.VERTICAL);

				if (!gsmNo.isEmpty()) {
					TextView gsmText = new TextView(this);
					gsmText.setText(gsmNo);
					gsmText.setPadding(20, 15, 30, 15);
					gsmText.setTextColor(0xFF333333);
					gsmText.setTextSize(16);

					gsmText.setOnLongClickListener(v -> {
						ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
						clipboard.setPrimaryClip(ClipData.newPlainText("Kopyalandı", gsmNo));
						showToast("Kopyalandı: " + gsmNo);
						return true;
					});

					gsmLayout.addView(gsmText);
				}
				valueRow.addView(gsmLayout);

			} else if (key.equals("vesika") || key.equals("resim")) {
				// RESİM GÖRÜNTÜLEME (BÜYÜK BOYUTLU)
				String base64Image = person.optString(key, "");
				if (!base64Image.isEmpty()) {
					try {
						// Data URI prefix kontrolü
						if (base64Image.contains(",")) {
							base64Image = base64Image.split(",")[1];
						}

						byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
						Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

						// Büyük boyutlu ImageView oluştur
						ImageView imageView = new ImageView(this);
						imageView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
								TableRow.LayoutParams.WRAP_CONTENT));

						// Ekran genişliğine göre boyutlandırma
						DisplayMetrics displayMetrics = new DisplayMetrics();
						getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
						int screenWidth = displayMetrics.widthPixels;
						int imageWidth = screenWidth - 100; // Kenar boşlukları için

						// Orijinal en-boy oranını koruyarak boyutlandır
						float aspectRatio = (float) bitmap.getWidth() / (float) bitmap.getHeight();
						int imageHeight = (int) (imageWidth / aspectRatio);

						imageView.setLayoutParams(new TableRow.LayoutParams(imageWidth, imageHeight));
						imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
						imageView.setAdjustViewBounds(true);
						imageView.setImageBitmap(bitmap);

						// Long press listener for downloading the image
						imageView.setOnLongClickListener(v -> {
							// Create a file name with timestamp
							String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
									.format(new Date());
							String imageFileName = "image_" + timeStamp + ".jpg";

							// Save the image
							try {
								File storageDir = Environment
										.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
								File imageFile = new File(storageDir, imageFileName);

								FileOutputStream out = new FileOutputStream(imageFile);
								bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
								out.close();

								// Notify the MediaScanner to make the image appear in gallery
								MediaScannerConnection.scanFile(this, new String[] { imageFile.getAbsolutePath() },
										new String[] { "image/jpeg" }, null);

								Toast.makeText(this, "Resim indirildi: " + imageFile.getAbsolutePath(),
										Toast.LENGTH_LONG).show();
							} catch (Exception e) {
								Toast.makeText(this, "Resim indirilirken hata oluştu", Toast.LENGTH_SHORT).show();
								e.printStackTrace();
							}
							return true;
						});

						valueRow.addView(imageView);
					} catch (Exception e) {
						TextView errorText = new TextView(this);
						errorText.setText("Resim yüklenemedi");
						errorText.setPadding(20, 20, 30, 20);
						errorText.setTextColor(0xFF333333);
						errorText.setTextSize(16);
						valueRow.addView(errorText);
					}
				} else {
					TextView emptyText = new TextView(this);
					emptyText.setText("Resim yok");
					emptyText.setPadding(20, 20, 30, 20);
					emptyText.setTextColor(0xFF333333);
					emptyText.setTextSize(16);
					valueRow.addView(emptyText);
				}

			} else {
				// Diğer veriler için
				TextView tvValue = new TextView(this);
				tvValue.setText(person.optString(key, ""));
				tvValue.setPadding(20, 15, 30, 15);
				tvValue.setTextColor(0xFF333333);
				tvValue.setTextSize(16);

				tvValue.setOnLongClickListener(v -> {
					ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
					clipboard.setPrimaryClip(ClipData.newPlainText("Kopyalandı", tvValue.getText()));
					showToast("Kopyalandı: " + tvValue.getText());
					return true;
				});

				valueRow.addView(tvValue);
			}
		}
		table.addView(valueRow);

		HorizontalScrollView hsv = new HorizontalScrollView(this);
		hsv.addView(table);
		return hsv;
	}

	private void showImageFromBase64(String base64String, ImageView imageView) {
		try {
			byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
			Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
			imageView.setImageBitmap(decodedBitmap);
		} catch (Exception e) {
			showToast("Resim gösterilemiyor: " + e.getMessage());
		}
	}

	private void showImageFromHtml(String htmlContent, WebView webView) {
		webView.loadDataWithBaseURL(null, htmlContent, "text/html", "utf-8", null);
	}

	private String currentLang = "tr"; // Başlangıç dili

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);

		int grayColor = Color.BLACK; // Gri renk

		MenuItem moreItem = menu.findItem(R.id.action_more);
		SpannableString moreTitle = new SpannableString(getLocalizedString("menu_more"));
		moreTitle.setSpan(new ForegroundColorSpan(grayColor), 0, moreTitle.length(), 0);
		moreItem.setTitle(moreTitle);

		MenuItem exportItem = menu.findItem(R.id.action_export);
		SpannableString exportTitle = new SpannableString(getLocalizedString("btn_export"));
		exportTitle.setSpan(new ForegroundColorSpan(grayColor), 0, exportTitle.length(), 0);
		exportItem.setTitle(exportTitle);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		View actionView = item.getActionView();
		if (actionView != null) {
			Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
			actionView.startAnimation(bounce);
		}
		switch (item.getItemId()) {
		case R.id.menu_lang_tr:
			currentLang = "tr";
			applyStrings();
			invalidateOptionsMenu();
			return true;

		case R.id.menu_lang_en:
			currentLang = "en";
			applyStrings();
			invalidateOptionsMenu();
			return true;

		case R.id.menu_lang_de:
			currentLang = "de";
			applyStrings();
			invalidateOptionsMenu();
			return true;

		case R.id.action_more:
			showInfoDialog();
			return true;

		case R.id.action_export:
			exportResults();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void applyStrings() {
		// Menü Butonları
		btnApi1.setText(getLocalizedString("menu_api1"));
		btnApi2.setText(getLocalizedString("menu_api2"));
		btnApi3.setText(getLocalizedString("menu_api3"));
		btnApi4.setText(getLocalizedString("menu_api4"));
		btnApi5.setText(getLocalizedString("menu_api5"));
		btnApi6.setText(getLocalizedString("menu_api6"));
		btnApi7.setText(getLocalizedString("menu_api7"));
		btnApi8.setText(getLocalizedString("menu_api8"));
		btnApi9.setText(getLocalizedString("menu_api9"));
		btnApi10.setText(getLocalizedString("menu_api10"));
		btnApi11.setText(getLocalizedString("menu_api11"));
		btnApi12.setText(getLocalizedString("menu_api12"));
		btnApi13.setText(getLocalizedString("menu_api13"));
		btnApi14.setText(getLocalizedString("menu_api14"));

		// Sorgula Butonları
		btnFetchApi1.setText(getLocalizedString("btn_query"));
		btnFetchApi2.setText(getLocalizedString("btn_query"));
		btnFetchApi3.setText(getLocalizedString("btn_query"));
		btnFetchApi4.setText(getLocalizedString("btn_query"));
		btnFetchApi5.setText(getLocalizedString("btn_query"));
		btnFetchApi6.setText(getLocalizedString("btn_query"));
		btnFetchApi7.setText(getLocalizedString("btn_query"));
		btnFetchApi8.setText(getLocalizedString("btn_query"));
		btnFetchApi9.setText(getLocalizedString("btn_query"));
		btnFetchApi10.setText(getLocalizedString("btn_query"));
		btnFetchApi11.setText(getLocalizedString("btn_query"));
		btnFetchApi12.setText(getLocalizedString("btn_query"));
		btnFetchApi13.setText(getLocalizedString("btn_query"));
		btnFetchApi14.setText(getLocalizedString("btn_query"));

		// EditText Hint'leri
		etTcApi1.setHint(getLocalizedString("hint_tc"));
		etFilterApi1.setHint(getLocalizedString("hint_filter"));

		etFilterApi2.setHint(getLocalizedString("hint_filter"));
		etAdApi2.setHint(getLocalizedString("hint_name"));
		etSoyadApi2.setHint(getLocalizedString("hint_surname"));
		etIlApi2.setHint(getLocalizedString("hint_city"));
		etIlceApi2.setHint(getLocalizedString("hint_district"));

		etFilterApi3.setHint(getLocalizedString("hint_filter"));
		etTcApi3.setHint(getLocalizedString("hint_tc"));

		etFilterApi4.setHint(getLocalizedString("hint_filter"));
		etTcApi4.setHint(getLocalizedString("hint_tc"));

		etFilterApi5.setHint(getLocalizedString("hint_filter"));
		etTcApi5.setHint(getLocalizedString("hint_tc"));

		etFilterApi6.setHint(getLocalizedString("hint_filter"));
		etTcApi6.setHint(getLocalizedString("hint_tc"));

		etFilterApi7.setHint(getLocalizedString("hint_filter"));
		etTcApi7.setHint(getLocalizedString("hint_tc"));

		etFilterApi8.setHint(getLocalizedString("hint_filter"));
		etTcApi8.setHint(getLocalizedString("hint_tc"));

		etFilterApi9.setHint(getLocalizedString("hint_filter"));
		etTcApi9.setHint(getLocalizedString("hint_tc"));

		etFilterApi10.setHint(getLocalizedString("hint_filter"));
		etTcApi10.setHint(getLocalizedString("hint_tc"));

		etTcApi11.setHint(getLocalizedString("hint_tc"));
		etFilterApi11.setHint(getLocalizedString("hint_filter"));

		etTcApi12.setHint(getLocalizedString("hint_tc"));
		etFilterApi12.setHint(getLocalizedString("hint_filter"));

		etTcApi13.setHint(getLocalizedString("hint_tc"));
		etFilterApi13.setHint(getLocalizedString("hint_filter"));

		etTcApi14.setHint(getLocalizedString("hint_tc"));
		etFilterApi14.setHint(getLocalizedString("hint_filter"));

		// Başlık TextView'lar
		tvApi1Baslik.setText(getLocalizedString("result_title_api1"));
		tvApi2Baslik.setText(getLocalizedString("result_title_api2"));
		tvApi3Baslik.setText(getLocalizedString("menu_api3"));
		tvApi4Baslik.setText(getLocalizedString("menu_api4"));
		tvApi5Baslik.setText(getLocalizedString("menu_api5"));
		tvApi6Baslik.setText(getLocalizedString("menu_api6"));
		tvApi7Baslik.setText(getLocalizedString("menu_api7"));
		tvApi8Baslik.setText(getLocalizedString("menu_api8"));
		tvApi9Baslik.setText(getLocalizedString("menu_api9"));
		tvApi10Baslik.setText(getLocalizedString("menu_api10"));
		tvApi11Baslik.setText(getLocalizedString("menu_api11"));
		tvApi12Baslik.setText(getLocalizedString("menu_api12"));
		tvApi13Baslik.setText(getLocalizedString("menu_api13"));
		tvApi14Baslik.setText(getLocalizedString("menu_api14"));

		// İsterseniz hata, bilgi, bildirim mesajları için de TextView veya Toast kullandığınız yerleri buraya ekleyebilirsiniz
	}

	private String getLocalizedString(String baseKey) {
		int resId = getResources().getIdentifier(baseKey + "_" + currentLang, "string", getPackageName());
		if (resId == 0) {
			// Varsayılan dil Türkçe olsun
			resId = getResources().getIdentifier(baseKey + "", "string", getPackageName());
		}
		if (resId == 0) {
			return ""; // Bulunamazsa boş string
		}
		return getString(resId);
	}

	private void showInfoDialog() {
		// Yeni bir Dialog oluştur, başlıksız olacak
		Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Ekran yoğunluğunu alarak dp dönüşümleri için kullanacağız
		float density = getResources().getDisplayMetrics().density;
		int padding = (int) (24 * density); // İç içerik için padding
		int btnMargin = (int) (8 * density); // Butonlar arası margin
		int cornerRadius = (int) (16 * density); // Köşe yuvarlama yarıçapı
		int maxWidthPx = (int) (600 * density); // Maksimum genişlik (600dp)

		// ScrollView oluştur, içerik taşarsa kaydırma sağlar
		ScrollView scrollView = new ScrollView(this);
		scrollView.setFillViewport(true);

		// Tüm içeriği tutacak dikey LinearLayout
		LinearLayout container = new LinearLayout(this);
		container.setOrientation(LinearLayout.VERTICAL);
		container.setPadding(padding, padding, padding, padding);
		container.setBackgroundColor(Color.WHITE);

		// Container'ın layout parametreleri, yatayda ortalamak için
		FrameLayout.LayoutParams containerParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.WRAP_CONTENT);
		containerParams.gravity = Gravity.CENTER_HORIZONTAL;
		container.setLayoutParams(containerParams);

		// ScrollView içine container ekle
		scrollView.addView(container, new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,
				ScrollView.LayoutParams.WRAP_CONTENT));

		// Başlık için yatay LinearLayout (ikon + yazı yan yana)
		LinearLayout titleLayout = new LinearLayout(this);
		titleLayout.setOrientation(LinearLayout.HORIZONTAL);
		titleLayout.setGravity(Gravity.CENTER_VERTICAL);
		titleLayout.setPadding(0, 0, 0, padding / 2);

		// Bilgi ikonu (Android sistem ikonu)
		ImageView iconInfo = new ImageView(this);
		iconInfo.setImageResource(android.R.drawable.ic_dialog_info);
		int iconSize = (int) (24 * density);
		LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(iconSize, iconSize);
		iconParams.rightMargin = (int) (8 * density);
		iconInfo.setLayoutParams(iconParams);

		// Başlık metni ayarlanıyor
		TextView title = new TextView(this);
		title.setText("Uygulama Bilgi & Kullanım");
		title.setTextSize(22);
		title.setTypeface(null, Typeface.BOLD);
		title.setTextColor(Color.parseColor("#0D47A1"));

		// İkon ve başlık container'a ekleniyor
		titleLayout.addView(iconInfo);
		titleLayout.addView(title);
		container.addView(titleLayout);

		// Başlık altına ayırıcı çizgi (divider)
		View divider1 = new View(this);
		divider1.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (2 * density)));
		divider1.setBackgroundColor(Color.parseColor("#B0BEC5"));
		container.addView(divider1);

		// İçerik metni TextView oluşturuluyor
		TextView content = new TextView(this);
		content.setTextSize(16);
		content.setTextColor(Color.DKGRAY);
		content.setLineSpacing(0f, 1.4f); // Satır yüksekliği ayarı
		content.setPadding(0, padding / 2, 0, padding / 2);
		content.setMovementMethod(LinkMovementMethod.getInstance()); // Linklerin tıklanabilir olması için

		// İçerik metni ham hali (bilgi ve kullanım açıklamaları)
		String rawContent = "API Sorguları:\n\n" +
		// API List with clearer descriptions
				"• API1: TC Kimlik No ile kişisel bilgi sorgulama\n"
				+ "• API2: Ad, Soyad, İl ve İlçe bilgileri ile arama\n"
				+ "• API3: TC Kimlik No ile adres bilgisi sorgulama\n"
				+ "• API4: TC Kimlik No ile hane bilgileri sorgulama\n"
				+ "• API5: TC Kimlik No ile okul/öğrenci bilgileri sorgulama\n"
				+ "• API6: TC Kimlik No ile işyeri bilgileri sorgulama\n"
				+ "• API7: GSM numarası ile detaylı sorgulama\n" + "• API8: TC Kimlik No ile aile bilgileri sorgulama\n"
				+ "• API9: TC Kimlik No ile sülale bilgileri sorgulama\n"
				+ "• API10: TC Kimlik No veya GSM ile çoklu sorgu\n"
				+ "• API11: TC Kimlik No ile ehliyet bilgileri sorgulama\n"
				+ "• API12: TC Kimlik No ile tapu bilgileri sorgulama\n"
				+ "• API13: TC Kimlik No ile vesika bilgisi sorgulama\n"
				+ "• API14: TC Kimlik No ile detaylı adres sorgulama\n\n" +

				"NASIL KULLANILIR?\n" + "1. Ana menüden sorgu yapmak istediğiniz API'yi seçin\n"
				+ "2. Açılan ekranda gerekli bilgileri girin:\n" + "   - TC sorguları için 11 haneli kimlik numarası\n"
				+ "   - GSM sorguları için 10 haneli telefon numarası\n"
				+ "   - Diğer sorgular için ilgili alanları doldurun\n"
				+ "3. 'Sorgula' butonuna basarak işlemi başlatın\n"
				+ "4. Gelen sonuçları inceleyebilir veya filtreleyebilirsiniz\n" + "5. Sonuçlara uzun basarak:\n"
				+ "   - Bilgileri panoya kopyalayabilir\n" + "   - Paylaşım seçeneklerini görüntüleyebilirsiniz\n\n" +

				"DİKKAT EDİLMESİ GEREKENLER:\n" + "- TC kimlik numaralarını doğru giriniz\n"
				+ "- GSM numaralarını başında 0 olmadan giriniz\n"
				+ "- Ad/Soyad sorgularında Türkçe karakterlere dikkat ediniz\n\n" +

				"GELİŞTİRİCİ BİLGİLERİ:\n" + "Bu uygulama Örnek Geliştirici tarafından geliştirilmiştir.\n"
				+ "İletişim: akdemirferit@gmail.com\n\n" + "Sosyal Medya:\n"
				+ "- Instagram butonuna tıklayarak profilimize ulaşabilirsiniz\n"
				+ "- GitHub butonuna tıklayarak proje kodlarını inceleyebilirsiniz";

		SpannableString spannableText = new SpannableString(rawContent);

		// Mail adresini tıklanabilir yap
		int mailStart = rawContent.indexOf("akdemirferit@gmail.com");
		if (mailStart != -1) {
			spannableText.setSpan(new ClickableSpan() {
				@Override
				public void onClick(View widget) {
					performHapticFeedback(widget);
					Intent intent = new Intent(Intent.ACTION_SENDTO);
					intent.setData(Uri.parse("mailto:akdemirferit@gmail.com"));
					intent.putExtra(Intent.EXTRA_SUBJECT, "Uygulama Hakkında");
					try {
						startActivity(Intent.createChooser(intent, "E-posta gönder..."));
					} catch (ActivityNotFoundException ex) {
						Toast.makeText(MainActivity.this, "E-posta uygulaması bulunamadı", Toast.LENGTH_SHORT).show();
					}
				}

				@Override
				public void updateDrawState(TextPaint ds) {
					super.updateDrawState(ds);
					ds.setColor(Color.BLUE);
					ds.setUnderlineText(false);
				}
			}, mailStart, mailStart + "akdemirferit@gmail.com".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		// Diğer span işlemleri
		applySpanToKeyword(spannableText, "API Sorguları:", 0xFF1565C0);
		applySpanToKeyword(spannableText, "NASIL KULLANILIR?", 0xFF1565C0);
		applySpanToKeyword(spannableText, "DİKKAT EDİLMESİ GEREKENLER:", 0xFF1565C0);
		applySpanToKeyword(spannableText, "GELİŞTİRİCİ BİLGİLERİ", 0xFF1565C0);

		// İşlenmiş metin TextView'a set ediliyor
		content.setText(spannableText);
		container.addView(content);

		// İkinci ayırıcı çizgi (içerik-alt kısmı butonlar için)
		View divider2 = new View(this);
		divider2.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) (2 * density)));
		divider2.setBackgroundColor(Color.parseColor("#B0BEC5"));
		container.addView(divider2);

		// Butonları içeren yatay LinearLayout
		LinearLayout buttonLayout = new LinearLayout(this);
		buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
		buttonLayout.setPadding(0, padding / 2, 0, 0);

		// Butonların layout parametreleri (eşit genişlik)
		LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,
				1f);
		btnParams.setMargins(0, 0, btnMargin, 0);

		LinearLayout.LayoutParams ghParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,
				1f);

		// Butonlar için renk tanımları
		int instagramOrange = Color.parseColor("#FFA500");
		int instagramOrangePressed = Color.parseColor("#FF8800");
		int githubBlack = Color.parseColor("#000000");
		int githubBlackPressed = Color.parseColor("#222222");
		int blue = Color.parseColor("#1976D2");
		int bluePressed = Color.parseColor("#135BA9");
		int white = Color.WHITE;

		// Instagram butonu oluşturuluyor
		Button btnInstagram = createColorRippleButton("Instagram", "\uD83D\uDCF8", instagramOrange,
				instagramOrangePressed, white);
		btnInstagram.setLayoutParams(btnParams);
		btnInstagram.setOnClickListener(v -> {
			performHapticFeedback(v); // Dokunma titreşimi
			Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://instagram.com/ferit22901"));
			startActivity(intent); // Instagram sayfasını aç
		});

		// GitHub butonu oluşturuluyor
		Button btnGitHub = createColorRippleButton("GitHub", "\uD83D\uDCBB", githubBlack, githubBlackPressed, white);
		btnGitHub.setLayoutParams(ghParams);
		btnGitHub.setOnClickListener(v -> {
			performHapticFeedback(v);
			Intent intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://github.com/Memati8383"));
			startActivity(intent); // GitHub profilini aç
		});

		// Butonlar layout'a ekleniyor
		buttonLayout.addView(btnInstagram);
		buttonLayout.addView(btnGitHub);
		container.addView(buttonLayout);

		// Tamam butonu oluşturuluyor, dialog'u kapatır
		Button btnOk = createColorRippleButton("Tamam", "\u2714", blue, bluePressed, white);
		LinearLayout.LayoutParams okParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		okParams.gravity = Gravity.END;
		okParams.topMargin = padding;
		btnOk.setLayoutParams(okParams);
		btnOk.setOnClickListener(v -> dialog.dismiss()); // Dialog kapatılır
		container.addView(btnOk);

		// Dialog içerik görünümü olarak scrollView ayarlanıyor
		dialog.setContentView(scrollView);

		Window window = dialog.getWindow();
		if (window != null) {
			window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND); // Arkayı karart
			window.setDimAmount(0.4f); // Karartma miktarı

			// Yuvarlatılmış köşe ve kenarlık için arka plan drawable
			GradientDrawable bgDrawable = new GradientDrawable();
			bgDrawable.setColor(Color.WHITE);
			bgDrawable.setCornerRadius(cornerRadius);
			bgDrawable.setStroke((int) (1 * density), Color.parseColor("#B0BEC5"));
			container.setBackground(bgDrawable);

			// Dialog genişliğini ekran genişliğine ayarla (MATCH_PARENT) yükseklik wrap_content
			window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

			// ScrollView genişliği alındıktan sonra maksimum genişlik limiti kontrolü
			scrollView.post(() -> {
				int width = scrollView.getWidth();
				if (width > maxWidthPx) {
					window.setLayout(maxWidthPx, ViewGroup.LayoutParams.WRAP_CONTENT);
				}
			});

			// Lollipop üzeri sürümlerde elevation ekle (gölge efekti)
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				window.setElevation(14 * density);
			}

			// Fade-in animasyonu için alpha başlangıç 0, animasyon animasyonda 1 yap
			scrollView.setAlpha(0f);
			scrollView.animate().alpha(1f).setDuration(300).start();
		}

		dialog.show(); // Dialog gösterilir
	}

	// Belirtilen anahtar kelimeyi SpannableString içerisinde kalın yapar ve belirtilen renkte renklendirir
	private void applySpanToKeyword(SpannableString ss, String keyword, int color) {
		int index = ss.toString().indexOf(keyword); // Anahtar kelimenin başlayacağı pozisyonu bul
		if (index >= 0) {
			// Anahtar kelimeyi kalın yap
			ss.setSpan(new StyleSpan(Typeface.BOLD), index, index + keyword.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// Anahtar kelimenin rengini ayarla
			ss.setSpan(new ForegroundColorSpan(color), index, index + keyword.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
	}

	// Metin, ikon ve renk parametrelerine göre dalga efektli (ripple) buton oluşturur
	private Button createColorRippleButton(String text, String iconUnicode, int normalColor, int pressedColor,
			int textColor) {
		Button btn = new Button(this);
		btn.setAllCaps(false); // Tüm harflerin büyük olmasını engelle
		btn.setText(iconUnicode + "  " + text); // İkon ve metni butona ekle
		btn.setTextColor(textColor); // Yazı rengini ayarla
		btn.setTypeface(null, Typeface.BOLD); // Yazı tipini kalın yap
		int paddingDp = (int) (12 * getResources().getDisplayMetrics().density); // Padding değerini dp cinsinden ayarla
		btn.setPadding(paddingDp, paddingDp / 2, paddingDp, paddingDp / 2); // Buton içi boşluk ayarları

		// Android Lollipop ve üstü için ripple efektini uygula
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			ColorStateList rippleColor = ColorStateList.valueOf(pressedColor);
			ColorDrawable backgroundColor = new ColorDrawable(normalColor);
			RippleDrawable rippleDrawable = new RippleDrawable(rippleColor, backgroundColor, null);
			btn.setBackground(rippleDrawable);
		} else { // Daha eski sürümler için durum tabanlı arka plan değişimi
			StateListDrawable states = new StateListDrawable();
			ColorDrawable pressedDrawable = new ColorDrawable(pressedColor);
			ColorDrawable normalDrawable = new ColorDrawable(normalColor);
			states.addState(new int[] { android.R.attr.state_pressed }, pressedDrawable); // Basılı durum rengi
			states.addState(new int[] {}, normalDrawable); // Normal durum rengi
			btn.setBackground(states);
		}

		return btn; // Butonu döndür
	}

	// View üzerinde dokunma titreşimi (haptic feedback) uygular
	private void performHapticFeedback(View v) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			v.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK); // Android Oreo ve üzeri için bağlam tıklaması titreşimi
		} else {
			v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY); // Daha eski sürümler için sanal tuş titreşimi
		}
	}

	// Kullanıcıya dışa aktarmak için veri olup olmadığını kontrol eder ve işlem başlatır
	private void exportResults() {
		// Tüm API sonuçlarının null olup olmadığını kontrol et
		if (lastApi1Results == null && lastApi2Results == null && lastApi3Results == null && lastApi4Results == null
				&& lastApi5Results == null && lastApi6Results == null && lastApi7Results == null
				&& lastApi8Results == null && lastApi9Results == null && lastApi10Results == null
				&& lastApi11Results == null && lastApi12Results == null && lastApi13Results == null
				&& lastApi14Results == null) {
			// Veri yoksa kullanıcıya bildir ve işlemi sonlandır
			showToast("Dışa aktarılacak veri yok");
			return;
		}

		// Veri varsa kullanıcıya dışa aktarma formatı seçimi için dialog göster
		showFormatChoiceDialog();
	}

	// Kaydedilmek üzere dışa aktarılacak veriyi tutmak için sınıf değişkeni
	private String exportDataToWrite = "";

	// Dosya kaydetme işlemi tamamlandığında çağrılan yöntem
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// REQUEST_CODE 123 ile dönen sonucu kontrol et ve işlem yap
		if (requestCode == 123 && resultCode == RESULT_OK && data != null && data.getData() != null) {
			Uri uri = data.getData();
			// Kullanıcının seçtiği URI'ye veri yazmaya çalış
			try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
				if (outputStream != null) {
					// Daha önce ayarlanan veriyi byte olarak yaz ve flush et
					outputStream.write(exportDataToWrite.getBytes());
					outputStream.flush();
					showToast("Dosya başarıyla kaydedildi");
				} else {
					// OutputStream açılamadıysa hata ver
					showToast("Dosya oluşturulamadı");
				}
			} catch (Exception e) {
				// Yazma sırasında exception oluşursa kullanıcıya bildir
				showToast("Dosya yazma hatası: " + e.getMessage());
			}
		}
	}

	// İçeriği otomatik olarak "İndirilenler" klasörüne kaydeder
	private void saveToFile(String content, String extension) {
		try {
			// Dosya ismini zaman damgası ile oluştur
			String fileName = "ondex_sonuclar_" + System.currentTimeMillis() + "." + extension;
			File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					fileName);

			// Dosya akışını aç ve içeriği yaz
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(content.getBytes());
			fos.close();

			// Başarılı kaydetme bildirimi
			showToast("İndirilenler/" + fileName + " olarak kaydedildi");
		} catch (IOException e) {
			// Hata durumunda kullanıcıya bildir
			showToast("Dosya kaydedilirken hata oluştu: " + e.getMessage());
		}
	}

	// Kullanıcıya dosya kaydetmek için kayıt konumu seçtiren dosya seçici başlatır
	private void startFileSavePicker(String content, String extension) {
		// Kaydedilecek veriyi global değişkene aktar
		exportDataToWrite = content;

		// Dosya oluşturmak için ACTION_CREATE_DOCUMENT intent'i başlat
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("text/plain");

		// Önerilen dosya adı ile dosya oluşturma penceresini aç
		intent.putExtra(Intent.EXTRA_TITLE, "ondex_sonuclar_" + System.currentTimeMillis() + "." + extension);

		// Request code 123 ile startActivityForResult çağrısı
		startActivityForResult(intent, 123);
	}

	// Kaydetme şekli için kullanıcıya seçim yaptıran dialog gösterir
	// "Konum Seç" seçeneği ile dosya kaydetme penceresi açılır
	// "İndirilenler'e Kaydet" seçeneği ile otomatik kaydetme yapılır
	private void showSaveOptionDialog(String content, String extension) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Kayıt Seçimi")
				.setMessage("Konumu seçmek ister misiniz yoksa otomatik olarak 'İndirilenler'e mi kaydedilsin?")
				.setPositiveButton("Konum Seç", (dialog, which) -> {
					// PDF formatı ise direkt PDF kaydetme fonksiyonunu çağır
					if (extension.equals("pdf")) {
						createAndSavePdf(content);
					} else {
						// Diğer formatlarda kullanıcıdan dosya konumu seçmesini iste
						startFileSavePicker(content, extension);
					}
				}).setNegativeButton("İndirilenler'e Kaydet", (dialog, which) -> {
					// PDF formatı ise direkt PDF kaydetme fonksiyonu
					if (extension.equals("pdf")) {
						createAndSavePdf(content);
					} else {
						// Diğer formatlarda otomatik olarak indirilenlere kaydet
						saveToFile(content, extension);
					}
				}).setCancelable(true).show();
	}

	// Kullanıcıya dışa aktarma formatını seçtiren dialog
	private void showFormatChoiceDialog() {
		final String[] formats = { "TXT", "JSON", "CSV", "PDF" };

		// Formatlar listesi ile dialog oluştur ve seçim yapıldığında ilgili export fonksiyonunu çağır
		new AlertDialog.Builder(this).setTitle("Dışa Aktarma Formatı").setItems(formats, (dialog, which) -> {
			switch (which) {
			case 0:
				exportAsTxt();
				break;
			case 1:
				exportAsJson();
				break;
			case 2:
				exportAsCsv();
				break;
			case 3:
				exportAsPdf();
				break;
			}
		}).show();
	}

	private void exportAsTxt() {
		StringBuilder txt = new StringBuilder("ONDEX API Sorgu Sonuçları\n\n");

		try {
			// API1 için düzeltme: Eğer tek bir sonuç varsa, onu bir array içine al
			if (lastApi1Results != null) {
				JSONArray api1Array = ensureJsonArray(lastApi1Results);
				txt.append(formatReadableText("API1", api1Array)).append("\n\n");
			}

			// Diğer API'ler (2-14)
			if (lastApi2Results != null)
				txt.append(formatReadableText("API2", ensureJsonArray(lastApi2Results))).append("\n\n");
			if (lastApi3Results != null)
				txt.append(formatReadableText("API3", ensureJsonArray(lastApi3Results))).append("\n\n");
			if (lastApi4Results != null)
				txt.append(formatReadableText("API4", ensureJsonArray(lastApi4Results))).append("\n\n");
			if (lastApi5Results != null)
				txt.append(formatReadableText("API5", ensureJsonArray(lastApi5Results))).append("\n\n");
			if (lastApi6Results != null)
				txt.append(formatReadableText("API6", ensureJsonArray(lastApi6Results))).append("\n\n");
			if (lastApi7Results != null)
				txt.append(formatReadableText("API7", ensureJsonArray(lastApi7Results))).append("\n\n");
			if (lastApi8Results != null)
				txt.append(formatReadableText("API8", ensureJsonArray(lastApi8Results))).append("\n\n");
			if (lastApi9Results != null)
				txt.append(formatReadableText("API9", ensureJsonArray(lastApi9Results))).append("\n\n");
			if (lastApi10Results != null)
				txt.append(formatReadableText("API10", ensureJsonArray(lastApi10Results))).append("\n\n");
			if (lastApi11Results != null)
				txt.append(formatReadableText("API11", ensureJsonArray(lastApi11Results))).append("\n\n");
			if (lastApi12Results != null)
				txt.append(formatReadableText("API12", ensureJsonArray(lastApi12Results))).append("\n\n");
			if (lastApi13Results != null)
				txt.append(formatReadableText("API13", ensureJsonArray(lastApi13Results))).append("\n\n");
			if (lastApi14Results != null)
				txt.append(formatReadableText("API14", ensureJsonArray(lastApi14Results))).append("\n\n");

			// Oluşturulma tarihi
			txt.append("\nOluşturulma Tarihi: ")
					.append(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date()))
					.append("\n");

			showSaveOptionDialog(txt.toString(), "txt");

		} catch (Exception e) {
			showToast("TXT oluşturma hatası: " + e.getMessage());
		}
	}

	/** JSON verisini her zaman bir JSONArray'e çevirir */
	private JSONArray ensureJsonArray(Object data) throws JSONException {
		if (data == null) {
			return new JSONArray(); // Boş array döndür
		} else if (data instanceof JSONArray) {
			return (JSONArray) data; // Zaten array ise direkt döndür
		} else {
			// Eğer tek bir JSONObject veya String ise, onu bir array içine al
			JSONArray array = new JSONArray();
			array.put(data);
			return array;
		}
	}

	// JSON formatında verileri dışa aktarma işlemi
	private void exportAsJson() {
		JSONObject exportJson = new JSONObject();

		try {
			// Add results with descriptive keys
			if (lastApi1Results != null)
				exportJson.put("tc_kimlik_sorgu", lastApi1Results);
			if (lastApi2Results != null)
				exportJson.put("ad_soyad_arama", lastApi2Results);
			if (lastApi3Results != null)
				exportJson.put("adres_bilgileri", lastApi3Results);
			if (lastApi4Results != null)
				exportJson.put("hane_bilgileri", lastApi4Results);
			if (lastApi5Results != null)
				exportJson.put("okul_bilgileri", lastApi5Results);
			if (lastApi6Results != null)
				exportJson.put("isyeri_bilgileri", lastApi6Results);
			if (lastApi7Results != null)
				exportJson.put("gsm_detaylar", lastApi7Results);
			if (lastApi8Results != null)
				exportJson.put("aile_bilgileri", lastApi8Results);
			if (lastApi9Results != null)
				exportJson.put("sülale_bilgileri", lastApi9Results);
			if (lastApi10Results != null)
				exportJson.put("tc_gsm_coklu_sorgu", lastApi10Results);
			if (lastApi11Results != null)
				exportJson.put("ehliyet_bilgileri", lastApi11Results);
			if (lastApi12Results != null)
				exportJson.put("tapu_bilgileri", lastApi12Results);
			if (lastApi13Results != null)
				exportJson.put("vesika_bilgileri", lastApi13Results);
			if (lastApi14Results != null)
				exportJson.put("detayli_adres", lastApi14Results);

			// Add metadata
			exportJson.put("olusturulma_tarihi",
					new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date()));
			exportJson.put("kaynak", "ONDEX API Sorgu Sonuçları");

			showSaveOptionDialog(exportJson.toString(2), "json");

		} catch (JSONException e) {
			showToast("JSON oluşturma hatası: " + e.getMessage());
		}
	}

	// CSV formatında verileri dışa aktarma işlemi
	private void exportAsCsv() {
		StringBuilder csv = new StringBuilder();

		try {
			// API sonuçlarını CSV formatına çevir ve başlık ekle
			if (lastApi1Results != null) {
				JSONArray array = new JSONArray();
				array.put(lastApi1Results); // Tek bir sonucu diziye sar
				csv.append("=== API1 ===\n").append(jsonArrayToCsv(array)).append("\n");
			}
			if (lastApi2Results != null)
				csv.append("=== API2 ===\n").append(jsonArrayToCsv(lastApi2Results)).append("\n");
			if (lastApi3Results != null)
				csv.append("=== API3 ===\n").append(jsonArrayToCsv(lastApi3Results)).append("\n");
			if (lastApi4Results != null)
				csv.append("=== API4 ===\n").append(jsonArrayToCsv(lastApi4Results)).append("\n");
			if (lastApi5Results != null)
				csv.append("=== API5 ===\n").append(jsonArrayToCsv(lastApi5Results)).append("\n");
			if (lastApi6Results != null)
				csv.append("=== API6 ===\n").append(jsonArrayToCsv(lastApi6Results)).append("\n");
			if (lastApi7Results != null)
				csv.append("=== API7 ===\n").append(jsonArrayToCsv(lastApi7Results)).append("\n");
			if (lastApi8Results != null)
				csv.append("=== API8 ===\n").append(jsonArrayToCsv(lastApi8Results)).append("\n");
			if (lastApi9Results != null)
				csv.append("=== API9 ===\n").append(jsonArrayToCsv(lastApi9Results)).append("\n");
			if (lastApi10Results != null)
				csv.append("=== API10 ===\n").append(jsonArrayToCsv(lastApi10Results)).append("\n");
			if (lastApi11Results != null)
				csv.append("=== API11 ===\n").append(jsonArrayToCsv(lastApi11Results)).append("\n");
			if (lastApi12Results != null)
				csv.append("=== API12 ===\n").append(jsonArrayToCsv(lastApi12Results)).append("\n");
			if (lastApi13Results != null)
				csv.append("=== API13 ===\n").append(jsonArrayToCsv(lastApi13Results)).append("\n");
			if (lastApi14Results != null)
				csv.append("=== API14 ===\n").append(jsonArrayToCsv(lastApi14Results)).append("\n");

			// CSV içeriğini kaydetmek için seçenek göster
			showSaveOptionDialog(csv.toString(), "csv");

		} catch (Exception e) {
			// CSV oluşturma hatası durumunda göster
			showToast("CSV oluşturma hatası: " + e.getMessage());
		}
	}

	// JSON Dizisini CSV formatına çeviren yardımcı metod
	private String jsonArrayToCsv(JSONArray array) throws JSONException {
		StringBuilder csv = new StringBuilder();
		if (array.length() == 0)
			return "";

		// Başlık satırını oluştur (JSON objesindeki key'ler)
		JSONObject first = array.getJSONObject(0);
		Iterator<String> keys = first.keys();
		List<String> keyList = new ArrayList<>();
		while (keys.hasNext()) {
			String key = keys.next();
			keyList.add(key);
			csv.append(key).append(",");
		}
		// Son virgülü kaldır ve satırı sonlandır
		csv.setLength(csv.length() - 1);
		csv.append("\n");

		// Her bir JSON objesinin değerlerini CSV satırı olarak ekle
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = array.getJSONObject(i);
			for (String key : keyList) {
				// Virgül karışmaması için değerlerdeki virgülü boşluk yap
				csv.append(obj.optString(key).replace(",", " ")).append(",");
			}
			csv.setLength(csv.length() - 1);
			csv.append("\n");
		}

		return csv.toString();
	}

	// PDF formatında verileri dışa aktarma işlemi
	private void exportAsPdf() {
		StringBuilder content = new StringBuilder("ONDEX API Sorgu Sonuçları\n\n");

		try {
			// API sonuçlarını PDF içeriğine ekle
			if (lastApi1Results != null)
				content.append(formatReadableText("1. TC Kimlik Sorgu Sonuçları", ensureJsonArray(lastApi1Results)))
						.append("\n\n");
			if (lastApi2Results != null)
				content.append(formatReadableText("2. Ad-Soyad Arama Sonuçları", ensureJsonArray(lastApi2Results)))
						.append("\n\n");
			if (lastApi3Results != null)
				content.append(formatReadableText("3. Adres Bilgileri", ensureJsonArray(lastApi3Results)))
						.append("\n\n");
			if (lastApi4Results != null)
				content.append(formatReadableText("4. Hane Bilgileri", ensureJsonArray(lastApi4Results)))
						.append("\n\n");
			if (lastApi5Results != null)
				content.append(formatReadableText("5. Okul Bilgileri", ensureJsonArray(lastApi5Results)))
						.append("\n\n");
			if (lastApi6Results != null)
				content.append(formatReadableText("6. İşyeri Bilgileri", ensureJsonArray(lastApi6Results)))
						.append("\n\n");
			if (lastApi7Results != null)
				content.append(formatReadableText("7. GSM Detayları", ensureJsonArray(lastApi7Results))).append("\n\n");
			if (lastApi8Results != null)
				content.append(formatReadableText("8. Aile Bilgileri", ensureJsonArray(lastApi8Results)))
						.append("\n\n");
			if (lastApi9Results != null)
				content.append(formatReadableText("9. Sülale Bilgileri", ensureJsonArray(lastApi9Results)))
						.append("\n\n");
			if (lastApi10Results != null)
				content.append(formatReadableText("10. TC/GSM Çoklu Sorgu", ensureJsonArray(lastApi10Results)))
						.append("\n\n");
			if (lastApi11Results != null)
				content.append(formatReadableText("11. Ehliyet Bilgileri", ensureJsonArray(lastApi11Results)))
						.append("\n\n");
			if (lastApi12Results != null)
				content.append(formatReadableText("12. Tapu Bilgileri", ensureJsonArray(lastApi12Results)))
						.append("\n\n");
			if (lastApi13Results != null)
				content.append(formatReadableText("13. Vesika Bilgileri", ensureJsonArray(lastApi13Results)))
						.append("\n\n");
			if (lastApi14Results != null)
				content.append(formatReadableText("14. Detaylı Adres Bilgileri", ensureJsonArray(lastApi14Results)))
						.append("\n\n");

			// Metadata ekle
			content.append("\nOluşturulma Tarihi: ")
					.append(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()).format(new Date()))
					.append("\nFormat: PDF\nKaynak: ONDEX API");

			createAndSavePdf(content.toString());

		} catch (Exception e) {
			showToast("PDF içerik oluşturma hatası: " + e.getMessage());
		}
	}

	private void createAndSavePdf(String content) {
		try {
			String fileName = "ondex_sonuclar_" + System.currentTimeMillis() + ".pdf";
			File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					fileName);

			PdfDocument document = new PdfDocument();
			Paint titlePaint = new Paint();
			Paint contentPaint = new Paint();

			// Stilleri ayarla
			titlePaint.setTextSize(16);
			titlePaint.setColor(Color.BLACK);
			titlePaint.setFakeBoldText(true);
			contentPaint.setTextSize(12);
			contentPaint.setColor(Color.DKGRAY);

			PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
			PdfDocument.Page page = document.startPage(pageInfo);
			Canvas canvas = page.getCanvas();

			int y = 40;

			// Başlık çiz
			canvas.drawText("ONDEX API Sorgu Sonuçları", 30, y, titlePaint);
			y += 30;

			// İçerik çiz
			for (String line : content.split("\n")) {
				if (y > 800) {
					document.finishPage(page);
					pageInfo = new PdfDocument.PageInfo.Builder(595, 842, document.getPages().size() + 1).create();
					page = document.startPage(pageInfo);
					canvas = page.getCanvas();
					y = 40;
				}

				// Başlık satırlarını kalın yap
				if (line.startsWith("===") || line.matches("^\\d+\\.\\s.+")) {
					canvas.drawText(line, 30, y, titlePaint);
				} else {
					canvas.drawText(line, 30, y, contentPaint);
				}
				y += 20;
			}

			document.finishPage(page);

			FileOutputStream fos = new FileOutputStream(file);
			document.writeTo(fos);
			document.close();
			fos.close();

			showToast("PDF kaydedildi: İndirilenler/" + fileName);

		} catch (Exception e) {
			showToast("PDF oluşturma hatası: " + e.getMessage());
		}
	}

	// API sonuçlarının JSON dizisini okunabilir biçimde metin haline getirir
	private String formatReadableText(String apiName, JSONArray jsonArray) throws JSONException {
		StringBuilder sb = new StringBuilder();
		sb.append("=== ").append(apiName).append(" ===\n");

		// Her kayıt için anahtar-değer çiftleri listele
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			sb.append("• Kayıt ").append(i + 1).append(":\n");

			Iterator<String> keys = obj.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				sb.append("   ").append(key).append(": ").append(obj.optString(key)).append("\n");
			}
			sb.append("\n"); // Kayıtlar arası boşluk bırak
		}
		return sb.toString();
	}

	private void createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "API Bildirimleri",
					NotificationManager.IMPORTANCE_DEFAULT);
			channel.setDescription("API sonuç bildirimleri");

			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			if (notificationManager != null) {
				notificationManager.createNotificationChannel(channel);
			}
		}
	}

	private void sendNotification(String title, String message, int notificationId) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// Ana aktiviteyi açacak intent
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

		Notification.Builder builder;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			builder = new Notification.Builder(this, CHANNEL_ID);
		} else {
			builder = new Notification.Builder(this).setPriority(Notification.PRIORITY_HIGH); // Android 7.1 ve altı için öncelik
		}

		// Uzun metin için BigTextStyle kullanımı
		Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle().bigText(message)
				.setBigContentTitle(title).setSummaryText("Uygulamanızdan gelen bildirim");

		builder.setContentTitle(title).setContentText(message).setSmallIcon(android.R.drawable.ic_dialog_info)
				.setColor(getResources().getColor(android.R.color.holo_blue_light, getTheme())).setAutoCancel(true)
				.setContentIntent(pendingIntent).setStyle(bigTextStyle).setDefaults(Notification.DEFAULT_ALL);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			// API 26 öncesi için sadece öncelik ayarlanabilir
			builder.setPriority(Notification.PRIORITY_HIGH);
		}

		if (notificationManager != null) {
			notificationManager.notify(notificationId, builder.build());
		}
	}

}
