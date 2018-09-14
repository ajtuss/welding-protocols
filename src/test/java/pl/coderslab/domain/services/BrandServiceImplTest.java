package pl.coderslab.domain.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.dto.BrandDTO;
import pl.coderslab.domain.dto.WelderModelDTO;
import pl.coderslab.domain.entities.Brand;
import pl.coderslab.domain.entities.WelderModel;
import pl.coderslab.domain.exceptions.BrandNotFoundException;
import pl.coderslab.domain.repositories.BrandRepository;
import pl.coderslab.domain.repositories.WelderModelRepository;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@Import({BrandServiceImpl.class, ModelMapper.class})
public class BrandServiceImplTest {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private WelderModelRepository modelRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BrandService brandService;

    private final static BrandDTO BRAND_CREATION = new BrandDTO("Kemppi");
    private final static BrandDTO BRAND_CREATION_2 = new BrandDTO("Fronius");
    private final static BrandDTO BRAND_CREATION_3 = new BrandDTO("EWM");


    @Test
    public void expectedTrueAfterSaveBrand() {
        BrandDTO result = brandService.saveBrand(BRAND_CREATION);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(BRAND_CREATION.getName(), result.getName());
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getModificationDate());
        assertNotNull(result.getVersionId());
    }

    @Test
    public void expectedTrueAfterUpdateBrand() {
        BrandDTO saved = brandService.saveBrand(BRAND_CREATION);
        saved.setName("Fronius");

        BrandDTO result = brandService.updateBrand(saved);

        assertNotNull(result);
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getName(), result.getName());
        assertEquals(saved.getCreationDate(), result.getCreationDate());
        assertNotEquals(saved.getModificationDate(), result.getModificationDate());
        assertNotEquals(saved.getVersionId(), result.getVersionId());
    }

    @Test
    public void expectedTrueAfterFindById() {
        BrandDTO expected = brandService.saveBrand(BRAND_CREATION);

        BrandDTO found = brandService.findById(expected.getId());

        assertEquals(expected, found);
    }

    @Test(expected = BrandNotFoundException.class)
    public void expectedExceptionAfterFindById() {
        brandService.findById(Long.MAX_VALUE);
    }

    @Test
    public void expectedTrueAfterFindAll() {
        BrandDTO brand1 = brandService.saveBrand(BRAND_CREATION);
        BrandDTO brand2 = brandService.saveBrand(BRAND_CREATION_2);
        BrandDTO brand3 = brandService.saveBrand(BRAND_CREATION_3);

        List<BrandDTO> found = brandService.findAll();

        assertThat(found, containsInAnyOrder(brand1, brand2, brand3));
        assertThat(found, hasSize(3));
    }

    @Test
    public void expectedTrueAfterRemove() {
        BrandDTO saved = brandService.saveBrand(BRAND_CREATION);

        brandService.remove(saved.getId());
    }


    @Test(expected = BrandNotFoundException.class)
    public void expectedExceptionAfterRemoveAndTryFindIt() {
        BrandDTO saved = brandService.saveBrand(BRAND_CREATION);

        brandService.remove(saved.getId());

        brandService.findById(saved.getId());
    }

    @Test(expected = BrandNotFoundException.class)
    public void expectedExceptionAfterRemoveNotExistingBrand() {
        brandService.remove(Long.MAX_VALUE);
    }

    @Test
    public void expectedTrueAfterFindModelsByBrandId(){
        Brand brand = new Brand();
        brand.setName("Kemppi");
        WelderModel model1 = new WelderModel();
        model1.setName("MasterTig 3000");
        model1.setBrand(brand);
        WelderModel model2 = new WelderModel();
        model2.setName("MasterTig 4000");
        model2.setBrand(brand);
        brandRepository.save(brand);
        modelRepository.save(model1);
        modelRepository.save(model2);

        List<WelderModelDTO> found = brandService.findWelderModelsByBrandId(brand.getId());

        assertThat(found, hasSize(2));
    }
}