/* This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package net.sf.l2j.gameserver.handler.usercommandhandlers;

import net.sf.l2j.gameserver.handler.IUserCommandHandler;
import net.sf.l2j.gameserver.model.L2CommandChannel;
import net.sf.l2j.gameserver.model.L2Party;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.serverpackets.SystemMessage;

/**
 * @author Chris
 */
public class ChannelLeave implements IUserCommandHandler
{
	private static final int[] COMMAND_IDS =
	{
		96
	};
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.l2j.gameserver.handler.IUserCommandHandler#useUserCommand(int, net.sf.l2j.gameserver.model.L2PcInstance)
	 */
	@Override
	public boolean useUserCommand(int id, L2PcInstance activeChar)
	{
		if (id != COMMAND_IDS[0])
		{
			return false;
		}
		
		if (activeChar.isInParty())
		{
			if (activeChar.getParty().isLeader(activeChar) && activeChar.getParty().isInCommandChannel())
			{
				L2CommandChannel channel = activeChar.getParty().getCommandChannel();
				L2Party party = activeChar.getParty();
				channel.removeParty(party);
				
				SystemMessage sm = SystemMessage.sendString("Your party has left the CommandChannel.");
				party.broadcastToPartyMembers(sm);
				sm = SystemMessage.sendString(party.getPartyMembers().get(0).getName() + "'s party has left the CommandChannel.");
				channel.broadcastToChannelMembers(sm);
				return true;
			}
		}
		
		return false;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.sf.l2j.gameserver.handler.IUserCommandHandler#getUserCommandList()
	 */
	@Override
	public int[] getUserCommandList()
	{
		return COMMAND_IDS;
	}
}
