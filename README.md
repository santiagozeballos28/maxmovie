STEPS
-----
1.- Be located in the package -> (com.trueffect.conection.db)
2.- In the "DataConection" class modify the following data to connect to the database.
    /*
     * The server(database name)
     */
     public static final String SERVER = "jdbc:postgresql://localhost:5432/maxmovie3";// 
    /*
     * Name of user the data base
     */
    public static final String USER = "postgres";
    /*
     * Password of data base
     */
    public static final String PASSWORD = "m4msanti";
	
3.- be located in the project (C:\Users\santiago.mamani\Documents\NetBeansProjects\MaxMovieV15.0\MovieProyectWebV15) 
4.- Execute the following commands	
     >mvn clean install
	 >mvn tomcat7:run
	 
	 
NOTE.- 
It is recommended to do a "Clean and Build".
The project uses the port:8181
(http://localhost:8181/MovieProyectWeb4/rest/RenterUser?idUserSearch=2&firstName=jhon)