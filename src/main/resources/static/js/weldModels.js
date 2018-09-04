$(document).ready(function () {

    var checked = '<span data-feather="check-square"></span>';
    var unChecked = '<span data-feather="square"></span>';

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
                    window.location.href = '/weldmodels/add';
                }

            },
            {
                extend: 'selectedSingle',
                text: 'Edytuj',
                action: function (e, dt, button, config) {
                    window.location.href = '/weldmodels/'+dt.row({selected: true}).data().id;
                }
            },
            {
                extend: 'selectedSingle',
                text: 'Usuń',
                action: function (e, dt, button, config) {
                    window.location.href = '/weldmodels/'+dt.row({selected: true}).data().id+'/delete';
                }
            }
        ],
        paging: true,
        select: {
            style: 'single'
        },
        ajax: {
            url: '/api/weldmodels',
            type: 'get', //typ połączenia
            // contentType: 'application/json', //gdy wysyłamy dane czasami chcemy ustawić ich typ
            // dataType: 'json', //typ danych jakich oczekujemy w odpowiedzi
            dataSrc: ""
            // success: function (res) {
            //     // console.log(res);
            // }
        },
        columns: [
            {'data': 'id',
            'width': '5%'},
            {
                'data': 'brandName',
                'width': '15%'
            },
            {'data': 'name'},
            {
                'data': 'mig',
                'width': '7%',
                "render": function (data, type, row) {
                    return (data === true) ? checked : unChecked;
                }
            },
            {
                'data': 'mma',
                'width': '7%',
                "render": function (data, type, row) {
                    return (data === true) ? checked : unChecked;
                }
            },
            {
                'data': 'tig',
                'width': '7%',
                "render": function (data, type, row) {
                    return (data === true) ? checked : unChecked;
                }
            },
            {
                'data': 'currentMeter',
                'width': '7%',
                "render": function (data, type, row) {
                    return (data === true) ? checked : unChecked;
                }
            },
            {
                'data': 'voltageMeter',
                'width': '7%',
                "render": function (data, type, row) {
                    return (data === true) ? checked : unChecked;
                }
            }

        ]

    });

    // table.button('2').remove();


});