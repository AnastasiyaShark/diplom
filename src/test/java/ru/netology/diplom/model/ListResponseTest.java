package ru.netology.diplom.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ListResponseTest {

    String fileName = "image.jpg";
    int size = 1256;


    @Test
    public void testDeSerializingWithJsonCreatorListResponse() throws IOException {
        String jsonString = String.format("{\"filename\": \"%s\", \"size\": \"%d\"}", fileName,size);

        ObjectMapper mapper = new ObjectMapper();
        ListResponse listResponse = mapper.readValue(jsonString, ListResponse.class);
        assertThat(listResponse.getFileName(), equalTo(fileName));
        assertThat(listResponse.getSize(), equalTo(size));
    }




}
