package ru.netology.diplom.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestNewName {
    private String filename;

    @JsonCreator
    public RequestNewName(@JsonProperty("name") String filename) {
        this.filename = filename;
    }


    @Override
    public String toString() {
        return "RequestNewName{" +
                "filename='" + filename + '\'' +
                '}';
    }
}
