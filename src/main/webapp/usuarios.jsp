<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <h3 class="card-title">
            <i class="fas fa-users-cog"></i>
            Gestión de Usuarios
        </h3>

        <div class="card-tools">

            <button
                type="button"
                class="btn btn-success btn-sm"
                id="btnAbrirNuevoUsuario">

                <i class="fas fa-user-plus"></i>
                Nuevo Usuario

            </button>

            <a
                href="reporte_general.jsp?modulo=usuarios"
                target="_blank"
                class="btn btn-info btn-sm">

                <i class="fas fa-file-pdf"></i>
                Reporte General

            </a>

        </div>

    </div>


    <!-- CUERPO -->

    <div class="card-body">

        <div id="listaUsuario">

            <div class="text-center p-3">

                <i class="fas fa-spinner fa-spin"></i>
                Cargando usuarios...

            </div>

        </div>

    </div>

</div>


<!-- MODAL NUEVO USUARIO -->

<div
    class="modal fade"
    id="modalNuevoUsuario"
    tabindex="-1"
    role="dialog">

    <div
        class="modal-dialog modal-lg"
        role="document">

        <div class="modal-content">

            <div class="modal-header">

                <h5 class="modal-title">

                    <i class="fas fa-user-plus"></i>
                    Nuevo Usuario

                </h5>

                <button
                    type="button"
                    class="close"
                    data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <div class="modal-body">

                <form id="formNuevoUsuario">

                    <div class="row">

                        <!-- Usuario -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Usuario
                                    <span class="text-danger">*</span>
                                </label>

                                <input
                                    type="text"
                                    name="nombre_usuario"
                                    id="nuevoNombreUsuario"
                                    class="form-control"
                                    maxlength="50"
                                    required>

                            </div>

                        </div>


                        <!-- Nombre completo -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Nombre completo
                                    <span class="text-danger">*</span>
                                </label>

                                <input
                                    type="text"
                                    name="nombre_completo"
                                    id="nuevoNombreCompleto"
                                    class="form-control"
                                    maxlength="150"
                                    required>

                            </div>

                        </div>


                        <!-- Correo -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Correo electrónico
                                </label>

                                <input
                                    type="email"
                                    name="correo"
                                    id="nuevoCorreo"
                                    class="form-control"
                                    maxlength="150">

                            </div>

                        </div>


                        <!-- Personal -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Personal
                                </label>

                                <select
                                    name="id_personal"
                                    id="nuevoIdPersonal"
                                    class="form-control">

                                    <option value="0">
                                        Cargando...
                                    </option>

                                </select>

                            </div>

                        </div>


                        <!-- Rol -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Rol
                                    <span class="text-danger">*</span>
                                </label>

                                <select
                                    name="id_rol"
                                    id="nuevoIdRol"
                                    class="form-control"
                                    required>

                                    <option value="0">
                                        Cargando...
                                    </option>

                                </select>

                            </div>

                        </div>


                        <!-- Contraseña -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Contraseña
                                    <span class="text-danger">*</span>
                                </label>

                                <div class="input-group">

                                    <input
                                        type="password"
                                        name="clave"
                                        id="nuevaClave"
                                        class="form-control"
                                        maxlength="255"
                                        required>

                                    <div class="input-group-append">

                                        <button
                                            type="button"
                                            class="btn btn-outline-secondary"
                                            onclick="toggleClave('nuevaClave')">

                                            <i class="fas fa-eye"></i>

                                        </button>

                                    </div>

                                </div>

                            </div>

                        </div>

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
                    id="btnGuardarUsuario">

                    <i class="fas fa-save"></i>
                    Guardar

                </button>

            </div>

        </div>

    </div>

</div>


<!-- MODAL EDITAR USUARIO -->

