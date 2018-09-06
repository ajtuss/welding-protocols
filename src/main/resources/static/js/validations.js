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
                    window.location.href = '/validations/add';
                }

            },
            {
                extend: 'selectedSingle',
                text: 'Edytuj',
                action: function (e, dt, button, config) {
                    window.location.href = '/validations/' + dt.row({selected: true}).data().id;
                }
            },
            {
                extend: 'selectedSingle',
                text: 'Usuń',
                action: function (e, dt, button, config) {
                    $.ajax({
                        type: 'delete',
                        url: '/api/validations/' + dt.row({selected: true}).data().id,
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
            url: '/api/validations',
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
            {'data': 'protocolNumber'},
            {'data': 'machineWelderModelBrandName'},
            {'data': 'machineWelderModelName'},
            {'data': 'type'},
            {'data': 'machineSerialNumber'},
            {'data': 'machineCustomerShortName'},
            {'data': 'dateValidation'},
            {'data': 'nextValidation'},
            {
                'data': 'finalized',
                'width': '5%'
            },
            {
                'data': 'result',
                'width': '5%'
            }
        ]

    });



});