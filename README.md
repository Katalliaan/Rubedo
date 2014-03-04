# Rubedo

##Make it
gradlew setupDecompWorkspace
gradlew eclipse

##Run it
Run configuration:
  Main class: net.minecraft.launchwrapper.Launch
  Program Arguments: --version 1.6 --tweakClass cpw.mods.fml.common.launcher.FMLTweaker --username <name> --password <password>
  VM Arguments: -Dfml.ignoreInvalidMinecraftCertificates=true -Xincgc -Xmx1024M -Xms1024M

##Build it
gradlew build