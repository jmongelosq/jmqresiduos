<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">

                <i class="fas fa-route"></i>

                Gestión de Rutas

            </h3>


            <div>

                <!-- NUEVA RUTA -->

                <button type="button"
                        class="btn btn-success btn-sm"
                        onclick="nuevaRuta()">

                    <i class="fas fa-plus"></i>

                    Nueva Ruta

                </button>


                <!-- REPORTE GENERAL -->

                <a href="reporte_general.jsp?modulo=rutas"
                   target="_blank"
                   class="btn btn-info btn-sm">

                    <i class="fas fa-file-pdf"></i>

                    Reporte General

                </a>

            </div>

        </div>

    </div>


    <!-- TABLA -->

    <div class="card-body">

        <table id="tablaRutas"
               class="table table-bordered table-striped table-hover"
               style="width:100%">

            <thead>

                <tr>

                    <th>ID</th>

                    <th>Nombre</th>

                    <th>Descripción</th>

                    <th>Barrio</th>

                    <th>Estado</th>

                    <th>Acciones</th>

                </tr>

            </thead>


            <tbody id="tbodyRutas">

            </tbody>

        </table>

    </div>

</div>


<!-- MODAL NUEVA / MODIFICAR RUTA -->

<div class="modal fade"
     id="modalRuta"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog"
         role="document">

        <div class="modal-content">


            <!-- HEADER MODAL -->

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="fas fa-route"></i>

                    <span id="tituloModalRuta">
                        Nueva Ruta
                    </span>

                </h5>


                <button type="button"
                        class="close text-white"
                        data-dismiss="modal"
                        aria-label="Cerrar">

                    <span aria-hidden="true">
                        &times;
                    </span>

                </button>

            </div>


            <!-- BODY MODAL -->

            <div class="modal-body">

                <form id="formRuta">


                    <input type="hidden"
                           id="id_ruta"
                           name="id_ruta">


                    <!-- NOMBRE -->

                    <div class="form-group">

                        <label>
                            Nombre de la Ruta
                        </label>

                        <input type="text"
                               class="form-control"
                               id="nombre"
                               name="nombre"
                               maxlength="100"
                               required>

                    </div>


                    <!-- BARRIO -->

                    <div class="form-group">

                        <label>
                            Barrio
                        </label>

                        <select class="form-control"
                                id="id_barrio"
                                name="id_barrio"
                                required>

                            <option value="">
                                Seleccione...
                            </option>

                        </select>

                    </div>


                    <!-- DESCRIPCIÓN -->

                    <div class="form-group">

                        <label>
                            Descripción
                        </label>

                        <textarea class="form-control"
                                  id="descripcion"
                                  name="descripcion"
                                  rows="3"
                                  maxlength="255"></textarea>

                    </div>


                    <!-- ESTADO -->

                    <div class="form-group"
                         id="grupoEstadoRuta"
                         style="display:none;">

                        <label>
                            Estado
                        </label>

                        <select class="form-control"
                                id="estado"
                                name="estado">

                            <option value="ACTIVO">
                                ACTIVO
                            </option>

                            <option value="INACTIVO">
                                INACTIVO
                            </option>

                        </select>

                    </div>

                </form>

            </div>


            <!-- FOOTER MODAL -->

            <div class="modal-footer">

                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>

                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-primary"
                        onclick="guardarRuta()">

                    <i class="fas fa-save"></i>

                    Guardar

                </button>

            </div>

        </div>

    </div>

</div>


<script>


/* INICIALIZACIÓN */

/*
 * Este JSP se carga dinámicamente dentro de dashboard.jsp.
 *
 * Por eso se ejecuta directamente y no usamos
 * $(document).ready().
 */

cargarRutas();

cargarBarrios();


/* CARGAR RUTAS */

