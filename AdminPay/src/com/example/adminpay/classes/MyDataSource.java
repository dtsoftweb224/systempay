package com.example.adminpay.classes;

import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
 
public class MyDataSource {
 
    public static DataSource getMySQLDataSource() {
    
        MysqlDataSource mysqlDS = null;
		mysqlDS = new MysqlDataSource();
		mysqlDS.setURL("jdbc:mysql://localhost:3306/pay_system");
		//mysqlDS.setURL("jdbc:mysql://46.163.185.253:3306/pay_system");
		mysqlDS.setUser("root");
		//mysqlDS.setPassword("root");
		mysqlDS.setPassword("ghbphfr");
        return mysqlDS;
    }

}