<div
    class="modal fade"
    id="modalEditarUsuario"
    tabindex="-1"
    role="dialog">

    <div
        class="modal-dialog modal-lg"
        role="document">

        <div class="modal-content">

            <div class="modal-header">

                <h5 class="modal-title">

                    <i class="fas fa-user-edit"></i>
                    Modificar Usuario

                </h5>

                <button
                    type="button"
                    class="close"
                    data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <div class="modal-body">

                <form id="formEditarUsuario">

                    <input
                        type="hidden"
                        name="id_usuario"
                        id="editIdUsuario">


                    <div class="row">

                        <!-- Usuario -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Usuario
                                    <span class="text-danger">*</span>
                                </label>

                                <input
                                    type="text"
                                    name="nombre_usuario"
                                    id="editNombreUsuario"
                                    class="form-control"
                                    maxlength="50"
                                    required>

                            </div>

                        </div>


                        <!-- Nombre completo -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Nombre completo
                                    <span class="text-danger">*</span>
                                </label>

                                <input
                                    type="text"
                                    name="nombre_completo"
                                    id="editNombreCompleto"
                                    class="form-control"
                                    maxlength="150"
                                    required>

                            </div>

                        </div>


                        <!-- Correo -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Correo electrónico
                                </label>

                                <input
                                    type="email"
                                    name="correo"
                                    id="editCorreo"
                                    class="form-control"
                                    maxlength="150">

                            </div>

                        </div>


                        <!-- Personal -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Personal
                                </label>

                                <select
                                    name="id_personal"
                                    id="editIdPersonal"
                                    class="form-control">

                                </select>

                            </div>

                        </div>


                        <!-- Rol -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Rol
                                    <span class="text-danger">*</span>
                                </label>

                                <select
                                    name="id_rol"
                                    id="editIdRol"
                                    class="form-control"
                                    required>

                                </select>

                            </div>

                        </div>


                        <!-- Nueva contraseña -->

                        <div class="col-md-6">

                            <div class="form-group">

                                <label>
                                    Nueva contraseña
                                </label>

                                <div class="input-group">

                                    <input
                                        type="password"
                                        name="clave"
                                        id="editClave"
                                        class="form-control"
                                        maxlength="255">

                                    <div class="input-group-append">

                                        <button
                                            type="button"
                                            class="btn btn-outline-secondary"
                                            onclick="toggleClave('editClave')">

                                            <i class="fas fa-eye"></i>

                                        </button>

                                    </div>

                                </div>

                                <small class="form-text text-muted">

                                    Dejar vacío para conservar la contraseña actual.

                                </small>

                            </div>

                        </div>

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
                    id="btnActualizarUsuario">

                    <i class="fas fa-save"></i>
                    Actualizar

                </button>

            </div>

        </div>

    </div>

</div>


<script>

