package database;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import org.postgresql.PGStatement;

import parser.Conn;

public class selector {
	public ResultSet return_alfabe() throws SQLException {
		Connection c = null;
		Statement s = null;
		String connString = "jdbc:postgresql://localhost/" + Conn.idefixMeta_db;
		String sql = "SELECT * FROM alfabe;";

		c = DriverManager.getConnection(connString, Conn.user, Conn.pswd);
		s = c.createStatement();
		ResultSet rs = null;

		rs = s.executeQuery(sql);
		c.close();
		return rs;

	}

	public ResultSet return_select(Object[] rows, String db, String tb,
			String user, String pswd) throws SQLException, IOException {
		Connection c = null;
		Statement s = null;
		FileWriter fstream = new FileWriter(
				"/home/ataybur/workspace/module1/ek/Executer.txt", true);
		BufferedWriter out = new BufferedWriter(fstream);

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT ";

		for (Object row : rows) {
			if (row.toString().compareTo(rows[rows.length - 1].toString()) == 0)
				sql = sql + row;
			else
				sql = sql + row + ",";
		}
		sql = sql + " from " + tb + ";";

		c = DriverManager.getConnection(connString, user, pswd);
		s = c.createStatement();
		ResultSet rs = s.executeQuery(sql);
		c.close();
		return rs;

	}

	public ResultSet return_select_with_limit(Object[] rows, String db,
			String tb, Integer Limit, Integer Offset, String user, String pswd)
			throws SQLException, IOException {
		Connection c = null;
		Statement s = null;
		FileWriter fstream = new FileWriter(
				"/home/ataybur/workspace/module1/ek/Executer.txt", true);
		BufferedWriter out = new BufferedWriter(fstream);

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT ";

		for (Object row : rows) {
			try {
				if (row.toString().compareTo(rows[rows.length - 1].toString()) == 0)
					sql = sql + row;
				else
					sql = sql + row + ",";
			} catch (Exception e) {				
				System.out.println("rows.length: "+rows.length);
				System.out.println("row[0]: "+rows[0]);
			}
		}
		sql = sql + " from " + tb + " limit " + Limit.toString() + " offset "
				+ Offset + ";";

		c = DriverManager.getConnection(connString, user, pswd);
		s = c.createStatement();
		ResultSet rs = s.executeQuery(sql);
		c.close();

		return rs;

	}

	public ResultSet select_nitelik(String db, String tb, String id_name,
			Integer id, String user, String pswd) throws SQLException,
			IOException {
		Connection c = null;
		Statement s = null;

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "select id_nitelik, sum(sayi) from yayin_nitelik "
				+ "where id_yayin in (" + "select id_yayin from " + tb
				+ " where " + id_name + "=" + id + ") "
				+ "and id_nitelik in ( "
				+ "select id_nitelik from yayin_nitelik "
				+ "where id_yayin in ( " + "select id_yayin from " + tb
				+ " where " + id_name + "=" + id + ")) "
				+ "group by id_nitelik; ";
		System.out.println("select_nitelik: sql:" + sql);
		c = DriverManager.getConnection(connString, user, pswd);
		s = c.createStatement();
		ResultSet rs = s.executeQuery(sql);
		c.close();

		return rs;

	}

	public Integer return_selectedId(String db, String tb, String row_name,
			String row_value, String user, String pswd) throws SQLException,
			IOException {
		Connection c = null;
		Statement s = null;
		Integer id = null;

		FileWriter fstream = new FileWriter(
				"/home/ataybur/workspace/module1/ek/Executer.txt", true);
		BufferedWriter out = new BufferedWriter(fstream);

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT id FROM " + tb + " where " + row_name + "='"
				+ row_value.replace("'", " ") + "';";

		c = DriverManager.getConnection(connString, user, pswd);
		s = c.createStatement();
		ResultSet rs = s.executeQuery(sql);
		while (rs.next())
			id = rs.getInt(1);

		c.close();

		return id;
	}

	public LinkedList<Integer> return_selectedColumnDiscinct(String db,
			String tb, String column_name, Integer Limit, Integer Offset,
			String user, String pswd) throws SQLException, IOException {
		Connection c = null;
		Statement s = null;
		LinkedList<Integer> id = new LinkedList<Integer>();

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT distinct " + column_name + " FROM " + tb
				+ " limit " + Limit.toString() + " offset " + Offset + ";";

		c = DriverManager.getConnection(connString, user, pswd);
		s = c.createStatement();
		ResultSet rs = s.executeQuery(sql);
		while (rs.next())
			id.add(rs.getInt(1));

		c.close();

		return id;
	}

