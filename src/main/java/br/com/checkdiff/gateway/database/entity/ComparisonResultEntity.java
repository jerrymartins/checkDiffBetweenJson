package br.com.checkdiff.gateway.database.entity;

import com.google.common.collect.MapDifference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("comparison")
public class ComparisonResultEntity {

    @Id
    private Long id;

    @NotNull
    @NotEmpty(message = "left cannot be empty")
    private String leftData;

    @NotNull
    @NotEmpty(message = "right cannot be empty")
    private String rightData;

    private Set<String> onlyLeftFields;

    private Set<String> onlyRightFields;

    private String differentFields;
}
