# ONDEX API Sorgulama Uygulaması

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
