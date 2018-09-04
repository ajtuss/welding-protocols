$(document).ready(function () {

    var brandSelect = $('#brand');

    function setBrandsToSelect() {
        $.get('/api/brands')
            .done(function (res) {
                $(res).each(function (index, brand) {
                    var select = $("<option>").val(brand.id).text(brand.name);
                    brandSelect.append(select);
                });
            });
    }

    function setModelsToSelect() {
        $.get('/api/model')
            .done(function (res) {
                $(res).each(function (index, brand) {
                    var select = $("<option>").val(brand.id).text(brand.name);
                    brandSelect.append(select);
                });
            });
    }

    setBrandsToSelect();
});