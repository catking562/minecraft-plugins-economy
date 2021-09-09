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
		if(config.getString("접두어") == null) {
			config.set("접두어", "&6[&e골드&6]&e ");
		}
		if(config.getString("돈확인") == null) {
			config.set("돈확인", "&c{플레이어}&f님의 남은 &e골드&f는 {골드}&eG&f입니다");
		}
		if(config.getString("명령어오류") == null) {
			config.set("명령어오류", "무슨 명령어인지 잘 모르겠어요!");
		}
		for(String string : config.getKeys(false)) {
			message.put(string, config.getString(string));
		}
	}
	
	public void onEnable() {
		if(!setupEconomy()) {
            System.out.println("vault나 에센셜플러그인을 찾을 수 없습니다.");
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
		return message.get(string).replace("&", "§");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				p.sendMessage(getString("접두어") + getString("돈확인").replace("{플레이어}", p.getName()).replace("{골드}", econ.getBalance(p) + ""));
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("도움말")) {
					p.sendMessage(getString("접두어") + "/돈 - 자신의 돈을 확인합니다.");
					p.sendMessage(getString("접두어") + "/돈 <플레이어> - 해당 플레이어의 돈을 확인합니다.");
					p.sendMessage(getString("접두어") + "/돈 도움말 - 돈 관련 명령어를 확인합니다.");
					p.sendMessage(getString("접두어") + "/돈 보내기 <플레이어> <값> - 돈을 해당플레이어에게 보냅니다..");
					p.sendMessage(getString("접두어") + "/돈 순위 - 돈 순위를 봅니다. 1~10등 까지");
					p.sendMessage(getString("접두어") + "/돈 순위 <플레이어> - 해당 플레이어의 돈 순위를 봅니다.");
					if(p.hasPermission("economy.admin")) {
						p.sendMessage(getString("접두어") + "/돈 주기 <플레이어> <값> - 해당 플레이어에게 돈을 지급합니다. §c(op)");
						p.sendMessage(getString("접두어") + "/돈 빼기 <플레이어> <값> - 해당 플레이어의 돈을 뺍니다.. §c(op)");
						p.sendMessage(getString("접두어") + "/돈 설정 <플레이어> <값> - 해당 플레이어의 돈을 설정합니다. §c(op)");
					}
				}else if(args[0].equalsIgnoreCase("보내기")) {
					p.sendMessage(getString("접두어") + "/돈 보내기 <플레이어> <값> - 돈을 해당플레이어에게 보냅니다.");
				}else if(args[0].equalsIgnoreCase("순위")) {
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
				}else if(args[0].equalsIgnoreCase("주기")) {
					p.sendMessage(getString("접두어") + "/돈 보내기 <플레이어> <값> - 돈을 해당플레이어에게 보냅니다.");
				}else if(args[0].equalsIgnoreCase("빼기")) {
					p.sendMessage(getString("접두어") + "/돈 보내기 <플레이어> <값> - 돈을 해당플레이어에게 보냅니다..");
				}else if(args[0].equalsIgnoreCase("설정")) {
					p.sendMessage(getString("접두어") + "/돈 보내기 <플레이어> <값> - 돈을 해당플레이어에게 보냅니다..");
				}else {
					p.sendMessage(getString("접두어") + getString("명령어오류"));
				}
			}
		}else {
			System.out.println("플레이어만 입력할 수 있는 명령어입니다.!");
		}
		return super.onCommand(sender, command, label, args);
	}

}
