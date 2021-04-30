package ru.netology.diplom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListResponse {

    @JsonProperty("filename")
    private String fileName;
    @JsonProperty(value = "size", required = true)
    private int size;

    @JsonCreator
    public ListResponse(@JsonProperty("filename") String originalFileName,
                        @JsonProperty(value = "size") int size) {
        this.fileName = originalFileName;
        this.size = size;
    }
}