	public LinkedList<Integer> return_selectedIdArray(String db, String tb,
			String out_row_name, String row_name, String row_value,
			String user, String pswd) throws SQLException, IOException {
		Connection c = null;
		Statement s = null;
		LinkedList<Integer> id = new LinkedList<Integer>();
		int counter = 0;
		FileWriter fstream = new FileWriter(
				"/home/ataybur/workspace/module1/ek/Executer.txt", true);
		BufferedWriter out = new BufferedWriter(fstream);

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT " + out_row_name + " FROM " + tb + " where "
				+ row_name + "='" + row_value.replace("'", " ") + "';";

		c = DriverManager.getConnection(connString, user, pswd);
		s = c.createStatement();
		ResultSet rs = s.executeQuery(sql);

		while (rs.next())
			id.add(rs.getInt(1));

		c.close();

		return id;
	}

	public Integer return_count(String db, String tb, String user, String pswd)
			throws SQLException, IOException {
		Connection c = null;
		Statement s = null;
		Integer id = null;

		FileWriter fstream = new FileWriter(
				"/home/ataybur/workspace/module1/ek/Executer.txt", true);
		BufferedWriter out = new BufferedWriter(fstream);

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT count(*) FROM " + tb + ";";

		c = DriverManager.getConnection(connString, user, pswd);
		s = c.createStatement();
		ResultSet rs = s.executeQuery(sql);
		while (rs.next())
			id = rs.getInt(1);

		c.close();

		return id;
	}

	public Integer return_select_count_with_where(Object[] rows, String db,
			String tb, Object[] data_contents, String user, String pswd) {

		Connection c = null;
		Integer count = 0;
		Statement s = null;

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT count(*) from " + tb + " where ";

		for (int i = 0; i < rows.length; i++) {
			if (rows[i].toString().compareTo(rows[rows.length - 1].toString()) == 0) {
				if (data_contents[i].getClass().getName()
						.compareTo("java.lang.String") == 0)
					sql = sql + rows[i] + " like \'" + data_contents[i] + "\';";
				else
					sql = sql + rows[i] + " = " + data_contents[i];
			} else if (data_contents[i].getClass().getName()
					.compareTo("java.lang.String") == 0)
				sql = sql + rows[i] + " like \'" + data_contents[i] + "\'"
						+ " and ";
			else
				sql = sql + rows[i] + " = " + data_contents[i] + " and ";
		}
		try {
			c = DriverManager.getConnection(connString, user, pswd);
			s = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ResultSet rs = null;
		try {
			rs = s.executeQuery(sql);
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (rs.next())
				count = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;

	}

	public Integer return_select_id_with_where(Object[] rows, String db,
			String tb, Object[] data_contents, String user, String pswd) {

		Connection c = null;
		Integer id = 0;
		Statement s = null;

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT id from " + tb + " where ";

		for (int i = 0; i < rows.length; i++) {
			if (rows[i].toString().compareTo(rows[rows.length - 1].toString()) == 0) {
				if (data_contents[i].getClass().getName()
						.compareTo("java.lang.String") == 0)

					sql = sql + rows[i] + " like \'"
							+ data_contents[i].toString().replace("\'", "")
							+ "\';";

				else
					sql = sql + rows[i] + " = " + data_contents[i];
			} else if (data_contents[i].getClass().getName()
					.compareTo("java.lang.String") == 0)
				sql = sql + rows[i] + " like \'"
						+ data_contents[i].toString().replace("\'", "") + "\'"
						+ " and ";
			else
				sql = sql + rows[i] + " = " + data_contents[i] + " and ";
		}
		try {
			c = DriverManager.getConnection(connString, user, pswd);
			s = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ResultSet rs = null;

		try {
			rs = s.executeQuery(sql);
			c.close();
		} catch (SQLException e) {
			System.out.println("sql: " + sql);
			e.printStackTrace();
		}

		try {
			if (rs.next())
				id = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
if(id==0)
	System.out.println("sql: " + sql);
		return id;

	}

	public ResultSet return_select_with_where(Object[] rows, String db,
			String tb, Object[] data_contents, String user, String pswd) {

		Connection c = null;
		Integer count = 0;
		Statement s = null;

		String connString = "jdbc:postgresql://localhost/" + db;
		String sql = "SELECT * from " + tb + " where ";

		for (int i = 0; i < rows.length; i++) {
			if (rows[i].toString().compareTo(rows[rows.length - 1].toString()) == 0) {
				if (data_contents[i].getClass().getName()
						.compareTo("java.lang.String") == 0)
					sql = sql + rows[i] + " like \'" + data_contents[i] + "\';";
				else
					sql = sql + rows[i] + " = " + data_contents[i];
			} else if (data_contents[i].getClass().getName()
					.compareTo("java.lang.String") == 0)
				sql = sql + rows[i] + " like \'" + data_contents[i] + "\'"
						+ " and ";
			else
				sql = sql + rows[i] + " = " + data_contents[i] + " and ";
		}
		try {
			c = DriverManager.getConnection(connString, user, pswd);
			s = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		ResultSet rs = null;
		try {
			rs = s.executeQuery(sql);
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;

	}

}
