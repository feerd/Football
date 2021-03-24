package football.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class YearValidator implements ConstraintValidator<Year, Integer> {

    protected long year;


    /**
     * sets yearValue
     *
     * @param yearValue
     */
    @Override
    public void initialize(Year yearValue) {
        this.year = yearValue.value();
    }


    /**
     * checks if value is valid
     *
     * @param integer
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (integer < year) {
            return true;
        }
        return false;
    }
}