function cargarRutas() {

    $.ajax({

        url: "RutaController",

        type: "POST",

        data: {
            accion: "listar"
        },

        dataType: "json",


        success: function(data) {


            /*
             * Si ya existe un DataTable,
             * primero lo destruimos.
             */

            if (
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable(
                    "#tablaRutas"
                )
            ) {

                $("#tablaRutas")
                    .DataTable()
                    .destroy();

            }


            var tbody =
                $("#tbodyRutas");


            tbody.empty();


            /*
             * Crear las filas.
             */

            $.each(
                data,
                function(index, r) {


                    var estado =
                        String(
                            r.estado || ""
                        ).toUpperCase();


                    var badgeEstado;


                    if (
                        estado === "ACTIVO"
                    ) {

                        badgeEstado =
                            '<span class="badge badge-success">'
                            + 'ACTIVO'
                            + '</span>';

                    } else {

                        badgeEstado =
                            '<span class="badge badge-danger">'
                            + 'INACTIVO'
                            + '</span>';

                    }


                    var fila =


                        "<tr>"


                        /* ID */

                        + "<td>"
                        + r.id_ruta
                        + "</td>"


                        /* NOMBRE */

                        + "<td>"
                        + escapeHtml(
                            r.nombre || ""
                        )
                        + "</td>"


                        /* DESCRIPCIÓN */

                        + "<td>"
                        + escapeHtml(
                            r.descripcion || ""
                        )
                        + "</td>"


                        /* BARRIO */

                        + "<td>"
                        + escapeHtml(
                            r.nombre_barrio || ""
                        )
                        + "</td>"


                        /* ESTADO */

                        + "<td>"
                        + badgeEstado
                        + "</td>"


                        /* ACCIONES */

                        + "<td class='text-center'>"


                        /* MODIFICAR */

                        + '<button '
                        + 'type="button" '
                        + 'class="btn btn-warning btn-sm mr-1" '
                        + 'onclick="editarRuta('
                        + r.id_ruta
                        + ')" '
                        + 'title="Modificar">'

                        + '<i class="fas fa-edit"></i>'

                        + '</button>'


                        /* VER */

                        + '<button '
                        + 'type="button" '
                        + 'class="btn btn-info btn-sm mr-1" '
                        + 'onclick="reporteIndividual('
                        + r.id_ruta
                        + ')" '
                        + 'title="Ver">'

                        + '<i class="fas fa-eye"></i>'

                        + '</button>'


                        /* ELIMINAR */

                        + '<button '
                        + 'type="button" '
                        + 'class="btn btn-danger btn-sm" '
                        + 'onclick="eliminarRuta('
                        + r.id_ruta
                        + ')" '
                        + 'title="Eliminar">'

                        + '<i class="fas fa-trash"></i>'

                        + '</button>'


                        + "</td>"


                        + "</tr>";


                    tbody.append(
                        fila
                    );

                }
            );


            /* DATATABLE */

            $("#tablaRutas").DataTable({

                language: {

                    search: "Buscar:",

                    lengthMenu:
                        "Mostrar _MENU_ registros",

                    info:
                        "Mostrando _START_ a _END_ de _TOTAL_ registros",

                    infoEmpty:
                        "Mostrando 0 a 0 de 0 registros",

                    zeroRecords:
                        "No se encontraron registros",

                    emptyTable:
                        "No hay rutas registradas",

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

                },

                pageLength: 10,

                responsive: true,

                autoWidth: false

            });

        },


        error: function(xhr) {

            console.error(
                "Error al cargar rutas:",
                xhr.responseText
            );


            Swal.fire(
                "Error",
                "No se pudieron cargar las rutas.",
                "error"
            );

        }

    });

}


/* CARGAR BARRIOS */

function cargarBarrios() {

    $.ajax({

        url: "BarrioController",

        type: "POST",

        data: {
            accion: "listarOptions"
        },

        dataType: "json",


        success: function(data) {


            var select =
                $("#id_barrio");


            select.empty();


            select.append(

                '<option value="">'
                + 'Seleccione...'
                + '</option>'

            );


            $.each(
                data,
                function(index, barrio) {

                    select.append(

                        $("<option>", {

                            value:
                                barrio.id_barrio,

                            text:
                                barrio.nombre

                        })

                    );

                }
            );

        },


        error: function(xhr) {

            console.error(
                "Error al cargar barrios:",
                xhr.responseText
            );

        }

    });

}


/* NUEVA RUTA */

function nuevaRuta() {


    $("#formRuta")[0]
        .reset();


    $("#id_ruta")
        .val("");


    $("#estado")
        .val("ACTIVO");


    $("#tituloModalRuta")
        .text("Nueva Ruta");


    $("#grupoEstadoRuta")
        .hide();


    $("#modalRuta")
        .modal("show");

}


/* MODIFICAR RUTA */

function editarRuta(id) {


    $.ajax({

        url: "RutaController",

        type: "POST",

        data: {
            accion: "listar"
        },

        dataType: "json",


        success: function(data) {


            var ruta =
                null;


            $.each(
                data,
                function(index, r) {


                    if (
                        parseInt(
                            r.id_ruta
                        )
                        ===
                        parseInt(id)
                    ) {

                        ruta = r;

                        return false;

                    }

                }
            );


            if (
                ruta === null
            ) {

                Swal.fire(
                    "Error",
                    "No se encontró la ruta seleccionada.",
                    "error"
                );

                return;

            }


            $("#id_ruta")
                .val(
                    ruta.id_ruta
                );


            $("#nombre")
                .val(
                    ruta.nombre || ""
                );


            $("#descripcion")
                .val(
                    ruta.descripcion || ""
                );


            $("#id_barrio")
                .val(
                    ruta.id_barrio
                );


            $("#estado")
                .val(
                    String(
                        ruta.estado
                    ).toUpperCase()
                    === "ACTIVO"
                        ? "ACTIVO"
                        : "INACTIVO"
                );


            $("#tituloModalRuta")
                .text(
                    "Modificar Ruta"
                );


            $("#grupoEstadoRuta")
                .show();


            $("#modalRuta")
                .modal("show");

        },


        error: function(xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                "Error",
                "No se pudo obtener la ruta.",
                "error"
            );

        }

    });

}


