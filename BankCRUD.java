import java.sql.*;

public class BankCRUD {

    public static void main(String[] args) {
        String url = "jdbc:h2:./bank_data/db;DB_CLOSE_DELAY=-1";
        String user = "sa";
        String password = "";
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功");
            System.out.println();
            Statement stmt = conn.createStatement();
            String createTableSQL =
                "CREATE TABLE IF NOT EXISTS account (" +
                "  id           INT AUTO_INCREMENT PRIMARY KEY," +
                "  name         VARCHAR(50)  NOT NULL," +
                "  balance      DECIMAL(12,2) DEFAULT 0," +
                "  type         VARCHAR(20)  DEFAULT '活期'," +
                "  create_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";
            stmt.execute(createTableSQL);
            stmt.close();
            System.out.println("数据表 account 已就绪");
            System.out.println();

            System.out.println("1 插入3条账户数据");
            String insertSQL = "INSERT INTO account (name, balance, type) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSQL);
            pstmt.setString(1, "张三");
            pstmt.setDouble(2, 5000.00);
            pstmt.setString(3, "活期");
            pstmt.executeUpdate();
            pstmt.setString(1, "李四");
            pstmt.setDouble(2, 30000.00);
            pstmt.setString(3, "定期");
            pstmt.executeUpdate();
            pstmt.setString(1, "王五");
            pstmt.setDouble(2, 8000.00);
            pstmt.setString(3, "活期");
            pstmt.executeUpdate();
            pstmt.close();
            System.out.println("已插入3条账户数据");
            System.out.println();

            System.out.println("2 查询所有账户");
            String selectSQL = "SELECT * FROM account ORDER BY id";
            Statement queryStmt = conn.createStatement();
            ResultSet rs = queryStmt.executeQuery(selectSQL);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                String type = rs.getString("type");
                Timestamp time = rs.getTimestamp("create_time");
                System.out.printf("  ID %d  %s  余额 %.2f  %s  %s%n",
                                  id, name, balance, type, time);
            }
            rs.close();
            queryStmt.close();
            System.out.println();

            System.out.println("3 修改数据 给张三加500");
            PreparedStatement findStmt = conn.prepareStatement(
                "SELECT id, balance FROM account WHERE name = ?");
            findStmt.setString(1, "张三");
            ResultSet findRs = findStmt.executeQuery();
            if (findRs.next()) {
                double oldBalance = findRs.getDouble("balance");
                System.out.printf("  修改前 张三余额 %.2f%n", oldBalance);
            }
            findRs.close();
            findStmt.close();
            String updateSQL = "UPDATE account SET balance = balance + ? WHERE name = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
            updateStmt.setDouble(1, 500);
            updateStmt.setString(2, "张三");
            int rows = updateStmt.executeUpdate();
            updateStmt.close();
            System.out.printf("  更新了 %d 行%n", rows);
            System.out.println();

            System.out.println("3 确认修改结果");
            Statement verifyStmt = conn.createStatement();
            ResultSet verifyRs = verifyStmt.executeQuery(
                "SELECT id, name, balance, type FROM account ORDER BY id");
            while (verifyRs.next()) {
                System.out.printf("  ID %d  %s  余额 %.2f  %s%n",
                    verifyRs.getInt("id"),
                    verifyRs.getString("name"),
                    verifyRs.getDouble("balance"),
                    verifyRs.getString("type"));
            }
            verifyRs.close();
            verifyStmt.close();
            System.out.println();

            System.out.println("4 删除王五的账户");
            String deleteSQL = "DELETE FROM account WHERE name = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);
            deleteStmt.setString(1, "王五");
            int deletedRows = deleteStmt.executeUpdate();
            deleteStmt.close();
            System.out.printf("  删除了 %d 行%n", deletedRows);
            System.out.println();

            System.out.println("最终数据 共2条");
            Statement finalStmt = conn.createStatement();
            ResultSet finalRs = finalStmt.executeQuery(
                "SELECT id, name, balance, type FROM account ORDER BY id");
            while (finalRs.next()) {
                System.out.printf("  ID %d  %s  余额 %.2f  %s%n",
                    finalRs.getInt("id"),
                    finalRs.getString("name"),
                    finalRs.getDouble("balance"),
                    finalRs.getString("type"));
            }
            finalRs.close();
            finalStmt.close();

        } catch (ClassNotFoundException e) {
            System.out.println("错误 找不到H2数据库驱动");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("错误 数据库操作失败");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("数据库连接已关闭");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
