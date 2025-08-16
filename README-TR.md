# 🚀 Code Share - Gerçek Zamanlı İşbirlikçi Kod Editörü

Spring Boot ve Y.js ile geliştirilmiş modern, gerçek zamanlı işbirlikçi kod editörü. Birden fazla geliştiricinin aynı anda kod düzenlemesine, canlı imleç takibine ve anında senkronizasyona olanak tanır.

## ✨ Özellikler

- **🔄 Gerçek Zamanlı İşbirliği**: Birden fazla kullanıcı aynı anda aynı belgeyi düzenleyebilir
- **👥 Canlı İmleç Takibi**: Diğer kullanıcıların imleçlerini ve seçimlerini gerçek zamanlı görün
- **🎨 Sözdizimi Vurgulama**: Birden fazla programlama dili desteği (JavaScript, Java, Python, HTML, CSS, JSON, Markdown)
- **📱 Mobil Uyumlu**: Hem masaüstü hem de mobil cihazlar için optimize edilmiş
- **🔐 Kullanıcı Kimlik Doğrulaması**: Güvenli kullanıcı kaydı ve giriş sistemi
- **📚 Belge Yönetimi**: Birden fazla kod belgesi oluşturun ve yönetin
- **⚡ Anında Senkronizasyon**: Değişiklikler CRDT (Çakışmasız Çoğaltılmış Veri Türleri) kullanılarak anında senkronize edilir
- **🎯 Modern Arayüz**: Bootstrap 5 ile oluşturulmuş temiz, sezgisel arayüz
- **📋 Panoya Kopyalama**: Kolay kod paylaşım işlevi
- **🌐 Çapraz Platform**: Tüm modern tarayıcılarda çalışır

## 🛠️ Teknoloji Yığını

### Arka Uç
- **Spring Boot 3** - Uygulama çerçevesi
- **Spring Security** - Kimlik doğrulama ve yetkilendirme
- **Spring WebSocket** - Gerçek zamanlı iletişim
- **JPA/Hibernate** - Veritabanı ORM
- **PostgreSQL** - Üretim veritabanı
- **Maven** - Bağımlılık yönetimi

### Ön Uç
- **Y.js** - Gerçek zamanlı işbirliği için CRDT
- **Monaco Editor** - Kod editörü (VS Code editörü)
- **Bootstrap 5** - Duyarlı UI çerçevesi
- **Thymeleaf** - Sunucu tarafı şablonlama
- **WebSocket** - Gerçek zamanlı iletişim

### Altyapı
- **Docker** - Konteynerleştirme
- **Docker Compose** - Çoklu konteyner orkestrasyon

## 🚀 Hızlı Başlangıç

### Ön Koşullar
- Java 17 veya üzeri
- Maven 3.6+
- Docker & Docker Compose (isteğe bağlı)

### Seçenek 1: Docker ile Çalıştırma (Önerilen)

1. **Depoyu klonlayın**
```bash
git clone https://github.com/yourusername/wsTextEditor.git
cd wsTextEditor
```

2. **Docker Compose ile başlatın**
```bash
docker-compose up -d
```

3. **Uygulamaya erişin**
- Tarayıcınızı açın ve şu adrese gidin: `http://localhost:8080`

### Seçenek 2: Yerel Olarak Çalıştırma

1. **Depoyu klonlayın**
```bash
git clone https://github.com/yourusername/wsTextEditor.git
cd wsTextEditor
```

2. **Projeyi derleyin**
```bash
./mvnw clean package
```

3. **Uygulamayı çalıştırın**
```bash
./mvnw spring-boot:run
```

4. **Y.js WebSocket Sunucusunu başlatın**
```bash
cd y-websocket
npm install
npm start
```

5. **Uygulamaya erişin**
- Tarayıcınızı açın ve şu adrese gidin: `http://localhost:8080`

## 📖 Nasıl Kullanılır

### 1. **Kayıt Ol/Giriş Yap**
- Yeni bir hesap oluşturun veya mevcut kimlik bilgilerinizle giriş yapın
- Başarılı kimlik doğrulamadan sonra panoya gidin

### 2. **Kod Belgesi Oluşturun**
- Panoda "✨ Yeni Kod Paylaşımı Oluştur" düğmesine tıklayın
- İşbirlikçi editöre yönlendirileceksiniz

### 3. **İşbirliği Yapmaya Başlayın**
- Belge URL'sini ekip üyeleriyle paylaşın
- Gerçek zamanlı değişiklikleri ve imleç konumlarını görün
- Açılır menüden programlama dilini seçin
- "📋 Kodu Kopyala" düğmesini kullanarak kodu kopyalayın

### 4. **Gerçek Zamanlı Özellikler**
- **Canlı Düzenleme**: Yazın ve değişiklikleri anında görün
- **İmleç Takibi**: Diğer kullanıcıların imleç konumlarını görün
- **Dil Desteği**: Farklı programlama dilleri arasında geçiş yapın
- **Otomatik Kaydetme**: Değişiklikler otomatik olarak kaydedilir

