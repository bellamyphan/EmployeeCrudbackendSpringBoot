package com.bellamyPhan.employeeManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "sequences")
public class Sequence {

    @Transient
    public static final String SEQUENCE_NAME = "employee_seq";

    @Id
    private String id;
    @Getter
    private int seq;
}
