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
                    window.location.href = '/machines/' + dt.row({selected: true}).data().id + '/delete';
                }
            }
        ],
        paging: true,
        select: {
            style: 'single'
        },
        ajax: {
            url: '/api/mechine',
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
            {'data': 'shortName'},
            {
                'data': 'fullName'
            },
            {
                'data': 'nip'
            },
            {
                'data': 'zip',
                'width': '7%'
            },
            {
                'data': 'city',
                'width': '7%'
            },
            {
                'data': 'street',
                'width': '7%'
            },
            {
                'data': 'email',
                'width': '7%'
            }

        ]

    });

});