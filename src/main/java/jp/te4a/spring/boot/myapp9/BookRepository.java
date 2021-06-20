package jp.te4a.spring.boot.myapp9;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookBean,Integer>{
}