$(document).ready(function () {

    listarUsuarios();

    cargarPersonal("#nuevoIdPersonal");
    cargarPersonal("#editIdPersonal");

    cargarRoles("#nuevoIdRol");
    cargarRoles("#editIdRol");


    // LISTAR USUARIOS

    function listarUsuarios() {

        $.ajax({

            url: "UsuarioController",

            type: "POST",

            data: {
                action: "listar"
            },

            success: function (data) {

                $("#listaUsuario").html(data);

                if ($.fn.DataTable.isDataTable("#tablaUsuarios")) {

                    $("#tablaUsuarios")
                        .DataTable()
                        .destroy();

                }

                $("#tablaUsuarios").DataTable({

                    responsive: true,


                    autoWidth: false,

                    pageLength: 10,

                    language: {

                        search: "Buscar:",

                        lengthMenu:
                            "Mostrar _MENU_ registros",

                        info:
                            "Mostrando _START_ a _END_ de _TOTAL_ registros",

                        infoEmpty:
                            "No hay registros",

                        zeroRecords:
                            "No se encontraron registros",

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
                    "Error al cargar los usuarios.",
                    "error"
                );

            }

        });

    }


    // NUEVO USUARIO

    $("#btnAbrirNuevoUsuario").click(function () {

        $("#formNuevoUsuario")[0].reset();

        $("#nuevoIdPersonal").val("0");

        $("#nuevoIdRol").val("0");

        $("#modalNuevoUsuario").modal("show");

    });


    // GUARDAR

    $("#btnGuardarUsuario").click(function () {

        const form =
            document.getElementById("formNuevoUsuario");

        if (!form.checkValidity()) {

            form.reportValidity();

            return;
        }

        $.ajax({

            url: "UsuarioController",

            type: "POST",

            data:
                $("#formNuevoUsuario").serialize()
                + "&action=insertar",

            success: function (resp) {

                if (resp.trim() === "OK") {

                    $("#modalNuevoUsuario")
                        .modal("hide");

                    listarUsuarios();

                    mostrarAlerta(
                        "Usuario guardado correctamente.",
                        "success"
                    );

                } else {

                    mostrarAlerta(
                        "No se pudo guardar el usuario. Verifique que el nombre de usuario no esté repetido.",
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

    $("#btnActualizarUsuario").click(function () {

        const form =
            document.getElementById("formEditarUsuario");

        if (!form.checkValidity()) {

            form.reportValidity();

            return;
        }

        $.ajax({

            url: "UsuarioController",

            type: "POST",

            data:
                $("#formEditarUsuario").serialize()
                + "&action=actualizar",

            success: function (resp) {

                if (resp.trim() === "OK") {

                    $("#modalEditarUsuario")
                        .modal("hide");

                    listarUsuarios();

                    mostrarAlerta(
                        "Usuario actualizado correctamente.",
                        "success"
                    );

                } else {

                    mostrarAlerta(
                        "No se pudo actualizar el usuario.",
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

});


// CARGAR PERSONAL

function cargarPersonal(selector) {

    $.ajax({

        url: "UsuarioController",

        type: "POST",

        data: {
            action: "listarPersonal"
        },

        success: function (data) {

            $(selector).html(data);

        },

        error: function () {

            $(selector).html(
                "<option value='0'>Error al cargar</option>"
            );

        }

    });

}


// CARGAR ROLES

function cargarRoles(selector) {

    $.ajax({

        url: "UsuarioController",

        type: "POST",

        data: {
            action: "listarRoles"
        },

        success: function (data) {

            $(selector).html(data);

        },

        error: function () {

            $(selector).html(
                "<option value='0'>Error al cargar</option>"
            );

        }

    });

}


// EDITAR USUARIO

function editarUsuario(id) {

    $.ajax({

        url: "UsuarioController",

        type: "POST",

        data: {

            action: "buscar",

            id_usuario: id

        },

        dataType: "json",

        success: function (u) {

            if (!u) {

                mostrarAlerta(
                    "No se pudo obtener el usuario.",
                    "error"
                );

                return;
            }


            $("#editIdUsuario")
                .val(u.id_usuario);

            $("#editNombreUsuario")
                .val(u.nombre_usuario);

            $("#editNombreCompleto")
                .val(u.nombre_completo);

            $("#editCorreo")
                .val(u.correo);

            $("#editIdPersonal")
                .val(u.id_personal);

            $("#editIdRol")
                .val(u.id_rol);

            $("#editClave")
                .val("");


            $("#modalEditarUsuario")
                .modal("show");

        },

        error: function () {

            mostrarAlerta(
                "Error al consultar el usuario.",
                "error"
            );

        }

    });

}


// DESACTIVAR

function desactivarUsuario(id) {

    Swal.fire({

        title: "¿Desactivar usuario?",

        text:
            "El usuario no podrá iniciar sesión mientras esté inactivo.",

        icon: "warning",

        showCancelButton: true,

        confirmButtonText: "Sí, desactivar",

        cancelButtonText: "Cancelar",

        confirmButtonColor: "#d33"

    }).then((result) => {

        if (result.isConfirmed) {

            $.post(

                "UsuarioController",

                {

                    action: "desactivar",

                    id_usuario: id

                },

                function (resp) {

                    if (resp.trim() === "OK") {

                        listarUsuariosGlobal();

                        mostrarAlerta(
                            "Usuario desactivado correctamente.",
                            "success"
                        );

                    } else {

                        mostrarAlerta(
                            "No se pudo desactivar el usuario.",
                            "error"
                        );

                    }

                }

            );

        }

    });

}


// ACTIVAR

function activarUsuario(id) {

    Swal.fire({

        title: "¿Activar usuario?",

        text:
            "El usuario podrá volver a iniciar sesión.",

        icon: "question",

        showCancelButton: true,

        confirmButtonText: "Sí, activar",

        cancelButtonText: "Cancelar",

        confirmButtonColor: "#28a745"

    }).then((result) => {

        if (result.isConfirmed) {

            $.post(

                "UsuarioController",

                {

                    action: "activar",

                    id_usuario: id

                },

                function (resp) {

                    if (resp.trim() === "OK") {

                        listarUsuariosGlobal();

                        mostrarAlerta(
                            "Usuario activado correctamente.",
                            "success"
                        );

                    } else {

                        mostrarAlerta(
                            "No se pudo activar el usuario.",
                            "error"
                        );

                    }

                }

            );

        }

    });

}


// RECARGAR TABLA

function listarUsuariosGlobal() {

    $.post(

        "UsuarioController",

        {
            action: "listar"
        },

        function (data) {

            $("#listaUsuario").html(data);

            if ($.fn.DataTable.isDataTable("#tablaUsuarios")) {

                $("#tablaUsuarios")
                    .DataTable()
                    .destroy();

            }

            $("#tablaUsuarios").DataTable({

                responsive: true,


                autoWidth: false,

                pageLength: 10,

                language: {

                    search: "Buscar:",

                    zeroRecords:
                        "No se encontraron registros",

                    info:
                        "Mostrando _START_ a _END_ de _TOTAL_ registros",

                    paginate: {

                        next: "Siguiente",

                        previous: "Anterior"

                    }

                },

                });

        }

    );

}


// MOSTRAR / OCULTAR CONTRASEÑA

function toggleClave(idCampo) {

    const input =
        document.getElementById(idCampo);

    if (input.type === "password") {

        input.type = "text";

    } else {

        input.type = "password";

    }

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