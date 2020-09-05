package br.com.checkdiff.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComparisonResultDomain implements Serializable {
    private Long id;
    private String leftData;
    private String rightData;
    private Set<String> onlyLeftFields;
    private Set<String> onlyRightFields;
    private String differentFields;
}
