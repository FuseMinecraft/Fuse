Fuse 9.2.1
-----------
- Update error messages in Terminal

Fuse 9.2
--------
- Add launchpad enable/disable command

Fuse 9.1
--------
- Revamp launchpad system:
    - Now works with or without pressure plate

Fuse 9.0.1
----------
- Fix updater link

Fuse 9.0
--------
- Begin compatibility switch with new Fuse Networks

Trident 8.1
-----------
- Remove NMS checking from /glassmode (broken)
- Remove opkits option from /trident
- Typo corrections
- Update error messages for Terminal

Trident 8.0.1
-------------
- Bug fixes

Trident 8.0
-----------
- Revamp developer command to make it work properly
- Add proper NMS checking with /glassmode
- Change manage_access to superusers
- Add plugin disable/reload protection against ViaVersion
- Add restrictions on /tprandom
- Remove superusers from /trident debug and add dev config entry
- Remove dynamic opkit enable/disable
- Remove Launchpad effects due to 1.8 throwing errors

Trident 7.6
------------
- Now compile against 1.11
- Revamp potion listener for 1.8 compatibility
- Add protection against /glassmode (Need 1.9 or above to compile - 1.8 will return compile error)
- Add backwards compatibility for 1.8 to 1.11

Trident 7.5
-----------
- Change Telesphoreo to superkai69
- Add /opkits enable/disable (beta feature)

Trident 7.4
-----------
- Add /terminal

Trident 7.3.3.1
---------------
- Merge ModFreedom/TotalFreedomMod into PacksGamingHD/Trident
- Merge PacksGamingHD/Trident into PacksGamingHD/Trident

Trident 7.3.3
-------------
- Merge ModFreedom/TotalFreedomMod into PacksGamingHD/Trident

Trident 7.3.2
-------------
- Reduce console spam
- Clean up /trident help

Trident 7.3.1
-------------
- Hot fixes

Trident 7.3
-----------
- Remove plotworld
- More fixes to adminchat
- Updates to AutoUpdater
- Fixes typo
- Revert the way that join on spawn was handled

Trident 7.2.5
-------------
- Reworked adminchat

Trident 7.2.4
-------------
- Bug fixes

Trident 7.2.3
-------------
- Add NoFall
- Revise configuration

Trident 7.2.2
-------------
- Add /tprandom

Trident 7.2
-----------
- Change permission nodes to one simple permission node for every group

Trident 7.1
-----------
- Add Launchpads

Trident 7.0
-----------
- Add AutoUpdater

Trident 6.7
-----------
- Final release of Trident 6

Trident 6.5
-----------
- Delete ChatFilter
- Update Discord link
- Add latest commands to plugin.yml
- Add AutoUpdater system (not working)
- Add /update (in Trident.java)
- Create Updater

Trident 6.3
-----------
- Add ChatFilter
- Add SignPatch
- Minor revisions to /trident
- Change I_Like_Mac back to PacksGamingHD

Trident 6.2
-----------
- smh

Trident 6.1
-----------
- Add new config entry
- Add command /aman
- Update command /manage to move a player back to member
- Removed ViaVersion restriction

Trident 6.0.2
-------------
- Add OxLemonxO to manage

Trident 6.0.1
-------------
- Add I_Like_Windows to co owner

Trident 6.0
-----------
- Add a /manage command to manage players
- Updated /ai to be more clear
- Updated /contributors
- Fix grammar errors in /fw
- Fix usage error with /gcmd
- Remove compile date from /trident
- Add /ship - ship a player with another player
- Add /glassmode - Make yourself glow and invisible
- Fixed a bug where /: wouldn't display the message in creative
- Automatically clear lag every five minutes
- Update I_Like_Mac on /developer
- Switch using the plugin name to plugin.getName()

Trident 5.5.2
-------------
- Fix bugs with /rename

Trident 5.5
-----------
- Revert to NetBeans Configuration

Trident 5.3.1
-------------
- Bug fixes

Trident 5.3
-----------
- Add CommandBlocker

Trident 5.2
-----------
- Add a config option to enable/disable OP kits

Trident 5.1
-----------
- Update command loader
- Update PacksGamingHD to I_Like_Mac
- Update Nitrogen to Trident
- Update README.md

Trident 5.0
-----------
- Add a new config option: applications_enabled
- Removes information_name and information_owner
- Refractored package names
- Attempt to fix security issues with AdminChat and Commandspy
- Remove TheDankPhoenix from contributors and add swagteam123
- Fix flight bug in NUtil
- Add prison help in Command_information
- Add command: /website
- Update staff list
- Add prison kit
- Remove TheDankPhoenix's login message

Nitrogen v4.1
-------------
- Add /discord to plugin.yml
- Remove BoyFantasy and add Super_Skillz to Trial Moderator
- Fix flight bug in NUtil

Nitrogen v4.0
-------------
- Refractored package names
- Rename Sky.java -> Nitrogen.java
- Clean up clearlag and now it clears entities in all worlds
- Updated /contributors to add TheDankPhoenix
- Add command /expel - Push players away from you
- Rename Command_sky.java -> Command_nitrogen.java
- Add Command_plotworld.java which teleports a player to the plotworld or the bigger plotworld
- Change the message when a person attempts to disable or reload ViaVersion
- Add command /rename - Rename an item
- On KitPvP clear potion effects on join
- Update LoginMessages.java
- Fix PotionListener.java
- Show a date when a person compiles Nitrogen
- Add new stuff to NUtil
    - add bcastMsg
    - add playerMsg
    - add adminAction
    - add dateToString
    - add stringToDate
    - add colorize
    - add setFlying
    - add static class NEntityWiper
- Remove server.worldname
- Update LICENSE.md and README.md
- Add command /discord - Gives a link to the Discord server