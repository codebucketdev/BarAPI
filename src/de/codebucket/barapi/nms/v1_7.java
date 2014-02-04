package de.codebucket.barapi.nms;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Location;

import de.codebucket.barapi.Util;

public class v1_7 
{
	
	public static Object getSpawnPacket(FakeDragon fd) 
	{
		Class<?> Entity = Util.getCraftClass("Entity");
		Class<?> EntityLiving = Util.getCraftClass("EntityLiving");
		Class<?> EntityEnderDragon = Util.getCraftClass("EntityEnderDragon");
		Object packet = null;
		try 
		{
			fd.dragon = EntityEnderDragon.getConstructor(new Class[] { Util.getCraftClass("World") }).newInstance(new Object[] { fd.world });
			Method setLocation = Util.getMethod(EntityEnderDragon, "setLocation", new Class[] { Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE });
			setLocation.invoke(fd.dragon, new Object[] { Integer.valueOf(fd.x), Integer.valueOf(fd.y), Integer.valueOf(fd.z), Integer.valueOf(fd.pitch), Integer.valueOf(fd.yaw) });
			Method setInvisible = Util.getMethod(EntityEnderDragon, "setInvisible", new Class[] { Boolean.TYPE });
			setInvisible.invoke(fd.dragon, new Object[] { Boolean.valueOf(fd.visible) });
			Method setCustomName = Util.getMethod(EntityEnderDragon, "setCustomName", new Class[] { String.class });
			setCustomName.invoke(fd.dragon, new Object[] { fd.name });
			Method setHealth = Util.getMethod(EntityEnderDragon, "setHealth", new Class[] { Float.TYPE });
			setHealth.invoke(fd.dragon, new Object[] { Float.valueOf(fd.health) });

			Field motX = Util.getField(Entity, "motX");
			motX.set(fd.dragon, Byte.valueOf(fd.xvel));

			Field motY = Util.getField(Entity, "motX");
			motY.set(fd.dragon, Byte.valueOf(fd.yvel));

			Field motZ = Util.getField(Entity, "motX");
			motZ.set(fd.dragon, Byte.valueOf(fd.xvel));

			Method getId = Util.getMethod(EntityEnderDragon, "getId", new Class[0]);
			fd.EntityID = ((Integer) getId.invoke(fd.dragon, new Object[0])).intValue();
			Class<?> PacketPlayOutSpawnEntityLiving = Util.getCraftClass("PacketPlayOutSpawnEntityLiving");
			packet = PacketPlayOutSpawnEntityLiving.getConstructor(new Class[] { EntityLiving }).newInstance(new Object[] { fd.dragon });
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}

		return packet;
	}

	public static Object getDestroyPacket(FakeDragon fd) 
	{
		Class<?> PacketPlayOutEntityDestroy = Util.getCraftClass("PacketPlayOutEntityDestroy");

		Object packet = null;
		try 
		{
			packet = PacketPlayOutEntityDestroy.newInstance();
			Field a = PacketPlayOutEntityDestroy.getDeclaredField("a");
			a.setAccessible(true);
			a.set(packet, new int[] { fd.EntityID });
		} 
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		}

		return packet;
	}

	public static Object getMetaPacket(FakeDragon fd, Object watcher) 
	{
		Class<?> DataWatcher = Util.getCraftClass("DataWatcher");
		Class<?> PacketPlayOutEntityMetadata = Util.getCraftClass("PacketPlayOutEntityMetadata");

		Object packet = null;
		try 
		{
			packet = PacketPlayOutEntityMetadata.getConstructor(new Class[] { Integer.TYPE, DataWatcher, Boolean.TYPE }).newInstance(new Object[] { Integer.valueOf(fd.EntityID), watcher, Boolean.valueOf(true) });
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}

		return packet;
	}

	public static Object getTeleportPacket(FakeDragon fd, Location loc) 
	{
		Class<?> PacketPlayOutEntityTeleport = Util.getCraftClass("PacketPlayOutEntityTeleport");

		Object packet = null;
		try 
		{
			packet = PacketPlayOutEntityTeleport.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Byte.TYPE, Byte.TYPE })
					.newInstance(new Object[] { Integer.valueOf(fd.EntityID), 
							Integer.valueOf(loc.getBlockX() * 32), 
							Integer.valueOf(loc.getBlockY() * 32), 
							Integer.valueOf(loc.getBlockZ() * 32), 
							Byte.valueOf((byte) ((int) loc.getYaw() * 256 / 360)), 
							Byte.valueOf((byte) ((int) loc.getPitch() * 256 / 360)) });
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		}
		catch (SecurityException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}

		return packet;
	}

	public static Object getWatcher(FakeDragon fd) 
	{
		Class<?> Entity = Util.getCraftClass("Entity");
		Class<?> DataWatcher = Util.getCraftClass("DataWatcher");

		Object watcher = null;
		try 
		{
			watcher = DataWatcher.getConstructor(new Class[] { Entity }).newInstance(new Object[] { fd.dragon });
			Method a = Util.getMethod(DataWatcher, "a", new Class[] { Integer.TYPE, Object.class });
			a.invoke(watcher, new Object[] { Integer.valueOf(0), Byte.valueOf((byte) (fd.visible ? 0 : 32)) });
			a.invoke(watcher, new Object[] { Integer.valueOf(6), Float.valueOf(fd.health) });
			a.invoke(watcher, new Object[] { Integer.valueOf(7), Integer.valueOf(0) });
			a.invoke(watcher, new Object[] { Integer.valueOf(8), Byte.valueOf((byte) 0) });
			a.invoke(watcher, new Object[] { Integer.valueOf(10), fd.name });
			a.invoke(watcher, new Object[] { Integer.valueOf(11), Byte.valueOf((byte) 1) });
		} 
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) 
		{
			e.printStackTrace();
		}
		return watcher;
	}
}