## 🏗️ Proje Yapısı

```
wsTextEditor/
├── src/main/java/com/example/wsTextEditor/
│   ├── config/          # Yapılandırma sınıfları
│   ├── controller/      # REST ve WebSocket denetleyicileri
│   ├── crdt/           # CRDT uygulaması
│   ├── model/          # JPA varlıkları
│   ├── repository/     # Veri depoları
│   └── WsTextEditorApplication.java
├── src/main/resources/
│   ├── static/css/     # CSS dosyaları
│   ├── templates/      # Thymeleaf şablonları
│   └── application.properties
├── y-websocket/        # Y.js WebSocket sunucusu
├── docker-compose.yml  # Docker yapılandırması
├── Dockerfile         # Uygulama konteyneri
└── pom.xml           # Maven bağımlılıkları
```

## ⚙️ Yapılandırma

### Veritabanı Yapılandırması
Uygulama veritabanı olarak PostgreSQL kullanır. Yapılandırma ortam değişkenleri ile yapılır:

```properties
# PostgreSQL (ortam değişkenleri ile)
spring.datasource.url=${JDBC_DATABASE_URL:jdbc:postgresql://localhost:5432/postgres}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

**Ortam Değişkenleri:**
- `SPRING_DATASOURCE_USERNAME` - Veritabanı kullanıcı adı
- `SPRING_DATASOURCE_PASSWORD` - Veritabanı şifresi
- `SPRING_DATASOURCE_HOST` - Veritabanı sunucusu (varsayılan: localhost)
- `SPRING_DATASOURCE_PORT` - Veritabanı portu (varsayılan: 5432)
- `SPRING_DATASOURCE_DB` - Veritabanı adı (varsayılan: postgres)

### WebSocket Yapılandırması
Y.js WebSocket sunucusu varsayılan olarak `1234` portunda çalışır. Gerekirse şablonlardaki URL'yi güncelleyin.

## 🐳 Docker Yapılandırması

Proje aşağıdaki servislerle Docker desteği içerir:

- **Web Uygulaması**: Spring Boot uygulaması (Port 8080)
- **Y.js WebSocket**: Gerçek zamanlı senkronizasyon sunucusu (Port 1234)
- **Veritabanı**: PostgreSQL (Port 5432)

## 🤝 Katkıda Bulunma

1. Depoyu fork edin
2. Özellik dalı oluşturun: `git checkout -b feature/harika-ozellik`
3. Değişikliklerinizi commit edin: `git commit -m 'Harika özellik ekle'`
4. Dala push edin: `git push origin feature/harika-ozellik`
5. Pull Request açın

## 📝 API Uç Noktaları

| Metod | Uç Nokta | Açıklama |
|-------|----------|----------|
| GET | `/` | Ana sayfa (giriş sayfasına yönlendirir) |
| GET | `/login` | Giriş sayfası |
| POST | `/login` | Giriş işlemi |
| GET | `/register` | Kayıt sayfası |
| POST | `/register` | Kayıt işlemi |
| GET | `/dashboard` | Kullanıcı panosu |
| POST | `/documents/create` | Yeni belge oluştur |
| GET | `/editor/{id}` | İşbirlikçi editör |

## 🔧 Sorun Giderme

### Yaygın Sorunlar

1. **WebSocket Bağlantısı Başarısız**
   - Y.js WebSocket sunucusunun 1234 portunda çalıştığından emin olun
   - Güvenlik duvarı ayarlarını kontrol edin

2. **Veritabanı Bağlantı Hatası**
   - PostgreSQL sunucusunun çalıştığından emin olun
   - Veritabanı kimlik bilgileri için ortam değişkenlerini kontrol edin
   - Veritabanının mevcut olduğundan ve kullanıcının uygun izinlere sahip olduğundan emin olun

3. **Mobil Zoom Sorunları**
   - Uygulama istenmeyen yakınlaştırmayı önlemek için mobil optimizasyonları içerir
   - En iyi deneyim için en son tarayıcı sürümlerini kullanın

## 📄 Lisans

Bu proje MIT Lisansı altında lisanslanmıştır - detaylar için [LICENSE](LICENSE) dosyasına bakın.

## 🙏 Teşekkürler

- [Y.js](https://github.com/yjs/yjs) - Gerçek zamanlı işbirliği çerçevesi
- [Monaco Editor](https://microsoft.github.io/monaco-editor/) - Kod editörü
- [Spring Boot](https://spring.io/projects/spring-boot) - Uygulama çerçevesi
- [Bootstrap](https://getbootstrap.com/) - UI çerçevesi

## 📞 Destek

Herhangi bir sorunuz veya sorununuz varsa, lütfen:
1. Mevcut [Issues](https://github.com/yourusername/wsTextEditor/issues) kontrol edin
2. Detaylı açıklama ile yeni bir issue oluşturun
3. Proje sahipleriyle iletişime geçin

---

⭐ **Bu depoyu faydalı buluyorsanız yıldızlayın!** 