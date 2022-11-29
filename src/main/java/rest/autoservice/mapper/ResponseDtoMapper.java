package rest.autoservice.mapper;

public interface ResponseDtoMapper<D, C> {
    D toDto(C object);
}
