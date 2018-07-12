# SensibleToolbox-RT
A reloaded version of stb
·重置版本的STB，修复了一堆花里胡哨的bug
·新增几个物品
·添加了自定义材质
·增加插件支持：Residence、PlotSquared
·原链接(bukkitDev):https://dev.bukkit.org/projects/sensible-toolbox


Sensible Toolbox adds a bunch of new items, tools, blocks, machines, an energy system, an item transfer system, plus a developer API to make it easy for other plugins to define their own items. It's heavily inspired by certain Forge-based mods, but of course requires no client modifications at all.

All my Plugins are using an Auto-Updater to make sure, you're always
using the latest and hopefully most bug free version of my Plugin.
If you don't want this Plugin to be auto-updated, you can turn it off
in the config file at "options -> auto-update: true/false"

They also use a Metrics-System which collects the following Informations:

A unique identifier
The server's version of Java
Whether the server is in offline or online mode
The plugin's version
The server's version
The OS version/name and architecture
The core count for the CPU
The number of players online
The Metrics version
Opting out of this service can be done by editing 
plugins/Plugin Metrics/config.yml and changing opt-out to true.

Known Plugin Incompatibilities

MultiInv: if you're carrying any STB items when you switch inventories, those items will lose their data, since MultiInv doesn't currently preserve extended item attributes which STB uses to store item-specific information. The MultiInv author does plan to add attribute support so hopefully a newer version of MultiInv will resolve this in the future. For now the workaround is to store any STB items before you switch inventories.
LWC: LWC is supported, but you must use a recent dev build of LWC with UUID support. Dev builds of LWC can be obtained from http://ci.griefcraft.com/job/LWC/. If you can't use a recent build, then you can disable LWC support: see Protection.
WorldEdit, Movecraft or indeed any plugin which move regions of blocks around or directly clear regions without firing events: don't use them with any region which contains an STB block. The physical block will be moved or removed, but STB doesn't (can't) know that and will keep a record for the associated STB object in memory. This could lead to all sorts of problems.
