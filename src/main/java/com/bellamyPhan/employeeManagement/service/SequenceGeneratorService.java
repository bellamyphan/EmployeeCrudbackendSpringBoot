package com.bellamyPhan.employeeManagement.service;

import com.bellamyPhan.employeeManagement.model.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService {
    @Autowired
    private MongoOperations mongoOperations;

    public int generateSequence(String seqName) {
        Sequence sequence = mongoOperations.findAndModify(
                Query.query(Criteria.where("id").is(seqName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                Sequence.class);
        if (sequence != null) {
            return sequence.getSeq();
        } else {
            return 1;
        }
    }
}
