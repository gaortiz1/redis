package com.witbooking.redis.core.storage;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;

@Data
@NoArgsConstructor
public class OrderMember implements Comparable<OrderMember>, Nullable {

    public static final Comparator<OrderMember> MEMBER_COMPARATOR = Comparator.comparing(OrderMember::getMember);
    public static final Comparator<OrderMember> ORDER_COMPARATOR = Comparator.comparing(OrderMember::getOrder);

    private Integer order;
    private String member;
    private Integer index;

    public OrderMember(Integer order, String member) {
        this(order, member, null);
    }

    public OrderMember(Integer order, String member, Integer index) {
        this.order = order;
        this.member = member;
        this.index = index;
    }

    public static OrderMember createFrom(Integer order, String member) {
        return new OrderMember(order, member);
    }

    public static OrderMember ofNullable() {
        return new OrderMember(-1, "", -1) {
            @Override
            public boolean isNull() {
                return true;
            }
        };
    }

    @Override
    public int compareTo(OrderMember orderMember) {
        return Comparator.comparing(OrderMember::getOrder)
                .thenComparing(OrderMember::getMember)
                .compare(this, orderMember);
    }
}

