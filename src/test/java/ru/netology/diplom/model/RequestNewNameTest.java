package ru.netology.diplom.model;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RequestNewNameTest {

    String filename = "image.jpg";
    @Test
    public void testDeSerializingWithJsonCreatorRequestNewName() throws IOException {
        String jsonString = String.format("{\"filename\": \"%s\"}", filename);

        ObjectMapper mapper = new ObjectMapper();
        RequestNewName requestNewName = mapper.readValue(jsonString, RequestNewName.class);
        assertThat(requestNewName.getFilename(), equalTo(filename));
    }

}
