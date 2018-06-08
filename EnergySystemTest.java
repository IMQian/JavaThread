package com.imooc.energy;

public class EnergySystemTest {
    //设定能量盒子的个数为100
    public static final int BOX_AMOUNT = 100;
    //设定每个能量盒子的初始能量为1000
    public static final double INITIAL_ENERGY=1000;

    public static void main(String[] args){
        EnergySystem eng = new EnergySystem(BOX_AMOUNT,INITIAL_ENERGY);
        for(int i=0;i<BOX_AMOUNT;i++){
            EnergyTransferTask energyTransfer = new EnergyTransferTask(eng,i,INITIAL_ENERGY);
            Thread transfer = new Thread(energyTransfer,"TransferThread_"+i);
            transfer.start();
        }
    }

}
