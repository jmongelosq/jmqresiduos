<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- ENCABEZADO -->

<!-- CONTENIDO -->

<section class="content">

    <div class="container-fluid">

        <div class="card">

            <div class="card-header">

                <h3 class="card-title">

                    <i class="fas fa-truck-loading"></i>

                    Gestión de Asignaciones

                </h3>


                <div class="card-tools">

                    <button type="button"
                            class="btn btn-success btn-sm mr-1"
                            onclick="nuevoAsignacion()">

                        <i class="fas fa-plus"></i>

                        Nueva Asignación

                    </button>


                    <a href="reporte_general.jsp?modulo=asignaciones"
                       target="_blank"
                       class="btn btn-info btn-sm">

                        <i class="fas fa-file-pdf"></i>

                        Reporte General

                    </a>

                </div>

            </div>


            <div class="card-body">

                <div id="contenedorAsignaciones"
                     class="table-responsive">

                    <div class="text-center p-4">

                        <i class="fas fa-spinner fa-spin"></i>

                        Cargando asignaciones...

                    </div>

                </div>

            </div>

        </div>

    </div>

</section>


<!-- MODAL NUEVA ASIGNACIÓN -->

<div class="modal fade"
     id="modalNuevaAsignacion"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog modal-lg"
         role="document">

        <div class="modal-content">

            <div class="modal-header bg-primary">

                <h4 class="modal-title text-white">

                    <i class="fas fa-plus-circle"></i>

                    Nueva Asignación

                </h4>


                <button type="button"
                        class="close text-white"
                        data-dismiss="modal"
                        aria-label="Cerrar">

                    <span aria-hidden="true">
                        &times;
                    </span>

                </button>

            </div>


            <div class="modal-body">

                <form id="formNuevaAsignacion">


                    <!-- RUTA / VEHÍCULO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Ruta

                                    <span class="text-danger">
                                        *
                                    </span>

                                </label>


                                <select id="nuevoIdRuta"
                                        name="id_ruta"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Cargando rutas...
                                    </option>

                                </select>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Vehículo

                                    <span class="text-danger">
                                        *
                                    </span>

                                </label>


                                <select id="nuevoIdVehiculo"
                                        name="id_vehiculo"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Cargando vehículos...
                                    </option>

                                </select>

                            </div>

                        </div>

                    </div>


                    <!-- PERSONAL / FECHA / HORA SALIDA -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Personal responsable

                                    <span class="text-danger">
                                        *
                                    </span>

                                </label>


                                <select id="nuevoIdPersonal"
                                        name="id_personal"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Cargando personal...
                                    </option>

                                </select>

                            </div>

                        </div>


                        <div class="col-md-3">

                            <div class="form-group">

                                <label>

                                    Fecha

                                    <span class="text-danger">
                                        *
                                    </span>

                                </label>


                                <input type="date"
                                       id="nuevoFecha"
                                       name="fecha"
                                       class="form-control"
                                       required>

                            </div>

                        </div>


                        <div class="col-md-3">

                            <div class="form-group">

                                <label>
                                    Hora de salida
                                </label>


                                <input type="time"
                                       id="nuevoHoraSalida"
                                       name="hora_salida"
                                       class="form-control">

                            </div>

                        </div>

                    </div>


                    <!-- HORA REGRESO / ESTADO -->

                    <div class="row">

                        <div class="col-md-3">

                            <div class="form-group">

                                <label>
                                    Hora de regreso
                                </label>


                                <input type="time"
                                       id="nuevoHoraRegreso"
                                       name="hora_regreso"
                                       class="form-control">

                            </div>

                        </div>


                        <div class="col-md-3">

                            <div class="form-group">

                                <label>
                                    Estado
                                </label>


                                <select id="nuevoEstado"
                                        name="estado"
                                        class="form-control">

                                    <option value="PROGRAMADA">
                                        PROGRAMADA
                                    </option>

                                    <option value="EN_CURSO">
                                        EN CURSO
                                    </option>

                                    <option value="FINALIZADA">
                                        FINALIZADA
                                    </option>

                                    <option value="CANCELADA">
                                        CANCELADA
                                    </option>

                                </select>

                            </div>

                        </div>

                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>
                            Observaciones
                        </label>


                        <textarea id="nuevoObservaciones"
                                  name="observaciones"
                                  class="form-control"
                                  rows="3"
                                  maxlength="255"
                                  placeholder="Ingrese observaciones de la asignación"></textarea>

                    </div>

                </form>

            </div>


            <div class="modal-footer">

                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>

                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-primary"
                        onclick="guardarAsignacion()">

                    <i class="fas fa-save"></i>

                    Guardar

                </button>

            </div>

        </div>

    </div>

