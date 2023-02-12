# PlanetCrafterEditor
Little Tool for "The Planet Crafter"

This tool war created to __check save files for doubled items in containers__.

Items in Planet Crafter have an id with which they are identified but if these ids are saved in multiple containers/inventories and/or multiple times in a container/inventory one item shows up ingame as multiple items. 
Since crafting deletes the item, the item will be removed from all containers and crafting seems to use multiple items. Thus counting needed resources for a project can be difficult.

"View -> Check for double Inventory entries of Items" moves all doubled items from all containers into your inventory as a single entry. If the inventory does not have enough space it will increase the inventory size. To change back the size, search for __{"id":1,"woIds__ in the created save file and change the last number after __"size":__ back to 35 (for Backpack T5)

Features:
+ Check save files for doubled items in containers
+ This tool can show Locations of items on the map
+ Check for missing containers for inventories
+ Show the content of a specific container
+ Initial overview over all items and buildings in your save file on the command line
+ Initial comparison of Oxygen, Heat, Pressure, Plants and Insects to Earth on the command line

New (V0.2):
+ __Show not looted loot Crates__ (from Planet Crafter Version 0.6.008)

__Use this tool on your own risk__. Make backups of your save files ("C:\Users\%Username%\AppData\LocalLow\MijuGames\Planet Crafter") before using this tool eventhough there should not be any writing in any existing file. New Files are named __(Old filename)\_modified\_(ms since 1970).json__

[Download the tool](./Compiled Versions) (and install Java) and run it with the "start.bat" or over the command line: "java -jar PlanetCrafterEditor.jar".





You may not use this tool commercially / to make Money (especially because the picture of the map is not mine) but feel free to add your own functionalities to this horrible mess of code.

PS: Since there is no zoom in this tool you can use the "Magnify" tool from Windows. :)

This Tool was developed in [Eclipse](https://www.eclipse.org/downloads/) thus the source code is an Eclipse Project.
