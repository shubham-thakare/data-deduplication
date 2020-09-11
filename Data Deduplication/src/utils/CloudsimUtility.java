package utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.File;
import org.cloudbus.cloudsim.HarddriveStorage;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.ParameterException;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class CloudsimUtility {
	private static List<Cloudlet> cloudletList;
	private static List<Vm> vmlist;

        public static void simulateCloudSim(String filePath, int fileSize) throws ParameterException{
            Log.printLine("Starting Cloudsim...");

            int num_user = 1;
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = false;
            CloudSim.init(num_user, calendar, trace_flag);
            
            HarddriveStorage hd1 = new HarddriveStorage(CommonProperties.storageSize);
            System.out.println("\n* * *");
            System.out.println("Used disk space on HarddriveStorageOne=" + hd1.getCurrentSize());
            System.out.println("* * *\n");
            
            if(fileSize <= 0)
                System.out.println("Duplicate file present...\nStopping upload operation...\n\n");
            
            File file1 = new File(filePath, fileSize);
            hd1.addFile(file1);
            LinkedList hdList;
            hdList = new LinkedList();
            hdList.add(hd1);
            
            Datacenter mydc = createDatacenter("DataCenter");
            DatacenterBroker broker = createBroker();
            int brokerId = broker.getId();
            CloudSim.startSimulation();
            System.out.println("\n* * *");
            System.out.println("Used disk space on HarddriveStorageOne=" + (int) hd1.getCurrentSize()/1024);
            System.out.println("* * *\n");
            double capacity1=hd1.getAvailableSpace();
        }

	private static Datacenter createDatacenter(String name) {
		List<Host> hostList = new ArrayList<>();
		List<Pe> peList = new ArrayList<>();
		int mips = 1000;                
		peList.add(new Pe(0, new PeProvisionerSimple(mips)));

		int hostId = 0;
		int ram = 2048;
		long storage = 1000000;
		int bw = 10000;

		hostList.add(
			new Host(
				hostId,
				new RamProvisionerSimple(ram),
				new BwProvisionerSimple(bw),
				storage,
				peList,
				new VmSchedulerTimeShared(peList)
			)
		);
                
		String arch = "x86"; 
		String os = "Linux";
		String vmm = "Xen";
		double time_zone = 10.0;
		double cost = 3.0; 
		double costPerMem = 0.05;
		double costPerStorage = 0.001;
		double costPerBw = 0.0; 
		LinkedList<Storage> storageList = new LinkedList<>(); 
		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch, os, vmm, hostList, time_zone, cost, costPerMem,
				costPerStorage, costPerBw);

		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
		}

		return datacenter;
	}

	private static DatacenterBroker createBroker() {
		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			return null;
		}
		return broker;
	}
}