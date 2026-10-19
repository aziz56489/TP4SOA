# Recrutement d’un nouvel employé — Camunda 7

Le processus du fichier `BPMN/DiagramEmploye.bpmn` est déployé automatiquement au démarrage. Il collecte les informations de l’employé, les enregistre via l’API REST locale, appelle l’API Advice Slip, puis attend la validation RH dans Tasklist. Le choix « Approve advice » finalise le processus; « Reject advice » le termine sans validation.

## Démarrer

```powershell
mvn spring-boot:run
```

Tasklist : `http://localhost:8080/camunda/app/tasklist/default/` — identifiants de développement : `demo` / `demo`.

Cliquez sur **Start process**, choisissez **New employee recruitment**, puis renseignez le formulaire.

## API employé (Postman)

- Méthode : `POST`
- URL : `http://localhost:8080/api/employees`
- Header : `Content-Type: application/json`
- Body (raw / JSON) :

```json
{
  "name": "Amira Ben Salah",
  "email": "amira@example.com",
  "jobOffer": "HR Assistant"
}
```

La réponse contient l’identifiant enregistré. `GET http://localhost:8080/api/employees` liste les employés stockés dans H2.

L’API externe peut être remplacée avec `advice.api.url` dans `application.properties`. La base H2 locale et les identifiants `demo` sont réservés au développement.