</div>


<!-- MODAL MODIFICAR ASIGNACIÓN -->

<div class="modal fade"
     id="modalEditarAsignacion"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog modal-lg"
         role="document">

        <div class="modal-content">

            <div class="modal-header bg-warning">

                <h4 class="modal-title">

                    <i class="fas fa-edit"></i>

                    Modificar Asignación

                </h4>


                <button type="button"
                        class="close"
                        data-dismiss="modal"
                        aria-label="Cerrar">

                    <span aria-hidden="true">
                        &times;
                    </span>

                </button>

            </div>


            <div class="modal-body">

                <form id="formEditarAsignacion">

                    <input type="hidden"
                           id="editIdAsignacion"
                           name="id_asignacion">


                    <!-- RUTA / VEHÍCULO -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Ruta

                                    <span class="text-danger">
                                        *
                                    </span>

                                </label>


                                <select id="editIdRuta"
                                        name="id_ruta"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Cargando rutas...
                                    </option>

                                </select>

                            </div>

                        </div>


                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Vehículo

                                    <span class="text-danger">
                                        *
                                    </span>

                                </label>


                                <select id="editIdVehiculo"
                                        name="id_vehiculo"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Cargando vehículos...
                                    </option>

                                </select>

                            </div>

                        </div>

                    </div>


                    <!-- PERSONAL / FECHA / HORA SALIDA -->

                    <div class="row">

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>

                                    Personal responsable

                                    <span class="text-danger">
                                        *
                                    </span>

                                </label>


                                <select id="editIdPersonal"
                                        name="id_personal"
                                        class="form-control"
                                        required>

                                    <option value="">
                                        Cargando personal...
                                    </option>

                                </select>

                            </div>

                        </div>


                        <div class="col-md-3">

                            <div class="form-group">

                                <label>

                                    Fecha

                                    <span class="text-danger">
                                        *
                                    </span>

                                </label>


                                <input type="date"
                                       id="editFecha"
                                       name="fecha"
                                       class="form-control"
                                       required>

                            </div>

                        </div>


                        <div class="col-md-3">

                            <div class="form-group">

                                <label>
                                    Hora de salida
                                </label>


                                <input type="time"
                                       id="editHoraSalida"
                                       name="hora_salida"
                                       class="form-control">

                            </div>

                        </div>

                    </div>


                    <!-- HORA REGRESO / ESTADO -->

                    <div class="row">

                        <div class="col-md-3">

                            <div class="form-group">

                                <label>
                                    Hora de regreso
                                </label>


                                <input type="time"
                                       id="editHoraRegreso"
                                       name="hora_regreso"
                                       class="form-control">

                            </div>

                        </div>


                        <div class="col-md-3">

                            <div class="form-group">

                                <label>
                                    Estado
                                </label>


                                <select id="editEstado"
                                        name="estado"
                                        class="form-control">

                                    <option value="PROGRAMADA">
                                        PROGRAMADA
                                    </option>

                                    <option value="EN_CURSO">
                                        EN CURSO
                                    </option>

                                    <option value="FINALIZADA">
                                        FINALIZADA
                                    </option>

                                    <option value="CANCELADA">
                                        CANCELADA
                                    </option>

                                </select>

                            </div>

                        </div>

                    </div>


                    <!-- OBSERVACIONES -->

                    <div class="form-group">

                        <label>
                            Observaciones
                        </label>


                        <textarea id="editObservaciones"
                                  name="observaciones"
                                  class="form-control"
                                  rows="3"
                                  maxlength="255"></textarea>

                    </div>

                </form>

            </div>


            <div class="modal-footer">

                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>

                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-warning"
                        onclick="actualizarAsignacion()">

                    <i class="fas fa-save"></i>

                    Actualizar

                </button>

            </div>

        </div>

    </div>

</div>


