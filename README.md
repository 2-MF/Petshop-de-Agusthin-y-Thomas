# 🐾 PetShop System

- Codigo del sistema asistido por IA generativa

Sistema de gestión para una tienda de mascotas, desarrollado como proyecto académico con Spring Boot 3.2.0 y Java 17.
🚀 Tecnologías

    Java 17
    Spring Boot 3.2.0
    Spring Web
    Spring Data JPA
    H2 (desarrollo) / MySQL (producción)
    Lombok
    Maven

📁 Estructura del proyecto

src/main/java/com/petshop/
├── controller/
│   ├── AgendaController.java
│   ├── ClienteController.java
│   ├── FuncionarioController.java
│   └── SolicitudController.java
├── model/
│   ├── Agenda.java
│   ├── Cliente.java
│   ├── Funcionario.java
│   ├── Mascota.java
│   ├── Solicitud.java
│   └── enums: EstadoAgenda, EstadoSolicitud, RolFuncionario, TipoServicio
├── repository/
├── service/
│   ├── AgendaService.java
│   ├── ClienteService.java
│   ├── FuncionarioService.java
│   └── SolicitudService.java
└── PetShopApplication.java

Copy
🗄️ Base de datos

El script petshop_database.sql crea la base petshop con las tablas del modelo entidad-relación.
