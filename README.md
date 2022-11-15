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
## 📝 Download Projectnya
```bash
> git clone https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv8
> cd mafv8
```

## 🔍 Installing the Dependencies
```bash
> npm i
> npm install gify-cli -g
```
If you are using Termux and not yet install Node.js before, install it before entering the "npm i" command:
```bash
> pkg install nodejs
```

## 🆗 Running the BOT
Regular node:
```bash
> npm start
```

PM2:
```bash
> pm2 start index.js
> pm2 monit
```

PM2 with cron job (restart after 5 hours):
```bash
> pm2 start index.js --cron "* */5 * * *"
> pm2 monit
```

After that scan the QR code using your WhatsApp in your phone!

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
* [`open-wa/wa-automate-nodejs`](https://github.com/open-wa/wa-automate-nodejs)
* [`MAF's Assistant V5`](https://github.com/MAULANAAKBARFIRDAUSYA-cmd/mafv5)

# License
Released under the MIT License.
