package com.exelatech.ecx.backend.BLL;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import com.exelatech.ecx.backend.dao.Dao;
import com.exelatech.ecx.backend.dao.EcxLookup;
import com.exelatech.ecx.backend.domain.Proftpd;
import com.exelatech.ecx.backend.service.IMapping;

public class ProftpdMap implements IMapping<ArrayList<Proftpd>> {

    static Logger log = Logger.getLogger(ProftpdMap.class.getName());
    private List<EcxLookup> EcxPlatformMap;
    private List<EcxLookup> EcxSLAMap;

    /**
     * Request data to mapping and fill SLA field
     */
    @Override
    public void RequestData() {
        if (EcxPlatformMap == null && EcxSLAMap == null) {
            log.info("Connecting to database");
            Dao<EcxLookup> dao = new Dao<EcxLookup>("ecx");

            log.info("Getting PRINT_PLATFORM_MAP from EcxLookupTable");
            this.EcxPlatformMap = dao.selectMultiple("EcxLookupTable.getByType", "type", "PRINT_PLATFORM_MAP");

            log.info("Getting PRINT_PLATFORM_RITS from EcxLookupTable");
            this.EcxSLAMap = dao.selectMultiple("EcxLookupTable.getByType", "type", "PRINT_PLATFORM_RITS");

            dao.Close();
        }
    }

    /**
     * Mapping data with platform and sla field
     */
    @Override
    public void Mapping(ArrayList<Proftpd> DataToMap) {
        MappPlattform(DataToMap);
        MappSLAField(DataToMap);
    }

    /**
     * Private function to map with platform column and exclude according to ecx
     * database
     */
    private void MappPlattform(ArrayList<Proftpd> DataToMap) {
        ArrayList<Proftpd> itemsToRemove = new ArrayList<Proftpd>();

        log.info("Mapping PRINT_PLATFORM_MAP");
        for (int map_idx = 0; map_idx < EcxPlatformMap.size(); map_idx++) {
            EcxLookup platform = EcxPlatformMap.get(map_idx);

            for (int index = 0; index < DataToMap.size(); index++) {
                Proftpd item = DataToMap.get(index);

                if (item.getUser()!= null && item.getUser().endsWith("t"))
                    itemsToRemove.add(item);

                if (item.getUser()!= null && platform.getId().getName().toLowerCase().equals(item.getUser().toLowerCase())) {
                    if (platform.getRule().getIgnore().equals("Y") || (item.getUser()!= null && item.getUser().endsWith("t")) ) { // IGNORE PLATFORM
                                                                                                      // OR USER TEST
                        itemsToRemove.add(item);
                    } else {
                        if (item.getAppCode() == null || item.getAppCode().isEmpty())
                            item.setAppCode(platform.getRule().getDefaultAppCode());

                        item.setPlatform(platform.getId().getValue()); // Map the platform
                        item.setSource(platform.getRule().getSource()); // Splunk sources that contain data for this
                                                                        // platform

                    }
                }
            }
        }

        DataToMap.removeAll(itemsToRemove);
    }

    /**
     * Private function to map with sla column
     */
    private void MappSLAField(ArrayList<Proftpd> DataToMap) {
        log.info("Mapping PRINT_PLATFORM_RITS");
        for (int map_idx = 0; map_idx < EcxSLAMap.size(); map_idx++) {
            EcxLookup sla = EcxSLAMap.get(map_idx);

            List<Proftpd> proftpdSelected = DataToMap.stream().filter(x -> x.getAppCode() !=null && x.getAppCode().equals(sla.getId().getName().trim()))
                    .collect(Collectors.toList());
            
            for (int index = 0; index < proftpdSelected.size(); index++) {
                Proftpd item = proftpdSelected.get(index);
                item.setSLA(sla.getId().getValue().trim());
            }
        }

    }

    public ProftpdMap() {
    }

}