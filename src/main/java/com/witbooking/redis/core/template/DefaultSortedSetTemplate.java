package com.witbooking.redis.core.template;

import com.witbooking.redis.core.storage.OrderMember;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class DefaultSortedSetTemplate implements SortedSetTemplate {

    private final ConcurrentMap<String, TreeSet<OrderMember>> storage = new ConcurrentHashMap<>();

    /**
     * Time complexity: O(log(N))
     *
     * @param key
     * @param order
     * @param member
     * @return
     */
    public String add(String key, Integer order, String member) {
        /*
        final TreeSet<OrderMember> members = storage.computeIfAbsent(key, k -> new TreeSet<>());

        OrderMember currentMember = members.sub

        final List<OrderMember> membersSorted = members.stream().sorted(OrderMember.MEMBER_COMPARATOR).collect(Collectors.toList());



        if (currentMember.isNull()) {
            members.add(OrderMember.createFrom(order, member));
            return "1";
        } else if (currentMember.getOrder().compareTo(order) != 0){
            members.remove(currentMember);
            members.add(OrderMember.createFrom(order, member));
            return "1";
        }

         */

        return "0";
    }

    /**
     * Time complexity: O(log(N))
     *
     * @param key
     * @param member
     * @return
     */
    public int rank(String key, String member) {
        /*
        return sortMembersAndSearchByMember(
                member,
                Optional.ofNullable(storage.get(key))
                        .orElse(Collections.emptyList())
        ).getIndex();
         */
        return 0;
    }

    /**
     * Time complexity: O(1)
     *
     * @param key
     * @return
     */
    public int sizeByKey(String key) {
        return storage.containsKey(key) ? storage.get(key).size() : 0;
    }

    @Override
    public Set<String> range(String key, Integer start, Integer stop) {
        return null;
    }

    public OrderMember get(String key, String member) {
        TreeSet<OrderMember> orderMembers = Optional.ofNullable(storage.get(key)).orElse(new TreeSet<>());
        final List<OrderMember> membersSorted = orderMembers.stream().sorted(OrderMember.MEMBER_COMPARATOR).collect(Collectors.toList());
        return sortMembersAndSearchByMember(member, membersSorted);
    }

    private OrderMember sortMembersAndSearchByMember(String member, List<OrderMember> members) {
        int first = 0;
        int last = members.size() - 1;

        while (first <= last) {

            int middle = first + ((last - first) / 2);

            if (members.get(middle).getMember().compareTo(member) < 0) {
                first = middle + 1;
            } else if (members.get(middle).getMember().compareTo(member) > 0) {
                last = middle - 1;
            } else {
                OrderMember orderMember = members.get(middle);
                orderMember.setIndex(middle);
                return orderMember;
            }
        }

        return OrderMember.ofNullable();
    }
}
