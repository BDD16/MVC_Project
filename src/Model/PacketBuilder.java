/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ATX
 */
public class PacketBuilder {
    
    
    int sizeOfKey = 4096;
    int sizeOfPadding = 11;
    int sizeOfPacket = (sizeOfKey/8) - sizeOfPadding;
    
    
    byte[] Packet;
    
    public PacketBuilder(byte[] data){
        if((data.length <= sizeOfPacket) && (data.length > 0)){
          Packet = data;  
        }
        else{
            System.out.println("Data size is too large");
        }
        
    }
    
    
    
}
