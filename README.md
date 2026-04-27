# Proyectofinal

Descripción
-----------

Proyectofinal es una API REST backend construida con Spring Boot diseñada siguiendo una arquitectura basada en features (por feature / dominio). El sistema expone endpoints para autenticación, gestión de usuarios y ventas, recursos de recursos humanos y catálogo, ofreciendo capas separadas de controllers, servicios, repositorios, DTOs y mappers.

Tecnologías
-----------

- Java
- Spring Boot
- Spring Web (REST)
- Spring Security
- Spring Data (repositories)
- OpenAPI / Swagger (documentación)
- Maven (mvnw incluido)

Arquitectura basada en features
-------------------------------

El proyecto está organizado por features en lugar de por capas transversales. Cada feature agrupa todo lo relacionado con un dominio funcional (controladores, servicios, repositorios, DTOs y mappers). Ventajas:

- Encapsulamiento del dominio y mayor cohesión.
- Facilita el trabajo en equipos por feature.
- Mejora la navegabilidad en proyectos grandes.

Estructura del proyecto (simplificada)
------------------------------------

```
src/
  main/
    java/
      org/ezequiel/proyectofinal/
        auth/
          controller/
          service/
          dto/
          repository/
          mapper/
        users/
          controller/
          service/
          dto/
          repository/
          mapper/
        sales/
          controller/
          service/
          dto/
          repository/
          mapper/
        hr/
          controller/
          service/
          dto/
          repository/
          mapper/
        catalog/
          controller/
          service/
          dto/
          repository/
          mapper/
    resources/
      application.yaml
      application-dev.yaml
      application-prod.yaml
      db/migration/
```

Descripción de features
-----------------------

- Auth
  - Provee endpoints de autenticación y autorización (login, refresh token, logout según implementación).
  - Gestión de credenciales de usuarios y control de acceso.
  - Integración con el sistema de roles y protección de endpoints.

- Users
  - Gestión de usuarios del sistema (CRUD, búsqueda, perfiles).
  - DTOs para requests/responses y mappers para conversión entre entidades y DTOs.
  - Reglas de validación específicas para creación y actualización.

- Sales
  - Endpoints relacionados con operaciones comerciales (órdenes, shippers, order-details).
  - Soporta operaciones de ciclo de vida de órdenes (crear, actualizar estado, listar, cancelar).
  - Relaciones con `users` y `catalog` para clientes y productos.

- HR
  - Gestión de empleados, territorios y datos de recursos humanos.
  - Contiene reglas de negocio específicas del dominio HR y seguridad adicional donde aplica.

- Catalog
  - Gestión de productos, categorías y stock.
  - Endpoints para búsqueda, filtrado y actualización de catálogo.

Seguridad
---------

- Autenticación
  - El sistema usa autenticación centralizada en el feature `auth`. La implementación puede basarse en tokens (por ejemplo JWT) o sesiones según la configuración del proyecto.

- Roles
  - El proyecto está preparado para manejar roles/autoridades (p.ej. `ADMIN`, `USER`, `WAREHOUSE`, `MANAGER`). Las reglas de acceso deben configurarse en la capa de seguridad.

- Protección de endpoints
  - Los endpoints críticos están protegidos mediante filtros/interceptors de Spring Security.
  - Se recomienda aplicar controles por anotaciones (`@PreAuthorize`, `@RolesAllowed`) en controllers o configuración global por URL.

Documentación de la API
----------------------

- OpenAPI / Swagger incluido: la documentación se expone por defecto en las rutas estándar de Springdoc/OpenAPI.
- Rutas comunes:
  - Interfaz Swagger UI: `/swagger-ui.html` ó `/swagger-ui/index.html` (según versión)
  - JSON OpenAPI: `/v3/api-docs`

Instalación y ejecución
------------------------

Requisitos

- JDK instalado (11+ recomendado)
- Maven (se incluye `mvnw` para evitar instalar Maven globalmente)

Ejecutar localmente

1. Clonar el repositorio

```bash
git clone <repo-url>
cd proyectofinal
```

2. Construir y ejecutar con el wrapper de Maven

```bash
./mvnw clean package
./mvnw spring-boot:run
```

3. Alternativamente ejecutar el JAR

```bash
java -jar target/proyectofinal-*.jar
```

Configuración

- Las configuraciones por entorno están en `src/main/resources` (`application-dev.yaml`, `application-prod.yaml`, `application.yaml`).
- Variables sensibles (credenciales, secretos) deben gestionarse mediante variables de entorno o un gestor de secretos.

Mejores prácticas aplicadas
---------------------------

- Organización por features para alta cohesión y bajo acoplamiento.
- Separación de capas: Controllers, Services, Repositories, DTOs y Mappers.
- Uso de DTOs para evitar exponer entidades JPA en la API.
- Validación de entrada en DTOs y manejo centralizado de excepciones.
- Externalización de configuración mediante profiles (`dev`, `prod`).
- Documentación de la API con OpenAPI/Swagger para facilitar consumo.
- Seguridad por roles y protección de endpoints con Spring Security.
- Migraciones de base de datos versionadas en `resources/db/migration`.

Siguientes pasos recomendados
----------------------------

- Revisar y ajustar la configuración de seguridad para la estrategia de tokens concreta (JWT u otra).
- Añadir ejemplos en las anotaciones OpenAPI para mejorar la documentación.
- Incluir CI para ejecutar pruebas y `mvn -DskipTests=false verify` en cada pipeline.

Contacto
-------

Para preguntas o contribuciones, abrir issues o crear pull requests en el repositorio.
