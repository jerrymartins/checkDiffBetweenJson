package br.com.checkdiff.domain;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComparisonResponseDomain implements Serializable {
    private Long id;
    private Set<String> onlyLeftFields;
    private Set<String> onlyRightFields;
    private String differentFields;
}
