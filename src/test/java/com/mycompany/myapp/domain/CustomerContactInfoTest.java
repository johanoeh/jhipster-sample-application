package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CustomerContactInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerContactInfo.class);
        CustomerContactInfo customerContactInfo1 = new CustomerContactInfo();
        customerContactInfo1.setId(1L);
        CustomerContactInfo customerContactInfo2 = new CustomerContactInfo();
        customerContactInfo2.setId(customerContactInfo1.getId());
        assertThat(customerContactInfo1).isEqualTo(customerContactInfo2);
        customerContactInfo2.setId(2L);
        assertThat(customerContactInfo1).isNotEqualTo(customerContactInfo2);
        customerContactInfo1.setId(null);
        assertThat(customerContactInfo1).isNotEqualTo(customerContactInfo2);
    }
}
