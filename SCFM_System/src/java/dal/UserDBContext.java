package dal;

import entity.Role;
import entity.User;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.PasswordGenerator;

public class UserDBContext extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public Set<User> listEmployee() {
        Set<User> users = new HashSet<>();
        try {

            String sql = "SELECT u.[userId]\n"
                    + "      ,[maso]\n"
                    + "      ,[username]\n"
                    + "      ,[password]\n"
                    + "      ,[fullName]\n"
                    + "      ,[email]\n"
                    + "      ,[gender]\n"
                    + "      ,[mobile]\n"
                    + "      ,[address]\n"
                    + "      ,[dateOfBirth]\n"
                    + "      ,[picture]\n"
                    + "	  ,ur.roleId\n"
                    + "  FROM [User] u join User_Role ur on u.userId = ur.userId\n"
                    + "				join [Role] r  on ur.roleId = r.roleId\n"
                    + "				where ur.roleId <3";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setMaso(rs.getString("maso"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setEmail(rs.getString("email"));
                user.setGender(rs.getBoolean("gender"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                user.setDateOfBirth(rs.getDate("dateOfBirth"));
                user.setPicture(rs.getString("picture"));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return users;
    }

    public void registerUser(User u) {
        try {
            String strSQL = "INSERT INTO [dbo].[User]\n"
                    + "           ([maso]\n"
                    + "           ,[userName]\n"
                    + "           ,[password]\n"
                    + "           ,[fullName]\n"
                    + "           ,[email]\n"
                    + "           ,[gender]\n"
                    + "           ,[mobile]\n"
                    + "           ,[address]\n"
                    + "           ,[dateOfBirth])\n"
                    + "     VALUES\n"
                    + "           (?,?,?,?,?,?,?,?,?)";

            stm = connection.prepareStatement(strSQL);

            stm.setString(1, u.getMaso());
            stm.setString(2, u.getUserName());
            stm.setString(3, u.getPassword());
            stm.setString(4, u.getFullName());
            stm.setString(5, u.getEmail());
            if (u.isGender() == true) {
                stm.setBoolean(6, u.isGender());
            } else {
                stm.setBoolean(6, false);
            }
            stm.setString(7, u.getMobile());
            stm.setString(8, u.getAddress());
            java.sql.Date sqlDate = new java.sql.Date(u.getDateOfBirth().getTime());
            stm.setDate(9, sqlDate);
            stm.execute();

            int userId = getUserIdByUsername(u.getUserName());

//sửa do dùng database cũ, k cần merge
            if (userId != -1) {
                String insertUserRoleSQL = "INSERT INTO [dbo].[User_Role] (userId, roleId) VALUES (?, ?)";
                stm = connection.prepareStatement(insertUserRoleSQL);
                stm.setInt(1, userId);
                stm.setInt(2, 3);
                stm.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createStaff(User u) {
        try {
            String strSQL = "INSERT INTO [dbo].[User]\n"
                    + "           ([maso]\n"
                    + "           ,[userName]\n"
                    + "           ,[password]\n"
                    + "           ,[fullName]\n"
                    + "           ,[email]\n"
                    + "           ,[gender]\n"
                    + "           ,[mobile]\n"
                    + "           ,[address]\n"
                    + "           ,[dateOfBirth])\n"
                    + "     VALUES\n"
                    + "           (?,?,?,?,?,?,?,?,?)";

            stm = connection.prepareStatement(strSQL);

            stm.setString(1, u.getMaso());
            stm.setNString(2, u.getUserName());
            stm.setString(3, u.getPassword());
            stm.setString(4, u.getFullName());
            stm.setString(5, u.getEmail());
            if (u.isGender() == true) {
                stm.setBoolean(6, u.isGender());
            } else {
                stm.setBoolean(6, false);
            }
            stm.setString(7, u.getMobile());
            stm.setString(8, u.getAddress());
            java.sql.Date sqlDate = new java.sql.Date(u.getDateOfBirth().getTime());
            stm.setDate(9, sqlDate);
            stm.execute();

            int userId = getUserIdByUsername(u.getUserName());

            if (userId != -1) {
                String insertUserRoleSQL = "INSERT INTO [dbo].[User_Role] (userId, roleId) VALUES (?, ?)";
                stm = connection.prepareStatement(insertUserRoleSQL);
                stm.setInt(1, userId);
                stm.setInt(2, 2);
                stm.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkEmailExist(String email) {

        try {
            String sql = "SELECT COUNT(*) FROM [dbo].[User] WHERE email = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUserIdByUsername(String userName) {
        int userId = -1; // Giá trị mặc định trong trường hợp không tìm thấy user
        try {
            String strSQL = "SELECT userId FROM [User] WHERE userName = ?";

            stm = connection.prepareStatement(strSQL);
            stm.setString(1, userName);

            rs = stm.executeQuery();

            if (rs.next()) {
                userId = rs.getInt("userId");
                System.out.println(userId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userId;
    }

    public boolean checkUserNullOrSpace(User user) {
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            return false;
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return false;
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            return false;
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return false;
        }
        if (user.getMobile() == null || user.getMobile().trim().isEmpty()) {
            return false;
        }
        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            return false;
        }
        if (user.getDateOfBirth() == null) {
            return false;
        }

        return true;
    }

    public boolean checkUserNullOrSpace2(User user) {
        if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            return false;
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return false;
        }
        if (user.getFullName() == null || user.getFullName().trim().isEmpty()) {
            return false;
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return false;
        }
        if (user.getMobile() == null || user.getMobile().trim().isEmpty()) {
            return false;
        }
        if (user.getAddress() == null || user.getAddress().trim().isEmpty()) {
            return false;
        }
        if (user.getDateOfBirth() == null) {
            return false;
        }
        return true;
    }

    public boolean checkUsername(String username) {
        try {
            String strSQL = "SELECT COUNT(*) AS count FROM [User] WHERE username = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, username);

            rs = stm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean checkActiveLogin(String username, String password, String role, int a) {
        String strSQL = "SELECT u.userId FROM [dbo].[User] u "
                + "JOIN [dbo].[User_Role] ur ON u.userId = ur.userId "
                + "JOIN [dbo].[Role] r ON ur.roleId = r.roleId "
                + "WHERE u.userName = ? AND u.password = ? AND r.roleName = ? AND u.active = ?";
        try (PreparedStatement stm = connection.prepareStatement(strSQL)) {
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setString(3, role);
            stm.setInt(4, a);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean checkUser(String username, String password) {
        try {
            String strSQL = "select * from [User] where username = ? and password = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("checkUser" + e.getMessage());
        }
        return false;
    }

    public String getFullNameByUsername(String userName) {
        String fullName = "";

        try {
            String strSQL = "SELECT fullName FROM [User] WHERE username = ?";

            stm = connection.prepareStatement(strSQL);
            stm.setString(1, userName);

            rs = stm.executeQuery();

            if (rs.next()) {
                fullName = rs.getString("fullName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fullName;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM [dbo].[User] WHERE email = ?";

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userId"));
                user.setMaso(rs.getString("maso"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setEmail(rs.getString("email"));
                user.setGender(rs.getBoolean("gender"));
                user.setMobile(rs.getString("mobile"));
                user.setAddress(rs.getString("address"));
                user.setDateOfBirth(rs.getDate("dateOfBirth"));
                user.setActive(rs.getBoolean("active"));  // Thêm dòng này
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public User getUserByUserId(int uid) {
        String sql = """
                 SELECT [userId]
                       ,[maso]
                       ,[username]
                       ,[password]
                       ,[fullName]
                       ,[email]
                       ,[gender]
                       ,[mobile]
                       ,[address]
                       ,[dateOfBirth]
                       ,[picture]
                       ,[active]  
                   FROM [User]
                   WHERE userId = ?""";

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, uid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                System.out.println(rs);
                User u = new User();
                u.setUserId(rs.getInt("userId"));
                u.setMaso(rs.getString("maso"));
                u.setUserName(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullName(rs.getString("fullname"));
                u.setEmail(rs.getString("email"));
                u.setGender(rs.getBoolean("gender"));
                u.setMobile(rs.getString("mobile"));
                u.setAddress(rs.getString("address"));
                u.setDateOfBirth(rs.getDate("dateOfBirth"));
                u.setPicture(rs.getNString("picture"));
                u.setActive(rs.getBoolean("active"));  // Thêm dòng này
                return u;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public boolean createAccountFromGoogle(String email, String username) {
        boolean isCreated = false;

        try {
            String randomPassword = PasswordGenerator.generateRandomPassword(10);
            String sql = """
                         INSERT INTO [User] ([maso]
                               ,[username]
                               ,[password]
                               ,[fullName]
                               ,[email]
                               ,[gender]
                               ,[mobile]
                               ,[address]
                               ,[dateOfBirth]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)""";

            stm = connection.prepareStatement(sql);

            stm.setString(1, "");
            stm.setString(2, username);
            stm.setString(3, randomPassword);
            stm.setString(4, "");
            stm.setString(5, email);
            stm.setBoolean(6, true);
            stm.setString(7, "");
            stm.setString(8, "");
            stm.setDate(9, null);

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                int userId = getUserIdByUsername(username);
                String insertUserRoleSQL = "INSERT INTO [User_Role] (userId, roleId) VALUES (?, ?)";
                stm = connection.prepareStatement(insertUserRoleSQL);
                stm.setInt(1, userId);
                stm.setInt(2, 3);
                stm.executeUpdate();
                isCreated = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isCreated;
    }

    public boolean checkPassword(String username, String fullName, String password) {
        // Check length
        if (password.length() < 12) {
            return false;
        }
        // Check character categories
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        int categories = 0;
        if (hasUpper) {
            categories++;
        }
        if (hasLower) {
            categories++;
        }
        if (hasDigit) {
            categories++;
        }
        if (hasSpecial) {
            categories++;
        }

        if (categories < 3) {
            return false;
        }
        // Check if password contains parts of username or full name exceeding two consecutive characters
        String[] nameParts = fullName.split("\\s+");
        for (String part : nameParts) {
            if (part.length() > 2 && password.contains(part)) {
                return false;
            }
        }
        if (username.length() > 2 && password.contains(username)) {
            return false;
        }
        return true;
    }

    public boolean isFPTEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@fpt\\.edu\\.vn$";
        return email.matches(emailRegex);
    }

    public boolean isFPTStaffEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@fe\\.edu\\.vn$";
        return email.matches(emailRegex);
    }

    public boolean isValidUsername(String username) {
        // Kiểm tra xem username có null hay không
        if (username == null) {
            return false;
        }

        // Kiểm tra độ dài của username
        int length = username.length();
        if (length < 6 || length > 30) {
            return false;
        }

        // Kiểm tra các ký tự trong username
        for (char ch : username.toCharArray()) {
            // Kiểm tra xem ký tự có nằm trong khoảng chữ cái và số không
            if (!Character.isLetterOrDigit(ch)) {
                return false;
            }
        }

        // Username hợp lệ nếu vượt qua tất cả các kiểm tra
        return true;
    }

    public String getValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return null;
        }

        // Kiểm tra xem số điện thoại có chứa ký tự chữ cái hoặc ký tự đặc biệt không
        if (!phoneNumber.matches("[+]?\\d+")) {
            return null;
        }
        // Kiểm tra mã quốc gia (nếu có)
        if (phoneNumber.startsWith("+")) {
            if (phoneNumber.length() < 11 || phoneNumber.length() > 16) {
                return null;
            }
            // Kiểm tra độ dài của số điện thoại
        } else if (phoneNumber.length() < 10 || phoneNumber.length() > 15) {
            return null;
        }
        return phoneNumber;

    }

    //        // Check against the last 5 passwords
//        String query = "SELECT password FROM user_passwords WHERE username = ? ORDER BY change_date DESC LIMIT 5";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, username);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                String oldPassword = rs.getString("password");
//                if (password.equals(oldPassword)) {
//                    return false;
//                }
//            }
//        }
    public int getRoleIdByUserId(int userId) {
        int roleId = -1;
        String sql = """
                     SELECT roleId
                     FROM dbo.User_Role
                     WHERE userId = ?;""";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, userId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    roleId = rs.getInt("roleId");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roleId;
    }

    public User getUserByUsernamePassword(String username, String password) {
        try {
            String sql = """
                         SELECT [userId]
                               ,[maso]
                               ,[username]
                               ,[password]
                               ,[fullName]
                               ,[email]
                               ,[gender]
                               ,[mobile]
                               ,[address]
                               ,[dateOfBirth]
                           FROM [dbo].[User] 
                         WHERE username = ? AND password = ?""";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setMaso("maso");
                user.setEmail("email");
                user.setGender(rs.getBoolean("gender"));
                user.setMobile("mobile");
                user.setAddress("address");
                user.setDateOfBirth(rs.getDate("dateOfBirth"));
                user.setUserName(username);
                user.setPassword(password);
                user.setFullName(rs.getString("fullName"));
                return user;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isUserOldEnough(String dateOfBirth) {
        try {
            // Lấy ngày sinh từ chuỗi
            LocalDate birthDate = LocalDate.parse(dateOfBirth);

            // Lấy ngày hiện tại
            LocalDate currentDate = LocalDate.now();

            // Tính khoảng cách thời gian từ ngày sinh đến ngày hiện tại
            Period period = Period.between(birthDate, currentDate);

            // Kiểm tra nếu tuổi là 16 tuổi trở lên
            return (period.getYears() > 16 || (period.getYears() == 16 && period.getMonths() > 0) || (period.getYears() == 16 && period.getMonths() == 0 && period.getDays() >= 0));

        } catch (DateTimeParseException e) {
            // Xử lý ngoại lệ nếu định dạng ngày không hợp lệ
            System.out.println("Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd.");
            return false;
        }
    }

    public boolean isUserOldEnoughStaff(String dateOfBirth) {
        try {
            // Lấy ngày sinh từ chuỗi
            LocalDate birthDate = LocalDate.parse(dateOfBirth);

            // Lấy ngày hiện tại
            LocalDate currentDate = LocalDate.now();

            // Tính khoảng cách thời gian từ ngày sinh đến ngày hiện tại
            Period period = Period.between(birthDate, currentDate);


            // Kiểm tra nếu tuổi là 24 tuổi trở lên

            return (period.getYears() > 24 || (period.getYears() == 18 && period.getMonths() > 0) || (period.getYears() == 16 && period.getMonths() == 0 && period.getDays() >= 0));

        } catch (DateTimeParseException e) {
            // Xử lý ngoại lệ nếu định dạng ngày không hợp lệ
            System.out.println("Ngày sinh không hợp lệ! Vui lòng nhập theo định dạng yyyy-MM-dd.");
            return false;
        }
    }

    public void updateUser(String fullname, boolean gender, String mobile, String address, Date dateOfBirth, int userId, String pictureName) {
        try {
            String sql = """
                         UPDATE [dbo].[User]
                            SET [fullName] = ?
                               ,[gender] = ?
                               ,[mobile] = ?
                               ,[address] = ?
                               ,[dateOfBirth] =?
                               ,[picture]=?
                          WHERE userId = ?""";
            stm = connection.prepareStatement(sql);
            stm.setInt(7, userId);
            stm.setString(1, fullname);
            stm.setBoolean(2, gender);
            stm.setString(3, mobile);
            stm.setString(4, address);
            stm.setDate(5, dateOfBirth);
            stm.setString(6, pictureName);
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changePassword(String username, String newPassword) {
        try {
            String sql = """
                         UPDATE [dbo].[User]
                            SET 
                               [password] = ?                                                           
                          WHERE username = ?""";
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setString(2, username);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getFullByUsernamePassword(String username, String password) {
        try {
            String query = """
                           SELECT [userId]
                                 ,[maso]
                                 ,[username]
                                 ,[password]
                                 ,[fullName]
                                 ,[email]
                                 ,[gender]
                                 ,[mobile]
                                 ,[address]
                                 ,[dateOfBirth]
                             FROM [dbo].[User]
                             where username = ? and password = ?""";

            stm = connection.prepareStatement(query);
            stm.setString(1, username);
            stm.setString(2, password);
            rs = stm.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void capNhatMatKhau(String email, String matKhauMoi) {
        String query = "UPDATE [User] SET [password] = ? WHERE [email] = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, matKhauMoi);
            statement.setString(2, email);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Role> getRolesByUserId(int userId) {
        List<Role> roles = new ArrayList<>();
        try {
            String sql = """
                 SELECT r.roleId, r.roleName
                 FROM dbo.User_Role ur
                 JOIN dbo.Role r ON ur.roleId = r.roleId
                 WHERE ur.userId = ?;""";

            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int roleId = rs.getInt("roleId");
                String roleName = rs.getString("roleName");
                Role role = new Role(roleId, roleName);
                roles.add(role);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }

    public boolean isValidFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return false;
        }
        String[] words = fullName.trim().split("\\s+");
        if (words.length < 2) {
            return false;
        }
        for (String word : words) {
            if (word.length() < 3 || !word.matches("[a-zA-Z]+")) {
                return false;
            }
        }
        return true;
    }

    public boolean isValidAddress(String address) {
        return address != null && address.trim().length() >= 2;
    }

    public boolean checkMobileExist(String mobile, int userId) {
        try {
            String sql = "SELECT COUNT(*) FROM [dbo].[User] WHERE mobile = ? AND userId != ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, mobile);
            stm.setInt(2, userId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getStudents() {
        List<User> students = new ArrayList<>();
        try {
            String sql = "SELECT u.picture, u.userId, u.maso, u.username, u.password, u.fullName, u.email, u.gender, u.mobile, u.address, u.dateOfBirth, u.active, ur.roleId "
                    + "FROM [User] u "
                    + "INNER JOIN User_Role ur ON u.userId = ur.userId "
                    + "WHERE ur.roleId = 3";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User student = new User();
                student.setPicture(rs.getString("picture"));
                student.setUserId(rs.getInt("userId"));
                student.setMaso(rs.getString("maso"));
                student.setUserName(rs.getString("username"));
                student.setPassword(rs.getString("password"));
                student.setFullName(rs.getString("fullName"));
                student.setEmail(rs.getString("email"));
                student.setGender(rs.getBoolean("gender"));
                student.setMobile(rs.getString("mobile"));
                student.setAddress(rs.getString("address"));
                student.setDateOfBirth(rs.getDate("dateOfBirth"));
                student.setActive(rs.getBoolean("active")); // Thêm dòng này
                students.add(student);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public List<User> getStaff() {
        List<User> Staff = new ArrayList<>();
        try {
            String sql = "SELECT u.picture, u.userId, u.maso, u.username, u.password, u.fullName, u.email, u.gender, u.mobile, u.address, u.dateOfBirth, u.active, ur.roleId "
                    + "FROM [User] u "
                    + "INNER JOIN User_Role ur ON u.userId = ur.userId "
                    + "WHERE ur.roleId = 2";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User staff = new User();
                staff.setPicture(rs.getString("picture"));
                staff.setUserId(rs.getInt("userId"));
                staff.setMaso(rs.getString("maso"));
                staff.setUserName(rs.getString("username"));
                staff.setPassword(rs.getString("password"));
                staff.setFullName(rs.getString("fullName"));
                staff.setEmail(rs.getString("email"));
                staff.setGender(rs.getBoolean("gender"));
                staff.setMobile(rs.getString("mobile"));
                staff.setAddress(rs.getString("address"));
                staff.setDateOfBirth(rs.getDate("dateOfBirth"));
                staff.setActive(rs.getBoolean("active")); // Thêm dòng này
                Staff.add(staff);
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Staff;
    }



        public void updateUserActiveStatus(int userId, int active) {
        String sql = "UPDATE [User] SET active = ? WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, active);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> searchStudentByKeyWord(String keyword) {
        List<User> users = new ArrayList<>();
        try {
            String sql = """
                    SELECT u.*
                     FROM [dbo].[User] u
                     JOIN [dbo].[User_Role] ur ON u.[userId] = ur.[userId]
                     WHERE ur.[roleId] = 3
                       AND u.[fullName] LIKE ?""";
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("userId"));
                u.setMaso(rs.getString("maso"));
                u.setUserName(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullName(rs.getString("fullName"));
                u.setEmail(rs.getString("email"));
                u.setGender(rs.getBoolean("gender"));
                u.setMobile(rs.getString("mobile"));
                u.setAddress(rs.getString("address"));
                u.setDateOfBirth(rs.getDate("dateOfBirth"));
                u.setPicture(rs.getString("picture"));
                u.setActive(rs.getBoolean("active"));
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public List<User> searchStaffByKeyWord(String keyword) {
        List<User> users = new ArrayList<>();
        try {
            String sql = """
                     SELECT u.*
                     FROM [dbo].[User] u
                     JOIN [dbo].[User_Role] ur ON u.[userId] = ur.[userId]
                     WHERE ur.[roleId] = 2
                       AND u.[fullName] LIKE ?""";
            stm = connection.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("userId"));
                u.setMaso(rs.getString("maso"));
                u.setUserName(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullName(rs.getString("fullName"));
                u.setEmail(rs.getString("email"));
                u.setGender(rs.getBoolean("gender"));
                u.setMobile(rs.getString("mobile"));
                u.setAddress(rs.getString("address"));
                u.setDateOfBirth(rs.getDate("dateOfBirth"));
                u.setPicture(rs.getString("picture"));
                u.setActive(rs.getBoolean("active"));
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }


    public boolean checkLogin(String username, String password, String role) {
        String strSQL = "SELECT u.userId FROM [dbo].[User] u "
                + "JOIN [dbo].[User_Role] ur ON u.userId = ur.userId "
                + "JOIN [dbo].[Role] r ON ur.roleId = r.roleId "
                + "WHERE u.userName = ? AND u.password = ? AND r.roleName =?";
        try (PreparedStatement stm = connection.prepareStatement(strSQL)) {
            stm.setString(1, username);
            stm.setString(2, password);
            stm.setString(3, role);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean checkEnoughAge(Date dateOfBirth, int requiredAge) {
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(dateOfBirth);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // Check if the birth date hasn't occurred yet this year
        if (today.get(Calendar.MONTH) + 1 < birthDate.get(Calendar.MONTH) + 1
                || (today.get(Calendar.MONTH) + 1 == birthDate.get(Calendar.MONTH) + 1
                && today.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age >= requiredAge;
    }


}
