package jp.te4a.spring.boot.myapp8.impls;

import java.util.*;

import org.springframework.util.MultiValueMap;

public class ParamsMultiValueMap implements MultiValueMap<String,String>{
    private final Map<String,List<String>> map = new HashMap<>();
    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Set<Entry<String, List<String>>> entrySet() {
        return map.entrySet();
    }

    @Override
    public List<String> get(Object key) {
        return map.get(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public List<String> put(String arg0, List<String> arg1) {
        return map.put(arg0, arg1);
    }

    @Override
    public void putAll(Map<? extends String, ? extends List<String>> m) {
        map.putAll(m);
    }

    @Override
    public List<String> remove(Object key) {
        return map.remove(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public Collection<List<String>> values() {
        return map.values();
    }

    @Override
    public String getFirst(String key) {
        return null;
    }

    @Override
    public void add(String key, String value) {
        List<String> t = new ArrayList<String>();
        t.add(value);
        map.put(key,t);
    }

    @Override
    public void addAll(String key, List<? extends String> values) {}

    @Override
    public void addAll(MultiValueMap<String, String> values) {}

    @Override
    public void set(String key, String value) {}

    @Override
    public void setAll(Map<String, String> values) {}

    @Override
    public Map<String, String> toSingleValueMap() {return null;}
}
