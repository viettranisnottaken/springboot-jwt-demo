package Tutorial.authDemo.shared.mapper;

public interface IBaseMapper<TDto, TEntity> {
    TDto entityToDto(TEntity entity);
    TEntity dtoToEntity(TDto dto);
}
