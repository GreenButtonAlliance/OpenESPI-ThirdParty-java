package org.energyos.espi.thirdparty.domain;

import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.energyos.espi.thirdparty.utils.TestUtils.assertAnnotationPresent;

public class SubscriptionPersistenceTests {
    @Test
    public void persistence() {
        assertAnnotationPresent(Subscription.class, Entity.class);
        assertAnnotationPresent(Subscription.class, Table.class);
    }

    @Test
    public void retailCustomer() {
        assertAnnotationPresent(Subscription.class, "retailCustomer", ManyToOne.class);
        assertAnnotationPresent(Subscription.class, "retailCustomer", JoinColumn.class);
    }
}