<script>

    // INICIALIZACIÓN

    $(document).ready(function () {

        listarAsignaciones();

        cargarRutas(
                "#nuevoIdRuta",
                0
                );

        cargarVehiculos(
                "#nuevoIdVehiculo",
                0
                );

        cargarPersonal(
                "#nuevoIdPersonal",
                0
                );


        establecerFechaActual();

    });


    // FECHA ACTUAL

    function establecerFechaActual() {

        var hoy = new Date();

        var mes =
                String(
                        hoy.getMonth() + 1
                        ).padStart(
                        2,
                        "0"
                        );

        var dia =
                String(
                        hoy.getDate()
                        ).padStart(
                        2,
                        "0"
                        );

        var fechaActual =
                hoy.getFullYear()
                + "-"
                + mes
                + "-"
                + dia;


        $("#nuevoFecha").val(
                fechaActual
                );
    }


    // LISTAR ASIGNACIONES

    function listarAsignaciones() {

        $.ajax({

            url: "AsignacionController",

            type: "POST",

            data: {
                action: "listar"
            },


            success: function (data) {

                $("#contenedorAsignaciones")
                        .html(data);


                if ($("#tablaAsignaciones").length) {

                    $("#tablaAsignaciones").DataTable({

                        destroy: true,

                        responsive: true,

                        autoWidth: false,

                        pageLength: 10,


                        language: {

                            search:
                                    "Buscar:",

                            lengthMenu:
                                    "Mostrar _MENU_ registros",

                            info:
                                    "Mostrando _START_ a _END_ de _TOTAL_ registros",

                            infoEmpty:
                                    "Mostrando 0 a 0 de 0 registros",

                            zeroRecords:
                                    "No se encontraron registros",

                            emptyTable:
                                    "No hay datos disponibles en la tabla",

                            infoFiltered:
                                    "(filtrado de _MAX_ registros totales)",

                            loadingRecords:
                                    "Cargando...",

                            processing:
                                    "Procesando...",

                            paginate: {

                                first:
                                        "Primero",

                                last:
                                        "Último",

                                next:
                                        "Siguiente",

                                previous:
                                        "Anterior"
                            }
                        }
                    });
                }

            },


            error: function (xhr) {

                console.error(
                        "Error al cargar asignaciones:",
                        xhr.responseText
                        );


                $("#contenedorAsignaciones")
                        .html(
                                "<div class='alert alert-danger'>" +
                                "No se pudieron cargar las asignaciones." +
                                "</div>"
                                );
            }
        });
    }


    // NUEVA ASIGNACIÓN

    function nuevoAsignacion() {

        $("#formNuevaAsignacion")[0]
                .reset();


        $("#nuevoEstado")
                .val("PROGRAMADA");


        establecerFechaActual();


        $("#modalNuevaAsignacion")
                .modal("show");
    }


    // CARGAR RUTAS

    function cargarRutas(
            selector,
            idActual
            ) {

        return $.ajax({

            url: "AsignacionController",

            type: "POST",

            data: {

                action: "listarRutas",

                id_actual:
                        idActual || 0
            },


            success: function (data) {

                $(selector)
                        .html(data);

            },


            error: function () {

                $(selector)
                        .html(
                                "<option value=''>" +
                                "No se pudieron cargar las rutas" +
                                "</option>"
                                );
            }
        });
    }


    // CARGAR VEHÍCULOS

    function cargarVehiculos(
            selector,
            idActual
            ) {

        return $.ajax({

            url: "AsignacionController",

            type: "POST",

            data: {

                action: "listarVehiculos",

                id_actual:
                        idActual || 0
            },


            success: function (data) {

                $(selector)
                        .html(data);

            },


            error: function () {

                $(selector)
                        .html(
                                "<option value=''>" +
                                "No se pudieron cargar los vehículos" +
                                "</option>"
                                );
            }
        });
    }


    // CARGAR PERSONAL

    function cargarPersonal(
            selector,
            idActual
            ) {

        return $.ajax({

            url: "AsignacionController",

            type: "POST",

            data: {

                action: "listarPersonal",

                id_actual:
                        idActual || 0
            },


            success: function (data) {

                $(selector)
                        .html(data);

            },


            error: function () {

                $(selector)
                        .html(
                                "<option value=''>" +
                                "No se pudo cargar el personal" +
                                "</option>"
                                );
            }
        });
    }


    // VALIDAR HORAS

    function validarHoras(
            horaSalida,
            horaRegreso
            ) {

        if (
            horaRegreso &&
            !horaSalida
                ) {

            Swal.fire({

                icon:
                        "warning",

                title:
                        "Hora de salida requerida",

                text:
                        "Para indicar la hora de regreso primero debe indicar la hora de salida."
            });

            return false;
        }


        if (
            horaSalida &&
            horaRegreso
                ) {

            if (
                horaRegreso <= horaSalida
                    ) {

                Swal.fire({

                    icon:
                            "warning",

                    title:
                            "Horario no válido",

                    text:
                            "La hora de regreso debe ser posterior a la hora de salida."
                });

                return false;
            }
        }


        return true;
    }


    // GUARDAR

    function guardarAsignacion() {

        var formulario =
                $("#formNuevaAsignacion")[0];


        if (!formulario.checkValidity()) {

            formulario.reportValidity();

            return;
        }


        if (
            !validarHoras(
                    $("#nuevoHoraSalida").val(),
                    $("#nuevoHoraRegreso").val()
                    )
                ) {

            return;
        }


        $.ajax({

            url:
                    "AsignacionController",

            type:
                    "POST",

            data:
                    $("#formNuevaAsignacion")
                    .serialize()
                    + "&action=insertar",


            success: function (respuesta) {

                procesarRespuesta(

                        respuesta,

                        "Asignación registrada",

                        "La asignación fue registrada correctamente.",

                        function () {

                            $("#modalNuevaAsignacion")
                                    .modal("hide");

                            listarAsignaciones();
                        }
                );
            },


            error: function (xhr) {

                console.error(
                        xhr.responseText
                        );


                Swal.fire(
                        "Error",
                        "Ocurrió un error al registrar la asignación.",
                        "error"
                        );
            }
        });
    }


    // EDITAR ASIGNACIÓN

    function editarAsignacion(id) {

        $.ajax({

            url:
                    "AsignacionController",

            type:
                    "POST",

            data: {

                action:
                        "buscar",

                id_asignacion:
                        id
            },

            dataType:
                    "json",


            success: function (data) {

                if (!data.success) {

                    Swal.fire(
                            "Error",
                            "No se encontró la asignación.",
                            "error"
                            );

                    return;
                }


                $("#editIdAsignacion")
                        .val(
                                data.id_asignacion
                                );


                $("#editFecha")
                        .val(
                                data.fecha
                                );


                $("#editHoraSalida")
                        .val(
                                data.hora_salida
                                ? data.hora_salida.substring(
                                        0,
                                        5
                                        )
                                : ""
                                );


                $("#editHoraRegreso")
                        .val(
                                data.hora_regreso
                                ? data.hora_regreso.substring(
                                        0,
                                        5
                                        )
                                : ""
                                );


                $("#editEstado")
                        .val(
                                data.estado
                                );


                $("#editObservaciones")
                        .val(
                                data.observaciones || ""
                                );


                // CARGAR LOS TRES COMBOS

                var rutas =
                        cargarRutas(
                                "#editIdRuta",
                                data.id_ruta
                                );


                var vehiculos =
                        cargarVehiculos(
                                "#editIdVehiculo",
                                data.id_vehiculo
                                );


                var personal =
                        cargarPersonal(
                                "#editIdPersonal",
                                data.id_personal
                                );


                // ESPERAR A QUE LOS TRES TERMINEN

                $.when(
                        rutas,
                        vehiculos,
                        personal
                        ).done(function () {

                    // SELECCIONAR RUTA ACTUAL

                    $("#editIdRuta")
                            .val(
                                    String(
                                            data.id_ruta
                                            )
                                    );


                    // SELECCIONAR VEHÍCULO ACTUAL

                    $("#editIdVehiculo")
                            .val(
                                    String(
                                            data.id_vehiculo
                                            )
                                    );


                    // SELECCIONAR PERSONAL ACTUAL

                    $("#editIdPersonal")
                            .val(
                                    String(
                                            data.id_personal
                                            )
                                    );


                    // COMPROBACIÓN

                    console.log(
                            "Asignación:",
                            data.id_asignacion
                            );

                    console.log(
                            "Ruta:",
                            data.id_ruta
                            );

                    console.log(
                            "Vehículo:",
                            data.id_vehiculo
                            );

                    console.log(
                            "Personal:",
                            data.id_personal
                            );


                    // MOSTRAR MODAL

                    $("#modalEditarAsignacion")
                            .modal("show");
                });
            },


            error: function (xhr) {

                console.error(
                        xhr.responseText
                        );


                Swal.fire(
                        "Error",
                        "No se pudo consultar la asignación.",
                        "error"
                        );
            }
        });
    }


    // ACTUALIZAR

    function actualizarAsignacion() {

        var formulario =
                $("#formEditarAsignacion")[0];


        if (!formulario.checkValidity()) {

            formulario.reportValidity();

            return;
        }


        if (
            !validarHoras(
                    $("#editHoraSalida").val(),
                    $("#editHoraRegreso").val()
                    )
                ) {

            return;
        }


        $.ajax({

            url:
                    "AsignacionController",

            type:
                    "POST",

            data:
                    $("#formEditarAsignacion")
                    .serialize()
                    + "&action=actualizar",


            success: function (respuesta) {

                procesarRespuesta(

                        respuesta,

                        "Asignación actualizada",

                        "Los datos fueron actualizados correctamente.",

                        function () {

                            $("#modalEditarAsignacion")
                                    .modal("hide");

                            listarAsignaciones();
                        }
                );
            },


            error: function (xhr) {

                console.error(
                        xhr.responseText
                        );


                Swal.fire(
                        "Error",
                        "Ocurrió un error al actualizar.",
                        "error"
                        );
            }
        });
    }


    // CANCELAR

    function desactivarAsignacion(id) {

        Swal.fire({

            title:
                    "¿Cancelar asignación?",

            text:
                    "La asignación quedará en estado CANCELADA.",

            icon:
                    "warning",

            showCancelButton:
                    true,

            confirmButtonText:
                    "Sí, cancelar",

            cancelButtonText:
                    "No, volver"

        }).then(function (resultado) {

            if (!resultado.isConfirmed) {

                return;
            }


            $.ajax({

                url:
                        "AsignacionController",

                type:
                        "POST",

                data: {

                    action:
                            "desactivar",

                    id_asignacion:
                            id
                },


                success: function (respuesta) {

                    procesarRespuesta(

                            respuesta,

                            "Asignación cancelada",

                            "La asignación fue cancelada correctamente.",

                            function () {

                                listarAsignaciones();
                            }
                    );
                },


                error: function () {

                    Swal.fire(
                            "Error",
                            "Ocurrió un error al cancelar.",
                            "error"
                            );
                }
            });
        });
    }


    // ACTIVAR

    function activarAsignacion(id) {

        Swal.fire({

            title:
                    "¿Activar asignación?",

            text:
                    "La asignación volverá al estado PROGRAMADA.",

            icon:
                    "question",

            showCancelButton:
                    true,

            confirmButtonText:
                    "Sí, activar",

            cancelButtonText:
                    "Cancelar"

        }).then(function (resultado) {

            if (!resultado.isConfirmed) {

                return;
            }


            $.ajax({

                url:
                        "AsignacionController",

                type:
                        "POST",

                data: {

                    action:
                            "activar",

                    id_asignacion:
                            id
                },


                success: function (respuesta) {

                    procesarRespuesta(

                            respuesta,

                            "Asignación activada",

                            "La asignación volvió al estado PROGRAMADA.",

                            function () {

                                listarAsignaciones();
                            }
                    );
                },


                error: function () {

                    Swal.fire(
                            "Error",
                            "Ocurrió un error al activar.",
                            "error"
                            );
                }
            });
        });
    }


    // PROCESAR RESPUESTA

    function procesarRespuesta(
            respuesta,
            tituloOK,
            textoOK,
            callbackOK
            ) {

        respuesta =
                (respuesta || "")
                .trim();


        if (respuesta === "ok") {

            Swal.fire({

                icon:
                        "success",

                title:
                        tituloOK,

                text:
                        textoOK,

                timer:
                        1800,

                showConfirmButton:
                        false

            }).then(function () {

                if (callbackOK) {

                    callbackOK();
                }
            });


            return;
        }


        if (
            respuesta.indexOf(
                    "error|"
                    ) === 0
                ) {

            var mensaje =
                    respuesta.substring(
                            6
                            );


            Swal.fire({

                icon:
                        "warning",

                title:
                        "No se puede realizar la operación",

                text:
                        mensaje
            });


            return;
        }


        Swal.fire({

            icon:
                    "error",

            title:
                    "Error",

            text:
                    "No se pudo completar la operación."
        });
    }

</script>