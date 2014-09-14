# Rubedo

##Make it
gradlew setupDecompWorkspace  
gradlew eclipse

##Run it
Eclipse Run configuration:

  Main class: net.minecraft.launchwrapper.Launch  
  Program Arguments: --version 1.6 --tweakClass cpw.mods.fml.common.launcher.FMLTweaker --accessToken FML --userProperties {} --assetIndex 1.7.10 --assetsDir %userprofile%\.gradle\caches\minecraft\assets  
  VM Arguments: -Dfml.ignoreInvalidMinecraftCertificates=true -Xincgc -Xmx1024M -Xms1024M

##Build it
gradlew build
