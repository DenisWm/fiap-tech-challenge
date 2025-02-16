package com.fiap.tech;

import com.fiap.tech.categories.infra.CategoryRepository;
import com.fiap.tech.client.infra.persistence.ClientRepository;
import com.fiap.tech.order.domain.OrderGateway;
import com.fiap.tech.order.infra.persistence.OrderRepository;
import com.fiap.tech.ordereditens.infra.persistence.OrderedItemRepository;
import com.fiap.tech.payment.infra.persistence.PaymentRepository;
import com.fiap.tech.product.infra.persistense.ProductRepository;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

class PostgresCleanUpExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(final ExtensionContext context) {
        final var appContext = SpringExtension.getApplicationContext(context);

        cleanUp(List.of(
                appContext.getBean(OrderRepository.class),
                appContext.getBean(OrderedItemRepository.class),
                appContext.getBean(ProductRepository.class),
                appContext.getBean(ClientRepository.class),
                appContext.getBean(CategoryRepository.class),
                appContext.getBean(PaymentRepository.class)
        ));
    }

    private void cleanUp(final Collection<CrudRepository> repositories) {
        repositories.forEach(CrudRepository::deleteAll);
    }
}
