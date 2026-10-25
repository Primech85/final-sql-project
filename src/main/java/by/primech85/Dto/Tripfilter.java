package by.primech85.Dto;

import by.primech85.entity.Status_type;

public record Tripfilter(Long arrival_code,
                         Long departure_code,
                         Status_type status,
                         Long train_id,
                         int limit,
                         int offset) {

}
