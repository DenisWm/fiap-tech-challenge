package com.fiap.tech.order.application.retrieve.list;

import com.fiap.tech.common.application.UseCase;
import com.fiap.tech.common.domain.pagination.Pagination;
import com.fiap.tech.product.application.retrieve.list.OrderSearchQuery;

public abstract class ListOrderUseCase extends UseCase<OrderSearchQuery, Pagination<OrderListOutput>> {
}
