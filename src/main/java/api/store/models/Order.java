package api.store.models;

public record Order(
        Integer id,
        Integer petId,
        Integer quantity,
        String shipDate,
        Status status,
        Boolean complete
) {

    public enum Status {
        placed,
        approved,
        delivered
    }

}
