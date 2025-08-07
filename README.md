# ONDEX API Sorgulama Uygulaması 🔍

Türkiye'de çeşitli resmi verilere erişim sağlayan API'leri kullanan Android uygulaması.

## 🛠️ Teknoloji ve Özellikler

### 📱 Platform
![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![Min SDK 21](https://img.shields.io/badge/Min%20SDK-21-orange)
![Target SDK 33](https://img.shields.io/badge/Target%20SDK-33-yellow)

### 💻 Geliştirme
![Java](https://img.shields.io/badge/Java-11-007396?logo=java&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android_Studio-2022.1-3DDC84?logo=android-studio)
![Gradle 7.4](https://img.shields.io/badge/Gradle-7.4-02303A?logo=gradle)

### 🌐 API Entegrasyonu
![API Endpoints](https://img.shields.io/badge/Endpoints-14-blue)
![REST API](https://img.shields.io/badge/REST-API-ff69b4)
![JSON Format](https://img.shields.io/badge/Data-JSON-9cf)

### 📦 Proje Bilgileri
![Repo Size](https://img.shields.io/github/repo-size/Memati8383/Ondex-API-Mobil-Game)
![Last Commit](https://img.shields.io/github/last-commit/Memati8383/Ondex-API-Mobil-Game?color=blue)
![License](https://img.shields.io/badge/License-MIT-green)

### 👥 Topluluk
![GitHub Stars](https://img.shields.io/github/stars/Memati8383/Ondex-API-Mobil-Game?style=social)
![GitHub Forks](https://img.shields.io/github/forks/Memati8383/Ondex-API-Mobil-Game?style=social)
![GitHub Issues](https://img.shields.io/github/issues/Memati8383/Ondex-API-Mobil-Game)
![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)

## 🚀 API Endpointleri

| API No | Açıklama | Parametreler | Endpoint |
|--------|----------|--------------|----------|
| API1 | TC Kimlik Sorgu | `tc` | `https://api.hexnox.pro/sowixapi/tcpro.php?tc={TC}` |
| API2 | Ad-Soyad Sorgu | `ad`, `soyad`, `il`, `ilce` | `https://api.hexnox.pro/sowixapi/adsoyadilce.php?ad={AD}&soyad={SOYAD}&il={IL}&ilce={ILCE}` |
| API3 | Adres Sorgu | `tc` | `https://api.hexnox.pro/sowixapi/adres.php?tc={TC}` |
| API4 | Hane Sorgu | `tc` | `https://api.hexnox.pro/sowixapi/hane.php?tc={TC}` |
| API5 | Okul No Sorgu | `tc` | `https://api.hexnox.pro/sowixapi/okulno.php?tc={TC}` |
| API6 | İşyeri Sorgu | `tc` | `https://api.hexnox.pro/sowixapi/isyeri.php?tc={TC}` |
| API7 | GSM Detay Sorgu | `gsm` | `https://api.hexnox.pro/sowixapi/gsmdetay.php?gsm={GSM}` |
| API8 | Aile Sorgu | `tc` | `https://api.hexnox.pro/sowixapi/aile.php?tc={TC}` |
| API9 | Sülale Sorgu | `tc` | `https://api.hexnox.pro/sowixapi/sulale.php?tc={TC}` |
| API10 | TC/GSM Sorgu | `tc` veya `gsm` | `https://api.hexnox.pro/sowixapi/tcgsm.php?tc={TC}` veya `https://api.hexnox.pro/sowixapi/gsm.php?gsm={GSM}` |
| API11 | Ehliyet Sorgu | `tc` | `http://api.hexnox.pro/sowixapi/ehlt.php?tc={TC}` |
| API12 | Tapu Sorgu | `tc` | `https://api.hexnox.pro/sowixapi/tapu.php?tc={TC}` |
| API13 | Vesika Sorgu | `tc` | `https://hexnox.pro/sowix/vesika.php?tc={TC}` |
| API14 | Adres Detay Sorgu | `tc` | `https://hexnox.pro/sowixfree/premadres.php?tc={TC}` |

## 🔍 API Kategorileri

### 🆔 Kimlik Bilgileri
![API1](https://img.shields.io/badge/TC_Kimlik_Sorgu-API1-red) 
![API10](https://img.shields.io/badge/TC/GSM_Sorgu-API10-blue)
- TC Kimlik No ile temel bilgiler
- TC veya GSM numarası ile çoklu sorgu

### 🏠 Adres Bilgileri
![API3](https://img.shields.io/badge/Adres_Sorgu-API3-green)
![API4](https://img.shields.io/badge/Hane_Sorgu-API4-orange)
![API14](https://img.shields.io/badge/Detaylı_Adres-API14-blueviolet)
- TC ile adres bilgisi
- Hane bilgileri
- Detaylı adres raporu

### 👨‍👩‍👧‍👦 Aile ve Akrabalık
![API8](https://img.shields.io/badge/Aile_Sorgu-API8-ff69b4)
![API9](https://img.shields.io/badge/Sülale_Sorgu-API9-9C27B0)
- Aile bireyleri bilgisi
- Sülale ve soy bilgisi

### 📚 Eğitim Bilgileri
![API5](https://img.shields.io/badge/Okul_Sorgu-API5-009688)
- Okul ve öğrenci bilgileri

### 💼 İş ve Meslek
![API6](https://img.shields.io/badge/İşyeri_Sorgu-API6-607D8B)
- İşyeri ve çalışma bilgileri

### 📱 İletişim Bilgileri
![API7](https://img.shields.io/badge/GSM_Detay-API7-2196F3)
- GSM numarası detay sorgusu

### 🚗 Ehliyet ve Belge
![API11](https://img.shields.io/badge/Ehliyet_Sorgu-API11-795548)
- Ehliyet ve sürücü belgesi bilgisi

### 🏡 Emlak Bilgileri
![API12](https://img.shields.io/badge/Tapu_Sorgu-API12-3F51B5)
- Tapu ve mülk bilgileri

### 📷 Vesika Bilgileri
![API13](https://img.shields.io/badge/Vesika_Sorgu-API13-FF5722)
- Resmi vesika ve fotoğraf bilgisi

### 🔎 Kişi Arama
![API2](https://img.shields.io/badge/Ad_Soyad_Arama-API2-4CAF50)
- Ad, soyad, il ve ilçe ile kişi arama


## 🛠️ Teknoloji ve Özellikler

### 📱 Platform
![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![Min SDK 21](https://img.shields.io/badge/Min%20SDK-21-orange)
![Target SDK 33](https://img.shields.io/badge/Target%20SDK-33-yellow)

### 💻 Geliştirme
![Java](https://img.shields.io/badge/Java-11-007396?logo=java&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android_Studio-2022.1-3DDC84?logo=android-studio)
![Gradle 7.4](https://img.shields.io/badge/Gradle-7.4-02303A?logo=gradle)

### 🌐 API Entegrasyonu
![API Endpoints](https://img.shields.io/badge/Endpoints-14-blue)
![REST API](https://img.shields.io/badge/REST-API-ff69b4)
![JSON Format](https://img.shields.io/badge/Data-JSON-9cf)

### 📦 Proje Bilgileri
![Repo Size](https://img.shields.io/github/repo-size/Memati8383/Ondex-API-Mobil-Game)
![Last Commit](https://img.shields.io/github/last-commit/Memati8383/Ondex-API-Mobil-Game?color=blue)
![License](https://img.shields.io/badge/License-MIT-green)

### 👥 Topluluk
![GitHub Stars](https://img.shields.io/github/stars/Memati8383/Ondex-API-Mobil-Game?style=social)
![GitHub Forks](https://img.shields.io/github/forks/Memati8383/Ondex-API-Mobil-Game?style=social)
![GitHub Issues](https://img.shields.io/github/issues/Memati8383/Ondex-API-Mobil-Game)
![PRs Welcome](https://img.shields.io/badge/PRs-Welcome-brightgreen)

## 🚀 Özellikler

<div align="center">

![Feature](https://img.shields.io/badge/-14%20Farklı%20API%20Sorgusu-red)
![Feature](https://img.shields.io/badge/-Gelişmiş%20Filtreleme-orange)
![Feature](https://img.shields.io/badge/-4%20Formatta%20Export-green)
![Feature](https://img.shields.io/badge/-Anlık%20Bildirimler-yellow)
![Feature](https://img.shields.io/badge/-Çoklu%20Dil-blue)
![Feature](https://img.shields.io/badge/-Material%20Design-757575)
![Feature](https://img.shields.io/badge/-Hızlı%20Arama-9C27B0)
![Feature](https://img.shields.io/badge/-Kopyalama%20Desteği-009688)

</div>

- **Çoklu API Desteği** - 14 farklı resmi veri sorgulama seçeneği
- **Akıllı Filtreleme** - Sonuçlarda anında arama ve filtreleme
- **Export Özelliği** - TXT, JSON, CSV ve PDF formatlarında kayıt
- **Bildirim Sistemi** - Sorgu sonuçları için anlık push bildirimleri
- **Material UI** - Modern ve kullanıcı dostu arayüz
- **Veri Paylaşımı** - Sonuçları kolayca paylaşma özelliği

## 🌍 Çoklu Dil Desteği 

![Turkish](https://img.shields.io/badge/Türkçe-TR-brightgreen)
![English](https://img.shields.io/badge/English-EN-blue)
![German](https://img.shields.io/badge/Deutsch-DE-yellow)

Uygulama 3 dilde tam destek sunmaktadır:
- 🇹🇷 Türkçe (Varsayılan)
- 🇬🇧 İngilizce 
- 🇩🇪 Almanca

## 📝 Kullanım Kılavuzu

![Step1](https://img.shields.io/badge/1.-Uygulamayı_Aç-4CAF50) ![Step2](https://img.shields.io/badge/2.-Sorgu_Türü_Seç-2196F3)  
![Step3](https://img.shields.io/badge/3.-Bilgileri_Gir-FF9800) ![Step4](https://img.shields.io/badge/4.-Sorgula-F44336)  
![Step5](https://img.shields.io/badge/5.-Sonuçları_İncele-9C27B0) ![Step6](https://img.shields.io/badge/6.-Dışa_Aktar-607D8B)

1. Uygulamayı başlatın
2. Menüden istediğiniz sorgu türünü seçin
3. Gerekli bilgileri ilgili alanlara girin
4. "Sorgula" butonuna basarak işlemi başlatın
5. Gelen sonuçları detaylı şekilde inceleyin
6. İsterseniz sonuçları 4 farklı formatta dışa aktarın

## ⚠️ Güvenlik & Gizlilik

![Security](https://img.shields.io/badge/SSL_Şifreleme-Aktif-brightgreen)
![Privacy](https://img.shields.io/badge/Kişisel_Veriler-Güvende-important)
![Compliance](https://img.shields.io/badge/KVKK_Uyumlu-Yes-blue)

- Tüm API bağlantıları SSL şifreleme ile korunmaktadır
- Kişisel veriler cihazda şifrelenerek saklanır
- KVKK ve GDPR uyumludur

## 🖼️ Ekran Görüntüleri

| Ana Sayfa | Sorgu 1 Ekranı | Sorgu 2 Ekranı |
|-----------|--------------|----------|
| ![Main](Mainn.jpg) | ![Query1](queryy1.jpg) | ![Query2](queryy2.jpg) |


## 👨‍💻 Geliştirici Bilgileri

<div align="center" style="margin: 30px 0;">

[![GitHub Profile](https://img.shields.io/badge/%20-Memati8383-black?logo=github&style=for-the-badge&logoColor=white)](https://github.com/Memati8383)
[![Instagram Profile](https://img.shields.io/badge/%20-@ferit22901-E4405F?logo=instagram&style=for-the-badge&logoColor=white)](https://instagram.com/ferit22901)
[![Email Contact](https://img.shields.io/badge/%20-akdemirferit@gmail.com-D14836?logo=gmail&style=for-the-badge&logoColor=white)](mailto:akdemirferit@gmail.com)

</div>

<p><img align="left" src="https://github-readme-stats.vercel.app/api/top-langs?username=memati8383&show_icons=true&locale=en&layout=compact" alt="memati8383" /></p>

<p>&nbsp;<img align="center" src="https://github-readme-stats.vercel.app/api?username=memati8383&show_icons=true&locale=en" alt="memati8383" /></p>

<p><img align="center" src="https://github-readme-streak-stats.herokuapp.com/?user=memati8383&" alt="memati8383" /></p>

## ⚖️ Lisans Bilgisi

<div style="background: #f5f5f5; padding: 20px; border-radius: 10px; border-left: 5px solid #2ecc71; margin: 20px 0;">

```text
MIT License

Copyright (c) 2025 Ferit Akdemir

Bu lisans, bu yazılımın kopyalarını alan herkese ücretsiz olarak,
yazılım ve ilgili belgeler ("Yazılım") üzerinde sınırsız kullanım hakkı verir.
Yazılımın kopyalarını düzenleme, birleştirme, yayımlama, dağıtma,
alt lisanslama ve/veya satma hakkı da dahil olmak üzere,
Yazılımla ilgili tüm haklar saklıdır.
```
</div>
