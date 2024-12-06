package me.mchiappinam.pdghmagnata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Threads extends Thread {
	private Main plugin;
	private String tipo;

	public Threads(Main pl,String tipo2) {
		plugin=pl;
		tipo=tipo2;
	}
    
    public static String calendario() {
		Calendar agora = Calendar.getInstance();
		SimpleDateFormat gdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
        return gdf.format(agora.getTime());
    }
	
	public void run() {
		switch(tipo) {
			case "check": {
		        try {
		        	Connection con = DriverManager.getConnection(Main.mysql_url,Main.mysql_user,Main.mysql_pass);
					//Prepared statement
					PreparedStatement pst = con.prepareStatement("SELECT * FROM  `"+Main.mysql_table+"` ORDER BY  `"+Main.mysql_table+"`.`balance` DESC LIMIT 0,1");
					ResultSet rs = pst.executeQuery();
		            while(rs.next()) {
		            	if(!plugin.getConfig().getString("magnata.nick").contains(rs.getString("username").toLowerCase())) {
		            		plugin.getServer().broadcastMessage(" ");
		            		plugin.getServer().broadcastMessage("§2§l"+plugin.getConfig().getString("magnata.nick")+"§a era §2[Magnata]§a desde "+plugin.getConfig().getString("magnata.desde")+", porém...");
		            		plugin.getServer().broadcastMessage("§2§l"+rs.getString("username")+"§a é o novo §2[Magnata]");
		            		plugin.getServer().broadcastMessage(" ");
		            		plugin.getConfig().set("magnata.nick", rs.getString("username").toLowerCase());
		            		plugin.getConfig().set("magnata.desde", calendario());
		            		plugin.getConfig().options().copyDefaults(true);
		            		plugin.saveConfig();
		            	}
		            }
					rs.close();
					pst.close();
					con.close();
					break;
				} catch (SQLException ex) {
					System.out.print(ex);
					break;
		        }
			}
		}
	}
}
