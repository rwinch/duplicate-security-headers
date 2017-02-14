Sample project to reproduce duplicate Spring Security headers when performing an async dispatch.

### Steps to recreate

1. mvn spring-boot:run
2. curl -i --user user:secret http://localhost:8080/async