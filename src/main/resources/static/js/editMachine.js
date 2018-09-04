$(document).ready(function () {

    var brandSelect = $('#brand');
    var modelSelect = $('#model');
    var customerSelect = $('#customer');

    function setBrandsToSelect() {
        $.get('/api/brands')
            .done(function (res) {
                $(res).each(function (index, brand) {
                    var select = $("<option>").val(brand.id).text(brand.name);
                    brandSelect.append(select);
                });
                brandSelect.val(brandSelect.data('id')).trigger('change');
            });
    }

    function setCustomersToSelect() {
        $.get('/api/customers')
            .done(function (res) {
                $(res).each(function (index, customer) {
                    var select = $("<option>").val(customer.id).text(customer.shortName);
                    customerSelect.append(select);
                });
            });
    }

    var setModelsToSelect = function () {
        var brandId = brandSelect.val();
        modelSelect.empty();
        if (brandId !== "") {
            $.get('/api/brands/' + brandSelect.val() + '/models')
                .done(function (res) {
                    $(res).each(function (index, model) {
                        var select = $("<option>").val(model.id).text(model.name);
                        modelSelect.append(select);
                    });
                    modelSelect.val(modelSelect.data('id'));
                });
        }
    };

    setBrandsToSelect();
    setCustomersToSelect();

    brandSelect.change( 'change',function () {
        // brandSelect.find('[value=""]').remove();
        setModelsToSelect();
    });
});