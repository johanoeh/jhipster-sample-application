package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BillingAccountTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingAccount.class);
        BillingAccount billingAccount1 = new BillingAccount();
        billingAccount1.setId(1L);
        BillingAccount billingAccount2 = new BillingAccount();
        billingAccount2.setId(billingAccount1.getId());
        assertThat(billingAccount1).isEqualTo(billingAccount2);
        billingAccount2.setId(2L);
        assertThat(billingAccount1).isNotEqualTo(billingAccount2);
        billingAccount1.setId(null);
        assertThat(billingAccount1).isNotEqualTo(billingAccount2);
    }
}
