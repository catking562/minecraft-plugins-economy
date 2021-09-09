package economy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class main extends JavaPlugin {
	
	public static Economy econ;
	
	Map<String, String> message = new HashMap<String, String>();
	
	private void config(File file, YamlConfiguration config) {
		if(config.getString("���ξ�") == null) {
			config.set("���ξ�", "&6[&e���&6]&e ");
		}
		if(config.getString("��Ȯ��") == null) {
			config.set("��Ȯ��", "&c{�÷��̾�}&f���� ���� &e���&f�� {���}&eG&f�Դϴ�");
		}
		if(config.getString("��ɾ����") == null) {
			config.set("��ɾ����", "���� ��ɾ����� �� �𸣰ھ��!");
		}
		for(String string : config.getKeys(false)) {
			message.put(string, config.getString(string));
		}
	}
	
	public void onEnable() {
		if(!setupEconomy()) {
            System.out.println("vault�� �������÷������� ã�� �� �����ϴ�.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		File file = new File(getDataFolder() + "/message.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		config(file, config);
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public String getString(String string) {
		return message.get(string).replace("&", "��");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				p.sendMessage(getString("���ξ�") + getString("��Ȯ��").replace("{�÷��̾�}", p.getName()).replace("{���}", econ.getBalance(p) + ""));
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("����")) {
					p.sendMessage(getString("���ξ�") + "/�� - �ڽ��� ���� Ȯ���մϴ�.");
					p.sendMessage(getString("���ξ�") + "/�� <�÷��̾�> - �ش� �÷��̾��� ���� Ȯ���մϴ�.");
					p.sendMessage(getString("���ξ�") + "/�� ���� - �� ���� ��ɾ Ȯ���մϴ�.");
					p.sendMessage(getString("���ξ�") + "/�� ������ <�÷��̾�> <��> - ���� �ش��÷��̾�� �����ϴ�..");
					p.sendMessage(getString("���ξ�") + "/�� ���� - �� ������ ���ϴ�. 1~10�� ����");
					p.sendMessage(getString("���ξ�") + "/�� ���� <�÷��̾�> - �ش� �÷��̾��� �� ������ ���ϴ�.");
					if(p.hasPermission("economy.admin")) {
						p.sendMessage(getString("���ξ�") + "/�� �ֱ� <�÷��̾�> <��> - �ش� �÷��̾�� ���� �����մϴ�. ��c(op)");
						p.sendMessage(getString("���ξ�") + "/�� ���� <�÷��̾�> <��> - �ش� �÷��̾��� ���� ���ϴ�.. ��c(op)");
						p.sendMessage(getString("���ξ�") + "/�� ���� <�÷��̾�> <��> - �ش� �÷��̾��� ���� �����մϴ�. ��c(op)");
					}
				}else if(args[0].equalsIgnoreCase("������")) {
					p.sendMessage(getString("���ξ�") + "/�� ������ <�÷��̾�> <��> - ���� �ش��÷��̾�� �����ϴ�.");
				}else if(args[0].equalsIgnoreCase("����")) {
					ArrayList<OfflinePlayer> pl = new ArrayList<OfflinePlayer>();
					ArrayList<Integer> in = new ArrayList<>();
					for(OfflinePlayer offp : Bukkit.getOfflinePlayers()) {
						pl.add(offp);
						in.add(1);
					}
					for (int i = 0; i < pl.size(); i++) {
						in.set(i, 1);
						for (int j = 0; j < in.size(); j++) {
							if(econ.getBalance(pl.get(i)) < econ.getBalance(pl.get(j))) {
								in.set(i, in.get(i) + 1);
							}
						}
					}
					//asdffafafdfsafasfsfafafsaf
				}else if(args[0].equalsIgnoreCase("�ֱ�")) {
					p.sendMessage(getString("���ξ�") + "/�� ������ <�÷��̾�> <��> - ���� �ش��÷��̾�� �����ϴ�.");
				}else if(args[0].equalsIgnoreCase("����")) {
					p.sendMessage(getString("���ξ�") + "/�� ������ <�÷��̾�> <��> - ���� �ش��÷��̾�� �����ϴ�..");
				}else if(args[0].equalsIgnoreCase("����")) {
					p.sendMessage(getString("���ξ�") + "/�� ������ <�÷��̾�> <��> - ���� �ش��÷��̾�� �����ϴ�..");
				}else {
					p.sendMessage(getString("���ξ�") + getString("��ɾ����"));
				}
			}
		}else {
			System.out.println("�÷��̾ �Է��� �� �ִ� ��ɾ��Դϴ�.!");
		}
		return super.onCommand(sender, command, label, args);
	}

}
