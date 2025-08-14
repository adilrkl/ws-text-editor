# Adım 1: Build Ortamı - Kodu derleyip JAR dosyasını oluştur
FROM eclipse-temurin:17-jdk-jammy as builder

WORKDIR /app

# Maven wrapper'ı kopyala
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Bağımlılıkları indir
RUN ./mvnw dependency:go-offline

# Uygulama kaynak kodunu kopyala
COPY src ./src

# Uygulamayı paketle
RUN ./mvnw clean -DskipTests package


# Adım 2: Çalıştırma Ortamı - Sadece JRE ve JAR dosyasını içerir
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Build ortamından sadece JAR dosyasını kopyala
COPY --from=builder /app/target/*.jar app.jar

# Uygulamanın çalışacağı port
EXPOSE 9090

# Uygulamayı başlat
CMD ["java", "-jar", "app.jar"]