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
/*
MySQLを使った場合
strategy = GenerationType.AUTO Table 'spdb.hibernate_sequence' doesn't exist
strategy = GenerationType.IDENTITY 問題なし
strategy = GenerationType.SEQUENCE Table 'spdb.hibernate_sequence' doesn't exist
strategy = GenerationType.TABLE Table 'spdb.hibernate_sequences' doesn't exist

@TableGenerator( name = "hibernate_sequence", table = "hibernate_sequence", pkColumnName = "seq_name", pkColumnValue = "id_seq", valueColumnName = "seq_value", initialValue = 1, allocationSize = 1 )
*/