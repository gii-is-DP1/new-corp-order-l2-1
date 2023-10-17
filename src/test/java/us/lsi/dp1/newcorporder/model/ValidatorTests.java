package us.lsi.dp1.newcorporder.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;
import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * @author Michael Isvy Simple test to make sure that Bean Validation is working (useful
 * when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

	@Test
	void shouldNotValidateWhenFirstNameEmpty() {
		LocaleContextHolder.setLocale(Locale.ENGLISH);
		Model model = new NamedEntity("") {};

		Validator validator = createValidator();
		Set<ConstraintViolation<Model>> constraintViolations = validator.validate(model);

		assertThat(constraintViolations.size()).isEqualTo(1);
		ConstraintViolation<Model> violation = constraintViolations.iterator().next();
		assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
		assertThat(violation.getMessage()).isEqualTo("size must be between 3 and 50");
	}

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }
}
