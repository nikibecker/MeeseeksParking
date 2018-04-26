package edu.csus.ecs.athena.meeseeksparking;

import android.database.SQLException;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

    /*-----------------------------------------------------------------------
        This class is for SELECT statements

        Example of how to use in Kotlin:
            Var sqlQueryStr = "SELECT * FROM users WHERE CSUSID = ? AND Password = ? AND PrivFlag = ?"
            var querySQL = QuerySQL()
            var results : ResultSet = querySQL.execute(sqlQueryStr,StringVar, IntVar, BitVar)
            ....
            Some Operations with results
            ....
            querySQL.close()    <-- dont forget this after you are done
                                    using the results variable, but not before
      --------------------------------------------------------------------------*/
public class QuerySQL {

    private DatabaseConnector dbConn = null;
    private Connection connection = null;
    private ResultSet results = null;

    protected QuerySQL() {

    }

    //Executes starts and executes the myAsyncTask class
    protected ResultSet execute(Object... params) {
        try {
            return new myAsyncTask().execute(params).get();
        }
        catch (Exception e) {
            Log.e("Async Param", "Something went wrong in the QuerySQL.execute()");
        }
        return null;
    }

    //Closes the connection. IMPORTANT: close only after the program
    // is done using the ResultSet.
    protected void close() {
        try {
            connection.close();
        }
        catch (java.sql.SQLException e) {
            throw new SQLException("Encountered an error when closing connection.", e);
        }
    }

    /*
            Use this to Query (SELECT) the database.
    This is NOT for delete, update, or insert.
    This class's execute accepts the params type 'PreparedStatement'
     */
    private class myAsyncTask extends AsyncTask <Object, Void, ResultSet> {
        @Override
        protected ResultSet doInBackground(Object... strs) {

            // Initialize connection object
            try {
                dbConn = new DatabaseConnector();
                connection = dbConn.getConnection();

            } catch (Exception e) {
                throw new SQLException("Failed to create connection to database.", e);
            }

            if (connection != null) {
                System.out.println("Successfully created connection to database.");

                // Perform some SQL queries over the connection.
                try {
                    if (strs.length > 0) {
                        PreparedStatement preparedStatement = connection.prepareStatement(strs[0].toString());
                        for (int i = 1; i < strs.length; i++)
                            preparedStatement.setObject(i, strs[i]);
                        results = preparedStatement.executeQuery();
                    } else
                        Log.e("Async Param", "Incorrect number of Params were passes to QuerySQL, only pass one param");

                } catch (java.sql.SQLException e) {
                    throw new SQLException("Encountered an error when executing given sql statement.", e);
                }
            } else {
                System.out.println("Failed to create connection to database.");
            }
            System.out.println("Execution finished.");
            return results;
        }

        @Override
        protected void onPostExecute(ResultSet results) {

        }
    }
}