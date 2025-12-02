/*
  =========================================================
  Project : IoT Firebase Hydroponic Control (5 Relay + Sensor)
  Mode    : Control 
  Feature : Sensor Upload + Relay + Relay Logs
  Author  : Jansen (Modified)
  Date    : Final Version
  =========================================================
*/

#include "secrets.h"
#include <Firebase.h>
#include "DHT.h"

// ✅ Tambahan LCD
#include <Wire.h>
#include <LiquidCrystal_I2C.h>

/* ------------------------------- PIN CONFIG ------------------------------- */
#define phUp    21
#define phDown  22
#define AMix    19
#define BMix    18
#define Water   23

#define DHTPIN  14
#define DHTTYPE DHT11

#define phPin   34
#define tdsPin  35

#define TRIGPIN 26
#define ECHOPIN 25

DHT dht(DHTPIN, DHTTYPE);
Firebase fb(REFERENCE_URL);

// LCD 16x2 I2C → alamat 0x27
LiquidCrystal_I2C lcd(0x27, 16, 2);

/* ------------------------------- VARIABLES ------------------------------- */
float phValue, tdsValue, temperature, humidity, waterLevel;

// Relay last-state
bool lastPhUp = false, lastPhDown = false;
bool lastAMix = false, lastBMix = false;
bool lastWater = false;

// INDEX untuk grafik Android
int phIndex = 0;
int tdsIndex = 0;

float readPH();
float readTDS();
float readWaterLevel();

void addRelayLog(String message);
String generateKey();
String getTimeString();

/* ------------------------------- TIMER ------------------------------- */
unsigned long lastSensorSend = 0;
const unsigned long sensorInterval = 1000;  // 1 detik

/* ------------------------------- SETUP ------------------------------- */
void setup() {
  Serial.begin(115200);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println("\n✅ WiFi Connected!");

  configTime(7 * 3600, 0, "pool.ntp.org");
  Serial.println("✅ Time Synced!");

  randomSeed(analogRead(0));

  pinMode(phUp, OUTPUT);
  pinMode(phDown, OUTPUT);
  pinMode(AMix, OUTPUT);
  pinMode(BMix, OUTPUT);
  pinMode(Water, OUTPUT);

  digitalWrite(phUp, HIGH);
  digitalWrite(phDown, HIGH);
  digitalWrite(AMix, HIGH);
  digitalWrite(BMix, HIGH);
  digitalWrite(Water, HIGH);

  dht.begin();

  pinMode(phPin, INPUT);
  pinMode(tdsPin, INPUT);
  pinMode(TRIGPIN, OUTPUT);
  pinMode(ECHOPIN, INPUT);

  // I2C custom pin → SDA=32, SCL=33
  Wire.begin(32, 33);

  lcd.init();
  lcd.backlight();
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Hydroponic IOT");
  lcd.setCursor(0, 1);
  lcd.print("System Ready");
  delay(1500);
  lcd.clear();
}

