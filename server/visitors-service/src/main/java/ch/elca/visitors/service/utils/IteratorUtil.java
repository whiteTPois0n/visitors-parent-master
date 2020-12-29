package ch.elca.visitors.service.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IteratorUtil {
    public static <T> List<T> toList(Iterable<T> list) {
        return StreamSupport.stream(list.spliterator(), false).collect(Collectors.toList());
    }
}
