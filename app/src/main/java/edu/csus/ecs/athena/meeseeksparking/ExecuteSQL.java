package edu.csus.ecs.athena.meeseeksparking;

import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;

        //-----------------------------------------------------------
        //This class is for UPDATE, DELETE, & INSERT statements
        //-----------------------------------------------------------
public class ExecuteSQL extends AsyncTask <Object, Void, Boolean> {

    /*
        Creates a generic INSERT query, odd number of params must be passed (greater than 3)
        param 1: Name of table, the rest must be of the order (col1, col2... coln, val1, val2... valn)

     */
    @Override
    protected Boolean doInBackground(Object... strs) {

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
        if (connection != null) {
            System.out.println("Successfully created connection to database.");

            // Perform some SQL queries over the connection.
            try {
                if (strs.length > 0) {
                    PreparedStatement preparedStatement = connection.prepareStatement(strs[0].toString());
                    for(int i = 1; i < strs.length; i++)
                        preparedStatement.setObject(i, strs[i]);
                    preparedStatement.executeUpdate();
                }
                else
                    Log.e("Async Param", "Incorrect number of Params were passes to QuerySQL, only pass one param");

                connection.close();
            }
            catch (java.sql.SQLException e) {
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