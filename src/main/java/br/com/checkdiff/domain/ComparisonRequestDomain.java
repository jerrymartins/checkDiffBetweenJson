package br.com.checkdiff.domain;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComparisonRequestDomain {
    private String id;

    @NotNull
    @NotEmpty
    private String left;

    @NotNull
    @NotEmpty
    private String right;
}
