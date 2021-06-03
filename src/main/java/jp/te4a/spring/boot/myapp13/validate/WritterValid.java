package jp.te4a.spring.boot.myapp13.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {WritterValidator.class})
public @interface WritterValid {
    String ok();
    String message() default "入力は「{ok}」である必要があります";
    // Classオブジェクトを得る（戻り値とする）メソッドgroups()
    // デフォルト値は空のクラス
    Class<?>[] groups() default {};
    // Payloadクラスを継承したClassオブジェクトを得る
    // （戻り値とする）メソッドpayload()、デフォルト値は空のクラス
    Class<? extends Payload>[] payload() default{};
}
