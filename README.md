# JMQ SYSTEM

Sistema web para la gestión del servicio de recolección de residuos sólidos urbanos.

Proyecto final de carrera del **Técnico Superior en TIC en Desarrollo de Sistemas Informáticos** – Centro Tecnológico de Avanzada (CTA) – Servicio Nacional de Promoción Profesional (SNPP), San Lorenzo, Paraguay.

**Autor:** José Máximo Mongelós Quiroga

## Descripción

JMQ SYSTEM centraliza en una sola plataforma la gestión del servicio de recolección de residuos: clientes, servicios contratados, rutas, asignaciones, recolecciones, facturación, pagos, pendientes e informes.

El sistema se complementa con la aplicación móvil **JMQ Facturas**, que permite al cobrador registrar los cobros en campo a través de la API **ApiJMQFacturas**.

## Módulos

| Grupo | Módulos |
| --- | --- |
| Referenciales | Usuarios, roles, personal, cargos, clientes, barrios, tipos de residuos, vehículos, modelos y rutas |
| Operaciones | Servicios, asignaciones, recolecciones y clasificación de residuos |
| Gestión financiera | Tarifas, facturación con timbrado e IVA, pagos y pendientes |
| Informes | Reportes en PDF por rango de fechas |

Cada usuario accede solo a los módulos de su rol: Administrador, Supervisor, Operador, Cajero, Cobrador y Consulta.

## Tecnologías

- **Back end:** Java 17, JSP, Jakarta Servlets, JDBC y Maven.
- **Front end:** HTML5, CSS3, JavaScript, jQuery, AJAX, Bootstrap, AdminLTE, DataTables y SweetAlert2. Todas las librerías están incluidas en el proyecto, por lo que funciona sin conexión a internet.
- **Base de datos:** PostgreSQL.
- **Reportes:** JasperReports e iReport.
- **Servidor de aplicaciones:** Apache Tomcat 10.

## Requisitos

- Java JDK 17 o superior.
- Apache Tomcat 10 o superior.
- PostgreSQL 16 o superior.
- Apache NetBeans (o Maven) para compilar.

## Instalación

1. Crear la base de datos `jmqresiduos` en PostgreSQL y restaurar el respaldo de la base.
2. Revisar los datos de conexión en `src/main/java/com/config/Conexion.java` (servidor, puerto, base, usuario y contraseña) y ajustarlos al servidor donde se instale.
3. Compilar el proyecto con **Clean and Build** en NetBeans, o con `mvn clean package`. Se genera el archivo `target/jmqresiduos.war`.
4. Copiar `jmqresiduos.war` en la carpeta `webapps` de Tomcat e iniciar Tomcat.
5. Ingresar desde el navegador a `http://IP-DEL-SERVIDOR:8080/jmqresiduos/` e iniciar sesión con un usuario administrador.

La guía completa, con los comandos para Ubuntu Server, se encuentra en el **Manual técnico de instalación** del libro del proyecto (sección 5.5.5).

## Estructura del proyecto

```
src/main/java/com/config       Conexión a la base de datos y permisos por rol
src/main/java/com/controller   Controladores (Servlets) de cada módulo
src/main/java/com/dao          Acceso a datos (DAO)
src/main/java/com/model        Clases del modelo
src/main/java/com/filter       Filtro de sesión y control de acceso
src/main/webapp                Páginas JSP, recursos de la interfaz y reportes JasperReports
```
