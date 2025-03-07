package Hadoop.Hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Hello world!
 *
 */
public class HBaseCustomClient {

	private HBaseAdmin admin;
	private Connection connection = null;

	public HBaseCustomClient(Configuration conf) throws IOException {

		connection = ConnectionFactory.createConnection(conf);
		admin = (HBaseAdmin) connection.getAdmin();
	}

	public void createTable(String tableName, String[] CFs) {

		try {
			if (admin.tableExists(tableName)) {

				System.out.println(tableName + "Already Exists");

			} else {

				HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));

				for (String CFName : CFs) {
					tableDescriptor.addFamily(new HColumnDescriptor(CFName));
				}

				admin.createTable(tableDescriptor);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteTable(String tableName) {

		try {
			if (admin.tableExists(tableName)) {

				admin.disableTable(tableName);
				admin.deleteTable(tableName);
			} else {
				System.out.println(tableName + " Doesn't exist");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void insertRecord(String tableName, String rowKey, String family, String qualifier, String value) {

		try {
			Table table = connection.getTable(TableName.valueOf(tableName));

			Put p = new Put(Bytes.toBytes(rowKey));

			p.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));

			table.put(p);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteRecord(String tableName, String rowKey) {

		try {
			Table table = connection.getTable(TableName.valueOf(tableName));
			Delete d = new Delete(Bytes.toBytes(rowKey));
			table.delete(d);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void printTable(String tablename) {

		ResultScanner rsObj = null;

		try {
			Table table = connection.getTable(TableName.valueOf(tablename));

			Scan s = new Scan();
			rsObj = table.getScanner(s);

			for (Result result : rsObj) {

				System.out.println(Bytes.toString(result.getRow()));
				for (Cell c : result.rawCells()) {
					System.out.println("Family: " + Bytes.toString(CellUtil.cloneFamily(c)));
					System.out.println("Qualifiers: " + Bytes.toString(CellUtil.cloneQualifier(c)));
					System.out.println("Values: " + Bytes.toString(CellUtil.cloneValue(c)));

				}

			}
			rsObj.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			rsObj.close();

			e.printStackTrace();
		}

	}
}
