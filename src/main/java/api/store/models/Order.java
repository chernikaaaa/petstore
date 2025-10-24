package api.store.models;

import com.beust.jcommander.internal.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {

    @Nullable
    private Integer id;
    @Nullable
    private Integer petId;
    @Nullable
    private Integer quantity;
    @Nullable
    private String shipDate;
    @Nullable
    private Status status;
    @Nullable
    private Boolean complete;

    public enum Status {
        placed,
        approved,
        delivered
    }

}
