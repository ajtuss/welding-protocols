$(document).ready(function () {


    var table = $('#table').DataTable({
        initComplete: function () {
            feather.replace();
        },

        dom: 'Bfrtip',
        buttons: [
            {
                text: 'Dodaj',
                className: '',
                action: function (e, dt, button, config) {
                    window.location.href = '/machines/add';
                }

            },
            {
                extend: 'selectedSingle',
                text: 'Edytuj',
                action: function (e, dt, button, config) {
                    window.location.href = '/machines/' + dt.row({selected: true}).data().id;
                }
            },
            {
                extend: 'selectedSingle',
                text: 'Usuń',
                action: function (e, dt, button, config) {
                    $.ajax({
                        type: 'delete',
                        url: '/api/machines/' + dt.row({selected: true}).data().id,
                        success: function (data) {
                            location.reload();
                        },
                        statusCode: {
                            404: function () {
                                alert("page not found");
                            },
                            500: function () {
                                alert("error 500");
                            }
                        }
                    });
                }
            }
        ],
        paging: true,
        select: {
            style: 'single'
        },
        ajax: {
            url: '/api/machines',
            type: 'get', //typ połączenia
            // contentType: 'application/json', //gdy wysyłamy dane czasami chcemy ustawić ich typ
            // dataType: 'json', //typ danych jakich oczekujemy w odpowiedzi
            dataSrc: ""
            // success: function (res) {
            //     // console.log(res);
            // }
        },
        columns: [
            {
                'data': 'id',
                'width': '5%'
            },
            {'data': 'welderModelBrandName'},
            {'data': 'welderModelName'},
            {'data': 'serialNumber'},
            {'data': 'customerShortName'}
        ]

    });

    new $.fn.dataTable.Buttons(table, {
        buttons: [
            {
                extend: 'selectedSingle',
                text: 'Dodaj walidację',
                action: function (e, dt, node, conf) {
                    window.location.href = '/validations/add?machineId=' + dt.row({selected: true}).data().id;
                }
            }
        ]
    });

    table.buttons(1, null).container().appendTo(
        table.table().container()
    );

});