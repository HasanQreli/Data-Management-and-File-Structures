package ceng.ceng351.cengfactorydb;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CENGFACTORYDB implements ICENGFACTORYDB{
    /**
     * Place your initialization code inside if required.
     *
     * <p>
     * This function will be called before all other operations. If your implementation
     * need initialization , necessary operations should be done inside this function. For
     * example, you can set your connection to the database server inside this function.
     */

    private static String user = "e2580751"; // TODO: Your userName
    private static String password = "6X1BfjuctkQ#"; //  TODO: Your password
    private static String host = "144.122.71.128"; // host name
    private static String database = "db2580751"; // TODO: Your database name
    private static int port = 8080; // port
    private Connection connection;
    public void initialize() {
        String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?useSSL=false";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection =  DriverManager.getConnection(url, this.user, this.password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Should create the necessary tables when called.
     *
     * @return the number of tables that are created successfully.
     */
    public int createTables() {
        int table_count = 0;
        String create_query_factory =
                "CREATE TABLE IF NOT EXISTS Factory(" +
                        "factoryId INT , " +
                        "factoryName VARCHAR(30), " +
                        "factoryType VARCHAR(30), " +
                        "country VARCHAR(30), " +
                        "PRIMARY KEY (factoryId) );";

        String create_query_employee =
                "CREATE TABLE IF NOT EXISTS Employee(" +
                        "employeeId INT, " +
                        "employeeName VARCHAR(30), " +
                        "department VARCHAR(30), " +
                        "salary INT, " +
                        "PRIMARY KEY (employeeId) );";

        String create_query_works_in =
                "CREATE TABLE IF NOT EXISTS Works_In(" +
                        "factoryId INT, " +
                        "employeeId INT, " +
                        "startDate DATE, " +
                        "FOREIGN KEY (factoryId) REFERENCES Factory(factoryId), " +
                        //"ON DELETE CASCADE, " +
                        "FOREIGN KEY (employeeId) REFERENCES Employee(employeeId)  ON DELETE CASCADE, " +
                        //"ON DELETE CASCADE, " +
                        "PRIMARY KEY (factoryId, employeeId) );";

        String create_query_product =
                "CREATE TABLE IF NOT EXISTS Product(" +
                        "productId INT , " +
                        "productName VARCHAR(30), " +
                        "productType VARCHAR(30), " +
                        "PRIMARY KEY (productId) );";

        String create_query_produce =
                "CREATE TABLE IF NOT EXISTS Produce(" +
                        "factoryId INT , " +
                        "productId INT , " +
                        "amount INT, " +
                        "productionCost INT, " +
                        "FOREIGN KEY (factoryId) REFERENCES Factory(factoryId), " +
                        //"ON DELETE CASCADE, " +
                        "FOREIGN KEY (productId) REFERENCES Product(productId), " +
                        //"ON DELETE CASCADE, " +
                        "PRIMARY KEY (factoryId, productId) );";

        String create_query_shipment =
                "CREATE TABLE IF NOT EXISTS Shipment(" +
                        "factoryId INT , " +
                        "productId INT , " +
                        "amount INT, " +
                        "pricePerUnit INT, " +
                        "FOREIGN KEY (factoryId) REFERENCES Factory(factoryId), " +
                        //"ON DELETE CASCADE, " +
                        "FOREIGN KEY (productId) REFERENCES Product(productId), " +
                        //"ON DELETE CASCADE, " +
                        "PRIMARY KEY (factoryId, productId) ) ;";


        String[] create_query_list = new String[]{
                create_query_factory,
                create_query_employee,
                create_query_works_in,
                create_query_product,
                create_query_produce,
                create_query_shipment
        };

        for(String query: create_query_list){
            try {
                Statement statement = this.connection.createStatement();
                statement.executeUpdate(query);
                table_count++;
            } catch (SQLException e){
                e.printStackTrace();
            }
        }

        return table_count;
    }

    /**
     * Should drop the tables if exists when called.
     *
     * @return the number of tables are dropped successfully.
     */
    public int dropTables() {
        int table_count = 0;
        String[] drop_table_list = new String[]{
                "DROP TABLE IF EXISTS Shipment;",
                "DROP TABLE IF EXISTS Produce;",
                "DROP TABLE IF EXISTS Product;",
                "DROP TABLE IF EXISTS Works_In;",
                "DROP TABLE IF EXISTS Employee;",
                "DROP TABLE IF EXISTS Factory;"
        };

        for(String table: drop_table_list){
            try {
                Statement statement = this.connection.createStatement();
                statement.executeUpdate(table);
                table_count++;

            } catch (SQLException e){
                e.printStackTrace();
            }
        }



        return table_count;
    }

    /**
     * Should insert an array of Factory into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertFactory(Factory[] factories) {

        int inserted = 0;

        for (int i = 0; i < factories.length; i++)
        {
            try {
                Factory fac = factories[i];

                PreparedStatement statement=this.connection.prepareStatement("INSERT INTO Factory VALUES(?,?,?,?)");
                statement.setInt(1, fac.getFactoryId());
                statement.setString(2, fac.getFactoryName());
                statement.setString(3, fac.getFactoryType());
                statement.setString(4, fac.getCountry());

                statement.executeUpdate();

                inserted++;

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted;
    }

    /**
     * Should insert an array of Employee into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertEmployee(Employee[] employees) {
        int inserted = 0;

        for (int i = 0; i < employees.length; i++)
        {
            try {
                Employee emp = employees[i];

                PreparedStatement statement=this.connection.prepareStatement("INSERT INTO Employee VALUES(?,?,?,?)");
                statement.setInt(1, emp.getEmployeeId());
                statement.setString(2, emp.getEmployeeName());
                statement.setString(3, emp.getDepartment());
                statement.setInt(4, emp.getSalary());

                statement.executeUpdate();

                inserted++;

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted;
    }

    /**
     * Should insert an array of WorksIn into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertWorksIn(WorksIn[] worksIns) {
        int inserted = 0;

        for (int i = 0; i < worksIns.length; i++)
        {
            try {
                WorksIn wI = worksIns[i];

                PreparedStatement statement=this.connection.prepareStatement("INSERT INTO Works_In VALUES(?,?,?)");
                statement.setInt(1, wI.getFactoryId());
                statement.setInt(2, wI.getEmployeeId());
                statement.setString(3, wI.getStartDate());

                statement.executeUpdate();

                inserted++;

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted;
    }

    /**
     * Should insert an array of Product into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduct(Product[] products) {
        int inserted = 0;

        for (int i = 0; i < products.length; i++)
        {
            try {
                Product prod = products[i];

                PreparedStatement statement=this.connection.prepareStatement("INSERT INTO Product VALUES(?,?,?)");
                statement.setInt(1, prod.getProductId());
                statement.setString(2, prod.getProductName());
                statement.setString(3, prod.getProductType());

                statement.executeUpdate();

                inserted++;

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted;
    }


    /**
     * Should insert an array of Produce into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduce(Produce[] produces) {
        int inserted = 0;

        for (int i = 0; i < produces.length; i++)
        {
            try {
                Produce prod = produces[i];

                PreparedStatement statement=this.connection.prepareStatement("INSERT INTO Produce VALUES(?, ?, ?, ?)");
                statement.setInt(1, prod.getFactoryId());
                statement.setInt(2, prod.getProductId());
                statement.setInt(3, prod.getAmount());
                statement.setInt(4, prod.getProductionCost());

                statement.executeUpdate();

                inserted++;

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted;
    }


    /**
     * Should insert an array of Shipment into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertShipment(Shipment[] shipments) {
        int inserted = 0;

        for (int i = 0; i < shipments.length; i++)
        {
            try {
                Shipment ship = shipments[i];

                PreparedStatement statement=this.connection.prepareStatement("INSERT INTO Shipment VALUES(?, ?, ?, ?)");
                statement.setInt(1, ship.getFactoryId());
                statement.setInt(2, ship.getProductId());
                statement.setInt(3, ship.getAmount());
                statement.setInt(4, ship.getPricePerUnit());

                statement.executeUpdate();

                inserted++;

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return inserted;
    }

    /**
     * Should return all factories that are located in a particular country.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesForGivenCountry(String country) {

        Factory[] factories = null;
        String query = "SELECT DISTINCT F.factoryId, F.factoryName, F.factoryType, F.country " +
                "FROM Factory F " +
                "WHERE F.country = ? " +
                "ORDER BY F.factoryId ASC;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, country);
            ResultSet rs = stmt.executeQuery();

            // Get the count of rows in ResultSet
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst(); // Move cursor back to the start
            }

            factories = new Factory[rowCount];
            int index = 0;

            while (rs.next()) {
                Factory factory = new Factory(rs.getInt("factoryId"), rs.getString("factoryName"), rs.getString("factoryType"), rs.getString("country"));


                factories[index] = factory;
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factories;
    }



    /**
     * Should return all factories without any working employees.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesWithoutAnyEmployee() {
        Factory[] factories = null;
        String query = "SELECT DISTINCT F.factoryId, F.factoryName, F.factoryType, F.country " +
                "FROM Factory F " +
                "WHERE NOT EXISTS(" +
                "   SELECT WI.factoryId" +
                "   FROM Works_In WI" +
                "   WHERE F.factoryId = WI.factoryId) " +
                "ORDER BY F.factoryId ASC;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            //stmt.setString(1, country);
            ResultSet rs = stmt.executeQuery();

            // Get the count of rows in ResultSet
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst(); // Move cursor back to the start
            }

            factories = new Factory[rowCount];
            int index = 0;

            while (rs.next()) {
                Factory factory = new Factory(rs.getInt("factoryId"), rs.getString("factoryName"), rs.getString("factoryType"), rs.getString("country"));


                factories[index] = factory;
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factories;
    }

    /**
     * Should return all factories that produce all products for a particular productType
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesProducingAllForGivenType(String productType) {
        Factory[] factories = null;
        String query = "SELECT DISTINCT F.factoryId, F.factoryName, F.factoryType, F.country " +
                "FROM Factory F, Produce P, Product Pdct " +
                "WHERE F.factoryId = P.factoryId AND P.productId = Pdct.productId AND Pdct.productType = ? AND " +
                "NOT EXISTS(" +
                "   SELECT Pdct1.productId" +
                "   FROM Product Pdct1" +
                "   WHERE Pdct1.productType = ?" +
                "   EXCEPT" +
                "   SELECT P1.productId" +
                "   FROM Produce P1" +
                "   WHERE P1.factoryId = F.factoryId" +
                "   ) " +
                "ORDER BY F.factoryId ASC;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productType);
            stmt.setString(2, productType);
            ResultSet rs = stmt.executeQuery();

            // Get the count of rows in ResultSet
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst(); // Move cursor back to the start
            }

            factories = new Factory[rowCount];
            int index = 0;

            while (rs.next()) {
                Factory factory = new Factory(rs.getInt("factoryId"), rs.getString("factoryName"), rs.getString("factoryType"), rs.getString("country"));


                factories[index] = factory;
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return factories;
    }


    /**
     * Should return the products that are produced in a particular factory but
     * don’t have any shipment from that factory.
     *
     * @return Product[]
     */
    //Bu sorunun cevap dogru da mantikta yanlis olmali
    public Product[] getProductsProducedNotShipped() {
        Product[] products = null;
        String query = "SELECT DISTINCT Pdct.productId, Pdct.productName, Pdct.productType " +
                "FROM Produce P, Product Pdct " +
                "WHERE P.productId = Pdct.productId AND NOT EXISTS( " +
                "   SELECT S.productId " +
                "   FROM Shipment S " +
                "   WHERE S.productId = P.productId AND S.factoryId = P.factoryId) " +
                "ORDER BY Pdct.productId ASC; ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            // Get the count of rows in ResultSet
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst(); // Move cursor back to the start
            }

            products = new Product[rowCount];
            int index = 0;

            while (rs.next()) {
                Product product = new Product(rs.getInt("productId"), rs.getString("productName"), rs.getString("productType"));


                products[index] = product;
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    /**
     * For a given factoryId and department, should return the average salary of
     *     the employees working in that factory and that specific department.
     *
     * @return double
     */
    public double getAverageSalaryForFactoryDepartment(int factoryId, String department) {
        double salary = 0;
        String query = "SELECT AVG(E.salary) " +
                "FROM Factory F, Employee E, Works_In WI " +
                "WHERE F.factoryId = ? AND E.department = ? AND WI.factoryId=F.factoryId AND WI.employeeId=E.employeeId ;";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, factoryId);
            stmt.setString(2, department);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                salary = rs.getDouble(1); // Retrieve the average salary from the ResultSet
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salary;
    }



    /**
     * Should return the most profitable products for each factory
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProducts() {
        QueryResult.MostValueableProduct[] products = null;
        String query = "SELECT DISTINCT Temp.factoryId, Temp.productId, Temp.productName, Temp.productType, (Temp.profit) AS maxprofit " +
                "FROM ( " +
                "   SELECT F.factoryId, Pdct.productId, Pdct.productName, Pdct.productType, (S.amount * S.pricePerUnit - P.amount * P.productionCost) AS profit " +
                "   FROM Factory F, Produce P, Shipment S, Product Pdct " +
                "   WHERE P.productId = Pdct.productId AND " +
                "         S.productId = Pdct.productId AND " +
                "         S.factoryId = F.factoryId AND " +
                "         P.factoryId = F.factoryId " +
                "   UNION " +
                "   SELECT F.factoryId, Pdct.productId, Pdct.productName, Pdct.productType, (-P.amount * P.productionCost) AS profit " +
                "   FROM Factory F, Produce P, Product Pdct " +
                "   WHERE P.productId = Pdct.productId AND " +
                "         P.factoryId = F.factoryId AND " +
                "         NOT EXISTS ( " +
                "             SELECT S.productId " +
                "             FROM Shipment S " +
                "             WHERE S.productId = Pdct.productId AND " +
                "                   S.factoryId = F.factoryId " +
                "         ) " +
                ") AS Temp " +
                "WHERE Temp.profit = ( " +
                "   SELECT MAX(Temp1.profit) " +
                "   FROM ( " +
                "       SELECT F.factoryId, Pdct.productId, Pdct.productName, Pdct.productType, (S.amount * S.pricePerUnit - P.amount * P.productionCost) AS profit " +
                "       FROM Factory F, Produce P, Shipment S, Product Pdct " +
                "       WHERE P.productId = Pdct.productId AND " +
                "             S.productId = Pdct.productId AND " +
                "             S.factoryId = F.factoryId AND " +
                "             P.factoryId = F.factoryId " +
                "       UNION " +
                "       SELECT F.factoryId, Pdct.productId, Pdct.productName, Pdct.productType, (-P.amount * P.productionCost) AS profit " +
                "       FROM Factory F, Produce P, Product Pdct " +
                "       WHERE P.productId = Pdct.productId AND " +
                "             P.factoryId = F.factoryId AND " +
                "             NOT EXISTS ( " +
                "                 SELECT S.productId " +
                "                 FROM Shipment S " +
                "                 WHERE S.productId = Pdct.productId AND " +
                "                       S.factoryId = F.factoryId " +
                "             ) " +
                "       ) AS Temp1 " +
                "       WHERE Temp.factoryId = Temp1.factoryId " +
                ") " +
                "ORDER BY maxprofit DESC, Temp.factoryId ASC;";


        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            // Get the count of rows in ResultSet
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst(); // Move cursor back to the start
            }

            products = new QueryResult.MostValueableProduct[rowCount];
            int index = 0;

            while (rs.next()) {
                QueryResult.MostValueableProduct product = new QueryResult.MostValueableProduct(rs.getInt("factoryId"), rs.getInt("productId"), rs.getString("productName"), rs.getString("productType"), rs.getDouble("maxprofit"));


                products[index] = product;
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * For each product, return the factories that gather the highest profit
     * for that product
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProductsOnFactory() {
        QueryResult.MostValueableProduct[] products = null;
        String query = "SELECT DISTINCT Temp.factoryId, Temp.productId, Temp.productName, Temp.productType, (Temp.profit) AS maxprofit " +
                "FROM ( " +
                "   SELECT F.factoryId, Pdct.productId, Pdct.productName, Pdct.productType, (S.amount * S.pricePerUnit - P.amount * P.productionCost) AS profit " +
                "   FROM Factory F, Produce P, Shipment S, Product Pdct " +
                "   WHERE P.productId = Pdct.productId AND " +
                "         S.productId = Pdct.productId AND " +
                "         S.factoryId = F.factoryId AND " +
                "         P.factoryId = F.factoryId " +
                "   UNION " +
                "   SELECT F.factoryId, Pdct.productId, Pdct.productName, Pdct.productType, (-P.amount * P.productionCost) AS profit " +
                "   FROM Factory F, Produce P, Product Pdct " +
                "   WHERE P.productId = Pdct.productId AND " +
                "         P.factoryId = F.factoryId AND " +
                "         NOT EXISTS ( " +
                "             SELECT S.productId " +
                "             FROM Shipment S " +
                "             WHERE S.productId = Pdct.productId AND " +
                "                   S.factoryId = F.factoryId " +
                "         ) " +
                ") AS Temp " +
                "WHERE Temp.profit = ( " +
                "   SELECT MAX(Temp1.profit) " +
                "   FROM ( " +
                "       SELECT F.factoryId, Pdct.productId, Pdct.productName, Pdct.productType, (S.amount * S.pricePerUnit - P.amount * P.productionCost) AS profit " +
                "       FROM Factory F, Produce P, Shipment S, Product Pdct " +
                "       WHERE P.productId = Pdct.productId AND " +
                "             S.productId = Pdct.productId AND " +
                "             S.factoryId = F.factoryId AND " +
                "             P.factoryId = F.factoryId " +
                "       UNION " +
                "       SELECT F.factoryId, Pdct.productId, Pdct.productName, Pdct.productType, (-P.amount * P.productionCost) AS profit " +
                "       FROM Factory F, Produce P, Product Pdct " +
                "       WHERE P.productId = Pdct.productId AND " +
                "             P.factoryId = F.factoryId AND " +
                "             NOT EXISTS ( " +
                "                 SELECT S.productId " +
                "                 FROM Shipment S " +
                "                 WHERE S.productId = Pdct.productId AND " +
                "                       S.factoryId = F.factoryId " +
                "             ) " +
                "       ) AS Temp1 " +
                "       WHERE Temp.productId = Temp1.productId " +
                ") " +
                "ORDER BY maxprofit DESC, Temp.productId ASC;";


        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            // Get the count of rows in ResultSet
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst(); // Move cursor back to the start
            }

            products = new QueryResult.MostValueableProduct[rowCount];
            int index = 0;

            while (rs.next()) {
                QueryResult.MostValueableProduct product = new QueryResult.MostValueableProduct(rs.getInt("factoryId"), rs.getInt("productId"), rs.getString("productName"), rs.getString("productType"), rs.getDouble("maxprofit"));


                products[index] = product;
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    /**
     * For each department, should return all employees that are paid under the
     *     average salary for that department. You consider the employees
     *     that do not work earning ”0”.
     *
     * @return QueryResult.LowSalaryEmployees[]
     */
    //Burda belki 0 as salary filan yapmak gereklidir bilemiom
    public QueryResult.LowSalaryEmployees[] getLowSalaryEmployeesForDepartments() {
        QueryResult.LowSalaryEmployees[] employees = null;
        String query = "SELECT DISTINCT Temp.employeeId, Temp.employeeName, Temp.department, Temp.salary " +
                "FROM ( SELECT DISTINCT E.employeeId, E.employeeName, E.department, E.salary " +
                "FROM Employee E " +
                "WHERE EXISTS( " +
                "               SELECT WI.employeeId " +
                "               FROM Works_In WI " +
                "               WHERE WI.employeeId = E.employeeId ) " +
                "AND E.salary < ( " +
                "   SELECT AVG( coalesce(E1.salary, 0)) " +
                "   FROM Employee E1 " +
                "   WHERE E1.department = E.department " +
                ") " +
                "UNION " +
                "SELECT DISTINCT E.employeeId, E.employeeName, E.department, 0  " +
                "FROM Employee E " +
                "WHERE NOT EXISTS( " +
                "                SELECT WI.employeeId " +
                "                FROM Works_In WI " +
                "                WHERE WI.employeeId = E.employeeId)) AS Temp " +
                "ORDER BY Temp.employeeId ASC;";


        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            // Get the count of rows in ResultSet
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst(); // Move cursor back to the start
            }

            employees = new QueryResult.LowSalaryEmployees[rowCount];
            int index = 0;

            while (rs.next()) {
                QueryResult.LowSalaryEmployees employee = new QueryResult.LowSalaryEmployees(rs.getInt("employeeId"), rs.getString("employeeName"), rs.getString("department"), rs.getInt("salary"));


                employees[index] = employee;
                index++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }


    /**
     * For the products of given productType, increase the productionCost of every unit by a given percentage.
     *
     * @return number of rows affected
     */
    public int increaseCost(String productType, double percentage) {
        int updateCount = 0;
        String query = "UPDATE Produce P " +
                "SET P.productionCost = P.productionCost*? " +
                "WHERE EXISTS( " +
                "   SELECT Pdct.productId " +
                "   FROM Product Pdct " +
                "   WHERE Pdct.productId = P.productId AND Pdct.productType = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, percentage);
            stmt.setString(2, productType);
            updateCount = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updateCount;
    }



    /**
     * Deleting all employees that have not worked since the given date.
     *
     * @return number of rows affected
     */
    public int deleteNotWorkingEmployees(String givenDate) {
        int deleteCount = 0;
        String query = "DELETE FROM Employee " +
                "WHERE NOT EXISTS ( " +
                "   SELECT 1 " +
                "   FROM Works_In WI " +
                "   WHERE WI.employeeId = Employee.employeeId AND WI.startDate > ? ) ";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, givenDate);
            deleteCount = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deleteCount;
    }





    /**
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     *  THE METHODS AFTER THIS LINE WILL NOT BE GRADED.
     *  YOU DON'T HAVE TO SOLVE THEM, LEAVE THEM AS IS IF YOU WANT.
     *  IF YOU HAVE ANY QUESTIONS, REACH ME VIA EMAIL.
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     */

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Peers are considered tied and receive the same rank. After
     * that, there should be a gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Everything is the same but after ties, there should be no
     * gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank2() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each factory, calculate the most profitable 4th product.
     *
     * @return QueryResult.FactoryProfit
     */
    public QueryResult.FactoryProfit calculateFourth() {
        return new QueryResult.FactoryProfit(0,0,0);
    }

    /**
     * Determine the salary variance between an employee and another
     * one who began working immediately after the first employee (by
     * startDate), for all employees.
     *
     * @return QueryResult.SalaryVariant[]
     */
    public QueryResult.SalaryVariant[] calculateVariance() {
        return new QueryResult.SalaryVariant[0];
    }

    /**
     * Create a method that is called once and whenever a Product starts
     * losing money, deletes it from Produce table
     *
     * @return void
     */
    public void deleteLosing() {

    }
}
