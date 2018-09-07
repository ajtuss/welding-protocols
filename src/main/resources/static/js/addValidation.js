$(document).ready(function () {

    var modelId = $('#model').data('id');
    var methodSelect = $('#method');


    function setModelToCheckbox() {
        $.get('/api/weldmodels/' + modelId)
            .done(function (res) {
                $(res).each(function (index, model) {
                    if (model.mig) {
                        methodSelect.append($("<option>").text('MIG'));
                    }
                    if (model.mma) {
                        methodSelect.append($("<option>").text('MMA'));
                    }
                    if (model.tig) {
                        methodSelect.append($("<option>").text('TIG'));
                    }
                });
            });
    }

    setModelToCheckbox();

});