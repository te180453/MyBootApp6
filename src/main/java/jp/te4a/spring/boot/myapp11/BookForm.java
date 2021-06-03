package jp.te4a.spring.boot.myapp11;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookForm{
    private Integer id ;
    @NotNull
    @Size(min=3)
    @TestValid(param= "abc")
    private String title;
    @Size(min=3,max=20)
    //@WritterValid(ok="東北たろう")
    private String writter;
    private String publisher;
    @Min(0)
    private Integer price;
}