<div align="center">
<img src="https://images3.alphacoders.com/128/1288825.png" alt="V Angkringan" width="1920" />

# V-Angkringan

V-Angkringan adalah aplikasi mobile yang dikembangkan oleh Kelompok Vincent A5

<p align="center">
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd"><img title="Author" src="https://img.shields.io/badge/Author-Maulana Akbar F.-purple.svg?style=for-the-badge&logo=github" /></a>
</p>
<p align="center">
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8"><img title="RATING" src="https://img.shields.io/badge/BASIC RATING-45%20%2F%20100-green?colorA=%23555555&colorB=%23FC5E00&style=for-the-badge"></a>
</p>
<p align="center">
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8"><img title="RATING" src="https://img.shields.io/badge/PREMIUM RATING-97%20%2F%20100-green?colorA=%23555555&colorB=%23017e40&style=for-the-badge"></a>
</p>
<p align="center">
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8"><img title="Stars" src="https://img.shields.io/github/stars/MAULANAAKBARFIRDAUSYA-cmd/mafv8?color=red&style=flat-square" /></a>
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8/network/members"><img title="Forks" src="https://img.shields.io/github/forks/MAULANAAKBARFIRDAUSYA-cmd/mafv8?color=red&style=flat-square" /></a>
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8/watchers"><img title="Watching" src="https://img.shields.io/github/watchers/MAULANAAKBARFIRDAUSYA-cmd/mafv8?label=watchers&color=blue&style=flat-square" /></a>
  <img src="https://img.shields.io/badge/maintained%3F-yes-green.svg?style=flat" />
  <img src="https://img.shields.io/github/repo-size/MAULANAAKBARFIRDAUSYA-cmd/mafv8" /> <br>
</p>

<p align="center">
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8#requirements">Requirements</a> •
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8#installation">Installation</a> •
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8#%EF%B8%8F-changing-language">Changing Language</a> •
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8#%EF%B8%8F-editing-the-file">Editing the File</a> •
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8#features">Features</a> •
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8#thanks-to">Thanks to</a> •
  <a href="https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8#license">License</a>
</p>

<h4 align="center">
<details>
 <summary>Contact Me!</summary></a></p>
 
[GitHub](https://github.com/MAULANAAKBARFIRDAUSYA-cmd)</a></p>
[My WhatsApp](https://wa.me/+6285236167349)</a></p>
[My WhatsApp BOT](https://wa.me/+6289504990855)</a></p>
[Telegram](https://t.me/maulanaakbarf)</a></p>
</h4>

</div>

Download Project :
* [Direct Link Download](https://github.com/MaulanaAkbarF/AngkringanMbahSingo2/archive/refs/heads/master.zip) (Zip File)

# Minimum Spesification
* Intel Xeon Platinum / AMD Ryzen Threadripper / AMD Epyc
* RAM Minimal DDR4 dengan kapasitas 8TB keatas
* Kartu Grafis RTX 4090 keatas
* Windows 12 Pro
* Penyimpanan SSD NVME Gen 4 dengan kapasitas minimal 256TB keatas

# Requirements
* [Java Software Development Kit](https://www.oracle.com/java/technologies/downloads/#java8)
* [Java Runtime Environment](https://download.cnet.com/Java-Runtime-Environment-JRE/3000-2213_4-10009607.html)
* Android API  (dapat didownload di aplikasi Android Studio)
* IDE dan Emulator

# Android IDE & Emulator

[ PC ]
* [Android Studio](https://developer.android.com/studio)
* External Emulator/Android Virtual Device (AVD) di Android Studio Emulator/Ponsel Kamu sebagai Emulator

[ Android ]
* Belum Tersedia

# Installation
## 📝 Download Project dan Ekstrak
```bash
> Download filenya
> Cari filenya dengan nama : AngkringanMbahSingo2-master.zip
> Klik kanan dan pilih Exctract All...
> Klik Exctract untuk memulai. Sekarang filenya telah berhasil diekstrak.
```

## 🔍 Import ke dalam Android Studio
```bash
> Buka Android Studio
> Klik pada opsi Open / pergi ke File > Open
> Cari dimana lokasi file project yang telah diekstrak tadi
> Klik pada gambar nama projectnya (buka pada folder nama projectnya)
> Klik Trust Project dan pilih New Window
> Tunggu hingga proses Build Gradle selesai (dan lakukan update jika ada perubahan) dan selesai
```

## 🆗 Jalankan Programmnya
Melalui Android Studio AVD:
```bash
> Pergo ke Tools > Device Manager
> Jika belum mempunyai AVD, klik Create Device
> Pilih tipe Ponsel yang akan digunakan, lalu klik Next
> Tentukan API / Versi Android pada AVD nantinya, kamu harus mengunduhnya sesuai kebutuhan (tidak semua) untuk bisa menjalankan.
> Tulis Nama AVD, dan jika dirasa sesuai bisa klik Finish.
> Tekan simbol Run pada Action dan Aplikasi berhasil dijalankan.
```

Melalui External Emulator:
```bash
> pm2 start index.js
> pm2 monit
```

Melalui Device Kamu:
```bash
> pm2 start index.js --cron "* */5 * * *"
> pm2 monit
```

## ✍️ Editing the file
Edit the required value in [config.json](https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8/blob/main/config.json).
```json
{
    "ownerBot": "62852xxxxxxxx@c.us", 
    "prefix": "$",
    "uaOverride": "WhatsApp/2.2037.6 Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36",
    "token": "api-key",
    "nao": "api-key",
    "vhtear": "api-key",
    "melodic": "administrator"
}
```

`ownerBot`: your WhatsApp number.  
`prefix`: bot's prefix.  
`uaOverride`: your user agent.  
`token`: API token. You can get it [here](https://api.i-tech.id) by creating an account. After that, set your server/host static IP in [here](https://api.i-tech.id/settings/profile).  
`nao`: SauceNAO API token. You can get it [here](https://saucenao.com/user.php) by creating an account.  
`vhtear`: VHTear API token. You can get it [here](https://api.vhtear.com/) by purchasing his API key.  
`melodic`: MelodicXT API token. You can use `administrator` key.  

## 🗣️ Changing language
If you want to change the language, replace all `ind` function to `eng`.   
Example:
```js
ind.wrongFormat()
```
To:
```js
eng.wrongFormat()
```

# Features
If you want to unlock premium commands, please contact me~

|     Leveling     |  Availability  |
| :--------------: | :------------: |
| Leveling         |       ✔️       |
| Set background   |       ✔️       |
| Set status color |      Soon      |
| Set level color  |      Soon      |
| Set XP color     |      Soon      |
| Set bar color    |      Soon      |

# Thanks to
* [`MAF's Assistant V5`](https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv5)

# License
Released under the MIT License.
