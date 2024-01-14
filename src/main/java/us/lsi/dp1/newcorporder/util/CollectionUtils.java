package us.lsi.dp1.newcorporder.util;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;

import java.util.Map;

public class CollectionUtils {

    public static <E> Multiset<E> asMultiset(Map<E, Integer> map) {
        Multiset<E> multiset = HashMultiset.create(map.size());
        for (Map.Entry<E, Integer> entry : map.entrySet()) {
            multiset.setCount(entry.getKey(), entry.getValue());
        }
        return multiset;
    }

    public static <E> Map<E, Integer> asMap(Multiset<E> multiset) {
        return Maps.asMap(multiset.elementSet(), multiset::count);
    }
}
