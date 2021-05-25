package jp.te4a.spring.boot.myapp6;

import java.util.concurrent.ConcurrentMap;

@Repository
public class BookRepository {
    private final ConcurrentMap<Integer,BookBean> bookMap = new ConcurrentHashMap<>();
}