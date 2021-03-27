package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CustomerSubscriptionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerSubscription.class);
        CustomerSubscription customerSubscription1 = new CustomerSubscription();
        customerSubscription1.setId(1L);
        CustomerSubscription customerSubscription2 = new CustomerSubscription();
        customerSubscription2.setId(customerSubscription1.getId());
        assertThat(customerSubscription1).isEqualTo(customerSubscription2);
        customerSubscription2.setId(2L);
        assertThat(customerSubscription1).isNotEqualTo(customerSubscription2);
        customerSubscription1.setId(null);
        assertThat(customerSubscription1).isNotEqualTo(customerSubscription2);
    }
}
