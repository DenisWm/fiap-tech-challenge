package com.fiap.tech.fiap_tech_challenge.order.domain;

import com.fiap.tech.fiap_tech_challenge.common.domain.ValueObject;
import lombok.Getter;

@Getter
public final class OrderStatus extends ValueObject {
        private final String status;

        private OrderStatus(String status) {
            this.status = status;
        }

        public static final OrderStatus RECEIVED = new OrderStatus("received");
        public static final OrderStatus IN_PREPARATION = new OrderStatus("in preparation");
        public static final OrderStatus READY = new OrderStatus("ready");
        public static final OrderStatus COMPLETED = new OrderStatus("completed");

    @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderStatus that = (OrderStatus) o;
            return status.equals(that.status);
        }

        @Override
        public int hashCode() {
            return status.hashCode();
        }

        @Override
        public String toString() {
            return "OrderStatus{" +
                    "status='" + status + '\'' +
                    '}';
        }
}
