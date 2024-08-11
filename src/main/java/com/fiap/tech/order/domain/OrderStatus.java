package com.fiap.tech.order.domain;

import com.fiap.tech.common.domain.ValueObject;
import lombok.Getter;

@Getter
public final class OrderStatus extends ValueObject {
        private final String value;

        private OrderStatus(String value) {
            this.value = value;
        }

        public static final OrderStatus RECEIVED = new OrderStatus("received");
        public static final OrderStatus IN_PREPARATION = new OrderStatus("in preparation");
        public static final OrderStatus READY = new OrderStatus("ready");
        public static final OrderStatus COMPLETED = new OrderStatus("completed");

    public static OrderStatus valueOf(String status) {
        return new OrderStatus(status);
    }

    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderStatus that = (OrderStatus) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return "OrderStatus{" +
                    "status='" + value + '\'' +
                    '}';
        }
}
