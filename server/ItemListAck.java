/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wtserver.server;

import wtserver.client.ServerMsg;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;
import wtserver.database.Users;

/**
 *
 * @author MSI
 */
public class ItemListAck extends ServerMsg {
    private final short msgId = ServerMsg.CS_IN_ITEMLIST_ACK;
    private int user_id = 0;
    
    public ItemListAck(int user_id)
    {
        this.user_id = user_id;
        size = 8;
        buffer = ByteBuffer.allocate(8192);
    }
    
    public byte [] getData(short seqNum)
    {
        buffer.position(0);
        byte random = (byte) new Random().nextInt();
        addByte(random);
        addShort(msgId);
        addShort(seqNum);
        addShort((short)0);
        byte checksum = 0;
        addByte(checksum);
        addByte((byte) 0);
        int nItems = 4;
        Users users = new Users();
        ArrayList<Users.Item> items = users.getItems(user_id);
        nItems += items.size();
        addByte((byte) nItems);
        size += 2;
        int Unique[] = {2000000000, 2000000001, 2000000002, 2000000003};
        int Items[] = {3001, 1001, 2001, 9001};
        for (int i = 0; i < 4; i++) {
            size += 33;
            addInteger(Unique[i]);
            addShort((short) Items[i]);
            addShort((short) 0);
            addShort((short) 0);
            addShort((short) 0);
            addShort((short) 0);
            addByte((byte) 0xE);
            addAsciiString("2015071222320" + i);
            addInteger((short) 1);
        }
        for (int i = 0; i < items.size(); i++) {
            size += 33;
            addInteger(items.get(i).id);
            addShort((short) items.get(i).type);
            addShort((short) 0);
            addShort((short) 0);
            addShort((short) 0);
            addShort((short) 0);
            String expiry = items.get(i).expiry;
            expiry = expiry.replace(" ","");
            expiry = expiry.replace("-","");
            expiry = expiry.replace(":","");
            addByte((byte) expiry.length());
            addAsciiString(expiry);
            addInteger((short) 1);
        }
        /*byte b[] = {0x00, 0x0C, 0x1D, 0x61, (byte)0xC1, 0x0A, (byte)0xE9, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x35, 0x30, 0x37, 0x31, 0x32, 0x32, 0x32, 0x33, 0x32, 0x30, 0x39, (byte)0xFF, (byte)0xFF, 0x00, 0x00, 0x1E, 0x61, (byte)0xC1, 0x0A, (byte)0xD1, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x35, 0x30, 0x37, 0x31, 0x32, 0x32, 0x32, 0x33, 0x32, 0x30, 0x39, (byte)0xFF, (byte)0xFF, 0x00, 0x00, 0x1F, 0x61, (byte)0xC1, 0x0A, (byte)0xD0, 0x0B, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x35, 0x30, 0x37, 0x31, 0x32, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, (byte)0xFF, (byte)0xFF, 0x00, 0x00, 0x20, 0x61, (byte)0xC1, 0x0A, (byte)0x95, 0x13, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x35, 0x30, 0x37, 0x31, 0x32, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, (byte)0xFF, (byte)0xFF, 0x00, 0x00, 0x21, 0x61, (byte)0xC1, 0x0A, 0x79, 0x17, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x35, 0x30, 0x37, 0x31, 0x32, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, (byte)0xFF, (byte)0xFF, 0x00, 0x00, 0x22, 0x61, (byte)0xC1, 0x0A, 0x29, 0x23, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x35, 0x30, 0x37, 0x31, 0x32, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, (byte)0xFF, (byte)0xFF, 0x00, 0x00, 0x23, 0x61, (byte)0xC1, 0x0A, 0x7C, 0x2F, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x35, 0x30, 0x37, 0x31, 0x32, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, (byte)0xFF, (byte)0xFF, 0x00, 0x00, 0x24, 0x61, (byte)0xC1, 0x0A, (byte)0xBB, 0x0B, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x34, 0x30, 0x38, 0x31, 0x31, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, 0x06, 0x00, 0x00, 0x00, 0x26, 0x61, (byte)0xC1, 0x0A, (byte)0x8A, 0x13, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x34, 0x30, 0x38, 0x31, 0x31, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, 0x06, 0x00, 0x00, 0x00, 0x27, 0x61, (byte)0xC1, 0x0A, 0x71, 0x17, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x34, 0x30, 0x38, 0x31, 0x31, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, 0x06, 0x00, 0x00, 0x00, 0x29, 0x61, (byte)0xC1, 0x0A, 0x2A, 0x23, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x34, 0x30, 0x38, 0x31, 0x31, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, 0x06, 0x00, 0x00, 0x00, 0x2A, 0x61, (byte)0xC1, 0x0A, 0x2E, 0x23, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0E, 0x32, 0x30, 0x31, 0x34, 0x30, 0x38, 0x31, 0x31, 0x32, 0x32, 0x33, 0x32, 0x31, 0x30, 0x06, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        
        for(int i = 0; i < b.length; i++)
        {
            addByte(b[i]);
        }*/
        
        size = (short) buffer.position();
        
        if((size - 8) % 16 > 0)
        {
            size -= (size - 8) % 16;
            size += 16;
        }
        short pSize = (short) ((size - 8) / 16);
        buffer.position(5);
        addShort(pSize);
        for(int i = 0; i < 7; i++)
        {
            checksum += buffer.get(i);
        }
        addByte(checksum);
        return buffer.array();
    }
}
