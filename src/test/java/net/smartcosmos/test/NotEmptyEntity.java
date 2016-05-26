package net.smartcosmos.test;

import lombok.Builder;
import lombok.Data;
import net.smartcosmos.constraint.NotEmpty;

@Data
@Builder
public class NotEmptyEntity {
    @NotEmpty
    private String foo;
}
