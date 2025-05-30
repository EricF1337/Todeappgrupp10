# ✅ To-Do App – Grupp 10

En enkel backend-applikation byggd i **Java med Spring Boot**, som möjliggör hantering av användare och deras uppgifter ("tasks"). Projektet är utvecklat testdrivet med fokus på **CI/CD via GitHub Actions**.

---

## 📦 Funktionalitet

Applikationen exponerar ett REST API med fullständig CRUD för två entiteter:

- `User` – innehåller namn, lösenord, e-post, etc.
- `Task` – innehåller titel, beskrivning, status och är kopplad till en användare

Relationen mellan `User` och `Task` är **OneToMany** (en användare kan ha flera uppgifter).

---

## 🏗️ Teknisk uppdelning (Trelagersarkitektur)

- **Controller**: Hanterar inkommande HTTP-anrop (REST API)
- **Service**: Innehåller affärslogik
- **Repository**: Interagerar med databasen via Spring Data JPA

---

## 🧪 Teststrategi

Projektet använder Testdriven Utveckling (TDD) och inkluderar följande testtyper:

| Typ              | Verktyg             | Beskrivning                                           |
|------------------|---------------------|--------------------------------------------------------|
| Enhetstester     | JUnit + Mockito     | Testar `service`-lagret med mockade repository        |
| Komponenttester  | MockMvc             | Testar `controller`-lagret, HTTP-respons mm           |
| Integrationstester| @SpringBootTest    | Testar verklig databasinteraktion med MySQL eller H2  |

> ✅ Totalt antal tester: minst 10 st, med minst 1 av varje typ ovan.

---

## 🗄️ Databaskonfiguration

### Produktion

Projektet använder **MySQL** som databas i produktion. Inställningarna finns i `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tododb
spring.datasource.username=root
spring.datasource.password=Skola-hero-1
spring.jpa.hibernate.ddl-auto=update
```

> 🔧 Se till att en lokal MySQL-server körs och att databasen `tododb` är skapad.

### Tester

Det finns en separat konfigurationsfil `application-test.properties` som använder en **H2 in-memory** databas:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
```

> ⚠️ Observera: För att denna H2-konfiguration ska aktiveras krävs att du använder `@ActiveProfiles("test")` i dina testklasser. Om inte, används standardkonfigurationen – alltså MySQL – även vid tester.

---

## 🔄 Continuous Integration (CI)

Projektet använder GitHub Actions för automatisk testning vid varje `push` och `pull request`.

CI-pipelinen:

- Startar en MySQL-databas i Docker
- Kör samtliga tester i en isolerad miljö
- Säkerställer att projektet är byggbart och testbart

Konfigurationen finns i `.github/workflows/maven.yml`.

---

## 🧰 Tekniker & verktyg

- Java 17
- Spring Boot
- MySQL
- JPA / Hibernate
- H2 (för tester – valfritt)
- JUnit, Mockito, MockMvc
- Git & GitHub
- GitHub Actions (CI)

---

## ▶️ Så här kör du projektet lokalt

1. Klona repot:
```bash
git clone https://github.com/EricF1337/Todeappgrupp10.git
cd Todeappgrupp10/demo
```

2. Skapa MySQL-databasen:
```sql
CREATE DATABASE tododb;
```

3. Kör applikationen:
```bash
mvn spring-boot:run
```

Applikationen är nu tillgänglig på: `http://localhost:8080`

---

## 🔗 API-endpoints

### 🔸 User-endpoints (`/api/users`)

| Metod | URL                | Beskrivning                    |
|-------|--------------------|--------------------------------|
| GET   | `/api/users`       | Hämta alla användare           |
| GET   | `/api/users/{id}`  | Hämta en användare via ID      |
| POST  | `/api/users`       | Skapa en ny användare          |
| PUT   | `/api/users/{id}`  | Uppdatera befintlig användare  |
| DELETE| `/api/users/{id}`  | Ta bort en användare           |

---

### 🔸 Task-endpoints (`/api/tasks`)

| Metod | URL                | Beskrivning                           |
|-------|--------------------|---------------------------------------|
| GET   | `/api/tasks`       | Hämta alla uppgifter                  |
| GET   | `/api/tasks/{id}`  | Hämta en uppgift via ID               |
| POST  | `/api/tasks`       | Skapa en ny uppgift (kopplas till user) |
| PUT   | `/api/tasks/{id}`  | Uppdatera en befintlig uppgift        |
| DELETE| `/api/tasks/{id}`  | Ta bort en uppgift                    |

> ⚠️ Notering: Vid skapande av en `Task` krävs en `userId` för att koppla uppgiften till rätt användare.
