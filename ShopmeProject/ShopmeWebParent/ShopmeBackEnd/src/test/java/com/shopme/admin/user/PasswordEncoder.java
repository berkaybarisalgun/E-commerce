package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;//

import static org.assertj.core.api.Assertions.assertThat;


public class PasswordEncoder {
    @Test
    public void testEncodePassword(){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String rawPassword="nam2020";
        String encodedPassword=passwordEncoder.encode(rawPassword);

        System.out.println(encodedPassword);

        boolean matches=passwordEncoder.matches(rawPassword,encodedPassword);

        assertThat(matches).isTrue();//I have to use "import static org.assertj.core.api.Assertions.assertThat;" this import in order to work this.
    }
}
