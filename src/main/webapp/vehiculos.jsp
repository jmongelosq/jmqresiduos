<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="card">

    <!-- ENCABEZADO -->

    <div class="card-header">

        <div class="d-flex justify-content-between align-items-center">

            <h3 class="card-title">
                <i class="fas fa-truck"></i>
                Gestión de Vehículos
            </h3>

            <div>

                <!-- NUEVO VEHÍCULO -->

                <button type="button"
                        class="btn btn-success btn-sm"
                        onclick="nuevoVehiculo()">

                    <i class="fas fa-plus"></i>
                    Nuevo Vehículo

                </button>


                <!-- REPORTE GENERAL -->

                <a href="reporte_general.jsp?modulo=vehiculos"
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

        <table id="tablaVehiculos"
               class="table table-bordered table-striped table-hover"
               style="width:100%">

            <thead>

                <tr>

                    <th>ID</th>
                    <th>Chapa</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Año</th>
                    <th>Capacidad</th>
                    <th>Estado</th>
                    <th>Acciones</th>

                </tr>

            </thead>

            <tbody id="tbodyVehiculos"></tbody>

        </table>

    </div>

</div>


<!-- MODAL NUEVO / MODIFICAR -->

<div class="modal fade"
     id="modalVehiculo"
     tabindex="-1"
     role="dialog"
     aria-hidden="true">

    <div class="modal-dialog"
         role="document">

        <div class="modal-content">


            <!-- HEADER -->

            <div class="modal-header bg-primary text-white">

                <h5 class="modal-title">

                    <i class="fas fa-truck"></i>

                    <span id="tituloModalVehiculo">
                        Nuevo Vehículo
                    </span>

                </h5>


                <button type="button"
                        class="close text-white"
                        data-dismiss="modal">

                    <span>&times;</span>

                </button>

            </div>


            <!-- BODY -->

            <div class="modal-body">

                <form id="formVehiculo">

                    <input type="hidden"
                           id="id_vehiculo"
                           name="id_vehiculo">


                    <!-- CHAPA -->

                    <div class="form-group">

                        <label>
                            Chapa
                        </label>

                        <input type="text"
                               class="form-control"
                               id="matricula"
                               name="matricula"
                               maxlength="20"
                               required>

                    </div>


                    <!-- MODELO -->

                    <div class="form-group">

                        <label>
                            Modelo
                        </label>

                        <select class="form-control"
                                id="id_modelo"
                                name="id_modelo"
                                required>

                            <option value="">
                                Seleccione...
                            </option>

                        </select>

                    </div>


                    <!-- AÑO -->

                    <div class="form-group">

                        <label>
                            Año
                        </label>

                        <input type="number"
                               class="form-control"
                               id="anio"
                               name="anio"
                               min="1900"
                               max="2100"
                               required>

                    </div>


                    <!-- CAPACIDAD -->

                    <div class="form-group">

                        <label>
                            Capacidad
                        </label>

                        <input type="number"
                               class="form-control"
                               id="capacidad"
                               name="capacidad"
                               step="0.01"
                               min="0"
                               required>

                    </div>


                    <!-- ESTADO -->

                    <div class="form-group">

                        <label>
                            Estado
                        </label>

                        <select class="form-control"
                                id="estado"
                                name="estado">

                            <option value="DISPONIBLE">
                                DISPONIBLE
                            </option>

                            <option value="ASIGNADO">
                                ASIGNADO
                            </option>

                            <option value="MANTENIMIENTO">
                                MANTENIMIENTO
                            </option>

                            <option value="FUERA_SERVICIO">
                                FUERA DE SERVICIO
                            </option>

                            <option value="INACTIVO">
                                INACTIVO
                            </option>

                        </select>

                    </div>

                </form>

            </div>


            <!-- FOOTER -->

            <div class="modal-footer">

                <button type="button"
                        class="btn btn-secondary"
                        data-dismiss="modal">

                    <i class="fas fa-times"></i>
                    Cancelar

                </button>


                <button type="button"
                        class="btn btn-primary"
                        onclick="guardarVehiculo()">

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
 * Este JSP se carga dentro de dashboard.jsp mediante AJAX.
 * Por eso no utilizamos $(document).ready().
 */

cargarVehiculos();
cargarModelos();


/* CARGAR VEHÍCULOS */

