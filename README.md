# ONDEX API Sorgulama Uygulaması 🔍

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)
![API](https://img.shields.io/badge/API-11%20Endpoints-blue)
![License](https://img.shields.io/badge/License-MIT-green)

![App Icon](app-icon.png)

Türkiye'de çeşitli resmi verilere erişim sağlayan API'leri kullanan Android uygulaması.

## 📌 API Endpointleri

| API No | Açıklama | Endpoint |
|--------|----------|----------|
| API1 | TC Kimlik No ile Sorgu | `https://api.ondex.uk/ondexapi/tcprosorgu.php?tc={TC}` |
| API2 | Ad-Soyad-İl-İlçe ile Sorgu | `https://api.ondex.uk/ondexapi/adsoyadprosorgu.php?ad={AD}&soyad={SOYAD}&il={IL}&ilce={ILCE}` |
| API3 | TC ile Adres Sorgulama | `https://api.ondex.uk/ondexapi/adressorgu.php?tc={TC}` |
| API4 | TC ile Hane Sorgulama | `https://api.ondex.uk/ondexapi/hanesorgu.php?tc={TC}` |
| API5 | TC ile Apartman Sorgulama | `https://api.ondex.uk/ondexapi/apartmansorgu.php?tc={TC}` |
| API6 | TC ile Sokak Sorgulama | `https://api.ondex.uk/ondexapi/sokaksorgu.php?tc={TC}` |
| API7 | TC ile Mahalle Sorgulama | `https://api.ondex.uk/ondexapi/mahallesorgu.php?tc={TC}` |
| API8 | TC ile Aile Sorgusu | `https://api.ondex.uk/ondexapi/aileprosorgu.php?tc={TC}` |
| API9 | TC ile Sülale Sorgusu | `https://api.ondex.uk/ondexapi/sulaleprosorgu.php?tc={TC}` |
| API10 | TC/GSM ile Sorgu | `https://api.ondex.uk/ondexapi/tcgsmsorgu.php?tc={TC}` veya `https://api.ondex.uk/ondexapi/gsmtcsorgu.php?gsm={GSM}` |
| API11 | TC ile İş Yeri Sorgusu | `https://api.ondex.uk/ondexapi/isyerisorgu.php?tc={TC}` |

## 🚀 Özellikler

![Feature](https://img.shields.io/badge/-11%20Farklı%20API%20Sorgusu-blue)
![Feature](https://img.shields.io/badge/-Filtreleme%20Özelliği-orange)
![Feature](https://img.shields.io/badge/-4%20Formatta%20Dışa%20Aktarma-green)
![Feature](https://img.shields.io/badge/-Bildirim%20Sistemi-yellow)

- **Çoklu API Desteği** - 11 farklı sorgu seçeneği
- **Akıllı Filtreleme** - Sonuçlarda hızlı arama
- **Export Özelliği** - TXT, JSON, CSV ve PDF formatlarında kayıt
- **Bildirimler** - Sorgu sonuçları için anlık bildirim

## 📦 Kurulum

```bash
git clone https://github.com/Memati8383/ondex-api-android.git
cd ondex-api-android
```

**Gereksinimler:**
- Android Studio Arctic Fox veya üzeri
- Android SDK 21+ (Lollipop)
- Java 8+

## 🖼️ Ekran Görüntüleri

| Ana Sayfa | Sorgu Ekranı | Sonuçlar |
|-----------|--------------|----------|
| ![Main](screenshots/main.png) | ![Query](screenshots/query.png) | ![Results](screenshots/results.png) |

## 👨‍💻 Geliştirici

[![GitHub](https://img.shields.io/badge/GitHub-Memati8383-black?logo=github)](https://github.com/Memati8383)
[![Instagram](https://img.shields.io/badge/Instagram-ferit22901-E4405F?logo=instagram)](https://instagram.com/ferit22901)
[![Email](https://img.shields.io/badge/Email-akdemirferit%40gmail.com-red?logo=gmail)](mailto:akdemirferit@gmail.com)

## ⚖️ Lisans

```text
MIT License
Copyright (c) 2023 Ferit Akdemir
```

## 🤝 Katkıda Bulunma

[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](https://github.com/Memati8383/ondex-api-android/pulls)

1. Forklayın (https://github.com/Memati8383/ondex-api-android/fork)
2. Yeni branch oluşturun (`git checkout -b feature/awesome-feature`)
3. Commit yapın (`git commit -am 'Add awesome feature'`)
4. Push yapın (`git push origin feature/awesome-feature`)
5. Yeni bir Pull Request oluşturun
