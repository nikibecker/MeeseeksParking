package edu.csus.ecs.athena.meeseeksparking;

import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ExecuteSQL extends AsyncTask <String, Void, Boolean> {

    /*
    Use this to Query (SELECT) the database.
    This is NOT for delete, update, or insert
     */
    @Override
    protected Boolean doInBackground(String... strs) {

        // Connect to database
        Connection connection = null;
        Boolean error = true;

        // check that the driver is installed
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            Log.e("TAG", e.getMessage(), e);
            //throw new ClassNotFoundException("MySQL JDBC driver NOT detected in library path.", e);
        }

        // Initialize connection object
        try {
            DatabaseConnector dbConn = new DatabaseConnector();

            // get connection
            connection = dbConn.getConnection();
        }
        catch (Exception e) {
            throw new SQLException("Failed to create connection to database.", e);
        }
        if (connection != null)
        {
            System.out.println("Successfully created connection to database.");

            // Perform some SQL queries over the connection.
            try {
                if (strs.length == 1) {
                    PreparedStatement preparedStatement = connection.prepareStatement(strs[0]);
                    preparedStatement.executeUpdate();
                }
                else
                    Log.e("Async Param", "Incorrect number of Params were passes to QuerySQL, only pass one param");

                connection.close();
            }
            catch (java.sql.SQLException e)
            {
                throw new SQLException("Encountered an error when executing given sql statement.", e);
            }
        }
        else {
            System.out.println("Failed to create connection to database.");
        }
        System.out.println("Execution finished.");
        return error;
    }

    @Override
    protected void onPostExecute(Boolean result) {

    }
}