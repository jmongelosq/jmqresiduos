# Cambios realizados – jmqresiduos

Proyecto basado en `jmqsystem`, corregido según el informe de corrección del 07/10/2026.

## 1. Nombre del proyecto

- `pom.xml`: `artifactId`, `name` y `finalName` → `jmqresiduos` (genera `target/jmqresiduos.war`).
- `META-INF/context.xml` y plugin de Tomcat: ruta `/jmqresiduos`.
- Se eliminó `META-INF/context_1.xml` (copia sin uso).
- Se eliminó `WEB-INF/lib/` (tenía `jasperreports-6.20.0.jar`, que chocaba con la 6.20.6 del `pom.xml`, y los JSTL ya incluidos por Maven).

## 2. Código eliminado (no estaba en uso)

Ninguno de estos archivos era accesible desde el menú; varios usaban tablas que no existen en la base
(`residuos`, `residuos_cabecera`, `residuos_detalle`, `vista_residuos`).

- Residuos (versión vieja): `ResiduoController/DAO`, `ResiduosCabeceraController/DAO`, `ResiduosDetalleController/DAO`,
  modelos `Residuo`, `ResiduosCabecera`, `ResiduosDetalle`; JSP `residuos`, `listar_cabeceras`, `listar_detalle`,
  `tablaCabecera`, `recoleccion_residuos`.
- Reportes viejos (reemplazados por `InformeController`): `ReporteEstadistica`, `ReporteFinanciero`, `ReporteRecoleccion`,
  `ReporteResiduo`, `ReporteRuta`, `ReporteVehiculo` (Controller + DAO), `GraficoResiduosController`;
  JSP `reportes_*.jsp`, `estadisticas.jsp`, `informe_recoleccion_individual.jsp`.
- Deudas (reemplazado por Pendientes): `DeudaController`, `DeudaDAO`, `Deuda`, `deudas.jsp`.
- Puntos limpios (ya no se usa): `PuntoLimpio*`, `IngresoPuntoLimpio*`, `puntosLimpios.jsp`, `ingresosPuntosLimpios.jsp`.
- Plantillas sueltas: `stater.jsp` (incluía un `footer.jsp` inexistente), `folder.jsp`, `header.jsp`, `logout.jsp`
  (el cierre de sesión lo hace `LogoutController`).
- Reportes `.jrxml` sin uso y todos los `.jasper` (los reportes se compilan desde el `.jrxml` al ejecutarse).

## 3. Sesión

- Nuevo `com/filter/SesionFilter.java`: protege todas las páginas y controladores.
  Sin sesión → redirige a `login.jsp` (o responde 401 si es una petición AJAX).
  Quedan libres: `login.jsp`, `index.jsp`, `LoginController`, `LogoutController`, `dist/` y `plugins/`.
- `index.jsp`: verificaba el atributo `logueado`, que nunca se guardaba; ahora usa `usuario`, igual que el login.
- `dashboard.jsp`: si una carga AJAX recibe 401 (sesión vencida), vuelve al login.

## 4. CDN → librerías locales

- `dashboard.jsp` carga una sola vez, desde `plugins/` y `dist/`: AdminLTE, Font Awesome, jQuery, Bootstrap,
  DataTables (+ Bootstrap 4, Responsive, Buttons), JSZip, pdfmake y SweetAlert2.
- Se quitaron las cargas repetidas en `asignaciones`, `facturacion`, `pagos`, `pendientes`, `recolecciones` y `tarifas`
  (algunas volvían a cargar jQuery o Bootstrap y rompían estilos o plugins de otras pantallas).
- SweetAlert2 ahora está disponible en todas las pantallas (antes 15 pantallas lo usaban sin cargarlo).
- Traducción de DataTables local: `plugins/datatables/i18n/es-ES.json`.

## 5. Recolección + clasificaciones (cabecera-detalle con transacción)

- `recolecciones.jsp`: el formulario "Nueva Recolección" permite agregar las clasificaciones de residuos
  (tipo, cantidad en kg, observación) antes de guardar.
- `RecoleccionController.insertar`: valida las clasificaciones (al menos una, cantidad > 0, sin tipos repetidos,
  total clasificado ≤ cantidad recolectada) y llama al DAO.
- `RecoleccionDAO.insertarConClasificaciones`: guarda `recoleccion` y `clasificacion_residuo` con la misma conexión,
  `setAutoCommit(false)` → `commit()`; ante cualquier error `rollback()` y no queda nada guardado.
- La edición de recolecciones y el módulo Clasificación de Residuos siguen funcionando igual.

## 6. Reportes

- Logo: 64 reportes usaban una ruta fija de una PC (`C:\Users\...\jmqsystem\...\logo.png`). Ahora se carga desde
  el proyecto (`WEB-INF/reportes/logo.png`), así que funciona en cualquier PC o carpeta.
- `RecoleccionController`: el PDF individual buscaba `recoleccion_individual.jrxml` (no existía);
  ahora usa `reporte_recolecciones_individual.jrxml`.
- `reporte_recolecciones_individual.jrxml`: usaba `a.id_personal` → corregido a `a.id_personal_responsable`.
- `informe_vehiculos.jrxml` e `informe_vehiculo_individual.jrxml`: usaban `v.marca` (la marca está en `modelo`) → `m.marca`.
- `vehiculos_listado.jrxml`: campos `capacidad` y `kilometraje` de tipo `Number` (Jasper no lo admite) → `BigDecimal`.

## 7. Comentarios

- Se quitaron los separadores decorativos (`// =====`, `<!-- ===== -->`) y los emojis; se conservaron los títulos
  y los comentarios que explican algo. El código no cambió.

