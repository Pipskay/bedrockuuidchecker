# BedrockUUIDChecker Plugin

Plugin Spigot/Paper untuk mendeteksi UUID bug pada player Bedrock.

## Cara Kerja
- Mendeteksi player Bedrock via Floodgate API
- Mengecek apakah UUID diawali dengan `00000000-0000-0000-`
- Jika tidak valid → player di-kick dengan pesan instruksi

---

## Cara Compile

### Opsi 1: Lokal (Windows)
1. Install JDK 17+ dari https://adoptium.net
2. Install Maven dari https://maven.apache.org
3. Buka CMD di folder ini, jalankan:
   ```
   mvn package
   ```
4. File JAR ada di: `target/BedrockUUIDChecker.jar`

### Opsi 2: GitHub Codespaces (Online, GRATIS)
1. Buat akun GitHub di https://github.com
2. Buat repository baru
3. Upload semua file ini ke repository tersebut
4. Klik tombol hijau `<> Code` → pilih `Codespaces` → `Create codespace`
5. Setelah terbuka, jalankan di terminal:
   ```
   mvn package
   ```
6. Download file `target/BedrockUUIDChecker.jar`

### Opsi 3: Gitpod (Online, GRATIS)
1. Upload project ke GitHub
2. Buka: https://gitpod.io/#https://github.com/USERNAME/REPO_NAME
3. Jalankan `mvn package` di terminal
4. Download JAR dari folder `target/`

---

## Cara Install ke Server
1. Copy `BedrockUUIDChecker.jar` ke folder `plugins/` server
2. Pastikan Floodgate & Geyser sudah terinstall
3. Restart server
4. Cek console: `[BedrockUUIDChecker] BedrockUUIDChecker v1.0.0 aktif!`

## Requirement
- Spigot / Paper 1.20+
- Java 17+
- Floodgate plugin
- Geyser-Spigot plugin
