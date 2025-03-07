package Hadoop.Hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

public class HBaseClientTest {

	public static void main(String[] args) throws IOException {
		
		Configuration conf = HBaseConfiguration.create();
		HBaseCustomClient client = new HBaseCustomClient(conf);
		String tableName = "Person";
		String[] families = { "Education", "WorkEx", "Skills" };
		
		client.deleteTable(tableName);
		client.createTable(tableName, families);
		client.insertRecord(tableName, "Achal", "Education", "High school", "Vidyanagar high schol");
		client.insertRecord(tableName, "Achal", "Education", "BE", "VGEC");
		client.insertRecord(tableName, "Achal", "Education", "MS", "IUB");
		
		client.insertRecord(tableName, "Achal", "WorkEx", "Innoventaa", "Java trainee");
		client.insertRecord(tableName, "Achal", "WorkEx", "TCS", "ASE");
		
		client.insertRecord(tableName, "Udit", "Skills", "Programing", "Python");
		client.insertRecord(tableName, "Udit", "Education", "MS", "IUB");

		client.printTable(tableName);

	}

}
