# PAP22z_z08
## Design proposal aplikacji webowej 

#### Opiekun zespołu: dr inż. Mateusz Modrzejewski
#### Skład zespołu: Małgorzata Kozłowska, Kacper Murygin, Albert Torzewski

### Rodzaj aplikacji
 Nasz zespół zdecydował się na wykonanie projektu w formie aplikacji webowej. Jedną z głównych zalet tego rozwiązania jest łatwość w obsłudze - wystarczy wpisać link w przeglądarce i dostajemy pełny dostęp do aplikacji. Nie trzeba nic pobierać na swoje urządzenie, jest to rozwiązanie uniwersalne - niezależne od rodzaju sprzętu, z którego korzystamy. W obecnych czasach można zauważyć tendencję do odchodzenia od aplikacji desktopowych - jest to dość zrozumiałe, mając na względzie fakt, w jakim kierunku rozwija się życie codziennego człowieka. Z własnych obserwacji stwierdzamy, że współczesnemu użytkownikowi zależy przede wszystkim na tym, aby dana aplikacja miała jak najlepszy, łatwy dostęp. Możliwość korzystania z aplikacji zarówno na naszych urządzeniach mobilnych, takich jak telefonach, tabletach (np. w drodze na uczelnię, do pracy), jak i tych stacjonarnych w domu jest czymś, co każdy wyjątkowo sobie ceni. Świadczyć o tym mogą uzyskane dane z badań z listopada 2016 roku, w którym to po raz pierwszy procent tworzonego przez urządzenia mobilne ruchu w sieci przewyższył ten, generowany przez desktopy. Wzrost tego ruchu z każdym rokiem nie spowalnia, a wręcz przeciwnie - w ostatnich 5 latach zwiększył się on o ponad 5% [^1].
 
 ![](https://i.imgur.com/0Cj9GIt.jpg)

***

### Nazwa aplikacji
Jonagold XP

***

### Tematyka
Sklep internetowy ze sprzętem komputerowym

***

### Planowane technologie
Jako zespół niemalże jednogłośnie zdecydowaliśmy, że aplikację webową wykonamy w języku programowania Java za pomocą frameworka p.t. Spring. 

Backend:
 - Java (Spring),
 - Baza danych - Oracle Database

Frontend:
 - HTML,
 - CSS, wraz z biblioteką Bootstrap,
 - JavaScript


### Działanie aplikacji
- użytkownik ma możliwość przeglądania całej oferty sklepu, może wybrać także kategorię, np. telefony czy smartwatche
- użytkownik może złożyć zamówienie na kilka produktów
- użytkownik może zobaczyć historię swoich zamówień
- użytkownik może wykupić usługę premium- darmowa dostawa, specjalne promocje
- po weryfikacji zakupu można dodać komentarz (recenzję) o produkcie
- po złożeniu zamówienia, użytkownik otrzymuje maila ze szczegółami zamówienia
- użytkownik może zapisać się do newslettera i otrzymywać powiadomienia o promocjach

***

### Instalacja i uruchomienie aplikacji
Ze względu na złożoność procesu deploymentu oraz ograniczonych możliwości podczas realizacji projektu aplikacja musi zostać uruchamiana lokalnie przez jej potencjalnego użytkownika. Co się z tym wiąże nie ma możliwości dostępu i aktualizacji danych pomiędzy różnymi urządzeniami bez uruchomienia lub edycji skryptu wypełniającego bazę danych odpowiednimi danymi.
1. Pobranie repozytorium na własne urządzenie
2. Utworzenie lokalnej bazy danych MySQL na jednym z wolnych portów komputera
3. Edytowanie pliku konfiguracyjnego (application.properties) aby zgadzał się z utworzoną bazą danych 
    - "server port"
    - "datasource password"
4. Uruchomienie skryptu w celu uzupełnienia bazy danych domyślnymi danymi
5. Uruchomienie głównej aplikacji (nazwa pliku)
6. Otworzenie dowolnej przeglądarki i wpisanie w wyszukiwarkę odpowiednio
    - http://localhost:"server port"/JonagoldXPAdmin/ dla strony głównej aplikacji z widoku admina
    - ... dla strony głównej aplikacji z widoku użytkowynika

Uwaga port do, którego odwołuje się aplikacja musi być wolny podczas jej uruchomienia w przeciwnym wypadku aplikacja może nie działać poprawnie.
***

### Przypisy
[^1]: [Mobile VS Desktop Usage Statistics](https://www.zippia.com/advice/mobile-vs-desktop-usage-statistics/)
