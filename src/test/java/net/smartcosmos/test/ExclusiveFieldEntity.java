package net.smartcosmos.test;

import lombok.Builder;
import lombok.Data;
import net.smartcosmos.constraint.ExclusiveField;

@Data
@Builder
@ExclusiveField(fields = { "foo", "bar" })
public class ExclusiveFieldEntity
{
    private String foo;
    private Integer bar;
}
