package com.fiap.tech.fiap_tech_challenge.order.application.retrieve.list;

import com.fiap.tech.fiap_tech_challenge.common.application.UseCase;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.Pagination;
import com.fiap.tech.fiap_tech_challenge.common.domain.pagination.SearchQuery;
import com.fiap.tech.fiap_tech_challenge.product.application.retrieve.list.OrderSearchQuery;

public abstract class ListOrderUseCase extends UseCase<OrderSearchQuery, Pagination<OrderListOutput>> {
}
