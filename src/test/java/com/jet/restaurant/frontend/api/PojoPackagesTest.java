package com.jet.restaurant.frontend.api;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.jupiter.api.Test;

import java.util.List;

class PojoPackagesTest {
    private static final String POJO_PACKAGE_CONTRACT = "com.jet.restaurant.frontend.api.contract";
    private static final String POJO_PACKAGE_ENTITY = "com.jet.restaurant.frontend.api.entity";
    private static final String POJO_PACKAGE_DTO = "com.jet.restaurant.frontend.api.dto";
    private static final String POJO_PACKAGE_TOPIC = "com.jet.restaurant.frontend.api.topic";

    private final List<PojoClass> pojoClassesContract = PojoClassFactory.getPojoClasses(POJO_PACKAGE_CONTRACT, new FilterPackageInfo());
    private final List<PojoClass> pojoClassesEntity = PojoClassFactory.getPojoClasses(POJO_PACKAGE_ENTITY, new FilterPackageInfo());
    private final List<PojoClass> pojoClassesDto = PojoClassFactory.getPojoClasses(POJO_PACKAGE_DTO, new FilterPackageInfo());
    private final List<PojoClass> pojoClassesTopic = PojoClassFactory.getPojoClasses(POJO_PACKAGE_TOPIC, new FilterPackageInfo());

    @Test
    void testPojoStructureAndBehavior() {
        Validator validatorModel = ValidatorBuilder.create()
                .with(new SetterTester()).with(new GetterTester()).build();
        validatorModel.validate(pojoClassesContract);
        validatorModel.validate(pojoClassesEntity);
        validatorModel.validate(pojoClassesDto);
        validatorModel.validate(pojoClassesTopic);
    }

}
