package com.svartvalp.demo.Repositories;

import com.svartvalp.demo.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class HumanRepositoryImpl implements HumanRepository{

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Human> getAllHumans() {
        try {
            var connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            var statement = connection.prepareStatement("select * from human");
            var resultSet = statement.executeQuery();
            List<Human> humans = new LinkedList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                Human human = new Human(id, name, lastName, age);
                humans.add(human);
            }
            connection.close();
            return humans;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Integer> insertHuman(Human human) {
        try {
            var connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            var statement = connection.prepareStatement("insert into human(name, last_name, age) VALUES (?,?,?) returning id");
            statement.setString(1, human.getName());
            statement.setString(2, human.getLastName());
            statement.setInt(3, human.getAge());
            var result = statement.executeQuery();
            connection.close();
            if(result.next()) {
                return Optional.of(result.getInt("id"));
            } else return Optional.empty();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void updateHuman(int id, Human human) {
        try {
            var connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            var statement = connection.prepareStatement("update human set name = ?, last_name = ?, age = ? where id = ?");
            statement.setString(1, human.getName());
            statement.setString(2, human.getLastName());
            statement.setInt(3, human.getAge());
            statement.setInt(4, id);
            statement.execute();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void deleteHuman(int id) {
        try {
            var connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            var statement = connection.prepareStatement("delete from human where id = ?");
            statement.setInt(1, id);
            statement.execute();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public List<Human> getHumansByAge(int age) {
        try {
            var connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            var statement = connection.prepareStatement("select * from human where age = ?");
            statement.setInt(1, age);
            var resultSet = statement.executeQuery();
            List<Human> humans = new LinkedList<>();
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                Human human = new Human(id, name, lastName, age);
                humans.add(human);
            }
            connection.close();
            return humans;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return new LinkedList<>();
    }

    @Override
    public Optional<Human> findHumanById(int id) {
        try {
            var connection = dataSource.getConnection();
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            var statement = connection.prepareStatement("select * from human where id = ?");
            statement.setInt(1, id);
            var resultSet = statement.executeQuery();
            if(resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                int age = resultSet.getInt("age");
                return  Optional.of(new Human(id, name, lastName, age));
            } else return Optional.empty();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void clear() {
        try {
            var connection = dataSource.getConnection();
            connection.prepareStatement("truncate human").execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
