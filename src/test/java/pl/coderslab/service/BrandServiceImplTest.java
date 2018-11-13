package pl.coderslab.service;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.test.context.junit4.SpringRunner;
import pl.coderslab.domain.Brand;
import pl.coderslab.domain.WelderModel;
import pl.coderslab.repository.BrandRepository;
import pl.coderslab.repository.WelderModelRepository;
import pl.coderslab.service.dto.BrandDTO;
import pl.coderslab.service.impl.BrandServiceImpl;
import pl.coderslab.web.errors.BrandNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@DataJpaTest
@Import({BrandServiceImpl.class, ModelMapper.class})
@EnableSpringDataWebSupport
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
        BrandDTO result = brandService.save(BRAND_CREATION);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(BRAND_CREATION.getName(), result.getName());
        assertNotNull(result.getCreationDate());
        assertNotNull(result.getModificationDate());
        assertNotNull(result.getVersionId());
    }

    @Test
    public void expectedTrueAfterUpdateBrand() {
        BrandDTO saved = brandService.save(BRAND_CREATION);
        saved.setName("Fronius");

        BrandDTO result = brandService.save(saved);
        assertNotNull(result);
        assertEquals(saved.getId(), result.getId());
        assertEquals(saved.getName(), result.getName());
        assertEquals(saved.getCreationDate(), result.getCreationDate());
    }

    @Test
    public void expectedTrueAfterFindById() {
        BrandDTO expected = brandService.save(BRAND_CREATION);

        Optional<BrandDTO> found = brandService.findById(expected.getId());
        assertTrue(found.isPresent());
        assertEquals(expected, found.get());
    }

    @Test
    public void expectedTrueAfterFindAll() {
        BrandDTO brand1 = brandService.save(BRAND_CREATION);
        BrandDTO brand2 = brandService.save(BRAND_CREATION_2);
        BrandDTO brand3 = brandService.save(BRAND_CREATION_3);

        Page<BrandDTO> found = brandService.findAll(new PageRequest(0, 20));

        assertThat(found, hasItems(brand1, brand2, brand3));
        assertThat(found.getContent(), hasSize(3));
    }

    @Test
    public void expectedTrueAfterRemove() {
        BrandDTO saved = brandService.save(BRAND_CREATION);

        brandService.remove(saved.getId());
    }


    @Test
    public void expectedNotFoundAfterRemoveAndTryFindIt() {
        BrandDTO saved = brandService.save(BRAND_CREATION);

        brandService.remove(saved.getId());

        Optional<BrandDTO> byId = brandService.findById(saved.getId());
        assertFalse(byId.isPresent());
    }

}