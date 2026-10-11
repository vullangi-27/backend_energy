# backend_energy

[![Deploy to Render](https://render.com/images/deploy-to-render-button.svg)](https://render.com/deploy?repo=https://github.com/vullangi-27/backend_energy)

Smart Energy Management System - Spring Boot Backend (Java 21).

## 🚀 One-Click Render Deployment

Click the button above or visit:
**[https://render.com/deploy?repo=https://github.com/vullangi-27/backend_energy](https://render.com/deploy?repo=https://github.com/vullangi-27/backend_energy)**

Render will automatically parse `render.yaml` and `Dockerfile` to build and deploy the application.

### Environment Variables

| Variable | Description | Default |
|---|---|---|
| `PORT` | Listening port on Render | `8080` (dynamic) |
| `SPRING_DATASOURCE_URL` | MySQL Database JDBC URL | `jdbc:mysql://localhost:3306/smart_energy_db` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `root` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `root` |

## 🛠 Local Setup & Run

```bash
# Run backend locally
./mvnw spring-boot:run
```