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
                    window.location.href = '/brands/'+dt.row({selected: true}).data().id;
                }
            },
            {
                extend: 'selectedSingle',
                text: 'Usuń',
                action: function (e, dt, button, config) {
                    $.ajax({
                        type:'delete',
                        url:'/api/brands/'+ dt.row({selected: true}).data().id,
                        // data:'delete_id='+del_id,
                        success: function(data)
                        {
                            location.reload();
                        },
                        statusCode: {
                            404: function() {
                                alert( "page not found" );
                            },
                            500: function() {
                                alert( "page not found" );
                            }
                        }
                    });
                    // window.location.href = '/brands/delete/'+dt.row({selected: true}).data().id;
                }
            }
        ],
        paging: true,
        select: {
            style: 'single'
        },
        ajax: {
            url: '/api/brands',
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