## Base de datos

- Script aparte: `base_de_datos/limpieza_jmqresiduos.sql` (ejecutar después de probar el proyecto y con respaldo).

---

# Segunda tanda de cambios

## 8. Botón Atrás del navegador

- `dashboard.jsp`: cada módulo queda en el historial (`dashboard.jsp#clientes`, `#pagos`, ...).
  Atrás / Adelante navegan entre módulos del sistema; al recargar se mantiene el módulo abierto.
- `login.jsp`: si ya hay una sesión iniciada, redirige al dashboard (Atrás no vuelve al login).
  No se guarda en caché.
- Después de cerrar sesión, Atrás no muestra pantallas internas (se recarga y el filtro pide login).
- `dashboard.jsp?modulo=pagos` (o cualquier módulo) abre ese módulo dentro del dashboard.
  Tarifas, Pagos, Barrios, Clientes, Personal y Tipos de Residuos vuelven a su módulo dentro del
  dashboard después de guardar (antes Tarifas mostraba la página suelta, sin menú).

## 9. Misma estructura en todas las pantallas

- Configuración común de tablas en `dashboard.jsp` (una sola vez para todo el sistema):
  "Mostrar N registros" | Copiar, CSV, Excel, PDF, Imprimir | Buscar; abajo información y paginación.
  La columna "Acciones" no se exporta.
- Se quitaron las configuraciones propias que la contradecían (Usuarios, Roles, Asignaciones).
- Recolecciones y Pagos ahora usan DataTables (antes tenían un buscador propio sin paginación).
- Clasificación de Residuos: se quitó su buscador propio, que ocultaba el de la tabla.
- Operaciones y Gestión Financiera con el mismo formato que Referenciales: tarjeta simple, título a
  la izquierda, "Nuevo ..." y "Reporte General" a la derecha; sin el título grande superior;
  encabezado de tabla igual (sin fondo negro); Pendientes integrado en una sola tarjeta.
- Se quitaron estilos propios que cambiaban la letra, el tamaño o los colores de una sola pantalla.
- Etiquetas de estado iguales en todas las tablas (ACTIVO, PAGADO, etc.).
- `pagos.jsp`: la variable `contexto` era `const`; al entrar por segunda vez a Pagos el JavaScript
  fallaba. Ahora es `var`.

## 10. Listas desplegables vacías (errores que ya existían)

- `personal.jsp` → Cargo: pedía los cargos con el parámetro `accion`, pero `CargoController` lee `action`,
  y además esperaba JSON cuando el controlador devuelve las opciones en HTML. La lista quedaba vacía.
- `vehiculos.jsp` → Modelo: el mismo problema con `ModeloController`.
- Se revisaron todas las llamadas AJAX del proyecto (parámetro, acción y formato de respuesta);
  no quedan otras diferencias.

## 11. Factura y reportes (tercera tanda)

- `factura_individual.jrxml`: nuevo diseño con el formato de factura de Paraguay (timbrado y número en el recuadro,
  condición de venta, valores de venta Exentas/5%/10%, total en letras, liquidación del IVA, ANULADA). Compatible con iReport.
- `FacturaDAO.buscarPorId`: si el cliente no tiene RUC, la factura muestra su cédula.
- `src/main/resources/jasperreports.properties`: si el equipo no tiene la fuente Arial, los reportes usan otra en vez de dar error.

## 12. Timbrado, informes y base de datos (cuarta tanda)

- `FacturaController.guardar`: valida el timbrado antes de emitir (8 dígitos; fin de vigencia no anterior al inicio;
  la fecha de emisión debe estar dentro de la vigencia). Lógica en `validarVigenciaTimbrado`.
- Informe de Tarifas implementado (antes mostraba "se implementará"): general por rango de fechas (tarifas vigentes
  en el período), búsqueda e informe individual. Se recuperaron y corrigieron `informe_tarifas.jrxml` e
  `informe_tarifa_individual.jrxml`.
- `informe_asignacion_listado.jrxml`: no aplicaba el rango de fechas (salían todas las asignaciones); ahora filtra.
- Informes generales: con un período sin datos se muestra el encabezado con el período en lugar de un PDF vacío.
- Base de datos: `base_de_datos/correccion_integridad_jmqresiduos.sql` (recolecciones huérfanas, clave foránea
  recoleccion → asignacion, restricción repetida en pago). Ya ejecutado.

## 13. Diseño visual (quinta tanda)

- Reportes (`WEB-INF/reportes/*.jrxml`): mismo encabezado en todos (logo, título, "JMQ SYSTEM · Gestión de Residuos",
  subtítulo y recuadro con la fecha de emisión), colores institucionales azul marino / azul, filas alternadas en los
  listados, márgenes internos en las celdas y números con formato de Paraguay (1.234.567). Siguen siendo compatibles
  con iReport y no necesitan internet.
- `factura_individual.jrxml`: rediseñada con una grilla de columnas fija, así que todos los recuadros quedan alineados
  (emisor con logo, timbrado, datos del cliente, detalle, subtotales, total en letras y liquidación del IVA).
- `informes.jsp`: banner de encabezado, secciones con ícono y tarjetas de colores por informe (solo AdminLTE/Bootstrap).
- `dashboardHome.jsp` (Inicio): banner de bienvenida con logo y fecha, cajas `small-box` e `info-box` de AdminLTE,
  estado de la flota, resumen del sistema y accesos rápidos a los módulos.
- Correcciones encontradas al revisar los reportes: textos del encabezado de `informe_vehiculos`, tipos numéricos de
  `vehiculo_individual` y ubicación de las observaciones en `informe_servicio_individual`.