/* ------------------------------- LOOP ------------------------------- */
void loop() {

  /* =================== BACA MANUAL CONTROL =================== */
  bool mPhUp, mPhDown, mAMix, mBMix, mWater;

  fb.getBool("relayControl/phUp", mPhUp);
  fb.getBool("relayControl/phDown", mPhDown);
  fb.getBool("relayControl/AMix", mAMix);
  fb.getBool("relayControl/BMix", mBMix);
  fb.getBool("relayControl/Water", mWater);

  // NOTE: controlSettings tetap ada di Firebase (tidak dihapus)

  /* =================== BACA SENSOR 1 DETIK =================== */
  if (millis() - lastSensorSend >= sensorInterval) {
    lastSensorSend = millis();

    humidity = dht.readHumidity();
    temperature = dht.readTemperature();
    phValue = readPH();
    tdsValue = readTDS();
    waterLevel = readWaterLevel();

    Serial.printf("pH: %.2f | TDS: %.2f | WaterLvl: %.2f | Temp: %.2f | Hum: %.2f\n",
                  phValue, tdsValue, waterLevel, temperature, humidity);

    // LCD
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("pH:");
    lcd.print(phValue, 1);
    lcd.print(" TDS:");
    lcd.print(tdsValue, 0);

    lcd.setCursor(0, 1);
    lcd.print("Air:");
    lcd.print(waterLevel, 0);
    lcd.print(" Suhu:");
    if (!isnan(temperature))
        lcd.print(temperature, 0);
    else
        lcd.print("--");

    // Send Firebase
    fb.setFloat("iotData/temperature", temperature);
    fb.setFloat("iotData/humidity", humidity);

    fb.setFloat("iotData/ph", phValue);
    fb.setInt("iotData/phIndex", phIndex++);

    fb.setFloat("iotData/tds", tdsValue);
    fb.setInt("iotData/tdsIndex", tdsIndex++);

    fb.setFloat("iotData/waterLevel", waterLevel);
  }

  /* =================== RELAY FINAL STATE (MANUAL) =================== */
  bool fPhUp   = mPhUp;
  bool fPhDown = mPhDown;
  bool fAMix   = mAMix;
  bool fBMix   = mBMix;
  bool fWater  = mWater;

  /* =================== LOG PERUBAHAN STATUS =================== */
  if (fPhUp != lastPhUp) {
    addRelayLog(String("pH Up ") + (fPhUp ? "MANUAL ON" : "MANUAL OFF"));
    lastPhUp = fPhUp;
  }

  if (fPhDown != lastPhDown) {
    addRelayLog(String("pH Down ") + (fPhDown ? "MANUAL ON" : "MANUAL OFF"));
    lastPhDown = fPhDown;
  }

  if (fAMix != lastAMix) {
    addRelayLog(String("AB Mix - A ") + (fAMix ? "MANUAL ON" : "MANUAL OFF"));
    lastAMix = fAMix;
  }

  if (fBMix != lastBMix) {
    addRelayLog(String("AB Mix - B ") + (fBMix ? "MANUAL ON" : "MANUAL OFF"));
    lastBMix = fBMix;
  }

  if (fWater != lastWater) {
    addRelayLog(String("Pump ") + (fWater ? "MANUAL ON" : "MANUAL OFF"));
    lastWater = fWater;
  }

  /* =================== EKSEKUSI RELAY =================== */
  digitalWrite(phUp,    fPhUp   ? LOW : HIGH);
  digitalWrite(phDown,  fPhDown ? LOW : HIGH);
  digitalWrite(AMix,    fAMix   ? LOW : HIGH);
  digitalWrite(BMix,    fBMix   ? LOW : HIGH);
  digitalWrite(Water,   fWater  ? LOW : HIGH);
}

/* ------------------------------- PH SENSOR ------------------------------- */
float readPH() {
  int v = analogRead(phPin);
  float voltage = v * (3.3 / 4095.0);
  return 7 + ((2.5 - voltage) / 0.18);
}

/* ------------------------------- TDS SENSOR ------------------------------ */
float readTDS() { int sensorValue = analogRead(tdsPin); float voltage = sensorValue * (3.3 / 4095.0); float tds = (133.42 * voltage * voltage * voltage - 255.86 * voltage * voltage + 857.39 * voltage) * 0.5; return tds; }

/* ------------------------------- WATER LEVEL ----------------------------- */
float readWaterLevel() {
  digitalWrite(TRIGPIN, LOW);
  delayMicroseconds(5);

  digitalWrite(TRIGPIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGPIN, LOW);

  long duration = pulseIn(ECHOPIN, HIGH, 30000);
  if (duration == 0) return 0;

  float distance = duration * 0.0343 / 2;
  return distance;
}

/* ------------------------------- KEY GENERATOR ------------------------------- */
String generateKey() {
  String key = "-";
  String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  for (int i = 0; i < 18; i++) {
    key += chars[random(chars.length())];
  }
  return key;
}

/* ------------------------------- ADD LOG (FORMAT ANDROID) ------------------------------- */
void addRelayLog(String message) {
  String timeStr = getTimeString();
  String entry = message + " - " + timeStr;
  String key = generateKey();

  // 🔥 FORMAT SAMA DENGAN ANDROID:
  // relayLogs/<key>/text : "xxx - 12:30:05"
  fb.setString("relayLogs/" + key + "/text", entry);

  Serial.println("✅ Log: " + entry);
}

String getTimeString() {
  struct tm timeinfo;
  if (!getLocalTime(&timeinfo)) return "00:00:00";

  char buffer[16];
  strftime(buffer, sizeof(buffer), "%H:%M:%S", &timeinfo);
  return String(buffer);
}
