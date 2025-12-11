<div align="center">
<hr>


# 💧HIPO App — Hydroponic Intelligent Operations🪴
</div>
<div align="center">
  <img src="./LOGO/ic_logo.png" width="260" alt="HIPO Logo">
  
  <br>
  <br>


[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge\&logo=android\&logoColor=white)]()
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge\&logo=firebase\&logoColor=black)]()
[![ESP32](https://img.shields.io/badge/ESP32-2D8CFF?style=for-the-badge\&logo=esp32\&logoColor=white)]()
[![IoT Smart Farming](https://img.shields.io/badge/IoT%20Smart%20Farming-00AEEF?style=for-the-badge\&logo=sparkfun\&logoColor=white)]()
[![Hydroponics](https://img.shields.io/badge/Hydroponics-0FA958?style=for-the-badge\&logo=leaflet\&logoColor=white)]()
</div><br>
<div align="center">
<hr>
</div>

## 💡 Overview

**HIPO App** is an IoT-based hydroponic monitoring and control system built using **Android (Java)**, **ESP32**, and **Firebase Realtime Database**. It enables real‑time sensor monitoring, automated nutrient and pH adjustment, pump control, and fully synchronized cloud communication.

This project is suitable for:

* Final Projects
* Campus EXPO Demonstrations
* IoT Smart Farming Research
* Cloud‑based IoT Systems

---

## 🌱 About HIPO App
<img src="./LOGO/POSTER.png" width="100%" alt="HIPO Logo">
  

HIPO App allows users to monitor environmental conditions (temperature, humidity, pH, TDS, water level) and control pumps/fans directly from their smartphone. Paired with ESP32 firmware, it provides a fully automated hydroponic management solution.

### 🔗 Mobile App Demo Video  

[Watch on YouTube](https://www.youtube.com/shorts/m2Hy2t82tbM)

---

## ✨ Features

### 📡 Real‑Time Monitoring

* Temperature
* Humidity
* pH Level
* TDS/Nutrient PPM
* Water Level (Ultrasonic)

### ⚙️ Intelligent Control Modes

* **Manual Mode** – User manually toggles relays.
* **Automatic Mode** – System adjusts parameters autonomously.
* **Hybrid Mode** – Combination of manual & automatic control.

### 🔧 5‑Channel Relay Control

* pH Up Pump
* pH Down Pump
* Nutrient Pump
* Water Pump
* Exhaust Fan / Aerator

### 📜 Relay Logs & Activity Tracking

* Timestamp logging
* ON/OFF history
* Mode-based action tracking

### 🔔 Smart Notifications

* Extreme pH detection
* Low nutrient alert
* Empty water tank alert
* Auto‑control activity notifications

### 🧭 Modern Dashboard UI

* Clean & responsive sensor cards
* Control panel with visual indicators

---

## 🧩 System Architecture

```
+-----------------------+
|      HIPO APP         |
|    (Android Java)     |
+-----------+-----------+
            |
            | Firebase Realtime DB
            |
+-----------v-----------+
|        ESP32          |
|   IoT Hydroponic MCU  |
+-----------+-----------+
            |
            +--> Sensors: pH, TDS, DHT, Ultrasonic
            |
            +--> Relays: 5‑channel
            |
            +--> Pumps & Actuators
```

---

## 🛠 Tech Stack

| Layer       | Tools                                        |
| ----------- | -------------------------------------------- |
| Android App | Java, XML, Android Studio                    |
| IoT Device  | ESP32, Arduino/PlatformIO                    |
| Cloud       | Firebase Realtime Database                   |
| Networking  | WiFi 2.4 GHz                                 |
| Tools       | Firebase Console, Serial Monitor, Multimeter |

---

## 📂 Project Structure

```
HIPO-App/
├── app/
│   ├── java/com/hipo/
│   │   ├── auth/
│   │   ├── dashboard/
│   │   ├── monitoring/
│   │   ├── control/
│   │   ├── logs/
│   │   └── utils/
│   └── res/
│       ├── layout/
│       ├── values/
│       └── drawable/
├── assets/
│   ├── logo_hipo.png
│   └── screen_*.png
└── iot-code/
    └── esp32_firmware.ino
```

---

## 🔧 Hardware Requirements

* ESP32 DevKit V1
* pH Meter module
* TDS Sensor
* DHT11/DHT22 Sensor
* HC‑SR04 Ultrasonic Sensor
* 5‑Channel Relay Board
* pH Up/Down Pumps
* Nutrient Pump
* Water Pump
* Fan / Aerator

---

## 🔌 Wiring Diagrams

### Sensors → ESP32

| Sensor             | ESP32 Pin |
| ------------------ | --------: |
| pH Meter           |   GPIO 34 |
| TDS Sensor         |   GPIO 35 |
| DHT11/DHT22        |    GPIO 4 |
| Ultrasonic Echo    |   GPIO 12 |
| Ultrasonic Trigger |   GPIO 14 |

### Relays → ESP32

| Relay   | Function      | ESP32 Pin |
| ------- | ------------- | --------: |
| Relay 1 | pH Up Pump    |   GPIO 15 |
| Relay 2 | pH Down Pump  |    GPIO 2 |
| Relay 3 | Nutrient Pump |    GPIO 5 |
| Relay 4 | Water Pump    |   GPIO 18 |
| Relay 5 | Fan / Aerator |   GPIO 19 |

> ⚠️ Use a separate power supply for pumps/relays. Connect **all grounds (GND)** together.

---

## 🌐 Firebase Structure 

```json
{
  "hipo": {
    "sensors": {
      "temperature": 28.5,
      "humidity": 70,
      "ph": 6.1,
      "tds": 850,
      "waterLevel": 65
    },
    "relays": {
      "phUp": false,
      "phDown": false,
      "nutrisi": false,
      "water": false,
      "fan": false
    },
    "mode": "AUTO"
  }
}
```

---

## 📅 Getting Started

### Requirements

* Android Studio (latest recommended)
* Java JDK 8+
* Android SDK
* ESP32 Board

### Installation

1. Clone the repository:

```bash
git clone https://github.com/yourrepo/HIPO-App.git
```

2. Open in Android Studio and sync Gradle.
3. Add `google-services.json` to **/app** folder.
4. Upload ESP32 firmware through Arduino IDE or PlatformIO.

---

## 🔄 How to Use the App

1. Open the HIPO App.
2. View real‑time sensor data on the dashboard.
3. Choose **Manual**, **Auto**, or **Hybrid** control mode.
4. Toggle relay switches in Manual mode.
5. Let the system maintain pH, nutrients, and water level automatically in Auto mode.
6. View relay history in the Logs section.
7. Check notifications for warnings or system actions.

---

## 🧪 Testing & Validation

* Sensor update interval: **2–3 seconds**
* Relay response time: **< 80 ms**
* Auto pH control range: **4.5 – 7.5**
* Firebase stress test: **200 updates/min — PASS**
* WiFi reconnect time: **≤ 4 seconds**

---

## 🐞 Troubleshooting

| Issue                | Cause              | Solution                    |
| -------------------- | ------------------ | --------------------------- |
| Sensor reading NaN   | Incorrect wiring   | Recheck VCC & GND           |
| Relay not switching  | Insufficient power | Use external PSU            |
| Slow data update     | Weak WiFi signal   | Improve 2.4GHz connectivity |
| ESP32 resets/crashes | pH module noise    | Add isolator/filter         |

---

## 👨‍💻 Project Members

* Jansen — Lead Developer
* Cariven Tanova
* Dariel
* Marciano

### 🏫 Supervisor

* Ade Maulana, S.Kom., M.TI

---

## 📆 License

This project is for educational and research purposes only.

---

<div align="center">
<b>Made with ❤️ for Final Project (IoT Programming & Advanced Mobile Application Programming) & EXPO</b>
</div>
