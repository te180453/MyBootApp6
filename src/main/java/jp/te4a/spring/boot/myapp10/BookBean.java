package jp.te4a.spring.boot.myapp10;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity //エンティティ化？
@Table(name="books") //テーブル
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBean{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(nullable = false)
    String title;
    String writter;
    String publisher;
    Integer price;
}