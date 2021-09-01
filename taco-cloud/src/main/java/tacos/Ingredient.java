package tacos;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@RequiredArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE,force = true)
@Table("ingredient")
public class Ingredient {
    @PrimaryKey
    private final String id;
    private final String name;
    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
}
