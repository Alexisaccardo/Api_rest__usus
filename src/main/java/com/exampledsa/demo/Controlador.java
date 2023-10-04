package com.exampledsa.demo;

import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controlador {

    @PostMapping("/register_user")
    public User register_user(@RequestBody User user) throws SQLException, ClassNotFoundException {

        String code = user.getCode();
        String name = user.getName();
        String lastname = user.getLastname();
        String cellphone = user.getCellphone();
        String address = user.getAddress();
        String city = user.getCity();

        if (code==null || code.equals("") || code.length() < 0 || name==null ||name.equals("") || name.length() < 0 ||
                lastname==null || lastname.equals("") || lastname.length() < 0 || cellphone==null || cellphone.equals("") ||
                cellphone.length() < 0 || address==null || address.equals("") || address.length() < 0 ||
                city==null || city.equals("") || city.length() < 0){

            return new User(null, null, null, null, null, null);

        } else {
            user = Register(code, name, lastname, cellphone, address, city);
        }

        return user;
    }

    private User Register(String code, String name, String lastname, String cellphone, String address, String city) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/users";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");


            // Sentencia INSERT
            String sql = "INSERT INTO user (code, name, lastname, cellphone, address, city) VALUES (?, ?, ?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastname);
            preparedStatement.setString(4, cellphone);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, city);

            // Ejecutar la sentencia
            int files = preparedStatement.executeUpdate();

            if (files > 0) {
                System.out.println("Usuario registrado de manera exitosa.");
                return new User(code, name, lastname, cellphone, address, city);
            } else {
                System.out.println("No se pudo registrar el usuario");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new User(null, null, null, null, null, null);
    }


    @PostMapping("/edit_user")
    public User edit_user(@RequestBody User user) throws SQLException, ClassNotFoundException {


        String code = user.getCode();
        String name = user.getName();
        String lastname = user.getLastname();
        String cellphone = user.getCellphone();
        String address = user.getAddress();
        String city = user.getCity();

        if (code==null || code.equals("") || code.length() < 0 || name==null ||name.equals("") || name.length() < 0 ||
                lastname==null || lastname.equals("") || lastname.length() < 0 || cellphone==null || cellphone.equals("") ||
                cellphone.length() < 0 || address==null || address.equals("") || address.length() < 0 ||
                city==null || city.equals("") || city.length() < 0){

            return new User(null, null, null, null, null, null);
        } else {
            user = Edit(code, name, lastname, cellphone, address, city);
        }
        return user;
    }

    private User Edit(String code, String name, String lastname, String cellphone, String address, String city) throws ClassNotFoundException, SQLException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/users";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consult = "UPDATE user SET name = ?, lastname = ?, cellphone = ?, address = ?, city = ? WHERE code = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consult);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lastname);
        preparedStatement.setString(3, cellphone);
        preparedStatement.setString(4, address);
        preparedStatement.setString(5, city);
        preparedStatement.setString(6, code);

        int files = preparedStatement.executeUpdate();
        if (files > 0) {
            System.out.println("Usuario actualizado de manera exitosa");
            return new User(code, name, lastname, cellphone, address, city);
        } else {
            System.out.println("No se encontro el usuario para actualizar");
        }

        preparedStatement.close();
        connection2.close();
        return new User(null, null, null, null, null, null);
    }


    @DeleteMapping("/delete_user")
    public User delete_user(@RequestBody User user) throws SQLException, ClassNotFoundException {
        String code = user.getCode();
        if (user.getCode() == null || user.getCode().equals("") || user.getCode().length()<0) {
            return new User(null, null, null, null, null, null);
        }else {

            int f = Delete(code);
            if (f==0){
                return new User("No se encuentra usuario", null, null, null, null, null);
            }else {

            }
        }

        return user;
    }

    private int Delete(String code) throws ClassNotFoundException, SQLException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/users";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        String sentenciaSQL = "DELETE FROM user WHERE code = ?";
        PreparedStatement prepareStatement = connection2.prepareStatement(sentenciaSQL);
        prepareStatement.setString(1, code);
        int f = prepareStatement.executeUpdate();

        System.out.println("Usuario eliminado correctamente");
        return f;
    }


    @GetMapping("/search_user")
    public List<User> search_User() throws SQLException, ClassNotFoundException {

        List<User> list = Select_consult();

        return list;
    }

    private List<User> Select_consult() throws ClassNotFoundException, SQLException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/users";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM user");

        List<User> list = new ArrayList<>();

        while (resultSet2.next()) {
            String code = resultSet2.getString("code");
            String name = resultSet2.getString("name");
            String lastname = resultSet2.getString("lastname");
            String cellphone = resultSet2.getString("cellphone");
            String address = resultSet2.getString("address");
            String city = resultSet2.getString("city");

            User user = new User(code, name, lastname, cellphone, address, city);

            list.add(user);

        }
        return list;
    }


    @GetMapping("/search_one/{code}")
    public User search_user(@PathVariable String code) throws SQLException, ClassNotFoundException {

        User user;

        if (code==null || code.equals("") || code.length()<0) {

            return new User("No se encuentra usuario", null, null, null, null, null);

        }else {

            user = Select_user(code);
        }


        return user;
    }

    private User Select_user(String code) throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/users";
        String username = "root";
        String password = "";

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consult_SQL = "SELECT * FROM user WHERE code = ?";

        PreparedStatement statement = connection.prepareStatement(consult_SQL);
        statement.setString(1, code); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet2 = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet2.next()) {

            code = resultSet2.getString("code");
            String name = resultSet2.getString("name");
            String lastname = resultSet2.getString("lastname");
            String cellphone = resultSet2.getString("cellphone");
            String address = resultSet2.getString("address");
            String city = resultSet2.getString("city");

            User user = new User(code, name, lastname, cellphone, address, city);

            return user;
        }
        // Cerrar recursos
        resultSet2.close();
        statement.close();
        connection.close();

        return null;
    }


}




