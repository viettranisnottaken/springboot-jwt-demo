package Tutorial.authDemo.shared;

public class Constants {

  public static final class ERROR {

    public static final class REQUEST {

      public static final String INVALID_PATH_VARIABLE = "error.request.invalid.path_variable";
      public static final String INVALID_PATH_VARIABLE_ID =
          "error.request.invalid.path_variable.id";
      public static final String INVALID_PATH_VARIABLE_CODE =
          "error.request.invalid.path_variable.code";
      public static final String INVALID_PATH_VARIABLE_EMAIL =
          "error.request.invalid.path_variable.email";
      public static final String INVALID_PATH_VARIABLE_STATUS =
          "error.request.invalid.path_variable.status";
      public static final String INVALID_PATH_VARIABLE_STATE =
          "error.request.invalid.path_variable.state";
      public static final String INVALID_BODY = "error.request.body.invalid";
    }

    public static final class USER {

      public static final String CREATE = "error.user.create";
      public static final String UPDATE = "error.user.update";
      public static final String NOT_FOUND = "error.user.not_found";
      public static final String EXISTED = "error.user.existed";
      public static final String NOT_MATCH = "error.user.not_match";
      public static final String EMAIL_NOT_NULL = "error.user.email_not_null";
      public static final String PASSWORD_NOT_NULL = "error.user.password_not_null";
      public static final String WRONG_CREDENTIAL = "error.user.wrong_credential";
    }

    public static final class CUSTOMER {

      public static final String CREATE = "error.customer.create";
      public static final String UPDATE = "error.customer.update";
      public static final String UPDATE_STATE = "error.customer.update_state";
      public static final String UPDATE_STATUS = "error.customer.update_status";
      public static final String EXIST = "error.customer.exist";
      public static final String NOT_EXIST = "error.customer.not_exist";
      public static final String CODE_BLANK = "error.customer.code_blank";
      public static final String IDENTIFICATION_BLANK = "error.customer.identification_blank";
      public static final String INVALID_EMAIL = "error.customer.invalid_email";
    }

    public static final class MONGODB {

      public static final String OBJECT_ID_PATTERN = "error.mongodb.object_id.pattern";
    }

    public static final class STATE {

      public static final String UNDEFINED = "error.state.undefined";
    }

    public static final class PROJECT {

      public static final String CREATE = "error.project.create";
      public static final String UPDATE = "error.project.update";
      public static final String NOT_FOUND = "error.project.not_found";
      public static final String EXISTED = "error.project.existed";
      public static final String NOT_MATCH = "error.project.not_match";
      public static final String CODE_NOT_NULL = "error.project.code_not_null";
    }

    public static final class POLICY {

      public static final String CREATE = "error.policy.create";
      public static final String UPDATE = "error.policy.update";
      public static final String NOT_FOUND = "error.policy.not_found";
      public static final String EXISTED = "error.policy.existed";
      public static final String NOT_MATCH = "error.policy.not_match";
      public static final String CODE_NOT_NULL = "error.policy.code_not_null";
    }

    public static final class CONTRACT {

      public static final String CREATE = "error.contract.create";
      public static final String UPDATE = "error.contract.update";
      public static final String NOT_FOUND = "error.contract.not_found";
      public static final String EXISTED = "error.contract.existed";
      public static final String NOT_MATCH = "error.contract.not_match";
    }

    public static final class SEQUENCE {

      public static final String SAVE = "error.sequence.save";
    }
  }

  public static final class SEQUENCE {

    public static final class CUSTOMER {

      public static final String CODE = "CUSTOMER_SQC";
      public static final String LABEL = "KH";
    }
  }
}
