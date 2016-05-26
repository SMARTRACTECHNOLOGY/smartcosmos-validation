package net.smartcosmos.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import net.smartcosmos.constraint.ExclusiveField;

@Data
@AllArgsConstructor
@Builder
@ExclusiveField(fields = { "name", "id" })
public class Entity
{
    private String name;
    private String id;
}
