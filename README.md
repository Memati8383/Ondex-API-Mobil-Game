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

![App Icon](app-icon.png) <!-- Add your app icon if available -->

Bu uygulama, çeşitli API'ler üzerinden TC kimlik numarası, ad-soyad, adres ve diğer kişisel bilgilerin sorgulanmasını sağlayan bir Android uygulamasıdır.

## Özellikler

- **11 Farklı API Sorgulama Seçeneği**
  - TC kimlik no ile sorgu
  - Ad-Soyad-İl-İlçe ile sorgu
  - Adres sorgulama
  - Hane sorgulama
  - Apartman sorgulama
  - Sokak sorgulama
  - Mahalle sorgulama
  - Aile sorgusu
  - Sülale sorgusu
  - TC veya GSM ile sorgu
  - İş yeri sorgusu

- **Sonuçları Filtreleme**
- **Sonuçları Dışa Aktarma (TXT, JSON, CSV, PDF)**
- **Bildirim Sistemi**
- **Kullanıcı Dostu Arayüz**

## Kurulum

1. Bu projeyi klonlayın:
   ```bash
   git clone https://github.com/Memati8383/ondex-api-android.git
   ```
2. Android Studio'da projeyi açın
3. Gerekli bağımlılıklar otomatik olarak yüklenecektir
4. Uygulamayı bir Android cihaz veya emülatörde çalıştırın

## Kullanım

1. Ana menüden sorgulamak istediğiniz API'yi seçin
2. Gerekli bilgileri girin (TC, ad-soyad, il-ilçe vb.)
3. "Sorgula" butonuna basın
4. Gelen sonuçları inceleyin
5. İsterseniz sonuçları filtreleyebilir veya dışa aktarabilirsiniz

## Dışa Aktarma Seçenekleri

Uygulama, sorgu sonuçlarını 4 farklı formatta dışa aktarabilir:

1. **TXT**: Okunabilir metin formatı
2. **JSON**: Programatik kullanım için JSON formatı
3. **CSV**: Excel gibi programlarda açılabilir CSV formatı
4. **PDF**: Paylaşım için PDF belgesi

## Ekran Görüntüleri

<!-- Add screenshots of your app here -->
![Main Screen](screenshots/main.png)
![Query Screen](screenshots/query.png)
![Results Screen](screenshots/results.png)

## Gereksinimler

- Android 5.0 (Lollipop) veya üzeri
- İnternet bağlantısı (API'lere erişim için)

## Geliştirici

- **Ferit Akdemir**
- Email: akdemirferit@gmail.com
- GitHub: [Memati8383](https://github.com/Memati8383)
- Instagram: [ferit22901](https://instagram.com/ferit22901)

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır - detaylar için [LICENSE](LICENSE) dosyasına bakınız.

## Katkıda Bulunma

Katkılarınız memnuniyetle karşılanır. Lütfen önce bir konu açarak neyi değiştirmek istediğinizi tartışın.

1. Forklayın
2. Özellik dalınızı oluşturun (`git checkout -b feature/AmazingFeature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add some AmazingFeature'`)
4. Dalınıza push yapın (`git push origin feature/AmazingFeature`)
5. Pull Request açın