function cargarVehiculos() {

    $.ajax({

        url: 'VehiculoController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var tbody =
                $('#tbodyVehiculos');



            /* Destruir DataTable anterior */

            if (
                $.fn.DataTable &&
                $.fn.DataTable.isDataTable(
                    '#tablaVehiculos'
                )
            ) {

                $('#tablaVehiculos')
                    .DataTable()
                    .destroy();
            }

            tbody.empty();


            /* Cargar registros */

            $.each(data, function (i, v) {

                var estado =
                    v.estado != null &&
                    v.estado !== ''
                        ? String(v.estado).toUpperCase()
                        : 'SIN ESTADO';


                /* CLASE DEL ESTADO */

                var claseEstado = 'badge-secondary';

                if (estado === 'DISPONIBLE') {

                    claseEstado = 'badge-success';

                } else if (estado === 'ASIGNADO') {

                    claseEstado = 'badge-info';

                } else if (estado === 'MANTENIMIENTO') {

                    claseEstado = 'badge-warning';

                } else if (estado === 'FUERA_SERVICIO') {

                    claseEstado = 'badge-danger';

                } else if (estado === 'INACTIVO') {

                    claseEstado = 'badge-secondary';
                }


                var fila = '';

                fila += '<tr>';


                /* ID */

                fila += '<td>';

                fila +=
                    v.id_vehiculo != null
                        ? v.id_vehiculo
                        : '';

                fila += '</td>';


                /* CHAPA */

                fila += '<td>';

                fila +=
                    v.matricula != null
                        ? v.matricula
                        : '';

                fila += '</td>';


                /* MARCA */

                fila += '<td>';

                fila +=
                    v.marca != null
                        ? v.marca
                        : '';

                fila += '</td>';


                /* MODELO */

                fila += '<td>';

                fila +=
                    v.nombre_modelo != null
                        ? v.nombre_modelo
                        : '';

                fila += '</td>';


                /* AÑO */

                fila += '<td>';

                fila +=
                    v.anio != null
                        ? v.anio
                        : '';

                fila += '</td>';


                /* CAPACIDAD */

                fila += '<td>';

                fila +=
                    v.capacidad != null
                        ? v.capacidad
                        : '';

                fila += '</td>';


                /* ESTADO */

                fila += '<td>';

                fila +=
                    '<span class="badge '
                    + claseEstado
                    + '">';

                fila += estado;

                fila += '</span>';

                fila += '</td>';


                /* ACCIONES */

                fila +=
                    '<td class="text-center">';


                /* MODIFICAR */

                fila +=
                    '<button type="button" '
                    + 'class="btn btn-warning btn-sm mr-1" '
                    + 'title="Modificar" '
                    + 'onclick="editarVehiculo('
                    + v.id_vehiculo
                    + ')">';

                fila +=
                    '<i class="fas fa-edit"></i>';

                fila += '</button>';


                /* VER */

                fila +=
                    '<a href="VehiculoController?'
                    + 'accion=reporteIndividual'
                    + '&id='
                    + v.id_vehiculo
                    + '" '
                    + 'target="_blank" '
                    + 'class="btn btn-info btn-sm mr-1" '
                    + 'title="Ver">';

                fila +=
                    '<i class="fas fa-eye"></i>';

                fila += '</a>';


                /* ELIMINAR */

                fila +=
                    '<button type="button" '
                    + 'class="btn btn-danger btn-sm" '
                    + 'title="Eliminar" '
                    + 'onclick="eliminarVehiculo('
                    + v.id_vehiculo
                    + ')">';

                fila +=
                    '<i class="fas fa-trash"></i>';

                fila += '</button>';


                fila += '</td>';

                fila += '</tr>';


                tbody.append(fila);

            });


            /* DATATABLE */

            $('#tablaVehiculos').DataTable({

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

                    paginate: {

                        first: "Primero",

                        last: "Último",

                        next: "Siguiente",

                        previous: "Anterior"
                    }
                },

                pageLength: 10,

                responsive: true,

                autoWidth: false
            });

        },


        error: function (xhr) {

            console.error(
                'Error Vehículos:',
                xhr.responseText
            );

            if (
                typeof Swal !== 'undefined'
            ) {

                Swal.fire(
                    'Error',
                    'No se pudieron cargar los vehículos.',
                    'error'
                );

            } else {

                alert(
                    'No se pudieron cargar los vehículos.'
                );
            }
        }
    });
}


/* CARGAR MODELOS */

function cargarModelos() {

    $.ajax({

        url: 'ModeloController',

        type: 'POST',

        data: {
            action: 'listarOptions'
        },

        dataType: 'html',

        success: function (data) {

            // El controlador devuelve las opciones ya armadas
            $('#id_modelo').html(data);

        },


        error: function (xhr) {

            console.error(
                'Error Modelos:',
                xhr.responseText
            );
        }
    });
}


/* NUEVO VEHÍCULO */

function nuevoVehiculo() {

    $('#formVehiculo')[0].reset();

    $('#id_vehiculo')
        .val('');

    $('#estado')
        .val('DISPONIBLE');

    $('#id_modelo')
        .val('');

    $('#tituloModalVehiculo')
        .text('Nuevo Vehículo');

    $('#modalVehiculo')
        .modal('show');
}


