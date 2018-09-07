$(document).ready(function () {

    var validationId = $('#form').data('id');

    // var table = $('#table tbody');

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
        paging: false,
        select: {
            style: 'single'
        },
        ajax: {
            url: '/api/validations/' + validationId + '/measures',
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
            {'data': 'iAdjust'},
            {'data': 'uAdjust'},
            {'data': 'iPower'},
            {'data': 'uPower'},
            {'data': 'iValid'},
            {'data': 'uValid'}
        ]


    });

    function myCallbackFunction(updatedCell, updatedRow, oldValue) {
        console.log("The new value for the cell is: " + updatedCell.data());
        console.log("The values for each cell in that row are: " + updatedRow.data());
    }

    // table.MakeCellsEditable({
    //     "onUpdate": myCallbackFunction,
    //     "columns": [3, 4, 5, 6],
    //     "inputTypes": [
    //         {
    //             "column": 3,
    //             "type": "text",
    //             "options": null
    //         }
    //     ]
    // });

    // addRowsWithMesures();


});