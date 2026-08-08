# Hackathon Platform — Backend (Spring MVC)

Backend en **Spring MVC pur** (config Java, pas Spring Boot), suivant exactement le style de ton exemple
(`AppConfig`, `WebSecurityConfig`, `AppInitializer`, packages `controller/converter/dto/exception/filter/model/repository/service/utilitaire`).

## Prérequis

- JDK 17
- Tomcat 10+ (Servlet 6 / Jakarta) — Tomcat 9 ne fonctionnera pas car ce projet utilise le namespace `jakarta.*`
- MySQL 8
- Maven

## Configuration base de données

Modifier `src/main/resources/application.properties` si besoin (utilisateur/mot de passe MySQL).
La base `hackathon_db` est créée automatiquement (`createDatabaseIfNotExist=true`), les tables sont générées par Hibernate (`hibernate.hbm2ddl.auto=update`).

## Lancer le projet

1. `mvn clean package` → génère `target/hackathon-backend.war`
2. Déployer le WAR sur Tomcat 10+ (ou configurer un module Tomcat dans IntelliJ, comme dans ta capture d'écran)
3. L'API est accessible sur `http://localhost:8080/hackathon-backend/api/...`

## Swagger

- UI : `http://localhost:8080/hackathon-backend/swagger-ui/index.html`
- JSON brut : `http://localhost:8080/hackathon-backend/v3/api-docs`

## Endpoints principaux

| Méthode | URL                          | Accès               | Description                     |
|---------|------------------------------|----------------------|----------------------------------|
| POST    | /api/auth/register            | public               | Créer un compte                 |
| POST    | /api/auth/login                | public               | Se connecter (retourne un JWT)  |
| POST    | /api/teams                     | authentifié          | Créer une équipe                |
| POST    | /api/teams/{id}/join            | authentifié          | Rejoindre une équipe            |
| POST    | /api/teams/{id}/leave           | authentifié          | Quitter une équipe               |
| GET     | /api/teams                      | authentifié          | Lister les équipes              |
| GET     | /api/teams/{id}/members          | authentifié          | Voir les membres d'une équipe   |
| POST    | /api/projects                   | authentifié          | Soumettre le projet de son équipe |
| PUT     | /api/projects/{id}               | authentifié          | Modifier le projet de son équipe |
| GET     | /api/projects                   | authentifié          | Lister tous les projets         |
| GET     | /api/jury/projects               | ROLE_JURY            | Lister les projets à évaluer    |
| POST    | /api/jury/evaluations            | ROLE_JURY            | Noter un projet                 |
| GET     | /api/leaderboard                 | public               | Classement des équipes          |

## Notes d'implémentation

- Un utilisateur ne peut appartenir qu'à **une seule équipe** à la fois (contrôlé dans `TeamService`).
- Une équipe ne peut soumettre qu'**un seul projet** (contrôlé dans `ProjectService`).
- Les rôles (`ROLE_ADMIN`, `ROLE_PARTICIPANT`, `ROLE_JURY`) sont stockés directement sur `Utilisateur.role` et injectés dans le JWT comme claim `roles`.
- Le classement (`/api/leaderboard`) calcule la moyenne des notes de chaque projet et trie par ordre décroissant.
- Les exceptions métier (`ResourceNotFound`, `BadRequestException`) sont centralisées dans `GlobalExceptionHandler`, qui renvoie un JSON `ErrorResponse` cohérent.
