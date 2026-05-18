# Microservicio de Gestión de Pagos ('pagos')

## Integrantes
* **Gonzalo Hormazábal**
* **Geraldinne González**

## Descripción
Módulo encargado de procesar y auditar los flujos financieros una vez adjudicada una subasta. Implementa un estricto control transaccional para asegurar la consistencia del dinero.

* **Puerto:** `8085`
* **Base de Datos:** `pagos_db` (MySQL)

## Funcionalidades Clave
* CRUD y filtros personalizados (por método, subasta, usuario y montos mayores).
* Transiciones controladas de estados de pago (`PENDIENTE`, `APROBADO`, `RECHAZADO`).
* Integridad de operaciones de escritura aseguradas con la anotación `@Transactional`.

## Configuración (`application.properties`)
* server.port=8085
* spring.datasource.url=jdbc:mysql://localhost:3306/pagos_db
* spring.datasource.username=root
* spring.datasource.password=
* spring.jpa.hibernate.ddl-auto=update

* spring.security.user.name=admin_pagos
* spring.security.user.password=Pagos2026!
 
* logging.level.cl.sda1085.pagos=DEBUG


## Pasos para Ejecutar

### 1. Preparación de la Base de Datos
Antes de ejecutar el servicio, crear la conexión a la base de datos de MySQL (XAMPP) corriendo en el puerto 3306 y con el nombre 'pagos_db'.

### 2. Verificación de Credenciales
Revisar que el archivo application.properties tenga por defecto, usuario root y contraseña vacía.

### 3. Lanzamiento del Microservicio
Ejecutar (run) la clase principal con la anotación @SpringBootApplication (PagosApplication.java).

### 4. Reglas de Seguridad
Al consumir los endpoints en Postman, ten en cuenta el comportamiento de la cadena de filtros de seguridad:

* Registrar Intento de Pago (POST /api/pagos): Está fuertemente protegido. Requiere configurar Authorization como Basic Auth utilizando los accesos del CLIENTE dueño de la oferta ganadora.
* Consultar Comprobantes (GET /api/pagos): Restringido de forma exclusiva para la auditoría contable interna bajo credenciales con rol de ADMIN.
