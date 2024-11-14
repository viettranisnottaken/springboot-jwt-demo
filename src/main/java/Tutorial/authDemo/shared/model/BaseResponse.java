package Tutorial.authDemo.shared.model;

import Tutorial.authDemo.shared.model.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@EqualsAndHashCode
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {

  List<String> errors;
  List<Map<String, String>> errorMap;
  String message;
  Integer totalPages;
  Integer pageIndex;
  Integer pageSize;
  Long total;
  ResponseStatus status;
  T data;
}
