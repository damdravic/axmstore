package ro.anaxim.axmstore.user.query;

public class UserQuery {

    public static final String COUNT_EMAIL_USER="SELECT COUNT(*) FROM users WHERE email=:email";

    public static final String INSERT_USER_QUERY="INSERT INTO users (first_name,last_name,email,password) VALUES (:firstName,:lastName,:email,:password)";

    public static final String INSERT_USER_ROLE_QUERY = "INSERT INTO user_roles (ur_user_id,ur_role_id) VALUES (:userId,:roleId)";

    public static final String SELECT_USER_BY_EMAIL_QUERY ="SELECT * FROM users WHERE email = :email" ;


}
