package me.mrCookieSlime.sensibletoolbox.api.util;

import java.util.UUID;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import com.griefcraft.lwc.LWC;
import com.griefcraft.model.Protection;
import com.griefcraft.util.UUIDRegistry;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import me.desht.dhutils.Debugger;
import me.desht.dhutils.LogUtils;
import me.mrCookieSlime.sensibletoolbox.SensibleToolboxPlugin;
import me.mrCookieSlime.sensibletoolbox.api.SensibleToolbox;
import net.sacredlabyrinth.Phaed.PreciousStones.PreciousStones;

import com.bekvon.bukkit.residence.api.ResidenceApi;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.object.Plot;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;

/**
 * Check protections in force for various vanilla blocks and inventories.
 */
public class BlockProtection {
    private BlockProtectionType blockProtectionType = BlockProtectionType.BEST;
    private InvProtectionType invProtectionType = InvProtectionType.BEST;
	private Plugin isResAvailable = getServer().getPluginManager().getPlugin("Residence");
	private Plugin isPlotAvailable = getServer().getPluginManager().getPlugin("PlotSquared");
	private Plugin isSkyAvailable = getServer().getPluginManager().getPlugin("ASkyBlock");
	//test
//	Plugin plugin, FP;

    public PlotAPI pAPI;
    public BlockProtection(SensibleToolboxPlugin plugin) {
        Configuration config = plugin.getConfig();

        try {
            setInvProtectionType(InvProtectionType.valueOf(config.getString("inventory_protection").toUpperCase()));
        } catch (IllegalArgumentException e) {
            LogUtils.warning("invalid config value for 'inventory_protection' - using BEST");
            setInvProtectionType(InvProtectionType.WORLDGUARD);
        }

        try {
            setBlockProtectionType(BlockProtectionType.valueOf(config.getString("block_protection").toUpperCase()));
        } catch (IllegalArgumentException e) {
            LogUtils.warning("invalid config value for 'block_protection' - using BEST");
            setBlockProtectionType(BlockProtectionType.BEST);
        }
    }

    /**
     * Set the block protection method to be used by STB.  If a type of BEST
     * is supplied, STB will choose the best available protection method.
     *
     * @param blockProtectionType the desired protection type
     */
    public void setBlockProtectionType(BlockProtectionType blockProtectionType) {
        switch (blockProtectionType) {
            case BEST:
                //if (SensibleToolbox.getPluginInstance().isWorldGuardAvailable()) {
                    this.blockProtectionType = BlockProtectionType.BEST;
					getLogger().log(Level.INFO, "请注意：你正在选择的方块保护方法为 BEST，这意味着插件将尝试保护所有支持的插件！" );
               // }
                break;
            default:
                if (blockProtectionType.isAvailable()) {
                    this.blockProtectionType = blockProtectionType;
                } else {
                    LogUtils.warning("Block protection type " + blockProtectionType + " is not available");
                    this.blockProtectionType = BlockProtectionType.NONE;
                }
                break;
        }
        Debugger.getInstance().debug("Using block protection method: " + this.blockProtectionType);
    }


    /**
     * Set the inventory protection method to be used by STB.  If a type of
     * BEST is supplied, STB will choose the best available protection method.
     *
     * @param invProtectionType the desired protection type
     */
    public void setInvProtectionType(InvProtectionType invProtectionType) {
        switch (invProtectionType) {
            case BEST:
                /*if (SensibleToolbox.getPluginInstance().getLWC() != null) {
                    this.invProtectionType = InvProtectionType.LWC;
                } else if (SensibleToolbox.getPluginInstance().isWorldGuardAvailable()) {
                    this.invProtectionType = InvProtectionType.WORLDGUARD;
                } else{
                    this.invProtectionType = InvProtectionType.NONE;
                }*/
				this.invProtectionType = InvProtectionType.BEST;
					getLogger().log(Level.INFO, "请注意：你正在选择的物品保护方法为 BEST，这意味着插件将尝试保护所有支持的插件！" );
                break;
            default:
                if (invProtectionType.isAvailable()) {
                    this.invProtectionType = invProtectionType;
                } else {
                    LogUtils.warning("Inventory protection type " + invProtectionType + " is not available");
                    this.invProtectionType = InvProtectionType.NONE;
                }
                break;
        }
        Debugger.getInstance().debug("Using inventory protection method: " + this.invProtectionType);
    }