/* GUARDAR RUTA */

function guardarRuta() {


    var id =
        $("#id_ruta")
            .val();


    var nombre =
        $("#nombre")
            .val()
            .trim();


    var descripcion =
        $("#descripcion")
            .val()
            .trim();


    var idBarrio =
        $("#id_barrio")
            .val();


    var estado =
        $("#estado")
            .val();


    /* VALIDAR NOMBRE */

    if (
        nombre === ""
    ) {

        Swal.fire(
            "Atención",
            "Debe ingresar el nombre de la ruta.",
            "warning"
        );

        $("#nombre")
            .focus();

        return;

    }


    /* VALIDAR BARRIO */

    if (
        idBarrio === ""
    ) {

        Swal.fire(
            "Atención",
            "Debe seleccionar un barrio.",
            "warning"
        );

        $("#id_barrio")
            .focus();

        return;

    }


    /* DETERMINAR ACCIÓN */

    var accion =
        id === ""
            ? "insertar"
            : "actualizar";


    $.ajax({

        url: "RutaController",

        type: "POST",

        data: {

            accion:
                accion,

            id_ruta:
                id,

            nombre:
                nombre,

            descripcion:
                descripcion,

            id_barrio:
                idBarrio,

            estado:
                estado

        },


        success: function(data) {


            /*
             * El Controller corregido devuelve:
             *
             * success
             *
             * o
             *
             * error
             */

            if (
                data.trim()
                ===
                "success"
            ) {


                $("#modalRuta")
                    .modal("hide");


                Swal.fire({

                    icon:
                        "success",

                    title:
                        "Correcto",

                    text:
                        accion === "insertar"
                            ? "Ruta registrada correctamente."
                            : "Ruta actualizada correctamente.",

                    timer:
                        1500,

                    showConfirmButton:
                        false

                });


                cargarRutas();

            } else {

                Swal.fire(
                    "Error",
                    "No se pudo guardar la ruta.",
                    "error"
                );

            }

        },


        error: function(xhr) {

            console.error(
                xhr.responseText
            );


            Swal.fire(
                "Error",
                "Error al comunicarse con el servidor.",
                "error"
            );

        }

    });

}


/* ELIMINAR RUTA */

function eliminarRuta(id) {


    Swal.fire({

        title:
            "¿Eliminar ruta?",

        text:
            "La ruta pasará a estado INACTIVO.",

        icon:
            "warning",

        showCancelButton:
            true,

        confirmButtonColor:
            "#dc3545",

        cancelButtonColor:
            "#6c757d",

        confirmButtonText:
            "Sí, eliminar",

        cancelButtonText:
            "Cancelar"

    }).then(
        function(result) {


            if (
                !result.isConfirmed
            ) {

                return;

            }


            $.ajax({

                url:
                    "RutaController",

                type:
                    "POST",

                data: {

                    accion:
                        "Borrar",

                    id:
                        id

                },


                success:
                    function(data) {


                        if (
                            data.trim()
                            ===
                            "success"
                        ) {


                            Swal.fire({

                                icon:
                                    "success",

                                title:
                                    "Correcto",

                                text:
                                    "Ruta eliminada correctamente.",

                                timer:
                                    1500,

                                showConfirmButton:
                                    false

                            });


                            cargarRutas();

                        } else {


                            Swal.fire(
                                "Error",
                                "No se pudo eliminar la ruta.",
                                "error"
                            );

                        }

                    },


                error:
                    function(xhr) {

                        console.error(
                            xhr.responseText
                        );


                        Swal.fire(
                            "Error",
                            "Error al procesar la baja.",
                            "error"
                        );

                    }

            });

        }
    );

}


/* REPORTE INDIVIDUAL */

function reporteIndividual(id) {


    window.open(

        "RutaController"
        + "?accion=reporteIndividual"
        + "&id="
        + id,

        "_blank"

    );

}


/* ESCAPAR HTML */

function escapeHtml(text) {


    if (
        text === null
        ||
        text === undefined
    ) {

        return "";

    }


    return String(text)

        .replace(
            /&/g,
            "&amp;"
        )

        .replace(
            /</g,
            "&lt;"
        )

        .replace(
            />/g,
            "&gt;"
        )

        .replace(
            /"/g,
            "&quot;"
        )

        .replace(
            /'/g,
            "&#039;"
        );

}


</script>