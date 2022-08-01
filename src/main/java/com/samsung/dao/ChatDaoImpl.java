package com.samsung.dao;

import com.samsung.domain.Chat;
import com.samsung.domain.Message;
import com.samsung.domain.Profile;
import org.sqlite.JDBC;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatDaoImpl implements ChatDao{
    private static final String CON_STR = "jdbc:sqlite:C:Users/pervu/OneDrive/Desktop/database.db";
    private static ChatDao instance = null;
    private final Connection connection;

    public ChatDaoImpl() throws SQLException, ClassNotFoundException {
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public static synchronized ChatDao getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null)
            instance = new ChatDaoImpl();
        return instance;
    }

    @Override
    public void insert(Chat chat) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO Chat(`name`, `usersId`, `isPrivate`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, chat.getName());
            statement.setObject(2, Chat.parseStringToListInt(chat.getUsersId()));
            statement.setObject(3, chat.getIsPrivate());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Chat chat) {
        try (PreparedStatement statement = this.connection.prepareStatement("UPDATE Chat SET name=?, usersId=?, isPrivate=? WHERE id = ?")) {
            statement.setObject(1, chat.getName());
            statement.setObject(2, Chat.parseStringToListInt(chat.getUsersId()));
            statement.setObject(3, chat.getIsPrivate());
            statement.setObject(4, chat.getId());

            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM Chat WHERE id = ?")) {
            statement.setObject(1, id);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Chat getById(int id) {
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Chat WHERE id="+id);
            return new Chat(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("usersId"),
                    resultSet.getString("isPrivate")
            );
            // Выполняем запрос
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Chat> getByName(String name) {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Chat WHERE name = ?")) {
            statement.setObject(1,name);
            ResultSet resultSet = statement.executeQuery();
            List<Chat> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Chat(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("usersId"),
                        resultSet.getString("isPrivate")
                ));
            }
            return list;
            // Выполняем запрос
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Chat> getAllByUserId(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Chat")) {
            ResultSet resultSet = statement.executeQuery();
            List<Chat> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(new Chat(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("usersId"),
                        resultSet.getString("isPrivate")
                ));
            }
            List<Chat> result = new ArrayList<>();
            for (Chat chat: list) {
                for (Profile profile : chat.getUsersId()) {
                    if (profile.getId() == id){
                        result.add(chat);
                    }
                }
            }
            return result;
            // Выполняем запрос
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
