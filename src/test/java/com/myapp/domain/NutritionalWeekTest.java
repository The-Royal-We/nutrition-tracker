package com.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NutritionalWeekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NutritionalWeek.class);
        NutritionalWeek nutritionalWeek1 = new NutritionalWeek();
        nutritionalWeek1.setId(1L);
        NutritionalWeek nutritionalWeek2 = new NutritionalWeek();
        nutritionalWeek2.setId(nutritionalWeek1.getId());
        assertThat(nutritionalWeek1).isEqualTo(nutritionalWeek2);
        nutritionalWeek2.setId(2L);
        assertThat(nutritionalWeek1).isNotEqualTo(nutritionalWeek2);
        nutritionalWeek1.setId(null);
        assertThat(nutritionalWeek1).isNotEqualTo(nutritionalWeek2);
    }
}