    /**
     * Check if the given player has access rights for the given block.
     *
     * @param player player to check
     * @param block block to check
     * @return true if the player may access the block's contents
     */
    @SuppressWarnings("deprecation")
	public boolean isInventoryAccessible(Player player, Block block) {
        switch (invProtectionType) {
            case LWC:
                LWC lwc = SensibleToolboxPlugin.getInstance().getLWC();
                Protection prot = lwc.findProtection(block);
                if (prot != null) {
                    boolean ok = lwc.canAccessProtection(player, prot);
                    Debugger.getInstance().debug(2, "LWC check: can " + player.getName() + " access " + block + "? " + ok);
                    return ok;
                } else {
                    return true;
                }
				/*
            case WORLDGUARD:

            case RESIDENCE:
                throw new IllegalArgumentException("should never get here!");*/
            case BEST:
			    boolean RES = true, WGUARD = true, ASKY = true;
				if (SensibleToolbox.getPluginInstance().getRes() != null) {
					ClaimedResidence residence = ResidenceApi.getResidenceManager().getByLoc(block.getLocation());
					if(residence==null) RES = true;
					ResidencePermissions perms = residence.getPermissions();
					RES = perms.playerHas(player, Flags.container, true);
				}
		        /*if(residence==null) RES = true;
		        ResidencePermissions perms = residence.getPermissions();
		        RES = perms.playerHas(player, Flags.container, true);*/
				if (SensibleToolbox.getPluginInstance().isWorldGuardAvailable() !=null) {
					ApplicableRegionSet set = WGBukkit.getRegionManager(block.getWorld()).getApplicableRegions(block.getLocation());
					//return set.allows(DefaultFlag.CHEST_ACCESS, WGBukkit.getPlugin().wrapPlayer(player));
					WGUARD = set.allows(DefaultFlag.CHEST_ACCESS, WGBukkit.getPlugin().wrapPlayer(player));
				}
				//start
				if (SensibleToolbox.getPluginInstance().isASkyBlockAvailable() !=null) {
					ASkyBlockAPI skyapi = ASkyBlockAPI.getInstance();
					if(skyapi.getIslandWorld() != null) {
						if (skyapi.getIslandWorld().equals(this.getLocation().getWorld())) {
							//this!
							if (skyapi.islandAtLocation(this.getLocation())) {
								UUID skyuuid = skyapi.getOwner(this.getLocation());
								if(skyuuid!=null) {
									if(skyuuid.equals(this.getPlayer().getUniqueId())) {
										ASKY = skyapi.getTeamMembers(skyuuid).contains(this.getPlayer().getUniqueId())
									}
								}
							}
						}
					}
				}
//end
				if (RES&&WGUARD&&ASKY) {
					return true;
				}else {
					player.sendMessage(ChatColor.RED + "你没有 " + ChatColor.GOLD + "容器使用(container)" + ChatColor.RED + " 权限");
					return false;
				}
            default:
                return true;
        }
    }

    /**
     * Check if the player with the given UUID has access rights for the given
     * block.
     * <p/>
     * Note that not all protection plugins may support checking by
     * UUID yet; if a plugin which doesn't support UUID checking is in force,
     * then this method will return false if the player is offline, even if
     * they would normally have permission.
     *
     * @param uuid UUID to check (may be null; if so, always return true)
     * @param block block to check
     * @return true if the player may access the block's contents
     */
    public boolean isInventoryAccessible(UUID uuid, Block block) {
        if (uuid == null) {
            return true;
        }
        switch (invProtectionType) {
            case LWC:
                LWC lwc = SensibleToolboxPlugin.getInstance().getLWC();
                Protection prot = lwc.findProtection(block);
                if (prot != null) {
                    boolean ok = uuid.equals(UUIDRegistry.getUUID(prot.getOwner()));
                    Debugger.getInstance().debug(2, "LWC check: can UUID " + uuid + " access " + block + "? " + ok);
                    return ok;
                } else {
                    return true;
                }
            case WORLDGUARD:
                Player player = Bukkit.getPlayer(uuid);
                return player != null && isInventoryAccessible(player, block);
            case BEST:
                throw new IllegalArgumentException("should never get here!");
            default:
                return true;
        }
    }

    /**
     * Check if the given player UUID has construction rights for the given
     * block.
     * </p>
     * Note that not all protection plugins may support checking by UUID yet;
     * if a plugin which doesn't support UUID checking is in force, then this
     * method will return false if the player is offline, even if they would
     * normally have permission.
     *
     * @param uuid the player's uuid
     * @param block the block to check for
     * @return true if the player has construction rights; false otherwise
     */
    public boolean playerCanBuild(UUID uuid, Block block, Operation op) {
        Player player = Bukkit.getPlayer(uuid);
        return player != null && playerCanBuild(player, block, op);
    }

