package com.my;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.Html;
import android.text.style.StyleSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.HapticFeedbackConstants;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import java.io.File;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class MainActivity extends Activity {

	// ScrollView referansı
	private ScrollView scrollView;

	// Ana UI thread üzerinde işlem yapmak için Handler
	private Handler mainHandler = new Handler(Looper.getMainLooper());

	// Butonlar (API çağrıları için)
	private Button btnApi1, btnApi2, btnApi3, btnApi4, btnApi5, btnApi6, btnApi7, btnApi8, btnApi9, btnApi10, btnApi11;

	// API'ler için ayrı ayrı LinearLayout'lar (arayüz düzenleri)
	private LinearLayout layoutApi1, layoutApi2, layoutApi3, layoutApi4, layoutApi5, layoutApi6, layoutApi7, layoutApi8,
			layoutApi9, layoutApi10, layoutApi11;

	// API'lere özgü EditText alanları (kullanıcıdan veri girişi için)
	private EditText etTcApi1, etAdApi2, etSoyadApi2, etIlApi2, etIlceApi2, etTcApi3, etTcApi4, etTcApi5, etTcApi6,
			etTcApi7, etTcApi8, etTcApi9, etGsmApi9, etTcApi10, etTcApi11;

	// Sorgu butonları (API çağrılarını başlatmak için)
	private Button btnFetchApi1, btnFetchApi2, btnFetchApi3, btnFetchApi4, btnFetchApi5, btnFetchApi6, btnFetchApi7,
			btnFetchApi8, btnFetchApi9, btnFetchApi10, btnFetchApi11;

	// API çağrısı sonuçlarının gösterileceği LinearLayout container'ları
	private LinearLayout resultContainerApi1, resultContainerApi2, resultContainerApi3, resultContainerApi4,
			resultContainerApi5, resultContainerApi6, resultContainerApi7, resultContainerApi8, resultContainerApi9,
			resultContainerApi10, resultContainerApi11;

	// En son alınan API sonuçlarını tutan JSONArray ya da JSONObject değişkenleri
	private JSONArray lastApi2Results, lastApi3Results, lastApi4Results, lastApi5Results, lastApi6Results,
			lastApi7Results, lastApi8Results, lastApi9Results, lastApi10Results, lastApi11Results;
	private JSONObject lastApi1Results;

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
				String apiUrl = "https://api.ondex.uk/ondexapi/tcprosorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi1, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONObject veri = json.getJSONObject("Veri");
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

			if (ad.isEmpty() || soyad.isEmpty() || il.isEmpty() || ilce.isEmpty()) {
				showToast("Ad, Soyad, İl ve İlçe giriniz");
				return;
			}
			try {
				String apiUrl = "http://api.ondex.uk/ondexapi/adsoyadprosorgu.php?ad=" + URLEncoder.encode(ad, "UTF-8")
						+ "&soyad=" + URLEncoder.encode(soyad, "UTF-8") + "&il=" + URLEncoder.encode(il, "UTF-8")
						+ "&ilce=" + URLEncoder.encode(ilce, "UTF-8");
				fetchApi(apiUrl, resultContainerApi2, new ApiResultHandler() {

					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
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
				String apiUrl = "https://api.ondex.uk/ondexapi/adressorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi3, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONObject veri = json.getJSONObject("Veri");
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
				String apiUrl = "http://api.ondex.uk/ondexapi/hanesorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi4, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
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

		// API5 - apartman sorgu
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
				String apiUrl = "https://api.ondex.uk/ondexapi/apartmansorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi5, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
								lastApi5Results = arr;
								String filter = etFilterApi5.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Apartman Sorgulama Başarılı", arr.length() + " kayıt bulundu.", 5001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi5Results = null;
								sendNotification("Apartman Sorgu Sonuç Yok", "Kayıt bulunamadı.", 5002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Apartman JSON Hatası", e.getMessage(), 5003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi5Results = null;
						sendNotification("Apartman Sorgu Hatası", errorMessage, 5004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API6 - sokak sorgu
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
				String apiUrl = "https://api.ondex.uk/ondexapi/sokaksorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi6, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
								lastApi6Results = arr;
								String filter = etFilterApi6.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Sokak Sorgulama Başarılı", arr.length() + " kayıt bulundu.", 6001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi6Results = null;
								sendNotification("Sokak Sorgu Sonuç Yok", "Kayıt bulunamadı.", 6002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Sokak JSON Hatası", e.getMessage(), 6003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi6Results = null;
						sendNotification("Sokak Sorgu Hatası", errorMessage, 6004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

		// API7 - mahalle sorgu
		btnFetchApi7.setOnClickListener(v -> {
			String tc = etTcApi7.getText().toString().trim();
			EditText etFilterApi7 = findViewById(R.id.etFilterApi7);

			Animation anim = AnimationUtils.loadAnimation(this, R.anim.bounce);
			v.startAnimation(anim);

			if (tc.isEmpty()) {
				showToast("TC giriniz");
				return;
			}
			try {
				String apiUrl = "https://api.ondex.uk/ondexapi/mahallesorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi7, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
								lastApi7Results = arr;
								String filter = etFilterApi7.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("Mahalle Sorgulama Başarılı", arr.length() + " kayıt bulundu.", 7001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi7Results = null;
								sendNotification("Mahalle Sorgu Sonuç Yok", "Kayıt bulunamadı.", 7002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("Mahalle JSON Hatası", e.getMessage(), 7003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi7Results = null;
						sendNotification("Mahalle Sorgu Hatası", errorMessage, 7004);
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
				String apiUrl = "https://api.ondex.uk/ondexapi/aileprosorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi8, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
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
				String apiUrl = "http://api.ondex.uk/ondexapi/sulaleprosorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi9, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
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
					apiUrl = "http://api.ondex.uk/ondexapi/tcgsmsorgu.php?tc=" + URLEncoder.encode(input, "UTF-8");
				} else {
					apiUrl = "http://api.ondex.uk/ondexapi/gsmtcsorgu.php?gsm=" + URLEncoder.encode(input, "UTF-8");
				}
				fetchApi(apiUrl, resultContainerApi10, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
								lastApi10Results = arr;
								String filter = etFilterApi10.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("TC veya GSM Sorgu Başarılı", arr.length() + " kayıt bulundu.", 10001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi10Results = null;
								sendNotification("TC veya GSM Sorgu Sonuç Yok", "Kayıt bulunamadı.", 10002);
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

		// API11 - İş yeri sorgu
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
				String apiUrl = "https://api.ondex.uk/ondexapi/isyerisorgu.php?tc=" + URLEncoder.encode(tc, "UTF-8");
				fetchApi(apiUrl, resultContainerApi11, new ApiResultHandler() {
					@Override
					public void onSuccess(String jsonStr, LinearLayout container) {
						try {
							JSONObject json = new JSONObject(jsonStr);
							if (json.has("Veri")) {
								JSONArray arr = json.getJSONArray("Veri");
								lastApi11Results = arr;
								String filter = etFilterApi11.getText().toString().trim();
								showFilteredResults(arr, container, filter);
								sendNotification("İşyeri Sorgulama Başarılı", arr.length() + " kayıt bulundu.", 11001);
							} else {
								addErrorMessage(container, "Sonuç bulunamadı.");
								lastApi11Results = null;
								sendNotification("İşyeri Sorgu Sonuç Yok", "Kayıt bulunamadı.", 11002);
							}
						} catch (Exception e) {
							addErrorMessage(container, "JSON parse hatası: " + e.getMessage());
							sendNotification("İşyeri JSON Hatası", e.getMessage(), 11003);
						}
					}

					@Override
					public void onError(String errorMessage, LinearLayout container) {
						addErrorMessage(container, errorMessage);
						lastApi11Results = null;
						sendNotification("İşyeri Sorgu Hatası", errorMessage, 11004);
					}
				});
			} catch (UnsupportedEncodingException e) {
				showToast("Kodlama hatası: " + e.getMessage());
			}
		});

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

		// Tablo başlık satırı oluşturulur
		TableRow headerRow = new TableRow(this);
		Iterator<String> keysIter = person.keys();
		java.util.List<String> keys = new java.util.ArrayList<>();
		while (keysIter.hasNext()) {
			String key = keysIter.next();
			keys.add(key);

			// Her anahtar için başlık TextView oluştur ve stil uygula
			TextView tvHeader = new TextView(this);
			tvHeader.setText(key);
			tvHeader.setPadding(10, 10, 20, 10);
			tvHeader.setTextSize(16);
			tvHeader.setTextColor(0xFF000000);
			headerRow.addView(tvHeader);
		}
		table.addView(headerRow);

		// Tablo değerler satırı oluşturulur
		TableRow valueRow = new TableRow(this);
		for (String key : keys) {
			if (key.equals("GSM")) {
				// "GSM" anahtarı özel işlem gerektirir (JSON Array içinde Array)
				JSONArray gsmArray = person.optJSONArray("GSM");
				LinearLayout gsmLayout = new LinearLayout(this);
				gsmLayout.setOrientation(LinearLayout.VERTICAL);
				if (gsmArray != null) {
					// GSM numaraları dikey olarak ayrı TextView'larla eklenir
					for (int i = 0; i < gsmArray.length(); i++) {
						JSONArray innerArr = gsmArray.optJSONArray(i);
						if (innerArr != null && innerArr.length() > 0) {
							String gsmNo = innerArr.optString(0);
							TextView gsmText = new TextView(this);
							gsmText.setText(gsmNo);
							gsmText.setPadding(10, 10, 20, 10);
							gsmText.setTextColor(0xFF333333);

							// Uzun tıklama ile numarayı panoya kopyalama işlevi
							gsmText.setOnLongClickListener(v -> {
								ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
								clipboard.setPrimaryClip(ClipData.newPlainText("Kopyalandı", gsmNo));
								showToast("Kopyalandı: " + gsmNo);
								return true;
							});

							gsmLayout.addView(gsmText);
						}
					}
				}
				valueRow.addView(gsmLayout);
			} else {
				// Diğer anahtarlar için basit TextView ile değer gösterilir
				TextView tvValue = new TextView(this);
				tvValue.setText(person.optString(key, ""));
				tvValue.setPadding(10, 10, 20, 10);
				tvValue.setTextColor(0xFF333333);

				// Uzun tıklama ile metni panoya kopyalama işlevi
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

		// Tabloyu yatay kaydırılabilir hale getirmek için HorizontalScrollView içine ekle
		HorizontalScrollView hsv = new HorizontalScrollView(this);
		hsv.addView(table);
		return hsv;
	}

	// Menü seçenekleri oluşturulduğunda çağrılır
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Menü XML dosyasını menu nesnesine şişirir
		getMenuInflater().inflate(R.menu.menu_main, menu);

		// "action_more" ikonunu gri renge ayarla
		MenuItem infoItem = menu.findItem(R.id.action_more);
		Drawable infoDrawable = infoItem.getIcon();
		if (infoDrawable != null) {
			infoDrawable.mutate();
			infoDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
		}

		// "action_export" ikonunu gri renge ayarla
		MenuItem exportItem = menu.findItem(R.id.action_export);
		Drawable exportDrawable = exportItem.getIcon();
		if (exportDrawable != null) {
			exportDrawable.mutate();
			exportDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);
		}
		return true;
	}

	// Menü itemlerinden biri seçildiğinde çağrılır
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		View actionView = null;

		// Eğer "action_more" seçildiyse bilgi dialogunu göster
		if (item.getItemId() == R.id.action_more) {
			actionView = findViewById(R.id.action_more); // Menüdeki view'u bul
			showInfoDialog();
		}
		// Eğer "action_export" seçildiyse sonuçları dışa aktar
		else if (item.getItemId() == R.id.action_export) {
			actionView = findViewById(R.id.action_export);
			exportResults();
		}

		// Bulunan actionView varsa butona animasyon uygula
		if (actionView != null) {
			Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
			actionView.startAnimation(anim);
		}

		// Üst sınıfın varsayılan davranışı çalıştırılır
		return super.onOptionsItemSelected(item);
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

		// İçerik metni ham hali (bilgi ve kullanım açıklamaları)
		String rawContent = "API Sorguları:\n" + "API1: TC ile tekil sorgu\n" + "API2: Ad, Soyad, İl, İlçe ile sorgu\n"
				+ "API3: TC ile adres sorgulama\n" + "API4: TC ile hane sorgulama\n"
				+ "API5: TC ile apartman sorgulama\n" + "API6: TC ile sokak sorgulama\n"
				+ "API7: TC ile mahalle sorgulama\n" + "API8: TC ile aile prosorgu\n" + "API9: TC ile sülale sorgusu\n"
				+ "API10: TC veya GSM ile sorgu\n" + "API11: TC ile iş yeri sorgusu\n\n" + "Nasıl Kullanılır?\n"
				+ "1. Ana menüden sorgulamak istediğiniz API'yi seçin.\n"
				+ "2. Açılan sorgu ekranında gerekli alanları doldurun.\n"
				+ "3. 'Sorgula' butonuna basarak verileri çekin.\n"
				+ "4. Ekranda dönen sonuçları filtreleyebilir ve inceleyebilirsiniz.\n"
				+ "5. Sonuçlara uzun basarak bilgileri panoya kopyalayabilirsiniz.\n\n" + "Geliştirici Hakkında:\n"
				+ "Bu uygulama Örnek Geliştirici tarafından geliştirilmiştir.\n"
				+ "İletişim: akdemirferit@gmail.com\n\n"
				+ "Instagram ve GitHub butonlarına tıklayarak bağlantılara ulaşabilirsiniz.";

		// SpannableString ile belirli kelimelere renk ve kalınlık uygulanacak
		SpannableString spannableText = new SpannableString(rawContent);

		// Ana başlıklar kalın ve renkli yapılıyor
		applySpanToKeyword(spannableText, "API Sorguları:", 0xFF1565C0);
		applySpanToKeyword(spannableText, "Nasıl Kullanılır?", 0xFF1565C0);
		applySpanToKeyword(spannableText, "Geliştirici Hakkında:", 0xFF1565C0);

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
				&& lastApi11Results == null) {
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

			// Diğer API'ler (2-11)
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
			// API'den gelen sonuçlar null değilse JSON objesine ekle
			if (lastApi1Results != null)
				exportJson.put("API1", lastApi1Results);
			if (lastApi2Results != null)
				exportJson.put("API2", lastApi2Results);
			if (lastApi3Results != null)
				exportJson.put("API3", lastApi3Results);
			if (lastApi4Results != null)
				exportJson.put("API4", lastApi4Results);
			if (lastApi5Results != null)
				exportJson.put("API5", lastApi5Results);
			if (lastApi6Results != null)
				exportJson.put("API6", lastApi6Results);
			if (lastApi7Results != null)
				exportJson.put("API7", lastApi7Results);
			if (lastApi8Results != null)
				exportJson.put("API8", lastApi8Results);
			if (lastApi9Results != null)
				exportJson.put("API9", lastApi9Results);
			if (lastApi10Results != null)
				exportJson.put("API10", lastApi10Results);
			if (lastApi11Results != null)
				exportJson.put("API11", lastApi11Results);

			// Oluşturulan JSON verisini kaydetme seçeneği sun
			showSaveOptionDialog(exportJson.toString(2), "json");

		} catch (JSONException e) {
			// JSON oluşturma sırasında hata olursa kullanıcıya bildir
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
			// API sonuçlarını okunabilir metin formatında PDF içeriğine ekle
			if (lastApi1Results != null)
				content.append(formatReadableText("API1", ensureJsonArray(lastApi1Results))).append("\n");
			if (lastApi2Results != null)
				content.append(formatReadableText("API2", ensureJsonArray(lastApi2Results))).append("\n");
			if (lastApi3Results != null)
				content.append(formatReadableText("API3", ensureJsonArray(lastApi3Results))).append("\n");
			if (lastApi4Results != null)
				content.append(formatReadableText("API4", ensureJsonArray(lastApi4Results))).append("\n");
			if (lastApi5Results != null)
				content.append(formatReadableText("API5", ensureJsonArray(lastApi5Results))).append("\n");
			if (lastApi6Results != null)
				content.append(formatReadableText("API6", ensureJsonArray(lastApi6Results))).append("\n");
			if (lastApi7Results != null)
				content.append(formatReadableText("API7", ensureJsonArray(lastApi7Results))).append("\n");
			if (lastApi8Results != null)
				content.append(formatReadableText("API8", ensureJsonArray(lastApi8Results))).append("\n");
			if (lastApi9Results != null)
				content.append(formatReadableText("API9", ensureJsonArray(lastApi9Results))).append("\n");
			if (lastApi10Results != null)
				content.append(formatReadableText("API10", ensureJsonArray(lastApi10Results))).append("\n");
			if (lastApi11Results != null)
				content.append(formatReadableText("API11", ensureJsonArray(lastApi11Results))).append("\n");

			// PDF kaydetme seçeneğini kullanıcya göster
			showSaveOptionDialog(content.toString(), "pdf");

		} catch (Exception e) {
			// Hata durumunda kullanıcı bilgilendirilir
			showToast("PDF içerik oluşturma hatası: " + e.getMessage());
		}
	}

	// String olarak verilen içeriği PDF dosyasına dönüştür ve kaydet
	private void createAndSavePdf(String content) {
		try {
			// PDF dosya adını oluştur (timestamp ile)
			String fileName = "ondex_sonuclar_" + System.currentTimeMillis() + ".pdf";
			File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
					fileName);

			PdfDocument document = new PdfDocument();
			Paint paint = new Paint();
			paint.setTextSize(12);

			// Sayfa ayarları ve sayfa oluşturma
			PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
			PdfDocument.Page page = document.startPage(pageInfo);
			Canvas canvas = page.getCanvas();

			int y = 40; // Başlangıç Y pozisyonu (üst boşluk)
			for (String line : content.split("\n")) {
				if (y > 800) {
					// Sayfa sınırı aşıldığında yeni sayfa oluştur
					document.finishPage(page);
					pageInfo = new PdfDocument.PageInfo.Builder(595, 842, document.getPages().size() + 1).create();
					page = document.startPage(pageInfo);
					canvas = page.getCanvas();
					y = 40;
				}
				// Metni çiz
				canvas.drawText(line, 30, y, paint);
				y += 20; // Her satırın altına geç
			}

			// Son sayfayı bitir
			document.finishPage(page);

			// PDF dosyasını kaydet
			FileOutputStream fos = new FileOutputStream(file);
			document.writeTo(fos);
			document.close();
			fos.close();

			// Kaydetme başarılıysa kullanıcıya bilgi ver
			showToast("PDF kaydedildi: İndirilenler/" + fileName);

		} catch (Exception e) {
			// Oluşturma veya kaydetme hatasında hata mesajı göster
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
