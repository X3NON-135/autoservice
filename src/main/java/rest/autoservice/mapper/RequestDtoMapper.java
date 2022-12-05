package rest.autoservice.mapper;

public interface RequestDtoMapper<D, C> {
    C toModel(D object);
}