    /**
     * Check if the given player UUID has construction rights for the given
     * block.
     *
     * @param player the player
     * @param block the block to check for
     * @return true if the player has construction rights; false otherwise
     */
	 //test
    public boolean playerCanBuild(Player player, Block block, Operation op) {
        switch (blockProtectionType) {
            case WORLDGUARD:
				// test code
            case PRECIOUS_STONES:
                switch (op) {
                    case PLACE:
                        return PreciousStones.API().canPlace(player, block.getLocation());
                    case BREAK:
                        return PreciousStones.API().canBreak(player, block.getLocation());
                    default:
                        return false;
                }
            case BEST:
			//!!!!
			    boolean WGPT = true, RESPT = true, PSPT = true, ASKYPT = true;
				// RES CHECK
		        //ClaimedResidence residence = ResidenceApi.getResidenceManager().getByLoc(block.getLocation());
		        //if(residence==null) RESPT = true;
		        //ResidencePermissions perms = residence.getPermissions();
				// PS CHECK
				pAPI = new PlotAPI();
				if (pAPI!=null){
					if(pAPI.isPlotWorld(block.getLocation().getWorld())){
			        com.intellectualcrafters.plot.object.Location loc = new com.intellectualcrafters.plot.object.Location(block.getLocation().getWorld().getName(), 
						(int) block.getLocation().getX(), 
						(int) block.getLocation().getY(), 
						(int) block.getLocation().getZ(), 
						block.getLocation().getYaw(), 
						block.getLocation().getPitch());
			        //if(loc.isPlotArea()){
						//!例子if(file!=null)就是如果file对象不为空，则执行if下面的语句
			        if(loc.isPlotArea()){
				    	Plot plot = pAPI.getPlot(block.getLocation());
				    	if(plot!=null){
							PSPT = false;
					    	if(plot.isAdded(player.getUniqueId())) {
								PSPT = true;
								Debugger.getInstance().debug(3, "PLOT STATUS(isAdd) " + PSPT);
							}else{
								PSPT = false;
							}
					    	if(plot.isOwner(player.getUniqueId())) {
								PSPT = true;
								Debugger.getInstance().debug(3, "PLOT STATUS(isOwner) " + PSPT);
							}else{
								PSPT = false;
							}
				        }else{
							PSPT = false;
						}
				//Debugger.getInstance().debug(3, "PLOT STATUS(Plot!=null) " + PSPT);
				
			        }else{
				        PSPT = false;
				        Debugger.getInstance().debug(3, "PLOT STATUS(Area) " + PSPT);
			        }
		}else{
			PSPT = true;
			Debugger.getInstance().debug(3, "PLOT STATUS(isOwner) " + PSPT);
		}
				}
						//!!
			if (SensibleToolbox.getPluginInstance().isASkyBlockAvailable() !=null) {
					ASkyBlockAPI skyapi = ASkyBlockAPI.getInstance();
					if(skyapi.getIslandWorld() != null) {
						if (skyapi.getIslandWorld().equals(this.getLocation().getWorld())) {
							//this!
							if (skyapi.islandAtLocation(this.getLocation())) {
								UUID skyuuid = skyapi.getOwner(this.getLocation());
								if(skyuuid!=null) {
									if(skyuuid.equals(this.getPlayer().getUniqueId())) {
										ASKY = skyapi.getTeamMembers(skyuuid).contains(this.getPlayer().getUniqueId())
									}
								}
							}
						}
					}
				}
		//TOTAL CHECK
				if (SensibleToolbox.getPluginInstance().isWorldGuardAvailable() !=null) {
                WGPT = WGBukkit.getPlugin().canBuild(player, block);}
				if (SensibleToolbox.getPluginInstance().getRes() !=null) {
				ClaimedResidence residence = ResidenceApi.getResidenceManager().getByLoc(block.getLocation());
				if(residence==null) {
					RESPT = true;
				}else{				
				ResidencePermissions perms = residence.getPermissions();
				RESPT = perms.playerHas(player, Flags.build, true);	
				}}
				
				if (WGPT&&RESPT&&PSPT) {
					return true;
				}
				player.sendMessage(ChatColor.RED + "你没有该区域的 " + ChatColor.GOLD + "BUILD" + ChatColor.RED + " 权限");
				return false;
				

            case RESIDENCE:
			// tresidence > residence
			// tperms > perms
			boolean WGRES = true, RESRES = true;
				WGRES = WGBukkit.getPlugin().canBuild(player, block);
		        ClaimedResidence tresidence = ResidenceApi.getResidenceManager().getByLoc(block.getLocation());
		        if(tresidence==null) return true;
		        ResidencePermissions tperms = tresidence.getPermissions();
		        RESRES = tperms.playerHas(player, Flags.build, true);
				if (WGRES&&RESRES) {
					return true;
				}else {
					return false;
				}
				//}


/*		        if(pAPI.isPlotWorld(block.getLocation().getWorld())){
			        com.intellectualcrafters.plot.object.Location loc = new com.intellectualcrafters.plot.object.Location(block.getLocation().getWorld().getName(), 
						(int) block.getLocation().getX(), 
						(int) block.getLocation().getY(), 
						(int) block.getLocation().getZ(), 
						block.getLocation().getYaw(), 
						block.getLocation().getPitch());
			        if(loc.isPlotArea()){
				    	Plot plot = pAPI.getPlot(block.getLocation());
				    		if(plot!=null){
					    	if(plot.isAdded(player.getUniqueId())) PSPT = true;
							Debugger.getInstance().debug(3, "PLOT STATUS(isAdd) " + PSPT);
					    	if(plot.isOwner(player.getUniqueId())) PSPT = true;
							Debugger.getInstance().debug(3, "PLOT STATUS(isOwner) " + PSPT);
				}
				PSPT = false;
				Debugger.getInstance().debug(3, "PLOT STATUS(Plot!=null) " + PSPT);
				
			}else{
				PSPT = true;
				Debugger.getInstance().debug(3, "PLOT STATUS(Area) " + PSPT);
			}
		}else{
			PSPT = true;
			Debugger.getInstance().debug(3, "PLOT STATUS(isOwner) " + PSPT);
		}
*/
				
			case PS:
			    pAPI = new PlotAPI();
		        if(pAPI.isPlotWorld(block.getLocation().getWorld())){
			        com.intellectualcrafters.plot.object.Location loc = new com.intellectualcrafters.plot.object.Location(block.getLocation().getWorld().getName(), 
						(int) block.getLocation().getX(), 
						(int) block.getLocation().getY(), 
						(int) block.getLocation().getZ(), 
						block.getLocation().getYaw(), 
						block.getLocation().getPitch());
			        if(loc.isPlotArea()){
				    	Plot plot = pAPI.getPlot(block.getLocation());
				    		if(plot!=null){
					    	if(plot.isAdded(player.getUniqueId())) return true;
					    	if(plot.isOwner(player.getUniqueId())) return true;
				}
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
		
		
            default:
                return true;
        }
    }

    /**
     * Defines the sort of protection STB will use to check if it can place or
     * break certain blocks.
     */
    public enum BlockProtectionType {
        /**
         * Use the best protection type available: WorldGuard, Precious Stones
         * or Bukkit, in that order.
         */
        BEST,
        /**
         * Residence Hook
         * */
        RESIDENCE,
        /**
         * Plotsquared Hook
         * */
        PS,
        /**
         * Use WorldGuard to check for protected regions.  If WorldGuard is
         * not installed, BUKKIT protection is used as a fallback.
         */
        WORLDGUARD,
        /**
         * Use the PreciousStones plugin to check for protected regions.
         * If PreciousStones is not installed, BUKKIT protection is used as a
         * fallback.
         */
        PRECIOUS_STONES,
        /**
         * No block protection at all (recommended only for single-player
         * or highly trusted player-base).
         */
        NONE;

        public boolean isAvailable() {
            switch (this) {
                case WORLDGUARD: return SensibleToolbox.getPluginInstance().isWorldGuardAvailable();
                case PRECIOUS_STONES: return SensibleToolbox.getPluginInstance().isPreciousStonesAvailable();
                default: return true;
            }
        }
    }

    /**
     * Defines the sort of protection STB will use to check if it can access
     * certain vanilla inventories, primarily chests (but also things like
     * hoppers/droppers/dispensers etc.)
     */
    public enum InvProtectionType {
        /**
         * Use the best protection type available: LWC, WorldGuard, none, in
         * that order.
         */
        BEST,
        /**
         * Residence Hook
         * */
        RESIDENCE,
        /**
         * Plotsquared Hook
         * */
        PS,
        /**
         * Use the LWC plugin for inventory protection.  LWC must be
         * installed; if not, there will be no inventory protection.
         */
        LWC,
        /**
         * Use the WorldGuard plugin for inventory protection.  WorldGuard
         * must be installed; if not, there will be no inventory protection.
         */
        WORLDGUARD,
        /**
         * No inventory protection at all (recommended only for single-player
         * or highly trusted player-base).
         */
        NONE;

        public boolean isAvailable() {
            switch (this) {
                case WORLDGUARD: return SensibleToolbox.getPluginInstance().isWorldGuardAvailable();
                case LWC: return SensibleToolbox.getPluginInstance().getLWC() != null;
                default: return true;
            }
        }
    }

    /**
     * The operation being checked.
     */
    public enum Operation {
        /**
         * Placing a block.
         */
        PLACE,
        /**
         * Breaking a block.
         */
        BREAK,
    }
}
