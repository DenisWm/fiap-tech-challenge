package com.fiap.tech;

import co.elastic.clients.elasticsearch.cat.indices.IndicesRecord;
import com.fiap.tech.common.domain.Entity;
import com.fiap.tech.common.domain.Identifier;
import com.fiap.tech.common.domain.validation.ValidationHandler;
import com.fiap.tech.event.DomainEvent;
import com.fiap.tech.event.DomainEventPublisher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EntityTest {

    @Test
    public void givenNullAsEvents_whenInstantiate_shouldBeOk(){
        //given
        final List<DomainEvent> events = null;

        //when
        final var anEntity = new DummyEntity(new DummyID(), null);

        //then
        Assertions.assertNotNull(anEntity.getDomainEvents());
        Assertions.assertTrue(anEntity.getDomainEvents().isEmpty());
    }


    //Revisar
    @Test
    public void givenEmptyDomainEvents_whenCallsRegisterEvent_shouldAddEventToList(){
        //given
        final var expectedEvents = 1;
        final var anEntity = new DummyEntity(new DummyID(), new ArrayList<>());

        //when
        //anEntity.registerEvent((DummyEvent));

        //then
        Assertions.assertNotNull(anEntity.getDomainEvents());
        Assertions.assertEquals(expectedEvents, anEntity.getDomainEvents().size());
    }

    @Test
    public void givenDomainEvents_whenPassInConstructor_shouldCreateDefensiveClone(){
        //given
        final List<DomainEvent> events = new ArrayList<>();
        events.add((DomainEvent) () -> null);

        //when
        final var anEntity = new DummyEntity(new DummyID(), null);

        //then
        Assertions.assertNotNull(anEntity.getDomainEvents());
        Assertions.assertEquals(1, anEntity.getDomainEvents().size());

        Assertions.assertThrows(RuntimeException.class, () -> {
            final var actualEvents = anEntity.getDomainEvents();
            actualEvents.add((DomainEvent) () -> null);
        });
    }

    //Revisar
    @Test
    public void givenAFewDomainEvents_whenCallsPublishEvents_shouldCallPublisherAndClearTheList(){
        //given
        final var expectedEvents = 0;
        final var expectedSentEvents = 2;
        final var counter = new AtomicInteger(0);

        final var anEntity = new DummyEntity(new DummyID(), new ArrayList<>());
        //anEntity.registerEvent((DummyEvent));
        //anEntity.registerEvent((DummyEvent));

        //when
        anEntity.publishDomainEvent(event -> {
            counter.incrementAndGet();
        });

        //then
        Assertions.assertNotNull(anEntity.getDomainEvents());
        Assertions.assertEquals(expectedEvents, anEntity.getDomainEvents().size());
        Assertions.assertEquals(expectedSentEvents, counter.get());
    }

    /*public static class DummyEvent implements DomainEvent{

        @Override
        public Instant occurredOn() {
            return InstantUtils.now();
        }
    }*/

    public static class DummyID extends Identifier {

        private final String id;

        public DummyID() {
            this.id = UUID.randomUUID().toString();
        }

        @Override
        public String getValue(){
            return this.id;
        }
    }

    public static class DummyEntity extends Entity<DummyID> {

        public DummyEntity(){
            this(new DummyID(), null);
        }

        public DummyEntity(DummyID dummyID, List<DomainEvent> domainEvents) {
            super(dummyID, domainEvents);
        }

        @Override
        public void validate(ValidationHandler handler) {

        }
    }
}
