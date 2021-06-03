package jp.te4a.spring.boot.myapp12;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class TestValidator implements ConstraintValidator<TestValid,String>{

    String param;
    @Override
    public void initialize(TestValid nv){
        param=  nv.param(); 
    }
    @Override
    public boolean isValid(String in,ConstraintValidatorContext cxt){
        System.out.println(in);
        if( in == null ){
            return false;
        }
        return !in.equals(param);
    }
}