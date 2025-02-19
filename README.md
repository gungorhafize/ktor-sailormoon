# 🌙 Sailor Moon API - Ktor Backend Project

## 📌 Project Overview

<p align="center">
    <img width="479" alt="Image" src="https://github.com/user-attachments/assets/cf2e7976-e416-4d0a-97cc-3b28d78d6e2c" />
</p>
This project is a Ktor-based REST API for managing Sailor Moon characters,
including listing, searching, adding, and deleting characters. The API uses PostgreSQL for data storage, Koin for dependency injection, and HikariCP for database connection pooling.

Here are some useful links to get you started:

- [Ktor Documentation](https://ktor.io/docs/home.html)
- [Ktor GitHub page](https://github.com/ktorio/ktor)
- The [Ktor Slack chat](https://app.slack.com/client/T09229ZC6/C0A974TJ9). You'll need
  to [request an invite](https://surveys.jetbrains.com/s3/kotlin-slack-sign-up) to join.

## 🚀 Project Features

- 📄 **Get Character List** with **Pagination**
- 🔍 **Search Characters** by Name
- ➕ **Add New Characters**
- 🗑️ **Delete Characters**
- 🔐 **Koin Dependency Injection**
- 📡 **PostgreSQL Database Integration**
- 📝 **Serialization (JSON) with Ktor**
- 🌍 **Static Content Serving** (for images)
- 📊 **Monitoring with Logging**

## 🌟 Ktor Features

Here's a list of ktor features included in this project:

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Default Headers](https://start.ktor.io/p/default-headers)             | Adds a default set of headers to HTTP responses                                    |
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [Status Pages](https://start.ktor.io/p/status-pages)                   | Provides exception handling for routes                                             |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [Exposed](https://start.ktor.io/p/exposed)                             | Adds Exposed database to your application                                          |

## 🛠️ Technologies Used

- 🚀 Ktor (Netty Server)
- 📝 100% Kotlin
- 🗄️ PostgreSQL (via Exposed ORM)
- 🔗 HikariCP (Connection Pooling)
- 🏗️ Koin (Dependency Injection)
- 🔄 Jackson (JSON Serialization)
- 🛫 Flyway (Database Migrations)
- 📜 SLF4J & Logback (Logging)
- 🏛️ Exposed (ORM for Kotlin)
- 🧪 Postman (API Testing)

## 🚀 Getting Started

### Prerequisites

- JDK 17+

- PostgreSQL database

- Docker (optional, for containerization)
- IntelliJ Idea

### Installation

### 1️⃣ Clone the repository:

- git clone https://github.com/gungorhafize/ktor-sailormoon.git
- cd ktor-sailormoon

### 2️⃣ Setup Database (PostgreSQL)

1. Ensure PostgreSQL is installed and running
2. Create the database:

``` markdown 
CREATE DATABASE sailormoon_db;
```

3. Verify the database creation:

``` markdown 
\l
```

4. Set up a user and grant permissions:

``` markdown 
CREATE USER sailormoon_user WITH PASSWORD 'yourpassword';
ALTER ROLE sailormoon_user SET client_encoding TO 'utf8';
ALTER ROLE sailormoon_user SET default_transaction_isolation TO 'read committed';
ALTER ROLE sailormoon_user SET timezone TO 'UTC';
GRANT ALL PRIVILEGES ON DATABASE sailormoon_db TO sailormoon_user;
```

### 3️⃣ Configure Environment Variables

``` markdown 
export DB_USER="sailormoon_user"
export DB_PASSWORD="yourpassword"
export DB_URL="jdbc:postgresql://localhost:5432/sailormoon_db"
```

### 4️⃣ Run the Application

``` markdown 
./gradlew run
```

Or run the main function directly from Application.kt:

``` markdown 
fun main() {
io.ktor.server.netty.EngineMain.main(emptyArray())
} 
```

## 📡 API Endpoints

| Method  | Endpoint                                   | Description                             |
|---------|--------------------------------------------|-----------------------------------------|
| GET     | /sailormoon/characters                     | Get a paginated list of characters      
| GET     | /sailormoon/characters/search?query={name} | Search for a character by name or alias 
| POST    | /sailormoon/addcharacter	                  | Add a new character                     
| DELETE	 | /sailormoon/characters/{id}	               | Delete a character                      

## 📜 API Documentation

Use Postman or any API client to test the endpoints.

### Character API Endpoints

### 📄 Get Character List with Pagination

##### Endpoint : /sailormoon/characters

##### Request : ``` GET localhost:8080/sailormoon/characters?page=1 ```

##### Response :

``` markdown 
{
"success": true,
"nextPage": 2,
"sailorMoon": [
  {
    "id": 1,
    "name": "Usagi Tsukino",
    "alias": "Sailor Moon",
    "birth_date": "June 30",
    "height_cm": 160,
    "power_level": 10,
    "special_attacks": [
      "Moon Tiara Magic",
      "Moon Healing Escalation",
      "Moon Gorgeous Meditation"
    ],
    "description": "The main character of the series and the Princess of the Moon. Although she is famous for her clumsiness, she transforms into a powerful warrior.",
    "image": “moon.jpg”
     },
    {
      "id": 2,
      "name": "Ami Mizuno",
      "alias": "Sailor Mercury",
      "birth_date": "September 10",
      "height_cm": 157,
      "power_level": 8,
      "special_attacks": [
      "Mercury Aqua Mist",
      "Shine Aqua Illusion",
      "Mercury Aqua Rhapsody"
        ],
      "description": "A smart and strategic fighter. She is the brain of the team, with abilities related to water and ice.",
      "image": "mercur.jpg"
},
...
```

### 🔍 Search Characters by Name
##### Endpoint : /sailormoon/characters/search

##### Request : ``` GET localhost:8080/sailormoon/search?query=usagi ```

##### Response : 
```
{
    "success": true,
    "sailorMoon": [
        {
            "id": 17,
            "name": "Usagi Tsukino",
            "alias": "Sailor Moon",
            "birth_date": "June 30",
            "height_cm": 160,
            "power_level": 10,
            "special_attacks": [
                "Moon Tiara Magic",
                "Moon Healing Escalation",
                "Moon Gorgeous Meditation"
            ],
            "description": "The main character of the series and the Princess of the Moon. Although she is famous for her clumsiness, she transforms into a powerful warrior.",
            "image": "moon.jpg"
        }
    ]
}
``` 

### ➕ Add New Character
##### Endpoint : /sailormoon/addcharacter

##### Request : ``` POST localhost:8080/sailormoon/addcharacter ```
#### Body Parameters: 
``` {
"id": 18,
"name": "Luna",
"alias": "Luna the Cat",
"birth_date": "Unknown",
"height_cm": 30,
"power_level": 5,
"special_attacks": [
"Moonlight Guidance",
"Transformation Detection",
"Crystal Ball Communication"
],
"description": "A mysterious black cat with a crescent moon on her forehead. She serves as a guide and mentor to Usagi Tsukino and the Sailor Guardians, providing wisdom and magical tools.",
"image": "luna.jpg"
}
```
##### Response :
```
{
"success": true,
"message": "Character added successfully.",
"sailorMoon": [
{
"id": 18,
"name": "Luna",
"alias": "Luna the Cat",
"birth_date": "Unknown",
"height_cm": 30,
"power_level": 5,
"special_attacks": [
"Moonlight Guidance",
"Transformation Detection",
"Crystal Ball Communication"
],
"description": "A mysterious black cat with a crescent moon on her forehead. She serves as a guide and mentor to Usagi Tsukino and the Sailor Guardians, providing wisdom and magical tools.",
"image": "luna.jpg"
}
]
}
```

### 🗑️ Delete Character
##### Endpoint : /sailormoon/characters/{id}

##### Request : ``` DELETE localhost:8080/sailormoon/characters/1 ```

##### Response :
```
{
    "success": true,
    "message": "Character deleted successfully."
}
```



## ⛓️ Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```
If the database connection is initialized successfully, you'll see the following output:

```
Task :run
2025-02-19 17:44:15.710 [main] INFO  - 📡 Attempting to connect to the database...
2025-02-19 17:44:15.753 [main] INFO   - HikariPool-1 - Starting...
2025-02-19 17:44:15.824 [main] INFO   - HikariPool-1 - Added connection org.postgresql.jdbc.PgConnection@64b31700
2025-02-19 17:44:15.824 [main] INFO   - HikariPool-1 - Start completed.
2025-02-19 17:44:15.833 [main] INFO   - ✅ Database connection established successfully!
2025-02-19 17:44:15.851 [main] INFO   - Flyway Community Edition 9.5.0 by Redgate
2025-02-19 17:44:15.851 [main] INFO   - See what's new here: https://flywaydb.org/documentation/learnmore/releaseNotes#9.5.0
2025-02-19 17:44:15.851 [main] INFO   -
2025-02-19 17:44:15.865 [main] INFO   - Database: jdbc:postgresql://localhost:5432/postgres (PostgreSQL 14.16)
2025-02-19 17:44:15.886 [main] INFO   - Successfully validated 1 migration (execution time 00:00.010s)
2025-02-19 17:44:15.898 [main] INFO   - Current version of schema "public": 1
2025-02-19 17:44:15.902 [main] INFO   - Schema "public" is up to date. No migration necessary.
2025-02-19 17:44:15.905 [main] INFO   - ✅ Flyway migrations completed successfully!
2025-02-19 17:44:15.905 [main] INFO  - ✅ CharacterTable checked/created successfully!
2025-02-19 17:44:15.905 [main] INFO  Application - 📡 Database connection initialized.
2025-02-19 17:44:15.976 [main] INFO  Application - Application started in 0.665 seconds.
```

### 🪄 Contributing
#### Contributions are welcome! Feel free to open an issue or submit a pull request. 🌙🐾

### 📄  License

#### Designed and developed by 2025 Hafize Gungor Kaya

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

<p align="center">Made with ❤️ by <b>Hafize</b></p> ```

