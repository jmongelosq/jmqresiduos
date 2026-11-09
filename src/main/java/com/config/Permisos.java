package com.config;

import java.text.Normalizer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class Permisos {

    private static final Map<String, Set<String>> MODULOS_POR_ROL = new HashMap<>();

    private static final Map<String, String> MODULO_POR_CONTROLADOR = new HashMap<>();

    private static final Set<String> CONTROLADORES_RESTRINGIDOS = Set.of(
            "UsuarioController",
            "RolController",
            "InformeController"
    );

    private static final Set<String> ACCIONES_DE_ESCRITURA = Set.of(
            "insertar",
            "actualizar",
            "guardar",
            "borrar",
            "eliminar",
            "activar",
            "desactivar",
            "anular",
            "suspender",
            "marcarpagada"
    );

    private static final String[] REFERENCIALES = {
        "usuarios", "roles", "personal", "cargo", "clientes", "barrio",
        "tipos_residuos", "vehiculos", "modelo", "rutas"
    };

    private static final String[] OPERACIONES = {
        "servicios", "asignaciones", "recolecciones", "clasificacion_residuos"
    };

    private static final String[] FINANZAS = {
        "tarifas", "facturacion", "pagos", "pendientes"
    };

    static {
        MODULO_POR_CONTROLADOR.put("UsuarioController", "usuarios");
        MODULO_POR_CONTROLADOR.put("RolController", "roles");
        MODULO_POR_CONTROLADOR.put("PersonalController", "personal");
        MODULO_POR_CONTROLADOR.put("CargoController", "cargo");
        MODULO_POR_CONTROLADOR.put("ClienteController", "clientes");
        MODULO_POR_CONTROLADOR.put("BarrioController", "barrio");
        MODULO_POR_CONTROLADOR.put("TipoResiduoController", "tipos_residuos");
        MODULO_POR_CONTROLADOR.put("VehiculoController", "vehiculos");
        MODULO_POR_CONTROLADOR.put("ModeloController", "modelo");
        MODULO_POR_CONTROLADOR.put("RutaController", "rutas");
        MODULO_POR_CONTROLADOR.put("ServicioController", "servicios");
        MODULO_POR_CONTROLADOR.put("AsignacionController", "asignaciones");
        MODULO_POR_CONTROLADOR.put("RecoleccionController", "recolecciones");
        MODULO_POR_CONTROLADOR.put("ClasificacionResiduoController", "clasificacion_residuos");
        MODULO_POR_CONTROLADOR.put("TarifaController", "tarifas");
        MODULO_POR_CONTROLADOR.put("FacturaController", "facturacion");
        MODULO_POR_CONTROLADOR.put("PagoController", "pagos");
        MODULO_POR_CONTROLADOR.put("PendienteController", "pendientes");
        MODULO_POR_CONTROLADOR.put("InformeController", "informes");

        Set<String> todos = new LinkedHashSet<>();
        Collections.addAll(todos, REFERENCIALES);
        Collections.addAll(todos, OPERACIONES);
        Collections.addAll(todos, FINANZAS);
        todos.add("informes");

        Set<String> supervisor = new LinkedHashSet<>(todos);
        supervisor.remove("usuarios");
        supervisor.remove("roles");

        Set<String> operador = new LinkedHashSet<>();
        Collections.addAll(operador, OPERACIONES);
        Collections.addAll(operador, "clientes", "rutas", "vehiculos");

        Set<String> cajero = new LinkedHashSet<>();
        Collections.addAll(cajero, FINANZAS);
        cajero.add("clientes");

        MODULOS_POR_ROL.put("ADMINISTRADOR", Collections.unmodifiableSet(todos));
        MODULOS_POR_ROL.put("SUPERVISOR", Collections.unmodifiableSet(supervisor));
        MODULOS_POR_ROL.put("OPERADOR", Collections.unmodifiableSet(operador));
        MODULOS_POR_ROL.put("CAJERO", Collections.unmodifiableSet(cajero));
        MODULOS_POR_ROL.put("CAJERA", Collections.unmodifiableSet(cajero));
        MODULOS_POR_ROL.put("COBRADOR", Set.of("pendientes", "pagos"));
        MODULOS_POR_ROL.put("CONSULTA", Set.of("informes"));
    }

    private Permisos() {
    }

    public static String normalizarRol(Object rol) {
        if (rol == null) {
            return "";
        }

        String texto = Normalizer.normalize(rol.toString().trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return texto.toUpperCase();
    }

    public static Set<String> modulosDe(Object rol) {
        Set<String> modulos = MODULOS_POR_ROL.get(normalizarRol(rol));
        return modulos == null ? Collections.emptySet() : modulos;
    }

    public static boolean puedeVer(Object rol, String modulo) {
        return modulosDe(rol).contains(modulo);
    }

    public static boolean puedeVerAlguno(Object rol, String... modulos) {
        Set<String> permitidos = modulosDe(rol);

        for (String modulo : modulos) {
            if (permitidos.contains(modulo)) {
                return true;
            }
        }

        return false;
    }

    public static String moduloDeRuta(String ruta) {
        if (ruta == null || ruta.isEmpty()) {
            return null;
        }

        String nombre = ruta.substring(ruta.lastIndexOf('/') + 1);

        if (nombre.endsWith(".jsp")) {
            String modulo = nombre.substring(0, nombre.length() - 4);
            return esModulo(modulo) ? modulo : null;
        }

        return MODULO_POR_CONTROLADOR.get(nombre);
    }

    public static boolean esControlador(String ruta) {
        return ruta != null && ruta.endsWith("Controller");
    }

    public static boolean esControladorRestringido(String ruta) {
        if (ruta == null) {
            return false;
        }

        return CONTROLADORES_RESTRINGIDOS.contains(ruta.substring(ruta.lastIndexOf('/') + 1));
    }

    public static boolean esAccionDeEscritura(String accion) {
        return accion != null && ACCIONES_DE_ESCRITURA.contains(accion.trim().toLowerCase());
    }

    private static boolean esModulo(String modulo) {
        return MODULOS_POR_ROL.get("ADMINISTRADOR").contains(modulo);
    }
}
