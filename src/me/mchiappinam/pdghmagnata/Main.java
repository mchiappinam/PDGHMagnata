package me.mchiappinam.pdghmagnata;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public static String mysql_url=null;
    public static String mysql_user=null;
    public static String mysql_pass=null;
    public static String mysql_table=null;
    public boolean jaDivulgou=false;
	
	public void onEnable() {
		start();
	    getServer().getPluginManager().registerEvents(new Listeners(this), this);
	    getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §2ativando... - Plugin by: mchiappinam");
	    getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §2verificando config... - Plugin by: mchiappinam");
		File file = new File(getDataFolder(),"config.yml");
		if(!file.exists()) {
			try {
			    getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §2salvando config pela primeira vez... - Plugin by: mchiappinam");
				saveResource("config_template.yml",false);
				File file2 = new File(getDataFolder(),"config_template.yml");
				file2.renameTo(new File(getDataFolder(),"config.yml"));
			    getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §2config salva... - Plugin by: mchiappinam");
			}catch(Exception e) {}
		}
		mysql_url="jdbc:mysql://"+getConfig().getString("MySQL.host")+":"+getConfig().getInt("MySQL.porta")+"/"+getConfig().getString("MySQL.db");
		mysql_user=getConfig().getString("MySQL.usuario");
		mysql_pass=getConfig().getString("MySQL.senha");
		mysql_table=getConfig().getString("MySQL.tabela");
		try {
			Connection con = DriverManager.getConnection(mysql_url,mysql_user,mysql_pass);
			if (con == null) {
				getLogger().warning("ERRO: Conexao ao banco de dados MySQL falhou!");
				getServer().getPluginManager().disablePlugin(this);
			}else{
				getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §3Conectado ao banco de dados MySQL!");
			}
			con.close();
		}catch (SQLException e) {
			getLogger().warning("ERRO: Conexao ao banco de dados MySQL falhou!");
			getLogger().warning("ERRO: "+e.toString());
			getServer().getPluginManager().disablePlugin(this);
		}

	    getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §2ativado - Plugin by: mchiappinam");
	    getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §2Acesse: http://pdgh.com.br/");
	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHMagnata] §2Acesse: http://pdgh.com.br/");
	}
	
	public void start() {
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	  		public void run() {
	  			check();
	  		}
		}, 0, 300*20);
	}
	
	public void check() {
			Threads t = new Threads(this,"check");
			t.start();
	}
}