/* MODIFICAR VEHÍCULO */

function editarVehiculo(id) {

    $.ajax({

        url: 'VehiculoController',

        type: 'POST',

        data: {
            accion: 'listar'
        },

        dataType: 'json',

        success: function (data) {

            var vehiculo = null;


            $.each(data, function (i, v) {

                if (
                    parseInt(v.id_vehiculo)
                    ===
                    parseInt(id)
                ) {

                    vehiculo = v;

                    return false;
                }

            });


            if (vehiculo === null) {

                Swal.fire(
                    'Error',
                    'No se encontró el vehículo seleccionado.',
                    'error'
                );

                return;
            }


            $('#id_vehiculo')
                .val(
                    vehiculo.id_vehiculo
                );


            $('#matricula')
                .val(
                    vehiculo.matricula || ''
                );


            $('#anio')
                .val(
                    vehiculo.anio || ''
                );


            $('#capacidad')
                .val(
                    vehiculo.capacidad || ''
                );


            $('#id_modelo')
                .val(
                    vehiculo.id_modelo
                );


            $('#estado')
                .val(
                    vehiculo.estado ||
                    'DISPONIBLE'
                );


            $('#tituloModalVehiculo')
                .text('Modificar Vehículo');


            $('#modalVehiculo')
                .modal('show');

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );

            Swal.fire(
                'Error',
                'No se pudo obtener el vehículo.',
                'error'
            );
        }
    });
}


/* GUARDAR VEHÍCULO */

function guardarVehiculo() {

    var id =
        $('#id_vehiculo')
            .val();


    var accion =
        id === ''
            ? 'insertar'
            : 'actualizar';


    var matricula =
        $('#matricula')
            .val()
            .trim();


    var idModelo =
        $('#id_modelo')
            .val();


    if (matricula === '') {

        Swal.fire(
            'Atención',
            'Debe ingresar la chapa del vehículo.',
            'warning'
        );

        $('#matricula')
            .focus();

        return;
    }


    if (idModelo === '') {

        Swal.fire(
            'Atención',
            'Debe seleccionar un modelo.',
            'warning'
        );

        $('#id_modelo')
            .focus();

        return;
    }


    var datos =
        $('#formVehiculo')
            .serialize();


    datos +=
        '&accion=' + accion;


    $.ajax({

        url: 'VehiculoController',

        type: 'POST',

        data: datos,

        dataType: 'json',

        success: function (respuesta) {

            if (
                respuesta.success
            ) {

                $('#modalVehiculo')
                    .modal('hide');


                Swal.fire({

                    icon: 'success',

                    title: 'Correcto',

                    text:
                        accion === 'insertar'
                            ? 'Vehículo registrado correctamente.'
                            : 'Vehículo actualizado correctamente.',

                    timer: 1500,

                    showConfirmButton: false

                });


                cargarVehiculos();

            } else {

                Swal.fire(
                    'Error',
                    'No se pudo guardar el vehículo.',
                    'error'
                );
            }

        },


        error: function (xhr) {

            console.error(
                xhr.responseText
            );

            Swal.fire(
                'Error',
                'Error al comunicarse con el servidor.',
                'error'
            );
        }
    });
}


/* ELIMINAR - BAJA LÓGICA */

function eliminarVehiculo(id) {

    Swal.fire({

        title:
            '¿Eliminar vehículo?',

        text:
            'El registro no será borrado físicamente. '
            + 'Pasará a estado INACTIVO.',

        icon:
            'warning',

        showCancelButton:
            true,

        confirmButtonColor:
            '#dc3545',

        cancelButtonColor:
            '#6c757d',

        confirmButtonText:
            'Sí, eliminar',

        cancelButtonText:
            'Cancelar'

    }).then(function (result) {

        if (!result.isConfirmed) {
            return;
        }


        $.ajax({

            url: 'VehiculoController',

            type: 'POST',

            data: {

                accion: 'Borrar',

                id: id

            },

            dataType: 'json',

            success: function (respuesta) {

                if (
                    respuesta.success
                ) {

                    Swal.fire({

                        icon:
                            'success',

                        title:
                            'Eliminado',

                        text:
                            'El vehículo pasó a estado INACTIVO.',

                        timer:
                            1500,

                        showConfirmButton:
                            false

                    });


                    cargarVehiculos();

                } else {

                    Swal.fire(
                        'Error',
                        'No se pudo eliminar el vehículo.',
                        'error'
                    );
                }

            },


            error: function (xhr) {

                console.error(
                    xhr.responseText
                );

                Swal.fire(
                    'Error',
                    'No se pudo procesar la baja.',
                    'error'
                );
            }

        });

    });
}

</script>