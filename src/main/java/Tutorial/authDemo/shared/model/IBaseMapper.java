package Tutorial.authDemo.shared.model;

public interface IBaseMapper<TEntity, TRequestDto, TResponseDto> {

  TEntity requestDtoToEntity(TRequestDto requestDto);
  
  TResponseDto entityToResponseDto(TEntity entity);
}
