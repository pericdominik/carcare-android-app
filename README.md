# CarCare

CarCare je Android mobilna aplikacija za praćenje održavanja vozila. Aplikacija korisniku 
omogućuje dodavanje vozila, vođenje servisne povijesti, praćenje troškova servisa, dodavanje 
podsjetnika, provjeru vremenskih uvjeta i otvaranje servisnih lokacija.

## Funkcionalnosti

- Dodavanje, pregled i brisanje vozila
- Dodavanje, pregled i brisanje servisnih zapisa
- Povezivanje servisa s odabranim vozilom
- Dodavanje podsjetnika s datumom i vremenom
- Označavanje podsjetnika kao završenog
- Zakazane lokalne notifikacije za podsjetnike
- Prikaz osnovne statistike na početnom zaslonu
- Dohvat vremenskih uvjeta pomoću OpenWeather API-ja
- Otvaranje servisnih lokacija u Google Maps aplikaciji
- Jednostavna animacija pomoću akcelerometra
- Spremanje podataka u Firebase Firestore bazu

## Korištene tehnologije

- Kotlin
- Android Studio
- Jetpack Compose
- Navigation Compose
- Firebase Firestore
- Retrofit
- OpenWeather API
- WorkManager
- NotificationCompat
- Akcelerometar
- Google Maps Intent
- Git

## Struktura projekta

```text
model          - podatkovni modeli aplikacije
view           - zasloni aplikacije
viewmodel      - poslovna logika i rad s podacima
navigation     - navigacija između zaslona
components     - zajedničke UI komponente
network        - Retrofit i OpenWeather API klase
notification   - WorkManager notifikacije
```

## Firebase kolekcije

Aplikacija koristi Firebase Firestore bazu podataka s kolekcijama:

```text
vehicles
serviceRecords
reminders
```

## Pokretanje projekta

1. Klonirati repozitorij.
2. Otvoriti projekt u Android Studio razvojnom okruženju.
3. Pokrenuti Gradle Sync.
4. Provjeriti da se datoteka `google-services.json` nalazi u `app` direktoriju.
5. U `local.properties` dodati OpenWeather API ključ:

```text
OPENWEATHER_API_KEY=vas_api_kljuc
```

6. Pokrenuti aplikaciju na emulatoru ili Android uređaju.

## Napomena

Datoteka `local.properties` ne nalazi se u repozitoriju jer sadrži lokalne postavke i API ključ. 
Za ispravan rad vremenske prognoze potrebno je dodati vlastiti OpenWeather API ključ.

## Autor

Dominik Perić  
Razvoj mobilnih aplikacija  
FERIT, 2026