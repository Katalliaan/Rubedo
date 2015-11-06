# Rubedo

![Screenshot](http://i.imgur.com/j7HJ1Vt.png)

##Make it
gradlew setupDecompWorkspace  
gradlew eclipse

##Run it
Eclipse Run configuration:

  Main class: GradleStart  
  Program Arguments: --version 1.7 --tweakClass cpw.mods.fml.common.launcher.FMLTweaker --accessToken FML --userProperties {} --assetIndex 1.7.10 --assetsDir C:\Users\Simon\.gradle\caches\minecraft\assets  
  VM Arguments: -Dfml.ignoreInvalidMinecraftCertificates=true -Xincgc -Xmx1024M -Xms1024M

##Build it
gradlew build
