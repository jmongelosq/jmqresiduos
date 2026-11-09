<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <h3 class="card-title">

            <i class="fas fa-user-tag"></i>

            Gestión de Roles

        </h3>

        <div class="card-tools">

            <button
                type="button"
                class="btn btn-success btn-sm"
                id="btnAbrirNuevoRol">

                <i class="fas fa-plus"></i>

                Nuevo Rol

            </button>

            <a
                href="reporte_general.jsp?modulo=roles"
                target="_blank"
                class="btn btn-info btn-sm">

                <i class="fas fa-file-pdf"></i>

                Reporte General

            </a>

        </div>

    </div>


    <!-- CUERPO -->

    <div class="card-body">

        <div id="listaRoles">

            <div class="text-center p-3">

                <i class="fas fa-spinner fa-spin"></i>

                Cargando roles...

            </div>

        </div>

    </div>

</div>


<!-- MODAL NUEVO -->

<div
    class="modal fade"
    id="modalNuevoRol"
    tabindex="-1">

    <div class="modal-dialog">

        <div class="modal-content">

            <div class="modal-header">

                <h5 class="modal-title">

                    <i class="fas fa-plus-circle"></i>

                    Nuevo Rol

                </h5>

                <button
                    type="button"
                    class="close"
                    data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <div class="modal-body">

                <form id="formNuevoRol">

                    <div class="form-group">

                        <label>

                            Nombre del rol
                            <span class="text-danger">*</span>

                        </label>

                        <input
                            type="text"
                            name="nombre_rol"
                            id="nuevoNombreRol"
                            class="form-control"
                            maxlength="50"
                            required>

                    </div>


                    <div class="form-group">

                        <label>

                            Descripción

                        </label>

                        <textarea
                            name="descripcion"
                            id="nuevaDescripcion"
                            class="form-control"
                            rows="3"
                            maxlength="255"></textarea>

                    </div>

                </form>

            </div>


            <div class="modal-footer">

                <button
                    type="button"
                    class="btn btn-secondary"
                    data-dismiss="modal">

                    Cancelar

                </button>

                <button
                    type="button"
                    class="btn btn-success"
                    id="btnGuardarRol">

                    <i class="fas fa-save"></i>

                    Guardar

                </button>

            </div>

        </div>

    </div>

</div>


<!-- MODAL EDITAR -->

<div
    class="modal fade"
    id="modalEditarRol"
    tabindex="-1">

    <div class="modal-dialog">

        <div class="modal-content">

            <div class="modal-header">

                <h5 class="modal-title">

                    <i class="fas fa-edit"></i>

                    Modificar Rol

                </h5>

                <button
                    type="button"
                    class="close"
                    data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <div class="modal-body">

                <form id="formEditarRol">

                    <input
                        type="hidden"
                        name="id_rol"
                        id="editIdRol">


                    <div class="form-group">

                        <label>

                            Nombre del rol
                            <span class="text-danger">*</span>

                        </label>

                        <input
                            type="text"
                            name="nombre_rol"
                            id="editNombreRol"
                            class="form-control"
                            maxlength="50"
                            required>

                    </div>


                    <div class="form-group">

                        <label>

                            Descripción

                        </label>

                        <textarea
                            name="descripcion"
                            id="editDescripcion"
                            class="form-control"
                            rows="3"
                            maxlength="255"></textarea>

                    </div>

                </form>

            </div>


            <div class="modal-footer">

                <button
                    type="button"
                    class="btn btn-secondary"
                    data-dismiss="modal">

                    Cancelar

                </button>

                <button
                    type="button"
                    class="btn btn-primary"
                    id="btnActualizarRol">

                    <i class="fas fa-save"></i>

                    Actualizar

                </button>

            </div>

        </div>

    </div>

</div>


<script>

