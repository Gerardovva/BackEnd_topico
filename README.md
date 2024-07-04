# ForoHub

![ForoHub Logo](https://your-logo-url.com/logo.png)

¡Bienvenido a **ForoHub**!

## Descripción

**ForoHub** es una API REST creada para gestionar un sistema de foro donde los usuarios pueden plantear preguntas, compartir conocimientos y colaborar. Este proyecto es parte del desafío de back end de Alura Latam.

## Características

- **Crear Tópico:** Permite a los usuarios crear un nuevo tópico en el foro.
- **Listar Tópicos:** Muestra todos los tópicos disponibles en el foro.
- **Ver Tópico Específico:** Recupera los detalles de un tópico específico.
- **Actualizar Tópico:** Modifica la información de un tópico existente.
- **Eliminar Tópico:** Elimina un tópico del foro.

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Security**
- **Maven**
- **MySQL**
- **JWT (JSON Web Token) para Autenticación y Autorización**

## Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- [Java JDK 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/installer/)
- [Git](https://git-scm.com/downloads)

## Instalación

Sigue estos pasos para clonar el repositorio e instalar las dependencias del proyecto:

1. **Clona el repositorio:**

   ```bash
   https://github.com/Gerardovva/Backend_topico.git

2. **Crea la base de datos en MySQL**
   ```topico_api
   CREATE DATABASE topico_api
3. **Actualiza las credenciales de la base de datos en el archivo src/main/resources/application.properties: **
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/topico_api spring.datasource.username=TU_USUARIO spring.datasource.password=TU_CONTRASEÑA spring.jpa.hibernate.ddl-auto=update


