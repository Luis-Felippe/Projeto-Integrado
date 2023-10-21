public class Main {
    public static void main(String[] args) {
        try {
            var InstacedJdbcConnection = new JdbcConnection();

            var conection = InstacedJdbcConnection.getConnection();

            var statement = conection.createStatement();

            var resultSet = statement.executeQuery("Select * from Aluno");

            while (resultSet.next()) {
                String coluna1 = resultSet.getString("matricula");
                String coluna2 = resultSet.getString("nome");
                System.out.println(coluna1 + ", " + coluna2);
            }
        } catch (Exception ex) {
            System.out.print("Error: " + ex.getMessage());
        }
    }
}
