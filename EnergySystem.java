package com.imooc.energy;

//能量守恒
public class EnergySystem {
    //能量盒子，能量储存的地方
    private final double[] energyBoxes;
    //创建一个锁对象
    private  final Object lockObj =new Object();
    /**
     * 定义了一个能量系统
     * @param n 能量盒子的个数
     * @param initialEnergy  每个能量盒子的初始值
     */
    public EnergySystem(int n,double initialEnergy){
        energyBoxes = new double[n];
        for(int i=0;i<energyBoxes.length;i++){
            energyBoxes[i]=initialEnergy;
        }
    }

    /**
     * 能量转移
     * @param from  能量的来源
     * @param to    能量转移的目的地
     * @param amount  转移能量的大小
     */
    public void transfer(int from,int to,double amount){
        //使用synchronized块来实现互斥
        synchronized (lockObj){
            //if(energyBoxes[from]<amount)
                //return;
            //while循环，保证条件不满足时任务都会被条件阻挡，而不是继续竞争CPU资源
            //wait set
            while(energyBoxes[from]<amount){
                try {
                    lockObj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.print(Thread.currentThread().getName());
            energyBoxes[from]-=amount;
            System.out.printf("从%d转移了%10.2f单位的能量到%d",from,amount,to);
            energyBoxes[to]+=amount;
            System.out.printf("能量总和：%10.2f%n",getTotalEnergies());
            //操作产生变化，等待的程序有可能条件满足，可以运行了
            //使用notifyAll来唤醒所有的等待的线程
            lockObj.notifyAll();
        }

    }

    /**
     * 获取能量世界的总和
     * @return
     */
    public double getTotalEnergies(){
        double sum=0;
        for(double amount:energyBoxes)
            sum += amount;
        return sum;
    }

    /**
     * 返回能量盒子的长度
     * @return
     */
    public int getBoxesAmount(){
        return energyBoxes.length;
    }
}