$(document).ready(function () {

    listarRoles();


    // LISTAR

    function listarRoles() {

        $.ajax({

            url: "RolController",

            type: "POST",

            data: {
                action: "listar"
            },

            success: function (data) {

                $("#listaRoles").html(data);

                if ($.fn.DataTable.isDataTable(
                        "#tablaRoles")) {

                    $("#tablaRoles")
                        .DataTable()
                        .destroy();
                }

                $("#tablaRoles").DataTable({

                    responsive: true,


                    autoWidth: false,

                    pageLength: 10,

                    language: {

                        search: "Buscar:",

                        zeroRecords:
                            "No se encontraron registros",

                        info:
                            "Mostrando _START_ a _END_ de _TOTAL_ registros",

                        infoEmpty:
                            "No hay registros",

                        paginate: {

                            first: "Primero",

                            last: "Último",

                            next: "Siguiente",

                            previous: "Anterior"

                        }

                    },

                    });

            },

            error: function () {

                mostrarAlerta(
                    "Error al cargar los roles.",
                    "error"
                );

            }

        });

    }


    // NUEVO

    $("#btnAbrirNuevoRol").click(function () {

        $("#formNuevoRol")[0].reset();

        $("#modalNuevoRol").modal("show");

    });


    // GUARDAR

    $("#btnGuardarRol").click(function () {

        const form =
            document.getElementById(
                "formNuevoRol"
            );

        if (!form.checkValidity()) {

            form.reportValidity();

            return;
        }

        $.ajax({

            url: "RolController",

            type: "POST",

            data:
                $("#formNuevoRol").serialize()
                + "&action=insertar",

            success: function (resp) {

                if (resp.trim() === "OK") {

                    $("#modalNuevoRol")
                        .modal("hide");

                    listarRoles();

                    mostrarAlerta(
                        "Rol guardado correctamente.",
                        "success"
                    );

                } else {

                    mostrarAlerta(
                        "No se pudo guardar el rol. Verifique que el nombre no esté repetido.",
                        "error"
                    );
                }

            },

            error: function () {

                mostrarAlerta(
                    "Error de conexión con el servidor.",
                    "error"
                );

            }

        });

    });


    // ACTUALIZAR

    $("#btnActualizarRol").click(function () {

        const form =
            document.getElementById(
                "formEditarRol"
            );

        if (!form.checkValidity()) {

            form.reportValidity();

            return;
        }

        $.ajax({

            url: "RolController",

            type: "POST",

            data:
                $("#formEditarRol").serialize()
                + "&action=actualizar",

            success: function (resp) {

                if (resp.trim() === "OK") {

                    $("#modalEditarRol")
                        .modal("hide");

                    listarRoles();

                    mostrarAlerta(
                        "Rol actualizado correctamente.",
                        "success"
                    );

                } else {

                    mostrarAlerta(
                        "No se pudo actualizar el rol.",
                        "error"
                    );
                }

            },

            error: function () {

                mostrarAlerta(
                    "Error de conexión con el servidor.",
                    "error"
                );

            }

        });

    });


    // EXPONER LISTADO PARA LAS FUNCIONES GLOBALES

    window.recargarRoles = listarRoles;

});


// EDITAR ROL

function editarRol(id) {

    $.ajax({

        url: "RolController",

        type: "POST",

        data: {

            action: "buscar",

            id_rol: id

        },

        dataType: "json",

        success: function (r) {

            if (!r) {

                mostrarAlerta(
                    "No se pudo obtener el rol.",
                    "error"
                );

                return;
            }

            $("#editIdRol")
                .val(r.id_rol);

            $("#editNombreRol")
                .val(r.nombre_rol);

            $("#editDescripcion")
                .val(r.descripcion);

            $("#modalEditarRol")
                .modal("show");

        },

        error: function () {

            mostrarAlerta(
                "Error al consultar el rol.",
                "error"
            );

        }

    });

}


// DESACTIVAR

function desactivarRol(id) {

    Swal.fire({

        title: "¿Desactivar rol?",

        text:
            "El rol quedará inactivo y no estará disponible para nuevos usuarios.",

        icon: "warning",

        showCancelButton: true,

        confirmButtonText: "Sí, desactivar",

        cancelButtonText: "Cancelar",

        confirmButtonColor: "#d33"

    }).then((result) => {

        if (result.isConfirmed) {

            $.post(

                "RolController",

                {

                    action: "desactivar",

                    id_rol: id

                },

                function (resp) {

                    if (resp.trim() === "OK") {

                        if (typeof window.recargarRoles
                            === "function") {

                            window.recargarRoles();

                        }

                        mostrarAlerta(
                            "Rol desactivado correctamente.",
                            "success"
                        );

                    } else {

                        mostrarAlerta(
                            "No se pudo desactivar el rol.",
                            "error"
                        );

                    }

                }

            );

        }

    });

}


// ACTIVAR

function activarRol(id) {

    Swal.fire({

        title: "¿Activar rol?",

        text:
            "El rol volverá a estar disponible para los usuarios.",

        icon: "question",

        showCancelButton: true,

        confirmButtonText: "Sí, activar",

        cancelButtonText: "Cancelar",

        confirmButtonColor: "#28a745"

    }).then((result) => {

        if (result.isConfirmed) {

            $.post(

                "RolController",

                {

                    action: "activar",

                    id_rol: id

                },

                function (resp) {

                    if (resp.trim() === "OK") {

                        if (typeof window.recargarRoles
                            === "function") {

                            window.recargarRoles();

                        }

                        mostrarAlerta(
                            "Rol activado correctamente.",
                            "success"
                        );

                    } else {

                        mostrarAlerta(
                            "No se pudo activar el rol.",
                            "error"
                        );

                    }

                }

            );

        }

    });

}


// ALERTAS

function mostrarAlerta(mensaje, tipo) {

    Swal.fire({

        icon: tipo,

        title: "JMQ System",

        text: mensaje,

        confirmButtonText: "OK",

        confirmButtonColor: "#3085d6",

        position: "center"

    });

}

</script>