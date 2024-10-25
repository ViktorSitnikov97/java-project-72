package hexlet.code.repository;

import hexlet.code.model.Url;
import hexlet.code.utils.FormattedTime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UrlRepository extends BaseRepository {

    public static void save(Url url) throws SQLException {
        String sql = "INSERT INTO urls (name, created_at) VALUES (?, ?);";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String nameUrl = url.getName();
            Timestamp createdAt = FormattedTime.getCurrentTime();

            preparedStatement.setString(1, nameUrl);
            preparedStatement.setTimestamp(2, createdAt);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                url.setId(generatedKeys.getLong("id"));
                url.setCreatedAt(createdAt);
            } else {
                throw new SQLException("DB have not returned an id after saving an entity");
            }
        }
    }

    public static Optional<Url> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM urls WHERE id = ?;";

        try (Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                var urlName = resultSet.getString("name");
                var createdAt = resultSet.getTimestamp("created_at");
                Url url = new Url(urlName);
                url.setId(id);
                url.setCreatedAt(createdAt);
                return Optional.of(url);
            }
            return Optional.empty();
        }
    }

    public static Boolean existsByUrl(String urlName) throws SQLException {
        String sql = "SELECT * FROM urls WHERE name = ?;";

        try (var conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, urlName);
            var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }

    public static List<Url> getEntities() throws SQLException {
        String sql = "SELECT * FROM urls;";
        List<Url> urls = new ArrayList<>();

        try (var conn = dataSource.getConnection();
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String nameUrl = resultSet.getString("name");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Long id = resultSet.getLong("id");

                Url url = new Url(nameUrl);
                url.setId(id);
                url.setCreatedAt(createdAt);
                urls.add(url);
            }
            return urls;
        }
    }

    public static void delete(Long id) throws SQLException {
        String sql = "DELETE FROM urls WHERE id = ?;";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}