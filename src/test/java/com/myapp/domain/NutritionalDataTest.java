package com.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NutritionalDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NutritionalData.class);
        NutritionalData nutritionalData1 = new NutritionalData();
        nutritionalData1.setId(1L);
        NutritionalData nutritionalData2 = new NutritionalData();
        nutritionalData2.setId(nutritionalData1.getId());
        assertThat(nutritionalData1).isEqualTo(nutritionalData2);
        nutritionalData2.setId(2L);
        assertThat(nutritionalData1).isNotEqualTo(nutritionalData2);
        nutritionalData1.setId(null);
        assertThat(nutritionalData1).isNotEqualTo(nutritionalData2);
    }
}
