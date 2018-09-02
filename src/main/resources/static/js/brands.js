$(document).ready(function () {

    var table = $('#table').DataTable({
        initComplete: function () {
        },

        dom: 'Bfrtip',
        buttons: [
            {
                text: 'Dodaj',
                className: '',
                action: function (e, dt, button, config) {
                    window.location.href = '/brands/add';
                }

            },
            {
                extend: 'selectedSingle',
                text: 'Edytuj',
                action: function (e, dt, button, config) {
                    window.location.href = '/brands/edit/'+dt.row({selected: true}).data().id;
                }
            },
            {
                extend: 'selectedSingle',
                text: 'Usuń',
                action: function (e, dt, button, config) {
                    window.location.href = '/brands/delete/'+dt.row({selected: true}).data().id;
                }
            }
        ],
        paging: true,
        select: {
            style: 'single'
        },
        ajax: {
            url: '/api/brand',
            type: 'get', //typ połączenia
            // contentType: 'application/json', //gdy wysyłamy dane czasami chcemy ustawić ich typ
            // dataType: 'json', //typ danych jakich oczekujemy w odpowiedzi
            dataSrc: ""
            // success: function (res) {
            //     // console.log(res);
            // }
        },
        columns: [
            {'data': 'id'},
            {'data': 'name'}

        ]

    });

    // table.button('2').remove();


});