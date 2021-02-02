package util.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import util.ValidationUtil;
import util.validator.annotations.MD5Hash;

public class MD5HashValidator implements ConstraintValidator<MD5Hash, String> {

	public MD5HashValidator() {
	}
	
	@Override
	public void initialize(MD5Hash constraintAnnotation) {
		
	}

	public boolean isValid( String md5hash, ConstraintValidatorContext context) {
		if (!ValidationUtil.isEmpty(md5hash)) {
			return md5hash.matches("[a-fA-F0-9]{32}");
		} else {
			return false;
		}
	}

}