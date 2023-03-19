/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 2016-Dec-21
 * Modified by:   kenneth
 *
 * Copyright 2016 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package uk.ac.ebi.metabolights.referencelayer;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by kenneth on 21/12/2016.
 */
public class PostgresSqlLoader  {

    private Properties statementsSql;


    static Properties properties = new Properties();

    public Connection getPostgresConnection(String fileName){

        try {
            properties = loadProperties(fileName);  //Read properties from the the provided file
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName(getDriver());
        } catch (ClassNotFoundException e) {

            System.out.println("Cannot find the PostgreSQL JDBC Driver. Include in your library path!");
            e.printStackTrace();
        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(getUrl(), getUser(),getPassword());

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

        }

        if (connection != null) {
            System.out.println("Database connection successful");
        } else {
            System.out.println("Failed to make database connection!");
        }

        return connection;
    }


    private String getUrl(){
        StringBuilder url = new StringBuilder();
        if (getDriver().toLowerCase().contains("postgres")){
            url.append("jdbc:postgresql://").append(getHost())
                    .append(':').append(getPort()).append('/').append(getInstance());
         } else {
            throw new IllegalArgumentException("Only PostgreSQL supported, for Oracle and MySQL use the SQLLoader class: " + getDriver());
        }
        return url.toString();
    }


    private static Properties loadProperties(String fileName)
            throws IOException {
        InputStream is = PostgresSqlLoader.class.getClassLoader().getResourceAsStream(fileName + ".properties");
        if (is == null){
            String message = "Unable to open configuration file " +  fileName;
            throw new IOException(message);
        }

        properties.load(is);
        String schema = properties.getProperty("schema");
        if (schema == null){
            properties.setProperty("schema", properties.getProperty("user"));
        }
        String instance = properties.getProperty("instance");
        if (instance == null){
            properties.setProperty("instance", fileName);
        }
        return properties;
    }

    public String getDriver () { return properties.getProperty("driver"); }

    public String getInstance () {
        return properties.getProperty("instance");
    }

    public String getHost () {
        return properties.getProperty("host");
    }

    public String getPort () {
        return properties.getProperty("port");
    }

    public String getUser () {
        return properties.getProperty("user");
    }

    public String getPassword () {
        return properties.getProperty("password");
    }




}
