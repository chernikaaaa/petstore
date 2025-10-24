package api.store.models;

import com.beust.jcommander.internal.Nullable;
import enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
